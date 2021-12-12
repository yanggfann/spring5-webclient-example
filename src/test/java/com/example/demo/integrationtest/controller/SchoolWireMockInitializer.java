package com.example.demo.integrationtest.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

public class SchoolWireMockInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
    WireMockServer schoolWireMockServer =
        new WireMockServer(
            new WireMockConfiguration()
                .dynamicPort()
                .usingFilesUnderClasspath("mock-server/school/"));
    schoolWireMockServer.start();
    schoolWireMockServer.resetScenarios();

    configurableApplicationContext
        .getBeanFactory()
        .registerSingleton("schoolWireMockServer", schoolWireMockServer);

    configurableApplicationContext.addApplicationListener(
        applicationEvent -> {
          if (applicationEvent instanceof ContextClosedEvent) {
            schoolWireMockServer.stop();
          }
        });

    TestPropertyValues.of(
            "school.base.url=http://localhost:" + schoolWireMockServer.port(),
            "school.studentcount.endpoint=/studentCount","school.teacher.endpoint=/teacher/")
        .applyTo(configurableApplicationContext);
  }
}
