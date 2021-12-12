package com.example.demo.integrationtest.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SchoolWireMockInitializer.class})
@ActiveProfiles("it")
public class IntegrationTestBase {}
