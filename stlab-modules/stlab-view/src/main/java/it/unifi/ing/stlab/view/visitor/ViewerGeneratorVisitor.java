package it.unifi.ing.stlab.view.visitor;

import it.unifi.ing.stlab.reflection.model.types.TypeVisitor;
import it.unifi.ing.stlab.view.model.Viewer;

public abstract class ViewerGeneratorVisitor implements TypeVisitor {
	
	public abstract Viewer getResult();
}
