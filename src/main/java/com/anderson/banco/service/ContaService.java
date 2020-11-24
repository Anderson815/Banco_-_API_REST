package com.anderson.banco.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anderson.banco.exceptions.DeleteAccountException;
import com.anderson.banco.exceptions.InvalidValue;
import com.anderson.banco.exceptions.NotFound;
import com.anderson.banco.model.Conta;
import com.anderson.banco.repository.Contas;

@Service
public class ContaService {
	
	@Autowired
	Contas contas;
	
	public Conta getConta(String uuid) {
		this.verificarUuid(uuid);
		return contas.findById(uuid).get();	
	}
	
	public List<Conta> getContas(){
		return contas.findAll();
	}
	
	public Conta criarConta(Conta conta) {
		conta.setId(UUID.randomUUID().toString());
		conta.setValor(new BigDecimal("0.00"));
		
		contas.save(conta);
		
		return conta;
	}
	
	public Conta depositarDinheiro(String uuid, BigDecimal valor) {		
		this.verificarUuid(uuid);
		
		Conta c = contas.findById(uuid).get();
		if(valor.doubleValue() > 0 && valor.doubleValue() <= 5000.00) {
			c.setValor(c.getValor().add(valor));
			contas.save(c);
		}else throw new InvalidValue("Valor inválido para depositar");
		
		return c;	
	}
	
	public Conta sacarDinheiro(String uuid, BigDecimal valor) {		
		this.verificarUuid(uuid);
		
		Conta c = contas.findById(uuid).get();		
		if(valor.doubleValue() > 0 && valor.doubleValue() <= c.getValor().doubleValue()) {
			c.setValor(c.getValor().subtract(valor));
			contas.save(c);
		}else throw new InvalidValue("Valor inválido para sacar");
		
		return c;
	}
		
	public void deletarConta(String uuid){	
		this.verificarUuid(uuid);
		if(contas.findById(uuid).get().getValor().doubleValue() == 0) contas.deleteById(uuid);
		else throw new DeleteAccountException();
	}
	
	private void verificarUuid(String uuid){
		if(!contas.existsById(uuid)){
			throw new NotFound(uuid); 
		}
	}
}
