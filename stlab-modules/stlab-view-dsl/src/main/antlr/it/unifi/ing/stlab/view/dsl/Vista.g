grammar Vista;

options {
  language = Java;
  superClass = BaseVistaParser;
}

@header {
package it.unifi.ing.stlab.view.dsl;

import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.Tab;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.widgets.ViewerContainer;
import it.unifi.ing.stlab.view.model.widgets.ViewerInput;
import it.unifi.ing.stlab.view.model.widgets.ViewerOutput;
import it.unifi.ing.stlab.view.model.widgets.container.Box;
import it.unifi.ing.stlab.view.model.widgets.container.FactPanel;
import it.unifi.ing.stlab.view.model.widgets.container.ConditionalPanel;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.container.Report;
import it.unifi.ing.stlab.view.model.widgets.container.PanelOrientation;
import it.unifi.ing.stlab.view.model.widgets.container.TabbedPanel;
import it.unifi.ing.stlab.view.model.widgets.container.Paragraph;
import it.unifi.ing.stlab.view.model.widgets.input.Combo;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;
import it.unifi.ing.stlab.view.model.widgets.input.InputText;
import it.unifi.ing.stlab.view.model.widgets.input.InputTemporal;
import it.unifi.ing.stlab.view.model.widgets.input.Suggestion;
import it.unifi.ing.stlab.view.model.widgets.input.TextArea;
import it.unifi.ing.stlab.view.model.widgets.input.FileUpload;
import it.unifi.ing.stlab.view.model.widgets.output.Label;
import it.unifi.ing.stlab.view.model.widgets.output.OutputField;
import it.unifi.ing.stlab.view.model.widgets.output.OutputImage;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputMeasurementUnit;
import it.unifi.ing.stlab.view.model.widgets.output.OutputPath;
import it.unifi.ing.stlab.view.model.widgets.output.OutputType;
import it.unifi.ing.stlab.view.model.widgets.output.OutputValue;
import it.unifi.ing.stlab.view.model.widgets.output.OutputLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;
}

@members {
  public Viewer parse() throws VistaParserException {
    Viewer result = null;
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
      throw new VistaParserException( errorReport() );
    }
  }
}

@lexer::header {
package it.unifi.ing.stlab.view.dsl;
}

@lexer::members {
@Override
public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
   throw new RuntimeException(getErrorHeader(e)+getErrorMessage(e, tokenNames));
}
}

/*
@header {
  package it.unifi.dsi.cci.action.presentation.controllers.generated;
}

@lexer::header {
  package it.unifi.dsi.cci.action.presentation.controllers.generated;
}

conditionalPanel : 'conditionalPanel' '{' ( selector )? ':' NAME gridItem '}';

*/

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
rule returns [Viewer ret]
  :
    (n=NAME { setContext($n.getText()); })?
    v=viewComponent
    {
      ret = v;
      clearStack();
      assignType( ret );
    }
    EOF;


viewComponent returns [Viewer ret]
  : ( r1=inputComponent { ret = r1;}
    | r2=outputComponent { ret = r2; }
    | r3=containerComponent { ret = r3; }
    | r4=view { ret = r4; }
    | r5=custom { ret = r5; });

inputComponent returns [ViewerInput ret]
  : ( r1=inputText { ret = r1;}
    | r2=textArea { ret = r2; }
    | r3=combo { ret = r3; }
    | r4=suggestion { ret = r4; }
    | r5=inputList { ret = r5; }
    | r6=inputTemporal { ret = r6; }
    | r7=fileUpload { ret = r7; });

outputComponent returns [ViewerOutput ret]
  : ( r1=label { ret = r1;}
    | r2=outputType { ret = r2; }
    | r3=outputValue { ret = r3; }
    | r4=outputMeasurementUnit { ret = r4; }
    | r5=outputPath { ret = r5; }
    | r6=outputList  { ret = r6; }
    | r7=outputField { ret = r7; }
    | r8=outputImage { ret = r8; }
    | r9=outputLink { ret = r9; } );


containerComponent returns [ViewerContainer ret]
  : ( r1=grid { ret = r1;}
    | r2=box { ret = r2; }
    | r3=conditionalPanel { ret = r3; }
    | r4=tabbedPanel { ret = r4; }
    | r5=report { ret = r5; }
    | r6=factPanel { ret = r6; }
    | r7=paragraph { ret = r7; });

view returns [Viewer ret]
  : 'view' n=NAME
    { ret = findVista( $n.getText() ); };

inputText returns [InputText ret]
  : 'inputText'
   { boolean required = false;
     boolean readOnly = false;
     Integer length = null; }
   ( 'required' { required = true;} )?
   ( 'readonly' { readOnly = true;} )?
   ( 'length=' num=NUMBER { length = new Integer($num.getText());} )?
   { ret = createInputText( required, length, readOnly ); };

inputTemporal returns [InputTemporal ret]
  : 'inputTemporal'
   { boolean required = false;
     boolean readOnly = false; }
   ( 'required' { required = true;} )?
   ( 'readonly' { readOnly = true;} )?
   { ret = createInputTemporal( required, readOnly ); };

textArea returns [TextArea ret]
  : 'textArea'
   { boolean required = false;
     boolean readOnly = false;
     Integer length = null; }
   ( 'required' { required = true;} )?
   ( 'readonly' { readOnly = true;} )?
   ( 'length=' num=NUMBER { length = new Integer($num.getText());} )?
   { ret = createTextArea( required, length, readOnly ); };

combo returns [Combo ret]
  : 'combo'
   { boolean required = false;
     boolean readOnly = false;}
   ( 'required' { required = true;} )?
   ( 'readonly' { readOnly = true;} )?
   { ret = createCombo( required, readOnly ); };


suggestion returns [Suggestion ret]
  : 'suggestion'
   { boolean required = false; }
   ( 'required' { required = true;} )?
   { ret = createSuggestion( required ); };

inputList returns [InputList ret]
  : 'inputList'
    { boolean vertical = true;}
    ( 'vertical' | 'horizontal' { vertical = false; } )?
    { ret = createInputList( vertical ); }
    '{'
    r1=viewComponent
    { SubViewer sv = createSottoVista( r1 );
        sv.assignSource(ret);
      }
    '}';

fileUpload returns [FileUpload ret]
  : 'fileUpload'
    n=NAME
    { ret =  createFileUpload( $n.getText() ); };

label returns [Label ret]
  : 'label'
    n=NAME
    { ret =  createLabel( $n.getText() ); };

outputValue returns [OutputValue ret]
  : 'outputValue'
    { ret =  createOutputValue(); };


outputType returns [OutputType ret]
  : 'outputType'
    { ret =  createOutputType(); };


outputMeasurementUnit returns [OutputMeasurementUnit ret]
  : 'outputMeasurementUnit'
    { ret =  createOutputMeasurementUnit(); };


outputPath returns [OutputPath ret]
  : 'outputPath'
    { ret = createOutputPath(); };


outputList returns [OutputList ret]
  : 'outputList'
    { boolean vertical = true;}
    ( 'vertical' | 'horizontal' { vertical = false; } )?
    { ret = createOutputList( vertical ); }
    '{'
    r1=viewComponent
    { SubViewer sv = createSottoVista( r1 );
      sv.assignSource(ret);
      }
    '}';

outputImage returns [OutputImage ret]
  : 'outputImage' n=NAME
  { ret= createOutputImage($n.getText()); }
  ;

outputField returns [OutputField ret]
  : 'out' {ret = createOutputField(); }
    n1 = ( 'Appointment' | 'Patient' | 'Agenda' | 'User' ) { ret.setRoot($n1.getText()); }
    ('.' n2 = FIELD { ret.addFieldPath($n2.getText()); } )+
    ;

outputLink returns [OutputLink ret]
  : 'outputLink'
    { ret =  createOutputLink(); };

grid returns [Grid ret]
  : 'grid'
    { PanelOrientation style = PanelOrientation.vertical; }
    ( 'vertical' |
      'horizontal' { style = PanelOrientation.horizontal; } |
      'spaced_horizontal' { style = PanelOrientation.spaced_horizontal; })?
    { ret = createGrid( style ); }
    ('collapse' {ret.setCollapse(true);})?
    '{'
    ( r1 = gridItem {
      r1.assignSource(ret);
    })+
    '}';

custom returns [ViewerCustom ret]
  : 'custom' n=NAME
  {ret = createCustom( $n.getText() );}
  '{'
    ( r1 = gridItem {
      r1.assignSource(ret);
    })+
    '}';

gridItem returns [SubViewer ret]
  : { TypeSelector s = null; }
    ( r1=selector
      { s = r1;}
    )?
    ':'
    v=viewComponent
    { ret = createSottoVista( s, v );
      popStack(s); };

selector returns [TypeSelector ret]
  :
    n1=NAME
   { ret = createTipoOsservazioneSelector( $n1.getText() );
     TypeSelector tmp = ret;
     pushStack(tmp); }
   ('.' n2=NAME
      { tmp.assignNext( createTipoOsservazioneSelector( $n2.getText() ));
        tmp = tmp.getNext();
        pushStack(tmp); }
   )*;

box returns [Box ret]
  : 'box'
  { ret = createBox(); }
  ('collapse' {ret.setCollapse(true);})?
  '{'
  r1 = gridItem {
  r1.assignSource(ret);
  }
  ( r2 = gridItem {
    r2.assignSource(ret);
  })?
  '}';

paragraph returns [Paragraph ret]
  : 'paragraph'
    { ret = createParagraph(); }
    ('collapse' {ret.setCollapse(true);})?
    '{'
    ( r1 = gridItem {
      r1.assignSource(ret);
    })+
    '}';

conditionalPanel returns [ConditionalPanel ret]
  : 'conditionalPanel'
  { ret = createConditionalPanel();
    ret.setClear( false ); }
  '{'
  s=selector {
      ret.addSelector( s );
      popStack( s );
  }
  ':'
  n=NAME { ret.addFenomeno( findFenomeno( s , $n.getText() )); }

  (op=('and' | 'or') { ret.addOperator($op.getText()); }
  s=selector {
      ret.addSelector( s );
      popStack( s );
  }
  ':'
  n=NAME { ret.addFenomeno( findFenomeno( s , $n.getText() )); }
  )*

  ('clear' cs=selector {
      ret.setClear( true );
      ret.setClearSelector( cs );
      popStack( cs );
    }
  )?

  ('clear-multi' cs=selector {
      ret.setClear( true );
      ret.addClearSelector( cs );
      popStack( cs );
    }
    (',' cs2=selector
       { ret.addClearSelector( cs2 );
         popStack( cs2 );
       }
    )*
  )?

  g=gridItem {
    g.assignSource(ret);
    }
  '}';

report returns [Report ret]
  : 'report' { ret = createReport(); }
  '{'
  r1 = gridItem {
      r1.assignSource(ret);
    }
  r2 = gridItem {
      r2.assignSource(ret);
    }
  r3 = gridItem {
      r3.assignSource(ret);
    }
  '}';


factPanel returns [FactPanel ret]
  : 'factPanel' { ret = createFactPanel(); }
  'query' n=NAME { ret.setQuery( findFactQuery( $n.getText() ) ); }
  '{'
  ( r1 = gridItem {
      r1.assignSource(ret);
    })+
  '}';

tabbedPanel returns [TabbedPanel ret]
  : 'tabbedPanel'
  { ret= createTabbedPanel(); }
  '{'
   ( t1=tab {
        t1.assignSource(ret);
      } )+
  '}';

tab returns [Tab ret]
  : n=NAME
    ';'
    { TypeSelector s = null; }
    ( r1=selector
      { s = r1;}
    )?
    ':'
    v=viewComponent
    { ret = createTab( $n.getText(), s, v );
      popStack(s); };

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

NAME : '"' ('a'..'z' |'A'..'Z'| '0'..'9' | '-' | '<' | '>' | '@' | '\'' | '?' |
            ',' | '\u00B0' |'[' | ']' | '(' | ')' | ':' | '.' | ';' | '/' |
            '_' | '\u00E0' | '\u00F2' | '\u00E8' | '\u00E9' | '\u00F9' | '\u00EC' | '\u00C8' | '%' | ' ' )+ '"';

NUMBER : ('0'..'9')+;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+    { skip(); } ;

ILLEGAL : .;

FIELD : ('Name' | 'Surname' | 'Sex' | 'BirthDate' | 'BirthPlace' | 'TaxCode' | 'SsnCode' | 'Qualifications' |
          'Residence' | 'Domicile' | 'Region' | 'HomePhone' | 'WorkPhone' | 'Nationality' | 'Asl' | 'Date' |
          'Number' | 'BookingCode' | 'AcceptanceCode' | 'Code' | 'Description' | 'Mail' );
