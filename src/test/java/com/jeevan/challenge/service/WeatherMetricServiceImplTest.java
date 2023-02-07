package com.jeevan.challenge.service;

import com.jeevan.challenge.collection.WeatherMetric;
import com.jeevan.challenge.repository.WeatherMetricRepository;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WeatherMetricServiceImplTest {

    @InjectMocks
    WeatherMetricServiceImpl weatherMetricService;

    @Mock
    private WeatherMetricRepository weatherMetricRepository;

    @DisplayName("Data being inserted to DB- Positive Flow")
    @Test
    void save() {
        when(weatherMetricRepository.save(any()).getId()).thenReturn("15");
        String id = weatherMetricService.save(new WeatherMetric());
        assertEquals(15, id);
    }
}