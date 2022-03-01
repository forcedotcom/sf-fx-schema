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
	"encoding/base64"
	"encoding/json"
	"net/url"
	"strings"
)

func DecodeSfContext(base64Str string) (*SfContext, error) {
	if len(strings.TrimSpace(base64Str)) == 0 {
		return nil, nil
	}
	ctx := &SfContext{}
	err := decodeInternal(base64Str, ctx)
	if err != nil {
		return nil, err
	}
	return ctx, nil
}

func EncodeSfContext(sfcontext *SfContext) (string, error) {
	if sfcontext == nil {
		return "", nil
	}
	bytes, err := sfcontext.MarshalJSON()
	if err != nil {
		return "", err
	}
	return base64.StdEncoding.EncodeToString(bytes), nil
}

func DecodeSfFnContext(base64Str string) (*SfFnContext, error) {
	if len(strings.TrimSpace(base64Str)) == 0 {
		return nil, nil
	}
	ctx := &SfFnContext{}
	err := decodeInternal(base64Str, ctx)
	if err != nil {
		return nil, err
	}
	return ctx, err
}

func EncodeSfFnContext(sffncontext *SfFnContext) (string, error) {
	if sffncontext == nil {
		return "", nil
	}
	bytes, err := sffncontext.MarshalJSON()
	if err != nil {
		return "", err
	}
	return base64.StdEncoding.EncodeToString(bytes), nil
}

func DecodeExtraInfo(urlEncStr string) (*ResponseExtraInfo, error) {
	if len(strings.TrimSpace(urlEncStr)) == 0 {
		return nil, nil
	}
	decoded, err := url.QueryUnescape(urlEncStr)
	if err != nil {
		return nil, err
	}
	targetObj := &ResponseExtraInfo{}
	err = json.Unmarshal([]byte(decoded), targetObj)
	if err != nil {
		return nil, err
	}
	return targetObj, nil
}

func EncodeExtraInfo(extraInfo *ResponseExtraInfo) (string, error) {
	if extraInfo == nil {
		return "", nil
	}
	bytes, err := extraInfo.MarshalJSON()
	if err != nil {
		return "", err
	}
	return url.QueryEscape(string(bytes)), nil
}

func decodeInternal(base64Str string, targetCtx interface{}) error {
	var bytes []byte
	var err error
	bytes, err = base64.StdEncoding.DecodeString(base64Str)
	if err != nil {
		return err
	}
	err = json.Unmarshal(bytes, targetCtx)
	if err != nil {
		return err
	}
	return nil
}
