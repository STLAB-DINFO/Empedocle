package it.unifi.ing.stlab.reflection.converter;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;

import org.richfaces.component.UIParameter;

import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.model.types.Type;

@Named
@RequestScoped
public class TypeConverter implements Converter {

	@Inject
	private TypeDao typeDao;

	@Override
	public Object getAsObject( FacesContext context, UIComponent component, String value ) {
		if ( value == null || value.isEmpty() ) {
			assignNullToBeanProperty( context, component );
			return null;
		}

		lookup();
		Type type = typeDao.findByUuid( value );

		if ( type == null ) {
			throw new ConverterException();
		}

		return type;
	}

	@Override
	public String getAsString( FacesContext context, UIComponent component, Object value ) {
		if ( !( value instanceof Type ) )
			return null;

		return ( (Type) value ).getUuid();
	}

	// XXX aggiunto per evitare NullPointerException su typeDao (non
	// inizializzato in suggestionList)
	private void lookup() {
		if ( typeDao == null ) {
			try {
				InitialContext jndi = new InitialContext();
				typeDao = (TypeDao) jndi.lookup( "java:module/TypeDaoBean" );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
	
	//XXX workaround: when converter returns null values, the <a4j:param> never assign null to the assignTo bean property.
	private void assignNullToBeanProperty( FacesContext context, UIComponent component ) {
		ELContext elContext = context.getELContext();
		
		if( component instanceof UIParameter)
			((UIParameter) component).getAssignToExpression().setValue( elContext, null );		
	}
}
