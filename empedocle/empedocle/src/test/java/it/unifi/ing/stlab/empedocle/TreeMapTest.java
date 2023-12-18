package it.unifi.ing.stlab.empedocle;

import it.unifi.ing.stlab.empedocle.model.Agenda;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

public class TreeMapTest {
	
	
	@Before
	public void setUp() {
	}
	
	@Test(expected=NullPointerException.class)
	public void testIteratorNull() {
		
		HashMap<String, Long> map = new HashMap<String, Long>();
        ValueComparator vc = new ValueComparator(map);
        TreeMap<String, Long> sorted = new TreeMap<String, Long>(vc);
        
		map.put( "Z", null);
		map.put( "A", null);
		map.put( "G", null);
		
	    sorted.putAll(map);
	    
        for (String key : sorted.keySet()) {
            System.out.println(key + " : " + sorted.get(key));
        }
        
	    System.out.println(sorted.values());
	}	
	
	@Test
	public void testIterator1() {
		
		HashMap<String, Long> map = new HashMap<String, Long>();
        ValueComparator vc = new ValueComparator(map);
        TreeMap<String, Long> sorted = new TreeMap<String, Long>(vc);
        
		map.put( "Z", 0L);
		map.put( "A", 0L);
		map.put( "G", 0L);
		
	    sorted.putAll(map);
	    
        for (String key : sorted.keySet()) {
            System.out.println(key + " : " + sorted.get(key));
        }
        
	    System.out.println(sorted.values());
	}	
	
	@Test
	public void testIterator() {
		
		HashMap<String, Long> map = new HashMap<String, Long>();
        ValueComparator vc = new ValueComparator(map);
        TreeMap<String, Long> sorted = new TreeMap<String, Long>(vc);
        
		map.put( "Elemento3", 9L);
		map.put( "Elemento2", 1L);
		map.put( "Elemento1", 5L);
		
	    sorted.putAll(map);
	    
        for (String key : sorted.keySet()) {
            System.out.println(key + " : " + sorted.get(key));
        }
        
	    System.out.println(sorted.values());
	}
	
	@Test
	public void testAgendaIterator() {
		
		HashMap<Agenda, Long> map = new HashMap<Agenda, Long>();
        AgendaValueComparator vc = new AgendaValueComparator(map);
        TreeMap<Agenda, Long> sorted = new TreeMap<Agenda, Long>(vc);
        
        Agenda a1 = new Agenda( "1");
        a1.setCode( "1");
        a1.setDescription( "a" );
        
        Agenda a2 = new Agenda( "2");
        a2.setCode( "2");
        a2.setDescription( "b" );
 
        Agenda a3 = new Agenda( "3");
        a3.setCode( "3");
        a3.setDescription( "c" );
        
		map.put( a1, 0L);
		map.put( a3, 0L);
		map.put( a2, 0L);
		
	    sorted.putAll(map);
	    
        for (Agenda key : sorted.keySet()) {
            System.out.println(key.toString() + " : " + sorted.get(key));
        }
        
	    System.out.println(sorted.values());
	}
	
	@Test
	public void testAgendaIterator2() {
		
		HashMap<Agenda, Long> map = new HashMap<Agenda, Long>();
        AgendaValueComparator vc = new AgendaValueComparator(map);
        TreeMap<Agenda, Long> sorted = new TreeMap<Agenda, Long>(vc);
        
        Agenda a1 = new Agenda( "1");
        a1.setCode( "1");
        a1.setDescription( "a" );
        
        Agenda a2 = new Agenda( "2");
        a2.setCode( "2");
        a2.setDescription( "b" );
 
        Agenda a3 = new Agenda( "3");
        a3.setCode( "3");
        a3.setDescription( "c" );
        
		map.put( a1, 0L);
		map.put( a3, 0L);
		map.put( a2, 10L);
		
	    sorted.putAll(map);
	    
        for (Agenda key : sorted.keySet()) {
            System.out.println(key.toString() + " : " + sorted.get(key));
        }
        
	    System.out.println(sorted.values());
	}	
	
	class AgendaValueComparator implements Comparator<Agenda> {

	    Map<Agenda, Long> base;
	    public AgendaValueComparator(Map<Agenda, Long> base) {
	        this.base = base;
	    }

	    @Override
	    public int compare(Agenda a, Agenda b) {
	        Long x = base.get(a);
	        Long y = base.get(b);
	        
	        if (x.equals(y)) {
	            return a.toString().compareTo(b.toString());
	        }
	        return x.compareTo(y) > 0 ? -1 : 1;
	        
	    }
	}	
	

	
	class ValueComparator implements Comparator<String> {

	    Map<String, Long> base;
	    public ValueComparator(Map<String, Long> base) {
	        this.base = base;
	    }

	    @Override
	    public int compare(String a, String b) {
	        Long x = base.get(a);
	        Long y = base.get(b);
	        
	        if (x.equals(y)) {
	            return a.compareTo(b);
	        }
	        
	        return x.compareTo(y) > 0 ? -1 : 1;
	        
	    }
	}
	

}
