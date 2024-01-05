package it.unifi.ing.stlab.reflection.lite.model.facts;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TextualFactValue;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;

public class TextualFactLite extends FactLite implements TextualFact {

	private String text;
	
	
	//
	// Accessor Methods
	//
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public void assignType(Type type) {
		if ( !isValidType( type ))
			throw new IllegalArgumentException();
		
		setType( type );
	}

	//
	// Methods
	//
	
	@Override
	public boolean isEmpty() {
        return text == null || "".equals(text.trim());
    }
	
	@Override
	public void assignDefaultValue(FactValue defaultValue) {
		if ( !ClassHelper.instanceOf(defaultValue, TextualFactValue.class )) {
			return;
		}
		
		TextualFactValue textualValue = ClassHelper.cast(defaultValue, TextualFactValue.class );
		setText( textualValue.getText() );
	}
	
	@Override
	public void accept(FactVisitor visitor) {
		visitor.visitTextual( this );
	}

	
	private boolean isValidType( Type type ) {
		if ( type == null ) return true;
        return ClassHelper.instanceOf(type, TextualType.class);
    }
}
