
package com.pelindo.ebtos.ejb.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.pelindo.ebtos.ejb.ws.client package. 
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

    private final static QName _CancelInvoice_QNAME = new QName("http://ws.ebtos.pelindo.com/", "cancelInvoice");
    private final static QName _DoPayment_QNAME = new QName("http://ws.ebtos.pelindo.com/", "doPayment");
    private final static QName _DoPaymentResponse_QNAME = new QName("http://ws.ebtos.pelindo.com/", "doPaymentResponse");
    private final static QName _CreateInvoice_QNAME = new QName("http://ws.ebtos.pelindo.com/", "createInvoice");
    private final static QName _CreateInvoiceResponse_QNAME = new QName("http://ws.ebtos.pelindo.com/", "createInvoiceResponse");
    private final static QName _CancelInvoiceResponse_QNAME = new QName("http://ws.ebtos.pelindo.com/", "cancelInvoiceResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.pelindo.ebtos.ejb.ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateInvoiceResponse }
     * 
     */
    public CreateInvoiceResponse createCreateInvoiceResponse() {
        return new CreateInvoiceResponse();
    }

    /**
     * Create an instance of {@link DoPayment }
     * 
     */
    public DoPayment createDoPayment() {
        return new DoPayment();
    }

    /**
     * Create an instance of {@link DoPaymentResponse }
     * 
     */
    public DoPaymentResponse createDoPaymentResponse() {
        return new DoPaymentResponse();
    }

    /**
     * Create an instance of {@link CancelInvoice }
     * 
     */
    public CancelInvoice createCancelInvoice() {
        return new CancelInvoice();
    }

    /**
     * Create an instance of {@link CreateInvoice }
     * 
     */
    public CreateInvoice createCreateInvoice() {
        return new CreateInvoice();
    }

    /**
     * Create an instance of {@link CancelInvoiceResponse }
     * 
     */
    public CancelInvoiceResponse createCancelInvoiceResponse() {
        return new CancelInvoiceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelInvoice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ebtos.pelindo.com/", name = "cancelInvoice")
    public JAXBElement<CancelInvoice> createCancelInvoice(CancelInvoice value) {
        return new JAXBElement<CancelInvoice>(_CancelInvoice_QNAME, CancelInvoice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoPayment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ebtos.pelindo.com/", name = "doPayment")
    public JAXBElement<DoPayment> createDoPayment(DoPayment value) {
        return new JAXBElement<DoPayment>(_DoPayment_QNAME, DoPayment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoPaymentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ebtos.pelindo.com/", name = "doPaymentResponse")
    public JAXBElement<DoPaymentResponse> createDoPaymentResponse(DoPaymentResponse value) {
        return new JAXBElement<DoPaymentResponse>(_DoPaymentResponse_QNAME, DoPaymentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateInvoice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ebtos.pelindo.com/", name = "createInvoice")
    public JAXBElement<CreateInvoice> createCreateInvoice(CreateInvoice value) {
        return new JAXBElement<CreateInvoice>(_CreateInvoice_QNAME, CreateInvoice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateInvoiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ebtos.pelindo.com/", name = "createInvoiceResponse")
    public JAXBElement<CreateInvoiceResponse> createCreateInvoiceResponse(CreateInvoiceResponse value) {
        return new JAXBElement<CreateInvoiceResponse>(_CreateInvoiceResponse_QNAME, CreateInvoiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelInvoiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ebtos.pelindo.com/", name = "cancelInvoiceResponse")
    public JAXBElement<CancelInvoiceResponse> createCancelInvoiceResponse(CancelInvoiceResponse value) {
        return new JAXBElement<CancelInvoiceResponse>(_CancelInvoiceResponse_QNAME, CancelInvoiceResponse.class, null, value);
    }

}
