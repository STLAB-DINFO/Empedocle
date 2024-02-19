package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDao;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;
import it.unifi.ing.stlab.reflection.model.types.Type;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestScoped
//@WebServlet(urlPatterns = "/suggest")
public class SuggestionServlet extends HttpServlet implements Serializable {

	private static final long serialVersionUID = -938192075772434961L;

	@Inject
	private PhenomenonDao phenomenonDao;
	
	@Inject
	private TypeDao typeDao;
	
	private static final int MAX_RESULTS = 10;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String type = req.getParameter( "type" );
		String input = req.getParameter( "query" );
		String limit = req.getParameter( "limit" );
		
		if( type == null || type.isEmpty() || input == null || input.isEmpty() ) {
			resp.getOutputStream().close();
			resp.sendError( 400, "parametri mancanti" );
		}
		
		QualitativeType qlt = getType( type );
		List<Phenomenon> items = autocomplete( qlt, input, limit );
		
		resp.setContentType( "application/json" );
		resp.getOutputStream().write( serializeToJson( items ) );
		resp.getOutputStream().close();
	}

	//FIXME per il momento faccio una query per trovare il tipo, poi scrivere nuovo metodo nel dao
	private List<Phenomenon> autocomplete(QualitativeType qlt, String input, String limit){
		int maxResults = ( limit == null? MAX_RESULTS : new Integer(limit) );
		return phenomenonDao.findBySuggestion( input, qlt, new Date(), maxResults );
	}
	
	private byte[] serializeToJson(List<Phenomenon> phenomena) {
		StringBuffer buffer = new StringBuffer();
		buffer.append( "[" );
		
		boolean first = true;
		
		for( Phenomenon p : phenomena ) {
			if( !first ) buffer.append( "," );
			first = false;
			
			buffer.append( "{" );
			buffer.append( "\"value\"" );
			buffer.append( ":" );
			buffer.append( "\"" );
			buffer.append( p.getUuid() );
			buffer.append( "\"" );
			buffer.append( "," );
			buffer.append( "\"label\"" );
			buffer.append( ":" );
			buffer.append( "\"" );
			buffer.append( p.getName() );
			buffer.append( "\"" );
			buffer.append( "}" );
		}
		
		buffer.append( "]" );
		
		return buffer.toString().getBytes();
	}
	
	private QualitativeType getType(String type) {
		Type result = typeDao.findById( new Long(type) );
		if( ClassHelper.instanceOf( result, QualitativeType.class ) )
			return ClassHelper.cast( result, QualitativeType.class );
		
		throw new RuntimeException( "type with id "+ type + " is not qualitative" );
	}
}
