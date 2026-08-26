
package ru.ktelabs.client.generated;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.ktelabs.client.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetSchedule_QNAME = new QName("http://ktelabs.ru/", "getSchedule");
    private final static QName _GetScheduleResponse_QNAME = new QName("http://ktelabs.ru/", "getScheduleResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.ktelabs.client.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetScheduleResponse }
     * 
     */
    public GetScheduleResponse createGetScheduleResponse() {
        return new GetScheduleResponse();
    }

    /**
     * Create an instance of {@link GetSchedule }
     * 
     */
    public GetSchedule createGetSchedule() {
        return new GetSchedule();
    }

    /**
     * Create an instance of {@link ru.ktelabs.client.generated.ScheduleRequest }
     * 
     */
    public ru.ktelabs.client.generated.ScheduleRequest createScheduleRequest() {
        return new ru.ktelabs.client.generated.ScheduleRequest();
    }

    /**
     * Create an instance of {@link ru.ktelabs.client.generated.ScheduleResponse }
     * 
     */
    public ru.ktelabs.client.generated.ScheduleResponse createScheduleResponse() {
        return new ru.ktelabs.client.generated.ScheduleResponse();
    }

    /**
     * Create an instance of {@link GetScheduleResponse.ScheduleResponse }
     * 
     */
    public GetScheduleResponse.ScheduleResponse createGetScheduleResponseScheduleResponse() {
        return new GetScheduleResponse.ScheduleResponse();
    }

    /**
     * Create an instance of {@link GetSchedule.ScheduleRequest }
     * 
     */
    public GetSchedule.ScheduleRequest createGetScheduleScheduleRequest() {
        return new GetSchedule.ScheduleRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSchedule }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetSchedule }{@code >}
     */
    @XmlElementDecl(namespace = "http://ktelabs.ru/", name = "getSchedule")
    public JAXBElement<GetSchedule> createGetSchedule(GetSchedule value) {
        return new JAXBElement<GetSchedule>(_GetSchedule_QNAME, GetSchedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetScheduleResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetScheduleResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ktelabs.ru/", name = "getScheduleResponse")
    public JAXBElement<GetScheduleResponse> createGetScheduleResponse(GetScheduleResponse value) {
        return new JAXBElement<GetScheduleResponse>(_GetScheduleResponse_QNAME, GetScheduleResponse.class, null, value);
    }

}
