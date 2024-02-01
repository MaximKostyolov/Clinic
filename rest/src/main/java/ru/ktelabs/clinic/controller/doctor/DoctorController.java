package ru.ktelabs.clinic.controller.doctor;

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
import ru.ktelabs.clinic.dto.doctor.DoctorParams;
import ru.ktelabs.clinic.dto.doctor.DoctorResponseDto;
import ru.ktelabs.clinic.dto.doctor.DoctorUpdateDto;
import ru.ktelabs.clinic.dto.doctor.NewDoctorDto;
import ru.ktelabs.clinic.mapper.doctor.DoctorMapper;
import ru.ktelabs.clinic.model.doctor.Doctor;
import ru.ktelabs.clinic.model.doctor.Specialization;
import ru.ktelabs.clinic.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Врачи",
        description = "Контроллер для работы с врачами"
)
@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    private final DoctorMapper doctorMapper;

    @Operation(
            summary = "Добавление нового врача",
            description = "Конечная точка для добавления нового врача"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "201",
            description = "Добавлен новый врач",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DoctorResponseDto.class)
            )}
    )})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponseDto create(
            @RequestBody @Valid @Parameter(description = "Данные нового врача") NewDoctorDto newDoctorDto,
            HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Doctor doctor = doctorService.create(newDoctorDto);
        return doctorMapper.toDoctorResponseDto(doctor);
    }

    @Operation(
            summary = "Обновление данных врача",
            description = "Конечная точка для обновления данных врача"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Данные врача обновлены",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DoctorResponseDto.class)
            )}
    )})
    @PatchMapping("/{id}")
    public DoctorResponseDto update(@PathVariable @Min(0) @Parameter(description = "Id врача") Long id,
               @RequestBody @Valid @Parameter(description = "Обновленные данные врача") DoctorUpdateDto doctorUpdateDto,
               HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Doctor updatedDoctor = doctorService.update(id, doctorUpdateDto);
        return doctorMapper.toDoctorResponseDto(updatedDoctor);
    }

    @Operation(
            summary = "Получить врача по Id",
            description = "Конечная точка для получения врача по Id"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Получен врач по Id",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DoctorResponseDto.class)
            )}
    )})
    @GetMapping("/{id}")
    public DoctorResponseDto getById(@PathVariable @Min(0) @Parameter(description = "Id врача") Long id,
                                     HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Doctor doctor = doctorService.getById(id);
        return doctorMapper.toDoctorResponseDto(doctor);
    }

    @Operation(
            summary = "Удалить врача по Id",
            description = "Конечная точка для удаления врача Id"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "204",
            description = "Удален врач по Id"
    )})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(0) @Parameter(description = "Id врача") Long id,
                       HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        doctorService.deleteById(id);
    }

    @Operation(
            summary = "Получить список врачей",
            description = "Конечная точка для получения списка врачей"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Получен список врачей",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = DoctorResponseDto.class))
            )}
    )})
    @GetMapping
    public List<DoctorResponseDto> getAll(
            @RequestParam(required = false) @Parameter(description = "Имя врача") String name,
            @RequestParam(required = false) @Parameter(description = "Описание врача") String description,
            @RequestParam(required = false) @Parameter(description = "Специализация врача") Specialization specialization,
            @RequestParam(required = false) @Parameter(description = "Средняя оценка врача") Double rating,
            @RequestParam(defaultValue = "0") @Parameter(description = "Индекс страницы") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Размер страницы") @Positive int size,
            HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        DoctorParams params = new DoctorParams(name, description, specialization, rating);
        List<Doctor> doctors = doctorService.getAll(params, page, size);
        return doctorMapper.toDoctorResponseDtos(doctors);
    }

}
