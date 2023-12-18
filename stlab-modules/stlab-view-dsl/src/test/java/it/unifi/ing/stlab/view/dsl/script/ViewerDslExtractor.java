package it.unifi.ing.stlab.view.dsl.script;

import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.dao.ViewerDaoBean;
import it.unifi.ing.stlab.view.dsl.ViewerEncoderVisitor;
import it.unifi.ing.stlab.view.model.Viewer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class ViewerDslExtractor extends JpaTest {

	protected ViewerDao viewerDao;
	
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "production" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		viewerDao = new ViewerDaoBean();
		FieldUtils.assignField( viewerDao, "entityManager", entityManager );
	}
	
	//XXX generazione di viewer dal type, decommentare l'annotazione
	// per farlo girare
//	@Test
	public void generate() {
		Viewer viewer = viewerDao.findByName( "Visita Oculistica Standard - VIEW" );
		
		ViewerEncoderVisitor encoder = new ViewerEncoderVisitor();
		viewer.accept( encoder );
		
		String baseDefinition = "";
		if( viewer.getType() != null )
			baseDefinition = "\"" + viewer.getType().getName() + "\"\n";
		
		String definition = baseDefinition + encoder.getDefinition();
		String css = viewer.getCss();
		
		writeFile( "/Users/stark/Desktop/prova.txt", formatOutput( viewer.getName(), definition, css ) );
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
	
}
