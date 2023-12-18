package it.unifi.ing.stlab.view.dsl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.factquery.factory.FactQueryFactory;
import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.Tab;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.widgets.ViewerContainer;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;
import it.unifi.ing.stlab.view.model.widgets.ViewerInput;
import it.unifi.ing.stlab.view.model.widgets.ViewerOutput;
import it.unifi.ing.stlab.view.model.widgets.container.Box;
import it.unifi.ing.stlab.view.model.widgets.container.ConditionalPanel;
import it.unifi.ing.stlab.view.model.widgets.container.FactPanel;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.container.PanelOrientation;
import it.unifi.ing.stlab.view.model.widgets.container.Report;
import it.unifi.ing.stlab.view.model.widgets.container.TabbedPanel;
import it.unifi.ing.stlab.view.model.widgets.input.Combo;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;
import it.unifi.ing.stlab.view.model.widgets.input.InputText;
import it.unifi.ing.stlab.view.model.widgets.input.Suggestion;
import it.unifi.ing.stlab.view.model.widgets.input.TextArea;
import it.unifi.ing.stlab.view.model.widgets.output.Label;
import it.unifi.ing.stlab.view.model.widgets.output.OutputField;
import it.unifi.ing.stlab.view.model.widgets.output.OutputImage;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputMeasurementUnit;
import it.unifi.ing.stlab.view.model.widgets.output.OutputPath;
import it.unifi.ing.stlab.view.model.widgets.output.OutputType;
import it.unifi.ing.stlab.view.model.widgets.output.OutputValue;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.junit.Before;
import org.junit.Test;


public class VistaParserTest {

	protected EntityManager entityManager;
	protected ViewerDao viewerDao;
	protected Query query;

	@Before
	public void setUp() {
		entityManager = mock( EntityManager.class );
		viewerDao = mock(ViewerDao.class);
		query = mock( Query.class );
		when( entityManager.createQuery( anyString() )).thenReturn( query );
		when( query.setParameter( anyString(), anyObject() ) ).thenReturn( query );
		when( query.setFlushMode( FlushModeType.COMMIT ) ).thenReturn( query );
	}
	
	private VistaParser parser( String expr ) throws Exception {
		VistaLexer lex = new VistaLexer(new ANTLRInputStream( new ByteArrayInputStream( expr.getBytes() )));
		CommonTokenStream tokens = new CommonTokenStream(lex);

		VistaParser parser = new VistaParser(tokens);
		parser.setEntityManager( entityManager );
		
		return parser;
	}
	
	 
	@Test
	public void testLabel() throws Exception {
		Label label = parser( "label \"Hello World\"" ).label(); 
	
		assertEquals( "Hello World", label.getValue() );
	}

	@Test
	public void testOutputValue() throws Exception {
		OutputValue output = parser( "outputValue" ).outputValue();
		assertNotNull( output );
	}

	@Test
	public void testOutputType() throws Exception {
		OutputType output = parser( "outputType" ).outputType();
		assertNotNull( output );
	}

	@Test
	public void testOutputMeasurementUnit() throws Exception {
		OutputMeasurementUnit output = parser( "outputMeasurementUnit" ).outputMeasurementUnit();
		assertNotNull( output );
	}

	@Test
	public void testOutputPathDefault() throws Exception {
		OutputPath output = parser( "outputPath" ).outputPath();
		assertNotNull( output );
	}
	
	@Test
	public void testOutputField() throws Exception {
		OutputField output = parser("out Patient.name").outputField();
		assertNotNull(output);
		
	}
	
	@Test
	public void testOutputImage() throws Exception {
		OutputImage output = parser("outputImage \"img/prova.png\" ").outputImage();
		assertNotNull(output);
		assertEquals("img/prova.png", output.getImagePath());
		
	}

	@Test
	public void testInputTextDefault() throws Exception {
		InputText inputText = parser( "inputText" ).inputText();
		assertNotNull( inputText );
		assertEquals( Boolean.FALSE, inputText.getRequired());
	}

	@Test
	public void testInputTextRequired() throws Exception {
		InputText inputText = parser( "inputText required" ).inputText();
		assertNotNull( inputText );
		assertEquals( Boolean.TRUE, inputText.getRequired());
	}
	
	@Test
	public void testInputTextLength() throws Exception {
		InputText inputText = parser("inputText length=30").inputText();
		assertNotNull(inputText);
		assertEquals(new Integer(30), inputText.getInputLength());
	}

	@Test
	public void testTextAreaDefault() throws Exception {
		TextArea textArea = parser( "textArea" ).textArea();
		assertNotNull( textArea );
		assertEquals( Boolean.FALSE, textArea.getRequired());
	}

	@Test
	public void testTextAreaRequired() throws Exception {
		TextArea textArea = parser( "textArea required" ).textArea();
		assertNotNull( textArea );
		assertEquals( Boolean.TRUE, textArea.getRequired());
	}
	
	@Test
	public void testTextAreaLength() throws Exception {
		TextArea textArea = parser("textArea length=30").textArea();
		assertNotNull(textArea);
		assertEquals(new Integer(30), textArea.getInputLength());
	}
	
	@Test
	public void testComboDefault() throws Exception {
		Combo combo = parser( "combo" ).combo();
		assertNotNull( combo );
		assertEquals( Boolean.FALSE, combo.getRequired());
	}

	@Test
	public void testComboRequired() throws Exception {
		Combo combo = parser( "combo required" ).combo();
		assertNotNull( combo );
		assertEquals( Boolean.TRUE, combo.getRequired());
	}
	
	@Test
	public void testSuggestionDefault() throws Exception {
		Suggestion suggestion = parser( "suggestion" ).suggestion();
		assertNotNull( suggestion );
		assertEquals( Boolean.FALSE, suggestion.getRequired());
	}
	
	@Test
	public void testSuggestionRequired() throws Exception {
		Suggestion suggestion = parser( "suggestion required" ).suggestion();
		assertNotNull( suggestion );
		assertEquals( Boolean.TRUE, suggestion.getRequired());
	}
	
	@Test
	public void testOutputComponent() throws Exception {
		ViewerOutput vista = parser( "label \"Hello\"" ).outputComponent();
		assertNotNull( vista );
		assertTrue( ClassHelper.instanceOf( vista, Label.class ));
	}
	
	@Test
	public void testInputComponent() throws Exception {
		ViewerInput vista = parser( "inputText" ).inputComponent();
		assertNotNull( vista );
		assertTrue( ClassHelper.instanceOf( vista, InputText.class ));
	}
	
	
	@Test
	public void testViewComponent() throws Exception {
		Viewer vista = parser( "inputText" ).viewComponent();
		assertNotNull( vista );
		assertTrue( ClassHelper.instanceOf( vista, InputText.class ));
	}
	
	@Test
	public void testInputListDefault() throws Exception {
		InputList inputList = parser( "inputList { inputText } ").inputList();
		assertNotNull( inputList );
		assertEquals( PanelOrientation.vertical, inputList.getOrientation() );
		assertEquals( 1, inputList.listChildren().size() );
	}

	@Test
	public void testInputListVertical() throws Exception {
		InputList inputList = parser( "inputList vertical { inputText } ").inputList();
		assertNotNull( inputList );
		assertEquals( PanelOrientation.vertical, inputList.getOrientation() );
		assertEquals( 1, inputList.listChildren().size() );
	}
	
	@Test
	public void testInputListHorizontal() throws Exception {
		InputList inputList = parser( "inputList horizontal { inputText } ").inputList();
		assertNotNull( inputList );
		assertEquals( PanelOrientation.horizontal, inputList.getOrientation() );
		assertEquals( 1, inputList.listChildren().size() );
	}
	

	@Test
	public void testOutputListDefault() throws Exception {
		OutputList outputList = parser( "outputList { outputValue } ").outputList();
		assertNotNull( outputList );
		assertEquals( PanelOrientation.vertical, outputList.getOrientation() );
		assertEquals( 1, outputList.listChildren().size() );
	}

	@Test
	public void testOutputListVertical() throws Exception {
		OutputList outputList = parser( "outputList vertical { outputValue } ").outputList();
		assertNotNull( outputList );
		assertEquals( PanelOrientation.vertical, outputList.getOrientation() );
		assertEquals( 1, outputList.listChildren().size() );
	}
	
	@Test
	public void testOutputListHorizontal() throws Exception {
		OutputList outputList = parser( "outputList horizontal { outputValue } ").outputList();
		assertNotNull( outputList );
		assertEquals( PanelOrientation.horizontal, outputList.getOrientation() );
		assertEquals( 1, outputList.listChildren().size() );
	}
	
	@Test
	public void testTabbedPanel() throws Exception{
		TabbedPanel tabbed = parser("tabbedPanel { \"Label\"; : label \"Content\" }").tabbedPanel();
		assertNotNull(tabbed);
		assertEquals(1, tabbed.listChildren().size());
		assertEquals("Label", ((Tab)tabbed.getByPriority(0l)).getLabel());
	}
	
	@Test
	public void testReport() throws Exception{
		Report report = parser("report {: label \"Header\" : label \"Content\" : label \"Footer\"} ").report();
		assertNotNull(report);
		assertEquals(3, report.listChildren().size());
		
	}
	
	@Test
	public void testFactPanel() throws Exception{
		List<FactQuery> results = new ArrayList<FactQuery>();
		results.add( FactQueryFactory.createQuery() );
		when( query.getResultList() ).thenReturn( results );
		
		FactPanel factPanel = parser("factPanel query \"query prova\" {: label \"ciao\"} ").factPanel();
		
		assertNotNull(factPanel);
		assertEquals(1, factPanel.listChildren().size());
		assertNotNull(factPanel.getQuery());
		
	}
	
	@Test
	public void testView() throws Exception {
		Viewer vista = new Label( UUID.randomUUID().toString() );
		List<Viewer> resultList = new ArrayList<Viewer>();
		resultList.add( vista );
		when( query.getResultList() ).thenReturn( resultList );
		
		when(viewerDao.fetchById(vista.getId())).thenReturn(vista);
		
		assertTrue( vista == parser( "view \"Hello\"" ).view() );
	}

	@Test( expected = RuntimeException.class )
	public void testViewNotFound() throws Exception {
		List<Viewer> resultList = new ArrayList<Viewer>();
		when( query.getResultList() ).thenReturn( resultList );
		parser( "view \"Hello\"" ).view();
	}

	@Test
	public void testSelector() throws Exception {
		TypeLink link = TypeLinkFactory.createLink();
		List<TypeLink> resultList = new ArrayList<TypeLink>();
		resultList.add( link );
		when( query.getResultList() ).thenReturn( resultList );

		TypeSelector selector = parser( "\"Battiti prima\"" ).selector();
		
		assertNotNull( selector );
		assertTrue( link == selector.getTypeLink() );
		assertNull( selector.getNext() );
	}
	

	@Test
	public void testSelectorRecursive() throws Exception {
		TypeLink link1 = TypeLinkFactory.createLink();
		List<TypeLink> resultList1 = new ArrayList<TypeLink>();
		resultList1.add( link1 );

		TypeLink link2 = TypeLinkFactory.createLink();
		List<TypeLink> resultList2 = new ArrayList<TypeLink>();
		resultList2.add( link2 );
		
		when( query.getResultList() ).thenReturn( resultList1, resultList2 );

		TypeSelector selector = parser( "\"Prova da sforzo\".\"Battiti prima\"" ).selector();
		
		assertNotNull( selector );
		assertTrue( link1 == selector.getTypeLink() );
		assertNotNull( selector.getNext() );
		assertTrue( link2 == selector.getNext().getTypeLink() );
		assertNull( selector.getNext().getNext() );
	}

	@Test
	public void testGridItem() throws Exception {
		TypeLink link = TypeLinkFactory.createLink();
		List<TypeLink> resultList = new ArrayList<TypeLink>();
		resultList.add( link );
		when( query.getResultList() ).thenReturn( resultList );
		
		SubViewer sottoVista = parser( " \"selector\" : inputText" ).gridItem();
	
		assertNotNull( sottoVista );
		assertNotNull( sottoVista.getSelector() );
		assertNotNull( sottoVista.getTarget() );
	}

	@Test
	public void testGridItemNoSelector() throws Exception {
		SubViewer sottoVista = parser( "  : outputValue" ).gridItem();
	
		assertNotNull( sottoVista );
		assertNull( sottoVista.getSelector() );
		assertNotNull( sottoVista.getTarget() );
	}
	
	
	@Test
	public void testBox() throws Exception {
		Box box = parser( 
			"box { " +
			" : label \"Label\"" +
			" : outputValue " +
			"}" ).box();
		
		assertNotNull( box );
		assertFalse( box.getCollapse() );
		assertEquals( 2, box.listChildren().size() );
	}

	@Test
	public void testBoxNoLabel() throws Exception {
		Box box = parser( 
			"box { " +
			" : inputText " +
			"}" ).box();
		
		assertNotNull( box );
		assertEquals( 1, box.listChildren().size() );
	}
	
	@Test
	public void testBoxCollapse() throws Exception {
		Box box = parser( 
			"box collapse { " +
			" : label \"Label\"" +
			" : outputValue " +
			"}" ).box();
		
		assertNotNull( box );
		assertTrue( box.getCollapse() );
		assertEquals( 2, box.listChildren().size() );
	}
	
	@Test
	public void testCustom() throws Exception {
		ViewerCustom custom = parser( 
				"custom \"prova\" { " +
				" : outputType " +
				"}" ).custom();		
		
		assertNotNull( custom );		
		assertEquals( "prova", custom.getXhtmlFileName() );
		assertEquals( 1, custom.listChildren().size() );
	}
	
	@Test
	public void testGridDefault() throws Exception {
		Grid grid = parser( 
				"grid { " +
				" : inputText " +
				"}" ).grid();		
		
		assertNotNull( grid );	
		assertFalse( grid.getCollapse() );
		assertEquals( PanelOrientation.vertical, grid.getOrientation() );
		assertEquals( 1, grid.listChildren().size() );
	}

	@Test
	public void testGridVertical() throws Exception {
		Grid grid = parser( 
				"grid vertical { " +
				" : inputText " +
				"}" ).grid();		
		
		assertNotNull( grid );		
		assertEquals( PanelOrientation.vertical, grid.getOrientation() );
		assertEquals( 1, grid.listChildren().size() );
	}
	
	@Test
	public void testGridHorizontal() throws Exception {
		Grid grid = parser( 
				"grid horizontal { " +
				" : inputText " +
				"}" ).grid();		
		
		assertNotNull( grid );		
		assertEquals( PanelOrientation.horizontal, grid.getOrientation() );
		assertEquals( 1, grid.listChildren().size() );
	}	
	
	@Test
	public void testGridSpacedHorizontal() throws Exception {
		Grid grid = parser( 
				"grid spaced_horizontal { " +
				" : inputText " +
				"}" ).grid();		
		
		assertNotNull( grid );		
		assertEquals( PanelOrientation.spaced_horizontal, grid.getOrientation() );
		assertEquals( 1, grid.listChildren().size() );
	}	
	
	@Test
	public void testGridCollapse() throws Exception {
		Grid grid = parser( 
				"grid vertical collapse { " +
				" : inputText " +
				"}" ).grid();		
		
		assertNotNull( grid );	
		assertTrue( grid.getCollapse() );
		assertEquals( PanelOrientation.vertical, grid.getOrientation() );
		assertEquals( 1, grid.listChildren().size() );
	}
	
	
	@Test
	public void testConditionalPanel() throws Exception {
		Type tos = TypeFactory.createTextualType();
		TypeLink link = TypeLinkFactory.createLink();
		link.assignTarget(tos);
		List<TypeLink> resultList1 = new ArrayList<TypeLink>();
		resultList1.add( link );

		Phenomenon fen = PhenomenonFactory.createPhenomenon();
		List<Phenomenon> resultList2 = new ArrayList<Phenomenon>();
		resultList2.add( fen );
		
		when( query.getResultList() ).thenReturn( resultList1, resultList2 );

		ConditionalPanel panel = parser(
			"conditionalPanel {" +
			" \"selector\" : \"fenomeno\"" +
			"           : outputValue " +
			"}").conditionalPanel();
		
		assertNotNull( panel );
		assertEquals( 1, panel.getPhenomena().size() );
		assertEquals( 1, panel.getSelectors().size() );
		assertEquals( 1, panel.listChildren().size() );
		assertEquals( 0, panel.getOperators().size() );
		assertEquals( false, panel.isClear() );
	}
	
	@Test
	public void testConditionalPanelMoreConditions() throws Exception {
		Type tos1 = TypeFactory.createQuantitativeType();
		TypeLink item1 = TypeLinkFactory.createLink();
		item1.assignTarget(tos1);
		List<TypeLink> resultList1 = new ArrayList<TypeLink>();
		resultList1.add( item1 );

		Phenomenon item2 = PhenomenonFactory.createPhenomenon();
		List<Phenomenon> resultList2 = new ArrayList<Phenomenon>();
		resultList2.add( item2 );
		
		Type tos3 = TypeFactory.createQuantitativeType();
		TypeLink item3 = TypeLinkFactory.createLink();
		item3.assignTarget(tos3);
		List<TypeLink> resultList3 = new ArrayList<TypeLink>();
		resultList3.add( item3 );

		Phenomenon item4 = PhenomenonFactory.createPhenomenon();
		List<Phenomenon> resultList4 = new ArrayList<Phenomenon>();
		resultList4.add( item4 );
		
		Type tos5 = TypeFactory.createQuantitativeType();
		TypeLink item5 = TypeLinkFactory.createLink();
		item5.assignTarget(tos5);
		List<TypeLink> resultList5 = new ArrayList<TypeLink>();
		resultList5.add( item5 );

		Phenomenon item6 = PhenomenonFactory.createPhenomenon();
		List<Phenomenon> resultList6 = new ArrayList<Phenomenon>();
		resultList6.add( item6 );
		
		Type tos7 = TypeFactory.createQuantitativeType();
		TypeLink item7 = TypeLinkFactory.createLink();
		item7.assignTarget(tos7);
		List<TypeLink> resultList7 = new ArrayList<TypeLink>();
		resultList7.add( item7 );
		
		when( query.getResultList() ).thenReturn( resultList1, resultList2, resultList3, resultList4, resultList5, resultList6, resultList7 );

		ConditionalPanel panel = parser(
			"conditionalPanel {" +
			" \"selector1\" : \"fenomeno1\" and \"selector2\" : \"fenomeno2\" or \"selector3\" : \"fenomeno3\" " +
			" clear \"selector4\" " +
			" clear-multi \"selector4\", \"selector5\" " +
			"           : outputValue " +
			"}").conditionalPanel();
		
		assertNotNull( panel );
		assertEquals( 3, panel.getPhenomena().size() );
		assertEquals( 3, panel.getSelectors().size() );
		assertEquals( 1, panel.listChildren().size() );
		assertEquals( 2, panel.getOperators().size() );
		
		assertEquals( true, panel.isClear() );
		assertNotNull( panel.getClearSelector() );
		assertEquals(false, panel.getClearSelectors().isEmpty());
		assertEquals(2, panel.getClearSelectors().size());
	}
	
	@Test
	public void testSelettoriNonAmbigui() throws Exception{
		TypeLink item1 = TypeLinkFactory.createLink();
		List<TypeLink> resultList1 = new ArrayList<TypeLink>();
		Type toss1 = TypeFactory.createTextualType();
		item1.assignTarget(toss1);
		resultList1.add( item1 );
		
		TypeLink item2 = TypeLinkFactory.createLink();
		TypeLink item3 = TypeLinkFactory.createLink();
		List<TypeLink> resultList2 = new ArrayList<TypeLink>();
		resultList2.add( item2 );
		resultList2.add( item3 );
		
		TypeLink item4 = TypeLinkFactory.createLink();
		List<TypeLink> resultList3 = new ArrayList<TypeLink>();
		resultList3.add( item4 );
		
		when( query.getResultList() ).thenReturn( resultList1, resultList2, resultList3 );
		
		Viewer vista = parser( 
				"grid { " +
				" \"prova\".\"ambigous\" : outputValue " +
				"}" ).parse();		
	
		assertNotNull( vista );
		assertEquals(item1, vista.getByPriority(0l).getSelector().getTypeLink());
		assertEquals(item4, vista.getByPriority(0l).getSelector().getNext().getTypeLink());
	}
	
	@Test
	public void testSelettoriNonAmbigui2() throws Exception{
		TypeLink item1 = TypeLinkFactory.createLink();
		List<TypeLink> resultList1 = new ArrayList<TypeLink>();
		Type toss1 = TypeFactory.createTextualType();
		item1.assignTarget(toss1);
		resultList1.add( item1 );
		
		TypeLink item2 = TypeLinkFactory.createLink();
		TypeLink item3 = TypeLinkFactory.createLink();
		List<TypeLink> resultList2 = new ArrayList<TypeLink>();
		resultList2.add( item2 );
		resultList2.add( item3 );
		
		TypeLink item4 = TypeLinkFactory.createLink();
		List<TypeLink> resultList3 = new ArrayList<TypeLink>();
		resultList3.add( item4 );
		
		when( query.getResultList() ).thenReturn( resultList1, resultList2, resultList3 );
		
		Viewer vista = parser( 
				"grid { " +
					"\"prova\" : box {"+
						"\"ambigous\" : outputValue " +
				"}}" ).parse();		
	
		assertNotNull( vista );
		assertEquals(item1, vista.getByPriority(0l).getSelector().getTypeLink());
		assertEquals(item4, vista.getByPriority(0l).getTarget().getByPriority(0l).getSelector().getTypeLink());
	}
	
	@Test
	public void testContainerComponent() throws Exception {
		ViewerContainer container = parser( 
				"grid horizontal { " +
				" : inputText " +
				"}" ).containerComponent();		
		
		assertNotNull( container );
	}
	
	@Test
	public void testRule() throws Exception {
		Viewer vista = parser( 
				"grid { " +
				" : outputValue " +
				"}" ).rule();		
	
		assertNotNull( vista );
	}
	 
	
	@Test
	public void testParse() throws Exception {
		Viewer vista = parser( 
				"grid { " +
				" : outputValue " +
				"}" ).parse();		
	
		assertNotNull( vista );
		assertNull( vista.getType() );
	}
	
	@Test( expected = RuntimeException.class )
	public void testVistaParserIllegalArgument() throws Exception {
		List<Viewer> resultList = new ArrayList<Viewer>();
		when( query.getResultList() ).thenReturn( resultList );
		parser( "view \"Hello\"" ).parse();
	}
	
	@Test( expected = VistaParserException.class )
	public void testVistaParserSyntaxError() throws Exception {
		List<Viewer> resultList = new ArrayList<Viewer>();
		when( query.getResultList() ).thenReturn( resultList );
		parser( "view Hello" ).parse();
	}
	
	@Test
	public void testContextViewerType() throws Exception {
		Type type = TypeFactory.createTextualType();
		List<Type> resultList = new ArrayList<Type>();
		resultList.add( type );
		when( query.getResultList() ).thenReturn( resultList );
		
		Viewer vista = parser( 
				"\"Type Prova\"" +
				"grid { " +
				" : outputValue " +
				"}" ).parse();		
	
		assertNotNull( vista );
		assertNotNull( vista.getType() );
		assertNull( vista.listChildrenOrdered().get(0).getTarget().getType() );
		assertEquals( vista.getType(), type );
	}
	
}
