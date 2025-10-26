package ua.august.rest_weather_sensor.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import ua.august.rest_weather_sensor.entities.Sensors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ua.august.rest_weather_sensor.services.SensorsService;

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
    @ApiResponse(responseCode = "409", description = "Sensor already exists!")
    public ResponseEntity<Sensors> registerSensor(@RequestBody Sensors sensor) {
        Sensors sensors = sensorsService.register(sensor);
        return ResponseEntity.ok(sensors);
    }

}
