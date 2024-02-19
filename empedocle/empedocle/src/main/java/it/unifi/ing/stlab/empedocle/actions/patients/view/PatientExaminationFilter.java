package it.unifi.ing.stlab.empedocle.actions.patients.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.unifi.ing.stlab.empedocle.actions.health.examination.ExaminationListType;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationQueryBuilder;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.AppointmentStatus;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterBean;
import it.unifi.ing.stlab.filters.FilterType;
import it.unifi.ing.stlab.filters.SelectItemBuilder;

@Named
@SessionScoped
public class PatientExaminationFilter extends FilterBean implements ExaminationQueryBuilder { 

	private static final long serialVersionUID = -4636045233963106991L;

	private ExaminationListType selectedList;
	
	private Long patientId;

	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	private AgendaDao agendaDao;
	
	public PatientExaminationFilter() {
		setPageSize( 10 );
		
		// filters available
		initFilters();
		// sorting available
		initSorting();
		
		selectedList = ExaminationListType.ALL;
	}
	
	private void initFilters() {
		initFilterDefs();
		initDefaultFilters();		
	}
	
	private void initFilterDefs() {
		addFilterDef( "Agenda", FilterType.SUGGESTION, "e.appointment.agenda.uuid = :pagenda", "pagenda", new SelectItemBuilder() {
			
			@Override
			public List<SelectItem> getSelectItems(Object param, int offset, int limit) {
				List<SelectItem> agendaItems = new ArrayList<SelectItem>();
				if (param == null)
					param = "";
					
				List<Agenda> availableAgendas = agendaDao.findBySuggestion( param.toString(), loggedUser.getUsername(), limit);
				List<Agenda> favoriteAgendas = agendaDao.findFavoriteAgendasByUsername( loggedUser.getUsername() );
	
				SelectItemGroup favorite_group = new SelectItemGroup("Agende preferite");
				SelectItemGroup other_group = new SelectItemGroup("Altre agende");
	
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
		
		addFilterDef( "Visited from", FilterType.DATE, "e.appointment.date >= :pamin", "pamin" );
		addFilterDef( "Visited until", FilterType.DATE, "e.appointment.date <= :pamax", "pamax" );
		
		setFilterDefsOrder( FilterDefsOrder.INSERTION );		
	}
	
	private void initSorting() {
		addSort( "Date", "e.appointment.date asc", "e.appointment.date desc" );
		
		toggle( "Date" ); // ordinamento asc
		toggle( "Date" ); // ordinamento desc
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
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select count( distinct e ) " )		
			  .append( " from Examination e " )
			  .append( " join e.appointment.patient.after aa " )
			  .append( " where aa.id = :id " )
			  .append( " and e.appointment.agenda in :agendas " )
			  .append( " and e.status in :ex_status " )
			  .append( " and e.appointment.status in :ap_status");
		
		writeFilters( buffer );

		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
	} 
	
	@Override
	public void resetFilters() {
		super.resetFilters();
		
		selectedList = ExaminationListType.ALL;
	}

	@Override
	public Query getListQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select distinct e " )
			  .append( " from Examination e " )
			  .append( " join e.appointment.patient.after aa ")
			  .append( " where aa.id = :id " )
			  .append( " and e.appointment.agenda in :agendas " )			  
			  .append( " and e.status in :ex_status " )
			  .append( " and e.appointment.status in :ap_status");
		
		writeFilters( buffer );
		
		if ( getSorting() != null ) {
			buffer.append( " order by " )
				  .append( getSorting() );
		}

		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
	}


	private void writeFilters( StringBuffer buffer ) {
		for ( Filter filter : getFilters() ) {
			if ( filter.isUsed() ) {
				buffer.append( " and " )
					.append( filter.getDefinition().getExpression() );
			}
		}
	}
	
	@Override
	public void resolveParameters( Query query ) {
		super.resolveParameters( query );
		
		query.setParameter( "id", patientId );
		query.setParameter( "agendas",  loggedUser.getAgendas());		
		
		switch ( selectedList ) {
		case ALL:
			query.setParameter( "ex_status", Arrays.asList(
					ExaminationStatus.TODO,
					ExaminationStatus.RUNNING,
					ExaminationStatus.SUSPENDED, 
					ExaminationStatus.DONE,
					ExaminationStatus.CONCLUDED ));
			query.setParameter( "ap_status", Arrays.asList( 
					AppointmentStatus.ACCEPTED, 
					AppointmentStatus.BOOKED ));
			break;
		case BOOKED:
			query.setParameter( "ex_status", Arrays.asList(
					ExaminationStatus.TODO ));
			query.setParameter( "ap_status", Arrays.asList( 
					AppointmentStatus.BOOKED ));
			break;
		case ACCEPTED:
			query.setParameter( "ex_status", Arrays.asList(
					ExaminationStatus.TODO ));
			query.setParameter( "ap_status", Arrays.asList( 
					AppointmentStatus.ACCEPTED ));
			break;
		case SUSPENDED:
			query.setParameter( "ex_status", Arrays.asList(
					ExaminationStatus.SUSPENDED ));
			query.setParameter( "ap_status", Arrays.asList( 
					AppointmentStatus.ACCEPTED ));
			break;
		case RUNNING:
			query.setParameter( "ex_status", Arrays.asList(
					ExaminationStatus.RUNNING ));
			query.setParameter( "ap_status", Arrays.asList( 
					AppointmentStatus.ACCEPTED ));
			break;
		case DONE:
			query.setParameter( "ex_status", Arrays.asList(
					ExaminationStatus.DONE ));
			query.setParameter( "ap_status", Arrays.asList( 
					AppointmentStatus.ACCEPTED ));
			break;
		case CONCLUDED:
			query.setParameter( "ex_status", Arrays.asList(
					ExaminationStatus.CONCLUDED ));
			query.setParameter( "ap_status", Arrays.asList( 
					AppointmentStatus.ACCEPTED ));
			break;
		default:
			break;
		}
	}

	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId( Long patientId ) {
		this.patientId = patientId;
	}
}
