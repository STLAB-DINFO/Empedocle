// $ANTLR 3.5.2 /Users/fulvio/Desktop/Vista.g/Vista.g 2018-11-07 12:40:22

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class VistaParser extends BaseVistaParser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "FIELD", "ILLEGAL", "NAME", "NUMBER", 
		"WHITESPACE", "','", "'.'", "':'", "';'", "'Agenda'", "'Appointment'", 
		"'Patient'", "'User'", "'and'", "'box'", "'clear'", "'clear-multi'", "'collapse'", 
		"'combo'", "'conditionalPanel'", "'custom'", "'factPanel'", "'fileUpload'", 
		"'grid'", "'horizontal'", "'inputList'", "'inputTemporal'", "'inputText'", 
		"'label'", "'length='", "'or'", "'out'", "'outputImage'", "'outputLink'", 
		"'outputList'", "'outputMeasurementUnit'", "'outputPath'", "'outputType'", 
		"'outputValue'", "'paragraph'", "'query'", "'readonly'", "'report'", "'required'", 
		"'spaced_horizontal'", "'suggestion'", "'tabbedPanel'", "'textArea'", 
		"'vertical'", "'view'", "'{'", "'}'"
	};
	public static final int EOF=-1;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int T__32=32;
	public static final int T__33=33;
	public static final int T__34=34;
	public static final int T__35=35;
	public static final int T__36=36;
	public static final int T__37=37;
	public static final int T__38=38;
	public static final int T__39=39;
	public static final int T__40=40;
	public static final int T__41=41;
	public static final int T__42=42;
	public static final int T__43=43;
	public static final int T__44=44;
	public static final int T__45=45;
	public static final int T__46=46;
	public static final int T__47=47;
	public static final int T__48=48;
	public static final int T__49=49;
	public static final int T__50=50;
	public static final int T__51=51;
	public static final int T__52=52;
	public static final int T__53=53;
	public static final int T__54=54;
	public static final int T__55=55;
	public static final int FIELD=4;
	public static final int ILLEGAL=5;
	public static final int NAME=6;
	public static final int NUMBER=7;
	public static final int WHITESPACE=8;

	// delegates
	public BaseVistaParser[] getDelegates() {
		return new BaseVistaParser[] {};
	}

	// delegators


	public VistaParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public VistaParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return VistaParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/Users/fulvio/Desktop/Vista.g/Vista.g"; }


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



	// $ANTLR start "rule"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:92:1: rule returns [Viewer ret] : (n= NAME )? v= viewComponent EOF ;
	public final Viewer rule() throws RecognitionException {
		Viewer ret = null;


		Token n=null;
		Viewer v =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:93:3: ( (n= NAME )? v= viewComponent EOF )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:94:5: (n= NAME )? v= viewComponent EOF
			{
			// /Users/fulvio/Desktop/Vista.g/Vista.g:94:5: (n= NAME )?
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0==NAME) ) {
				alt1=1;
			}
			switch (alt1) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:94:6: n= NAME
					{
					n=(Token)match(input,NAME,FOLLOW_NAME_in_rule81); 
					 setContext(n.getText()); 
					}
					break;

			}

			pushFollow(FOLLOW_viewComponent_in_rule93);
			v=viewComponent();
			state._fsp--;


			      ret = v;
			      clearStack();
			      assignType( ret );
			    
			match(input,EOF,FOLLOW_EOF_in_rule105); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "rule"



	// $ANTLR start "viewComponent"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:104:1: viewComponent returns [Viewer ret] : (r1= inputComponent |r2= outputComponent |r3= containerComponent |r4= view |r5= custom ) ;
	public final Viewer viewComponent() throws RecognitionException {
		Viewer ret = null;


		ViewerInput r1 =null;
		ViewerOutput r2 =null;
		ViewerContainer r3 =null;
		Viewer r4 =null;
		ViewerCustom r5 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:105:3: ( (r1= inputComponent |r2= outputComponent |r3= containerComponent |r4= view |r5= custom ) )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:105:5: (r1= inputComponent |r2= outputComponent |r3= containerComponent |r4= view |r5= custom )
			{
			// /Users/fulvio/Desktop/Vista.g/Vista.g:105:5: (r1= inputComponent |r2= outputComponent |r3= containerComponent |r4= view |r5= custom )
			int alt2=5;
			switch ( input.LA(1) ) {
			case 22:
			case 26:
			case 29:
			case 30:
			case 31:
			case 49:
			case 51:
				{
				alt2=1;
				}
				break;
			case 32:
			case 35:
			case 36:
			case 37:
			case 38:
			case 39:
			case 40:
			case 41:
			case 42:
				{
				alt2=2;
				}
				break;
			case 18:
			case 23:
			case 25:
			case 27:
			case 43:
			case 46:
			case 50:
				{
				alt2=3;
				}
				break;
			case 53:
				{
				alt2=4;
				}
				break;
			case 24:
				{
				alt2=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}
			switch (alt2) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:105:7: r1= inputComponent
					{
					pushFollow(FOLLOW_inputComponent_in_viewComponent124);
					r1=inputComponent();
					state._fsp--;

					 ret = r1;
					}
					break;
				case 2 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:106:7: r2= outputComponent
					{
					pushFollow(FOLLOW_outputComponent_in_viewComponent136);
					r2=outputComponent();
					state._fsp--;

					 ret = r2; 
					}
					break;
				case 3 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:107:7: r3= containerComponent
					{
					pushFollow(FOLLOW_containerComponent_in_viewComponent148);
					r3=containerComponent();
					state._fsp--;

					 ret = r3; 
					}
					break;
				case 4 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:108:7: r4= view
					{
					pushFollow(FOLLOW_view_in_viewComponent160);
					r4=view();
					state._fsp--;

					 ret = r4; 
					}
					break;
				case 5 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:109:7: r5= custom
					{
					pushFollow(FOLLOW_custom_in_viewComponent172);
					r5=custom();
					state._fsp--;

					 ret = r5; 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "viewComponent"



	// $ANTLR start "inputComponent"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:111:1: inputComponent returns [ViewerInput ret] : (r1= inputText |r2= textArea |r3= combo |r4= suggestion |r5= inputList |r6= inputTemporal |r7= fileUpload ) ;
	public final ViewerInput inputComponent() throws RecognitionException {
		ViewerInput ret = null;


		InputText r1 =null;
		TextArea r2 =null;
		Combo r3 =null;
		Suggestion r4 =null;
		InputList r5 =null;
		InputTemporal r6 =null;
		FileUpload r7 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:112:3: ( (r1= inputText |r2= textArea |r3= combo |r4= suggestion |r5= inputList |r6= inputTemporal |r7= fileUpload ) )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:112:5: (r1= inputText |r2= textArea |r3= combo |r4= suggestion |r5= inputList |r6= inputTemporal |r7= fileUpload )
			{
			// /Users/fulvio/Desktop/Vista.g/Vista.g:112:5: (r1= inputText |r2= textArea |r3= combo |r4= suggestion |r5= inputList |r6= inputTemporal |r7= fileUpload )
			int alt3=7;
			switch ( input.LA(1) ) {
			case 31:
				{
				alt3=1;
				}
				break;
			case 51:
				{
				alt3=2;
				}
				break;
			case 22:
				{
				alt3=3;
				}
				break;
			case 49:
				{
				alt3=4;
				}
				break;
			case 29:
				{
				alt3=5;
				}
				break;
			case 30:
				{
				alt3=6;
				}
				break;
			case 26:
				{
				alt3=7;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}
			switch (alt3) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:112:7: r1= inputText
					{
					pushFollow(FOLLOW_inputText_in_inputComponent193);
					r1=inputText();
					state._fsp--;

					 ret = r1;
					}
					break;
				case 2 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:113:7: r2= textArea
					{
					pushFollow(FOLLOW_textArea_in_inputComponent205);
					r2=textArea();
					state._fsp--;

					 ret = r2; 
					}
					break;
				case 3 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:114:7: r3= combo
					{
					pushFollow(FOLLOW_combo_in_inputComponent217);
					r3=combo();
					state._fsp--;

					 ret = r3; 
					}
					break;
				case 4 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:115:7: r4= suggestion
					{
					pushFollow(FOLLOW_suggestion_in_inputComponent229);
					r4=suggestion();
					state._fsp--;

					 ret = r4; 
					}
					break;
				case 5 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:116:7: r5= inputList
					{
					pushFollow(FOLLOW_inputList_in_inputComponent241);
					r5=inputList();
					state._fsp--;

					 ret = r5; 
					}
					break;
				case 6 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:117:7: r6= inputTemporal
					{
					pushFollow(FOLLOW_inputTemporal_in_inputComponent253);
					r6=inputTemporal();
					state._fsp--;

					 ret = r6; 
					}
					break;
				case 7 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:118:7: r7= fileUpload
					{
					pushFollow(FOLLOW_fileUpload_in_inputComponent265);
					r7=fileUpload();
					state._fsp--;

					 ret = r7; 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "inputComponent"



	// $ANTLR start "outputComponent"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:120:1: outputComponent returns [ViewerOutput ret] : (r1= label |r2= outputType |r3= outputValue |r4= outputMeasurementUnit |r5= outputPath |r6= outputList |r7= outputField |r8= outputImage |r9= outputLink ) ;
	public final ViewerOutput outputComponent() throws RecognitionException {
		ViewerOutput ret = null;


		Label r1 =null;
		OutputType r2 =null;
		OutputValue r3 =null;
		OutputMeasurementUnit r4 =null;
		OutputPath r5 =null;
		OutputList r6 =null;
		OutputField r7 =null;
		OutputImage r8 =null;
		OutputLink r9 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:121:3: ( (r1= label |r2= outputType |r3= outputValue |r4= outputMeasurementUnit |r5= outputPath |r6= outputList |r7= outputField |r8= outputImage |r9= outputLink ) )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:121:5: (r1= label |r2= outputType |r3= outputValue |r4= outputMeasurementUnit |r5= outputPath |r6= outputList |r7= outputField |r8= outputImage |r9= outputLink )
			{
			// /Users/fulvio/Desktop/Vista.g/Vista.g:121:5: (r1= label |r2= outputType |r3= outputValue |r4= outputMeasurementUnit |r5= outputPath |r6= outputList |r7= outputField |r8= outputImage |r9= outputLink )
			int alt4=9;
			switch ( input.LA(1) ) {
			case 32:
				{
				alt4=1;
				}
				break;
			case 41:
				{
				alt4=2;
				}
				break;
			case 42:
				{
				alt4=3;
				}
				break;
			case 39:
				{
				alt4=4;
				}
				break;
			case 40:
				{
				alt4=5;
				}
				break;
			case 38:
				{
				alt4=6;
				}
				break;
			case 35:
				{
				alt4=7;
				}
				break;
			case 36:
				{
				alt4=8;
				}
				break;
			case 37:
				{
				alt4=9;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}
			switch (alt4) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:121:7: r1= label
					{
					pushFollow(FOLLOW_label_in_outputComponent286);
					r1=label();
					state._fsp--;

					 ret = r1;
					}
					break;
				case 2 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:122:7: r2= outputType
					{
					pushFollow(FOLLOW_outputType_in_outputComponent298);
					r2=outputType();
					state._fsp--;

					 ret = r2; 
					}
					break;
				case 3 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:123:7: r3= outputValue
					{
					pushFollow(FOLLOW_outputValue_in_outputComponent310);
					r3=outputValue();
					state._fsp--;

					 ret = r3; 
					}
					break;
				case 4 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:124:7: r4= outputMeasurementUnit
					{
					pushFollow(FOLLOW_outputMeasurementUnit_in_outputComponent322);
					r4=outputMeasurementUnit();
					state._fsp--;

					 ret = r4; 
					}
					break;
				case 5 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:125:7: r5= outputPath
					{
					pushFollow(FOLLOW_outputPath_in_outputComponent334);
					r5=outputPath();
					state._fsp--;

					 ret = r5; 
					}
					break;
				case 6 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:126:7: r6= outputList
					{
					pushFollow(FOLLOW_outputList_in_outputComponent346);
					r6=outputList();
					state._fsp--;

					 ret = r6; 
					}
					break;
				case 7 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:127:7: r7= outputField
					{
					pushFollow(FOLLOW_outputField_in_outputComponent359);
					r7=outputField();
					state._fsp--;

					 ret = r7; 
					}
					break;
				case 8 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:128:7: r8= outputImage
					{
					pushFollow(FOLLOW_outputImage_in_outputComponent371);
					r8=outputImage();
					state._fsp--;

					 ret = r8; 
					}
					break;
				case 9 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:129:7: r9= outputLink
					{
					pushFollow(FOLLOW_outputLink_in_outputComponent383);
					r9=outputLink();
					state._fsp--;

					 ret = r9; 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "outputComponent"



	// $ANTLR start "containerComponent"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:132:1: containerComponent returns [ViewerContainer ret] : (r1= grid |r2= box |r3= conditionalPanel |r4= tabbedPanel |r5= report |r6= factPanel |r7= paragraph ) ;
	public final ViewerContainer containerComponent() throws RecognitionException {
		ViewerContainer ret = null;


		Grid r1 =null;
		Box r2 =null;
		ConditionalPanel r3 =null;
		TabbedPanel r4 =null;
		Report r5 =null;
		FactPanel r6 =null;
		Paragraph r7 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:133:3: ( (r1= grid |r2= box |r3= conditionalPanel |r4= tabbedPanel |r5= report |r6= factPanel |r7= paragraph ) )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:133:5: (r1= grid |r2= box |r3= conditionalPanel |r4= tabbedPanel |r5= report |r6= factPanel |r7= paragraph )
			{
			// /Users/fulvio/Desktop/Vista.g/Vista.g:133:5: (r1= grid |r2= box |r3= conditionalPanel |r4= tabbedPanel |r5= report |r6= factPanel |r7= paragraph )
			int alt5=7;
			switch ( input.LA(1) ) {
			case 27:
				{
				alt5=1;
				}
				break;
			case 18:
				{
				alt5=2;
				}
				break;
			case 23:
				{
				alt5=3;
				}
				break;
			case 50:
				{
				alt5=4;
				}
				break;
			case 46:
				{
				alt5=5;
				}
				break;
			case 25:
				{
				alt5=6;
				}
				break;
			case 43:
				{
				alt5=7;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}
			switch (alt5) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:133:7: r1= grid
					{
					pushFollow(FOLLOW_grid_in_containerComponent406);
					r1=grid();
					state._fsp--;

					 ret = r1;
					}
					break;
				case 2 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:134:7: r2= box
					{
					pushFollow(FOLLOW_box_in_containerComponent418);
					r2=box();
					state._fsp--;

					 ret = r2; 
					}
					break;
				case 3 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:135:7: r3= conditionalPanel
					{
					pushFollow(FOLLOW_conditionalPanel_in_containerComponent430);
					r3=conditionalPanel();
					state._fsp--;

					 ret = r3; 
					}
					break;
				case 4 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:136:7: r4= tabbedPanel
					{
					pushFollow(FOLLOW_tabbedPanel_in_containerComponent442);
					r4=tabbedPanel();
					state._fsp--;

					 ret = r4; 
					}
					break;
				case 5 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:137:7: r5= report
					{
					pushFollow(FOLLOW_report_in_containerComponent454);
					r5=report();
					state._fsp--;

					 ret = r5; 
					}
					break;
				case 6 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:138:7: r6= factPanel
					{
					pushFollow(FOLLOW_factPanel_in_containerComponent466);
					r6=factPanel();
					state._fsp--;

					 ret = r6; 
					}
					break;
				case 7 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:139:7: r7= paragraph
					{
					pushFollow(FOLLOW_paragraph_in_containerComponent478);
					r7=paragraph();
					state._fsp--;

					 ret = r7; 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "containerComponent"



	// $ANTLR start "view"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:141:1: view returns [Viewer ret] : 'view' n= NAME ;
	public final Viewer view() throws RecognitionException {
		Viewer ret = null;


		Token n=null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:142:3: ( 'view' n= NAME )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:142:5: 'view' n= NAME
			{
			match(input,53,FOLLOW_53_in_view495); 
			n=(Token)match(input,NAME,FOLLOW_NAME_in_view499); 
			 ret = findVista( n.getText() ); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "view"



	// $ANTLR start "inputText"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:145:1: inputText returns [InputText ret] : 'inputText' ( 'required' )? ( 'readonly' )? ( 'length=' num= NUMBER )? ;
	public final InputText inputText() throws RecognitionException {
		InputText ret = null;


		Token num=null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:146:3: ( 'inputText' ( 'required' )? ( 'readonly' )? ( 'length=' num= NUMBER )? )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:146:5: 'inputText' ( 'required' )? ( 'readonly' )? ( 'length=' num= NUMBER )?
			{
			match(input,31,FOLLOW_31_in_inputText519); 
			 boolean required = false;
			     boolean readOnly = false;
			     Integer length = null; 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:150:4: ( 'required' )?
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==47) ) {
				alt6=1;
			}
			switch (alt6) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:150:6: 'required'
					{
					match(input,47,FOLLOW_47_in_inputText531); 
					 required = true;
					}
					break;

			}

			// /Users/fulvio/Desktop/Vista.g/Vista.g:151:4: ( 'readonly' )?
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==45) ) {
				alt7=1;
			}
			switch (alt7) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:151:6: 'readonly'
					{
					match(input,45,FOLLOW_45_in_inputText543); 
					 readOnly = true;
					}
					break;

			}

			// /Users/fulvio/Desktop/Vista.g/Vista.g:152:4: ( 'length=' num= NUMBER )?
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0==33) ) {
				alt8=1;
			}
			switch (alt8) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:152:6: 'length=' num= NUMBER
					{
					match(input,33,FOLLOW_33_in_inputText555); 
					num=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_inputText559); 
					 length = new Integer(num.getText());
					}
					break;

			}

			 ret = createInputText( required, length, readOnly ); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "inputText"



	// $ANTLR start "inputTemporal"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:155:1: inputTemporal returns [InputTemporal ret] : 'inputTemporal' ( 'required' )? ( 'readonly' )? ;
	public final InputTemporal inputTemporal() throws RecognitionException {
		InputTemporal ret = null;


		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:156:3: ( 'inputTemporal' ( 'required' )? ( 'readonly' )? )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:156:5: 'inputTemporal' ( 'required' )? ( 'readonly' )?
			{
			match(input,30,FOLLOW_30_in_inputTemporal583); 
			 boolean required = false;
			     boolean readOnly = false; 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:159:4: ( 'required' )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==47) ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:159:6: 'required'
					{
					match(input,47,FOLLOW_47_in_inputTemporal595); 
					 required = true;
					}
					break;

			}

			// /Users/fulvio/Desktop/Vista.g/Vista.g:160:4: ( 'readonly' )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==45) ) {
				alt10=1;
			}
			switch (alt10) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:160:6: 'readonly'
					{
					match(input,45,FOLLOW_45_in_inputTemporal607); 
					 readOnly = true;
					}
					break;

			}

			 ret = createInputTemporal( required, readOnly ); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "inputTemporal"



	// $ANTLR start "textArea"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:163:1: textArea returns [TextArea ret] : 'textArea' ( 'required' )? ( 'readonly' )? ( 'length=' num= NUMBER )? ;
	public final TextArea textArea() throws RecognitionException {
		TextArea ret = null;


		Token num=null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:164:3: ( 'textArea' ( 'required' )? ( 'readonly' )? ( 'length=' num= NUMBER )? )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:164:5: 'textArea' ( 'required' )? ( 'readonly' )? ( 'length=' num= NUMBER )?
			{
			match(input,51,FOLLOW_51_in_textArea631); 
			 boolean required = false;
			     boolean readOnly = false;
			     Integer length = null; 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:168:4: ( 'required' )?
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( (LA11_0==47) ) {
				alt11=1;
			}
			switch (alt11) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:168:6: 'required'
					{
					match(input,47,FOLLOW_47_in_textArea643); 
					 required = true;
					}
					break;

			}

			// /Users/fulvio/Desktop/Vista.g/Vista.g:169:4: ( 'readonly' )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==45) ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:169:6: 'readonly'
					{
					match(input,45,FOLLOW_45_in_textArea655); 
					 readOnly = true;
					}
					break;

			}

			// /Users/fulvio/Desktop/Vista.g/Vista.g:170:4: ( 'length=' num= NUMBER )?
			int alt13=2;
			int LA13_0 = input.LA(1);
			if ( (LA13_0==33) ) {
				alt13=1;
			}
			switch (alt13) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:170:6: 'length=' num= NUMBER
					{
					match(input,33,FOLLOW_33_in_textArea667); 
					num=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_textArea671); 
					 length = new Integer(num.getText());
					}
					break;

			}

			 ret = createTextArea( required, length, readOnly ); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "textArea"



	// $ANTLR start "combo"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:173:1: combo returns [Combo ret] : 'combo' ( 'required' )? ( 'readonly' )? ;
	public final Combo combo() throws RecognitionException {
		Combo ret = null;


		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:174:3: ( 'combo' ( 'required' )? ( 'readonly' )? )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:174:5: 'combo' ( 'required' )? ( 'readonly' )?
			{
			match(input,22,FOLLOW_22_in_combo695); 
			 boolean required = false;
			     boolean readOnly = false;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:177:4: ( 'required' )?
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==47) ) {
				alt14=1;
			}
			switch (alt14) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:177:6: 'required'
					{
					match(input,47,FOLLOW_47_in_combo707); 
					 required = true;
					}
					break;

			}

			// /Users/fulvio/Desktop/Vista.g/Vista.g:178:4: ( 'readonly' )?
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==45) ) {
				alt15=1;
			}
			switch (alt15) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:178:6: 'readonly'
					{
					match(input,45,FOLLOW_45_in_combo719); 
					 readOnly = true;
					}
					break;

			}

			 ret = createCombo( required, readOnly ); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "combo"



	// $ANTLR start "suggestion"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:182:1: suggestion returns [Suggestion ret] : 'suggestion' ( 'required' )? ;
	public final Suggestion suggestion() throws RecognitionException {
		Suggestion ret = null;


		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:183:3: ( 'suggestion' ( 'required' )? )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:183:5: 'suggestion' ( 'required' )?
			{
			match(input,49,FOLLOW_49_in_suggestion744); 
			 boolean required = false; 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:185:4: ( 'required' )?
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( (LA16_0==47) ) {
				alt16=1;
			}
			switch (alt16) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:185:6: 'required'
					{
					match(input,47,FOLLOW_47_in_suggestion756); 
					 required = true;
					}
					break;

			}

			 ret = createSuggestion( required ); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "suggestion"



	// $ANTLR start "inputList"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:188:1: inputList returns [InputList ret] : 'inputList' ( 'vertical' | 'horizontal' )? '{' r1= viewComponent '}' ;
	public final InputList inputList() throws RecognitionException {
		InputList ret = null;


		Viewer r1 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:189:3: ( 'inputList' ( 'vertical' | 'horizontal' )? '{' r1= viewComponent '}' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:189:5: 'inputList' ( 'vertical' | 'horizontal' )? '{' r1= viewComponent '}'
			{
			match(input,29,FOLLOW_29_in_inputList780); 
			 boolean vertical = true;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:191:5: ( 'vertical' | 'horizontal' )?
			int alt17=3;
			int LA17_0 = input.LA(1);
			if ( (LA17_0==52) ) {
				alt17=1;
			}
			else if ( (LA17_0==28) ) {
				alt17=2;
			}
			switch (alt17) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:191:7: 'vertical'
					{
					match(input,52,FOLLOW_52_in_inputList794); 
					}
					break;
				case 2 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:191:20: 'horizontal'
					{
					match(input,28,FOLLOW_28_in_inputList798); 
					 vertical = false; 
					}
					break;

			}

			 ret = createInputList( vertical ); 
			match(input,54,FOLLOW_54_in_inputList815); 
			pushFollow(FOLLOW_viewComponent_in_inputList823);
			r1=viewComponent();
			state._fsp--;

			 SubViewer sv = createSottoVista( r1 );
			        sv.assignSource(ret);
			      
			match(input,55,FOLLOW_55_in_inputList835); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "inputList"



	// $ANTLR start "fileUpload"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:200:1: fileUpload returns [FileUpload ret] : 'fileUpload' n= NAME ;
	public final FileUpload fileUpload() throws RecognitionException {
		FileUpload ret = null;


		Token n=null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:201:3: ( 'fileUpload' n= NAME )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:201:5: 'fileUpload' n= NAME
			{
			match(input,26,FOLLOW_26_in_fileUpload849); 
			n=(Token)match(input,NAME,FOLLOW_NAME_in_fileUpload857); 
			 ret =  createFileUpload( n.getText() ); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "fileUpload"



	// $ANTLR start "label"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:205:1: label returns [Label ret] : 'label' n= NAME ;
	public final Label label() throws RecognitionException {
		Label ret = null;


		Token n=null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:206:3: ( 'label' n= NAME )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:206:5: 'label' n= NAME
			{
			match(input,32,FOLLOW_32_in_label877); 
			n=(Token)match(input,NAME,FOLLOW_NAME_in_label885); 
			 ret =  createLabel( n.getText() ); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "label"



	// $ANTLR start "outputValue"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:210:1: outputValue returns [OutputValue ret] : 'outputValue' ;
	public final OutputValue outputValue() throws RecognitionException {
		OutputValue ret = null;


		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:211:3: ( 'outputValue' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:211:5: 'outputValue'
			{
			match(input,42,FOLLOW_42_in_outputValue905); 
			 ret =  createOutputValue(); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "outputValue"



	// $ANTLR start "outputType"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:215:1: outputType returns [OutputType ret] : 'outputType' ;
	public final OutputType outputType() throws RecognitionException {
		OutputType ret = null;


		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:216:3: ( 'outputType' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:216:5: 'outputType'
			{
			match(input,41,FOLLOW_41_in_outputType926); 
			 ret =  createOutputType(); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "outputType"



	// $ANTLR start "outputMeasurementUnit"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:220:1: outputMeasurementUnit returns [OutputMeasurementUnit ret] : 'outputMeasurementUnit' ;
	public final OutputMeasurementUnit outputMeasurementUnit() throws RecognitionException {
		OutputMeasurementUnit ret = null;


		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:221:3: ( 'outputMeasurementUnit' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:221:5: 'outputMeasurementUnit'
			{
			match(input,39,FOLLOW_39_in_outputMeasurementUnit947); 
			 ret =  createOutputMeasurementUnit(); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "outputMeasurementUnit"



	// $ANTLR start "outputPath"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:225:1: outputPath returns [OutputPath ret] : 'outputPath' ;
	public final OutputPath outputPath() throws RecognitionException {
		OutputPath ret = null;


		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:226:3: ( 'outputPath' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:226:5: 'outputPath'
			{
			match(input,40,FOLLOW_40_in_outputPath968); 
			 ret = createOutputPath(); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "outputPath"



	// $ANTLR start "outputList"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:230:1: outputList returns [OutputList ret] : 'outputList' ( 'vertical' | 'horizontal' )? '{' r1= viewComponent '}' ;
	public final OutputList outputList() throws RecognitionException {
		OutputList ret = null;


		Viewer r1 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:231:3: ( 'outputList' ( 'vertical' | 'horizontal' )? '{' r1= viewComponent '}' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:231:5: 'outputList' ( 'vertical' | 'horizontal' )? '{' r1= viewComponent '}'
			{
			match(input,38,FOLLOW_38_in_outputList989); 
			 boolean vertical = true;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:233:5: ( 'vertical' | 'horizontal' )?
			int alt18=3;
			int LA18_0 = input.LA(1);
			if ( (LA18_0==52) ) {
				alt18=1;
			}
			else if ( (LA18_0==28) ) {
				alt18=2;
			}
			switch (alt18) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:233:7: 'vertical'
					{
					match(input,52,FOLLOW_52_in_outputList1003); 
					}
					break;
				case 2 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:233:20: 'horizontal'
					{
					match(input,28,FOLLOW_28_in_outputList1007); 
					 vertical = false; 
					}
					break;

			}

			 ret = createOutputList( vertical ); 
			match(input,54,FOLLOW_54_in_outputList1024); 
			pushFollow(FOLLOW_viewComponent_in_outputList1032);
			r1=viewComponent();
			state._fsp--;

			 SubViewer sv = createSottoVista( r1 );
			      sv.assignSource(ret);
			      
			match(input,55,FOLLOW_55_in_outputList1044); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "outputList"



	// $ANTLR start "outputImage"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:242:1: outputImage returns [OutputImage ret] : 'outputImage' n= NAME ;
	public final OutputImage outputImage() throws RecognitionException {
		OutputImage ret = null;


		Token n=null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:243:3: ( 'outputImage' n= NAME )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:243:5: 'outputImage' n= NAME
			{
			match(input,36,FOLLOW_36_in_outputImage1058); 
			n=(Token)match(input,NAME,FOLLOW_NAME_in_outputImage1062); 
			 ret= createOutputImage(n.getText()); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "outputImage"



	// $ANTLR start "outputField"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:247:1: outputField returns [OutputField ret] : 'out' n1= ( 'Appointment' | 'Patient' | 'Agenda' | 'User' ) ( '.' n2= FIELD )+ ;
	public final OutputField outputField() throws RecognitionException {
		OutputField ret = null;


		Token n1=null;
		Token n2=null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:248:3: ( 'out' n1= ( 'Appointment' | 'Patient' | 'Agenda' | 'User' ) ( '.' n2= FIELD )+ )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:248:5: 'out' n1= ( 'Appointment' | 'Patient' | 'Agenda' | 'User' ) ( '.' n2= FIELD )+
			{
			match(input,35,FOLLOW_35_in_outputField1083); 
			ret = createOutputField(); 
			n1=input.LT(1);
			if ( (input.LA(1) >= 13 && input.LA(1) <= 16) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			 ret.setRoot(n1.getText()); 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:250:5: ( '.' n2= FIELD )+
			int cnt19=0;
			loop19:
			while (true) {
				int alt19=2;
				int LA19_0 = input.LA(1);
				if ( (LA19_0==10) ) {
					alt19=1;
				}

				switch (alt19) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:250:6: '.' n2= FIELD
					{
					match(input,10,FOLLOW_10_in_outputField1120); 
					n2=(Token)match(input,FIELD,FOLLOW_FIELD_in_outputField1126); 
					 ret.addFieldPath(n2.getText()); 
					}
					break;

				default :
					if ( cnt19 >= 1 ) break loop19;
					EarlyExitException eee = new EarlyExitException(19, input);
					throw eee;
				}
				cnt19++;
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "outputField"



	// $ANTLR start "outputLink"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:253:1: outputLink returns [OutputLink ret] : 'outputLink' ;
	public final OutputLink outputLink() throws RecognitionException {
		OutputLink ret = null;


		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:254:3: ( 'outputLink' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:254:5: 'outputLink'
			{
			match(input,37,FOLLOW_37_in_outputLink1150); 
			 ret =  createOutputLink(); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "outputLink"



	// $ANTLR start "grid"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:257:1: grid returns [Grid ret] : 'grid' ( 'vertical' | 'horizontal' | 'spaced_horizontal' )? ( 'collapse' )? '{' (r1= gridItem )+ '}' ;
	public final Grid grid() throws RecognitionException {
		Grid ret = null;


		SubViewer r1 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:258:3: ( 'grid' ( 'vertical' | 'horizontal' | 'spaced_horizontal' )? ( 'collapse' )? '{' (r1= gridItem )+ '}' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:258:5: 'grid' ( 'vertical' | 'horizontal' | 'spaced_horizontal' )? ( 'collapse' )? '{' (r1= gridItem )+ '}'
			{
			match(input,27,FOLLOW_27_in_grid1170); 
			 PanelOrientation style = PanelOrientation.vertical; 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:260:5: ( 'vertical' | 'horizontal' | 'spaced_horizontal' )?
			int alt20=4;
			switch ( input.LA(1) ) {
				case 52:
					{
					alt20=1;
					}
					break;
				case 28:
					{
					alt20=2;
					}
					break;
				case 48:
					{
					alt20=3;
					}
					break;
			}
			switch (alt20) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:260:7: 'vertical'
					{
					match(input,52,FOLLOW_52_in_grid1184); 
					}
					break;
				case 2 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:261:7: 'horizontal'
					{
					match(input,28,FOLLOW_28_in_grid1194); 
					 style = PanelOrientation.horizontal; 
					}
					break;
				case 3 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:262:7: 'spaced_horizontal'
					{
					match(input,48,FOLLOW_48_in_grid1206); 
					 style = PanelOrientation.spaced_horizontal; 
					}
					break;

			}

			 ret = createGrid( style ); 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:264:5: ( 'collapse' )?
			int alt21=2;
			int LA21_0 = input.LA(1);
			if ( (LA21_0==21) ) {
				alt21=1;
			}
			switch (alt21) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:264:6: 'collapse'
					{
					match(input,21,FOLLOW_21_in_grid1223); 
					ret.setCollapse(true);
					}
					break;

			}

			match(input,54,FOLLOW_54_in_grid1233); 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:266:5: (r1= gridItem )+
			int cnt22=0;
			loop22:
			while (true) {
				int alt22=2;
				int LA22_0 = input.LA(1);
				if ( (LA22_0==NAME||LA22_0==11) ) {
					alt22=1;
				}

				switch (alt22) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:266:7: r1= gridItem
					{
					pushFollow(FOLLOW_gridItem_in_grid1245);
					r1=gridItem();
					state._fsp--;


					      r1.assignSource(ret);
					    
					}
					break;

				default :
					if ( cnt22 >= 1 ) break loop22;
					EarlyExitException eee = new EarlyExitException(22, input);
					throw eee;
				}
				cnt22++;
			}

			match(input,55,FOLLOW_55_in_grid1255); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "grid"



	// $ANTLR start "custom"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:271:1: custom returns [ViewerCustom ret] : 'custom' n= NAME '{' (r1= gridItem )+ '}' ;
	public final ViewerCustom custom() throws RecognitionException {
		ViewerCustom ret = null;


		Token n=null;
		SubViewer r1 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:272:3: ( 'custom' n= NAME '{' (r1= gridItem )+ '}' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:272:5: 'custom' n= NAME '{' (r1= gridItem )+ '}'
			{
			match(input,24,FOLLOW_24_in_custom1269); 
			n=(Token)match(input,NAME,FOLLOW_NAME_in_custom1273); 
			ret = createCustom( n.getText() );
			match(input,54,FOLLOW_54_in_custom1281); 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:275:5: (r1= gridItem )+
			int cnt23=0;
			loop23:
			while (true) {
				int alt23=2;
				int LA23_0 = input.LA(1);
				if ( (LA23_0==NAME||LA23_0==11) ) {
					alt23=1;
				}

				switch (alt23) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:275:7: r1= gridItem
					{
					pushFollow(FOLLOW_gridItem_in_custom1293);
					r1=gridItem();
					state._fsp--;


					      r1.assignSource(ret);
					    
					}
					break;

				default :
					if ( cnt23 >= 1 ) break loop23;
					EarlyExitException eee = new EarlyExitException(23, input);
					throw eee;
				}
				cnt23++;
			}

			match(input,55,FOLLOW_55_in_custom1303); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "custom"



	// $ANTLR start "gridItem"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:280:1: gridItem returns [SubViewer ret] : (r1= selector )? ':' v= viewComponent ;
	public final SubViewer gridItem() throws RecognitionException {
		SubViewer ret = null;


		TypeSelector r1 =null;
		Viewer v =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:281:3: ( (r1= selector )? ':' v= viewComponent )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:281:5: (r1= selector )? ':' v= viewComponent
			{
			 TypeSelector s = null; 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:282:5: (r1= selector )?
			int alt24=2;
			int LA24_0 = input.LA(1);
			if ( (LA24_0==NAME) ) {
				alt24=1;
			}
			switch (alt24) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:282:7: r1= selector
					{
					pushFollow(FOLLOW_selector_in_gridItem1327);
					r1=selector();
					state._fsp--;

					 s = r1;
					}
					break;

			}

			match(input,11,FOLLOW_11_in_gridItem1348); 
			pushFollow(FOLLOW_viewComponent_in_gridItem1356);
			v=viewComponent();
			state._fsp--;

			 ret = createSottoVista( s, v );
			      popStack(s); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "gridItem"



	// $ANTLR start "selector"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:290:1: selector returns [TypeSelector ret] : n1= NAME ( '.' n2= NAME )* ;
	public final TypeSelector selector() throws RecognitionException {
		TypeSelector ret = null;


		Token n1=null;
		Token n2=null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:291:3: (n1= NAME ( '.' n2= NAME )* )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:292:5: n1= NAME ( '.' n2= NAME )*
			{
			n1=(Token)match(input,NAME,FOLLOW_NAME_in_selector1382); 
			 ret = createTipoOsservazioneSelector( n1.getText() );
			     TypeSelector tmp = ret;
			     pushStack(tmp); 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:296:4: ( '.' n2= NAME )*
			loop25:
			while (true) {
				int alt25=2;
				int LA25_0 = input.LA(1);
				if ( (LA25_0==10) ) {
					alt25=1;
				}

				switch (alt25) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:296:5: '.' n2= NAME
					{
					match(input,10,FOLLOW_10_in_selector1393); 
					n2=(Token)match(input,NAME,FOLLOW_NAME_in_selector1397); 
					 tmp.assignNext( createTipoOsservazioneSelector( n2.getText() ));
					        tmp = tmp.getNext();
					        pushStack(tmp); 
					}
					break;

				default :
					break loop25;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "selector"



	// $ANTLR start "box"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:302:1: box returns [Box ret] : 'box' ( 'collapse' )? '{' r1= gridItem (r2= gridItem )? '}' ;
	public final Box box() throws RecognitionException {
		Box ret = null;


		SubViewer r1 =null;
		SubViewer r2 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:303:3: ( 'box' ( 'collapse' )? '{' r1= gridItem (r2= gridItem )? '}' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:303:5: 'box' ( 'collapse' )? '{' r1= gridItem (r2= gridItem )? '}'
			{
			match(input,18,FOLLOW_18_in_box1425); 
			 ret = createBox(); 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:305:3: ( 'collapse' )?
			int alt26=2;
			int LA26_0 = input.LA(1);
			if ( (LA26_0==21) ) {
				alt26=1;
			}
			switch (alt26) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:305:4: 'collapse'
					{
					match(input,21,FOLLOW_21_in_box1434); 
					ret.setCollapse(true);
					}
					break;

			}

			match(input,54,FOLLOW_54_in_box1442); 
			pushFollow(FOLLOW_gridItem_in_box1450);
			r1=gridItem();
			state._fsp--;


			  r1.assignSource(ret);
			  
			// /Users/fulvio/Desktop/Vista.g/Vista.g:310:3: (r2= gridItem )?
			int alt27=2;
			int LA27_0 = input.LA(1);
			if ( (LA27_0==NAME||LA27_0==11) ) {
				alt27=1;
			}
			switch (alt27) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:310:5: r2= gridItem
					{
					pushFollow(FOLLOW_gridItem_in_box1462);
					r2=gridItem();
					state._fsp--;


					    r2.assignSource(ret);
					  
					}
					break;

			}

			match(input,55,FOLLOW_55_in_box1470); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "box"



	// $ANTLR start "paragraph"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:315:1: paragraph returns [Paragraph ret] : 'paragraph' ( 'collapse' )? '{' (r1= gridItem )+ '}' ;
	public final Paragraph paragraph() throws RecognitionException {
		Paragraph ret = null;


		SubViewer r1 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:316:3: ( 'paragraph' ( 'collapse' )? '{' (r1= gridItem )+ '}' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:316:5: 'paragraph' ( 'collapse' )? '{' (r1= gridItem )+ '}'
			{
			match(input,43,FOLLOW_43_in_paragraph1484); 
			 ret = createParagraph(); 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:318:5: ( 'collapse' )?
			int alt28=2;
			int LA28_0 = input.LA(1);
			if ( (LA28_0==21) ) {
				alt28=1;
			}
			switch (alt28) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:318:6: 'collapse'
					{
					match(input,21,FOLLOW_21_in_paragraph1497); 
					ret.setCollapse(true);
					}
					break;

			}

			match(input,54,FOLLOW_54_in_paragraph1507); 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:320:5: (r1= gridItem )+
			int cnt29=0;
			loop29:
			while (true) {
				int alt29=2;
				int LA29_0 = input.LA(1);
				if ( (LA29_0==NAME||LA29_0==11) ) {
					alt29=1;
				}

				switch (alt29) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:320:7: r1= gridItem
					{
					pushFollow(FOLLOW_gridItem_in_paragraph1519);
					r1=gridItem();
					state._fsp--;


					      r1.assignSource(ret);
					    
					}
					break;

				default :
					if ( cnt29 >= 1 ) break loop29;
					EarlyExitException eee = new EarlyExitException(29, input);
					throw eee;
				}
				cnt29++;
			}

			match(input,55,FOLLOW_55_in_paragraph1529); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "paragraph"



	// $ANTLR start "conditionalPanel"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:325:1: conditionalPanel returns [ConditionalPanel ret] : 'conditionalPanel' '{' s= selector ':' n= NAME (op= ( 'and' | 'or' ) s= selector ':' n= NAME )* ( 'clear' cs= selector )? ( 'clear-multi' cs= selector ( ',' cs2= selector )* )? g= gridItem '}' ;
	public final ConditionalPanel conditionalPanel() throws RecognitionException {
		ConditionalPanel ret = null;


		Token n=null;
		Token op=null;
		TypeSelector s =null;
		TypeSelector cs =null;
		TypeSelector cs2 =null;
		SubViewer g =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:326:3: ( 'conditionalPanel' '{' s= selector ':' n= NAME (op= ( 'and' | 'or' ) s= selector ':' n= NAME )* ( 'clear' cs= selector )? ( 'clear-multi' cs= selector ( ',' cs2= selector )* )? g= gridItem '}' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:326:5: 'conditionalPanel' '{' s= selector ':' n= NAME (op= ( 'and' | 'or' ) s= selector ':' n= NAME )* ( 'clear' cs= selector )? ( 'clear-multi' cs= selector ( ',' cs2= selector )* )? g= gridItem '}'
			{
			match(input,23,FOLLOW_23_in_conditionalPanel1543); 
			 ret = createConditionalPanel();
			    ret.setClear( false ); 
			match(input,54,FOLLOW_54_in_conditionalPanel1551); 
			pushFollow(FOLLOW_selector_in_conditionalPanel1557);
			s=selector();
			state._fsp--;


			      ret.addSelector( s );
			      popStack( s );
			  
			match(input,11,FOLLOW_11_in_conditionalPanel1563); 
			n=(Token)match(input,NAME,FOLLOW_NAME_in_conditionalPanel1569); 
			 ret.addFenomeno( findFenomeno( s , n.getText() )); 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:337:3: (op= ( 'and' | 'or' ) s= selector ':' n= NAME )*
			loop30:
			while (true) {
				int alt30=2;
				int LA30_0 = input.LA(1);
				if ( (LA30_0==17||LA30_0==34) ) {
					alt30=1;
				}

				switch (alt30) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:337:4: op= ( 'and' | 'or' ) s= selector ':' n= NAME
					{
					op=input.LT(1);
					if ( input.LA(1)==17||input.LA(1)==34 ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					 ret.addOperator(op.getText()); 
					pushFollow(FOLLOW_selector_in_conditionalPanel1593);
					s=selector();
					state._fsp--;


					      ret.addSelector( s );
					      popStack( s );
					  
					match(input,11,FOLLOW_11_in_conditionalPanel1599); 
					n=(Token)match(input,NAME,FOLLOW_NAME_in_conditionalPanel1605); 
					 ret.addFenomeno( findFenomeno( s , n.getText() )); 
					}
					break;

				default :
					break loop30;
				}
			}

			// /Users/fulvio/Desktop/Vista.g/Vista.g:346:3: ( 'clear' cs= selector )?
			int alt31=2;
			int LA31_0 = input.LA(1);
			if ( (LA31_0==19) ) {
				alt31=1;
			}
			switch (alt31) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:346:4: 'clear' cs= selector
					{
					match(input,19,FOLLOW_19_in_conditionalPanel1618); 
					pushFollow(FOLLOW_selector_in_conditionalPanel1622);
					cs=selector();
					state._fsp--;


					      ret.setClear( true );
					      ret.setClearSelector( cs );
					      popStack( cs );
					    
					}
					break;

			}

			// /Users/fulvio/Desktop/Vista.g/Vista.g:353:3: ( 'clear-multi' cs= selector ( ',' cs2= selector )* )?
			int alt33=2;
			int LA33_0 = input.LA(1);
			if ( (LA33_0==20) ) {
				alt33=1;
			}
			switch (alt33) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:353:4: 'clear-multi' cs= selector ( ',' cs2= selector )*
					{
					match(input,20,FOLLOW_20_in_conditionalPanel1635); 
					pushFollow(FOLLOW_selector_in_conditionalPanel1639);
					cs=selector();
					state._fsp--;


					      ret.setClear( true );
					      ret.addClearSelector( cs );
					      popStack( cs );
					    
					// /Users/fulvio/Desktop/Vista.g/Vista.g:358:5: ( ',' cs2= selector )*
					loop32:
					while (true) {
						int alt32=2;
						int LA32_0 = input.LA(1);
						if ( (LA32_0==9) ) {
							alt32=1;
						}

						switch (alt32) {
						case 1 :
							// /Users/fulvio/Desktop/Vista.g/Vista.g:358:6: ',' cs2= selector
							{
							match(input,9,FOLLOW_9_in_conditionalPanel1648); 
							pushFollow(FOLLOW_selector_in_conditionalPanel1652);
							cs2=selector();
							state._fsp--;

							 ret.addClearSelector( cs2 );
							         popStack( cs2 );
							       
							}
							break;

						default :
							break loop32;
						}
					}

					}
					break;

			}

			pushFollow(FOLLOW_gridItem_in_conditionalPanel1680);
			g=gridItem();
			state._fsp--;


			    g.assignSource(ret);
			    
			match(input,55,FOLLOW_55_in_conditionalPanel1686); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "conditionalPanel"



	// $ANTLR start "report"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:370:1: report returns [Report ret] : 'report' '{' r1= gridItem r2= gridItem r3= gridItem '}' ;
	public final Report report() throws RecognitionException {
		Report ret = null;


		SubViewer r1 =null;
		SubViewer r2 =null;
		SubViewer r3 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:371:3: ( 'report' '{' r1= gridItem r2= gridItem r3= gridItem '}' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:371:5: 'report' '{' r1= gridItem r2= gridItem r3= gridItem '}'
			{
			match(input,46,FOLLOW_46_in_report1700); 
			 ret = createReport(); 
			match(input,54,FOLLOW_54_in_report1706); 
			pushFollow(FOLLOW_gridItem_in_report1714);
			r1=gridItem();
			state._fsp--;


			      r1.assignSource(ret);
			    
			pushFollow(FOLLOW_gridItem_in_report1724);
			r2=gridItem();
			state._fsp--;


			      r2.assignSource(ret);
			    
			pushFollow(FOLLOW_gridItem_in_report1734);
			r3=gridItem();
			state._fsp--;


			      r3.assignSource(ret);
			    
			match(input,55,FOLLOW_55_in_report1740); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "report"



	// $ANTLR start "factPanel"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:385:1: factPanel returns [FactPanel ret] : 'factPanel' 'query' n= NAME '{' (r1= gridItem )+ '}' ;
	public final FactPanel factPanel() throws RecognitionException {
		FactPanel ret = null;


		Token n=null;
		SubViewer r1 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:386:3: ( 'factPanel' 'query' n= NAME '{' (r1= gridItem )+ '}' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:386:5: 'factPanel' 'query' n= NAME '{' (r1= gridItem )+ '}'
			{
			match(input,25,FOLLOW_25_in_factPanel1755); 
			 ret = createFactPanel(); 
			match(input,44,FOLLOW_44_in_factPanel1761); 
			n=(Token)match(input,NAME,FOLLOW_NAME_in_factPanel1765); 
			 ret.setQuery( findFactQuery( n.getText() ) ); 
			match(input,54,FOLLOW_54_in_factPanel1771); 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:389:3: (r1= gridItem )+
			int cnt34=0;
			loop34:
			while (true) {
				int alt34=2;
				int LA34_0 = input.LA(1);
				if ( (LA34_0==NAME||LA34_0==11) ) {
					alt34=1;
				}

				switch (alt34) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:389:5: r1= gridItem
					{
					pushFollow(FOLLOW_gridItem_in_factPanel1781);
					r1=gridItem();
					state._fsp--;


					      r1.assignSource(ret);
					    
					}
					break;

				default :
					if ( cnt34 >= 1 ) break loop34;
					EarlyExitException eee = new EarlyExitException(34, input);
					throw eee;
				}
				cnt34++;
			}

			match(input,55,FOLLOW_55_in_factPanel1789); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "factPanel"



	// $ANTLR start "tabbedPanel"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:394:1: tabbedPanel returns [TabbedPanel ret] : 'tabbedPanel' '{' (t1= tab )+ '}' ;
	public final TabbedPanel tabbedPanel() throws RecognitionException {
		TabbedPanel ret = null;


		Tab t1 =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:395:3: ( 'tabbedPanel' '{' (t1= tab )+ '}' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:395:5: 'tabbedPanel' '{' (t1= tab )+ '}'
			{
			match(input,50,FOLLOW_50_in_tabbedPanel1803); 
			 ret= createTabbedPanel(); 
			match(input,54,FOLLOW_54_in_tabbedPanel1811); 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:398:4: (t1= tab )+
			int cnt35=0;
			loop35:
			while (true) {
				int alt35=2;
				int LA35_0 = input.LA(1);
				if ( (LA35_0==NAME) ) {
					alt35=1;
				}

				switch (alt35) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:398:6: t1= tab
					{
					pushFollow(FOLLOW_tab_in_tabbedPanel1820);
					t1=tab();
					state._fsp--;


					        t1.assignSource(ret);
					      
					}
					break;

				default :
					if ( cnt35 >= 1 ) break loop35;
					EarlyExitException eee = new EarlyExitException(35, input);
					throw eee;
				}
				cnt35++;
			}

			match(input,55,FOLLOW_55_in_tabbedPanel1829); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "tabbedPanel"



	// $ANTLR start "tab"
	// /Users/fulvio/Desktop/Vista.g/Vista.g:403:1: tab returns [Tab ret] : n= NAME ';' (r1= selector )? ':' v= viewComponent ;
	public final Tab tab() throws RecognitionException {
		Tab ret = null;


		Token n=null;
		TypeSelector r1 =null;
		Viewer v =null;

		try {
			// /Users/fulvio/Desktop/Vista.g/Vista.g:404:3: (n= NAME ';' (r1= selector )? ':' v= viewComponent )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:404:5: n= NAME ';' (r1= selector )? ':' v= viewComponent
			{
			n=(Token)match(input,NAME,FOLLOW_NAME_in_tab1845); 
			match(input,12,FOLLOW_12_in_tab1851); 
			 TypeSelector s = null; 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:407:5: (r1= selector )?
			int alt36=2;
			int LA36_0 = input.LA(1);
			if ( (LA36_0==NAME) ) {
				alt36=1;
			}
			switch (alt36) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:407:7: r1= selector
					{
					pushFollow(FOLLOW_selector_in_tab1867);
					r1=selector();
					state._fsp--;

					 s = r1;
					}
					break;

			}

			match(input,11,FOLLOW_11_in_tab1888); 
			pushFollow(FOLLOW_viewComponent_in_tab1896);
			v=viewComponent();
			state._fsp--;

			 ret = createTab( n.getText(), s, v );
			      popStack(s); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "tab"

	// Delegated rules



	public static final BitSet FOLLOW_NAME_in_rule81 = new BitSet(new long[]{0x002E4FF9EFC40000L});
	public static final BitSet FOLLOW_viewComponent_in_rule93 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_rule105 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_inputComponent_in_viewComponent124 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_outputComponent_in_viewComponent136 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_containerComponent_in_viewComponent148 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_view_in_viewComponent160 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_custom_in_viewComponent172 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_inputText_in_inputComponent193 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_textArea_in_inputComponent205 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_combo_in_inputComponent217 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_suggestion_in_inputComponent229 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_inputList_in_inputComponent241 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_inputTemporal_in_inputComponent253 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_fileUpload_in_inputComponent265 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_label_in_outputComponent286 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_outputType_in_outputComponent298 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_outputValue_in_outputComponent310 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_outputMeasurementUnit_in_outputComponent322 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_outputPath_in_outputComponent334 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_outputList_in_outputComponent346 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_outputField_in_outputComponent359 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_outputImage_in_outputComponent371 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_outputLink_in_outputComponent383 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_grid_in_containerComponent406 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_box_in_containerComponent418 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_conditionalPanel_in_containerComponent430 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_tabbedPanel_in_containerComponent442 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_report_in_containerComponent454 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_factPanel_in_containerComponent466 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_paragraph_in_containerComponent478 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_53_in_view495 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_view499 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_31_in_inputText519 = new BitSet(new long[]{0x0000A00200000002L});
	public static final BitSet FOLLOW_47_in_inputText531 = new BitSet(new long[]{0x0000200200000002L});
	public static final BitSet FOLLOW_45_in_inputText543 = new BitSet(new long[]{0x0000000200000002L});
	public static final BitSet FOLLOW_33_in_inputText555 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_NUMBER_in_inputText559 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_30_in_inputTemporal583 = new BitSet(new long[]{0x0000A00000000002L});
	public static final BitSet FOLLOW_47_in_inputTemporal595 = new BitSet(new long[]{0x0000200000000002L});
	public static final BitSet FOLLOW_45_in_inputTemporal607 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_51_in_textArea631 = new BitSet(new long[]{0x0000A00200000002L});
	public static final BitSet FOLLOW_47_in_textArea643 = new BitSet(new long[]{0x0000200200000002L});
	public static final BitSet FOLLOW_45_in_textArea655 = new BitSet(new long[]{0x0000000200000002L});
	public static final BitSet FOLLOW_33_in_textArea667 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_NUMBER_in_textArea671 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_22_in_combo695 = new BitSet(new long[]{0x0000A00000000002L});
	public static final BitSet FOLLOW_47_in_combo707 = new BitSet(new long[]{0x0000200000000002L});
	public static final BitSet FOLLOW_45_in_combo719 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_49_in_suggestion744 = new BitSet(new long[]{0x0000800000000002L});
	public static final BitSet FOLLOW_47_in_suggestion756 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_29_in_inputList780 = new BitSet(new long[]{0x0050000010000000L});
	public static final BitSet FOLLOW_52_in_inputList794 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_28_in_inputList798 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_inputList815 = new BitSet(new long[]{0x002E4FF9EFC40000L});
	public static final BitSet FOLLOW_viewComponent_in_inputList823 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_inputList835 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_26_in_fileUpload849 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_fileUpload857 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_32_in_label877 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_label885 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_42_in_outputValue905 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_41_in_outputType926 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_39_in_outputMeasurementUnit947 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_40_in_outputPath968 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_38_in_outputList989 = new BitSet(new long[]{0x0050000010000000L});
	public static final BitSet FOLLOW_52_in_outputList1003 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_28_in_outputList1007 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_outputList1024 = new BitSet(new long[]{0x002E4FF9EFC40000L});
	public static final BitSet FOLLOW_viewComponent_in_outputList1032 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_outputList1044 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_36_in_outputImage1058 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_outputImage1062 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_35_in_outputField1083 = new BitSet(new long[]{0x000000000001E000L});
	public static final BitSet FOLLOW_set_in_outputField1095 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_outputField1120 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_FIELD_in_outputField1126 = new BitSet(new long[]{0x0000000000000402L});
	public static final BitSet FOLLOW_37_in_outputLink1150 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_27_in_grid1170 = new BitSet(new long[]{0x0051000010200000L});
	public static final BitSet FOLLOW_52_in_grid1184 = new BitSet(new long[]{0x0040000000200000L});
	public static final BitSet FOLLOW_28_in_grid1194 = new BitSet(new long[]{0x0040000000200000L});
	public static final BitSet FOLLOW_48_in_grid1206 = new BitSet(new long[]{0x0040000000200000L});
	public static final BitSet FOLLOW_21_in_grid1223 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_grid1233 = new BitSet(new long[]{0x0000000000000840L});
	public static final BitSet FOLLOW_gridItem_in_grid1245 = new BitSet(new long[]{0x0080000000000840L});
	public static final BitSet FOLLOW_55_in_grid1255 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_24_in_custom1269 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_custom1273 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_custom1281 = new BitSet(new long[]{0x0000000000000840L});
	public static final BitSet FOLLOW_gridItem_in_custom1293 = new BitSet(new long[]{0x0080000000000840L});
	public static final BitSet FOLLOW_55_in_custom1303 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_selector_in_gridItem1327 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_gridItem1348 = new BitSet(new long[]{0x002E4FF9EFC40000L});
	public static final BitSet FOLLOW_viewComponent_in_gridItem1356 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_selector1382 = new BitSet(new long[]{0x0000000000000402L});
	public static final BitSet FOLLOW_10_in_selector1393 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_selector1397 = new BitSet(new long[]{0x0000000000000402L});
	public static final BitSet FOLLOW_18_in_box1425 = new BitSet(new long[]{0x0040000000200000L});
	public static final BitSet FOLLOW_21_in_box1434 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_box1442 = new BitSet(new long[]{0x0000000000000840L});
	public static final BitSet FOLLOW_gridItem_in_box1450 = new BitSet(new long[]{0x0080000000000840L});
	public static final BitSet FOLLOW_gridItem_in_box1462 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_box1470 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_43_in_paragraph1484 = new BitSet(new long[]{0x0040000000200000L});
	public static final BitSet FOLLOW_21_in_paragraph1497 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_paragraph1507 = new BitSet(new long[]{0x0000000000000840L});
	public static final BitSet FOLLOW_gridItem_in_paragraph1519 = new BitSet(new long[]{0x0080000000000840L});
	public static final BitSet FOLLOW_55_in_paragraph1529 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_23_in_conditionalPanel1543 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_conditionalPanel1551 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_selector_in_conditionalPanel1557 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_conditionalPanel1563 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_conditionalPanel1569 = new BitSet(new long[]{0x00000004001A0840L});
	public static final BitSet FOLLOW_set_in_conditionalPanel1579 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_selector_in_conditionalPanel1593 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_conditionalPanel1599 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_conditionalPanel1605 = new BitSet(new long[]{0x00000004001A0840L});
	public static final BitSet FOLLOW_19_in_conditionalPanel1618 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_selector_in_conditionalPanel1622 = new BitSet(new long[]{0x0000000000100840L});
	public static final BitSet FOLLOW_20_in_conditionalPanel1635 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_selector_in_conditionalPanel1639 = new BitSet(new long[]{0x0000000000000A40L});
	public static final BitSet FOLLOW_9_in_conditionalPanel1648 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_selector_in_conditionalPanel1652 = new BitSet(new long[]{0x0000000000000A40L});
	public static final BitSet FOLLOW_gridItem_in_conditionalPanel1680 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_conditionalPanel1686 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_46_in_report1700 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_report1706 = new BitSet(new long[]{0x0000000000000840L});
	public static final BitSet FOLLOW_gridItem_in_report1714 = new BitSet(new long[]{0x0000000000000840L});
	public static final BitSet FOLLOW_gridItem_in_report1724 = new BitSet(new long[]{0x0000000000000840L});
	public static final BitSet FOLLOW_gridItem_in_report1734 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_report1740 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_25_in_factPanel1755 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_factPanel1761 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_factPanel1765 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_factPanel1771 = new BitSet(new long[]{0x0000000000000840L});
	public static final BitSet FOLLOW_gridItem_in_factPanel1781 = new BitSet(new long[]{0x0080000000000840L});
	public static final BitSet FOLLOW_55_in_factPanel1789 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_50_in_tabbedPanel1803 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_tabbedPanel1811 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_tab_in_tabbedPanel1820 = new BitSet(new long[]{0x0080000000000040L});
	public static final BitSet FOLLOW_55_in_tabbedPanel1829 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_tab1845 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_12_in_tab1851 = new BitSet(new long[]{0x0000000000000840L});
	public static final BitSet FOLLOW_selector_in_tab1867 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_tab1888 = new BitSet(new long[]{0x002E4FF9EFC40000L});
	public static final BitSet FOLLOW_viewComponent_in_tab1896 = new BitSet(new long[]{0x0000000000000002L});
}
