package com.incerpay.incerceller.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incerpay.incerceller.domain.PaymentMethod;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Converter
public class PaymentMethodListConverter implements AttributeConverter<List<PaymentMethod>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<PaymentMethod> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert list of PaymentMethod to JSON", e);
        }
    }

    @Override
    public List<PaymentMethod> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(dbData,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, PaymentMethod.class));
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to convert JSON to list of PaymentMethod", e);
        }
    }
}