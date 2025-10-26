package ua.august.rest_weather_sensor.exceptions;

public enum ErrorCodes {

    // Sensor
    SENSOR_EXISTS(4009),
    SENSOR_NOT_FOUND(4004),

    // Measurement
    MEASUREMENT_NOT_CREATED(4091),
    MEASUREMENT_NOT_FOUND(4044),

    // Validation
    VALIDATION_FAILED(4005),

    // Internal
    INTERNAL_ERROR(5000);

    private final int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }
}


