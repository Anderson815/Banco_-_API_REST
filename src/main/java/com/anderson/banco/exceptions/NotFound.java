package com.anderson.banco.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFound extends RuntimeException{
	public NotFound(String uuid) {
		super("Não encontramos o UUID: " + uuid);
	}
}
