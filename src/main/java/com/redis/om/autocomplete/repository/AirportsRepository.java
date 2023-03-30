package com.redis.om.autocomplete.repository;

import java.util.List;

import com.redis.om.autocomplete.domain.Airport;
import com.redis.om.spring.autocomplete.Suggestion;
import com.redis.om.spring.repository.RedisDocumentRepository;
import com.redis.om.spring.repository.query.autocomplete.AutoCompleteOptions;


public interface AirportsRepository extends RedisDocumentRepository<Airport, String> {
  List<Suggestion> autoCompleteName(String query);
  List<Suggestion> autoCompleteName(String query, AutoCompleteOptions options);
}
