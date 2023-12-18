package it.unifi.ing.stlab.empedocle.actions.factquery;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.stlab.factquery.dao.FactQueryDao;
import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.navigation.Navigator;

@Named("queryList")
@RequestScoped
public class FactQueryList extends Navigator {

	//
	// CDI injections
	//
	
	@Inject 
	protected FactQueryFilter queryFilter;
	
	@Inject
	private Conversation conversation;
	
	//
	// EJB injections
	//
	
	@Inject
	private FactQueryDao queryDao;
	
	
	//
	// Local attributes
	//
	
	private String queryId;
	private Integer itemCount;
	
	public FactQueryList() {
	}
	
	@Override
	public Integer getItemCount() {
		if ( itemCount == null ) {
			itemCount = queryDao.count( queryFilter );
		}
		return itemCount;
	}
	
	
	@PostConstruct
	public void init() {
		setNavigationStatus( queryFilter );
		
		refreshCurrentPage();
	}

	
	@Produces @RequestScoped @Named( "queryResults" ) 
	public List<FactQuery> getResultList() {
		if ( getItemCount().intValue() == 0 ) 
			return new ArrayList<FactQuery>();
		
		return queryDao.find( queryFilter,  getOffset(),  getLimit() );
	}

	
	public String view( Long id ){
		queryId = id.toString();
		return "view";
		
	}
	
	public String addNew() {
		conversation.begin();
		return "add-new";
	}
	
	public String edit( Long id ){
		queryId = id.toString();
		conversation.begin();
		return "edit";
		
	}
	
	public String getQueryId() {
		return queryId;
	}
	
}