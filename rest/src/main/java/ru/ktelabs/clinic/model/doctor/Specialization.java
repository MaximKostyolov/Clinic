package ru.ktelabs.clinic.model.doctor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Специализация врача")
public enum Specialization {

    DENTIST("Стоматолог", 60),

    SURGEON("Хирург", 60),

    UROLOGIST("Уролог", 30),

    NEUROLOGIST("Невропатолог", 30),

    PSYCHIATRIST("Психиатр", 30),

    OTOLARYNGOLOGIST("Отоларинголог", 30),

    DERMATOLOGIST("Дерматолог", 30),

    CARDIOLOGIST("Кардиолог", 30),

    ENDOCRINOLOGIST("Эндокринолог", 30),

    ONCOLOGIST("Онколог", 60),

    RHEUMATOLOGIST("Ревматолог", 30),

    PEDIATRICIAN("Педиатр", 30),

    PODIATRIST("Ортопед", 60),

    OPHTHALMOLOGIST("Окулист", 30),

    RADIOLOGIST("Рентгенолог", 10),

    GYNECOLOGIST("Гинеколог", 30),

    ALLERGIST("Аллерголог", 30),

    NEUROSURGEON("Нейрохирург", 60),

    PHYSICIAN("Терапевт", 30),

    GASTROENTEROLOGIST("Гастроэнтеролог", 30),

    IMMUNOLOGIST("Иммунолог", 30),

    PULMONOLOGIST("Пульмонолог", 30);

    @Schema(description = "Специализация врача")
    private final String title;

    @Schema(description = "Длительность приема врача в минутах")
    private final Integer duration;

    Specialization(String title, Integer duration) {
        this.title = title;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return getTitle();
    }

}
