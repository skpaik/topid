package io.goribco.apis.advice;


import io.goribco.apis.model.errrorres.RestErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    /*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return errorMap;
    }
    */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return ErrorAdviceHelper.createRestErrorMessage(errorMap, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleUsernameNotFoundException(UsernameNotFoundException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:UsernameNotFoundException", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RestErrorMessage> handleIOException(IOException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:IOException", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleBadCredentialsException(BadCredentialsException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:BadCredentialsException", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleDisabledException(DisabledException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:DisabledException", HttpStatus.BAD_REQUEST);
    }
}