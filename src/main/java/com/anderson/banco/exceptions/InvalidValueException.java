package com.anderson.banco.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidValueException extends RuntimeException{
	public InvalidValueException(String msg) {
		super(msg);
	}
}
