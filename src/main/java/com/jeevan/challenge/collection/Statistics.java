package com.jeevan.challenge.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Statistics {

    @Id
    private String sensorId;
    private double averageTemp;
    private double minTemp;
    private double maxTemp;
    private double averageHumidity;
    private double maxWindSpeed;
}
