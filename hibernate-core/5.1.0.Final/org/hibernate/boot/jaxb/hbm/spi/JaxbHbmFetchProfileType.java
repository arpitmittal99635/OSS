//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.10 at 11:20:59 AM CST 
//


package org.hibernate.boot.jaxb.hbm.spi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FetchProfileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FetchProfileType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fetch" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="association" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="entity" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="style" type="{http://www.hibernate.org/xsd/orm/hbm}FetchStyleEnum" default="join" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FetchProfileType", propOrder = {
    "fetch"
})
public class JaxbHbmFetchProfileType
    implements Serializable
{

    protected List<JaxbHbmFetchProfileType.JaxbHbmFetch> fetch;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Gets the value of the fetch property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fetch property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFetch().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JaxbHbmFetchProfileType.JaxbHbmFetch }
     * 
     * 
     */
    public List<JaxbHbmFetchProfileType.JaxbHbmFetch> getFetch() {
        if (fetch == null) {
            fetch = new ArrayList<JaxbHbmFetchProfileType.JaxbHbmFetch>();
        }
        return this.fetch;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
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
     *       &lt;attribute name="association" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="entity" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="style" type="{http://www.hibernate.org/xsd/orm/hbm}FetchStyleEnum" default="join" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class JaxbHbmFetch
        implements Serializable
    {

        @XmlAttribute(name = "association", required = true)
        protected String association;
        @XmlAttribute(name = "entity")
        protected String entity;
        @XmlAttribute(name = "style")
        protected JaxbHbmFetchStyleEnum style;

        /**
         * Gets the value of the association property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAssociation() {
            return association;
        }

        /**
         * Sets the value of the association property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAssociation(String value) {
            this.association = value;
        }

        /**
         * Gets the value of the entity property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEntity() {
            return entity;
        }

        /**
         * Sets the value of the entity property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEntity(String value) {
            this.entity = value;
        }

        /**
         * Gets the value of the style property.
         * 
         * @return
         *     possible object is
         *     {@link JaxbHbmFetchStyleEnum }
         *     
         */
        public JaxbHbmFetchStyleEnum getStyle() {
            if (style == null) {
                return JaxbHbmFetchStyleEnum.JOIN;
            } else {
                return style;
            }
        }

        /**
         * Sets the value of the style property.
         * 
         * @param value
         *     allowed object is
         *     {@link JaxbHbmFetchStyleEnum }
         *     
         */
        public void setStyle(JaxbHbmFetchStyleEnum value) {
            this.style = value;
        }

    }

}
