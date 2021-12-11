package com.example.demo.service;

import com.example.demo.model.School;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SchoolService {
  private final WebClient webClient;

  public Mono<School> getSchool() {
    return webClient.get().uri("/count").retrieve().bodyToMono(School.class);
  }
}
