package it.unifi.ing.stlab.reflection.actions;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.richfaces.event.TreeSelectionChangeEvent;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.reflection.actions.wrappers.TypeBean;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;

@Named
@ViewScoped
public class TypeView extends TypeController {

	private static final long serialVersionUID = -8897562391028061443L;

	//
	// EJB injections
	//
	@Inject
	private TypeDao typeDao;

	//
	// HttpParam injections
	//
	@Inject
	@HttpParam( "id" )
	private String id;

	//
	// Local attributes
	//
	private TypeBean current;
	private FactValue defaultValue;

	
	@PostConstruct
	public void init() {
		current = new TypeBean( null, typeDao.fetchById( Long.parseLong( id ) ) );
	}

	public String getId() {
		return id;
	}

	@Override
	public void selectionChanged( TreeSelectionChangeEvent selectionChangeEvent ) {
		super.selectionChanged( selectionChangeEvent );
		defaultValue = null;
	}

	//
	// navigation methods
	//
	public String close() {
		return "close";
	}

	//
	// get methods
	//
	public TypeBean getCurrent() {
		return current;
	}

	public FactValue getDefaultValue() {
		if ( defaultValue == null ) {
			initDefaultValue();
		}
		return defaultValue;
	}

	private void initDefaultValue() {
		if ( isCurrentLinkSet() ) {
			defaultValue = typeDao.fetchFactValue( getSelectedLink().getLink() );
		}
	}

	@Override
	protected EntityManager getEntityManager() {
		return null;
	}
}
