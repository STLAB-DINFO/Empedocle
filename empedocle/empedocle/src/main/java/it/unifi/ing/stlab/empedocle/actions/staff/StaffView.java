package it.unifi.ing.stlab.empedocle.actions.staff;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.model.Staff;

@Named
@ViewScoped
public class StaffView implements Serializable {

	private static final long serialVersionUID = 3458218808438506412L;

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
	private StaffDao staffDao;

	//
	// Local attributes
	//
	private Staff current;

	@PostConstruct
	public void init() {
		current = staffDao.fetchById( Long.parseLong( id ) );
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

	public Staff getCurrent() {
		return current;
	}
}
