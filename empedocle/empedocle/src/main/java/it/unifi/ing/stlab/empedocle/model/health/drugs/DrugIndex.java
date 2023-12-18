package it.unifi.ing.stlab.empedocle.model.health.drugs;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Digits;

import it.unifi.ing.stlab.empedocle.model.health.coding.atc.ATCCode;
import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;

@Entity
@Table(name = "drug_index")
public class DrugIndex implements Persistable {
	
	private PersistableImpl persistable;
	
	private String brandName;
	private String manufacturer;
	private String packaging;
	private DrugClass drugClass;
	private DrugType drugType;
	private PrescriptionType prescriptionType;
	private ATCCode atc;
	private String aic;
	private Set<AifaNote> aifaNotes;
	private Set<UpdateInfo> updateInfos;
	private BigDecimal price;
	
	private Phenomenon phenomenon;

	public DrugIndex( String uuid ) {
		persistable = new PersistableImpl( uuid );
	}
	protected DrugIndex() {
		persistable = new PersistableImpl();
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="drug_index", 
		allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_gen")	
	public Long getId() {
		return persistable.getId();
	}
	protected void setId(Long id) {
		persistable.setId(id);
	}

	
	public String getUuid() {
		return persistable.getUuid();
	}
	protected void setUuid(String uuid) {
		persistable.setUuid(uuid);
	}
	
	@Column( name = "brand_name" )
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName( String brandName ) {
		this.brandName = brandName;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer( String manufacturer ) {
		this.manufacturer = manufacturer;
	}
	
	public String getPackaging() {
		return packaging;
	}
	public void setPackaging( String packaging ) {
		this.packaging = packaging;
	}
	
	@Column( name = "drug_class" )
	@Enumerated( EnumType.STRING )
	public DrugClass getDrugClass() {
		return drugClass;
	}
	public void setDrugClass( DrugClass drugClass ) {
		this.drugClass = drugClass;
	}
	
	@Column( name = "drug_type" )
	@Enumerated( EnumType.STRING )
	public DrugType getDrugType() {
		return drugType;
	}
	public void setDrugType( DrugType drugType ) {
		this.drugType = drugType;
	}
	
	@Column( name = "prescription_type" )
	@Enumerated( EnumType.STRING )
	public PrescriptionType getPrescriptionType() {
		return prescriptionType;
	}
	public void setPrescriptionType( PrescriptionType prescriptionType ) {
		this.prescriptionType = prescriptionType;
	}
	
	@ManyToOne
	@JoinColumn( name = "atc_id" )
	public ATCCode getAtc() {
		return atc;
	}
	public void setAtc( ATCCode atc ) {
		this.atc = atc;
	}
	
	@ElementCollection( targetClass = AifaNote.class )
	@JoinTable( name = "aifa_notes", joinColumns = @JoinColumn( name = "drug_id" ) )
	@Column( name = "note" )
	@Enumerated( EnumType.STRING )
	public Set<AifaNote> getAifaNotes() {
		return aifaNotes;
	}
	public void setAifaNotes( Set<AifaNote> aifaNotes ) {
		this.aifaNotes = aifaNotes;
	}
	
	@ElementCollection( targetClass = UpdateInfo.class )
	@JoinTable( name = "update_infos", joinColumns = @JoinColumn( name = "drug_id" ) )
	@Column( name = "info" )
	@Enumerated( EnumType.STRING )
	public Set<UpdateInfo> getUpdateInfos() {
		return updateInfos;
	}
	public void setUpdateInfos( Set<UpdateInfo> updateInfos ) {
		this.updateInfos = updateInfos;
	}
	
	@Digits( integer = 5, fraction = 2 )
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice( BigDecimal price ) {
		this.price = price;
	}
	
	@Column( unique = true )
	public String getAic() {
		return aic;
	}
	public void setAic( String aic ) {
		this.aic = aic;
	}
	
	@ManyToOne( cascade = { CascadeType.ALL } )
	@JoinColumn( name = "phen_id" )
	public Phenomenon getPhenomenon() {
		return phenomenon;
	}
	public void setPhenomenon( Phenomenon phenomenon ) {
		this.phenomenon = phenomenon;
	}
}
