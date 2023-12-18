package it.unifi.ing.stlab.empedocle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.codehaus.plexus.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class StringUtilsTest {
	
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void testEasy() {
		String content = "## START ## ciao ## END ##";
		String result = StringUtils.getNestedString( content, "## START ##", "## END ##");
		assertEquals(" ciao ", result );
	}	
	
	@Test
	public void testViewerName() {
		String content = getFakeFile();
		String result = StringUtils.getNestedString( content, "##START_VIEWER_NAME##", "##END_VIEWER_NAME##");
		assertEquals("Visita Oculistica - VIEW", result.trim() );
	}	
	
	@Test
	public void testDefinition() {
		String content = getFakeFile();
		String result = StringUtils.getNestedString( content, "##START_DEFINITION##", "##END_DEFINITION##");
		assertNotNull(result);
		System.out.println( result.trim() );
	}	
	
	@Test
	public void testCss() {
		String content = getFakeFile();
		String result = StringUtils.getNestedString( content, "##START_CSS##", "##END_CSS##");
		assertNotNull(result);
		System.out.println( result.trim() );
	}	
	
	@Test
	public void testAllTogether() {
		String content = getFakeFile();
		
		String viewerName = StringUtils.getNestedString( content, "##START_VIEWER_NAME##", "##END_VIEWER_NAME##");
		String definition = StringUtils.getNestedString( content, "##START_DEFINITION##", "##END_DEFINITION##");
		String css = StringUtils.getNestedString( content, "##START_CSS##", "##END_CSS##");
		
		assertNotNull( viewerName.trim() );
		assertNotNull( definition.trim() );		
		assertNotNull( css.trim() );
		
		System.out.println( viewerName.trim() );
		System.out.println( definition.trim() );
		System.out.println( css.trim() );
	}	
	
	@Test
	public void testEmpty() {
		String content = getFakeFileEmpty();
		
		String viewerName = StringUtils.getNestedString( content, "##START_VIEWER_NAME##", "##END_VIEWER_NAME##");
		String definition = StringUtils.getNestedString( content, "##START_DEFINITION##", "##END_DEFINITION##");
		String css = StringUtils.getNestedString( content, "##START_CSS##", "##END_CSS##");
		
		assertNotNull( viewerName );
		assertTrue( viewerName.trim().isEmpty() );
		assertNotNull( definition );
		assertTrue( definition.trim().isEmpty() );
		assertNotNull( css );
		assertTrue( css.trim().isEmpty() );
		
	}		
	
	
	private String getFakeFile(){
		return  "##START_VIEWER_NAME##" +
				"Visita Oculistica - VIEW" +
				"##END_VIEWER_NAME##" +

				"##START_DEFINITION##" +
				"Visita Oculistica" +
				"grid collapse {" +
					"\"1. Anamnesi - Medico inviante\" : outputValue" +
				"}" +
				"##END_DEFINITION##" +
		
				"##START_CSS##" +
				".view-root {font: 11px tahoma;}" +
				"##END_CSS##";
	}
	
	private String getFakeFileEmpty(){
		return  "##START_VIEWER_NAME##" +
				"##END_VIEWER_NAME##" +

				"##START_DEFINITION##" +
				"##END_DEFINITION##" +
		
				"##START_CSS##" +
				"##END_CSS##";
	}
}
