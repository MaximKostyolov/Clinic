package ru.ktelabs.clinic.service.schedule;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ktelabs.clinic.dto.schedule.NewScheduleDto;
import ru.ktelabs.clinic.dto.schedule.ScheduleDeleteDto;
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
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private static final QSchedule Q_SCHEDULE = QSchedule.schedule;

    private final ScheduleRepository scheduleRepository;

    private final DoctorService doctorService;

    private final PatientService patientService;

    @Override
    public List<Schedule> create(NewScheduleDto newScheduleDto) {
        List<Schedule> schedules = new ArrayList<>();
        Doctor doctor = doctorService.getById(newScheduleDto.getDoctorId());
        for (LocalDate date : newScheduleDto.getWorkDays()) {
            LocalDateTime recordingTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 8, 0);
            while (recordingTime.isBefore(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 13, 0))) {
                Schedule schedule = new Schedule();
                schedule.setDoctor(doctor);
                schedule.setRecordingTime(recordingTime);
                schedules.add(schedule);
                recordingTime = recordingTime.plusMinutes(doctor.getSpecialization().getDuration());
            }
            recordingTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 14, 0);
            while (recordingTime.isBefore(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 18, 0))) {
                Schedule schedule = new Schedule();
                schedule.setDoctor(doctor);
                schedule.setRecordingTime(recordingTime);
                schedules.add(schedule);
                recordingTime = recordingTime.plusMinutes(doctor.getSpecialization().getDuration());
            }
        }
        return scheduleRepository.saveAll(schedules);
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
        if (schedule.getPatient().getId() == patientId) {
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
    public void delete(ScheduleDeleteDto scheduleDeleteDto) {
        List<Schedule> schedules = findByDoctorIdAndDay(scheduleDeleteDto);
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


    private List<Schedule> findByDoctorIdAndDay(ScheduleDeleteDto scheduleDeleteDto) {
        LocalDateTime startTime = LocalDateTime.of(scheduleDeleteDto.getDay().getYear(),
                scheduleDeleteDto.getDay().getMonth(), scheduleDeleteDto.getDay().getDayOfMonth(), 7, 59);
        LocalDateTime endTime = LocalDateTime.of(scheduleDeleteDto.getDay().getYear(),
                scheduleDeleteDto.getDay().getMonth(), scheduleDeleteDto.getDay().getDayOfMonth(), 18, 1);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(Q_SCHEDULE.doctor.id.eq(scheduleDeleteDto.getDoctorId()));
        builder.and(Q_SCHEDULE.recordingTime.goe(startTime));
        builder.and(Q_SCHEDULE.recordingTime.loe(endTime));
        Pageable pageable = PageRequest.of(0, 100, Sort.by("id").ascending());
        return scheduleRepository.findAll(builder, pageable).toList();
    }

}
