package ru.ktelabs.clinic.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ScheduleClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("client.Gen");
        return marshaller;
    }

    @Bean
    public ScheduleClient countryClient(Jaxb2Marshaller marshaller) {
        ScheduleClient client = new ScheduleClient();
        client.setDefaultUri("http://localhost:9090/");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
