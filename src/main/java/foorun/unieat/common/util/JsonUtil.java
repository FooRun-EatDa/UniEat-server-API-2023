package foorun.unieat.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import foorun.unieat.api.model.base.dto.UniEatBaseDTO;

public final class JsonUtil {
    private static final JsonMapper mapper;
    static {
        mapper = JsonMapper.builder()
                .registerSubtypes(JavaTimeModule.class)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .build();
        ;
    }

    public static String asJson(Object obj) {
        return asJson(obj, false);
    }

    public static String asJson(Object obj, boolean pretty) {
        try {
            return pretty ? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj) : mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T ofJson(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String asPrettyJson(Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}