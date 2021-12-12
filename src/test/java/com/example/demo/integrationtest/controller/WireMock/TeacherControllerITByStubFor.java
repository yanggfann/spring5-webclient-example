package com.example.demo.integrationtest.controller.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.model.Teacher;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeacherControllerITByStubFor {
  @Autowired
  private WebTestClient webTestClient;

  private static WireMockServer wireMockServer;

  @DynamicPropertySource
  static void overrideWebClientBaseUrl(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("school.base.url", wireMockServer::baseUrl);
    dynamicPropertyRegistry.add("school.teacher.endpoint", () -> "/teacher/");
  }

  @BeforeAll
  static void startWireMock() {
    wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
        .dynamicPort());

    wireMockServer.start();
  }

  @AfterAll
  static void stopWireMock() {
    wireMockServer.stop();
  }

  @BeforeEach
  void clearWireMock() {
    wireMockServer.resetAll();
  }

  @Test
  public void shouldSuccessfullyGetTeacherWhenReturnWithStub() {
    wireMockServer.stubFor(get(urlEqualTo("/teacher/2"))
        .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value())
            .withHeader("Content-Type", "application/json")
            .withBody("""
                {
                  "id": 2,
                  "name": "Teacher2"
                }
                """)));
    webTestClient
        .get()
        .uri("/teacher/2")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(Teacher.class)
        .value(teacher -> assertThat(teacher.getId()).isEqualTo(2))
        .value(teacher -> assertThat(teacher.getName()).isEqualTo("Teacher2"));
  }
}
