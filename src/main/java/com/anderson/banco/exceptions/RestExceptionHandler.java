package com.anderson.banco.exceptions;

import java.util.Date;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ResponseExceptionDetails> responseNotFoundException(NotFoundException e){
		ResponseExceptionDetails erro = new ResponseExceptionDetails(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), e.getMessage());	
		return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidValueException.class)
	public ResponseEntity<ResponseExceptionDetails> responseInvalidValueException(InvalidValueException e){
		ResponseExceptionDetails erro = new ResponseExceptionDetails(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), e.getMessage());
		return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DeleteAccountException.class)
	public ResponseEntity<ResponseExceptionDetails> responseDeleteAccountException(DeleteAccountException e){
		ResponseExceptionDetails erro = new ResponseExceptionDetails(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage());
		return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ResponseExceptionDetails> responseConstraintViolationException(ConstraintViolationException e){
		ResponseExceptionDetails erro = new ResponseExceptionDetails(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "O Rg já foi cadastrado em alguma conta no banco");
		return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PropertyValueException.class)
	public ResponseEntity<ResponseExceptionDetails> responsePropertyValueException(PropertyValueException e){
		ResponseExceptionDetails erro = new ResponseExceptionDetails(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Algum campo obrigatório não foi preenchido");
		return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RequestConstraintException.class)
	public ResponseEntity<ResponseExceptionDetails> responseRequestConstraintException(RequestConstraintException e){
		ResponseExceptionDetails erro = new ResponseExceptionDetails(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), e.getMessage());
		return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
	}
}
