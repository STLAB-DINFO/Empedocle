package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.cardiology;

import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.StatisticsPathGeneratorVisitor;
import it.unifi.ing.stlab.reflection.model.types.TextualType;

public class CardiologyStatisticsPathGeneratorVisitor extends
		StatisticsPathGeneratorVisitor {

	public CardiologyStatisticsPathGeneratorVisitor(String basePath) {
		super(basePath);
	}
	
	@Override
	protected StatisticsPathGeneratorVisitor getInstance(String basePath) {
		return new CardiologyStatisticsPathGeneratorVisitor(basePath);
	}
	
	@Override
	public void visitTextualType(TextualType type) {
		// scrivo la colonna solo se si tratta di conclusioni
		if( type.getName() != null && 
				type.getName().contains( "Conclusioni" ) ) {
			super.visitTextualType( type );
		}

	}

}
