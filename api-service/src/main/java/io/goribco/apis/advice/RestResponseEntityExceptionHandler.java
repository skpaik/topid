package io.goribco.apis.advice;

import io.goribco.apis.model.errrorres.RestErrorMessage;
import io.goribco.apis.model.exceptions.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> employeeNotFoundHandler(EmployeeNotFoundException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:EmployeeNotFoundException", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ModelDataExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<RestErrorMessage> handleModelDataExistsException(ModelDataExistsException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:ModelDataExistsException", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<RestErrorMessage> handleDuplicateKeyException(DuplicateKeyException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:DuplicateKeyException", HttpStatus.CONFLICT);
    }


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleUserNotFoundException(UserNotFoundException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:UserNotFoundException", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ModelDataNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleModelDataNotFoundException(ModelDataNotFoundException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:ModelDataNotFoundException", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WallPostNotAllowedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<RestErrorMessage> handleWallPostNotAllowedException(WallPostNotAllowedException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + " <> DEBUG:WallPostNotAllowedException", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<RestErrorMessage> handleUserUnauthorizedException(UserUnauthorizedException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:UserUnauthorizedException", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<RestErrorMessage> handleAccessDeniedException(AccessDeniedException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:AccessDeniedException", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RestErrorMessage> genericExceptionFoundHandler(Exception ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:Exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UrPathNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleProfileUrlNameNotValidException(UrPathNotValidException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:UrPathNotValidException", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReqBodyNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleReqBodyNotValidException(ReqBodyNotValidException ex) {

        return ErrorAdviceHelper.createRestErrorMessage(ex.getMessage() + "<> DEBUG:ReqBodyNotValidException", HttpStatus.BAD_REQUEST);
    }
}
