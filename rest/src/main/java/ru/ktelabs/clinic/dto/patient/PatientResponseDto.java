package ru.ktelabs.clinic.dto.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Пацинент")
public class PatientResponseDto {

    @Schema(description = "Id пациента")
    private Long id;

    @Schema(description = "UUID пациента")
    private UUID uuid;

    @Schema(description = "Фамилия Имя Отчество пациента")
    private String name;

    @Schema(description = "E-mail пациента")
    private String email;

    @Schema(description = "Телефонный номер пациента")
    private String phone;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Schema(description = "Дата рождения пациента")
    private LocalDate birthday;

    @Schema(description = "Id пациента")
    private String photoUrl;

}
