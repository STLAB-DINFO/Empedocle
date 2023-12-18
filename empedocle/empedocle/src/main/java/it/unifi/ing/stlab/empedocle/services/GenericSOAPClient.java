package it.unifi.ing.stlab.empedocle.services;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class GenericSOAPClient {

	public static void send( Document body, String toUrl ) {
		try {
			// create SOAP connection
			SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = connectionFactory.createConnection();

			// send SOAP message
			SOAPMessage response = connection.call( createSOAPRequest( body ), toUrl );

			// process SOAP response
			printSOAPResponse( response );

			connection.close();
		} catch ( Exception e ) {
			throw new RuntimeException( "Error occurred while sending SOAP Request to Server!", e );
		}
	}

	private static SOAPMessage createSOAPRequest( Document body ) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart part = message.getSOAPPart();

		// SOAP envelope
		SOAPEnvelope envelope = part.getEnvelope();

		// SOAP body
		SOAPBody soapBody = envelope.getBody();
		soapBody.addDocument( body );

		// message.saveChanges();

		// print request message
		System.out.println( "Request SOAP message: " );
		message.writeTo( System.out );
		System.out.println();

		return message;
	}

	private static void printSOAPResponse( SOAPMessage response ) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		Source content = response.getSOAPPart().getContent();
		System.out.println( "Response SOAP Message: " );
		StreamResult result = new StreamResult( System.out );

		transformer.transform( content, result );
	}
}
