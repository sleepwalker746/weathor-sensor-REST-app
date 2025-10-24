package ua.august.rest_weather_sensor.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Operation(summary = "Запуск тестового клиента", description = """
            Выполняет последовательность действий:
            
            1. Регистрирует сенсор с заданным именем (если он ещё не зарегистрирован в БД);
            2. Отправляет 1000 случайных измерений температуры воздуха и кол-ва дождливых дней;
            3. Получает список всех измерений и выводит их количество в консоль.
            
            Используется для проверки работоспособности REST API сервера.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Клиент успешно завершил работу, измерения отправлены"),
            @ApiResponse(responseCode = "500", description = "Ошибка при выполнении клиента или отправки данных")
    })
    @PostMapping("/run")
    public String runClient() throws Exception {
        dataClientRunner.execute();
        return "Клиент выполнен!";
    }
}
