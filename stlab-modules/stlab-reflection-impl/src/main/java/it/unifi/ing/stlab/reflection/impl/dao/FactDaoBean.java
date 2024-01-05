package it.unifi.ing.stlab.reflection.impl.dao;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactInsertLink;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactUpdateLink;
import it.unifi.ing.stlab.reflection.impl.visitor.fact.FactPersistVisitor;
import it.unifi.ing.stlab.reflection.impl.visitor.fact.RecursiveResolveLazyLoadVisitor;
import it.unifi.ing.stlab.reflection.impl.visitor.type.FactFactoryVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignContextVisitor;
import it.unifi.ing.stlab.reflection.visitor.fact.FactDefaultInitializerVisitor;
import it.unifi.ing.stlab.reflection.visitor.type.ResolveLazyLoadVisitor;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@TransactionAttribute
public class FactDaoBean implements FactDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Fact findById(Long id) {
		return entityManager.find( FactImpl.class, id );
	}
	
	@Override
	public boolean isOwner(Long factId, String userId) {
		Fact fact = findById( factId );
		
		return fact != null && ClassHelper.cast(fact, FactImpl.class)
				.getOrigin().getAuthor().getUserid().equals(userId);
	}
	
	@Override
	public Fact findByContextId(Long contextId, Long typeId) {
		List<?> results = entityManager.createQuery( 
			"select f " +
			" from FactImpl f " +
			" where f.context.id = :contextId " +
			"   and f.type.id = :typeId " +
			"   and f.destination is null " )
			.setParameter( "contextId", contextId )
			.setParameter( "typeId", typeId )
			.setMaxResults( 1 )
			.getResultList();
			
		if ( results.size() == 0 )
			return null;

		return (Fact)results.get( 0 );
	}
	
	@Override
	public Fact fetchById(Long id) {
		
		entityManager.createQuery( 
			"select f " +
			" from FactImpl f " +
			"  join f.ancestors aa " +
			"  left join fetch f.before " +
			"  left join fetch f.children " +
			"  left join fetch f.origin " +
			" where aa.id = :id" ).setParameter( "id", id ).getResultList();
		
		Fact result = entityManager.find( FactImpl.class, id );
		result.getType().accept(new ResolveLazyLoadVisitor());
		
		return result;
	}
	
	// FIXME c'è soluzione più elegante?
	@Override
	public Fact fetchForStatistics(Long id) {
		
		entityManager.createQuery( 
			"select f " +
			" from FactImpl f " +
			"  join f.ancestors aa " +
			"  left join fetch f.before b" +
			"  left join fetch f.children" +
			"  left join fetch f.type " +
			"  left join fetch f.context c " +
			"  left join fetch c.appointment a " +
			"  left join fetch a.patient " +
			"  left join fetch a.services " +
			"  left join fetch f.origin " +
			" where aa.id = :id" ).setParameter( "id", id ).getResultList();
		
		Fact result = entityManager.find( FactImpl.class, id );
		result.accept(new RecursiveResolveLazyLoadVisitor());
		result.getType().accept(new ResolveLazyLoadVisitor());
		
		return result;
	}
	
	@Override
	public void save( Fact fact ) {
		if ( fact == null ) return;
		
		fact.accept( new FactPersistVisitor( entityManager ));
	}
	
	@Override
	public void addChildren(TypeLink tl, Fact f, User user, Time time){
		FactFactoryVisitor visitor = new FactFactoryVisitor(user, time);
		tl.getTarget().accept(visitor);
		FactImpl newFact = visitor.getResult();
		
		AssignContextVisitor assignContext = new AssignContextVisitor( f.getContext() );
		newFact.accept( assignContext );
		
		FactDefaultInitializerVisitor assignDefault = new FactDefaultInitializerVisitor();
		newFact.accept( assignDefault );
		
		save( newFact );
		
		//cerco la priority da assegnare al link, prima di inserirlo
		Long maxPriority = -1L;
		for( FactLink link : f.listActiveLinks() ){
			if( link.getPriority() == null )
				ClassHelper.cast( link, FactLinkImpl.class ).setPriority( 0l );
			
			if( tl.equals( link.getType() ) && link.getPriority() > maxPriority )
				maxPriority = link.getPriority();
		}
		maxPriority++;
		
		//inserisco il link e gli assegno la priorità
		FactLinkFactory linkFactory = new FactLinkFactory();
		FactLinkImpl insert = linkFactory.insertLink( ClassHelper.cast( f, FactImpl.class ), newFact );
		insert.setType(tl);
		insert.setPriority( maxPriority );
		
		entityManager.persist( insert );
		
	}

	@Override
	public void removeChildren(FactLink fl) {
		if( ClassHelper.instanceOf( fl, FactInsertLink.class )){
			fl.getTarget().delete();
			fl.delete();
			
		}
		
		if( ClassHelper.instanceOf( fl, FactUpdateLink.class )){
			FactLinkFactory linkFactory = new FactLinkFactory();
			
			ClassHelper.cast( fl.getTarget(), FactImpl.class ).getOrigin().delete();
			fl.getTarget().delete();
			
			FactLink removeLink = linkFactory.removeLink( 
										ClassHelper.cast( fl.getSource(), FactImpl.class ), 
										ClassHelper.cast( fl, FactUpdateLink.class).getRefersTo() );
			
			entityManager.persist(removeLink);
			
			fl.delete();
		}
	}

	@Override
	public boolean existsByUsedType(Long typeId) {
		return entityManager.createQuery( 
				"select f " +
				" from FactImpl f " +
				" where f.type.id = :typeId" )
			.setParameter( "typeId", typeId )
			.setMaxResults( 1 )
			.getResultList()
			.size() > 0;
	}
}
