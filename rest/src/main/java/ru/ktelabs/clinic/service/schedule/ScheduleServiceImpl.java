package ru.ktelabs.clinic.service.schedule;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ktelabs.client.generated.*;
import ru.ktelabs.clinic.dto.schedule.NewScheduleDto;
import ru.ktelabs.clinic.dto.schedule.ScheduleDto;
import ru.ktelabs.clinic.dto.schedule.ScheduleParams;
import ru.ktelabs.clinic.exception.AccesErrorException;
import ru.ktelabs.clinic.exception.NotFoundException;
import ru.ktelabs.clinic.model.doctor.Doctor;
import ru.ktelabs.clinic.model.patient.Patient;
import ru.ktelabs.clinic.model.schedule.Schedule;
import ru.ktelabs.clinic.model.schedule.QSchedule;
import ru.ktelabs.clinic.repository.schedule.ScheduleRepository;
import ru.ktelabs.clinic.service.doctor.DoctorService;
import ru.ktelabs.clinic.service.patient.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.ktelabs.clinic.DateUtils.DATE_TIME_FORMATTER;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private static final QSchedule Q_SCHEDULE = QSchedule.schedule;

    private final ScheduleRepository scheduleRepository;

    private final DoctorService doctorService;

    private final PatientService patientService;

    private final ru.ktelabs.client.generated.ScheduleService scheduleClient;

    @Override
    public List<Schedule> create(NewScheduleDto newScheduleDto) {
        Doctor doctor = doctorService.getById(newScheduleDto.getDoctorId());
        String workingDays = convertWorkingDaysToString(newScheduleDto);

        GetSchedule.ScheduleRequest scheduleRequest = new GetSchedule.ScheduleRequest();
        scheduleRequest.setDuration(doctor.getSpecialization().getDuration());
        scheduleRequest.setWorkingDays(workingDays);

        GetScheduleResponse.ScheduleResponse response = scheduleClient.getSchedule(scheduleRequest);
        List<LocalDateTime> recordingTimes = convertRecordingTimesFromString(response);
        List<Schedule> schedules = new ArrayList<>();
        for (LocalDateTime recordingTime : recordingTimes) {
            Schedule schedule = new Schedule();
            schedule.setDoctor(doctor);
            schedule.setRecordingTime(recordingTime);
            schedules.add(schedule);
        }
        scheduleRepository.saveAll(schedules);

        return schedules;
    }

    @Override
    public Schedule update(Long id, Long patientId) {
        Schedule schedule = getById(id);
        Patient patient = patientService.getById(patientId);
        if (schedule.getPatient() != null) {
            if (schedule.getPatient().equals(patient)) {
                schedule.setPatient(null);
                return scheduleRepository.save(schedule);
            } else {
                throw new AccesErrorException("Patient with id = " + patientId + "was not recording to this schedule");
            }
        } else {
            schedule.setPatient(patient);
            return scheduleRepository.save(schedule);
        }
    }

    @Override
    public Schedule update(Long id, Long patientId, Boolean isSuccesful) {
        Schedule schedule = getById(id);
        if (Objects.equals(schedule.getPatient().getId(), patientId)) {
            if (schedule.getRecordingTime().isBefore(LocalDateTime.now())) {
                schedule.setIsSuccessful(isSuccesful);
                return scheduleRepository.save(schedule);
            } else {
                throw new AccesErrorException("Recording time is not coming yet");
            }
        } else {
            throw new AccesErrorException("Patient with id = " + patientId + "was not recording to this schedule");
        }
    }

    @Override
    public Schedule getById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Schedule with id = " + id + " was not found"));
    }

    @Override
    public void deleteById(Long id) {
        if (scheduleRepository.findById(id).isPresent()) {
            scheduleRepository.deleteById(id);
        } else {
            throw new NotFoundException("Schedule with id = " + id + " was not found");
        }
    }

    @Override
    public void delete(ScheduleDto scheduleDto) {
        List<Schedule> schedules = findByDoctorIdAndDay(scheduleDto);
        if (!schedules.isEmpty()) {
            scheduleRepository.deleteAll(schedules);
        } else {
            throw new AccesErrorException("Couldn't delete schedules by this params");
        }
    }

    @Override
    public List<Schedule> getAll(ScheduleParams params, int page, int size) {
        Predicate p = buildQSchedulePredicateByParams(params);
        Pageable pageable = PageRequest.of(page, size, Sort.by("recordTime").ascending());
        return scheduleRepository.findAll(p, pageable).toList();
    }

    @Override
    public List<Schedule> getByDoctorIdAndDay(ScheduleDto scheduleDto) {
        return findByDoctorIdAndDay(scheduleDto);
    }

    private Predicate buildQSchedulePredicateByParams(ScheduleParams params) {
        BooleanBuilder builder = new BooleanBuilder();
        if (params.getDoctorId() != null) {
            builder.and(Q_SCHEDULE.doctor.id.eq(params.getDoctorId()));
        }
        if (params.getPatientId() != null) {
            builder.and(Q_SCHEDULE.patient.id.eq(params.getPatientId()));
        }
        if (params.getAvailable() != null) {
            if (params.getAvailable()) {
                builder.and(Q_SCHEDULE.patient.isNull());
            } else {
                builder.and(Q_SCHEDULE.patient.isNotNull());
            }
        }
        if (params.getIsSuccessful() != null) {
            builder.and(Q_SCHEDULE.isSuccessful.eq(params.getIsSuccessful()));
        }
        if (params.getSpecialization() != null) {
            builder.and(Q_SCHEDULE.doctor.specialization.eq(params.getSpecialization()));
        }
        return builder;
    }


    private List<Schedule> findByDoctorIdAndDay(ScheduleDto scheduleDto) {
        LocalDateTime startTime = LocalDateTime.of(scheduleDto.getDay().getYear(),
                scheduleDto.getDay().getMonth(), scheduleDto.getDay().getDayOfMonth(), 7, 59);
        LocalDateTime endTime = LocalDateTime.of(scheduleDto.getDay().getYear(),
                scheduleDto.getDay().getMonth(), scheduleDto.getDay().getDayOfMonth(), 18, 1);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(Q_SCHEDULE.doctor.id.eq(scheduleDto.getDoctorId()));
        builder.and(Q_SCHEDULE.recordingTime.goe(startTime));
        builder.and(Q_SCHEDULE.recordingTime.loe(endTime));
        Pageable pageable = PageRequest.of(0, 100, Sort.by("id").ascending());
        return scheduleRepository.findAll(builder, pageable).toList();
    }

    private String convertWorkingDaysToString(NewScheduleDto newScheduleDto) {
        StringBuilder workingDays = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (LocalDate workingDay : newScheduleDto.getWorkDays()) {
            workingDays.append(workingDay.format(formatter)).append(System.lineSeparator());
        }
        return workingDays.toString();
    }

    private List<LocalDateTime> convertRecordingTimesFromString(GetScheduleResponse.ScheduleResponse response) {
        List<LocalDateTime> recordTimes = new ArrayList<>();
        String[] recordsRow = response.getRecordingTimes().split(System.lineSeparator());
        for (String record : recordsRow) {
            if (!record.isEmpty()) {
                LocalDateTime recordTime = LocalDateTime.parse(record, DATE_TIME_FORMATTER);
                recordTimes.add(recordTime);
            }
        }
        return recordTimes;
    }

}
