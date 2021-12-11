package com.example.demo.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
  public static final int CONNECT_TIMEOUT_MILLIS = 3_000;
  public static final int READ_TIMEOUT_MILLIS = 5_000;

  private final String schoolBaseUrl;

  public WebClientConfig(@Value("${school.base.url}") String schoolBaseUrl) {
    this.schoolBaseUrl = schoolBaseUrl;
  }

  @Bean
  WebClient webClient() {
    HttpClient httpClient =
        HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MILLIS)
            .doOnConnected(
                conn ->
                    conn.addHandler(
                        new ReadTimeoutHandler(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)));
    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .baseUrl(schoolBaseUrl)
        .build();
  }
}
