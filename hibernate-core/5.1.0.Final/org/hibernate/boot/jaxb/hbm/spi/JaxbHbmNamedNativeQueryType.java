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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;


/**
 * 
 *                 The sql-query element declares a named SQL query string
 *             
 * 
 * <p>Java class for NamedNativeQueryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NamedNativeQueryType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *         &lt;element name="query-param" type="{http://www.hibernate.org/xsd/orm/hbm}QueryParamType"/&gt;
 *         &lt;group ref="{http://www.hibernate.org/xsd/orm/hbm}NativeQueryReturnGroup"/&gt;
 *         &lt;element name="synchronize" type="{http://www.hibernate.org/xsd/orm/hbm}synchronize-type"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="cache-mode" type="{http://www.hibernate.org/xsd/orm/hbm}CacheModeEnum" /&gt;
 *       &lt;attribute name="cache-region" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="cacheable" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="comment" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="fetch-size" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="flush-mode" type="{http://www.hibernate.org/xsd/orm/hbm}FlushModeEnum" /&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="read-only" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="timeout" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="callable" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="resultset-ref" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NamedNativeQueryType", propOrder = {
    "content"
})
public class JaxbHbmNamedNativeQueryType
    implements Serializable
{

    @XmlElementRefs({
        @XmlElementRef(name = "return", namespace = "http://www.hibernate.org/xsd/orm/hbm", type = JAXBElement.class),
        @XmlElementRef(name = "load-collection", namespace = "http://www.hibernate.org/xsd/orm/hbm", type = JAXBElement.class),
        @XmlElementRef(name = "synchronize", namespace = "http://www.hibernate.org/xsd/orm/hbm", type = JAXBElement.class),
        @XmlElementRef(name = "return-scalar", namespace = "http://www.hibernate.org/xsd/orm/hbm", type = JAXBElement.class),
        @XmlElementRef(name = "return-join", namespace = "http://www.hibernate.org/xsd/orm/hbm", type = JAXBElement.class),
        @XmlElementRef(name = "query-param", namespace = "http://www.hibernate.org/xsd/orm/hbm", type = JAXBElement.class)
    })
    @XmlMixed
    protected List<Serializable> content;
    @XmlAttribute(name = "cache-mode")
    @XmlJavaTypeAdapter(Adapter1 .class)
    protected CacheMode cacheMode;
    @XmlAttribute(name = "cache-region")
    protected String cacheRegion;
    @XmlAttribute(name = "cacheable")
    protected Boolean cacheable;
    @XmlAttribute(name = "comment")
    protected String comment;
    @XmlAttribute(name = "fetch-size")
    protected Integer fetchSize;
    @XmlAttribute(name = "flush-mode")
    @XmlJavaTypeAdapter(Adapter5 .class)
    protected FlushMode flushMode;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "read-only")
    protected Boolean readOnly;
    @XmlAttribute(name = "timeout")
    protected Integer timeout;
    @XmlAttribute(name = "callable")
    protected Boolean callable;
    @XmlAttribute(name = "resultset-ref")
    protected String resultsetRef;

    /**
     * 
     *                 The sql-query element declares a named SQL query string
     *             Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link JaxbHbmNativeQueryReturnType }{@code >}
     * {@link JAXBElement }{@code <}{@link JaxbHbmNativeQueryCollectionLoadReturnType }{@code >}
     * {@link String }
     * {@link JAXBElement }{@code <}{@link JaxbHbmSynchronizeType }{@code >}
     * {@link JAXBElement }{@code <}{@link JaxbHbmNativeQueryScalarReturnType }{@code >}
     * {@link JAXBElement }{@code <}{@link JaxbHbmNativeQueryJoinReturnType }{@code >}
     * {@link JAXBElement }{@code <}{@link JaxbHbmQueryParamType }{@code >}
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }

    /**
     * Gets the value of the cacheMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public CacheMode getCacheMode() {
        return cacheMode;
    }

    /**
     * Sets the value of the cacheMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCacheMode(CacheMode value) {
        this.cacheMode = value;
    }

    /**
     * Gets the value of the cacheRegion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCacheRegion() {
        return cacheRegion;
    }

    /**
     * Sets the value of the cacheRegion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCacheRegion(String value) {
        this.cacheRegion = value;
    }

    /**
     * Gets the value of the cacheable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isCacheable() {
        if (cacheable == null) {
            return false;
        } else {
            return cacheable;
        }
    }

    /**
     * Sets the value of the cacheable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCacheable(Boolean value) {
        this.cacheable = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the fetchSize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFetchSize() {
        return fetchSize;
    }

    /**
     * Sets the value of the fetchSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFetchSize(Integer value) {
        this.fetchSize = value;
    }

    /**
     * Gets the value of the flushMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public FlushMode getFlushMode() {
        return flushMode;
    }

    /**
     * Sets the value of the flushMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlushMode(FlushMode value) {
        this.flushMode = value;
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
     * Gets the value of the readOnly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isReadOnly() {
        if (readOnly == null) {
            return false;
        } else {
            return readOnly;
        }
    }

    /**
     * Sets the value of the readOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReadOnly(Boolean value) {
        this.readOnly = value;
    }

    /**
     * Gets the value of the timeout property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * Sets the value of the timeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimeout(Integer value) {
        this.timeout = value;
    }

    /**
     * Gets the value of the callable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isCallable() {
        if (callable == null) {
            return false;
        } else {
            return callable;
        }
    }

    /**
     * Sets the value of the callable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCallable(Boolean value) {
        this.callable = value;
    }

    /**
     * Gets the value of the resultsetRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultsetRef() {
        return resultsetRef;
    }

    /**
     * Sets the value of the resultsetRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultsetRef(String value) {
        this.resultsetRef = value;
    }

}
