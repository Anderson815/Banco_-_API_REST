package com.anderson.banco.controller;

import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

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
import com.anderson.banco.service.ContaService;

@RestController
@RequestMapping("/conta")
public class ContaController {
		
	@Autowired
	ContaService contaService;
	
	@GetMapping(value="/{uuid}")
	public ResponseEntity<Conta> getConta(@PathVariable String uuid){		
		return new ResponseEntity<>(contaService.getConta(uuid), HttpStatus.OK);
	}
		
	@GetMapping
	public ResponseEntity<List<Conta>> getContas(){
		return new ResponseEntity(contaService.getContas(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Conta> criarConta(@RequestBody Conta conta){		
		return new ResponseEntity(contaService.criarConta(conta), HttpStatus.CREATED);
	}

	@PutMapping(value="depositar/{uuid}")
	public ResponseEntity<Conta> depositarDinheiro(@PathVariable String uuid, @RequestParam(value = "valor") BigDecimal valor){
		return new ResponseEntity<>(contaService.depositarDinheiro(uuid, valor), HttpStatus.OK);
	}
	
	@PutMapping(value="sacar/{uuid}")
	public ResponseEntity<Conta> sacarDinheiro(@PathVariable String uuid, @RequestParam(value = "valor") BigDecimal valor){
		return new ResponseEntity<>(contaService.sacarDinheiro(uuid, valor), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{uuid}")
	public ResponseEntity<Object> deletarConta(@PathVariable String uuid){
		contaService.deletarConta(uuid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
	}
	
}
