// Generated from src/main/java/info/fulloo/trygve/parser/Kant.g4 by ANTLR 4.5.1

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
		T__31=32, STRING=33, INTEGER=34, FLOAT=35, BOOLEAN=36, SWITCH=37, CASE=38, 
		DEFAULT=39, BREAK=40, CONTINUE=41, RETURN=42, REQUIRES=43, NEW=44, CLONE=45, 
		NULL=46, CONST=47, LOGICAL_NOT=48, POW=49, BOOLEAN_SUMOP=50, BOOLEAN_MULOP=51, 
		ABELIAN_SUMOP=52, ABELIAN_MULOP=53, MINUS=54, PLUS=55, LT=56, GT=57, LOGICAL_NEGATION=58, 
		ABELIAN_INCREMENT_OP=59, JAVA_ID=60, INLINE_COMMENT=61, C_COMMENT=62, 
		WHITESPACE=63, ASSIGN=64;
	public static final int
		RULE_program = 0, RULE_main = 1, RULE_type_declaration_list = 2, RULE_type_declaration = 3, 
		RULE_implements_list = 4, RULE_type_parameters = 5, RULE_type_list = 6, 
		RULE_type_parameter = 7, RULE_context_body = 8, RULE_context_body_element = 9, 
		RULE_role_decl = 10, RULE_role_vec_modifier = 11, RULE_role_body = 12, 
		RULE_self_methods = 13, RULE_stageprop_decl = 14, RULE_stageprop_body = 15, 
		RULE_class_body = 16, RULE_class_body_element = 17, RULE_interface_body = 18, 
		RULE_method_decl = 19, RULE_method_decl_hook = 20, RULE_method_signature = 21, 
		RULE_expr_and_decl_list = 22, RULE_return_type = 23, RULE_method_name = 24, 
		RULE_access_qualifier = 25, RULE_object_decl = 26, RULE_trivial_object_decl = 27, 
		RULE_compound_type_name = 28, RULE_type_name = 29, RULE_identifier_list = 30, 
		RULE_param_list = 31, RULE_param_decl = 32, RULE_expr = 33, RULE_abelian_expr = 34, 
		RULE_abelian_product = 35, RULE_abelian_unary_op = 36, RULE_abelian_atom = 37, 
		RULE_message = 38, RULE_boolean_expr = 39, RULE_block = 40, RULE_expr_or_null = 41, 
		RULE_if_expr = 42, RULE_for_expr = 43, RULE_while_expr = 44, RULE_do_while_expr = 45, 
		RULE_switch_expr = 46, RULE_switch_body = 47, RULE_null_expr = 48, RULE_constant = 49, 
		RULE_argument_list = 50;
	public static final String[] ruleNames = {
		"program", "main", "type_declaration_list", "type_declaration", "implements_list", 
		"type_parameters", "type_list", "type_parameter", "context_body", "context_body_element", 
		"role_decl", "role_vec_modifier", "role_body", "self_methods", "stageprop_decl", 
		"stageprop_body", "class_body", "class_body_element", "interface_body", 
		"method_decl", "method_decl_hook", "method_signature", "expr_and_decl_list", 
		"return_type", "method_name", "access_qualifier", "object_decl", "trivial_object_decl", 
		"compound_type_name", "type_name", "identifier_list", "param_list", "param_decl", 
		"expr", "abelian_expr", "abelian_product", "abelian_unary_op", "abelian_atom", 
		"message", "boolean_expr", "block", "expr_or_null", "if_expr", "for_expr", 
		"while_expr", "do_while_expr", "switch_expr", "switch_body", "null_expr", 
		"constant", "argument_list"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'context'", "'{'", "'}'", "'class'", "'extends'", "'interface'", 
		"'implements'", "','", "'role'", "'['", "']'", "';'", "'stageprop'", "'('", 
		"')'", "'public'", "'private'", "'int'", "'double'", "'char'", "'String'", 
		"'!='", "'=='", "'>='", "'<='", "'.'", "'if'", "'else'", "'for'", "':'", 
		"'while'", "'do'", null, null, null, null, "'switch'", "'case'", "'default'", 
		"'break'", "'continue'", "'return'", "'requires'", "'new'", "'clone'", 
		"'null'", "'const'", null, "'**'", null, "'&&'", null, null, "'-'", "'+'", 
		"'<'", "'>'", null, null, null, null, null, null, "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, "STRING", "INTEGER", 
		"FLOAT", "BOOLEAN", "SWITCH", "CASE", "DEFAULT", "BREAK", "CONTINUE", 
		"RETURN", "REQUIRES", "NEW", "CLONE", "NULL", "CONST", "LOGICAL_NOT", 
		"POW", "BOOLEAN_SUMOP", "BOOLEAN_MULOP", "ABELIAN_SUMOP", "ABELIAN_MULOP", 
		"MINUS", "PLUS", "LT", "GT", "LOGICAL_NEGATION", "ABELIAN_INCREMENT_OP", 
		"JAVA_ID", "INLINE_COMMENT", "C_COMMENT", "WHITESPACE", "ASSIGN"
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
			setState(106);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(102);
				type_declaration_list(0);
				setState(103);
				main();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
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
			setState(108);
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
			setState(113);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(111);
				type_declaration();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(119);
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
					setState(115);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(116);
					type_declaration();
					}
					} 
				}
				setState(121);
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
		public List<TerminalNode> JAVA_ID() { return getTokens(KantParser.JAVA_ID); }
		public TerminalNode JAVA_ID(int i) {
			return getToken(KantParser.JAVA_ID, i);
		}
		public Context_bodyContext context_body() {
			return getRuleContext(Context_bodyContext.class,0);
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
		public Interface_bodyContext interface_body() {
			return getRuleContext(Interface_bodyContext.class,0);
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
		int _la;
		try {
			setState(202);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(122);
				match(T__0);
				setState(123);
				match(JAVA_ID);
				setState(124);
				match(T__1);
				setState(125);
				context_body(0);
				setState(126);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(128);
				match(T__3);
				setState(129);
				match(JAVA_ID);
				setState(130);
				type_parameters();
				setState(134);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(131);
					implements_list(0);
					}
					}
					setState(136);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(137);
				match(T__1);
				setState(138);
				class_body(0);
				setState(139);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(141);
				match(T__3);
				setState(142);
				match(JAVA_ID);
				setState(143);
				type_parameters();
				setState(144);
				match(T__4);
				setState(145);
				match(JAVA_ID);
				setState(149);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(146);
					implements_list(0);
					}
					}
					setState(151);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(152);
				match(T__1);
				setState(153);
				class_body(0);
				setState(154);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(156);
				match(T__3);
				setState(157);
				match(JAVA_ID);
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
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(168);
				match(T__3);
				setState(169);
				match(JAVA_ID);
				setState(170);
				match(T__4);
				setState(171);
				match(JAVA_ID);
				setState(175);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(172);
					implements_list(0);
					}
					}
					setState(177);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(178);
				match(T__1);
				setState(179);
				class_body(0);
				setState(180);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(182);
				match(T__3);
				setState(183);
				match(JAVA_ID);
				setState(187);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(184);
					implements_list(0);
					}
					}
					setState(189);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(190);
				match(T__4);
				setState(191);
				match(JAVA_ID);
				setState(192);
				match(T__1);
				setState(193);
				class_body(0);
				setState(194);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(196);
				match(T__5);
				setState(197);
				match(JAVA_ID);
				setState(198);
				match(T__1);
				setState(199);
				interface_body(0);
				setState(200);
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
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_implements_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(205);
			match(T__6);
			setState(206);
			match(JAVA_ID);
			}
			_ctx.stop = _input.LT(-1);
			setState(213);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Implements_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_implements_list);
					setState(208);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(209);
					match(T__7);
					setState(210);
					match(JAVA_ID);
					}
					} 
				}
				setState(215);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
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
		enterRule(_localctx, 10, RULE_type_parameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			match(LT);
			setState(217);
			type_parameter();
			setState(222);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(218);
				match(T__7);
				setState(219);
				type_parameter();
				}
				}
				setState(224);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(225);
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
		enterRule(_localctx, 12, RULE_type_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			match(LT);
			setState(228);
			type_name();
			setState(233);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(229);
				match(T__7);
				setState(230);
				type_name();
				}
				}
				setState(235);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(236);
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
		enterRule(_localctx, 14, RULE_type_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			type_name();
			setState(241);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(239);
				match(T__4);
				setState(240);
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
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_context_body, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(244);
				context_body_element();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(252);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Context_bodyContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_context_body);
					setState(248);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(249);
					context_body_element();
					}
					} 
				}
				setState(254);
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
		enterRule(_localctx, 18, RULE_context_body_element);
		try {
			setState(259);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(255);
				method_decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(256);
				object_decl();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(257);
				role_decl();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(258);
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
		enterRule(_localctx, 20, RULE_role_decl);
		try {
			setState(337);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(261);
				match(T__8);
				setState(262);
				role_vec_modifier();
				setState(263);
				match(JAVA_ID);
				setState(264);
				match(T__1);
				setState(265);
				role_body(0);
				setState(266);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(268);
				match(T__8);
				setState(269);
				role_vec_modifier();
				setState(270);
				match(JAVA_ID);
				setState(271);
				match(T__1);
				setState(272);
				role_body(0);
				setState(273);
				match(T__2);
				setState(274);
				match(REQUIRES);
				setState(275);
				match(T__1);
				setState(276);
				self_methods(0);
				setState(277);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(279);
				access_qualifier();
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
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(287);
				access_qualifier();
				setState(288);
				match(T__8);
				setState(289);
				role_vec_modifier();
				setState(290);
				match(JAVA_ID);
				setState(291);
				match(T__1);
				setState(292);
				role_body(0);
				setState(293);
				match(T__2);
				setState(294);
				match(REQUIRES);
				setState(295);
				match(T__1);
				setState(296);
				self_methods(0);
				setState(297);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
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
			case 6:
				enterOuterAlt(_localctx, 6);
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
				setState(312);
				match(REQUIRES);
				setState(313);
				match(T__1);
				setState(314);
				self_methods(0);
				setState(315);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(317);
				access_qualifier();
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
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(325);
				access_qualifier();
				setState(326);
				match(T__8);
				setState(327);
				role_vec_modifier();
				setState(328);
				match(JAVA_ID);
				setState(329);
				match(T__1);
				setState(330);
				role_body(0);
				setState(331);
				match(T__2);
				setState(332);
				match(REQUIRES);
				setState(333);
				match(T__1);
				setState(334);
				self_methods(0);
				setState(335);
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
		enterRule(_localctx, 22, RULE_role_vec_modifier);
		try {
			setState(342);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(339);
				match(T__9);
				setState(340);
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
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_role_body, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(348);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(345);
				method_decl();
				}
				break;
			case 2:
				{
				setState(346);
				object_decl();
				}
				break;
			case 3:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(356);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(354);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(350);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(351);
						method_decl();
						}
						break;
					case 2:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(352);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(353);
						object_decl();
						}
						break;
					}
					} 
				}
				setState(358);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
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
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_self_methods, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(360);
			method_signature();
			}
			_ctx.stop = _input.LT(-1);
			setState(369);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(367);
					switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
					case 1:
						{
						_localctx = new Self_methodsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_self_methods);
						setState(362);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(363);
						match(T__11);
						setState(364);
						method_signature();
						}
						break;
					case 2:
						{
						_localctx = new Self_methodsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_self_methods);
						setState(365);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(366);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(371);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
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
		public Role_bodyContext role_body() {
			return getRuleContext(Role_bodyContext.class,0);
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
		enterRule(_localctx, 28, RULE_stageprop_decl);
		try {
			setState(410);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(372);
				match(T__12);
				setState(373);
				role_vec_modifier();
				setState(374);
				match(JAVA_ID);
				setState(375);
				match(T__1);
				setState(376);
				stageprop_body(0);
				setState(377);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(379);
				match(T__12);
				setState(380);
				role_vec_modifier();
				setState(381);
				match(JAVA_ID);
				setState(382);
				match(T__1);
				setState(383);
				stageprop_body(0);
				setState(384);
				match(T__2);
				setState(385);
				match(REQUIRES);
				setState(386);
				match(T__1);
				setState(387);
				self_methods(0);
				setState(388);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(390);
				access_qualifier();
				setState(391);
				match(T__12);
				setState(392);
				role_vec_modifier();
				setState(393);
				match(JAVA_ID);
				setState(394);
				match(T__1);
				setState(395);
				role_body(0);
				setState(396);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(398);
				access_qualifier();
				setState(399);
				match(T__12);
				setState(400);
				role_vec_modifier();
				setState(401);
				match(JAVA_ID);
				setState(402);
				match(T__1);
				setState(403);
				role_body(0);
				setState(404);
				match(T__2);
				setState(405);
				match(REQUIRES);
				setState(406);
				match(T__1);
				setState(407);
				self_methods(0);
				setState(408);
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
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_stageprop_body, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(415);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(413);
				method_decl();
				}
				break;
			case 2:
				{
				setState(414);
				object_decl();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(423);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(421);
					switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
					case 1:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(417);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(418);
						method_decl();
						}
						break;
					case 2:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(419);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(420);
						object_decl();
						}
						break;
					}
					} 
				}
				setState(425);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
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
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_class_body, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(429);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(427);
				class_body_element();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(435);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Class_bodyContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_class_body);
					setState(431);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(432);
					class_body_element();
					}
					} 
				}
				setState(437);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
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
		enterRule(_localctx, 34, RULE_class_body_element);
		try {
			setState(440);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(438);
				method_decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(439);
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
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_interface_body, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(443);
			method_signature();
			}
			_ctx.stop = _input.LT(-1);
			setState(452);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(450);
					switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
					case 1:
						{
						_localctx = new Interface_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_interface_body);
						setState(445);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(446);
						match(T__11);
						setState(447);
						method_signature();
						}
						break;
					case 2:
						{
						_localctx = new Interface_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_interface_body);
						setState(448);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(449);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(454);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
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
		enterRule(_localctx, 38, RULE_method_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(455);
			method_decl_hook();
			setState(456);
			match(T__1);
			setState(457);
			expr_and_decl_list(0);
			setState(458);
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
		enterRule(_localctx, 40, RULE_method_decl_hook);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(460);
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
		enterRule(_localctx, 42, RULE_method_signature);
		try {
			int _alt;
			setState(494);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(462);
				access_qualifier();
				setState(463);
				return_type();
				setState(464);
				method_name();
				setState(465);
				match(T__13);
				setState(466);
				param_list(0);
				setState(467);
				match(T__14);
				setState(471);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(468);
						match(CONST);
						}
						} 
					}
					setState(473);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(474);
				access_qualifier();
				setState(475);
				return_type();
				setState(476);
				method_name();
				setState(480);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(477);
						match(CONST);
						}
						} 
					}
					setState(482);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(483);
				access_qualifier();
				setState(484);
				method_name();
				setState(485);
				match(T__13);
				setState(486);
				param_list(0);
				setState(487);
				match(T__14);
				setState(491);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(488);
						match(CONST);
						}
						} 
					}
					setState(493);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
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
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_expr_and_decl_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(503);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				setState(497);
				object_decl();
				}
				break;
			case 2:
				{
				setState(498);
				expr();
				setState(499);
				match(T__11);
				setState(500);
				object_decl();
				}
				break;
			case 3:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(513);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(511);
					switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
					case 1:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(505);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(506);
						object_decl();
						}
						break;
					case 2:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(507);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(508);
						expr();
						}
						break;
					case 3:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(509);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(510);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(515);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
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
		enterRule(_localctx, 46, RULE_return_type);
		try {
			setState(518);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(516);
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
		enterRule(_localctx, 48, RULE_method_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(520);
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
		enterRule(_localctx, 50, RULE_access_qualifier);
		try {
			setState(525);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(522);
				match(T__15);
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 2);
				{
				setState(523);
				match(T__16);
				}
				break;
			case T__8:
			case T__12:
			case T__17:
			case T__18:
			case T__19:
			case T__20:
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
		enterRule(_localctx, 52, RULE_object_decl);
		try {
			setState(543);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(527);
				access_qualifier();
				setState(528);
				compound_type_name();
				setState(529);
				identifier_list(0);
				setState(530);
				match(T__11);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(532);
				access_qualifier();
				setState(533);
				compound_type_name();
				setState(534);
				identifier_list(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(536);
				compound_type_name();
				setState(537);
				identifier_list(0);
				setState(538);
				match(T__11);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(540);
				compound_type_name();
				setState(541);
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
		enterRule(_localctx, 54, RULE_trivial_object_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(545);
			compound_type_name();
			setState(546);
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
		enterRule(_localctx, 56, RULE_compound_type_name);
		try {
			setState(553);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(548);
				type_name();
				setState(549);
				match(T__9);
				setState(550);
				match(T__10);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(552);
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
		enterRule(_localctx, 58, RULE_type_name);
		try {
			setState(562);
			switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(555);
				match(JAVA_ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(556);
				match(JAVA_ID);
				setState(557);
				type_list();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(558);
				match(T__17);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(559);
				match(T__18);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(560);
				match(T__19);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(561);
				match(T__20);
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
		int _startState = 60;
		enterRecursionRule(_localctx, 60, RULE_identifier_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(569);
			switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
			case 1:
				{
				setState(565);
				match(JAVA_ID);
				}
				break;
			case 2:
				{
				setState(566);
				match(JAVA_ID);
				setState(567);
				match(ASSIGN);
				setState(568);
				expr();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(581);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(579);
					switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
					case 1:
						{
						_localctx = new Identifier_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identifier_list);
						setState(571);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(572);
						match(T__7);
						setState(573);
						match(JAVA_ID);
						}
						break;
					case 2:
						{
						_localctx = new Identifier_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identifier_list);
						setState(574);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(575);
						match(T__7);
						setState(576);
						match(JAVA_ID);
						setState(577);
						match(ASSIGN);
						setState(578);
						expr();
						}
						break;
					}
					} 
				}
				setState(583);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
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
		int _startState = 62;
		enterRecursionRule(_localctx, 62, RULE_param_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(587);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				{
				setState(585);
				param_decl();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(594);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Param_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_param_list);
					setState(589);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(590);
					match(T__7);
					setState(591);
					param_decl();
					}
					} 
				}
				setState(596);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
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
		enterRule(_localctx, 64, RULE_param_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(597);
			compound_type_name();
			setState(598);
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
		enterRule(_localctx, 66, RULE_expr);
		try {
			setState(613);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(600);
				abelian_expr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(601);
				boolean_expr(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(602);
				block();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(603);
				if_expr();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(604);
				for_expr();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(605);
				while_expr();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(606);
				do_while_expr();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(607);
				switch_expr();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(608);
				match(BREAK);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(609);
				match(CONTINUE);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(610);
				match(RETURN);
				setState(611);
				expr();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(612);
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
		public Token op;
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
		public List<Abelian_exprContext> abelian_expr() {
			return getRuleContexts(Abelian_exprContext.class);
		}
		public Abelian_exprContext abelian_expr(int i) {
			return getRuleContext(Abelian_exprContext.class,i);
		}
		public TerminalNode GT() { return getToken(KantParser.GT, 0); }
		public TerminalNode LT() { return getToken(KantParser.LT, 0); }
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
		int _startState = 68;
		enterRecursionRule(_localctx, 68, RULE_abelian_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(616);
			abelian_product(0);
			setState(621);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(617);
					match(ABELIAN_SUMOP);
					setState(618);
					abelian_product(0);
					}
					} 
				}
				setState(623);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
			}
			}
			_ctx.stop = _input.LT(-1);
			setState(632);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(630);
					switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
					case 1:
						{
						_localctx = new Abelian_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_expr);
						setState(624);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(625);
						((Abelian_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << LT) | (1L << GT))) != 0)) ) {
							((Abelian_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(626);
						abelian_expr(3);
						}
						break;
					case 2:
						{
						_localctx = new Abelian_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_expr);
						setState(627);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(628);
						match(ASSIGN);
						setState(629);
						expr();
						}
						break;
					}
					} 
				}
				setState(634);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
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
		int _startState = 70;
		enterRecursionRule(_localctx, 70, RULE_abelian_product, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(636);
			abelian_unary_op();
			setState(641);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(637);
					match(ABELIAN_MULOP);
					setState(638);
					abelian_unary_op();
					}
					} 
				}
				setState(643);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			}
			}
			_ctx.stop = _input.LT(-1);
			setState(649);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Abelian_productContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_abelian_product);
					setState(644);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(645);
					match(POW);
					setState(646);
					abelian_atom(0);
					}
					} 
				}
				setState(651);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
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
		public TerminalNode LOGICAL_NEGATION() { return getToken(KantParser.LOGICAL_NEGATION, 0); }
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
		enterRule(_localctx, 72, RULE_abelian_unary_op);
		try {
			setState(657);
			switch (_input.LA(1)) {
			case ABELIAN_SUMOP:
				enterOuterAlt(_localctx, 1);
				{
				setState(652);
				match(ABELIAN_SUMOP);
				setState(653);
				abelian_atom(0);
				}
				break;
			case LOGICAL_NEGATION:
				enterOuterAlt(_localctx, 2);
				{
				setState(654);
				match(LOGICAL_NEGATION);
				setState(655);
				abelian_atom(0);
				}
				break;
			case T__13:
			case STRING:
			case INTEGER:
			case FLOAT:
			case BOOLEAN:
			case NEW:
			case NULL:
			case ABELIAN_INCREMENT_OP:
			case JAVA_ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(656);
				abelian_atom(0);
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
		int _startState = 74;
		enterRecursionRule(_localctx, 74, RULE_abelian_atom, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(696);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				{
				setState(660);
				match(ABELIAN_INCREMENT_OP);
				setState(661);
				abelian_atom(10);
				}
				break;
			case 2:
				{
				setState(662);
				match(NEW);
				setState(663);
				message();
				}
				break;
			case 3:
				{
				setState(664);
				match(NEW);
				setState(665);
				type_name();
				setState(666);
				match(T__9);
				setState(667);
				expr();
				setState(668);
				match(T__10);
				}
				break;
			case 4:
				{
				setState(670);
				match(NEW);
				setState(671);
				match(JAVA_ID);
				setState(672);
				type_list();
				setState(673);
				match(T__13);
				setState(674);
				argument_list(0);
				setState(675);
				match(T__14);
				}
				break;
			case 5:
				{
				setState(677);
				null_expr();
				}
				break;
			case 6:
				{
				setState(678);
				message();
				}
				break;
			case 7:
				{
				setState(679);
				match(JAVA_ID);
				}
				break;
			case 8:
				{
				setState(680);
				constant();
				}
				break;
			case 9:
				{
				setState(681);
				match(T__13);
				setState(682);
				abelian_expr(0);
				setState(683);
				match(T__14);
				}
				break;
			case 10:
				{
				setState(685);
				match(ABELIAN_INCREMENT_OP);
				setState(686);
				expr();
				setState(687);
				match(T__9);
				setState(688);
				expr();
				setState(689);
				match(T__10);
				}
				break;
			case 11:
				{
				setState(691);
				match(ABELIAN_INCREMENT_OP);
				setState(692);
				expr();
				setState(693);
				match(T__25);
				setState(694);
				match(JAVA_ID);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(731);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(729);
					switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
					case 1:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(698);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(699);
						match(T__25);
						setState(700);
						message();
						}
						break;
					case 2:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(701);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(702);
						match(T__25);
						setState(703);
						match(JAVA_ID);
						}
						break;
					case 3:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(704);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(705);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 4:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(706);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(707);
						match(T__9);
						setState(708);
						expr();
						setState(709);
						match(T__10);
						}
						break;
					case 5:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(711);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(712);
						match(T__9);
						setState(713);
						expr();
						setState(714);
						match(T__10);
						setState(715);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 6:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(717);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(718);
						match(T__25);
						setState(719);
						match(JAVA_ID);
						setState(720);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 7:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(721);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(722);
						match(T__25);
						setState(723);
						match(CLONE);
						}
						break;
					case 8:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(724);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(725);
						match(T__25);
						setState(726);
						match(CLONE);
						setState(727);
						match(T__13);
						setState(728);
						match(T__14);
						}
						break;
					}
					} 
				}
				setState(733);
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

	public static class MessageContext extends ParserRuleContext {
		public TerminalNode JAVA_ID() { return getToken(KantParser.JAVA_ID, 0); }
		public Argument_listContext argument_list() {
			return getRuleContext(Argument_listContext.class,0);
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
		enterRule(_localctx, 76, RULE_message);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(734);
			match(JAVA_ID);
			setState(735);
			match(T__13);
			setState(736);
			argument_list(0);
			setState(737);
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

	public static class Boolean_exprContext extends ParserRuleContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public Abelian_exprContext abelian_expr() {
			return getRuleContext(Abelian_exprContext.class,0);
		}
		public Boolean_exprContext boolean_expr() {
			return getRuleContext(Boolean_exprContext.class,0);
		}
		public TerminalNode BOOLEAN_MULOP() { return getToken(KantParser.BOOLEAN_MULOP, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode BOOLEAN_SUMOP() { return getToken(KantParser.BOOLEAN_SUMOP, 0); }
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
		int _startState = 78;
		enterRecursionRule(_localctx, 78, RULE_boolean_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(742);
			switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
			case 1:
				{
				setState(740);
				constant();
				}
				break;
			case 2:
				{
				setState(741);
				abelian_expr(0);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(752);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(750);
					switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
					case 1:
						{
						_localctx = new Boolean_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolean_expr);
						setState(744);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(745);
						match(BOOLEAN_MULOP);
						setState(746);
						expr();
						}
						break;
					case 2:
						{
						_localctx = new Boolean_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolean_expr);
						setState(747);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(748);
						match(BOOLEAN_SUMOP);
						setState(749);
						expr();
						}
						break;
					}
					} 
				}
				setState(754);
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
		enterRule(_localctx, 80, RULE_block);
		try {
			setState(761);
			switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(755);
				match(T__1);
				setState(756);
				expr_and_decl_list(0);
				setState(757);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(759);
				match(T__1);
				setState(760);
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
		enterRule(_localctx, 82, RULE_expr_or_null);
		try {
			setState(765);
			switch (_input.LA(1)) {
			case T__1:
			case T__13:
			case T__26:
			case T__28:
			case T__30:
			case T__31:
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
			case LOGICAL_NEGATION:
			case ABELIAN_INCREMENT_OP:
			case JAVA_ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(763);
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
		enterRule(_localctx, 84, RULE_if_expr);
		try {
			setState(781);
			switch ( getInterpreter().adaptivePredict(_input,64,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(767);
				match(T__26);
				setState(768);
				match(T__13);
				setState(769);
				expr();
				setState(770);
				match(T__14);
				setState(771);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(773);
				match(T__26);
				setState(774);
				match(T__13);
				setState(775);
				expr();
				setState(776);
				match(T__14);
				setState(777);
				expr();
				setState(778);
				match(T__27);
				setState(779);
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
		enterRule(_localctx, 86, RULE_for_expr);
		try {
			setState(818);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(783);
				match(T__28);
				setState(784);
				match(T__13);
				setState(785);
				expr();
				setState(786);
				match(T__11);
				setState(787);
				expr();
				setState(788);
				match(T__11);
				setState(789);
				expr();
				setState(790);
				match(T__14);
				setState(791);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(793);
				match(T__28);
				setState(794);
				match(T__13);
				setState(795);
				object_decl();
				setState(796);
				expr();
				setState(797);
				match(T__11);
				setState(798);
				expr();
				setState(799);
				match(T__14);
				setState(800);
				expr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(802);
				match(T__28);
				setState(803);
				match(T__13);
				setState(804);
				match(JAVA_ID);
				setState(805);
				match(T__29);
				setState(806);
				expr();
				setState(807);
				match(T__14);
				setState(808);
				expr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(810);
				match(T__28);
				setState(811);
				match(T__13);
				setState(812);
				trivial_object_decl();
				setState(813);
				match(T__29);
				setState(814);
				expr();
				setState(815);
				match(T__14);
				setState(816);
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
		enterRule(_localctx, 88, RULE_while_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(820);
			match(T__30);
			setState(821);
			match(T__13);
			setState(822);
			expr();
			setState(823);
			match(T__14);
			setState(824);
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
		enterRule(_localctx, 90, RULE_do_while_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(826);
			match(T__31);
			setState(827);
			expr();
			setState(828);
			match(T__30);
			setState(829);
			match(T__13);
			setState(830);
			expr();
			setState(831);
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
		enterRule(_localctx, 92, RULE_switch_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(833);
			match(SWITCH);
			setState(834);
			match(T__13);
			setState(835);
			expr();
			setState(836);
			match(T__14);
			setState(837);
			match(T__1);
			setState(841);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE || _la==DEFAULT) {
				{
				{
				setState(838);
				switch_body();
				}
				}
				setState(843);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(844);
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
		enterRule(_localctx, 94, RULE_switch_body);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(849);
			switch (_input.LA(1)) {
			case CASE:
				{
				setState(846);
				match(CASE);
				setState(847);
				constant();
				}
				break;
			case DEFAULT:
				{
				setState(848);
				match(DEFAULT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(851);
			match(T__29);
			setState(852);
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
		enterRule(_localctx, 96, RULE_null_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(854);
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
		enterRule(_localctx, 98, RULE_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(856);
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
		int _startState = 100;
		enterRecursionRule(_localctx, 100, RULE_argument_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(861);
			switch ( getInterpreter().adaptivePredict(_input,68,_ctx) ) {
			case 1:
				{
				setState(859);
				expr();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(868);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,69,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Argument_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_argument_list);
					setState(863);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(864);
					match(T__7);
					setState(865);
					expr();
					}
					} 
				}
				setState(870);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,69,_ctx);
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
		case 4:
			return implements_list_sempred((Implements_listContext)_localctx, predIndex);
		case 8:
			return context_body_sempred((Context_bodyContext)_localctx, predIndex);
		case 12:
			return role_body_sempred((Role_bodyContext)_localctx, predIndex);
		case 13:
			return self_methods_sempred((Self_methodsContext)_localctx, predIndex);
		case 15:
			return stageprop_body_sempred((Stageprop_bodyContext)_localctx, predIndex);
		case 16:
			return class_body_sempred((Class_bodyContext)_localctx, predIndex);
		case 18:
			return interface_body_sempred((Interface_bodyContext)_localctx, predIndex);
		case 22:
			return expr_and_decl_list_sempred((Expr_and_decl_listContext)_localctx, predIndex);
		case 30:
			return identifier_list_sempred((Identifier_listContext)_localctx, predIndex);
		case 31:
			return param_list_sempred((Param_listContext)_localctx, predIndex);
		case 34:
			return abelian_expr_sempred((Abelian_exprContext)_localctx, predIndex);
		case 35:
			return abelian_product_sempred((Abelian_productContext)_localctx, predIndex);
		case 37:
			return abelian_atom_sempred((Abelian_atomContext)_localctx, predIndex);
		case 39:
			return boolean_expr_sempred((Boolean_exprContext)_localctx, predIndex);
		case 50:
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
			return precpred(_ctx, 2);
		case 19:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean abelian_product_sempred(Abelian_productContext _localctx, int predIndex) {
		switch (predIndex) {
		case 20:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean abelian_atom_sempred(Abelian_atomContext _localctx, int predIndex) {
		switch (predIndex) {
		case 21:
			return precpred(_ctx, 16);
		case 22:
			return precpred(_ctx, 15);
		case 23:
			return precpred(_ctx, 11);
		case 24:
			return precpred(_ctx, 7);
		case 25:
			return precpred(_ctx, 6);
		case 26:
			return precpred(_ctx, 3);
		case 27:
			return precpred(_ctx, 2);
		case 28:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean boolean_expr_sempred(Boolean_exprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 29:
			return precpred(_ctx, 4);
		case 30:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean argument_list_sempred(Argument_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 31:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3B\u036a\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\3\2\3\2\3\2\3\2\5\2m\n\2\3\3\3\3\3\4\3\4\3\4\5\4t\n\4\3\4\3\4\7\4"+
		"x\n\4\f\4\16\4{\13\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u0087"+
		"\n\5\f\5\16\5\u008a\13\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u0096"+
		"\n\5\f\5\16\5\u0099\13\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u00a2\n\5\f\5"+
		"\16\5\u00a5\13\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u00b0\n\5\f\5"+
		"\16\5\u00b3\13\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u00bc\n\5\f\5\16\5\u00bf"+
		"\13\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u00cd\n\5\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u00d6\n\6\f\6\16\6\u00d9\13\6\3\7\3\7\3"+
		"\7\3\7\7\7\u00df\n\7\f\7\16\7\u00e2\13\7\3\7\3\7\3\b\3\b\3\b\3\b\7\b\u00ea"+
		"\n\b\f\b\16\b\u00ed\13\b\3\b\3\b\3\t\3\t\3\t\5\t\u00f4\n\t\3\n\3\n\3\n"+
		"\5\n\u00f9\n\n\3\n\3\n\7\n\u00fd\n\n\f\n\16\n\u0100\13\n\3\13\3\13\3\13"+
		"\3\13\5\13\u0106\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u0154\n\f\3\r\3\r\3"+
		"\r\5\r\u0159\n\r\3\16\3\16\3\16\3\16\5\16\u015f\n\16\3\16\3\16\3\16\3"+
		"\16\7\16\u0165\n\16\f\16\16\16\u0168\13\16\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\7\17\u0172\n\17\f\17\16\17\u0175\13\17\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u019d\n\20\3\21\3\21\3\21\5\21"+
		"\u01a2\n\21\3\21\3\21\3\21\3\21\7\21\u01a8\n\21\f\21\16\21\u01ab\13\21"+
		"\3\22\3\22\3\22\5\22\u01b0\n\22\3\22\3\22\7\22\u01b4\n\22\f\22\16\22\u01b7"+
		"\13\22\3\23\3\23\5\23\u01bb\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\7\24\u01c5\n\24\f\24\16\24\u01c8\13\24\3\25\3\25\3\25\3\25\3\25\3"+
		"\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u01d8\n\27\f\27\16\27"+
		"\u01db\13\27\3\27\3\27\3\27\3\27\7\27\u01e1\n\27\f\27\16\27\u01e4\13\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u01ec\n\27\f\27\16\27\u01ef\13\27"+
		"\5\27\u01f1\n\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u01fa\n\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\7\30\u0202\n\30\f\30\16\30\u0205\13\30\3"+
		"\31\3\31\5\31\u0209\n\31\3\32\3\32\3\33\3\33\3\33\5\33\u0210\n\33\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\5\34\u0222\n\34\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\5\36\u022c"+
		"\n\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\5\37\u0235\n\37\3 \3 \3 \3 \3"+
		" \5 \u023c\n \3 \3 \3 \3 \3 \3 \3 \3 \7 \u0246\n \f \16 \u0249\13 \3!"+
		"\3!\3!\5!\u024e\n!\3!\3!\3!\7!\u0253\n!\f!\16!\u0256\13!\3\"\3\"\3\"\3"+
		"#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\5#\u0268\n#\3$\3$\3$\3$\7$\u026e"+
		"\n$\f$\16$\u0271\13$\3$\3$\3$\3$\3$\3$\7$\u0279\n$\f$\16$\u027c\13$\3"+
		"%\3%\3%\3%\7%\u0282\n%\f%\16%\u0285\13%\3%\3%\3%\7%\u028a\n%\f%\16%\u028d"+
		"\13%\3&\3&\3&\3&\3&\5&\u0294\n&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'"+
		"\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\5\'\u02bb\n\'\3\'\3\'\3\'\3\'"+
		"\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\7\'\u02dc\n\'\f\'\16\'\u02df\13"+
		"\'\3(\3(\3(\3(\3(\3)\3)\3)\5)\u02e9\n)\3)\3)\3)\3)\3)\3)\7)\u02f1\n)\f"+
		")\16)\u02f4\13)\3*\3*\3*\3*\3*\3*\5*\u02fc\n*\3+\3+\5+\u0300\n+\3,\3,"+
		"\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\5,\u0310\n,\3-\3-\3-\3-\3-\3-\3-"+
		"\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-"+
		"\3-\3-\3-\3-\3-\5-\u0335\n-\3.\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3/\3/\3\60"+
		"\3\60\3\60\3\60\3\60\3\60\7\60\u034a\n\60\f\60\16\60\u034d\13\60\3\60"+
		"\3\60\3\61\3\61\3\61\5\61\u0354\n\61\3\61\3\61\3\61\3\62\3\62\3\63\3\63"+
		"\3\64\3\64\3\64\5\64\u0360\n\64\3\64\3\64\3\64\7\64\u0365\n\64\f\64\16"+
		"\64\u0368\13\64\3\64\2\22\6\n\22\32\34 \"&.>@FHLPf\65\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdf\2"+
		"\4\4\2\30\33:;\3\2#&\u03b2\2l\3\2\2\2\4n\3\2\2\2\6s\3\2\2\2\b\u00cc\3"+
		"\2\2\2\n\u00ce\3\2\2\2\f\u00da\3\2\2\2\16\u00e5\3\2\2\2\20\u00f0\3\2\2"+
		"\2\22\u00f8\3\2\2\2\24\u0105\3\2\2\2\26\u0153\3\2\2\2\30\u0158\3\2\2\2"+
		"\32\u015e\3\2\2\2\34\u0169\3\2\2\2\36\u019c\3\2\2\2 \u01a1\3\2\2\2\"\u01af"+
		"\3\2\2\2$\u01ba\3\2\2\2&\u01bc\3\2\2\2(\u01c9\3\2\2\2*\u01ce\3\2\2\2,"+
		"\u01f0\3\2\2\2.\u01f9\3\2\2\2\60\u0208\3\2\2\2\62\u020a\3\2\2\2\64\u020f"+
		"\3\2\2\2\66\u0221\3\2\2\28\u0223\3\2\2\2:\u022b\3\2\2\2<\u0234\3\2\2\2"+
		">\u023b\3\2\2\2@\u024d\3\2\2\2B\u0257\3\2\2\2D\u0267\3\2\2\2F\u0269\3"+
		"\2\2\2H\u027d\3\2\2\2J\u0293\3\2\2\2L\u02ba\3\2\2\2N\u02e0\3\2\2\2P\u02e8"+
		"\3\2\2\2R\u02fb\3\2\2\2T\u02ff\3\2\2\2V\u030f\3\2\2\2X\u0334\3\2\2\2Z"+
		"\u0336\3\2\2\2\\\u033c\3\2\2\2^\u0343\3\2\2\2`\u0353\3\2\2\2b\u0358\3"+
		"\2\2\2d\u035a\3\2\2\2f\u035f\3\2\2\2hi\5\6\4\2ij\5\4\3\2jm\3\2\2\2km\5"+
		"\6\4\2lh\3\2\2\2lk\3\2\2\2m\3\3\2\2\2no\5D#\2o\5\3\2\2\2pq\b\4\1\2qt\5"+
		"\b\5\2rt\3\2\2\2sp\3\2\2\2sr\3\2\2\2ty\3\2\2\2uv\f\4\2\2vx\5\b\5\2wu\3"+
		"\2\2\2x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2z\7\3\2\2\2{y\3\2\2\2|}\7\3\2\2}~"+
		"\7>\2\2~\177\7\4\2\2\177\u0080\5\22\n\2\u0080\u0081\7\5\2\2\u0081\u00cd"+
		"\3\2\2\2\u0082\u0083\7\6\2\2\u0083\u0084\7>\2\2\u0084\u0088\5\f\7\2\u0085"+
		"\u0087\5\n\6\2\u0086\u0085\3\2\2\2\u0087\u008a\3\2\2\2\u0088\u0086\3\2"+
		"\2\2\u0088\u0089\3\2\2\2\u0089\u008b\3\2\2\2\u008a\u0088\3\2\2\2\u008b"+
		"\u008c\7\4\2\2\u008c\u008d\5\"\22\2\u008d\u008e\7\5\2\2\u008e\u00cd\3"+
		"\2\2\2\u008f\u0090\7\6\2\2\u0090\u0091\7>\2\2\u0091\u0092\5\f\7\2\u0092"+
		"\u0093\7\7\2\2\u0093\u0097\7>\2\2\u0094\u0096\5\n\6\2\u0095\u0094\3\2"+
		"\2\2\u0096\u0099\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098"+
		"\u009a\3\2\2\2\u0099\u0097\3\2\2\2\u009a\u009b\7\4\2\2\u009b\u009c\5\""+
		"\22\2\u009c\u009d\7\5\2\2\u009d\u00cd\3\2\2\2\u009e\u009f\7\6\2\2\u009f"+
		"\u00a3\7>\2\2\u00a0\u00a2\5\n\6\2\u00a1\u00a0\3\2\2\2\u00a2\u00a5\3\2"+
		"\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a6\3\2\2\2\u00a5"+
		"\u00a3\3\2\2\2\u00a6\u00a7\7\4\2\2\u00a7\u00a8\5\"\22\2\u00a8\u00a9\7"+
		"\5\2\2\u00a9\u00cd\3\2\2\2\u00aa\u00ab\7\6\2\2\u00ab\u00ac\7>\2\2\u00ac"+
		"\u00ad\7\7\2\2\u00ad\u00b1\7>\2\2\u00ae\u00b0\5\n\6\2\u00af\u00ae\3\2"+
		"\2\2\u00b0\u00b3\3\2\2\2\u00b1\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2"+
		"\u00b4\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b4\u00b5\7\4\2\2\u00b5\u00b6\5\""+
		"\22\2\u00b6\u00b7\7\5\2\2\u00b7\u00cd\3\2\2\2\u00b8\u00b9\7\6\2\2\u00b9"+
		"\u00bd\7>\2\2\u00ba\u00bc\5\n\6\2\u00bb\u00ba\3\2\2\2\u00bc\u00bf\3\2"+
		"\2\2\u00bd\u00bb\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00c0\3\2\2\2\u00bf"+
		"\u00bd\3\2\2\2\u00c0\u00c1\7\7\2\2\u00c1\u00c2\7>\2\2\u00c2\u00c3\7\4"+
		"\2\2\u00c3\u00c4\5\"\22\2\u00c4\u00c5\7\5\2\2\u00c5\u00cd\3\2\2\2\u00c6"+
		"\u00c7\7\b\2\2\u00c7\u00c8\7>\2\2\u00c8\u00c9\7\4\2\2\u00c9\u00ca\5&\24"+
		"\2\u00ca\u00cb\7\5\2\2\u00cb\u00cd\3\2\2\2\u00cc|\3\2\2\2\u00cc\u0082"+
		"\3\2\2\2\u00cc\u008f\3\2\2\2\u00cc\u009e\3\2\2\2\u00cc\u00aa\3\2\2\2\u00cc"+
		"\u00b8\3\2\2\2\u00cc\u00c6\3\2\2\2\u00cd\t\3\2\2\2\u00ce\u00cf\b\6\1\2"+
		"\u00cf\u00d0\7\t\2\2\u00d0\u00d1\7>\2\2\u00d1\u00d7\3\2\2\2\u00d2\u00d3"+
		"\f\3\2\2\u00d3\u00d4\7\n\2\2\u00d4\u00d6\7>\2\2\u00d5\u00d2\3\2\2\2\u00d6"+
		"\u00d9\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\13\3\2\2"+
		"\2\u00d9\u00d7\3\2\2\2\u00da\u00db\7:\2\2\u00db\u00e0\5\20\t\2\u00dc\u00dd"+
		"\7\n\2\2\u00dd\u00df\5\20\t\2\u00de\u00dc\3\2\2\2\u00df\u00e2\3\2\2\2"+
		"\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e3\3\2\2\2\u00e2\u00e0"+
		"\3\2\2\2\u00e3\u00e4\7;\2\2\u00e4\r\3\2\2\2\u00e5\u00e6\7:\2\2\u00e6\u00eb"+
		"\5<\37\2\u00e7\u00e8\7\n\2\2\u00e8\u00ea\5<\37\2\u00e9\u00e7\3\2\2\2\u00ea"+
		"\u00ed\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ee\3\2"+
		"\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00ef\7;\2\2\u00ef\17\3\2\2\2\u00f0\u00f3"+
		"\5<\37\2\u00f1\u00f2\7\7\2\2\u00f2\u00f4\5<\37\2\u00f3\u00f1\3\2\2\2\u00f3"+
		"\u00f4\3\2\2\2\u00f4\21\3\2\2\2\u00f5\u00f6\b\n\1\2\u00f6\u00f9\5\24\13"+
		"\2\u00f7\u00f9\3\2\2\2\u00f8\u00f5\3\2\2\2\u00f8\u00f7\3\2\2\2\u00f9\u00fe"+
		"\3\2\2\2\u00fa\u00fb\f\5\2\2\u00fb\u00fd\5\24\13\2\u00fc\u00fa\3\2\2\2"+
		"\u00fd\u0100\3\2\2\2\u00fe\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\23"+
		"\3\2\2\2\u0100\u00fe\3\2\2\2\u0101\u0106\5(\25\2\u0102\u0106\5\66\34\2"+
		"\u0103\u0106\5\26\f\2\u0104\u0106\5\36\20\2\u0105\u0101\3\2\2\2\u0105"+
		"\u0102\3\2\2\2\u0105\u0103\3\2\2\2\u0105\u0104\3\2\2\2\u0106\25\3\2\2"+
		"\2\u0107\u0108\7\13\2\2\u0108\u0109\5\30\r\2\u0109\u010a\7>\2\2\u010a"+
		"\u010b\7\4\2\2\u010b\u010c\5\32\16\2\u010c\u010d\7\5\2\2\u010d\u0154\3"+
		"\2\2\2\u010e\u010f\7\13\2\2\u010f\u0110\5\30\r\2\u0110\u0111\7>\2\2\u0111"+
		"\u0112\7\4\2\2\u0112\u0113\5\32\16\2\u0113\u0114\7\5\2\2\u0114\u0115\7"+
		"-\2\2\u0115\u0116\7\4\2\2\u0116\u0117\5\34\17\2\u0117\u0118\7\5\2\2\u0118"+
		"\u0154\3\2\2\2\u0119\u011a\5\64\33\2\u011a\u011b\7\13\2\2\u011b\u011c"+
		"\5\30\r\2\u011c\u011d\7>\2\2\u011d\u011e\7\4\2\2\u011e\u011f\5\32\16\2"+
		"\u011f\u0120\7\5\2\2\u0120\u0154\3\2\2\2\u0121\u0122\5\64\33\2\u0122\u0123"+
		"\7\13\2\2\u0123\u0124\5\30\r\2\u0124\u0125\7>\2\2\u0125\u0126\7\4\2\2"+
		"\u0126\u0127\5\32\16\2\u0127\u0128\7\5\2\2\u0128\u0129\7-\2\2\u0129\u012a"+
		"\7\4\2\2\u012a\u012b\5\34\17\2\u012b\u012c\7\5\2\2\u012c\u0154\3\2\2\2"+
		"\u012d\u012e\7\13\2\2\u012e\u012f\5\30\r\2\u012f\u0130\7>\2\2\u0130\u0131"+
		"\7\4\2\2\u0131\u0132\5\32\16\2\u0132\u0133\7\5\2\2\u0133\u0154\3\2\2\2"+
		"\u0134\u0135\7\13\2\2\u0135\u0136\5\30\r\2\u0136\u0137\7>\2\2\u0137\u0138"+
		"\7\4\2\2\u0138\u0139\5\32\16\2\u0139\u013a\7\5\2\2\u013a\u013b\7-\2\2"+
		"\u013b\u013c\7\4\2\2\u013c\u013d\5\34\17\2\u013d\u013e\7\5\2\2\u013e\u0154"+
		"\3\2\2\2\u013f\u0140\5\64\33\2\u0140\u0141\7\13\2\2\u0141\u0142\5\30\r"+
		"\2\u0142\u0143\7>\2\2\u0143\u0144\7\4\2\2\u0144\u0145\5\32\16\2\u0145"+
		"\u0146\7\5\2\2\u0146\u0154\3\2\2\2\u0147\u0148\5\64\33\2\u0148\u0149\7"+
		"\13\2\2\u0149\u014a\5\30\r\2\u014a\u014b\7>\2\2\u014b\u014c\7\4\2\2\u014c"+
		"\u014d\5\32\16\2\u014d\u014e\7\5\2\2\u014e\u014f\7-\2\2\u014f\u0150\7"+
		"\4\2\2\u0150\u0151\5\34\17\2\u0151\u0152\7\5\2\2\u0152\u0154\3\2\2\2\u0153"+
		"\u0107\3\2\2\2\u0153\u010e\3\2\2\2\u0153\u0119\3\2\2\2\u0153\u0121\3\2"+
		"\2\2\u0153\u012d\3\2\2\2\u0153\u0134\3\2\2\2\u0153\u013f\3\2\2\2\u0153"+
		"\u0147\3\2\2\2\u0154\27\3\2\2\2\u0155\u0156\7\f\2\2\u0156\u0159\7\r\2"+
		"\2\u0157\u0159\3\2\2\2\u0158\u0155\3\2\2\2\u0158\u0157\3\2\2\2\u0159\31"+
		"\3\2\2\2\u015a\u015b\b\16\1\2\u015b\u015f\5(\25\2\u015c\u015f\5\66\34"+
		"\2\u015d\u015f\3\2\2\2\u015e\u015a\3\2\2\2\u015e\u015c\3\2\2\2\u015e\u015d"+
		"\3\2\2\2\u015f\u0166\3\2\2\2\u0160\u0161\f\6\2\2\u0161\u0165\5(\25\2\u0162"+
		"\u0163\f\4\2\2\u0163\u0165\5\66\34\2\u0164\u0160\3\2\2\2\u0164\u0162\3"+
		"\2\2\2\u0165\u0168\3\2\2\2\u0166\u0164\3\2\2\2\u0166\u0167\3\2\2\2\u0167"+
		"\33\3\2\2\2\u0168\u0166\3\2\2\2\u0169\u016a\b\17\1\2\u016a\u016b\5,\27"+
		"\2\u016b\u0173\3\2\2\2\u016c\u016d\f\5\2\2\u016d\u016e\7\16\2\2\u016e"+
		"\u0172\5,\27\2\u016f\u0170\f\3\2\2\u0170\u0172\7\16\2\2\u0171\u016c\3"+
		"\2\2\2\u0171\u016f\3\2\2\2\u0172\u0175\3\2\2\2\u0173\u0171\3\2\2\2\u0173"+
		"\u0174\3\2\2\2\u0174\35\3\2\2\2\u0175\u0173\3\2\2\2\u0176\u0177\7\17\2"+
		"\2\u0177\u0178\5\30\r\2\u0178\u0179\7>\2\2\u0179\u017a\7\4\2\2\u017a\u017b"+
		"\5 \21\2\u017b\u017c\7\5\2\2\u017c\u019d\3\2\2\2\u017d\u017e\7\17\2\2"+
		"\u017e\u017f\5\30\r\2\u017f\u0180\7>\2\2\u0180\u0181\7\4\2\2\u0181\u0182"+
		"\5 \21\2\u0182\u0183\7\5\2\2\u0183\u0184\7-\2\2\u0184\u0185\7\4\2\2\u0185"+
		"\u0186\5\34\17\2\u0186\u0187\7\5\2\2\u0187\u019d\3\2\2\2\u0188\u0189\5"+
		"\64\33\2\u0189\u018a\7\17\2\2\u018a\u018b\5\30\r\2\u018b\u018c\7>\2\2"+
		"\u018c\u018d\7\4\2\2\u018d\u018e\5\32\16\2\u018e\u018f\7\5\2\2\u018f\u019d"+
		"\3\2\2\2\u0190\u0191\5\64\33\2\u0191\u0192\7\17\2\2\u0192\u0193\5\30\r"+
		"\2\u0193\u0194\7>\2\2\u0194\u0195\7\4\2\2\u0195\u0196\5\32\16\2\u0196"+
		"\u0197\7\5\2\2\u0197\u0198\7-\2\2\u0198\u0199\7\4\2\2\u0199\u019a\5\34"+
		"\17\2\u019a\u019b\7\5\2\2\u019b\u019d\3\2\2\2\u019c\u0176\3\2\2\2\u019c"+
		"\u017d\3\2\2\2\u019c\u0188\3\2\2\2\u019c\u0190\3\2\2\2\u019d\37\3\2\2"+
		"\2\u019e\u019f\b\21\1\2\u019f\u01a2\5(\25\2\u01a0\u01a2\5\66\34\2\u01a1"+
		"\u019e\3\2\2\2\u01a1\u01a0\3\2\2\2\u01a2\u01a9\3\2\2\2\u01a3\u01a4\f\5"+
		"\2\2\u01a4\u01a8\5(\25\2\u01a5\u01a6\f\3\2\2\u01a6\u01a8\5\66\34\2\u01a7"+
		"\u01a3\3\2\2\2\u01a7\u01a5\3\2\2\2\u01a8\u01ab\3\2\2\2\u01a9\u01a7\3\2"+
		"\2\2\u01a9\u01aa\3\2\2\2\u01aa!\3\2\2\2\u01ab\u01a9\3\2\2\2\u01ac\u01ad"+
		"\b\22\1\2\u01ad\u01b0\5$\23\2\u01ae\u01b0\3\2\2\2\u01af\u01ac\3\2\2\2"+
		"\u01af\u01ae\3\2\2\2\u01b0\u01b5\3\2\2\2\u01b1\u01b2\f\5\2\2\u01b2\u01b4"+
		"\5$\23\2\u01b3\u01b1\3\2\2\2\u01b4\u01b7\3\2\2\2\u01b5\u01b3\3\2\2\2\u01b5"+
		"\u01b6\3\2\2\2\u01b6#\3\2\2\2\u01b7\u01b5\3\2\2\2\u01b8\u01bb\5(\25\2"+
		"\u01b9\u01bb\5\66\34\2\u01ba\u01b8\3\2\2\2\u01ba\u01b9\3\2\2\2\u01bb%"+
		"\3\2\2\2\u01bc\u01bd\b\24\1\2\u01bd\u01be\5,\27\2\u01be\u01c6\3\2\2\2"+
		"\u01bf\u01c0\f\5\2\2\u01c0\u01c1\7\16\2\2\u01c1\u01c5\5,\27\2\u01c2\u01c3"+
		"\f\3\2\2\u01c3\u01c5\7\16\2\2\u01c4\u01bf\3\2\2\2\u01c4\u01c2\3\2\2\2"+
		"\u01c5\u01c8\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7\'\3"+
		"\2\2\2\u01c8\u01c6\3\2\2\2\u01c9\u01ca\5*\26\2\u01ca\u01cb\7\4\2\2\u01cb"+
		"\u01cc\5.\30\2\u01cc\u01cd\7\5\2\2\u01cd)\3\2\2\2\u01ce\u01cf\5,\27\2"+
		"\u01cf+\3\2\2\2\u01d0\u01d1\5\64\33\2\u01d1\u01d2\5\60\31\2\u01d2\u01d3"+
		"\5\62\32\2\u01d3\u01d4\7\20\2\2\u01d4\u01d5\5@!\2\u01d5\u01d9\7\21\2\2"+
		"\u01d6\u01d8\7\61\2\2\u01d7\u01d6\3\2\2\2\u01d8\u01db\3\2\2\2\u01d9\u01d7"+
		"\3\2\2\2\u01d9\u01da\3\2\2\2\u01da\u01f1\3\2\2\2\u01db\u01d9\3\2\2\2\u01dc"+
		"\u01dd\5\64\33\2\u01dd\u01de\5\60\31\2\u01de\u01e2\5\62\32\2\u01df\u01e1"+
		"\7\61\2\2\u01e0\u01df\3\2\2\2\u01e1\u01e4\3\2\2\2\u01e2\u01e0\3\2\2\2"+
		"\u01e2\u01e3\3\2\2\2\u01e3\u01f1\3\2\2\2\u01e4\u01e2\3\2\2\2\u01e5\u01e6"+
		"\5\64\33\2\u01e6\u01e7\5\62\32\2\u01e7\u01e8\7\20\2\2\u01e8\u01e9\5@!"+
		"\2\u01e9\u01ed\7\21\2\2\u01ea\u01ec\7\61\2\2\u01eb\u01ea\3\2\2\2\u01ec"+
		"\u01ef\3\2\2\2\u01ed\u01eb\3\2\2\2\u01ed\u01ee\3\2\2\2\u01ee\u01f1\3\2"+
		"\2\2\u01ef\u01ed\3\2\2\2\u01f0\u01d0\3\2\2\2\u01f0\u01dc\3\2\2\2\u01f0"+
		"\u01e5\3\2\2\2\u01f1-\3\2\2\2\u01f2\u01f3\b\30\1\2\u01f3\u01fa\5\66\34"+
		"\2\u01f4\u01f5\5D#\2\u01f5\u01f6\7\16\2\2\u01f6\u01f7\5\66\34\2\u01f7"+
		"\u01fa\3\2\2\2\u01f8\u01fa\3\2\2\2\u01f9\u01f2\3\2\2\2\u01f9\u01f4\3\2"+
		"\2\2\u01f9\u01f8\3\2\2\2\u01fa\u0203\3\2\2\2\u01fb\u01fc\f\6\2\2\u01fc"+
		"\u0202\5\66\34\2\u01fd\u01fe\f\5\2\2\u01fe\u0202\5D#\2\u01ff\u0200\f\4"+
		"\2\2\u0200\u0202\7\16\2\2\u0201\u01fb\3\2\2\2\u0201\u01fd\3\2\2\2\u0201"+
		"\u01ff\3\2\2\2\u0202\u0205\3\2\2\2\u0203\u0201\3\2\2\2\u0203\u0204\3\2"+
		"\2\2\u0204/\3\2\2\2\u0205\u0203\3\2\2\2\u0206\u0209\5<\37\2\u0207\u0209"+
		"\3\2\2\2\u0208\u0206\3\2\2\2\u0208\u0207\3\2\2\2\u0209\61\3\2\2\2\u020a"+
		"\u020b\7>\2\2\u020b\63\3\2\2\2\u020c\u0210\7\22\2\2\u020d\u0210\7\23\2"+
		"\2\u020e\u0210\3\2\2\2\u020f\u020c\3\2\2\2\u020f\u020d\3\2\2\2\u020f\u020e"+
		"\3\2\2\2\u0210\65\3\2\2\2\u0211\u0212\5\64\33\2\u0212\u0213\5:\36\2\u0213"+
		"\u0214\5> \2\u0214\u0215\7\16\2\2\u0215\u0222\3\2\2\2\u0216\u0217\5\64"+
		"\33\2\u0217\u0218\5:\36\2\u0218\u0219\5> \2\u0219\u0222\3\2\2\2\u021a"+
		"\u021b\5:\36\2\u021b\u021c\5> \2\u021c\u021d\7\16\2\2\u021d\u0222\3\2"+
		"\2\2\u021e\u021f\5:\36\2\u021f\u0220\5> \2\u0220\u0222\3\2\2\2\u0221\u0211"+
		"\3\2\2\2\u0221\u0216\3\2\2\2\u0221\u021a\3\2\2\2\u0221\u021e\3\2\2\2\u0222"+
		"\67\3\2\2\2\u0223\u0224\5:\36\2\u0224\u0225\7>\2\2\u02259\3\2\2\2\u0226"+
		"\u0227\5<\37\2\u0227\u0228\7\f\2\2\u0228\u0229\7\r\2\2\u0229\u022c\3\2"+
		"\2\2\u022a\u022c\5<\37\2\u022b\u0226\3\2\2\2\u022b\u022a\3\2\2\2\u022c"+
		";\3\2\2\2\u022d\u0235\7>\2\2\u022e\u022f\7>\2\2\u022f\u0235\5\16\b\2\u0230"+
		"\u0235\7\24\2\2\u0231\u0235\7\25\2\2\u0232\u0235\7\26\2\2\u0233\u0235"+
		"\7\27\2\2\u0234\u022d\3\2\2\2\u0234\u022e\3\2\2\2\u0234\u0230\3\2\2\2"+
		"\u0234\u0231\3\2\2\2\u0234\u0232\3\2\2\2\u0234\u0233\3\2\2\2\u0235=\3"+
		"\2\2\2\u0236\u0237\b \1\2\u0237\u023c\7>\2\2\u0238\u0239\7>\2\2\u0239"+
		"\u023a\7B\2\2\u023a\u023c\5D#\2\u023b\u0236\3\2\2\2\u023b\u0238\3\2\2"+
		"\2\u023c\u0247\3\2\2\2\u023d\u023e\f\5\2\2\u023e\u023f\7\n\2\2\u023f\u0246"+
		"\7>\2\2\u0240\u0241\f\3\2\2\u0241\u0242\7\n\2\2\u0242\u0243\7>\2\2\u0243"+
		"\u0244\7B\2\2\u0244\u0246\5D#\2\u0245\u023d\3\2\2\2\u0245\u0240\3\2\2"+
		"\2\u0246\u0249\3\2\2\2\u0247\u0245\3\2\2\2\u0247\u0248\3\2\2\2\u0248?"+
		"\3\2\2\2\u0249\u0247\3\2\2\2\u024a\u024b\b!\1\2\u024b\u024e\5B\"\2\u024c"+
		"\u024e\3\2\2\2\u024d\u024a\3\2\2\2\u024d\u024c\3\2\2\2\u024e\u0254\3\2"+
		"\2\2\u024f\u0250\f\4\2\2\u0250\u0251\7\n\2\2\u0251\u0253\5B\"\2\u0252"+
		"\u024f\3\2\2\2\u0253\u0256\3\2\2\2\u0254\u0252\3\2\2\2\u0254\u0255\3\2"+
		"\2\2\u0255A\3\2\2\2\u0256\u0254\3\2\2\2\u0257\u0258\5:\36\2\u0258\u0259"+
		"\7>\2\2\u0259C\3\2\2\2\u025a\u0268\5F$\2\u025b\u0268\5P)\2\u025c\u0268"+
		"\5R*\2\u025d\u0268\5V,\2\u025e\u0268\5X-\2\u025f\u0268\5Z.\2\u0260\u0268"+
		"\5\\/\2\u0261\u0268\5^\60\2\u0262\u0268\7*\2\2\u0263\u0268\7+\2\2\u0264"+
		"\u0265\7,\2\2\u0265\u0268\5D#\2\u0266\u0268\7,\2\2\u0267\u025a\3\2\2\2"+
		"\u0267\u025b\3\2\2\2\u0267\u025c\3\2\2\2\u0267\u025d\3\2\2\2\u0267\u025e"+
		"\3\2\2\2\u0267\u025f\3\2\2\2\u0267\u0260\3\2\2\2\u0267\u0261\3\2\2\2\u0267"+
		"\u0262\3\2\2\2\u0267\u0263\3\2\2\2\u0267\u0264\3\2\2\2\u0267\u0266\3\2"+
		"\2\2\u0268E\3\2\2\2\u0269\u026a\b$\1\2\u026a\u026f\5H%\2\u026b\u026c\7"+
		"\66\2\2\u026c\u026e\5H%\2\u026d\u026b\3\2\2\2\u026e\u0271\3\2\2\2\u026f"+
		"\u026d\3\2\2\2\u026f\u0270\3\2\2\2\u0270\u027a\3\2\2\2\u0271\u026f\3\2"+
		"\2\2\u0272\u0273\f\4\2\2\u0273\u0274\t\2\2\2\u0274\u0279\5F$\5\u0275\u0276"+
		"\f\3\2\2\u0276\u0277\7B\2\2\u0277\u0279\5D#\2\u0278\u0272\3\2\2\2\u0278"+
		"\u0275\3\2\2\2\u0279\u027c\3\2\2\2\u027a\u0278\3\2\2\2\u027a\u027b\3\2"+
		"\2\2\u027bG\3\2\2\2\u027c\u027a\3\2\2\2\u027d\u027e\b%\1\2\u027e\u0283"+
		"\5J&\2\u027f\u0280\7\67\2\2\u0280\u0282\5J&\2\u0281\u027f\3\2\2\2\u0282"+
		"\u0285\3\2\2\2\u0283\u0281\3\2\2\2\u0283\u0284\3\2\2\2\u0284\u028b\3\2"+
		"\2\2\u0285\u0283\3\2\2\2\u0286\u0287\f\4\2\2\u0287\u0288\7\63\2\2\u0288"+
		"\u028a\5L\'\2\u0289\u0286\3\2\2\2\u028a\u028d\3\2\2\2\u028b\u0289\3\2"+
		"\2\2\u028b\u028c\3\2\2\2\u028cI\3\2\2\2\u028d\u028b\3\2\2\2\u028e\u028f"+
		"\7\66\2\2\u028f\u0294\5L\'\2\u0290\u0291\7<\2\2\u0291\u0294\5L\'\2\u0292"+
		"\u0294\5L\'\2\u0293\u028e\3\2\2\2\u0293\u0290\3\2\2\2\u0293\u0292\3\2"+
		"\2\2\u0294K\3\2\2\2\u0295\u0296\b\'\1\2\u0296\u0297\7=\2\2\u0297\u02bb"+
		"\5L\'\f\u0298\u0299\7.\2\2\u0299\u02bb\5N(\2\u029a\u029b\7.\2\2\u029b"+
		"\u029c\5<\37\2\u029c\u029d\7\f\2\2\u029d\u029e\5D#\2\u029e\u029f\7\r\2"+
		"\2\u029f\u02bb\3\2\2\2\u02a0\u02a1\7.\2\2\u02a1\u02a2\7>\2\2\u02a2\u02a3"+
		"\5\16\b\2\u02a3\u02a4\7\20\2\2\u02a4\u02a5\5f\64\2\u02a5\u02a6\7\21\2"+
		"\2\u02a6\u02bb\3\2\2\2\u02a7\u02bb\5b\62\2\u02a8\u02bb\5N(\2\u02a9\u02bb"+
		"\7>\2\2\u02aa\u02bb\5d\63\2\u02ab\u02ac\7\20\2\2\u02ac\u02ad\5F$\2\u02ad"+
		"\u02ae\7\21\2\2\u02ae\u02bb\3\2\2\2\u02af\u02b0\7=\2\2\u02b0\u02b1\5D"+
		"#\2\u02b1\u02b2\7\f\2\2\u02b2\u02b3\5D#\2\u02b3\u02b4\7\r\2\2\u02b4\u02bb"+
		"\3\2\2\2\u02b5\u02b6\7=\2\2\u02b6\u02b7\5D#\2\u02b7\u02b8\7\34\2\2\u02b8"+
		"\u02b9\7>\2\2\u02b9\u02bb\3\2\2\2\u02ba\u0295\3\2\2\2\u02ba\u0298\3\2"+
		"\2\2\u02ba\u029a\3\2\2\2\u02ba\u02a0\3\2\2\2\u02ba\u02a7\3\2\2\2\u02ba"+
		"\u02a8\3\2\2\2\u02ba\u02a9\3\2\2\2\u02ba\u02aa\3\2\2\2\u02ba\u02ab\3\2"+
		"\2\2\u02ba\u02af\3\2\2\2\u02ba\u02b5\3\2\2\2\u02bb\u02dd\3\2\2\2\u02bc"+
		"\u02bd\f\22\2\2\u02bd\u02be\7\34\2\2\u02be\u02dc\5N(\2\u02bf\u02c0\f\21"+
		"\2\2\u02c0\u02c1\7\34\2\2\u02c1\u02dc\7>\2\2\u02c2\u02c3\f\r\2\2\u02c3"+
		"\u02dc\7=\2\2\u02c4\u02c5\f\t\2\2\u02c5\u02c6\7\f\2\2\u02c6\u02c7\5D#"+
		"\2\u02c7\u02c8\7\r\2\2\u02c8\u02dc\3\2\2\2\u02c9\u02ca\f\b\2\2\u02ca\u02cb"+
		"\7\f\2\2\u02cb\u02cc\5D#\2\u02cc\u02cd\7\r\2\2\u02cd\u02ce\7=\2\2\u02ce"+
		"\u02dc\3\2\2\2\u02cf\u02d0\f\5\2\2\u02d0\u02d1\7\34\2\2\u02d1\u02d2\7"+
		">\2\2\u02d2\u02dc\7=\2\2\u02d3\u02d4\f\4\2\2\u02d4\u02d5\7\34\2\2\u02d5"+
		"\u02dc\7/\2\2\u02d6\u02d7\f\3\2\2\u02d7\u02d8\7\34\2\2\u02d8\u02d9\7/"+
		"\2\2\u02d9\u02da\7\20\2\2\u02da\u02dc\7\21\2\2\u02db\u02bc\3\2\2\2\u02db"+
		"\u02bf\3\2\2\2\u02db\u02c2\3\2\2\2\u02db\u02c4\3\2\2\2\u02db\u02c9\3\2"+
		"\2\2\u02db\u02cf\3\2\2\2\u02db\u02d3\3\2\2\2\u02db\u02d6\3\2\2\2\u02dc"+
		"\u02df\3\2\2\2\u02dd\u02db\3\2\2\2\u02dd\u02de\3\2\2\2\u02deM\3\2\2\2"+
		"\u02df\u02dd\3\2\2\2\u02e0\u02e1\7>\2\2\u02e1\u02e2\7\20\2\2\u02e2\u02e3"+
		"\5f\64\2\u02e3\u02e4\7\21\2\2\u02e4O\3\2\2\2\u02e5\u02e6\b)\1\2\u02e6"+
		"\u02e9\5d\63\2\u02e7\u02e9\5F$\2\u02e8\u02e5\3\2\2\2\u02e8\u02e7\3\2\2"+
		"\2\u02e9\u02f2\3\2\2\2\u02ea\u02eb\f\6\2\2\u02eb\u02ec\7\65\2\2\u02ec"+
		"\u02f1\5D#\2\u02ed\u02ee\f\5\2\2\u02ee\u02ef\7\64\2\2\u02ef\u02f1\5D#"+
		"\2\u02f0\u02ea\3\2\2\2\u02f0\u02ed\3\2\2\2\u02f1\u02f4\3\2\2\2\u02f2\u02f0"+
		"\3\2\2\2\u02f2\u02f3\3\2\2\2\u02f3Q\3\2\2\2\u02f4\u02f2\3\2\2\2\u02f5"+
		"\u02f6\7\4\2\2\u02f6\u02f7\5.\30\2\u02f7\u02f8\7\5\2\2\u02f8\u02fc\3\2"+
		"\2\2\u02f9\u02fa\7\4\2\2\u02fa\u02fc\7\5\2\2\u02fb\u02f5\3\2\2\2\u02fb"+
		"\u02f9\3\2\2\2\u02fcS\3\2\2\2\u02fd\u0300\5D#\2\u02fe\u0300\3\2\2\2\u02ff"+
		"\u02fd\3\2\2\2\u02ff\u02fe\3\2\2\2\u0300U\3\2\2\2\u0301\u0302\7\35\2\2"+
		"\u0302\u0303\7\20\2\2\u0303\u0304\5D#\2\u0304\u0305\7\21\2\2\u0305\u0306"+
		"\5D#\2\u0306\u0310\3\2\2\2\u0307\u0308\7\35\2\2\u0308\u0309\7\20\2\2\u0309"+
		"\u030a\5D#\2\u030a\u030b\7\21\2\2\u030b\u030c\5D#\2\u030c\u030d\7\36\2"+
		"\2\u030d\u030e\5D#\2\u030e\u0310\3\2\2\2\u030f\u0301\3\2\2\2\u030f\u0307"+
		"\3\2\2\2\u0310W\3\2\2\2\u0311\u0312\7\37\2\2\u0312\u0313\7\20\2\2\u0313"+
		"\u0314\5D#\2\u0314\u0315\7\16\2\2\u0315\u0316\5D#\2\u0316\u0317\7\16\2"+
		"\2\u0317\u0318\5D#\2\u0318\u0319\7\21\2\2\u0319\u031a\5D#\2\u031a\u0335"+
		"\3\2\2\2\u031b\u031c\7\37\2\2\u031c\u031d\7\20\2\2\u031d\u031e\5\66\34"+
		"\2\u031e\u031f\5D#\2\u031f\u0320\7\16\2\2\u0320\u0321\5D#\2\u0321\u0322"+
		"\7\21\2\2\u0322\u0323\5D#\2\u0323\u0335\3\2\2\2\u0324\u0325\7\37\2\2\u0325"+
		"\u0326\7\20\2\2\u0326\u0327\7>\2\2\u0327\u0328\7 \2\2\u0328\u0329\5D#"+
		"\2\u0329\u032a\7\21\2\2\u032a\u032b\5D#\2\u032b\u0335\3\2\2\2\u032c\u032d"+
		"\7\37\2\2\u032d\u032e\7\20\2\2\u032e\u032f\58\35\2\u032f\u0330\7 \2\2"+
		"\u0330\u0331\5D#\2\u0331\u0332\7\21\2\2\u0332\u0333\5D#\2\u0333\u0335"+
		"\3\2\2\2\u0334\u0311\3\2\2\2\u0334\u031b\3\2\2\2\u0334\u0324\3\2\2\2\u0334"+
		"\u032c\3\2\2\2\u0335Y\3\2\2\2\u0336\u0337\7!\2\2\u0337\u0338\7\20\2\2"+
		"\u0338\u0339\5D#\2\u0339\u033a\7\21\2\2\u033a\u033b\5D#\2\u033b[\3\2\2"+
		"\2\u033c\u033d\7\"\2\2\u033d\u033e\5D#\2\u033e\u033f\7!\2\2\u033f\u0340"+
		"\7\20\2\2\u0340\u0341\5D#\2\u0341\u0342\7\21\2\2\u0342]\3\2\2\2\u0343"+
		"\u0344\7\'\2\2\u0344\u0345\7\20\2\2\u0345\u0346\5D#\2\u0346\u0347\7\21"+
		"\2\2\u0347\u034b\7\4\2\2\u0348\u034a\5`\61\2\u0349\u0348\3\2\2\2\u034a"+
		"\u034d\3\2\2\2\u034b\u0349\3\2\2\2\u034b\u034c\3\2\2\2\u034c\u034e\3\2"+
		"\2\2\u034d\u034b\3\2\2\2\u034e\u034f\7\5\2\2\u034f_\3\2\2\2\u0350\u0351"+
		"\7(\2\2\u0351\u0354\5d\63\2\u0352\u0354\7)\2\2\u0353\u0350\3\2\2\2\u0353"+
		"\u0352\3\2\2\2\u0354\u0355\3\2\2\2\u0355\u0356\7 \2\2\u0356\u0357\5.\30"+
		"\2\u0357a\3\2\2\2\u0358\u0359\7\60\2\2\u0359c\3\2\2\2\u035a\u035b\t\3"+
		"\2\2\u035be\3\2\2\2\u035c\u035d\b\64\1\2\u035d\u0360\5D#\2\u035e\u0360"+
		"\3\2\2\2\u035f\u035c\3\2\2\2\u035f\u035e\3\2\2\2\u0360\u0366\3\2\2\2\u0361"+
		"\u0362\f\4\2\2\u0362\u0363\7\n\2\2\u0363\u0365\5D#\2\u0364\u0361\3\2\2"+
		"\2\u0365\u0368\3\2\2\2\u0366\u0364\3\2\2\2\u0366\u0367\3\2\2\2\u0367g"+
		"\3\2\2\2\u0368\u0366\3\2\2\2Hlsy\u0088\u0097\u00a3\u00b1\u00bd\u00cc\u00d7"+
		"\u00e0\u00eb\u00f3\u00f8\u00fe\u0105\u0153\u0158\u015e\u0164\u0166\u0171"+
		"\u0173\u019c\u01a1\u01a7\u01a9\u01af\u01b5\u01ba\u01c4\u01c6\u01d9\u01e2"+
		"\u01ed\u01f0\u01f9\u0201\u0203\u0208\u020f\u0221\u022b\u0234\u023b\u0245"+
		"\u0247\u024d\u0254\u0267\u026f\u0278\u027a\u0283\u028b\u0293\u02ba\u02db"+
		"\u02dd\u02e8\u02f0\u02f2\u02fb\u02ff\u030f\u0334\u034b\u0353\u035f\u0366";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}