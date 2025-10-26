package ua.august.rest_weather_sensor.exceptions;

public class MeasurementNotCreatedException extends RuntimeException {
    public MeasurementNotCreatedException(String message) {
        super(message);
    }
}
