package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.view.model.widgets.output.OutputField;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class OutputFieldController {

	@Inject
	private Instance<FieldRetriever> retriever;
	
	public String obtainField(OutputField outputField, Fact fact){
		return retriever.get().retrieve(fact, outputField.getPath());
		
	}
	
}
