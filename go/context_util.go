/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
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
