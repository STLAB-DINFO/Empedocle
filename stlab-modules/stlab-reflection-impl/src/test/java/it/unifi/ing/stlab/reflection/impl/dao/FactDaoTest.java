package it.unifi.ing.stlab.reflection.impl.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.CompositeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.TextualFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.actions.FactAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactInsertLink;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactRemoveLink;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactContext;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;

public class FactDaoTest extends PersistenceTest {

	protected FactDao factDao;
	protected Long contextId;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		factDao = new FactDaoBean();
		FieldUtils.assignField( factDao, "entityManager", entityManager );
	}
	
	@Override 
	protected void insertData() throws Exception {
		CompositeType compositeType = TypeFactory.createCompositeType();
		compositeType.setName( "Composite type" );
		entityManager.persist( compositeType );
		
		TextualType textualType = TypeFactory.createTextualType();
		textualType.setName( "Textual type" );
		entityManager.persist( textualType );
		
		TypeLink typeLink = TypeLinkFactory.createLink();
		typeLink.assignSource( compositeType );
		typeLink.assignTarget( textualType );
		entityManager.persist( typeLink );
	
		FactContext factContext = new FactContext( UUID.randomUUID().toString() );
		entityManager.persist( factContext );
		contextId = factContext.getId();
		
		CompositeFactImpl compositeFact = FactFactory.createComposite();
		compositeFact.assignType( compositeType );
		compositeFact.setContext( factContext );
		entityManager.persist( compositeFact );
	
		TextualFactImpl textualFact = FactFactory.createTextual();
		textualFact.assignType( textualType );
		textualFact.setContext( factContext );
		entityManager.persist( textualFact );
	
		FactLinkFactory factLinkFactory = new FactLinkFactory();
		FactLink factLink = factLinkFactory.insertLink(compositeFact, textualFact);
		entityManager.persist( factLink );
	}
	
	@Test
	public void testAddChildren(){
		TypeLink typeLink = TypeLinkFactory.createLink();
		TextualType textualType = TypeFactory.createTextualType();
		typeLink.assignTarget(textualType);
		
		entityManager.persist( textualType );
		entityManager.persist( typeLink );
		
		Long typeLinkId = typeLink.getId();
		Long typeId = textualType.getId();
		
		FactContext factContext = new FactContext( UUID.randomUUID().toString() );
		entityManager.persist( factContext );
		
		Fact fact = FactFactory.createComposite();
		fact.setContext(factContext);
		entityManager.persist( fact );
		
		User user = new User(UUID.randomUUID().toString());
		entityManager.persist( user );
		
		Time time = new Time(new Date());
		
		factDao.addChildren(typeLink, fact, user, time);
		
		FactLink newFactLink = (FactLink)entityManager.createQuery("select fl from FactLinkImpl fl " +
													" where fl.type.id = :typeLinkId")
													.setParameter("typeLinkId", typeLinkId)
													.getSingleResult();
		
		Fact newFact = (Fact)entityManager.createQuery("select f from FactImpl f " +
														" where f.type.id = :typeId")
														.setParameter("typeId", typeId)
														.getSingleResult();
		
		assertNotNull(newFactLink);
		assertNotNull(newFact);
		assertTrue(ClassHelper.instanceOf(newFactLink, FactInsertLink.class));							
		assertEquals(fact, newFactLink.getSource());
		assertEquals(newFact, newFactLink.getTarget());
		assertEquals(factContext, newFact.getContext());
		
	}
	
	@Test
	public void testRemoveChildrenInsertLink(){
		TypeLink typeLink = TypeLinkFactory.createLink();
		TextualType textualType = TypeFactory.createTextualType();
		typeLink.assignTarget(textualType);
		
		entityManager.persist( textualType );
		entityManager.persist( typeLink );
		
		Fact fact = FactFactory.createComposite();
		FactContext factContext = new FactContext( UUID.randomUUID().toString() );
		entityManager.persist( factContext );
		entityManager.persist( fact );
		
		User user = new User(UUID.randomUUID().toString());
		entityManager.persist( user );
		
		Time time = new Time(new Date());
		
		factDao.addChildren(typeLink, fact, user, time);
		
		FactLink factLinkToRemove = fact.listChildren().iterator().next();
		Fact factToRemove = factLinkToRemove.getTarget();
		factDao.removeChildren(factLinkToRemove);
		
		assertTrue(GarbageCollector.getInstance().contains(factLinkToRemove));
		assertTrue(GarbageCollector.getInstance().contains(factToRemove));
	}
	
	
	@Test
	public void testRemoveChildrenUpdateLink(){
		TypeLink typeLink = TypeLinkFactory.createLink();
		TextualType textualType = TypeFactory.createTextualType();
		typeLink.assignTarget(textualType);
		
		entityManager.persist( textualType );
		entityManager.persist( typeLink );
		
		FactImpl source = FactFactory.createComposite();
		FactContext factContext = new FactContext( UUID.randomUUID().toString() );
		entityManager.persist( factContext );
		entityManager.persist( source );
		
		User user = new User(UUID.randomUUID().toString());
		entityManager.persist( user );
		
		Time time = new Time(new Date());
		
		factDao.addChildren(typeLink, source, user, time);
		
		FactManager factManager = new FactManager();
		FactImpl dest = factManager.modify( user, time, source );
		entityManager.persist( dest.getOrigin() );
		entityManager.persist( dest );
		
		FactLink factLinkToRemove = dest.listChildren().iterator().next();
		FactImpl factToRemove = ClassHelper.cast( factLinkToRemove.getTarget(), FactImpl.class );
		FactAction factActionToRemove = factToRemove.getOrigin();
		
		factDao.removeChildren(factLinkToRemove);
		
		FactRemoveLink removeLink = (FactRemoveLink)entityManager.createQuery(" select fl " +
														" from FactRemoveLink fl ")
														.getSingleResult();
		
		assertTrue(GarbageCollector.getInstance().contains(factLinkToRemove));
		assertTrue(GarbageCollector.getInstance().contains(factToRemove));
		assertTrue(GarbageCollector.getInstance().contains(factActionToRemove));
		assertNotNull(removeLink);
		
	}
	
	
	
	@Test
	public void testChildrenPriority(){
		TypeLink typeLink = TypeLinkFactory.createLink();
		TextualType textualType = TypeFactory.createTextualType();
		typeLink.assignTarget( textualType );
		
		entityManager.persist( textualType );
		entityManager.persist( typeLink );
		
		FactContext factContext = new FactContext( UUID.randomUUID().toString() );
		entityManager.persist( factContext );
		
		FactImpl root = FactFactory.createComposite();
		root.setContext( factContext );
		entityManager.persist( root );
		
		User user = new User( UUID.randomUUID().toString() );
		entityManager.persist( user );
		
		Time time = new Time( new Date() );
		
		//priority primo figlio
		factDao.addChildren( typeLink, root, user, time );
		
		assertEquals( 1, root.listActiveLinks().size() );
		FactLink link0 = root.listChildrenOrdered().get( 0 );
		assertNotNull( link0.getPriority() );
		assertEquals( new Long( 0 ), link0.getPriority() );
		
		//priority secondo figlio
		factDao.addChildren( typeLink, root, user, time );
		root.refreshChildrenOrdered();
		
		assertEquals( 2, root.listActiveLinks().size() );
		assertNotNull( root.listChildrenOrdered().get( 1 ).getPriority() );
		FactLink link1 = root.listChildrenOrdered().get( 1 );
		assertEquals( new Long( 0 ), link0.getPriority() );
		assertNotNull( link1.getPriority() );
		assertEquals( new Long( 1 ), link1.getPriority() );
		
		//rimozione primo figlio e aggiunta nuovo
		factDao.removeChildren( link0 );
		factDao.addChildren( typeLink, root, user, time );
		root.refreshChildrenOrdered();
		
//		for(FactLink l : root.listChildrenOrdered())
//			System.out.println(l + " - " + l.getPriority());
		
		assertEquals( 2, root.listActiveLinks().size() );
		assertEquals( link1, root.listChildrenOrdered().get( 0 ) );
		assertEquals( new Long( 1 ), link1.getPriority() );
		assertNotNull( root.listChildrenOrdered().get( 1 ).getPriority() );
		assertEquals( new Long( 2 ), root.listChildrenOrdered().get( 1 ).getPriority() );
		
		//versionamento e aggiunta di un figlio
		FactManager factManager = new FactManager();
		FactImpl updatedRoot = factManager.modify( user, time, root );
		factDao.addChildren( typeLink, updatedRoot, user, time );
		
//		System.out.println( "=====================" );
//		
//		for(FactLink l : updatedRoot.listChildrenOrdered()) {
//			if(l instanceof FactUpdateLink)
//				System.out.println(l + " -> " +((FactUpdateLink)l).getRefersTo() + " - " + l.getPriority());
//			else
//				System.out.println(l + " - " + l.getPriority());
//		}
		
		assertEquals( 3, updatedRoot.listActiveLinks().size() );
		assertNotNull( updatedRoot.listChildrenOrdered().get( 0 ).getPriority() );
		assertEquals( new Long( 1 ), updatedRoot.listChildrenOrdered().get( 0 ).getPriority() );
		assertNotNull( updatedRoot.listChildrenOrdered().get( 1 ).getPriority() );
		assertEquals( new Long( 2 ), updatedRoot.listChildrenOrdered().get( 1 ).getPriority() );
		assertNotNull( updatedRoot.listChildrenOrdered().get( 2 ).getPriority() );
		assertEquals( new Long( 3 ), updatedRoot.listChildrenOrdered().get( 2 ).getPriority() );
		
		//purge
		FactLink updatedLink2 = updatedRoot.listChildrenOrdered().get( 1 );
		( ( TextualFact )updatedLink2.getTarget() ).setText( "prova" );
		factManager.purge( updatedRoot );
		updatedRoot.refreshChildrenOrdered();
		
		assertEquals( 3, updatedRoot.listActiveLinks().size() );
		assertEquals( link1, updatedRoot.listChildrenOrdered().get( 0 ) );
		assertEquals( new Long( 1 ), updatedRoot.listChildrenOrdered().get( 0 ).getPriority() );
		assertEquals( updatedLink2, updatedRoot.listChildrenOrdered().get( 1 ) );
		assertEquals( new Long( 2 ), updatedRoot.listChildrenOrdered().get( 1 ).getPriority() );
		assertEquals( new Long( 3 ), updatedRoot.listChildrenOrdered().get( 2 ).getPriority() );
	}
	
	@Test
	public void testIsUsed() {
		Type type1 = TypeFactory.createTextualType();
		entityManager.persist( type1 );
		
		Type type2 = TypeFactory.createTextualType();
		entityManager.persist( type2 );
		
		Fact fact = FactFactory.createTextual();
		fact.assignType( type1 );
		entityManager.persist( fact );
		
		assertTrue( factDao.existsByUsedType( type1.getId() ) );
		assertFalse( factDao.existsByUsedType( type2.getId() ) );
	}

}
