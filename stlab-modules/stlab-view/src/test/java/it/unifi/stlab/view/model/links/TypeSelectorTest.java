package it.unifi.stlab.view.model.links;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.CompositeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.TextualFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.view.model.links.TypeSelector;

import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class TypeSelectorTest {

	protected TypeSelector selector;
	protected FactLinkFactory linkFactory;
	
	@Before
	public void setUp() {
		selector = new TypeSelector( UUID.randomUUID().toString() );
		linkFactory = new FactLinkFactory();
	}
	
	@Test
	public void testId() {
		assertNull( selector.getId() );
	}
	
	@Test
	public void testUuid() {
		assertNotNull( selector.getUuid() );
	}
	
	@Test
	public void testHashCodeEquals() {
		String u1 = UUID.randomUUID().toString();
		String u2 = UUID.randomUUID().toString();
		
		TypeSelector s1 = new TypeSelector( u1 );
		TypeSelector s2 = new TypeSelector( u1 );
		TypeSelector s3 = new TypeSelector( u2 );
		
		assertTrue( s1.equals( s2 ));
		assertTrue( s2.equals( s1 ));
		assertFalse( s1.equals( s3 ));
		
		assertTrue( s1.hashCode() == s2.hashCode() );
		assertFalse( s1.hashCode() == s3.hashCode() );
	}
	
	@Test
	public void testApplyFound() {
		CompositeFact cf = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		FactLink fl = linkFactory.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf);
		
		TypeLink tl = TypeLinkFactory.createLink();
		fl.setType(tl);
		
		selector.setTypeLink(tl);
		Fact result = selector.apply(cf);
		
		assertNotNull( result );
		assertEquals(tf, result);
	}
	
	@Test
	public void testApplyNull() {
		TypeLink tl = TypeLinkFactory.createLink();
		selector.setTypeLink(tl);
		
		assertNull( selector.apply( null ));
	}
	
	@Test
	public void testApplyFoundWithUpdateLink() {
		CompositeFact cf = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		FactLink fl = linkFactory.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf);
		TypeLink tl = TypeLinkFactory.createLink();
		fl.setType(tl);
		
		linkFactory.updateLink((CompositeFactImpl)cf, (TextualFactImpl)tf, (FactLinkImpl)fl);
		
		selector.setTypeLink(tl);
		Fact result = selector.apply(cf);
		
		assertNotNull(result);
		assertEquals(tf, result );
	}
	
	@Test
	public void testApplyNotFound() {
		CompositeFact cf = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		FactLink fl = linkFactory.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf);
		
		TypeLink tl = TypeLinkFactory.createLink();
		fl.setType(tl);
		
		selector.setTypeLink(TypeLinkFactory.createLink());
		
		assertNull(selector.apply(cf));
	}
	
	@Test
	public void testApplyNextSelectorFound() {
		CompositeFact cf = FactFactory.createComposite();
		CompositeFact cf2 = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		FactLink fl = linkFactory.insertLink((CompositeFactImpl)cf, (CompositeFactImpl)cf2);
		FactLink fl2 = linkFactory.insertLink((CompositeFactImpl)cf2, (TextualFactImpl)tf);
		
		TypeLink tl = TypeLinkFactory.createLink();
		fl.setType(tl);
		
		TypeLink tl2 = TypeLinkFactory.createLink();
		fl2.setType(tl2);
		
		selector.setTypeLink(tl);
		TypeSelector next = new TypeSelector(UUID.randomUUID().toString());
		next.setTypeLink(tl2);
		selector.assignNext(next);
		
		Fact result = selector.apply(cf);
		
		assertNotNull(result);
		assertEquals(tf, result);
	}
	
	@Test
	public void testApplyNextSelectorNotFound() {
		CompositeFact cf = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		FactLink fl = linkFactory.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf);
		
		TypeLink tl = TypeLinkFactory.createLink();
		fl.setType(tl);
		
		selector.setTypeLink(tl);
		TypeSelector next = new TypeSelector(UUID.randomUUID().toString());
		next.setTypeLink(TypeLinkFactory.createLink());
		selector.assignNext(next);
		
		assertNull(selector.apply(cf));
	}
	
	
	@Test
	public void testApplyItemFound(){
		CompositeFact cf = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		FactLink fl = linkFactory.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf);
		
		TypeLink tl = TypeLinkFactory.createLink();
		fl.setType(tl);
		
		selector.setTypeLink(tl);
		
		Set<FactLink> result = selector.applyItem(cf);
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(fl, result.iterator().next());
		
	}
	
	
	@Test
	public void testApplyItemNull() {
		TypeLink tl = TypeLinkFactory.createLink();
		selector.setTypeLink(tl);
		
		assertEquals(0, selector.applyItem( null ).size());
	}
	
	@Test
	public void testApplyItemFoundMore(){
		CompositeFact cf = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		FactLink fl = linkFactory.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf);
		FactLink fl2 = linkFactory.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf);
		FactLink fl3 = linkFactory.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf);
		
		TypeLink tl = TypeLinkFactory.createLink();
		fl.setType(tl);
		fl2.setType(tl);
		fl3.setType(tl);
		
		selector.setTypeLink(tl);
		
		Set<FactLink> result = selector.applyItem(cf);
		
		assertNotNull(result);
		assertEquals(3, result.size());
		assertTrue(result.contains(fl));
		assertTrue(result.contains(fl2));
		assertTrue(result.contains(fl3));

	}
	
	@Test
	public void testApplyItemNotFound() {
		CompositeFact cf = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		FactLink fl = linkFactory.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf);
		
		TypeLink tl = TypeLinkFactory.createLink();
		fl.setType(tl);
		
		selector.setTypeLink(TypeLinkFactory.createLink());
		
		Set<FactLink> result = selector.applyItem(cf);
		
		assertNotNull(result);
		assertEquals(0, result.size());
	}
	
	@Test
	public void testApplyItemNotComposite() {
		TextualFact tf = FactFactory.createTextual();
		
		selector.setTypeLink(TypeLinkFactory.createLink());
		Set<FactLink> result = selector.applyItem(tf);
		
		assertNotNull(result);
		assertEquals(0, result.size());
	}
	
	@Test
	public void testApplyItemNextSelectorFound() {
		CompositeFact cf = FactFactory.createComposite();
		CompositeFact cf2 = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		FactLink fl = linkFactory.insertLink((CompositeFactImpl)cf, (CompositeFactImpl)cf2);
		FactLink fl2 = linkFactory.insertLink((CompositeFactImpl)cf2, (TextualFactImpl)tf);
		
		TypeLink tl = TypeLinkFactory.createLink();
		fl.setType(tl);
		
		TypeLink tl2 = TypeLinkFactory.createLink();
		fl2.setType(tl2);
		
		selector.setTypeLink(tl);
		TypeSelector next = new TypeSelector(UUID.randomUUID().toString());
		next.setTypeLink(tl2);
		selector.assignNext(next);
		
		Set<FactLink> result = selector.applyItem(cf);
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(fl2, result.iterator().next());
	}
	
	@Test
	public void testApplyItemNextSelectorNotFound() {
		CompositeFact cf = FactFactory.createComposite();
		CompositeFact cf2 = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		FactLink fl = linkFactory.insertLink((CompositeFactImpl)cf, (CompositeFactImpl)cf2);
		FactLink fl2 = linkFactory.insertLink((CompositeFactImpl)cf2, (TextualFactImpl)tf);
		
		TypeLink tl = TypeLinkFactory.createLink();
		fl.setType(tl);
		
		TypeLink tl2 = TypeLinkFactory.createLink();
		fl2.setType(tl2);
		
		selector.setTypeLink(tl);
		TypeSelector next = new TypeSelector(UUID.randomUUID().toString());
		next.setTypeLink(TypeLinkFactory.createLink());
		selector.assignNext(next);
		
		Set<FactLink> result = selector.applyItem(cf);
		
		assertNotNull(result);
		assertEquals(0, result.size());
	}	
	
	@Test
	public void testGetLength(){
		TypeSelector sel1 = new TypeSelector(UUID.randomUUID().toString());
		
		assertEquals(1, sel1.getLength());
		
		TypeSelector sel2 = new TypeSelector(UUID.randomUUID().toString());
		TypeSelector sel3 = new TypeSelector(UUID.randomUUID().toString());
		sel1.assignNext(sel2);
		sel2.assignNext(sel3);
		
		assertEquals(3, sel1.getLength());
	}
	
	@Test
	public void testApplyType(){
		CompositeType ct = TypeFactory.createCompositeType();
		TextualType tt = TypeFactory.createTextualType();
		TextualType tt2 = TypeFactory.createTextualType();
		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource(ct);
		tl.assignTarget(tt);
		TypeLink tl2 = TypeLinkFactory.createLink();
		tl2.assignSource(ct);
		tl2.assignTarget(tt2);
		
		TypeSelector sel = new TypeSelector(UUID.randomUUID().toString());
		sel.setTypeLink(tl);
		
		TypeLink result = sel.applyType(ct);
		
		assertNotNull(result);
		assertEquals(result, tl);
		
	}
	
	@Test
	public void testApplyTypeNull(){
		TypeLink tl = TypeLinkFactory.createLink();
		TypeSelector sel = new TypeSelector(UUID.randomUUID().toString());
		sel.setTypeLink(tl);
		
		assertNull(sel.applyType(null));
	}
	
	@Test
	public void testApplyTypeMoreDepth(){
		CompositeType ct = TypeFactory.createCompositeType();
		CompositeType ct2 = TypeFactory.createCompositeType();
		TextualType tt = TypeFactory.createTextualType();
		TextualType tt2 = TypeFactory.createTextualType();
		
		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource(ct);
		tl.assignTarget(tt);
		
		TypeLink tl2 = TypeLinkFactory.createLink();
		tl2.assignSource(ct);
		tl2.assignTarget(ct2);
		
		TypeLink tl3 = TypeLinkFactory.createLink();
		tl3.assignSource(ct2);
		tl3.assignTarget(tt2);
		
		TypeSelector sel = new TypeSelector(UUID.randomUUID().toString());
		sel.setTypeLink(tl2);
		TypeSelector sel2 = new TypeSelector(UUID.randomUUID().toString());
		sel2.setTypeLink(tl3);
		sel.assignNext(sel2);
		
		TypeLink result = sel.applyType(ct);
		
		assertNotNull(result);
		assertEquals(result, tl3);
		
	}
	
	@Test
	public void testApplyTypeNotFound(){
		CompositeType ct = TypeFactory.createCompositeType();
		TextualType tt = TypeFactory.createTextualType();
		TextualType tt2 = TypeFactory.createTextualType();
		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource(ct);
		tl.assignTarget(tt);
		TypeLink tl2 = TypeLinkFactory.createLink();
		tl2.assignSource(ct);
		tl2.assignTarget(tt2);
		
		TypeSelector sel = new TypeSelector(UUID.randomUUID().toString());
		sel.setTypeLink(TypeLinkFactory.createLink());
		
		TypeLink result = sel.applyType(ct);
		
		assertNull(result);
		
	}
}
