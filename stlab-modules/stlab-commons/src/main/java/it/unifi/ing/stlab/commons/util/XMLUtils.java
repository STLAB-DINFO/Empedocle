package it.unifi.ing.stlab.commons.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

public class XMLUtils {

	public static String dom2String( Document doc, boolean xml_declaration ) throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();

		Transformer transformer = tf.newTransformer();
		if ( !xml_declaration )
			transformer.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "yes" );

		StringWriter writer = new StringWriter();
		transformer.transform( new DOMSource( doc ), new StreamResult( writer ) );

		String xml = writer.getBuffer().toString();

		return xml;
	}

	public static String indent( String xml ) throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute( "indent-number", 2 );

        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
		transformer.setOutputProperty( OutputKeys.DOCTYPE_PUBLIC, "yes");

		StringWriter writer = new StringWriter();
        transformer.transform( new StreamSource( new StringReader( xml ) ), new StreamResult( writer ) );

        return writer.getBuffer().toString();
	}
}
