/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

// Code generated by OpenAPI Generator (https://openapi-generator.tech); DO NOT EDIT.

package sffxschema

import (
	"encoding/json"
)

// UserContext Salesforce user/org context information
type UserContext struct {
	// 18-character unique Org identifier
	OrgId string `json:"orgId"`
	// 18-character unique User identifier
	UserId string `json:"userId"`
	// Optional 18-character unique User identifier invoked-on-behalf-of
	OnBehalfOfUserId *string `json:"onBehalfOfUserId,omitempty"`
	// Unique username
	Username string `json:"username"`
	// Base URL of Salesforce appserver that invoked function
	SalesforceBaseUrl string `json:"salesforceBaseUrl"`
	// MyDomain URL of Salesforce appserver that invoked function
	OrgDomainUrl *string `json:"orgDomainUrl,omitempty"`
	// Instance where organization is hosted
	SalesforceInstance *string `json:"salesforceInstance,omitempty"`
}

// NewUserContext instantiates a new UserContext object
// This constructor will assign default values to properties that have it defined,
// and makes sure properties required by API are set, but the set of arguments
// will change when the set of required properties is changed
func NewUserContext(orgId string, userId string, username string, salesforceBaseUrl string) *UserContext {
	this := UserContext{}
	this.OrgId = orgId
	this.UserId = userId
	this.Username = username
	this.SalesforceBaseUrl = salesforceBaseUrl
	return &this
}

// NewUserContextWithDefaults instantiates a new UserContext object
// This constructor will only assign default values to properties that have it defined,
// but it doesn't guarantee that properties required by API are set
func NewUserContextWithDefaults() *UserContext {
	this := UserContext{}
	return &this
}

// GetOrgId returns the OrgId field value
func (o *UserContext) GetOrgId() string {
	if o == nil {
		var ret string
		return ret
	}

	return o.OrgId
}

// GetOrgIdOk returns a tuple with the OrgId field value
// and a boolean to check if the value has been set.
func (o *UserContext) GetOrgIdOk() (*string, bool) {
	if o == nil  {
		return nil, false
	}
	return &o.OrgId, true
}

// SetOrgId sets field value
func (o *UserContext) SetOrgId(v string) {
	o.OrgId = v
}

// GetUserId returns the UserId field value
func (o *UserContext) GetUserId() string {
	if o == nil {
		var ret string
		return ret
	}

	return o.UserId
}

// GetUserIdOk returns a tuple with the UserId field value
// and a boolean to check if the value has been set.
func (o *UserContext) GetUserIdOk() (*string, bool) {
	if o == nil  {
		return nil, false
	}
	return &o.UserId, true
}

// SetUserId sets field value
func (o *UserContext) SetUserId(v string) {
	o.UserId = v
}

// GetOnBehalfOfUserId returns the OnBehalfOfUserId field value if set, zero value otherwise.
func (o *UserContext) GetOnBehalfOfUserId() string {
	if o == nil || o.OnBehalfOfUserId == nil {
		var ret string
		return ret
	}
	return *o.OnBehalfOfUserId
}

// GetOnBehalfOfUserIdOk returns a tuple with the OnBehalfOfUserId field value if set, nil otherwise
// and a boolean to check if the value has been set.
func (o *UserContext) GetOnBehalfOfUserIdOk() (*string, bool) {
	if o == nil || o.OnBehalfOfUserId == nil {
		return nil, false
	}
	return o.OnBehalfOfUserId, true
}

// HasOnBehalfOfUserId returns a boolean if a field has been set.
func (o *UserContext) HasOnBehalfOfUserId() bool {
	if o != nil && o.OnBehalfOfUserId != nil {
		return true
	}

	return false
}

// SetOnBehalfOfUserId gets a reference to the given string and assigns it to the OnBehalfOfUserId field.
func (o *UserContext) SetOnBehalfOfUserId(v string) {
	o.OnBehalfOfUserId = &v
}

// GetUsername returns the Username field value
func (o *UserContext) GetUsername() string {
	if o == nil {
		var ret string
		return ret
	}

	return o.Username
}

// GetUsernameOk returns a tuple with the Username field value
// and a boolean to check if the value has been set.
func (o *UserContext) GetUsernameOk() (*string, bool) {
	if o == nil  {
		return nil, false
	}
	return &o.Username, true
}

// SetUsername sets field value
func (o *UserContext) SetUsername(v string) {
	o.Username = v
}

// GetSalesforceBaseUrl returns the SalesforceBaseUrl field value
func (o *UserContext) GetSalesforceBaseUrl() string {
	if o == nil {
		var ret string
		return ret
	}

	return o.SalesforceBaseUrl
}

// GetSalesforceBaseUrlOk returns a tuple with the SalesforceBaseUrl field value
// and a boolean to check if the value has been set.
func (o *UserContext) GetSalesforceBaseUrlOk() (*string, bool) {
	if o == nil  {
		return nil, false
	}
	return &o.SalesforceBaseUrl, true
}

// SetSalesforceBaseUrl sets field value
func (o *UserContext) SetSalesforceBaseUrl(v string) {
	o.SalesforceBaseUrl = v
}

// GetOrgDomainUrl returns the OrgDomainUrl field value if set, zero value otherwise.
func (o *UserContext) GetOrgDomainUrl() string {
	if o == nil || o.OrgDomainUrl == nil {
		var ret string
		return ret
	}
	return *o.OrgDomainUrl
}

// GetOrgDomainUrlOk returns a tuple with the OrgDomainUrl field value if set, nil otherwise
// and a boolean to check if the value has been set.
func (o *UserContext) GetOrgDomainUrlOk() (*string, bool) {
	if o == nil || o.OrgDomainUrl == nil {
		return nil, false
	}
	return o.OrgDomainUrl, true
}

// HasOrgDomainUrl returns a boolean if a field has been set.
func (o *UserContext) HasOrgDomainUrl() bool {
	if o != nil && o.OrgDomainUrl != nil {
		return true
	}

	return false
}

// SetOrgDomainUrl gets a reference to the given string and assigns it to the OrgDomainUrl field.
func (o *UserContext) SetOrgDomainUrl(v string) {
	o.OrgDomainUrl = &v
}

// GetSalesforceInstance returns the SalesforceInstance field value if set, zero value otherwise.
func (o *UserContext) GetSalesforceInstance() string {
	if o == nil || o.SalesforceInstance == nil {
		var ret string
		return ret
	}
	return *o.SalesforceInstance
}

// GetSalesforceInstanceOk returns a tuple with the SalesforceInstance field value if set, nil otherwise
// and a boolean to check if the value has been set.
func (o *UserContext) GetSalesforceInstanceOk() (*string, bool) {
	if o == nil || o.SalesforceInstance == nil {
		return nil, false
	}
	return o.SalesforceInstance, true
}

// HasSalesforceInstance returns a boolean if a field has been set.
func (o *UserContext) HasSalesforceInstance() bool {
	if o != nil && o.SalesforceInstance != nil {
		return true
	}

	return false
}

// SetSalesforceInstance gets a reference to the given string and assigns it to the SalesforceInstance field.
func (o *UserContext) SetSalesforceInstance(v string) {
	o.SalesforceInstance = &v
}

func (o UserContext) MarshalJSON() ([]byte, error) {
	toSerialize := map[string]interface{}{}
	if true {
		toSerialize["orgId"] = o.OrgId
	}
	if true {
		toSerialize["userId"] = o.UserId
	}
	if o.OnBehalfOfUserId != nil {
		toSerialize["onBehalfOfUserId"] = o.OnBehalfOfUserId
	}
	if true {
		toSerialize["username"] = o.Username
	}
	if true {
		toSerialize["salesforceBaseUrl"] = o.SalesforceBaseUrl
	}
	if o.OrgDomainUrl != nil {
		toSerialize["orgDomainUrl"] = o.OrgDomainUrl
	}
	if o.SalesforceInstance != nil {
		toSerialize["salesforceInstance"] = o.SalesforceInstance
	}
	return json.Marshal(toSerialize)
}

type NullableUserContext struct {
	value *UserContext
	isSet bool
}

func (v NullableUserContext) Get() *UserContext {
	return v.value
}

func (v *NullableUserContext) Set(val *UserContext) {
	v.value = val
	v.isSet = true
}

func (v NullableUserContext) IsSet() bool {
	return v.isSet
}

func (v *NullableUserContext) Unset() {
	v.value = nil
	v.isSet = false
}

func NewNullableUserContext(val *UserContext) *NullableUserContext {
	return &NullableUserContext{value: val, isSet: true}
}

func (v NullableUserContext) MarshalJSON() ([]byte, error) {
	return json.Marshal(v.value)
}

func (v *NullableUserContext) UnmarshalJSON(src []byte) error {
	v.isSet = true
	return json.Unmarshal(src, &v.value)
}


