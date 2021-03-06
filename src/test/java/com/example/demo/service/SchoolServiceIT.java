package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.model.School;
import java.io.IOException;
import java.util.Objects;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

class SchoolServiceIT {

  public static MockWebServer mockBackEnd;
  private static String baseUrl;
  private SchoolService schoolService;

  @BeforeAll
  static void setUp() throws IOException {
    mockBackEnd = new MockWebServer();
    mockBackEnd.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    mockBackEnd.shutdown();
  }

  @BeforeEach
  void setup() {
    WebClient webClient = WebClient.builder().build();
    baseUrl = String.format("http://localhost:%s/studentCount", mockBackEnd.getPort());
    schoolService = new SchoolService(webClient, baseUrl);
  }

  @Test
  public void shouldSuccessWhenCallSchoolSuccess_withJupiter() {
    String fakeBody2 = """
        {
            "studentCount": 2
        }
        """;
    mockBackEnd.enqueue(new MockResponse().setHeader("Content-Type", "application/json")
        .setChunkedBody(fakeBody2, 2).setResponseCode(200));

    School schoolResponse = schoolService.getSchool().block();
    assertTrue(Objects.nonNull(schoolResponse));
    Assertions.assertEquals(2, schoolResponse.getStudentCount());
  }

  @Test
  public void shouldSuccessWhenCallSchoolSuccess_withReactorTest() {
    String fakeBody2 = """
        {
            "studentCount": 2
        }
        """;
    mockBackEnd.enqueue(new MockResponse().setHeader("Content-Type", "application/json")
        .setChunkedBody(fakeBody2, 2).setResponseCode(200));

    School school = new School(2);
    StepVerifier.create(schoolService.getSchool()).expectNext(school).verifyComplete();
  }

  @Test
  public void shouldThrowExceptionWhenCallSchoolReturn4xx_withJupiter() {
    int badRequestCode = HttpStatus.BAD_REQUEST.value();
    mockBackEnd.enqueue(new MockResponse().setResponseCode(badRequestCode));

    WebClientResponseException exception =
        Assertions.assertThrows(
            WebClientResponseException.class, () -> schoolService.getSchool().block());
    Assertions.assertEquals(
        String.format("400 Bad Request from GET %s", baseUrl), exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionWhenCallSchoolReturn4xx_withReactorTest() {
    int badRequestCode = HttpStatus.BAD_REQUEST.value();
    mockBackEnd.enqueue(new MockResponse().setResponseCode(badRequestCode));

    StepVerifier.create(schoolService.getSchool())
        .expectErrorMessage(String.format("400 Bad Request from GET %s", baseUrl)).verify();
  }

  @Test
  public void shouldThrowExceptionWhenCallSchoolReturn5xx() {
    int internalServerErrorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    mockBackEnd.enqueue(new MockResponse().setResponseCode(internalServerErrorCode));

    WebClientResponseException exception =
        Assertions.assertThrows(
            WebClientResponseException.class, () -> schoolService.getSchool().block());
    Assertions.assertEquals(
        String.format("500 Internal Server Error from GET %s", baseUrl), exception.getMessage());
  }
}
