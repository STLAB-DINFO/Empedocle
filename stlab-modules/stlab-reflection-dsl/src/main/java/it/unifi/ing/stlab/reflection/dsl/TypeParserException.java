package it.unifi.ing.stlab.reflection.dsl;

public class TypeParserException extends Exception {

	private static final long serialVersionUID = 1L;

	public TypeParserException() {
		super();
	}

	public TypeParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypeParserException(String message) {
		super(message);
	}

	public TypeParserException(Throwable cause) {
		super(cause);
	}
}
