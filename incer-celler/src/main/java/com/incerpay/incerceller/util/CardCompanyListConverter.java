package com.incerpay.incerceller.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incerpay.incerceller.domain.CardCompany;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Converter
public class CardCompanyListConverter implements AttributeConverter<List<CardCompany>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<CardCompany> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert list of CardCompany to JSON", e);
        }
    }

    @Override
    public List<CardCompany> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(dbData,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, CardCompany.class));
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to convert JSON to list of CardCompany", e);
        }
    }
}

