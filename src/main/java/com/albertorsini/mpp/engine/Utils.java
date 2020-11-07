package com.albertorsini.mpp.engine;

import com.albertorsini.mpp.engine.exception.JsonConverterException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private Utils() {
    throw new IllegalStateException("Utility class");
  }

  public static String convertToJson(final Object object) {
    try {
      return OBJECT_MAPPER.writeValueAsString(object);
    } catch (final JsonProcessingException e) {
      throw new JsonConverterException(e);
    }
  }

  public static <T> T convertToObject(final String content, final Class<T> valueType) {
    try {
      return OBJECT_MAPPER.readValue(content, valueType);
    } catch (final JsonProcessingException e) {
      throw new JsonConverterException(e);
    }
  }

  public static <T> T convertToObject(final String content, TypeReference<T> valueTypeRef) {
    try {
      return OBJECT_MAPPER.readValue(content, valueTypeRef);
    } catch (final JsonProcessingException e) {
      throw new JsonConverterException(e);
    }
  }
}
