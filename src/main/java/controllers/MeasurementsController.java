package controllers;

import entities.Measurements;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import services.MeasurementsService;
import util.MeasurementNotCreatedException;
import util.MeasurementsErrorResponse;

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
    @GetMapping()
    public List <Measurements> getAllMeasurements(Pageable page) {
        return measurementsService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid Measurements measurements,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ValidationException(errorMsg.toString());
        }

        measurementsService.saveMeasurements(measurements);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ExceptionHandler
    private  ResponseEntity<MeasurementsErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementsErrorResponse response = new MeasurementsErrorResponse(
                "Вычисления с такими данными не были созданы!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
