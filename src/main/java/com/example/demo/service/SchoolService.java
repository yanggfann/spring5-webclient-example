package com.example.demo.service;

import com.example.demo.model.School;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SchoolService {

  private final WebClient webClient;

  private final String endPoint;

  public SchoolService(WebClient webClient, @Value("${school.studentcount.endpoint}") String endPoint) {
    this.webClient = webClient;
    this.endPoint = endPoint;
  }

  public Mono<School> getSchool() {
    return webClient
        .get()
        .uri(endPoint)
        .retrieve()
        .bodyToMono(School.class);
  }
}
