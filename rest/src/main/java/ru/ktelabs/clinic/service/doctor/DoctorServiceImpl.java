package ru.ktelabs.clinic.service.doctor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import ru.ktelabs.clinic.exception.NotFoundException;
import ru.ktelabs.clinic.repository.doctor.DoctorRepository;
import ru.ktelabs.clinic.dto.doctor.DoctorParams;
import ru.ktelabs.clinic.dto.doctor.DoctorUpdateDto;
import ru.ktelabs.clinic.dto.doctor.NewDoctorDto;
import ru.ktelabs.clinic.exception.AccesErrorException;
import ru.ktelabs.clinic.mapper.doctor.DoctorMapper;
import ru.ktelabs.clinic.model.comment.Comment;
import ru.ktelabs.clinic.model.comment.Status;
import ru.ktelabs.clinic.model.doctor.Doctor;
import ru.ktelabs.clinic.model.doctor.QDoctor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private static final QDoctor Q_DOCTOR = QDoctor.doctor;

    private final DoctorRepository doctorRepository;

    private final DoctorMapper doctorMapper;

    @Override
    public Doctor create(NewDoctorDto newDoctorDto) {
        Doctor doctor = doctorMapper.toDoctor(newDoctorDto);
        try {
            return doctorRepository.save(doctor);
        } catch (RuntimeException exception) {
            throw new AccesErrorException("Doctor with name: " + doctor.getName() + " is already exists");
        }
    }

    @Override
    public Doctor update(Long id, DoctorUpdateDto doctorUpdateDto) {
        Doctor doctor = getById(id);
        updateDoctor(doctor, doctorUpdateDto);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor getById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Doctor with id = " + id + " was not found"));
    }

    @Override
    public void deleteById(Long id) {
        if (doctorRepository.findById(id).isPresent()) {
            doctorRepository.deleteById(id);
        } else {
            throw new NotFoundException("Doctor with id = " + id + " was not found");
        }
    }

    @Override
    public List<Doctor> getAll(DoctorParams params, int page, int size) {
        Predicate p = buildQDoctorPredicateByParams(params);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        List<Doctor> doctors = doctorRepository.findAll(p, pageable).toList();
        if (params.getRating() == null) {
            return doctors;
        } else {
            return getDoctorsByRaiting(doctors, params.getRating());
        }

    }

    private void updateDoctor(Doctor doctor, DoctorUpdateDto doctorUpdateDto) {
        if (doctorUpdateDto.getName() != null) {
            doctor.setName(doctorUpdateDto.getName());
        }
        if (doctorUpdateDto.getDescription() != null) {
            doctor.setDescription(doctorUpdateDto.getDescription());
        }
        if (doctorUpdateDto.getSpecialization() != null) {
            doctor.setSpecialization(doctorUpdateDto.getSpecialization());
        }
        if (doctorUpdateDto.getPhotoUrl() != null) {
            doctor.setPhotoUrl(doctorUpdateDto.getPhotoUrl());
        }
    }


    private Predicate buildQDoctorPredicateByParams(DoctorParams params) {
        BooleanBuilder builder = new BooleanBuilder();
        if (params.getName() != null) {
            builder.and(Q_DOCTOR.name.like(params.getName()));
        }
        if (params.getDescription() != null) {
            builder.and(Q_DOCTOR.description.like(params.getDescription()));
        }
        if (params.getSpecialization() != null) {
            builder.and(Q_DOCTOR.specialization.eq(params.getSpecialization()));
        }
        return builder;
    }


    private List<Doctor> getDoctorsByRaiting(List<Doctor> doctors, Double rating) {
        List<Doctor> doctorsByRating = new ArrayList<>();
        for (Doctor doctor : doctors) {
            int doctorRaiting = 0;
            Double count = 0.0;
            if (!doctor.getComments().isEmpty()) {
                for (Comment comment : doctor.getComments()) {
                    if (comment.getStatus().equals(Status.PUBLISHED)) {
                        count = count + 1.0;
                        doctorRaiting = doctorRaiting + comment.getGrade();
                    }
                }
                if (doctorRaiting / count > rating) {
                    doctorsByRating.add(doctor);
                }
            }
        }
        return doctorsByRating;
    }

}
