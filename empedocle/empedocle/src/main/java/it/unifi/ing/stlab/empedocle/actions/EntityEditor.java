package it.unifi.ing.stlab.empedocle.actions;


public interface EntityEditor {
	
	/**
	 * Interrompe casi d'uso Modifica, Crea Nuovo
	 * @return	nav-rule
	 */
    String cancel();
	
	/**
	 * Salva o aggiorna l'elemento corrente
	 * @return	nav-rule
	 */
    String save();
	
	/**
	 * Elimina l'elemento correntemente selezionato
	 * @return	nav-rule
	 */
    String delete();
	

}
