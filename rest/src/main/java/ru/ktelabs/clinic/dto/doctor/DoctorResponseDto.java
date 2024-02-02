package ru.ktelabs.clinic.dto.doctor;


import io.swagger.v3.oas.annotations.media.Schema;
import ru.ktelabs.clinic.dto.comment.CommentResponseDto;
import ru.ktelabs.clinic.model.doctor.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Врач")
public class DoctorResponseDto {

    @Schema(description = "Id врача")
    private Long id;

    @Schema(description = "UUID врача")
    private String uuid;

    @Schema(description = "Имя врача")
    private String name;

    @Schema(description = "Информация о враче")
    private String description;

    @Schema(description = "Специализация врача")
    private Specialization specialization;

    @Schema(description = "Cсылка на фотографию врача")
    private String photoUrl;

    @Schema(description = "Рейтинг врача")
    private Double rating;

    @Schema(description = "Комментарии о враче")
    private List<CommentResponseDto> comments;

}
