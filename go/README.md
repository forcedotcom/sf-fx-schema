# Go language: CloudEvents OpenAPI Schema for Salesforce Functions invocation

Provides generated model_*.go and hand-written context_*.go to encode/decode Salesforce Function Invocation
context and extra info.

See ../schema.json for OpenApi schema that is used to generate the model_*.go classes using the openapi-generator
toolset.

Note this is *not* a Java project - the pom.xml in this directory is only used to run tests during a toplevel
project build (see ../README.md) and run openapi-generator during a `codegen` profile invocation.
