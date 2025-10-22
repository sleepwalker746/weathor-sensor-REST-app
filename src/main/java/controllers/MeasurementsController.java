package controllers;

import entities.Measurements;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import services.MeasurementsService;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;
    }
    //add(POST)
    //index(GET)
    //rainyDayCount(GET)
//    @GetMapping("/add")
//    public{
//    }

    @GetMapping()
    public List <Measurements> getAllMeasurements() {
        return measurementsService.findAll();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid Measurements measurements,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
        measurementsService.saveMeasurements(measurements);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
