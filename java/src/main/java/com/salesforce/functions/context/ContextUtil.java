/*
 * Copyright 2022 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.salesforce.functions.context;

import java.io.IOException;
import java.text.ParsePosition;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Utility methods to decode and encode context methods in a form suitable for
 * CloudEvent extensions `sfcontext` and `sffncontext`.
 */
public class ContextUtil {

    // Thread-safe decoder/encoder instances
    static final DateTimeFormatter ODT_FMT = new DateTimeFormatterBuilder()
            .appendPattern("yyyyMMdd'T'HHmmssX")
            .toFormatter();
    static final SimpleModule ODT_PARSE_MOD = new SimpleModule()
    .addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
        @Override
        public OffsetDateTime deserialize(JsonParser parser, DeserializationContext dctx) throws IOException {            
            return ContextUtil.parseOdt(parser.getText());
        }

    }).addSerializer(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>() {
        @Override
        public void serialize(OffsetDateTime odt, JsonGenerator jg, SerializerProvider sp) throws IOException {
            jg.writeString(ContextUtil.formatOdt(odt));
        }
    });

    static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .addModule(new JavaTimeModule())
            .addModule(ODT_PARSE_MOD)
            .build();
    static final Base64.Encoder B64_ENCODER = Base64.getEncoder();
    static final Base64.Decoder B64_DECODER = Base64.getDecoder();

    // Static methods only, no need to construct.
    private ContextUtil() {
    }

    /**
     * Decode a SfContext from its base64-encoded JSON.
     * @param base64Str base64-encoded JSON
     * @return decoded object if input is non-empty/non-null.  Null if given null/empty input.
     * @throws IOException if malformed JSON or base64.
     */
    public static SfContext decodeSfContext(String base64Str) throws IOException {
        return decodeInternal(base64Str, SfContext.class);
    }

    /**
     * Encode a SfContext to base64-encoded JSON.
     * @param obj Salesforce Context, including UserContext.
     * @return base64-encoded JSON string if input is non-null.  Null if given null input.
     */
    public static String encodeSfContext(SfContext obj) {
        return encodeInternal(obj);
    }

    /**
     * Decode a SfFnContext from its base64-encoded JSON.
     * @param base64Str base64-encoded JSON
     * @return decoded object if input is non-empty/non-null.  Null if given null/empty input.
     * @throws IOException if malformed JSON or base64.
     */
    public static SfFnContext decodeSfFnContext(String base64Str) throws IOException {
        return decodeInternal(base64Str, SfFnContext.class);
    }

    /**
     * Encode a SfFnContext to base64-encoded JSON.
     * @param obj Salesforce Function Context.
     * @return base64-encoded JSON string if input is non-null.  Null if given null input.
     */
    public static String encodeSfFnContext(SfFnContext obj) {
        return encodeInternal(obj);
    }

    private static String encodeInternal(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            byte[] jsonBytes = OBJECT_MAPPER.writeValueAsBytes(obj);
            return B64_ENCODER.encodeToString(jsonBytes);
        } catch (IOException ex) {
            throw new RuntimeException("Unable to encode JSON", ex);
        }
    }

    private static <T> T decodeInternal(String base64Str, Class<T> cls) throws IOException {
        if (base64Str == null || base64Str.trim().length() == 0) {
            return null;
        }
        byte[] jsonDecoded = B64_DECODER.decode(base64Str);
        return OBJECT_MAPPER.readValue(jsonDecoded, cls);
    }

    @SuppressWarnings("deprecated")
    static OffsetDateTime parseOdt(String txt) throws IOException {
        try {
            java.util.Date dt = com.fasterxml.jackson.databind.util.ISO8601Utils.parse(txt, new ParsePosition(0));
            return OffsetDateTime.ofInstant(Instant.ofEpochMilli(dt.getTime()), ZoneOffset.UTC);
        } catch (java.text.ParseException ex) {
            throw new IOException(ex);
        }
    }

    static String formatOdt(OffsetDateTime odt) {
        return ODT_FMT.format(odt.atZoneSameInstant(ZoneOffset.UTC));
    }
}
