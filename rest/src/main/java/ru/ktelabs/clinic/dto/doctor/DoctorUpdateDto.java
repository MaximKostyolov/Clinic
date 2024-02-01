package ru.ktelabs.clinic.dto.doctor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.ktelabs.clinic.model.doctor.Specialization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность обновления данных врача")
public class DoctorUpdateDto {

    @Size(max = 100, message = "Имя врача не может быть более 100 символов")
    @Pattern(regexp = "^[а-яА-Я-\\s]*$", message = "Имя врача cодержит недопустимые символы")
    private String name;

    @Size(max = 1000, message = "Описание врача не может быть более 1000 символов")
    @Pattern(regexp = "^[0-9a-zA-Zа-яёЁА-Я-@#$.,?%^&+=!\"'«»\\s]*$",
            message = "Описание врача cодержит недопустимые символы")
    @Schema(description = "Информация о враче")
    private String description;

    @Schema(description = "Специализация врача")
    private Specialization specialization;

    @Size(max = 250, message = "Ссылка на фотографию не может быть более 250 символов")
    @Pattern(regexp = "(http(s)?:\\/\\/.)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)",
            message = "Ссылка на фотографию cодержит недопустимые символы")
    @Schema(description = "Ссылка на фотографию врача")
    private String photoUrl;

}
