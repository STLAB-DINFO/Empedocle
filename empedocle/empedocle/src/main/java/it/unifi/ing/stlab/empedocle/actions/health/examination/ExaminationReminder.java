package it.unifi.ing.stlab.empedocle.actions.health.examination;

import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.users.model.User;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ExaminationReminder implements Serializable {

	private static final long serialVersionUID = -7756436408240372213L;

	// data iniziale per non visualizzare roba vecchissima rimasta
	// in erogazione per errori nel periodo di rodaggio iniziale
	private static final Date defaultStartDate;

	@Inject
	protected ExaminationFilter examinationFilter;

	@Inject
	private ExaminationDao examinationDao;

	@Inject
	private LoggedUser loggedUser;

	private Long nSuspended;
	private Long nRunning;

	static {
		// data iniziale di default: 1 gennaio 2013
		Calendar c = Calendar.getInstance();
		c.set( Calendar.YEAR, 2013 );
		c.set( Calendar.MONTH, 0 );
		c.set( Calendar.DAY_OF_MONTH, 1 );
		defaultStartDate = c.getTime();
	}

	public String listSuspendedExaminations() {
		examinationFilter.getFilters().clear();
		examinationFilter.selectList( "SUSPENDED" );

		setFilters();

		return navigationAction();
	}

	public String listRunningExaminations() {
		examinationFilter.getFilters().clear();
		examinationFilter.selectList( "RUNNING" );

		setFilters();

		return navigationAction();
	}

	public Long getnSuspended() {
		if ( nSuspended == null ) {
			initNSuspended();
		}
		return nSuspended;
	}

	public Long getnRunning() {
		if ( nRunning == null ) {
			initNRunning();
		}
		return nRunning;
	}

	private void initNSuspended() {
		nSuspended = examinationDao.countUserExaminationsByStatus( 
				loggedUser.getUsername(),
				ExaminationStatus.SUSPENDED, 
				DateHelper.startOfToday( defaultStartDate ),
				DateHelper.startOfTomorrow( Calendar.getInstance().getTime() ) );

	}

	private void initNRunning() {
		nRunning = examinationDao.countUserExaminationsByStatus( 
				loggedUser.getUsername(),
				ExaminationStatus.RUNNING, 
				DateHelper.startOfToday( defaultStartDate ),
				DateHelper.startOfTomorrow( Calendar.getInstance().getTime() ) );
	}

	private String navigationAction() {
		return "pages/examination/examination-list.jsf?faces-redirect=true";
	}

	private void setFilters() {
		examinationFilter.getFilters().add( userFilter() );
		examinationFilter.getFilters().add( fromDateFilter() );
		examinationFilter.getFilters().add( toDateFilter() );
	}

	private Filter userFilter() {
		User user = loggedUser.getUser();

		Filter userFilter = new Filter();
		userFilter.setDefinition( examinationFilter.findFilterDefByName( "In carico a" ) );
		userFilter.setSuggestion( user.getSurname() + " " + user.getName() );
		userFilter.setValue( loggedUser.getUser().getUuid() );
		return userFilter;
	}
	
	private Filter fromDateFilter() {
		Filter fromDateFilter = new Filter();
		fromDateFilter.setDefinition( examinationFilter.findFilterDefByName( "Data - Dal" ) );
		fromDateFilter.setValue( DateHelper.startOfToday( defaultStartDate ) );
		return fromDateFilter;
	}	

	private Filter toDateFilter() {
		Filter toDateFilter = new Filter();
		toDateFilter.setDefinition( examinationFilter.findFilterDefByName( "Data - Al" ) );
		toDateFilter.setValue( DateHelper.startOfTomorrow( Calendar.getInstance().getTime() ) );
		return toDateFilter;
	}
}
