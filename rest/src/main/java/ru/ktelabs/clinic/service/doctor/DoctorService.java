package ru.ktelabs.clinic.service.doctor;

import ru.ktelabs.clinic.dto.doctor.DoctorParams;
import ru.ktelabs.clinic.dto.doctor.DoctorUpdateDto;
import ru.ktelabs.clinic.dto.doctor.NewDoctorDto;
import ru.ktelabs.clinic.model.doctor.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor create(NewDoctorDto newDoctorDto);

    Doctor update(Long id, DoctorUpdateDto doctorUpdateDto);

    Doctor getById(Long id);

    void deleteById(Long id);

    List<Doctor> getAll(DoctorParams params, int page, int size);

}
