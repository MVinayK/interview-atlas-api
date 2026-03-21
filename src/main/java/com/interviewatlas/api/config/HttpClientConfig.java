package com.interviewatlas.api.config;

import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class HttpClientConfig {

  @Bean
  RestClient.Builder restClientBuilder() {
    HttpClient httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .build();

    return RestClient.builder()
        .requestFactory(new JdkClientHttpRequestFactory(httpClient));
  }
}
