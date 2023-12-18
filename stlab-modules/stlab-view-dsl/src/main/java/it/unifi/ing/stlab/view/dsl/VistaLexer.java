// $ANTLR 3.5.2 /Users/fulvio/Desktop/Vista.g/Vista.g 2018-11-07 12:40:23

package it.unifi.ing.stlab.view.dsl;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class VistaLexer extends Lexer {
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

	@Override
	public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
	   throw new RuntimeException(getErrorHeader(e)+getErrorMessage(e, tokenNames));
	}


	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public VistaLexer() {} 
	public VistaLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public VistaLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "/Users/fulvio/Desktop/Vista.g/Vista.g"; }

	// $ANTLR start "T__9"
	public final void mT__9() throws RecognitionException {
		try {
			int _type = T__9;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:17:6: ( ',' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:17:8: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__9"

	// $ANTLR start "T__10"
	public final void mT__10() throws RecognitionException {
		try {
			int _type = T__10;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:18:7: ( '.' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:18:9: '.'
			{
			match('.'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__10"

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:19:7: ( ':' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:19:9: ':'
			{
			match(':'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__11"

	// $ANTLR start "T__12"
	public final void mT__12() throws RecognitionException {
		try {
			int _type = T__12;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:20:7: ( ';' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:20:9: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__12"

	// $ANTLR start "T__13"
	public final void mT__13() throws RecognitionException {
		try {
			int _type = T__13;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:21:7: ( 'Agenda' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:21:9: 'Agenda'
			{
			match("Agenda"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__13"

	// $ANTLR start "T__14"
	public final void mT__14() throws RecognitionException {
		try {
			int _type = T__14;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:22:7: ( 'Appointment' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:22:9: 'Appointment'
			{
			match("Appointment"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__14"

	// $ANTLR start "T__15"
	public final void mT__15() throws RecognitionException {
		try {
			int _type = T__15;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:23:7: ( 'Patient' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:23:9: 'Patient'
			{
			match("Patient"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__15"

	// $ANTLR start "T__16"
	public final void mT__16() throws RecognitionException {
		try {
			int _type = T__16;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:24:7: ( 'User' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:24:9: 'User'
			{
			match("User"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__16"

	// $ANTLR start "T__17"
	public final void mT__17() throws RecognitionException {
		try {
			int _type = T__17;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:25:7: ( 'and' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:25:9: 'and'
			{
			match("and"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__17"

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:26:7: ( 'box' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:26:9: 'box'
			{
			match("box"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:27:7: ( 'clear' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:27:9: 'clear'
			{
			match("clear"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:28:7: ( 'clear-multi' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:28:9: 'clear-multi'
			{
			match("clear-multi"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:29:7: ( 'collapse' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:29:9: 'collapse'
			{
			match("collapse"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:30:7: ( 'combo' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:30:9: 'combo'
			{
			match("combo"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:31:7: ( 'conditionalPanel' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:31:9: 'conditionalPanel'
			{
			match("conditionalPanel"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__23"

	// $ANTLR start "T__24"
	public final void mT__24() throws RecognitionException {
		try {
			int _type = T__24;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:32:7: ( 'custom' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:32:9: 'custom'
			{
			match("custom"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__24"

	// $ANTLR start "T__25"
	public final void mT__25() throws RecognitionException {
		try {
			int _type = T__25;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:33:7: ( 'factPanel' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:33:9: 'factPanel'
			{
			match("factPanel"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__25"

	// $ANTLR start "T__26"
	public final void mT__26() throws RecognitionException {
		try {
			int _type = T__26;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:34:7: ( 'fileUpload' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:34:9: 'fileUpload'
			{
			match("fileUpload"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__26"

	// $ANTLR start "T__27"
	public final void mT__27() throws RecognitionException {
		try {
			int _type = T__27;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:35:7: ( 'grid' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:35:9: 'grid'
			{
			match("grid"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__27"

	// $ANTLR start "T__28"
	public final void mT__28() throws RecognitionException {
		try {
			int _type = T__28;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:36:7: ( 'horizontal' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:36:9: 'horizontal'
			{
			match("horizontal"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__28"

	// $ANTLR start "T__29"
	public final void mT__29() throws RecognitionException {
		try {
			int _type = T__29;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:37:7: ( 'inputList' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:37:9: 'inputList'
			{
			match("inputList"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__29"

	// $ANTLR start "T__30"
	public final void mT__30() throws RecognitionException {
		try {
			int _type = T__30;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:38:7: ( 'inputTemporal' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:38:9: 'inputTemporal'
			{
			match("inputTemporal"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__30"

	// $ANTLR start "T__31"
	public final void mT__31() throws RecognitionException {
		try {
			int _type = T__31;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:39:7: ( 'inputText' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:39:9: 'inputText'
			{
			match("inputText"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__31"

	// $ANTLR start "T__32"
	public final void mT__32() throws RecognitionException {
		try {
			int _type = T__32;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:40:7: ( 'label' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:40:9: 'label'
			{
			match("label"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__32"

	// $ANTLR start "T__33"
	public final void mT__33() throws RecognitionException {
		try {
			int _type = T__33;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:41:7: ( 'length=' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:41:9: 'length='
			{
			match("length="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__33"

	// $ANTLR start "T__34"
	public final void mT__34() throws RecognitionException {
		try {
			int _type = T__34;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:42:7: ( 'or' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:42:9: 'or'
			{
			match("or"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__34"

	// $ANTLR start "T__35"
	public final void mT__35() throws RecognitionException {
		try {
			int _type = T__35;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:43:7: ( 'out' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:43:9: 'out'
			{
			match("out"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__35"

	// $ANTLR start "T__36"
	public final void mT__36() throws RecognitionException {
		try {
			int _type = T__36;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:44:7: ( 'outputImage' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:44:9: 'outputImage'
			{
			match("outputImage"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__36"

	// $ANTLR start "T__37"
	public final void mT__37() throws RecognitionException {
		try {
			int _type = T__37;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:45:7: ( 'outputLink' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:45:9: 'outputLink'
			{
			match("outputLink"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__37"

	// $ANTLR start "T__38"
	public final void mT__38() throws RecognitionException {
		try {
			int _type = T__38;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:46:7: ( 'outputList' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:46:9: 'outputList'
			{
			match("outputList"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__38"

	// $ANTLR start "T__39"
	public final void mT__39() throws RecognitionException {
		try {
			int _type = T__39;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:47:7: ( 'outputMeasurementUnit' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:47:9: 'outputMeasurementUnit'
			{
			match("outputMeasurementUnit"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__39"

	// $ANTLR start "T__40"
	public final void mT__40() throws RecognitionException {
		try {
			int _type = T__40;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:48:7: ( 'outputPath' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:48:9: 'outputPath'
			{
			match("outputPath"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__40"

	// $ANTLR start "T__41"
	public final void mT__41() throws RecognitionException {
		try {
			int _type = T__41;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:49:7: ( 'outputType' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:49:9: 'outputType'
			{
			match("outputType"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__41"

	// $ANTLR start "T__42"
	public final void mT__42() throws RecognitionException {
		try {
			int _type = T__42;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:50:7: ( 'outputValue' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:50:9: 'outputValue'
			{
			match("outputValue"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__42"

	// $ANTLR start "T__43"
	public final void mT__43() throws RecognitionException {
		try {
			int _type = T__43;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:51:7: ( 'paragraph' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:51:9: 'paragraph'
			{
			match("paragraph"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__43"

	// $ANTLR start "T__44"
	public final void mT__44() throws RecognitionException {
		try {
			int _type = T__44;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:52:7: ( 'query' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:52:9: 'query'
			{
			match("query"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__44"

	// $ANTLR start "T__45"
	public final void mT__45() throws RecognitionException {
		try {
			int _type = T__45;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:53:7: ( 'readonly' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:53:9: 'readonly'
			{
			match("readonly"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__45"

	// $ANTLR start "T__46"
	public final void mT__46() throws RecognitionException {
		try {
			int _type = T__46;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:54:7: ( 'report' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:54:9: 'report'
			{
			match("report"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__46"

	// $ANTLR start "T__47"
	public final void mT__47() throws RecognitionException {
		try {
			int _type = T__47;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:55:7: ( 'required' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:55:9: 'required'
			{
			match("required"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__47"

	// $ANTLR start "T__48"
	public final void mT__48() throws RecognitionException {
		try {
			int _type = T__48;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:56:7: ( 'spaced_horizontal' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:56:9: 'spaced_horizontal'
			{
			match("spaced_horizontal"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__48"

	// $ANTLR start "T__49"
	public final void mT__49() throws RecognitionException {
		try {
			int _type = T__49;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:57:7: ( 'suggestion' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:57:9: 'suggestion'
			{
			match("suggestion"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__49"

	// $ANTLR start "T__50"
	public final void mT__50() throws RecognitionException {
		try {
			int _type = T__50;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:58:7: ( 'tabbedPanel' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:58:9: 'tabbedPanel'
			{
			match("tabbedPanel"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__50"

	// $ANTLR start "T__51"
	public final void mT__51() throws RecognitionException {
		try {
			int _type = T__51;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:59:7: ( 'textArea' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:59:9: 'textArea'
			{
			match("textArea"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__51"

	// $ANTLR start "T__52"
	public final void mT__52() throws RecognitionException {
		try {
			int _type = T__52;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:60:7: ( 'vertical' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:60:9: 'vertical'
			{
			match("vertical"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__52"

	// $ANTLR start "T__53"
	public final void mT__53() throws RecognitionException {
		try {
			int _type = T__53;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:61:7: ( 'view' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:61:9: 'view'
			{
			match("view"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__53"

	// $ANTLR start "T__54"
	public final void mT__54() throws RecognitionException {
		try {
			int _type = T__54;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:62:7: ( '{' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:62:9: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__54"

	// $ANTLR start "T__55"
	public final void mT__55() throws RecognitionException {
		try {
			int _type = T__55;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:63:7: ( '}' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:63:9: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__55"

	// $ANTLR start "NAME"
	public final void mNAME() throws RecognitionException {
		try {
			int _type = NAME;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:419:6: ( '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '<' | '>' | '@' | '\\'' | '?' | ',' | '\\u00B0' | '[' | ']' | '(' | ')' | ':' | '.' | ';' | '/' | '_' | '\\u00E0' | '\\u00F2' | '\\u00E8' | '\\u00E9' | '\\u00F9' | '\\u00EC' | '\\u00C8' | '%' | ' ' )+ '\"' )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:419:8: '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '<' | '>' | '@' | '\\'' | '?' | ',' | '\\u00B0' | '[' | ']' | '(' | ')' | ':' | '.' | ';' | '/' | '_' | '\\u00E0' | '\\u00F2' | '\\u00E8' | '\\u00E9' | '\\u00F9' | '\\u00EC' | '\\u00C8' | '%' | ' ' )+ '\"'
			{
			match('\"'); 
			// /Users/fulvio/Desktop/Vista.g/Vista.g:419:12: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '<' | '>' | '@' | '\\'' | '?' | ',' | '\\u00B0' | '[' | ']' | '(' | ')' | ':' | '.' | ';' | '/' | '_' | '\\u00E0' | '\\u00F2' | '\\u00E8' | '\\u00E9' | '\\u00F9' | '\\u00EC' | '\\u00C8' | '%' | ' ' )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==' '||LA1_0=='%'||(LA1_0 >= '\'' && LA1_0 <= ')')||(LA1_0 >= ',' && LA1_0 <= '<')||(LA1_0 >= '>' && LA1_0 <= '[')||LA1_0==']'||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')||LA1_0=='\u00B0'||LA1_0=='\u00C8'||LA1_0=='\u00E0'||(LA1_0 >= '\u00E8' && LA1_0 <= '\u00E9')||LA1_0=='\u00EC'||LA1_0=='\u00F2'||LA1_0=='\u00F9') ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:
					{
					if ( input.LA(1)==' '||input.LA(1)=='%'||(input.LA(1) >= '\'' && input.LA(1) <= ')')||(input.LA(1) >= ',' && input.LA(1) <= '<')||(input.LA(1) >= '>' && input.LA(1) <= '[')||input.LA(1)==']'||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||input.LA(1)=='\u00B0'||input.LA(1)=='\u00C8'||input.LA(1)=='\u00E0'||(input.LA(1) >= '\u00E8' && input.LA(1) <= '\u00E9')||input.LA(1)=='\u00EC'||input.LA(1)=='\u00F2'||input.LA(1)=='\u00F9' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			match('\"'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NAME"

	// $ANTLR start "NUMBER"
	public final void mNUMBER() throws RecognitionException {
		try {
			int _type = NUMBER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:423:8: ( ( '0' .. '9' )+ )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:423:10: ( '0' .. '9' )+
			{
			// /Users/fulvio/Desktop/Vista.g/Vista.g:423:10: ( '0' .. '9' )+
			int cnt2=0;
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt2 >= 1 ) break loop2;
					EarlyExitException eee = new EarlyExitException(2, input);
					throw eee;
				}
				cnt2++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NUMBER"

	// $ANTLR start "WHITESPACE"
	public final void mWHITESPACE() throws RecognitionException {
		try {
			int _type = WHITESPACE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:425:12: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:425:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
			{
			// /Users/fulvio/Desktop/Vista.g/Vista.g:425:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '\t' && LA3_0 <= '\n')||(LA3_0 >= '\f' && LA3_0 <= '\r')||LA3_0==' ') ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||(input.LA(1) >= '\f' && input.LA(1) <= '\r')||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt3 >= 1 ) break loop3;
					EarlyExitException eee = new EarlyExitException(3, input);
					throw eee;
				}
				cnt3++;
			}

			 skip(); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WHITESPACE"

	// $ANTLR start "ILLEGAL"
	public final void mILLEGAL() throws RecognitionException {
		try {
			int _type = ILLEGAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:427:9: ( . )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:427:11: .
			{
			matchAny(); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ILLEGAL"

	// $ANTLR start "FIELD"
	public final void mFIELD() throws RecognitionException {
		try {
			int _type = FIELD;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/Vista.g/Vista.g:429:7: ( ( 'Name' | 'Surname' | 'Sex' | 'BirthDate' | 'BirthPlace' | 'TaxCode' | 'SsnCode' | 'Qualifications' | 'Residence' | 'Domicile' | 'Region' | 'HomePhone' | 'WorkPhone' | 'Nationality' | 'Asl' | 'Date' | 'Number' | 'BookingCode' | 'AcceptanceCode' | 'Code' | 'Description' | 'Mail' ) )
			// /Users/fulvio/Desktop/Vista.g/Vista.g:429:9: ( 'Name' | 'Surname' | 'Sex' | 'BirthDate' | 'BirthPlace' | 'TaxCode' | 'SsnCode' | 'Qualifications' | 'Residence' | 'Domicile' | 'Region' | 'HomePhone' | 'WorkPhone' | 'Nationality' | 'Asl' | 'Date' | 'Number' | 'BookingCode' | 'AcceptanceCode' | 'Code' | 'Description' | 'Mail' )
			{
			// /Users/fulvio/Desktop/Vista.g/Vista.g:429:9: ( 'Name' | 'Surname' | 'Sex' | 'BirthDate' | 'BirthPlace' | 'TaxCode' | 'SsnCode' | 'Qualifications' | 'Residence' | 'Domicile' | 'Region' | 'HomePhone' | 'WorkPhone' | 'Nationality' | 'Asl' | 'Date' | 'Number' | 'BookingCode' | 'AcceptanceCode' | 'Code' | 'Description' | 'Mail' )
			int alt4=22;
			switch ( input.LA(1) ) {
			case 'N':
				{
				int LA4_1 = input.LA(2);
				if ( (LA4_1=='a') ) {
					int LA4_13 = input.LA(3);
					if ( (LA4_13=='m') ) {
						alt4=1;
					}
					else if ( (LA4_13=='t') ) {
						alt4=14;
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 4, 13, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}
				else if ( (LA4_1=='u') ) {
					alt4=17;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 'S':
				{
				switch ( input.LA(2) ) {
				case 'u':
					{
					alt4=2;
					}
					break;
				case 'e':
					{
					alt4=3;
					}
					break;
				case 's':
					{
					alt4=7;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case 'B':
				{
				int LA4_3 = input.LA(2);
				if ( (LA4_3=='i') ) {
					int LA4_18 = input.LA(3);
					if ( (LA4_18=='r') ) {
						int LA4_28 = input.LA(4);
						if ( (LA4_28=='t') ) {
							int LA4_31 = input.LA(5);
							if ( (LA4_31=='h') ) {
								int LA4_32 = input.LA(6);
								if ( (LA4_32=='D') ) {
									alt4=4;
								}
								else if ( (LA4_32=='P') ) {
									alt4=5;
								}

								else {
									int nvaeMark = input.mark();
									try {
										for (int nvaeConsume = 0; nvaeConsume < 6 - 1; nvaeConsume++) {
											input.consume();
										}
										NoViableAltException nvae =
											new NoViableAltException("", 4, 32, input);
										throw nvae;
									} finally {
										input.rewind(nvaeMark);
									}
								}

							}

							else {
								int nvaeMark = input.mark();
								try {
									for (int nvaeConsume = 0; nvaeConsume < 5 - 1; nvaeConsume++) {
										input.consume();
									}
									NoViableAltException nvae =
										new NoViableAltException("", 4, 31, input);
									throw nvae;
								} finally {
									input.rewind(nvaeMark);
								}
							}

						}

						else {
							int nvaeMark = input.mark();
							try {
								for (int nvaeConsume = 0; nvaeConsume < 4 - 1; nvaeConsume++) {
									input.consume();
								}
								NoViableAltException nvae =
									new NoViableAltException("", 4, 28, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}

					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 4, 18, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}
				else if ( (LA4_3=='o') ) {
					alt4=18;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 'T':
				{
				alt4=6;
				}
				break;
			case 'Q':
				{
				alt4=8;
				}
				break;
			case 'R':
				{
				int LA4_6 = input.LA(2);
				if ( (LA4_6=='e') ) {
					int LA4_20 = input.LA(3);
					if ( (LA4_20=='s') ) {
						alt4=9;
					}
					else if ( (LA4_20=='g') ) {
						alt4=11;
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 4, 20, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 6, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 'D':
				{
				switch ( input.LA(2) ) {
				case 'o':
					{
					alt4=10;
					}
					break;
				case 'a':
					{
					alt4=16;
					}
					break;
				case 'e':
					{
					alt4=21;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 7, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case 'H':
				{
				alt4=12;
				}
				break;
			case 'W':
				{
				alt4=13;
				}
				break;
			case 'A':
				{
				int LA4_10 = input.LA(2);
				if ( (LA4_10=='s') ) {
					alt4=15;
				}
				else if ( (LA4_10=='c') ) {
					alt4=19;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 10, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 'C':
				{
				alt4=20;
				}
				break;
			case 'M':
				{
				alt4=22;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}
			switch (alt4) {
				case 1 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:429:10: 'Name'
					{
					match("Name"); 

					}
					break;
				case 2 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:429:19: 'Surname'
					{
					match("Surname"); 

					}
					break;
				case 3 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:429:31: 'Sex'
					{
					match("Sex"); 

					}
					break;
				case 4 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:429:39: 'BirthDate'
					{
					match("BirthDate"); 

					}
					break;
				case 5 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:429:53: 'BirthPlace'
					{
					match("BirthPlace"); 

					}
					break;
				case 6 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:429:68: 'TaxCode'
					{
					match("TaxCode"); 

					}
					break;
				case 7 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:429:80: 'SsnCode'
					{
					match("SsnCode"); 

					}
					break;
				case 8 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:429:92: 'Qualifications'
					{
					match("Qualifications"); 

					}
					break;
				case 9 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:430:11: 'Residence'
					{
					match("Residence"); 

					}
					break;
				case 10 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:430:25: 'Domicile'
					{
					match("Domicile"); 

					}
					break;
				case 11 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:430:38: 'Region'
					{
					match("Region"); 

					}
					break;
				case 12 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:430:49: 'HomePhone'
					{
					match("HomePhone"); 

					}
					break;
				case 13 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:430:63: 'WorkPhone'
					{
					match("WorkPhone"); 

					}
					break;
				case 14 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:430:77: 'Nationality'
					{
					match("Nationality"); 

					}
					break;
				case 15 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:430:93: 'Asl'
					{
					match("Asl"); 

					}
					break;
				case 16 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:430:101: 'Date'
					{
					match("Date"); 

					}
					break;
				case 17 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:431:11: 'Number'
					{
					match("Number"); 

					}
					break;
				case 18 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:431:22: 'BookingCode'
					{
					match("BookingCode"); 

					}
					break;
				case 19 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:431:38: 'AcceptanceCode'
					{
					match("AcceptanceCode"); 

					}
					break;
				case 20 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:431:57: 'Code'
					{
					match("Code"); 

					}
					break;
				case 21 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:431:66: 'Description'
					{
					match("Description"); 

					}
					break;
				case 22 :
					// /Users/fulvio/Desktop/Vista.g/Vista.g:431:82: 'Mail'
					{
					match("Mail"); 

					}
					break;

			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FIELD"

	@Override
	public void mTokens() throws RecognitionException {
		// /Users/fulvio/Desktop/Vista.g/Vista.g:1:8: ( T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | NAME | NUMBER | WHITESPACE | ILLEGAL | FIELD )
		int alt5=52;
		alt5 = dfa5.predict(input);
		switch (alt5) {
			case 1 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:10: T__9
				{
				mT__9(); 

				}
				break;
			case 2 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:15: T__10
				{
				mT__10(); 

				}
				break;
			case 3 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:21: T__11
				{
				mT__11(); 

				}
				break;
			case 4 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:27: T__12
				{
				mT__12(); 

				}
				break;
			case 5 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:33: T__13
				{
				mT__13(); 

				}
				break;
			case 6 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:39: T__14
				{
				mT__14(); 

				}
				break;
			case 7 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:45: T__15
				{
				mT__15(); 

				}
				break;
			case 8 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:51: T__16
				{
				mT__16(); 

				}
				break;
			case 9 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:57: T__17
				{
				mT__17(); 

				}
				break;
			case 10 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:63: T__18
				{
				mT__18(); 

				}
				break;
			case 11 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:69: T__19
				{
				mT__19(); 

				}
				break;
			case 12 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:75: T__20
				{
				mT__20(); 

				}
				break;
			case 13 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:81: T__21
				{
				mT__21(); 

				}
				break;
			case 14 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:87: T__22
				{
				mT__22(); 

				}
				break;
			case 15 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:93: T__23
				{
				mT__23(); 

				}
				break;
			case 16 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:99: T__24
				{
				mT__24(); 

				}
				break;
			case 17 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:105: T__25
				{
				mT__25(); 

				}
				break;
			case 18 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:111: T__26
				{
				mT__26(); 

				}
				break;
			case 19 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:117: T__27
				{
				mT__27(); 

				}
				break;
			case 20 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:123: T__28
				{
				mT__28(); 

				}
				break;
			case 21 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:129: T__29
				{
				mT__29(); 

				}
				break;
			case 22 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:135: T__30
				{
				mT__30(); 

				}
				break;
			case 23 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:141: T__31
				{
				mT__31(); 

				}
				break;
			case 24 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:147: T__32
				{
				mT__32(); 

				}
				break;
			case 25 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:153: T__33
				{
				mT__33(); 

				}
				break;
			case 26 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:159: T__34
				{
				mT__34(); 

				}
				break;
			case 27 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:165: T__35
				{
				mT__35(); 

				}
				break;
			case 28 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:171: T__36
				{
				mT__36(); 

				}
				break;
			case 29 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:177: T__37
				{
				mT__37(); 

				}
				break;
			case 30 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:183: T__38
				{
				mT__38(); 

				}
				break;
			case 31 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:189: T__39
				{
				mT__39(); 

				}
				break;
			case 32 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:195: T__40
				{
				mT__40(); 

				}
				break;
			case 33 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:201: T__41
				{
				mT__41(); 

				}
				break;
			case 34 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:207: T__42
				{
				mT__42(); 

				}
				break;
			case 35 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:213: T__43
				{
				mT__43(); 

				}
				break;
			case 36 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:219: T__44
				{
				mT__44(); 

				}
				break;
			case 37 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:225: T__45
				{
				mT__45(); 

				}
				break;
			case 38 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:231: T__46
				{
				mT__46(); 

				}
				break;
			case 39 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:237: T__47
				{
				mT__47(); 

				}
				break;
			case 40 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:243: T__48
				{
				mT__48(); 

				}
				break;
			case 41 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:249: T__49
				{
				mT__49(); 

				}
				break;
			case 42 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:255: T__50
				{
				mT__50(); 

				}
				break;
			case 43 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:261: T__51
				{
				mT__51(); 

				}
				break;
			case 44 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:267: T__52
				{
				mT__52(); 

				}
				break;
			case 45 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:273: T__53
				{
				mT__53(); 

				}
				break;
			case 46 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:279: T__54
				{
				mT__54(); 

				}
				break;
			case 47 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:285: T__55
				{
				mT__55(); 

				}
				break;
			case 48 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:291: NAME
				{
				mNAME(); 

				}
				break;
			case 49 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:296: NUMBER
				{
				mNUMBER(); 

				}
				break;
			case 50 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:303: WHITESPACE
				{
				mWHITESPACE(); 

				}
				break;
			case 51 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:314: ILLEGAL
				{
				mILLEGAL(); 

				}
				break;
			case 52 :
				// /Users/fulvio/Desktop/Vista.g/Vista.g:1:322: FIELD
				{
				mFIELD(); 

				}
				break;

		}
	}


	protected DFA5 dfa5 = new DFA5(this);
	static final String DFA5_eotS =
		"\5\uffff\22\47\2\uffff\1\47\2\uffff\13\47\53\uffff\1\131\7\uffff\1\136"+
		"\23\uffff";
	static final String DFA5_eofS =
		"\156\uffff";
	static final String DFA5_minS =
		"\1\0\4\uffff\1\143\1\141\1\163\1\156\1\157\1\154\1\141\1\162\1\157\1\156"+
		"\1\141\1\162\1\141\1\165\1\145\1\160\1\141\1\145\2\uffff\1\40\2\uffff"+
		"\1\141\1\145\1\151\1\141\1\165\1\145\1\141\3\157\1\141\14\uffff\1\145"+
		"\1\154\5\uffff\1\160\3\uffff\1\164\2\uffff\1\141\13\uffff\1\141\3\uffff"+
		"\1\165\1\160\3\uffff\1\162\1\164\1\165\1\uffff\1\55\1\114\1\164\3\uffff"+
		"\1\145\1\111\1\155\1\uffff\1\151\6\uffff\1\156\2\uffff";
	static final String DFA5_maxS =
		"\1\uffff\4\uffff\1\163\1\141\1\163\1\156\1\157\1\165\1\151\1\162\1\157"+
		"\1\156\1\145\1\165\1\141\1\165\1\145\1\165\1\145\1\151\2\uffff\1\u00f9"+
		"\2\uffff\2\165\1\157\1\141\1\165\1\145\4\157\1\141\14\uffff\1\145\1\156"+
		"\5\uffff\1\160\3\uffff\1\164\2\uffff\1\161\13\uffff\1\141\3\uffff\1\165"+
		"\1\160\3\uffff\1\162\1\164\1\165\1\uffff\1\55\1\124\1\164\3\uffff\1\145"+
		"\1\126\1\170\1\uffff\1\151\6\uffff\1\163\2\uffff";
	static final String DFA5_acceptS =
		"\1\uffff\1\1\1\2\1\3\1\4\22\uffff\1\56\1\57\1\uffff\1\61\1\62\13\uffff"+
		"\1\63\1\1\1\2\1\3\1\4\1\5\1\6\1\64\1\7\1\10\1\11\1\12\2\uffff\1\20\1\21"+
		"\1\22\1\23\1\24\1\uffff\1\30\1\31\1\32\1\uffff\1\43\1\44\1\uffff\1\50"+
		"\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\uffff\1\15\1\16\1"+
		"\17\2\uffff\1\45\1\46\1\47\3\uffff\1\33\3\uffff\1\14\1\13\1\25\3\uffff"+
		"\1\34\1\uffff\1\37\1\40\1\41\1\42\1\26\1\27\1\uffff\1\35\1\36";
	static final String DFA5_specialS =
		"\1\0\155\uffff}>";
	static final String[] DFA5_transitionS = {
			"\11\47\2\33\1\47\2\33\22\47\1\33\1\47\1\31\11\47\1\1\1\47\1\2\1\47\12"+
			"\32\1\3\1\4\5\47\1\5\1\36\1\45\1\42\3\47\1\43\4\47\1\46\1\34\1\47\1\6"+
			"\1\40\1\41\1\35\1\37\1\7\1\47\1\44\11\47\1\10\1\11\1\12\2\47\1\13\1\14"+
			"\1\15\1\16\2\47\1\17\2\47\1\20\1\21\1\22\1\23\1\24\1\25\1\47\1\26\4\47"+
			"\1\27\1\47\1\30\uff82\47",
			"",
			"",
			"",
			"",
			"\1\56\3\uffff\1\54\10\uffff\1\55\2\uffff\1\56",
			"\1\57",
			"\1\60",
			"\1\61",
			"\1\62",
			"\1\63\2\uffff\1\64\5\uffff\1\65",
			"\1\66\7\uffff\1\67",
			"\1\70",
			"\1\71",
			"\1\72",
			"\1\73\3\uffff\1\74",
			"\1\75\2\uffff\1\76",
			"\1\77",
			"\1\100",
			"\1\101",
			"\1\102\4\uffff\1\103",
			"\1\104\3\uffff\1\105",
			"\1\106\3\uffff\1\107",
			"",
			"",
			"\1\112\4\uffff\1\112\1\uffff\3\112\2\uffff\21\112\1\uffff\36\112\1\uffff"+
			"\1\112\1\uffff\1\112\1\uffff\32\112\65\uffff\1\112\27\uffff\1\112\27"+
			"\uffff\1\112\7\uffff\2\112\2\uffff\1\112\5\uffff\1\112\6\uffff\1\112",
			"",
			"",
			"\1\56\23\uffff\1\56",
			"\1\56\15\uffff\1\56\1\uffff\1\56",
			"\1\56\5\uffff\1\56",
			"\1\56",
			"\1\56",
			"\1\56",
			"\1\56\3\uffff\1\56\11\uffff\1\56",
			"\1\56",
			"\1\56",
			"\1\56",
			"\1\56",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\115",
			"\1\116\1\117\1\120",
			"",
			"",
			"",
			"",
			"",
			"\1\121",
			"",
			"",
			"",
			"\1\122",
			"",
			"",
			"\1\123\16\uffff\1\124\1\125",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\126",
			"",
			"",
			"",
			"\1\127",
			"\1\130",
			"",
			"",
			"",
			"\1\132",
			"\1\133",
			"\1\134",
			"",
			"\1\135",
			"\1\137\7\uffff\1\140",
			"\1\141",
			"",
			"",
			"",
			"\1\142",
			"\1\143\2\uffff\1\144\1\145\2\uffff\1\146\3\uffff\1\147\1\uffff\1\150",
			"\1\151\12\uffff\1\152",
			"",
			"\1\153",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\154\4\uffff\1\155",
			"",
			""
	};

	static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
	static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
	static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
	static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
	static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
	static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
	static final short[][] DFA5_transition;

	static {
		int numStates = DFA5_transitionS.length;
		DFA5_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
		}
	}

	protected class DFA5 extends DFA {

		public DFA5(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 5;
			this.eot = DFA5_eot;
			this.eof = DFA5_eof;
			this.min = DFA5_min;
			this.max = DFA5_max;
			this.accept = DFA5_accept;
			this.special = DFA5_special;
			this.transition = DFA5_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | NAME | NUMBER | WHITESPACE | ILLEGAL | FIELD );";
		}
		@Override
		public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
			IntStream input = _input;
			int _s = s;
			switch ( s ) {
					case 0 : 
						int LA5_0 = input.LA(1);
						s = -1;
						if ( (LA5_0==',') ) {s = 1;}
						else if ( (LA5_0=='.') ) {s = 2;}
						else if ( (LA5_0==':') ) {s = 3;}
						else if ( (LA5_0==';') ) {s = 4;}
						else if ( (LA5_0=='A') ) {s = 5;}
						else if ( (LA5_0=='P') ) {s = 6;}
						else if ( (LA5_0=='U') ) {s = 7;}
						else if ( (LA5_0=='a') ) {s = 8;}
						else if ( (LA5_0=='b') ) {s = 9;}
						else if ( (LA5_0=='c') ) {s = 10;}
						else if ( (LA5_0=='f') ) {s = 11;}
						else if ( (LA5_0=='g') ) {s = 12;}
						else if ( (LA5_0=='h') ) {s = 13;}
						else if ( (LA5_0=='i') ) {s = 14;}
						else if ( (LA5_0=='l') ) {s = 15;}
						else if ( (LA5_0=='o') ) {s = 16;}
						else if ( (LA5_0=='p') ) {s = 17;}
						else if ( (LA5_0=='q') ) {s = 18;}
						else if ( (LA5_0=='r') ) {s = 19;}
						else if ( (LA5_0=='s') ) {s = 20;}
						else if ( (LA5_0=='t') ) {s = 21;}
						else if ( (LA5_0=='v') ) {s = 22;}
						else if ( (LA5_0=='{') ) {s = 23;}
						else if ( (LA5_0=='}') ) {s = 24;}
						else if ( (LA5_0=='\"') ) {s = 25;}
						else if ( ((LA5_0 >= '0' && LA5_0 <= '9')) ) {s = 26;}
						else if ( ((LA5_0 >= '\t' && LA5_0 <= '\n')||(LA5_0 >= '\f' && LA5_0 <= '\r')||LA5_0==' ') ) {s = 27;}
						else if ( (LA5_0=='N') ) {s = 28;}
						else if ( (LA5_0=='S') ) {s = 29;}
						else if ( (LA5_0=='B') ) {s = 30;}
						else if ( (LA5_0=='T') ) {s = 31;}
						else if ( (LA5_0=='Q') ) {s = 32;}
						else if ( (LA5_0=='R') ) {s = 33;}
						else if ( (LA5_0=='D') ) {s = 34;}
						else if ( (LA5_0=='H') ) {s = 35;}
						else if ( (LA5_0=='W') ) {s = 36;}
						else if ( (LA5_0=='C') ) {s = 37;}
						else if ( (LA5_0=='M') ) {s = 38;}
						else if ( ((LA5_0 >= '\u0000' && LA5_0 <= '\b')||LA5_0=='\u000B'||(LA5_0 >= '\u000E' && LA5_0 <= '\u001F')||LA5_0=='!'||(LA5_0 >= '#' && LA5_0 <= '+')||LA5_0=='-'||LA5_0=='/'||(LA5_0 >= '<' && LA5_0 <= '@')||(LA5_0 >= 'E' && LA5_0 <= 'G')||(LA5_0 >= 'I' && LA5_0 <= 'L')||LA5_0=='O'||LA5_0=='V'||(LA5_0 >= 'X' && LA5_0 <= '`')||(LA5_0 >= 'd' && LA5_0 <= 'e')||(LA5_0 >= 'j' && LA5_0 <= 'k')||(LA5_0 >= 'm' && LA5_0 <= 'n')||LA5_0=='u'||(LA5_0 >= 'w' && LA5_0 <= 'z')||LA5_0=='|'||(LA5_0 >= '~' && LA5_0 <= '\uFFFF')) ) {s = 39;}
						if ( s>=0 ) return s;
						break;
			}
			NoViableAltException nvae =
				new NoViableAltException(getDescription(), 5, _s, input);
			error(nvae);
			throw nvae;
		}
	}

}
