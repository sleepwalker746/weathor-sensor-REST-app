package ua.august.rest_weather_sensor.exceptions;

public record FieldValidationError (String field, String message) {
}
