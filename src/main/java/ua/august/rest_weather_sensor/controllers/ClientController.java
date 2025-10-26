package ua.august.rest_weather_sensor.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.august.rest_weather_sensor.runner.DataClientRunner;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final DataClientRunner dataClientRunner;

    @Autowired
    public ClientController(DataClientRunner dataClientRunner) {
        this.dataClientRunner = dataClientRunner;
    }
    @Operation(summary = "Test client launch", description = """
            Executes a chain of actions:
            
            1. Registers a sensor with a specified name (if it is not already registered in the database);
            2. Sends 1000 random measurements of air temperature and number of rainy days;
            3. Receives a list of all measurements and outputs their quantity to the log.
            
            Used to test the functionality of the REST API server.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client has successfully completed the task, measurements have been sent."),
            @ApiResponse(responseCode = "500", description = "Internal server error processing request")
    })
    @PostMapping("/run")
    public ResponseEntity<String> runClient(@RequestParam String sensorName) {
        dataClientRunner.execute(sensorName);
        return ResponseEntity.ok("Client executed for sensor" + sensorName);
    }
}
