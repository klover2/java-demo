package com.klover.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.klover.jackson.serializer.JavaTimeModule;
import com.klover.jackson.serializer.NumberModule;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.Serial;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

/**
 * JsonUtil
 *
 * @author klover
 * @date 2024/3/18 10:19
 */
@UtilityClass
public class JsonUtil {
    /**
     * 将对象序列化成json字符串
     *
     * @param value javaBean
     * @return jsonString json字符串
     */
    public static <T> String toJSONString(T value) {
        try {
            return getInstance().writeValueAsString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json反序列化成对象
     *
     * @param content content
     * @return Map
     */
    public static Map<String, Object> parse(String content) {
        JavaType javaType = getInstance().getTypeFactory().constructParametricType(Map.class, String.class, Object.class);
        return parse(content, javaType);
    }

    /**
     * 将类反序列化成map
     *
     * @param content content
     * @param <T>     泛型
     * @return Map
     */
    public static <T> Map<String, Object> convert(T content) {
        if (content == null) {
            return null;
        }
        JavaType javaType = getInstance().getTypeFactory().constructParametricType(Map.class, String.class, Object.class);
        try {
            return getInstance().convertValue(content, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param content   content
     * @param valueType class
     * @return Bean
     */
    public static <T> T parse(String content, Class<T> valueType) {
        try {
            return getInstance().readValue(content, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param content       content
     * @param typeReference 泛型类型
     * @return Bean
     */
    public static <T> T parse(String content, TypeReference<T> typeReference) {
        try {
            return getInstance().readValue(content, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param content  content
     * @param javaType 泛型类型
     * @return Bean
     */
    public static <T> T parse(String content, JavaType javaType) {
        try {
            return getInstance().readValue(content, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取java type
     *
     * @param collectionClass 外层类
     * @param elementClasses  子类
     * @return JavaType
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return getInstance().getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static ObjectMapper getInstance() {
        return JacksonHolder.INSTANCE;
    }

    private static class JacksonHolder {
        private static final ObjectMapper INSTANCE = new JacksonObjectMapper();
    }

    public static class JacksonObjectMapper extends ObjectMapper {
        @Serial
        private static final long serialVersionUID = 1L;

        public JacksonObjectMapper() {
            super();
            // 去掉默认的时间戳格式
            super.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            // 设置为0时区
            super.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            super.setDateFormat(dateFormat);
            // 序列化处理
            super.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
            super.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
            //失败处理
            super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            // 反序列化的时候如果多了其他属性,不抛出异常
            super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 单引号处理
            super.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            // 日期格式化
            super.registerModule(new JavaTimeModule());
            // 数字格式化
            super.registerModule(new NumberModule());
        }
    }
}
