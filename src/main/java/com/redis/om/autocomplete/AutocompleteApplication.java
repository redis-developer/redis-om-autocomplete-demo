package com.redis.om.autocomplete;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.common.io.Files;
import com.redis.om.autocomplete.domain.Airport;
import com.redis.om.autocomplete.repository.AirportsRepository;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableRedisDocumentRepositories
@EnableSwagger2
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
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

  public static void main(String[] args) {
    SpringApplication.run(AutocompleteApplication.class, args);
  }

}
