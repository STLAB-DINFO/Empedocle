package it.unifi.ing.stlab.commons.pdf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.FSEntityResolver;
import org.xml.sax.InputSource;

public class PDFRendererForcedFilter implements Filter {

	public void destroy() {
	}

	public void init( FilterConfig filterConfig ) throws ServletException {
	}

	public void doFilter( ServletRequest req, ServletResponse resp, FilterChain filterChain )
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		// Capture the content for this request
		ContentCaptureServletResponse capContent = new ContentCaptureServletResponse( response );
		filterChain.doFilter( request, capContent );
		
		// Transform the XHTML content to a document readable by the renderer.
		String html = capContent.getContent();
		
		// Hack to get images from relative path
		html = hack( html, 
				Pattern.compile( Pattern.quote( "src=\"" ) + "(.*?)" + Pattern.quote( "\"" ) ),
				request );
		// Hack to get links from relative path
		html = hack( html,
				Pattern.compile( Pattern.quote( "href=\"" ) + "(.*?)" + Pattern.quote( "\"" ) ),
				request );		
		
		InputSource source = new InputSource( new StringReader( html ) );
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			documentBuilder.setEntityResolver( FSEntityResolver.instance() );
			Document document = documentBuilder.parse( source );
			
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument( document, null );
			renderer.layout();
			response.setContentType( "application/pdf" );

			String filename = request.getParameter( "preferredFilename" );
			if ( filename != null ) {
				response.setHeader( "Content-Disposition",
						String.format( "attachment; filename=\"%s\"", filename ) );
			}

			OutputStream out = response.getOutputStream();
			renderer.createPDF( out );
		} catch ( Exception e ) {
			throw new IOException( e );
		}
	}
	
	private String hack( String source, Pattern pattern, HttpServletRequest request ) {
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		String base = url.replaceFirst( uri, "" );
		
		String result = new String( source );

		Matcher matcher = pattern.matcher( source );
		while ( matcher.find() ) {
			String match = matcher.group( 1 );
			result = result.replace( match, base + match );
		}
		
		return result;
	}
}