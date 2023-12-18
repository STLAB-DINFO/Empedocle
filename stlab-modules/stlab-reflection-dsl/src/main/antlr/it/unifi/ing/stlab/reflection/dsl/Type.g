grammar Type;


options {
  language = Java;
  superClass = BaseTypeParser;
}

@lexer::header {
package it.unifi.ing.stlab.reflection.dsl;
}

@parser::header {
package it.unifi.ing.stlab.reflection.dsl;

import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.commons.util.TimeFormat;
}

@parser::members {
  public Type parse() throws TypeParserException {
    Type result = null;
    try {
      result = rule();
    } catch (IllegalArgumentException e) {
      extendedReportError( e );
    } catch (RecognitionException re) {
        reportError(re);
        recover(input,re);
    }

    if ( isOk() ) {
      return result;
    } else {
      throw new TypeParserException( errorReport() );
    }
  }
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
rule returns [Type ret]
  : ( r1=tx { ret = r1; }
  | r2=ql { ret = r2; }
  | r3=st { ret = r3; }
  | r4=qt { ret = r4; }
  | r5=dt { ret = r5; }
  | r6=ct { ret = r6; })
  { ret.setAnonymous( false ); }
  EOF;

tx returns [TextualType ret]
  : 'tx'
    { boolean recurrent = false;
        ret = createTextualType(); }
    ( 'recurrent' { recurrent = true; } )?
    { ret.setRecurrent( recurrent ); ret.setAnonymous( true ); };

ql returns [EnumeratedType ret]
  : 'ql'
    { boolean recurrent = false;
      boolean ordered = false;
        ret = createEnumeratedType(); }
    ( 'recurrent' { recurrent = true; } )?
    { ret.setRecurrent( recurrent ); ret.setAnonymous( true ); }
    ( 'ordered' { ordered = true; } )?
    '{'
      n1=NAME { createPhenomenon( ret, trim( $n1.getText() ), ordered ); }
      (',' n2=NAME { createPhenomenon( ret, trim( $n2.getText() ), ordered ); } )*
    '}';


st returns [QueriedType ret]
  : 'st'
    { boolean recurrent = false;
        ret = createQueriedType(); }
    ( 'recurrent' { recurrent = true; } )?
    { ret.setRecurrent( recurrent ); ret.setAnonymous( true ); }
    '{'
      n=NAME { ret.setQueryDef( trim( $n.getText() )); }
    '}';


qt returns [QuantitativeType ret]
  : 'qt'
    { boolean recurrent = false;
        ret = createQuantitativeType(); }
    ( 'recurrent' { recurrent = true; } )?
    { ret.setRecurrent( recurrent ); ret.setAnonymous( true ); }
    '{'
      r1 = um { ret.addUnit( r1 ); }
      (',' r2 = um { ret.addUnit( r2 ); } )*
    '}';

dt returns [TemporalType ret]
  : 'dt'
    { boolean recurrent = false;
        ret = createTemporalType();
        ret.setType( TimeFormat.DATE ); }
    ( 'date'
      | 'datetime' { ret.setType( TimeFormat.DATETIME ); }
      | 'time' { ret.setType( TimeFormat.TIME ); }
      | 'year' { ret.setType( TimeFormat.YEAR ); }
      | 'month_year' { ret.setType( TimeFormat.MONTH_YEAR ); } )?
    ( 'recurrent' { recurrent = true; } )?
    { ret.setRecurrent( recurrent );
      ret.setAnonymous( true ); };

um returns [UnitUse ret]
  : n1=NAME
  '('
  n2=NUMBER
  ','
  n3=NUMBER
  ')'
  { ret = createUnitUse( trim( $n1.getText() ), integer( $n2.getText() ), integer( $n3.getText() )); };


ct returns [CompositeType ret]
  : 'ct'
    { long priority = 0;
      boolean recurrent = false;
        ret = createCompositeType(); }
    ( 'recurrent' { recurrent = true; } )?
    { ret.setRecurrent( recurrent ); ret.setAnonymous( true ); }
    '{'
      r1 = sct { r1.assignSource( ret ); r1.setPriority( priority); priority++;}
      (',' r2 = sct { r2.assignSource( ret ); r2.setPriority( priority); priority++; })*
    '}';


sct returns [TypeLink ret]
  : { ret = createTypeLink(); }
  n1 = NAME
  { ret.setName( trim( $n1.getText()));
    ret.setMin( 1 );
    ret.setMax( 1 );
  }
  ( '('
    n2 = NUMBER { ret.setMin( integer( $n2.getText())); }
    ','
    ( n3 = NUMBER { ret.setMax( integer( $n3.getText() )); }
    | 'unbounded' { ret.setMax( 0 ); } )
  ')' )?
  ':'
  { Type sc = null; }
  ( sc1 = tx { sc = sc1; }
  | sc2 = ql { sc = sc2; }
  | sc3 = dt { sc = sc3; }
  | sc4 = st { sc = sc4; }
  | sc5 = qt { sc = sc5; }
  | sc6 = ct { sc = sc6; }
  | n4 = NAME { sc = findType( trim( $n4.getText())); })
  { ret.assignTarget( sc ); };


/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

NAME : '"' ('a'..'z' |'A'..'Z'| '0'..'9' | '-' | '+' | '<' | '>' | '=' | '\u2264' | '\u2265' | '@' | '\'' | '?' | 
            ',' | ';' | '\u00B0' |'[' | ']' | '(' | ')' | ':' | '.' | '/' |
            '_' | '\u00E0' | '\u00F2' | '\u00E8' | '\u00E9' | '\\u00F9' | '\\u00EC' | '\\u00C8' | ' ' )+ '"';

NUMBER : ('0'..'9')+;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+    { skip(); } ;

ILLEGAL : .; 
