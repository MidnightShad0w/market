package com.danila.market.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Converter
public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> details) {
        String detailsJSON = null;
        try {
            detailsJSON = objectMapper.writeValueAsString(details);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
        return detailsJSON;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String detailsJSON) {
        Map<String, Object> details = null;
        try {
            details = objectMapper.readValue(detailsJSON,
                    new TypeReference<HashMap<String, Object>>() {});
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return details;
    }
}
