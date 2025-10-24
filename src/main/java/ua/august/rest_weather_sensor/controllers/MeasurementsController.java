package ua.august.rest_weather_sensor.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import ua.august.rest_weather_sensor.dto.MeasurementsDTO;
import ua.august.rest_weather_sensor.entities.Measurements;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;

    }

    //index(GET)
    @Operation(summary = "Получить все показатели", description = "Возвращает страницу из 10 показателей")
    @GetMapping()
    public Page<Measurements> getAllMeasurements(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return measurementsService.findAll(page, size);
    }

    //rainyDayCount(GET)
    @Operation(summary = "Количество дождливых дней", description = "Возвращает количество дождливых дней")
    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Long> getRainyDaysCount() {
        Long count = measurementsService.findRainingMeasurements();
        return ResponseEntity.ok(count);
    }

    //add(POST)
    @Operation(
            summary = "Добавить измерение",
            description = "Сохраняет нове показатели температуры воздух и информацию о дожде от определённого сенсора."
    )
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Показатели успешно добавлены"),
            @ApiResponse(responseCode = "400", description = "Ошибка: некоректная информация!"),
            @ApiResponse(responseCode = "404", description = "Сенсор с таким именем не найден!")
    })

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
    private ResponseEntity<MeasurementsErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementsErrorResponse response = new MeasurementsErrorResponse(
                "Вычисления с такими данными не были созданы!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
