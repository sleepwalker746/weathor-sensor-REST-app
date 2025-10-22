package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repositories.SensorsRepository;

@RestController
@RequestMapping("/measurements")
public class SensorsController {

    private  final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsController(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }
    //registration(POST)
}
