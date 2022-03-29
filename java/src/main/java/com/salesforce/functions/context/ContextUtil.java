/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.context;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
public final class ContextUtil {

    /** Thread-safe date/time formatter to format OffsetDateTime properties. */
    static final DateTimeFormatter ODT_FMT = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ssX")
            .toFormatter();

    /** Thread-safe JSON (de-)serializer module for OffsetDateTime to use in ObjectMapper. */
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

    /** Json parser/serializer configured with options to allow unknown properties and handle date-time as OffsetDateTime */
    static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .addModule(new JavaTimeModule())
            .addModule(ODT_PARSE_MOD)
            .build();

    /** Thread-safe Base64 encoder. */
    static final Base64.Encoder B64_ENCODER = Base64.getEncoder();

    /** Thread-safe Base64 decoder. */
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
     * @throws IOException if unable to encode.
     */
    public static String encodeSfContext(SfContext obj) throws IOException {
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
     * @throws IOException if unable to encode.
     */
    public static String encodeSfFnContext(SfFnContext obj) throws IOException {
        return encodeInternal(obj);
    }

    /**
     * Decode a ResponseExtraInfo from its URL-encoded UTF-8 JSON string.
     * Note use of URL encoding for this object rather than base64.
     * @param urlEncStr URL-encoded UTF-8 string.
     * @return decoded object if input is non-empty/non-null, Null if given null/empty input.
     * @throws IOException if malformed JSON or URL-encoding.
     */
    public static ResponseExtraInfo decodeExtraInfo(String urlEncStr) throws IOException {
        if (urlEncStr == null || urlEncStr.trim().length() == 0) {
            return null;
        }
        String decoded = URLDecoder.decode(urlEncStr, StandardCharsets.UTF_8);
        return OBJECT_MAPPER.readValue(decoded, ResponseExtraInfo.class);
    }

    /**
     * Encode a ResponseExtraInfo to URL-encoded UTF-8 JSON.
     * @param obj Response Extra Info object.
     * @return URL-encoded UTF-8 string if input is non-null. Null if given null input.
     * @throws IOException if unable to encode.
     */
    public static String encodeExtraInfo(ResponseExtraInfo obj) throws IOException {
        if (obj == null) {
            return null;
        }
        String jsonStr = OBJECT_MAPPER.writeValueAsString(obj);
        return URLEncoder.encode(jsonStr, StandardCharsets.UTF_8);
    }

    private static String encodeInternal(Object obj) throws IOException {
        if (obj == null) {
            return null;
        }
        byte[] jsonBytes = OBJECT_MAPPER.writeValueAsBytes(obj);
        return B64_ENCODER.encodeToString(jsonBytes);
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
