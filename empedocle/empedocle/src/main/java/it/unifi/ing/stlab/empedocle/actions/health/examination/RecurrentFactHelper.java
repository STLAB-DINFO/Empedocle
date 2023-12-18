package it.unifi.ing.stlab.empedocle.actions.health.examination;

import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.visitor.fact.FactResumeVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

public class RecurrentFactHelper {
	
	private ExaminationDao examinationDao;
	
	public RecurrentFactHelper(ExaminationDao examinationDao) {
		super();
		this.examinationDao = examinationDao;
	}
	
	public void resumeRecurrentFacts(Fact root) {
		
		for(FactLink child : root.listChildren()) {
			
			if(child.getTarget().getType().getRecurrent()) {
				
				Fact currFact = child.getTarget();
				Fact resumed = examinationDao.resume(currFact, ((Examination)currFact.getContext()).getAppointment().getPatient());
				
				if(resumed != null) {
					FactResumeVisitor v = new FactResumeVisitor(currFact);
					resumed.accept(v);
				}
				
			}
			else {
				resumeRecurrentFacts(child.getTarget());
				
			}

		}
	}
	
}
