package it.unifi.ing.stlab.empedocle.view.controllers.dermatology;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDao;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SedeNevoController extends ContainerController {

	@Inject
	private PhenomenonDao phenomenonDao;
	
	public TypeLink getTypeLink(CompositeFact fact, TypeSelector selector) {
		return selector.applyType(fact.getType());
	}
	
	//FIXME metodo provvisorio? + cursor..
//	public FactLink getFactLink(CompositeFact fact, TypeSelector selector, String uuid) {
//		for( FactLink link : selector.applyItem(fact) ) {
//			if( uuid.equals( ClassHelper.cast( link, FactLinkImpl.class ).getUuid() ) )
//				return link;
//		}
//		
//		throw new RuntimeException( "FactLink da eliminare non trovato (uuid: " + uuid + ")");
//	}
	
	public void assignPhenomenon(CompositeFact parent, Viewer viewer, String x, String y, String phenomenonName) {
		if( parent == null || viewer == null || phenomenonName == null ) return;
		
		Set<FactLink> links = viewer.getByPriority( 0l ).getSelector().applyItem( parent );
		Fact destination = null;
		
		for( FactLink link : links ) {
			if( destination == null && link.getTarget().isEmpty() ) 
				destination = link.getTarget();
			
		}
		
		if( destination != null && ClassHelper.instanceOf( destination, CompositeFact.class ) ) {
			Fact imageX = viewer.getByPriority( 1l ).getSelector().apply( destination );
			Fact imageY = viewer.getByPriority( 2l ).getSelector().apply( destination );
			Fact sede = viewer.getByPriority( 3l ).getSelector().apply( destination );
			
			if( imageX != null && imageY != null ) {
				writeCoordinates( quantitative( imageX ), quantitative( imageY ), new Double( x ), new Double( y ) );
				writePhenomenonName( qualitative( sede ), phenomenonName );
			}
		}
		
	}
	
	public List<FactLink> retrieveImagePoints(CompositeFact parent, Viewer viewer) {
		List<FactLink> result = new LinkedList<FactLink>();
		result.addAll( viewer.getByPriority( 0l ).getSelector().applyItem( parent ) );
		
		Collections.sort( result, new Comparator<FactLink>() {
			@Override
			public int compare(FactLink arg0, FactLink arg1) {
				int p0 = ( arg0.getPriority() == null ? 0 : arg0.getPriority().intValue() );
				int p1 = ( arg1.getPriority() == null ? 0 : arg1.getPriority().intValue() );
				return ( p0 - p1 );
			}
		});
		
		return result;
	}
	
	public Double getImgX(Fact value, Viewer viewer){
		QuantitativeFact qnt = quantitative( findBySelector(value, viewer.getByPriority( 1l ).getSelector() ) );
		return qnt.getQuantity().getValue();
		
	}
	
	public Double getImgY(Fact value, Viewer viewer){
		QuantitativeFact qnt = quantitative( findBySelector(value, viewer.getByPriority( 2l ).getSelector() ) );
		return qnt.getQuantity().getValue();
		
	}
	
	public String getSede(Fact value, Viewer viewer){
		QualitativeFact qlt = qualitative( findBySelector(value, viewer.getByPriority( 3l ).getSelector() ) );
		return qlt.getPhenomenon().getName();
		
	}
	
	//FIXME qui viene creato il codice xhtml per i puntini
	public String getPointsMarkup(CompositeFact value, Viewer viewer) {
		StringBuffer sb = new StringBuffer();
		List<FactLink> links = retrieveImagePoints( value, viewer );
		for( FactLink link : links ){
			Double x = getImgX( link.getTarget(), viewer);
			Double y = getImgY( link.getTarget(), viewer);
			
			//FIXME controllo da cancellare dopo demo!
			if( x != null && y != null ) {
			
				sb.append("<g uuid=\"");
//				sb.append( ClassHelper.cast( link, FactLinkImpl.class ).getUuid() );
				sb.append("\">");
				sb.append("<circle cx=\"");
				sb.append( x );
				sb.append("\" cy=\"");
				sb.append( y );
				sb.append("\" r=\"5\" stroke=\"black\" stroke-width=\"1\" fill=\"blue\" />");
				sb.append("<text x=\"");
				sb.append( x - 2 );
				sb.append("\" y=\"");
				sb.append( y + 2 );
				sb.append("\" font-size=\"8\" stroke=\"white\" fill=\"white\" >");
				sb.append( links.indexOf( link ) + 1 );
				sb.append("</text>");
				sb.append("</g>");
				
			}
		}
		
		return sb.toString();
	}
	
	
	//
	// Private Methods
	//
	
	private void writeCoordinates(QuantitativeFact targetX, QuantitativeFact targetY, Double x, Double y) {
		if( targetX.getQuantity() == null )
			targetX.setQuantity( new Quantity() );
		
		if( targetY.getQuantity() == null )
			targetY.setQuantity( new Quantity() );
		
		targetX.getQuantity().setValue( x );
		targetY.getQuantity().setValue( y );
	}
	
	private void writePhenomenonName(QualitativeFact target, String phenomenonName) {
		Phenomenon p = phenomenonDao.findByName( target, phenomenonName, new Date() );
		if( p == null )
			throw new RuntimeException( "fenomeno con nome " + phenomenonName + " inesistente per il tipo " + target.getType().getName() );
		
		target.setPhenomenon( p );
	}
	
	private QuantitativeFact quantitative(Fact fact) {
		if ( fact == null ) return null;
		return ClassHelper.cast( fact, QuantitativeFact.class );
	}
	
	private QualitativeFact qualitative(Fact fact) {
		if ( fact == null ) return null;
		return ClassHelper.cast( fact, QualitativeFact.class );
	}
	
}
