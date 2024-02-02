package ru.ktelabs.clinic.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import ru.ktelabs.clinic.model.comment.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Комментарий")
public class CommentResponseDto {

    @Schema(description = "Id комментария")
    private Long id;

    @Schema(description = "Текст комментария")
    private String text;

    @Schema(description = "Оценка врача")
    private Integer grade;

    @Schema(description = "Id врача")
    private Long doctorId;

    @Schema(description = "Id пациента")
    private Long patientId;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    @Schema(description = "Время создания")
    private LocalDateTime created;

    @Schema(description = "Статус комментария")
    private Status status;

}
