package ru.ktelabs.clinic.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class ScheduleClient  extends WebServiceGatewaySupport {

    public GetScheduleResponse getSchedule(String workingDay, Integer duration) {

        GetScheduleRequest request = new GetScheduleRequest();

        request.setWorkingDays(workingDay);
        request.setDuration(duration);

        GetScheduleResponse response = (GetScheduleResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        return response;
    }

}
