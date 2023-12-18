package it.unifi.ing.stlab.entities.implementation.persistable;

import it.unifi.ing.stlab.entities.model.persistable.Persistable;

public class PersistableImpl implements Persistable {

	private Long id;
	private String uuid;


	public PersistableImpl(String uuid) {
		super();
		this.uuid = uuid;
	}
	public PersistableImpl() {
		super();
	}

	
	@Override
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + uuid.hashCode();
		return result;
	}

	
	//due oggetti sono uguali se hanno lo stesso uuid
	// un oggetto ancora non persistito ha l'uuid che viene passato nel costruttore, mentre l'id gli viene assegnato solo quando si fa il persist sul DB
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!( obj instanceof Persistable )) return false;
		if(uuid == null) return false;
		
		Persistable other = (Persistable) obj;
		return uuid.equals( other.getUuid() );
	}
}