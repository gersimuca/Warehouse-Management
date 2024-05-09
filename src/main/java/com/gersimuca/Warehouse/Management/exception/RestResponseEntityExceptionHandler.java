package com.gersimuca.Warehouse.Management.exception;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Throwable.class})
    protected ResponseEntity<Object> handleGenericException(Exception e, WebRequest request){
        logger.error(e.getMessage(), e);
        String message = "A server error occurred, please check the logs for details!";

        if(e.getMessage() != null) message = e.getMessage();
        return handleExceptionInternal(e, message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {ServiceException.class})
    protected ResponseEntity<Object> customBTExceptionHandle(ServiceException e){
        ErrorResponse btErrorResponse = ErrorResponse.builder()
                .params(e.getParams() != null ? e.getParams() : null)
                .cause(e.getCause())
                .throwableCauseMessage(e.getCause() != null ? e.getCause().getLocalizedMessage() : null)
                .errorMessage(e.getMessage())
                .status(e.getStatus() != null ? e.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(e.getStatus() != null ? e.getStatus().value() : HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .trace(e.isTrace())
                .build();
        logger.error(".customBTExceptionHandle: \n" + btErrorResponse.toString(), e);
        return getObjectResponseEntity(btErrorResponse);
    }

    @NotNull
    private ResponseEntity<Object> getObjectResponseEntity(ErrorResponse BTErrorResponse){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(BTErrorResponse, headers, BTErrorResponse.getStatus());
    }
}
