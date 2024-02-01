package ru.ktelabs.clinic.client;

import kte_labs_soap_web_service.GetScheduleRequest;
import kte_labs_soap_web_service.GetScheduleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import ru.ktelabs.clinic.dto.schedule.NewScheduleDto;
import ru.ktelabs.clinic.model.doctor.Doctor;


public class ScheduleClient  extends WebServiceGatewaySupport {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleClient.class);

    public GetScheduleResponse getSchedule(NewScheduleDto newScheduleDto, Doctor doctor) {

        GetScheduleRequest request = new GetScheduleRequest();

        kte_labs_soap_web_service.NewScheduleDto soapNewScheduleDto = new kte_labs_soap_web_service.NewScheduleDto();
        soapNewScheduleDto.setDoctorId(newScheduleDto.getDoctorId());
        soapNewScheduleDto.setWorkDays(newScheduleDto.getWorkDays());

        kte_labs_soap_web_service.Doctor soapDoctor = new kte_labs_soap_web_service.Doctor();
        soapDoctor.setId(doctor.getId());
        soapDoctor.setUuid(doctor.getUuid());
        soapDoctor.setName(doctor.getName());
        soapDoctor.setDescription(doctor.getDescription());
        soapDoctor.setPhotoUrl(doctor.getPhotoUrl());
        soapDoctor.setComments(doctor.getComments());

        request.setNewScheduleDto(soapNewScheduleDto);
        request.setDoctor(soapDoctor);

        logger.info("Generating records time to doctor with Id " + newScheduleDto.getDoctorId());

        GetScheduleResponse response = (GetScheduleResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        return response;
    }

}
