package com.interviewatlas.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class InterviewAtlasApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(InterviewAtlasApiApplication.class, args);
  }
}
