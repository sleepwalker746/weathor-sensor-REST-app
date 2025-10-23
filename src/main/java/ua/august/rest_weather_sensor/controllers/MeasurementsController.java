package ua.august.rest_weather_sensor.controllers;

import org.modelmapper.ModelMapper;
import ua.august.rest_weather_sensor.dto.MeasurementsDTO;
import ua.august.rest_weather_sensor.entities.Measurements;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.august.rest_weather_sensor.services.MeasurementsService;
import util.MeasurementNotCreatedException;
import util.MeasurementsErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
    }

    //index(GET)
    @GetMapping()
    public List <Measurements> getAllMeasurements(Pageable page) {
        return measurementsService.findAll();
    }

    //rainyDayCount(GET)
    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Long> getRainyDaysCount() {
        Long count = measurementsService.findRainingMeasurements();
        return ResponseEntity.ok(count);
    }

    //add(POST)
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementsDTO measurementsDTO,
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

        measurementsService.saveMeasurements(measurementsDTO);
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

    private Measurements convertToEntity(MeasurementsDTO measurementsDTO) { return modelMapper.map(measurementsDTO, Measurements.class); }
    private MeasurementsDTO convertToDTO(Measurements measurements) { return modelMapper.map(measurements, MeasurementsDTO.class); }
}
