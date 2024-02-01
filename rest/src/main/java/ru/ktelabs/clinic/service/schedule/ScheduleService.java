package ru.ktelabs.clinic.service.schedule;

import ru.ktelabs.clinic.dto.schedule.NewScheduleDto;
import ru.ktelabs.clinic.dto.schedule.ScheduleDeleteDto;
import ru.ktelabs.clinic.dto.schedule.ScheduleParams;
import ru.ktelabs.clinic.model.schedule.Schedule;

import java.util.List;

public interface ScheduleService {

    List<Schedule> create(NewScheduleDto newScheduleDto);

    Schedule update(Long id, Long patientId);

    Schedule update(Long id, Long patientId, Boolean isSuccesful);

    Schedule getById(Long id);

    void deleteById(Long id);

    void delete(ScheduleDeleteDto scheduleDeleteDto);

    List<Schedule> getAll(ScheduleParams params, int page, int size);

}
