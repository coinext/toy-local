package com.exam.toylocal.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.InputStream;
import java.util.Map;

/**
 * @author hahms
 * @since 12/11/2018
 */
public class JSONUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public static ObjectMapper objectMapper() {
        return objectMapper;
    }

    public static <T> T fromJson(String jsonString, Class<T> claz) {
        try {
            return objectMapper.readValue(jsonString, claz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(InputStream inputStream, Class<T> claz) {
        try {
            return objectMapper.readValue(inputStream, claz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> toMap(Object pojo) {
        Map<String, Object> map = objectMapper.convertValue(pojo,
                new TypeReference<Map<String, Object>>() {
                });
        return map;
    }

}
