package ru.ktelabs;

import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;

import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WSConfig {
	
	@Autowired private Bus bus;
	
	@Bean
	public Endpoint scheduleEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, new ScheduleSoapServiceImpl());
		endpoint.publish("/scheduleService");
		
		return endpoint;
	}
}
