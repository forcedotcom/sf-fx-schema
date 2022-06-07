/**
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */



/**
* Extra response out-of-band information returned from function invocation
*/
export class ResponseExtraInfo {
    /**
    * Unique function request identifier
    */
    'requestId': string;
    /**
    * Resource identifier of function being invoked
    */
    'resource'?: string;
    /**
    * Source path:line number if possible to fill in on error, null/undefined otherwise
    */
    'source'?: string;
    /**
    * Execution time in milliseconds, may include fractional millis after decimal point
    */
    'execTimeMs'?: number;
    /**
    * HTTP status code of response: 200-599, -1 if non-HTTP error encountered.
    */
    'statusCode'?: number;
    /**
    * Flag set to true if error encountered in function code, false if other error, null/undefined if no error
    */
    'isFunctionError'?: boolean;
    /**
    * Language-specific error stack trace to help developer diagnose issues, null/undefined if no error
    */
    'stack'?: string;
    /**
    * Optional error message for failed function invocations
    */
    'errorMessage'?: string;

    static discriminator: string | undefined = undefined;

    static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
        {
            "name": "requestId",
            "baseName": "requestId",
            "type": "string"
        },
        {
            "name": "resource",
            "baseName": "resource",
            "type": "string"
        },
        {
            "name": "source",
            "baseName": "source",
            "type": "string"
        },
        {
            "name": "execTimeMs",
            "baseName": "execTimeMs",
            "type": "number"
        },
        {
            "name": "statusCode",
            "baseName": "statusCode",
            "type": "number"
        },
        {
            "name": "isFunctionError",
            "baseName": "isFunctionError",
            "type": "boolean"
        },
        {
            "name": "stack",
            "baseName": "stack",
            "type": "string"
        },
        {
            "name": "errorMessage",
            "baseName": "errorMessage",
            "type": "string"
        }    ];

    static getAttributeTypeMap() {
        return ResponseExtraInfo.attributeTypeMap;
    }
}

