package com.example.security.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@ControllerAdvice
public class GlobalExceptionHandler {
	   @ExceptionHandler(value = { JwtException.class, ExpiredJwtException.class })
	    public ResponseEntity<Object> handleInvalidTokenException(Exception ex) {
	        return new ResponseEntity<>("Token inválido o expirado", HttpStatus.UNAUTHORIZED);
	    }
	}