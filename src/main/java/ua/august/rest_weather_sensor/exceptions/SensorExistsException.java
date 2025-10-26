package ua.august.rest_weather_sensor.exceptions;

// 4009
public class SensorExistsException extends RuntimeException {
    public SensorExistsException(String message) {
        super(message);
    }
}
