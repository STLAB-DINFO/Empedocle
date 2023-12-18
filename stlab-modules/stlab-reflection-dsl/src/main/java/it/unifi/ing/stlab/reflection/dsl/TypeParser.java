// $ANTLR 3.5.2 /Users/fulvio/Desktop/g2/Type.g 2018-11-07 11:11:39

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class TypeParser extends BaseTypeParser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ILLEGAL", "NAME", "NUMBER", "WHITESPACE", 
		"'('", "')'", "','", "':'", "'ct'", "'date'", "'datetime'", "'dt'", "'month_year'", 
		"'ordered'", "'ql'", "'qt'", "'recurrent'", "'st'", "'time'", "'tx'", 
		"'unbounded'", "'year'", "'{'", "'}'"
	};
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
	public BaseTypeParser[] getDelegates() {
		return new BaseTypeParser[] {};
	}

	// delegators


	public TypeParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public TypeParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return TypeParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/Users/fulvio/Desktop/g2/Type.g"; }


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



	// $ANTLR start "rule"
	// /Users/fulvio/Desktop/g2/Type.g:52:1: rule returns [Type ret] : (r1= tx |r2= ql |r3= st |r4= qt |r5= dt |r6= ct ) EOF ;
	public final Type rule() throws RecognitionException {
		Type ret = null;


		TextualType r1 =null;
		EnumeratedType r2 =null;
		QueriedType r3 =null;
		QuantitativeType r4 =null;
		TemporalType r5 =null;
		CompositeType r6 =null;

		try {
			// /Users/fulvio/Desktop/g2/Type.g:53:3: ( (r1= tx |r2= ql |r3= st |r4= qt |r5= dt |r6= ct ) EOF )
			// /Users/fulvio/Desktop/g2/Type.g:53:5: (r1= tx |r2= ql |r3= st |r4= qt |r5= dt |r6= ct ) EOF
			{
			// /Users/fulvio/Desktop/g2/Type.g:53:5: (r1= tx |r2= ql |r3= st |r4= qt |r5= dt |r6= ct )
			int alt1=6;
			switch ( input.LA(1) ) {
			case 23:
				{
				alt1=1;
				}
				break;
			case 18:
				{
				alt1=2;
				}
				break;
			case 21:
				{
				alt1=3;
				}
				break;
			case 19:
				{
				alt1=4;
				}
				break;
			case 15:
				{
				alt1=5;
				}
				break;
			case 12:
				{
				alt1=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}
			switch (alt1) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:53:7: r1= tx
					{
					pushFollow(FOLLOW_tx_in_rule73);
					r1=tx();
					state._fsp--;

					 ret = r1; 
					}
					break;
				case 2 :
					// /Users/fulvio/Desktop/g2/Type.g:54:5: r2= ql
					{
					pushFollow(FOLLOW_ql_in_rule83);
					r2=ql();
					state._fsp--;

					 ret = r2; 
					}
					break;
				case 3 :
					// /Users/fulvio/Desktop/g2/Type.g:55:5: r3= st
					{
					pushFollow(FOLLOW_st_in_rule93);
					r3=st();
					state._fsp--;

					 ret = r3; 
					}
					break;
				case 4 :
					// /Users/fulvio/Desktop/g2/Type.g:56:5: r4= qt
					{
					pushFollow(FOLLOW_qt_in_rule103);
					r4=qt();
					state._fsp--;

					 ret = r4; 
					}
					break;
				case 5 :
					// /Users/fulvio/Desktop/g2/Type.g:57:5: r5= dt
					{
					pushFollow(FOLLOW_dt_in_rule113);
					r5=dt();
					state._fsp--;

					 ret = r5; 
					}
					break;
				case 6 :
					// /Users/fulvio/Desktop/g2/Type.g:58:5: r6= ct
					{
					pushFollow(FOLLOW_ct_in_rule123);
					r6=ct();
					state._fsp--;

					 ret = r6; 
					}
					break;

			}

			 ret.setAnonymous( false ); 
			match(input,EOF,FOLLOW_EOF_in_rule134); 
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



	// $ANTLR start "tx"
	// /Users/fulvio/Desktop/g2/Type.g:62:1: tx returns [TextualType ret] : 'tx' ( 'recurrent' )? ;
	public final TextualType tx() throws RecognitionException {
		TextualType ret = null;


		try {
			// /Users/fulvio/Desktop/g2/Type.g:63:3: ( 'tx' ( 'recurrent' )? )
			// /Users/fulvio/Desktop/g2/Type.g:63:5: 'tx' ( 'recurrent' )?
			{
			match(input,23,FOLLOW_23_in_tx148); 
			 boolean recurrent = false;
			        ret = createTextualType(); 
			// /Users/fulvio/Desktop/g2/Type.g:66:5: ( 'recurrent' )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==20) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:66:7: 'recurrent'
					{
					match(input,20,FOLLOW_20_in_tx162); 
					 recurrent = true; 
					}
					break;

			}

			 ret.setRecurrent( recurrent ); ret.setAnonymous( true ); 
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
	// $ANTLR end "tx"



	// $ANTLR start "ql"
	// /Users/fulvio/Desktop/g2/Type.g:69:1: ql returns [EnumeratedType ret] : 'ql' ( 'recurrent' )? ( 'ordered' )? '{' n1= NAME ( ',' n2= NAME )* '}' ;
	public final EnumeratedType ql() throws RecognitionException {
		EnumeratedType ret = null;


		Token n1=null;
		Token n2=null;

		try {
			// /Users/fulvio/Desktop/g2/Type.g:70:3: ( 'ql' ( 'recurrent' )? ( 'ordered' )? '{' n1= NAME ( ',' n2= NAME )* '}' )
			// /Users/fulvio/Desktop/g2/Type.g:70:5: 'ql' ( 'recurrent' )? ( 'ordered' )? '{' n1= NAME ( ',' n2= NAME )* '}'
			{
			match(input,18,FOLLOW_18_in_ql187); 
			 boolean recurrent = false;
			      boolean ordered = false;
			        ret = createEnumeratedType(); 
			// /Users/fulvio/Desktop/g2/Type.g:74:5: ( 'recurrent' )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==20) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:74:7: 'recurrent'
					{
					match(input,20,FOLLOW_20_in_ql201); 
					 recurrent = true; 
					}
					break;

			}

			 ret.setRecurrent( recurrent ); ret.setAnonymous( true ); 
			// /Users/fulvio/Desktop/g2/Type.g:76:5: ( 'ordered' )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==17) ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:76:7: 'ordered'
					{
					match(input,17,FOLLOW_17_in_ql220); 
					 ordered = true; 
					}
					break;

			}

			match(input,26,FOLLOW_26_in_ql231); 
			n1=(Token)match(input,NAME,FOLLOW_NAME_in_ql241); 
			 createPhenomenon( ret, trim( n1.getText() ), ordered ); 
			// /Users/fulvio/Desktop/g2/Type.g:79:7: ( ',' n2= NAME )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0==10) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:79:8: ',' n2= NAME
					{
					match(input,10,FOLLOW_10_in_ql252); 
					n2=(Token)match(input,NAME,FOLLOW_NAME_in_ql256); 
					 createPhenomenon( ret, trim( n2.getText() ), ordered ); 
					}
					break;

				default :
					break loop5;
				}
			}

			match(input,27,FOLLOW_27_in_ql267); 
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
	// $ANTLR end "ql"



	// $ANTLR start "st"
	// /Users/fulvio/Desktop/g2/Type.g:83:1: st returns [QueriedType ret] : 'st' ( 'recurrent' )? '{' n= NAME '}' ;
	public final QueriedType st() throws RecognitionException {
		QueriedType ret = null;


		Token n=null;

		try {
			// /Users/fulvio/Desktop/g2/Type.g:84:3: ( 'st' ( 'recurrent' )? '{' n= NAME '}' )
			// /Users/fulvio/Desktop/g2/Type.g:84:5: 'st' ( 'recurrent' )? '{' n= NAME '}'
			{
			match(input,21,FOLLOW_21_in_st282); 
			 boolean recurrent = false;
			        ret = createQueriedType(); 
			// /Users/fulvio/Desktop/g2/Type.g:87:5: ( 'recurrent' )?
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==20) ) {
				alt6=1;
			}
			switch (alt6) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:87:7: 'recurrent'
					{
					match(input,20,FOLLOW_20_in_st296); 
					 recurrent = true; 
					}
					break;

			}

			 ret.setRecurrent( recurrent ); ret.setAnonymous( true ); 
			match(input,26,FOLLOW_26_in_st313); 
			n=(Token)match(input,NAME,FOLLOW_NAME_in_st323); 
			 ret.setQueryDef( trim( n.getText() )); 
			match(input,27,FOLLOW_27_in_st331); 
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
	// $ANTLR end "st"



	// $ANTLR start "qt"
	// /Users/fulvio/Desktop/g2/Type.g:94:1: qt returns [QuantitativeType ret] : 'qt' ( 'recurrent' )? '{' r1= um ( ',' r2= um )* '}' ;
	public final QuantitativeType qt() throws RecognitionException {
		QuantitativeType ret = null;


		UnitUse r1 =null;
		UnitUse r2 =null;

		try {
			// /Users/fulvio/Desktop/g2/Type.g:95:3: ( 'qt' ( 'recurrent' )? '{' r1= um ( ',' r2= um )* '}' )
			// /Users/fulvio/Desktop/g2/Type.g:95:5: 'qt' ( 'recurrent' )? '{' r1= um ( ',' r2= um )* '}'
			{
			match(input,19,FOLLOW_19_in_qt346); 
			 boolean recurrent = false;
			        ret = createQuantitativeType(); 
			// /Users/fulvio/Desktop/g2/Type.g:98:5: ( 'recurrent' )?
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==20) ) {
				alt7=1;
			}
			switch (alt7) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:98:7: 'recurrent'
					{
					match(input,20,FOLLOW_20_in_qt360); 
					 recurrent = true; 
					}
					break;

			}

			 ret.setRecurrent( recurrent ); ret.setAnonymous( true ); 
			match(input,26,FOLLOW_26_in_qt377); 
			pushFollow(FOLLOW_um_in_qt389);
			r1=um();
			state._fsp--;

			 ret.addUnit( r1 ); 
			// /Users/fulvio/Desktop/g2/Type.g:102:7: ( ',' r2= um )*
			loop8:
			while (true) {
				int alt8=2;
				int LA8_0 = input.LA(1);
				if ( (LA8_0==10) ) {
					alt8=1;
				}

				switch (alt8) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:102:8: ',' r2= um
					{
					match(input,10,FOLLOW_10_in_qt400); 
					pushFollow(FOLLOW_um_in_qt406);
					r2=um();
					state._fsp--;

					 ret.addUnit( r2 ); 
					}
					break;

				default :
					break loop8;
				}
			}

			match(input,27,FOLLOW_27_in_qt417); 
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
	// $ANTLR end "qt"



	// $ANTLR start "dt"
	// /Users/fulvio/Desktop/g2/Type.g:105:1: dt returns [TemporalType ret] : 'dt' ( 'date' | 'datetime' | 'time' | 'year' | 'month_year' )? ( 'recurrent' )? ;
	public final TemporalType dt() throws RecognitionException {
		TemporalType ret = null;


		try {
			// /Users/fulvio/Desktop/g2/Type.g:106:3: ( 'dt' ( 'date' | 'datetime' | 'time' | 'year' | 'month_year' )? ( 'recurrent' )? )
			// /Users/fulvio/Desktop/g2/Type.g:106:5: 'dt' ( 'date' | 'datetime' | 'time' | 'year' | 'month_year' )? ( 'recurrent' )?
			{
			match(input,15,FOLLOW_15_in_dt431); 
			 boolean recurrent = false;
			        ret = createTemporalType();
			        ret.setType( TimeFormat.DATE ); 
			// /Users/fulvio/Desktop/g2/Type.g:110:5: ( 'date' | 'datetime' | 'time' | 'year' | 'month_year' )?
			int alt9=6;
			switch ( input.LA(1) ) {
				case 13:
					{
					alt9=1;
					}
					break;
				case 14:
					{
					alt9=2;
					}
					break;
				case 22:
					{
					alt9=3;
					}
					break;
				case 25:
					{
					alt9=4;
					}
					break;
				case 16:
					{
					alt9=5;
					}
					break;
			}
			switch (alt9) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:110:7: 'date'
					{
					match(input,13,FOLLOW_13_in_dt445); 
					}
					break;
				case 2 :
					// /Users/fulvio/Desktop/g2/Type.g:111:9: 'datetime'
					{
					match(input,14,FOLLOW_14_in_dt455); 
					 ret.setType( TimeFormat.DATETIME ); 
					}
					break;
				case 3 :
					// /Users/fulvio/Desktop/g2/Type.g:112:9: 'time'
					{
					match(input,22,FOLLOW_22_in_dt467); 
					 ret.setType( TimeFormat.TIME ); 
					}
					break;
				case 4 :
					// /Users/fulvio/Desktop/g2/Type.g:113:9: 'year'
					{
					match(input,25,FOLLOW_25_in_dt479); 
					 ret.setType( TimeFormat.YEAR ); 
					}
					break;
				case 5 :
					// /Users/fulvio/Desktop/g2/Type.g:114:9: 'month_year'
					{
					match(input,16,FOLLOW_16_in_dt491); 
					 ret.setType( TimeFormat.MONTH_YEAR ); 
					}
					break;

			}

			// /Users/fulvio/Desktop/g2/Type.g:115:5: ( 'recurrent' )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==20) ) {
				alt10=1;
			}
			switch (alt10) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:115:7: 'recurrent'
					{
					match(input,20,FOLLOW_20_in_dt504); 
					 recurrent = true; 
					}
					break;

			}

			 ret.setRecurrent( recurrent );
			      ret.setAnonymous( true ); 
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
	// $ANTLR end "dt"



	// $ANTLR start "um"
	// /Users/fulvio/Desktop/g2/Type.g:119:1: um returns [UnitUse ret] : n1= NAME '(' n2= NUMBER ',' n3= NUMBER ')' ;
	public final UnitUse um() throws RecognitionException {
		UnitUse ret = null;


		Token n1=null;
		Token n2=null;
		Token n3=null;

		try {
			// /Users/fulvio/Desktop/g2/Type.g:120:3: (n1= NAME '(' n2= NUMBER ',' n3= NUMBER ')' )
			// /Users/fulvio/Desktop/g2/Type.g:120:5: n1= NAME '(' n2= NUMBER ',' n3= NUMBER ')'
			{
			n1=(Token)match(input,NAME,FOLLOW_NAME_in_um531); 
			match(input,8,FOLLOW_8_in_um535); 
			n2=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_um541); 
			match(input,10,FOLLOW_10_in_um545); 
			n3=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_um551); 
			match(input,9,FOLLOW_9_in_um555); 
			 ret = createUnitUse( trim( n1.getText() ), integer( n2.getText() ), integer( n3.getText() )); 
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
	// $ANTLR end "um"



	// $ANTLR start "ct"
	// /Users/fulvio/Desktop/g2/Type.g:129:1: ct returns [CompositeType ret] : 'ct' ( 'recurrent' )? '{' r1= sct ( ',' r2= sct )* '}' ;
	public final CompositeType ct() throws RecognitionException {
		CompositeType ret = null;


		TypeLink r1 =null;
		TypeLink r2 =null;

		try {
			// /Users/fulvio/Desktop/g2/Type.g:130:3: ( 'ct' ( 'recurrent' )? '{' r1= sct ( ',' r2= sct )* '}' )
			// /Users/fulvio/Desktop/g2/Type.g:130:5: 'ct' ( 'recurrent' )? '{' r1= sct ( ',' r2= sct )* '}'
			{
			match(input,12,FOLLOW_12_in_ct574); 
			 long priority = 0;
			      boolean recurrent = false;
			        ret = createCompositeType(); 
			// /Users/fulvio/Desktop/g2/Type.g:134:5: ( 'recurrent' )?
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( (LA11_0==20) ) {
				alt11=1;
			}
			switch (alt11) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:134:7: 'recurrent'
					{
					match(input,20,FOLLOW_20_in_ct588); 
					 recurrent = true; 
					}
					break;

			}

			 ret.setRecurrent( recurrent ); ret.setAnonymous( true ); 
			match(input,26,FOLLOW_26_in_ct605); 
			pushFollow(FOLLOW_sct_in_ct617);
			r1=sct();
			state._fsp--;

			 r1.assignSource( ret ); r1.setPriority( priority); priority++;
			// /Users/fulvio/Desktop/g2/Type.g:138:7: ( ',' r2= sct )*
			loop12:
			while (true) {
				int alt12=2;
				int LA12_0 = input.LA(1);
				if ( (LA12_0==10) ) {
					alt12=1;
				}

				switch (alt12) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:138:8: ',' r2= sct
					{
					match(input,10,FOLLOW_10_in_ct628); 
					pushFollow(FOLLOW_sct_in_ct634);
					r2=sct();
					state._fsp--;

					 r2.assignSource( ret ); r2.setPriority( priority); priority++; 
					}
					break;

				default :
					break loop12;
				}
			}

			match(input,27,FOLLOW_27_in_ct644); 
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
	// $ANTLR end "ct"



	// $ANTLR start "sct"
	// /Users/fulvio/Desktop/g2/Type.g:142:1: sct returns [TypeLink ret] :n1= NAME ( '(' n2= NUMBER ',' (n3= NUMBER | 'unbounded' ) ')' )? ':' (sc1= tx |sc2= ql |sc3= dt |sc4= st |sc5= qt |sc6= ct |n4= NAME ) ;
	public final TypeLink sct() throws RecognitionException {
		TypeLink ret = null;


		Token n1=null;
		Token n2=null;
		Token n3=null;
		Token n4=null;
		TextualType sc1 =null;
		EnumeratedType sc2 =null;
		TemporalType sc3 =null;
		QueriedType sc4 =null;
		QuantitativeType sc5 =null;
		CompositeType sc6 =null;

		try {
			// /Users/fulvio/Desktop/g2/Type.g:143:3: (n1= NAME ( '(' n2= NUMBER ',' (n3= NUMBER | 'unbounded' ) ')' )? ':' (sc1= tx |sc2= ql |sc3= dt |sc4= st |sc5= qt |sc6= ct |n4= NAME ) )
			// /Users/fulvio/Desktop/g2/Type.g:143:5: n1= NAME ( '(' n2= NUMBER ',' (n3= NUMBER | 'unbounded' ) ')' )? ':' (sc1= tx |sc2= ql |sc3= dt |sc4= st |sc5= qt |sc6= ct |n4= NAME )
			{
			 ret = createTypeLink(); 
			n1=(Token)match(input,NAME,FOLLOW_NAME_in_sct667); 
			 ret.setName( trim( n1.getText()));
			    ret.setMin( 1 );
			    ret.setMax( 1 );
			  
			// /Users/fulvio/Desktop/g2/Type.g:149:3: ( '(' n2= NUMBER ',' (n3= NUMBER | 'unbounded' ) ')' )?
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==8) ) {
				alt14=1;
			}
			switch (alt14) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:149:5: '(' n2= NUMBER ',' (n3= NUMBER | 'unbounded' ) ')'
					{
					match(input,8,FOLLOW_8_in_sct677); 
					n2=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_sct687); 
					 ret.setMin( integer( n2.getText())); 
					match(input,10,FOLLOW_10_in_sct695); 
					// /Users/fulvio/Desktop/g2/Type.g:152:5: (n3= NUMBER | 'unbounded' )
					int alt13=2;
					int LA13_0 = input.LA(1);
					if ( (LA13_0==NUMBER) ) {
						alt13=1;
					}
					else if ( (LA13_0==24) ) {
						alt13=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 13, 0, input);
						throw nvae;
					}

					switch (alt13) {
						case 1 :
							// /Users/fulvio/Desktop/g2/Type.g:152:7: n3= NUMBER
							{
							n3=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_sct707); 
							 ret.setMax( integer( n3.getText() )); 
							}
							break;
						case 2 :
							// /Users/fulvio/Desktop/g2/Type.g:153:7: 'unbounded'
							{
							match(input,24,FOLLOW_24_in_sct717); 
							 ret.setMax( 0 ); 
							}
							break;

					}

					match(input,9,FOLLOW_9_in_sct725); 
					}
					break;

			}

			match(input,11,FOLLOW_11_in_sct732); 
			 Type sc = null; 
			// /Users/fulvio/Desktop/g2/Type.g:157:3: (sc1= tx |sc2= ql |sc3= dt |sc4= st |sc5= qt |sc6= ct |n4= NAME )
			int alt15=7;
			switch ( input.LA(1) ) {
			case 23:
				{
				alt15=1;
				}
				break;
			case 18:
				{
				alt15=2;
				}
				break;
			case 15:
				{
				alt15=3;
				}
				break;
			case 21:
				{
				alt15=4;
				}
				break;
			case 19:
				{
				alt15=5;
				}
				break;
			case 12:
				{
				alt15=6;
				}
				break;
			case NAME:
				{
				alt15=7;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 15, 0, input);
				throw nvae;
			}
			switch (alt15) {
				case 1 :
					// /Users/fulvio/Desktop/g2/Type.g:157:5: sc1= tx
					{
					pushFollow(FOLLOW_tx_in_sct746);
					sc1=tx();
					state._fsp--;

					 sc = sc1; 
					}
					break;
				case 2 :
					// /Users/fulvio/Desktop/g2/Type.g:158:5: sc2= ql
					{
					pushFollow(FOLLOW_ql_in_sct758);
					sc2=ql();
					state._fsp--;

					 sc = sc2; 
					}
					break;
				case 3 :
					// /Users/fulvio/Desktop/g2/Type.g:159:5: sc3= dt
					{
					pushFollow(FOLLOW_dt_in_sct770);
					sc3=dt();
					state._fsp--;

					 sc = sc3; 
					}
					break;
				case 4 :
					// /Users/fulvio/Desktop/g2/Type.g:160:5: sc4= st
					{
					pushFollow(FOLLOW_st_in_sct782);
					sc4=st();
					state._fsp--;

					 sc = sc4; 
					}
					break;
				case 5 :
					// /Users/fulvio/Desktop/g2/Type.g:161:5: sc5= qt
					{
					pushFollow(FOLLOW_qt_in_sct794);
					sc5=qt();
					state._fsp--;

					 sc = sc5; 
					}
					break;
				case 6 :
					// /Users/fulvio/Desktop/g2/Type.g:162:5: sc6= ct
					{
					pushFollow(FOLLOW_ct_in_sct806);
					sc6=ct();
					state._fsp--;

					 sc = sc6; 
					}
					break;
				case 7 :
					// /Users/fulvio/Desktop/g2/Type.g:163:5: n4= NAME
					{
					n4=(Token)match(input,NAME,FOLLOW_NAME_in_sct818); 
					 sc = findType( trim( n4.getText())); 
					}
					break;

			}

			 ret.assignTarget( sc ); 
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
	// $ANTLR end "sct"

	// Delegated rules



	public static final BitSet FOLLOW_tx_in_rule73 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_ql_in_rule83 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_st_in_rule93 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_qt_in_rule103 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_dt_in_rule113 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_ct_in_rule123 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_rule134 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_23_in_tx148 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_20_in_tx162 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_18_in_ql187 = new BitSet(new long[]{0x0000000004120000L});
	public static final BitSet FOLLOW_20_in_ql201 = new BitSet(new long[]{0x0000000004020000L});
	public static final BitSet FOLLOW_17_in_ql220 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_26_in_ql231 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_NAME_in_ql241 = new BitSet(new long[]{0x0000000008000400L});
	public static final BitSet FOLLOW_10_in_ql252 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_NAME_in_ql256 = new BitSet(new long[]{0x0000000008000400L});
	public static final BitSet FOLLOW_27_in_ql267 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_21_in_st282 = new BitSet(new long[]{0x0000000004100000L});
	public static final BitSet FOLLOW_20_in_st296 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_26_in_st313 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_NAME_in_st323 = new BitSet(new long[]{0x0000000008000000L});
	public static final BitSet FOLLOW_27_in_st331 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_19_in_qt346 = new BitSet(new long[]{0x0000000004100000L});
	public static final BitSet FOLLOW_20_in_qt360 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_26_in_qt377 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_um_in_qt389 = new BitSet(new long[]{0x0000000008000400L});
	public static final BitSet FOLLOW_10_in_qt400 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_um_in_qt406 = new BitSet(new long[]{0x0000000008000400L});
	public static final BitSet FOLLOW_27_in_qt417 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_15_in_dt431 = new BitSet(new long[]{0x0000000002516002L});
	public static final BitSet FOLLOW_13_in_dt445 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_14_in_dt455 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_22_in_dt467 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_25_in_dt479 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_16_in_dt491 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_20_in_dt504 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_um531 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_8_in_um535 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NUMBER_in_um541 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_um545 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NUMBER_in_um551 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_um555 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_ct574 = new BitSet(new long[]{0x0000000004100000L});
	public static final BitSet FOLLOW_20_in_ct588 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_26_in_ct605 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_sct_in_ct617 = new BitSet(new long[]{0x0000000008000400L});
	public static final BitSet FOLLOW_10_in_ct628 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_sct_in_ct634 = new BitSet(new long[]{0x0000000008000400L});
	public static final BitSet FOLLOW_27_in_ct644 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_sct667 = new BitSet(new long[]{0x0000000000000900L});
	public static final BitSet FOLLOW_8_in_sct677 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NUMBER_in_sct687 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_sct695 = new BitSet(new long[]{0x0000000001000040L});
	public static final BitSet FOLLOW_NUMBER_in_sct707 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_24_in_sct717 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_sct725 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_sct732 = new BitSet(new long[]{0x0000000000AC9020L});
	public static final BitSet FOLLOW_tx_in_sct746 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ql_in_sct758 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dt_in_sct770 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_st_in_sct782 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_qt_in_sct794 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ct_in_sct806 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_sct818 = new BitSet(new long[]{0x0000000000000002L});
}
