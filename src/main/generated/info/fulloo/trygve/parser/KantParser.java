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
		RULE_boolean_expr = 38, RULE_boolean_product = 39, RULE_boolean_unary_op = 40, 
		RULE_boolean_atom = 41, RULE_message = 42, RULE_block = 43, RULE_expr_or_null = 44, 
		RULE_if_expr = 45, RULE_for_expr = 46, RULE_while_expr = 47, RULE_do_while_expr = 48, 
		RULE_switch_expr = 49, RULE_switch_body = 50, RULE_null_expr = 51, RULE_constant = 52, 
		RULE_argument_list = 53;
	public static final String[] ruleNames = {
		"program", "main", "type_declaration_list", "type_declaration", "implements_list", 
		"type_parameters", "type_list", "type_parameter", "context_body", "context_body_element", 
		"role_decl", "role_vec_modifier", "role_body", "self_methods", "stageprop_decl", 
		"stageprop_body", "class_body", "class_body_element", "interface_body", 
		"method_decl", "method_decl_hook", "method_signature", "expr_and_decl_list", 
		"return_type", "method_name", "access_qualifier", "object_decl", "trivial_object_decl", 
		"compound_type_name", "type_name", "identifier_list", "param_list", "param_decl", 
		"expr", "abelian_expr", "abelian_product", "abelian_unary_op", "abelian_atom", 
		"boolean_expr", "boolean_product", "boolean_unary_op", "boolean_atom", 
		"message", "block", "expr_or_null", "if_expr", "for_expr", "while_expr", 
		"do_while_expr", "switch_expr", "switch_body", "null_expr", "constant", 
		"argument_list"
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
			setState(112);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(108);
				type_declaration_list(0);
				setState(109);
				main();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(111);
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
			setState(114);
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
			setState(119);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(117);
				type_declaration();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(125);
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
					setState(121);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(122);
					type_declaration();
					}
					} 
				}
				setState(127);
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
			setState(208);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(128);
				match(T__0);
				setState(129);
				match(JAVA_ID);
				setState(130);
				match(T__1);
				setState(131);
				context_body(0);
				setState(132);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(134);
				match(T__3);
				setState(135);
				match(JAVA_ID);
				setState(136);
				type_parameters();
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(137);
					implements_list(0);
					}
					}
					setState(142);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(143);
				match(T__1);
				setState(144);
				class_body(0);
				setState(145);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(147);
				match(T__3);
				setState(148);
				match(JAVA_ID);
				setState(149);
				type_parameters();
				setState(150);
				match(T__4);
				setState(151);
				match(JAVA_ID);
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(152);
					implements_list(0);
					}
					}
					setState(157);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(158);
				match(T__1);
				setState(159);
				class_body(0);
				setState(160);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(162);
				match(T__3);
				setState(163);
				match(JAVA_ID);
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(164);
					implements_list(0);
					}
					}
					setState(169);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(170);
				match(T__1);
				setState(171);
				class_body(0);
				setState(172);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(174);
				match(T__3);
				setState(175);
				match(JAVA_ID);
				setState(176);
				match(T__4);
				setState(177);
				match(JAVA_ID);
				setState(181);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(178);
					implements_list(0);
					}
					}
					setState(183);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(184);
				match(T__1);
				setState(185);
				class_body(0);
				setState(186);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(188);
				match(T__3);
				setState(189);
				match(JAVA_ID);
				setState(193);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(190);
					implements_list(0);
					}
					}
					setState(195);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(196);
				match(T__4);
				setState(197);
				match(JAVA_ID);
				setState(198);
				match(T__1);
				setState(199);
				class_body(0);
				setState(200);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(202);
				match(T__5);
				setState(203);
				match(JAVA_ID);
				setState(204);
				match(T__1);
				setState(205);
				interface_body(0);
				setState(206);
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
			setState(211);
			match(T__6);
			setState(212);
			match(JAVA_ID);
			}
			_ctx.stop = _input.LT(-1);
			setState(219);
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
					setState(214);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(215);
					match(T__7);
					setState(216);
					match(JAVA_ID);
					}
					} 
				}
				setState(221);
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
			setState(222);
			match(LT);
			setState(223);
			type_parameter();
			setState(228);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(224);
				match(T__7);
				setState(225);
				type_parameter();
				}
				}
				setState(230);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(231);
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
			setState(233);
			match(LT);
			setState(234);
			type_name();
			setState(239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(235);
				match(T__7);
				setState(236);
				type_name();
				}
				}
				setState(241);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(242);
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
			setState(244);
			type_name();
			setState(247);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(245);
				match(T__4);
				setState(246);
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
			setState(252);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(250);
				context_body_element();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(258);
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
					setState(254);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(255);
					context_body_element();
					}
					} 
				}
				setState(260);
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
			setState(265);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(261);
				method_decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(262);
				object_decl();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(263);
				role_decl();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(264);
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
			setState(343);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(267);
				match(T__8);
				setState(268);
				role_vec_modifier();
				setState(269);
				match(JAVA_ID);
				setState(270);
				match(T__1);
				setState(271);
				role_body(0);
				setState(272);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(274);
				match(T__8);
				setState(275);
				role_vec_modifier();
				setState(276);
				match(JAVA_ID);
				setState(277);
				match(T__1);
				setState(278);
				role_body(0);
				setState(279);
				match(T__2);
				setState(280);
				match(REQUIRES);
				setState(281);
				match(T__1);
				setState(282);
				self_methods(0);
				setState(283);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(285);
				access_qualifier();
				setState(286);
				match(T__8);
				setState(287);
				role_vec_modifier();
				setState(288);
				match(JAVA_ID);
				setState(289);
				match(T__1);
				setState(290);
				role_body(0);
				setState(291);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(293);
				access_qualifier();
				setState(294);
				match(T__8);
				setState(295);
				role_vec_modifier();
				setState(296);
				match(JAVA_ID);
				setState(297);
				match(T__1);
				setState(298);
				role_body(0);
				setState(299);
				match(T__2);
				setState(300);
				match(REQUIRES);
				setState(301);
				match(T__1);
				setState(302);
				self_methods(0);
				setState(303);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(305);
				match(T__8);
				setState(306);
				role_vec_modifier();
				setState(307);
				match(JAVA_ID);
				setState(308);
				match(T__1);
				setState(309);
				role_body(0);
				setState(310);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(312);
				match(T__8);
				setState(313);
				role_vec_modifier();
				setState(314);
				match(JAVA_ID);
				setState(315);
				match(T__1);
				setState(316);
				role_body(0);
				setState(317);
				match(T__2);
				setState(318);
				match(REQUIRES);
				setState(319);
				match(T__1);
				setState(320);
				self_methods(0);
				setState(321);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(323);
				access_qualifier();
				setState(324);
				match(T__8);
				setState(325);
				role_vec_modifier();
				setState(326);
				match(JAVA_ID);
				setState(327);
				match(T__1);
				setState(328);
				role_body(0);
				setState(329);
				match(T__2);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(331);
				access_qualifier();
				setState(332);
				match(T__8);
				setState(333);
				role_vec_modifier();
				setState(334);
				match(JAVA_ID);
				setState(335);
				match(T__1);
				setState(336);
				role_body(0);
				setState(337);
				match(T__2);
				setState(338);
				match(REQUIRES);
				setState(339);
				match(T__1);
				setState(340);
				self_methods(0);
				setState(341);
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
			setState(348);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(345);
				match(T__9);
				setState(346);
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
			setState(354);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(351);
				method_decl();
				}
				break;
			case 2:
				{
				setState(352);
				object_decl();
				}
				break;
			case 3:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(362);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(360);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(356);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(357);
						method_decl();
						}
						break;
					case 2:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(358);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(359);
						object_decl();
						}
						break;
					}
					} 
				}
				setState(364);
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
			setState(366);
			method_signature();
			}
			_ctx.stop = _input.LT(-1);
			setState(375);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(373);
					switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
					case 1:
						{
						_localctx = new Self_methodsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_self_methods);
						setState(368);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(369);
						match(T__11);
						setState(370);
						method_signature();
						}
						break;
					case 2:
						{
						_localctx = new Self_methodsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_self_methods);
						setState(371);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(372);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(377);
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
			setState(450);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(378);
				match(T__12);
				setState(379);
				role_vec_modifier();
				setState(380);
				match(JAVA_ID);
				setState(381);
				match(T__1);
				setState(382);
				stageprop_body(0);
				setState(383);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(385);
				match(T__12);
				setState(386);
				role_vec_modifier();
				setState(387);
				match(JAVA_ID);
				setState(388);
				match(T__1);
				setState(389);
				stageprop_body(0);
				setState(390);
				match(T__2);
				setState(391);
				match(REQUIRES);
				setState(392);
				match(T__1);
				setState(393);
				self_methods(0);
				setState(394);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(396);
				access_qualifier();
				setState(397);
				match(T__12);
				setState(398);
				role_vec_modifier();
				setState(399);
				match(JAVA_ID);
				setState(400);
				match(T__1);
				setState(401);
				stageprop_body(0);
				setState(402);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(404);
				access_qualifier();
				setState(405);
				match(T__12);
				setState(406);
				role_vec_modifier();
				setState(407);
				match(JAVA_ID);
				setState(408);
				match(T__1);
				setState(409);
				stageprop_body(0);
				setState(410);
				match(T__2);
				setState(411);
				match(REQUIRES);
				setState(412);
				match(T__1);
				setState(413);
				self_methods(0);
				setState(414);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(416);
				match(T__12);
				setState(417);
				role_vec_modifier();
				setState(418);
				match(JAVA_ID);
				setState(419);
				match(T__1);
				setState(420);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(422);
				match(T__12);
				setState(423);
				role_vec_modifier();
				setState(424);
				match(JAVA_ID);
				setState(425);
				match(T__1);
				setState(426);
				match(T__2);
				setState(427);
				match(REQUIRES);
				setState(428);
				match(T__1);
				setState(429);
				self_methods(0);
				setState(430);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(432);
				access_qualifier();
				setState(433);
				match(T__12);
				setState(434);
				role_vec_modifier();
				setState(435);
				match(JAVA_ID);
				setState(436);
				match(T__1);
				setState(437);
				match(T__2);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(439);
				access_qualifier();
				setState(440);
				match(T__12);
				setState(441);
				role_vec_modifier();
				setState(442);
				match(JAVA_ID);
				setState(443);
				match(T__1);
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
			setState(455);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(453);
				method_decl();
				}
				break;
			case 2:
				{
				setState(454);
				object_decl();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(463);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(461);
					switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
					case 1:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(457);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(458);
						method_decl();
						}
						break;
					case 2:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(459);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(460);
						object_decl();
						}
						break;
					}
					} 
				}
				setState(465);
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
			setState(469);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(467);
				class_body_element();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(475);
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
					setState(471);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(472);
					class_body_element();
					}
					} 
				}
				setState(477);
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
			setState(480);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(478);
				method_decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(479);
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
			setState(483);
			method_signature();
			}
			_ctx.stop = _input.LT(-1);
			setState(492);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(490);
					switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
					case 1:
						{
						_localctx = new Interface_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_interface_body);
						setState(485);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(486);
						match(T__11);
						setState(487);
						method_signature();
						}
						break;
					case 2:
						{
						_localctx = new Interface_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_interface_body);
						setState(488);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(489);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(494);
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
			setState(495);
			method_decl_hook();
			setState(496);
			match(T__1);
			setState(497);
			expr_and_decl_list(0);
			setState(498);
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
			setState(500);
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
			setState(534);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(502);
				access_qualifier();
				setState(503);
				return_type();
				setState(504);
				method_name();
				setState(505);
				match(T__13);
				setState(506);
				param_list(0);
				setState(507);
				match(T__14);
				setState(511);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(508);
						match(CONST);
						}
						} 
					}
					setState(513);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(514);
				access_qualifier();
				setState(515);
				return_type();
				setState(516);
				method_name();
				setState(520);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(517);
						match(CONST);
						}
						} 
					}
					setState(522);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(523);
				access_qualifier();
				setState(524);
				method_name();
				setState(525);
				match(T__13);
				setState(526);
				param_list(0);
				setState(527);
				match(T__14);
				setState(531);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(528);
						match(CONST);
						}
						} 
					}
					setState(533);
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
			setState(543);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				setState(537);
				object_decl();
				}
				break;
			case 2:
				{
				setState(538);
				expr();
				setState(539);
				match(T__11);
				setState(540);
				object_decl();
				}
				break;
			case 3:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(553);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(551);
					switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
					case 1:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(545);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(546);
						object_decl();
						}
						break;
					case 2:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(547);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(548);
						expr();
						}
						break;
					case 3:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(549);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(550);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(555);
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
			setState(558);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(556);
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
		enterRule(_localctx, 48, RULE_method_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(560);
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
		enterRule(_localctx, 50, RULE_access_qualifier);
		try {
			setState(565);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(562);
				match(T__15);
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 2);
				{
				setState(563);
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
		enterRule(_localctx, 52, RULE_object_decl);
		try {
			setState(583);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(567);
				access_qualifier();
				setState(568);
				compound_type_name();
				setState(569);
				identifier_list(0);
				setState(570);
				match(T__11);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(572);
				access_qualifier();
				setState(573);
				compound_type_name();
				setState(574);
				identifier_list(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(576);
				compound_type_name();
				setState(577);
				identifier_list(0);
				setState(578);
				match(T__11);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(580);
				compound_type_name();
				setState(581);
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
			setState(585);
			compound_type_name();
			setState(586);
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
			setState(593);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(588);
				type_name();
				setState(589);
				match(T__9);
				setState(590);
				match(T__10);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(592);
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
			setState(602);
			switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(595);
				match(JAVA_ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(596);
				match(JAVA_ID);
				setState(597);
				type_list();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(598);
				match(T__17);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(599);
				match(T__18);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(600);
				match(T__19);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(601);
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
			setState(609);
			switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
			case 1:
				{
				setState(605);
				match(JAVA_ID);
				}
				break;
			case 2:
				{
				setState(606);
				match(JAVA_ID);
				setState(607);
				match(ASSIGN);
				setState(608);
				expr();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(621);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(619);
					switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
					case 1:
						{
						_localctx = new Identifier_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identifier_list);
						setState(611);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(612);
						match(T__7);
						setState(613);
						match(JAVA_ID);
						}
						break;
					case 2:
						{
						_localctx = new Identifier_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identifier_list);
						setState(614);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(615);
						match(T__7);
						setState(616);
						match(JAVA_ID);
						setState(617);
						match(ASSIGN);
						setState(618);
						expr();
						}
						break;
					}
					} 
				}
				setState(623);
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
			setState(627);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				{
				setState(625);
				param_decl();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(634);
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
					setState(629);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(630);
					match(T__7);
					setState(631);
					param_decl();
					}
					} 
				}
				setState(636);
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
			setState(637);
			compound_type_name();
			setState(638);
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
			setState(653);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(640);
				abelian_expr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(641);
				boolean_expr(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(642);
				block();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(643);
				if_expr();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(644);
				for_expr();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(645);
				while_expr();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(646);
				do_while_expr();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(647);
				switch_expr();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(648);
				match(BREAK);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(649);
				match(CONTINUE);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(650);
				match(RETURN);
				setState(651);
				expr();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(652);
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
		int _startState = 68;
		enterRecursionRule(_localctx, 68, RULE_abelian_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(665);
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
				setState(656);
				abelian_product(0);
				setState(661);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(657);
						match(ABELIAN_SUMOP);
						setState(658);
						abelian_product(0);
						}
						} 
					}
					setState(663);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
				}
				}
				break;
			case T__28:
				{
				setState(664);
				if_expr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(672);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Abelian_exprContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_abelian_expr);
					setState(667);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(668);
					match(ASSIGN);
					setState(669);
					expr();
					}
					} 
				}
				setState(674);
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
			setState(676);
			abelian_unary_op();
			setState(681);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(677);
					match(ABELIAN_MULOP);
					setState(678);
					abelian_unary_op();
					}
					} 
				}
				setState(683);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			}
			}
			_ctx.stop = _input.LT(-1);
			setState(689);
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
					setState(684);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(685);
					match(POW);
					setState(686);
					abelian_atom(0);
					}
					} 
				}
				setState(691);
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
			setState(695);
			switch ( getInterpreter().adaptivePredict(_input,55,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(692);
				match(ABELIAN_SUMOP);
				setState(693);
				abelian_atom(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(694);
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
			setState(734);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				{
				setState(698);
				match(ABELIAN_INCREMENT_OP);
				setState(699);
				abelian_atom(10);
				}
				break;
			case 2:
				{
				setState(700);
				match(NEW);
				setState(701);
				message();
				}
				break;
			case 3:
				{
				setState(702);
				match(NEW);
				setState(703);
				type_name();
				setState(704);
				match(T__9);
				setState(705);
				expr();
				setState(706);
				match(T__10);
				}
				break;
			case 4:
				{
				setState(708);
				match(NEW);
				setState(709);
				match(JAVA_ID);
				setState(710);
				type_list();
				setState(711);
				match(T__13);
				setState(712);
				argument_list(0);
				setState(713);
				match(T__14);
				}
				break;
			case 5:
				{
				setState(715);
				null_expr();
				}
				break;
			case 6:
				{
				setState(716);
				message();
				}
				break;
			case 7:
				{
				setState(717);
				match(JAVA_ID);
				}
				break;
			case 8:
				{
				setState(718);
				constant();
				}
				break;
			case 9:
				{
				setState(719);
				match(T__13);
				setState(720);
				abelian_expr(0);
				setState(721);
				match(T__14);
				}
				break;
			case 10:
				{
				setState(723);
				match(ABELIAN_INCREMENT_OP);
				setState(724);
				expr();
				setState(725);
				match(T__9);
				setState(726);
				expr();
				setState(727);
				match(T__10);
				}
				break;
			case 11:
				{
				setState(729);
				match(ABELIAN_INCREMENT_OP);
				setState(730);
				expr();
				setState(731);
				match(T__21);
				setState(732);
				match(JAVA_ID);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(769);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(767);
					switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
					case 1:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(736);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(737);
						match(T__21);
						setState(738);
						message();
						}
						break;
					case 2:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(739);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(740);
						match(T__21);
						setState(741);
						match(JAVA_ID);
						}
						break;
					case 3:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(742);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(743);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 4:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(744);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(745);
						match(T__9);
						setState(746);
						expr();
						setState(747);
						match(T__10);
						}
						break;
					case 5:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(749);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(750);
						match(T__9);
						setState(751);
						expr();
						setState(752);
						match(T__10);
						setState(753);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 6:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(755);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(756);
						match(T__21);
						setState(757);
						match(JAVA_ID);
						setState(758);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 7:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(759);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(760);
						match(T__21);
						setState(761);
						match(CLONE);
						}
						break;
					case 8:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(762);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(763);
						match(T__21);
						setState(764);
						match(CLONE);
						setState(765);
						match(T__13);
						setState(766);
						match(T__14);
						}
						break;
					}
					} 
				}
				setState(771);
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
		int _startState = 76;
		enterRecursionRule(_localctx, 76, RULE_boolean_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(787);
			switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
			case 1:
				{
				setState(773);
				boolean_product();
				setState(778);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,59,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(774);
						match(BOOLEAN_SUMOP);
						setState(775);
						boolean_product();
						}
						} 
					}
					setState(780);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,59,_ctx);
				}
				}
				break;
			case 2:
				{
				setState(781);
				if_expr();
				}
				break;
			case 3:
				{
				setState(782);
				abelian_expr(0);
				setState(783);
				((Boolean_exprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << LT) | (1L << GT))) != 0)) ) {
					((Boolean_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(784);
				abelian_expr(0);
				}
				break;
			case 4:
				{
				setState(786);
				abelian_expr(0);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(797);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,62,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(795);
					switch ( getInterpreter().adaptivePredict(_input,61,_ctx) ) {
					case 1:
						{
						_localctx = new Boolean_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolean_expr);
						setState(789);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(790);
						((Boolean_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << BOOLEAN_MULOP))) != 0)) ) {
							((Boolean_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(791);
						boolean_expr(5);
						}
						break;
					case 2:
						{
						_localctx = new Boolean_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolean_expr);
						setState(792);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(793);
						match(ASSIGN);
						setState(794);
						expr();
						}
						break;
					}
					} 
				}
				setState(799);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,62,_ctx);
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
		enterRule(_localctx, 78, RULE_boolean_product);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(800);
			boolean_unary_op();
			setState(805);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(801);
					match(BOOLEAN_MULOP);
					setState(802);
					boolean_unary_op();
					}
					} 
				}
				setState(807);
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
		enterRule(_localctx, 80, RULE_boolean_unary_op);
		try {
			setState(811);
			switch (_input.LA(1)) {
			case LOGICAL_NEGATION:
				enterOuterAlt(_localctx, 1);
				{
				setState(808);
				match(LOGICAL_NEGATION);
				setState(809);
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
				setState(810);
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
		enterRule(_localctx, 82, RULE_boolean_atom);
		try {
			setState(820);
			switch (_input.LA(1)) {
			case STRING:
			case INTEGER:
			case FLOAT:
			case BOOLEAN:
				enterOuterAlt(_localctx, 1);
				{
				setState(813);
				constant();
				}
				break;
			case JAVA_ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(814);
				match(JAVA_ID);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 3);
				{
				setState(815);
				null_expr();
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 4);
				{
				setState(816);
				match(T__13);
				setState(817);
				boolean_expr(0);
				setState(818);
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
		enterRule(_localctx, 84, RULE_message);
		try {
			setState(832);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(822);
				method_name();
				setState(823);
				match(T__13);
				setState(824);
				argument_list(0);
				setState(825);
				match(T__14);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(827);
				type_name();
				setState(828);
				match(T__13);
				setState(829);
				argument_list(0);
				setState(830);
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
		enterRule(_localctx, 86, RULE_block);
		try {
			setState(840);
			switch ( getInterpreter().adaptivePredict(_input,67,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(834);
				match(T__1);
				setState(835);
				expr_and_decl_list(0);
				setState(836);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(838);
				match(T__1);
				setState(839);
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
		enterRule(_localctx, 88, RULE_expr_or_null);
		try {
			setState(844);
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
				setState(842);
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
		enterRule(_localctx, 90, RULE_if_expr);
		try {
			setState(860);
			switch ( getInterpreter().adaptivePredict(_input,69,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(846);
				match(T__28);
				setState(847);
				match(T__13);
				setState(848);
				expr();
				setState(849);
				match(T__14);
				setState(850);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(852);
				match(T__28);
				setState(853);
				match(T__13);
				setState(854);
				expr();
				setState(855);
				match(T__14);
				setState(856);
				expr();
				setState(857);
				match(T__29);
				setState(858);
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
		enterRule(_localctx, 92, RULE_for_expr);
		try {
			setState(897);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(862);
				match(T__30);
				setState(863);
				match(T__13);
				setState(864);
				expr();
				setState(865);
				match(T__11);
				setState(866);
				expr();
				setState(867);
				match(T__11);
				setState(868);
				expr();
				setState(869);
				match(T__14);
				setState(870);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(872);
				match(T__30);
				setState(873);
				match(T__13);
				setState(874);
				object_decl();
				setState(875);
				expr();
				setState(876);
				match(T__11);
				setState(877);
				expr();
				setState(878);
				match(T__14);
				setState(879);
				expr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(881);
				match(T__30);
				setState(882);
				match(T__13);
				setState(883);
				match(JAVA_ID);
				setState(884);
				match(T__31);
				setState(885);
				expr();
				setState(886);
				match(T__14);
				setState(887);
				expr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(889);
				match(T__30);
				setState(890);
				match(T__13);
				setState(891);
				trivial_object_decl();
				setState(892);
				match(T__31);
				setState(893);
				expr();
				setState(894);
				match(T__14);
				setState(895);
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
		enterRule(_localctx, 94, RULE_while_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(899);
			match(T__32);
			setState(900);
			match(T__13);
			setState(901);
			expr();
			setState(902);
			match(T__14);
			setState(903);
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
		enterRule(_localctx, 96, RULE_do_while_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(905);
			match(T__33);
			setState(906);
			expr();
			setState(907);
			match(T__32);
			setState(908);
			match(T__13);
			setState(909);
			expr();
			setState(910);
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
		enterRule(_localctx, 98, RULE_switch_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(912);
			match(SWITCH);
			setState(913);
			match(T__13);
			setState(914);
			expr();
			setState(915);
			match(T__14);
			setState(916);
			match(T__1);
			setState(920);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE || _la==DEFAULT) {
				{
				{
				setState(917);
				switch_body();
				}
				}
				setState(922);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(923);
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
		enterRule(_localctx, 100, RULE_switch_body);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(928);
			switch (_input.LA(1)) {
			case CASE:
				{
				setState(925);
				match(CASE);
				setState(926);
				constant();
				}
				break;
			case DEFAULT:
				{
				setState(927);
				match(DEFAULT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(930);
			match(T__31);
			setState(931);
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
		enterRule(_localctx, 102, RULE_null_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(933);
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
		enterRule(_localctx, 104, RULE_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(935);
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
		int _startState = 106;
		enterRecursionRule(_localctx, 106, RULE_argument_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(940);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				{
				setState(938);
				expr();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(947);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,74,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Argument_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_argument_list);
					setState(942);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(943);
					match(T__7);
					setState(944);
					expr();
					}
					} 
				}
				setState(949);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,74,_ctx);
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
		case 38:
			return boolean_expr_sempred((Boolean_exprContext)_localctx, predIndex);
		case 53:
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
			return precpred(_ctx, 16);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3C\u03b9\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\3\2\3\2\3\2\3\2\5\2s\n\2\3\3\3\3\3\4"+
		"\3\4\3\4\5\4z\n\4\3\4\3\4\7\4~\n\4\f\4\16\4\u0081\13\4\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u008d\n\5\f\5\16\5\u0090\13\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u009c\n\5\f\5\16\5\u009f\13\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\7\5\u00a8\n\5\f\5\16\5\u00ab\13\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\7\5\u00b6\n\5\f\5\16\5\u00b9\13\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\7\5\u00c2\n\5\f\5\16\5\u00c5\13\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u00d3\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6"+
		"\u00dc\n\6\f\6\16\6\u00df\13\6\3\7\3\7\3\7\3\7\7\7\u00e5\n\7\f\7\16\7"+
		"\u00e8\13\7\3\7\3\7\3\b\3\b\3\b\3\b\7\b\u00f0\n\b\f\b\16\b\u00f3\13\b"+
		"\3\b\3\b\3\t\3\t\3\t\5\t\u00fa\n\t\3\n\3\n\3\n\5\n\u00ff\n\n\3\n\3\n\7"+
		"\n\u0103\n\n\f\n\16\n\u0106\13\n\3\13\3\13\3\13\3\13\5\13\u010c\n\13\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\5\f\u015a\n\f\3\r\3\r\3\r\5\r\u015f\n\r\3\16\3"+
		"\16\3\16\3\16\5\16\u0165\n\16\3\16\3\16\3\16\3\16\7\16\u016b\n\16\f\16"+
		"\16\16\u016e\13\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u0178"+
		"\n\17\f\17\16\17\u017b\13\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u01c5\n\20\3\21\3\21\3\21"+
		"\5\21\u01ca\n\21\3\21\3\21\3\21\3\21\7\21\u01d0\n\21\f\21\16\21\u01d3"+
		"\13\21\3\22\3\22\3\22\5\22\u01d8\n\22\3\22\3\22\7\22\u01dc\n\22\f\22\16"+
		"\22\u01df\13\22\3\23\3\23\5\23\u01e3\n\23\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\7\24\u01ed\n\24\f\24\16\24\u01f0\13\24\3\25\3\25\3\25\3\25"+
		"\3\25\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u0200\n\27\f\27"+
		"\16\27\u0203\13\27\3\27\3\27\3\27\3\27\7\27\u0209\n\27\f\27\16\27\u020c"+
		"\13\27\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u0214\n\27\f\27\16\27\u0217"+
		"\13\27\5\27\u0219\n\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u0222\n"+
		"\30\3\30\3\30\3\30\3\30\3\30\3\30\7\30\u022a\n\30\f\30\16\30\u022d\13"+
		"\30\3\31\3\31\5\31\u0231\n\31\3\32\3\32\3\33\3\33\3\33\5\33\u0238\n\33"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\5\34\u024a\n\34\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\5\36"+
		"\u0254\n\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\5\37\u025d\n\37\3 \3 \3"+
		" \3 \3 \5 \u0264\n \3 \3 \3 \3 \3 \3 \3 \3 \7 \u026e\n \f \16 \u0271\13"+
		" \3!\3!\3!\5!\u0276\n!\3!\3!\3!\7!\u027b\n!\f!\16!\u027e\13!\3\"\3\"\3"+
		"\"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\5#\u0290\n#\3$\3$\3$\3$\7$\u0296"+
		"\n$\f$\16$\u0299\13$\3$\5$\u029c\n$\3$\3$\3$\7$\u02a1\n$\f$\16$\u02a4"+
		"\13$\3%\3%\3%\3%\7%\u02aa\n%\f%\16%\u02ad\13%\3%\3%\3%\7%\u02b2\n%\f%"+
		"\16%\u02b5\13%\3&\3&\3&\5&\u02ba\n&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'"+
		"\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\5\'\u02e1\n\'\3\'\3\'\3\'"+
		"\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\7\'\u0302\n\'\f\'\16\'\u0305"+
		"\13\'\3(\3(\3(\3(\7(\u030b\n(\f(\16(\u030e\13(\3(\3(\3(\3(\3(\3(\5(\u0316"+
		"\n(\3(\3(\3(\3(\3(\3(\7(\u031e\n(\f(\16(\u0321\13(\3)\3)\3)\7)\u0326\n"+
		")\f)\16)\u0329\13)\3*\3*\3*\5*\u032e\n*\3+\3+\3+\3+\3+\3+\3+\5+\u0337"+
		"\n+\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\5,\u0343\n,\3-\3-\3-\3-\3-\3-\5-\u034b"+
		"\n-\3.\3.\5.\u034f\n.\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\5/\u035f"+
		"\n/\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3"+
		"\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3"+
		"\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\5\60\u0384\n\60\3\61\3\61\3\61"+
		"\3\61\3\61\3\61\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63"+
		"\3\63\3\63\7\63\u0399\n\63\f\63\16\63\u039c\13\63\3\63\3\63\3\64\3\64"+
		"\3\64\5\64\u03a3\n\64\3\64\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67\3\67"+
		"\5\67\u03af\n\67\3\67\3\67\3\67\7\67\u03b4\n\67\f\67\16\67\u03b7\13\67"+
		"\3\67\2\22\6\n\22\32\34 \"&.>@FHLNl8\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjl\2\6\4\2\678??"+
		"\4\2\33\36;<\4\2\31\32\66\66\3\2%(\u040a\2r\3\2\2\2\4t\3\2\2\2\6y\3\2"+
		"\2\2\b\u00d2\3\2\2\2\n\u00d4\3\2\2\2\f\u00e0\3\2\2\2\16\u00eb\3\2\2\2"+
		"\20\u00f6\3\2\2\2\22\u00fe\3\2\2\2\24\u010b\3\2\2\2\26\u0159\3\2\2\2\30"+
		"\u015e\3\2\2\2\32\u0164\3\2\2\2\34\u016f\3\2\2\2\36\u01c4\3\2\2\2 \u01c9"+
		"\3\2\2\2\"\u01d7\3\2\2\2$\u01e2\3\2\2\2&\u01e4\3\2\2\2(\u01f1\3\2\2\2"+
		"*\u01f6\3\2\2\2,\u0218\3\2\2\2.\u0221\3\2\2\2\60\u0230\3\2\2\2\62\u0232"+
		"\3\2\2\2\64\u0237\3\2\2\2\66\u0249\3\2\2\28\u024b\3\2\2\2:\u0253\3\2\2"+
		"\2<\u025c\3\2\2\2>\u0263\3\2\2\2@\u0275\3\2\2\2B\u027f\3\2\2\2D\u028f"+
		"\3\2\2\2F\u029b\3\2\2\2H\u02a5\3\2\2\2J\u02b9\3\2\2\2L\u02e0\3\2\2\2N"+
		"\u0315\3\2\2\2P\u0322\3\2\2\2R\u032d\3\2\2\2T\u0336\3\2\2\2V\u0342\3\2"+
		"\2\2X\u034a\3\2\2\2Z\u034e\3\2\2\2\\\u035e\3\2\2\2^\u0383\3\2\2\2`\u0385"+
		"\3\2\2\2b\u038b\3\2\2\2d\u0392\3\2\2\2f\u03a2\3\2\2\2h\u03a7\3\2\2\2j"+
		"\u03a9\3\2\2\2l\u03ae\3\2\2\2no\5\6\4\2op\5\4\3\2ps\3\2\2\2qs\5\6\4\2"+
		"rn\3\2\2\2rq\3\2\2\2s\3\3\2\2\2tu\5D#\2u\5\3\2\2\2vw\b\4\1\2wz\5\b\5\2"+
		"xz\3\2\2\2yv\3\2\2\2yx\3\2\2\2z\177\3\2\2\2{|\f\4\2\2|~\5\b\5\2}{\3\2"+
		"\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080\7\3\2\2\2\u0081"+
		"\177\3\2\2\2\u0082\u0083\7\3\2\2\u0083\u0084\7?\2\2\u0084\u0085\7\4\2"+
		"\2\u0085\u0086\5\22\n\2\u0086\u0087\7\5\2\2\u0087\u00d3\3\2\2\2\u0088"+
		"\u0089\7\6\2\2\u0089\u008a\7?\2\2\u008a\u008e\5\f\7\2\u008b\u008d\5\n"+
		"\6\2\u008c\u008b\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008e"+
		"\u008f\3\2\2\2\u008f\u0091\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u0092\7\4"+
		"\2\2\u0092\u0093\5\"\22\2\u0093\u0094\7\5\2\2\u0094\u00d3\3\2\2\2\u0095"+
		"\u0096\7\6\2\2\u0096\u0097\7?\2\2\u0097\u0098\5\f\7\2\u0098\u0099\7\7"+
		"\2\2\u0099\u009d\7?\2\2\u009a\u009c\5\n\6\2\u009b\u009a\3\2\2\2\u009c"+
		"\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u00a0\3\2"+
		"\2\2\u009f\u009d\3\2\2\2\u00a0\u00a1\7\4\2\2\u00a1\u00a2\5\"\22\2\u00a2"+
		"\u00a3\7\5\2\2\u00a3\u00d3\3\2\2\2\u00a4\u00a5\7\6\2\2\u00a5\u00a9\7?"+
		"\2\2\u00a6\u00a8\5\n\6\2\u00a7\u00a6\3\2\2\2\u00a8\u00ab\3\2\2\2\u00a9"+
		"\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ac\3\2\2\2\u00ab\u00a9\3\2"+
		"\2\2\u00ac\u00ad\7\4\2\2\u00ad\u00ae\5\"\22\2\u00ae\u00af\7\5\2\2\u00af"+
		"\u00d3\3\2\2\2\u00b0\u00b1\7\6\2\2\u00b1\u00b2\7?\2\2\u00b2\u00b3\7\7"+
		"\2\2\u00b3\u00b7\7?\2\2\u00b4\u00b6\5\n\6\2\u00b5\u00b4\3\2\2\2\u00b6"+
		"\u00b9\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00ba\3\2"+
		"\2\2\u00b9\u00b7\3\2\2\2\u00ba\u00bb\7\4\2\2\u00bb\u00bc\5\"\22\2\u00bc"+
		"\u00bd\7\5\2\2\u00bd\u00d3\3\2\2\2\u00be\u00bf\7\6\2\2\u00bf\u00c3\7?"+
		"\2\2\u00c0\u00c2\5\n\6\2\u00c1\u00c0\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3"+
		"\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c6\3\2\2\2\u00c5\u00c3\3\2"+
		"\2\2\u00c6\u00c7\7\7\2\2\u00c7\u00c8\7?\2\2\u00c8\u00c9\7\4\2\2\u00c9"+
		"\u00ca\5\"\22\2\u00ca\u00cb\7\5\2\2\u00cb\u00d3\3\2\2\2\u00cc\u00cd\7"+
		"\b\2\2\u00cd\u00ce\7?\2\2\u00ce\u00cf\7\4\2\2\u00cf\u00d0\5&\24\2\u00d0"+
		"\u00d1\7\5\2\2\u00d1\u00d3\3\2\2\2\u00d2\u0082\3\2\2\2\u00d2\u0088\3\2"+
		"\2\2\u00d2\u0095\3\2\2\2\u00d2\u00a4\3\2\2\2\u00d2\u00b0\3\2\2\2\u00d2"+
		"\u00be\3\2\2\2\u00d2\u00cc\3\2\2\2\u00d3\t\3\2\2\2\u00d4\u00d5\b\6\1\2"+
		"\u00d5\u00d6\7\t\2\2\u00d6\u00d7\7?\2\2\u00d7\u00dd\3\2\2\2\u00d8\u00d9"+
		"\f\3\2\2\u00d9\u00da\7\n\2\2\u00da\u00dc\7?\2\2\u00db\u00d8\3\2\2\2\u00dc"+
		"\u00df\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\13\3\2\2"+
		"\2\u00df\u00dd\3\2\2\2\u00e0\u00e1\7;\2\2\u00e1\u00e6\5\20\t\2\u00e2\u00e3"+
		"\7\n\2\2\u00e3\u00e5\5\20\t\2\u00e4\u00e2\3\2\2\2\u00e5\u00e8\3\2\2\2"+
		"\u00e6\u00e4\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e9\3\2\2\2\u00e8\u00e6"+
		"\3\2\2\2\u00e9\u00ea\7<\2\2\u00ea\r\3\2\2\2\u00eb\u00ec\7;\2\2\u00ec\u00f1"+
		"\5<\37\2\u00ed\u00ee\7\n\2\2\u00ee\u00f0\5<\37\2\u00ef\u00ed\3\2\2\2\u00f0"+
		"\u00f3\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f4\3\2"+
		"\2\2\u00f3\u00f1\3\2\2\2\u00f4\u00f5\7<\2\2\u00f5\17\3\2\2\2\u00f6\u00f9"+
		"\5<\37\2\u00f7\u00f8\7\7\2\2\u00f8\u00fa\5<\37\2\u00f9\u00f7\3\2\2\2\u00f9"+
		"\u00fa\3\2\2\2\u00fa\21\3\2\2\2\u00fb\u00fc\b\n\1\2\u00fc\u00ff\5\24\13"+
		"\2\u00fd\u00ff\3\2\2\2\u00fe\u00fb\3\2\2\2\u00fe\u00fd\3\2\2\2\u00ff\u0104"+
		"\3\2\2\2\u0100\u0101\f\5\2\2\u0101\u0103\5\24\13\2\u0102\u0100\3\2\2\2"+
		"\u0103\u0106\3\2\2\2\u0104\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105\23"+
		"\3\2\2\2\u0106\u0104\3\2\2\2\u0107\u010c\5(\25\2\u0108\u010c\5\66\34\2"+
		"\u0109\u010c\5\26\f\2\u010a\u010c\5\36\20\2\u010b\u0107\3\2\2\2\u010b"+
		"\u0108\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010a\3\2\2\2\u010c\25\3\2\2"+
		"\2\u010d\u010e\7\13\2\2\u010e\u010f\5\30\r\2\u010f\u0110\7?\2\2\u0110"+
		"\u0111\7\4\2\2\u0111\u0112\5\32\16\2\u0112\u0113\7\5\2\2\u0113\u015a\3"+
		"\2\2\2\u0114\u0115\7\13\2\2\u0115\u0116\5\30\r\2\u0116\u0117\7?\2\2\u0117"+
		"\u0118\7\4\2\2\u0118\u0119\5\32\16\2\u0119\u011a\7\5\2\2\u011a\u011b\7"+
		"/\2\2\u011b\u011c\7\4\2\2\u011c\u011d\5\34\17\2\u011d\u011e\7\5\2\2\u011e"+
		"\u015a\3\2\2\2\u011f\u0120\5\64\33\2\u0120\u0121\7\13\2\2\u0121\u0122"+
		"\5\30\r\2\u0122\u0123\7?\2\2\u0123\u0124\7\4\2\2\u0124\u0125\5\32\16\2"+
		"\u0125\u0126\7\5\2\2\u0126\u015a\3\2\2\2\u0127\u0128\5\64\33\2\u0128\u0129"+
		"\7\13\2\2\u0129\u012a\5\30\r\2\u012a\u012b\7?\2\2\u012b\u012c\7\4\2\2"+
		"\u012c\u012d\5\32\16\2\u012d\u012e\7\5\2\2\u012e\u012f\7/\2\2\u012f\u0130"+
		"\7\4\2\2\u0130\u0131\5\34\17\2\u0131\u0132\7\5\2\2\u0132\u015a\3\2\2\2"+
		"\u0133\u0134\7\13\2\2\u0134\u0135\5\30\r\2\u0135\u0136\7?\2\2\u0136\u0137"+
		"\7\4\2\2\u0137\u0138\5\32\16\2\u0138\u0139\7\5\2\2\u0139\u015a\3\2\2\2"+
		"\u013a\u013b\7\13\2\2\u013b\u013c\5\30\r\2\u013c\u013d\7?\2\2\u013d\u013e"+
		"\7\4\2\2\u013e\u013f\5\32\16\2\u013f\u0140\7\5\2\2\u0140\u0141\7/\2\2"+
		"\u0141\u0142\7\4\2\2\u0142\u0143\5\34\17\2\u0143\u0144\7\5\2\2\u0144\u015a"+
		"\3\2\2\2\u0145\u0146\5\64\33\2\u0146\u0147\7\13\2\2\u0147\u0148\5\30\r"+
		"\2\u0148\u0149\7?\2\2\u0149\u014a\7\4\2\2\u014a\u014b\5\32\16\2\u014b"+
		"\u014c\7\5\2\2\u014c\u015a\3\2\2\2\u014d\u014e\5\64\33\2\u014e\u014f\7"+
		"\13\2\2\u014f\u0150\5\30\r\2\u0150\u0151\7?\2\2\u0151\u0152\7\4\2\2\u0152"+
		"\u0153\5\32\16\2\u0153\u0154\7\5\2\2\u0154\u0155\7/\2\2\u0155\u0156\7"+
		"\4\2\2\u0156\u0157\5\34\17\2\u0157\u0158\7\5\2\2\u0158\u015a\3\2\2\2\u0159"+
		"\u010d\3\2\2\2\u0159\u0114\3\2\2\2\u0159\u011f\3\2\2\2\u0159\u0127\3\2"+
		"\2\2\u0159\u0133\3\2\2\2\u0159\u013a\3\2\2\2\u0159\u0145\3\2\2\2\u0159"+
		"\u014d\3\2\2\2\u015a\27\3\2\2\2\u015b\u015c\7\f\2\2\u015c\u015f\7\r\2"+
		"\2\u015d\u015f\3\2\2\2\u015e\u015b\3\2\2\2\u015e\u015d\3\2\2\2\u015f\31"+
		"\3\2\2\2\u0160\u0161\b\16\1\2\u0161\u0165\5(\25\2\u0162\u0165\5\66\34"+
		"\2\u0163\u0165\3\2\2\2\u0164\u0160\3\2\2\2\u0164\u0162\3\2\2\2\u0164\u0163"+
		"\3\2\2\2\u0165\u016c\3\2\2\2\u0166\u0167\f\6\2\2\u0167\u016b\5(\25\2\u0168"+
		"\u0169\f\4\2\2\u0169\u016b\5\66\34\2\u016a\u0166\3\2\2\2\u016a\u0168\3"+
		"\2\2\2\u016b\u016e\3\2\2\2\u016c\u016a\3\2\2\2\u016c\u016d\3\2\2\2\u016d"+
		"\33\3\2\2\2\u016e\u016c\3\2\2\2\u016f\u0170\b\17\1\2\u0170\u0171\5,\27"+
		"\2\u0171\u0179\3\2\2\2\u0172\u0173\f\5\2\2\u0173\u0174\7\16\2\2\u0174"+
		"\u0178\5,\27\2\u0175\u0176\f\3\2\2\u0176\u0178\7\16\2\2\u0177\u0172\3"+
		"\2\2\2\u0177\u0175\3\2\2\2\u0178\u017b\3\2\2\2\u0179\u0177\3\2\2\2\u0179"+
		"\u017a\3\2\2\2\u017a\35\3\2\2\2\u017b\u0179\3\2\2\2\u017c\u017d\7\17\2"+
		"\2\u017d\u017e\5\30\r\2\u017e\u017f\7?\2\2\u017f\u0180\7\4\2\2\u0180\u0181"+
		"\5 \21\2\u0181\u0182\7\5\2\2\u0182\u01c5\3\2\2\2\u0183\u0184\7\17\2\2"+
		"\u0184\u0185\5\30\r\2\u0185\u0186\7?\2\2\u0186\u0187\7\4\2\2\u0187\u0188"+
		"\5 \21\2\u0188\u0189\7\5\2\2\u0189\u018a\7/\2\2\u018a\u018b\7\4\2\2\u018b"+
		"\u018c\5\34\17\2\u018c\u018d\7\5\2\2\u018d\u01c5\3\2\2\2\u018e\u018f\5"+
		"\64\33\2\u018f\u0190\7\17\2\2\u0190\u0191\5\30\r\2\u0191\u0192\7?\2\2"+
		"\u0192\u0193\7\4\2\2\u0193\u0194\5 \21\2\u0194\u0195\7\5\2\2\u0195\u01c5"+
		"\3\2\2\2\u0196\u0197\5\64\33\2\u0197\u0198\7\17\2\2\u0198\u0199\5\30\r"+
		"\2\u0199\u019a\7?\2\2\u019a\u019b\7\4\2\2\u019b\u019c\5 \21\2\u019c\u019d"+
		"\7\5\2\2\u019d\u019e\7/\2\2\u019e\u019f\7\4\2\2\u019f\u01a0\5\34\17\2"+
		"\u01a0\u01a1\7\5\2\2\u01a1\u01c5\3\2\2\2\u01a2\u01a3\7\17\2\2\u01a3\u01a4"+
		"\5\30\r\2\u01a4\u01a5\7?\2\2\u01a5\u01a6\7\4\2\2\u01a6\u01a7\7\5\2\2\u01a7"+
		"\u01c5\3\2\2\2\u01a8\u01a9\7\17\2\2\u01a9\u01aa\5\30\r\2\u01aa\u01ab\7"+
		"?\2\2\u01ab\u01ac\7\4\2\2\u01ac\u01ad\7\5\2\2\u01ad\u01ae\7/\2\2\u01ae"+
		"\u01af\7\4\2\2\u01af\u01b0\5\34\17\2\u01b0\u01b1\7\5\2\2\u01b1\u01c5\3"+
		"\2\2\2\u01b2\u01b3\5\64\33\2\u01b3\u01b4\7\17\2\2\u01b4\u01b5\5\30\r\2"+
		"\u01b5\u01b6\7?\2\2\u01b6\u01b7\7\4\2\2\u01b7\u01b8\7\5\2\2\u01b8\u01c5"+
		"\3\2\2\2\u01b9\u01ba\5\64\33\2\u01ba\u01bb\7\17\2\2\u01bb\u01bc\5\30\r"+
		"\2\u01bc\u01bd\7?\2\2\u01bd\u01be\7\4\2\2\u01be\u01bf\7\5\2\2\u01bf\u01c0"+
		"\7/\2\2\u01c0\u01c1\7\4\2\2\u01c1\u01c2\5\34\17\2\u01c2\u01c3\7\5\2\2"+
		"\u01c3\u01c5\3\2\2\2\u01c4\u017c\3\2\2\2\u01c4\u0183\3\2\2\2\u01c4\u018e"+
		"\3\2\2\2\u01c4\u0196\3\2\2\2\u01c4\u01a2\3\2\2\2\u01c4\u01a8\3\2\2\2\u01c4"+
		"\u01b2\3\2\2\2\u01c4\u01b9\3\2\2\2\u01c5\37\3\2\2\2\u01c6\u01c7\b\21\1"+
		"\2\u01c7\u01ca\5(\25\2\u01c8\u01ca\5\66\34\2\u01c9\u01c6\3\2\2\2\u01c9"+
		"\u01c8\3\2\2\2\u01ca\u01d1\3\2\2\2\u01cb\u01cc\f\5\2\2\u01cc\u01d0\5("+
		"\25\2\u01cd\u01ce\f\3\2\2\u01ce\u01d0\5\66\34\2\u01cf\u01cb\3\2\2\2\u01cf"+
		"\u01cd\3\2\2\2\u01d0\u01d3\3\2\2\2\u01d1\u01cf\3\2\2\2\u01d1\u01d2\3\2"+
		"\2\2\u01d2!\3\2\2\2\u01d3\u01d1\3\2\2\2\u01d4\u01d5\b\22\1\2\u01d5\u01d8"+
		"\5$\23\2\u01d6\u01d8\3\2\2\2\u01d7\u01d4\3\2\2\2\u01d7\u01d6\3\2\2\2\u01d8"+
		"\u01dd\3\2\2\2\u01d9\u01da\f\5\2\2\u01da\u01dc\5$\23\2\u01db\u01d9\3\2"+
		"\2\2\u01dc\u01df\3\2\2\2\u01dd\u01db\3\2\2\2\u01dd\u01de\3\2\2\2\u01de"+
		"#\3\2\2\2\u01df\u01dd\3\2\2\2\u01e0\u01e3\5(\25\2\u01e1\u01e3\5\66\34"+
		"\2\u01e2\u01e0\3\2\2\2\u01e2\u01e1\3\2\2\2\u01e3%\3\2\2\2\u01e4\u01e5"+
		"\b\24\1\2\u01e5\u01e6\5,\27\2\u01e6\u01ee\3\2\2\2\u01e7\u01e8\f\5\2\2"+
		"\u01e8\u01e9\7\16\2\2\u01e9\u01ed\5,\27\2\u01ea\u01eb\f\3\2\2\u01eb\u01ed"+
		"\7\16\2\2\u01ec\u01e7\3\2\2\2\u01ec\u01ea\3\2\2\2\u01ed\u01f0\3\2\2\2"+
		"\u01ee\u01ec\3\2\2\2\u01ee\u01ef\3\2\2\2\u01ef\'\3\2\2\2\u01f0\u01ee\3"+
		"\2\2\2\u01f1\u01f2\5*\26\2\u01f2\u01f3\7\4\2\2\u01f3\u01f4\5.\30\2\u01f4"+
		"\u01f5\7\5\2\2\u01f5)\3\2\2\2\u01f6\u01f7\5,\27\2\u01f7+\3\2\2\2\u01f8"+
		"\u01f9\5\64\33\2\u01f9\u01fa\5\60\31\2\u01fa\u01fb\5\62\32\2\u01fb\u01fc"+
		"\7\20\2\2\u01fc\u01fd\5@!\2\u01fd\u0201\7\21\2\2\u01fe\u0200\7\63\2\2"+
		"\u01ff\u01fe\3\2\2\2\u0200\u0203\3\2\2\2\u0201\u01ff\3\2\2\2\u0201\u0202"+
		"\3\2\2\2\u0202\u0219\3\2\2\2\u0203\u0201\3\2\2\2\u0204\u0205\5\64\33\2"+
		"\u0205\u0206\5\60\31\2\u0206\u020a\5\62\32\2\u0207\u0209\7\63\2\2\u0208"+
		"\u0207\3\2\2\2\u0209\u020c\3\2\2\2\u020a\u0208\3\2\2\2\u020a\u020b\3\2"+
		"\2\2\u020b\u0219\3\2\2\2\u020c\u020a\3\2\2\2\u020d\u020e\5\64\33\2\u020e"+
		"\u020f\5\62\32\2\u020f\u0210\7\20\2\2\u0210\u0211\5@!\2\u0211\u0215\7"+
		"\21\2\2\u0212\u0214\7\63\2\2\u0213\u0212\3\2\2\2\u0214\u0217\3\2\2\2\u0215"+
		"\u0213\3\2\2\2\u0215\u0216\3\2\2\2\u0216\u0219\3\2\2\2\u0217\u0215\3\2"+
		"\2\2\u0218\u01f8\3\2\2\2\u0218\u0204\3\2\2\2\u0218\u020d\3\2\2\2\u0219"+
		"-\3\2\2\2\u021a\u021b\b\30\1\2\u021b\u0222\5\66\34\2\u021c\u021d\5D#\2"+
		"\u021d\u021e\7\16\2\2\u021e\u021f\5\66\34\2\u021f\u0222\3\2\2\2\u0220"+
		"\u0222\3\2\2\2\u0221\u021a\3\2\2\2\u0221\u021c\3\2\2\2\u0221\u0220\3\2"+
		"\2\2\u0222\u022b\3\2\2\2\u0223\u0224\f\6\2\2\u0224\u022a\5\66\34\2\u0225"+
		"\u0226\f\5\2\2\u0226\u022a\5D#\2\u0227\u0228\f\4\2\2\u0228\u022a\7\16"+
		"\2\2\u0229\u0223\3\2\2\2\u0229\u0225\3\2\2\2\u0229\u0227\3\2\2\2\u022a"+
		"\u022d\3\2\2\2\u022b\u0229\3\2\2\2\u022b\u022c\3\2\2\2\u022c/\3\2\2\2"+
		"\u022d\u022b\3\2\2\2\u022e\u0231\5<\37\2\u022f\u0231\3\2\2\2\u0230\u022e"+
		"\3\2\2\2\u0230\u022f\3\2\2\2\u0231\61\3\2\2\2\u0232\u0233\t\2\2\2\u0233"+
		"\63\3\2\2\2\u0234\u0238\7\22\2\2\u0235\u0238\7\23\2\2\u0236\u0238\3\2"+
		"\2\2\u0237\u0234\3\2\2\2\u0237\u0235\3\2\2\2\u0237\u0236\3\2\2\2\u0238"+
		"\65\3\2\2\2\u0239\u023a\5\64\33\2\u023a\u023b\5:\36\2\u023b\u023c\5> "+
		"\2\u023c\u023d\7\16\2\2\u023d\u024a\3\2\2\2\u023e\u023f\5\64\33\2\u023f"+
		"\u0240\5:\36\2\u0240\u0241\5> \2\u0241\u024a\3\2\2\2\u0242\u0243\5:\36"+
		"\2\u0243\u0244\5> \2\u0244\u0245\7\16\2\2\u0245\u024a\3\2\2\2\u0246\u0247"+
		"\5:\36\2\u0247\u0248\5> \2\u0248\u024a\3\2\2\2\u0249\u0239\3\2\2\2\u0249"+
		"\u023e\3\2\2\2\u0249\u0242\3\2\2\2\u0249\u0246\3\2\2\2\u024a\67\3\2\2"+
		"\2\u024b\u024c\5:\36\2\u024c\u024d\7?\2\2\u024d9\3\2\2\2\u024e\u024f\5"+
		"<\37\2\u024f\u0250\7\f\2\2\u0250\u0251\7\r\2\2\u0251\u0254\3\2\2\2\u0252"+
		"\u0254\5<\37\2\u0253\u024e\3\2\2\2\u0253\u0252\3\2\2\2\u0254;\3\2\2\2"+
		"\u0255\u025d\7?\2\2\u0256\u0257\7?\2\2\u0257\u025d\5\16\b\2\u0258\u025d"+
		"\7\24\2\2\u0259\u025d\7\25\2\2\u025a\u025d\7\26\2\2\u025b\u025d\7\27\2"+
		"\2\u025c\u0255\3\2\2\2\u025c\u0256\3\2\2\2\u025c\u0258\3\2\2\2\u025c\u0259"+
		"\3\2\2\2\u025c\u025a\3\2\2\2\u025c\u025b\3\2\2\2\u025d=\3\2\2\2\u025e"+
		"\u025f\b \1\2\u025f\u0264\7?\2\2\u0260\u0261\7?\2\2\u0261\u0262\7C\2\2"+
		"\u0262\u0264\5D#\2\u0263\u025e\3\2\2\2\u0263\u0260\3\2\2\2\u0264\u026f"+
		"\3\2\2\2\u0265\u0266\f\5\2\2\u0266\u0267\7\n\2\2\u0267\u026e\7?\2\2\u0268"+
		"\u0269\f\3\2\2\u0269\u026a\7\n\2\2\u026a\u026b\7?\2\2\u026b\u026c\7C\2"+
		"\2\u026c\u026e\5D#\2\u026d\u0265\3\2\2\2\u026d\u0268\3\2\2\2\u026e\u0271"+
		"\3\2\2\2\u026f\u026d\3\2\2\2\u026f\u0270\3\2\2\2\u0270?\3\2\2\2\u0271"+
		"\u026f\3\2\2\2\u0272\u0273\b!\1\2\u0273\u0276\5B\"\2\u0274\u0276\3\2\2"+
		"\2\u0275\u0272\3\2\2\2\u0275\u0274\3\2\2\2\u0276\u027c\3\2\2\2\u0277\u0278"+
		"\f\4\2\2\u0278\u0279\7\n\2\2\u0279\u027b\5B\"\2\u027a\u0277\3\2\2\2\u027b"+
		"\u027e\3\2\2\2\u027c\u027a\3\2\2\2\u027c\u027d\3\2\2\2\u027dA\3\2\2\2"+
		"\u027e\u027c\3\2\2\2\u027f\u0280\5:\36\2\u0280\u0281\7?\2\2\u0281C\3\2"+
		"\2\2\u0282\u0290\5F$\2\u0283\u0290\5N(\2\u0284\u0290\5X-\2\u0285\u0290"+
		"\5\\/\2\u0286\u0290\5^\60\2\u0287\u0290\5`\61\2\u0288\u0290\5b\62\2\u0289"+
		"\u0290\5d\63\2\u028a\u0290\7,\2\2\u028b\u0290\7-\2\2\u028c\u028d\7.\2"+
		"\2\u028d\u0290\5D#\2\u028e\u0290\7.\2\2\u028f\u0282\3\2\2\2\u028f\u0283"+
		"\3\2\2\2\u028f\u0284\3\2\2\2\u028f\u0285\3\2\2\2\u028f\u0286\3\2\2\2\u028f"+
		"\u0287\3\2\2\2\u028f\u0288\3\2\2\2\u028f\u0289\3\2\2\2\u028f\u028a\3\2"+
		"\2\2\u028f\u028b\3\2\2\2\u028f\u028c\3\2\2\2\u028f\u028e\3\2\2\2\u0290"+
		"E\3\2\2\2\u0291\u0292\b$\1\2\u0292\u0297\5H%\2\u0293\u0294\7\67\2\2\u0294"+
		"\u0296\5H%\2\u0295\u0293\3\2\2\2\u0296\u0299\3\2\2\2\u0297\u0295\3\2\2"+
		"\2\u0297\u0298\3\2\2\2\u0298\u029c\3\2\2\2\u0299\u0297\3\2\2\2\u029a\u029c"+
		"\5\\/\2\u029b\u0291\3\2\2\2\u029b\u029a\3\2\2\2\u029c\u02a2\3\2\2\2\u029d"+
		"\u029e\f\3\2\2\u029e\u029f\7C\2\2\u029f\u02a1\5D#\2\u02a0\u029d\3\2\2"+
		"\2\u02a1\u02a4\3\2\2\2\u02a2\u02a0\3\2\2\2\u02a2\u02a3\3\2\2\2\u02a3G"+
		"\3\2\2\2\u02a4\u02a2\3\2\2\2\u02a5\u02a6\b%\1\2\u02a6\u02ab\5J&\2\u02a7"+
		"\u02a8\78\2\2\u02a8\u02aa\5J&\2\u02a9\u02a7\3\2\2\2\u02aa\u02ad\3\2\2"+
		"\2\u02ab\u02a9\3\2\2\2\u02ab\u02ac\3\2\2\2\u02ac\u02b3\3\2\2\2\u02ad\u02ab"+
		"\3\2\2\2\u02ae\u02af\f\4\2\2\u02af\u02b0\7\64\2\2\u02b0\u02b2\5L\'\2\u02b1"+
		"\u02ae\3\2\2\2\u02b2\u02b5\3\2\2\2\u02b3\u02b1\3\2\2\2\u02b3\u02b4\3\2"+
		"\2\2\u02b4I\3\2\2\2\u02b5\u02b3\3\2\2\2\u02b6\u02b7\7\67\2\2\u02b7\u02ba"+
		"\5L\'\2\u02b8\u02ba\5L\'\2\u02b9\u02b6\3\2\2\2\u02b9\u02b8\3\2\2\2\u02ba"+
		"K\3\2\2\2\u02bb\u02bc\b\'\1\2\u02bc\u02bd\7>\2\2\u02bd\u02e1\5L\'\f\u02be"+
		"\u02bf\7\60\2\2\u02bf\u02e1\5V,\2\u02c0\u02c1\7\60\2\2\u02c1\u02c2\5<"+
		"\37\2\u02c2\u02c3\7\f\2\2\u02c3\u02c4\5D#\2\u02c4\u02c5\7\r\2\2\u02c5"+
		"\u02e1\3\2\2\2\u02c6\u02c7\7\60\2\2\u02c7\u02c8\7?\2\2\u02c8\u02c9\5\16"+
		"\b\2\u02c9\u02ca\7\20\2\2\u02ca\u02cb\5l\67\2\u02cb\u02cc\7\21\2\2\u02cc"+
		"\u02e1\3\2\2\2\u02cd\u02e1\5h\65\2\u02ce\u02e1\5V,\2\u02cf\u02e1\7?\2"+
		"\2\u02d0\u02e1\5j\66\2\u02d1\u02d2\7\20\2\2\u02d2\u02d3\5F$\2\u02d3\u02d4"+
		"\7\21\2\2\u02d4\u02e1\3\2\2\2\u02d5\u02d6\7>\2\2\u02d6\u02d7\5D#\2\u02d7"+
		"\u02d8\7\f\2\2\u02d8\u02d9\5D#\2\u02d9\u02da\7\r\2\2\u02da\u02e1\3\2\2"+
		"\2\u02db\u02dc\7>\2\2\u02dc\u02dd\5D#\2\u02dd\u02de\7\30\2\2\u02de\u02df"+
		"\7?\2\2\u02df\u02e1\3\2\2\2\u02e0\u02bb\3\2\2\2\u02e0\u02be\3\2\2\2\u02e0"+
		"\u02c0\3\2\2\2\u02e0\u02c6\3\2\2\2\u02e0\u02cd\3\2\2\2\u02e0\u02ce\3\2"+
		"\2\2\u02e0\u02cf\3\2\2\2\u02e0\u02d0\3\2\2\2\u02e0\u02d1\3\2\2\2\u02e0"+
		"\u02d5\3\2\2\2\u02e0\u02db\3\2\2\2\u02e1\u0303\3\2\2\2\u02e2\u02e3\f\22"+
		"\2\2\u02e3\u02e4\7\30\2\2\u02e4\u0302\5V,\2\u02e5\u02e6\f\21\2\2\u02e6"+
		"\u02e7\7\30\2\2\u02e7\u0302\7?\2\2\u02e8\u02e9\f\r\2\2\u02e9\u0302\7>"+
		"\2\2\u02ea\u02eb\f\t\2\2\u02eb\u02ec\7\f\2\2\u02ec\u02ed\5D#\2\u02ed\u02ee"+
		"\7\r\2\2\u02ee\u0302\3\2\2\2\u02ef\u02f0\f\b\2\2\u02f0\u02f1\7\f\2\2\u02f1"+
		"\u02f2\5D#\2\u02f2\u02f3\7\r\2\2\u02f3\u02f4\7>\2\2\u02f4\u0302\3\2\2"+
		"\2\u02f5\u02f6\f\5\2\2\u02f6\u02f7\7\30\2\2\u02f7\u02f8\7?\2\2\u02f8\u0302"+
		"\7>\2\2\u02f9\u02fa\f\4\2\2\u02fa\u02fb\7\30\2\2\u02fb\u0302\7\61\2\2"+
		"\u02fc\u02fd\f\3\2\2\u02fd\u02fe\7\30\2\2\u02fe\u02ff\7\61\2\2\u02ff\u0300"+
		"\7\20\2\2\u0300\u0302\7\21\2\2\u0301\u02e2\3\2\2\2\u0301\u02e5\3\2\2\2"+
		"\u0301\u02e8\3\2\2\2\u0301\u02ea\3\2\2\2\u0301\u02ef\3\2\2\2\u0301\u02f5"+
		"\3\2\2\2\u0301\u02f9\3\2\2\2\u0301\u02fc\3\2\2\2\u0302\u0305\3\2\2\2\u0303"+
		"\u0301\3\2\2\2\u0303\u0304\3\2\2\2\u0304M\3\2\2\2\u0305\u0303\3\2\2\2"+
		"\u0306\u0307\b(\1\2\u0307\u030c\5P)\2\u0308\u0309\7\65\2\2\u0309\u030b"+
		"\5P)\2\u030a\u0308\3\2\2\2\u030b\u030e\3\2\2\2\u030c\u030a\3\2\2\2\u030c"+
		"\u030d\3\2\2\2\u030d\u0316\3\2\2\2\u030e\u030c\3\2\2\2\u030f\u0316\5\\"+
		"/\2\u0310\u0311\5F$\2\u0311\u0312\t\3\2\2\u0312\u0313\5F$\2\u0313\u0316"+
		"\3\2\2\2\u0314\u0316\5F$\2\u0315\u0306\3\2\2\2\u0315\u030f\3\2\2\2\u0315"+
		"\u0310\3\2\2\2\u0315\u0314\3\2\2\2\u0316\u031f\3\2\2\2\u0317\u0318\f\6"+
		"\2\2\u0318\u0319\t\4\2\2\u0319\u031e\5N(\7\u031a\u031b\f\4\2\2\u031b\u031c"+
		"\7C\2\2\u031c\u031e\5D#\2\u031d\u0317\3\2\2\2\u031d\u031a\3\2\2\2\u031e"+
		"\u0321\3\2\2\2\u031f\u031d\3\2\2\2\u031f\u0320\3\2\2\2\u0320O\3\2\2\2"+
		"\u0321\u031f\3\2\2\2\u0322\u0327\5R*\2\u0323\u0324\7\66\2\2\u0324\u0326"+
		"\5R*\2\u0325\u0323\3\2\2\2\u0326\u0329\3\2\2\2\u0327\u0325\3\2\2\2\u0327"+
		"\u0328\3\2\2\2\u0328Q\3\2\2\2\u0329\u0327\3\2\2\2\u032a\u032b\7=\2\2\u032b"+
		"\u032e\5N(\2\u032c\u032e\5T+\2\u032d\u032a\3\2\2\2\u032d\u032c\3\2\2\2"+
		"\u032eS\3\2\2\2\u032f\u0337\5j\66\2\u0330\u0337\7?\2\2\u0331\u0337\5h"+
		"\65\2\u0332\u0333\7\20\2\2\u0333\u0334\5N(\2\u0334\u0335\7\21\2\2\u0335"+
		"\u0337\3\2\2\2\u0336\u032f\3\2\2\2\u0336\u0330\3\2\2\2\u0336\u0331\3\2"+
		"\2\2\u0336\u0332\3\2\2\2\u0337U\3\2\2\2\u0338\u0339\5\62\32\2\u0339\u033a"+
		"\7\20\2\2\u033a\u033b\5l\67\2\u033b\u033c\7\21\2\2\u033c\u0343\3\2\2\2"+
		"\u033d\u033e\5<\37\2\u033e\u033f\7\20\2\2\u033f\u0340\5l\67\2\u0340\u0341"+
		"\7\21\2\2\u0341\u0343\3\2\2\2\u0342\u0338\3\2\2\2\u0342\u033d\3\2\2\2"+
		"\u0343W\3\2\2\2\u0344\u0345\7\4\2\2\u0345\u0346\5.\30\2\u0346\u0347\7"+
		"\5\2\2\u0347\u034b\3\2\2\2\u0348\u0349\7\4\2\2\u0349\u034b\7\5\2\2\u034a"+
		"\u0344\3\2\2\2\u034a\u0348\3\2\2\2\u034bY\3\2\2\2\u034c\u034f\5D#\2\u034d"+
		"\u034f\3\2\2\2\u034e\u034c\3\2\2\2\u034e\u034d\3\2\2\2\u034f[\3\2\2\2"+
		"\u0350\u0351\7\37\2\2\u0351\u0352\7\20\2\2\u0352\u0353\5D#\2\u0353\u0354"+
		"\7\21\2\2\u0354\u0355\5D#\2\u0355\u035f\3\2\2\2\u0356\u0357\7\37\2\2\u0357"+
		"\u0358\7\20\2\2\u0358\u0359\5D#\2\u0359\u035a\7\21\2\2\u035a\u035b\5D"+
		"#\2\u035b\u035c\7 \2\2\u035c\u035d\5D#\2\u035d\u035f\3\2\2\2\u035e\u0350"+
		"\3\2\2\2\u035e\u0356\3\2\2\2\u035f]\3\2\2\2\u0360\u0361\7!\2\2\u0361\u0362"+
		"\7\20\2\2\u0362\u0363\5D#\2\u0363\u0364\7\16\2\2\u0364\u0365\5D#\2\u0365"+
		"\u0366\7\16\2\2\u0366\u0367\5D#\2\u0367\u0368\7\21\2\2\u0368\u0369\5D"+
		"#\2\u0369\u0384\3\2\2\2\u036a\u036b\7!\2\2\u036b\u036c\7\20\2\2\u036c"+
		"\u036d\5\66\34\2\u036d\u036e\5D#\2\u036e\u036f\7\16\2\2\u036f\u0370\5"+
		"D#\2\u0370\u0371\7\21\2\2\u0371\u0372\5D#\2\u0372\u0384\3\2\2\2\u0373"+
		"\u0374\7!\2\2\u0374\u0375\7\20\2\2\u0375\u0376\7?\2\2\u0376\u0377\7\""+
		"\2\2\u0377\u0378\5D#\2\u0378\u0379\7\21\2\2\u0379\u037a\5D#\2\u037a\u0384"+
		"\3\2\2\2\u037b\u037c\7!\2\2\u037c\u037d\7\20\2\2\u037d\u037e\58\35\2\u037e"+
		"\u037f\7\"\2\2\u037f\u0380\5D#\2\u0380\u0381\7\21\2\2\u0381\u0382\5D#"+
		"\2\u0382\u0384\3\2\2\2\u0383\u0360\3\2\2\2\u0383\u036a\3\2\2\2\u0383\u0373"+
		"\3\2\2\2\u0383\u037b\3\2\2\2\u0384_\3\2\2\2\u0385\u0386\7#\2\2\u0386\u0387"+
		"\7\20\2\2\u0387\u0388\5D#\2\u0388\u0389\7\21\2\2\u0389\u038a\5D#\2\u038a"+
		"a\3\2\2\2\u038b\u038c\7$\2\2\u038c\u038d\5D#\2\u038d\u038e\7#\2\2\u038e"+
		"\u038f\7\20\2\2\u038f\u0390\5D#\2\u0390\u0391\7\21\2\2\u0391c\3\2\2\2"+
		"\u0392\u0393\7)\2\2\u0393\u0394\7\20\2\2\u0394\u0395\5D#\2\u0395\u0396"+
		"\7\21\2\2\u0396\u039a\7\4\2\2\u0397\u0399\5f\64\2\u0398\u0397\3\2\2\2"+
		"\u0399\u039c\3\2\2\2\u039a\u0398\3\2\2\2\u039a\u039b\3\2\2\2\u039b\u039d"+
		"\3\2\2\2\u039c\u039a\3\2\2\2\u039d\u039e\7\5\2\2\u039ee\3\2\2\2\u039f"+
		"\u03a0\7*\2\2\u03a0\u03a3\5j\66\2\u03a1\u03a3\7+\2\2\u03a2\u039f\3\2\2"+
		"\2\u03a2\u03a1\3\2\2\2\u03a3\u03a4\3\2\2\2\u03a4\u03a5\7\"\2\2\u03a5\u03a6"+
		"\5.\30\2\u03a6g\3\2\2\2\u03a7\u03a8\7\62\2\2\u03a8i\3\2\2\2\u03a9\u03aa"+
		"\t\5\2\2\u03aak\3\2\2\2\u03ab\u03ac\b\67\1\2\u03ac\u03af\5D#\2\u03ad\u03af"+
		"\3\2\2\2\u03ae\u03ab\3\2\2\2\u03ae\u03ad\3\2\2\2\u03af\u03b5\3\2\2\2\u03b0"+
		"\u03b1\f\4\2\2\u03b1\u03b2\7\n\2\2\u03b2\u03b4\5D#\2\u03b3\u03b0\3\2\2"+
		"\2\u03b4\u03b7\3\2\2\2\u03b5\u03b3\3\2\2\2\u03b5\u03b6\3\2\2\2\u03b6m"+
		"\3\2\2\2\u03b7\u03b5\3\2\2\2Mry\177\u008e\u009d\u00a9\u00b7\u00c3\u00d2"+
		"\u00dd\u00e6\u00f1\u00f9\u00fe\u0104\u010b\u0159\u015e\u0164\u016a\u016c"+
		"\u0177\u0179\u01c4\u01c9\u01cf\u01d1\u01d7\u01dd\u01e2\u01ec\u01ee\u0201"+
		"\u020a\u0215\u0218\u0221\u0229\u022b\u0230\u0237\u0249\u0253\u025c\u0263"+
		"\u026d\u026f\u0275\u027c\u028f\u0297\u029b\u02a2\u02ab\u02b3\u02b9\u02e0"+
		"\u0301\u0303\u030c\u0315\u031d\u031f\u0327\u032d\u0336\u0342\u034a\u034e"+
		"\u035e\u0383\u039a\u03a2\u03ae\u03b5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}