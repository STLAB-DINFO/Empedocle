package it.unifi.ing.stlab.entities.model.persistable;

// e' un'entita' persistente sul DB
public interface Persistable {

	Long getId();
	String getUuid();

	int hashCode();
	boolean equals(Object obj);

}
