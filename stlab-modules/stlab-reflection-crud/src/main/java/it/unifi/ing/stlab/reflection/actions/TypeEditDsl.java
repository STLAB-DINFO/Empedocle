package it.unifi.ing.stlab.reflection.actions;

import java.io.ByteArrayInputStream;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.RollbackException;
import javax.transaction.UserTransaction;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.hibernate.exception.ConstraintViolationException;

import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.dsl.TypeLexer;
import it.unifi.ing.stlab.reflection.dsl.TypeParser;
import it.unifi.ing.stlab.reflection.model.types.Type;

@Named
@Stateful
@ViewScoped
@TransactionManagement( TransactionManagementType.BEAN )
public class TypeEditDsl {

	//
	// CDI injections
	//
	@PersistenceContext( type = PersistenceContextType.EXTENDED )
	private EntityManager entityManager;

	@Resource
	private UserTransaction utx;

	@Inject
	private FacesContext facesContext;

	//
	// EJB injections
	//
	@EJB
	private TypeDao typeDao;

	//
	// Local attributes
	//
	private String name;
	private String definition;
	private Type type;

	//
	// navigation methods
	//
	public String save() {
		try {
			utx.setTransactionTimeout( 600 );
			utx.begin();

			type.setName( name );
			type.setAnonymous( false );
			type.setRecurrent( false );  //FIXME in questo modo ignora la direttiva recurrent via dsl
			type.setReadOnly( false );

			GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ) );

			typeDao.save( type );

			utx.commit();
			message( FacesMessage.SEVERITY_INFO, "Tipo salvato con successo!", true );
		} catch ( RollbackException e ) {
			Throwable t = e.getCause();
			
			while ( ( t != null ) && !( t instanceof ConstraintViolationException ) ) {
				t = t.getCause();
			}
			
			if ( t instanceof ConstraintViolationException ) {
				message( FacesMessage.SEVERITY_ERROR,
						"Impossibile effettuare il salvataggio: nome '" + name + "' già in uso!",
						true );
			}
		} catch ( Exception e ) {
			message( FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio!", true );
		}
		return "save";
	}

	public String cancel() {
		return "cancel";
	}

	public boolean canSave() {
		return type != null && 
				name != null && !name.isEmpty() && 
				definition != null && !definition.isEmpty();
	}

	
	public void validate() {
		if ( definition == null || definition.isEmpty() )
			return;

		try {
			TypeLexer lex = new TypeLexer(
					new ANTLRInputStream( new ByteArrayInputStream( definition.getBytes() ) ) );
			CommonTokenStream tokens = new CommonTokenStream( lex );
			TypeParser parser = new TypeParser( tokens );

			parser.setEntityManager( entityManager );

			if ( type != null ) {
				type.delete();
				type = null;
			}

			type = parser.parse();

			message( FacesMessage.SEVERITY_INFO, "Validazione eseguita con successo!", false );
		} catch ( Exception e ) {
			recoverException( e );
		}
	}

	//
	// get & set methods
	//
	public String getDefinition() {
		return definition;
	}
	public void setDefinition( String definition ) {
		this.definition = definition;
	}

	public String getName() {
		return name;
	}
	public void setName( String name ) {
		this.name = name;
	}

	//
	// private methods
	//
	private void reset() {
		type = null;
	}

	// private void notifyStaleType() {
	// facesContext.addMessage(null, new
	// FacesMessage(FacesMessage.SEVERITY_WARN, "La definizione del tipo è
	// cambiata, rivalida il viewer", ""));
	// }

	private void recoverException( Exception e ) {
		message( FacesMessage.SEVERITY_ERROR, e.getMessage(), false );
		reset();
	}

	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}
}
