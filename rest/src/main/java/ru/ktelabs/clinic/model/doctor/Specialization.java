package ru.ktelabs.clinic.model.doctor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlEnumValue;
import lombok.Getter;

@Getter
@Schema(description = "Специализация врача")
public enum Specialization {

    @XmlEnumValue(value = "dentist")
    DENTIST("Стоматолог", 60),

    @XmlEnumValue(value = "surgeon")
    SURGEON("Хирург", 60),

    @XmlEnumValue(value = "urologist")
    UROLOGIST("Уролог", 30),

    @XmlEnumValue(value = "neurologist")
    NEUROLOGIST("Невропатолог", 30),

    @XmlEnumValue(value = "psychiatrist")
    PSYCHIATRIST("Психиатр", 30),

    @XmlEnumValue(value = "otolaryngologist")
    OTOLARYNGOLOGIST("Отоларинголог", 30),

    @XmlEnumValue(value = "dermatologist")
    DERMATOLOGIST("Дерматолог", 30),

    @XmlEnumValue(value = "cardiologist")
    CARDIOLOGIST("Кардиолог", 30),

    @XmlEnumValue(value = "endocrinologist")
    ENDOCRINOLOGIST("Эндокринолог", 30),

    @XmlEnumValue(value = "oncologist")
    ONCOLOGIST("Онколог", 60),

    @XmlEnumValue(value = "rheumatologist")
    RHEUMATOLOGIST("Ревматолог", 30),

    @XmlEnumValue(value = "pediatrician")
    PEDIATRICIAN("Педиатр", 30),

    @XmlEnumValue(value = "podiatrist")
    PODIATRIST("Ортопед", 60),

    @XmlEnumValue(value = "ophthalmologist")
    OPHTHALMOLOGIST("Окулист", 30),

    @XmlEnumValue(value = "radiologist")
    RADIOLOGIST("Рентгенолог", 10),

    @XmlEnumValue(value = "gynecologist")
    GYNECOLOGIST("Гинеколог", 30),

    @XmlEnumValue(value = "allergist")
    ALLERGIST("Аллерголог", 30),

    @XmlEnumValue(value = "neurosurgeon")
    NEUROSURGEON("Нейрохирург", 60),

    @XmlEnumValue(value = "physician")
    PHYSICIAN("Терапевт", 30),

    @XmlEnumValue(value = "gastroenterologist")
    GASTROENTEROLOGIST("Гастроэнтеролог", 30),

    @XmlEnumValue(value = "immunologist")
    IMMUNOLOGIST("Иммунолог", 30),

    @XmlEnumValue(value = "pulmonologist")
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
