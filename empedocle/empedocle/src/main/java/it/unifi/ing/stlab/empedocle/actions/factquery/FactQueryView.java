package it.unifi.ing.stlab.empedocle.actions.factquery;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.commons.qualifier.StatefulBean;
import it.unifi.ing.stlab.empedocle.visitor.factquery.EncodeExpressionBeanVisitor;
import it.unifi.ing.stlab.factquery.dao.FactQueryDao;
import it.unifi.ing.stlab.factquery.model.FactQuery;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Named("queryView")
@ViewScoped
public class FactQueryView implements Serializable {
	
	private static final long serialVersionUID = 3458218808438506412L;
	
	@Inject
	private Conversation conversation;

	@Inject @StatefulBean
	private FactQueryDao factQueryDao;
	
	@Inject @HttpParam("queryId")
	private String queryId;
	
	private FactQuery current;
	private final List<ExpressionBean> exprBeans;
	
	//
	// Public Methods
	//
	public FactQueryView() {
		exprBeans = new LinkedList<ExpressionBean>();
	}
	
	public String close() {
		return "close";
		
	}
	
	public String edit() {
		conversation.begin();
		return "edit";
		
	}

	//TODO riabilitare funzione
//	public String delete() {
//		return "delete";
//		
//	}
	
	//TODO riabilitare funzione
//	public boolean isUsed() {
//		return queryDao.isUsed( getCurrent() );
//	}
	
	public FactQuery getCurrent() {
		if( current == null )
			initCurrent();
		
		return current;
	}
	
	public List<ExpressionBean> getExprBeans() {
		return exprBeans;
	}

	public String getQueryId() {
		return queryId;
	}
	
	private void initCurrent() {
		if( queryId != null && !queryId.isEmpty() ) {
			current = factQueryDao.findById( Long.parseLong( queryId ) );
			
			EncodeExpressionBeanVisitor visitor = new EncodeExpressionBeanVisitor();
			current.getExpression().accept( visitor );
			exprBeans.addAll( visitor.getResults() );
			
		}
		
	}

}
