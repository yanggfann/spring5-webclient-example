package com.example.demo.integrationtest.controller.WireMock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.integrationtest.controller.IntegrationTestBase;
import com.example.demo.model.School;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@TestMethodOrder(OrderAnnotation.class)
class SchoolControllerITByStatefulBehaviour extends IntegrationTestBase {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  @Order(1)
  public void shouldSuccessfullyGetSchoolWhenAddJsonPropertyForField() {
    webTestClient
        .get()
        .uri("/school")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(School.class)
        .value(school -> assertThat(school.getStudentCount()).isEqualTo(5));
  }

  @Test
  @Order(2)
  public void shouldThrowExceptionWhenBodyIsNotJson() {
    webTestClient
        .get()
        .uri("/school")
        .exchange()
        .expectStatus()
        .is5xxServerError();
  }
}
