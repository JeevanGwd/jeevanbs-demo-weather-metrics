package com.jeevan.challenge.repository;

import com.jeevan.challenge.collection.WeatherMetric;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherMetricRepository extends MongoRepository<WeatherMetric, String> { }
