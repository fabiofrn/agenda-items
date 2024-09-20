package com.fabio.agenda.exceptions.handler;

import com.fabio.agenda.exceptions.ApplicationException;
import com.fabio.agenda.exceptions.BusinessException;
import com.fabio.agenda.exceptions.NotFoundException;
import com.fabio.agenda.exceptions.domain.ApiErro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErro> handleBusinessException(BusinessException exception, WebRequest request) {
        ApiErro apiErro = new ApiErro(LocalDateTime.now(), exception.getMessage(), null);
        logger.error(apiErro.mensagem());
        return new ResponseEntity<>(apiErro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ApiErro> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        ApiErro apiErro = new ApiErro(LocalDateTime.now(), "Registro duplicado.", exception.getMessage());
        logger.error(apiErro.mensagem());
        return new ResponseEntity<>(apiErro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiErro> handleNotFoundExceptionException(NotFoundException exception) {
        ApiErro apiErro = new ApiErro(LocalDateTime.now(), exception.getMessage(), null);
        logger.error(apiErro.mensagem());
        return new ResponseEntity<>(apiErro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ApplicationException.class})
    public ResponseEntity<ApiErro> handleApplicationException(ApplicationException exception) {
        ApiErro apiErro = new ApiErro(LocalDateTime.now(), "Houve um erro inesperado na aplicação.", exception.getMessage());
        logger.error(apiErro.mensagem());
        return new ResponseEntity<>(apiErro, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            erros.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
    }

}
