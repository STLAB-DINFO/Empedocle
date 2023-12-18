package it.unifi.ing.stlab.empedocle.actions.util.taxcode;

public class FiscalCodeValidatorException extends Exception {

    private static final long serialVersionUID = -6338223176043548995L;

    public FiscalCodeValidatorException() {
        super();
    }

    public FiscalCodeValidatorException( String message, Throwable cause ) {
        super( message, cause );
    }

    public FiscalCodeValidatorException( String message ) {
        super( message );
    }

    public FiscalCodeValidatorException( Throwable cause ) {
        super( cause );
    }
}
