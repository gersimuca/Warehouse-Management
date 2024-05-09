package com.gersimuca.Warehouse.Management.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceException extends RuntimeException {

    private Map<String, Object> params;
    private String errorDetailMessage;
    private HttpStatus status;
    private boolean trace;

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, HttpStatus status) {
        this(message, null, status, new HashMap<>(), null, false);
    }

    public ServiceException(String message, HttpStatus status, Map<String, Object> params) {
        this(message, null, status, params, null, false);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, HttpStatus status,
                               Map<String, Object> params, String errorDetailMessage, boolean trace) {
        super(message, cause);
        this.status = status;
        this.params = params;
        this.errorDetailMessage = errorDetailMessage;
        this.trace = trace;
    }

}

