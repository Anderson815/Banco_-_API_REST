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

	@GetMapping(value = "/{uuid}", produces = "application/json")
	@ApiOperation(value = "Retorna uma única conta do banco de acordo com o ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o produto informado"),
			@ApiResponse(code = 404, message = "Não existe conta com o uuid informado")
	})
	public ResponseEntity<ContaModelResponse> getConta(@PathVariable String uuid){
		return new ResponseEntity<>(contaService.getConta(uuid), HttpStatus.OK);
	}

	@GetMapping(produces = "application/json")
	@ApiOperation(value="Retorna uma lista com todas as contas do banco")
	@ApiResponse( code = 200, message = "Lista de todos os produtos cadastrados")
	public ResponseEntity<List<ContaModelResponse>> getContas(){
		return new ResponseEntity<>(contaService.getContas(), HttpStatus.OK);
	}

	@PostMapping(produces = "application/json")
	@ApiOperation(value="Cria uma conta no banco")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Conta cadastrada"),
			@ApiResponse(code = 400, message = "Não foi passado os atributos do objeto de forma adequada")
	})
	public ResponseEntity<ContaModelResponse> criarConta(@Valid @RequestBody ContaModelRequest contaModelRequest, BindingResult erroRequest){
		//BindingResult é para a validação
		if(erroRequest.hasErrors()) throw new RequestConstraintException(erroRequest.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(contaService.criarConta(contaModelRequest), HttpStatus.CREATED);
	}

	@PutMapping(value="/{uuid}/deposito", produces = "application/json")
	@ApiOperation(value="Deposita dinheiro para a conta pelo ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "O dinheiro foi depositado"),
			@ApiResponse(code = 400, message = "Valor abaixo de um centavo"),
			@ApiResponse(code = 400, message = "valor acima do limite(5000,00)"),
			@ApiResponse(code = 404, message = "Não existe conta com o uuid informado")
	})
	public ResponseEntity<ContaModelResponse> depositarDinheiro(@PathVariable String uuid, @RequestParam(value = "valor") BigDecimal valor){
		return new ResponseEntity<>(contaService.depositarDinheiro(uuid, valor), HttpStatus.OK);
	}

	@PutMapping(value="/{uuid}/saque", produces = "application/json")
	@ApiOperation(value="Saca dinheiro da conta pelo ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "O dinheiro foi sacado"),
			@ApiResponse(code = 400, message = "Valor abaixo de um centavo"),
			@ApiResponse(code = 400, message = "valor acima da quantidade depositada na conta"),
			@ApiResponse(code = 404, message = "Não existe conta com o uuid informado")
	})
	public ResponseEntity<ContaModelResponse> sacarDinheiro(@PathVariable String uuid, @RequestParam(value = "valor") BigDecimal valor){
		return new ResponseEntity<>(contaService.sacarDinheiro(uuid, valor), HttpStatus.OK);
	}

	@PutMapping(value="/{uuidRetira}/transferencia", produces = "application/json")
	@ApiOperation(value="Transfere dinheiro de uma conta para outra atráves dos IDs")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "O dinheiro foi transferido"),
			@ApiResponse(code = 400, message = "Valor abaixo de um centavo"),
			@ApiResponse(code = 400, message = "Valor de transferência acima da quantidade depositada na conta"),
			@ApiResponse(code = 404, message = "Não existe conta com o uuid informado para retirar o dinheiro"),
			@ApiResponse(code = 404, message = "Não existe conta com o uuid informado para receber o dinheiro")
	})
	public ResponseEntity<ContaModelResponse> transferir(@PathVariable(value = "uuidRetira") String uuidRetira, @RequestParam(value = "uuidRecebe") String uuidRecebe, @RequestParam(value = "valor") BigDecimal valor){
		return new ResponseEntity<>(contaService.tranferir(uuidRetira, uuidRecebe, valor), HttpStatus.OK);
	}

	@DeleteMapping(value="/{uuid}", produces = "application/json")
	@ApiOperation(value="Deleta a conta pelo ID")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "A conta foi deletada"),
			@ApiResponse(code = 400, message = "Existe dinheiro para ser sacado na conta informada"),
			@ApiResponse(code = 404, message = "Não existe conta com o uuid informado")
	})
	public ResponseEntity<Object> deletarConta(@PathVariable String uuid){
		contaService.deletarConta(uuid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
	}

	//sub recurso compra

	@PostMapping(value = "/{uuid}/compra", produces = "application/json")
	@ApiOperation(value="Faz uma compra para a conta do ID informado")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "A compra foi criada"),
			@ApiResponse(code = 400, message = "Valor negativo"),
			@ApiResponse(code = 400, message = "Valor de compra acima da quantidade depositada na conta"),
			@ApiResponse(code = 400, message = "Não foi passado os atributos do objeto de forma adequada"),
			@ApiResponse(code = 404, message = "Não existe conta com o uuid informado")
	})
	public ResponseEntity<CompraModelResponse> criarCompra(@PathVariable String uuid, @Valid @RequestBody CompraModelRequest compraModelRequest, BindingResult erroRequest){
		if(erroRequest.hasErrors()) throw new RequestConstraintException(erroRequest.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(contaService.criarCompra(uuid, compraModelRequest), HttpStatus.CREATED);
	}

	@GetMapping(value = "/{uuid}/compra", produces = "application/json")
	@ApiOperation(value="Retorna uma lista com todas as compras pelo ID da conta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lista das compras da conta informada"),
			@ApiResponse(code = 404, message = "Não existe conta com o uuid informado")
	})
	public ResponseEntity<List<CompraModelResponse>> getCompras(@PathVariable String uuid){
		return new ResponseEntity<>(contaService.getCompras(uuid), HttpStatus.OK);
	}

	@GetMapping(value = "/{uuid}/compra/{id_compra}", produces = "application/json")
	@ApiOperation(value="Retorna uma compra específica pelo ID da compra e o ID da conta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a compra informada"),
			@ApiResponse(code = 404, message = "Não existe conta com o uuid informado"),
			@ApiResponse(code = 404, message = "Não existe compra com o id_compra informado")
	})
	public ResponseEntity<CompraModelResponse> getCompra(@PathVariable(value = "uuid") String uuid, @PathVariable(value = "id_compra") int id_compra){
		return new ResponseEntity<>(contaService.getCompra(uuid, id_compra), HttpStatus.OK);
	}
}
