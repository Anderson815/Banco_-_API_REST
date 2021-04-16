package com.anderson.banco.controller;

import java.util.List;
import java.math.BigDecimal;

import com.anderson.banco.exceptions.RequestConstraintException;
import com.anderson.banco.model.request.CompraModelRequest;
import com.anderson.banco.model.response.CompraModelResponse;
import com.anderson.banco.model.request.ContaModelRequest;
import com.anderson.banco.model.response.ContaModelResponse;
import com.anderson.banco.service.ContaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController //Indica que será uma aplicação RESTful e retornara JSON
@RequestMapping("/conta") // serve para mapear URLs para toda uma classe
@Api(tags="Conta")
@CrossOrigin(origins="*")
public class ContaController {
		
	@Autowired //é para gerenciar a dependência, só possível para classes Beans do Spring
	ContaService contaService;
	
	@GetMapping(value="/{uuid}", produces = "application/json")
	@ApiOperation(value="Retorna uma única conta do banco de acordo com o ID")
	@ApiResponse( code = 200, message = "Lista de todos os produtos cadastrados")
	public ResponseEntity<ContaModelResponse> getConta(@PathVariable String uuid){
		return new ResponseEntity<>(contaService.getConta(uuid), HttpStatus.OK);
	}

	@GetMapping(produces = "application/json")
	@ApiOperation(value="Retorna uma lista com todas as contas do banco")
	public ResponseEntity<List<ContaModelResponse>> getContas(){
		return new ResponseEntity<>(contaService.getContas(), HttpStatus.OK);
	}

	@PostMapping(produces = "application/json")
	@ApiOperation(value="Cria uma conta no banco")
	public ResponseEntity<ContaModelResponse> criarConta(@Valid @RequestBody ContaModelRequest contaModelRequest, BindingResult erroRequest){
		//BindingResult é para a validação
		if(erroRequest.hasErrors()) throw new RequestConstraintException(erroRequest.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(contaService.criarConta(contaModelRequest), HttpStatus.CREATED);
	}

	@PutMapping(value="/{uuid}/deposito", produces = "application/json")
	@ApiOperation(value="Deposita dinheiro para a conta pelo ID")
	public ResponseEntity<ContaModelResponse> depositarDinheiro(@PathVariable String uuid, @RequestParam(value = "valor") BigDecimal valor){
		return new ResponseEntity<>(contaService.depositarDinheiro(uuid, valor), HttpStatus.OK);
	}

	@PutMapping(value="/{uuid}/saque", produces = "application/json")
	@ApiOperation(value="Saca dinheiro da conta pelo ID")
	public ResponseEntity<ContaModelResponse> sacarDinheiro(@PathVariable String uuid, @RequestParam(value = "valor") BigDecimal valor){
		return new ResponseEntity<>(contaService.sacarDinheiro(uuid, valor), HttpStatus.OK);
	}

	@PutMapping(value="/{uuidRetira}/transferencia", produces = "application/json")
	@ApiOperation(value="Transfere dinheiro de uma conta para outra atráves dos IDs")
	public ResponseEntity<ContaModelResponse> transferir(@PathVariable(value = "uuidRetira") String uuidRetira, @RequestParam(value = "uuidRecebe") String uuidRecebe, @RequestParam(value = "valor") BigDecimal valor){
		return new ResponseEntity<>(contaService.tranferir(uuidRetira, uuidRecebe, valor), HttpStatus.OK);
	}

	@DeleteMapping(value="/{uuid}", produces = "application/json")
	@ApiOperation(value="Deleta a conta pelo ID")
	public ResponseEntity<Object> deletarConta(@PathVariable String uuid){
		contaService.deletarConta(uuid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
	}

	//sub recurso compra

	@PostMapping(value = "/{uuid}/compra", produces = "application/json")
	@ApiOperation(value="Faz uma compra para a conta do ID informado")
	public ResponseEntity<CompraModelResponse> criarCompra(@PathVariable String uuid, @Valid @RequestBody CompraModelRequest compraModelRequest, BindingResult erroRequest){
		if(erroRequest.hasErrors()) throw new RequestConstraintException(erroRequest.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(contaService.criarCompra(uuid, compraModelRequest), HttpStatus.CREATED);
	}

	@GetMapping(value = "/{uuid}/compra", produces = "application/json")
	@ApiOperation(value="Retorna uma lista com todas as compras pelo ID da conta")
	public ResponseEntity<List<CompraModelResponse>> getCompras(@PathVariable String uuid){
		return new ResponseEntity<>(contaService.getCompras(uuid), HttpStatus.OK);
	}

	@GetMapping(value = "/{uuid}/compra/{id_compra}", produces = "application/json")
	@ApiOperation(value="Retorna uma compra específica pelo ID da compra e o ID da conta")
	public ResponseEntity<CompraModelResponse> getCompra(@PathVariable(value = "uuid") String uuid, @PathVariable(value = "id_compra") int id_compra){
		return new ResponseEntity<>(contaService.getCompra(uuid, id_compra), HttpStatus.OK);
	}
}
