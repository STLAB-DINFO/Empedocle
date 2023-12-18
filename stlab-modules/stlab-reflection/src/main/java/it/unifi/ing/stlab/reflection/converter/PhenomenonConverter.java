package it.unifi.ing.stlab.reflection.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;

import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDao;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;

@Named
@RequestScoped
public class PhenomenonConverter implements Converter {

	@Inject
	private PhenomenonDao phenomenonDao;

	@Override
	public Object getAsObject( FacesContext context, UIComponent component, String value ) {
		if ( value == null || value.isEmpty() ) {
			return null;
		}

		lookup();
		Phenomenon phenomenon = phenomenonDao.findByUuid( value );

		if ( phenomenon == null ) {
			throw new ConverterException();
		}

		return phenomenon;
	}

	@Override
	public String getAsString( FacesContext arg0, UIComponent arg1, Object object ) {
		if ( !( object instanceof Phenomenon ) )
			return null;

		return ( (Phenomenon) object ).getUuid();
	}
	
	// XXX aggiunto per evitare NullPointerException su phenomenonDao (non
	// inizializzato in suggestion)
	private void lookup() {
		if ( phenomenonDao == null ) {
			try {
				InitialContext jndi = new InitialContext();
				phenomenonDao = (PhenomenonDao) jndi.lookup( "java:module/PhenomenonDaoBean" );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}

}
