package ru.ktelabs.clinic.model.doctor;

import jakarta.persistence.*;
import ru.ktelabs.clinic.model.comment.Comment;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.IdentifierGenerator;

import java.util.List;

@Entity
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", type = IdentifierGenerator.class)
    @Column(name = "uuid", columnDefinition = "varchar(36)")
    private String uuid;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @Column(name = "specialization", nullable = false)
    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @Column(name = "photo_url", length = 250, nullable = false)
    private String photoUrl;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

}
