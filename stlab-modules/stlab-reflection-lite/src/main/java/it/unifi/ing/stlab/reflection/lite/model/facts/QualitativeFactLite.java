package it.unifi.ing.stlab.reflection.lite.model.facts;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QualitativeFactValue;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;
import it.unifi.ing.stlab.reflection.model.types.Type;

public class QualitativeFactLite extends FactLite implements QualitativeFact {

	private Phenomenon phenomenon;
	
	
	//
	// Accessor Methods
	//
	
	public Phenomenon getPhenomenon() {
		return phenomenon;
	}
	public void setPhenomenon(Phenomenon phenomenon) {
		this.phenomenon = phenomenon;
	}
	
	
	@Override
	public void assignType(Type type) {
		if ( !isValidType( type ))
			throw new IllegalArgumentException();
		
		super.setType(type);
	}
	
	
	//
	// Methods
	//

	@Override
	public boolean isEmpty() {
        return phenomenon == null;
    }
	

	@Override
	public void assignDefaultValue(FactValue defaultValue) {
		if ( !ClassHelper.instanceOf(defaultValue, QualitativeFactValue.class )) {
			return;
		}
		
		QualitativeFactValue textualValue = ClassHelper.cast(defaultValue, QualitativeFactValue.class );
		setPhenomenon( textualValue.getPhenomenon() );
	}
	

	@Override
	public void accept(FactVisitor visitor) {
		visitor.visitQualitative( this );
	}
	
	
	private boolean isValidType( Type type ) {
		if ( type == null ) return true;
        return ClassHelper.instanceOf(type, QualitativeType.class);
    }

}
