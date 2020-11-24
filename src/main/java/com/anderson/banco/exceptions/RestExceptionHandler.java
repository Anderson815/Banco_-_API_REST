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
	
	@ExceptionHandler(NotFound.class)
	public ResponseEntity<ResponseException> responseNotFound(NotFound e){
		ResponseException erro = new ResponseException(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), e.getMessage());	
		return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidValue.class)
	public ResponseEntity<ResponseException> responseInvalidValue(InvalidValue e){
		ResponseException erro = new ResponseException(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage());
		return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DeleteAccountException.class)
	public ResponseEntity<ResponseException> responseDeleteAccountException(DeleteAccountException e){
		ResponseException erro = new ResponseException(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage());
		return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ResponseException> responseConstraintViolationException(ConstraintViolationException e){
		ResponseException erro = new ResponseException(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage());
		return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(PropertyValueException.class)
	public ResponseEntity<ResponseException> responsePropertyValueException(PropertyValueException e){
		ResponseException erro = new ResponseException(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage());
		return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
