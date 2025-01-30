package com.simsekali.awssqsdemo.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = ofPattern("yyyy-MM-dd");

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(DATE_TIME_FORMATTER));
    }
}
