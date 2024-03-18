package com.klover.jackson.serializer;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JavaTimeModule
 *
 * @author klover
 * @date 2024/3/18 10:33
 */
public class JavaTimeModule extends SimpleModule {
    private static final AtomicInteger MODULE_ID_SEQ = new AtomicInteger(1);
    @Serial
    private static final long serialVersionUID = -7746375838310782726L;

    public JavaTimeModule() {
        super(JavaTimeModule.class.getSimpleName() + MODULE_ID_SEQ.getAndIncrement(), PackageVersion.VERSION);

        // 将java.util.date类型映射成毫秒戳
        this.addSerializer(Date.class, DateToTimestampSerializer.instance);

        // 将java.time.LocalDateTime类型映射成YYYY-MM-DD HH:mm:ss
        DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(datetimeFormat));
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(datetimeFormat));

        // 将java.time.LocalDate类型映射成YYYY-MM-DD
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormat));
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormat));

        // 将java.time.LocalTime类型映射成HH:mm:ss
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormat));
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormat));
    }
}
