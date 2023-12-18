package it.unifi.ing.stlab.empedocle.presentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SelectionBean<E> {
	
	// TODO prendere il msg come parametro nel componente xhtml
	public final static String EMPTY_MSG = "Nessuna associazione presente";
	
	private E entity;
	private List<E> selectedEntities = new ArrayList<E>();
	private Boolean active;
	
	public SelectionBean(E entity) {
		super();
		this.entity = entity;
		this.active = false;
	}

	public SelectionBean(E entity, Boolean active) {
		super();
		this.entity = entity;
		this.active = active;
	}
	
	public abstract void reset();
	
	public Boolean renderEmptyMessage() {
		return selectedEntities.isEmpty() && !getActive();
	}
	
	public void toggleActive() {
		setActive(! getActive());
	}
	
	public void addSelectedEntity(E entity) {
		selectedEntities.add(entity);
	}
	
	public void addSelectedEntities(Collection<E> entities) {
		selectedEntities.addAll(entities);
	}
	
	public void remove(E entity) {
		this.selectedEntities.remove(entity);
	}

	public E getEntity() {
		return entity;
	}

	public Boolean getActive() {
		return active;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<E> getSelectedEntities() {
		return selectedEntities;
	}

	public void setSelectedEntities(List<E> selectedEntities) {
		this.selectedEntities = selectedEntities;
	}
	
	public String getEmptyMessage() {
		return EMPTY_MSG;
	}
	
}
