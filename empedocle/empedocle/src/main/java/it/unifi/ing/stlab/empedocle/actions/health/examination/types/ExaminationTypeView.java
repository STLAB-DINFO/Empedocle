package it.unifi.ing.stlab.empedocle.actions.health.examination.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationTypeDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.Authorization;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.ViewerUse;

@Named
@ViewScoped
public class ExaminationTypeView implements Serializable {

	private static final long serialVersionUID = -3452021360909330683L;

	//
	// HttpParam injections
	//
	@Inject
	@HttpParam( "id" )
	private String id;

	//
	// EJB injections
	//
	@Inject
	private ExaminationTypeDao examinationTypeDao;

	@Inject
	private AgendaDao agendaDao;

	//
	// Local attributes
	//
	private ExaminationType current;

	@PostConstruct
	public void init() {
		current = examinationTypeDao.findById( Long.parseLong( id ) );
	}

	//
	// navigation methods
	//
	public String close() {
		return "close";
	}

	//
	// get methods
	//
	public String getId() {
		return id;
	}

	public ExaminationType getCurrent() {
		return current;
	}

	public List<Authorization> getAuthorizations() {
		return new ArrayList<Authorization>( getCurrent().listAuthorizations() );
	}

	public boolean hasAuthorizations() {
		return !( getCurrent().listAuthorizations().isEmpty() );
	}

	public List<ViewerUse> getViewerUses() {
		List<ViewerUse> results = new ArrayList<ViewerUse>( getCurrent().listViewers() );
		Collections.sort( results, new Comparator<ViewerUse>() {

			@Override
			public int compare( ViewerUse vu1, ViewerUse vu2 ) {
				String ctx1 = vu1.getContext() != null ? vu1.getContext().getName() : " ";
				String ctx2 = vu2.getContext() != null ? vu2.getContext().getName() : " ";

				int result = ctx1.compareTo( ctx2 );

				if ( result == 0 ) {
					String qlf1 = vu1.getQualification() != null ? vu1.getQualification().getName()
							: " ";
					String qlf2 = vu2.getQualification() != null ? vu2.getQualification().getName()
							: " ";
					result = qlf1.compareTo( qlf2 );
				}

				return result != 0 ? result : vu1.getUuid().compareTo( vu2.getUuid() );
			}

		} );
		return results;
	}

	public boolean hasViewerUses() {
		return !( getCurrent().listViewers().isEmpty() );
	}

	public List<Agenda> getAgendas() {
		return agendaDao.findByExaminationTypeId( getCurrent().getId() );
	}
}
