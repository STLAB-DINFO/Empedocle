package it.unifi.ing.stlab.view.actions.loader;

import it.unifi.ing.stlab.commons.util.FileUtils;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.dsl.VistaLexer;
import it.unifi.ing.stlab.view.dsl.VistaParser;
import it.unifi.ing.stlab.view.dsl.VistaParserException;
import it.unifi.ing.stlab.view.model.Viewer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;

//@Startup
//@Singleton
@TransactionManagement( TransactionManagementType.BEAN )
public class ViewerLoaderBean implements ViewerLoader {
	
	@PersistenceContext
	private EntityManager entityManager;	
	
	@Resource
	private UserTransaction utx;	
	
	@EJB
	private ViewerDao viewerDao;		
	
	@Inject
	private Logger logger;
	
	@PostConstruct
	public void load() {
        logger.info( "ViewerLoaderBean started" );

        ArrayList<File> directories = new ArrayList<File>();
//        directories.add( new File( "/root/empedocle/tmp/viewers/custom" ));
//        directories.add( new File( "/root/empedocle/tmp/viewers/other/cardiologia" ));        
        directories.add( new File( "/Users/fulvio/Desktop/RACE/Empedocle/VIEWER/" ));    
//        directories.add( new File( "/root/empedocle/tmp/viewers/other/iniezioni_intravitreali" ));        
//        directories.add( new File( "/root/empedocle/tmp/viewers/other/iniezioni_intravitreali_1rev" ));        
//        directories.add( new File( "/root/empedocle/tmp/viewers/other/oculistica" ));  
//        directories.add( new File( "/root/empedocle/tmp/viewers/other/oculistica_standard" ));        
//        directories.add( new File( "/root/empedocle/tmp/viewers/other/oculistica_standard_1rev" ));        
//        directories.add( new File( "/root/empedocle/tmp/viewers/other/oculistica_standard_2rev" ));                

       /* directories.add( new File( "/Users/Sara/Dropbox/RTRT/our_app/materiale_flussi/SDO/viewers" ));        
        directories.add( new File( "/Users/Sara/Dropbox/RTRT/our_app/materiale_flussi/AP/viewers" ));        
        directories.add( new File( "/Users/Sara/Dropbox/RTRT/our_app/materiale_flussi/RMR/viewers" ));        
        directories.add( new File( "/Users/Sara/Dropbox/RTRT/our_app/materiale_flussi/SPA/viewers" ));        
        directories.add( new File( "/Users/Sara/Dropbox/RTRT/our_app/materiale_flussi/SEA/viewers" ));        
        directories.add( new File( "/Users/Sara/Dropbox/RTRT/our_app/materiale_flussi/SAA/viewers" ));        
        directories.add( new File( "/Users/Sara/Dropbox/RTRT/our_app/materiale_flussi/TEL/viewers" ));  */
        
        for (File directory: directories ){
    		File[] files = directory.listFiles();
    		Arrays.sort(files);
			for ( File file : files ) {
    			if ( !file.isHidden() ) {
    				try {
    					String content = FileUtils.readFile( file );
    					
    					String viewerName = StringUtils.getNestedString( content, "##START_VIEWER_NAME##", "##END_VIEWER_NAME##");
    					String definition = StringUtils.getNestedString( content, "##START_DEFINITION##", "##END_DEFINITION##");
    					String css = StringUtils.getNestedString( content, "##START_CSS##", "##END_CSS##");
    					
    					viewerName = viewerName != null ? viewerName.trim() : null;
    					definition = definition != null ? definition.trim() : null;
    					css = css != null ? css.trim() : null;
    					
    					if ( viewerDao.findByName( viewerName ) == null ) {
    						utx.begin();
    						Viewer viewer = validate( definition );
    						
    						if ( viewer != null ) {
    							viewer.setName( viewerName );
    							viewer.setCss( css );
    							viewer.setAnonymous( false );
    							
    							viewerDao.save( viewer );
    							
    							logger.info( viewerName + " successfully imported" );
    						} 
    						utx.commit();
    					} else {
    						logger.warn( viewerName + " already imported" );
    					}
    				} catch (Exception e) {
    					logger.error( e );
    				}
    			}
    		}
        }
        logger.info( "ViewerLoaderBean ended" );		
    }
	
	private Viewer validate( String definition ) throws IOException, VistaParserException {
		if ( definition == null )
			return null;
		
		VistaLexer lex = new VistaLexer(new ANTLRInputStream(
				new ByteArrayInputStream(definition.getBytes())));
		CommonTokenStream tokens = new CommonTokenStream(lex);
		VistaParser parser = new VistaParser(tokens);
		parser.setEntityManager( entityManager );
		
		return parser.parse();
	}
}
