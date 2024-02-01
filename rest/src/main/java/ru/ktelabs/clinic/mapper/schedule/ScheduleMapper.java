package ru.ktelabs.clinic.mapper.schedule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.ktelabs.clinic.dto.schedule.ScheduleResponseDto;
import ru.ktelabs.clinic.model.schedule.Schedule;

import java.util.List;

@Component
@Mapper
public interface ScheduleMapper {

    @Mapping(target = "doctorId", source = "schedule.doctor.id")
    @Mapping(target = "patientId", source = "schedule.patient.id")
    ScheduleResponseDto toScheduleResponseDto(Schedule schedule);

    List<ScheduleResponseDto> toScheduleResponseDtos(List<Schedule> schedules);

}
