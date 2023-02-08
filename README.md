# Jeevanbs-demo-weather-metrics
Project to fetch weather stats

Challenge:
Building a Service that receives weather data from various sensors that report metrics such as temperature, humidity, wind speed, etc. 
Display the statistics on the call of the API.

##Software
Java 11
Maven
SpringBoot with Embedded tomcat
MongoDB

##Implementation
Service accepts new weather data through API request.
And displays the weather stats on call of the API. Weather statistics can be fetched for a single sensor or for all the sensors.

##Running Application locally

Prerequisites:
1. Java 11
2. IDE(IntelliJ/Eclipse)
3. MongoDb

Steps for running the project:
1. Checkout the project from - https://github.com/JeevanGwd/jeevanbs-demo-weather-metrics.git
2. Import the project to any IDE.
3. Navigate to the project root and build the project - mvn install
4. Update the MongoDb details in Application.properties file.
5. Start the springboot application from - ChallengeApplication.java
6. Open the swagger UI screen using the following URL - "http://localhost:8091/swagger-ui/#/weather-metric-controller". 

    Note: OnStart of Application initial data is uploaded from a file (main\resources\dbdata\test_data.json);

API's Available:
1. POST - /weather-metric/save
   - To persist the new weather to the DB.
2. GET - /weather-metric/sensors
   - To fetch statistics of all the sensors for a specified time range or if time range is not specified statistics of past 3 days is fetched.
   - humidity stats can be omitted if required by setting the param to false.
3. GET - /weather-metric/sensors/{id}
   - To fetch stats of a single sensor for a specified time range or if time range is not specified statistics of past 3 days is fetched.
   - id param is mandatory