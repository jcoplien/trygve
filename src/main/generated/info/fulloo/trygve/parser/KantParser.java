// Generated from info/fulloo/trygve/parser/Kant.g4 by ANTLR 4.5.1

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
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, STRING=35, INTEGER=36, FLOAT=37, BOOLEAN=38, 
		SWITCH=39, CASE=40, DEFAULT=41, BREAK=42, CONTINUE=43, RETURN=44, REQUIRES=45, 
		NEW=46, CLONE=47, NULL=48, CONST=49, POW=50, BOOLEAN_SUMOP=51, BOOLEAN_MULOP=52, 
		ABELIAN_SUMOP=53, ABELIAN_MULOP=54, MINUS=55, PLUS=56, LT=57, GT=58, LOGICAL_NEGATION=59, 
		ABELIAN_INCREMENT_OP=60, JAVA_ID=61, INLINE_COMMENT=62, C_COMMENT=63, 
		WHITESPACE=64, ASSIGN=65;
	public static final int
		RULE_program = 0, RULE_main = 1, RULE_type_declaration_list = 2, RULE_type_declaration = 3, 
		RULE_context_declaration = 4, RULE_class_declaration = 5, RULE_interface_declaration = 6, 
		RULE_implements_list = 7, RULE_type_parameters = 8, RULE_type_list = 9, 
		RULE_type_parameter = 10, RULE_context_body = 11, RULE_context_body_element = 12, 
		RULE_role_decl = 13, RULE_role_vec_modifier = 14, RULE_role_body = 15, 
		RULE_self_methods = 16, RULE_stageprop_decl = 17, RULE_stageprop_body = 18, 
		RULE_class_body = 19, RULE_class_body_element = 20, RULE_interface_body = 21, 
		RULE_method_decl = 22, RULE_method_decl_hook = 23, RULE_method_signature = 24, 
		RULE_expr_and_decl_list = 25, RULE_return_type = 26, RULE_method_name = 27, 
		RULE_access_qualifier = 28, RULE_object_decl = 29, RULE_trivial_object_decl = 30, 
		RULE_compound_type_name = 31, RULE_type_name = 32, RULE_builtin_type_name = 33, 
		RULE_identifier_list = 34, RULE_param_list = 35, RULE_param_decl = 36, 
		RULE_expr = 37, RULE_abelian_expr = 38, RULE_abelian_product = 39, RULE_abelian_unary_op = 40, 
		RULE_abelian_atom = 41, RULE_boolean_expr = 42, RULE_boolean_product = 43, 
		RULE_boolean_unary_op = 44, RULE_boolean_atom = 45, RULE_message = 46, 
		RULE_block = 47, RULE_expr_or_null = 48, RULE_if_expr = 49, RULE_for_expr = 50, 
		RULE_while_expr = 51, RULE_do_while_expr = 52, RULE_switch_expr = 53, 
		RULE_switch_body = 54, RULE_null_expr = 55, RULE_constant = 56, RULE_argument_list = 57;
	public static final String[] ruleNames = {
		"program", "main", "type_declaration_list", "type_declaration", "context_declaration", 
		"class_declaration", "interface_declaration", "implements_list", "type_parameters", 
		"type_list", "type_parameter", "context_body", "context_body_element", 
		"role_decl", "role_vec_modifier", "role_body", "self_methods", "stageprop_decl", 
		"stageprop_body", "class_body", "class_body_element", "interface_body", 
		"method_decl", "method_decl_hook", "method_signature", "expr_and_decl_list", 
		"return_type", "method_name", "access_qualifier", "object_decl", "trivial_object_decl", 
		"compound_type_name", "type_name", "builtin_type_name", "identifier_list", 
		"param_list", "param_decl", "expr", "abelian_expr", "abelian_product", 
		"abelian_unary_op", "abelian_atom", "boolean_expr", "boolean_product", 
		"boolean_unary_op", "boolean_atom", "message", "block", "expr_or_null", 
		"if_expr", "for_expr", "while_expr", "do_while_expr", "switch_expr", "switch_body", 
		"null_expr", "constant", "argument_list"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'context'", "'{'", "'}'", "'class'", "'extends'", "'interface'", 
		"'implements'", "','", "'role'", "'['", "']'", "';'", "'stageprop'", "'('", 
		"')'", "'public'", "'private'", "'int'", "'double'", "'char'", "'String'", 
		"'.'", "'||'", "'^'", "'!='", "'=='", "'>='", "'<='", "'if'", "'else'", 
		"'for'", "':'", "'while'", "'do'", null, null, null, null, "'switch'", 
		"'case'", "'default'", "'break'", "'continue'", "'return'", "'requires'", 
		"'new'", "'clone'", "'null'", "'const'", "'**'", null, "'&&'", null, null, 
		"'-'", "'+'", "'<'", "'>'", "'!'", null, null, null, null, null, "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, "STRING", 
		"INTEGER", "FLOAT", "BOOLEAN", "SWITCH", "CASE", "DEFAULT", "BREAK", "CONTINUE", 
		"RETURN", "REQUIRES", "NEW", "CLONE", "NULL", "CONST", "POW", "BOOLEAN_SUMOP", 
		"BOOLEAN_MULOP", "ABELIAN_SUMOP", "ABELIAN_MULOP", "MINUS", "PLUS", "LT", 
		"GT", "LOGICAL_NEGATION", "ABELIAN_INCREMENT_OP", "JAVA_ID", "INLINE_COMMENT", 
		"C_COMMENT", "WHITESPACE", "ASSIGN"
	};
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
			setState(120);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(116);
				type_declaration_list(0);
				setState(117);
				main();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(119);
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
			setState(122);
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
			setState(127);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(125);
				type_declaration();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(133);
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
					setState(129);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(130);
					type_declaration();
					}
					} 
				}
				setState(135);
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
			setState(139);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(136);
				context_declaration();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(137);
				class_declaration();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 3);
				{
				setState(138);
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(T__0);
			setState(142);
			match(JAVA_ID);
			setState(143);
			match(T__1);
			setState(144);
			context_body(0);
			setState(145);
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
			setState(215);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(147);
				match(T__3);
				setState(148);
				match(JAVA_ID);
				setState(149);
				type_parameters();
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(150);
					implements_list(0);
					}
					}
					setState(155);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(156);
				match(T__1);
				setState(157);
				class_body(0);
				setState(158);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(160);
				match(T__3);
				setState(161);
				match(JAVA_ID);
				setState(162);
				type_parameters();
				setState(163);
				match(T__4);
				setState(164);
				match(JAVA_ID);
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(165);
					implements_list(0);
					}
					}
					setState(170);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(171);
				match(T__1);
				setState(172);
				class_body(0);
				setState(173);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(175);
				match(T__3);
				setState(176);
				match(JAVA_ID);
				setState(180);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(177);
					implements_list(0);
					}
					}
					setState(182);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(183);
				match(T__1);
				setState(184);
				class_body(0);
				setState(185);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(187);
				match(T__3);
				setState(188);
				match(JAVA_ID);
				setState(189);
				match(T__4);
				setState(190);
				match(JAVA_ID);
				setState(194);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(191);
					implements_list(0);
					}
					}
					setState(196);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(197);
				match(T__1);
				setState(198);
				class_body(0);
				setState(199);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(201);
				match(T__3);
				setState(202);
				match(JAVA_ID);
				setState(206);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(203);
					implements_list(0);
					}
					}
					setState(208);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(209);
				match(T__4);
				setState(210);
				match(JAVA_ID);
				setState(211);
				match(T__1);
				setState(212);
				class_body(0);
				setState(213);
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
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			match(T__5);
			setState(218);
			match(JAVA_ID);
			setState(219);
			match(T__1);
			setState(220);
			interface_body(0);
			setState(221);
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

	public static class Implements_listContext extends ParserRuleContext {
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
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
			{
			setState(224);
			match(T__6);
			setState(225);
			match(JAVA_ID);
			}
			_ctx.stop = _input.LT(-1);
			setState(232);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Implements_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_implements_list);
					setState(227);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(228);
					match(T__7);
					setState(229);
					match(JAVA_ID);
					}
					} 
				}
				setState(234);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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
		public List<Type_parameterContext> type_parameter() {
			return getRuleContexts(Type_parameterContext.class);
		}
		public Type_parameterContext type_parameter(int i) {
			return getRuleContext(Type_parameterContext.class,i);
		}
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
			setState(235);
			match(LT);
			setState(236);
			type_parameter();
			setState(241);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(237);
				match(T__7);
				setState(238);
				type_parameter();
				}
				}
				setState(243);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(244);
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
		public List<Type_nameContext> type_name() {
			return getRuleContexts(Type_nameContext.class);
		}
		public Type_nameContext type_name(int i) {
			return getRuleContext(Type_nameContext.class,i);
		}
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
			setState(246);
			match(LT);
			setState(247);
			type_name();
			setState(252);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(248);
				match(T__7);
				setState(249);
				type_name();
				}
				}
				setState(254);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(255);
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
			setState(257);
			type_name();
			setState(260);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(258);
				match(T__4);
				setState(259);
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
			setState(265);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(263);
				context_body_element();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(271);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Context_bodyContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_context_body);
					setState(267);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(268);
					context_body_element();
					}
					} 
				}
				setState(273);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
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
			setState(278);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(274);
				method_decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(275);
				object_decl();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(276);
				role_decl();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(277);
				stageprop_decl();
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
			setState(356);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(280);
				match(T__8);
				setState(281);
				role_vec_modifier();
				setState(282);
				match(JAVA_ID);
				setState(283);
				match(T__1);
				setState(284);
				role_body(0);
				setState(285);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(287);
				match(T__8);
				setState(288);
				role_vec_modifier();
				setState(289);
				match(JAVA_ID);
				setState(290);
				match(T__1);
				setState(291);
				role_body(0);
				setState(292);
				match(T__2);
				setState(293);
				match(REQUIRES);
				setState(294);
				match(T__1);
				setState(295);
				self_methods(0);
				setState(296);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(298);
				access_qualifier();
				setState(299);
				match(T__8);
				setState(300);
				role_vec_modifier();
				setState(301);
				match(JAVA_ID);
				setState(302);
				match(T__1);
				setState(303);
				role_body(0);
				setState(304);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(306);
				access_qualifier();
				setState(307);
				match(T__8);
				setState(308);
				role_vec_modifier();
				setState(309);
				match(JAVA_ID);
				setState(310);
				match(T__1);
				setState(311);
				role_body(0);
				setState(312);
				match(T__2);
				setState(313);
				match(REQUIRES);
				setState(314);
				match(T__1);
				setState(315);
				self_methods(0);
				setState(316);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(318);
				match(T__8);
				setState(319);
				role_vec_modifier();
				setState(320);
				match(JAVA_ID);
				setState(321);
				match(T__1);
				setState(322);
				role_body(0);
				setState(323);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
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
				setState(331);
				match(REQUIRES);
				setState(332);
				match(T__1);
				setState(333);
				self_methods(0);
				setState(334);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(336);
				access_qualifier();
				setState(337);
				match(T__8);
				setState(338);
				role_vec_modifier();
				setState(339);
				match(JAVA_ID);
				setState(340);
				match(T__1);
				setState(341);
				role_body(0);
				setState(342);
				match(T__2);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(344);
				access_qualifier();
				setState(345);
				match(T__8);
				setState(346);
				role_vec_modifier();
				setState(347);
				match(JAVA_ID);
				setState(348);
				match(T__1);
				setState(349);
				role_body(0);
				setState(350);
				match(T__2);
				setState(351);
				match(REQUIRES);
				setState(352);
				match(T__1);
				setState(353);
				self_methods(0);
				setState(354);
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
			setState(361);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(358);
				match(T__9);
				setState(359);
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
			setState(367);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(364);
				method_decl();
				}
				break;
			case 2:
				{
				setState(365);
				object_decl();
				}
				break;
			case 3:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(375);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(373);
					switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
					case 1:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(369);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(370);
						method_decl();
						}
						break;
					case 2:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(371);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(372);
						object_decl();
						}
						break;
					}
					} 
				}
				setState(377);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
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
			setState(379);
			method_signature();
			}
			_ctx.stop = _input.LT(-1);
			setState(388);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(386);
					switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
					case 1:
						{
						_localctx = new Self_methodsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_self_methods);
						setState(381);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(382);
						match(T__11);
						setState(383);
						method_signature();
						}
						break;
					case 2:
						{
						_localctx = new Self_methodsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_self_methods);
						setState(384);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(385);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(390);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
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
			setState(463);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(391);
				match(T__12);
				setState(392);
				role_vec_modifier();
				setState(393);
				match(JAVA_ID);
				setState(394);
				match(T__1);
				setState(395);
				stageprop_body(0);
				setState(396);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(398);
				match(T__12);
				setState(399);
				role_vec_modifier();
				setState(400);
				match(JAVA_ID);
				setState(401);
				match(T__1);
				setState(402);
				stageprop_body(0);
				setState(403);
				match(T__2);
				setState(404);
				match(REQUIRES);
				setState(405);
				match(T__1);
				setState(406);
				self_methods(0);
				setState(407);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(409);
				access_qualifier();
				setState(410);
				match(T__12);
				setState(411);
				role_vec_modifier();
				setState(412);
				match(JAVA_ID);
				setState(413);
				match(T__1);
				setState(414);
				stageprop_body(0);
				setState(415);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(417);
				access_qualifier();
				setState(418);
				match(T__12);
				setState(419);
				role_vec_modifier();
				setState(420);
				match(JAVA_ID);
				setState(421);
				match(T__1);
				setState(422);
				stageprop_body(0);
				setState(423);
				match(T__2);
				setState(424);
				match(REQUIRES);
				setState(425);
				match(T__1);
				setState(426);
				self_methods(0);
				setState(427);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(429);
				match(T__12);
				setState(430);
				role_vec_modifier();
				setState(431);
				match(JAVA_ID);
				setState(432);
				match(T__1);
				setState(433);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(435);
				match(T__12);
				setState(436);
				role_vec_modifier();
				setState(437);
				match(JAVA_ID);
				setState(438);
				match(T__1);
				setState(439);
				match(T__2);
				setState(440);
				match(REQUIRES);
				setState(441);
				match(T__1);
				setState(442);
				self_methods(0);
				setState(443);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(445);
				access_qualifier();
				setState(446);
				match(T__12);
				setState(447);
				role_vec_modifier();
				setState(448);
				match(JAVA_ID);
				setState(449);
				match(T__1);
				setState(450);
				match(T__2);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(452);
				access_qualifier();
				setState(453);
				match(T__12);
				setState(454);
				role_vec_modifier();
				setState(455);
				match(JAVA_ID);
				setState(456);
				match(T__1);
				setState(457);
				match(T__2);
				setState(458);
				match(REQUIRES);
				setState(459);
				match(T__1);
				setState(460);
				self_methods(0);
				setState(461);
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
			setState(468);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(466);
				method_decl();
				}
				break;
			case 2:
				{
				setState(467);
				object_decl();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(476);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(474);
					switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
					case 1:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(470);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(471);
						method_decl();
						}
						break;
					case 2:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(472);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(473);
						object_decl();
						}
						break;
					}
					} 
				}
				setState(478);
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
			setState(482);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				setState(480);
				class_body_element();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(488);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Class_bodyContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_class_body);
					setState(484);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(485);
					class_body_element();
					}
					} 
				}
				setState(490);
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

	public static class Class_body_elementContext extends ParserRuleContext {
		public Method_declContext method_decl() {
			return getRuleContext(Method_declContext.class,0);
		}
		public Object_declContext object_decl() {
			return getRuleContext(Object_declContext.class,0);
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
			setState(493);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(491);
				method_decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(492);
				object_decl();
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
			setState(496);
			method_signature();
			}
			_ctx.stop = _input.LT(-1);
			setState(505);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(503);
					switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
					case 1:
						{
						_localctx = new Interface_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_interface_body);
						setState(498);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(499);
						match(T__11);
						setState(500);
						method_signature();
						}
						break;
					case 2:
						{
						_localctx = new Interface_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_interface_body);
						setState(501);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(502);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(507);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
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
		public Expr_and_decl_listContext expr_and_decl_list() {
			return getRuleContext(Expr_and_decl_listContext.class,0);
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
			setState(508);
			method_decl_hook();
			setState(509);
			match(T__1);
			setState(510);
			expr_and_decl_list(0);
			setState(511);
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
			setState(513);
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
			setState(547);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(515);
				access_qualifier();
				setState(516);
				return_type();
				setState(517);
				method_name();
				setState(518);
				match(T__13);
				setState(519);
				param_list(0);
				setState(520);
				match(T__14);
				setState(524);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(521);
						match(CONST);
						}
						} 
					}
					setState(526);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(527);
				access_qualifier();
				setState(528);
				return_type();
				setState(529);
				method_name();
				setState(533);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(530);
						match(CONST);
						}
						} 
					}
					setState(535);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(536);
				access_qualifier();
				setState(537);
				method_name();
				setState(538);
				match(T__13);
				setState(539);
				param_list(0);
				setState(540);
				match(T__14);
				setState(544);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(541);
						match(CONST);
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
			setState(556);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				{
				setState(550);
				object_decl();
				}
				break;
			case 2:
				{
				setState(551);
				expr();
				setState(552);
				match(T__11);
				setState(553);
				object_decl();
				}
				break;
			case 3:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(566);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(564);
					switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
					case 1:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(558);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(559);
						object_decl();
						}
						break;
					case 2:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(560);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(561);
						expr();
						}
						break;
					case 3:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(562);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(563);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(568);
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
		enterRule(_localctx, 52, RULE_return_type);
		try {
			setState(571);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(569);
				type_name();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
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
		enterRule(_localctx, 54, RULE_method_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(573);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABELIAN_SUMOP) | (1L << ABELIAN_MULOP) | (1L << JAVA_ID))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
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
		enterRule(_localctx, 56, RULE_access_qualifier);
		try {
			setState(578);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(575);
				match(T__15);
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 2);
				{
				setState(576);
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
		enterRule(_localctx, 58, RULE_object_decl);
		try {
			setState(596);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(580);
				access_qualifier();
				setState(581);
				compound_type_name();
				setState(582);
				identifier_list(0);
				setState(583);
				match(T__11);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(585);
				access_qualifier();
				setState(586);
				compound_type_name();
				setState(587);
				identifier_list(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(589);
				compound_type_name();
				setState(590);
				identifier_list(0);
				setState(591);
				match(T__11);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(593);
				compound_type_name();
				setState(594);
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
		enterRule(_localctx, 60, RULE_trivial_object_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(598);
			compound_type_name();
			setState(599);
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
		enterRule(_localctx, 62, RULE_compound_type_name);
		try {
			setState(606);
			switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(601);
				type_name();
				setState(602);
				match(T__9);
				setState(603);
				match(T__10);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(605);
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
		enterRule(_localctx, 64, RULE_type_name);
		try {
			setState(612);
			switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(608);
				match(JAVA_ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(609);
				match(JAVA_ID);
				setState(610);
				type_list();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(611);
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
		enterRule(_localctx, 66, RULE_builtin_type_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(614);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
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
		int _startState = 68;
		enterRecursionRule(_localctx, 68, RULE_identifier_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(621);
			switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
			case 1:
				{
				setState(617);
				match(JAVA_ID);
				}
				break;
			case 2:
				{
				setState(618);
				match(JAVA_ID);
				setState(619);
				match(ASSIGN);
				setState(620);
				expr();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(633);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(631);
					switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
					case 1:
						{
						_localctx = new Identifier_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identifier_list);
						setState(623);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(624);
						match(T__7);
						setState(625);
						match(JAVA_ID);
						}
						break;
					case 2:
						{
						_localctx = new Identifier_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identifier_list);
						setState(626);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(627);
						match(T__7);
						setState(628);
						match(JAVA_ID);
						setState(629);
						match(ASSIGN);
						setState(630);
						expr();
						}
						break;
					}
					} 
				}
				setState(635);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
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
		int _startState = 70;
		enterRecursionRule(_localctx, 70, RULE_param_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(639);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				{
				setState(637);
				param_decl();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(646);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Param_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_param_list);
					setState(641);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(642);
					match(T__7);
					setState(643);
					param_decl();
					}
					} 
				}
				setState(648);
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
		enterRule(_localctx, 72, RULE_param_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(649);
			compound_type_name();
			setState(650);
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
		enterRule(_localctx, 74, RULE_expr);
		try {
			setState(665);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(652);
				abelian_expr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(653);
				boolean_expr(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(654);
				block();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(655);
				if_expr();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(656);
				for_expr();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(657);
				while_expr();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(658);
				do_while_expr();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(659);
				switch_expr();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(660);
				match(BREAK);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(661);
				match(CONTINUE);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(662);
				match(RETURN);
				setState(663);
				expr();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(664);
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
		int _startState = 76;
		enterRecursionRule(_localctx, 76, RULE_abelian_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(677);
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
				setState(668);
				abelian_product(0);
				setState(673);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,51,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(669);
						match(ABELIAN_SUMOP);
						setState(670);
						abelian_product(0);
						}
						} 
					}
					setState(675);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,51,_ctx);
				}
				}
				break;
			case T__28:
				{
				setState(676);
				if_expr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(684);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Abelian_exprContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_abelian_expr);
					setState(679);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(680);
					match(ASSIGN);
					setState(681);
					expr();
					}
					} 
				}
				setState(686);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
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
		int _startState = 78;
		enterRecursionRule(_localctx, 78, RULE_abelian_product, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(688);
			abelian_unary_op();
			setState(693);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(689);
					match(ABELIAN_MULOP);
					setState(690);
					abelian_unary_op();
					}
					} 
				}
				setState(695);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
			}
			}
			_ctx.stop = _input.LT(-1);
			setState(701);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,55,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Abelian_productContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_abelian_product);
					setState(696);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(697);
					match(POW);
					setState(698);
					abelian_atom(0);
					}
					} 
				}
				setState(703);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,55,_ctx);
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
		enterRule(_localctx, 80, RULE_abelian_unary_op);
		try {
			setState(707);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(704);
				match(ABELIAN_SUMOP);
				setState(705);
				abelian_atom(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(706);
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
		public TerminalNode ABELIAN_INCREMENT_OP() { return getToken(KantParser.ABELIAN_INCREMENT_OP, 0); }
		public Abelian_atomContext abelian_atom() {
			return getRuleContext(Abelian_atomContext.class,0);
		}
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
		int _startState = 82;
		enterRecursionRule(_localctx, 82, RULE_abelian_atom, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(750);
			switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
			case 1:
				{
				setState(710);
				match(ABELIAN_INCREMENT_OP);
				setState(711);
				abelian_atom(10);
				}
				break;
			case 2:
				{
				setState(712);
				match(NEW);
				setState(713);
				message();
				}
				break;
			case 3:
				{
				setState(714);
				match(NEW);
				setState(715);
				type_name();
				setState(716);
				match(T__9);
				setState(717);
				expr();
				setState(718);
				match(T__10);
				}
				break;
			case 4:
				{
				setState(720);
				match(NEW);
				setState(721);
				match(JAVA_ID);
				setState(722);
				type_list();
				setState(723);
				match(T__13);
				setState(724);
				argument_list(0);
				setState(725);
				match(T__14);
				}
				break;
			case 5:
				{
				setState(727);
				builtin_type_name();
				setState(728);
				match(T__21);
				setState(729);
				message();
				}
				break;
			case 6:
				{
				setState(731);
				null_expr();
				}
				break;
			case 7:
				{
				setState(732);
				message();
				}
				break;
			case 8:
				{
				setState(733);
				match(JAVA_ID);
				}
				break;
			case 9:
				{
				setState(734);
				constant();
				}
				break;
			case 10:
				{
				setState(735);
				match(T__13);
				setState(736);
				abelian_expr(0);
				setState(737);
				match(T__14);
				}
				break;
			case 11:
				{
				setState(739);
				match(ABELIAN_INCREMENT_OP);
				setState(740);
				expr();
				setState(741);
				match(T__9);
				setState(742);
				expr();
				setState(743);
				match(T__10);
				}
				break;
			case 12:
				{
				setState(745);
				match(ABELIAN_INCREMENT_OP);
				setState(746);
				expr();
				setState(747);
				match(T__21);
				setState(748);
				match(JAVA_ID);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(785);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,59,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(783);
					switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
					case 1:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(752);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(753);
						match(T__21);
						setState(754);
						message();
						}
						break;
					case 2:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(755);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(756);
						match(T__21);
						setState(757);
						match(JAVA_ID);
						}
						break;
					case 3:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(758);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(759);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 4:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(760);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(761);
						match(T__9);
						setState(762);
						expr();
						setState(763);
						match(T__10);
						}
						break;
					case 5:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(765);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(766);
						match(T__9);
						setState(767);
						expr();
						setState(768);
						match(T__10);
						setState(769);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 6:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(771);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(772);
						match(T__21);
						setState(773);
						match(JAVA_ID);
						setState(774);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 7:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(775);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(776);
						match(T__21);
						setState(777);
						match(CLONE);
						}
						break;
					case 8:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(778);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(779);
						match(T__21);
						setState(780);
						match(CLONE);
						setState(781);
						match(T__13);
						setState(782);
						match(T__14);
						}
						break;
					}
					} 
				}
				setState(787);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,59,_ctx);
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
		int _startState = 84;
		enterRecursionRule(_localctx, 84, RULE_boolean_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(803);
			switch ( getInterpreter().adaptivePredict(_input,61,_ctx) ) {
			case 1:
				{
				setState(789);
				boolean_product();
				setState(794);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(790);
						match(BOOLEAN_SUMOP);
						setState(791);
						boolean_product();
						}
						} 
					}
					setState(796);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
				}
				}
				break;
			case 2:
				{
				setState(797);
				if_expr();
				}
				break;
			case 3:
				{
				setState(798);
				abelian_expr(0);
				setState(799);
				((Boolean_exprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << LT) | (1L << GT))) != 0)) ) {
					((Boolean_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(800);
				abelian_expr(0);
				}
				break;
			case 4:
				{
				setState(802);
				abelian_expr(0);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(813);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(811);
					switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
					case 1:
						{
						_localctx = new Boolean_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolean_expr);
						setState(805);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(806);
						((Boolean_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << BOOLEAN_MULOP))) != 0)) ) {
							((Boolean_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(807);
						boolean_expr(5);
						}
						break;
					case 2:
						{
						_localctx = new Boolean_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolean_expr);
						setState(808);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(809);
						match(ASSIGN);
						setState(810);
						expr();
						}
						break;
					}
					} 
				}
				setState(815);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
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
		enterRule(_localctx, 86, RULE_boolean_product);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(816);
			boolean_unary_op();
			setState(821);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,64,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(817);
					match(BOOLEAN_MULOP);
					setState(818);
					boolean_unary_op();
					}
					} 
				}
				setState(823);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,64,_ctx);
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
		enterRule(_localctx, 88, RULE_boolean_unary_op);
		try {
			setState(827);
			switch (_input.LA(1)) {
			case LOGICAL_NEGATION:
				enterOuterAlt(_localctx, 1);
				{
				setState(824);
				match(LOGICAL_NEGATION);
				setState(825);
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
				setState(826);
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
		enterRule(_localctx, 90, RULE_boolean_atom);
		try {
			setState(836);
			switch (_input.LA(1)) {
			case STRING:
			case INTEGER:
			case FLOAT:
			case BOOLEAN:
				enterOuterAlt(_localctx, 1);
				{
				setState(829);
				constant();
				}
				break;
			case JAVA_ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(830);
				match(JAVA_ID);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 3);
				{
				setState(831);
				null_expr();
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 4);
				{
				setState(832);
				match(T__13);
				setState(833);
				boolean_expr(0);
				setState(834);
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
		enterRule(_localctx, 92, RULE_message);
		try {
			setState(848);
			switch ( getInterpreter().adaptivePredict(_input,67,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(838);
				method_name();
				setState(839);
				match(T__13);
				setState(840);
				argument_list(0);
				setState(841);
				match(T__14);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(843);
				type_name();
				setState(844);
				match(T__13);
				setState(845);
				argument_list(0);
				setState(846);
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
		enterRule(_localctx, 94, RULE_block);
		try {
			setState(856);
			switch ( getInterpreter().adaptivePredict(_input,68,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(850);
				match(T__1);
				setState(851);
				expr_and_decl_list(0);
				setState(852);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(854);
				match(T__1);
				setState(855);
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
		enterRule(_localctx, 96, RULE_expr_or_null);
		try {
			setState(860);
			switch (_input.LA(1)) {
			case T__1:
			case T__13:
			case T__17:
			case T__18:
			case T__19:
			case T__20:
			case T__28:
			case T__30:
			case T__32:
			case T__33:
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
				setState(858);
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
		enterRule(_localctx, 98, RULE_if_expr);
		try {
			setState(876);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(862);
				match(T__28);
				setState(863);
				match(T__13);
				setState(864);
				expr();
				setState(865);
				match(T__14);
				setState(866);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(868);
				match(T__28);
				setState(869);
				match(T__13);
				setState(870);
				expr();
				setState(871);
				match(T__14);
				setState(872);
				expr();
				setState(873);
				match(T__29);
				setState(874);
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
		enterRule(_localctx, 100, RULE_for_expr);
		try {
			setState(913);
			switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(878);
				match(T__30);
				setState(879);
				match(T__13);
				setState(880);
				expr();
				setState(881);
				match(T__11);
				setState(882);
				expr();
				setState(883);
				match(T__11);
				setState(884);
				expr();
				setState(885);
				match(T__14);
				setState(886);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(888);
				match(T__30);
				setState(889);
				match(T__13);
				setState(890);
				object_decl();
				setState(891);
				expr();
				setState(892);
				match(T__11);
				setState(893);
				expr();
				setState(894);
				match(T__14);
				setState(895);
				expr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(897);
				match(T__30);
				setState(898);
				match(T__13);
				setState(899);
				match(JAVA_ID);
				setState(900);
				match(T__31);
				setState(901);
				expr();
				setState(902);
				match(T__14);
				setState(903);
				expr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(905);
				match(T__30);
				setState(906);
				match(T__13);
				setState(907);
				trivial_object_decl();
				setState(908);
				match(T__31);
				setState(909);
				expr();
				setState(910);
				match(T__14);
				setState(911);
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
		enterRule(_localctx, 102, RULE_while_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(915);
			match(T__32);
			setState(916);
			match(T__13);
			setState(917);
			expr();
			setState(918);
			match(T__14);
			setState(919);
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
		enterRule(_localctx, 104, RULE_do_while_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(921);
			match(T__33);
			setState(922);
			expr();
			setState(923);
			match(T__32);
			setState(924);
			match(T__13);
			setState(925);
			expr();
			setState(926);
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
		enterRule(_localctx, 106, RULE_switch_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(928);
			match(SWITCH);
			setState(929);
			match(T__13);
			setState(930);
			expr();
			setState(931);
			match(T__14);
			setState(932);
			match(T__1);
			setState(936);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE || _la==DEFAULT) {
				{
				{
				setState(933);
				switch_body();
				}
				}
				setState(938);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(939);
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
		enterRule(_localctx, 108, RULE_switch_body);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(944);
			switch (_input.LA(1)) {
			case CASE:
				{
				setState(941);
				match(CASE);
				setState(942);
				constant();
				}
				break;
			case DEFAULT:
				{
				setState(943);
				match(DEFAULT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(946);
			match(T__31);
			setState(947);
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
		enterRule(_localctx, 110, RULE_null_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(949);
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
		enterRule(_localctx, 112, RULE_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(951);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << BOOLEAN))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
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
		int _startState = 114;
		enterRecursionRule(_localctx, 114, RULE_argument_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(956);
			switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
			case 1:
				{
				setState(954);
				expr();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(963);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,75,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Argument_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_argument_list);
					setState(958);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(959);
					match(T__7);
					setState(960);
					expr();
					}
					} 
				}
				setState(965);
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
		case 34:
			return identifier_list_sempred((Identifier_listContext)_localctx, predIndex);
		case 35:
			return param_list_sempred((Param_listContext)_localctx, predIndex);
		case 38:
			return abelian_expr_sempred((Abelian_exprContext)_localctx, predIndex);
		case 39:
			return abelian_product_sempred((Abelian_productContext)_localctx, predIndex);
		case 41:
			return abelian_atom_sempred((Abelian_atomContext)_localctx, predIndex);
		case 42:
			return boolean_expr_sempred((Boolean_exprContext)_localctx, predIndex);
		case 57:
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
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean context_body_sempred(Context_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean role_body_sempred(Role_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 4);
		case 4:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean self_methods_sempred(Self_methodsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return precpred(_ctx, 3);
		case 6:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean stageprop_body_sempred(Stageprop_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return precpred(_ctx, 3);
		case 8:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean class_body_sempred(Class_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 9:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean interface_body_sempred(Interface_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10:
			return precpred(_ctx, 3);
		case 11:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expr_and_decl_list_sempred(Expr_and_decl_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 12:
			return precpred(_ctx, 4);
		case 13:
			return precpred(_ctx, 3);
		case 14:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean identifier_list_sempred(Identifier_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 15:
			return precpred(_ctx, 3);
		case 16:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean param_list_sempred(Param_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 17:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean abelian_expr_sempred(Abelian_exprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 18:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean abelian_product_sempred(Abelian_productContext _localctx, int predIndex) {
		switch (predIndex) {
		case 19:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean abelian_atom_sempred(Abelian_atomContext _localctx, int predIndex) {
		switch (predIndex) {
		case 20:
			return precpred(_ctx, 17);
		case 21:
			return precpred(_ctx, 15);
		case 22:
			return precpred(_ctx, 11);
		case 23:
			return precpred(_ctx, 7);
		case 24:
			return precpred(_ctx, 6);
		case 25:
			return precpred(_ctx, 3);
		case 26:
			return precpred(_ctx, 2);
		case 27:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean boolean_expr_sempred(Boolean_exprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 28:
			return precpred(_ctx, 4);
		case 29:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean argument_list_sempred(Argument_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 30:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3C\u03c9\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\3\2\3\2\3\2"+
		"\3\2\5\2{\n\2\3\3\3\3\3\4\3\4\3\4\5\4\u0082\n\4\3\4\3\4\7\4\u0086\n\4"+
		"\f\4\16\4\u0089\13\4\3\5\3\5\3\5\5\5\u008e\n\5\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\7\3\7\3\7\3\7\7\7\u009a\n\7\f\7\16\7\u009d\13\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\7\7\u00a9\n\7\f\7\16\7\u00ac\13\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\7\7\u00b5\n\7\f\7\16\7\u00b8\13\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\7\7\u00c3\n\7\f\7\16\7\u00c6\13\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\7\7\u00cf\n\7\f\7\16\7\u00d2\13\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u00da"+
		"\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u00e9\n\t"+
		"\f\t\16\t\u00ec\13\t\3\n\3\n\3\n\3\n\7\n\u00f2\n\n\f\n\16\n\u00f5\13\n"+
		"\3\n\3\n\3\13\3\13\3\13\3\13\7\13\u00fd\n\13\f\13\16\13\u0100\13\13\3"+
		"\13\3\13\3\f\3\f\3\f\5\f\u0107\n\f\3\r\3\r\3\r\5\r\u010c\n\r\3\r\3\r\7"+
		"\r\u0110\n\r\f\r\16\r\u0113\13\r\3\16\3\16\3\16\3\16\5\16\u0119\n\16\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\5\17\u0167\n\17\3\20\3\20\3\20\5\20\u016c"+
		"\n\20\3\21\3\21\3\21\3\21\5\21\u0172\n\21\3\21\3\21\3\21\3\21\7\21\u0178"+
		"\n\21\f\21\16\21\u017b\13\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\7"+
		"\22\u0185\n\22\f\22\16\22\u0188\13\22\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u01d2\n\23\3\24"+
		"\3\24\3\24\5\24\u01d7\n\24\3\24\3\24\3\24\3\24\7\24\u01dd\n\24\f\24\16"+
		"\24\u01e0\13\24\3\25\3\25\3\25\5\25\u01e5\n\25\3\25\3\25\7\25\u01e9\n"+
		"\25\f\25\16\25\u01ec\13\25\3\26\3\26\5\26\u01f0\n\26\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\7\27\u01fa\n\27\f\27\16\27\u01fd\13\27\3\30\3"+
		"\30\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\7\32\u020d"+
		"\n\32\f\32\16\32\u0210\13\32\3\32\3\32\3\32\3\32\7\32\u0216\n\32\f\32"+
		"\16\32\u0219\13\32\3\32\3\32\3\32\3\32\3\32\3\32\7\32\u0221\n\32\f\32"+
		"\16\32\u0224\13\32\5\32\u0226\n\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\5\33\u022f\n\33\3\33\3\33\3\33\3\33\3\33\3\33\7\33\u0237\n\33\f\33\16"+
		"\33\u023a\13\33\3\34\3\34\5\34\u023e\n\34\3\35\3\35\3\36\3\36\3\36\5\36"+
		"\u0245\n\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3\37\3\37\5\37\u0257\n\37\3 \3 \3 \3!\3!\3!\3!\3!\5!\u0261"+
		"\n!\3\"\3\"\3\"\3\"\5\"\u0267\n\"\3#\3#\3$\3$\3$\3$\3$\5$\u0270\n$\3$"+
		"\3$\3$\3$\3$\3$\3$\3$\7$\u027a\n$\f$\16$\u027d\13$\3%\3%\3%\5%\u0282\n"+
		"%\3%\3%\3%\7%\u0287\n%\f%\16%\u028a\13%\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\5\'\u029c\n\'\3(\3(\3(\3(\7(\u02a2\n(\f"+
		"(\16(\u02a5\13(\3(\5(\u02a8\n(\3(\3(\3(\7(\u02ad\n(\f(\16(\u02b0\13(\3"+
		")\3)\3)\3)\7)\u02b6\n)\f)\16)\u02b9\13)\3)\3)\3)\7)\u02be\n)\f)\16)\u02c1"+
		"\13)\3*\3*\3*\5*\u02c6\n*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3"+
		"+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3"+
		"+\3+\3+\3+\5+\u02f1\n+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3"+
		"+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\7+\u0312\n+\f+\16+\u0315"+
		"\13+\3,\3,\3,\3,\7,\u031b\n,\f,\16,\u031e\13,\3,\3,\3,\3,\3,\3,\5,\u0326"+
		"\n,\3,\3,\3,\3,\3,\3,\7,\u032e\n,\f,\16,\u0331\13,\3-\3-\3-\7-\u0336\n"+
		"-\f-\16-\u0339\13-\3.\3.\3.\5.\u033e\n.\3/\3/\3/\3/\3/\3/\3/\5/\u0347"+
		"\n/\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\5\60\u0353\n\60"+
		"\3\61\3\61\3\61\3\61\3\61\3\61\5\61\u035b\n\61\3\62\3\62\5\62\u035f\n"+
		"\62\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3"+
		"\63\5\63\u036f\n\63\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\5\64\u0394\n\64"+
		"\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\67"+
		"\3\67\3\67\3\67\3\67\3\67\7\67\u03a9\n\67\f\67\16\67\u03ac\13\67\3\67"+
		"\3\67\38\38\38\58\u03b3\n8\38\38\38\39\39\3:\3:\3;\3;\3;\5;\u03bf\n;\3"+
		";\3;\3;\7;\u03c4\n;\f;\16;\u03c7\13;\3;\2\22\6\20\30 \"&(,\64FHNPTVt<"+
		"\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFH"+
		"JLNPRTVXZ\\^`bdfhjlnprt\2\7\4\2\678??\3\2\24\27\4\2\33\36;<\4\2\31\32"+
		"\66\66\3\2%(\u0414\2z\3\2\2\2\4|\3\2\2\2\6\u0081\3\2\2\2\b\u008d\3\2\2"+
		"\2\n\u008f\3\2\2\2\f\u00d9\3\2\2\2\16\u00db\3\2\2\2\20\u00e1\3\2\2\2\22"+
		"\u00ed\3\2\2\2\24\u00f8\3\2\2\2\26\u0103\3\2\2\2\30\u010b\3\2\2\2\32\u0118"+
		"\3\2\2\2\34\u0166\3\2\2\2\36\u016b\3\2\2\2 \u0171\3\2\2\2\"\u017c\3\2"+
		"\2\2$\u01d1\3\2\2\2&\u01d6\3\2\2\2(\u01e4\3\2\2\2*\u01ef\3\2\2\2,\u01f1"+
		"\3\2\2\2.\u01fe\3\2\2\2\60\u0203\3\2\2\2\62\u0225\3\2\2\2\64\u022e\3\2"+
		"\2\2\66\u023d\3\2\2\28\u023f\3\2\2\2:\u0244\3\2\2\2<\u0256\3\2\2\2>\u0258"+
		"\3\2\2\2@\u0260\3\2\2\2B\u0266\3\2\2\2D\u0268\3\2\2\2F\u026f\3\2\2\2H"+
		"\u0281\3\2\2\2J\u028b\3\2\2\2L\u029b\3\2\2\2N\u02a7\3\2\2\2P\u02b1\3\2"+
		"\2\2R\u02c5\3\2\2\2T\u02f0\3\2\2\2V\u0325\3\2\2\2X\u0332\3\2\2\2Z\u033d"+
		"\3\2\2\2\\\u0346\3\2\2\2^\u0352\3\2\2\2`\u035a\3\2\2\2b\u035e\3\2\2\2"+
		"d\u036e\3\2\2\2f\u0393\3\2\2\2h\u0395\3\2\2\2j\u039b\3\2\2\2l\u03a2\3"+
		"\2\2\2n\u03b2\3\2\2\2p\u03b7\3\2\2\2r\u03b9\3\2\2\2t\u03be\3\2\2\2vw\5"+
		"\6\4\2wx\5\4\3\2x{\3\2\2\2y{\5\6\4\2zv\3\2\2\2zy\3\2\2\2{\3\3\2\2\2|}"+
		"\5L\'\2}\5\3\2\2\2~\177\b\4\1\2\177\u0082\5\b\5\2\u0080\u0082\3\2\2\2"+
		"\u0081~\3\2\2\2\u0081\u0080\3\2\2\2\u0082\u0087\3\2\2\2\u0083\u0084\f"+
		"\4\2\2\u0084\u0086\5\b\5\2\u0085\u0083\3\2\2\2\u0086\u0089\3\2\2\2\u0087"+
		"\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\7\3\2\2\2\u0089\u0087\3\2\2\2"+
		"\u008a\u008e\5\n\6\2\u008b\u008e\5\f\7\2\u008c\u008e\5\16\b\2\u008d\u008a"+
		"\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008c\3\2\2\2\u008e\t\3\2\2\2\u008f"+
		"\u0090\7\3\2\2\u0090\u0091\7?\2\2\u0091\u0092\7\4\2\2\u0092\u0093\5\30"+
		"\r\2\u0093\u0094\7\5\2\2\u0094\13\3\2\2\2\u0095\u0096\7\6\2\2\u0096\u0097"+
		"\7?\2\2\u0097\u009b\5\22\n\2\u0098\u009a\5\20\t\2\u0099\u0098\3\2\2\2"+
		"\u009a\u009d\3\2\2\2\u009b\u0099\3\2\2\2\u009b\u009c\3\2\2\2\u009c\u009e"+
		"\3\2\2\2\u009d\u009b\3\2\2\2\u009e\u009f\7\4\2\2\u009f\u00a0\5(\25\2\u00a0"+
		"\u00a1\7\5\2\2\u00a1\u00da\3\2\2\2\u00a2\u00a3\7\6\2\2\u00a3\u00a4\7?"+
		"\2\2\u00a4\u00a5\5\22\n\2\u00a5\u00a6\7\7\2\2\u00a6\u00aa\7?\2\2\u00a7"+
		"\u00a9\5\20\t\2\u00a8\u00a7\3\2\2\2\u00a9\u00ac\3\2\2\2\u00aa\u00a8\3"+
		"\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ad\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad"+
		"\u00ae\7\4\2\2\u00ae\u00af\5(\25\2\u00af\u00b0\7\5\2\2\u00b0\u00da\3\2"+
		"\2\2\u00b1\u00b2\7\6\2\2\u00b2\u00b6\7?\2\2\u00b3\u00b5\5\20\t\2\u00b4"+
		"\u00b3\3\2\2\2\u00b5\u00b8\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b6\u00b7\3\2"+
		"\2\2\u00b7\u00b9\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b9\u00ba\7\4\2\2\u00ba"+
		"\u00bb\5(\25\2\u00bb\u00bc\7\5\2\2\u00bc\u00da\3\2\2\2\u00bd\u00be\7\6"+
		"\2\2\u00be\u00bf\7?\2\2\u00bf\u00c0\7\7\2\2\u00c0\u00c4\7?\2\2\u00c1\u00c3"+
		"\5\20\t\2\u00c2\u00c1\3\2\2\2\u00c3\u00c6\3\2\2\2\u00c4\u00c2\3\2\2\2"+
		"\u00c4\u00c5\3\2\2\2\u00c5\u00c7\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c7\u00c8"+
		"\7\4\2\2\u00c8\u00c9\5(\25\2\u00c9\u00ca\7\5\2\2\u00ca\u00da\3\2\2\2\u00cb"+
		"\u00cc\7\6\2\2\u00cc\u00d0\7?\2\2\u00cd\u00cf\5\20\t\2\u00ce\u00cd\3\2"+
		"\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1"+
		"\u00d3\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3\u00d4\7\7\2\2\u00d4\u00d5\7?"+
		"\2\2\u00d5\u00d6\7\4\2\2\u00d6\u00d7\5(\25\2\u00d7\u00d8\7\5\2\2\u00d8"+
		"\u00da\3\2\2\2\u00d9\u0095\3\2\2\2\u00d9\u00a2\3\2\2\2\u00d9\u00b1\3\2"+
		"\2\2\u00d9\u00bd\3\2\2\2\u00d9\u00cb\3\2\2\2\u00da\r\3\2\2\2\u00db\u00dc"+
		"\7\b\2\2\u00dc\u00dd\7?\2\2\u00dd\u00de\7\4\2\2\u00de\u00df\5,\27\2\u00df"+
		"\u00e0\7\5\2\2\u00e0\17\3\2\2\2\u00e1\u00e2\b\t\1\2\u00e2\u00e3\7\t\2"+
		"\2\u00e3\u00e4\7?\2\2\u00e4\u00ea\3\2\2\2\u00e5\u00e6\f\3\2\2\u00e6\u00e7"+
		"\7\n\2\2\u00e7\u00e9\7?\2\2\u00e8\u00e5\3\2\2\2\u00e9\u00ec\3\2\2\2\u00ea"+
		"\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\21\3\2\2\2\u00ec\u00ea\3\2\2"+
		"\2\u00ed\u00ee\7;\2\2\u00ee\u00f3\5\26\f\2\u00ef\u00f0\7\n\2\2\u00f0\u00f2"+
		"\5\26\f\2\u00f1\u00ef\3\2\2\2\u00f2\u00f5\3\2\2\2\u00f3\u00f1\3\2\2\2"+
		"\u00f3\u00f4\3\2\2\2\u00f4\u00f6\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f6\u00f7"+
		"\7<\2\2\u00f7\23\3\2\2\2\u00f8\u00f9\7;\2\2\u00f9\u00fe\5B\"\2\u00fa\u00fb"+
		"\7\n\2\2\u00fb\u00fd\5B\"\2\u00fc\u00fa\3\2\2\2\u00fd\u0100\3\2\2\2\u00fe"+
		"\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0101\3\2\2\2\u0100\u00fe\3\2"+
		"\2\2\u0101\u0102\7<\2\2\u0102\25\3\2\2\2\u0103\u0106\5B\"\2\u0104\u0105"+
		"\7\7\2\2\u0105\u0107\5B\"\2\u0106\u0104\3\2\2\2\u0106\u0107\3\2\2\2\u0107"+
		"\27\3\2\2\2\u0108\u0109\b\r\1\2\u0109\u010c\5\32\16\2\u010a\u010c\3\2"+
		"\2\2\u010b\u0108\3\2\2\2\u010b\u010a\3\2\2\2\u010c\u0111\3\2\2\2\u010d"+
		"\u010e\f\5\2\2\u010e\u0110\5\32\16\2\u010f\u010d\3\2\2\2\u0110\u0113\3"+
		"\2\2\2\u0111\u010f\3\2\2\2\u0111\u0112\3\2\2\2\u0112\31\3\2\2\2\u0113"+
		"\u0111\3\2\2\2\u0114\u0119\5.\30\2\u0115\u0119\5<\37\2\u0116\u0119\5\34"+
		"\17\2\u0117\u0119\5$\23\2\u0118\u0114\3\2\2\2\u0118\u0115\3\2\2\2\u0118"+
		"\u0116\3\2\2\2\u0118\u0117\3\2\2\2\u0119\33\3\2\2\2\u011a\u011b\7\13\2"+
		"\2\u011b\u011c\5\36\20\2\u011c\u011d\7?\2\2\u011d\u011e\7\4\2\2\u011e"+
		"\u011f\5 \21\2\u011f\u0120\7\5\2\2\u0120\u0167\3\2\2\2\u0121\u0122\7\13"+
		"\2\2\u0122\u0123\5\36\20\2\u0123\u0124\7?\2\2\u0124\u0125\7\4\2\2\u0125"+
		"\u0126\5 \21\2\u0126\u0127\7\5\2\2\u0127\u0128\7/\2\2\u0128\u0129\7\4"+
		"\2\2\u0129\u012a\5\"\22\2\u012a\u012b\7\5\2\2\u012b\u0167\3\2\2\2\u012c"+
		"\u012d\5:\36\2\u012d\u012e\7\13\2\2\u012e\u012f\5\36\20\2\u012f\u0130"+
		"\7?\2\2\u0130\u0131\7\4\2\2\u0131\u0132\5 \21\2\u0132\u0133\7\5\2\2\u0133"+
		"\u0167\3\2\2\2\u0134\u0135\5:\36\2\u0135\u0136\7\13\2\2\u0136\u0137\5"+
		"\36\20\2\u0137\u0138\7?\2\2\u0138\u0139\7\4\2\2\u0139\u013a\5 \21\2\u013a"+
		"\u013b\7\5\2\2\u013b\u013c\7/\2\2\u013c\u013d\7\4\2\2\u013d\u013e\5\""+
		"\22\2\u013e\u013f\7\5\2\2\u013f\u0167\3\2\2\2\u0140\u0141\7\13\2\2\u0141"+
		"\u0142\5\36\20\2\u0142\u0143\7?\2\2\u0143\u0144\7\4\2\2\u0144\u0145\5"+
		" \21\2\u0145\u0146\7\5\2\2\u0146\u0167\3\2\2\2\u0147\u0148\7\13\2\2\u0148"+
		"\u0149\5\36\20\2\u0149\u014a\7?\2\2\u014a\u014b\7\4\2\2\u014b\u014c\5"+
		" \21\2\u014c\u014d\7\5\2\2\u014d\u014e\7/\2\2\u014e\u014f\7\4\2\2\u014f"+
		"\u0150\5\"\22\2\u0150\u0151\7\5\2\2\u0151\u0167\3\2\2\2\u0152\u0153\5"+
		":\36\2\u0153\u0154\7\13\2\2\u0154\u0155\5\36\20\2\u0155\u0156\7?\2\2\u0156"+
		"\u0157\7\4\2\2\u0157\u0158\5 \21\2\u0158\u0159\7\5\2\2\u0159\u0167\3\2"+
		"\2\2\u015a\u015b\5:\36\2\u015b\u015c\7\13\2\2\u015c\u015d\5\36\20\2\u015d"+
		"\u015e\7?\2\2\u015e\u015f\7\4\2\2\u015f\u0160\5 \21\2\u0160\u0161\7\5"+
		"\2\2\u0161\u0162\7/\2\2\u0162\u0163\7\4\2\2\u0163\u0164\5\"\22\2\u0164"+
		"\u0165\7\5\2\2\u0165\u0167\3\2\2\2\u0166\u011a\3\2\2\2\u0166\u0121\3\2"+
		"\2\2\u0166\u012c\3\2\2\2\u0166\u0134\3\2\2\2\u0166\u0140\3\2\2\2\u0166"+
		"\u0147\3\2\2\2\u0166\u0152\3\2\2\2\u0166\u015a\3\2\2\2\u0167\35\3\2\2"+
		"\2\u0168\u0169\7\f\2\2\u0169\u016c\7\r\2\2\u016a\u016c\3\2\2\2\u016b\u0168"+
		"\3\2\2\2\u016b\u016a\3\2\2\2\u016c\37\3\2\2\2\u016d\u016e\b\21\1\2\u016e"+
		"\u0172\5.\30\2\u016f\u0172\5<\37\2\u0170\u0172\3\2\2\2\u0171\u016d\3\2"+
		"\2\2\u0171\u016f\3\2\2\2\u0171\u0170\3\2\2\2\u0172\u0179\3\2\2\2\u0173"+
		"\u0174\f\6\2\2\u0174\u0178\5.\30\2\u0175\u0176\f\4\2\2\u0176\u0178\5<"+
		"\37\2\u0177\u0173\3\2\2\2\u0177\u0175\3\2\2\2\u0178\u017b\3\2\2\2\u0179"+
		"\u0177\3\2\2\2\u0179\u017a\3\2\2\2\u017a!\3\2\2\2\u017b\u0179\3\2\2\2"+
		"\u017c\u017d\b\22\1\2\u017d\u017e\5\62\32\2\u017e\u0186\3\2\2\2\u017f"+
		"\u0180\f\5\2\2\u0180\u0181\7\16\2\2\u0181\u0185\5\62\32\2\u0182\u0183"+
		"\f\3\2\2\u0183\u0185\7\16\2\2\u0184\u017f\3\2\2\2\u0184\u0182\3\2\2\2"+
		"\u0185\u0188\3\2\2\2\u0186\u0184\3\2\2\2\u0186\u0187\3\2\2\2\u0187#\3"+
		"\2\2\2\u0188\u0186\3\2\2\2\u0189\u018a\7\17\2\2\u018a\u018b\5\36\20\2"+
		"\u018b\u018c\7?\2\2\u018c\u018d\7\4\2\2\u018d\u018e\5&\24\2\u018e\u018f"+
		"\7\5\2\2\u018f\u01d2\3\2\2\2\u0190\u0191\7\17\2\2\u0191\u0192\5\36\20"+
		"\2\u0192\u0193\7?\2\2\u0193\u0194\7\4\2\2\u0194\u0195\5&\24\2\u0195\u0196"+
		"\7\5\2\2\u0196\u0197\7/\2\2\u0197\u0198\7\4\2\2\u0198\u0199\5\"\22\2\u0199"+
		"\u019a\7\5\2\2\u019a\u01d2\3\2\2\2\u019b\u019c\5:\36\2\u019c\u019d\7\17"+
		"\2\2\u019d\u019e\5\36\20\2\u019e\u019f\7?\2\2\u019f\u01a0\7\4\2\2\u01a0"+
		"\u01a1\5&\24\2\u01a1\u01a2\7\5\2\2\u01a2\u01d2\3\2\2\2\u01a3\u01a4\5:"+
		"\36\2\u01a4\u01a5\7\17\2\2\u01a5\u01a6\5\36\20\2\u01a6\u01a7\7?\2\2\u01a7"+
		"\u01a8\7\4\2\2\u01a8\u01a9\5&\24\2\u01a9\u01aa\7\5\2\2\u01aa\u01ab\7/"+
		"\2\2\u01ab\u01ac\7\4\2\2\u01ac\u01ad\5\"\22\2\u01ad\u01ae\7\5\2\2\u01ae"+
		"\u01d2\3\2\2\2\u01af\u01b0\7\17\2\2\u01b0\u01b1\5\36\20\2\u01b1\u01b2"+
		"\7?\2\2\u01b2\u01b3\7\4\2\2\u01b3\u01b4\7\5\2\2\u01b4\u01d2\3\2\2\2\u01b5"+
		"\u01b6\7\17\2\2\u01b6\u01b7\5\36\20\2\u01b7\u01b8\7?\2\2\u01b8\u01b9\7"+
		"\4\2\2\u01b9\u01ba\7\5\2\2\u01ba\u01bb\7/\2\2\u01bb\u01bc\7\4\2\2\u01bc"+
		"\u01bd\5\"\22\2\u01bd\u01be\7\5\2\2\u01be\u01d2\3\2\2\2\u01bf\u01c0\5"+
		":\36\2\u01c0\u01c1\7\17\2\2\u01c1\u01c2\5\36\20\2\u01c2\u01c3\7?\2\2\u01c3"+
		"\u01c4\7\4\2\2\u01c4\u01c5\7\5\2\2\u01c5\u01d2\3\2\2\2\u01c6\u01c7\5:"+
		"\36\2\u01c7\u01c8\7\17\2\2\u01c8\u01c9\5\36\20\2\u01c9\u01ca\7?\2\2\u01ca"+
		"\u01cb\7\4\2\2\u01cb\u01cc\7\5\2\2\u01cc\u01cd\7/\2\2\u01cd\u01ce\7\4"+
		"\2\2\u01ce\u01cf\5\"\22\2\u01cf\u01d0\7\5\2\2\u01d0\u01d2\3\2\2\2\u01d1"+
		"\u0189\3\2\2\2\u01d1\u0190\3\2\2\2\u01d1\u019b\3\2\2\2\u01d1\u01a3\3\2"+
		"\2\2\u01d1\u01af\3\2\2\2\u01d1\u01b5\3\2\2\2\u01d1\u01bf\3\2\2\2\u01d1"+
		"\u01c6\3\2\2\2\u01d2%\3\2\2\2\u01d3\u01d4\b\24\1\2\u01d4\u01d7\5.\30\2"+
		"\u01d5\u01d7\5<\37\2\u01d6\u01d3\3\2\2\2\u01d6\u01d5\3\2\2\2\u01d7\u01de"+
		"\3\2\2\2\u01d8\u01d9\f\5\2\2\u01d9\u01dd\5.\30\2\u01da\u01db\f\3\2\2\u01db"+
		"\u01dd\5<\37\2\u01dc\u01d8\3\2\2\2\u01dc\u01da\3\2\2\2\u01dd\u01e0\3\2"+
		"\2\2\u01de\u01dc\3\2\2\2\u01de\u01df\3\2\2\2\u01df\'\3\2\2\2\u01e0\u01de"+
		"\3\2\2\2\u01e1\u01e2\b\25\1\2\u01e2\u01e5\5*\26\2\u01e3\u01e5\3\2\2\2"+
		"\u01e4\u01e1\3\2\2\2\u01e4\u01e3\3\2\2\2\u01e5\u01ea\3\2\2\2\u01e6\u01e7"+
		"\f\5\2\2\u01e7\u01e9\5*\26\2\u01e8\u01e6\3\2\2\2\u01e9\u01ec\3\2\2\2\u01ea"+
		"\u01e8\3\2\2\2\u01ea\u01eb\3\2\2\2\u01eb)\3\2\2\2\u01ec\u01ea\3\2\2\2"+
		"\u01ed\u01f0\5.\30\2\u01ee\u01f0\5<\37\2\u01ef\u01ed\3\2\2\2\u01ef\u01ee"+
		"\3\2\2\2\u01f0+\3\2\2\2\u01f1\u01f2\b\27\1\2\u01f2\u01f3\5\62\32\2\u01f3"+
		"\u01fb\3\2\2\2\u01f4\u01f5\f\5\2\2\u01f5\u01f6\7\16\2\2\u01f6\u01fa\5"+
		"\62\32\2\u01f7\u01f8\f\3\2\2\u01f8\u01fa\7\16\2\2\u01f9\u01f4\3\2\2\2"+
		"\u01f9\u01f7\3\2\2\2\u01fa\u01fd\3\2\2\2\u01fb\u01f9\3\2\2\2\u01fb\u01fc"+
		"\3\2\2\2\u01fc-\3\2\2\2\u01fd\u01fb\3\2\2\2\u01fe\u01ff\5\60\31\2\u01ff"+
		"\u0200\7\4\2\2\u0200\u0201\5\64\33\2\u0201\u0202\7\5\2\2\u0202/\3\2\2"+
		"\2\u0203\u0204\5\62\32\2\u0204\61\3\2\2\2\u0205\u0206\5:\36\2\u0206\u0207"+
		"\5\66\34\2\u0207\u0208\58\35\2\u0208\u0209\7\20\2\2\u0209\u020a\5H%\2"+
		"\u020a\u020e\7\21\2\2\u020b\u020d\7\63\2\2\u020c\u020b\3\2\2\2\u020d\u0210"+
		"\3\2\2\2\u020e\u020c\3\2\2\2\u020e\u020f\3\2\2\2\u020f\u0226\3\2\2\2\u0210"+
		"\u020e\3\2\2\2\u0211\u0212\5:\36\2\u0212\u0213\5\66\34\2\u0213\u0217\5"+
		"8\35\2\u0214\u0216\7\63\2\2\u0215\u0214\3\2\2\2\u0216\u0219\3\2\2\2\u0217"+
		"\u0215\3\2\2\2\u0217\u0218\3\2\2\2\u0218\u0226\3\2\2\2\u0219\u0217\3\2"+
		"\2\2\u021a\u021b\5:\36\2\u021b\u021c\58\35\2\u021c\u021d\7\20\2\2\u021d"+
		"\u021e\5H%\2\u021e\u0222\7\21\2\2\u021f\u0221\7\63\2\2\u0220\u021f\3\2"+
		"\2\2\u0221\u0224\3\2\2\2\u0222\u0220\3\2\2\2\u0222\u0223\3\2\2\2\u0223"+
		"\u0226\3\2\2\2\u0224\u0222\3\2\2\2\u0225\u0205\3\2\2\2\u0225\u0211\3\2"+
		"\2\2\u0225\u021a\3\2\2\2\u0226\63\3\2\2\2\u0227\u0228\b\33\1\2\u0228\u022f"+
		"\5<\37\2\u0229\u022a\5L\'\2\u022a\u022b\7\16\2\2\u022b\u022c\5<\37\2\u022c"+
		"\u022f\3\2\2\2\u022d\u022f\3\2\2\2\u022e\u0227\3\2\2\2\u022e\u0229\3\2"+
		"\2\2\u022e\u022d\3\2\2\2\u022f\u0238\3\2\2\2\u0230\u0231\f\6\2\2\u0231"+
		"\u0237\5<\37\2\u0232\u0233\f\5\2\2\u0233\u0237\5L\'\2\u0234\u0235\f\4"+
		"\2\2\u0235\u0237\7\16\2\2\u0236\u0230\3\2\2\2\u0236\u0232\3\2\2\2\u0236"+
		"\u0234\3\2\2\2\u0237\u023a\3\2\2\2\u0238\u0236\3\2\2\2\u0238\u0239\3\2"+
		"\2\2\u0239\65\3\2\2\2\u023a\u0238\3\2\2\2\u023b\u023e\5B\"\2\u023c\u023e"+
		"\3\2\2\2\u023d\u023b\3\2\2\2\u023d\u023c\3\2\2\2\u023e\67\3\2\2\2\u023f"+
		"\u0240\t\2\2\2\u02409\3\2\2\2\u0241\u0245\7\22\2\2\u0242\u0245\7\23\2"+
		"\2\u0243\u0245\3\2\2\2\u0244\u0241\3\2\2\2\u0244\u0242\3\2\2\2\u0244\u0243"+
		"\3\2\2\2\u0245;\3\2\2\2\u0246\u0247\5:\36\2\u0247\u0248\5@!\2\u0248\u0249"+
		"\5F$\2\u0249\u024a\7\16\2\2\u024a\u0257\3\2\2\2\u024b\u024c\5:\36\2\u024c"+
		"\u024d\5@!\2\u024d\u024e\5F$\2\u024e\u0257\3\2\2\2\u024f\u0250\5@!\2\u0250"+
		"\u0251\5F$\2\u0251\u0252\7\16\2\2\u0252\u0257\3\2\2\2\u0253\u0254\5@!"+
		"\2\u0254\u0255\5F$\2\u0255\u0257\3\2\2\2\u0256\u0246\3\2\2\2\u0256\u024b"+
		"\3\2\2\2\u0256\u024f\3\2\2\2\u0256\u0253\3\2\2\2\u0257=\3\2\2\2\u0258"+
		"\u0259\5@!\2\u0259\u025a\7?\2\2\u025a?\3\2\2\2\u025b\u025c\5B\"\2\u025c"+
		"\u025d\7\f\2\2\u025d\u025e\7\r\2\2\u025e\u0261\3\2\2\2\u025f\u0261\5B"+
		"\"\2\u0260\u025b\3\2\2\2\u0260\u025f\3\2\2\2\u0261A\3\2\2\2\u0262\u0267"+
		"\7?\2\2\u0263\u0264\7?\2\2\u0264\u0267\5\24\13\2\u0265\u0267\5D#\2\u0266"+
		"\u0262\3\2\2\2\u0266\u0263\3\2\2\2\u0266\u0265\3\2\2\2\u0267C\3\2\2\2"+
		"\u0268\u0269\t\3\2\2\u0269E\3\2\2\2\u026a\u026b\b$\1\2\u026b\u0270\7?"+
		"\2\2\u026c\u026d\7?\2\2\u026d\u026e\7C\2\2\u026e\u0270\5L\'\2\u026f\u026a"+
		"\3\2\2\2\u026f\u026c\3\2\2\2\u0270\u027b\3\2\2\2\u0271\u0272\f\5\2\2\u0272"+
		"\u0273\7\n\2\2\u0273\u027a\7?\2\2\u0274\u0275\f\3\2\2\u0275\u0276\7\n"+
		"\2\2\u0276\u0277\7?\2\2\u0277\u0278\7C\2\2\u0278\u027a\5L\'\2\u0279\u0271"+
		"\3\2\2\2\u0279\u0274\3\2\2\2\u027a\u027d\3\2\2\2\u027b\u0279\3\2\2\2\u027b"+
		"\u027c\3\2\2\2\u027cG\3\2\2\2\u027d\u027b\3\2\2\2\u027e\u027f\b%\1\2\u027f"+
		"\u0282\5J&\2\u0280\u0282\3\2\2\2\u0281\u027e\3\2\2\2\u0281\u0280\3\2\2"+
		"\2\u0282\u0288\3\2\2\2\u0283\u0284\f\4\2\2\u0284\u0285\7\n\2\2\u0285\u0287"+
		"\5J&\2\u0286\u0283\3\2\2\2\u0287\u028a\3\2\2\2\u0288\u0286\3\2\2\2\u0288"+
		"\u0289\3\2\2\2\u0289I\3\2\2\2\u028a\u0288\3\2\2\2\u028b\u028c\5@!\2\u028c"+
		"\u028d\7?\2\2\u028dK\3\2\2\2\u028e\u029c\5N(\2\u028f\u029c\5V,\2\u0290"+
		"\u029c\5`\61\2\u0291\u029c\5d\63\2\u0292\u029c\5f\64\2\u0293\u029c\5h"+
		"\65\2\u0294\u029c\5j\66\2\u0295\u029c\5l\67\2\u0296\u029c\7,\2\2\u0297"+
		"\u029c\7-\2\2\u0298\u0299\7.\2\2\u0299\u029c\5L\'\2\u029a\u029c\7.\2\2"+
		"\u029b\u028e\3\2\2\2\u029b\u028f\3\2\2\2\u029b\u0290\3\2\2\2\u029b\u0291"+
		"\3\2\2\2\u029b\u0292\3\2\2\2\u029b\u0293\3\2\2\2\u029b\u0294\3\2\2\2\u029b"+
		"\u0295\3\2\2\2\u029b\u0296\3\2\2\2\u029b\u0297\3\2\2\2\u029b\u0298\3\2"+
		"\2\2\u029b\u029a\3\2\2\2\u029cM\3\2\2\2\u029d\u029e\b(\1\2\u029e\u02a3"+
		"\5P)\2\u029f\u02a0\7\67\2\2\u02a0\u02a2\5P)\2\u02a1\u029f\3\2\2\2\u02a2"+
		"\u02a5\3\2\2\2\u02a3\u02a1\3\2\2\2\u02a3\u02a4\3\2\2\2\u02a4\u02a8\3\2"+
		"\2\2\u02a5\u02a3\3\2\2\2\u02a6\u02a8\5d\63\2\u02a7\u029d\3\2\2\2\u02a7"+
		"\u02a6\3\2\2\2\u02a8\u02ae\3\2\2\2\u02a9\u02aa\f\3\2\2\u02aa\u02ab\7C"+
		"\2\2\u02ab\u02ad\5L\'\2\u02ac\u02a9\3\2\2\2\u02ad\u02b0\3\2\2\2\u02ae"+
		"\u02ac\3\2\2\2\u02ae\u02af\3\2\2\2\u02afO\3\2\2\2\u02b0\u02ae\3\2\2\2"+
		"\u02b1\u02b2\b)\1\2\u02b2\u02b7\5R*\2\u02b3\u02b4\78\2\2\u02b4\u02b6\5"+
		"R*\2\u02b5\u02b3\3\2\2\2\u02b6\u02b9\3\2\2\2\u02b7\u02b5\3\2\2\2\u02b7"+
		"\u02b8\3\2\2\2\u02b8\u02bf\3\2\2\2\u02b9\u02b7\3\2\2\2\u02ba\u02bb\f\4"+
		"\2\2\u02bb\u02bc\7\64\2\2\u02bc\u02be\5T+\2\u02bd\u02ba\3\2\2\2\u02be"+
		"\u02c1\3\2\2\2\u02bf\u02bd\3\2\2\2\u02bf\u02c0\3\2\2\2\u02c0Q\3\2\2\2"+
		"\u02c1\u02bf\3\2\2\2\u02c2\u02c3\7\67\2\2\u02c3\u02c6\5T+\2\u02c4\u02c6"+
		"\5T+\2\u02c5\u02c2\3\2\2\2\u02c5\u02c4\3\2\2\2\u02c6S\3\2\2\2\u02c7\u02c8"+
		"\b+\1\2\u02c8\u02c9\7>\2\2\u02c9\u02f1\5T+\f\u02ca\u02cb\7\60\2\2\u02cb"+
		"\u02f1\5^\60\2\u02cc\u02cd\7\60\2\2\u02cd\u02ce\5B\"\2\u02ce\u02cf\7\f"+
		"\2\2\u02cf\u02d0\5L\'\2\u02d0\u02d1\7\r\2\2\u02d1\u02f1\3\2\2\2\u02d2"+
		"\u02d3\7\60\2\2\u02d3\u02d4\7?\2\2\u02d4\u02d5\5\24\13\2\u02d5\u02d6\7"+
		"\20\2\2\u02d6\u02d7\5t;\2\u02d7\u02d8\7\21\2\2\u02d8\u02f1\3\2\2\2\u02d9"+
		"\u02da\5D#\2\u02da\u02db\7\30\2\2\u02db\u02dc\5^\60\2\u02dc\u02f1\3\2"+
		"\2\2\u02dd\u02f1\5p9\2\u02de\u02f1\5^\60\2\u02df\u02f1\7?\2\2\u02e0\u02f1"+
		"\5r:\2\u02e1\u02e2\7\20\2\2\u02e2\u02e3\5N(\2\u02e3\u02e4\7\21\2\2\u02e4"+
		"\u02f1\3\2\2\2\u02e5\u02e6\7>\2\2\u02e6\u02e7\5L\'\2\u02e7\u02e8\7\f\2"+
		"\2\u02e8\u02e9\5L\'\2\u02e9\u02ea\7\r\2\2\u02ea\u02f1\3\2\2\2\u02eb\u02ec"+
		"\7>\2\2\u02ec\u02ed\5L\'\2\u02ed\u02ee\7\30\2\2\u02ee\u02ef\7?\2\2\u02ef"+
		"\u02f1\3\2\2\2\u02f0\u02c7\3\2\2\2\u02f0\u02ca\3\2\2\2\u02f0\u02cc\3\2"+
		"\2\2\u02f0\u02d2\3\2\2\2\u02f0\u02d9\3\2\2\2\u02f0\u02dd\3\2\2\2\u02f0"+
		"\u02de\3\2\2\2\u02f0\u02df\3\2\2\2\u02f0\u02e0\3\2\2\2\u02f0\u02e1\3\2"+
		"\2\2\u02f0\u02e5\3\2\2\2\u02f0\u02eb\3\2\2\2\u02f1\u0313\3\2\2\2\u02f2"+
		"\u02f3\f\23\2\2\u02f3\u02f4\7\30\2\2\u02f4\u0312\5^\60\2\u02f5\u02f6\f"+
		"\21\2\2\u02f6\u02f7\7\30\2\2\u02f7\u0312\7?\2\2\u02f8\u02f9\f\r\2\2\u02f9"+
		"\u0312\7>\2\2\u02fa\u02fb\f\t\2\2\u02fb\u02fc\7\f\2\2\u02fc\u02fd\5L\'"+
		"\2\u02fd\u02fe\7\r\2\2\u02fe\u0312\3\2\2\2\u02ff\u0300\f\b\2\2\u0300\u0301"+
		"\7\f\2\2\u0301\u0302\5L\'\2\u0302\u0303\7\r\2\2\u0303\u0304\7>\2\2\u0304"+
		"\u0312\3\2\2\2\u0305\u0306\f\5\2\2\u0306\u0307\7\30\2\2\u0307\u0308\7"+
		"?\2\2\u0308\u0312\7>\2\2\u0309\u030a\f\4\2\2\u030a\u030b\7\30\2\2\u030b"+
		"\u0312\7\61\2\2\u030c\u030d\f\3\2\2\u030d\u030e\7\30\2\2\u030e\u030f\7"+
		"\61\2\2\u030f\u0310\7\20\2\2\u0310\u0312\7\21\2\2\u0311\u02f2\3\2\2\2"+
		"\u0311\u02f5\3\2\2\2\u0311\u02f8\3\2\2\2\u0311\u02fa\3\2\2\2\u0311\u02ff"+
		"\3\2\2\2\u0311\u0305\3\2\2\2\u0311\u0309\3\2\2\2\u0311\u030c\3\2\2\2\u0312"+
		"\u0315\3\2\2\2\u0313\u0311\3\2\2\2\u0313\u0314\3\2\2\2\u0314U\3\2\2\2"+
		"\u0315\u0313\3\2\2\2\u0316\u0317\b,\1\2\u0317\u031c\5X-\2\u0318\u0319"+
		"\7\65\2\2\u0319\u031b\5X-\2\u031a\u0318\3\2\2\2\u031b\u031e\3\2\2\2\u031c"+
		"\u031a\3\2\2\2\u031c\u031d\3\2\2\2\u031d\u0326\3\2\2\2\u031e\u031c\3\2"+
		"\2\2\u031f\u0326\5d\63\2\u0320\u0321\5N(\2\u0321\u0322\t\4\2\2\u0322\u0323"+
		"\5N(\2\u0323\u0326\3\2\2\2\u0324\u0326\5N(\2\u0325\u0316\3\2\2\2\u0325"+
		"\u031f\3\2\2\2\u0325\u0320\3\2\2\2\u0325\u0324\3\2\2\2\u0326\u032f\3\2"+
		"\2\2\u0327\u0328\f\6\2\2\u0328\u0329\t\5\2\2\u0329\u032e\5V,\7\u032a\u032b"+
		"\f\4\2\2\u032b\u032c\7C\2\2\u032c\u032e\5L\'\2\u032d\u0327\3\2\2\2\u032d"+
		"\u032a\3\2\2\2\u032e\u0331\3\2\2\2\u032f\u032d\3\2\2\2\u032f\u0330\3\2"+
		"\2\2\u0330W\3\2\2\2\u0331\u032f\3\2\2\2\u0332\u0337\5Z.\2\u0333\u0334"+
		"\7\66\2\2\u0334\u0336\5Z.\2\u0335\u0333\3\2\2\2\u0336\u0339\3\2\2\2\u0337"+
		"\u0335\3\2\2\2\u0337\u0338\3\2\2\2\u0338Y\3\2\2\2\u0339\u0337\3\2\2\2"+
		"\u033a\u033b\7=\2\2\u033b\u033e\5V,\2\u033c\u033e\5\\/\2\u033d\u033a\3"+
		"\2\2\2\u033d\u033c\3\2\2\2\u033e[\3\2\2\2\u033f\u0347\5r:\2\u0340\u0347"+
		"\7?\2\2\u0341\u0347\5p9\2\u0342\u0343\7\20\2\2\u0343\u0344\5V,\2\u0344"+
		"\u0345\7\21\2\2\u0345\u0347\3\2\2\2\u0346\u033f\3\2\2\2\u0346\u0340\3"+
		"\2\2\2\u0346\u0341\3\2\2\2\u0346\u0342\3\2\2\2\u0347]\3\2\2\2\u0348\u0349"+
		"\58\35\2\u0349\u034a\7\20\2\2\u034a\u034b\5t;\2\u034b\u034c\7\21\2\2\u034c"+
		"\u0353\3\2\2\2\u034d\u034e\5B\"\2\u034e\u034f\7\20\2\2\u034f\u0350\5t"+
		";\2\u0350\u0351\7\21\2\2\u0351\u0353\3\2\2\2\u0352\u0348\3\2\2\2\u0352"+
		"\u034d\3\2\2\2\u0353_\3\2\2\2\u0354\u0355\7\4\2\2\u0355\u0356\5\64\33"+
		"\2\u0356\u0357\7\5\2\2\u0357\u035b\3\2\2\2\u0358\u0359\7\4\2\2\u0359\u035b"+
		"\7\5\2\2\u035a\u0354\3\2\2\2\u035a\u0358\3\2\2\2\u035ba\3\2\2\2\u035c"+
		"\u035f\5L\'\2\u035d\u035f\3\2\2\2\u035e\u035c\3\2\2\2\u035e\u035d\3\2"+
		"\2\2\u035fc\3\2\2\2\u0360\u0361\7\37\2\2\u0361\u0362\7\20\2\2\u0362\u0363"+
		"\5L\'\2\u0363\u0364\7\21\2\2\u0364\u0365\5L\'\2\u0365\u036f\3\2\2\2\u0366"+
		"\u0367\7\37\2\2\u0367\u0368\7\20\2\2\u0368\u0369\5L\'\2\u0369\u036a\7"+
		"\21\2\2\u036a\u036b\5L\'\2\u036b\u036c\7 \2\2\u036c\u036d\5L\'\2\u036d"+
		"\u036f\3\2\2\2\u036e\u0360\3\2\2\2\u036e\u0366\3\2\2\2\u036fe\3\2\2\2"+
		"\u0370\u0371\7!\2\2\u0371\u0372\7\20\2\2\u0372\u0373\5L\'\2\u0373\u0374"+
		"\7\16\2\2\u0374\u0375\5L\'\2\u0375\u0376\7\16\2\2\u0376\u0377\5L\'\2\u0377"+
		"\u0378\7\21\2\2\u0378\u0379\5L\'\2\u0379\u0394\3\2\2\2\u037a\u037b\7!"+
		"\2\2\u037b\u037c\7\20\2\2\u037c\u037d\5<\37\2\u037d\u037e\5L\'\2\u037e"+
		"\u037f\7\16\2\2\u037f\u0380\5L\'\2\u0380\u0381\7\21\2\2\u0381\u0382\5"+
		"L\'\2\u0382\u0394\3\2\2\2\u0383\u0384\7!\2\2\u0384\u0385\7\20\2\2\u0385"+
		"\u0386\7?\2\2\u0386\u0387\7\"\2\2\u0387\u0388\5L\'\2\u0388\u0389\7\21"+
		"\2\2\u0389\u038a\5L\'\2\u038a\u0394\3\2\2\2\u038b\u038c\7!\2\2\u038c\u038d"+
		"\7\20\2\2\u038d\u038e\5> \2\u038e\u038f\7\"\2\2\u038f\u0390\5L\'\2\u0390"+
		"\u0391\7\21\2\2\u0391\u0392\5L\'\2\u0392\u0394\3\2\2\2\u0393\u0370\3\2"+
		"\2\2\u0393\u037a\3\2\2\2\u0393\u0383\3\2\2\2\u0393\u038b\3\2\2\2\u0394"+
		"g\3\2\2\2\u0395\u0396\7#\2\2\u0396\u0397\7\20\2\2\u0397\u0398\5L\'\2\u0398"+
		"\u0399\7\21\2\2\u0399\u039a\5L\'\2\u039ai\3\2\2\2\u039b\u039c\7$\2\2\u039c"+
		"\u039d\5L\'\2\u039d\u039e\7#\2\2\u039e\u039f\7\20\2\2\u039f\u03a0\5L\'"+
		"\2\u03a0\u03a1\7\21\2\2\u03a1k\3\2\2\2\u03a2\u03a3\7)\2\2\u03a3\u03a4"+
		"\7\20\2\2\u03a4\u03a5\5L\'\2\u03a5\u03a6\7\21\2\2\u03a6\u03aa\7\4\2\2"+
		"\u03a7\u03a9\5n8\2\u03a8\u03a7\3\2\2\2\u03a9\u03ac\3\2\2\2\u03aa\u03a8"+
		"\3\2\2\2\u03aa\u03ab\3\2\2\2\u03ab\u03ad\3\2\2\2\u03ac\u03aa\3\2\2\2\u03ad"+
		"\u03ae\7\5\2\2\u03aem\3\2\2\2\u03af\u03b0\7*\2\2\u03b0\u03b3\5r:\2\u03b1"+
		"\u03b3\7+\2\2\u03b2\u03af\3\2\2\2\u03b2\u03b1\3\2\2\2\u03b3\u03b4\3\2"+
		"\2\2\u03b4\u03b5\7\"\2\2\u03b5\u03b6\5\64\33\2\u03b6o\3\2\2\2\u03b7\u03b8"+
		"\7\62\2\2\u03b8q\3\2\2\2\u03b9\u03ba\t\6\2\2\u03bas\3\2\2\2\u03bb\u03bc"+
		"\b;\1\2\u03bc\u03bf\5L\'\2\u03bd\u03bf\3\2\2\2\u03be\u03bb\3\2\2\2\u03be"+
		"\u03bd\3\2\2\2\u03bf\u03c5\3\2\2\2\u03c0\u03c1\f\4\2\2\u03c1\u03c2\7\n"+
		"\2\2\u03c2\u03c4\5L\'\2\u03c3\u03c0\3\2\2\2\u03c4\u03c7\3\2\2\2\u03c5"+
		"\u03c3\3\2\2\2\u03c5\u03c6\3\2\2\2\u03c6u\3\2\2\2\u03c7\u03c5\3\2\2\2"+
		"Nz\u0081\u0087\u008d\u009b\u00aa\u00b6\u00c4\u00d0\u00d9\u00ea\u00f3\u00fe"+
		"\u0106\u010b\u0111\u0118\u0166\u016b\u0171\u0177\u0179\u0184\u0186\u01d1"+
		"\u01d6\u01dc\u01de\u01e4\u01ea\u01ef\u01f9\u01fb\u020e\u0217\u0222\u0225"+
		"\u022e\u0236\u0238\u023d\u0244\u0256\u0260\u0266\u026f\u0279\u027b\u0281"+
		"\u0288\u029b\u02a3\u02a7\u02ae\u02b7\u02bf\u02c5\u02f0\u0311\u0313\u031c"+
		"\u0325\u032d\u032f\u0337\u033d\u0346\u0352\u035a\u035e\u036e\u0393\u03aa"+
		"\u03b2\u03be\u03c5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}