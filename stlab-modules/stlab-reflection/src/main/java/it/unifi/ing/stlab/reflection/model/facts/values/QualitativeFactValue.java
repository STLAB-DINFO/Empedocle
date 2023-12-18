package it.unifi.ing.stlab.reflection.model.facts.values;

import it.unifi.ing.stlab.reflection.model.types.Phenomenon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "QL" )
public class QualitativeFactValue extends FactValue {

	private Phenomenon phenomenon;

	public QualitativeFactValue(String uuid) {
		super(uuid);
	}
	protected QualitativeFactValue() {
		super();
	}
	
	
	@ManyToOne
	@JoinColumn( name = "phen_id" )
	public Phenomenon getPhenomenon() {
		return phenomenon;
	}
	public void setPhenomenon(Phenomenon phenomenon) {
		this.phenomenon = phenomenon;
	}
	
	@Transient
	@Override
	public boolean isEmpty() {
		return this.getPhenomenon() == null;
	}
	
	@Override
	public void accept(FactValueVisitor v) {
		v.visitQualitative(this);
		
	}
	
	@Override
	@Transient
	public String getValue() {
		if(phenomenon != null) {
			return phenomenon.getName();
		}
		else {
			return null;
		}
	}
	
}
