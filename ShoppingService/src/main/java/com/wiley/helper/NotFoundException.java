package com.wiley.helper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product not found")
public class NotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
}
