package ru.ktelabs.clinic.service.patient;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import ru.ktelabs.clinic.dto.patient.NewPatientDto;
import ru.ktelabs.clinic.dto.patient.PatientParams;
import ru.ktelabs.clinic.dto.patient.PatientUpdateDto;
import ru.ktelabs.clinic.exception.NotFoundException;
import ru.ktelabs.clinic.mapper.patient.PatientMapper;
import ru.ktelabs.clinic.repository.patient.PatientRepository;
import ru.ktelabs.clinic.exception.AccesErrorException;
import ru.ktelabs.clinic.model.patient.Patient;
import ru.ktelabs.clinic.model.patient.QPatient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private static final QPatient Q_PATIENT = QPatient.patient;

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    @Override
    public Patient create(NewPatientDto newPatientDto) {
        Patient patient = patientMapper.toPatient(newPatientDto);
        try {
            return patientRepository.save(patient);
        } catch (RuntimeException exception) {
            throw new AccesErrorException("Patient with email: " + patient.getEmail() + " or with phone: " +
                    patient.getPhone() + " is already exists");
        }
    }

    @Override
    public Patient update(Long id, PatientUpdateDto patientUpdateDto) {
        Patient patient = getById(id);
        updatePatient(patient, patientUpdateDto);
        return patientRepository.save(patient);
    }

    @Override
    public Patient getById(Long id) {
        return patientRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Patient with id = " + id + " was not found"));
    }

    @Override
    public void deleteById(Long id) {
        if (patientRepository.findById(id).isPresent()) {
            patientRepository.deleteById(id);
        } else {
            throw new NotFoundException("Patient with id = " + id + " was not found");
        }
    }

    @Override
    public List<Patient> getAll(PatientParams params, int page, int size) {
        Predicate p = buildQPatientPredicateByParams(params);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return patientRepository.findAll(p, pageable).toList();
    }

    private void updatePatient(Patient patient, PatientUpdateDto patientUpdateDto) {
        if (patientUpdateDto.getName() != null) {
            patient.setName(patientUpdateDto.getName());
        }
        if (patientUpdateDto.getEmail() != null) {
            patient.setEmail(patientUpdateDto.getEmail());
        }
        if (patientUpdateDto.getPhone() != null) {
            patient.setPhone(patientUpdateDto.getPhone());
        }
        if (patientUpdateDto.getBirthday() != null) {
            patient.setBirthday(patientUpdateDto.getBirthday());
        }
        if (patientUpdateDto.getPhotoUrl() != null) {
            patient.setPhotoUrl(patientUpdateDto.getPhotoUrl());
        }
    }

    private Predicate buildQPatientPredicateByParams(PatientParams params) {
        BooleanBuilder builder = new BooleanBuilder();
        if (params.getName() != null) {
            builder.and(Q_PATIENT.name.like(params.getName()));
        }
        if (params.getEmail() != null) {
            builder.and(Q_PATIENT.email.like(params.getEmail()));
        }
        if (params.getPhone() != null) {
            builder.and(Q_PATIENT.phone.eq(params.getPhone()));
        }
        if (params.getBirthday() != null) {
            builder.and(Q_PATIENT.birthday.eq(params.getBirthday()));
        }
        return builder;
    }

}
