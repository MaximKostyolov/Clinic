package ru.ktelabs.clinic.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность нового комментария")
public class NewCommentDto {

    @NotBlank(message = "Текст комментария не может быть пустым")
    @Size(max = 512, message = "Текст комментария не может быть более 512 символов")
    @Schema(description = "Текст комментария")
    private String text;

    @NotNull(message = "Оценка не может быть пустой")
    @Min(value = 1, message = "Минимальное значение оценки - 1")
    @Max(value = 5, message = "Максимальное значение оценки - 5")
    @Schema(description = "Оценка врача")
    private Integer grade;

    @NotNull(message = "Id врача не может быть пустым")
    @Min(value = 0, message = "Id врача не может быть меньше 0")
    @Schema(description = "Id врача")
    private Long doctorId;

}
