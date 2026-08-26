
package ru.ktelabs.client.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getScheduleResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getScheduleResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="scheduleResponse" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="recordingTimes" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getScheduleResponse", propOrder = {
    "scheduleResponse"
})
public class GetScheduleResponse {

    protected GetScheduleResponse.ScheduleResponse scheduleResponse;

    /**
     * Gets the value of the scheduleResponse property.
     * 
     * @return
     *     possible object is
     *     {@link GetScheduleResponse.ScheduleResponse }
     *     
     */
    public GetScheduleResponse.ScheduleResponse getScheduleResponse() {
        return scheduleResponse;
    }

    /**
     * Sets the value of the scheduleResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetScheduleResponse.ScheduleResponse }
     *     
     */
    public void setScheduleResponse(GetScheduleResponse.ScheduleResponse value) {
        this.scheduleResponse = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="recordingTimes" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "recordingTimes"
    })
    public static class ScheduleResponse {

        @XmlElement(required = true)
        protected String recordingTimes;

        /**
         * Gets the value of the recordingTimes property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecordingTimes() {
            return recordingTimes;
        }

        /**
         * Sets the value of the recordingTimes property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecordingTimes(String value) {
            this.recordingTimes = value;
        }

    }

}
