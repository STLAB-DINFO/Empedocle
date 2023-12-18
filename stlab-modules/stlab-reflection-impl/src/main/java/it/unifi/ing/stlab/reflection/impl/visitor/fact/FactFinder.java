package it.unifi.ing.stlab.reflection.impl.visitor.fact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

/**
 * To find facts starting from a specified fact and type link selectors.
 *
 */
public class FactFinder {
	
	public List<Fact> find( Fact fact, String... selectors ) {
		if ( fact == null || selectors == null || selectors.length == 0 ) return null;
		if ( !ClassHelper.instanceOf( fact, CompositeFact.class )) return null;
		
		List<Fact> result = new ArrayList<Fact>();
		
		List<String> selectorList = new LinkedList<String>(Arrays.asList( selectors ));
		String selector = selectorList.remove( 0 );
		for (FactLink link : fact.listActiveLinks()) {
			if ( link.getType() != null && link.getType().getName().equals( selector )) {
				if ( selectorList.isEmpty() ) {
					result.add( link.getTarget() );
				} else {
					result.addAll( find( link.getTarget(),
							selectorList.toArray( new String[selectorList.size()] ) ) );
				}
			}
		}

		return result;
	}
}
