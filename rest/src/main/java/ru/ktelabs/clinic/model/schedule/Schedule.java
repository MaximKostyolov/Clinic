package ru.ktelabs.clinic.model.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.ktelabs.clinic.model.doctor.Doctor;
import ru.ktelabs.clinic.model.patient.Patient;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = true)
    private Patient patient;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    @Column(name = "recording_time", nullable = false)
    private LocalDateTime recordingTime;

    @Column(name = "is_successful", nullable = true)
    private Boolean isSuccessful;

}
