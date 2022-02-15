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

/**
 * ContextUtil unit tests.
 */
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

});
