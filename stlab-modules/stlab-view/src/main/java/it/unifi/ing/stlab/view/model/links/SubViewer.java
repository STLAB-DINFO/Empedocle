package it.unifi.ing.stlab.view.model.links;

import it.unifi.ing.stlab.entities.model.composite.CompositeLink;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.view.model.Viewer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SOTTOVISTA")
public class SubViewer extends ViewerLink
		implements Persistable, CompositeLink<Viewer, ViewerLink>{
	 
	
	public SubViewer(String uuid) {
		super(uuid);
	}
	protected SubViewer() {
		super();
	}

	@Override
	public void assignSource(Viewer newSource) {
		if ( !isValidSource( newSource )) 
			throw new IllegalArgumentException();
		
		super.assignSource(newSource);
	}
	
	private boolean isValidSource(Viewer newSource){
		return newSource.isValidSubViewer(this);
	}
	
}