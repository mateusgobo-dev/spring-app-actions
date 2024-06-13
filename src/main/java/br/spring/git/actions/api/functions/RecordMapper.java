package br.spring.git.actions.api.functions;

import lombok.Getter;

@Getter
public class RecordMapper {
    private final String json;
    private final Class typeOf;

    private RecordMapper(String json, Class typeOf) {
        this.json = json;
        this.typeOf = typeOf;
    }

    public static final RecordMapper instanceOf(String json, Class typeOf) {
        return new RecordMapper(json, typeOf);
    }
}
