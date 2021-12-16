# Spring5 Webclient

Refer to: https://www.baeldung.com/spring-5-webclient

## What Is the WebClient?

WebClient is a reactive web client introduced in Spring 5. Simply put, WebClient is an interface representing the main entry point for performing web requests.

It was created as part of the Spring Web Reactive module, and will be replacing the classic RestTemplate in these scenarios. In addition, the new client is a reactive, non-blocking solution that works over the HTTP/1.1 protocol.

It's important to note that even though it is, in fact, a non-blocking client and it belongs to the spring-webflux library, the solution offers support for both synchronous and asynchronous operations, making it suitable also for applications running on a Servlet Stack.

## Creating a WebClient Instance
For example: `com.example.demo.config.WebClientConfig`.

The most advanced one is building a client by using the `DefaultWebClientBuilder` class, which allows full customization.

The default HTTP timeouts of `30` seconds are too slow for our needs, to customize this behavior, we can create an HttpClient instance and configure our WebClient to use it.

### Why the default HTTP timeout is 30 seconds?

> A ChannelOption allows to configure a ChannelConfig in a type-safe way. Which ChannelOption is supported depends on the actual implementation of ChannelConfig and may depend on the nature of the transport it belongs to.

The `DEFAULT_CONNECT_TIMEOUT` of `DefaultChannelConfig` is 30 seconds.

## Preparing a Request – Define the Method

Calling its shortcut methods such as get, post, and delete.

## Getting a Response

For example: `com.example.demo.service.SchoolService`.

The `retrieve` method is the shortest path to fetching a body directly. 

It's important to pay attention to the `ResponseSpec.bodyToMono` method, which will throw a WebClientException if the status code is 4xx (client error) or 5xx (server error).

## Working with the WebTestClient

The WebTestClient is the main entry point for testing WebFlux server endpoints. It has a very similar API to the WebClient, and it delegates most of the work to an internal WebClient instance focusing mainly on providing a test context. The `DefaultWebTestClient` class is a single interface implementation.

The client for testing can be bound to a real server or work with specific controllers or functions.

## How to test

### Write Unit Test With Mockito

Use Mockito to mimic the behavior of WebClient. Refer to: https://www.baeldung.com/spring-mocking-webclient

Mockito is the most common mocking library for Java. It's good at providing pre-defined responses to method calls, but things get challenging when mocking fluent APIs. This is because in a fluent API, a lot of objects pass between the calling code and the mock.

We need to provide a different mock object for each call in the chain, with four different when/thenReturn calls required. This is verbose and cumbersome. It also requires us to know the implementation details of how exactly our service uses WebClient, making this a brittle way of testing.

For example: `com.example.demo.service.SchoolServiceUnitTest`.

### Write Integration Test With MockWebServer

Refer to: https://www.baeldung.com/spring-mocking-webclient

MockWebServer, built by the Square team, is a small web server that can receive and respond to HTTP requests.

Interacting with MockWebServer from our test cases allows our code to use real HTTP calls to a local endpoint. We get the benefit of testing the intended HTTP interactions and none of the challenges of mocking a complex fluent client.

Using MockWebServer is recommended by the Spring Team for writing integration tests.

For example: `com.example.demo.integrationtest.controller.MockWebServer.SchoolControllerIT`.

### Write Integration Test With WireMock

#### What is WireMock?

Mock your APIs for fast, robust and comprehensive testing.

WireMock is a simulator for HTTP-based APIs. Some might consider it a **service virtualization** tool or a **mock server**.

It enables you to **stay productive** when an API you depend on **doesn’t exist** or isn’t complete. It supports the testing of **edge cases and failure modes** that the real API won’t reliably produce. And because it’s fast it can **reduce your build time** from hours down to minutes.

WireMock Guide Tutorials: http://wiremock.org/docs/getting-started/

- Stubbing: http://wiremock.org/docs/stubbing/
    For example: `com.example.demo.integrationtest.controller.WireMock.TeacherControllerITByStubFor`
- Json: http://wiremock.org/docs/stubbing/
  For example: `com.example.demo.integrationtest.controller.WireMock.TeacherControllerITByJson`
- Stateful Behaviour: http://wiremock.org/docs/stateful-behaviour/
  For example: `com.example.demo.integrationtest.controller.WireMock.TeacherControllerITByStatefulBehaviour`
  Basically stateful behavior is always used when we call the same endpoint multiple times in a method.

Both `stubFor` and `json` file can mock a response for webclient. But `stubFor` is better than `json` file, because we can directly know what is the response for the current test method.

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
