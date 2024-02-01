package ru.ktelabs.clinic.dto.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность нового пацинента")
public class NewPatientDto {

    @NotBlank(message = "Имя пацинента не может быть пустым")
    @Size(max = 100, message = "Имя пацинента не может быть более 100 символов")
    @Pattern(regexp = "^[а-яА-Я-\\s]*$", message = "Имя пацинента cодержит недопустимые символы")
    @Schema(description = "Фамилия Имя Отчество пациента")
    private String name;

    @NotBlank(message = "E-mail не может быть пустым")
    @Size(max = 20, message = "Электронный адресс не может быть более 20 символов")
    @Email(message = "Электронный адресс указан не правильно")
    @Schema(description = "E-mail пациента")
    private String email;

    @NotBlank(message = "Номер телефона не может быть пустым")
    @Size(max = 20, message = "Номер телефона не может быть более 11 символов")
    @Pattern(regexp = "^8\\d{10}$", message = "Номер телефона cодержит недопустимые символы. Введите номер телефона, " +
            "начиная с 8, без пробелов и прочих символов")
    @Schema(description = "Телефонный номер пациента")
    private String phone;

    @NotBlank(message = "Дата рождения не может быть пустой")
    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Past(message = "Дата рождения должна быть ранее чем текущая дата")
    @Schema(description = "Дата рождения пациента")
    private LocalDate birthday;

    @NotBlank(message = "Ссылка на фотографию не может быть пустой")
    @Size(max = 250, message = "Ссылка на фотографию не может быть более 250 символов")
    @Pattern(regexp = "(http(s)?:\\/\\/.)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)",
            message = "Ссылка на фотографию cодержит недопустимые символы")
    @Schema(description = "Ссылка на фотографию пациента")
    private String photoUrl;

}
