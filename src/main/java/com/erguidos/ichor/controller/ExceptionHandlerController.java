package com.erguidos.ichor.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.erguidos.ichor.dto.response.ErrorCodeResponseDTO;
import com.erguidos.ichor.exceptions.BadBloodTypeException;
import com.erguidos.ichor.exceptions.IncorrectPasswordException;
import com.erguidos.ichor.exceptions.UserAlreadyExistsException;
import com.erguidos.ichor.exceptions.UserNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorCodeResponseDTO> handleUserNotFound(UserNotFoundException e){
		ErrorCodeResponseDTO responseDTO = new ErrorCodeResponseDTO(404,e.getMessage(),LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorCodeResponseDTO> handleUserAlreadyExists(UserAlreadyExistsException e){
		ErrorCodeResponseDTO responseDTO = new ErrorCodeResponseDTO(409,e.getMessage(),LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);
	}
	
	@ExceptionHandler(IncorrectPasswordException.class)
	public ResponseEntity<ErrorCodeResponseDTO> handleIncorrectPassword(IncorrectPasswordException e){
		ErrorCodeResponseDTO responseDTO = new ErrorCodeResponseDTO(401,e.getMessage(),LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
	}
	
	@ExceptionHandler(BadBloodTypeException.class)
	public ResponseEntity<ErrorCodeResponseDTO> handleBadBloodType(BadBloodTypeException e){
		ErrorCodeResponseDTO responseDTO = new ErrorCodeResponseDTO(422,e.getMessage(),LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(responseDTO);
	}

}
