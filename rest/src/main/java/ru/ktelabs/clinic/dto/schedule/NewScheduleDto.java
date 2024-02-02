package ru.ktelabs.clinic.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность новых временных слотов для врача")
public class NewScheduleDto {

    @NotNull(message = "Id врача не может быть пустым")
    @Min(value = 0, message = "Id врача не может быть меньше 0")
    @Schema(description = "Id врача")
    private Long doctorId;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @NotEmpty(message = "Рабочие дни врача не могут быть пустыми")
    @Schema(description = "Рабочие дни врача")
    private List<LocalDate> workDays;

}
