package it.unifi.ing.stlab.reflection.impl.model.facts;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QualitativeFactValue;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;
import it.unifi.ing.stlab.reflection.model.types.Type;

import javax.persistence.*;

@Entity
@DiscriminatorValue( "QL" )
public class QualitativeFactImpl extends FactImpl implements QualitativeFact {

	private Phenomenon phenomenon;
	
	
	public QualitativeFactImpl(String uuid) {
		super(uuid);
	}
	protected QualitativeFactImpl() {
		super();
	}

	
	@Override
	public void assignType(Type type) {
		if ( !isValidType( type ))
			throw new IllegalArgumentException();
		
		super.setType(type);
	}
	private boolean isValidType( Type type ) {
		if ( type == null ) return true;
        return ClassHelper.instanceOf(type, QualitativeType.class);
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
        return phenomenon == null;
    }

	@Override
	public boolean sameAs(FactImpl obj) {
		if ( getType() == null ) return false;
		if ( !ClassHelper.instanceOf(obj, QualitativeFactImpl.class )) return false;
		
		QualitativeFactImpl other = ClassHelper.cast( obj, QualitativeFactImpl.class );
		
		return getType().equals( other.getType() ) && 
				(( isEmpty() && other.isEmpty() ) || 
				 ( !isEmpty() && getPhenomenon().equals( other.getPhenomenon() )));
	}

	@Override
	public QualitativeFactImpl copy() {
		QualitativeFactImpl result = FactFactory.createQualitative();
		result.setType( getType() );
		result.setTimeRange( getTimeRange() );
		result.setContext( getContext() );
		result.setStatus( getStatus() );
		result.setPhenomenon( getPhenomenon() );
		return result;
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
	

}
