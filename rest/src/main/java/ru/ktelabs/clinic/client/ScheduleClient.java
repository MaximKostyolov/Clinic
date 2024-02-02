package ru.ktelabs.clinic.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class ScheduleClient  extends WebServiceGatewaySupport {

    public client.Gen.GetScheduleResponse getSchedule(String workingDay, Integer duration) {

        client.Gen.GetScheduleRequest request = new client.Gen.GetScheduleRequest();

        request.setWorkingDays(workingDay);
        request.setDuration(duration);

        client.Gen.GetScheduleResponse response = (client.Gen.GetScheduleResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        return response;
    }

}
