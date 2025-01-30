package com.simsekali.awssqsdemo.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private static final String DEFAULT_TIME_ZONE = "GMT";

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(DEFAULT_TIME_ZONE));
        jsonGenerator.writeNumber(zonedDateTime.toInstant().toEpochMilli());
    }
}
