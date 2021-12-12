package com.example.demo.integrationtest.controller.WireMock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.integrationtest.controller.IntegrationTestBase;
import com.example.demo.model.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

class TeacherControllerITByJson extends IntegrationTestBase {
  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void shouldSuccessfullyGetTeacherWhenAddJsonPropertyForField() {
    webTestClient
        .get()
        .uri("/teacher/1")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(Teacher.class)
        .value(teacher -> assertThat(teacher.getId()).isEqualTo(1))
        .value(teacher -> assertThat(teacher.getName()).isEqualTo("fakeName"));
  }
}
