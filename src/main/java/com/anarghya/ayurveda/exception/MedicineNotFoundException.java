package com.anarghya.ayurveda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
@RestControllerAdvice
public class MedicineNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MedicineNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicineNotFoundException(String message) {
		super(message);
	}
	@ExceptionHandler(MedicineNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(MedicineNotFoundException ex) {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

}
