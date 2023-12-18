package it.unifi.ing.stlab.reflection.visitor.type;

import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitUseFactory;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.TypeVisitor;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.reflection.visitor.factvalue.FactValueCopyVisitor;

public class TypeCopyVisitor implements TypeVisitor {
	
	private Type result;

	@Override
	public void visitTextualType(TextualType type) {
		result = TypeFactory.createTextualType();
		basicVisit(type);

	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		result = TypeFactory.createEnumeratedType();
		basicVisit(type);
		for(Phenomenon p : type.listPhenomena()) {
			Phenomenon copy = PhenomenonFactory.createPhenomenon();
			copy.setName(p.getName());
			copy.setPosition(p.getPosition());
			copy.setTimeRange(p.getTimeRange());
			((EnumeratedType)result).addPhenomenon(copy);
		}

	}

	@Override
	public void visitQueriedType(QueriedType type) {
		result = TypeFactory.createQueriedType();
		basicVisit(type);
		((QueriedType)result).setQueryDef(type.getQueryDef());

	}

	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		result = TypeFactory.createQuantitativeType();
		basicVisit(type);
		for(UnitUse uu : type.listUnits()) {
			UnitUse copy = UnitUseFactory.createUnitUse();
			copy.setDecimals(uu.getDecimals());
			copy.setDigits(uu.getDigits());
			copy.setUnit(uu.getUnit());
			((QuantitativeType)result).addUnit(copy);
		}

	}

	@Override
	public void visitTemporalType(TemporalType type) {
		result = TypeFactory.createTemporalType();
		basicVisit(type);
		((TemporalType)result).setType(type.getType());
		
	}

	@Override
	public void visitCompositeType(CompositeType type) {
		result = TypeFactory.createCompositeType();
		basicVisit(type);
		
		// copia riferimenti ai figli
		for(TypeLink tl : type.listChildren()) {
			TypeLink copy = copyTypeLink(tl);
			copy.assignSource(result);
			
			// se è anonimo (non reference), duplicare anche i target dei TL (in quanto anonimi)
			if(tl.getTarget().getAnonymous()) {
				TypeCopyVisitor v = new TypeCopyVisitor();
				tl.getTarget().accept(v);
				copy.assignTarget(v.getResult());
			}
			// altrimenti, copio solo il link mantenendo il target originale
			else {
				copy.assignTarget(tl.getTarget());
			}
			
		}

	}
	
	private TypeLink copyTypeLink(TypeLink source) {
		TypeLink tlCopy = TypeLinkFactory.createLink();
		
		// copia il fact value
		if(source.getDefaultValue() != null) {
			FactValueCopyVisitor v = new FactValueCopyVisitor();
			source.getDefaultValue().accept(v);
			tlCopy.setDefaultValue(v.getResult());
		}
		
		tlCopy.setMax(source.getMax());
		tlCopy.setMin(source.getMin());
		tlCopy.setName(source.getName());
		tlCopy.setPriority(source.getPriority());
		return tlCopy;
		
	}
	
	private void basicVisit(Type type) {
		result.setAnonymous(type.getAnonymous());
		result.setDescription(type.getDescription());
		
		if(type.getName() != null ) {
			result.setName(type.getName() + " (Copia)");
			
		}
		
		result.setReadOnly(type.getReadOnly());
		result.setRecurrent(type.getRecurrent());
		result.setTimeRange(type.getTimeRange());
		
	}

	public Type getResult() {
		return result;
	}

	public void setResult(Type result) {
		this.result = result;
	}

}
