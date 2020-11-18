package com.anderson.banco.controller;

import java.util.List;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.banco.model.Conta;
import com.anderson.banco.repository.Contas;

@RestController
@RequestMapping("/conta")
public class ContaController {
	
	@Autowired
	Contas contas;
	
	@GetMapping(value="/{uuid}")
	public ResponseEntity<Conta> getConta(@PathVariable String uuid){
		if(contas.findById(uuid).isEmpty()) return new ResponseEntity(HttpStatus.NO_CONTENT);
		else return new ResponseEntity(contas.findById(uuid).get(), HttpStatus.OK);			
	}
		
	@GetMapping
	public ResponseEntity<List<Conta>> getContas(){
		//ResponseEntity é um objeto de resposta do tipo REST devolvendo um JSON completo
		return new ResponseEntity(contas.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<List<Conta>> criarConta(@RequestBody Conta conta){
		
		conta.setId(UUID.randomUUID().toString());
		conta.setValor(new BigDecimal("0.00"));
		
		contas.save(conta);
		
		return new ResponseEntity(contas.findAll(), HttpStatus.CREATED);
	}

	@PutMapping(value="depositar/{uuid}")
	public ResponseEntity<Conta> depositarDinheiro(@PathVariable String uuid, @RequestParam(value = "valor") BigDecimal valor){
		Conta c = contas.findById(uuid).get();
		
		if(valor.doubleValue() > 0 && valor.doubleValue() <= 5000) {
			c.setValor(c.getValor().add(valor));
			contas.save(c);
			
			return new ResponseEntity<Conta>(c, HttpStatus.OK);
		}else return new ResponseEntity<Conta>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping(value="sacar/{uuid}")
	public ResponseEntity<Conta> sacarDinheiro(@PathVariable String uuid, @RequestParam(value = "valor") BigDecimal valor){
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		Conta c = contas.findById(uuid).get();
		
		
		if(valor.doubleValue() > 0 && valor.doubleValue() <= c.getValor().doubleValue()) {
			c.setValor(c.getValor().subtract(valor));
			contas.save(c);
			
			return new ResponseEntity<Conta>(c, HttpStatus.OK);
		}else return new ResponseEntity<Conta>(HttpStatus.BAD_REQUEST);	
	}
	
	@DeleteMapping(value="/{uuid}")
	public ResponseEntity<String> deletarConta(@PathVariable String uuid){
		
		if(contas.findById(uuid).get().getValor().doubleValue() == 0){
			contas.deleteById(uuid);
			return new ResponseEntity<String>("Deletado com sucesso", HttpStatus.OK);
		}else return new ResponseEntity<String>("Você não pode deletar essa conta, pois ainda tem dinheiro para sacar", HttpStatus.BAD_REQUEST);
			
	}
	
}
