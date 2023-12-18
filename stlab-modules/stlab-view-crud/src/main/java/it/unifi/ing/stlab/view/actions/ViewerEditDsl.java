package it.unifi.ing.stlab.view.actions;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.RollbackException;
import javax.transaction.UserTransaction;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.hibernate.exception.ConstraintViolationException;

import it.unifi.ing.stlab.commons.viewers.SuggestionInterface;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.dsl.ViewerEncoderVisitor;
import it.unifi.ing.stlab.view.dsl.VistaLexer;
import it.unifi.ing.stlab.view.dsl.VistaParser;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.visitor.EditViewerGeneratorVisitor;
import it.unifi.ing.stlab.view.visitor.OutputViewerGeneratorVisitor;
import it.unifi.ing.stlab.view.visitor.ViewerGeneratorVisitor;

@Named
@Stateful
@ConversationScoped
@TransactionManagement( TransactionManagementType.BEAN )
public class ViewerEditDsl {

	//
	// CDI Injections
	//
	@Inject
	private FacesContext facesContext;

	@Inject
	private Conversation conversation;

	@PersistenceContext( type = PersistenceContextType.EXTENDED )
	private EntityManager entityManager;

	@Resource
	private UserTransaction utx;

	//
	// EJB Injections
	//
	@EJB
	private ViewerDao viewerDao;

	@EJB
	private TypeDao typeDao;

	//
	// Local attributes
	//
	private Viewer viewer;
	private Type type;

	private String name;
	private String definition;
	private String css;

	private TypeSuggestion typeSuggestion;

	private boolean preview;

	@PostConstruct
	public void init() {
		initTypeSuggestion();
	}

	//
	// navigation methods
	//
	public String save() {
		try {
			utx.setTransactionTimeout( 1200 );
			utx.begin();

			viewer.setName( name );
			viewer.setAnonymous( false );

			if ( css != null && !css.isEmpty() )
				viewer.setCss( css );

			GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ) );

			viewerDao.save( viewer );

			utx.commit();
			conversation.end();

			message( FacesMessage.SEVERITY_INFO, "Vista salvata con successo!", true );

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
		conversation.end();
		return "cancel";
	}
	
	
	public void validate() {
		if ( definition == null || definition.isEmpty() )
			return;

		try {
			VistaLexer lex = new VistaLexer(
					new ANTLRInputStream( new ByteArrayInputStream( definition.getBytes() ) ) );
			CommonTokenStream tokens = new CommonTokenStream( lex );
			VistaParser parser = new VistaParser( tokens );

			parser.setEntityManager( entityManager );

			if ( viewer != null ) {
				viewer.delete();
				reset();
			}

			viewer = parser.parse();

			message( FacesMessage.SEVERITY_INFO, "Validazione eseguita con successo!", false );
		} catch ( Exception e ) {
			recoverException( e );
		}
	}	

	public void generateEditViewer() {
		if ( type == null )
			return;

		generate( new EditViewerGeneratorVisitor() );
	}

	public void generateOutputViewer() {
		if ( type == null )
			return;

		generate( new OutputViewerGeneratorVisitor() );
	}

	public void preview( boolean value ) {
		preview = value;
	}

	public boolean checkPreview() {
		return type != null && viewer != null;
	}

	public boolean canSave() {
		return viewer != null && 
			name != null && !name.isEmpty() && 
			definition != null && !definition.isEmpty();
	}

	//
	// get & set methods
	//
	public Viewer getViewer() {
		return viewer;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType( Type type ) {
		this.type = type;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition( String definition ) {
		this.definition = definition;
	}

	public String getCss() {
		return css;
	}

	public void setCss( String css ) {
		this.css = css;
	}

	public boolean isPreviewEnabled() {
		return preview;
	}

	public TypeSuggestion getTypeSuggestion() {
		return typeSuggestion;
	}

	//
	// private methods
	//
	private void reset() {
		viewer = null;
	}

	private void recoverException( Exception e ) {
		message( FacesMessage.SEVERITY_ERROR, e.getMessage(), false );
		reset();
	}

	private void generate( ViewerGeneratorVisitor visitor ) {
		Type selectedType = typeDao.fetchById( type.getId() );
		selectedType.accept( visitor );

		viewer = visitor.getResult();
		viewer.setCss( getDefaultCss() );
		viewer.setType( type );

		encodeViewer();
	}

	private void encodeViewer() {
		ViewerEncoderVisitor visitor = new ViewerEncoderVisitor();
		viewer.accept( visitor );

		String baseDefinition = "\"" + viewer.getType().getName() + "\"\n";
		definition = baseDefinition + visitor.getDefinition();
		css = viewer.getCss();
	}

	private String getDefaultCss() {
		return ".fieldset {background-color: #f9f9f9; margin: 5px;}\n"
				+ ".fieldset .fieldset {background-color: white;}\n"
				+ ".fieldset .fieldset  .fieldset {background-color: #f9f9f9;}\n"
				+ ".label_text {font-weight: bold;}\n" + ".outputPath_text {font-weight: bold;}\n"
				+ ".grid_table .grid_cell_0 {width: 25%; text-align: right; padding-right: 15px; vertical-align: top;}\n"
				+ ".grid_cell_1 .grid_table {width: auto;}\n"
				+ ".grid_cell_1 .grid_table .grid_cell_0 {padding-right: 0px; width: auto;}\n"
				+ ".grid_cell_1 .grid_table .grid_cell_1 {padding-left: 6px;}\n"
				+ ".box_label {font-weight: bold; background: transparent; background-image: -webkit-linear-gradient(bottom, #f9f9f9 50%, transparent 50%); padding-left: 6px; padding-right: 6px;}\n"
				+ ".fieldset .fieldset .box_label {font-weight: bold; background: transparent; background-image: -webkit-linear-gradient(bottom, transparent 50%, #f9f9f9 50%); padding-left: 6px; padding-right: 6px;}\n"
				+ ".fieldset .fieldset .fieldset .box_label {font-weight: bold; background: transparent; background-image: -webkit-linear-gradient(bottom, #f9f9f9 50%, transparent 50%); padding-left: 6px; padding-right: 6px;}\n"
				+ ".fieldset {padding: 10px 0 3px 0;}\n";
	}

	private void initTypeSuggestion() {
		typeSuggestion = new TypeSuggestion();
	}

	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}

	//
	// Internal class for type suggestion
	//
	public class TypeSuggestion implements SuggestionInterface {

		private String suggestion;

		@Override
		public List<SelectItem> autocomplete( String suggestion ) {
			List<SelectItem> result = new ArrayList<SelectItem>();
			List<Type> types = typeDao.findBySuggestion( suggestion, 0 );

			for ( Type t : types ) {
				result.add( new SelectItem( t.getUuid(), t.getName() ) );
			}

			return result;
		}

		public String getSuggestion() {
			return suggestion;
		}

		public void setSuggestion( String suggestion ) {
			this.suggestion = suggestion;
		}

		public void clear() {
			suggestion = null;
		}
	}

}
