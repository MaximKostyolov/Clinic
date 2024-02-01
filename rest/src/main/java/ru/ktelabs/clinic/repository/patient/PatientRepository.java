package ru.ktelabs.clinic.repository.patient;

import ru.ktelabs.clinic.model.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, QuerydslPredicateExecutor<Patient> {
}
