package com.klover.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.klover.jackson.serializer.JavaTimeModule;
import com.klover.jackson.serializer.NumberModule;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.Serial;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * XmlUtil
 *
 * @author klover
 * @date 2024/3/18 10:29
 */
@UtilityClass
public class XmlUtil {
    /**
     * 将json反序列化成对象
     *
     * @param xml       xml
     * @param valueType 泛型类型
     * @return T
     */
    public static <T> T parse(String xml, JavaType valueType) {
        try {
            return getInstance().readValue(xml, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param xml       xml
     * @param valueType class
     * @return T
     */
    public static <T> T parse(String xml, Class<T> valueType) {
        try {
            return getInstance().readValue(xml, valueType);
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
     * 将对象序列化成XML字符串
     *
     * @param value javaBean
     * @return xmlString xml字符串
     */
    public static <T> String toXMLString(T value) {
        try {
            return getInstance().writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper getInstance() {
        return XmlUtil.JacksonHolder.INSTANCE;
    }

    private static class JacksonHolder {
        private static final ObjectMapper INSTANCE = new XmlUtil.JacksonObjectMapper();
    }

    public static class JacksonObjectMapper extends XmlMapper {
        @Serial
        private static final long serialVersionUID = -3204663374727823367L;

        public JacksonObjectMapper() {
            super();
            // 去掉默认的时间戳格式
            super.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            // 设置为0时区
            super.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            super.setDateFormat(dateFormat);
            // 反序列化时，若实体类没有对应的属性，是否抛出JsonMappingException异常，false忽略掉
            this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 序列化是否绕根元素，true，则以类名为根元素
            this.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            // 忽略空属性
            this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            // XML标签名:使用骆驼命名的属性名，
            this.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
            // 日期格式化
            super.registerModule(new JavaTimeModule());
            // 数字格式化
            super.registerModule(new NumberModule());
        }
    }
}
