package ru.ktelabs;

import kte_labs_soap_web_service.Doctor;
import kte_labs_soap_web_service.NewScheduleDto;
import kte_labs_soap_web_service.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ktelabs.clinic.repository.schedule.ScheduleRepository;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleSoapService {

    public String generateRecordsTime(String workingDays, Integer duration) {
        List<LocalDateTime> recordingTimes = new ArrayList<>();
        String[] dayRow = workingDays.split("\n");
        List<LocalDate> workDays = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (String day : dayRow) {
            if (!day.isEmpty()) {
                LocalDate date = LocalDate.parse(day, formatter);
                workDays.add(date);
            }
        }
        for (LocalDate date : workDays) {
            LocalDateTime recordingTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 8, 0);
            while (recordingTime.isBefore(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 13, 0))) {
                recordingTimes.add(recordingTime);
                recordingTime = recordingTime.plusMinutes(duration);
            }
            recordingTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 14, 0);
            while (recordingTime.isBefore(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 18, 0))) {
                recordingTimes.add(recordingTime);
                recordingTime = recordingTime.plusMinutes(duration);
            }
        }
        return convertToString(recordingTimes);
    }

    private String convertToString(List<LocalDateTime> recordingTimes) {
        String slots = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (LocalDateTime recordingTime : recordingTimes) {
            slots = slots + recordingTime.format(formatter) + "\n";
        }
        return slots;
    }

}
