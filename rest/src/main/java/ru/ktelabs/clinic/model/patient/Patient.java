package ru.ktelabs.clinic.model.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "patients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", type = IdentifierGenerator.class)
    @Column(name = "uuid", columnDefinition = "varchar(36)")
    private String uuid;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "email", length = 20, nullable = false)
    private String email;

    @Column(name = "phone", length = 11, nullable = false)
    private String phone;

    @Column(name = "birthday", nullable = false)
    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;

    @Column(name = "photo_url", length = 250, nullable = false)
    private String photoUrl;

}
