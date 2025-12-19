# weathor-sensor-REST-app
Two-module Spring Boot project consisting of an API service and a data client simulating weather sensors. The client generates random temperature and rainfall values and sends them to the API via a resilient RestTemplate integration with error handling. The API stores measurements through DTO-Entity mapping and links Sensor and Measurement entities.
