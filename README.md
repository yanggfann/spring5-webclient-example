# wiremock-example
This repo is used to show how to use wiremock.

## How to test

### Write Unit Test With Mockito

Refer to: https://www.baeldung.com/spring-mocking-webclient

### Write Integration Test With MockWebServer

Refer to: https://www.baeldung.com/spring-mocking-webclient

### Write Integration Test With WireMock

#### What is WireMock?

Mock your APIs for fast, robust and comprehensive testing.

WireMock is a simulator for HTTP-based APIs. Some might consider it a **service virtualization** tool or a **mock server**.

It enables you to **stay productive** when an API you depend on **doesn’t exist** or isn’t complete. It supports the testing of **edge cases and failure modes** that the real API won’t reliably produce. And because it’s fast it can **reduce your build time** from hours down to minutes.

Refer to: http://wiremock.org/docs/getting-started/

- Stubbing: http://wiremock.org/docs/stubbing/
- Json: http://wiremock.org/docs/stubbing/
- Stateful Behaviour: http://wiremock.org/docs/stateful-behaviour/

## Reference Links

### Init Repo

You can initialize a spring boot repo with [Spring initializr](https://start.spring.io/).

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.1/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.1/gradle-plugin/reference/html/#build-image)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Maven Repository

You can find dependencies from [mvnrepository](https://mvnrepository.com/).

If you are confused about what a Maven repository is, you can get an answer from [what-is-a-maven-repository](https://www.cloudrepo.io/articles/what-is-a-maven-repository.html)

### Gradle

Base on the document of Gradle document, the compile have been removed from Gradle 7.0, it recommend to use implementation, here are the details: [gradle document](https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_separation)

### Spring5 Webclient

[WebClient](https://www.baeldung.com/spring-5-webclient) is an interface representing the main entry point for performing web requests.
