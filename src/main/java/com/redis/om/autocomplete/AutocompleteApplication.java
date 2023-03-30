package com.redis.om.autocomplete;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.common.io.Files;
import com.redis.om.autocomplete.domain.Airport;
import com.redis.om.autocomplete.repository.AirportsRepository;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
@EnableRedisDocumentRepositories
public class AutocompleteApplication {

  @Bean
  public CommandLineRunner loadData(AirportsRepository repository, @Value("classpath:/data/airport_codes.csv") File dataFile) throws IOException {
    return args -> {
      repository.deleteAll();
      List<Airport> data = Files //
          .readLines(dataFile, StandardCharsets.UTF_8) //
          .stream() //
          .map(l -> l.split(",")) //
          .map(ar -> Airport.of(ar[0], ar[1], ar[2])) //
          .collect(Collectors.toList());
      repository.saveAll(data);
    };
  }

  @Bean
  public OpenAPI apiInfo() {
    return new OpenAPI().info(new Info().title("Redis OM Auto-complete").version("1.0.0"));
  }

  @Bean
  public GroupedOpenApi httpApi() {
    return GroupedOpenApi.builder()
        .group("http")
        .pathsToMatch("/**")
        .build();
  }

  public static void main(String[] args) {
    SpringApplication.run(AutocompleteApplication.class, args);
  }

}
