package it.unifi.ing.stlab.reflection.impl.visitor.fact;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.visitor.type.FactFactoryVisitor;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignStatusVisitor;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class AssignStatusVisitorTest {
	
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
		TextualType tt2 = TypeFactory.createTextualType();
		
		TypeLink tl1 = TypeLinkFactory.createLink();
		tl1.assignSource( ct );
		tl1.assignTarget( tt1 );
		tl1.setMin( 1 );
		tl1.setPriority( 0l );
		
		TypeLink tl2 = TypeLinkFactory.createLink();
		tl2.assignSource( ct );
		tl2.assignTarget( tt2 );
		tl2.setMin( 1 );
		tl2.setPriority( 1l );
		
		manager = new FactManager();
		
		FactFactoryVisitor visitor = new FactFactoryVisitor( author, time );
		ct.accept( visitor );
		
		root = visitor.getResult();
	}
	
	@Test
	public void testAssignStatus() {
		root.accept( new AssignStatusVisitor( FactStatus.DRAFT ) );
		
		assertEquals( FactStatus.DRAFT, root.getStatus() );
		assertEquals( FactStatus.DRAFT, root.listChildrenOrdered().get( 0 ).getTarget().getStatus() );
		assertEquals( FactStatus.DRAFT, root.listChildrenOrdered().get( 1 ).getTarget().getStatus() );
		
		FactImpl rootMod = manager.modify( author, new Time(Calendar.getInstance().getTime()), root );
		
		(( TextualFact )rootMod.listChildrenOrdered().get( 1 ).getTarget()).setText( "ciao" );
		
		manager.purge( rootMod );
		rootMod.refreshChildrenOrdered();

		rootMod.accept( new AssignStatusVisitor( FactStatus.ACTIVE ) );
		assertEquals( FactStatus.ACTIVE, rootMod.getStatus() );
		assertEquals( FactStatus.ACTIVE, rootMod.listChildrenOrdered().get( 0 ).getTarget().getStatus() );
		assertEquals( FactStatus.ACTIVE, rootMod.listChildrenOrdered().get( 1 ).getTarget().getStatus() );
		
		assertEquals( FactStatus.DRAFT, root.getStatus() );
		assertEquals( FactStatus.ACTIVE, root.listChildrenOrdered().get( 0 ).getTarget().getStatus() );
		assertEquals( FactStatus.DRAFT, root.listChildrenOrdered().get( 1 ).getTarget().getStatus() );
		
	}
	

}
