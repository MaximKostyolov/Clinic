
package ru.ktelabs.client.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSchedule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSchedule"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="scheduleRequest" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="workingDays" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
@XmlType(name = "getSchedule", propOrder = {
    "scheduleRequest"
})
public class GetSchedule {

    protected GetSchedule.ScheduleRequest scheduleRequest;

    /**
     * Gets the value of the scheduleRequest property.
     * 
     * @return
     *     possible object is
     *     {@link GetSchedule.ScheduleRequest }
     *     
     */
    public GetSchedule.ScheduleRequest getScheduleRequest() {
        return scheduleRequest;
    }

    /**
     * Sets the value of the scheduleRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSchedule.ScheduleRequest }
     *     
     */
    public void setScheduleRequest(GetSchedule.ScheduleRequest value) {
        this.scheduleRequest = value;
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
     *         &lt;element name="workingDays" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
        "workingDays",
        "duration"
    })
    public static class ScheduleRequest {

        @XmlElement(required = true)
        protected String workingDays;
        protected int duration;

        /**
         * Gets the value of the workingDays property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWorkingDays() {
            return workingDays;
        }

        /**
         * Sets the value of the workingDays property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWorkingDays(String value) {
            this.workingDays = value;
        }

        /**
         * Gets the value of the duration property.
         * 
         */
        public int getDuration() {
            return duration;
        }

        /**
         * Sets the value of the duration property.
         * 
         */
        public void setDuration(int value) {
            this.duration = value;
        }

    }

}
