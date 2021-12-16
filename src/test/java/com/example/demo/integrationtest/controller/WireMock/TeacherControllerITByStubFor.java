package com.example.demo.integrationtest.controller.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.integrationtest.controller.IntegrationTestBase;
import com.example.demo.model.Teacher;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

class TeacherControllerITByStubFor extends IntegrationTestBase {
  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private WireMockServer wireMockServer;

  @BeforeEach
  void setUp() {
    configureFor("localhost", wireMockServer.port());
  }

  @Test
  public void shouldSuccessfullyGetTeacherWhenReturnWithStub() {
    stubFor(get(urlEqualTo("/teacher/2"))
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
