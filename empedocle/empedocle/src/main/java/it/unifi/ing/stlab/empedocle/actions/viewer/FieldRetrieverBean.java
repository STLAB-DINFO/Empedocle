package it.unifi.ing.stlab.empedocle.actions.viewer;

import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.patients.dao.PatientDao;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.view.controllers.FieldRetriever;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.JexlException;
import org.apache.commons.jexl2.MapContext;

@RequestScoped
public class FieldRetrieverBean implements FieldRetriever {

	@Inject
	private PatientDao patientDao;
	
	private JexlEngine jexlEngine;
	private JexlContext context;
	
	@Override
	public String retrieve(Fact fact, String path) {
		if(jexlEngine == null) 
			initJexlEngine();
		
		if(context == null)
			initJexlContext( fact );
		
		Expression expr = jexlEngine.createExpression( formatPath( path ) );
		
		return evaluateExpression( expr );
	}

	
	private void initJexlEngine(){
		jexlEngine = new JexlEngine();
		jexlEngine.setSilent( true );
//		jexlEngine.setStrict( true );
	}

	private void initJexlContext(Fact fact){
		context = new MapContext();
		Examination exam = ClassHelper.cast( fact.getContext(), Examination.class );
		context.set( "Appointment", exam.getAppointment() );
		Patient patient = exam.getAppointment().getPatient();
		if( patient.getId() != null )
			context.set( "Patient", patientDao.findLastVersionById( patient.getId()  ) ); // in the real scenario
		else 
			context.set( "Patient", patient ); // only in the case of patient generated on-the-fly by ExaminationRandomInitializer (invoked by ViewerPreview)
		context.set( "Agenda", exam.getAppointment().getAgenda() );
		context.set( "User", exam.getAuthor() );
	}
	
	private String formatPath(String path){
		// modifiche da apportare alla path costruito col dsl
		String formattedPath;
		formattedPath = path.replaceAll("Qualifications", "listQualifications()");
		
		return formattedPath;
	}
	
	private String formatResult(Object result){
		if(result instanceof Date){
			SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
			sdf.setTimeZone( TimeZone.getDefault() );
			return sdf.format( result );
		}
		
		if(result instanceof Collection<?>) {
			// FIXME ordinare la collezione e testare
			return result.toString().replaceAll( "\\[", "" )
									.replaceAll("\\]", "")
									.replaceAll(",", "");
		}
			
			
		if(result != null) {
			return result.toString();
		}
		
		return null;
	}
	
	private String evaluateExpression(Expression expr){
		try{
			Object result = expr.evaluate( context );
			return formatResult( result );
			
		} catch(JexlException je){
			throw new IllegalArgumentException(je.getMessage());
		}
		
	}
}
