package it.unifi.ing.stlab.reflection.impl.visitor.fact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.TextualFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.reflection.model.facts.FactContext;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Calendar;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

// TODO fare altri test
public class FactCopyVisitorTest {
	
	protected FactCopyVisitor visitor;
	protected Time time;
	protected User author;
	
	@Before
	public void setUp() {
		visitor = new FactCopyVisitor();
		
		time = new Time(Calendar.getInstance().getTime());
		author = UserFactory.createUser();
		author.setUserid("usr");
	}
	
	@Test
	public void testCopyComposite() {
		FactManager mng = new FactManager();
		FactContext ctx = new FactContext(UUID.randomUUID().toString());
		
		CompositeType tCmp = TypeFactory.createCompositeType();
		FactImpl cmp = mng.createComposite(author, time);
		cmp.setContext(ctx);
		cmp.setStatus(FactStatus.ACTIVE);
		cmp.assignType(tCmp);
		
		TextualType ttxt1 = TypeFactory.createTextualType();
		TextualFactImpl txt1 = mng.createTextual(author, time);
		txt1.setStatus(FactStatus.DRAFT);
		txt1.setContext(ctx);
		txt1.assignType(ttxt1);
		txt1.setText("testo1");
		
		TextualType ttxt2 = TypeFactory.createTextualType();
		TextualFactImpl txt2 = mng.createTextual(author, time);
		txt2.setStatus(FactStatus.ACTIVE);
		txt2.setContext(ctx);
		txt2.assignType(ttxt2);
		txt2.setText("testo2");
		
		FactLinkFactory flFactory = new FactLinkFactory();
		FactLinkImpl fl1 = flFactory.insertLink(cmp, txt1);
		FactLinkImpl fl2 = flFactory.insertLink(cmp, txt2);
		
		TypeLink tl1 = TypeLinkFactory.createLink();
		TypeLink tl2 = TypeLinkFactory.createLink();
		
		fl1.setType(tl1);
		fl1.setPriority(10L);
		fl2.setType(tl2);
		fl2.setPriority(5L);
		
		tl1.assignSource(tCmp);
		tl1.assignTarget(ttxt1);
		tl1.setMin(1);
		tl1.setMax(3);
		tl1.setPriority(10L);
		
		tl2.assignSource(tCmp);
		tl2.assignTarget(ttxt2);
		tl2.setMin(2);
		tl2.setMax(5);
		tl2.setPriority(5L);
		
		FactCopyVisitor v = new FactCopyVisitor();
		cmp.accept(v);
		
		assertFactEquality(cmp, v.getResult());
		assertEquals(2, v.getResult().listActiveLinks().size());
		
		for(FactLink fl : v.getResult().listActiveLinks()) {
			assertNotNull(fl.getType());
		}
		
		assertEquals(3, v.getResult().listDescendents().size());
		assertEquals(1, v.getResult().listAncestors().size());
		assertEquals(0, v.getResult().listParents().size());
		
		assertFactEquality(txt2, v.getResult().listChildrenOrdered().get(0).getTarget());
		assertFactEquality(txt1, v.getResult().listChildrenOrdered().get(1).getTarget());
		
		assertTrue(txt1 != v.getResult().listChildrenOrdered().get(0).getTarget());
		assertTrue(txt2 != v.getResult().listChildrenOrdered().get(1).getTarget());
		assertTrue(cmp != v.getResult());
		
	}
	
	private void assertFactEquality(FactImpl expected, FactImpl actual) {
		assertTrue("Fact diversi: " + expected.getUuid() + " e " + actual.getUuid() ,actual.sameAs(expected));
		assertEquals(expected.listChildren().size(), actual.listChildren().size());
		assertNull(actual.getStatus());
		assertNull(actual.getContext());
		assertEquals(expected.getType(), actual.getType());
	}

}
