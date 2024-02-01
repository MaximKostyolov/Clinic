package ru.ktelabs;

import kte_labs_soap_web_service.Doctor;
import kte_labs_soap_web_service.NewScheduleDto;
import kte_labs_soap_web_service.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ktelabs.clinic.repository.schedule.ScheduleRepository;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleSoapService {

    private ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleSoapService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> generateRecordsTime(NewScheduleDto newScheduleDto, Doctor doctor) {
        List<Schedule> schedules = new ArrayList<>();
        for (XMLGregorianCalendar date : newScheduleDto.getWorkDays()) {
            XMLGregorianCalendar recordingTime = new XMLGregorianCalendar();
            recordingTime.setYear(date.getYear());
            recordingTime.setMonth(date.getMonth());
            recordingTime.setDay(date.getDay());
            recordingTime.setHour(8);
            recordingTime.setMinute(0);
            while (recordingTime.isBefore(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDay(), 13, 0))) {
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

}
