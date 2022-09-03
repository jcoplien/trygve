// Generated from Kant.g4 by ANTLR 4.10

package info.fulloo.trygve.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KantParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, STRING=41, INTEGER=42, FLOAT=43, BOOLEAN=44, SWITCH=45, 
		CASE=46, DEFAULT=47, BREAK=48, CONTINUE=49, RETURN=50, REQUIRES=51, NEW=52, 
		CLONE=53, NULL=54, CONST=55, UNUSED=56, POW=57, ELLIPSIS=58, BOOLEAN_SUMOP=59, 
		BOOLEAN_MULOP=60, ABELIAN_SUMOP=61, ABELIAN_MULOP=62, MINUS=63, PLUS=64, 
		LT=65, GT=66, LOGICAL_NEGATION=67, ABELIAN_INCREMENT_OP=68, JAVA_ID=69, 
		INLINE_COMMENT=70, C_COMMENT=71, WHITESPACE=72, ASSIGN=73;
	public static final int
		RULE_program = 0, RULE_main = 1, RULE_type_declaration_list = 2, RULE_type_declaration = 3, 
		RULE_context_declaration = 4, RULE_class_declaration = 5, RULE_interface_declaration = 6, 
		RULE_implements_list = 7, RULE_type_parameters = 8, RULE_type_list = 9, 
		RULE_type_parameter = 10, RULE_context_body = 11, RULE_context_body_element = 12, 
		RULE_role_decl = 13, RULE_role_vec_modifier = 14, RULE_role_body = 15, 
		RULE_self_methods = 16, RULE_stageprop_decl = 17, RULE_stageprop_body = 18, 
		RULE_class_body = 19, RULE_class_body_element = 20, RULE_interface_body = 21, 
		RULE_method_decl = 22, RULE_method_decl_hook = 23, RULE_method_signature = 24, 
		RULE_expr_and_decl_list = 25, RULE_type_and_expr_and_decl_list = 26, RULE_return_type = 27, 
		RULE_method_name = 28, RULE_access_qualifier = 29, RULE_object_decl = 30, 
		RULE_trivial_object_decl = 31, RULE_compound_type_name = 32, RULE_type_name = 33, 
		RULE_builtin_type_name = 34, RULE_identifier_list = 35, RULE_param_list = 36, 
		RULE_param_decl = 37, RULE_expr = 38, RULE_abelian_expr = 39, RULE_abelian_product = 40, 
		RULE_abelian_unary_op = 41, RULE_abelian_atom = 42, RULE_boolean_expr = 43, 
		RULE_boolean_product = 44, RULE_boolean_unary_op = 45, RULE_boolean_atom = 46, 
		RULE_message = 47, RULE_block = 48, RULE_expr_or_null = 49, RULE_if_expr = 50, 
		RULE_for_expr = 51, RULE_while_expr = 52, RULE_do_while_expr = 53, RULE_switch_expr = 54, 
		RULE_switch_body = 55, RULE_null_expr = 56, RULE_constant = 57, RULE_argument_list = 58;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "main", "type_declaration_list", "type_declaration", "context_declaration", 
			"class_declaration", "interface_declaration", "implements_list", "type_parameters", 
			"type_list", "type_parameter", "context_body", "context_body_element", 
			"role_decl", "role_vec_modifier", "role_body", "self_methods", "stageprop_decl", 
			"stageprop_body", "class_body", "class_body_element", "interface_body", 
			"method_decl", "method_decl_hook", "method_signature", "expr_and_decl_list", 
			"type_and_expr_and_decl_list", "return_type", "method_name", "access_qualifier", 
			"object_decl", "trivial_object_decl", "compound_type_name", "type_name", 
			"builtin_type_name", "identifier_list", "param_list", "param_decl", "expr", 
			"abelian_expr", "abelian_product", "abelian_unary_op", "abelian_atom", 
			"boolean_expr", "boolean_product", "boolean_unary_op", "boolean_atom", 
			"message", "block", "expr_or_null", "if_expr", "for_expr", "while_expr", 
			"do_while_expr", "switch_expr", "switch_body", "null_expr", "constant", 
			"argument_list"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'context'", "'{'", "'}'", "'class'", "'extends'", "'interface'", 
			"'implements'", "','", "'role'", "'['", "']'", "';'", "'stageprop'", 
			"'('", "')'", "'public'", "'private'", "'int'", "'double'", "'char'", 
			"'String'", "'.'", "'||'", "'^'", "'is'", "'Is'", "'not'", "'Not'", "'!='", 
			"'=='", "'>='", "'<='", "'isnot'", "'IsNot'", "'if'", "'else'", "'for'", 
			"':'", "'while'", "'do'", null, null, null, null, "'switch'", "'case'", 
			"'default'", "'break'", "'continue'", "'return'", "'requires'", "'new'", 
			"'clone'", "'null'", "'const'", "'unused'", "'**'", "'...'", null, "'&&'", 
			null, null, "'-'", "'+'", "'<'", "'>'", "'!'", null, null, null, null, 
			null, "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "STRING", "INTEGER", "FLOAT", "BOOLEAN", 
			"SWITCH", "CASE", "DEFAULT", "BREAK", "CONTINUE", "RETURN", "REQUIRES", 
			"NEW", "CLONE", "NULL", "CONST", "UNUSED", "POW", "ELLIPSIS", "BOOLEAN_SUMOP", 
			"BOOLEAN_MULOP", "ABELIAN_SUMOP", "ABELIAN_MULOP", "MINUS", "PLUS", "LT", 
			"GT", "LOGICAL_NEGATION", "ABELIAN_INCREMENT_OP", "JAVA_ID", "INLINE_COMMENT", 
			"C_COMMENT", "WHITESPACE", "ASSIGN"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Kant.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public KantParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public Type_declaration_listContext type_declaration_list() {
			return getRuleContext(Type_declaration_listContext.class,0);
		}
		public MainContext main() {
			return getRuleContext(MainContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			setState(122);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(118);
				type_declaration_list(0);
				setState(119);
				main();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(121);
				type_declaration_list(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MainContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public MainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_main; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterMain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitMain(this);
		}
	}

	public final MainContext main() throws RecognitionException {
		MainContext _localctx = new MainContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_main);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Type_declaration_listContext extends ParserRuleContext {
		public Type_declarationContext type_declaration() {
			return getRuleContext(Type_declarationContext.class,0);
		}
		public Type_declaration_listContext type_declaration_list() {
			return getRuleContext(Type_declaration_listContext.class,0);
		}
		public Type_declaration_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_declaration_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterType_declaration_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitType_declaration_list(this);
		}
	}

	public final Type_declaration_listContext type_declaration_list() throws RecognitionException {
		return type_declaration_list(0);
	}

	private Type_declaration_listContext type_declaration_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Type_declaration_listContext _localctx = new Type_declaration_listContext(_ctx, _parentState);
		Type_declaration_listContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_type_declaration_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(127);
				type_declaration();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(135);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Type_declaration_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_type_declaration_list);
					setState(131);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(132);
					type_declaration();
					}
					} 
				}
				setState(137);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Type_declarationContext extends ParserRuleContext {
		public Context_declarationContext context_declaration() {
			return getRuleContext(Context_declarationContext.class,0);
		}
		public Class_declarationContext class_declaration() {
			return getRuleContext(Class_declarationContext.class,0);
		}
		public Interface_declarationContext interface_declaration() {
			return getRuleContext(Interface_declarationContext.class,0);
		}
		public Type_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterType_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitType_declaration(this);
		}
	}

	public final Type_declarationContext type_declaration() throws RecognitionException {
		Type_declarationContext _localctx = new Type_declarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type_declaration);
		try {
			setState(141);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				context_declaration();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(139);
				class_declaration();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 3);
				{
				setState(140);
				interface_declaration();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Context_declarationContext extends ParserRuleContext {
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public Context_bodyContext context_body() {
			return getRuleContext(Context_bodyContext.class,0);
		}
		public List<Implements_listContext> implements_list() {
			return getRuleContexts(Implements_listContext.class);
		}
		public Implements_listContext implements_list(int i) {
			return getRuleContext(Implements_listContext.class,i);
		}
		public Context_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_context_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterContext_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitContext_declaration(this);
		}
	}

	public final Context_declarationContext context_declaration() throws RecognitionException {
		Context_declarationContext _localctx = new Context_declarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_context_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			match(T__0);
			setState(144);
			match(JAVA_ID);
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(145);
				implements_list(0);
				}
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(151);
			match(T__1);
			setState(152);
			context_body(0);
			setState(153);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Class_declarationContext extends ParserRuleContext {
		public List<TerminalNode> JAVA_ID() { return getTokens(KantParser.JAVA_ID); }
		public TerminalNode JAVA_ID(int i) {
			return getToken(KantParser.JAVA_ID, i);
		}
		public Type_parametersContext type_parameters() {
			return getRuleContext(Type_parametersContext.class,0);
		}
		public Class_bodyContext class_body() {
			return getRuleContext(Class_bodyContext.class,0);
		}
		public List<Implements_listContext> implements_list() {
			return getRuleContexts(Implements_listContext.class);
		}
		public Implements_listContext implements_list(int i) {
			return getRuleContext(Implements_listContext.class,i);
		}
		public Class_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterClass_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitClass_declaration(this);
		}
	}

	public final Class_declarationContext class_declaration() throws RecognitionException {
		Class_declarationContext _localctx = new Class_declarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_class_declaration);
		int _la;
		try {
			setState(223);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(155);
				match(T__3);
				setState(156);
				match(JAVA_ID);
				setState(157);
				type_parameters();
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(158);
					implements_list(0);
					}
					}
					setState(163);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(164);
				match(T__1);
				setState(165);
				class_body(0);
				setState(166);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(168);
				match(T__3);
				setState(169);
				match(JAVA_ID);
				setState(170);
				type_parameters();
				setState(171);
				match(T__4);
				setState(172);
				match(JAVA_ID);
				setState(176);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(173);
					implements_list(0);
					}
					}
					setState(178);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(179);
				match(T__1);
				setState(180);
				class_body(0);
				setState(181);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(183);
				match(T__3);
				setState(184);
				match(JAVA_ID);
				setState(188);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(185);
					implements_list(0);
					}
					}
					setState(190);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(191);
				match(T__1);
				setState(192);
				class_body(0);
				setState(193);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(195);
				match(T__3);
				setState(196);
				match(JAVA_ID);
				setState(197);
				match(T__4);
				setState(198);
				match(JAVA_ID);
				setState(202);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(199);
					implements_list(0);
					}
					}
					setState(204);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(205);
				match(T__1);
				setState(206);
				class_body(0);
				setState(207);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(209);
				match(T__3);
				setState(210);
				match(JAVA_ID);
				setState(214);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(211);
					implements_list(0);
					}
					}
					setState(216);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(217);
				match(T__4);
				setState(218);
				match(JAVA_ID);
				setState(219);
				match(T__1);
				setState(220);
				class_body(0);
				setState(221);
				match(T__2);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Interface_declarationContext extends ParserRuleContext {
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public Interface_bodyContext interface_body() {
			return getRuleContext(Interface_bodyContext.class,0);
		}
		public Type_parametersContext type_parameters() {
			return getRuleContext(Type_parametersContext.class,0);
		}
		public Interface_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interface_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterInterface_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitInterface_declaration(this);
		}
	}

	public final Interface_declarationContext interface_declaration() throws RecognitionException {
		Interface_declarationContext _localctx = new Interface_declarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_interface_declaration);
		try {
			setState(238);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(225);
				match(T__5);
				setState(226);
				match(JAVA_ID);
				setState(227);
				match(T__1);
				setState(228);
				interface_body(0);
				setState(229);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(231);
				match(T__5);
				setState(232);
				match(JAVA_ID);
				setState(233);
				type_parameters();
				setState(234);
				match(T__1);
				setState(235);
				interface_body(0);
				setState(236);
				match(T__2);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Implements_listContext extends ParserRuleContext {
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public Type_listContext type_list() {
			return getRuleContext(Type_listContext.class,0);
		}
		public Implements_listContext implements_list() {
			return getRuleContext(Implements_listContext.class,0);
		}
		public Implements_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_implements_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterImplements_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitImplements_list(this);
		}
	}

	public final Implements_listContext implements_list() throws RecognitionException {
		return implements_list(0);
	}

	private Implements_listContext implements_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Implements_listContext _localctx = new Implements_listContext(_ctx, _parentState);
		Implements_listContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_implements_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(241);
				match(T__6);
				setState(242);
				match(JAVA_ID);
				}
				break;
			case 2:
				{
				setState(243);
				match(T__6);
				setState(244);
				match(JAVA_ID);
				setState(245);
				type_list();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(257);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(255);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new Implements_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_implements_list);
						setState(248);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(249);
						match(T__7);
						setState(250);
						match(JAVA_ID);
						}
						break;
					case 2:
						{
						_localctx = new Implements_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_implements_list);
						setState(251);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(252);
						match(T__7);
						setState(253);
						match(JAVA_ID);
						setState(254);
						type_list();
						}
						break;
					}
					} 
				}
				setState(259);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Type_parametersContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(KantParser.LT, 0); }
		public List<Type_parameterContext> type_parameter() {
			return getRuleContexts(Type_parameterContext.class);
		}
		public Type_parameterContext type_parameter(int i) {
			return getRuleContext(Type_parameterContext.class,i);
		}
		public TerminalNode GT() { return getToken(KantParser.GT, 0); }
		public Type_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_parameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterType_parameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitType_parameters(this);
		}
	}

	public final Type_parametersContext type_parameters() throws RecognitionException {
		Type_parametersContext _localctx = new Type_parametersContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_type_parameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			match(LT);
			setState(261);
			type_parameter();
			setState(266);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(262);
				match(T__7);
				setState(263);
				type_parameter();
				}
				}
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(269);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Type_listContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(KantParser.LT, 0); }
		public List<Type_nameContext> type_name() {
			return getRuleContexts(Type_nameContext.class);
		}
		public Type_nameContext type_name(int i) {
			return getRuleContext(Type_nameContext.class,i);
		}
		public TerminalNode GT() { return getToken(KantParser.GT, 0); }
		public Type_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterType_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitType_list(this);
		}
	}

	public final Type_listContext type_list() throws RecognitionException {
		Type_listContext _localctx = new Type_listContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_type_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			match(LT);
			setState(272);
			type_name();
			setState(277);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(273);
				match(T__7);
				setState(274);
				type_name();
				}
				}
				setState(279);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(280);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Type_parameterContext extends ParserRuleContext {
		public List<Type_nameContext> type_name() {
			return getRuleContexts(Type_nameContext.class);
		}
		public Type_nameContext type_name(int i) {
			return getRuleContext(Type_nameContext.class,i);
		}
		public Type_parameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterType_parameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitType_parameter(this);
		}
	}

	public final Type_parameterContext type_parameter() throws RecognitionException {
		Type_parameterContext _localctx = new Type_parameterContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_type_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			type_name();
			setState(285);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(283);
				match(T__4);
				setState(284);
				type_name();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Context_bodyContext extends ParserRuleContext {
		public Context_body_elementContext context_body_element() {
			return getRuleContext(Context_body_elementContext.class,0);
		}
		public Context_bodyContext context_body() {
			return getRuleContext(Context_bodyContext.class,0);
		}
		public Context_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_context_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterContext_body(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitContext_body(this);
		}
	}

	public final Context_bodyContext context_body() throws RecognitionException {
		return context_body(0);
	}

	private Context_bodyContext context_body(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Context_bodyContext _localctx = new Context_bodyContext(_ctx, _parentState);
		Context_bodyContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_context_body, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(288);
				context_body_element();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(296);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Context_bodyContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_context_body);
					setState(292);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(293);
					context_body_element();
					}
					} 
				}
				setState(298);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Context_body_elementContext extends ParserRuleContext {
		public Method_declContext method_decl() {
			return getRuleContext(Method_declContext.class,0);
		}
		public Object_declContext object_decl() {
			return getRuleContext(Object_declContext.class,0);
		}
		public Role_declContext role_decl() {
			return getRuleContext(Role_declContext.class,0);
		}
		public Stageprop_declContext stageprop_decl() {
			return getRuleContext(Stageprop_declContext.class,0);
		}
		public Context_declarationContext context_declaration() {
			return getRuleContext(Context_declarationContext.class,0);
		}
		public Context_body_elementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_context_body_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterContext_body_element(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitContext_body_element(this);
		}
	}

	public final Context_body_elementContext context_body_element() throws RecognitionException {
		Context_body_elementContext _localctx = new Context_body_elementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_context_body_element);
		try {
			setState(304);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(299);
				method_decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(300);
				object_decl();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(301);
				role_decl();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(302);
				stageprop_decl();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(303);
				context_declaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Role_declContext extends ParserRuleContext {
		public Role_vec_modifierContext role_vec_modifier() {
			return getRuleContext(Role_vec_modifierContext.class,0);
		}
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public Role_bodyContext role_body() {
			return getRuleContext(Role_bodyContext.class,0);
		}
		public TerminalNode REQUIRES() { return getToken(KantParser.REQUIRES, 0); }
		public Self_methodsContext self_methods() {
			return getRuleContext(Self_methodsContext.class,0);
		}
		public Access_qualifierContext access_qualifier() {
			return getRuleContext(Access_qualifierContext.class,0);
		}
		public Role_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_role_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterRole_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitRole_decl(this);
		}
	}

	public final Role_declContext role_decl() throws RecognitionException {
		Role_declContext _localctx = new Role_declContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_role_decl);
		try {
			setState(382);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(306);
				match(T__8);
				setState(307);
				role_vec_modifier();
				setState(308);
				match(JAVA_ID);
				setState(309);
				match(T__1);
				setState(310);
				role_body(0);
				setState(311);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(313);
				match(T__8);
				setState(314);
				role_vec_modifier();
				setState(315);
				match(JAVA_ID);
				setState(316);
				match(T__1);
				setState(317);
				role_body(0);
				setState(318);
				match(T__2);
				setState(319);
				match(REQUIRES);
				setState(320);
				match(T__1);
				setState(321);
				self_methods(0);
				setState(322);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(324);
				access_qualifier();
				setState(325);
				match(T__8);
				setState(326);
				role_vec_modifier();
				setState(327);
				match(JAVA_ID);
				setState(328);
				match(T__1);
				setState(329);
				role_body(0);
				setState(330);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(332);
				access_qualifier();
				setState(333);
				match(T__8);
				setState(334);
				role_vec_modifier();
				setState(335);
				match(JAVA_ID);
				setState(336);
				match(T__1);
				setState(337);
				role_body(0);
				setState(338);
				match(T__2);
				setState(339);
				match(REQUIRES);
				setState(340);
				match(T__1);
				setState(341);
				self_methods(0);
				setState(342);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(344);
				match(T__8);
				setState(345);
				role_vec_modifier();
				setState(346);
				match(JAVA_ID);
				setState(347);
				match(T__1);
				setState(348);
				role_body(0);
				setState(349);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(351);
				match(T__8);
				setState(352);
				role_vec_modifier();
				setState(353);
				match(JAVA_ID);
				setState(354);
				match(T__1);
				setState(355);
				role_body(0);
				setState(356);
				match(T__2);
				setState(357);
				match(REQUIRES);
				setState(358);
				match(T__1);
				setState(359);
				self_methods(0);
				setState(360);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(362);
				access_qualifier();
				setState(363);
				match(T__8);
				setState(364);
				role_vec_modifier();
				setState(365);
				match(JAVA_ID);
				setState(366);
				match(T__1);
				setState(367);
				role_body(0);
				setState(368);
				match(T__2);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(370);
				access_qualifier();
				setState(371);
				match(T__8);
				setState(372);
				role_vec_modifier();
				setState(373);
				match(JAVA_ID);
				setState(374);
				match(T__1);
				setState(375);
				role_body(0);
				setState(376);
				match(T__2);
				setState(377);
				match(REQUIRES);
				setState(378);
				match(T__1);
				setState(379);
				self_methods(0);
				setState(380);
				match(T__2);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Role_vec_modifierContext extends ParserRuleContext {
		public Role_vec_modifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_role_vec_modifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterRole_vec_modifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitRole_vec_modifier(this);
		}
	}

	public final Role_vec_modifierContext role_vec_modifier() throws RecognitionException {
		Role_vec_modifierContext _localctx = new Role_vec_modifierContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_role_vec_modifier);
		try {
			setState(387);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(384);
				match(T__9);
				setState(385);
				match(T__10);
				}
				break;
			case JAVA_ID:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Role_bodyContext extends ParserRuleContext {
		public Method_declContext method_decl() {
			return getRuleContext(Method_declContext.class,0);
		}
		public Object_declContext object_decl() {
			return getRuleContext(Object_declContext.class,0);
		}
		public Method_signatureContext method_signature() {
			return getRuleContext(Method_signatureContext.class,0);
		}
		public Role_bodyContext role_body() {
			return getRuleContext(Role_bodyContext.class,0);
		}
		public Role_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_role_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterRole_body(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitRole_body(this);
		}
	}

	public final Role_bodyContext role_body() throws RecognitionException {
		return role_body(0);
	}

	private Role_bodyContext role_body(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Role_bodyContext _localctx = new Role_bodyContext(_ctx, _parentState);
		Role_bodyContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_role_body, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(400);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(390);
				method_decl();
				}
				break;
			case 2:
				{
				setState(391);
				object_decl();
				}
				break;
			case 3:
				{
				setState(392);
				method_signature();
				setState(396);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(393);
						match(T__11);
						}
						} 
					}
					setState(398);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				}
				}
				break;
			case 4:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(416);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(414);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
					case 1:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(402);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(403);
						method_decl();
						}
						break;
					case 2:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(404);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(405);
						object_decl();
						}
						break;
					case 3:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(406);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(407);
						method_signature();
						setState(411);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(408);
								match(T__11);
								}
								} 
							}
							setState(413);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
						}
						}
						break;
					}
					} 
				}
				setState(418);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Self_methodsContext extends ParserRuleContext {
		public Method_signatureContext method_signature() {
			return getRuleContext(Method_signatureContext.class,0);
		}
		public Self_methodsContext self_methods() {
			return getRuleContext(Self_methodsContext.class,0);
		}
		public Self_methodsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_self_methods; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterSelf_methods(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitSelf_methods(this);
		}
	}

	public final Self_methodsContext self_methods() throws RecognitionException {
		return self_methods(0);
	}

	private Self_methodsContext self_methods(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Self_methodsContext _localctx = new Self_methodsContext(_ctx, _parentState);
		Self_methodsContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_self_methods, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(420);
			method_signature();
			}
			_ctx.stop = _input.LT(-1);
			setState(429);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(427);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
					case 1:
						{
						_localctx = new Self_methodsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_self_methods);
						setState(422);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(423);
						match(T__11);
						setState(424);
						method_signature();
						}
						break;
					case 2:
						{
						_localctx = new Self_methodsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_self_methods);
						setState(425);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(426);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(431);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Stageprop_declContext extends ParserRuleContext {
		public Role_vec_modifierContext role_vec_modifier() {
			return getRuleContext(Role_vec_modifierContext.class,0);
		}
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public Stageprop_bodyContext stageprop_body() {
			return getRuleContext(Stageprop_bodyContext.class,0);
		}
		public TerminalNode REQUIRES() { return getToken(KantParser.REQUIRES, 0); }
		public Self_methodsContext self_methods() {
			return getRuleContext(Self_methodsContext.class,0);
		}
		public Access_qualifierContext access_qualifier() {
			return getRuleContext(Access_qualifierContext.class,0);
		}
		public Stageprop_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stageprop_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterStageprop_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitStageprop_decl(this);
		}
	}

	public final Stageprop_declContext stageprop_decl() throws RecognitionException {
		Stageprop_declContext _localctx = new Stageprop_declContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_stageprop_decl);
		try {
			setState(504);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(432);
				match(T__12);
				setState(433);
				role_vec_modifier();
				setState(434);
				match(JAVA_ID);
				setState(435);
				match(T__1);
				setState(436);
				stageprop_body(0);
				setState(437);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(439);
				match(T__12);
				setState(440);
				role_vec_modifier();
				setState(441);
				match(JAVA_ID);
				setState(442);
				match(T__1);
				setState(443);
				stageprop_body(0);
				setState(444);
				match(T__2);
				setState(445);
				match(REQUIRES);
				setState(446);
				match(T__1);
				setState(447);
				self_methods(0);
				setState(448);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(450);
				access_qualifier();
				setState(451);
				match(T__12);
				setState(452);
				role_vec_modifier();
				setState(453);
				match(JAVA_ID);
				setState(454);
				match(T__1);
				setState(455);
				stageprop_body(0);
				setState(456);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(458);
				access_qualifier();
				setState(459);
				match(T__12);
				setState(460);
				role_vec_modifier();
				setState(461);
				match(JAVA_ID);
				setState(462);
				match(T__1);
				setState(463);
				stageprop_body(0);
				setState(464);
				match(T__2);
				setState(465);
				match(REQUIRES);
				setState(466);
				match(T__1);
				setState(467);
				self_methods(0);
				setState(468);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(470);
				match(T__12);
				setState(471);
				role_vec_modifier();
				setState(472);
				match(JAVA_ID);
				setState(473);
				match(T__1);
				setState(474);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(476);
				match(T__12);
				setState(477);
				role_vec_modifier();
				setState(478);
				match(JAVA_ID);
				setState(479);
				match(T__1);
				setState(480);
				match(T__2);
				setState(481);
				match(REQUIRES);
				setState(482);
				match(T__1);
				setState(483);
				self_methods(0);
				setState(484);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(486);
				access_qualifier();
				setState(487);
				match(T__12);
				setState(488);
				role_vec_modifier();
				setState(489);
				match(JAVA_ID);
				setState(490);
				match(T__1);
				setState(491);
				match(T__2);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(493);
				access_qualifier();
				setState(494);
				match(T__12);
				setState(495);
				role_vec_modifier();
				setState(496);
				match(JAVA_ID);
				setState(497);
				match(T__1);
				setState(498);
				match(T__2);
				setState(499);
				match(REQUIRES);
				setState(500);
				match(T__1);
				setState(501);
				self_methods(0);
				setState(502);
				match(T__2);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Stageprop_bodyContext extends ParserRuleContext {
		public Method_declContext method_decl() {
			return getRuleContext(Method_declContext.class,0);
		}
		public Object_declContext object_decl() {
			return getRuleContext(Object_declContext.class,0);
		}
		public Method_signatureContext method_signature() {
			return getRuleContext(Method_signatureContext.class,0);
		}
		public TerminalNode UNUSED() { return getToken(KantParser.UNUSED, 0); }
		public Stageprop_bodyContext stageprop_body() {
			return getRuleContext(Stageprop_bodyContext.class,0);
		}
		public Stageprop_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stageprop_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterStageprop_body(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitStageprop_body(this);
		}
	}

	public final Stageprop_bodyContext stageprop_body() throws RecognitionException {
		return stageprop_body(0);
	}

	private Stageprop_bodyContext stageprop_body(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Stageprop_bodyContext _localctx = new Stageprop_bodyContext(_ctx, _parentState);
		Stageprop_bodyContext _prevctx = _localctx;
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_stageprop_body, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(524);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(507);
				method_decl();
				}
				break;
			case 2:
				{
				setState(508);
				object_decl();
				}
				break;
			case 3:
				{
				setState(509);
				method_signature();
				setState(510);
				match(UNUSED);
				setState(514);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(511);
						match(T__11);
						}
						} 
					}
					setState(516);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
				}
				}
				break;
			case 4:
				{
				setState(517);
				method_signature();
				setState(521);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(518);
						match(T__11);
						}
						} 
					}
					setState(523);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				}
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(549);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(547);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
					case 1:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(526);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(527);
						method_decl();
						}
						break;
					case 2:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(528);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(529);
						object_decl();
						}
						break;
					case 3:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(530);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(531);
						method_signature();
						setState(532);
						match(UNUSED);
						setState(536);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(533);
								match(T__11);
								}
								} 
							}
							setState(538);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
						}
						}
						break;
					case 4:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(539);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(540);
						method_signature();
						setState(544);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(541);
								match(T__11);
								}
								} 
							}
							setState(546);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
						}
						}
						break;
					}
					} 
				}
				setState(551);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Class_bodyContext extends ParserRuleContext {
		public Class_body_elementContext class_body_element() {
			return getRuleContext(Class_body_elementContext.class,0);
		}
		public Class_bodyContext class_body() {
			return getRuleContext(Class_bodyContext.class,0);
		}
		public Class_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterClass_body(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitClass_body(this);
		}
	}

	public final Class_bodyContext class_body() throws RecognitionException {
		return class_body(0);
	}

	private Class_bodyContext class_body(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Class_bodyContext _localctx = new Class_bodyContext(_ctx, _parentState);
		Class_bodyContext _prevctx = _localctx;
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_class_body, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(555);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(553);
				class_body_element();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(561);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Class_bodyContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_class_body);
					setState(557);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(558);
					class_body_element();
					}
					} 
				}
				setState(563);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Class_body_elementContext extends ParserRuleContext {
		public Method_declContext method_decl() {
			return getRuleContext(Method_declContext.class,0);
		}
		public Object_declContext object_decl() {
			return getRuleContext(Object_declContext.class,0);
		}
		public Type_declarationContext type_declaration() {
			return getRuleContext(Type_declarationContext.class,0);
		}
		public Class_body_elementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_body_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterClass_body_element(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitClass_body_element(this);
		}
	}

	public final Class_body_elementContext class_body_element() throws RecognitionException {
		Class_body_elementContext _localctx = new Class_body_elementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_class_body_element);
		try {
			setState(567);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(564);
				method_decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(565);
				object_decl();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(566);
				type_declaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Interface_bodyContext extends ParserRuleContext {
		public Method_signatureContext method_signature() {
			return getRuleContext(Method_signatureContext.class,0);
		}
		public Interface_bodyContext interface_body() {
			return getRuleContext(Interface_bodyContext.class,0);
		}
		public Interface_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interface_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterInterface_body(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitInterface_body(this);
		}
	}

	public final Interface_bodyContext interface_body() throws RecognitionException {
		return interface_body(0);
	}

	private Interface_bodyContext interface_body(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Interface_bodyContext _localctx = new Interface_bodyContext(_ctx, _parentState);
		Interface_bodyContext _prevctx = _localctx;
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_interface_body, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(570);
			method_signature();
			}
			_ctx.stop = _input.LT(-1);
			setState(579);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(577);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
					case 1:
						{
						_localctx = new Interface_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_interface_body);
						setState(572);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(573);
						match(T__11);
						setState(574);
						method_signature();
						}
						break;
					case 2:
						{
						_localctx = new Interface_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_interface_body);
						setState(575);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(576);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(581);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Method_declContext extends ParserRuleContext {
		public Method_decl_hookContext method_decl_hook() {
			return getRuleContext(Method_decl_hookContext.class,0);
		}
		public Type_and_expr_and_decl_listContext type_and_expr_and_decl_list() {
			return getRuleContext(Type_and_expr_and_decl_listContext.class,0);
		}
		public Method_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterMethod_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitMethod_decl(this);
		}
	}

	public final Method_declContext method_decl() throws RecognitionException {
		Method_declContext _localctx = new Method_declContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_method_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(582);
			method_decl_hook();
			setState(583);
			match(T__1);
			setState(584);
			type_and_expr_and_decl_list();
			setState(585);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Method_decl_hookContext extends ParserRuleContext {
		public Method_signatureContext method_signature() {
			return getRuleContext(Method_signatureContext.class,0);
		}
		public Method_decl_hookContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_decl_hook; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterMethod_decl_hook(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitMethod_decl_hook(this);
		}
	}

	public final Method_decl_hookContext method_decl_hook() throws RecognitionException {
		Method_decl_hookContext _localctx = new Method_decl_hookContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_method_decl_hook);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(587);
			method_signature();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Method_signatureContext extends ParserRuleContext {
		public Access_qualifierContext access_qualifier() {
			return getRuleContext(Access_qualifierContext.class,0);
		}
		public Return_typeContext return_type() {
			return getRuleContext(Return_typeContext.class,0);
		}
		public Method_nameContext method_name() {
			return getRuleContext(Method_nameContext.class,0);
		}
		public Param_listContext param_list() {
			return getRuleContext(Param_listContext.class,0);
		}
		public List<TerminalNode> CONST() { return getTokens(KantParser.CONST); }
		public TerminalNode CONST(int i) {
			return getToken(KantParser.CONST, i);
		}
		public Method_signatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_signature; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterMethod_signature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitMethod_signature(this);
		}
	}

	public final Method_signatureContext method_signature() throws RecognitionException {
		Method_signatureContext _localctx = new Method_signatureContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_method_signature);
		try {
			int _alt;
			setState(621);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(589);
				access_qualifier();
				setState(590);
				return_type();
				setState(591);
				method_name();
				setState(592);
				match(T__13);
				setState(593);
				param_list(0);
				setState(594);
				match(T__14);
				setState(598);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(595);
						match(CONST);
						}
						} 
					}
					setState(600);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(601);
				access_qualifier();
				setState(602);
				return_type();
				setState(603);
				method_name();
				setState(607);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(604);
						match(CONST);
						}
						} 
					}
					setState(609);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(610);
				access_qualifier();
				setState(611);
				method_name();
				setState(612);
				match(T__13);
				setState(613);
				param_list(0);
				setState(614);
				match(T__14);
				setState(618);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(615);
						match(CONST);
						}
						} 
					}
					setState(620);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expr_and_decl_listContext extends ParserRuleContext {
		public Object_declContext object_decl() {
			return getRuleContext(Object_declContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Expr_and_decl_listContext expr_and_decl_list() {
			return getRuleContext(Expr_and_decl_listContext.class,0);
		}
		public Expr_and_decl_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_and_decl_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterExpr_and_decl_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitExpr_and_decl_list(this);
		}
	}

	public final Expr_and_decl_listContext expr_and_decl_list() throws RecognitionException {
		return expr_and_decl_list(0);
	}

	private Expr_and_decl_listContext expr_and_decl_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Expr_and_decl_listContext _localctx = new Expr_and_decl_listContext(_ctx, _parentState);
		Expr_and_decl_listContext _prevctx = _localctx;
		int _startState = 50;
		enterRecursionRule(_localctx, 50, RULE_expr_and_decl_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(630);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				{
				setState(624);
				object_decl();
				}
				break;
			case 2:
				{
				setState(625);
				expr();
				setState(626);
				match(T__11);
				setState(627);
				object_decl();
				}
				break;
			case 3:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(640);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(638);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
					case 1:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(632);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(633);
						object_decl();
						}
						break;
					case 2:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(634);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(635);
						expr();
						}
						break;
					case 3:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(636);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(637);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(642);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Type_and_expr_and_decl_listContext extends ParserRuleContext {
		public Expr_and_decl_listContext expr_and_decl_list() {
			return getRuleContext(Expr_and_decl_listContext.class,0);
		}
		public Type_declarationContext type_declaration() {
			return getRuleContext(Type_declarationContext.class,0);
		}
		public Type_and_expr_and_decl_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_and_expr_and_decl_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterType_and_expr_and_decl_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitType_and_expr_and_decl_list(this);
		}
	}

	public final Type_and_expr_and_decl_listContext type_and_expr_and_decl_list() throws RecognitionException {
		Type_and_expr_and_decl_listContext _localctx = new Type_and_expr_and_decl_listContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_type_and_expr_and_decl_list);
		try {
			setState(650);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(643);
				expr_and_decl_list(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(644);
				expr_and_decl_list(0);
				setState(645);
				type_declaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(647);
				type_declaration();
				setState(648);
				expr_and_decl_list(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Return_typeContext extends ParserRuleContext {
		public Type_nameContext type_name() {
			return getRuleContext(Type_nameContext.class,0);
		}
		public Return_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterReturn_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitReturn_type(this);
		}
	}

	public final Return_typeContext return_type() throws RecognitionException {
		Return_typeContext _localctx = new Return_typeContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_return_type);
		try {
			setState(658);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(652);
				type_name();
				setState(653);
				match(T__9);
				setState(654);
				match(T__10);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(656);
				type_name();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Method_nameContext extends ParserRuleContext {
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public TerminalNode ABELIAN_SUMOP() { return getToken(KantParser.ABELIAN_SUMOP, 0); }
		public TerminalNode ABELIAN_MULOP() { return getToken(KantParser.ABELIAN_MULOP, 0); }
		public Method_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterMethod_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitMethod_name(this);
		}
	}

	public final Method_nameContext method_name() throws RecognitionException {
		Method_nameContext _localctx = new Method_nameContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_method_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(660);
			_la = _input.LA(1);
			if ( !(((((_la - 61)) & ~0x3f) == 0 && ((1L << (_la - 61)) & ((1L << (ABELIAN_SUMOP - 61)) | (1L << (ABELIAN_MULOP - 61)) | (1L << (JAVA_ID - 61)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Access_qualifierContext extends ParserRuleContext {
		public Access_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_access_qualifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterAccess_qualifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitAccess_qualifier(this);
		}
	}

	public final Access_qualifierContext access_qualifier() throws RecognitionException {
		Access_qualifierContext _localctx = new Access_qualifierContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_access_qualifier);
		try {
			setState(665);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(662);
				match(T__15);
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 2);
				{
				setState(663);
				match(T__16);
				}
				break;
			case T__8:
			case T__12:
			case T__17:
			case T__18:
			case T__19:
			case T__20:
			case ABELIAN_SUMOP:
			case ABELIAN_MULOP:
			case JAVA_ID:
				enterOuterAlt(_localctx, 3);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Object_declContext extends ParserRuleContext {
		public Access_qualifierContext access_qualifier() {
			return getRuleContext(Access_qualifierContext.class,0);
		}
		public Compound_type_nameContext compound_type_name() {
			return getRuleContext(Compound_type_nameContext.class,0);
		}
		public Identifier_listContext identifier_list() {
			return getRuleContext(Identifier_listContext.class,0);
		}
		public Object_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterObject_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitObject_decl(this);
		}
	}

	public final Object_declContext object_decl() throws RecognitionException {
		Object_declContext _localctx = new Object_declContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_object_decl);
		try {
			setState(683);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(667);
				access_qualifier();
				setState(668);
				compound_type_name();
				setState(669);
				identifier_list(0);
				setState(670);
				match(T__11);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(672);
				access_qualifier();
				setState(673);
				compound_type_name();
				setState(674);
				identifier_list(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(676);
				compound_type_name();
				setState(677);
				identifier_list(0);
				setState(678);
				match(T__11);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(680);
				compound_type_name();
				setState(681);
				identifier_list(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Trivial_object_declContext extends ParserRuleContext {
		public Compound_type_nameContext compound_type_name() {
			return getRuleContext(Compound_type_nameContext.class,0);
		}
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public Trivial_object_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trivial_object_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterTrivial_object_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitTrivial_object_decl(this);
		}
	}

	public final Trivial_object_declContext trivial_object_decl() throws RecognitionException {
		Trivial_object_declContext _localctx = new Trivial_object_declContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_trivial_object_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(685);
			compound_type_name();
			setState(686);
			match(JAVA_ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Compound_type_nameContext extends ParserRuleContext {
		public Type_nameContext type_name() {
			return getRuleContext(Type_nameContext.class,0);
		}
		public Compound_type_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compound_type_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterCompound_type_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitCompound_type_name(this);
		}
	}

	public final Compound_type_nameContext compound_type_name() throws RecognitionException {
		Compound_type_nameContext _localctx = new Compound_type_nameContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_compound_type_name);
		try {
			setState(693);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(688);
				type_name();
				setState(689);
				match(T__9);
				setState(690);
				match(T__10);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(692);
				type_name();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Type_nameContext extends ParserRuleContext {
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public Type_listContext type_list() {
			return getRuleContext(Type_listContext.class,0);
		}
		public Builtin_type_nameContext builtin_type_name() {
			return getRuleContext(Builtin_type_nameContext.class,0);
		}
		public Type_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterType_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitType_name(this);
		}
	}

	public final Type_nameContext type_name() throws RecognitionException {
		Type_nameContext _localctx = new Type_nameContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_type_name);
		try {
			setState(699);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,55,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(695);
				match(JAVA_ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(696);
				match(JAVA_ID);
				setState(697);
				type_list();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(698);
				builtin_type_name();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Builtin_type_nameContext extends ParserRuleContext {
		public Builtin_type_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builtin_type_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterBuiltin_type_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitBuiltin_type_name(this);
		}
	}

	public final Builtin_type_nameContext builtin_type_name() throws RecognitionException {
		Builtin_type_nameContext _localctx = new Builtin_type_nameContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_builtin_type_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(701);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Identifier_listContext extends ParserRuleContext {
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public TerminalNode ASSIGN() { return getToken(KantParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Identifier_listContext identifier_list() {
			return getRuleContext(Identifier_listContext.class,0);
		}
		public Identifier_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterIdentifier_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitIdentifier_list(this);
		}
	}

	public final Identifier_listContext identifier_list() throws RecognitionException {
		return identifier_list(0);
	}

	private Identifier_listContext identifier_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Identifier_listContext _localctx = new Identifier_listContext(_ctx, _parentState);
		Identifier_listContext _prevctx = _localctx;
		int _startState = 70;
		enterRecursionRule(_localctx, 70, RULE_identifier_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(708);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				{
				setState(704);
				match(JAVA_ID);
				}
				break;
			case 2:
				{
				setState(705);
				match(JAVA_ID);
				setState(706);
				match(ASSIGN);
				setState(707);
				expr();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(720);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(718);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
					case 1:
						{
						_localctx = new Identifier_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identifier_list);
						setState(710);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(711);
						match(T__7);
						setState(712);
						match(JAVA_ID);
						}
						break;
					case 2:
						{
						_localctx = new Identifier_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identifier_list);
						setState(713);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(714);
						match(T__7);
						setState(715);
						match(JAVA_ID);
						setState(716);
						match(ASSIGN);
						setState(717);
						expr();
						}
						break;
					}
					} 
				}
				setState(722);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Param_listContext extends ParserRuleContext {
		public Param_declContext param_decl() {
			return getRuleContext(Param_declContext.class,0);
		}
		public Param_listContext param_list() {
			return getRuleContext(Param_listContext.class,0);
		}
		public TerminalNode ELLIPSIS() { return getToken(KantParser.ELLIPSIS, 0); }
		public Param_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterParam_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitParam_list(this);
		}
	}

	public final Param_listContext param_list() throws RecognitionException {
		return param_list(0);
	}

	private Param_listContext param_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Param_listContext _localctx = new Param_listContext(_ctx, _parentState);
		Param_listContext _prevctx = _localctx;
		int _startState = 72;
		enterRecursionRule(_localctx, 72, RULE_param_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(726);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
			case 1:
				{
				setState(724);
				param_decl();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(738);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(736);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
					case 1:
						{
						_localctx = new Param_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_param_list);
						setState(728);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(729);
						match(T__7);
						setState(730);
						param_decl();
						}
						break;
					case 2:
						{
						_localctx = new Param_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_param_list);
						setState(731);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(732);
						match(ELLIPSIS);
						}
						break;
					case 3:
						{
						_localctx = new Param_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_param_list);
						setState(733);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(734);
						match(T__7);
						setState(735);
						match(ELLIPSIS);
						}
						break;
					}
					} 
				}
				setState(740);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Param_declContext extends ParserRuleContext {
		public Compound_type_nameContext compound_type_name() {
			return getRuleContext(Compound_type_nameContext.class,0);
		}
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public Param_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterParam_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitParam_decl(this);
		}
	}

	public final Param_declContext param_decl() throws RecognitionException {
		Param_declContext _localctx = new Param_declContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_param_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(741);
			compound_type_name();
			setState(742);
			match(JAVA_ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public Abelian_exprContext abelian_expr() {
			return getRuleContext(Abelian_exprContext.class,0);
		}
		public Boolean_exprContext boolean_expr() {
			return getRuleContext(Boolean_exprContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public If_exprContext if_expr() {
			return getRuleContext(If_exprContext.class,0);
		}
		public For_exprContext for_expr() {
			return getRuleContext(For_exprContext.class,0);
		}
		public While_exprContext while_expr() {
			return getRuleContext(While_exprContext.class,0);
		}
		public Do_while_exprContext do_while_expr() {
			return getRuleContext(Do_while_exprContext.class,0);
		}
		public Switch_exprContext switch_expr() {
			return getRuleContext(Switch_exprContext.class,0);
		}
		public TerminalNode BREAK() { return getToken(KantParser.BREAK, 0); }
		public TerminalNode CONTINUE() { return getToken(KantParser.CONTINUE, 0); }
		public TerminalNode RETURN() { return getToken(KantParser.RETURN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_expr);
		try {
			setState(757);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(744);
				abelian_expr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(745);
				boolean_expr(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(746);
				block();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(747);
				if_expr();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(748);
				for_expr();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(749);
				while_expr();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(750);
				do_while_expr();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(751);
				switch_expr();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(752);
				match(BREAK);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(753);
				match(CONTINUE);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(754);
				match(RETURN);
				setState(755);
				expr();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(756);
				match(RETURN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Abelian_exprContext extends ParserRuleContext {
		public List<Abelian_productContext> abelian_product() {
			return getRuleContexts(Abelian_productContext.class);
		}
		public Abelian_productContext abelian_product(int i) {
			return getRuleContext(Abelian_productContext.class,i);
		}
		public List<TerminalNode> ABELIAN_SUMOP() { return getTokens(KantParser.ABELIAN_SUMOP); }
		public TerminalNode ABELIAN_SUMOP(int i) {
			return getToken(KantParser.ABELIAN_SUMOP, i);
		}
		public If_exprContext if_expr() {
			return getRuleContext(If_exprContext.class,0);
		}
		public Abelian_exprContext abelian_expr() {
			return getRuleContext(Abelian_exprContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(KantParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Abelian_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_abelian_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterAbelian_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitAbelian_expr(this);
		}
	}

	public final Abelian_exprContext abelian_expr() throws RecognitionException {
		return abelian_expr(0);
	}

	private Abelian_exprContext abelian_expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Abelian_exprContext _localctx = new Abelian_exprContext(_ctx, _parentState);
		Abelian_exprContext _prevctx = _localctx;
		int _startState = 78;
		enterRecursionRule(_localctx, 78, RULE_abelian_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(769);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__13:
			case T__17:
			case T__18:
			case T__19:
			case T__20:
			case STRING:
			case INTEGER:
			case FLOAT:
			case BOOLEAN:
			case NEW:
			case NULL:
			case ABELIAN_SUMOP:
			case ABELIAN_MULOP:
			case ABELIAN_INCREMENT_OP:
			case JAVA_ID:
				{
				setState(760);
				abelian_product(0);
				setState(765);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(761);
						match(ABELIAN_SUMOP);
						setState(762);
						abelian_product(0);
						}
						} 
					}
					setState(767);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
				}
				}
				break;
			case T__34:
				{
				setState(768);
				if_expr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(776);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,65,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Abelian_exprContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_abelian_expr);
					setState(771);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(772);
					match(ASSIGN);
					setState(773);
					expr();
					}
					} 
				}
				setState(778);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,65,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Abelian_productContext extends ParserRuleContext {
		public List<Abelian_unary_opContext> abelian_unary_op() {
			return getRuleContexts(Abelian_unary_opContext.class);
		}
		public Abelian_unary_opContext abelian_unary_op(int i) {
			return getRuleContext(Abelian_unary_opContext.class,i);
		}
		public List<TerminalNode> ABELIAN_MULOP() { return getTokens(KantParser.ABELIAN_MULOP); }
		public TerminalNode ABELIAN_MULOP(int i) {
			return getToken(KantParser.ABELIAN_MULOP, i);
		}
		public Abelian_productContext abelian_product() {
			return getRuleContext(Abelian_productContext.class,0);
		}
		public TerminalNode POW() { return getToken(KantParser.POW, 0); }
		public Abelian_atomContext abelian_atom() {
			return getRuleContext(Abelian_atomContext.class,0);
		}
		public Abelian_productContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_abelian_product; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterAbelian_product(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitAbelian_product(this);
		}
	}

	public final Abelian_productContext abelian_product() throws RecognitionException {
		return abelian_product(0);
	}

	private Abelian_productContext abelian_product(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Abelian_productContext _localctx = new Abelian_productContext(_ctx, _parentState);
		Abelian_productContext _prevctx = _localctx;
		int _startState = 80;
		enterRecursionRule(_localctx, 80, RULE_abelian_product, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(780);
			abelian_unary_op();
			setState(785);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(781);
					match(ABELIAN_MULOP);
					setState(782);
					abelian_unary_op();
					}
					} 
				}
				setState(787);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
			}
			}
			_ctx.stop = _input.LT(-1);
			setState(793);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,67,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Abelian_productContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_abelian_product);
					setState(788);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(789);
					match(POW);
					setState(790);
					abelian_atom(0);
					}
					} 
				}
				setState(795);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,67,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Abelian_unary_opContext extends ParserRuleContext {
		public TerminalNode ABELIAN_SUMOP() { return getToken(KantParser.ABELIAN_SUMOP, 0); }
		public Abelian_atomContext abelian_atom() {
			return getRuleContext(Abelian_atomContext.class,0);
		}
		public Abelian_unary_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_abelian_unary_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterAbelian_unary_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitAbelian_unary_op(this);
		}
	}

	public final Abelian_unary_opContext abelian_unary_op() throws RecognitionException {
		Abelian_unary_opContext _localctx = new Abelian_unary_opContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_abelian_unary_op);
		try {
			setState(799);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,68,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(796);
				match(ABELIAN_SUMOP);
				setState(797);
				abelian_atom(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(798);
				abelian_atom(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Abelian_atomContext extends ParserRuleContext {
		public TerminalNode NEW() { return getToken(KantParser.NEW, 0); }
		public MessageContext message() {
			return getRuleContext(MessageContext.class,0);
		}
		public Type_nameContext type_name() {
			return getRuleContext(Type_nameContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public Type_listContext type_list() {
			return getRuleContext(Type_listContext.class,0);
		}
		public Argument_listContext argument_list() {
			return getRuleContext(Argument_listContext.class,0);
		}
		public Builtin_type_nameContext builtin_type_name() {
			return getRuleContext(Builtin_type_nameContext.class,0);
		}
		public Null_exprContext null_expr() {
			return getRuleContext(Null_exprContext.class,0);
		}
		public TerminalNode ABELIAN_INCREMENT_OP() { return getToken(KantParser.ABELIAN_INCREMENT_OP, 0); }
		public Abelian_atomContext abelian_atom() {
			return getRuleContext(Abelian_atomContext.class,0);
		}
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public Abelian_exprContext abelian_expr() {
			return getRuleContext(Abelian_exprContext.class,0);
		}
		public TerminalNode CLONE() { return getToken(KantParser.CLONE, 0); }
		public Abelian_atomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_abelian_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterAbelian_atom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitAbelian_atom(this);
		}
	}

	public final Abelian_atomContext abelian_atom() throws RecognitionException {
		return abelian_atom(0);
	}

	private Abelian_atomContext abelian_atom(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Abelian_atomContext _localctx = new Abelian_atomContext(_ctx, _parentState);
		Abelian_atomContext _prevctx = _localctx;
		int _startState = 84;
		enterRecursionRule(_localctx, 84, RULE_abelian_atom, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(842);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,69,_ctx) ) {
			case 1:
				{
				setState(802);
				match(NEW);
				setState(803);
				message();
				}
				break;
			case 2:
				{
				setState(804);
				match(NEW);
				setState(805);
				type_name();
				setState(806);
				match(T__9);
				setState(807);
				expr();
				setState(808);
				match(T__10);
				}
				break;
			case 3:
				{
				setState(810);
				match(NEW);
				setState(811);
				match(JAVA_ID);
				setState(812);
				type_list();
				setState(813);
				match(T__13);
				setState(814);
				argument_list(0);
				setState(815);
				match(T__14);
				}
				break;
			case 4:
				{
				setState(817);
				builtin_type_name();
				setState(818);
				match(T__21);
				setState(819);
				message();
				}
				break;
			case 5:
				{
				setState(821);
				null_expr();
				}
				break;
			case 6:
				{
				setState(822);
				message();
				}
				break;
			case 7:
				{
				setState(823);
				match(JAVA_ID);
				}
				break;
			case 8:
				{
				setState(824);
				match(ABELIAN_INCREMENT_OP);
				setState(825);
				abelian_atom(10);
				}
				break;
			case 9:
				{
				setState(826);
				constant();
				}
				break;
			case 10:
				{
				setState(827);
				match(T__13);
				setState(828);
				abelian_expr(0);
				setState(829);
				match(T__14);
				}
				break;
			case 11:
				{
				setState(831);
				match(ABELIAN_INCREMENT_OP);
				setState(832);
				expr();
				setState(833);
				match(T__9);
				setState(834);
				expr();
				setState(835);
				match(T__10);
				}
				break;
			case 12:
				{
				setState(837);
				match(ABELIAN_INCREMENT_OP);
				setState(838);
				expr();
				setState(839);
				match(T__21);
				setState(840);
				match(JAVA_ID);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(877);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,71,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(875);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
					case 1:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(844);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(845);
						match(T__21);
						setState(846);
						message();
						}
						break;
					case 2:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(847);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(848);
						match(T__21);
						setState(849);
						match(JAVA_ID);
						}
						break;
					case 3:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(850);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(851);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 4:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(852);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(853);
						match(T__9);
						setState(854);
						expr();
						setState(855);
						match(T__10);
						}
						break;
					case 5:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(857);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(858);
						match(T__9);
						setState(859);
						expr();
						setState(860);
						match(T__10);
						setState(861);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 6:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(863);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(864);
						match(T__21);
						setState(865);
						match(JAVA_ID);
						setState(866);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 7:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(867);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(868);
						match(T__21);
						setState(869);
						match(CLONE);
						}
						break;
					case 8:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(870);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(871);
						match(T__21);
						setState(872);
						match(CLONE);
						setState(873);
						match(T__13);
						setState(874);
						match(T__14);
						}
						break;
					}
					} 
				}
				setState(879);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,71,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Boolean_exprContext extends ParserRuleContext {
		public Token op1;
		public Token op2;
		public Token op;
		public List<Boolean_productContext> boolean_product() {
			return getRuleContexts(Boolean_productContext.class);
		}
		public Boolean_productContext boolean_product(int i) {
			return getRuleContext(Boolean_productContext.class,i);
		}
		public List<TerminalNode> BOOLEAN_SUMOP() { return getTokens(KantParser.BOOLEAN_SUMOP); }
		public TerminalNode BOOLEAN_SUMOP(int i) {
			return getToken(KantParser.BOOLEAN_SUMOP, i);
		}
		public If_exprContext if_expr() {
			return getRuleContext(If_exprContext.class,0);
		}
		public List<Abelian_exprContext> abelian_expr() {
			return getRuleContexts(Abelian_exprContext.class);
		}
		public Abelian_exprContext abelian_expr(int i) {
			return getRuleContext(Abelian_exprContext.class,i);
		}
		public TerminalNode GT() { return getToken(KantParser.GT, 0); }
		public TerminalNode LT() { return getToken(KantParser.LT, 0); }
		public List<Boolean_exprContext> boolean_expr() {
			return getRuleContexts(Boolean_exprContext.class);
		}
		public Boolean_exprContext boolean_expr(int i) {
			return getRuleContext(Boolean_exprContext.class,i);
		}
		public TerminalNode BOOLEAN_MULOP() { return getToken(KantParser.BOOLEAN_MULOP, 0); }
		public TerminalNode ASSIGN() { return getToken(KantParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Boolean_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterBoolean_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitBoolean_expr(this);
		}
	}

	public final Boolean_exprContext boolean_expr() throws RecognitionException {
		return boolean_expr(0);
	}

	private Boolean_exprContext boolean_expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Boolean_exprContext _localctx = new Boolean_exprContext(_ctx, _parentState);
		Boolean_exprContext _prevctx = _localctx;
		int _startState = 86;
		enterRecursionRule(_localctx, 86, RULE_boolean_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(900);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				{
				setState(881);
				boolean_product();
				setState(886);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,72,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(882);
						match(BOOLEAN_SUMOP);
						setState(883);
						boolean_product();
						}
						} 
					}
					setState(888);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,72,_ctx);
				}
				}
				break;
			case 2:
				{
				setState(889);
				if_expr();
				}
				break;
			case 3:
				{
				setState(890);
				abelian_expr(0);
				setState(891);
				((Boolean_exprContext)_localctx).op1 = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__24 || _la==T__25) ) {
					((Boolean_exprContext)_localctx).op1 = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(892);
				((Boolean_exprContext)_localctx).op2 = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__26 || _la==T__27) ) {
					((Boolean_exprContext)_localctx).op2 = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(893);
				abelian_expr(0);
				}
				break;
			case 4:
				{
				setState(895);
				abelian_expr(0);
				setState(896);
				((Boolean_exprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(((((_la - 25)) & ~0x3f) == 0 && ((1L << (_la - 25)) & ((1L << (T__24 - 25)) | (1L << (T__25 - 25)) | (1L << (T__28 - 25)) | (1L << (T__29 - 25)) | (1L << (T__30 - 25)) | (1L << (T__31 - 25)) | (1L << (T__32 - 25)) | (1L << (T__33 - 25)) | (1L << (LT - 25)) | (1L << (GT - 25)))) != 0)) ) {
					((Boolean_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(897);
				abelian_expr(0);
				}
				break;
			case 5:
				{
				setState(899);
				abelian_expr(0);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(910);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,75,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(908);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
					case 1:
						{
						_localctx = new Boolean_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolean_expr);
						setState(902);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(903);
						((Boolean_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << BOOLEAN_MULOP))) != 0)) ) {
							((Boolean_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(904);
						boolean_expr(6);
						}
						break;
					case 2:
						{
						_localctx = new Boolean_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolean_expr);
						setState(905);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(906);
						match(ASSIGN);
						setState(907);
						expr();
						}
						break;
					}
					} 
				}
				setState(912);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,75,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Boolean_productContext extends ParserRuleContext {
		public List<Boolean_unary_opContext> boolean_unary_op() {
			return getRuleContexts(Boolean_unary_opContext.class);
		}
		public Boolean_unary_opContext boolean_unary_op(int i) {
			return getRuleContext(Boolean_unary_opContext.class,i);
		}
		public List<TerminalNode> BOOLEAN_MULOP() { return getTokens(KantParser.BOOLEAN_MULOP); }
		public TerminalNode BOOLEAN_MULOP(int i) {
			return getToken(KantParser.BOOLEAN_MULOP, i);
		}
		public Boolean_productContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean_product; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterBoolean_product(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitBoolean_product(this);
		}
	}

	public final Boolean_productContext boolean_product() throws RecognitionException {
		Boolean_productContext _localctx = new Boolean_productContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_boolean_product);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(913);
			boolean_unary_op();
			setState(918);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(914);
					match(BOOLEAN_MULOP);
					setState(915);
					boolean_unary_op();
					}
					} 
				}
				setState(920);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Boolean_unary_opContext extends ParserRuleContext {
		public TerminalNode LOGICAL_NEGATION() { return getToken(KantParser.LOGICAL_NEGATION, 0); }
		public Boolean_exprContext boolean_expr() {
			return getRuleContext(Boolean_exprContext.class,0);
		}
		public Boolean_atomContext boolean_atom() {
			return getRuleContext(Boolean_atomContext.class,0);
		}
		public Boolean_unary_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean_unary_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterBoolean_unary_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitBoolean_unary_op(this);
		}
	}

	public final Boolean_unary_opContext boolean_unary_op() throws RecognitionException {
		Boolean_unary_opContext _localctx = new Boolean_unary_opContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_boolean_unary_op);
		try {
			setState(924);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LOGICAL_NEGATION:
				enterOuterAlt(_localctx, 1);
				{
				setState(921);
				match(LOGICAL_NEGATION);
				setState(922);
				boolean_expr(0);
				}
				break;
			case T__13:
			case STRING:
			case INTEGER:
			case FLOAT:
			case BOOLEAN:
			case NULL:
			case JAVA_ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(923);
				boolean_atom();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Boolean_atomContext extends ParserRuleContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public Null_exprContext null_expr() {
			return getRuleContext(Null_exprContext.class,0);
		}
		public Boolean_exprContext boolean_expr() {
			return getRuleContext(Boolean_exprContext.class,0);
		}
		public Boolean_atomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterBoolean_atom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitBoolean_atom(this);
		}
	}

	public final Boolean_atomContext boolean_atom() throws RecognitionException {
		Boolean_atomContext _localctx = new Boolean_atomContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_boolean_atom);
		try {
			setState(933);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING:
			case INTEGER:
			case FLOAT:
			case BOOLEAN:
				enterOuterAlt(_localctx, 1);
				{
				setState(926);
				constant();
				}
				break;
			case JAVA_ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(927);
				match(JAVA_ID);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 3);
				{
				setState(928);
				null_expr();
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 4);
				{
				setState(929);
				match(T__13);
				setState(930);
				boolean_expr(0);
				setState(931);
				match(T__14);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MessageContext extends ParserRuleContext {
		public Method_nameContext method_name() {
			return getRuleContext(Method_nameContext.class,0);
		}
		public Argument_listContext argument_list() {
			return getRuleContext(Argument_listContext.class,0);
		}
		public Type_nameContext type_name() {
			return getRuleContext(Type_nameContext.class,0);
		}
		public MessageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_message; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterMessage(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitMessage(this);
		}
	}

	public final MessageContext message() throws RecognitionException {
		MessageContext _localctx = new MessageContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_message);
		try {
			setState(945);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,79,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(935);
				method_name();
				setState(936);
				match(T__13);
				setState(937);
				argument_list(0);
				setState(938);
				match(T__14);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(940);
				type_name();
				setState(941);
				match(T__13);
				setState(942);
				argument_list(0);
				setState(943);
				match(T__14);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public Expr_and_decl_listContext expr_and_decl_list() {
			return getRuleContext(Expr_and_decl_listContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitBlock(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_block);
		try {
			setState(953);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,80,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(947);
				match(T__1);
				setState(948);
				expr_and_decl_list(0);
				setState(949);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(951);
				match(T__1);
				setState(952);
				match(T__2);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expr_or_nullContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Expr_or_nullContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_or_null; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterExpr_or_null(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitExpr_or_null(this);
		}
	}

	public final Expr_or_nullContext expr_or_null() throws RecognitionException {
		Expr_or_nullContext _localctx = new Expr_or_nullContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_expr_or_null);
		try {
			setState(957);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
			case T__13:
			case T__17:
			case T__18:
			case T__19:
			case T__20:
			case T__34:
			case T__36:
			case T__38:
			case T__39:
			case STRING:
			case INTEGER:
			case FLOAT:
			case BOOLEAN:
			case SWITCH:
			case BREAK:
			case CONTINUE:
			case RETURN:
			case NEW:
			case NULL:
			case ABELIAN_SUMOP:
			case ABELIAN_MULOP:
			case LOGICAL_NEGATION:
			case ABELIAN_INCREMENT_OP:
			case JAVA_ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(955);
				expr();
				}
				break;
			case EOF:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_exprContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public If_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterIf_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitIf_expr(this);
		}
	}

	public final If_exprContext if_expr() throws RecognitionException {
		If_exprContext _localctx = new If_exprContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_if_expr);
		try {
			setState(973);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,82,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(959);
				match(T__34);
				setState(960);
				match(T__13);
				setState(961);
				expr();
				setState(962);
				match(T__14);
				setState(963);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(965);
				match(T__34);
				setState(966);
				match(T__13);
				setState(967);
				expr();
				setState(968);
				match(T__14);
				setState(969);
				expr();
				setState(970);
				match(T__35);
				setState(971);
				expr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class For_exprContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Object_declContext object_decl() {
			return getRuleContext(Object_declContext.class,0);
		}
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public Trivial_object_declContext trivial_object_decl() {
			return getRuleContext(Trivial_object_declContext.class,0);
		}
		public For_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_for_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterFor_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitFor_expr(this);
		}
	}

	public final For_exprContext for_expr() throws RecognitionException {
		For_exprContext _localctx = new For_exprContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_for_expr);
		try {
			setState(1010);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,83,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(975);
				match(T__36);
				setState(976);
				match(T__13);
				setState(977);
				expr();
				setState(978);
				match(T__11);
				setState(979);
				expr();
				setState(980);
				match(T__11);
				setState(981);
				expr();
				setState(982);
				match(T__14);
				setState(983);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(985);
				match(T__36);
				setState(986);
				match(T__13);
				setState(987);
				object_decl();
				setState(988);
				expr();
				setState(989);
				match(T__11);
				setState(990);
				expr();
				setState(991);
				match(T__14);
				setState(992);
				expr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(994);
				match(T__36);
				setState(995);
				match(T__13);
				setState(996);
				match(JAVA_ID);
				setState(997);
				match(T__37);
				setState(998);
				expr();
				setState(999);
				match(T__14);
				setState(1000);
				expr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1002);
				match(T__36);
				setState(1003);
				match(T__13);
				setState(1004);
				trivial_object_decl();
				setState(1005);
				match(T__37);
				setState(1006);
				expr();
				setState(1007);
				match(T__14);
				setState(1008);
				expr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class While_exprContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public While_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterWhile_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitWhile_expr(this);
		}
	}

	public final While_exprContext while_expr() throws RecognitionException {
		While_exprContext _localctx = new While_exprContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_while_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1012);
			match(T__38);
			setState(1013);
			match(T__13);
			setState(1014);
			expr();
			setState(1015);
			match(T__14);
			setState(1016);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Do_while_exprContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Do_while_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_do_while_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterDo_while_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitDo_while_expr(this);
		}
	}

	public final Do_while_exprContext do_while_expr() throws RecognitionException {
		Do_while_exprContext _localctx = new Do_while_exprContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_do_while_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1018);
			match(T__39);
			setState(1019);
			expr();
			setState(1020);
			match(T__38);
			setState(1021);
			match(T__13);
			setState(1022);
			expr();
			setState(1023);
			match(T__14);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Switch_exprContext extends ParserRuleContext {
		public TerminalNode SWITCH() { return getToken(KantParser.SWITCH, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<Switch_bodyContext> switch_body() {
			return getRuleContexts(Switch_bodyContext.class);
		}
		public Switch_bodyContext switch_body(int i) {
			return getRuleContext(Switch_bodyContext.class,i);
		}
		public Switch_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switch_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterSwitch_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitSwitch_expr(this);
		}
	}

	public final Switch_exprContext switch_expr() throws RecognitionException {
		Switch_exprContext _localctx = new Switch_exprContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_switch_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1025);
			match(SWITCH);
			setState(1026);
			match(T__13);
			setState(1027);
			expr();
			setState(1028);
			match(T__14);
			setState(1029);
			match(T__1);
			setState(1033);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE || _la==DEFAULT) {
				{
				{
				setState(1030);
				switch_body();
				}
				}
				setState(1035);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1036);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Switch_bodyContext extends ParserRuleContext {
		public Expr_and_decl_listContext expr_and_decl_list() {
			return getRuleContext(Expr_and_decl_listContext.class,0);
		}
		public TerminalNode CASE() { return getToken(KantParser.CASE, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public TerminalNode DEFAULT() { return getToken(KantParser.DEFAULT, 0); }
		public Switch_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switch_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterSwitch_body(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitSwitch_body(this);
		}
	}

	public final Switch_bodyContext switch_body() throws RecognitionException {
		Switch_bodyContext _localctx = new Switch_bodyContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_switch_body);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1041);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CASE:
				{
				setState(1038);
				match(CASE);
				setState(1039);
				constant();
				}
				break;
			case DEFAULT:
				{
				setState(1040);
				match(DEFAULT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1043);
			match(T__37);
			setState(1044);
			expr_and_decl_list(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Null_exprContext extends ParserRuleContext {
		public TerminalNode NULL() { return getToken(KantParser.NULL, 0); }
		public Null_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_null_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterNull_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitNull_expr(this);
		}
	}

	public final Null_exprContext null_expr() throws RecognitionException {
		Null_exprContext _localctx = new Null_exprContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_null_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1046);
			match(NULL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(KantParser.STRING, 0); }
		public TerminalNode INTEGER() { return getToken(KantParser.INTEGER, 0); }
		public TerminalNode FLOAT() { return getToken(KantParser.FLOAT, 0); }
		public TerminalNode BOOLEAN() { return getToken(KantParser.BOOLEAN, 0); }
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitConstant(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1048);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << BOOLEAN))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Argument_listContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Argument_listContext argument_list() {
			return getRuleContext(Argument_listContext.class,0);
		}
		public Argument_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).enterArgument_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KantListener ) ((KantListener)listener).exitArgument_list(this);
		}
	}

	public final Argument_listContext argument_list() throws RecognitionException {
		return argument_list(0);
	}

	private Argument_listContext argument_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Argument_listContext _localctx = new Argument_listContext(_ctx, _parentState);
		Argument_listContext _prevctx = _localctx;
		int _startState = 116;
		enterRecursionRule(_localctx, 116, RULE_argument_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1053);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,86,_ctx) ) {
			case 1:
				{
				setState(1051);
				expr();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(1060);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,87,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Argument_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_argument_list);
					setState(1055);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(1056);
					match(T__7);
					setState(1057);
					expr();
					}
					} 
				}
				setState(1062);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,87,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return type_declaration_list_sempred((Type_declaration_listContext)_localctx, predIndex);
		case 7:
			return implements_list_sempred((Implements_listContext)_localctx, predIndex);
		case 11:
			return context_body_sempred((Context_bodyContext)_localctx, predIndex);
		case 15:
			return role_body_sempred((Role_bodyContext)_localctx, predIndex);
		case 16:
			return self_methods_sempred((Self_methodsContext)_localctx, predIndex);
		case 18:
			return stageprop_body_sempred((Stageprop_bodyContext)_localctx, predIndex);
		case 19:
			return class_body_sempred((Class_bodyContext)_localctx, predIndex);
		case 21:
			return interface_body_sempred((Interface_bodyContext)_localctx, predIndex);
		case 25:
			return expr_and_decl_list_sempred((Expr_and_decl_listContext)_localctx, predIndex);
		case 35:
			return identifier_list_sempred((Identifier_listContext)_localctx, predIndex);
		case 36:
			return param_list_sempred((Param_listContext)_localctx, predIndex);
		case 39:
			return abelian_expr_sempred((Abelian_exprContext)_localctx, predIndex);
		case 40:
			return abelian_product_sempred((Abelian_productContext)_localctx, predIndex);
		case 42:
			return abelian_atom_sempred((Abelian_atomContext)_localctx, predIndex);
		case 43:
			return boolean_expr_sempred((Boolean_exprContext)_localctx, predIndex);
		case 58:
			return argument_list_sempred((Argument_listContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean type_declaration_list_sempred(Type_declaration_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean implements_list_sempred(Implements_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean context_body_sempred(Context_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean role_body_sempred(Role_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 6);
		case 5:
			return precpred(_ctx, 4);
		case 6:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean self_methods_sempred(Self_methodsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return precpred(_ctx, 3);
		case 8:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean stageprop_body_sempred(Stageprop_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 9:
			return precpred(_ctx, 7);
		case 10:
			return precpred(_ctx, 5);
		case 11:
			return precpred(_ctx, 3);
		case 12:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean class_body_sempred(Class_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 13:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean interface_body_sempred(Interface_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 14:
			return precpred(_ctx, 3);
		case 15:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expr_and_decl_list_sempred(Expr_and_decl_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 16:
			return precpred(_ctx, 4);
		case 17:
			return precpred(_ctx, 3);
		case 18:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean identifier_list_sempred(Identifier_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 19:
			return precpred(_ctx, 3);
		case 20:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean param_list_sempred(Param_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 21:
			return precpred(_ctx, 4);
		case 22:
			return precpred(_ctx, 3);
		case 23:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean abelian_expr_sempred(Abelian_exprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 24:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean abelian_product_sempred(Abelian_productContext _localctx, int predIndex) {
		switch (predIndex) {
		case 25:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean abelian_atom_sempred(Abelian_atomContext _localctx, int predIndex) {
		switch (predIndex) {
		case 26:
			return precpred(_ctx, 17);
		case 27:
			return precpred(_ctx, 15);
		case 28:
			return precpred(_ctx, 11);
		case 29:
			return precpred(_ctx, 7);
		case 30:
			return precpred(_ctx, 6);
		case 31:
			return precpred(_ctx, 3);
		case 32:
			return precpred(_ctx, 2);
		case 33:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean boolean_expr_sempred(Boolean_exprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 34:
			return precpred(_ctx, 5);
		case 35:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean argument_list_sempred(Argument_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 36:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001I\u0428\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002"+
		"-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u00071\u0002"+
		"2\u00072\u00023\u00073\u00024\u00074\u00025\u00075\u00026\u00076\u0002"+
		"7\u00077\u00028\u00078\u00029\u00079\u0002:\u0007:\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0003\u0000{\b\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002\u0082\b\u0002\u0001\u0002"+
		"\u0001\u0002\u0005\u0002\u0086\b\u0002\n\u0002\f\u0002\u0089\t\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0003\u0003\u008e\b\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0005\u0004\u0093\b\u0004\n\u0004\f\u0004\u0096\t\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0005\u0005\u00a0\b\u0005\n\u0005\f\u0005\u00a3"+
		"\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u00af"+
		"\b\u0005\n\u0005\f\u0005\u00b2\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u00bb\b\u0005"+
		"\n\u0005\f\u0005\u00be\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005"+
		"\u0005\u00c9\b\u0005\n\u0005\f\u0005\u00cc\t\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005"+
		"\u00d5\b\u0005\n\u0005\f\u0005\u00d8\t\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005\u00e0\b\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0003\u0006\u00ef\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u00f7\b\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005"+
		"\u0007\u0100\b\u0007\n\u0007\f\u0007\u0103\t\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0005\b\u0109\b\b\n\b\f\b\u010c\t\b\u0001\b\u0001\b\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0005\t\u0114\b\t\n\t\f\t\u0117\t\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0003\n\u011e\b\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0003\u000b\u0123\b\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u0127"+
		"\b\u000b\n\u000b\f\u000b\u012a\t\u000b\u0001\f\u0001\f\u0001\f\u0001\f"+
		"\u0001\f\u0003\f\u0131\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u017f"+
		"\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0184\b\u000e\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u018b"+
		"\b\u000f\n\u000f\f\u000f\u018e\t\u000f\u0001\u000f\u0003\u000f\u0191\b"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0005\u000f\u019a\b\u000f\n\u000f\f\u000f\u019d\t\u000f"+
		"\u0005\u000f\u019f\b\u000f\n\u000f\f\u000f\u01a2\t\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0005\u0010\u01ac\b\u0010\n\u0010\f\u0010\u01af\t\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011"+
		"\u01f9\b\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0005\u0012\u0201\b\u0012\n\u0012\f\u0012\u0204\t\u0012\u0001"+
		"\u0012\u0001\u0012\u0005\u0012\u0208\b\u0012\n\u0012\f\u0012\u020b\t\u0012"+
		"\u0003\u0012\u020d\b\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0005\u0012\u0217\b\u0012"+
		"\n\u0012\f\u0012\u021a\t\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0005"+
		"\u0012\u021f\b\u0012\n\u0012\f\u0012\u0222\t\u0012\u0005\u0012\u0224\b"+
		"\u0012\n\u0012\f\u0012\u0227\t\u0012\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0003\u0013\u022c\b\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u0230\b"+
		"\u0013\n\u0013\f\u0013\u0233\t\u0013\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0003\u0014\u0238\b\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0005\u0015\u0242\b\u0015"+
		"\n\u0015\f\u0015\u0245\t\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u0255"+
		"\b\u0018\n\u0018\f\u0018\u0258\t\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0005\u0018\u025e\b\u0018\n\u0018\f\u0018\u0261\t\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0005"+
		"\u0018\u0269\b\u0018\n\u0018\f\u0018\u026c\t\u0018\u0003\u0018\u026e\b"+
		"\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0003\u0019\u0277\b\u0019\u0001\u0019\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0005\u0019\u027f\b\u0019\n"+
		"\u0019\f\u0019\u0282\t\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0003\u001a\u028b\b\u001a\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0003"+
		"\u001b\u0293\b\u001b\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0003\u001d\u029a\b\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0003\u001e\u02ac\b\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0001"+
		" \u0001 \u0001 \u0001 \u0001 \u0003 \u02b6\b \u0001!\u0001!\u0001!\u0001"+
		"!\u0003!\u02bc\b!\u0001\"\u0001\"\u0001#\u0001#\u0001#\u0001#\u0001#\u0003"+
		"#\u02c5\b#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0005"+
		"#\u02cf\b#\n#\f#\u02d2\t#\u0001$\u0001$\u0001$\u0003$\u02d7\b$\u0001$"+
		"\u0001$\u0001$\u0001$\u0001$\u0001$\u0001$\u0001$\u0005$\u02e1\b$\n$\f"+
		"$\u02e4\t$\u0001%\u0001%\u0001%\u0001&\u0001&\u0001&\u0001&\u0001&\u0001"+
		"&\u0001&\u0001&\u0001&\u0001&\u0001&\u0001&\u0001&\u0003&\u02f6\b&\u0001"+
		"\'\u0001\'\u0001\'\u0001\'\u0005\'\u02fc\b\'\n\'\f\'\u02ff\t\'\u0001\'"+
		"\u0003\'\u0302\b\'\u0001\'\u0001\'\u0001\'\u0005\'\u0307\b\'\n\'\f\'\u030a"+
		"\t\'\u0001(\u0001(\u0001(\u0001(\u0005(\u0310\b(\n(\f(\u0313\t(\u0001"+
		"(\u0001(\u0001(\u0005(\u0318\b(\n(\f(\u031b\t(\u0001)\u0001)\u0001)\u0003"+
		")\u0320\b)\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001"+
		"*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001"+
		"*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001"+
		"*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001"+
		"*\u0001*\u0001*\u0003*\u034b\b*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001"+
		"*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001"+
		"*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001"+
		"*\u0001*\u0001*\u0001*\u0001*\u0001*\u0005*\u036c\b*\n*\f*\u036f\t*\u0001"+
		"+\u0001+\u0001+\u0001+\u0005+\u0375\b+\n+\f+\u0378\t+\u0001+\u0001+\u0001"+
		"+\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0003+\u0385"+
		"\b+\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0005+\u038d\b+\n+\f+\u0390"+
		"\t+\u0001,\u0001,\u0001,\u0005,\u0395\b,\n,\f,\u0398\t,\u0001-\u0001-"+
		"\u0001-\u0003-\u039d\b-\u0001.\u0001.\u0001.\u0001.\u0001.\u0001.\u0001"+
		".\u0003.\u03a6\b.\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001"+
		"/\u0001/\u0001/\u0003/\u03b2\b/\u00010\u00010\u00010\u00010\u00010\u0001"+
		"0\u00030\u03ba\b0\u00011\u00011\u00031\u03be\b1\u00012\u00012\u00012\u0001"+
		"2\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u0001"+
		"2\u00032\u03ce\b2\u00013\u00013\u00013\u00013\u00013\u00013\u00013\u0001"+
		"3\u00013\u00013\u00013\u00013\u00013\u00013\u00013\u00013\u00013\u0001"+
		"3\u00013\u00013\u00013\u00013\u00013\u00013\u00013\u00013\u00013\u0001"+
		"3\u00013\u00013\u00013\u00013\u00013\u00013\u00013\u00033\u03f3\b3\u0001"+
		"4\u00014\u00014\u00014\u00014\u00014\u00015\u00015\u00015\u00015\u0001"+
		"5\u00015\u00015\u00016\u00016\u00016\u00016\u00016\u00016\u00056\u0408"+
		"\b6\n6\f6\u040b\t6\u00016\u00016\u00017\u00017\u00017\u00037\u0412\b7"+
		"\u00017\u00017\u00017\u00018\u00018\u00019\u00019\u0001:\u0001:\u0001"+
		":\u0003:\u041e\b:\u0001:\u0001:\u0001:\u0005:\u0423\b:\n:\f:\u0426\t:"+
		"\u0001:\u0000\u0010\u0004\u000e\u0016\u001e $&*2FHNPTVt;\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \""+
		"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprt\u0000\u0007\u0002\u0000=>E"+
		"E\u0001\u0000\u0012\u0015\u0001\u0000\u0019\u001a\u0001\u0000\u001b\u001c"+
		"\u0003\u0000\u0019\u001a\u001d\"AB\u0002\u0000\u0017\u0018<<\u0001\u0000"+
		"),\u048a\u0000z\u0001\u0000\u0000\u0000\u0002|\u0001\u0000\u0000\u0000"+
		"\u0004\u0081\u0001\u0000\u0000\u0000\u0006\u008d\u0001\u0000\u0000\u0000"+
		"\b\u008f\u0001\u0000\u0000\u0000\n\u00df\u0001\u0000\u0000\u0000\f\u00ee"+
		"\u0001\u0000\u0000\u0000\u000e\u00f6\u0001\u0000\u0000\u0000\u0010\u0104"+
		"\u0001\u0000\u0000\u0000\u0012\u010f\u0001\u0000\u0000\u0000\u0014\u011a"+
		"\u0001\u0000\u0000\u0000\u0016\u0122\u0001\u0000\u0000\u0000\u0018\u0130"+
		"\u0001\u0000\u0000\u0000\u001a\u017e\u0001\u0000\u0000\u0000\u001c\u0183"+
		"\u0001\u0000\u0000\u0000\u001e\u0190\u0001\u0000\u0000\u0000 \u01a3\u0001"+
		"\u0000\u0000\u0000\"\u01f8\u0001\u0000\u0000\u0000$\u020c\u0001\u0000"+
		"\u0000\u0000&\u022b\u0001\u0000\u0000\u0000(\u0237\u0001\u0000\u0000\u0000"+
		"*\u0239\u0001\u0000\u0000\u0000,\u0246\u0001\u0000\u0000\u0000.\u024b"+
		"\u0001\u0000\u0000\u00000\u026d\u0001\u0000\u0000\u00002\u0276\u0001\u0000"+
		"\u0000\u00004\u028a\u0001\u0000\u0000\u00006\u0292\u0001\u0000\u0000\u0000"+
		"8\u0294\u0001\u0000\u0000\u0000:\u0299\u0001\u0000\u0000\u0000<\u02ab"+
		"\u0001\u0000\u0000\u0000>\u02ad\u0001\u0000\u0000\u0000@\u02b5\u0001\u0000"+
		"\u0000\u0000B\u02bb\u0001\u0000\u0000\u0000D\u02bd\u0001\u0000\u0000\u0000"+
		"F\u02c4\u0001\u0000\u0000\u0000H\u02d6\u0001\u0000\u0000\u0000J\u02e5"+
		"\u0001\u0000\u0000\u0000L\u02f5\u0001\u0000\u0000\u0000N\u0301\u0001\u0000"+
		"\u0000\u0000P\u030b\u0001\u0000\u0000\u0000R\u031f\u0001\u0000\u0000\u0000"+
		"T\u034a\u0001\u0000\u0000\u0000V\u0384\u0001\u0000\u0000\u0000X\u0391"+
		"\u0001\u0000\u0000\u0000Z\u039c\u0001\u0000\u0000\u0000\\\u03a5\u0001"+
		"\u0000\u0000\u0000^\u03b1\u0001\u0000\u0000\u0000`\u03b9\u0001\u0000\u0000"+
		"\u0000b\u03bd\u0001\u0000\u0000\u0000d\u03cd\u0001\u0000\u0000\u0000f"+
		"\u03f2\u0001\u0000\u0000\u0000h\u03f4\u0001\u0000\u0000\u0000j\u03fa\u0001"+
		"\u0000\u0000\u0000l\u0401\u0001\u0000\u0000\u0000n\u0411\u0001\u0000\u0000"+
		"\u0000p\u0416\u0001\u0000\u0000\u0000r\u0418\u0001\u0000\u0000\u0000t"+
		"\u041d\u0001\u0000\u0000\u0000vw\u0003\u0004\u0002\u0000wx\u0003\u0002"+
		"\u0001\u0000x{\u0001\u0000\u0000\u0000y{\u0003\u0004\u0002\u0000zv\u0001"+
		"\u0000\u0000\u0000zy\u0001\u0000\u0000\u0000{\u0001\u0001\u0000\u0000"+
		"\u0000|}\u0003L&\u0000}\u0003\u0001\u0000\u0000\u0000~\u007f\u0006\u0002"+
		"\uffff\uffff\u0000\u007f\u0082\u0003\u0006\u0003\u0000\u0080\u0082\u0001"+
		"\u0000\u0000\u0000\u0081~\u0001\u0000\u0000\u0000\u0081\u0080\u0001\u0000"+
		"\u0000\u0000\u0082\u0087\u0001\u0000\u0000\u0000\u0083\u0084\n\u0002\u0000"+
		"\u0000\u0084\u0086\u0003\u0006\u0003\u0000\u0085\u0083\u0001\u0000\u0000"+
		"\u0000\u0086\u0089\u0001\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000"+
		"\u0000\u0087\u0088\u0001\u0000\u0000\u0000\u0088\u0005\u0001\u0000\u0000"+
		"\u0000\u0089\u0087\u0001\u0000\u0000\u0000\u008a\u008e\u0003\b\u0004\u0000"+
		"\u008b\u008e\u0003\n\u0005\u0000\u008c\u008e\u0003\f\u0006\u0000\u008d"+
		"\u008a\u0001\u0000\u0000\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008d"+
		"\u008c\u0001\u0000\u0000\u0000\u008e\u0007\u0001\u0000\u0000\u0000\u008f"+
		"\u0090\u0005\u0001\u0000\u0000\u0090\u0094\u0005E\u0000\u0000\u0091\u0093"+
		"\u0003\u000e\u0007\u0000\u0092\u0091\u0001\u0000\u0000\u0000\u0093\u0096"+
		"\u0001\u0000\u0000\u0000\u0094\u0092\u0001\u0000\u0000\u0000\u0094\u0095"+
		"\u0001\u0000\u0000\u0000\u0095\u0097\u0001\u0000\u0000\u0000\u0096\u0094"+
		"\u0001\u0000\u0000\u0000\u0097\u0098\u0005\u0002\u0000\u0000\u0098\u0099"+
		"\u0003\u0016\u000b\u0000\u0099\u009a\u0005\u0003\u0000\u0000\u009a\t\u0001"+
		"\u0000\u0000\u0000\u009b\u009c\u0005\u0004\u0000\u0000\u009c\u009d\u0005"+
		"E\u0000\u0000\u009d\u00a1\u0003\u0010\b\u0000\u009e\u00a0\u0003\u000e"+
		"\u0007\u0000\u009f\u009e\u0001\u0000\u0000\u0000\u00a0\u00a3\u0001\u0000"+
		"\u0000\u0000\u00a1\u009f\u0001\u0000\u0000\u0000\u00a1\u00a2\u0001\u0000"+
		"\u0000\u0000\u00a2\u00a4\u0001\u0000\u0000\u0000\u00a3\u00a1\u0001\u0000"+
		"\u0000\u0000\u00a4\u00a5\u0005\u0002\u0000\u0000\u00a5\u00a6\u0003&\u0013"+
		"\u0000\u00a6\u00a7\u0005\u0003\u0000\u0000\u00a7\u00e0\u0001\u0000\u0000"+
		"\u0000\u00a8\u00a9\u0005\u0004\u0000\u0000\u00a9\u00aa\u0005E\u0000\u0000"+
		"\u00aa\u00ab\u0003\u0010\b\u0000\u00ab\u00ac\u0005\u0005\u0000\u0000\u00ac"+
		"\u00b0\u0005E\u0000\u0000\u00ad\u00af\u0003\u000e\u0007\u0000\u00ae\u00ad"+
		"\u0001\u0000\u0000\u0000\u00af\u00b2\u0001\u0000\u0000\u0000\u00b0\u00ae"+
		"\u0001\u0000\u0000\u0000\u00b0\u00b1\u0001\u0000\u0000\u0000\u00b1\u00b3"+
		"\u0001\u0000\u0000\u0000\u00b2\u00b0\u0001\u0000\u0000\u0000\u00b3\u00b4"+
		"\u0005\u0002\u0000\u0000\u00b4\u00b5\u0003&\u0013\u0000\u00b5\u00b6\u0005"+
		"\u0003\u0000\u0000\u00b6\u00e0\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005"+
		"\u0004\u0000\u0000\u00b8\u00bc\u0005E\u0000\u0000\u00b9\u00bb\u0003\u000e"+
		"\u0007\u0000\u00ba\u00b9\u0001\u0000\u0000\u0000\u00bb\u00be\u0001\u0000"+
		"\u0000\u0000\u00bc\u00ba\u0001\u0000\u0000\u0000\u00bc\u00bd\u0001\u0000"+
		"\u0000\u0000\u00bd\u00bf\u0001\u0000\u0000\u0000\u00be\u00bc\u0001\u0000"+
		"\u0000\u0000\u00bf\u00c0\u0005\u0002\u0000\u0000\u00c0\u00c1\u0003&\u0013"+
		"\u0000\u00c1\u00c2\u0005\u0003\u0000\u0000\u00c2\u00e0\u0001\u0000\u0000"+
		"\u0000\u00c3\u00c4\u0005\u0004\u0000\u0000\u00c4\u00c5\u0005E\u0000\u0000"+
		"\u00c5\u00c6\u0005\u0005\u0000\u0000\u00c6\u00ca\u0005E\u0000\u0000\u00c7"+
		"\u00c9\u0003\u000e\u0007\u0000\u00c8\u00c7\u0001\u0000\u0000\u0000\u00c9"+
		"\u00cc\u0001\u0000\u0000\u0000\u00ca\u00c8\u0001\u0000\u0000\u0000\u00ca"+
		"\u00cb\u0001\u0000\u0000\u0000\u00cb\u00cd\u0001\u0000\u0000\u0000\u00cc"+
		"\u00ca\u0001\u0000\u0000\u0000\u00cd\u00ce\u0005\u0002\u0000\u0000\u00ce"+
		"\u00cf\u0003&\u0013\u0000\u00cf\u00d0\u0005\u0003\u0000\u0000\u00d0\u00e0"+
		"\u0001\u0000\u0000\u0000\u00d1\u00d2\u0005\u0004\u0000\u0000\u00d2\u00d6"+
		"\u0005E\u0000\u0000\u00d3\u00d5\u0003\u000e\u0007\u0000\u00d4\u00d3\u0001"+
		"\u0000\u0000\u0000\u00d5\u00d8\u0001\u0000\u0000\u0000\u00d6\u00d4\u0001"+
		"\u0000\u0000\u0000\u00d6\u00d7\u0001\u0000\u0000\u0000\u00d7\u00d9\u0001"+
		"\u0000\u0000\u0000\u00d8\u00d6\u0001\u0000\u0000\u0000\u00d9\u00da\u0005"+
		"\u0005\u0000\u0000\u00da\u00db\u0005E\u0000\u0000\u00db\u00dc\u0005\u0002"+
		"\u0000\u0000\u00dc\u00dd\u0003&\u0013\u0000\u00dd\u00de\u0005\u0003\u0000"+
		"\u0000\u00de\u00e0\u0001\u0000\u0000\u0000\u00df\u009b\u0001\u0000\u0000"+
		"\u0000\u00df\u00a8\u0001\u0000\u0000\u0000\u00df\u00b7\u0001\u0000\u0000"+
		"\u0000\u00df\u00c3\u0001\u0000\u0000\u0000\u00df\u00d1\u0001\u0000\u0000"+
		"\u0000\u00e0\u000b\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005\u0006\u0000"+
		"\u0000\u00e2\u00e3\u0005E\u0000\u0000\u00e3\u00e4\u0005\u0002\u0000\u0000"+
		"\u00e4\u00e5\u0003*\u0015\u0000\u00e5\u00e6\u0005\u0003\u0000\u0000\u00e6"+
		"\u00ef\u0001\u0000\u0000\u0000\u00e7\u00e8\u0005\u0006\u0000\u0000\u00e8"+
		"\u00e9\u0005E\u0000\u0000\u00e9\u00ea\u0003\u0010\b\u0000\u00ea\u00eb"+
		"\u0005\u0002\u0000\u0000\u00eb\u00ec\u0003*\u0015\u0000\u00ec\u00ed\u0005"+
		"\u0003\u0000\u0000\u00ed\u00ef\u0001\u0000\u0000\u0000\u00ee\u00e1\u0001"+
		"\u0000\u0000\u0000\u00ee\u00e7\u0001\u0000\u0000\u0000\u00ef\r\u0001\u0000"+
		"\u0000\u0000\u00f0\u00f1\u0006\u0007\uffff\uffff\u0000\u00f1\u00f2\u0005"+
		"\u0007\u0000\u0000\u00f2\u00f7\u0005E\u0000\u0000\u00f3\u00f4\u0005\u0007"+
		"\u0000\u0000\u00f4\u00f5\u0005E\u0000\u0000\u00f5\u00f7\u0003\u0012\t"+
		"\u0000\u00f6\u00f0\u0001\u0000\u0000\u0000\u00f6\u00f3\u0001\u0000\u0000"+
		"\u0000\u00f7\u0101\u0001\u0000\u0000\u0000\u00f8\u00f9\n\u0002\u0000\u0000"+
		"\u00f9\u00fa\u0005\b\u0000\u0000\u00fa\u0100\u0005E\u0000\u0000\u00fb"+
		"\u00fc\n\u0001\u0000\u0000\u00fc\u00fd\u0005\b\u0000\u0000\u00fd\u00fe"+
		"\u0005E\u0000\u0000\u00fe\u0100\u0003\u0012\t\u0000\u00ff\u00f8\u0001"+
		"\u0000\u0000\u0000\u00ff\u00fb\u0001\u0000\u0000\u0000\u0100\u0103\u0001"+
		"\u0000\u0000\u0000\u0101\u00ff\u0001\u0000\u0000\u0000\u0101\u0102\u0001"+
		"\u0000\u0000\u0000\u0102\u000f\u0001\u0000\u0000\u0000\u0103\u0101\u0001"+
		"\u0000\u0000\u0000\u0104\u0105\u0005A\u0000\u0000\u0105\u010a\u0003\u0014"+
		"\n\u0000\u0106\u0107\u0005\b\u0000\u0000\u0107\u0109\u0003\u0014\n\u0000"+
		"\u0108\u0106\u0001\u0000\u0000\u0000\u0109\u010c\u0001\u0000\u0000\u0000"+
		"\u010a\u0108\u0001\u0000\u0000\u0000\u010a\u010b\u0001\u0000\u0000\u0000"+
		"\u010b\u010d\u0001\u0000\u0000\u0000\u010c\u010a\u0001\u0000\u0000\u0000"+
		"\u010d\u010e\u0005B\u0000\u0000\u010e\u0011\u0001\u0000\u0000\u0000\u010f"+
		"\u0110\u0005A\u0000\u0000\u0110\u0115\u0003B!\u0000\u0111\u0112\u0005"+
		"\b\u0000\u0000\u0112\u0114\u0003B!\u0000\u0113\u0111\u0001\u0000\u0000"+
		"\u0000\u0114\u0117\u0001\u0000\u0000\u0000\u0115\u0113\u0001\u0000\u0000"+
		"\u0000\u0115\u0116\u0001\u0000\u0000\u0000\u0116\u0118\u0001\u0000\u0000"+
		"\u0000\u0117\u0115\u0001\u0000\u0000\u0000\u0118\u0119\u0005B\u0000\u0000"+
		"\u0119\u0013\u0001\u0000\u0000\u0000\u011a\u011d\u0003B!\u0000\u011b\u011c"+
		"\u0005\u0005\u0000\u0000\u011c\u011e\u0003B!\u0000\u011d\u011b\u0001\u0000"+
		"\u0000\u0000\u011d\u011e\u0001\u0000\u0000\u0000\u011e\u0015\u0001\u0000"+
		"\u0000\u0000\u011f\u0120\u0006\u000b\uffff\uffff\u0000\u0120\u0123\u0003"+
		"\u0018\f\u0000\u0121\u0123\u0001\u0000\u0000\u0000\u0122\u011f\u0001\u0000"+
		"\u0000\u0000\u0122\u0121\u0001\u0000\u0000\u0000\u0123\u0128\u0001\u0000"+
		"\u0000\u0000\u0124\u0125\n\u0003\u0000\u0000\u0125\u0127\u0003\u0018\f"+
		"\u0000\u0126\u0124\u0001\u0000\u0000\u0000\u0127\u012a\u0001\u0000\u0000"+
		"\u0000\u0128\u0126\u0001\u0000\u0000\u0000\u0128\u0129\u0001\u0000\u0000"+
		"\u0000\u0129\u0017\u0001\u0000\u0000\u0000\u012a\u0128\u0001\u0000\u0000"+
		"\u0000\u012b\u0131\u0003,\u0016\u0000\u012c\u0131\u0003<\u001e\u0000\u012d"+
		"\u0131\u0003\u001a\r\u0000\u012e\u0131\u0003\"\u0011\u0000\u012f\u0131"+
		"\u0003\b\u0004\u0000\u0130\u012b\u0001\u0000\u0000\u0000\u0130\u012c\u0001"+
		"\u0000\u0000\u0000\u0130\u012d\u0001\u0000\u0000\u0000\u0130\u012e\u0001"+
		"\u0000\u0000\u0000\u0130\u012f\u0001\u0000\u0000\u0000\u0131\u0019\u0001"+
		"\u0000\u0000\u0000\u0132\u0133\u0005\t\u0000\u0000\u0133\u0134\u0003\u001c"+
		"\u000e\u0000\u0134\u0135\u0005E\u0000\u0000\u0135\u0136\u0005\u0002\u0000"+
		"\u0000\u0136\u0137\u0003\u001e\u000f\u0000\u0137\u0138\u0005\u0003\u0000"+
		"\u0000\u0138\u017f\u0001\u0000\u0000\u0000\u0139\u013a\u0005\t\u0000\u0000"+
		"\u013a\u013b\u0003\u001c\u000e\u0000\u013b\u013c\u0005E\u0000\u0000\u013c"+
		"\u013d\u0005\u0002\u0000\u0000\u013d\u013e\u0003\u001e\u000f\u0000\u013e"+
		"\u013f\u0005\u0003\u0000\u0000\u013f\u0140\u00053\u0000\u0000\u0140\u0141"+
		"\u0005\u0002\u0000\u0000\u0141\u0142\u0003 \u0010\u0000\u0142\u0143\u0005"+
		"\u0003\u0000\u0000\u0143\u017f\u0001\u0000\u0000\u0000\u0144\u0145\u0003"+
		":\u001d\u0000\u0145\u0146\u0005\t\u0000\u0000\u0146\u0147\u0003\u001c"+
		"\u000e\u0000\u0147\u0148\u0005E\u0000\u0000\u0148\u0149\u0005\u0002\u0000"+
		"\u0000\u0149\u014a\u0003\u001e\u000f\u0000\u014a\u014b\u0005\u0003\u0000"+
		"\u0000\u014b\u017f\u0001\u0000\u0000\u0000\u014c\u014d\u0003:\u001d\u0000"+
		"\u014d\u014e\u0005\t\u0000\u0000\u014e\u014f\u0003\u001c\u000e\u0000\u014f"+
		"\u0150\u0005E\u0000\u0000\u0150\u0151\u0005\u0002\u0000\u0000\u0151\u0152"+
		"\u0003\u001e\u000f\u0000\u0152\u0153\u0005\u0003\u0000\u0000\u0153\u0154"+
		"\u00053\u0000\u0000\u0154\u0155\u0005\u0002\u0000\u0000\u0155\u0156\u0003"+
		" \u0010\u0000\u0156\u0157\u0005\u0003\u0000\u0000\u0157\u017f\u0001\u0000"+
		"\u0000\u0000\u0158\u0159\u0005\t\u0000\u0000\u0159\u015a\u0003\u001c\u000e"+
		"\u0000\u015a\u015b\u0005E\u0000\u0000\u015b\u015c\u0005\u0002\u0000\u0000"+
		"\u015c\u015d\u0003\u001e\u000f\u0000\u015d\u015e\u0005\u0003\u0000\u0000"+
		"\u015e\u017f\u0001\u0000\u0000\u0000\u015f\u0160\u0005\t\u0000\u0000\u0160"+
		"\u0161\u0003\u001c\u000e\u0000\u0161\u0162\u0005E\u0000\u0000\u0162\u0163"+
		"\u0005\u0002\u0000\u0000\u0163\u0164\u0003\u001e\u000f\u0000\u0164\u0165"+
		"\u0005\u0003\u0000\u0000\u0165\u0166\u00053\u0000\u0000\u0166\u0167\u0005"+
		"\u0002\u0000\u0000\u0167\u0168\u0003 \u0010\u0000\u0168\u0169\u0005\u0003"+
		"\u0000\u0000\u0169\u017f\u0001\u0000\u0000\u0000\u016a\u016b\u0003:\u001d"+
		"\u0000\u016b\u016c\u0005\t\u0000\u0000\u016c\u016d\u0003\u001c\u000e\u0000"+
		"\u016d\u016e\u0005E\u0000\u0000\u016e\u016f\u0005\u0002\u0000\u0000\u016f"+
		"\u0170\u0003\u001e\u000f\u0000\u0170\u0171\u0005\u0003\u0000\u0000\u0171"+
		"\u017f\u0001\u0000\u0000\u0000\u0172\u0173\u0003:\u001d\u0000\u0173\u0174"+
		"\u0005\t\u0000\u0000\u0174\u0175\u0003\u001c\u000e\u0000\u0175\u0176\u0005"+
		"E\u0000\u0000\u0176\u0177\u0005\u0002\u0000\u0000\u0177\u0178\u0003\u001e"+
		"\u000f\u0000\u0178\u0179\u0005\u0003\u0000\u0000\u0179\u017a\u00053\u0000"+
		"\u0000\u017a\u017b\u0005\u0002\u0000\u0000\u017b\u017c\u0003 \u0010\u0000"+
		"\u017c\u017d\u0005\u0003\u0000\u0000\u017d\u017f\u0001\u0000\u0000\u0000"+
		"\u017e\u0132\u0001\u0000\u0000\u0000\u017e\u0139\u0001\u0000\u0000\u0000"+
		"\u017e\u0144\u0001\u0000\u0000\u0000\u017e\u014c\u0001\u0000\u0000\u0000"+
		"\u017e\u0158\u0001\u0000\u0000\u0000\u017e\u015f\u0001\u0000\u0000\u0000"+
		"\u017e\u016a\u0001\u0000\u0000\u0000\u017e\u0172\u0001\u0000\u0000\u0000"+
		"\u017f\u001b\u0001\u0000\u0000\u0000\u0180\u0181\u0005\n\u0000\u0000\u0181"+
		"\u0184\u0005\u000b\u0000\u0000\u0182\u0184\u0001\u0000\u0000\u0000\u0183"+
		"\u0180\u0001\u0000\u0000\u0000\u0183\u0182\u0001\u0000\u0000\u0000\u0184"+
		"\u001d\u0001\u0000\u0000\u0000\u0185\u0186\u0006\u000f\uffff\uffff\u0000"+
		"\u0186\u0191\u0003,\u0016\u0000\u0187\u0191\u0003<\u001e\u0000\u0188\u018c"+
		"\u00030\u0018\u0000\u0189\u018b\u0005\f\u0000\u0000\u018a\u0189\u0001"+
		"\u0000\u0000\u0000\u018b\u018e\u0001\u0000\u0000\u0000\u018c\u018a\u0001"+
		"\u0000\u0000\u0000\u018c\u018d\u0001\u0000\u0000\u0000\u018d\u0191\u0001"+
		"\u0000\u0000\u0000\u018e\u018c\u0001\u0000\u0000\u0000\u018f\u0191\u0001"+
		"\u0000\u0000\u0000\u0190\u0185\u0001\u0000\u0000\u0000\u0190\u0187\u0001"+
		"\u0000\u0000\u0000\u0190\u0188\u0001\u0000\u0000\u0000\u0190\u018f\u0001"+
		"\u0000\u0000\u0000\u0191\u01a0\u0001\u0000\u0000\u0000\u0192\u0193\n\u0006"+
		"\u0000\u0000\u0193\u019f\u0003,\u0016\u0000\u0194\u0195\n\u0004\u0000"+
		"\u0000\u0195\u019f\u0003<\u001e\u0000\u0196\u0197\n\u0002\u0000\u0000"+
		"\u0197\u019b\u00030\u0018\u0000\u0198\u019a\u0005\f\u0000\u0000\u0199"+
		"\u0198\u0001\u0000\u0000\u0000\u019a\u019d\u0001\u0000\u0000\u0000\u019b"+
		"\u0199\u0001\u0000\u0000\u0000\u019b\u019c\u0001\u0000\u0000\u0000\u019c"+
		"\u019f\u0001\u0000\u0000\u0000\u019d\u019b\u0001\u0000\u0000\u0000\u019e"+
		"\u0192\u0001\u0000\u0000\u0000\u019e\u0194\u0001\u0000\u0000\u0000\u019e"+
		"\u0196\u0001\u0000\u0000\u0000\u019f\u01a2\u0001\u0000\u0000\u0000\u01a0"+
		"\u019e\u0001\u0000\u0000\u0000\u01a0\u01a1\u0001\u0000\u0000\u0000\u01a1"+
		"\u001f\u0001\u0000\u0000\u0000\u01a2\u01a0\u0001\u0000\u0000\u0000\u01a3"+
		"\u01a4\u0006\u0010\uffff\uffff\u0000\u01a4\u01a5\u00030\u0018\u0000\u01a5"+
		"\u01ad\u0001\u0000\u0000\u0000\u01a6\u01a7\n\u0003\u0000\u0000\u01a7\u01a8"+
		"\u0005\f\u0000\u0000\u01a8\u01ac\u00030\u0018\u0000\u01a9\u01aa\n\u0001"+
		"\u0000\u0000\u01aa\u01ac\u0005\f\u0000\u0000\u01ab\u01a6\u0001\u0000\u0000"+
		"\u0000\u01ab\u01a9\u0001\u0000\u0000\u0000\u01ac\u01af\u0001\u0000\u0000"+
		"\u0000\u01ad\u01ab\u0001\u0000\u0000\u0000\u01ad\u01ae\u0001\u0000\u0000"+
		"\u0000\u01ae!\u0001\u0000\u0000\u0000\u01af\u01ad\u0001\u0000\u0000\u0000"+
		"\u01b0\u01b1\u0005\r\u0000\u0000\u01b1\u01b2\u0003\u001c\u000e\u0000\u01b2"+
		"\u01b3\u0005E\u0000\u0000\u01b3\u01b4\u0005\u0002\u0000\u0000\u01b4\u01b5"+
		"\u0003$\u0012\u0000\u01b5\u01b6\u0005\u0003\u0000\u0000\u01b6\u01f9\u0001"+
		"\u0000\u0000\u0000\u01b7\u01b8\u0005\r\u0000\u0000\u01b8\u01b9\u0003\u001c"+
		"\u000e\u0000\u01b9\u01ba\u0005E\u0000\u0000\u01ba\u01bb\u0005\u0002\u0000"+
		"\u0000\u01bb\u01bc\u0003$\u0012\u0000\u01bc\u01bd\u0005\u0003\u0000\u0000"+
		"\u01bd\u01be\u00053\u0000\u0000\u01be\u01bf\u0005\u0002\u0000\u0000\u01bf"+
		"\u01c0\u0003 \u0010\u0000\u01c0\u01c1\u0005\u0003\u0000\u0000\u01c1\u01f9"+
		"\u0001\u0000\u0000\u0000\u01c2\u01c3\u0003:\u001d\u0000\u01c3\u01c4\u0005"+
		"\r\u0000\u0000\u01c4\u01c5\u0003\u001c\u000e\u0000\u01c5\u01c6\u0005E"+
		"\u0000\u0000\u01c6\u01c7\u0005\u0002\u0000\u0000\u01c7\u01c8\u0003$\u0012"+
		"\u0000\u01c8\u01c9\u0005\u0003\u0000\u0000\u01c9\u01f9\u0001\u0000\u0000"+
		"\u0000\u01ca\u01cb\u0003:\u001d\u0000\u01cb\u01cc\u0005\r\u0000\u0000"+
		"\u01cc\u01cd\u0003\u001c\u000e\u0000\u01cd\u01ce\u0005E\u0000\u0000\u01ce"+
		"\u01cf\u0005\u0002\u0000\u0000\u01cf\u01d0\u0003$\u0012\u0000\u01d0\u01d1"+
		"\u0005\u0003\u0000\u0000\u01d1\u01d2\u00053\u0000\u0000\u01d2\u01d3\u0005"+
		"\u0002\u0000\u0000\u01d3\u01d4\u0003 \u0010\u0000\u01d4\u01d5\u0005\u0003"+
		"\u0000\u0000\u01d5\u01f9\u0001\u0000\u0000\u0000\u01d6\u01d7\u0005\r\u0000"+
		"\u0000\u01d7\u01d8\u0003\u001c\u000e\u0000\u01d8\u01d9\u0005E\u0000\u0000"+
		"\u01d9\u01da\u0005\u0002\u0000\u0000\u01da\u01db\u0005\u0003\u0000\u0000"+
		"\u01db\u01f9\u0001\u0000\u0000\u0000\u01dc\u01dd\u0005\r\u0000\u0000\u01dd"+
		"\u01de\u0003\u001c\u000e\u0000\u01de\u01df\u0005E\u0000\u0000\u01df\u01e0"+
		"\u0005\u0002\u0000\u0000\u01e0\u01e1\u0005\u0003\u0000\u0000\u01e1\u01e2"+
		"\u00053\u0000\u0000\u01e2\u01e3\u0005\u0002\u0000\u0000\u01e3\u01e4\u0003"+
		" \u0010\u0000\u01e4\u01e5\u0005\u0003\u0000\u0000\u01e5\u01f9\u0001\u0000"+
		"\u0000\u0000\u01e6\u01e7\u0003:\u001d\u0000\u01e7\u01e8\u0005\r\u0000"+
		"\u0000\u01e8\u01e9\u0003\u001c\u000e\u0000\u01e9\u01ea\u0005E\u0000\u0000"+
		"\u01ea\u01eb\u0005\u0002\u0000\u0000\u01eb\u01ec\u0005\u0003\u0000\u0000"+
		"\u01ec\u01f9\u0001\u0000\u0000\u0000\u01ed\u01ee\u0003:\u001d\u0000\u01ee"+
		"\u01ef\u0005\r\u0000\u0000\u01ef\u01f0\u0003\u001c\u000e\u0000\u01f0\u01f1"+
		"\u0005E\u0000\u0000\u01f1\u01f2\u0005\u0002\u0000\u0000\u01f2\u01f3\u0005"+
		"\u0003\u0000\u0000\u01f3\u01f4\u00053\u0000\u0000\u01f4\u01f5\u0005\u0002"+
		"\u0000\u0000\u01f5\u01f6\u0003 \u0010\u0000\u01f6\u01f7\u0005\u0003\u0000"+
		"\u0000\u01f7\u01f9\u0001\u0000\u0000\u0000\u01f8\u01b0\u0001\u0000\u0000"+
		"\u0000\u01f8\u01b7\u0001\u0000\u0000\u0000\u01f8\u01c2\u0001\u0000\u0000"+
		"\u0000\u01f8\u01ca\u0001\u0000\u0000\u0000\u01f8\u01d6\u0001\u0000\u0000"+
		"\u0000\u01f8\u01dc\u0001\u0000\u0000\u0000\u01f8\u01e6\u0001\u0000\u0000"+
		"\u0000\u01f8\u01ed\u0001\u0000\u0000\u0000\u01f9#\u0001\u0000\u0000\u0000"+
		"\u01fa\u01fb\u0006\u0012\uffff\uffff\u0000\u01fb\u020d\u0003,\u0016\u0000"+
		"\u01fc\u020d\u0003<\u001e\u0000\u01fd\u01fe\u00030\u0018\u0000\u01fe\u0202"+
		"\u00058\u0000\u0000\u01ff\u0201\u0005\f\u0000\u0000\u0200\u01ff\u0001"+
		"\u0000\u0000\u0000\u0201\u0204\u0001\u0000\u0000\u0000\u0202\u0200\u0001"+
		"\u0000\u0000\u0000\u0202\u0203\u0001\u0000\u0000\u0000\u0203\u020d\u0001"+
		"\u0000\u0000\u0000\u0204\u0202\u0001\u0000\u0000\u0000\u0205\u0209\u0003"+
		"0\u0018\u0000\u0206\u0208\u0005\f\u0000\u0000\u0207\u0206\u0001\u0000"+
		"\u0000\u0000\u0208\u020b\u0001\u0000\u0000\u0000\u0209\u0207\u0001\u0000"+
		"\u0000\u0000\u0209\u020a\u0001\u0000\u0000\u0000\u020a\u020d\u0001\u0000"+
		"\u0000\u0000\u020b\u0209\u0001\u0000\u0000\u0000\u020c\u01fa\u0001\u0000"+
		"\u0000\u0000\u020c\u01fc\u0001\u0000\u0000\u0000\u020c\u01fd\u0001\u0000"+
		"\u0000\u0000\u020c\u0205\u0001\u0000\u0000\u0000\u020d\u0225\u0001\u0000"+
		"\u0000\u0000\u020e\u020f\n\u0007\u0000\u0000\u020f\u0224\u0003,\u0016"+
		"\u0000\u0210\u0211\n\u0005\u0000\u0000\u0211\u0224\u0003<\u001e\u0000"+
		"\u0212\u0213\n\u0003\u0000\u0000\u0213\u0214\u00030\u0018\u0000\u0214"+
		"\u0218\u00058\u0000\u0000\u0215\u0217\u0005\f\u0000\u0000\u0216\u0215"+
		"\u0001\u0000\u0000\u0000\u0217\u021a\u0001\u0000\u0000\u0000\u0218\u0216"+
		"\u0001\u0000\u0000\u0000\u0218\u0219\u0001\u0000\u0000\u0000\u0219\u0224"+
		"\u0001\u0000\u0000\u0000\u021a\u0218\u0001\u0000\u0000\u0000\u021b\u021c"+
		"\n\u0001\u0000\u0000\u021c\u0220\u00030\u0018\u0000\u021d\u021f\u0005"+
		"\f\u0000\u0000\u021e\u021d\u0001\u0000\u0000\u0000\u021f\u0222\u0001\u0000"+
		"\u0000\u0000\u0220\u021e\u0001\u0000\u0000\u0000\u0220\u0221\u0001\u0000"+
		"\u0000\u0000\u0221\u0224\u0001\u0000\u0000\u0000\u0222\u0220\u0001\u0000"+
		"\u0000\u0000\u0223\u020e\u0001\u0000\u0000\u0000\u0223\u0210\u0001\u0000"+
		"\u0000\u0000\u0223\u0212\u0001\u0000\u0000\u0000\u0223\u021b\u0001\u0000"+
		"\u0000\u0000\u0224\u0227\u0001\u0000\u0000\u0000\u0225\u0223\u0001\u0000"+
		"\u0000\u0000\u0225\u0226\u0001\u0000\u0000\u0000\u0226%\u0001\u0000\u0000"+
		"\u0000\u0227\u0225\u0001\u0000\u0000\u0000\u0228\u0229\u0006\u0013\uffff"+
		"\uffff\u0000\u0229\u022c\u0003(\u0014\u0000\u022a\u022c\u0001\u0000\u0000"+
		"\u0000\u022b\u0228\u0001\u0000\u0000\u0000\u022b\u022a\u0001\u0000\u0000"+
		"\u0000\u022c\u0231\u0001\u0000\u0000\u0000\u022d\u022e\n\u0003\u0000\u0000"+
		"\u022e\u0230\u0003(\u0014\u0000\u022f\u022d\u0001\u0000\u0000\u0000\u0230"+
		"\u0233\u0001\u0000\u0000\u0000\u0231\u022f\u0001\u0000\u0000\u0000\u0231"+
		"\u0232\u0001\u0000\u0000\u0000\u0232\'\u0001\u0000\u0000\u0000\u0233\u0231"+
		"\u0001\u0000\u0000\u0000\u0234\u0238\u0003,\u0016\u0000\u0235\u0238\u0003"+
		"<\u001e\u0000\u0236\u0238\u0003\u0006\u0003\u0000\u0237\u0234\u0001\u0000"+
		"\u0000\u0000\u0237\u0235\u0001\u0000\u0000\u0000\u0237\u0236\u0001\u0000"+
		"\u0000\u0000\u0238)\u0001\u0000\u0000\u0000\u0239\u023a\u0006\u0015\uffff"+
		"\uffff\u0000\u023a\u023b\u00030\u0018\u0000\u023b\u0243\u0001\u0000\u0000"+
		"\u0000\u023c\u023d\n\u0003\u0000\u0000\u023d\u023e\u0005\f\u0000\u0000"+
		"\u023e\u0242\u00030\u0018\u0000\u023f\u0240\n\u0001\u0000\u0000\u0240"+
		"\u0242\u0005\f\u0000\u0000\u0241\u023c\u0001\u0000\u0000\u0000\u0241\u023f"+
		"\u0001\u0000\u0000\u0000\u0242\u0245\u0001\u0000\u0000\u0000\u0243\u0241"+
		"\u0001\u0000\u0000\u0000\u0243\u0244\u0001\u0000\u0000\u0000\u0244+\u0001"+
		"\u0000\u0000\u0000\u0245\u0243\u0001\u0000\u0000\u0000\u0246\u0247\u0003"+
		".\u0017\u0000\u0247\u0248\u0005\u0002\u0000\u0000\u0248\u0249\u00034\u001a"+
		"\u0000\u0249\u024a\u0005\u0003\u0000\u0000\u024a-\u0001\u0000\u0000\u0000"+
		"\u024b\u024c\u00030\u0018\u0000\u024c/\u0001\u0000\u0000\u0000\u024d\u024e"+
		"\u0003:\u001d\u0000\u024e\u024f\u00036\u001b\u0000\u024f\u0250\u00038"+
		"\u001c\u0000\u0250\u0251\u0005\u000e\u0000\u0000\u0251\u0252\u0003H$\u0000"+
		"\u0252\u0256\u0005\u000f\u0000\u0000\u0253\u0255\u00057\u0000\u0000\u0254"+
		"\u0253\u0001\u0000\u0000\u0000\u0255\u0258\u0001\u0000\u0000\u0000\u0256"+
		"\u0254\u0001\u0000\u0000\u0000\u0256\u0257\u0001\u0000\u0000\u0000\u0257"+
		"\u026e\u0001\u0000\u0000\u0000\u0258\u0256\u0001\u0000\u0000\u0000\u0259"+
		"\u025a\u0003:\u001d\u0000\u025a\u025b\u00036\u001b\u0000\u025b\u025f\u0003"+
		"8\u001c\u0000\u025c\u025e\u00057\u0000\u0000\u025d\u025c\u0001\u0000\u0000"+
		"\u0000\u025e\u0261\u0001\u0000\u0000\u0000\u025f\u025d\u0001\u0000\u0000"+
		"\u0000\u025f\u0260\u0001\u0000\u0000\u0000\u0260\u026e\u0001\u0000\u0000"+
		"\u0000\u0261\u025f\u0001\u0000\u0000\u0000\u0262\u0263\u0003:\u001d\u0000"+
		"\u0263\u0264\u00038\u001c\u0000\u0264\u0265\u0005\u000e\u0000\u0000\u0265"+
		"\u0266\u0003H$\u0000\u0266\u026a\u0005\u000f\u0000\u0000\u0267\u0269\u0005"+
		"7\u0000\u0000\u0268\u0267\u0001\u0000\u0000\u0000\u0269\u026c\u0001\u0000"+
		"\u0000\u0000\u026a\u0268\u0001\u0000\u0000\u0000\u026a\u026b\u0001\u0000"+
		"\u0000\u0000\u026b\u026e\u0001\u0000\u0000\u0000\u026c\u026a\u0001\u0000"+
		"\u0000\u0000\u026d\u024d\u0001\u0000\u0000\u0000\u026d\u0259\u0001\u0000"+
		"\u0000\u0000\u026d\u0262\u0001\u0000\u0000\u0000\u026e1\u0001\u0000\u0000"+
		"\u0000\u026f\u0270\u0006\u0019\uffff\uffff\u0000\u0270\u0277\u0003<\u001e"+
		"\u0000\u0271\u0272\u0003L&\u0000\u0272\u0273\u0005\f\u0000\u0000\u0273"+
		"\u0274\u0003<\u001e\u0000\u0274\u0277\u0001\u0000\u0000\u0000\u0275\u0277"+
		"\u0001\u0000\u0000\u0000\u0276\u026f\u0001\u0000\u0000\u0000\u0276\u0271"+
		"\u0001\u0000\u0000\u0000\u0276\u0275\u0001\u0000\u0000\u0000\u0277\u0280"+
		"\u0001\u0000\u0000\u0000\u0278\u0279\n\u0004\u0000\u0000\u0279\u027f\u0003"+
		"<\u001e\u0000\u027a\u027b\n\u0003\u0000\u0000\u027b\u027f\u0003L&\u0000"+
		"\u027c\u027d\n\u0002\u0000\u0000\u027d\u027f\u0005\f\u0000\u0000\u027e"+
		"\u0278\u0001\u0000\u0000\u0000\u027e\u027a\u0001\u0000\u0000\u0000\u027e"+
		"\u027c\u0001\u0000\u0000\u0000\u027f\u0282\u0001\u0000\u0000\u0000\u0280"+
		"\u027e\u0001\u0000\u0000\u0000\u0280\u0281\u0001\u0000\u0000\u0000\u0281"+
		"3\u0001\u0000\u0000\u0000\u0282\u0280\u0001\u0000\u0000\u0000\u0283\u028b"+
		"\u00032\u0019\u0000\u0284\u0285\u00032\u0019\u0000\u0285\u0286\u0003\u0006"+
		"\u0003\u0000\u0286\u028b\u0001\u0000\u0000\u0000\u0287\u0288\u0003\u0006"+
		"\u0003\u0000\u0288\u0289\u00032\u0019\u0000\u0289\u028b\u0001\u0000\u0000"+
		"\u0000\u028a\u0283\u0001\u0000\u0000\u0000\u028a\u0284\u0001\u0000\u0000"+
		"\u0000\u028a\u0287\u0001\u0000\u0000\u0000\u028b5\u0001\u0000\u0000\u0000"+
		"\u028c\u028d\u0003B!\u0000\u028d\u028e\u0005\n\u0000\u0000\u028e\u028f"+
		"\u0005\u000b\u0000\u0000\u028f\u0293\u0001\u0000\u0000\u0000\u0290\u0293"+
		"\u0003B!\u0000\u0291\u0293\u0001\u0000\u0000\u0000\u0292\u028c\u0001\u0000"+
		"\u0000\u0000\u0292\u0290\u0001\u0000\u0000\u0000\u0292\u0291\u0001\u0000"+
		"\u0000\u0000\u02937\u0001\u0000\u0000\u0000\u0294\u0295\u0007\u0000\u0000"+
		"\u0000\u02959\u0001\u0000\u0000\u0000\u0296\u029a\u0005\u0010\u0000\u0000"+
		"\u0297\u029a\u0005\u0011\u0000\u0000\u0298\u029a\u0001\u0000\u0000\u0000"+
		"\u0299\u0296\u0001\u0000\u0000\u0000\u0299\u0297\u0001\u0000\u0000\u0000"+
		"\u0299\u0298\u0001\u0000\u0000\u0000\u029a;\u0001\u0000\u0000\u0000\u029b"+
		"\u029c\u0003:\u001d\u0000\u029c\u029d\u0003@ \u0000\u029d\u029e\u0003"+
		"F#\u0000\u029e\u029f\u0005\f\u0000\u0000\u029f\u02ac\u0001\u0000\u0000"+
		"\u0000\u02a0\u02a1\u0003:\u001d\u0000\u02a1\u02a2\u0003@ \u0000\u02a2"+
		"\u02a3\u0003F#\u0000\u02a3\u02ac\u0001\u0000\u0000\u0000\u02a4\u02a5\u0003"+
		"@ \u0000\u02a5\u02a6\u0003F#\u0000\u02a6\u02a7\u0005\f\u0000\u0000\u02a7"+
		"\u02ac\u0001\u0000\u0000\u0000\u02a8\u02a9\u0003@ \u0000\u02a9\u02aa\u0003"+
		"F#\u0000\u02aa\u02ac\u0001\u0000\u0000\u0000\u02ab\u029b\u0001\u0000\u0000"+
		"\u0000\u02ab\u02a0\u0001\u0000\u0000\u0000\u02ab\u02a4\u0001\u0000\u0000"+
		"\u0000\u02ab\u02a8\u0001\u0000\u0000\u0000\u02ac=\u0001\u0000\u0000\u0000"+
		"\u02ad\u02ae\u0003@ \u0000\u02ae\u02af\u0005E\u0000\u0000\u02af?\u0001"+
		"\u0000\u0000\u0000\u02b0\u02b1\u0003B!\u0000\u02b1\u02b2\u0005\n\u0000"+
		"\u0000\u02b2\u02b3\u0005\u000b\u0000\u0000\u02b3\u02b6\u0001\u0000\u0000"+
		"\u0000\u02b4\u02b6\u0003B!\u0000\u02b5\u02b0\u0001\u0000\u0000\u0000\u02b5"+
		"\u02b4\u0001\u0000\u0000\u0000\u02b6A\u0001\u0000\u0000\u0000\u02b7\u02bc"+
		"\u0005E\u0000\u0000\u02b8\u02b9\u0005E\u0000\u0000\u02b9\u02bc\u0003\u0012"+
		"\t\u0000\u02ba\u02bc\u0003D\"\u0000\u02bb\u02b7\u0001\u0000\u0000\u0000"+
		"\u02bb\u02b8\u0001\u0000\u0000\u0000\u02bb\u02ba\u0001\u0000\u0000\u0000"+
		"\u02bcC\u0001\u0000\u0000\u0000\u02bd\u02be\u0007\u0001\u0000\u0000\u02be"+
		"E\u0001\u0000\u0000\u0000\u02bf\u02c0\u0006#\uffff\uffff\u0000\u02c0\u02c5"+
		"\u0005E\u0000\u0000\u02c1\u02c2\u0005E\u0000\u0000\u02c2\u02c3\u0005I"+
		"\u0000\u0000\u02c3\u02c5\u0003L&\u0000\u02c4\u02bf\u0001\u0000\u0000\u0000"+
		"\u02c4\u02c1\u0001\u0000\u0000\u0000\u02c5\u02d0\u0001\u0000\u0000\u0000"+
		"\u02c6\u02c7\n\u0003\u0000\u0000\u02c7\u02c8\u0005\b\u0000\u0000\u02c8"+
		"\u02cf\u0005E\u0000\u0000\u02c9\u02ca\n\u0001\u0000\u0000\u02ca\u02cb"+
		"\u0005\b\u0000\u0000\u02cb\u02cc\u0005E\u0000\u0000\u02cc\u02cd\u0005"+
		"I\u0000\u0000\u02cd\u02cf\u0003L&\u0000\u02ce\u02c6\u0001\u0000\u0000"+
		"\u0000\u02ce\u02c9\u0001\u0000\u0000\u0000\u02cf\u02d2\u0001\u0000\u0000"+
		"\u0000\u02d0\u02ce\u0001\u0000\u0000\u0000\u02d0\u02d1\u0001\u0000\u0000"+
		"\u0000\u02d1G\u0001\u0000\u0000\u0000\u02d2\u02d0\u0001\u0000\u0000\u0000"+
		"\u02d3\u02d4\u0006$\uffff\uffff\u0000\u02d4\u02d7\u0003J%\u0000\u02d5"+
		"\u02d7\u0001\u0000\u0000\u0000\u02d6\u02d3\u0001\u0000\u0000\u0000\u02d6"+
		"\u02d5\u0001\u0000\u0000\u0000\u02d7\u02e2\u0001\u0000\u0000\u0000\u02d8"+
		"\u02d9\n\u0004\u0000\u0000\u02d9\u02da\u0005\b\u0000\u0000\u02da\u02e1"+
		"\u0003J%\u0000\u02db\u02dc\n\u0003\u0000\u0000\u02dc\u02e1\u0005:\u0000"+
		"\u0000\u02dd\u02de\n\u0002\u0000\u0000\u02de\u02df\u0005\b\u0000\u0000"+
		"\u02df\u02e1\u0005:\u0000\u0000\u02e0\u02d8\u0001\u0000\u0000\u0000\u02e0"+
		"\u02db\u0001\u0000\u0000\u0000\u02e0\u02dd\u0001\u0000\u0000\u0000\u02e1"+
		"\u02e4\u0001\u0000\u0000\u0000\u02e2\u02e0\u0001\u0000\u0000\u0000\u02e2"+
		"\u02e3\u0001\u0000\u0000\u0000\u02e3I\u0001\u0000\u0000\u0000\u02e4\u02e2"+
		"\u0001\u0000\u0000\u0000\u02e5\u02e6\u0003@ \u0000\u02e6\u02e7\u0005E"+
		"\u0000\u0000\u02e7K\u0001\u0000\u0000\u0000\u02e8\u02f6\u0003N\'\u0000"+
		"\u02e9\u02f6\u0003V+\u0000\u02ea\u02f6\u0003`0\u0000\u02eb\u02f6\u0003"+
		"d2\u0000\u02ec\u02f6\u0003f3\u0000\u02ed\u02f6\u0003h4\u0000\u02ee\u02f6"+
		"\u0003j5\u0000\u02ef\u02f6\u0003l6\u0000\u02f0\u02f6\u00050\u0000\u0000"+
		"\u02f1\u02f6\u00051\u0000\u0000\u02f2\u02f3\u00052\u0000\u0000\u02f3\u02f6"+
		"\u0003L&\u0000\u02f4\u02f6\u00052\u0000\u0000\u02f5\u02e8\u0001\u0000"+
		"\u0000\u0000\u02f5\u02e9\u0001\u0000\u0000\u0000\u02f5\u02ea\u0001\u0000"+
		"\u0000\u0000\u02f5\u02eb\u0001\u0000\u0000\u0000\u02f5\u02ec\u0001\u0000"+
		"\u0000\u0000\u02f5\u02ed\u0001\u0000\u0000\u0000\u02f5\u02ee\u0001\u0000"+
		"\u0000\u0000\u02f5\u02ef\u0001\u0000\u0000\u0000\u02f5\u02f0\u0001\u0000"+
		"\u0000\u0000\u02f5\u02f1\u0001\u0000\u0000\u0000\u02f5\u02f2\u0001\u0000"+
		"\u0000\u0000\u02f5\u02f4\u0001\u0000\u0000\u0000\u02f6M\u0001\u0000\u0000"+
		"\u0000\u02f7\u02f8\u0006\'\uffff\uffff\u0000\u02f8\u02fd\u0003P(\u0000"+
		"\u02f9\u02fa\u0005=\u0000\u0000\u02fa\u02fc\u0003P(\u0000\u02fb\u02f9"+
		"\u0001\u0000\u0000\u0000\u02fc\u02ff\u0001\u0000\u0000\u0000\u02fd\u02fb"+
		"\u0001\u0000\u0000\u0000\u02fd\u02fe\u0001\u0000\u0000\u0000\u02fe\u0302"+
		"\u0001\u0000\u0000\u0000\u02ff\u02fd\u0001\u0000\u0000\u0000\u0300\u0302"+
		"\u0003d2\u0000\u0301\u02f7\u0001\u0000\u0000\u0000\u0301\u0300\u0001\u0000"+
		"\u0000\u0000\u0302\u0308\u0001\u0000\u0000\u0000\u0303\u0304\n\u0001\u0000"+
		"\u0000\u0304\u0305\u0005I\u0000\u0000\u0305\u0307\u0003L&\u0000\u0306"+
		"\u0303\u0001\u0000\u0000\u0000\u0307\u030a\u0001\u0000\u0000\u0000\u0308"+
		"\u0306\u0001\u0000\u0000\u0000\u0308\u0309\u0001\u0000\u0000\u0000\u0309"+
		"O\u0001\u0000\u0000\u0000\u030a\u0308\u0001\u0000\u0000\u0000\u030b\u030c"+
		"\u0006(\uffff\uffff\u0000\u030c\u0311\u0003R)\u0000\u030d\u030e\u0005"+
		">\u0000\u0000\u030e\u0310\u0003R)\u0000\u030f\u030d\u0001\u0000\u0000"+
		"\u0000\u0310\u0313\u0001\u0000\u0000\u0000\u0311\u030f\u0001\u0000\u0000"+
		"\u0000\u0311\u0312\u0001\u0000\u0000\u0000\u0312\u0319\u0001\u0000\u0000"+
		"\u0000\u0313\u0311\u0001\u0000\u0000\u0000\u0314\u0315\n\u0002\u0000\u0000"+
		"\u0315\u0316\u00059\u0000\u0000\u0316\u0318\u0003T*\u0000\u0317\u0314"+
		"\u0001\u0000\u0000\u0000\u0318\u031b\u0001\u0000\u0000\u0000\u0319\u0317"+
		"\u0001\u0000\u0000\u0000\u0319\u031a\u0001\u0000\u0000\u0000\u031aQ\u0001"+
		"\u0000\u0000\u0000\u031b\u0319\u0001\u0000\u0000\u0000\u031c\u031d\u0005"+
		"=\u0000\u0000\u031d\u0320\u0003T*\u0000\u031e\u0320\u0003T*\u0000\u031f"+
		"\u031c\u0001\u0000\u0000\u0000\u031f\u031e\u0001\u0000\u0000\u0000\u0320"+
		"S\u0001\u0000\u0000\u0000\u0321\u0322\u0006*\uffff\uffff\u0000\u0322\u0323"+
		"\u00054\u0000\u0000\u0323\u034b\u0003^/\u0000\u0324\u0325\u00054\u0000"+
		"\u0000\u0325\u0326\u0003B!\u0000\u0326\u0327\u0005\n\u0000\u0000\u0327"+
		"\u0328\u0003L&\u0000\u0328\u0329\u0005\u000b\u0000\u0000\u0329\u034b\u0001"+
		"\u0000\u0000\u0000\u032a\u032b\u00054\u0000\u0000\u032b\u032c\u0005E\u0000"+
		"\u0000\u032c\u032d\u0003\u0012\t\u0000\u032d\u032e\u0005\u000e\u0000\u0000"+
		"\u032e\u032f\u0003t:\u0000\u032f\u0330\u0005\u000f\u0000\u0000\u0330\u034b"+
		"\u0001\u0000\u0000\u0000\u0331\u0332\u0003D\"\u0000\u0332\u0333\u0005"+
		"\u0016\u0000\u0000\u0333\u0334\u0003^/\u0000\u0334\u034b\u0001\u0000\u0000"+
		"\u0000\u0335\u034b\u0003p8\u0000\u0336\u034b\u0003^/\u0000\u0337\u034b"+
		"\u0005E\u0000\u0000\u0338\u0339\u0005D\u0000\u0000\u0339\u034b\u0003T"+
		"*\n\u033a\u034b\u0003r9\u0000\u033b\u033c\u0005\u000e\u0000\u0000\u033c"+
		"\u033d\u0003N\'\u0000\u033d\u033e\u0005\u000f\u0000\u0000\u033e\u034b"+
		"\u0001\u0000\u0000\u0000\u033f\u0340\u0005D\u0000\u0000\u0340\u0341\u0003"+
		"L&\u0000\u0341\u0342\u0005\n\u0000\u0000\u0342\u0343\u0003L&\u0000\u0343"+
		"\u0344\u0005\u000b\u0000\u0000\u0344\u034b\u0001\u0000\u0000\u0000\u0345"+
		"\u0346\u0005D\u0000\u0000\u0346\u0347\u0003L&\u0000\u0347\u0348\u0005"+
		"\u0016\u0000\u0000\u0348\u0349\u0005E\u0000\u0000\u0349\u034b\u0001\u0000"+
		"\u0000\u0000\u034a\u0321\u0001\u0000\u0000\u0000\u034a\u0324\u0001\u0000"+
		"\u0000\u0000\u034a\u032a\u0001\u0000\u0000\u0000\u034a\u0331\u0001\u0000"+
		"\u0000\u0000\u034a\u0335\u0001\u0000\u0000\u0000\u034a\u0336\u0001\u0000"+
		"\u0000\u0000\u034a\u0337\u0001\u0000\u0000\u0000\u034a\u0338\u0001\u0000"+
		"\u0000\u0000\u034a\u033a\u0001\u0000\u0000\u0000\u034a\u033b\u0001\u0000"+
		"\u0000\u0000\u034a\u033f\u0001\u0000\u0000\u0000\u034a\u0345\u0001\u0000"+
		"\u0000\u0000\u034b\u036d\u0001\u0000\u0000\u0000\u034c\u034d\n\u0011\u0000"+
		"\u0000\u034d\u034e\u0005\u0016\u0000\u0000\u034e\u036c\u0003^/\u0000\u034f"+
		"\u0350\n\u000f\u0000\u0000\u0350\u0351\u0005\u0016\u0000\u0000\u0351\u036c"+
		"\u0005E\u0000\u0000\u0352\u0353\n\u000b\u0000\u0000\u0353\u036c\u0005"+
		"D\u0000\u0000\u0354\u0355\n\u0007\u0000\u0000\u0355\u0356\u0005\n\u0000"+
		"\u0000\u0356\u0357\u0003L&\u0000\u0357\u0358\u0005\u000b\u0000\u0000\u0358"+
		"\u036c\u0001\u0000\u0000\u0000\u0359\u035a\n\u0006\u0000\u0000\u035a\u035b"+
		"\u0005\n\u0000\u0000\u035b\u035c\u0003L&\u0000\u035c\u035d\u0005\u000b"+
		"\u0000\u0000\u035d\u035e\u0005D\u0000\u0000\u035e\u036c\u0001\u0000\u0000"+
		"\u0000\u035f\u0360\n\u0003\u0000\u0000\u0360\u0361\u0005\u0016\u0000\u0000"+
		"\u0361\u0362\u0005E\u0000\u0000\u0362\u036c\u0005D\u0000\u0000\u0363\u0364"+
		"\n\u0002\u0000\u0000\u0364\u0365\u0005\u0016\u0000\u0000\u0365\u036c\u0005"+
		"5\u0000\u0000\u0366\u0367\n\u0001\u0000\u0000\u0367\u0368\u0005\u0016"+
		"\u0000\u0000\u0368\u0369\u00055\u0000\u0000\u0369\u036a\u0005\u000e\u0000"+
		"\u0000\u036a\u036c\u0005\u000f\u0000\u0000\u036b\u034c\u0001\u0000\u0000"+
		"\u0000\u036b\u034f\u0001\u0000\u0000\u0000\u036b\u0352\u0001\u0000\u0000"+
		"\u0000\u036b\u0354\u0001\u0000\u0000\u0000\u036b\u0359\u0001\u0000\u0000"+
		"\u0000\u036b\u035f\u0001\u0000\u0000\u0000\u036b\u0363\u0001\u0000\u0000"+
		"\u0000\u036b\u0366\u0001\u0000\u0000\u0000\u036c\u036f\u0001\u0000\u0000"+
		"\u0000\u036d\u036b\u0001\u0000\u0000\u0000\u036d\u036e\u0001\u0000\u0000"+
		"\u0000\u036eU\u0001\u0000\u0000\u0000\u036f\u036d\u0001\u0000\u0000\u0000"+
		"\u0370\u0371\u0006+\uffff\uffff\u0000\u0371\u0376\u0003X,\u0000\u0372"+
		"\u0373\u0005;\u0000\u0000\u0373\u0375\u0003X,\u0000\u0374\u0372\u0001"+
		"\u0000\u0000\u0000\u0375\u0378\u0001\u0000\u0000\u0000\u0376\u0374\u0001"+
		"\u0000\u0000\u0000\u0376\u0377\u0001\u0000\u0000\u0000\u0377\u0385\u0001"+
		"\u0000\u0000\u0000\u0378\u0376\u0001\u0000\u0000\u0000\u0379\u0385\u0003"+
		"d2\u0000\u037a\u037b\u0003N\'\u0000\u037b\u037c\u0007\u0002\u0000\u0000"+
		"\u037c\u037d\u0007\u0003\u0000\u0000\u037d\u037e\u0003N\'\u0000\u037e"+
		"\u0385\u0001\u0000\u0000\u0000\u037f\u0380\u0003N\'\u0000\u0380\u0381"+
		"\u0007\u0004\u0000\u0000\u0381\u0382\u0003N\'\u0000\u0382\u0385\u0001"+
		"\u0000\u0000\u0000\u0383\u0385\u0003N\'\u0000\u0384\u0370\u0001\u0000"+
		"\u0000\u0000\u0384\u0379\u0001\u0000\u0000\u0000\u0384\u037a\u0001\u0000"+
		"\u0000\u0000\u0384\u037f\u0001\u0000\u0000\u0000\u0384\u0383\u0001\u0000"+
		"\u0000\u0000\u0385\u038e\u0001\u0000\u0000\u0000\u0386\u0387\n\u0005\u0000"+
		"\u0000\u0387\u0388\u0007\u0005\u0000\u0000\u0388\u038d\u0003V+\u0006\u0389"+
		"\u038a\n\u0002\u0000\u0000\u038a\u038b\u0005I\u0000\u0000\u038b\u038d"+
		"\u0003L&\u0000\u038c\u0386\u0001\u0000\u0000\u0000\u038c\u0389\u0001\u0000"+
		"\u0000\u0000\u038d\u0390\u0001\u0000\u0000\u0000\u038e\u038c\u0001\u0000"+
		"\u0000\u0000\u038e\u038f\u0001\u0000\u0000\u0000\u038fW\u0001\u0000\u0000"+
		"\u0000\u0390\u038e\u0001\u0000\u0000\u0000\u0391\u0396\u0003Z-\u0000\u0392"+
		"\u0393\u0005<\u0000\u0000\u0393\u0395\u0003Z-\u0000\u0394\u0392\u0001"+
		"\u0000\u0000\u0000\u0395\u0398\u0001\u0000\u0000\u0000\u0396\u0394\u0001"+
		"\u0000\u0000\u0000\u0396\u0397\u0001\u0000\u0000\u0000\u0397Y\u0001\u0000"+
		"\u0000\u0000\u0398\u0396\u0001\u0000\u0000\u0000\u0399\u039a\u0005C\u0000"+
		"\u0000\u039a\u039d\u0003V+\u0000\u039b\u039d\u0003\\.\u0000\u039c\u0399"+
		"\u0001\u0000\u0000\u0000\u039c\u039b\u0001\u0000\u0000\u0000\u039d[\u0001"+
		"\u0000\u0000\u0000\u039e\u03a6\u0003r9\u0000\u039f\u03a6\u0005E\u0000"+
		"\u0000\u03a0\u03a6\u0003p8\u0000\u03a1\u03a2\u0005\u000e\u0000\u0000\u03a2"+
		"\u03a3\u0003V+\u0000\u03a3\u03a4\u0005\u000f\u0000\u0000\u03a4\u03a6\u0001"+
		"\u0000\u0000\u0000\u03a5\u039e\u0001\u0000\u0000\u0000\u03a5\u039f\u0001"+
		"\u0000\u0000\u0000\u03a5\u03a0\u0001\u0000\u0000\u0000\u03a5\u03a1\u0001"+
		"\u0000\u0000\u0000\u03a6]\u0001\u0000\u0000\u0000\u03a7\u03a8\u00038\u001c"+
		"\u0000\u03a8\u03a9\u0005\u000e\u0000\u0000\u03a9\u03aa\u0003t:\u0000\u03aa"+
		"\u03ab\u0005\u000f\u0000\u0000\u03ab\u03b2\u0001\u0000\u0000\u0000\u03ac"+
		"\u03ad\u0003B!\u0000\u03ad\u03ae\u0005\u000e\u0000\u0000\u03ae\u03af\u0003"+
		"t:\u0000\u03af\u03b0\u0005\u000f\u0000\u0000\u03b0\u03b2\u0001\u0000\u0000"+
		"\u0000\u03b1\u03a7\u0001\u0000\u0000\u0000\u03b1\u03ac\u0001\u0000\u0000"+
		"\u0000\u03b2_\u0001\u0000\u0000\u0000\u03b3\u03b4\u0005\u0002\u0000\u0000"+
		"\u03b4\u03b5\u00032\u0019\u0000\u03b5\u03b6\u0005\u0003\u0000\u0000\u03b6"+
		"\u03ba\u0001\u0000\u0000\u0000\u03b7\u03b8\u0005\u0002\u0000\u0000\u03b8"+
		"\u03ba\u0005\u0003\u0000\u0000\u03b9\u03b3\u0001\u0000\u0000\u0000\u03b9"+
		"\u03b7\u0001\u0000\u0000\u0000\u03baa\u0001\u0000\u0000\u0000\u03bb\u03be"+
		"\u0003L&\u0000\u03bc\u03be\u0001\u0000\u0000\u0000\u03bd\u03bb\u0001\u0000"+
		"\u0000\u0000\u03bd\u03bc\u0001\u0000\u0000\u0000\u03bec\u0001\u0000\u0000"+
		"\u0000\u03bf\u03c0\u0005#\u0000\u0000\u03c0\u03c1\u0005\u000e\u0000\u0000"+
		"\u03c1\u03c2\u0003L&\u0000\u03c2\u03c3\u0005\u000f\u0000\u0000\u03c3\u03c4"+
		"\u0003L&\u0000\u03c4\u03ce\u0001\u0000\u0000\u0000\u03c5\u03c6\u0005#"+
		"\u0000\u0000\u03c6\u03c7\u0005\u000e\u0000\u0000\u03c7\u03c8\u0003L&\u0000"+
		"\u03c8\u03c9\u0005\u000f\u0000\u0000\u03c9\u03ca\u0003L&\u0000\u03ca\u03cb"+
		"\u0005$\u0000\u0000\u03cb\u03cc\u0003L&\u0000\u03cc\u03ce\u0001\u0000"+
		"\u0000\u0000\u03cd\u03bf\u0001\u0000\u0000\u0000\u03cd\u03c5\u0001\u0000"+
		"\u0000\u0000\u03cee\u0001\u0000\u0000\u0000\u03cf\u03d0\u0005%\u0000\u0000"+
		"\u03d0\u03d1\u0005\u000e\u0000\u0000\u03d1\u03d2\u0003L&\u0000\u03d2\u03d3"+
		"\u0005\f\u0000\u0000\u03d3\u03d4\u0003L&\u0000\u03d4\u03d5\u0005\f\u0000"+
		"\u0000\u03d5\u03d6\u0003L&\u0000\u03d6\u03d7\u0005\u000f\u0000\u0000\u03d7"+
		"\u03d8\u0003L&\u0000\u03d8\u03f3\u0001\u0000\u0000\u0000\u03d9\u03da\u0005"+
		"%\u0000\u0000\u03da\u03db\u0005\u000e\u0000\u0000\u03db\u03dc\u0003<\u001e"+
		"\u0000\u03dc\u03dd\u0003L&\u0000\u03dd\u03de\u0005\f\u0000\u0000\u03de"+
		"\u03df\u0003L&\u0000\u03df\u03e0\u0005\u000f\u0000\u0000\u03e0\u03e1\u0003"+
		"L&\u0000\u03e1\u03f3\u0001\u0000\u0000\u0000\u03e2\u03e3\u0005%\u0000"+
		"\u0000\u03e3\u03e4\u0005\u000e\u0000\u0000\u03e4\u03e5\u0005E\u0000\u0000"+
		"\u03e5\u03e6\u0005&\u0000\u0000\u03e6\u03e7\u0003L&\u0000\u03e7\u03e8"+
		"\u0005\u000f\u0000\u0000\u03e8\u03e9\u0003L&\u0000\u03e9\u03f3\u0001\u0000"+
		"\u0000\u0000\u03ea\u03eb\u0005%\u0000\u0000\u03eb\u03ec\u0005\u000e\u0000"+
		"\u0000\u03ec\u03ed\u0003>\u001f\u0000\u03ed\u03ee\u0005&\u0000\u0000\u03ee"+
		"\u03ef\u0003L&\u0000\u03ef\u03f0\u0005\u000f\u0000\u0000\u03f0\u03f1\u0003"+
		"L&\u0000\u03f1\u03f3\u0001\u0000\u0000\u0000\u03f2\u03cf\u0001\u0000\u0000"+
		"\u0000\u03f2\u03d9\u0001\u0000\u0000\u0000\u03f2\u03e2\u0001\u0000\u0000"+
		"\u0000\u03f2\u03ea\u0001\u0000\u0000\u0000\u03f3g\u0001\u0000\u0000\u0000"+
		"\u03f4\u03f5\u0005\'\u0000\u0000\u03f5\u03f6\u0005\u000e\u0000\u0000\u03f6"+
		"\u03f7\u0003L&\u0000\u03f7\u03f8\u0005\u000f\u0000\u0000\u03f8\u03f9\u0003"+
		"L&\u0000\u03f9i\u0001\u0000\u0000\u0000\u03fa\u03fb\u0005(\u0000\u0000"+
		"\u03fb\u03fc\u0003L&\u0000\u03fc\u03fd\u0005\'\u0000\u0000\u03fd\u03fe"+
		"\u0005\u000e\u0000\u0000\u03fe\u03ff\u0003L&\u0000\u03ff\u0400\u0005\u000f"+
		"\u0000\u0000\u0400k\u0001\u0000\u0000\u0000\u0401\u0402\u0005-\u0000\u0000"+
		"\u0402\u0403\u0005\u000e\u0000\u0000\u0403\u0404\u0003L&\u0000\u0404\u0405"+
		"\u0005\u000f\u0000\u0000\u0405\u0409\u0005\u0002\u0000\u0000\u0406\u0408"+
		"\u0003n7\u0000\u0407\u0406\u0001\u0000\u0000\u0000\u0408\u040b\u0001\u0000"+
		"\u0000\u0000\u0409\u0407\u0001\u0000\u0000\u0000\u0409\u040a\u0001\u0000"+
		"\u0000\u0000\u040a\u040c\u0001\u0000\u0000\u0000\u040b\u0409\u0001\u0000"+
		"\u0000\u0000\u040c\u040d\u0005\u0003\u0000\u0000\u040dm\u0001\u0000\u0000"+
		"\u0000\u040e\u040f\u0005.\u0000\u0000\u040f\u0412\u0003r9\u0000\u0410"+
		"\u0412\u0005/\u0000\u0000\u0411\u040e\u0001\u0000\u0000\u0000\u0411\u0410"+
		"\u0001\u0000\u0000\u0000\u0412\u0413\u0001\u0000\u0000\u0000\u0413\u0414"+
		"\u0005&\u0000\u0000\u0414\u0415\u00032\u0019\u0000\u0415o\u0001\u0000"+
		"\u0000\u0000\u0416\u0417\u00056\u0000\u0000\u0417q\u0001\u0000\u0000\u0000"+
		"\u0418\u0419\u0007\u0006\u0000\u0000\u0419s\u0001\u0000\u0000\u0000\u041a"+
		"\u041b\u0006:\uffff\uffff\u0000\u041b\u041e\u0003L&\u0000\u041c\u041e"+
		"\u0001\u0000\u0000\u0000\u041d\u041a\u0001\u0000\u0000\u0000\u041d\u041c"+
		"\u0001\u0000\u0000\u0000\u041e\u0424\u0001\u0000\u0000\u0000\u041f\u0420"+
		"\n\u0002\u0000\u0000\u0420\u0421\u0005\b\u0000\u0000\u0421\u0423\u0003"+
		"L&\u0000\u0422\u041f\u0001\u0000\u0000\u0000\u0423\u0426\u0001\u0000\u0000"+
		"\u0000\u0424\u0422\u0001\u0000\u0000\u0000\u0424\u0425\u0001\u0000\u0000"+
		"\u0000\u0425u\u0001\u0000\u0000\u0000\u0426\u0424\u0001\u0000\u0000\u0000"+
		"Xz\u0081\u0087\u008d\u0094\u00a1\u00b0\u00bc\u00ca\u00d6\u00df\u00ee\u00f6"+
		"\u00ff\u0101\u010a\u0115\u011d\u0122\u0128\u0130\u017e\u0183\u018c\u0190"+
		"\u019b\u019e\u01a0\u01ab\u01ad\u01f8\u0202\u0209\u020c\u0218\u0220\u0223"+
		"\u0225\u022b\u0231\u0237\u0241\u0243\u0256\u025f\u026a\u026d\u0276\u027e"+
		"\u0280\u028a\u0292\u0299\u02ab\u02b5\u02bb\u02c4\u02ce\u02d0\u02d6\u02e0"+
		"\u02e2\u02f5\u02fd\u0301\u0308\u0311\u0319\u031f\u034a\u036b\u036d\u0376"+
		"\u0384\u038c\u038e\u0396\u039c\u03a5\u03b1\u03b9\u03bd\u03cd\u03f2\u0409"+
		"\u0411\u041d\u0424";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}