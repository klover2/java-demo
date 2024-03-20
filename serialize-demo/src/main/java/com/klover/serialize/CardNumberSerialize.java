package com.klover.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Objects;

/**
 * 卡号脱敏
 *
 * @author klover
 * @date 2024/3/20 17:09
 */
public class CardNumberSerialize extends JsonSerializer<String> {
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(Objects.equals(s, "") ? s : s.replaceAll("(\\d{6})\\d{6}(\\d{4})", "$1******$2"));
    }
}
