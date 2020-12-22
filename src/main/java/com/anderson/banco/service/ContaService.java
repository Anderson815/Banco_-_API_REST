package com.anderson.banco.service;

import com.anderson.banco.exceptions.DeleteAccountException;
import com.anderson.banco.exceptions.InvalidValueException;
import com.anderson.banco.exceptions.NotFoundException;
import com.anderson.banco.model.CompraModelRequest;
import com.anderson.banco.model.CompraModelResponse;
import com.anderson.banco.model.ContaModelResponse;
import com.anderson.banco.repository.ContaRepository;
import com.anderson.banco.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ContaService {

	public static final double LIMITE_MAX_DE_DEPOSITO = 5000.00;
	@Autowired
	ContaRepository contaRepository;


	@Autowired
	CompraRepository compraRepository;

	public ContaModelResponse getConta(String uuid) {
		this.verificarUuid(uuid);
		return contaRepository.findById(uuid).get();
	}
	
	public List<ContaModelResponse> getContas(){
		return contaRepository.findAll();
	}
	
	public ContaModelResponse criarConta(ContaModelResponse contaModelResponse) {
		contaModelResponse.setId(UUID.randomUUID().toString());
		contaModelResponse.setValor(new BigDecimal("0.00"));
		
		contaRepository.save(contaModelResponse);
		return contaModelResponse;
	}
	
	public ContaModelResponse depositarDinheiro(String uuid, BigDecimal valor) {
		this.verificarUuid(uuid);
		
		ContaModelResponse c = contaRepository.findById(uuid).get();
		if(valor.doubleValue() > 0) {
			if(valor.doubleValue() <= LIMITE_MAX_DE_DEPOSITO) {
				c.setValor(c.getValor().add(valor));
				contaRepository.save(c);
			}else throw new InvalidValueException("depositar: valor acima de R$ 5000,00");
		}else throw new InvalidValueException("depositar: valor abaixo de R$ 0,01");
		
		return c;	
	}
	
	public ContaModelResponse sacarDinheiro(String uuid, BigDecimal valor) {
		this.verificarUuid(uuid);
		
		ContaModelResponse c = contaRepository.findById(uuid).get();
		if(valor.doubleValue() > 0) {
			if(valor.doubleValue() <= c.getValor().doubleValue()) {
				c.setValor(c.getValor().subtract(valor));
				contaRepository.save(c);
			}else throw new InvalidValueException("sacar: valor de saque acima do valor depositado na conta");
		}else throw new InvalidValueException("sacar: valor abaixo de R$ 0,01");
		
		return c;
	}

	public void deletarConta(String uuid){	
		this.verificarUuid(uuid);
		if(contaRepository.findById(uuid).get().getValor().doubleValue() == 0) contaRepository.deleteById(uuid);
		else throw new DeleteAccountException();
	}

	//sub-recurso Compra

	public CompraModelResponse criarCompra(String uuid, CompraModelRequest compraRequest){
		this.verificarUuid(uuid);
		ContaModelResponse conta = contaRepository.findById(uuid).get();

		CompraModelResponse compraResponse = new CompraModelResponse();
		compraResponse.setTitulo(compraRequest.getTitulo());
		compraResponse.setValor(compraRequest.getValor());
		compraResponse.setConta(conta);


		if(compraResponse.getValor().doubleValue() > conta.getValor().doubleValue()) throw new InvalidValueException("comprar: a conta não possui esse dinheiro");
		else{
			conta.setValor(conta.getValor().subtract(compraResponse.getValor()));
			contaRepository.save(conta);
		}


		compraRepository.save(compraResponse);

		return compraResponse;
	}

	public List<CompraModelResponse> getCompras(String uuid){
		this.verificarUuid(uuid);
		ContaModelResponse conta = contaRepository.findById(uuid).get();

		return conta.getCompras();
	}

	public CompraModelResponse getCompra(String uuid, int id_compra){
		this.verificarUuid(uuid);
		this.verificarIdCompra(uuid, id_compra);

		return compraRepository.findById(id_compra).get();
	}
	
	//Método auxiliar

	private void verificarUuid(String uuid){
		if(!contaRepository.existsById(uuid)){
			throw new NotFoundException("conta: " + uuid);
		}
	}

	private void verificarIdCompra(String uuid, int id_compra){
		if(compraRepository.existsById(id_compra)){
			CompraModelResponse compra = compraRepository.findById(id_compra).get();
			if(!compra.getConta().getId().equals(uuid)) throw new NotFoundException("compra: " + Integer.toString(id_compra));
		}else{
			throw new NotFoundException("compra: " + Integer.toString(id_compra));
		}
	}
}
