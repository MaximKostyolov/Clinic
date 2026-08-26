package ru.ktelabs;

import jakarta.jws.WebService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.ktelabs.DateUtils.DATE_FORMATTER;
import static ru.ktelabs.DateUtils.DATE_TIME_FORMATTER;

@WebService(serviceName = "scheduleService",
        portName = "scheduleServicePort",
        endpointInterface = "ru.ktelabs.ScheduleSoapService",
        targetNamespace = "http://ktelabs.ru/")
public class ScheduleSoapServiceImpl implements ScheduleSoapService {

    @Override
    public ScheduleResponse getSchedule(ScheduleRequest scheduleRequest) {
        String workingDays = scheduleRequest.getWorkingDays();
        int duration = scheduleRequest.getDuration();
        List<LocalDateTime> recordingTimes = new ArrayList<>();
        String[] days = workingDays.split(System.lineSeparator());
        List<LocalDate> workDates = new ArrayList<>();
        for (String day : days) {
            if (!day.isEmpty()) {
                LocalDate date = LocalDate.parse(day, DATE_FORMATTER);
                workDates.add(date);
            }
        }
        for (LocalDate date : workDates) {
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
        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setRecordingTimes(convertToString(recordingTimes));

        return scheduleResponse;
    }

    private String convertToString(List<LocalDateTime> recordingTimes) {
        StringBuilder slots = new StringBuilder();
        for (LocalDateTime recordingTime : recordingTimes) {
            slots.append(recordingTime.format(DATE_TIME_FORMATTER)).append(System.lineSeparator());
        }
        return slots.toString();
    }

}
