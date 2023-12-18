package it.unifi.ing.stlab.empedocle.model.health;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "icd9cm")
public class Icd9cm implements Persistable {

	private PersistableImpl persistable;
	private String code;
	private String description;
	private Phenomenon phenomenon;
	
	
	public Icd9cm( String uuid ) {
		persistable = new PersistableImpl( uuid );
	}
	protected Icd9cm() {
		persistable = new PersistableImpl();
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="icd9cm", 
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
	
	@Column( unique = true )
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Lob
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@ManyToOne( cascade = { CascadeType.ALL } )
	@JoinColumn( name = "phen_id" )
	public Phenomenon getPhenomenon() {
		return phenomenon;
	}
	public void setPhenomenon(Phenomenon phenomenon) {
		this.phenomenon = phenomenon;
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
	
	

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	@Column(unique = true, length = 10)
//	public String getCodice() {
//		return codice;
//	}
//	public void setCodice(String codice) {
//		this.codice = codice;
//	}
//
//	public String getDescrizione() {
//		return descrizione;
//	}
//	public void setDescrizione(String descrizione) {
//		this.descrizione = descrizione;
//	}
//
//	@ManyToOne
//	@JoinColumn(name = "codice_padre")
//	public Icd9cm getCodicePadre() {
//		return codicePadre;
//	}
//	public void setCodicePadre(Icd9cm codicePadre) {
//		this.codicePadre = codicePadre;
//	}
//
//	public boolean sameAs(Icd9cm other) {
//		if(this == other) {
//			return true;
//		}
//		
//		if (other == null) {
//			return false;
//		}
//
//		if (isEmpty() || other.isEmpty()) {
//			return false;
//		}
//
//		if (getCodice() != null) {
//			if (!getCodice().equals(other.getCodice())) {
//				return false;
//			}
//		} else if (other.getCodice() != null) {
//			return false;
//		}
//
//		if (getDescrizione() != null) {
//			if (!StringUtils.equivalent(getDescrizione(),
//					other.getDescrizione())) {
//				return false;
//			}
//		} else if (other.getDescrizione() != null) {
//			return false;
//		}
//
//		if (getCodicePadre() != null) {
//			if (!getCodicePadre().sameAs(other.getCodicePadre())) {
//				return false;
//			}
//		} else if (other.getCodicePadre() != null) {
//			return false;
//		}
//
//		return true;
//	}
//
//	@Transient
//	public boolean isEmpty() {
//		return StringUtils.isEmpty(getCodice())
//				&& StringUtils.isEmpty(getDescrizione())
//				&& getCodicePadre() == null;
//	}
//
}
