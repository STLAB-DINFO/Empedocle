package it.unifi.ing.stlab.empedocle.actions;


public interface EntityEditor {
	
	/**
	 * Interrompe casi d'uso Modifica, Crea Nuovo
	 * @return	nav-rule
	 */
	public String cancel();
	
	/**
	 * Salva o aggiorna l'elemento corrente
	 * @return	nav-rule
	 */
	public String save();
	
	/**
	 * Elimina l'elemento correntemente selezionato
	 * @return	nav-rule
	 */
	public String delete();
	

}
