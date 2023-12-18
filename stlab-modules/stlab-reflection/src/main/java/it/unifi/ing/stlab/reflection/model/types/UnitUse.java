package it.unifi.ing.stlab.reflection.model.types;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table( name = "unit_uses" )
public class UnitUse implements Persistable {

	private PersistableImpl persistable;
	private QuantitativeType type;
	private Unit unit;
	private Integer digits;
	private Integer decimals;
	
	
	public UnitUse( String uuid ) {
		persistable = new PersistableImpl( uuid );
	}
	protected UnitUse() {
		persistable = new PersistableImpl();
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="unit_use", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_gen")	
	public Long getId() {
		return persistable.getId();
	}
	protected void setId(Long id) {
		persistable.setId(id);
	}

	
	// UUID
	public String getUuid() {
		return persistable.getUuid();
	}
	public void setUuid(String uuid) {
		persistable.setUuid(uuid);
	}

	
	@ManyToOne
	@JoinColumn( name = "type_id" )
	protected QuantitativeType getType() {
		return type;
	}
	protected void setType(QuantitativeType type) {
		this.type = type;
	}

	
	@ManyToOne
	@JoinColumn( name = "unit_id" )
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	
	// Digits
	public Integer getDigits() {
		return digits;
	}
	public void setDigits(Integer digits) {
		this.digits = digits;
	}

	
	// Decimals
	public Integer getDecimals() {
		return decimals;
	}
	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}


	
	//
	// HashCode & Equals
	//
	
	public int hashCode() {
		return persistable.hashCode();
	}

	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
	
}
