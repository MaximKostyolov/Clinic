package ru.ktelabs.clinic.mapper.doctor;

import ru.ktelabs.clinic.dto.doctor.DoctorResponseDto;
import ru.ktelabs.clinic.dto.doctor.NewDoctorDto;
import ru.ktelabs.clinic.model.doctor.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface DoctorMapper {

    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "comments", ignore = true)
    DoctorResponseDto toDoctorResponseDto(Doctor doctor);

    List<DoctorResponseDto> toDoctorResponseDtos(List<Doctor> doctors);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Doctor toDoctor(NewDoctorDto newDoctorDto);

}
