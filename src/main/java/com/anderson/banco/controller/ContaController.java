package com.anderson.banco.controller;

import java.util.List;
import java.math.BigDecimal;

import com.anderson.banco.model.CompraModelRequest;
import com.anderson.banco.model.CompraModelResponse;
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

import com.anderson.banco.model.ContaModelResponse;
import com.anderson.banco.service.ContaService;

import javax.validation.Valid;

@RestController
@RequestMapping("/conta") // serve para mapear URLs para toda uma classe
public class ContaController {
		
	@Autowired
	ContaService contaService;
	
	@GetMapping(value="/{uuid}")
	public ResponseEntity<ContaModelResponse> getConta(@PathVariable String uuid){
		return new ResponseEntity<>(contaService.getConta(uuid), HttpStatus.OK);
	}
		
	@GetMapping
	public ResponseEntity<List<ContaModelResponse>> getContas(){
		return new ResponseEntity(contaService.getContas(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ContaModelResponse> criarConta(@RequestBody ContaModelResponse contaModelResponse){
		return new ResponseEntity(contaService.criarConta(contaModelResponse), HttpStatus.CREATED);
	}

	@PutMapping(value="depositar/{uuid}")
	public ResponseEntity<ContaModelResponse> depositarDinheiro(@PathVariable String uuid, @RequestParam(value = "valor") BigDecimal valor){
		return new ResponseEntity<>(contaService.depositarDinheiro(uuid, valor), HttpStatus.OK);
	}
	
	@PutMapping(value="sacar/{uuid}")
	public ResponseEntity<ContaModelResponse> sacarDinheiro(@PathVariable String uuid, @RequestParam(value = "valor") BigDecimal valor){
		return new ResponseEntity<>(contaService.sacarDinheiro(uuid, valor), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{uuid}")
	public ResponseEntity<Object> deletarConta(@PathVariable String uuid){
		contaService.deletarConta(uuid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
	}

	//sub recurso

	@PostMapping(value = "/{uuid}/compra")
	public ResponseEntity<CompraModelResponse> criarCompra(@PathVariable String uuid, @Valid @RequestBody CompraModelRequest compraModelRequest){
		return new ResponseEntity<>(contaService.criarCompra(uuid, compraModelRequest), HttpStatus.CREATED);
	}
}
