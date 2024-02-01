package ru.ktelabs.clinic.controller.comment;

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
import ru.ktelabs.clinic.dto.comment.CommentParams;
import ru.ktelabs.clinic.dto.comment.CommentResponseDto;
import ru.ktelabs.clinic.dto.comment.CommentUpdateDto;
import ru.ktelabs.clinic.dto.comment.NewCommentDto;
import ru.ktelabs.clinic.mapper.comment.CommentMapper;
import ru.ktelabs.clinic.model.comment.Comment;
import ru.ktelabs.clinic.model.comment.Status;
import ru.ktelabs.clinic.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Комментарии",
        description = "Контроллер для работы с комментариями"
)
@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/comments")
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    private static final String HEADER = "Patient-Id";

    @Operation(
            summary = "Добавление нового комментария",
            description = "Конечная точка для добавления нового комментария"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "201",
            description = "Создан новый комментария",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommentResponseDto.class)
            )}
    )})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto create(@RequestHeader(HEADER) Long patientId,
                                     @RequestBody @Valid @Parameter(description = "Данные нового комментария") NewCommentDto newCommentDto,
                                     HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Comment comment = commentService.create(patientId, newCommentDto);
        return commentMapper.toCommentResponseDto(comment);
    }

    @Operation(
            summary = "Обновление комментария",
            description = "Конечная точка для обновления комментария"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Комментарий обновлен",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommentResponseDto.class)
            )}
    )})
    @PatchMapping("/{id}")
    public CommentResponseDto update(@RequestHeader(HEADER) Long patientId,
                                     @PathVariable @Min(0) @Parameter(description = "Id комментария") Long id,
                                     @RequestBody @Valid @Parameter(description = "Обновленный комментарий") CommentUpdateDto commentUpdateDto,
                                     HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Comment updatedComment = commentService.update(patientId, id, commentUpdateDto);
        return commentMapper.toCommentResponseDto(updatedComment);
    }

    @Operation(
            summary = "Публикация или отклонение комментария",
            description = "Конечная точка для публикации или отклонения комментария администратором"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Комментарий проверен администратором",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommentResponseDto.class)
            )}
    )})
    @PatchMapping("/admin/{id}")
    public CommentResponseDto updateFromAdmin(@PathVariable @Min(0) @Parameter(description = "Id комментария") Long id,
                                              @RequestBody @Parameter(description = "Статус комментарий") Status status,
                                              HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Comment updatedComment = commentService.updateFromAdmin(id, status);
        return commentMapper.toCommentResponseDto(updatedComment);
    }

    @Operation(
            summary = "Получить комментарий по Id",
            description = "Конечная точка для получения комментария по Id"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Получен комментарий по Id",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommentResponseDto.class)
            )}
    )})
    @GetMapping("/{id}")
    public CommentResponseDto getById(@PathVariable @Min(0) @Parameter(description = "Id комментария") Long id,
                                      HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        Comment comment = commentService.getById(id);
        return commentMapper.toCommentResponseDto(comment);
    }

    @Operation(
            summary = "Удалить комментарий по Id",
            description = "Конечная точка для удаления комментария Id"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "204",
            description = "Удален врач по Id"
    )})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestHeader(HEADER) Long patientId,
                       @PathVariable @Min(0) @Parameter(description = "Id комментария") Long id,
                       HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        commentService.deleteById(patientId, id);
    }

    @Operation(
            summary = "Получить список комментариев",
            description = "Конечная точка для получения списка комментариев"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "Получен список комментариев",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = CommentResponseDto.class))
            )}
    )})
    @GetMapping
    public List<CommentResponseDto> getAll(
            @RequestParam(required = false) @Parameter(description = "Текст комментария") String text,
            @RequestParam(required = false) @Parameter(description = "Оценка комментария") Integer grade,
            @RequestParam(required = false) @Parameter(description = "Id врача") Long doctorId,
            @RequestParam(required = false) @Parameter(description = "Id пациента") Long patientId,
            @RequestParam(required = false) @Parameter(description = "Статус комментария") Status status,
            @RequestParam(defaultValue = "0") @Parameter(description = "Индекс страницы") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Размер страницы") @Positive int size,
            HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        CommentParams params = new CommentParams(text, grade, doctorId, patientId, status);
        List<Comment> comments = commentService.getAll(params, page, size);
        return commentMapper.toCommentResponseDtos(comments);
    }

}
