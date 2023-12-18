package it.unifi.ing.stlab.reflection.dsl.script;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Ignore;

import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.dao.types.TypeDaoBean;
import it.unifi.ing.stlab.reflection.dsl.TypeEncoderVisitor;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;

//XXX check
@Ignore
public class TypeDslExtractor extends JpaTest {

	protected TypeDao typeDao;
	
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_mysql" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		typeDao = new TypeDaoBean();
		FieldUtils.assignField( typeDao, "entityManager", entityManager );
	}
	
	//XXX generazione del type, decommentare l'annotazione per farlo girare
//	@Test
	public void generate() {
		Type type = typeDao.findByName( "---" );
		
		//expand tratta tutto come anonima e produce un dsl senza riferimenti
		boolean expand = true;
		TypeEncoderVisitor encoder = new TypeEncoderVisitor( expand );
		type.accept( encoder );
		
		writeFile( "---", formatOutput( type.getName(), encoder.getResult() ) );
	}
	
	private String formatOutput(String name, String definition) {
		StringBuffer output = new StringBuffer();
		output.append( "##START_TYPE_NAME##\n" );
		output.append( name );
		output.append( "\n##END_TYPE_NAME##\n" );
		output.append( "##START_DEFINITION##\n" );
		output.append( definition );
		output.append( "\n##END_DEFINITION##\n" );
		
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