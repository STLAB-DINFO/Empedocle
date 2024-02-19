package it.unifi.ing.stlab.reflection.impl.model.facts;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TextualFactValue;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue( "TX" )
public class TextualFactImpl extends FactImpl implements TextualFact {

	private String text;
	
	public TextualFactImpl(String uuid) {
		super(uuid);
	}
	protected TextualFactImpl() {
		super();
	}

	
	@Override
	public void assignType(Type type) {
		if ( !isValidType( type ))
			throw new IllegalArgumentException();
		
		setType( type );
	}

	private boolean isValidType( Type type ) {
		if ( type == null ) return true;
        return ClassHelper.instanceOf(type, TextualType.class);
    }
	
	
	
	@Lob
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	
	//
	// Methods
	//
	
	@Transient
	@Override
	public boolean isEmpty() {
        return text == null || "".equals(text.trim());
    }

	@Override
	public boolean sameAs(FactImpl obj) {
		if ( getType() == null ) return false;
		if ( !ClassHelper.instanceOf(obj, TextualFactImpl.class )) return false;
		
		TextualFactImpl other = ClassHelper.cast( obj, TextualFactImpl.class );
		
		return getType().equals( other.getType() ) && 
				(( isEmpty() && other.isEmpty() ) || 
					( !isEmpty() && getText().equals( other.getText() )));
	}

	@Override
	public TextualFactImpl copy() {
		TextualFactImpl result = FactFactory.createTextual();
		result.setType( getType() );
		result.setTimeRange( getTimeRange() );
		result.setContext( getContext() );
		result.setStatus( getStatus() );
		result.setText( getText() );
		return result;
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
}
