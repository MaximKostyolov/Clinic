package ru.ktelabs.clinic.dto.doctor;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.ktelabs.clinic.model.doctor.Specialization;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Schema(description = "Параметры поиска врачей")
public class DoctorParams {

    @Schema(description = "Имя врача")
    String name;

    @Schema(description = "Информация о враче")
    String description;

    @Schema(description = "Специализация врача")
    Specialization specialization;

    @Schema(description = "Рейтинг врача")
    Double rating;

}
