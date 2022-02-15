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

import { ResponseExtraInfo } from "./responseExtraInfo";
import { SfContext } from "./sfContext";
import { SfFnContext } from "./sfFnContext";

export class ContextUtil {

    /**
     * Decode a SfContext from its base64-encoded JSON.
     * @param base64Str base64-encoded JSON
     * @return decoded object if input is non-empty/non-null.  Null if given null/empty input.
     */
     public static decodeSfContext(base64Str: string): SfContext {
        if (base64Str == null || base64Str.trim().length === 0) {
            return null;
        }
        var obj: SfContext = JSON.parse(Buffer.from(base64Str, "base64").toString("utf8"));
        return obj;
    }

    /**
     * Encode a SfContext to base64-encoded JSON.
     * @param obj Salesforce Context, including UserContext.
     * @return base64-encoded JSON string if input is non-null.  Null if given null input.
     */
     public static encodeSfContext(obj: SfContext): string {
        if (obj == null) {
            return null;
        }
        return Buffer.from(JSON.stringify(obj), "utf8").toString("base64");
    }

    /**
     * Decode a SfFnContext from its base64-encoded JSON.  Note: will parse `deadline` string
     * in ISO8601 format to a JS `Date` object when set in incoming JSON object.
     * @param base64Str base64-encoded JSON
     * @return decoded object if input is non-empty/non-null.  Null if given null/empty input.
     */
     public static decodeSfFnContext(base64Str: string): SfFnContext {
        if (base64Str == null || base64Str.trim().length === 0) {
            return null;
        }
        var obj: SfFnContext = JSON.parse(Buffer.from(base64Str, "base64").toString("utf8"));
        if (obj.deadline != null && typeof obj.deadline === "string") {
            var dt: Date = new Date(Date.parse(obj.deadline));
            obj.deadline = dt;
        }
        return obj;
    }

    /**
     * Encode a SfFnContext to base64-encoded JSON.  Note: will format `deadline` as ISO8601
     * string to Seconds level in UTC with "Z" suffix.
     * @param obj Salesforce Function Context.
     * @return base64-encoded JSON string if input is non-null.  Null if given null input.
     */
     public static encodeSfFnContext(obj: SfFnContext): string {
        if (obj == null) {
            return null;
        }
        // Make a copy so we can format `deadline` if populated
        var objCopy: Object = Object.assign({}, obj);
        if (obj.deadline != null && obj.deadline instanceof Date) {
            var isoStr = obj.deadline.toISOString();
            isoStr = isoStr.slice(0,-5)+"Z" // remove .000 milliseconds part
            objCopy['deadline'] = isoStr;
        }
        return Buffer.from(JSON.stringify(objCopy), "utf8").toString("base64");
    }

    /**
     * Decode a ResponseExtraInfo from its URL-encoded UTF-8 JSON string.
     * Note use of URL encoding for this object rather than base64.
     * @param urlEncStr URL-encoded UTF-8 string.
     * @return decoded object if input is non-empty/non-null, Null if given null/empty input.
     */
     public static decodeExtraInfo(urlEncStr: string): ResponseExtraInfo {
        if (urlEncStr == null || urlEncStr.trim().length === 0) {
            return null;
        }
        var obj: ResponseExtraInfo = JSON.parse(decodeURIComponent(urlEncStr));
        return obj;
    }

    /**
     * Encode a ResponseExtraInfo to URL-encoded UTF-8 JSON.
     * @param obj Response Extra Info object.
     * @return URL-encoded UTF-9 string if input is non-null.  Null if given null input.
     */
     public static encodeExtraInfo(obj: ResponseExtraInfo): string {
        if (obj == null) {
            return null;
        }
        return encodeURIComponent(JSON.stringify(obj));
    }
}
