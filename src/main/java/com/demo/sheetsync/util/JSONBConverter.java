package com.demo.sheetsync.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.prefs.Preferences;

@Converter(autoApply = true)
public class JSONBConverter implements AttributeConverter<List<LinkedHashMap<String, Object>>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<LinkedHashMap<String, Object>> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing Preferences", e);
        }
    }

    @Override
    public List<LinkedHashMap<String, Object>> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData,
                    objectMapper
                            .getTypeFactory().constructCollectionType(List.class, LinkedHashMap.class));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error deserializing Preferences", e);
        }
    }
}






