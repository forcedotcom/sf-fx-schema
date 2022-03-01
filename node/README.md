# Node Javascript/Typescript languages: CloudEvents OpenAPI Schema for Salesforce Functions invocation

Provides generated `src/*.ts` and hand-written `src/contextUtil.ts` to encode/decode Salesforce Function Invocation
context and extra info.

See ../schema.json for OpenApi schema that is used to generate the typescript classes using the openapi-generator
toolset.

Note this is *not* a Java project - the pom.xml in this directory is only used to run `npm` to build and test
tests during a toplevel project build (see ../README.md) and run openapi-generator during a
`codegen` profile invocation.

