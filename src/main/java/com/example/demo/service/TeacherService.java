package com.example.demo.service;

import com.example.demo.model.Teacher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TeacherService {

  private final WebClient webClient;

  private final String endPoint;

  public TeacherService(WebClient webClient, @Value("${school.teacher.endpoint}") String endPoint) {
    this.webClient = webClient;
    this.endPoint = endPoint;
  }

  public Mono<Teacher> getTeacher(String id) {
    return webClient
        .get()
        .uri(endPoint + id)
        .retrieve()
        .bodyToMono(Teacher.class);
  }
}
