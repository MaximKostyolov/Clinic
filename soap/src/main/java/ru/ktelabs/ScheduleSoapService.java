package ru.ktelabs;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;


@WebService(targetNamespace = "http://ktelabs.ru/", name = "ScheduleService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface ScheduleSoapService {

    @WebResult(name = "scheduleResponse")
    @WebMethod(action = "urn:generateRecordsTime")
    ScheduleResponse getSchedule(@WebParam(name = "scheduleRequest") ScheduleRequest scheduleRequest);

}
