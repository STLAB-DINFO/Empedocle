package it.unifi.ing.stlab.view.model.widgets.input;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerInput;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("SUGGESTION")
public class Suggestion extends ViewerInput {

	public Suggestion(String uuid) {
		super(uuid);
	}
	protected Suggestion() {
		super();
	}

	@Transient
	@Override
	public String getXhtml() {
		return "../component/suggestion.xhtml";
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitSuggestion(this);
	}
	
}