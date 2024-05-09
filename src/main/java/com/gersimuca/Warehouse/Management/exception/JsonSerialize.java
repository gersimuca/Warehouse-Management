package com.gersimuca.Warehouse.Management.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.json.JSONObject;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JsonSerialize extends JsonSerializer<ErrorResponse> {

    private static final String DATE_TIME_FORMAT_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_WITH_TIMEZONE);

    @Override
    public void serialize(ErrorResponse pgiErrorResponse, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("errorCode", pgiErrorResponse.getErrorCode());
        jsonGenerator.writeStringField("errorMessage", pgiErrorResponse.getErrorMessage());

        if(pgiErrorResponse.getParams() != null && !pgiErrorResponse.getParams().isEmpty()){
            JSONObject jsonObject = new JSONObject(pgiErrorResponse.getParams());
            jsonGenerator.writeFieldName("params");
            jsonGenerator.writeRawValue(jsonObject.toString());
        }

        jsonGenerator.writeStringField("formattedErrorMessage", pgiErrorResponse.getFormattedErrorMessage());
        jsonGenerator.writeStringField("status", pgiErrorResponse.getStatus().toString());
        jsonGenerator.writeNumberField("statusCode", pgiErrorResponse.getStatusCode());

        if(pgiErrorResponse.getTimestamp() != null){
            ZonedDateTime zonedDateTime = pgiErrorResponse.getTimestamp().atZone(ZoneId.systemDefault());
            String timestamp = zonedDateTime.format(dateFormatter);
            jsonGenerator.writeStringField("timestamp", timestamp);
        }

        jsonGenerator.writeStringField("path", pgiErrorResponse.getThrowableCauseMessage());

        if (pgiErrorResponse.isTrace()) {
            jsonGenerator.writeStringField("throwableCauseMessage", pgiErrorResponse.getThrowableCauseMessage());
            if (pgiErrorResponse.getCause() != null) {
                jsonGenerator.writeObjectField("cause", pgiErrorResponse.getCause().toString());
            }
        }

        jsonGenerator.writeEndObject();
    }
}

