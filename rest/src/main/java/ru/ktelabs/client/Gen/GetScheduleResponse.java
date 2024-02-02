
package client.Gen;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="recordingTimes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "recordingTimes"
})
@XmlRootElement(name = "getScheduleResponse")
public class GetScheduleResponse {

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
