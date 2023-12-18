package it.unifi.ing.stlab.empedocle.view.controllers.ophthalmology;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@ConversationScoped
public class GeneticController extends ContainerController implements Serializable {

	private static final long serialVersionUID = 9050067595022428142L;

	@PersistenceContext
	private EntityManager entityManager;
	
	private QualitativeFact informative;
	private QualitativeFact advice;
	private QualitativeFact parentVisit;
	private QualitativeFact familyAdvice;
	private QualitativeFact register;
	
	private Boolean secondVersion;
	
	private Boolean informativeRecovered;
	private Boolean adviceRecovered;
	private Boolean parentVisitRecovered;
	private Boolean familyAdviceRecovered;
	private Boolean registerRecovered;
	
	// Versione 2
	public boolean isSecondVersion(Fact fact, Viewer viewer) {
		if(secondVersion == null){
			try {
				getRecovered(fact, viewer, 8l);
				secondVersion = true;
			} catch(RuntimeException e) {
				secondVersion = false;
			}
		}
		
		return secondVersion;
	}
	
	// Informativa Pazienti
	public boolean isInformativeRecovered(Fact fact, Viewer viewer, Long patientId) {
		if(informative == null) {
			informative = getRecovered(fact, viewer, 0l);
		}
		if(informativeRecovered == null) {
			informativeRecovered = hasBeenResumed(informative, patientId);
		}
		return informativeRecovered;
		
	}
	public boolean isInformativeDateRendered(Fact fact, Viewer viewer) {
		TemporalFact date = getDate(fact, viewer, 1l);
		return isDateRendered(informative, date);
	}
	
	// Consulenza genetica preconcezionale
	public boolean isAdviceRecovered(Fact fact, Viewer viewer, Long patientId) {
		if(advice == null) {
			advice = getRecovered(fact, viewer, 2l);
		}
		if(adviceRecovered == null) {
			adviceRecovered = hasBeenResumed(advice, patientId);
		}
		return adviceRecovered;
	}
	public boolean isAdviceDateRendered(Fact fact, Viewer viewer) {
		TemporalFact date = getDate(fact, viewer, 3l);
		return isDateRendered(advice, date);
	}
	
	// Visita parenti prossimi
	public boolean isParentVisitRecovered(Fact fact, Viewer viewer, Long patientId) {
		if(parentVisit == null) {
			parentVisit = getRecovered(fact, viewer, 4l);
		}
		if(parentVisitRecovered == null) {
			parentVisitRecovered = hasBeenResumed(parentVisit, patientId);
		}
		return parentVisitRecovered;
	}
	public boolean isParentVisitDateRendered(Fact fact, Viewer viewer) {
		TemporalFact date = getDate(fact, viewer, 5l);
		return isDateRendered(parentVisit, date);
	}
	
	// Consulenza genetica preconcezionale famiglia
	public boolean isFamilyAdviceRecovered(Fact fact, Viewer viewer, Long patientId) {
		if(familyAdvice == null) {
			familyAdvice = getRecovered(fact, viewer, 6l);
		}
		if(familyAdviceRecovered == null) {
			familyAdviceRecovered = hasBeenResumed(familyAdvice, patientId);
		}
		return familyAdviceRecovered;
	}
	public boolean isFamilyAdviceDateRendered(Fact fact, Viewer viewer) {
		TemporalFact date = getDate(fact, viewer, 7l);
		return isDateRendered(familyAdvice, date);
	}	
	
	// Consenso inserimento Registro Toscano Malattie Rare
	public boolean isRegisterRecovered(Fact fact, Viewer viewer, Long patientId) {
		if(register == null) {
			register = getRecovered(fact, viewer, 8l);
		}
		if(registerRecovered == null) {
			registerRecovered = hasBeenResumed(register, patientId);
		}
		
		return registerRecovered;
	}
	public boolean isRegisterDateRendered(Fact fact, Viewer viewer) {
		TemporalFact date = getDate(fact, viewer, 9l);
		return isDateRendered(register, date);
	}
	
	//
	// Getter Methods
	//
	
	public QualitativeFact getInformative() {
		return informative;
	}
	public QualitativeFact getAdvice() {
		return advice;
	}
	public QualitativeFact getParentVisit() {
		return parentVisit;
	}
	public QualitativeFact getFamilyAdvice() {
		return familyAdvice;
	}
	public QualitativeFact getRegister() {
		return register;
	}
	
	//
	// Private Methods
	//
	
	private QualitativeFact getRecovered(Fact fact, Viewer viewer, Long priority) {
		Fact f = findBySelector(fact, viewer.getByPriority(priority).getSelector());
		return ClassHelper.cast(f, QualitativeFact.class);
	}
	
	private TemporalFact getDate(Fact fact, Viewer viewer, Long priority) {
		Fact f = findBySelector(fact, viewer.getByPriority(priority).getSelector());
		return ClassHelper.cast(f, TemporalFact.class);
	}
	
	private boolean isDateRendered(QualitativeFact qlt, TemporalFact date) {
		if (qlt.getPhenomenon() != null && 
				"Si".equals(qlt.getPhenomenon().getName())) {
			if (date.getDate() == null) {
				date.setDate(new Date());
			}
			return true;
		}

		date.setDate(null);
		return false;
	}
	
	// controllo se esiste un fact non vuoto di questo tipo
	// se esiste, allora è stata recuperata da una visita precedente
	private boolean hasBeenResumed(QualitativeFact f, Long pId) {
		String q = "SELECT f FROM QualitativeFactImpl f"
				+ " JOIN f.context.appointment.patient.after aa "
				+ " WHERE f.type = :type" + " AND f.phenomenon is not null"
				+ " AND aa.id = :patient"
				+ " AND f.destination is null" + " AND f != :currFact"
				+ " AND f.phenomenon is not null"
				+ " ORDER BY f.origin.time DESC";

		List<Fact> result = entityManager.createQuery(q, Fact.class)
				.setMaxResults(1).setParameter("type", f.getType())
				.setParameter("patient", pId).setParameter("currFact", f)
				.getResultList();

		if (result.size() < 1) {
			return false;
		} else {
			return true;
		}
	}
}
