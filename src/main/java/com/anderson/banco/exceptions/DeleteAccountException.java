package com.anderson.banco.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DeleteAccountException extends RuntimeException{
	public DeleteAccountException() {
		super("A conta possui dinheiro para ser sacado");
	}
}
