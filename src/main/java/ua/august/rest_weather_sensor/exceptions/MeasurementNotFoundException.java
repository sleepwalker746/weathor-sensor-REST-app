package ua.august.rest_weather_sensor.exceptions;

public class MeasurementNotFoundException extends RuntimeException {
    public MeasurementNotFoundException(String message) {
        super(message);
    }
}
