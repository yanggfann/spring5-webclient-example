package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.demo.model.School;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class SchoolServiceUnitTest {

  private SchoolService schoolService;
  @Mock
  private WebClient webClientMock;
  @Mock
  private RequestHeadersSpec requestHeadersMock;
  @Mock
  private RequestHeadersUriSpec requestHeadersUriMock;
  @Mock
  private ResponseSpec responseMock;

  @BeforeEach
  void setup() {
    schoolService = new SchoolService(webClientMock, "/count");
  }

  @Test
  void shouldReturnSchoolSuccessfully() {
    int studentCount = 3;
    School school = new School(studentCount);
    when(webClientMock.get())
        .thenReturn(requestHeadersUriMock);
    when(requestHeadersUriMock.uri("/count"))
        .thenReturn(requestHeadersMock);
    when(requestHeadersMock.retrieve())
        .thenReturn(responseMock);
    when(responseMock.bodyToMono(School.class))
        .thenReturn(Mono.just(school));

    School actualSchool = schoolService.getSchool().block();

    assertTrue(Objects.nonNull(actualSchool));
    assertEquals(studentCount, actualSchool.getStudentCount());
  }

}
