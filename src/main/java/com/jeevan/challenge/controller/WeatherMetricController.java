package com.jeevan.challenge.controller;

import com.jeevan.challenge.collection.WeatherMetric;
import com.jeevan.challenge.service.WeatherMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/weather-metric")
public class WeatherMetricController {

    @Autowired
    private WeatherMetricService weatherMetricService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveWeatherMetric(@Valid @RequestBody WeatherMetric weatherMetric){
        return ResponseEntity.ok().body(weatherMetricService.save(weatherMetric));
    }

    @GetMapping(value = "/sensors/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getStatisticsForSensorId(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime>  endDate,
                                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> startDate,
                                                   @PathVariable("id") @NotBlank String sensorId){
        return weatherMetricService.getStatisticsForSensorId(startDate, endDate, sensorId);
    }

    @GetMapping(value ="/sensors" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getStatistics(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> startDate,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime>  endDate,
                                        @RequestParam(defaultValue = "true", required = false) Boolean humidity){
        return weatherMetricService.getStatistics(startDate, endDate, humidity);
    }
}
