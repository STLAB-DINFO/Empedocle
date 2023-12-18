package it.unifi.ing.stlab.view.dsl.script;

import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.dao.types.TypeDaoBean;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;
import it.unifi.ing.stlab.view.dsl.ViewerEncoderVisitor;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.visitor.EditViewerGeneratorVisitor;
import it.unifi.ing.stlab.view.visitor.OutputViewerGeneratorVisitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class ViewerGenerator extends JpaTest {

	protected TypeDao typeDao;
	
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "production" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		typeDao = new TypeDaoBean();
		FieldUtils.assignField( typeDao, "entityManager", entityManager );
	}
	
	//XXX generazione di viewer dal type, decommentare l'annotazione
	// per farlo girare
	@Test
	public void generateView() {
		OutputViewerGeneratorVisitor generator = new OutputViewerGeneratorVisitor();
		Type type = typeDao.findByName( "Cartella Elettronica Cefalee v0.1" );
		type.accept( generator );
		Viewer generatedViewer = generator.getResult();
		generatedViewer.setCss( getDefaultCss() );
		
		ViewerEncoderVisitor encoder = new ViewerEncoderVisitor();
		generatedViewer.accept( encoder );
		String baseDefinition = "\"" + generatedViewer.getType().getName() + "\"\n";
		String definition = baseDefinition + encoder.getDefinition();
		String css = generatedViewer.getCss();
		
		writeFile( "cec_viewer_view.txt", formatOutput( type.getName(), definition, css ) );
	}
	
	@Test
	public void generateEdit() {
		EditViewerGeneratorVisitor generator = new EditViewerGeneratorVisitor();
		Type type = typeDao.findByName( "Cartella Elettronica Cefalee v0.1" );
		type.accept( generator );
		Viewer generatedViewer = generator.getResult();
		generatedViewer.setCss( getDefaultCss() );
		
		ViewerEncoderVisitor encoder = new ViewerEncoderVisitor();
		generatedViewer.accept( encoder );
		String baseDefinition = "\"" + generatedViewer.getType().getName() + "\"\n";
		String definition = baseDefinition + encoder.getDefinition();
		String css = generatedViewer.getCss();
		
		writeFile( "cec_viewer_edit.txt", formatOutput( type.getName(), definition, css ) );
	}
	
	private String formatOutput(String name, String definition, String css) {
		StringBuffer output = new StringBuffer();
		output.append( "##START_VIEWER_NAME##\n" );
		output.append( name );
		output.append( "\n##END_VIEWER_NAME##\n" );
		output.append( "##START_DEFINITION##\n" );
		output.append( definition );
		output.append( "\n##END_DEFINITION##\n" );
		output.append( "##START_CSS##\n" );
		output.append( css );
		output.append( "\n##END_CSS##" );
		
		return output.toString();
	}
	
	private void writeFile(String filePath, String content) {
		FileOutputStream outputStream = null;
		try {
			File destination = new File( filePath );
			if( !destination.exists() )
				destination.createNewFile();
			
			outputStream = new FileOutputStream( destination );
			outputStream.write( content.getBytes() );
		} catch( IOException e ) {
			e.printStackTrace();
		} finally {
			try {
				if( outputStream != null )
					outputStream.close();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	private String getDefaultCss() {
		return ".fieldset {background-color: #f9f9f9; margin: 5px;}\n" +
		".fieldset .fieldset {background-color: white;}\n" +
		".fieldset .fieldset  .fieldset {background-color: #f9f9f9;}\n" +
		".label_text {font-weight: bold;}\n" +
		".outputPath_text {font-weight: bold;}\n" +
		".grid_table .grid_cell_0 {width: 25%; text-align: right; padding-right: 15px; vertical-align: top;}\n" +
		".grid_cell_1 .grid_table {width: auto;}\n" +
		".grid_cell_1 .grid_table .grid_cell_0 {padding-right: 0px; width: auto;}\n" +
		".grid_cell_1 .grid_table .grid_cell_1 {padding-left: 6px;}\n" +
		".box_label {font-weight: bold; background: transparent; background-image: -webkit-linear-gradient(bottom, #f9f9f9 50%, transparent 50%); padding-left: 6px; padding-right: 6px;}\n" +
		".fieldset .fieldset .box_label {font-weight: bold; background: transparent; background-image: -webkit-linear-gradient(bottom, transparent 50%, #f9f9f9 50%); padding-left: 6px; padding-right: 6px;}\n" +
		".fieldset .fieldset .fieldset .box_label {font-weight: bold; background: transparent; background-image: -webkit-linear-gradient(bottom, #f9f9f9 50%, transparent 50%); padding-left: 6px; padding-right: 6px;}\n" +
		".fieldset {padding: 10px 0 3px 0;}";
	}
}
