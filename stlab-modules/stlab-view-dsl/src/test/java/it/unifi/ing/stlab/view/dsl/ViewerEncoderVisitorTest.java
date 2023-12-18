package it.unifi.ing.stlab.view.dsl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.factquery.factory.FactQueryFactory;
import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.Tab;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;
import it.unifi.ing.stlab.view.model.widgets.container.Box;
import it.unifi.ing.stlab.view.model.widgets.container.ConditionalPanel;
import it.unifi.ing.stlab.view.model.widgets.container.FactPanel;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.container.PanelOrientation;
import it.unifi.ing.stlab.view.model.widgets.container.Report;
import it.unifi.ing.stlab.view.model.widgets.container.TabbedPanel;
import it.unifi.ing.stlab.view.model.widgets.input.Combo;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;
import it.unifi.ing.stlab.view.model.widgets.input.InputTemporal;
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

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

public class ViewerEncoderVisitorTest {

	protected ViewerEncoderVisitor visitor;
	protected EntityManager entityManager;
	protected Query query;
	
	@Before
	public void setUp() throws Exception{
		visitor = new ViewerEncoderVisitor();
		
		entityManager = mock( EntityManager.class );
		query = mock( Query.class );
		when( entityManager.createQuery( anyString() )).thenReturn( query );
	}
	
	@Test
	public void testVisitLabel() throws Exception{
		Label l = new Label(UUID.randomUUID().toString());
		l.setValue("test");
		l.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("label \"test\"", result);
	}
	
	@Test
	public void testVisitOutputType(){
		OutputType o = new OutputType(UUID.randomUUID().toString());
		o.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("outputType", result);
	}
	
	@Test
	public void testVisitOutputValue(){
		OutputValue o = new OutputValue(UUID.randomUUID().toString());
		o.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("outputValue", result);
	}
	
	@Test
	public void testVisitOutputMeasurementUnit(){
		OutputMeasurementUnit o = new OutputMeasurementUnit(UUID.randomUUID().toString());
		o.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("outputMeasurementUnit", result);
	}
	
	@Test
	public void testVisitOutputPath(){
		OutputPath o = new OutputPath(UUID.randomUUID().toString());
		o.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("outputPath", result);
	}
	
	@Test
	public void testVisitOutputListVertical(){
		OutputList o = initOutputList();
		o.setOrientation(PanelOrientation.vertical);
		o.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("outputList { outputValue }", result);
	}
	
	@Test
	public void testVisitOutputListHorizontal(){
		OutputList o = initOutputList();
		o.setOrientation(PanelOrientation.horizontal);
		o.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("outputList horizontal { outputValue }", result);
	}
	
	@Test
	public void testVisitOutputField(){
		OutputField o = new OutputField(UUID.randomUUID().toString());
		o.setPath("Paziente.Nome");
		
		o.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("out Paziente.Nome", result);
	}
	
	@Test
	public void testVisitOutputImage(){
		OutputImage o = new OutputImage(UUID.randomUUID().toString());
		o.setImagePath("/path/path/name.png");
		
		o.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("outputImage \"/path/path/name.png\"", result);
	}
	
	@Test
	public void testVisitInputText1(){
		InputText i = new InputText(UUID.randomUUID().toString());
		
		i.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("inputText", result);
	}
	
	@Test
	public void testVisitInputText2(){
		InputText i = new InputText(UUID.randomUUID().toString());
		i.setInputLength(30);
		i.setRequired(true);
		
		i.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("inputText required length=30", result);
	}
	
	@Test
	public void testVisitInputTemporal(){
		InputTemporal i = new InputTemporal(UUID.randomUUID().toString());
		
		i.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("inputTemporal", result);
	}
	
	@Test
	public void testVisitTextArea1(){
		TextArea t = new TextArea(UUID.randomUUID().toString());
		
		t.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("textArea", result);
	}
	
	@Test
	public void testVisitTextArea2(){
		TextArea t = new TextArea(UUID.randomUUID().toString());
		t.setInputLength(30);
		t.setRequired(true);
		
		t.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("textArea required length=30", result);
	}
	
	@Test
	public void testVisitCombo1(){
		Combo c = new Combo(UUID.randomUUID().toString());
		
		c.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("combo", result);
	}
	
	@Test
	public void testVisitCombo2(){
		Combo c = new Combo(UUID.randomUUID().toString());
		c.setRequired(true);
		
		c.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("combo required", result);
	}
	
	@Test
	public void testVisitSuggestion1(){
		Suggestion s = new Suggestion(UUID.randomUUID().toString());
		
		s.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("suggestion", result);
	}
	
	@Test
	public void testVisitSuggestion2(){
		Suggestion s = new Suggestion(UUID.randomUUID().toString());
		s.setRequired(true);
		
		s.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("suggestion required", result);
	}
	
	@Test
	public void testVisitInputListVertical(){
		InputList i = initInputList();
		i.setOrientation(PanelOrientation.vertical);
		i.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("inputList { combo }", result);
	}
	
	@Test
	public void testVisitInputListHorizontal(){
		InputList i = initInputList();
		i.setOrientation(PanelOrientation.horizontal);
		i.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("inputList horizontal { combo }", result);
	}
	
	@Test
	public void testVisitGridHorizontal(){
		Grid g = initGrid();
		g.setOrientation(PanelOrientation.horizontal);
		
		Combo c = new Combo(UUID.randomUUID().toString());
		SubViewer sv2 = new SubViewer(UUID.randomUUID().toString());
		sv2.assignSource(g);
		sv2.assignTarget(c);
		
		g.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("grid horizontal {\n\t: outputPath\n\t: combo\n}\n", result);
	}
	
	@Test
	public void testVisitGridVertical(){
		Grid g = initGrid();
		g.setOrientation(PanelOrientation.vertical);
		g.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("grid {\n\t: outputPath\n}\n", result);
	}
	
	@Test
	public void testVisitGridCollapse(){
		Grid g = initGrid();
		g.setOrientation(PanelOrientation.horizontal);
		g.setCollapse(true);
		g.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("grid horizontal collapse {\n\t: outputPath\n}\n", result);
	}
	
	@Test
	public void testVisitBox(){
		Box b = initBox();
		
		b.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("box {\n\t: outputPath\n\t: combo\n}\n", result);
	}
	
	@Test
	public void testVisitBoxCollapse(){
		Box b = initBox();
		b.setCollapse(true);
		
		b.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("box collapse {\n\t: outputPath\n\t: combo\n}\n", result);
	}
	
	@Test
	public void testVisitConditionalPanel1(){
		ConditionalPanel c = initConditional();
		
		TypeSelector sel1 = initSelector("Selettore");
		c.addSelector(sel1);
		
		Phenomenon p = PhenomenonFactory.createPhenomenon();
		p.setName("Fen");
		c.addFenomeno(p);
		
		c.setClear(false);
		
		c.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("conditionalPanel {\n\t\"Selettore\" : \"Fen\"\n\t: outputPath\n}\n", result);
	}
	
	@Test
	public void testVisitConditionalPanel2(){
		ConditionalPanel c = initConditional();
		
		TypeSelector sel1 = initSelector("Selettore 1");
		c.addSelector(sel1);
		
		TypeSelector sel2 = initSelector("Selettore 2");
		c.addSelector(sel2);
		
		Phenomenon p1 = PhenomenonFactory.createPhenomenon();
		p1.setName("Fen A");
		c.addFenomeno(p1);
		Phenomenon p2 = PhenomenonFactory.createPhenomenon();
		p2.setName("Fen B");
		c.addFenomeno(p2);
		
		c.addOperator("and");

		c.setClear(false);
		
		c.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("conditionalPanel {\n\t\"Selettore 1\" : \"Fen A\" and \"Selettore 2\" : \"Fen B\"\n\t: outputPath\n}\n", result);
	}
	
	@Test
	public void testVisitConditionalPanel3(){
		ConditionalPanel c = initConditional();
		
		TypeSelector sel1 = initSelector("Selettore 1");
		c.addSelector(sel1);
		
		TypeSelector sel2 = initSelector("Selettore 2");
		sel1.assignNext(sel2);
		
		Phenomenon p1 = PhenomenonFactory.createPhenomenon();
		p1.setName("Fen A");
		c.addFenomeno(p1);

		c.setClear(false);
		
		c.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("conditionalPanel {\n\t\"Selettore 1\".\"Selettore 2\" : \"Fen A\"\n\t: outputPath\n}\n", result);
	}
	
	@Test
	public void testVisitConditionalPanel4(){
		ConditionalPanel c = initConditional();
		
		TypeSelector sel2 = initSelector("Selettore 2");
		c.listChildrenOrdered().get(0).setSelector(sel2);
		
		TypeSelector sel1 = initSelector("Selettore 1");
		c.addSelector(sel1);
		
		Phenomenon p1 = PhenomenonFactory.createPhenomenon();
		p1.setName("Fen A");
		c.addFenomeno(p1);

		c.setClear(false);
		
		c.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("conditionalPanel {\n\t\"Selettore 1\" : \"Fen A\"\n\t\"Selettore 2\" : outputPath\n}\n", result);
	}
	
	@Test
	public void testVisitConditionalPanel5(){
		ConditionalPanel c = initConditional();
		
		TypeSelector sel1 = initSelector("Selettore");
		c.addSelector(sel1);
		
		TypeSelector sel2 = initSelector("Selettore Clear");
		c.setClearSelector(sel2);
		c.setClear(true);
		
		Phenomenon p = PhenomenonFactory.createPhenomenon();
		p.setName("Fen");
		c.addFenomeno(p);
		
		c.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("conditionalPanel {\n\t\"Selettore\" : \"Fen\"\n\tclear \"Selettore Clear\" \n\t: outputPath\n}\n", result);
	}
	
	@Test
	public void testVisitTabbedPanel1(){
		TabbedPanel t = initTabbedPanel();
		t.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("tabbedPanel {\n\t\"Titolo Tab\" ; : outputPath\n}\n", result);
	}
	
	@Test
	public void testVisitTabbedPanel2(){
		TabbedPanel t = initTabbedPanel();
		
		TypeSelector sel1 = initSelector("Selettore 1");
		t.listChildrenOrdered().get(0).setSelector(sel1);
		
		t.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("tabbedPanel {\n\t\"Titolo Tab\" ; \"Selettore 1\" : outputPath\n}\n", result);
	}
	
	@Test
	public void testVisitReport(){
		Report r = new Report(UUID.randomUUID().toString());

		OutputPath o1 = new OutputPath(UUID.randomUUID().toString());
		SubViewer sv1 = new SubViewer(UUID.randomUUID().toString());
		sv1.assignSource(r);
		sv1.assignTarget(o1);
		
		OutputPath o2 = new OutputPath(UUID.randomUUID().toString());
		SubViewer sv2 = new SubViewer(UUID.randomUUID().toString());
		sv2.assignSource(r);
		sv2.assignTarget(o2);
		
		OutputPath o3 = new OutputPath(UUID.randomUUID().toString());
		SubViewer sv3 = new SubViewer(UUID.randomUUID().toString());
		sv3.assignSource(r);
		sv3.assignTarget(o3);
		
		r.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("report {\n\t: outputPath\n\t: outputPath\n\t: outputPath\n}\n", result);
	}
	
	@Test
	public void testVisitFactPanel(){
		FactPanel fp = new FactPanel( UUID.randomUUID().toString() );
		FactQuery fq = FactQueryFactory.createQuery();
		fq.setName( "fq" );
		fp.setQuery( fq );
		
		OutputPath o1 = new OutputPath( UUID.randomUUID().toString() );
		SubViewer sv1 = new SubViewer( UUID.randomUUID().toString() );
		sv1.assignSource( fp );
		sv1.assignTarget( o1 );
		
		fp.accept( visitor );
		
		String result = visitor.getDefinition();
		assertNotNull( result );
		assertFalse( result.isEmpty() );
		assertEquals( "factPanel query \"fq\" {\n\t: outputPath\n}\n", result );
	}
	
	@Test
	public void testVisitCustomViewer(){
		ViewerCustom vc = new ViewerCustom(UUID.randomUUID().toString());
		vc.setName("Prova Custom Viewer");
		
		vc.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("view \"Prova Custom Viewer\"", result);
	}
	
	@Test
	public void testVisitViewerContainer1(){
		Grid g = initGrid();
		g.setOrientation(PanelOrientation.vertical);

		g.listChildrenOrdered().get(0).setSelector(initSelector("prova selector"));
		
		OutputValue o2 = new OutputValue(UUID.randomUUID().toString());
		SubViewer sv2 = new SubViewer(UUID.randomUUID().toString());
		TypeSelector sel2 = new TypeSelector(UUID.randomUUID().toString());
		TypeLink tl2 = TypeLinkFactory.createLink();
		tl2.setName("prova selector 2");
		sel2.setTypeLink(tl2);
		sv2.setSelector(sel2);
		sv2.assignSource(g);
		sv2.assignTarget(o2);
		
		g.refreshChildrenOrdered();
		g.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("grid {\n\t\"prova selector\" : outputPath\n\t\"prova selector 2\" : outputValue\n}\n", result);
	}
	
	@Test
	public void testVisitViewerContainer2(){
		Grid g = initGrid();
		g.setOrientation(PanelOrientation.vertical);

		TypeSelector sel1 = initSelector("prova selector");
		TypeSelector sel2 = initSelector("prova selector 2");
		TypeSelector sel3 = initSelector("prova selector 3");
		
		sel1.assignNext(sel2);
		sel2.assignNext(sel3);
		
		g.listChildrenOrdered().get(0).setSelector(sel1);
		
		g.accept(visitor);
		
		String result = visitor.getDefinition();
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("grid {\n\t\"prova selector\".\"prova selector 2\".\"prova selector 3\" : outputPath\n}\n", result);
	}
	
	
	// init methods
	private OutputList initOutputList(){
		OutputList outList = new OutputList(UUID.randomUUID().toString());
		OutputValue ov = new OutputValue(UUID.randomUUID().toString());
		SubViewer sv = new SubViewer(UUID.randomUUID().toString());
		sv.assignSource(outList);
		sv.assignTarget(ov);
		
		return outList;
	}
	
	private InputList initInputList(){
		InputList inputList = new InputList(UUID.randomUUID().toString());
		Combo c = new Combo(UUID.randomUUID().toString());
		SubViewer sv = new SubViewer(UUID.randomUUID().toString());
		sv.assignSource(inputList);
		sv.assignTarget(c);
		
		return inputList;
	}
	
	private Grid initGrid(){
		Grid grid = new Grid(UUID.randomUUID().toString());		
		OutputPath o = new OutputPath(UUID.randomUUID().toString());
		SubViewer sv = new SubViewer(UUID.randomUUID().toString());
		sv.assignSource(grid);
		sv.assignTarget(o);
		grid.setCollapse(false);
		
		return grid;
	}
	
	private Box initBox() {
		Box box = new Box(UUID.randomUUID().toString());
		
		OutputPath o = new OutputPath(UUID.randomUUID().toString());
		SubViewer sv = new SubViewer(UUID.randomUUID().toString());
		sv.assignSource(box);
		sv.assignTarget(o);
		
		Combo c = new Combo(UUID.randomUUID().toString());
		SubViewer sv2 = new SubViewer(UUID.randomUUID().toString());
		sv2.assignSource(box);
		sv2.assignTarget(c);
		
		box.setCollapse(false);
		
		return box;
	}
	
	private ConditionalPanel initConditional(){
		ConditionalPanel conditional = new ConditionalPanel(UUID.randomUUID().toString());
		
		OutputPath o = new OutputPath(UUID.randomUUID().toString());
		SubViewer sv = new SubViewer(UUID.randomUUID().toString());
		sv.assignSource(conditional);
		sv.assignTarget(o);
		conditional.setCollapse(false);

		return conditional;
	}
	
	private TabbedPanel initTabbedPanel(){
		TabbedPanel tabbed = new TabbedPanel(UUID.randomUUID().toString());
		OutputPath o = new OutputPath(UUID.randomUUID().toString());
		
		Tab tp = new Tab(UUID.randomUUID().toString());
		tp.setLabel("Titolo Tab");
		tp.assignSource(tabbed);
		tp.assignTarget(o);
		
		tabbed.setCollapse(false);
		
		return tabbed;
	}
	
	private TypeSelector initSelector(String name){
		TypeSelector sel = new TypeSelector(UUID.randomUUID().toString());
		TypeLink tl = TypeLinkFactory.createLink();
		tl.setName(name);
		sel.setTypeLink(tl);
		
		return sel;
	}
}
