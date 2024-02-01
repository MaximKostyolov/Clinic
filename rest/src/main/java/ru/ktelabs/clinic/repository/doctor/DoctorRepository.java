package ru.ktelabs.clinic.repository.doctor;

import ru.ktelabs.clinic.model.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>, QuerydslPredicateExecutor<Doctor> {
}
