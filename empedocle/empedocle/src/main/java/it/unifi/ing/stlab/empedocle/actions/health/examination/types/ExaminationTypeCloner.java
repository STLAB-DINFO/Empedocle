package it.unifi.ing.stlab.empedocle.actions.health.examination.types;

import it.unifi.ing.stlab.empedocle.factory.health.AuthorizationFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationTypeFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ViewerUseFactory;
import it.unifi.ing.stlab.empedocle.model.health.Authorization;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.ViewerUse;

public class ExaminationTypeCloner {

	private ExaminationType original;
	
	public ExaminationTypeCloner(ExaminationType original) {
		this.original = original;
	}
	
	public ExaminationType clone() {
		if( original == null ) throw new RuntimeException( "originale nullo" );
		
		ExaminationType clone = ExaminationTypeFactory.createExaminationType();
		
		// properties
		clone.setName( original.getName() + " (copia)" );
		clone.setDescription( original.getDescription() );
		clone.setTimeToLive( original.getTimeToLive() );
		
		// type
		clone.setType( original.getType() );
		
		// authorizations
		for( Authorization auth : original.getAuthorizations() )
			clone.addAuthorization( cloneAuthorization( auth ) );
		
		// viewers
		for( ViewerUse viewer : original.getViewers() )
			clone.addViewerUse( cloneViewerUse( viewer ) );
		
		return clone;
	}

	private Authorization cloneAuthorization(Authorization auth) {
		Authorization copy = AuthorizationFactory.createAuthorization();
		copy.setExamOperation( auth.getExamOperation() );
		copy.setQualification( auth.getQualification() );
		
		return copy;
	}
	
	private ViewerUse cloneViewerUse(ViewerUse viewer) {
		ViewerUse copy = ViewerUseFactory.createViewerUse();
		copy.setContext( viewer.getContext() );
		copy.setQualification( viewer.getQualification() );
		copy.setViewer( viewer.getViewer() );
		
		return copy;
	}

}
