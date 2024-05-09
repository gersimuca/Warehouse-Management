package com.gersimuca.Warehouse.Management.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@SuperBuilder
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonSerialize(using = com.gersimuca.Warehouse.Management.exception.JsonSerialize.class)
public class ErrorResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 3047523500285381864L;

    private String errorCode;
    private String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> params;

    private HttpStatus status;
    private int statusCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @JsonIgnore
    private Throwable cause;

    private String throwableCauseMessage;

    @JsonIgnore
    private String method;

    private String path;
    private String formattedErrorMessage;
    private boolean trace;

    public String getFormattedErrorMessage(){
        if(StringUtils.hasText(errorMessage)){
            if(this.errorMessage.contains("{{") || this.errorMessage.contains("}}")){
                String formattedMsg = this.errorMessage;
                try {
                    if (params != null && !params.isEmpty() && StringUtils.hasText(formattedErrorMessage)) {
                        for (Map.Entry<String, Object> entry : params.entrySet()) {
                            String value = entry.getValue() instanceof String ? (String) entry.getValue() : entry.getValue().toString();
                            formattedMsg = formattedMsg.replace("{{" + entry.getValue() + "}}", value);
                        }
                    }
                    formattedErrorMessage = formattedMsg;
                } catch (Exception e){
                    log.warn("Formatting error: {}", e.getMessage());
                }
            } else {
                formattedErrorMessage = this.errorMessage;
            }
        }
        return formattedErrorMessage;
    }

    @Override
    public String toString() {
        return " {\n" +
                "  errorCode=" + errorCode + ",\n" +
                "  errorMessage=" + errorMessage + ",\n" +
                (params != null ? "  params=" + params + ",\n" : "") +
                "  formattedErrorMessage=" + getFormattedErrorMessage() + ",\n" +
                "  status=" + status + ",\n" +
                "  statusCode=" + statusCode + ",\n" +
                "  timestamp=" + timestamp + ",\n" +
                "  method=" + method + ",\n" +
                "  path=" + path + ",\n" +
                (cause != null ? "  cause=" + cause + ",\n" : "") +
                (cause != null ? "  throwableCauseMessage=" + cause.getLocalizedMessage() + "\n" : "") +
                '}' + "\n";
    }
}

