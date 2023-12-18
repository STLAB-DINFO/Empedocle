// $ANTLR 3.5.2 /Users/fulvio/Desktop/g2/Type.g 2018-11-07 11:11:39

package it.unifi.ing.stlab.reflection.dsl;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class TypeLexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__8=8;
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
	public static final int ILLEGAL=4;
	public static final int NAME=5;
	public static final int NUMBER=6;
	public static final int WHITESPACE=7;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public TypeLexer() {} 
	public TypeLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public TypeLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "/Users/fulvio/Desktop/g2/Type.g"; }

	// $ANTLR start "T__8"
	public final void mT__8() throws RecognitionException {
		try {
			int _type = T__8;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/g2/Type.g:11:6: ( '(' )
			// /Users/fulvio/Desktop/g2/Type.g:11:8: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__8"

	// $ANTLR start "T__9"
	public final void mT__9() throws RecognitionException {
		try {
			int _type = T__9;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/g2/Type.g:12:6: ( ')' )
			// /Users/fulvio/Desktop/g2/Type.g:12:8: ')'
			{
			match(')'); 
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
			// /Users/fulvio/Desktop/g2/Type.g:13:7: ( ',' )
			// /Users/fulvio/Desktop/g2/Type.g:13:9: ','
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
	// $ANTLR end "T__10"

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/g2/Type.g:14:7: ( ':' )
			// /Users/fulvio/Desktop/g2/Type.g:14:9: ':'
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
			// /Users/fulvio/Desktop/g2/Type.g:15:7: ( 'ct' )
			// /Users/fulvio/Desktop/g2/Type.g:15:9: 'ct'
			{
			match("ct"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:16:7: ( 'date' )
			// /Users/fulvio/Desktop/g2/Type.g:16:9: 'date'
			{
			match("date"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:17:7: ( 'datetime' )
			// /Users/fulvio/Desktop/g2/Type.g:17:9: 'datetime'
			{
			match("datetime"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:18:7: ( 'dt' )
			// /Users/fulvio/Desktop/g2/Type.g:18:9: 'dt'
			{
			match("dt"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:19:7: ( 'month_year' )
			// /Users/fulvio/Desktop/g2/Type.g:19:9: 'month_year'
			{
			match("month_year"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:20:7: ( 'ordered' )
			// /Users/fulvio/Desktop/g2/Type.g:20:9: 'ordered'
			{
			match("ordered"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:21:7: ( 'ql' )
			// /Users/fulvio/Desktop/g2/Type.g:21:9: 'ql'
			{
			match("ql"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:22:7: ( 'qt' )
			// /Users/fulvio/Desktop/g2/Type.g:22:9: 'qt'
			{
			match("qt"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:23:7: ( 'recurrent' )
			// /Users/fulvio/Desktop/g2/Type.g:23:9: 'recurrent'
			{
			match("recurrent"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:24:7: ( 'st' )
			// /Users/fulvio/Desktop/g2/Type.g:24:9: 'st'
			{
			match("st"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:25:7: ( 'time' )
			// /Users/fulvio/Desktop/g2/Type.g:25:9: 'time'
			{
			match("time"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:26:7: ( 'tx' )
			// /Users/fulvio/Desktop/g2/Type.g:26:9: 'tx'
			{
			match("tx"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:27:7: ( 'unbounded' )
			// /Users/fulvio/Desktop/g2/Type.g:27:9: 'unbounded'
			{
			match("unbounded"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:28:7: ( 'year' )
			// /Users/fulvio/Desktop/g2/Type.g:28:9: 'year'
			{
			match("year"); 

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
			// /Users/fulvio/Desktop/g2/Type.g:29:7: ( '{' )
			// /Users/fulvio/Desktop/g2/Type.g:29:9: '{'
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
	// $ANTLR end "T__26"

	// $ANTLR start "T__27"
	public final void mT__27() throws RecognitionException {
		try {
			int _type = T__27;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/g2/Type.g:30:7: ( '}' )
			// /Users/fulvio/Desktop/g2/Type.g:30:9: '}'
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
	// $ANTLR end "T__27"

	// $ANTLR start "NAME"
	public final void mNAME() throws RecognitionException {
		try {
			int _type = NAME;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/fulvio/Desktop/g2/Type.g:171:6: ( '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '+' | '<' | '>' | '=' | '\\u2264' | '\\u2265' | '@' | '\\'' | '?' | ',' | ';' | '\\u00B0' | '[' | ']' | '(' | ')' | ':' | '.' | '/' | '_' | '\\u00E0' | '\\u00F2' | '\\u00E8' | '\\u00E9' | '\\u00F9' | '\\u00EC' | '\\u00C8' | ' ' )+ '\"' )
			// /Users/fulvio/Desktop/g2/Type.g:171:8: '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '+' | '<' | '>' | '=' | '\\u2264' | '\\u2265' | '@' | '\\'' | '?' | ',' | ';' | '\\u00B0' | '[' | ']' | '(' | ')' | ':' | '.' | '/' | '_' | '\\u00E0' | '\\u00F2' | '\\u00E8' | '\\u00E9' | '\\u00F9' | '\\u00EC' | '\\u00C8' | ' ' )+ '\"'
			{
			match('\"'); 
			// /Users/fulvio/Desktop/g2/Type.g:171:12: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '+' | '<' | '>' | '=' | '\\u2264' | '\\u2265' | '@' | '\\'' | '?' | ',' | ';' | '\\u00B0' | '[' | ']' | '(' | ')' | ':' | '.' | '/' | '_' | '\\u00E0' | '\\u00F2' | '\\u00E8' | '\\u00E9' | '\\u00F9' | '\\u00EC' | '\\u00C8' | ' ' )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==' '||(LA1_0 >= '\'' && LA1_0 <= ')')||(LA1_0 >= '+' && LA1_0 <= '[')||LA1_0==']'||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')||LA1_0=='\u00B0'||LA1_0=='\u00C8'||LA1_0=='\u00E0'||(LA1_0 >= '\u00E8' && LA1_0 <= '\u00E9')||LA1_0=='\u00EC'||LA1_0=='\u00F2'||LA1_0=='\u00F9'||(LA1_0 >= '\u2264' && LA1_0 <= '\u2265')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:
					{
					if ( input.LA(1)==' '||(input.LA(1) >= '\'' && input.LA(1) <= ')')||(input.LA(1) >= '+' && input.LA(1) <= '[')||input.LA(1)==']'||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||input.LA(1)=='\u00B0'||input.LA(1)=='\u00C8'||input.LA(1)=='\u00E0'||(input.LA(1) >= '\u00E8' && input.LA(1) <= '\u00E9')||input.LA(1)=='\u00EC'||input.LA(1)=='\u00F2'||input.LA(1)=='\u00F9'||(input.LA(1) >= '\u2264' && input.LA(1) <= '\u2265') ) {
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
			// /Users/fulvio/Desktop/g2/Type.g:175:8: ( ( '0' .. '9' )+ )
			// /Users/fulvio/Desktop/g2/Type.g:175:10: ( '0' .. '9' )+
			{
			// /Users/fulvio/Desktop/g2/Type.g:175:10: ( '0' .. '9' )+
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
					// /Users/fulvio/Desktop/g2/Type.g:
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
			// /Users/fulvio/Desktop/g2/Type.g:177:12: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
			// /Users/fulvio/Desktop/g2/Type.g:177:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
			{
			// /Users/fulvio/Desktop/g2/Type.g:177:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
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
					// /Users/fulvio/Desktop/g2/Type.g:
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
			// /Users/fulvio/Desktop/g2/Type.g:179:9: ( . )
			// /Users/fulvio/Desktop/g2/Type.g:179:11: .
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

	@Override
	public void mTokens() throws RecognitionException {
		// /Users/fulvio/Desktop/g2/Type.g:1:8: ( T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | NAME | NUMBER | WHITESPACE | ILLEGAL )
		int alt4=24;
		int LA4_0 = input.LA(1);
		if ( (LA4_0=='(') ) {
			alt4=1;
		}
		else if ( (LA4_0==')') ) {
			alt4=2;
		}
		else if ( (LA4_0==',') ) {
			alt4=3;
		}
		else if ( (LA4_0==':') ) {
			alt4=4;
		}
		else if ( (LA4_0=='c') ) {
			int LA4_5 = input.LA(2);
			if ( (LA4_5=='t') ) {
				alt4=5;
			}

			else {
				alt4=24;
			}

		}
		else if ( (LA4_0=='d') ) {
			switch ( input.LA(2) ) {
			case 'a':
				{
				int LA4_26 = input.LA(3);
				if ( (LA4_26=='t') ) {
					int LA4_43 = input.LA(4);
					if ( (LA4_43=='e') ) {
						int LA4_44 = input.LA(5);
						if ( (LA4_44=='t') ) {
							alt4=7;
						}

						else {
							alt4=6;
						}

					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 4 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 4, 43, input);
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
							new NoViableAltException("", 4, 26, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 't':
				{
				alt4=8;
				}
				break;
			default:
				alt4=24;
			}
		}
		else if ( (LA4_0=='m') ) {
			int LA4_7 = input.LA(2);
			if ( (LA4_7=='o') ) {
				alt4=9;
			}

			else {
				alt4=24;
			}

		}
		else if ( (LA4_0=='o') ) {
			int LA4_8 = input.LA(2);
			if ( (LA4_8=='r') ) {
				alt4=10;
			}

			else {
				alt4=24;
			}

		}
		else if ( (LA4_0=='q') ) {
			switch ( input.LA(2) ) {
			case 'l':
				{
				alt4=11;
				}
				break;
			case 't':
				{
				alt4=12;
				}
				break;
			default:
				alt4=24;
			}
		}
		else if ( (LA4_0=='r') ) {
			int LA4_10 = input.LA(2);
			if ( (LA4_10=='e') ) {
				alt4=13;
			}

			else {
				alt4=24;
			}

		}
		else if ( (LA4_0=='s') ) {
			int LA4_11 = input.LA(2);
			if ( (LA4_11=='t') ) {
				alt4=14;
			}

			else {
				alt4=24;
			}

		}
		else if ( (LA4_0=='t') ) {
			switch ( input.LA(2) ) {
			case 'i':
				{
				alt4=15;
				}
				break;
			case 'x':
				{
				alt4=16;
				}
				break;
			default:
				alt4=24;
			}
		}
		else if ( (LA4_0=='u') ) {
			int LA4_13 = input.LA(2);
			if ( (LA4_13=='n') ) {
				alt4=17;
			}

			else {
				alt4=24;
			}

		}
		else if ( (LA4_0=='y') ) {
			int LA4_14 = input.LA(2);
			if ( (LA4_14=='e') ) {
				alt4=18;
			}

			else {
				alt4=24;
			}

		}
		else if ( (LA4_0=='{') ) {
			alt4=19;
		}
		else if ( (LA4_0=='}') ) {
			alt4=20;
		}
		else if ( (LA4_0=='\"') ) {
			int LA4_17 = input.LA(2);
			if ( (LA4_17==' '||(LA4_17 >= '\'' && LA4_17 <= ')')||(LA4_17 >= '+' && LA4_17 <= '[')||LA4_17==']'||LA4_17=='_'||(LA4_17 >= 'a' && LA4_17 <= 'z')||LA4_17=='\u00B0'||LA4_17=='\u00C8'||LA4_17=='\u00E0'||(LA4_17 >= '\u00E8' && LA4_17 <= '\u00E9')||LA4_17=='\u00EC'||LA4_17=='\u00F2'||LA4_17=='\u00F9'||(LA4_17 >= '\u2264' && LA4_17 <= '\u2265')) ) {
				alt4=21;
			}

			else {
				alt4=24;
			}

		}
		else if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
			alt4=22;
		}
		else if ( ((LA4_0 >= '\t' && LA4_0 <= '\n')||(LA4_0 >= '\f' && LA4_0 <= '\r')||LA4_0==' ') ) {
			alt4=23;
		}
		else if ( ((LA4_0 >= '\u0000' && LA4_0 <= '\b')||LA4_0=='\u000B'||(LA4_0 >= '\u000E' && LA4_0 <= '\u001F')||LA4_0=='!'||(LA4_0 >= '#' && LA4_0 <= '\'')||(LA4_0 >= '*' && LA4_0 <= '+')||(LA4_0 >= '-' && LA4_0 <= '/')||(LA4_0 >= ';' && LA4_0 <= 'b')||(LA4_0 >= 'e' && LA4_0 <= 'l')||LA4_0=='n'||LA4_0=='p'||(LA4_0 >= 'v' && LA4_0 <= 'x')||LA4_0=='z'||LA4_0=='|'||(LA4_0 >= '~' && LA4_0 <= '\uFFFF')) ) {
			alt4=24;
		}

		else {
			NoViableAltException nvae =
				new NoViableAltException("", 4, 0, input);
			throw nvae;
		}

		switch (alt4) {
			case 1 :
				// /Users/fulvio/Desktop/g2/Type.g:1:10: T__8
				{
				mT__8(); 

				}
				break;
			case 2 :
				// /Users/fulvio/Desktop/g2/Type.g:1:15: T__9
				{
				mT__9(); 

				}
				break;
			case 3 :
				// /Users/fulvio/Desktop/g2/Type.g:1:20: T__10
				{
				mT__10(); 

				}
				break;
			case 4 :
				// /Users/fulvio/Desktop/g2/Type.g:1:26: T__11
				{
				mT__11(); 

				}
				break;
			case 5 :
				// /Users/fulvio/Desktop/g2/Type.g:1:32: T__12
				{
				mT__12(); 

				}
				break;
			case 6 :
				// /Users/fulvio/Desktop/g2/Type.g:1:38: T__13
				{
				mT__13(); 

				}
				break;
			case 7 :
				// /Users/fulvio/Desktop/g2/Type.g:1:44: T__14
				{
				mT__14(); 

				}
				break;
			case 8 :
				// /Users/fulvio/Desktop/g2/Type.g:1:50: T__15
				{
				mT__15(); 

				}
				break;
			case 9 :
				// /Users/fulvio/Desktop/g2/Type.g:1:56: T__16
				{
				mT__16(); 

				}
				break;
			case 10 :
				// /Users/fulvio/Desktop/g2/Type.g:1:62: T__17
				{
				mT__17(); 

				}
				break;
			case 11 :
				// /Users/fulvio/Desktop/g2/Type.g:1:68: T__18
				{
				mT__18(); 

				}
				break;
			case 12 :
				// /Users/fulvio/Desktop/g2/Type.g:1:74: T__19
				{
				mT__19(); 

				}
				break;
			case 13 :
				// /Users/fulvio/Desktop/g2/Type.g:1:80: T__20
				{
				mT__20(); 

				}
				break;
			case 14 :
				// /Users/fulvio/Desktop/g2/Type.g:1:86: T__21
				{
				mT__21(); 

				}
				break;
			case 15 :
				// /Users/fulvio/Desktop/g2/Type.g:1:92: T__22
				{
				mT__22(); 

				}
				break;
			case 16 :
				// /Users/fulvio/Desktop/g2/Type.g:1:98: T__23
				{
				mT__23(); 

				}
				break;
			case 17 :
				// /Users/fulvio/Desktop/g2/Type.g:1:104: T__24
				{
				mT__24(); 

				}
				break;
			case 18 :
				// /Users/fulvio/Desktop/g2/Type.g:1:110: T__25
				{
				mT__25(); 

				}
				break;
			case 19 :
				// /Users/fulvio/Desktop/g2/Type.g:1:116: T__26
				{
				mT__26(); 

				}
				break;
			case 20 :
				// /Users/fulvio/Desktop/g2/Type.g:1:122: T__27
				{
				mT__27(); 

				}
				break;
			case 21 :
				// /Users/fulvio/Desktop/g2/Type.g:1:128: NAME
				{
				mNAME(); 

				}
				break;
			case 22 :
				// /Users/fulvio/Desktop/g2/Type.g:1:133: NUMBER
				{
				mNUMBER(); 

				}
				break;
			case 23 :
				// /Users/fulvio/Desktop/g2/Type.g:1:140: WHITESPACE
				{
				mWHITESPACE(); 

				}
				break;
			case 24 :
				// /Users/fulvio/Desktop/g2/Type.g:1:151: ILLEGAL
				{
				mILLEGAL(); 

				}
				break;

		}
	}



}
