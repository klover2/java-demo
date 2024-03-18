package com.klover.jackson.serializer;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializerBase;

import java.math.BigDecimal;

/**
 * BigDecimalSerializer
 *
 * @author klover
 * @date 2024/3/18 10:32
 */
public class BigDecimalSerializer extends ToStringSerializerBase {
    /**
     * Singleton instance to use.
     */
    public final static BigDecimalSerializer instance = new BigDecimalSerializer();

    /**
     * <p>
     * Note: usually you should NOT create new instances, but instead use
     * {@link #instance} which is stateless and fully thread-safe. However,
     * there are cases where constructor is needed; for example,
     * when using explicit serializer annotations like
     * {@link com.fasterxml.jackson.databind.annotation.JsonSerialize#using}.
     */
    public BigDecimalSerializer() {
        super(BigDecimal.class);
    }

    /**
     * Sometimes it may actually make sense to retain actual handled type.
     */
    public BigDecimalSerializer(Class<?> handledType) {
        super(handledType);
    }

    @Override
    public final String valueToString(Object value) {
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).stripTrailingZeros().toPlainString();
        }
        return value.toString();
    }
}
