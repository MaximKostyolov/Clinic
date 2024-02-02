package ru.ktelabs.clinic.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Временной слот расписания врачей")
public class ScheduleResponseDto {

    @Schema(description = "Id временного слота")
    private Long id;

    @Schema(description = "Id врача")
    private Long doctorId;

    @Schema(description = "Id пациента. Заполняется в случае записи пациента")
    private Long patientId;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    @Schema(description = "Время записи временного слота")
    private LocalDateTime recordingTime;

    @Schema(description = "Успешность временного слота. Заполняется после приема (true - прием состоялся; " +
            "false - прием не состоялся")
    private Boolean isSuccessful;

}
