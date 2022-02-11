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

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.Test;


/**
 * Unit tests for ContextUtil
 */
public class ContextUtilTest {
    static final String CE_SFCONTEXT = "eyJhcGlWZXJzaW9uIjoiNTAuMCIsInBheWxvYWRWZXJzaW9uIjoiMC4x" +
            "IiwidXNlckNvbnRleHQiOnsib3JnSWQiOiIwMER4eDAwMDAwMDZJWUoiLCJ1c2VySWQiOiIwMDV4eDAwMDA" +
            "wMVg4VXoiLCJvbkJlaGFsZk9mVXNlcklkIjpudWxsLCJ1c2VybmFtZSI6InRlc3QtenFpc25mNnl0bHF2QG" +
            "V4YW1wbGUuY29tIiwic2FsZXNmb3JjZUJhc2VVcmwiOiJodHRwOi8vcGlzdGFjaGlvLXZpcmdvLTEwNjMtZ" +
            "GV2LWVkLmxvY2FsaG9zdC5pbnRlcm5hbC5zYWxlc2ZvcmNlLmNvbTo2MTA5Iiwib3JnRG9tYWluVXJsIjoi" +
            "aHR0cDovL3Bpc3RhY2hpby12aXJnby0xMDYzLWRldi1lZC5sb2NhbGhvc3QuaW50ZXJuYWwuc2FsZXNmb3J" +
            "jZS5jb206NjEwOSJ9fQ==";
    static final String CE_SFFNCONTEXT = "eyJhY2Nlc3NUb2tlbiI6IjAwRHh4MDAwMDAwNklZSiFBUUVBUU5SYU" +
            "FRRUFRTlJhQVFFQVFOUmFBUUVBUU5SYUFRRUFRTlJhQVFFQVFOUmFBUUVBUU5SYUFRRUFRTlJhQVFFQVFOU" +
            "mFBUUVBUU5SYSIsImZ1bmN0aW9uSW52b2NhdGlvbklkIjoiOW1kNnUwMDAwMDAwTGZ4QUFFIiwicHJveHlD" +
            "bGllbnRUb2tlbiI6InNmLWZ4LXByb3h5OW1kNnUwMDAwMDAwTGZ4QUFFLXNmZFp6MTl2SUxtWnoxOSIsImZ" +
            "1bmN0aW9uTmFtZSI6Ik15UHJvamVjdC50ZXN0Zm4xIiwiYXBleElkIjoiYTBjTTAwMDAwMDQzU1Q0IiwiYX" +
            "BleEZRTiI6ImNsYXNzZXMvRnVuY3Rpb25BcGV4LmNsczoxNCIsInJlcXVlc3RJZCI6IjAwRHh4MDAwMDAwN" +
            "klZSkVBMi05bWQ2dTAwMDAwMDBMZnhBQUUtMWE4OTNlNTAiLCJyZXNvdXJjZSI6InRlc3RmbjEtYW50ZWF0" +
            "ZXItZDk5LmJhc2luLWRkMWRkMS5ldmVyZ3JlZW4uc3BhY2UiLCJhc3luY1Jlc3BvbnNlQ2FsbGJhY2tQYXR" +
            "oIjoiL3NlcnZpY2VzL2Z1bmN0aW9uL2FyaCIsImRlYWRsaW5lIjoiMjAyMi0wMi0xMFQxNzo1OToxMVoifQ" +
            "==";
    static final String RESPONSE_EXTRA_INFO = "%7B%22requestId%22%3A%2200Dxx0000006IYJEA2-9md6u0" +
            "000000LfxAAE-1a893e50%22%2C%22resource%22%3A%22testfn1-anteater-d99.basin-dd1dd1.ev" +
            "ergreen.space%22%2C%22source%22%3A%22com/example/fn/MyFunc.java%3A29%22%2C%22execTi" +
            "meMs%22%3A101.393%2C%22statusCode%22%3A500%2C%22isFunctionError%22%3Atrue%2C%22stac" +
            "k%22%3A%22com/example/fn/MyFunc.java%3A29%5Cn%20java/lang/RuntimeException.java%3A1" +
            "11%22%7D";

    @Test
    public void testDateTimeParse() throws Exception {
        OffsetDateTime expected = OffsetDateTime.of(2022, 2, 10, 17, 59, 11, 0, ZoneOffset.UTC);
        OffsetDateTime got;
        got = ContextUtil.parseOdt("2022-02-10T17:59:11.000Z");
        assertEquals(expected, got);
        got = ContextUtil.parseOdt("2022-02-10T17:59:11Z");
        assertEquals(expected, got);
        got = ContextUtil.parseOdt("2022-02-10T17:59:11+00:00");
        assertEquals(expected, got);
        got = ContextUtil.parseOdt("20220210T175911Z");
        assertEquals(expected, got);
        got = ContextUtil.parseOdt("20220210T175911.000Z");
        assertEquals(expected, got);
        got = ContextUtil.parseOdt("20220210T175911+0000");
        assertEquals(expected, got);
        got = ContextUtil.parseOdt("20220210T175911.000+0000");
        assertEquals(expected, got);
    }

    @Test
    public void testSfContext() throws Exception {
        SfContext ctx = ContextUtil.decodeSfContext(CE_SFCONTEXT);
        assertNotNull(ctx);
        assertNotNull(ctx.getApiVersion());
        assertNotNull(ctx.getPayloadVersion());
        assertNotNull(ctx.getUserContext());

        assertEquals("50.0", ctx.getApiVersion());
        assertEquals("0.1", ctx.getPayloadVersion());
        assertEquals("00Dxx0000006IYJ", ctx.getUserContext().getOrgId());
        assertEquals("005xx000001X8Uz", ctx.getUserContext().getUserId());
        assertNull(ctx.getUserContext().getOnBehalfOfUserId());
        assertEquals("test-zqisnf6ytlqv@example.com", ctx.getUserContext().getUsername());
        String urlStr = "http://pistachio-virgo-1063-dev-ed.localhost.internal.salesforce.com:6109";
        URI url = new URI(urlStr);
        assertEquals(url, ctx.getUserContext().getSalesforceBaseUrl());
        assertEquals(url, ctx.getUserContext().getOrgDomainUrl());

        String reEncoded = ContextUtil.encodeSfContext(ctx);
        assertEquals(CE_SFCONTEXT, reEncoded);
    }

    @Test
    public void testSfFnContext() throws Exception {
        SfFnContext ctx = ContextUtil.decodeSfFnContext(CE_SFFNCONTEXT);
        assertNotNull(ctx);
        assertNotNull(ctx.getAccessToken());
        assertNotNull(ctx.getFunctionInvocationId());
        assertNotNull(ctx.getProxyClientToken());
        assertNotNull(ctx.getFunctionName());
        assertNotNull(ctx.getApexId());
        assertNotNull(ctx.getApexFQN());
        assertNotNull(ctx.getRequestId());
        assertNotNull(ctx.getResource());
        assertNotNull(ctx.getAsyncResponseCallbackPath());
        assertNotNull(ctx.getDeadline());

        String accTok = "00Dxx0000006IYJ!AQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRa";
        assertEquals(accTok, ctx.getAccessToken());
        assertEquals("9md6u0000000LfxAAE", ctx.getFunctionInvocationId());
        assertEquals("sf-fx-proxy9md6u0000000LfxAAE-sfdZz19vILmZz19", ctx.getProxyClientToken());
        assertEquals("MyProject.testfn1", ctx.getFunctionName());
        assertEquals("a0cM00000043ST4", ctx.getApexId());
        assertEquals("classes/FunctionApex.cls:14", ctx.getApexFQN());
        assertEquals("00Dxx0000006IYJEA2-9md6u0000000LfxAAE-1a893e50", ctx.getRequestId());
        assertEquals("testfn1-anteater-d99.basin-dd1dd1.evergreen.space", ctx.getResource());
        assertEquals("/services/function/arh", ctx.getAsyncResponseCallbackPath());
        OffsetDateTime odt = OffsetDateTime.of(2022, 2, 10, 17, 59, 11, 0, ZoneOffset.UTC);
        assertEquals(odt, ctx.getDeadline());

        String reEncoded = ContextUtil.encodeSfFnContext(ctx);
        assertEquals(CE_SFFNCONTEXT, reEncoded);
    }

    @Test
    public void testExtraInfo() throws Exception {
        ResponseExtraInfo xi = ContextUtil.decodeExtraInfo(RESPONSE_EXTRA_INFO);
        assertNotNull(xi);
        assertNotNull(xi.getRequestId());
        assertNotNull(xi.getResource());
        assertNotNull(xi.getSource());
        assertNotNull(xi.getExecTimeMs());
        assertNotNull(xi.getStatusCode());
        assertNotNull(xi.getIsFunctionError());
        assertNotNull(xi.getStack());

        assertEquals("00Dxx0000006IYJEA2-9md6u0000000LfxAAE-1a893e50", xi.getRequestId());
        assertEquals("testfn1-anteater-d99.basin-dd1dd1.evergreen.space", xi.getResource());
        assertEquals("com/example/fn/MyFunc.java:29", xi.getSource());
        assertEquals(new BigDecimal("101.393"), xi.getExecTimeMs());
        assertEquals(Integer.valueOf(500), xi.getStatusCode());
        assertEquals(Boolean.valueOf(true), xi.getIsFunctionError());
        assertEquals("com/example/fn/MyFunc.java:29\n java/lang/RuntimeException.java:111", xi.getStack());

        String reEncoded = ContextUtil.encodeExtraInfo(xi);
        // Java URLEncoder escapes slashes (/) but python3 urllib (created sample above) does not.
        // Also Java URLEncoder escapes spaces with plus (+), urlllib used %20.
        String expected = RESPONSE_EXTRA_INFO.replaceAll("/", "%2F").replaceAll("%20", "+");
        assertEquals(expected, reEncoded);
    }
}
