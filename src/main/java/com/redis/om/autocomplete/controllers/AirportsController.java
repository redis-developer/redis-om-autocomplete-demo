package com.redis.om.autocomplete.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import com.redis.om.spring.autocomplete.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.om.autocomplete.repository.AirportsRepository;
import com.redis.om.spring.repository.query.autocomplete.AutoCompleteOptions;

@CrossOrigin( //
    methods = { POST, GET, OPTIONS, DELETE, PATCH }, //
    maxAge = 3600, //
    allowedHeaders = { //
        "x-requested-with", "origin", "content-type", "accept", "accept-patch" //
    }, //
    origins = "*" //
)
@RestController
@RequestMapping("/airports")
public class AirportsController {

  @Autowired
  private AirportsRepository repository;

  @GetMapping("/search/{q}")
  public List<Suggestion> query(@PathVariable("q") String query) {
    List<Suggestion> suggestions = repository //
        .autoCompleteName(query, AutoCompleteOptions.get().withPayload());
    return suggestions;
  }

}
