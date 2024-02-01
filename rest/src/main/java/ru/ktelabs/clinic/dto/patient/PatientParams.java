package ru.ktelabs.clinic.dto.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Value
@RequiredArgsConstructor
@Schema(description = "Параметры поиска пациентов")
public class PatientParams {

    @Schema(description = "Фамилия Имя Отчество пациента")
    String name;

    @Schema(description = "E-mail пациента")
    String email;

    @Schema(description = "Телефонный номер пациента")
    String phone;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Schema(description = "Дата рождения пациента")
    LocalDate birthday;

}
