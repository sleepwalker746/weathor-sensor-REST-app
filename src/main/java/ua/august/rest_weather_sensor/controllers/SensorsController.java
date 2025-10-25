package ua.august.rest_weather_sensor.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.august.rest_weather_sensor.entities.Sensors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ua.august.rest_weather_sensor.services.SensorsService;
import util.SensorExistsException;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private  final SensorsService sensorsService;

    @Autowired
    public SensorsController(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }
    //registration(POST)
    @PostMapping("/registration")
    @Operation(summary = "Registering of a new sensor", description = "Registering a new sensor in DB")
    public ResponseEntity<Sensors> registerSensor(@RequestBody Sensors sensor) {
        Sensors sensors = sensorsService.register(sensor);
        return ResponseEntity.ok(sensors);
    }
    
    @ExceptionHandler(SensorExistsException.class)
    public ResponseEntity<String> handleSensorExists(SensorExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
