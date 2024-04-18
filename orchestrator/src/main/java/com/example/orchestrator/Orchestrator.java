package com.example.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Orchestrator {

  public static void main(String... args) {
    SpringApplication.run(Orchestrator.class, args);
  }

}