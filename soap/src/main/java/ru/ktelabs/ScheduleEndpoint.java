package ru.ktelabs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.ktelabs.springsoap.gen.GetScheduleRequest;
import ru.ktelabs.springsoap.gen.GetScheduleResponse;

@Endpoint
public class ScheduleEndpoint {

    private static final String NAMESPACE_URI = "http://www.ktelabs.ru/springsoap/gen";

    private ScheduleSoapService scheduleSoapService;

    @Autowired
    public ScheduleEndpoint(ScheduleSoapService scheduleSoapService) {
        this.scheduleSoapService = scheduleSoapService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getScheduleRequest")
    @ResponsePayload
    public GetScheduleResponse getSchedule(@RequestPayload GetScheduleRequest request) {
        GetScheduleResponse response = new GetScheduleResponse();
        response.setRecordingTimes(scheduleSoapService.generateRecordsTime(request.getWorkingDays(), request.getDuration()));

        return response;
    }

}
