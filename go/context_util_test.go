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
package sffxschema

import (
	"testing"
	"time"
)

const CeSfContext = "eyJhcGlWZXJzaW9uIjoiNTAuMCIsInBheWxvYWRWZXJzaW9uIjoiMC4x" +
	"IiwidXNlckNvbnRleHQiOnsib3JnSWQiOiIwMER4eDAwMDAwMDZJWUoiLCJ1c2VySWQiOiIwMDV4eDAwMDA" +
	"wMVg4VXoiLCJvbkJlaGFsZk9mVXNlcklkIjpudWxsLCJ1c2VybmFtZSI6InRlc3QtenFpc25mNnl0bHF2QG" +
	"V4YW1wbGUuY29tIiwic2FsZXNmb3JjZUJhc2VVcmwiOiJodHRwOi8vcGlzdGFjaGlvLXZpcmdvLTEwNjMtZ" +
	"GV2LWVkLmxvY2FsaG9zdC5pbnRlcm5hbC5zYWxlc2ZvcmNlLmNvbTo2MTA5Iiwib3JnRG9tYWluVXJsIjoi" +
	"aHR0cDovL3Bpc3RhY2hpby12aXJnby0xMDYzLWRldi1lZC5sb2NhbGhvc3QuaW50ZXJuYWwuc2FsZXNmb3J" +
	"jZS5jb206NjEwOSJ9fQ=="

const CeSfFnContext = "eyJhY2Nlc3NUb2tlbiI6IjAwRHh4MDAwMDAwNklZSiFBUUVBUU5SYUFRRUFRTlJhQ" +
	"VFFQVFOUmFBUUVBUU5SYUFRRUFRTlJhQVFFQVFOUmFBUUVBUU5SYUFRRUFRTlJhQVFFQVFOUmFBUUVBUU5S" +
	"YSIsImZ1bmN0aW9uSW52b2NhdGlvbklkIjoiOW1kNnUwMDAwMDAwTGZ4QUFFIiwicHJveHlDbGllbnRUb2t" +
	"lbiI6InNmLWZ4LXByb3h5OW1kNnUwMDAwMDAwTGZ4QUFFLXNmZFp6MTl2SUxtWnoxOSIsImZ1bmN0aW9uTm" +
	"FtZSI6Ik15UHJvamVjdC50ZXN0Zm4xIiwiYXBleElkIjoiYTBjTTAwMDAwMDQzU1Q0IiwiYXBleEZRTiI6I" +
	"mNsYXNzZXMvRnVuY3Rpb25BcGV4LmNsczoxNCIsInJlcXVlc3RJZCI6IjAwRHh4MDAwMDAwNklZSkVBMi05" +
	"bWQ2dTAwMDAwMDBMZnhBQUUtMWE4OTNlNTAiLCJyZXNvdXJjZSI6InRlc3RmbjEtYW50ZWF0ZXItZDk5LmJ" +
	"hc2luLWRkMWRkMS5ldmVyZ3JlZW4uc3BhY2UiLCJhc3luY1Jlc3BvbnNlQ2FsbGJhY2tQYXRoIjoiL3Nlcn" +
	"ZpY2VzL2Z1bmN0aW9uL2FyaCIsImRlYWRsaW5lIjoiMjAyMi0wMi0xMFQxNzo1OToxMVoifQ=="

func TestSfContext(t *testing.T) {
	var ctx *SfContext
	var err error
	var expStr string
	var gotStr string

	uu := []struct {
		encoded string
	}{
		{encoded: CeSfContext},
		{encoded: "-replaced by first run-"},
	}
	for i, u := range uu {
		ctx, err = DecodeSfContext(u.encoded)
		if err != nil {
			t.Fatalf("[%d] err=%v", i, err)
		}
		if ctx == nil {
			t.Fatalf("[%d] expected non-nil decode, got: %v", i, ctx)
		}
		expStr = "50.0"
		gotStr = ctx.GetApiVersion()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "apiVersion", expStr, gotStr)
		}
		expStr = "0.1"
		gotStr = ctx.GetPayloadVersion()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "payloadVersion", expStr, gotStr)
		}

		uctx, ok := ctx.GetUserContextOk()
		if !ok {
			t.Fatalf("[%d] expected GetUserContextOk, got=%v", i, ok)
		}
		if uctx == nil {
			t.Fatalf("[%d] expected non-nil UserContext, got=%v", i, uctx)
		}
		expStr = "00Dxx0000006IYJ"
		gotStr = uctx.GetOrgId()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "orgId", expStr, gotStr)
		}
		expStr = "005xx000001X8Uz"
		gotStr = uctx.GetUserId()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "userId", expStr, gotStr)
		}
		gotStr = uctx.GetOnBehalfOfUserId()
		var gotStrPtr *string
		gotStrPtr, ok = uctx.GetOnBehalfOfUserIdOk()
		if ok {
			t.Errorf("[%d] expected !GetOnBehalfOfUserIdOk, got=%v", i, ok)
		}
		if gotStrPtr != nil {
			t.Errorf("[%d] expected nil *onBehalfOfUserId, got=%v", i, gotStrPtr)
		}
		expStr = ""
		gotStr = uctx.GetOnBehalfOfUserId()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "onBehalfOfUserId", expStr, gotStr)
		}
		expStr = "test-zqisnf6ytlqv@example.com"
		gotStr = uctx.GetUsername()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "username", expStr, gotStr)
		}
		expStr = "http://pistachio-virgo-1063-dev-ed.localhost.internal.salesforce.com:6109"
		gotStr = uctx.GetSalesforceBaseUrl()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "salesforceBaseUrl", expStr, gotStr)
		}
		gotStr = uctx.GetOrgDomainUrl()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "orgDomainUrl", expStr, gotStr)
		}

		// In golang, JSON serialization does not maintain ordering so cannot do direct String compare
		// so need to Encode/Decode/compare contents
		if i == 0 {
			var reEncoded string
			reEncoded, err = EncodeSfContext(ctx)
			if err != nil {
				t.Fatal(err)
			}

			// Replace next iteration through loop with re-encoded value
			uu[1].encoded = reEncoded
		}
	}
}

func TestSfFnContext(t *testing.T) {
	var ctx *SfFnContext
	var err error
	var expStr string
	var gotStr string

	uu := []struct {
		encoded string
	}{
		{encoded: CeSfFnContext},
		{encoded: "-replaced by first run-"},
	}
	for i, u := range uu {
		ctx, err = DecodeSfFnContext(u.encoded)
		if err != nil {
			t.Fatalf("[%d] err=%v", i, err)
		}
		if ctx == nil {
			t.Fatalf("[%d] expected non-nil decode, got: %v", i, ctx)
		}
		expStr = "00Dxx0000006IYJ!AQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRaAQEAQNRa"
		gotStr = ctx.GetAccessToken()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "accessToken", expStr, gotStr)
		}
		expStr = "9md6u0000000LfxAAE"
		gotStr = ctx.GetFunctionInvocationId()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "functionInvocationId", expStr, gotStr)
		}
		expStr = "sf-fx-proxy9md6u0000000LfxAAE-sfdZz19vILmZz19"
		gotStr = ctx.GetProxyClientToken()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "proxyClientToken", expStr, gotStr)
		}
		expStr = "MyProject.testfn1"
		gotStr = ctx.GetFunctionName()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "functionName", expStr, gotStr)
		}
		expStr = "a0cM00000043ST4"
		gotStr = ctx.GetApexId()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "apexId", expStr, gotStr)
		}
		expStr = "classes/FunctionApex.cls:14"
		gotStr = ctx.GetApexFQN()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "apexFQN", expStr, gotStr)
		}
		expStr = "00Dxx0000006IYJEA2-9md6u0000000LfxAAE-1a893e50"
		gotStr = ctx.GetRequestId()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "requestId", expStr, gotStr)
		}
		expStr = "testfn1-anteater-d99.basin-dd1dd1.evergreen.space"
		gotStr = ctx.GetResource()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "resource", expStr, gotStr)
		}
		expStr = "/services/function/arh"
		gotStr = ctx.GetAsyncResponseCallbackPath()
		if gotStr != expStr {
			t.Errorf("[%d] expected %s=%s, got=%s", i, "asyncResponseCallbackPath", expStr, gotStr)
		}
		expTime := time.Date(2022, time.February, 10, 17, 59, 11, 0, time.UTC)
		gotTime := ctx.GetDeadline()
		if gotTime != expTime {
			t.Errorf("[%d] expected %s=%v, got=%v", i, "deadline", expTime, gotTime)
		}

		// In golang, JSON serialization does not maintain ordering so cannot do direct String compare
		// so need to Encode/Decode/compare contents
		if i == 0 {
			var reEncoded string
			reEncoded, err = EncodeSfFnContext(ctx)
			if err != nil {
				t.Fatal(err)
			}

			// Replace next iteration through loop with re-encoded value
			uu[1].encoded = reEncoded
		}
	}
}
