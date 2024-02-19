package it.unifi.ing.stlab.view.dsl;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.view.factory.TypeSelectorFactory;
import it.unifi.ing.stlab.view.factory.ViewerFactory;
import it.unifi.ing.stlab.view.factory.ViewerLinkFactory;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.Tab;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;
import it.unifi.ing.stlab.view.model.widgets.container.*;
import it.unifi.ing.stlab.view.model.widgets.input.*;
import it.unifi.ing.stlab.view.model.widgets.output.*;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public abstract class BaseVistaParser extends Parser {

	private EntityManager entityManager;
	private final List<Exception> errors;
	private final List<Type> ossStack;
	
	private Type viewerType;
	
	public BaseVistaParser(TokenStream stream) {
		super(stream);
		ossStack = new ArrayList<Type>();
		errors = new LinkedList<Exception>();
	}

	public BaseVistaParser(TokenStream stream, RecognizerSharedState recognizer) {
		super(stream, recognizer);
		ossStack = new ArrayList<Type>();
		errors = new LinkedList<Exception>();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public void reportError(RecognitionException e) {
		String header = getErrorHeader(e);
    	String content = getErrorMessage(e, getTokenNames());
    	
		extendedReportError(new RuntimeException(header+" "+content) );
	}
	public void extendedReportError( Exception e ) {
		errors.add( e );
	}
	
	public boolean isOk() {
		return errors.isEmpty();
	}
	public boolean hasErrors() {
		return !isOk();
	}
	public String errorReport() {
		if ( isOk() ) return "OK";
		
		StringBuffer result = new StringBuffer();
		for ( Exception e : errors ) {
			result.append( e.getMessage() ).append( "\n" );
		}
		return result.toString();
	}

	protected Label createLabel( String value ) {
		Label result = ViewerFactory.createLabel();
		result.setValue( value.replaceAll( "\"", "" ));
		
		return result;
	}

	protected OutputValue createOutputValue() {
		OutputValue result = ViewerFactory.createOutputValue();
		return result;
	}

	protected OutputType createOutputType() {
		OutputType result = ViewerFactory.createOutputType();
		return result;
	}

	protected OutputMeasurementUnit createOutputMeasurementUnit() {
		OutputMeasurementUnit result = ViewerFactory.createOutputUnit();
		return result;
	}

	protected OutputPath createOutputPath() {
		OutputPath result = ViewerFactory.createOutputPath();
		return result;
	}

	protected OutputList createOutputList( boolean vertical ) {
		OutputList result = ViewerFactory.createOutputList();
		if ( vertical ) {
			result.setOrientation( PanelOrientation.vertical );
		} else {
			result.setOrientation( PanelOrientation.horizontal );
		}
		
		return result;
	}

	protected OutputField createOutputField(){
		OutputField result = ViewerFactory.createOutputField();

		return result;
	}
	
	protected OutputImage createOutputImage(String path){
		OutputImage result = ViewerFactory.createOutputImage();
		result.setImagePath(path.replaceAll( "\"", "" ));

		return result;
	}
	
	protected OutputLink createOutputLink() {
		OutputLink result = ViewerFactory.createOutputLink();
		return result;
	}
	
	protected InputText createInputText( boolean required, Integer length, boolean readOnly ) {
		InputText result = ViewerFactory.createInputText();
		result.setRequired(required);
		result.setInputLength(length);
		result.setReadOnly(readOnly);

		return result;
	}
	
	protected InputTemporal createInputTemporal( boolean required, boolean readOnly ) {
		InputTemporal result = ViewerFactory.createInputTemporal();
		result.setRequired(required);
		result.setReadOnly(readOnly);

		return result;
	}

	protected TextArea createTextArea( boolean required, Integer length, boolean readOnly  ) {
		TextArea result = ViewerFactory.createTextArea();
		result.setRequired(required);
		result.setInputLength(length);
		result.setReadOnly(readOnly);

		return result;
	}

	protected Combo createCombo( boolean required, boolean readOnly ) {
		Combo result = ViewerFactory.createCombo();
		result.setRequired(required);
		result.setReadOnly(readOnly);

		return result;
	}

	protected Suggestion createSuggestion( boolean required ) {
		Suggestion result = ViewerFactory.createSuggestion();
		result.setRequired(required);

		return result;
	}

	protected InputList createInputList( boolean vertical ) {
		InputList result = ViewerFactory.createInputList();
		if ( vertical ) {
			result.setOrientation( PanelOrientation.vertical );
		} else {
			result.setOrientation( PanelOrientation.horizontal );
		}

		return result;
	}
	
	protected FileUpload createFileUpload( String value ) {
		FileUpload result = ViewerFactory.createFileUpload();
		result.setAcceptedTypes( value.replaceAll( "\"", "" ) );

		return result;
	}


	protected Box createBox() {
		Box result = ViewerFactory.createBox();
		result.setCollapse( false );

		return result;
	}

	protected Grid createGrid( PanelOrientation style ) {
		Grid result = ViewerFactory.createGrid();
		result.setOrientation( style );
		result.setCollapse( false );

		return result;
	}
	
	protected Paragraph createParagraph() {
		Paragraph result = ViewerFactory.createParagraph();
		result.setCollapse( false );

		return result;
	}

	protected ConditionalPanel createConditionalPanel() {
		ConditionalPanel result = ViewerFactory.createConditionalPanel();
		result.setCollapse( false );

		return result;
	}
	
	protected TabbedPanel createTabbedPanel(){
		TabbedPanel result = ViewerFactory.createTabbedPanel();
		result.setCollapse( false );

		return result;
	}
	
	protected Tab createTab(String label, TypeSelector s, Viewer v){
		Tab result = (Tab)ViewerLinkFactory.createTab();
		result.setLabel(label.replaceAll( "\"", "" ));
		result.setSelector(s);
		result.assignTarget(v);
		
		return result;
	}

	protected Report createReport(){
		Report result = ViewerFactory.createReport();
		result.setCollapse( false );

		return result;
	}
	
	protected FactPanel createFactPanel(){
		FactPanel result = ViewerFactory.createFactPanel();
		result.setCollapse( false );

		return result;
	}
	
	protected ViewerCustom createCustom( String name ){
		ViewerCustom result = new ViewerCustom(UUID.randomUUID().toString());
		result.init();
		result.setXhtmlFileName( name.replaceAll( "\"", "" ) );
		
		return result;
	}
	
	protected Viewer findVista( String nome ) {
		List<?> resultList = entityManager
									.createQuery( " select distinct v from Viewer v " +
														" where v.name = :name " )
									.setParameter( "name", nome.replaceAll( "\"", "" ) )
									.getResultList();
		
		if ( resultList == null || resultList.size() < 1 )
			throw new RuntimeException( " No viewer with name " + nome );

		if ( resultList.size() > 1 )
			throw new RuntimeException( " More than one viewer with name " + nome );
		
		return ClassHelper.cast( resultList.get( 0 ), Viewer.class );
	}

	protected Phenomenon findFenomeno( TypeSelector selector, String nome ) {
		TypeSelector cur = selector;
		while ( cur.getNext() != null ) {
			cur = cur.getNext();
		}
		
		List<?> resultList = 
				entityManager.createQuery( " select distinct f from QualitativeType t join t.phenomena f " +
											" where t.id = :type_id  and f.name = :name " )
							.setParameter( "type_id", cur.getTypeLink().getTarget().getId() )
							.setParameter( "name", nome.replaceAll( "\"", "" ))
							.setFlushMode( FlushModeType.COMMIT )
							.getResultList();
		
		if ( resultList == null || resultList.size() < 1 )
			throw new RuntimeException( " Nessun fenomeno con nome " + nome + 
											" trovato per l'osservazione "+ cur.getTypeLink().getTarget().getName() );

		if ( resultList.size() > 1 )
			throw new RuntimeException( " More than one phenomenon " + nome +
											" found for the observation "+ cur.getTypeLink().getTarget().getName() );
		
		return ClassHelper.cast( resultList.get( 0 ), Phenomenon.class );
	}
	
	protected TypeSelector createTipoOsservazioneSelector( String itemName ) {
		TypeLink typeLink = null;
		
		try {
			typeLink = findSottotipoOsservazione( itemName );
		} catch(NonUniqueResultException iae){
			if( getLast() == null )
				throw new NonUniqueResultException( " More than one subtype with name " + itemName );
			
			typeLink = findSottotipoOsservazioneAmbigua( getLast(), itemName );
		}
		
		TypeSelector result = TypeSelectorFactory.createSelector();
		result.setTypeLink( typeLink );
		return result;
	}
	
	
	protected void setContext( String nome ){
		List<?> resultList = entityManager
									.createQuery( " select distinct t from Type t " +
													" left join fetch t.children " +
													" where t.name = :name " )
									.setParameter( "name", nome.replaceAll( "\"", "" ) )
									.setFlushMode( FlushModeType.COMMIT )
									.getResultList();
		
		if ( resultList == null || resultList.size() < 1 )
			throw new RuntimeException( " No observation with name " + nome );

		if ( resultList.size() > 1 )
			throw new NonUniqueResultException( " More than one observation with name " + nome );
		
		viewerType = ClassHelper.cast( resultList.get( 0 ), Type.class );
		ossStack.add( viewerType );
		
	}

	protected TypeLink findSottotipoOsservazione( String nome ) {
		List<?> resultList = entityManager
								.createQuery( "select distinct t from TypeLink t " +
												"left join fetch t.target " +
												"where t.name = :name" )
								.setParameter( "name", nome.replaceAll( "\"", "" ) )
								.setFlushMode( FlushModeType.COMMIT )
								.getResultList();
		
		if ( resultList == null || resultList.size() < 1 )
			throw new RuntimeException( "No subtype with name " + nome );

		if ( resultList.size() > 1 )
			throw new NonUniqueResultException( "More than one subtype with name " + nome );
		
		return ClassHelper.cast( resultList.get( 0 ), TypeLink.class );
	}
	
	protected TypeLink findSottotipoOsservazioneAmbigua( Type type, String nomeLink ) {
		List<?> resultList = entityManager
										.createQuery( " select distinct i " +
														" from CompositeType c left join c.children i " +
														" left join fetch i.target " +
														" where c.id = :type and i.name = :nomeLink " )
										.setParameter( "type", type.getId())
										.setParameter( "nomeLink", nomeLink.replaceAll( "\"", "" ))
										.setFlushMode( FlushModeType.COMMIT )
										.getResultList();
		
		if ( resultList == null || resultList.size() < 1 )
			throw new RuntimeException( "No subtype with name " + nomeLink + "contained in \"" + type.getName() +"\"" );

		if ( resultList.size() > 1 )
			throw new NonUniqueResultException( "More than one subtype with name " + nomeLink + "contained in \"" + type.getName() +"\"" );

		return ClassHelper.cast( resultList.get( 0 ), TypeLink.class );
	}
	
	protected FactQuery findFactQuery(String name) {
		List<?> resultList = entityManager
										.createQuery( " select distinct fq from FactQuery fq " +
														" where fq.name = :name" )
										.setParameter( "name", name.replaceAll( "\"", "" ))
										.setFlushMode( FlushModeType.COMMIT )
										.getResultList();
		
		if ( resultList == null || resultList.size() < 1 )
			throw new RuntimeException( "No factQuery with name " + name );

		if ( resultList.size() > 1 )
			throw new NonUniqueResultException( "More than one factQuery with name " + name + " ( impossible! ) " );
		
		return ClassHelper.cast( resultList.get( 0 ), FactQuery.class );
	}
	
	protected SubViewer createSottoVista( Viewer v ) {
		SubViewer result = (SubViewer)ViewerLinkFactory.createSubViewer();
		result.assignTarget( v );
		return result;
	}
	
	protected SubViewer createSottoVista( TypeSelector s, Viewer v ) {
		SubViewer result = (SubViewer)ViewerLinkFactory.createSubViewer();
		result.setSelector( s );
		result.assignTarget( v );
		return result;
	}
	
	
	protected void assignType(Viewer root) {
		if( viewerType == null )
			return;
		
		root.setType( viewerType );
	}
	
	
	//
	// Stack methods
	//
	
	protected void popStack(TypeSelector s){
		if(ossStack.isEmpty())
			return;
		
		if(s == null)
			return;
		
		for(int i=0; i<s.getLength(); i++)
			ossStack.remove(0);
		
	}
	
	protected Type getLast(){
		if(ossStack.isEmpty())
			return null;
		
		return ossStack.get(0);
	}
	
	protected void pushStack(TypeSelector s){
		if(s == null)
			return;
	
		ossStack.add(0, s.getTypeLink().getTarget());
		
	}
	
	protected void clearStack(){
		ossStack.clear();
	}

}
