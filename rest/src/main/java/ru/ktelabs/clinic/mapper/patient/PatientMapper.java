package ru.ktelabs.clinic.mapper.patient;

import ru.ktelabs.clinic.dto.patient.PatientResponseDto;
import ru.ktelabs.clinic.dto.patient.NewPatientDto;
import ru.ktelabs.clinic.model.patient.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface PatientMapper {
    PatientResponseDto toPatientResponseDto(Patient patient);

    List<PatientResponseDto> toPatientResponseDtos(List<Patient> doctors);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    Patient toPatient(NewPatientDto newPatientDto);

}
