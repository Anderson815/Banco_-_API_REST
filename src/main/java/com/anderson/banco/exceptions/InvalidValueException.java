package com.anderson.banco.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidValueException extends RuntimeException{
	public InvalidValueException(String msg) {
		super("Valor inválido para " + msg);
	}
}
