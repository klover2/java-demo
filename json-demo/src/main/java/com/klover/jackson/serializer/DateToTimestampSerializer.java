package com.klover.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import java.io.IOException;
import java.io.Serial;
import java.text.DateFormat;
import java.util.Date;

/**
 * DateToTimestampSerializer
 *
 * @author klover
 * @date 2024/3/18 10:33
 */
public class DateToTimestampSerializer extends DateSerializer {
    @Serial
    private static final long serialVersionUID = -1965529463039965285L;

    /**
     * Default instance that is used when no contextual configuration
     * is needed.
     */
    public static final DateToTimestampSerializer instance = new DateToTimestampSerializer();

    public DateToTimestampSerializer() {
        this(null);
    }

    public DateToTimestampSerializer(DateFormat customFormat) {
        super(true, customFormat);
    }

    @Override
    public void serialize(Date value, JsonGenerator g, SerializerProvider provider) throws IOException {
        g.writeString(String.valueOf(_timestamp(value)));
    }
}
