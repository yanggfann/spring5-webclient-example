package com.example.demo.integrationtest.controller.MockWebServer;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.controller.SchoolController;
import com.example.demo.model.School;
import com.example.demo.service.SchoolService;
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

class SchoolControllerIT {
  public static MockWebServer mockBackEnd;
  private static String baseUrl;
  private SchoolService schoolService;
  private SchoolController schoolController;

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
    baseUrl = String.format("http://localhost:%s/count", mockBackEnd.getPort());
    schoolService = new SchoolService(webClient, baseUrl);
    schoolController = new SchoolController(schoolService);
  }

  @Test
  public void shouldSuccessWhenCallSchoolSuccess() {
    String fakeBody2 = """
        {
            "studentCount": 2
        }
        """;
    mockBackEnd.enqueue(new MockResponse().setHeader("Content-Type", "application/json")
        .setChunkedBody(fakeBody2, 2).setResponseCode(200));

    School schoolResponse = schoolController.getSchool().block();
    assertTrue(Objects.nonNull(schoolResponse));
    Assertions.assertEquals(2, schoolResponse.getStudentCount());
  }

  @Test
  public void shouldThrowExceptionWhenCallSchoolReturn4xx() {
    int badRequestCode = HttpStatus.BAD_REQUEST.value();
    mockBackEnd.enqueue(new MockResponse().setResponseCode(badRequestCode));

    WebClientResponseException exception =
        Assertions.assertThrows(
            WebClientResponseException.class, () -> schoolController.getSchool().block());
    Assertions.assertEquals(
        String.format("400 Bad Request from GET %s", baseUrl), exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionWhenCallSchoolReturn5xx() {
    int internalServerErrorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    mockBackEnd.enqueue(new MockResponse().setResponseCode(internalServerErrorCode));

    WebClientResponseException exception =
        Assertions.assertThrows(
            WebClientResponseException.class, () -> schoolController.getSchool().block());
    Assertions.assertEquals(
        String.format("500 Internal Server Error from GET %s", baseUrl), exception.getMessage());
  }
}
