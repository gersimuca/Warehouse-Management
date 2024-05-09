package com.gersimuca.Warehouse.Management.util.request;

import com.gersimuca.Warehouse.Management.domain.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class RequestUtil {
    public static Response getResponse(HttpServletRequest request, Map<?,?> data, String message, String error, HttpStatus status){
        return new Response(LocalDateTime.now().toString(), status.value(), request.getRequestURI(), HttpStatus.valueOf(status.value()), message, error, data);
    }
}
