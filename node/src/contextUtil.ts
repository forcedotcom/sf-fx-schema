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

    public static decodeSfContext(base64Str: string): SfContext {
        if (base64Str == null || base64Str.trim().length === 0) {
            return null;
        }
        var obj: SfContext = JSON.parse(Buffer.from(base64Str, "base64").toString("utf8"));
        return obj;
    }

    public static encodeSfContext(obj: SfContext): string {
        if (obj == null) {
            return null;
        }
        return Buffer.from(JSON.stringify(obj), "utf8").toString("base64");
    }

    public static decodeSfFnContext(base64Str: string): SfFnContext {
        if (base64Str == null || base64Str.trim().length === 0) {
            return null;
        }
        var obj: SfFnContext = JSON.parse(Buffer.from(base64Str, "base64").toString("utf8"));
        return obj;
    }

    public static encodeSfFnContext(obj: SfFnContext): string {
        if (obj == null) {
            return null;
        }
        return Buffer.from(JSON.stringify(obj), "utf8").toString("base64");
    }

    public static decodeExtraInfo(urlEncStr: string): ResponseExtraInfo {
        if (urlEncStr == null || urlEncStr.trim().length === 0) {
            return null;
        }
        var obj: ResponseExtraInfo = JSON.parse(decodeURIComponent(urlEncStr));
        return obj;
    }

    public static encodeExtraInfo(obj: ResponseExtraInfo): string {
        if (obj == null) {
            return null;
        }
        return encodeURIComponent(JSON.stringify(obj));
    }
}
