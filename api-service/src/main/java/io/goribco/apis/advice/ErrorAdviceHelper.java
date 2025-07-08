package io.goribco.apis.advice;

import io.goribco.apis.model.errrorres.RestErrorBody;
import io.goribco.apis.model.errrorres.RestErrorMessage;
import io.goribco.core.response.BaseMsg;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public class ErrorAdviceHelper {
    @NotNull
    public static ResponseEntity<RestErrorMessage> createRestErrorMessage(String exceptionMsg, HttpStatus httpStatus) {
        RestErrorBody restErrorBody = getRestErrorBody(exceptionMsg, httpStatus);

        return getRestErrorMessageResponseEntity(httpStatus, restErrorBody);
    }

    @NotNull
    private static RestErrorBody getRestErrorBody(String exceptionMsg, HttpStatus httpStatus) {
        RestErrorBody restErrorBody = new RestErrorBody();

        restErrorBody.set_msg(new BaseMsg(exceptionMsg, httpStatus.value()));

        return restErrorBody;
    }

    public static ResponseEntity<RestErrorMessage> createRestErrorMessage(Map<String, String> errorMap, HttpStatus httpStatus) {
        RestErrorBody restErrorBody = getRestErrorBody("Inputs are not valid", httpStatus);

        restErrorBody.set_errors(errorMap);

        return getRestErrorMessageResponseEntity(httpStatus, restErrorBody);
    }

    @NotNull
    private static ResponseEntity<RestErrorMessage> getRestErrorMessageResponseEntity(HttpStatus httpStatus, RestErrorBody restErrorBody) {
        RestErrorMessage restErrorMessage = getRestErrorMessage(httpStatus, restErrorBody);

        return ResponseEntity.status(httpStatus).body(restErrorMessage);
    }

    @NotNull
    private static RestErrorMessage getRestErrorMessage(HttpStatus httpStatus, RestErrorBody restErrorBody) {
        RestErrorMessage restErrorMessage = new RestErrorMessage();

        restErrorMessage.setStatusCode(httpStatus);
        restErrorMessage.setStatusCodeValue(httpStatus.value());
        restErrorMessage.setBody(restErrorBody);

        return restErrorMessage;
    }

    @NotNull
    public static RestErrorMessage get2estErrorMessage(String errorMsg, String exceptionMsg, HttpStatus httpStatus) {
        RestErrorBody restErrorBody = getRestErrorBody(errorMsg, httpStatus);
        restErrorBody.set_error(exceptionMsg);

        return getRestErrorMessage(httpStatus, restErrorBody);
    }
}
