package it.unifi.ing.stlab.view.converter;

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

import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.model.Viewer;

@Named
@RequestScoped
public class ViewerConverter implements Converter {

	@Inject
	private ViewerDao viewerDao;

	@Override
	public Object getAsObject( FacesContext context, UIComponent component, String value ) {
		if ( value == null || value.isEmpty() ) {
			assignNullToBeanProperty( context, component );
			return null;
		}

		lookup();
		Viewer viewer = viewerDao.findByUuid( value );

		if ( viewer == null ) {
			throw new ConverterException();
		}

		return viewer;
	}

	@Override
	public String getAsString( FacesContext context, UIComponent component, Object value ) {
		if ( !( value instanceof Viewer ) )
			return null;

		return ( (Viewer) value ).getUuid();
	}

	// XXX aggiunto per evitare NullPointerException su viewerDao (non
	// inizializzato in suggestionList)
	private void lookup() {
		if ( viewerDao == null ) {
			try {
				InitialContext jndi = new InitialContext();
				viewerDao = (ViewerDao) jndi.lookup( "java:module/ViewerDaoBean" );
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
