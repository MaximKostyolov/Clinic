package ru.ktelabs.clinic.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность обновления комментария")
public class CommentUpdateDto {

    @Size(max = 512, message = "Текст комментария не может быть более 512 символов")
    @Schema(description = "Текст комментария")
    private String text;

    @Min(value = 1, message = "Минимальное значение оценки - 1")
    @Max(value = 5, message = "Максимальное значение оценки - 5")
    @Schema(description = "Оценка врача")
    private Integer grade;

}
