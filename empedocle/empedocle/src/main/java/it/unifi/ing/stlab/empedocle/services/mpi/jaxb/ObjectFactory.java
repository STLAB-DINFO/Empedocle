//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.12.18 at 01:24:42 PM CET 
//


package it.unifi.ing.stlab.empedocle.services.mpi.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.unifi.ing.stlab.empedocle.services.mpi.jaxb package. 
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

    private final static QName _MergeInformation_QNAME = new QName("http://www.aou-careggi.toscana.it/thislab/eai/people/merge_information", "merge_information");
    private final static QName _MergePatient_QNAME = new QName("http://www.aou-careggi.toscana.it/thislab/eai/people/merge_patient", "merge_patient");
    private final static QName _MessageHeader_QNAME = new QName("http://www.aou-careggi.toscana.it/thislab/eai/people/message_header", "message_header");
    private final static QName _UpdatePersonInformation_QNAME = new QName("http://www.aou-careggi.toscana.it/thislab/eai/people/update_person_information", "update_person_information");
    private final static QName _PatientIdentification_QNAME = new QName("http://www.aou-careggi.toscana.it/thislab/eai/people/patient_identification", "patient_identification");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.unifi.ing.stlab.empedocle.services.mpi.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdatePersonInformation }
     * 
     */
    public UpdatePersonInformation createUpdatePersonInformation() {
        return new UpdatePersonInformation();
    }

    /**
     * Create an instance of {@link MessageHeader }
     * 
     */
    public MessageHeader createMessageHeader() {
        return new MessageHeader();
    }

    /**
     * Create an instance of {@link Endpoint }
     * 
     */
    public Endpoint createEndpoint() {
        return new Endpoint();
    }

    /**
     * Create an instance of {@link PatientIdentification }
     * 
     */
    public PatientIdentification createPatientIdentification() {
        return new PatientIdentification();
    }

    /**
     * Create an instance of {@link EnteTerritoriale }
     * 
     */
    public EnteTerritoriale createEnteTerritoriale() {
        return new EnteTerritoriale();
    }

    /**
     * Create an instance of {@link PatientIdentifiers }
     * 
     */
    public PatientIdentifiers createPatientIdentifiers() {
        return new PatientIdentifiers();
    }

    /**
     * Create an instance of {@link TesseraTEAM }
     * 
     */
    public TesseraTEAM createTesseraTEAM() {
        return new TesseraTEAM();
    }

    /**
     * Create an instance of {@link Indirizzo }
     * 
     */
    public Indirizzo createIndirizzo() {
        return new Indirizzo();
    }

    /**
     * Create an instance of {@link Recapiti }
     * 
     */
    public Recapiti createRecapiti() {
        return new Recapiti();
    }

    /**
     * Create an instance of {@link Assistenza }
     * 
     */
    public Assistenza createAssistenza() {
        return new Assistenza();
    }

    /**
     * Create an instance of {@link Cittadinanza }
     * 
     */
    public Cittadinanza createCittadinanza() {
        return new Cittadinanza();
    }

    /**
     * Create an instance of {@link TesseraSTP }
     * 
     */
    public TesseraSTP createTesseraSTP() {
        return new TesseraSTP();
    }

    /**
     * Create an instance of {@link Telefono }
     * 
     */
    public Telefono createTelefono() {
        return new Telefono();
    }

    /**
     * Create an instance of {@link Istituzione }
     * 
     */
    public Istituzione createIstituzione() {
        return new Istituzione();
    }

    /**
     * Create an instance of {@link LuogoNascita }
     * 
     */
    public LuogoNascita createLuogoNascita() {
        return new LuogoNascita();
    }

    /**
     * Create an instance of {@link Email }
     * 
     */
    public Email createEmail() {
        return new Email();
    }

    /**
     * Create an instance of {@link TesseraSanitaria }
     * 
     */
    public TesseraSanitaria createTesseraSanitaria() {
        return new TesseraSanitaria();
    }

    /**
     * Create an instance of {@link MergePatient }
     * 
     */
    public MergePatient createMergePatient() {
        return new MergePatient();
    }

    /**
     * Create an instance of {@link MergeInformation }
     * 
     */
    public MergeInformation createMergeInformation() {
        return new MergeInformation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MergeInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.aou-careggi.toscana.it/thislab/eai/people/merge_information", name = "merge_information")
    public JAXBElement<MergeInformation> createMergeInformation(MergeInformation value) {
        return new JAXBElement<MergeInformation>(_MergeInformation_QNAME, MergeInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MergePatient }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.aou-careggi.toscana.it/thislab/eai/people/merge_patient", name = "merge_patient")
    public static JAXBElement<MergePatient> createMergePatient(MergePatient value) {
        return new JAXBElement<MergePatient>(_MergePatient_QNAME, MergePatient.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MessageHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.aou-careggi.toscana.it/thislab/eai/people/message_header", name = "message_header")
    public JAXBElement<MessageHeader> createMessageHeader(MessageHeader value) {
        return new JAXBElement<MessageHeader>(_MessageHeader_QNAME, MessageHeader.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePersonInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.aou-careggi.toscana.it/thislab/eai/people/update_person_information", name = "update_person_information")
    public static JAXBElement<UpdatePersonInformation> createUpdatePersonInformation(UpdatePersonInformation value) {
        return new JAXBElement<UpdatePersonInformation>(_UpdatePersonInformation_QNAME, UpdatePersonInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PatientIdentification }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.aou-careggi.toscana.it/thislab/eai/people/patient_identification", name = "patient_identification")
    public JAXBElement<PatientIdentification> createPatientIdentification(PatientIdentification value) {
        return new JAXBElement<PatientIdentification>(_PatientIdentification_QNAME, PatientIdentification.class, null, value);
    }

}
