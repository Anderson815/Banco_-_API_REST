package com.anderson.banco.controller;

import java.util.List;
import java.math.BigDecimal;

import com.anderson.banco.exceptions.RequestConstraintException;
import com.anderson.banco.model.request.CompraModelRequest;
import com.anderson.banco.model.response.CompraModelResponse;
import com.anderson.banco.model.request.ContaModelRequest;
import com.anderson.banco.model.response.ContaModelResponse;
import com.anderson.banco.service.ContaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController //Indica que será uma aplicação RESTful e retornara JSON
@RequestMapping("/conta") // serve para mapear URLs para toda uma classe
public class ContaController {
		
	@Autowired //é para gerenciar a dependência, só possível para classes Beans do Spring
	ContaService contaService;
	
	@GetMapping(value="/{uuid}")
	public ResponseEntity<ContaModelResponse> getConta(@PathVariable String uuid){
		return new ResponseEntity<>(contaService.getConta(uuid), HttpStatus.OK);
	}
		
	@GetMapping
	public ResponseEntity<List<ContaModelResponse>> getContas(){
		return new ResponseEntity<>(contaService.getContas(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ContaModelResponse> criarConta(@Valid @RequestBody ContaModelRequest contaModelRequest, BindingResult erroRequest){
		//BindingResult é para a validação
		if(erroRequest.hasErrors()) throw new RequestConstraintException(erroRequest.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(contaService.criarConta(contaModelRequest), HttpStatus.CREATED);
	}

	@PutMapping(value="/{uuid}/deposito")
	public ResponseEntity<ContaModelResponse> depositarDinheiro(@PathVariable String uuid, @RequestParam(value = "valor") BigDecimal valor){
		return new ResponseEntity<>(contaService.depositarDinheiro(uuid, valor), HttpStatus.OK);
	}
	
	@PutMapping(value="/{uuid}/saque")
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
	public ResponseEntity<CompraModelResponse> criarCompra(@PathVariable String uuid, @Valid @RequestBody CompraModelRequest compraModelRequest, BindingResult erroRequest){
		if(erroRequest.hasErrors()) throw new RequestConstraintException(erroRequest.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(contaService.criarCompra(uuid, compraModelRequest), HttpStatus.CREATED);
	}

	@GetMapping(value = "/{uuid}/compra")
	public ResponseEntity<List<CompraModelResponse>> getCompras(@PathVariable String uuid){
		return new ResponseEntity<>(contaService.getCompras(uuid), HttpStatus.OK);
	}

	@GetMapping(value = "/{uuid}/compra/{id_compra}")
	public ResponseEntity<CompraModelResponse> getCompra(@PathVariable(value = "uuid") String uuid, @PathVariable(value = "id_compra") int id_compra){
		return new ResponseEntity<>(contaService.getCompra(uuid, id_compra), HttpStatus.OK);
	}
}
