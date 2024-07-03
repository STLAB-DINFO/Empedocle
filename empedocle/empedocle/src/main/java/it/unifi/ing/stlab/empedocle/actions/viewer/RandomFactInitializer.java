package it.unifi.ing.stlab.empedocle.actions.viewer;

import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDao;
import it.unifi.ing.stlab.reflection.impl.visitor.type.FactRandomFactoryVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.visitor.fact.FactRandomInitializerVisitor;
import it.unifi.ing.stlab.view.actions.FactInitializer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RandomFactInitializer implements FactInitializer {

	@Inject
	private PhenomenonDao phenomenonDAO;
	
	@Override
	public Fact init(Type type) {
		FactRandomFactoryVisitor factory = new FactRandomFactoryVisitor();
		type.accept( factory );
		Fact result = factory.getFact();
		
		FactRandomInitializerVisitor initializer = new FactRandomInitializerVisitor();
		initializer.setPhenomenonDAO(phenomenonDAO);
		result.accept(initializer);
		
		//ExaminationRandomInitializer visitaRandom = new ExaminationRandomInitializer();
		//result.setContext(visitaRandom.initNewExamination());
		
		return result;
	}

}
