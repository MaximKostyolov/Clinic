package ru.ktelabs.clinic.controller.schedule;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ktelabs.clinic.dto.schedule.NewScheduleDto;
import ru.ktelabs.clinic.dto.schedule.ScheduleDeleteDto;
import ru.ktelabs.clinic.dto.schedule.ScheduleParams;
import ru.ktelabs.clinic.dto.schedule.ScheduleResponseDto;
import ru.ktelabs.clinic.mapper.schedule.ScheduleMapper;
import ru.ktelabs.clinic.model.doctor.Specialization;
import ru.ktelabs.clinic.model.schedule.Schedule;
import ru.ktelabs.clinic.service.schedule.ScheduleService;

import java.util.List;

@Tag(
        name = "Расписание врачей",
        description = "Контроллер для работы с расписанием врачей и записью на прием"
)
@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    private final ScheduleMapper scheduleMapper;

    @Operation(
            summary = "Добавление новых временных слотов врачу",
            description = "Конечная точка для добавления новых временных слотов врачу"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "201",
            description = "Созданы новые временные слоты",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = ScheduleResponseDto.class))
            )}
    )})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<ScheduleResponseDto> create(@RequestBody @Valid @Parameter(description = "Данные для добавления новых " +
            "временных слотов") NewScheduleDto newScheduleDto, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        List<Schedule> schedules = scheduleService.create(newScheduleDto);
        return scheduleMapper.toScheduleResponseDtos(schedules);
    }

    @Operation(
            summary = "Добавление или удаление записи от пациента к временному слоту",
            description = "Конечная точка для добавления или удаления записи от пациента к временному слоту"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Запись от пациента к временному слоту добавлена или удалена",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ScheduleResponseDto.class)
            )}
    )})
    @PatchMapping("/{id}")
    public ScheduleResponseDto update(@PathVariable @Min(0) @Parameter(description = "Id временного слота") Long id,
                                      @RequestParam @Min(0) @Parameter(description = "Id пациента") Long patientId,
                                      HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Schedule updatedSchedule = scheduleService.update(id, patientId);
        return scheduleMapper.toScheduleResponseDto(updatedSchedule);
    }

    @Operation(
            summary = "Добавление флага успешности к временному слоту если прием состоялся",
            description = "Конечная точка для добавления флага успешности к временному слоту"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Флаг успешности к временному слоту добавлен",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ScheduleResponseDto.class)
            )}
    )})
    @PatchMapping("/successful/{id}")
    public ScheduleResponseDto updateSuccessful(@PathVariable @Min(0) @Parameter(description = "Id временного слота") Long id,
                                      @RequestParam @Min(0) @Parameter(description = "Id пациента") Long patientId,
                                      @RequestParam @Parameter(description = "Успешность") Boolean isSuccessful,
                                      HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Schedule updatedSchedule = scheduleService.update(id, patientId, isSuccessful);
        return scheduleMapper.toScheduleResponseDto(updatedSchedule);
    }

    @Operation(
            summary = "Получить временной слот по Id",
            description = "Конечная точка для получения временного слота по Id"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Получен временной слот по Id",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ScheduleResponseDto.class)
            )}
    )})
    @GetMapping("/{id}")
    public ScheduleResponseDto getById(@PathVariable @Min(0) @Parameter(description = "Id временного слота") Long id,
                                       HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Schedule schedule = scheduleService.getById(id);
        return scheduleMapper.toScheduleResponseDto(schedule);
    }

    @Operation(
            summary = "Удалить временной слот врача по Id",
            description = "Конечная точка для удаления временного слота врача по Id"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "204",
            description = "Удален временной слот врача по Id"
    )})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(0) @Parameter(description = "Id временного слота врача") Long id,
                       HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        scheduleService.deleteById(id);
    }

    @Operation(
            summary = "Удалить временные слоты врача в определенный день",
            description = "Конечная точка для удаления временных слотов врача в определенный день"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "204",
            description = "Удалены временные слоты врача в определенный день"
    )})
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Valid @Parameter(description = "Данные для удаления временных слотов врача")
                       ScheduleDeleteDto scheduleDeleteDto, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        scheduleService.delete(scheduleDeleteDto);
    }

    @Operation(
            summary = "Получить список временных слотов",
            description = "Конечная точка для получения списка временных слотов"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Получен список временных слотов",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = ScheduleResponseDto.class))
            )}
    )})
    @GetMapping
    public List<ScheduleResponseDto> getAll(
            @RequestParam(required = false) @Min(0) @Parameter(description = "Id врача") Long doctorId,
            @RequestParam(required = false) @Min(0) @Parameter(description = "Id пациента") Long patientId,
            @RequestParam(required = false) @Parameter(description = "Доступность временного слота") Boolean available,
            @RequestParam(required = false) @Parameter(description = "Успешность временного слота") Boolean isSuccessful,
            @RequestParam(required = false) @Parameter(description = "Специализация врача") Specialization specialization,
            @RequestParam(defaultValue = "0") @Parameter(description = "Индекс страницы") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Размер страницы") @Positive int size,
            HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        ScheduleParams params = new ScheduleParams(doctorId, patientId, available, isSuccessful, specialization);
        List<Schedule> schedules = scheduleService.getAll(params, page, size);
        return scheduleMapper.toScheduleResponseDtos(schedules);
    }

}
