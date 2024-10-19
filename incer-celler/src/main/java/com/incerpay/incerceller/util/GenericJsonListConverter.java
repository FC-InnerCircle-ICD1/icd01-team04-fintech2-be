package com.incerpay.incerceller.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GenericJsonListConverter<T> implements AttributeConverter<List<T>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> type;

    public GenericJsonListConverter(Class<T> type) {
        this.type = type;
    }

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert list to JSON", e);
        }
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(dbData,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to convert JSON to list", e);
        }
    }
}

