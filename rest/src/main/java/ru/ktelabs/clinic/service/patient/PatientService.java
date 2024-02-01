package ru.ktelabs.clinic.service.patient;

import ru.ktelabs.clinic.dto.patient.NewPatientDto;
import ru.ktelabs.clinic.dto.patient.PatientParams;
import ru.ktelabs.clinic.dto.patient.PatientUpdateDto;
import ru.ktelabs.clinic.model.patient.Patient;

import java.util.List;

public interface PatientService {

    Patient create(NewPatientDto newPatientDto);

    Patient update(Long id, PatientUpdateDto patientUpdateDto);

    Patient getById(Long id);

    void deleteById(Long id);

    List<Patient> getAll(PatientParams params, int page, int size);

}
