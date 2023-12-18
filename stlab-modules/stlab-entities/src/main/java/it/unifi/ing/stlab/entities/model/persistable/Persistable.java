package it.unifi.ing.stlab.entities.model.persistable;

// e' un'entita' persistente sul DB
public interface Persistable {

	public Long getId();
	public String getUuid();

	public int hashCode();
	public boolean equals(Object obj);

}