package br.spring.git.actions.api.functions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Function;

public interface ParserFn {
    Function<Object, String> toJson = dto -> {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    };

    Function<RecordMapper, Object> toObject = dto -> {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(dto.getJson(), dto.getTypeOf());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    };
}
