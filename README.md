# CloudEvents OpenAPI Schema for Salesforce Functions invocation

Salesforce Functions are invoked via an HTTP Binary-encoded CloudEvent with extensions and a customer-provided payload body.

This project specifies an OpenAPI schema for the containing CloudEvent and extension objects so that callers in multiple languages can all agree on expected structure and types.

## Structure

LICENSE - BSD-3-Clause license for this project and its generated source code
pom.xml - Maven project file to build this multi-module project, requires Maven 3.6+ and Java 11+
schema.json - OpenAPI 3.x schema specification for *Context model objects to be used for language-specific code generation.
go/ - Go language subproject
java/ - Java language subproject
node/ - Node.js (JavaScript/Typescript) language subproject

## Prerequisites

The following programming languages must be installed in your path to be able to run the build, optional code generation, and tests:

* Java/OpenJDK 11 or higher
* Maven 3.6.x or higher
* go 1.16 or higher
* node 16+ LTS and the associated version of npm

## Building Generated Libraries

By default, maven will compile, test, and package the existing (already generated & committed) code in this project:

```bash
$ mvn clean package
...
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
...
```

There are two optional maven profiles that can be added to run the openapi code generation and javadoc/source packaging steps:

```bash
$ mvn -Pcodegen,cibuild clean package
...
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
...
```

## Publishing Generated Libraries

TODO

* How to create a Github Release (for use by golang, maven release build, etc)
* How to publish to Maven Central
* How to publish to NPM
