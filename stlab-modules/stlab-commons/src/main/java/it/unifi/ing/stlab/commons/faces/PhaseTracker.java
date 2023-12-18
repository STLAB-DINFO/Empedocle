package it.unifi.ing.stlab.commons.faces;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

public class PhaseTracker implements javax.faces.event.PhaseListener{

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent e) {
		e.getFacesContext().getExternalContext().log("after "+e.getPhaseId());
	}

	@Override
	public void beforePhase(PhaseEvent e) {
		e.getFacesContext().getExternalContext().log("before "+e.getPhaseId());
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
