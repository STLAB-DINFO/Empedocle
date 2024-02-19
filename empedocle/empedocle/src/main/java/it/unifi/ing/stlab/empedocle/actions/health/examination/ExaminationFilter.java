package it.unifi.ing.stlab.empedocle.actions.health.examination;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationQueryBuilder;
import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.Staff;
import it.unifi.ing.stlab.empedocle.model.health.AppointmentStatus;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterBean;
import it.unifi.ing.stlab.filters.FilterType;
import it.unifi.ing.stlab.filters.SelectItemBuilder;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.model.User;

@Named
@SessionScoped
public class ExaminationFilter extends FilterBean implements ExaminationQueryBuilder { 

	private static final long serialVersionUID = 5593366033833521811L;
	
	private ExaminationListType selectedList;
	private Map<ExaminationListType, ExaminationQueryBuilder> queryBuilders;
	
	//
	// EJB injection
	//
	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	private AgendaDao agendaDao;
	
	@Inject
	private UserDao userDao;	
	
	@Inject
	private StaffDao staffDao;		
	
	public ExaminationFilter() {
		setPageSize( 10 );
	}
	
	@PostConstruct
	public void init() {
		// filters available
		initFilters();
		// sorting available
		initSorting();
		// query builders
		initQueryBuilders();
		
		selectedList = ExaminationListType.ALL;
	}
	
	private void initFilters() {
		initFilterDefs();
		initDefaultFilters();		
	}
	
	private void initSorting() {
		addSort( "Date", "e.appointment.date asc, e.appointment.patient.taxCode asc", "e.appointment.date desc, e.appointment.patient.taxCode desc" );
		toggle( "Date" );
	}

	@Override
	public void resetFilters() {
		super.resetFilters();
		selectedList = ExaminationListType.ALL;
	}
	
	public void selectList( String listType ) {
		for ( ExaminationListType type : ExaminationListType.values()) {
			if ( type.toString().equalsIgnoreCase( listType )) {
				selectedList = type;
				clear();
			}
		}
	}
	public boolean isSelectedList( String listType ) {
		return selectedList.toString().equalsIgnoreCase( listType );
	}
	
	@Override
	public Query getCountQuery( EntityManager entityManager ) {
		return queryBuilders.get( selectedList ).getCountQuery( entityManager );
	} 

	@Override
	public Query getListQuery( EntityManager entityManager ) {
		return queryBuilders.get(selectedList).getListQuery( entityManager );
	}
	
	@Override
	protected void initDefaultFilters() {
		Filter agendaFilter = new Filter();
		agendaFilter.setDefinition( findFilterDefByName( "Agenda" ) );

		Filter fromDateFilter = new Filter();
		fromDateFilter.setDefinition( findFilterDefByName( "Date - from" ) );
		fromDateFilter.setValue( DateHelper.startOfToday( Calendar.getInstance().getTime() ) );

		Filter toDateFilter = new Filter();
		toDateFilter.setDefinition( findFilterDefByName( "Date - to" ) );
		toDateFilter.setValue( DateHelper.startOfTomorrow( Calendar.getInstance().getTime() ) );

		getFilters().add( agendaFilter );
		getFilters().add( fromDateFilter );
		getFilters().add( toDateFilter );
	}

	// se cerco le visite di una giornata, dovrò andare dalla mezzanotte di oggi alla mezzanotte di domani
	private void initFilterDefs() {
		
		addFilterDef( "Agenda", FilterType.SUGGESTION, "e.appointment.agenda.uuid = :pagenda", "pagenda", new SelectItemBuilder() {
			
			@Override
			public List<SelectItem> getSelectItems(Object param, int offset, int limit) {
				List<SelectItem> agendaItems = new ArrayList<SelectItem>();
				if (param == null)
					param = "";
					
				List<Agenda> availableAgendas = agendaDao.findBySuggestion( param.toString(), loggedUser.getUsername(), limit);
				List<Agenda> favoriteAgendas = agendaDao.findFavoriteAgendasByUsername( loggedUser.getUsername() );
	
				SelectItemGroup favorite_group = new SelectItemGroup("Favorite agenda");
				SelectItemGroup other_group = new SelectItemGroup("Other agendas");
	
				List<SelectItem> favorite_items = new ArrayList<SelectItem>();
				List<SelectItem> other_items = new ArrayList<SelectItem>();			
				
				for( Agenda a : availableAgendas ) {
					SelectItem selectItem = new SelectItem( a.getUuid(), a.toString() );
					
					if ( favoriteAgendas.contains( a ) ) {
			        	favorite_items.add( selectItem );
					} else {
			        	other_items.add( selectItem );
					}
				}
								
				if( !favorite_items.isEmpty() ) {
					favorite_group.setSelectItems(favorite_items.toArray(new SelectItem[1]));
					agendaItems.add(favorite_group);
				}
				if( !other_items.isEmpty() ) {
					other_group.setSelectItems(other_items.toArray(new SelectItem[1]));
					agendaItems.add(other_group);
				}
				
				return agendaItems;
			}
		} );			
			
		addFilterDef( "Date - from", FilterType.DATE, "e.appointment.date >= :pamin", "pamin" );
		addFilterDef( "Date - to", FilterType.DATE, "e.appointment.date <= :pamax", "pamax" );
		
		addFilterDef( "Assigned to", FilterType.SUGGESTION, "e.author.uuid = :puser", "puser", new SelectItemBuilder() {
			@Override
			public List<SelectItem> getSelectItems(Object param, int offset, int limit) {
				List<SelectItem> userItems = new ArrayList<SelectItem>();
				
				Staff currStaff = staffDao.fetchByUsername( loggedUser.getUsername() );
				List<User> users = userDao.findBySuggestion( 
						param.toString(), limit );

				for ( User u : users ) {
					userItems.add( new SelectItem( u.getUuid(), u.getSurname() + " " + u.getName() ));
				}
				return userItems;
			}
		} );		
		
		addFilterDef( "Tax Code", FilterType.TEXT, "e.appointment.patient.taxCode like :ptaxc", "ptaxc" );
		addFilterDef( "Surname", FilterType.TEXT, "e.appointment.patient.surname like :psur", "psur" );
		addFilterDef( "Name", FilterType.TEXT, "e.appointment.patient.name like :pname", "pname" );
		addFilterDef( "Birthplace", FilterType.TEXT, "e.appointment.patient.birthPlace like :pbplace", "pbplace" );
		addFilterDef( "Birthdate - from", FilterType.DATE, "e.appointment.patient.birthDate >= :pbmin", "pbmin" );
		addFilterDef( "Birthdate - to", FilterType.DATE, "e.appointment.patient.birthDate <= :pbmax", "pbmax" );
		
		setFilterDefsOrder( FilterDefsOrder.INSERTION );
	}
	
	private void initQueryBuilders() {
		queryBuilders = new HashMap<ExaminationListType, ExaminationQueryBuilder>();
		final Set<Agenda> agendas = loggedUser.getAgendas();
		
		// FIXME si può evitare l'uso dell'operatore IN ?
		queryBuilders.put( ExaminationListType.ALL, new ExaminationQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.appointment.agenda in :agendas", "agendas", agendas));			
		}});
		queryBuilders.put( ExaminationListType.BOOKED, new ExaminationQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.appointment.agenda in :agendas", "agendas", agendas));			
			addPredefinedFilter( createFilter( "e.status = :pestatus", "pestatus", ExaminationStatus.TODO ));
			addPredefinedFilter( createFilter( "e.appointment.status = :pastatus", "pastatus", AppointmentStatus.BOOKED ));
		}} );
		queryBuilders.put( ExaminationListType.ACCEPTED, new ExaminationQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.appointment.agenda in :agendas", "agendas", agendas));			
			addPredefinedFilter( createFilter( "e.status = :pestatus", "pestatus", ExaminationStatus.TODO ));
			addPredefinedFilter( createFilter( "e.appointment.status = :pastatus", "pastatus", AppointmentStatus.ACCEPTED ));
		}} );
		queryBuilders.put( ExaminationListType.RUNNING, new ExaminationQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.appointment.agenda in :agendas", "agendas", agendas));			
			addPredefinedFilter( createFilter( "e.status = :pestatus", "pestatus", ExaminationStatus.RUNNING ));
		}} );
		queryBuilders.put( ExaminationListType.SUSPENDED, new ExaminationQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.appointment.agenda in :agendas", "agendas", agendas));			
			addPredefinedFilter( createFilter( "e.status = :pestatus", "pestatus", ExaminationStatus.SUSPENDED ));
		}} );
		queryBuilders.put( ExaminationListType.DONE, new ExaminationQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.appointment.agenda in :agendas", "agendas", agendas));			
			addPredefinedFilter( createFilter( "e.status = :pestatus", "pestatus", ExaminationStatus.DONE ));
		}} );
		queryBuilders.put( ExaminationListType.CONCLUDED, new ExaminationQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.appointment.agenda in :agendas", "agendas", agendas));			
			addPredefinedFilter( createFilter( "e.status = :pestatus", "pestatus", ExaminationStatus.CONCLUDED ));
		}} );
	}
}
