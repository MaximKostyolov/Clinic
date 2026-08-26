package ru.ktelabs.clinic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ktelabs.client.generated.ScheduleService;
import ru.ktelabs.client.generated.ScheduleService_Service;

import java.net.URL;

@Configuration
public class SoapClientConfig {

    @Bean
    public ScheduleService_Service scheduleServiceService() {
        URL wsdlUrl = getClass().getClassLoader().getResource("schedule.wsdl");
        if (wsdlUrl == null) {
            return new ScheduleService_Service();
        }
        return new ScheduleService_Service(wsdlUrl);
    }


    @Bean
    public ScheduleService scheduleServicePort(ScheduleService_Service service) {
        return service.getScheduleServicePort();
    }
}
