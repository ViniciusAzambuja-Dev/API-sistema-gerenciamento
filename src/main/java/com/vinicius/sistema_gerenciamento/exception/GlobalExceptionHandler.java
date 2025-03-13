package com.vinicius.sistema_gerenciamento.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.vinicius.sistema_gerenciamento.dto.response.Error.ErrorResponseDTO;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleRecordNotFoundException(RecordNotFoundException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(404, exception.getMessage(), LocalDateTime.now(),null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(401, exception.getMessage(), LocalDateTime.now(), null);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception)  {
        List<String> datails = exception.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + " " + error.getDefaultMessage())
        .collect(Collectors.toList());

        ErrorResponseDTO error = new ErrorResponseDTO(400, "Campo(s) inválidos", LocalDateTime.now(), datails);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException exception) {
        List<String> datails = exception.getConstraintViolations().stream()
        .map(error -> error.getPropertyPath() + " " + error.getMessage())
        .collect(Collectors.toList());

        ErrorResponseDTO error = new ErrorResponseDTO(400, "Campo(s) inválidos", LocalDateTime.now(), datails);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        String type = exception.getRequiredType().getName();

        ErrorResponseDTO error = new ErrorResponseDTO(400, exception.getName() + " deve ser do tipo " + type, LocalDateTime.now(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedException(UnauthorizedException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(401, exception.getMessage(), LocalDateTime.now(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
