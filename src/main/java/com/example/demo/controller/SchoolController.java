package com.example.demo.controller;

import com.example.demo.model.School;
import com.example.demo.service.SchoolService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class SchoolController {
  private final SchoolService schoolService;

  @GetMapping("/count")
  public Mono<School> getStudentCount() {
    return schoolService.getSchool();
  }
}