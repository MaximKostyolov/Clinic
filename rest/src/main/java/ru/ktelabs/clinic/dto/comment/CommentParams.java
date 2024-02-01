package ru.ktelabs.clinic.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.ktelabs.clinic.model.comment.Status;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Schema(description = "Параметры поиска комментариев")
public class CommentParams {

    @Schema(description = "Текст комментария")
    String text;

    @Schema(description = "Оценка комментария")
    Integer grade;

    @Schema(description = "Id врача")
    Long doctorId;

    @Schema(description = "Id пациента")
    Long patientId;

    @Schema(description = "Статус комментария")
    Status status;

}
