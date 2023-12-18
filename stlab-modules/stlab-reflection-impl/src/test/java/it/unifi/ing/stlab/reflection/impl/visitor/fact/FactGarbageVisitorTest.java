package it.unifi.ing.stlab.reflection.impl.visitor.fact;

import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.actions.FactModifyAction;
import it.unifi.ing.stlab.reflection.impl.visitor.type.FactFactoryVisitor;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class FactGarbageVisitorTest {
	
	protected FactManager manager;
	protected User author;
	
	protected FactImpl root;
	
	@Before
	public void setUp() {
		Time time = new Time(Calendar.getInstance().getTime());
		
		author = UserFactory.createUser();
		author.setUserid("usr");
		
		CompositeType ct = TypeFactory.createCompositeType();
		TextualType tt1 = TypeFactory.createTextualType();
		
		TypeLink tl1 = TypeLinkFactory.createLink();
		tl1.assignSource( ct );
		tl1.assignTarget( tt1 );
		tl1.setMin( 1 );
		tl1.setPriority( 0l );
		
		manager = new FactManager();
		
		FactFactoryVisitor visitor = new FactFactoryVisitor( author, time );
		ct.accept( visitor );
		
		FactImpl rootTmp = visitor.getResult();
		root = manager.modify( author, new Time(Calendar.getInstance().getTime()), rootTmp );
	}
	
	@Test
	public void testGarbageFact() {
		root.accept( new FactGarbageVisitor() );
		
		// trashed root
		assertTrue( GarbageCollector.getInstance().contains( root ) );
		assertTrue( GarbageCollector.getInstance().contains( root.getOrigin() ) );
		assertTrue( GarbageCollector.getInstance().contains( root.listChildrenOrdered().get( 0 ) ) );
		
		// trashed children
		FactImpl children = root.listChildrenOrdered().get( 0 ).getTarget();
		assertTrue( GarbageCollector.getInstance().contains( children ) );
		assertTrue( GarbageCollector.getInstance().contains( children.getOrigin() ) );
		
		// trashed root old
		FactImpl rootOld = ( (FactModifyAction) root.getOrigin() ).getSource();
		assertTrue( GarbageCollector.getInstance().contains( rootOld ) );
		assertTrue( GarbageCollector.getInstance().contains( rootOld.getOrigin() ) );
		assertTrue( GarbageCollector.getInstance().contains( rootOld.getDestination() ) );
		assertTrue( GarbageCollector.getInstance().contains( rootOld.listChildrenOrdered().get( 0 ) ) );
		
		// trashed children old
		FactImpl childrenOld = rootOld.listChildrenOrdered().get( 0 ).getTarget();
		assertTrue( GarbageCollector.getInstance().contains( childrenOld ) );
		assertTrue( GarbageCollector.getInstance().contains( childrenOld.getOrigin() ) );
		assertTrue( GarbageCollector.getInstance().contains( childrenOld.getDestination() ) );
		
	}
	
	@Test
	public void testGarbageFactNew() {
		FactImpl rootNew = manager.modify( author, new Time(Calendar.getInstance().getTime()), root );
		rootNew.accept( new FactGarbageVisitor() );
		
		// trashed root3
		assertTrue( GarbageCollector.getInstance().contains( rootNew ) );
		assertTrue( GarbageCollector.getInstance().contains( rootNew.getOrigin() ) );
		assertTrue( GarbageCollector.getInstance().contains( rootNew.listChildrenOrdered().get( 0 ) ) );
		
		// trashed children3
		FactImpl childrenNew = rootNew.listChildrenOrdered().get( 0 ).getTarget();
		assertTrue( GarbageCollector.getInstance().contains( childrenNew ) );
		assertTrue( GarbageCollector.getInstance().contains( childrenNew.getOrigin() ) );
		
		// trashed root
		assertTrue( GarbageCollector.getInstance().contains( root ) );
		assertTrue( GarbageCollector.getInstance().contains( root.getOrigin() ) );
		assertTrue( GarbageCollector.getInstance().contains( root.getDestination() ) );
		assertTrue( GarbageCollector.getInstance().contains( root.listChildrenOrdered().get( 0 ) ) );
		
		// trashed children
		FactImpl children = root.listChildrenOrdered().get( 0 ).getTarget();
		assertTrue( GarbageCollector.getInstance().contains( children ) );
		assertTrue( GarbageCollector.getInstance().contains( children.getOrigin() ) );
		assertTrue( GarbageCollector.getInstance().contains( children.getDestination() ) );
		
		// trashed root old
		FactImpl rootOld = ( (FactModifyAction) root.getOrigin() ).getSource();
		assertTrue( GarbageCollector.getInstance().contains( rootOld ) );
		assertTrue( GarbageCollector.getInstance().contains( rootOld.getOrigin() ) );
		assertTrue( GarbageCollector.getInstance().contains( rootOld.getDestination() ) );
		assertTrue( GarbageCollector.getInstance().contains( rootOld.listChildrenOrdered().get( 0 ) ) );
		
		// trashed children old
		FactImpl childrenOld = rootOld.listChildrenOrdered().get( 0 ).getTarget();
		assertTrue( GarbageCollector.getInstance().contains( childrenOld ) );
		assertTrue( GarbageCollector.getInstance().contains( childrenOld.getOrigin() ) );
		assertTrue( GarbageCollector.getInstance().contains( childrenOld.getDestination() ) );
		
	}

}
