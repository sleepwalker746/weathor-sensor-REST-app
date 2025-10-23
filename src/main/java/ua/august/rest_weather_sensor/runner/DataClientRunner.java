package ua.august.rest_weather_sensor.runner;

import org.springframework.web.client.HttpServerErrorException;
import ua.august.rest_weather_sensor.dto.MeasurementsDTO;
import ua.august.rest_weather_sensor.dto.SensorsRegistrationDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ua.august.rest_weather_sensor.services.RestTemplateService;

import java.util.List;

@Component
public class DataClientRunner implements CommandLineRunner {

    private final RestTemplateService restTemplateService;
    private static final String SENSOR_NAME = "TestSensor-Client";

    public DataClientRunner(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("\n<<Пробный запуск клиента MARK-1>>");

        System.out.println("\n=== 1. Регистрация сенсора ===");

        try {
            SensorsRegistrationDTO registeredSensor = restTemplateService.registerSensor(SENSOR_NAME);
            System.out.println("\nСенсор успешно зарегестрирован под именем: " + registeredSensor);
        } catch (HttpServerErrorException e) {
            System.out.println("Сенсор с таким именем уже существует. Продолжаем отправку данных...");
        }


        System.out.println("\n=== 2. Отправка 1000 случайных измерений ===");

        restTemplateService.sendRandomMeasurements(SENSOR_NAME);

        System.out.println("\n=== 3. Получение всех измерений ===");

        ResponseEntity<List<MeasurementsDTO>> response = restTemplateService.getAllMeasurements();
        List<MeasurementsDTO> measurementsDTOs = response.getBody();
        if (measurementsDTOs != null) {
            System.out.println("Количество измерений ровно: " + measurementsDTOs.size());
        } else {
            System.out.println("Ошибка: Получен пустой список измерений");
        }

        System.out.println("\n<<Клиент MARK-1 завершил работу>>");

    }
}
