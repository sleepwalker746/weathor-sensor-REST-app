package controllers;

import entities.Sensors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repositories.SensorsRepository;
import services.SensorsService;

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
    public ResponseEntity<Sensors> registerSensor(@RequestBody Sensors sensor) {
        Sensors sensors = sensorsService.register(sensor);
        return ResponseEntity.ok(sensors);
    }
}
