package it.unifi.ing.stlab.entities.implementation.compact;

import it.unifi.ing.stlab.entities.model.compact.CompactEntity;
import it.unifi.ing.stlab.entities.model.compact.CompactLink;
import it.unifi.ing.stlab.entities.model.compact.InsertLink;


public class InsertLinkImpl
	<T extends CompactEntity<T,L,?>, 
	 L extends CompactLink<T,L>> 

	extends CompactLinkImpl<T,L> implements InsertLink<T,L> {

}
