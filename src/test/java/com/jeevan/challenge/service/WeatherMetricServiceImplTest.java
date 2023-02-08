package com.jeevan.challenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeevan.challenge.collection.Statistics;
import com.jeevan.challenge.collection.WeatherMetric;
import com.jeevan.challenge.repository.WeatherMetricRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeatherMetricServiceImplTest {

    @InjectMocks
    WeatherMetricServiceImpl weatherMetricService;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private WeatherMetricRepository weatherMetricRepositoryMock;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void save() {
        WeatherMetric weatherMetric = new WeatherMetric();
        weatherMetric.setId("15");
        doReturn(weatherMetric).when(weatherMetricRepositoryMock).save(any(WeatherMetric.class));
        String id = weatherMetricService.save(new WeatherMetric());
        assertEquals("15", id);
    }

    @Test
    void getStatistics() {
        List<Statistics> response = getStatisticsList();
        AggregationResults<Statistics> aggregationResults = (AggregationResults<Statistics>) mock(AggregationResults.class);
        when(aggregationResults.getMappedResults()).thenReturn(response);
        doReturn(aggregationResults)
                .when(mongoTemplate)
                .aggregate(Mockito.nullable(Aggregation.class), Mockito.<Class<?>>any(), Mockito.<Class<?>>any());

        ResponseEntity result = weatherMetricService.getStatistics(Optional.of(LocalDateTime.now()), Optional.of(LocalDateTime.now().minusDays(3)), true);

        Statistics[] statistics = mapper.convertValue(result.getBody(), Statistics[].class);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(20, statistics[0].getAverageTemp());
    }

    @Test
    void getStatisticsForSensorId() {
        List<Statistics> response = getStatisticsList();
        AggregationResults<Statistics> aggregationResults = (AggregationResults<Statistics>) mock(AggregationResults.class);
        when(aggregationResults.getMappedResults()).thenReturn(response);
        doReturn(aggregationResults)
                .when(mongoTemplate)
                .aggregate(Mockito.nullable(Aggregation.class), Mockito.<Class<?>>any(), Mockito.<Class<?>>any());

        ResponseEntity result = weatherMetricService.getStatisticsForSensorId(Optional.of(LocalDateTime.now()),
                                            Optional.of(LocalDateTime.now().minusDays(3)), "sensor1");

        Statistics[] statistics = mapper.convertValue(result.getBody(), Statistics[].class);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(20, statistics[0].getAverageTemp());
    }

    private static List<Statistics> getStatisticsList() {
        Statistics statisticsResponse = new Statistics();
        statisticsResponse.setSensorId("sensor1");
        statisticsResponse.setAverageHumidity(10);
        statisticsResponse.setAverageTemp(20);
        return Arrays.asList(statisticsResponse);
    }

}