/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
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
    static final String CE_SFCONTEXT = 
            "eyJhcGlWZXJzaW9uIjoiNTAuMCIsInBheWxvYWRWZXJzaW9uIjoiMC4xIiwidXNlckNvbnRleHQiOnsib3J" + 
            "nSWQiOiIwMER4eDAwMDAwMDZJWUoiLCJ1c2VySWQiOiIwMDV4eDAwMDAwMVg4VXoiLCJvbkJlaGFsZk9mVX" + 
            "NlcklkIjpudWxsLCJ1c2VybmFtZSI6InRlc3QtenFpc25mNnl0bHF2QGV4YW1wbGUuY29tIiwic2FsZXNmb" + 
            "3JjZUJhc2VVcmwiOiJodHRwOi8vcGlzdGFjaGlvLXZpcmdvLTEwNjMtZGV2LWVkLmxvY2FsaG9zdC5pbnRl" + 
            "cm5hbC5zYWxlc2ZvcmNlLmNvbTo2MTA5Iiwib3JnRG9tYWluVXJsIjoiaHR0cDovL3Bpc3RhY2hpby12aXJ" + 
            "nby0xMDYzLWRldi1lZC5sb2NhbGhvc3QuaW50ZXJuYWwuc2FsZXNmb3JjZS5jb206NjEwOSIsInNhbGVzZm" + 
            "9yY2VJbnN0YW5jZSI6bnVsbH19";
    static final String CE_SFFNCONTEXT =             
            "eyJhY2Nlc3NUb2tlbiI6IjAwRHh4MDAwMDAwNklZSiFBUUVBUU5SYUFRRUFRTlJhQVFFQVFOUmFBUUVBUU5" + 
            "SYUFRRUFRTlJhQVFFQVFOUmFBUUVBUU5SYUFRRUFRTlJhQVFFQVFOUmFBUUVBUU5SYSIsImZ1bmN0aW9uSW" + 
            "52b2NhdGlvbklkIjoiOW1kNnUwMDAwMDAwTGZ4QUFFIiwicHJveHlDbGllbnRUb2tlbiI6InNmLWZ4LXByb" + 
            "3h5OW1kNnUwMDAwMDAwTGZ4QUFFLXNmZFp6MTl2SUxtWnoxOSIsImZ1bmN0aW9uTmFtZSI6Ik15UHJvamVj" + 
            "dC50ZXN0Zm4xIiwiYXBleElkIjoiYTBjTTAwMDAwMDQzU1Q0IiwiYXBleEZRTiI6ImNsYXNzZXMvRnVuY3R" + 
            "pb25BcGV4LmNsczoxNCIsInJlcXVlc3RJZCI6IjAwRHh4MDAwMDAwNklZSkVBMi05bWQ2dTAwMDAwMDBMZn" + 
            "hBQUUtMWE4OTNlNTAiLCJyZXNvdXJjZSI6InRlc3RmbjEtYW50ZWF0ZXItZDk5LmJhc2luLWRkMWRkMS5ld" + 
            "mVyZ3JlZW4uc3BhY2UiLCJhc3luY1Jlc3BvbnNlQ2FsbGJhY2tQYXRoIjoiL3NlcnZpY2VzL2Z1bmN0aW9u" + 
            "L2FyaCIsImRlYWRsaW5lIjoiMjAyMi0wMi0xMFQxNzo1OToxMVoiLCJmdW5jdGlvbk5hbWVzcGFjZSI6bnV" + 
            "sbCwiaW52b2tpbmdOYW1lc3BhY2UiOm51bGx9";

    static final String RESPONSE_EXTRA_INFO =     
            "%7B%22requestId%22%3A%2200Dxx0000006IYJEA2-9md6u0000000LfxAAE-1a893e50%22%2C%22reso" + 
            "urce%22%3A%22testfn1-anteater-d99.basin-dd1dd1.evergreen.space%22%2C%22source%22%3A" + 
            "%22com%2Fexample%2Ffn%2FMyFunc.java%3A29%22%2C%22execTimeMs%22%3A101.393%2C%22statu" + 
            "sCode%22%3A500%2C%22isFunctionError%22%3Atrue%2C%22stack%22%3A%22com%2Fexample%2Ffn" + 
            "%2FMyFunc.java%3A29%5Cn+java%2Flang%2FRuntimeException.java%3A111%22%2C%22errorMess" + 
            "age%22%3Anull%7D";

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
