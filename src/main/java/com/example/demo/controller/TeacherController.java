package com.example.demo.controller;

import com.example.demo.model.Teacher;
import com.example.demo.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class TeacherController {
  private final TeacherService teacherService;

  @GetMapping("/teacher/{id}")
  public Mono<Teacher> getTeacher(@PathVariable String id) {
    return teacherService.getTeacher(id);
  }
}
