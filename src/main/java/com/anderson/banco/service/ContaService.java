package com.anderson.banco.service;

import com.anderson.banco.exceptions.DeleteAccountException;
import com.anderson.banco.exceptions.InvalidValueException;
import com.anderson.banco.exceptions.NotFoundException;
import com.anderson.banco.exceptions.RequestConstraintException;
import com.anderson.banco.model.request.CompraModelRequest;
import com.anderson.banco.model.db.CompraModelDb;
import com.anderson.banco.model.response.CompraModelResponse;
import com.anderson.banco.model.request.ContaModelRequest;
import com.anderson.banco.model.db.ContaModelDb;
import com.anderson.banco.model.response.ContaModelResponse;
import com.anderson.banco.repository.ContaRepository;
import com.anderson.banco.repository.CompraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Service //Transforma a interface em um Bean para o Spring
public class ContaService {

	public static final double LIMITE_MAX_DE_DEPOSITO = 5000.00;
	@Autowired //é para gerenciar a dependência, só possível para classes Beans do Spring
	ContaRepository contaRepository;

	@Autowired //é para gerenciar a dependência, só possível para classes Beans do Spring
	CompraRepository compraRepository;

	public ContaModelResponse getConta(String uuid) {
		return this.contaParaResposta(this.obterConta(uuid));
	}

	public List<ContaModelResponse> getContas(){
		List<ContaModelDb> contasDb = contaRepository.findAll();
		List<ContaModelResponse> contasResponse = new ArrayList<>();

		for(ContaModelDb contaDb: contasDb){
			ContaModelResponse contaResponse = this.contaParaResposta(contaDb);
			contasResponse.add(contaResponse);
		}

		return contasResponse;
	}


	public ContaModelResponse criarConta(ContaModelRequest contaRequest) {
		if(contaRepository.existsByRg(contaRequest.getRg())) throw new RequestConstraintException("Rg já cadastrado");

		ContaModelDb contaDb = new ContaModelDb();
		contaDb.setId(UUID.randomUUID().toString());
		contaDb.setValor(new BigDecimal("0.00"));
		contaDb.setNome(contaRequest.getNome());
		contaDb.setRg(contaRequest.getRg());
		
		contaRepository.save(contaDb);

		return this.contaParaResposta(contaDb);
	}
	
	public ContaModelResponse depositarDinheiro(String uuid, BigDecimal valor) {
		ContaModelDb contaDb = this.obterConta(uuid);
		valor = valor.setScale(2, RoundingMode.DOWN);

		if(valor.doubleValue() > 0) {
			if(valor.doubleValue() <= LIMITE_MAX_DE_DEPOSITO) {
				contaDb.setValor(contaDb.getValor().add(valor));
				contaRepository.save(contaDb);
			}else throw new InvalidValueException("depositar: valor acima de R$ 5000,00");
		}else throw new InvalidValueException("depositar: valor abaixo de R$ 0,01");
		
		return this.contaParaResposta(contaDb);
	}
	
	public ContaModelResponse sacarDinheiro(String uuid, BigDecimal valor) {
		ContaModelDb contaDb = this.obterConta(uuid);
		valor = valor.setScale(2, RoundingMode.DOWN);

		if(valor.doubleValue() > 0) {
			if(valor.doubleValue() <= contaDb.getValor().doubleValue()) {
				contaDb.setValor(contaDb.getValor().subtract(valor));
				contaRepository.save(contaDb);
			}else throw new InvalidValueException("sacar: valor de saque acima do valor depositado na conta");
		}else throw new InvalidValueException("sacar: valor abaixo de R$ 0,01");
		
		return this.contaParaResposta(contaDb);
	}
	
	public ContaModelResponse tranferir(String uuidRetira, String uuidRecebe, BigDecimal valor){
		ContaModelDb contaDbRetira = this.obterConta(uuidRetira);
		ContaModelDb contaDbRecebe = this.obterConta(uuidRecebe);
		valor = valor.setScale(2, RoundingMode.DOWN);

		if(valor.doubleValue() < 0.01) throw new InvalidValueException("tranferir: valor abaixo de R$ 0,01");
		if(contaDbRetira.getValor().doubleValue() < valor.doubleValue()) throw new InvalidValueException("transferir: valor de transferência acima do valor depositado na conta");

		contaDbRetira.setValor(contaDbRetira.getValor().subtract(valor));
		contaDbRecebe.setValor(contaDbRecebe.getValor().add(valor));

		contaRepository.save(contaDbRetira);
		contaRepository.save(contaDbRecebe);

		return this.contaParaResposta(contaDbRetira);
	}
	
	public void deletarConta(String uuid){	
		ContaModelDb contaDb = this.obterConta(uuid);
		if(contaDb.getValor().doubleValue() == 0) {
			while(contaDb.getCompras().size() > 0){
				CompraModelDb compraDb = contaDb.getCompras().get(contaDb.getCompras().size() - 1);
				compraRepository.deleteById(compraDb.getId());
				contaDb.getCompras().remove(compraDb);
			}
			contaRepository.deleteById(uuid);
		}
		else throw new DeleteAccountException();
	}
	
	//sub-recurso Compra

	public CompraModelResponse criarCompra(String uuid, CompraModelRequest compraRequest){
		ContaModelDb contaDb = this.obterConta(uuid);

		CompraModelDb compraDb = new CompraModelDb();
		compraDb.setTitulo(compraRequest.getTitulo());
		compraDb.setValor(compraRequest.getValor().setScale(2, RoundingMode.DOWN));
		compraDb.setConta(contaDb);

		if(compraDb.getValor().doubleValue() > contaDb.getValor().doubleValue()) throw new InvalidValueException("comprar: valor insuficiente na conta");
		else{
			contaDb.setValor(contaDb.getValor().subtract(compraDb.getValor()));
			contaRepository.save(contaDb);
		}

		compraRepository.save(compraDb);

		return this.compraParaResposta(compraDb);
	}

	public List<CompraModelResponse> getCompras(String uuid){
		ContaModelDb contaDb = this.obterConta(uuid);
		List<CompraModelResponse> comprasResponse = new ArrayList<>();

		for(CompraModelDb compraDb: contaDb.getCompras()){
			CompraModelResponse compraResponse = this.compraParaResposta(compraDb);
			comprasResponse.add(compraResponse);
		}

		return comprasResponse;
	}

	public CompraModelResponse getCompra(String uuid, int id_compra){
		ContaModelDb contaDb = this.obterConta(uuid);
		CompraModelDb compraDb = compraRepository.findById(id_compra).orElseThrow(() -> new NotFoundException("compra: " + id_compra));

		if (contaDb != compraDb.getConta()) throw new NotFoundException("compra: " + id_compra);

		return this.compraParaResposta(compraDb);
	}

	//Método auxiliar

    private ContaModelDb obterConta(String uuid){
		return contaRepository.findById(uuid).orElseThrow(() -> new NotFoundException("conta: " + uuid));
	}

	private ContaModelResponse contaParaResposta(ContaModelDb contaDb){
		ContaModelResponse contaResponse = new ContaModelResponse();

		contaResponse.setId(contaDb.getId());
		contaResponse.setNome(contaDb.getNome());
		contaResponse.setRg(contaDb.getRg());
		contaResponse.setValor(contaDb.getValor());

		return contaResponse;
	}

	private CompraModelResponse compraParaResposta(CompraModelDb compraDb){
    	CompraModelResponse compraResponse = new CompraModelResponse();
    	
    	compraResponse.setId(compraDb.getId());
    	compraResponse.setTitulo(compraDb.getTitulo());
    	compraResponse.setValor(compraDb.getValor());
    	
    	return compraResponse;
	}
}