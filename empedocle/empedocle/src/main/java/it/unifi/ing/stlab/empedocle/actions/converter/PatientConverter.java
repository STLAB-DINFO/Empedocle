package it.unifi.ing.stlab.empedocle.actions.converter;

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

import it.unifi.ing.stlab.patients.dao.PatientDao;
import it.unifi.ing.stlab.patients.model.Patient;

@Named
@RequestScoped
public class PatientConverter implements Converter {
	
	@Inject
	private PatientDao patientDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			assignNullToBeanProperty( context, component );
            return null;
        }

		lookup();
        Patient patient = patientDao.findByUuid( value );

        if (patient == null) {
            throw new ConverterException();
        }

        return patient;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (!(value instanceof Patient)) {
			return null;
		}

		return ((Patient) value).getUuid();
	}
	
	//XXX aggiunto per evitare NullPointerException su agendaDao (non inizializzato in suggestionList)
	private void lookup() {
		if( patientDao == null ) {
			try {
				InitialContext jndi = new InitialContext();
				patientDao = (PatientDao) jndi.lookup("java:module/PatientDaoBean");
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
