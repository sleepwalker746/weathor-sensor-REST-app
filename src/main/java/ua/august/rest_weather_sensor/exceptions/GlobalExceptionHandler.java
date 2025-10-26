package ua.august.rest_weather_sensor.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ProblemDetail createProblemDetail(HttpStatus status, String title, String detail, ErrorCodes code) {
        ProblemDetail pd = ProblemDetail.forStatus(status);
        pd.setTitle(title);
        pd.setDetail(detail);
        pd.setType(URI.create("https://example.com/problems/" + code.value()));
        pd.setProperty("code", code.value());
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    // Sensors
    @ExceptionHandler(SensorExistsException.class)
    public ProblemDetail handleSensorExists(SensorExistsException e) {
        logger.warn("Sensor exists: {}", e.getMessage());
        return createProblemDetail(HttpStatus.CONFLICT, "Sensor already exists", e.getMessage(), ErrorCodes.SENSOR_EXISTS);
    }

    @ExceptionHandler(SensorNotFoundException.class)
    public ProblemDetail handleSensorNotFound(SensorNotFoundException e) {
        logger.warn("Sensor not found: {}", e.getMessage());
        return createProblemDetail(HttpStatus.NOT_FOUND, "Sensor not found", e.getMessage(), ErrorCodes.SENSOR_NOT_FOUND);
    }

    // Measurements
    @ExceptionHandler(MeasurementNotCreatedException.class)
    public ProblemDetail handleMeasurementNotCreated(MeasurementNotCreatedException e) {
        logger.error("Measurement not created: {}", e.getMessage());
        return createProblemDetail(HttpStatus.BAD_REQUEST, "Measurement not created", e.getMessage(), ErrorCodes.MEASUREMENT_NOT_CREATED);
    }

    @ExceptionHandler(MeasurementNotFoundException.class)
    public ProblemDetail handleMeasurementNotFound(MeasurementNotFoundException e) {
        logger.warn("Measurement not found: {}", e.getMessage());
        return createProblemDetail(HttpStatus.NOT_FOUND, "Measurement not found", e.getMessage(), ErrorCodes.MEASUREMENT_NOT_FOUND);
    }

    // Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException e) {
        var fieldError = e.getBindingResult().getFieldError();
        String detail = fieldError != null ? fieldError.getDefaultMessage() : "Validation failed";

        return createProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                detail,
                ErrorCodes.VALIDATION_FAILED
        );
    }

    // Unknown errors
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception e) {
        logger.error("Unexpected error", e);
        return createProblemDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                "An unexpected error occurred. Please contact support.",
                ErrorCodes.INTERNAL_ERROR
        );
    }
}
