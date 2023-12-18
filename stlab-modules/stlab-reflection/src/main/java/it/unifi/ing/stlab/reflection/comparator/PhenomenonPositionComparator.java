package it.unifi.ing.stlab.reflection.comparator;

import it.unifi.ing.stlab.reflection.model.types.Phenomenon;

import java.util.Comparator;

public class PhenomenonPositionComparator implements Comparator<Phenomenon>{

	@Override
	public int compare(Phenomenon p1, Phenomenon p2) {
		return p1.getPosition().compareTo(p2.getPosition());
	}
	
}
