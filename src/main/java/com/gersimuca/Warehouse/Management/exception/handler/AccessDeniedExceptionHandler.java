package com.gersimuca.Warehouse.Management.exception.handler;

import com.gersimuca.Warehouse.Management.domain.Response;
import com.gersimuca.Warehouse.Management.util.request.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;

import static org.apache.commons.lang.StringUtils.EMPTY;


@RestControllerAdvice
public class AccessDeniedExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response> handleAccessDeniedException(AccessDeniedException ex, WebRequest webRequest, HttpServletRequest request) {
        String error = "Access Denied: You are not authorized to access this.";
        Response response = RequestUtil.getResponse(request, Collections.emptyMap(), EMPTY, error, HttpStatus.FORBIDDEN);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
