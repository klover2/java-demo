package com.klover.jackson.serializer;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * NumberModule
 *
 * @author klover
 * @date 2024/3/18 10:35
 */
public class NumberModule extends SimpleModule {
    private static final AtomicInteger MODULE_ID_SEQ = new AtomicInteger(1);
    @Serial
    private static final long serialVersionUID = 2505922598435296802L;

    public NumberModule() {
        super(NumberModule.class.getSimpleName() + MODULE_ID_SEQ.getAndIncrement(), PackageVersion.VERSION);
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
        this.addSerializer(BigDecimal.class, BigDecimalSerializer.instance);
        this.addSerializer(BigInteger.class, ToStringSerializer.instance);
    }
}