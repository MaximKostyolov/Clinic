package ru.ktelabs.clinic.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(
        title = "Система записи на прием к врачу",
        description = "Система позволяет записаться на прием к определенному врачу с учетом свободного времени для " +
                "записи и отзывов других пациентов",
        version = "1.0.0"
))
public class OpenApiConfig {
}
