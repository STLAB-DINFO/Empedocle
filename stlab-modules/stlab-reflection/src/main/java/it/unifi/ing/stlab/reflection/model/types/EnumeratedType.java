package it.unifi.ing.stlab.reflection.model.types;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;


@Entity
@DiscriminatorValue( "EQL" )
public class EnumeratedType extends QualitativeType {

	private Set<Phenomenon> phenomena;
	
	public EnumeratedType(String uuid) {
		super(uuid);
		phenomena = new HashSet<Phenomenon>();
	}
	protected EnumeratedType() {
		super();
		phenomena = new HashSet<Phenomenon>();
	}

	@OneToMany( cascade = { CascadeType.PERSIST } )
	@JoinTable(
		name="type_phenomena",
	    joinColumns={ @JoinColumn(name = "type_id", referencedColumnName = "id") },
	    inverseJoinColumns={ @JoinColumn( name = "phen_id", referencedColumnName = "id", unique=true) })
	protected Set<Phenomenon> getPhenomena() {
		return phenomena;
	}
	protected void setPhenomena(Set<Phenomenon> phenomena) {
		this.phenomena = phenomena;
	}
	public Set<Phenomenon> listPhenomena() {
		return Collections.unmodifiableSet( phenomena );
	}

	public void addPhenomenon(Phenomenon f) {
		if ( f == null ) return;
		
		phenomena.add(f);
	}
	public void removePhenomenon(Phenomenon f) {
		phenomena.remove(f);
	}
	public void clearPhenomena() {
		phenomena.clear();
	}


	@Override
	public void accept(TypeVisitor visitor) {
		visitor.visitEnumeratedType( this );
	}
}
