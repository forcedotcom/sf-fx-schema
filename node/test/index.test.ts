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

import "mocha";
import {
   ContextUtil,
   ResponseExtraInfo,
   SfContext,
   SfFnContext,
   UserContext
} from "../src";
import { assert } from 'chai';

 const CE_SFCONTEXT = "eyJhcGlWZXJzaW9uIjoiNTAuMCIsInBheWxvYWRWZXJzaW9uIjoiMC4x" +
    "IiwidXNlckNvbnRleHQiOnsib3JnSWQiOiIwMER4eDAwMDAwMDZJWUoiLCJ1c2VySWQiOiIwMDV4eDAwMDA" +
    "wMVg4VXoiLCJvbkJlaGFsZk9mVXNlcklkIjpudWxsLCJ1c2VybmFtZSI6InRlc3QtenFpc25mNnl0bHF2QG" +
    "V4YW1wbGUuY29tIiwic2FsZXNmb3JjZUJhc2VVcmwiOiJodHRwOi8vcGlzdGFjaGlvLXZpcmdvLTEwNjMtZ" +
    "GV2LWVkLmxvY2FsaG9zdC5pbnRlcm5hbC5zYWxlc2ZvcmNlLmNvbTo2MTA5Iiwib3JnRG9tYWluVXJsIjoi" +
    "aHR0cDovL3Bpc3RhY2hpby12aXJnby0xMDYzLWRldi1lZC5sb2NhbGhvc3QuaW50ZXJuYWwuc2FsZXNmb3J" +
    "jZS5jb206NjEwOSJ9fQ==";
 const CE_SFFNCONTEXT = "eyJhY2Nlc3NUb2tlbiI6IjAwRHh4MDAwMDAwNklZSiFBUUVBUU5SYU" +
    "FRRUFRTlJhQVFFQVFOUmFBUUVBUU5SYUFRRUFRTlJhQVFFQVFOUmFBUUVBUU5SYUFRRUFRTlJhQVFFQVFOU" +
    "mFBUUVBUU5SYSIsImZ1bmN0aW9uSW52b2NhdGlvbklkIjoiOW1kNnUwMDAwMDAwTGZ4QUFFIiwicHJveHlD" +
    "bGllbnRUb2tlbiI6InNmLWZ4LXByb3h5OW1kNnUwMDAwMDAwTGZ4QUFFLXNmZFp6MTl2SUxtWnoxOSIsImZ" +
    "1bmN0aW9uTmFtZSI6Ik15UHJvamVjdC50ZXN0Zm4xIiwiYXBleElkIjoiYTBjTTAwMDAwMDQzU1Q0IiwiYX" +
    "BleEZRTiI6ImNsYXNzZXMvRnVuY3Rpb25BcGV4LmNsczoxNCIsInJlcXVlc3RJZCI6IjAwRHh4MDAwMDAwN" +
    "klZSkVBMi05bWQ2dTAwMDAwMDBMZnhBQUUtMWE4OTNlNTAiLCJyZXNvdXJjZSI6InRlc3RmbjEtYW50ZWF0" +
    "ZXItZDk5LmJhc2luLWRkMWRkMS5ldmVyZ3JlZW4uc3BhY2UiLCJhc3luY1Jlc3BvbnNlQ2FsbGJhY2tQYXR" +
    "oIjoiL3NlcnZpY2VzL2Z1bmN0aW9uL2FyaCIsImRlYWRsaW5lIjoiMjAyMi0wMi0xMFQxNzo1OToxMVoifQ" +
 "==";
 const RESPONSE_EXTRA_INFO = "%7B%22requestId%22%3A%2200Dxx0000006IYJEA2-9md6u0" +
    "000000LfxAAE-1a893e50%22%2C%22resource%22%3A%22testfn1-anteater-d99.basin-dd1dd1.ev" +
    "ergreen.space%22%2C%22source%22%3A%22com/example/fn/MyFunc.java%3A29%22%2C%22execTi" +
    "meMs%22%3A101.393%2C%22statusCode%22%3A500%2C%22isFunctionError%22%3Atrue%2C%22stac" +
    "k%22%3A%22com/example/fn/MyFunc.java%3A29%5Cn%20java/lang/RuntimeException.java%3A1" +
    "11%22%7D";

/**
 * ContextUtil unit tests.
 */
 describe('Unit Tests', () => {

  it("testSfContext", () => {
    var ctx: SfContext = ContextUtil.decodeSfContext(CE_SFCONTEXT);
    assert.isNotNull(ctx);
    assert.isNotNull(ctx.apiVersion);
    assert.isNotNull(ctx.payloadVersion);
    assert.isNotNull(ctx.userContext);

    assert.equal("50.0", ctx.apiVersion);
    assert.equal("0.1", ctx.payloadVersion);
    assert.equal("00Dxx0000006IYJ", ctx.userContext.orgId);
    assert.equal("005xx000001X8Uz", ctx.userContext.userId);
    assert.isNull(ctx.userContext.onBehalfOfUserId);
    assert.equal("test-zqisnf6ytlqv@example.com", ctx.userContext.username);
    var urlStr: string = "http://pistachio-virgo-1063-dev-ed.localhost.internal.salesforce.com:6109";
    assert.equal(urlStr, ctx.userContext.salesforceBaseUrl);
    assert.equal(urlStr, ctx.userContext.orgDomainUrl);

    var reEncoded: string = ContextUtil.encodeSfContext(ctx);
    assert.equal(CE_SFCONTEXT, reEncoded);
  });

  it("testSfFnContext", () => {
    var ctx: SfFnContext = ContextUtil.decodeSfFnContext(CE_SFFNCONTEXT);
    assert.isNotNull(ctx);
    assert.isNotNull(ctx.accessToken);
    assert.isNotNull(ctx.functionInvocationId);
    assert.isNotNull(ctx.proxyClientToken);
    assert.isNotNull(ctx.functionName);
    assert.isNotNull(ctx.apexId);
    assert.isNotNull(ctx.apexFQN);
    assert.isNotNull(ctx.requestId);
    assert.isNotNull(ctx.resource);
    assert.isNotNull(ctx.asyncResponseCallbackPath);
    assert.isNotNull(ctx.deadline);

    var accTok: string = "00Dxx0000006IYJ!AQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRa";
    assert.equal(accTok, ctx.accessToken);
    assert.equal("9md6u0000000LfxAAE", ctx.functionInvocationId);
    assert.equal("sf-fx-proxy9md6u0000000LfxAAE-sfdZz19vILmZz19", ctx.proxyClientToken);
    assert.equal("MyProject.testfn1", ctx.functionName);
    assert.equal("a0cM00000043ST4", ctx.apexId);
    assert.equal("classes/FunctionApex.cls:14", ctx.apexFQN);
    assert.equal("00Dxx0000006IYJEA2-9md6u0000000LfxAAE-1a893e50", ctx.requestId);
    assert.equal("testfn1-anteater-d99.basin-dd1dd1.evergreen.space", ctx.resource);
    assert.equal("/services/function/arh", ctx.asyncResponseCallbackPath);
    var february = 1; // Ugh, JS 0-based months
    var dt: Date = new Date(Date.UTC(2022, february, 10, 17, 59, 11, 0));
    assert.equal(dt.toISOString(), ctx.deadline.toISOString());

    var reEncoded: string = ContextUtil.encodeSfFnContext(ctx);
    assert.equal(CE_SFFNCONTEXT, reEncoded);
  });

  it("testExtraInfo", () => {
    var xi: ResponseExtraInfo = ContextUtil.decodeExtraInfo(RESPONSE_EXTRA_INFO);
    assert.isNotNull(xi);
    assert.isNotNull(xi.requestId);
    assert.isNotNull(xi.resource);
    assert.isNotNull(xi.source);
    assert.isNotNull(xi.execTimeMs);
    assert.isNotNull(xi.statusCode);
    assert.isNotNull(xi.isFunctionError);
    assert.isNotNull(xi.stack);

    assert.equal("00Dxx0000006IYJEA2-9md6u0000000LfxAAE-1a893e50", xi.requestId);
    assert.equal("testfn1-anteater-d99.basin-dd1dd1.evergreen.space", xi.resource);
    assert.equal("com/example/fn/MyFunc.java:29", xi.source);
    assert.equal(101.393, xi.execTimeMs);
    assert.equal(500, xi.statusCode);
    assert.equal(true, xi.isFunctionError);
    assert.equal("com/example/fn/MyFunc.java:29\n java/lang/RuntimeException.java:111", xi.stack);

    var reEncoded: string = ContextUtil.encodeExtraInfo(xi);
    // JS encodeURIComponent escapes slashes (/) but python3 urllib (created sample above) does not.
    let re = /[/]/gi;
    var expected: string = RESPONSE_EXTRA_INFO.replace(re, "%2F");
    assert.equal(expected, reEncoded);
  });

});
