package com.erguidos.ichor.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.erguidos.ichor.dto.response.ErrorCodeResponse;
import com.erguidos.ichor.exceptions.BadBloodTypeException;
import com.erguidos.ichor.exceptions.IncorrectPasswordException;
import com.erguidos.ichor.exceptions.UserAlreadyExistsException;
import com.erguidos.ichor.exceptions.UserNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorCodeResponse> handleUserNotFound(UserNotFoundException e){
		ErrorCodeResponse responseDTO = new ErrorCodeResponse(404,e.getMessage(),LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorCodeResponse> handleUserAlreadyExists(UserAlreadyExistsException e){
		ErrorCodeResponse responseDTO = new ErrorCodeResponse(409,e.getMessage(),LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);
	}
	
	@ExceptionHandler(IncorrectPasswordException.class)
	public ResponseEntity<ErrorCodeResponse> handleIncorrectPassword(IncorrectPasswordException e){
		ErrorCodeResponse responseDTO = new ErrorCodeResponse(401,e.getMessage(),LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
	}
	
	@ExceptionHandler(BadBloodTypeException.class)
	public ResponseEntity<ErrorCodeResponse> handleBadBloodType(BadBloodTypeException e){
		ErrorCodeResponse responseDTO = new ErrorCodeResponse(422,e.getMessage(),LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(responseDTO);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorCodeResponse> handleIllegalArgument(IllegalArgumentException e){
		ErrorCodeResponse responseDTO = new ErrorCodeResponse(400, e.getMessage(), LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorCodeResponse> handleNullPointer(NullPointerException e){
		ErrorCodeResponse responseDTO = new ErrorCodeResponse(400, e.getMessage(), LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
	}

}
