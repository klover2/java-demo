package com.klover.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Objects;

/**
 * 全部脱敏
 *
 * @author klover
 * @date 2024/3/20 17:11
 */
public class CompleteDesensitizationSerialize extends JsonSerializer<String> {
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(Objects.equals(s, "") ? s : "***");
    }
}
