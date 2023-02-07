package com.jeevan.challenge.service;

import com.jeevan.challenge.collection.WeatherMetric;
import com.jeevan.challenge.repository.WeatherMetricRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WeatherMetricServiceImplTest {

    @InjectMocks
    WeatherMetricServiceImpl weatherMetricService;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private WeatherMetricRepository weatherMetricRepositoryMock;

    @Test
    void save() {
        WeatherMetric weatherMetric = new WeatherMetric();
        weatherMetric.setId("15");
        doReturn(weatherMetric).when(weatherMetricRepositoryMock).save(any(WeatherMetric.class));
        String id = weatherMetricService.save(new WeatherMetric());
        assertEquals("15", id);
    }
}