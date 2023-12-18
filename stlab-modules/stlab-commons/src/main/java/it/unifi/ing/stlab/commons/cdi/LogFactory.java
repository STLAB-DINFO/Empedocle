package it.unifi.ing.stlab.commons.cdi;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.log4j.Logger;

public class LogFactory {

	@Produces
	public Logger createLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger( injectionPoint.getMember().getDeclaringClass()
				.getName() );
	}

}
