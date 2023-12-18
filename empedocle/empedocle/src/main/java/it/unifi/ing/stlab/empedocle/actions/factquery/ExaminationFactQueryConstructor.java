package it.unifi.ing.stlab.empedocle.actions.factquery;

import it.unifi.ing.stlab.empedocle.visitor.factquery.BuildExpressionVisitor;
import it.unifi.ing.stlab.factquery.dao.FactQueryConstructor;
import it.unifi.ing.stlab.factquery.model.FactQuery;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@RequestScoped
public class ExaminationFactQueryConstructor extends FactQueryConstructor {

	public Query buildQuery(EntityManager entityManager, FactQuery factQuery ) {
		Query query = entityManager.createQuery( buildQueryDef( factQuery ) );
		resolveParameters( factQuery, query );
		
		query.setFirstResult( factQuery.getOffset() )
				.setMaxResults( factQuery.getLimit() );
		
		return query;
	}
	
	protected String buildQueryDef(FactQuery factQuery) {
		StringBuffer sb = new StringBuffer();
		
		//scrivo la parte iniziale
		sb.append( " select distinct ( factroot ) " )
			.append( " from FactImpl factroot " )
			.append( " join factroot.context.appointment.patient.after aa " )
			.append( " where factroot.type = factroot.context.type.type " )
			.append( " and aa.id = :pid " )
			.append( " and factroot.context.appointment.agenda in :agendas " )
			.append( " and factroot.destination is null " )
			.append( " and factroot.status != :notStatus " )
			.append( " and factroot.context.status in :contextStatuses " );
		
		if( factQuery.getExpression() != null ) {
		
			sb.append( " and (" );
			
			//scrivo gli exist utilizzando un visitor per la Expression
			BuildExpressionVisitor visitor = new BuildExpressionVisitor();
			factQuery.getExpression().accept( visitor );
			
			sb.append( visitor.getResult() )
				.append( ")" );
			
			//copio i parametri aggiuntivi
			for( String key : visitor.getAdditionalParams().keySet() ){
				addAdditionalParams( key, visitor.getAdditionalParams().get( key ) );
			}
			
		}
		
		sb.append( " order by factroot.context.appointment.date desc " );
		
		return sb.toString();
	}
	
}
