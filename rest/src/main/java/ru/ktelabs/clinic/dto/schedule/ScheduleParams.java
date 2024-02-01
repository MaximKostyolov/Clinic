package ru.ktelabs.clinic.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import ru.ktelabs.clinic.model.doctor.Specialization;

@Value
@RequiredArgsConstructor
@Schema(description = "Параметры поиска временных слотов")
public class ScheduleParams {

    @Schema(description = "Id врача")
    Long doctorId;

    @Schema(description = "Id пациента")
    Long patientId;

    @Schema(description = "Доступность временного слота")
    Boolean available;

    @Schema(description = "Успешность временного слота")
    Boolean isSuccessful;

    @Schema(description = "Специализация врача")
    Specialization specialization;

}
