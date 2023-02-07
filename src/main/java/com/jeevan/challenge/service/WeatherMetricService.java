package com.jeevan.challenge.service;

import com.jeevan.challenge.collection.WeatherMetric;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WeatherMetricService {
    String save(WeatherMetric weatherMetric);

    ResponseEntity getStatistics(Optional<LocalDateTime> startDate, Optional<LocalDateTime> endDate, Boolean humidity);

    ResponseEntity getStatisticsForSensorId(Optional<LocalDateTime> startDateFromRequest, Optional<LocalDateTime>  endDateFromRequest, String sensorId);
}
