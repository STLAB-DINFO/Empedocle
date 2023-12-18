package it.unifi.ing.stlab.empedocle.services.handlers;

import java.io.IOException;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SOAPLoggingHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public boolean handleMessage( SOAPMessageContext context ) {
		Boolean outboundProperty = (Boolean) context
				.get( MessageContext.MESSAGE_OUTBOUND_PROPERTY );
		if ( outboundProperty ) {
			System.out.println( "Outbound message: " );
		} else {
			System.out.println( "Inbound message: " );
		}
		SOAPMessage message = context.getMessage();
		try {
			message.writeTo( System.out );
			System.out.println();
		} catch ( SOAPException | IOException e ) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean handleFault( SOAPMessageContext context ) {
		return true;
	}

	@Override
	public void close( MessageContext context ) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

}