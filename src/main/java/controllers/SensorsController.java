package controllers;

import entities.Sensors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repositories.SensorsRepository;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private  final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsController(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }
    //registration(POST)
    @PostMapping("/registration")
    public ResponseEntity<Sensors> registerSensor(@RequestBody Sensors sensor) {
        return ResponseEntity.ok(sensorsRepository.save(sensor));
    }

}
