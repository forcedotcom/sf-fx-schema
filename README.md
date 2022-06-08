# CloudEvents OpenAPI Schema for Salesforce Functions invocation

Salesforce Functions are invoked via an HTTP Binary-encoded CloudEvent with extensions and a customer-provided payload body.

This project specifies an OpenAPI schema for the containing CloudEvent and extension objects so that callers in multiple languages can all agree on expected structure and types.

## Structure

* LICENSE - BSD-3-Clause license for this project and its generated source code
* pom.xml - Maven project file to build this multi-module project, requires Maven 3.6+ and Java 11+
* schema.json - OpenAPI 3.x schema specification for *Context model objects to be used for language-specific code generation.
* go/ - Go language subproject
* java/ - Java language subproject
* node/ - Node.js (JavaScript/Typescript) language subproject

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

In the notes below we will use `$rver` to stand in for the release version number, for example 1.0.1.

First start a release branch that will have commits for non-SNAPSHOT version numbers:
```
$ git checkout -b release-$rver
```

Update all the maven project pom.xml files to the release version and check that appropriate updates were made.
If correct, commit the revised version.
```
$ mvn versions:set -DnewVersion=$rver
$ vi node/package.json   # set "version": to the new $rver value, save and exit.
$ git status
$ git diff
$ git add -A
$ git commit -m "Prepare for release $rver"
```

Make sure you can rebuild locally without deploying.  There should be *no* changes to source code after a clean
rebuild with code generation:
```
$ mvn -Pcodegen,cibuild clean package
$ git diff    # should be empty
```

### Publish github release and tag for go usage

To publish for use by go clients, you just need to publish a tag with a `go/` prefix as well as a "normal" version tag:
```
$ git push origin release-$rver
$ git tag v$rver
$ git tag go/v$rver
$ git push origin --tags
```

Then on the github tags page, https://github.com/forcedotcom/sf-fx-schema/tags, click the right-most "..." 
for the newly pushed v$rver tag, choose Create Release, fill in the title like v$rver, fill in the changelog
description and click Publish release.

### Publish to NPM

To publish to NPM, you will need a valid account at npmjs.com (https://www.npmjs.com/signup) that has been added
to the `salesforce` org with publish privileges.
In addition, that account *must* have 2-Factor authentication enabled for authorization and publishing.

Do all the npm install and publish steps in this project's `node/` subdirectory:
```
$ cd node
$ head package.json   # double-check that "version" is set to $rver
```

Make sure that local installation works first:
```
$ npm install $(pwd)
```

Do a dry-run publish second and make sure that completes without error.  You may be prompted for your 6-digit 2FA
Authenticator code.
```
$ npm publish --access public --dry-run
```

If successful, do a full public publish.  Again, you may be prompted for your 2FA code:
```
$ npm publish --access public --dry-run
```


### Publish to Maven Central

To publish to Maven Central, you must have the prerequisites satisfied from the Sonatype OSSRH page
https://central.sonatype.org/publish/publish-guide/, starting with "Create your JIRA account".  You 
will not need to create a New Project ticket since this project has already been published.
In addition, you may need to file an OSSRH ticket to get access to publish to the `com.salesforce.functions`
project and wait for approval.

You must also have GPG installed, available on your path, and the first key pair in your keyring set
to your USERNAME@salesforce.com, which must be the same email used to register for Jira.  And you 
must have distributed your public key to one of the keyservers supported by Maven Central (recommended:
use the web interface for pasting in your public key rather than using CLI tools).  Details
at: https://central.sonatype.org/publish/requirements/gpg/

In your `~/.m2/settings.xml` file in the `<servers>...</servers>` block you will need to add a new
block with `ossrh` as its ID and your Jira ID/Password.  Details and examples starting at 
`<settings>...` on this page: 
https://central.sonatype.org/publish/publish-maven/#distribution-management-and-authentication
```
settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>your-jira-id</username>
      <password>your-jira-pwd</password>
    </server>
  </servers>
</settings>
````

Also in `~/.m2/settings.xml` under `<profiles>...` you will need an `ossrh` profile with the
`gpg.*` properties described in the GPG Signed Components section of:
https://central.sonatype.org/publish/publish-maven/#gpg-signed-components
(note you do not need this profile to be activeByDefault):
```
    <profile>
      <id>ossrh</id>
      <properties>
        <gpg.executable>/usr/local/bin/gpg</gpg.executable>
        <gpg.passphrase>the_gpg_pass_phrase</gpg.passphrase>
      </properties>
    </profile>
```

Once all those steps have been completed (Including publisher access through JIRA) and validated, you should be able to publish.  Note
that any 4xx error from oss.sonatype.org indicates a missing authentication, authorization,
or signing step above.
```
$ mvn -Possrh,cibuild,-sfdc clean deploy
```

Once that deploy is successful, you must log in to https://oss.sonatype.org/, review the staging repository
https://oss.sonatype.org/#stagingRepositories following the instructions at
https://central.sonatype.org/publish/release/#locate-and-examine-your-staging-repository. If all the auto-
reviews were successful, the Activity tab should show success and the staging repo will be Closed.  Use
the Artifacts tab to browse the content of the release and if everything reviews OK, check the [X] checkbox
next to the staging repo in the top panel and click the `Release` button.  Alternatively on the command line:
```
$ mvn -Possrh,cibuild,-sfdc nexus-staging:release
...
Waiting for operation to complete...
......

[INFO] Released
...
[INFO] BUILD SUCCESS

```

It will then be sync-d within approx 20 minutes to central where it can be browsed:
https://repo1.maven.org/maven2/com/salesforce/functions/

And within approx 2 hours to replicas and the search site:
https://search.maven.org/search?q=a:sf-fx-schema


### Prep for next release

Once all the bits have been published successfully, prepare for the next release by updating
to the next SNAPSHOT version, pushing that to the release branch:
```
$ nextver=X.Y.Z   # example 1.0.2
$ mvn versions:set -DnewVersion=${nextver}-SNAPSHOT
$ git status
$ git diff
$ git add -A
$ git commit -m "Prepare for next release $nextver"
$ git push origin release-$rver
```

Submit that branch as a PR for review, get a positive review, and merge to `main`
for the next release.

