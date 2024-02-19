package it.unifi.ing.stlab.reflection.dsl;

import it.unifi.ing.stlab.reflection.comparator.PhenomenonNameComparator;
import it.unifi.ing.stlab.reflection.model.types.*;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TypeEncoderVisitor implements TypeVisitor {

	private final StringBuffer buffer;
	private int level;
	private final boolean expand;
	
	public TypeEncoderVisitor() {
		buffer = new StringBuffer();
		level = 0;
		expand = false;
	}
	
	public TypeEncoderVisitor(boolean expand) {
		buffer = new StringBuffer();
		level = 0;
		this.expand = expand;
	}
	
	@Override
	public void visitTextualType(TextualType type) {
		buffer.append( "tx" );
	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		buffer.append( "ql { ");
		
		boolean first = true;
		for ( Phenomenon ph : getOrderedPhenomena( type )) {
			if ( first ) {
				first = false;
			} else {
				buffer.append( ", " );
			}
			buffer.append( "\"" )
				  .append( ph.getName() )
				  .append( "\"" );
		}
		
		buffer.append( " }" );
	}

	private List<Phenomenon> getOrderedPhenomena( EnumeratedType type ) {
		List<Phenomenon> result = new ArrayList<Phenomenon>();
		
		result.addAll( type.listPhenomena() );
		
		Collections.sort( result, new PhenomenonNameComparator());
		
		return result;
	}
	
	@Override
	public void visitQueriedType(QueriedType type) {
		buffer.append( "st { \"" )
			  .append( type.getQueryDef() )
			  .append( "\" }");
	}

	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		buffer.append( "qt { ");
		
		boolean first = true;
		for ( UnitUse uu : getOrderedUnitUse( type )) {
			if ( first ) {
				first = false;
			} else {
				buffer.append( ", " );
			}
			buffer.append( "\"" )
				  .append( uu.getUnit().getName() )
				  .append( "\"(" )
				  .append( uu.getDigits() )
				  .append( "," )
				  .append( uu.getDecimals() )
				  .append( ")" );
		}
		
		buffer.append( " }" );
	}

	private List<UnitUse> getOrderedUnitUse( QuantitativeType type ) {
		List<UnitUse> result = new ArrayList<UnitUse>();
		
		result.addAll( type.listUnits() );
		
		Collections.sort( result, new Comparator<UnitUse>() {
			@Override
			public int compare(UnitUse o1, UnitUse o2) {
				return o1.getUnit().getName().compareTo( o2.getUnit().getName() );
			}
		});
		
		return result;
	}

	
	@Override
	public void visitTemporalType(TemporalType type) {
		buffer.append( "dt " );
		buffer.append( type.getType().getId() );
		
	}

	
	@Override
	public void visitCompositeType(CompositeType type) {
		buffer.append( "ct {\r\n");
		level++;
		
		boolean first = true;
		for ( TypeLink link : type.listChildrenOrdered()) {
			if ( first ) {
				first = false;
			} else {
				buffer.append( ",\r\n" );
			}
			appendTabs();
			buffer.append( "\"" )
				  .append( link.getName() )
				  .append( "\"" );
			
			if (!( new Integer( 1 ).equals( link.getMin() ) && new Integer( 1 ).equals( link.getMax() ))) {
				buffer.append( "(" )
					  .append( link.getMin() )
					  .append( "," );
				
				if ( new Integer( -1 ).equals( link.getMax() )) {
					buffer.append( "unbounded" );
				} else {
					buffer.append( link.getMax() );
				}
				
				buffer.append( ")" );
			}
			
			buffer.append( ": " );
			if ( !expand && ( link.getTarget().getName() != null && !"".equals( link.getTarget().getName().trim()) ) ) {
				buffer.append( "\"" ).append( link.getTarget().getName() ).append( "\"" );
			} else {
				link.getTarget().accept( this );
			}
			
		}
		
		buffer.append( "\r\n" );
		level--;
		appendTabs();
		buffer.append( "}" );
		
	}

	@SuppressWarnings("unused")
	@Deprecated
	private List<TypeLink> getOrderedLinks( CompositeType type ) {
		List<TypeLink> result = new ArrayList<TypeLink>();
		
		result.addAll( type.listChildren() );
		
		Collections.sort( result, new Comparator<TypeLink>() {
			@Override
			public int compare(TypeLink o1, TypeLink o2) {
				return o1.getName().compareTo( o2.getName() );
			}
		});
		
		return result;
	}
	
	private void appendTabs() {
		for ( int i = 0; i < level; i ++ ) {
			buffer.append( "\t" );
		}
	}
	
	public String getResult() {
		return buffer.toString();
	}
}
