package com.jeevan.challenge.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "weather_metric")
public class WeatherMetric {

    @Id
    private String id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("timeStamp")
    private LocalDateTime timeStamp;

    @JsonProperty("sensorId")
    @NotBlank(message ="SensorID is required")
    @NotNull(message ="SensorID is required")
    public String sensorId;

    @JsonProperty("temperature")
    public Double temperature;

    @JsonProperty("humidity")
    public Double humidity;

    @JsonProperty("pressure")
    public Double pressure;

    @JsonProperty("wind")
    public Wind wind;

}
