package com.jeevan.challenge.service;

import com.jeevan.challenge.collection.Statistics;
import com.jeevan.challenge.collection.WeatherMetric;
import com.jeevan.challenge.repository.WeatherMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherMetricServiceImpl implements WeatherMetricService {

    public static final String TIME_STAMP = "timeStamp";
    public static final String AVERAGE_TEMP = "averageTemp";
    public static final String TEMPERATURE = "temperature";
    public static final String SENSOR_ID = "sensorId";
    public static final String MIN_TEMP = "minTemp";
    public static final String MAX_TEMP = "maxTemp";
    public static final String WIND_SPEED = "wind.speed";
    public static final String MAX_WIND_SPEED = "maxWindSpeed";
    public static final String AVERAGE_HUMIDITY = "averageHumidity";
    public static final String HUMIDITY = "humidity";
    @Autowired
    private WeatherMetricRepository weatherMetricRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(WeatherMetric weatherMetric) {
        return weatherMetricRepository.save(weatherMetric).getId();
    }

    @Override
    public ResponseEntity getStatistics(Optional<LocalDateTime> startDateFromRequest, Optional<LocalDateTime>  endDateFromRequest,
                                        Boolean humidity) {

        LocalDateTime startDate = getStartDate(startDateFromRequest);
        LocalDateTime endDate = getEndDate(endDateFromRequest);

        //Match Operation for Getting the data from defined StartDate and End date
        MatchOperation timeStampMatch = Aggregation.match(Criteria.where(TIME_STAMP).gte(startDate).lt(endDate));

        GroupOperation groupOperation = getGroupOperation(humidity);

        //Aggregation result
        Aggregation aggregation = Aggregation.newAggregation(timeStampMatch, groupOperation);

        List<Statistics> aggregate = mongoTemplate.aggregate(aggregation, WeatherMetric.class, Statistics.class).getMappedResults();

        return ResponseEntity.ok().body(aggregate);
    }

    @Override
    public ResponseEntity getStatisticsForSensorId(Optional<LocalDateTime> startDateFromRequest, Optional<LocalDateTime>  endDateFromRequest,
                                                        String sensorId) {
        LocalDateTime startDate = getStartDate(startDateFromRequest);
        LocalDateTime endDate = getEndDate(endDateFromRequest);

        //Match Operation for Getting the data from defined StartDate and End date and based on sensorId
        MatchOperation matchOperation = Aggregation.match(Criteria.where(TIME_STAMP).gte(startDate).lt(endDate).and(SENSOR_ID).is(sensorId));

        //Group Operation for Grouping based on SensorId and calculating Statistics
        GroupOperation groupOperation = getGroupOperation(true);

        //Aggregation result
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation);

        List<Statistics> aggregate = mongoTemplate.aggregate(aggregation, WeatherMetric.class, Statistics.class).getMappedResults();
        return ResponseEntity.ok().body(aggregate);
    }

    private static GroupOperation getGroupOperation(Boolean humidity) {
        //Group Operation for Grouping based on SensorId and calculating Statistics
        GroupOperation groupOperation = Aggregation.group(SENSOR_ID).avg(TEMPERATURE).as(AVERAGE_TEMP)
                .min(TEMPERATURE).as(MIN_TEMP)
                .max(TEMPERATURE).as(MAX_TEMP)
                .max(WIND_SPEED).as(MAX_WIND_SPEED);

        //Option to exclude or include Humidity in Statistics
        if (humidity) {
            groupOperation = groupOperation.avg(HUMIDITY).as(AVERAGE_HUMIDITY);
        }
        return groupOperation;
    }

    private LocalDateTime getStartDate(Optional<LocalDateTime> startDateFromRequest) {
        LocalDateTime startDate = startDateFromRequest.orElseGet(()-> LocalDateTime.now().minusDays(3));
        return startDate;
    }

    private LocalDateTime getEndDate(Optional<LocalDateTime> endDateFromRequest) {
        return endDateFromRequest.orElseGet(() -> LocalDateTime.now());
    }
}
