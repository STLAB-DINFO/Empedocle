package it.unifi.ing.stlab.commons.cdi.properties;

import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class PropertyProducer {

	@Produces @Property(file="", name="")
	public String getProperty(InjectionPoint ip) {
		Property annotation = ip.getAnnotated().getAnnotation(Property.class);
		
		PropertyLoader loader = new PropertyLoader();
		Properties props = loader.load(annotation.file());
		return props.getProperty(annotation.name());
	}

}
