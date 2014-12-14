package io.framework.todo.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper extends ObjectMapper {
    public JsonMapper() {
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
