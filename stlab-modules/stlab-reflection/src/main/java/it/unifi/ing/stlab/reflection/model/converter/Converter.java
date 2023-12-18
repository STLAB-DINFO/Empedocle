package it.unifi.ing.stlab.reflection.model.converter;

import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.types.Unit;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

public class Converter {

	private Unit unitFrom;
	private Unit unitTo;
	private String expression;

	public Converter(Unit from, Unit to, String expr) {
		this.unitFrom = from;
		this.unitTo = to;
		this.expression = expr;
	}
	
	public Quantity convert(Quantity quantity) {
		Quantity result = new Quantity();
		result.setUnit(unitTo);	    
	    result.setValue( evaluate(expression, quantity.getValue()) );		
		
		return result;
	}

	private Double evaluate(String expression, Double value) {
		JexlEngine jexlEngine = new JexlEngine();
		jexlEngine.setSilent(false);
		jexlEngine.setStrict(true);
		
		Expression e = jexlEngine.createExpression( expression );

	    JexlContext context = new MapContext();
	    context.set("x", value);
	    
	    return (Double)e.evaluate(context);
	}

	public Unit getUnitFrom() {
		return unitFrom;
	}
	public void setUnitFrom(Unit unitFrom) {
		this.unitFrom = unitFrom;
	}

	public Unit getUnitTo() {
		return unitTo;
	}
	public void setUnitTo(Unit unitTo) {
		this.unitTo = unitTo;
	}
	
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}	
}