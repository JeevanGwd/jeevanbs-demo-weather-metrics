package com.jeevan.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeevan.challenge.collection.WeatherMetric;
import com.jeevan.challenge.repository.WeatherMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
public class ChallengeApplication implements CommandLineRunner {
	@Autowired
	private WeatherMetricRepository weatherMetricRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) {
		String fileName = "src/main/resources/dbdata/test_data.json";
		File file = new File(fileName);
		List<WeatherMetric> observationDocuments = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()));) {
			String line;
			ObjectMapper mapper = new ObjectMapper();
			while ((line = br.readLine()) != null) {
				WeatherMetric weatherMetric = mapper.readValue(line, WeatherMetric.class);
				observationDocuments.add(weatherMetric);
			}
		} catch (IOException ex) {
			ex.getMessage();
		}
		weatherMetricRepository.deleteAll();
		weatherMetricRepository.saveAll(observationDocuments);
	}
}
