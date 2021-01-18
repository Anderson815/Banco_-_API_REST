package com.anderson.banco.service;

import com.anderson.banco.exceptions.DeleteAccountException;
import com.anderson.banco.exceptions.InvalidValueException;
import com.anderson.banco.exceptions.NotFoundException;
import com.anderson.banco.exceptions.RequestConstraintException;
import com.anderson.banco.model.request.CompraModelRequest;
import com.anderson.banco.model.response.CompraModelResponse;
import com.anderson.banco.model.request.ContaModelRequest;
import com.anderson.banco.model.response.ContaModelResponse;
import com.anderson.banco.repository.ContaRepository;
import com.anderson.banco.repository.CompraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service //Transforma a interface em um Bean para o Spring
public class ContaService {

	public static final double LIMITE_MAX_DE_DEPOSITO = 5000.00;
	@Autowired //é para gerenciar a dependência, só possível para classes Beans do Spring
	ContaRepository contaRepository;

	@Autowired //é para gerenciar a dependência, só possível para classes Beans do Spring
	CompraRepository compraRepository;

	public ContaModelResponse getConta(String uuid) {
		return this.obterConta(uuid);
	}
	
	public List<ContaModelResponse> getContas(){
		return contaRepository.findAll();
	}
	
	public ContaModelResponse criarConta(ContaModelRequest contaRequest) {
		if(contaRepository.existsByRg(contaRequest.getRg())) throw new RequestConstraintException("Rg já cadastrado");

		ContaModelResponse contaResponse = new ContaModelResponse();
		contaResponse.setId(UUID.randomUUID().toString());
		contaResponse.setValor(new BigDecimal("0.00"));
		contaResponse.setNome(contaRequest.getNome());
		contaResponse.setRg(contaRequest.getRg());
		
		contaRepository.save(contaResponse);
		return contaResponse;
	}
	
	public ContaModelResponse depositarDinheiro(String uuid, BigDecimal valor) {
		ContaModelResponse conta = this.obterConta(uuid);
		if(valor.doubleValue() > 0) {
			if(valor.doubleValue() <= LIMITE_MAX_DE_DEPOSITO) {
				conta.setValor(conta.getValor().add(valor));
				contaRepository.save(conta);
			}else throw new InvalidValueException("depositar: valor acima de R$ 5000,00");
		}else throw new InvalidValueException("depositar: valor abaixo de R$ 0,01");
		
		return conta;	
	}
	
	public ContaModelResponse sacarDinheiro(String uuid, BigDecimal valor) {
		ContaModelResponse conta = this.obterConta(uuid);
		if(valor.doubleValue() > 0) {
			if(valor.doubleValue() <= conta.getValor().doubleValue()) {
				conta.setValor(conta.getValor().subtract(valor));
				contaRepository.save(conta);
			}else throw new InvalidValueException("sacar: valor de saque acima do valor depositado na conta");
		}else throw new InvalidValueException("sacar: valor abaixo de R$ 0,01");
		
		return conta;
	}

	public ContaModelResponse tranferir(String uuidRetira, String uuidRecebe, BigDecimal valor){
		ContaModelResponse contaRetira = this.obterConta(uuidRetira);
		ContaModelResponse contaRecebe = this.obterConta(uuidRecebe);
		if(valor.doubleValue() < 0.01) throw new InvalidValueException("tranferir: valor abaixo de R$ 0,01");
		if(contaRetira.getValor().doubleValue() < valor.doubleValue()) throw new InvalidValueException("transferir: valor de transferência acima do valor depositado na conta");

		contaRetira.setValor(contaRetira.getValor().subtract(valor));
		contaRecebe.setValor(contaRecebe.getValor().add(valor));

		contaRepository.save(contaRetira);
		contaRepository.save(contaRecebe);

		return contaRetira;
	}

	public void deletarConta(String uuid){	
		ContaModelResponse conta = this.obterConta(uuid);
		if(conta.getValor().doubleValue() == 0) {
			while(conta.getCompras().size() > 0){
				CompraModelResponse compra = conta.getCompras().get(conta.getCompras().size() - 1);
				compraRepository.deleteById(compra.getId());
				conta.getCompras().remove(compra);
			}
			contaRepository.deleteById(uuid);
		}
		else throw new DeleteAccountException();
	}

	//sub-recurso Compra

	public CompraModelResponse criarCompra(String uuid, CompraModelRequest compraRequest){
		ContaModelResponse conta = this.obterConta(uuid);

		CompraModelResponse compraResponse = new CompraModelResponse();
		compraResponse.setTitulo(compraRequest.getTitulo());
		compraResponse.setValor(compraRequest.getValor());
		compraResponse.setConta(conta);

		if(compraResponse.getValor().doubleValue() > conta.getValor().doubleValue()) throw new InvalidValueException("comprar: valor insuficiente na conta");
		else{
			conta.setValor(conta.getValor().subtract(compraResponse.getValor()));
			contaRepository.save(conta);
		}

		compraRepository.save(compraResponse);

		return compraResponse;
	}

	public List<CompraModelResponse> getCompras(String uuid){
		ContaModelResponse conta = this.obterConta(uuid);
		return conta.getCompras();
	}

	public CompraModelResponse getCompra(String uuid, int id_compra){
		ContaModelResponse conta = this.obterConta(uuid);
		CompraModelResponse compra = compraRepository.findById(id_compra).orElseThrow(() -> new NotFoundException("compra: " + id_compra));

		if (conta != compra.getConta()) throw new NotFoundException("compra: " + id_compra);

		return compra;
	}
	
	//Método auxiliar

    private ContaModelResponse obterConta(String uuid){
		return contaRepository.findById(uuid).orElseThrow(() -> new NotFoundException("conta: " + uuid));
	}

}