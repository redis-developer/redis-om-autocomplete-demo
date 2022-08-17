package com.redis.om.autocomplete.repository;

import com.redis.om.autocomplete.domain.Airport;
import com.redis.om.spring.repository.RedisDocumentRepository;

public interface AirportsRepository extends RedisDocumentRepository<Airport, String> {
}
