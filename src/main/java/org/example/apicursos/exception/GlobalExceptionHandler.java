package org.example.apicursos.exception;

import org.example.apicursos.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private ResponseEntity<ErrorDTO> responseError(HttpStatus httpStatus, String message) {

    ErrorDTO errorDTO = new ErrorDTO(
            httpStatus.value(),
            message
    );

    return ResponseEntity.status(httpStatus).body(errorDTO);
  }

  @ExceptionHandler(CourseNotFoundException.class)
  public ResponseEntity<ErrorDTO> handleCourseNotFound(CourseNotFoundException courseNotFoundException) {

    return responseError(
            HttpStatus.NOT_FOUND,
            courseNotFoundException.getMessage()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDTO> handleValidationError(MethodArgumentNotValidException exception) {
    String message = exception
            .getBindingResult()
            .getFieldError() != null
            ? exception.getBindingResult().getFieldError().getDefaultMessage() : "Erro de validação";

    return responseError(
            HttpStatus.BAD_REQUEST,
            message
    );
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorDTO> handleIDValidationError() {

    return responseError(
            HttpStatus.BAD_REQUEST,
            "UUID Inválido."
    );
  }
}
