package ru.ktelabs.clinic.controller.patient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import ru.ktelabs.clinic.dto.patient.NewPatientDto;
import ru.ktelabs.clinic.dto.patient.PatientResponseDto;
import ru.ktelabs.clinic.mapper.patient.PatientMapper;
import ru.ktelabs.clinic.service.patient.PatientService;
import ru.ktelabs.clinic.dto.patient.PatientParams;
import ru.ktelabs.clinic.dto.patient.PatientUpdateDto;
import ru.ktelabs.clinic.model.patient.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(
        name = "Пациенты",
        description = "Контроллер для работы с пациентами"
)
@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/patients")
public class PatientController {

    private final PatientService patientService;

    private final PatientMapper patientMapper;

    @Operation(
            summary = "Добавление нового пациента",
            description = "Конечная точка для добавления нового пациента"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "201",
            description = "Добавлен новый пациент",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PatientResponseDto.class)
            )}
    )})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponseDto create(
            @RequestBody @Valid @Parameter(description = "Данные нового пациента") NewPatientDto newPatientDto,
            HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Patient patient = patientService.create(newPatientDto);
        return patientMapper.toPatientResponseDto(patient);
    }

    @Operation(
            summary = "Обновление данных пациента",
            description = "Конечная точка для обновления данных пациента"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Данные пациента обновлены",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PatientResponseDto.class)
            )}
    )})
    @PatchMapping("/{id}")
    public PatientResponseDto update(@PathVariable @Min(0) @Parameter(description = "Id пациента") Long id,
            @RequestBody @Valid @Parameter(description = "Обновленные данные пациента") PatientUpdateDto patientUpdateDto,
            HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Patient updatedPatient = patientService.update(id, patientUpdateDto);
        return patientMapper.toPatientResponseDto(updatedPatient);
    }

    @Operation(
            summary = "Получить пациента по Id",
            description = "Конечная точка для получения пациента по Id"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Получен пациент по Id",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PatientResponseDto.class)
            )}
    )})
    @GetMapping("/{id}")
    public PatientResponseDto getById(@PathVariable @Min(0) @Parameter(description = "Id пациента") Long id,
                                     HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Patient patient = patientService.getById(id);
        return patientMapper.toPatientResponseDto(patient);
    }

    @Operation(
            summary = "Удалить пациента по Id",
            description = "Конечная точка для удаления пациента Id"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "204",
            description = "Удален врач по Id"
    )})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(0) @Parameter(description = "Id пациента") Long id,
                       HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        patientService.deleteById(id);
    }

    @Operation(
            summary = "Получить список пациентов",
            description = "Конечная точка для получения списка пациентов"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Получен список пациентов",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = PatientResponseDto.class))
            )}
    )})
    @GetMapping
    public List<PatientResponseDto> getAll(
            @RequestParam(required = false) @Parameter(description = "Имя пациента") String name,
            @RequestParam(required = false) @Parameter(description = "E-mail пациента") String email,
            @RequestParam(required = false) @Parameter(description = "Телефонный номер пациента") String phone,
            @RequestParam(required = false) @Parameter(description = "Дата рождения пациента") LocalDate birthday,
            @RequestParam(defaultValue = "0") @Parameter(description = "Индекс страницы") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Размер страницы") @Positive int size,
            HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        PatientParams params = new PatientParams(name, email, phone, birthday);
        List<Patient> doctors = patientService.getAll(params, page, size);
        return patientMapper.toPatientResponseDtos(doctors);
    }

}
