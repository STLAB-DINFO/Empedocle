package it.unifi.ing.stlab.factquery.model.expression;

public enum ComparisonOperator {

	EQUALS("eq", "è uguale a", "="),
	NOT_EQUALS("not", "non è uguale a", "!="),
	GREATER_THAN("gt", "è maggiore di", ">"),
	GREATER_EQUALS("ge", "è maggiore/uguale di", ">="),
	LESS_THAN("lt", "è minore di", "<"),
	LESS_EQUALS("le", "è minore/uguale di", "<="),
	NULL("null", "è nullo", "is null"),
	NOT_NULL("not null", "non è nullo", "is not null");
	
	
	private String id;
	private String name;
	private String symbol;

	
	private ComparisonOperator(String id, String name, String symbol){
		this.id = id;
		this.name = name;
		this.symbol = symbol;
	}

	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSymbol() {
		return symbol;
	}
	
	
	
}
