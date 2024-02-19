package it.unifi.ing.stlab.empedocle.actions.messages;

import it.unifi.ing.stlab.empedocle.dao.messages.MessageQueryBuilder;
import it.unifi.ing.stlab.empedocle.model.messages.MessageLevel;
import it.unifi.ing.stlab.filters.FilterBean;
import it.unifi.ing.stlab.filters.FilterType;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Named
@SessionScoped
public class MessageFilter extends FilterBean implements MessageQueryBuilder { 	

	private static final long serialVersionUID = 1L;

	private MessageListType selectedList;
	private Map<MessageListType, MessageQueryBuilder> queryBuilders;
	
	public MessageFilter() {
		setPageSize( 10 );

		addFilterDef( "Sender", FilterType.TEXT, "m.sender like :psender", "psender" );
		addFilterDef( "Date - Starting from", FilterType.DATE, "m.date >= :pmin", "pmin" );
		addFilterDef( "Date - Before", FilterType.DATE, "m.date <= :pmax", "pmax" );
		addFilterDef( "Read", FilterType.BOOLEAN, "m.isRead = :pread", "pread");
		addFilterDef( "Surname", FilterType.TEXT, "m.patient.surname like :psurname", "psurname" );
		addFilterDef( "Name", FilterType.TEXT, "m.patient.name like :pname", "pname" );

		setFilterDefsOrder(FilterDefsOrder.INSERTION);

		addSort( "Date", "m.date desc", "m.date asc" );
		toggle( "Date" );

		initQueryBuilders();
		
		selectedList = MessageListType.ALL;
	}
	
	private void initQueryBuilders() {
		queryBuilders = new HashMap<MessageListType, MessageQueryBuilder>();
		
		queryBuilders.put( MessageListType.ALL, new MessageQueryBuilderImpl( this ) {{
		}} );
		queryBuilders.put( MessageListType.INFO, new MessageQueryBuilderImpl( this ) {{ 
			addPredefinedFilter( createFilter( "m.level = :plevel", "plevel", MessageLevel.INFO ));
		}} );
		queryBuilders.put( MessageListType.WARNING, new MessageQueryBuilderImpl( this ) {{ 
			addPredefinedFilter( createFilter( "m.level = :plevel", "plevel", MessageLevel.WARNING ));
		}} );
		queryBuilders.put( MessageListType.SEVERE, new MessageQueryBuilderImpl( this ) {{ 
			addPredefinedFilter( createFilter( "m.level = :plevel", "plevel", MessageLevel.SEVERE ));
		}} );	}
	
	@Override
	public Query getCountQuery( EntityManager entityManager ) {
		return queryBuilders.get( selectedList ).getCountQuery( entityManager );
	} 

	@Override
	public Query getListQuery( EntityManager entityManager ) {
		return queryBuilders.get(selectedList).getListQuery( entityManager );
	}
	
	public void selectList( String listType ) {
		for ( MessageListType type : MessageListType.values()) {
			if ( type.toString().equalsIgnoreCase( listType )) {
				selectedList = type;
				clear();
			}
		}
	}
	public boolean isSelectedList( String listType ) {
		return selectedList.toString().equalsIgnoreCase( listType );
	}

}
