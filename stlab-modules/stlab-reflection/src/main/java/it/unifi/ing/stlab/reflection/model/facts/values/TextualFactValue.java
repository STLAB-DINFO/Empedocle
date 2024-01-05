package it.unifi.ing.stlab.reflection.model.facts.values;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "TX" )
public class TextualFactValue extends FactValue {

	private String text;
	
	
	public TextualFactValue(String uuid) {
		super(uuid);
	}
	protected TextualFactValue() {
		super();
	}
	
	
	@Lob
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	@Transient
	@Override
	public boolean isEmpty() {
        return this.getText() == null || "".equals(this.getText().trim());
    }
	
	@Override
	public void accept(FactValueVisitor v) {
		v.visitTextual(this);
		
	}
	
	@Override
	@Transient
	public String getValue() {
		return text;
		
	}
	
}
