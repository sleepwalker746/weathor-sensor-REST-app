package ua.august.rest_weather_sensor.exceptions;

// 4004
public class SensorNotFoundException extends RuntimeException {
    public SensorNotFoundException(String message) {
        super(message);
    }
}
