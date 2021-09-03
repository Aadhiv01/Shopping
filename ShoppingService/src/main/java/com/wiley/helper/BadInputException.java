package com.wiley.helper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Entry")
public class BadInputException extends Exception{

	private static final long serialVersionUID = 1L;
	
}
