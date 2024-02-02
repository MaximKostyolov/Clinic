package ru.ktelabs.clinic.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность удаления временных слотов для врача и поиска записей по id врача и дню")
public class ScheduleDto {

    @NotNull(message = "Id врача не может быть пустым")
    @Min(value = 0, message = "Id врача не может быть меньше 0")
    @Schema(description = "Id врача")
    private Long doctorId;

    @NotEmpty(message = "День, в который нужно удалить временные слоты врача, не могут быть пустыми")
    @Schema(description = "День, в который нужно удалить временные слоты врача")
    private LocalDate day;

}
