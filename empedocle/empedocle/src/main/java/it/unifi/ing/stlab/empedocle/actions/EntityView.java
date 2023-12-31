package it.unifi.ing.stlab.empedocle.actions;

public interface EntityView {
	
	/**
	 * Stabilisce se l'entità è in uso nel sistema, ovvero se è vincolata da FK
	 * 
	 * @return true se l'entità è vincolata da FK
	 */
    boolean isUsed();
	
	/**
	 * Chiude la vista corrente, tornando alla vista 'list'
	 * 
	 * @return navigation-rule
	 */
    String close();
	
	/**
	 * Avvia il caso d'uso 'modifica' per l'entità selezionata
	 * 
	 * @return navigation-rule
	 */
    String edit();
	
	/**
	 * Richiede l'eliminazione dell'entità selezionata
	 * 
	 * @return navigation-rule
	 */
    String delete();

}
