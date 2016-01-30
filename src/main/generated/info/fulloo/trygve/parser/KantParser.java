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
		RULE_compound_type_name = 28, RULE_type_name = 29, RULE_builtin_type_name = 30, 
		RULE_identifier_list = 31, RULE_param_list = 32, RULE_param_decl = 33, 
		RULE_expr = 34, RULE_abelian_expr = 35, RULE_abelian_product = 36, RULE_abelian_unary_op = 37, 
		RULE_abelian_atom = 38, RULE_boolean_expr = 39, RULE_boolean_product = 40, 
		RULE_boolean_unary_op = 41, RULE_boolean_atom = 42, RULE_message = 43, 
		RULE_block = 44, RULE_expr_or_null = 45, RULE_if_expr = 46, RULE_for_expr = 47, 
		RULE_while_expr = 48, RULE_do_while_expr = 49, RULE_switch_expr = 50, 
		RULE_switch_body = 51, RULE_null_expr = 52, RULE_constant = 53, RULE_argument_list = 54;
	public static final String[] ruleNames = {
		"program", "main", "type_declaration_list", "type_declaration", "implements_list", 
		"type_parameters", "type_list", "type_parameter", "context_body", "context_body_element", 
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
			setState(114);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(110);
				type_declaration_list(0);
				setState(111);
				main();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(113);
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
			setState(116);
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
			setState(121);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(119);
				type_declaration();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(127);
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
					setState(123);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(124);
					type_declaration();
					}
					} 
				}
				setState(129);
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
			setState(210);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(130);
				match(T__0);
				setState(131);
				match(JAVA_ID);
				setState(132);
				match(T__1);
				setState(133);
				context_body(0);
				setState(134);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(136);
				match(T__3);
				setState(137);
				match(JAVA_ID);
				setState(138);
				type_parameters();
				setState(142);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(139);
					implements_list(0);
					}
					}
					setState(144);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(145);
				match(T__1);
				setState(146);
				class_body(0);
				setState(147);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(149);
				match(T__3);
				setState(150);
				match(JAVA_ID);
				setState(151);
				type_parameters();
				setState(152);
				match(T__4);
				setState(153);
				match(JAVA_ID);
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(154);
					implements_list(0);
					}
					}
					setState(159);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(160);
				match(T__1);
				setState(161);
				class_body(0);
				setState(162);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(164);
				match(T__3);
				setState(165);
				match(JAVA_ID);
				setState(169);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(166);
					implements_list(0);
					}
					}
					setState(171);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(172);
				match(T__1);
				setState(173);
				class_body(0);
				setState(174);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(176);
				match(T__3);
				setState(177);
				match(JAVA_ID);
				setState(178);
				match(T__4);
				setState(179);
				match(JAVA_ID);
				setState(183);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(180);
					implements_list(0);
					}
					}
					setState(185);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(186);
				match(T__1);
				setState(187);
				class_body(0);
				setState(188);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(190);
				match(T__3);
				setState(191);
				match(JAVA_ID);
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(192);
					implements_list(0);
					}
					}
					setState(197);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(198);
				match(T__4);
				setState(199);
				match(JAVA_ID);
				setState(200);
				match(T__1);
				setState(201);
				class_body(0);
				setState(202);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(204);
				match(T__5);
				setState(205);
				match(JAVA_ID);
				setState(206);
				match(T__1);
				setState(207);
				interface_body(0);
				setState(208);
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
			setState(213);
			match(T__6);
			setState(214);
			match(JAVA_ID);
			}
			_ctx.stop = _input.LT(-1);
			setState(221);
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
					setState(216);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(217);
					match(T__7);
					setState(218);
					match(JAVA_ID);
					}
					} 
				}
				setState(223);
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
			setState(224);
			match(LT);
			setState(225);
			type_parameter();
			setState(230);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(226);
				match(T__7);
				setState(227);
				type_parameter();
				}
				}
				setState(232);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(233);
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
			setState(235);
			match(LT);
			setState(236);
			type_name();
			setState(241);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(237);
				match(T__7);
				setState(238);
				type_name();
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
			setState(246);
			type_name();
			setState(249);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(247);
				match(T__4);
				setState(248);
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
			setState(254);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(252);
				context_body_element();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(260);
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
					setState(256);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(257);
					context_body_element();
					}
					} 
				}
				setState(262);
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
			setState(267);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(263);
				method_decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(264);
				object_decl();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(265);
				role_decl();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(266);
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
			setState(345);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(269);
				match(T__8);
				setState(270);
				role_vec_modifier();
				setState(271);
				match(JAVA_ID);
				setState(272);
				match(T__1);
				setState(273);
				role_body(0);
				setState(274);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(276);
				match(T__8);
				setState(277);
				role_vec_modifier();
				setState(278);
				match(JAVA_ID);
				setState(279);
				match(T__1);
				setState(280);
				role_body(0);
				setState(281);
				match(T__2);
				setState(282);
				match(REQUIRES);
				setState(283);
				match(T__1);
				setState(284);
				self_methods(0);
				setState(285);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
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
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(295);
				access_qualifier();
				setState(296);
				match(T__8);
				setState(297);
				role_vec_modifier();
				setState(298);
				match(JAVA_ID);
				setState(299);
				match(T__1);
				setState(300);
				role_body(0);
				setState(301);
				match(T__2);
				setState(302);
				match(REQUIRES);
				setState(303);
				match(T__1);
				setState(304);
				self_methods(0);
				setState(305);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
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
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(314);
				match(T__8);
				setState(315);
				role_vec_modifier();
				setState(316);
				match(JAVA_ID);
				setState(317);
				match(T__1);
				setState(318);
				role_body(0);
				setState(319);
				match(T__2);
				setState(320);
				match(REQUIRES);
				setState(321);
				match(T__1);
				setState(322);
				self_methods(0);
				setState(323);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
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
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(333);
				access_qualifier();
				setState(334);
				match(T__8);
				setState(335);
				role_vec_modifier();
				setState(336);
				match(JAVA_ID);
				setState(337);
				match(T__1);
				setState(338);
				role_body(0);
				setState(339);
				match(T__2);
				setState(340);
				match(REQUIRES);
				setState(341);
				match(T__1);
				setState(342);
				self_methods(0);
				setState(343);
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
			setState(350);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(347);
				match(T__9);
				setState(348);
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
			setState(356);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(353);
				method_decl();
				}
				break;
			case 2:
				{
				setState(354);
				object_decl();
				}
				break;
			case 3:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(364);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(362);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(358);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(359);
						method_decl();
						}
						break;
					case 2:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(360);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(361);
						object_decl();
						}
						break;
					}
					} 
				}
				setState(366);
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
			setState(368);
			method_signature();
			}
			_ctx.stop = _input.LT(-1);
			setState(377);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(375);
					switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
					case 1:
						{
						_localctx = new Self_methodsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_self_methods);
						setState(370);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(371);
						match(T__11);
						setState(372);
						method_signature();
						}
						break;
					case 2:
						{
						_localctx = new Self_methodsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_self_methods);
						setState(373);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(374);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(379);
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
			setState(452);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(380);
				match(T__12);
				setState(381);
				role_vec_modifier();
				setState(382);
				match(JAVA_ID);
				setState(383);
				match(T__1);
				setState(384);
				stageprop_body(0);
				setState(385);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(387);
				match(T__12);
				setState(388);
				role_vec_modifier();
				setState(389);
				match(JAVA_ID);
				setState(390);
				match(T__1);
				setState(391);
				stageprop_body(0);
				setState(392);
				match(T__2);
				setState(393);
				match(REQUIRES);
				setState(394);
				match(T__1);
				setState(395);
				self_methods(0);
				setState(396);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
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
				stageprop_body(0);
				setState(404);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(406);
				access_qualifier();
				setState(407);
				match(T__12);
				setState(408);
				role_vec_modifier();
				setState(409);
				match(JAVA_ID);
				setState(410);
				match(T__1);
				setState(411);
				stageprop_body(0);
				setState(412);
				match(T__2);
				setState(413);
				match(REQUIRES);
				setState(414);
				match(T__1);
				setState(415);
				self_methods(0);
				setState(416);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(418);
				match(T__12);
				setState(419);
				role_vec_modifier();
				setState(420);
				match(JAVA_ID);
				setState(421);
				match(T__1);
				setState(422);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(424);
				match(T__12);
				setState(425);
				role_vec_modifier();
				setState(426);
				match(JAVA_ID);
				setState(427);
				match(T__1);
				setState(428);
				match(T__2);
				setState(429);
				match(REQUIRES);
				setState(430);
				match(T__1);
				setState(431);
				self_methods(0);
				setState(432);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(434);
				access_qualifier();
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
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(441);
				access_qualifier();
				setState(442);
				match(T__12);
				setState(443);
				role_vec_modifier();
				setState(444);
				match(JAVA_ID);
				setState(445);
				match(T__1);
				setState(446);
				match(T__2);
				setState(447);
				match(REQUIRES);
				setState(448);
				match(T__1);
				setState(449);
				self_methods(0);
				setState(450);
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
			setState(457);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(455);
				method_decl();
				}
				break;
			case 2:
				{
				setState(456);
				object_decl();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(465);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(463);
					switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
					case 1:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(459);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(460);
						method_decl();
						}
						break;
					case 2:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(461);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(462);
						object_decl();
						}
						break;
					}
					} 
				}
				setState(467);
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
			setState(471);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(469);
				class_body_element();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(477);
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
					setState(473);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(474);
					class_body_element();
					}
					} 
				}
				setState(479);
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
			setState(482);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(480);
				method_decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(481);
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
			setState(485);
			method_signature();
			}
			_ctx.stop = _input.LT(-1);
			setState(494);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(492);
					switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
					case 1:
						{
						_localctx = new Interface_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_interface_body);
						setState(487);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(488);
						match(T__11);
						setState(489);
						method_signature();
						}
						break;
					case 2:
						{
						_localctx = new Interface_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_interface_body);
						setState(490);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(491);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(496);
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
			setState(497);
			method_decl_hook();
			setState(498);
			match(T__1);
			setState(499);
			expr_and_decl_list(0);
			setState(500);
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
			setState(502);
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
			setState(536);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(504);
				access_qualifier();
				setState(505);
				return_type();
				setState(506);
				method_name();
				setState(507);
				match(T__13);
				setState(508);
				param_list(0);
				setState(509);
				match(T__14);
				setState(513);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(510);
						match(CONST);
						}
						} 
					}
					setState(515);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(516);
				access_qualifier();
				setState(517);
				return_type();
				setState(518);
				method_name();
				setState(522);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(519);
						match(CONST);
						}
						} 
					}
					setState(524);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(525);
				access_qualifier();
				setState(526);
				method_name();
				setState(527);
				match(T__13);
				setState(528);
				param_list(0);
				setState(529);
				match(T__14);
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
			setState(545);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				setState(539);
				object_decl();
				}
				break;
			case 2:
				{
				setState(540);
				expr();
				setState(541);
				match(T__11);
				setState(542);
				object_decl();
				}
				break;
			case 3:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(555);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(553);
					switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
					case 1:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(547);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(548);
						object_decl();
						}
						break;
					case 2:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(549);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(550);
						expr();
						}
						break;
					case 3:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(551);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(552);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(557);
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
			setState(560);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(558);
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
			setState(562);
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
			setState(567);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(564);
				match(T__15);
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 2);
				{
				setState(565);
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
			setState(585);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(569);
				access_qualifier();
				setState(570);
				compound_type_name();
				setState(571);
				identifier_list(0);
				setState(572);
				match(T__11);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(574);
				access_qualifier();
				setState(575);
				compound_type_name();
				setState(576);
				identifier_list(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(578);
				compound_type_name();
				setState(579);
				identifier_list(0);
				setState(580);
				match(T__11);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(582);
				compound_type_name();
				setState(583);
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
			setState(587);
			compound_type_name();
			setState(588);
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
			setState(595);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(590);
				type_name();
				setState(591);
				match(T__9);
				setState(592);
				match(T__10);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(594);
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
		enterRule(_localctx, 58, RULE_type_name);
		try {
			setState(601);
			switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(597);
				match(JAVA_ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(598);
				match(JAVA_ID);
				setState(599);
				type_list();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(600);
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
		enterRule(_localctx, 60, RULE_builtin_type_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(603);
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
		int _startState = 62;
		enterRecursionRule(_localctx, 62, RULE_identifier_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(610);
			switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
			case 1:
				{
				setState(606);
				match(JAVA_ID);
				}
				break;
			case 2:
				{
				setState(607);
				match(JAVA_ID);
				setState(608);
				match(ASSIGN);
				setState(609);
				expr();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(622);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(620);
					switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
					case 1:
						{
						_localctx = new Identifier_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identifier_list);
						setState(612);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(613);
						match(T__7);
						setState(614);
						match(JAVA_ID);
						}
						break;
					case 2:
						{
						_localctx = new Identifier_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identifier_list);
						setState(615);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(616);
						match(T__7);
						setState(617);
						match(JAVA_ID);
						setState(618);
						match(ASSIGN);
						setState(619);
						expr();
						}
						break;
					}
					} 
				}
				setState(624);
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
		int _startState = 64;
		enterRecursionRule(_localctx, 64, RULE_param_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(628);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				{
				setState(626);
				param_decl();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(635);
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
					setState(630);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(631);
					match(T__7);
					setState(632);
					param_decl();
					}
					} 
				}
				setState(637);
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
		enterRule(_localctx, 66, RULE_param_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(638);
			compound_type_name();
			setState(639);
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
		enterRule(_localctx, 68, RULE_expr);
		try {
			setState(654);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(641);
				abelian_expr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(642);
				boolean_expr(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(643);
				block();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(644);
				if_expr();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(645);
				for_expr();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(646);
				while_expr();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(647);
				do_while_expr();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(648);
				switch_expr();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(649);
				match(BREAK);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(650);
				match(CONTINUE);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(651);
				match(RETURN);
				setState(652);
				expr();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(653);
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
		int _startState = 70;
		enterRecursionRule(_localctx, 70, RULE_abelian_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(666);
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
				setState(657);
				abelian_product(0);
				setState(662);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(658);
						match(ABELIAN_SUMOP);
						setState(659);
						abelian_product(0);
						}
						} 
					}
					setState(664);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
				}
				}
				break;
			case T__28:
				{
				setState(665);
				if_expr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(673);
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
					setState(668);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(669);
					match(ASSIGN);
					setState(670);
					expr();
					}
					} 
				}
				setState(675);
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
		int _startState = 72;
		enterRecursionRule(_localctx, 72, RULE_abelian_product, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(677);
			abelian_unary_op();
			setState(682);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(678);
					match(ABELIAN_MULOP);
					setState(679);
					abelian_unary_op();
					}
					} 
				}
				setState(684);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			}
			}
			_ctx.stop = _input.LT(-1);
			setState(690);
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
					setState(685);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(686);
					match(POW);
					setState(687);
					abelian_atom(0);
					}
					} 
				}
				setState(692);
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
		enterRule(_localctx, 74, RULE_abelian_unary_op);
		try {
			setState(696);
			switch ( getInterpreter().adaptivePredict(_input,55,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(693);
				match(ABELIAN_SUMOP);
				setState(694);
				abelian_atom(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(695);
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
		int _startState = 76;
		enterRecursionRule(_localctx, 76, RULE_abelian_atom, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(739);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				{
				setState(699);
				match(ABELIAN_INCREMENT_OP);
				setState(700);
				abelian_atom(10);
				}
				break;
			case 2:
				{
				setState(701);
				match(NEW);
				setState(702);
				message();
				}
				break;
			case 3:
				{
				setState(703);
				match(NEW);
				setState(704);
				type_name();
				setState(705);
				match(T__9);
				setState(706);
				expr();
				setState(707);
				match(T__10);
				}
				break;
			case 4:
				{
				setState(709);
				match(NEW);
				setState(710);
				match(JAVA_ID);
				setState(711);
				type_list();
				setState(712);
				match(T__13);
				setState(713);
				argument_list(0);
				setState(714);
				match(T__14);
				}
				break;
			case 5:
				{
				setState(716);
				builtin_type_name();
				setState(717);
				match(T__21);
				setState(718);
				message();
				}
				break;
			case 6:
				{
				setState(720);
				null_expr();
				}
				break;
			case 7:
				{
				setState(721);
				message();
				}
				break;
			case 8:
				{
				setState(722);
				match(JAVA_ID);
				}
				break;
			case 9:
				{
				setState(723);
				constant();
				}
				break;
			case 10:
				{
				setState(724);
				match(T__13);
				setState(725);
				abelian_expr(0);
				setState(726);
				match(T__14);
				}
				break;
			case 11:
				{
				setState(728);
				match(ABELIAN_INCREMENT_OP);
				setState(729);
				expr();
				setState(730);
				match(T__9);
				setState(731);
				expr();
				setState(732);
				match(T__10);
				}
				break;
			case 12:
				{
				setState(734);
				match(ABELIAN_INCREMENT_OP);
				setState(735);
				expr();
				setState(736);
				match(T__21);
				setState(737);
				match(JAVA_ID);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(774);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(772);
					switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
					case 1:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(741);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(742);
						match(T__21);
						setState(743);
						message();
						}
						break;
					case 2:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(744);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(745);
						match(T__21);
						setState(746);
						match(JAVA_ID);
						}
						break;
					case 3:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(747);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(748);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 4:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(749);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(750);
						match(T__9);
						setState(751);
						expr();
						setState(752);
						match(T__10);
						}
						break;
					case 5:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(754);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(755);
						match(T__9);
						setState(756);
						expr();
						setState(757);
						match(T__10);
						setState(758);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 6:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(760);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(761);
						match(T__21);
						setState(762);
						match(JAVA_ID);
						setState(763);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 7:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(764);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(765);
						match(T__21);
						setState(766);
						match(CLONE);
						}
						break;
					case 8:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(767);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(768);
						match(T__21);
						setState(769);
						match(CLONE);
						setState(770);
						match(T__13);
						setState(771);
						match(T__14);
						}
						break;
					}
					} 
				}
				setState(776);
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
		int _startState = 78;
		enterRecursionRule(_localctx, 78, RULE_boolean_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(792);
			switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
			case 1:
				{
				setState(778);
				boolean_product();
				setState(783);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,59,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(779);
						match(BOOLEAN_SUMOP);
						setState(780);
						boolean_product();
						}
						} 
					}
					setState(785);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,59,_ctx);
				}
				}
				break;
			case 2:
				{
				setState(786);
				if_expr();
				}
				break;
			case 3:
				{
				setState(787);
				abelian_expr(0);
				setState(788);
				((Boolean_exprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << LT) | (1L << GT))) != 0)) ) {
					((Boolean_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(789);
				abelian_expr(0);
				}
				break;
			case 4:
				{
				setState(791);
				abelian_expr(0);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(802);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,62,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(800);
					switch ( getInterpreter().adaptivePredict(_input,61,_ctx) ) {
					case 1:
						{
						_localctx = new Boolean_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolean_expr);
						setState(794);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(795);
						((Boolean_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << BOOLEAN_MULOP))) != 0)) ) {
							((Boolean_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(796);
						boolean_expr(5);
						}
						break;
					case 2:
						{
						_localctx = new Boolean_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolean_expr);
						setState(797);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(798);
						match(ASSIGN);
						setState(799);
						expr();
						}
						break;
					}
					} 
				}
				setState(804);
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
		enterRule(_localctx, 80, RULE_boolean_product);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(805);
			boolean_unary_op();
			setState(810);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(806);
					match(BOOLEAN_MULOP);
					setState(807);
					boolean_unary_op();
					}
					} 
				}
				setState(812);
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
		enterRule(_localctx, 82, RULE_boolean_unary_op);
		try {
			setState(816);
			switch (_input.LA(1)) {
			case LOGICAL_NEGATION:
				enterOuterAlt(_localctx, 1);
				{
				setState(813);
				match(LOGICAL_NEGATION);
				setState(814);
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
				setState(815);
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
		enterRule(_localctx, 84, RULE_boolean_atom);
		try {
			setState(825);
			switch (_input.LA(1)) {
			case STRING:
			case INTEGER:
			case FLOAT:
			case BOOLEAN:
				enterOuterAlt(_localctx, 1);
				{
				setState(818);
				constant();
				}
				break;
			case JAVA_ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(819);
				match(JAVA_ID);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 3);
				{
				setState(820);
				null_expr();
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 4);
				{
				setState(821);
				match(T__13);
				setState(822);
				boolean_expr(0);
				setState(823);
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
		enterRule(_localctx, 86, RULE_message);
		try {
			setState(837);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(827);
				method_name();
				setState(828);
				match(T__13);
				setState(829);
				argument_list(0);
				setState(830);
				match(T__14);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(832);
				type_name();
				setState(833);
				match(T__13);
				setState(834);
				argument_list(0);
				setState(835);
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
		enterRule(_localctx, 88, RULE_block);
		try {
			setState(845);
			switch ( getInterpreter().adaptivePredict(_input,67,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(839);
				match(T__1);
				setState(840);
				expr_and_decl_list(0);
				setState(841);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(843);
				match(T__1);
				setState(844);
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
		enterRule(_localctx, 90, RULE_expr_or_null);
		try {
			setState(849);
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
				setState(847);
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
		enterRule(_localctx, 92, RULE_if_expr);
		try {
			setState(865);
			switch ( getInterpreter().adaptivePredict(_input,69,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(851);
				match(T__28);
				setState(852);
				match(T__13);
				setState(853);
				expr();
				setState(854);
				match(T__14);
				setState(855);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(857);
				match(T__28);
				setState(858);
				match(T__13);
				setState(859);
				expr();
				setState(860);
				match(T__14);
				setState(861);
				expr();
				setState(862);
				match(T__29);
				setState(863);
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
		enterRule(_localctx, 94, RULE_for_expr);
		try {
			setState(902);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(867);
				match(T__30);
				setState(868);
				match(T__13);
				setState(869);
				expr();
				setState(870);
				match(T__11);
				setState(871);
				expr();
				setState(872);
				match(T__11);
				setState(873);
				expr();
				setState(874);
				match(T__14);
				setState(875);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(877);
				match(T__30);
				setState(878);
				match(T__13);
				setState(879);
				object_decl();
				setState(880);
				expr();
				setState(881);
				match(T__11);
				setState(882);
				expr();
				setState(883);
				match(T__14);
				setState(884);
				expr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(886);
				match(T__30);
				setState(887);
				match(T__13);
				setState(888);
				match(JAVA_ID);
				setState(889);
				match(T__31);
				setState(890);
				expr();
				setState(891);
				match(T__14);
				setState(892);
				expr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(894);
				match(T__30);
				setState(895);
				match(T__13);
				setState(896);
				trivial_object_decl();
				setState(897);
				match(T__31);
				setState(898);
				expr();
				setState(899);
				match(T__14);
				setState(900);
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
		enterRule(_localctx, 96, RULE_while_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(904);
			match(T__32);
			setState(905);
			match(T__13);
			setState(906);
			expr();
			setState(907);
			match(T__14);
			setState(908);
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
		enterRule(_localctx, 98, RULE_do_while_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(910);
			match(T__33);
			setState(911);
			expr();
			setState(912);
			match(T__32);
			setState(913);
			match(T__13);
			setState(914);
			expr();
			setState(915);
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
		enterRule(_localctx, 100, RULE_switch_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(917);
			match(SWITCH);
			setState(918);
			match(T__13);
			setState(919);
			expr();
			setState(920);
			match(T__14);
			setState(921);
			match(T__1);
			setState(925);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE || _la==DEFAULT) {
				{
				{
				setState(922);
				switch_body();
				}
				}
				setState(927);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(928);
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
		enterRule(_localctx, 102, RULE_switch_body);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(933);
			switch (_input.LA(1)) {
			case CASE:
				{
				setState(930);
				match(CASE);
				setState(931);
				constant();
				}
				break;
			case DEFAULT:
				{
				setState(932);
				match(DEFAULT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(935);
			match(T__31);
			setState(936);
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
		enterRule(_localctx, 104, RULE_null_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(938);
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
		enterRule(_localctx, 106, RULE_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(940);
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
		int _startState = 108;
		enterRecursionRule(_localctx, 108, RULE_argument_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(945);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				{
				setState(943);
				expr();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(952);
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
					setState(947);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(948);
					match(T__7);
					setState(949);
					expr();
					}
					} 
				}
				setState(954);
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
		case 31:
			return identifier_list_sempred((Identifier_listContext)_localctx, predIndex);
		case 32:
			return param_list_sempred((Param_listContext)_localctx, predIndex);
		case 35:
			return abelian_expr_sempred((Abelian_exprContext)_localctx, predIndex);
		case 36:
			return abelian_product_sempred((Abelian_productContext)_localctx, predIndex);
		case 38:
			return abelian_atom_sempred((Abelian_atomContext)_localctx, predIndex);
		case 39:
			return boolean_expr_sempred((Boolean_exprContext)_localctx, predIndex);
		case 54:
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3C\u03be\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\3\2\3\2\3\2\3\2\5\2u\n\2\3\3\3"+
		"\3\3\4\3\4\3\4\5\4|\n\4\3\4\3\4\7\4\u0080\n\4\f\4\16\4\u0083\13\4\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u008f\n\5\f\5\16\5\u0092\13\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u009e\n\5\f\5\16\5\u00a1"+
		"\13\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u00aa\n\5\f\5\16\5\u00ad\13\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u00b8\n\5\f\5\16\5\u00bb\13\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u00c4\n\5\f\5\16\5\u00c7\13\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u00d5\n\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\7\6\u00de\n\6\f\6\16\6\u00e1\13\6\3\7\3\7\3\7\3\7\7\7\u00e7"+
		"\n\7\f\7\16\7\u00ea\13\7\3\7\3\7\3\b\3\b\3\b\3\b\7\b\u00f2\n\b\f\b\16"+
		"\b\u00f5\13\b\3\b\3\b\3\t\3\t\3\t\5\t\u00fc\n\t\3\n\3\n\3\n\5\n\u0101"+
		"\n\n\3\n\3\n\7\n\u0105\n\n\f\n\16\n\u0108\13\n\3\13\3\13\3\13\3\13\5\13"+
		"\u010e\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u015c\n\f\3\r\3\r\3\r\5\r\u0161"+
		"\n\r\3\16\3\16\3\16\3\16\5\16\u0167\n\16\3\16\3\16\3\16\3\16\7\16\u016d"+
		"\n\16\f\16\16\16\u0170\13\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\7"+
		"\17\u017a\n\17\f\17\16\17\u017d\13\17\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u01c7\n\20\3\21"+
		"\3\21\3\21\5\21\u01cc\n\21\3\21\3\21\3\21\3\21\7\21\u01d2\n\21\f\21\16"+
		"\21\u01d5\13\21\3\22\3\22\3\22\5\22\u01da\n\22\3\22\3\22\7\22\u01de\n"+
		"\22\f\22\16\22\u01e1\13\22\3\23\3\23\5\23\u01e5\n\23\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\24\3\24\7\24\u01ef\n\24\f\24\16\24\u01f2\13\24\3\25\3"+
		"\25\3\25\3\25\3\25\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u0202"+
		"\n\27\f\27\16\27\u0205\13\27\3\27\3\27\3\27\3\27\7\27\u020b\n\27\f\27"+
		"\16\27\u020e\13\27\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u0216\n\27\f\27"+
		"\16\27\u0219\13\27\5\27\u021b\n\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\5\30\u0224\n\30\3\30\3\30\3\30\3\30\3\30\3\30\7\30\u022c\n\30\f\30\16"+
		"\30\u022f\13\30\3\31\3\31\5\31\u0233\n\31\3\32\3\32\3\33\3\33\3\33\5\33"+
		"\u023a\n\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\5\34\u024c\n\34\3\35\3\35\3\35\3\36\3\36\3\36\3\36"+
		"\3\36\5\36\u0256\n\36\3\37\3\37\3\37\3\37\5\37\u025c\n\37\3 \3 \3!\3!"+
		"\3!\3!\3!\5!\u0265\n!\3!\3!\3!\3!\3!\3!\3!\3!\7!\u026f\n!\f!\16!\u0272"+
		"\13!\3\"\3\"\3\"\5\"\u0277\n\"\3\"\3\"\3\"\7\"\u027c\n\"\f\"\16\"\u027f"+
		"\13\"\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\5$\u0291\n$\3%\3"+
		"%\3%\3%\7%\u0297\n%\f%\16%\u029a\13%\3%\5%\u029d\n%\3%\3%\3%\7%\u02a2"+
		"\n%\f%\16%\u02a5\13%\3&\3&\3&\3&\7&\u02ab\n&\f&\16&\u02ae\13&\3&\3&\3"+
		"&\7&\u02b3\n&\f&\16&\u02b6\13&\3\'\3\'\3\'\5\'\u02bb\n\'\3(\3(\3(\3(\3"+
		"(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3"+
		"(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\5(\u02e6\n(\3(\3(\3(\3(\3(\3"+
		"(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3"+
		"(\3(\3(\7(\u0307\n(\f(\16(\u030a\13(\3)\3)\3)\3)\7)\u0310\n)\f)\16)\u0313"+
		"\13)\3)\3)\3)\3)\3)\3)\5)\u031b\n)\3)\3)\3)\3)\3)\3)\7)\u0323\n)\f)\16"+
		")\u0326\13)\3*\3*\3*\7*\u032b\n*\f*\16*\u032e\13*\3+\3+\3+\5+\u0333\n"+
		"+\3,\3,\3,\3,\3,\3,\3,\5,\u033c\n,\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\5-\u0348"+
		"\n-\3.\3.\3.\3.\3.\3.\5.\u0350\n.\3/\3/\5/\u0354\n/\3\60\3\60\3\60\3\60"+
		"\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\5\60\u0364\n\60\3\61"+
		"\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61"+
		"\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61"+
		"\3\61\3\61\3\61\3\61\3\61\3\61\5\61\u0389\n\61\3\62\3\62\3\62\3\62\3\62"+
		"\3\62\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64"+
		"\7\64\u039e\n\64\f\64\16\64\u03a1\13\64\3\64\3\64\3\65\3\65\3\65\5\65"+
		"\u03a8\n\65\3\65\3\65\3\65\3\66\3\66\3\67\3\67\38\38\38\58\u03b4\n8\3"+
		"8\38\38\78\u03b9\n8\f8\168\u03bc\138\38\2\22\6\n\22\32\34 \"&.@BHJNPn"+
		"9\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDF"+
		"HJLNPRTVXZ\\^`bdfhjln\2\7\4\2\678??\3\2\24\27\4\2\33\36;<\4\2\31\32\66"+
		"\66\3\2%(\u040c\2t\3\2\2\2\4v\3\2\2\2\6{\3\2\2\2\b\u00d4\3\2\2\2\n\u00d6"+
		"\3\2\2\2\f\u00e2\3\2\2\2\16\u00ed\3\2\2\2\20\u00f8\3\2\2\2\22\u0100\3"+
		"\2\2\2\24\u010d\3\2\2\2\26\u015b\3\2\2\2\30\u0160\3\2\2\2\32\u0166\3\2"+
		"\2\2\34\u0171\3\2\2\2\36\u01c6\3\2\2\2 \u01cb\3\2\2\2\"\u01d9\3\2\2\2"+
		"$\u01e4\3\2\2\2&\u01e6\3\2\2\2(\u01f3\3\2\2\2*\u01f8\3\2\2\2,\u021a\3"+
		"\2\2\2.\u0223\3\2\2\2\60\u0232\3\2\2\2\62\u0234\3\2\2\2\64\u0239\3\2\2"+
		"\2\66\u024b\3\2\2\28\u024d\3\2\2\2:\u0255\3\2\2\2<\u025b\3\2\2\2>\u025d"+
		"\3\2\2\2@\u0264\3\2\2\2B\u0276\3\2\2\2D\u0280\3\2\2\2F\u0290\3\2\2\2H"+
		"\u029c\3\2\2\2J\u02a6\3\2\2\2L\u02ba\3\2\2\2N\u02e5\3\2\2\2P\u031a\3\2"+
		"\2\2R\u0327\3\2\2\2T\u0332\3\2\2\2V\u033b\3\2\2\2X\u0347\3\2\2\2Z\u034f"+
		"\3\2\2\2\\\u0353\3\2\2\2^\u0363\3\2\2\2`\u0388\3\2\2\2b\u038a\3\2\2\2"+
		"d\u0390\3\2\2\2f\u0397\3\2\2\2h\u03a7\3\2\2\2j\u03ac\3\2\2\2l\u03ae\3"+
		"\2\2\2n\u03b3\3\2\2\2pq\5\6\4\2qr\5\4\3\2ru\3\2\2\2su\5\6\4\2tp\3\2\2"+
		"\2ts\3\2\2\2u\3\3\2\2\2vw\5F$\2w\5\3\2\2\2xy\b\4\1\2y|\5\b\5\2z|\3\2\2"+
		"\2{x\3\2\2\2{z\3\2\2\2|\u0081\3\2\2\2}~\f\4\2\2~\u0080\5\b\5\2\177}\3"+
		"\2\2\2\u0080\u0083\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082"+
		"\7\3\2\2\2\u0083\u0081\3\2\2\2\u0084\u0085\7\3\2\2\u0085\u0086\7?\2\2"+
		"\u0086\u0087\7\4\2\2\u0087\u0088\5\22\n\2\u0088\u0089\7\5\2\2\u0089\u00d5"+
		"\3\2\2\2\u008a\u008b\7\6\2\2\u008b\u008c\7?\2\2\u008c\u0090\5\f\7\2\u008d"+
		"\u008f\5\n\6\2\u008e\u008d\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3\2"+
		"\2\2\u0090\u0091\3\2\2\2\u0091\u0093\3\2\2\2\u0092\u0090\3\2\2\2\u0093"+
		"\u0094\7\4\2\2\u0094\u0095\5\"\22\2\u0095\u0096\7\5\2\2\u0096\u00d5\3"+
		"\2\2\2\u0097\u0098\7\6\2\2\u0098\u0099\7?\2\2\u0099\u009a\5\f\7\2\u009a"+
		"\u009b\7\7\2\2\u009b\u009f\7?\2\2\u009c\u009e\5\n\6\2\u009d\u009c\3\2"+
		"\2\2\u009e\u00a1\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0"+
		"\u00a2\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2\u00a3\7\4\2\2\u00a3\u00a4\5\""+
		"\22\2\u00a4\u00a5\7\5\2\2\u00a5\u00d5\3\2\2\2\u00a6\u00a7\7\6\2\2\u00a7"+
		"\u00ab\7?\2\2\u00a8\u00aa\5\n\6\2\u00a9\u00a8\3\2\2\2\u00aa\u00ad\3\2"+
		"\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ae\3\2\2\2\u00ad"+
		"\u00ab\3\2\2\2\u00ae\u00af\7\4\2\2\u00af\u00b0\5\"\22\2\u00b0\u00b1\7"+
		"\5\2\2\u00b1\u00d5\3\2\2\2\u00b2\u00b3\7\6\2\2\u00b3\u00b4\7?\2\2\u00b4"+
		"\u00b5\7\7\2\2\u00b5\u00b9\7?\2\2\u00b6\u00b8\5\n\6\2\u00b7\u00b6\3\2"+
		"\2\2\u00b8\u00bb\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba"+
		"\u00bc\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bc\u00bd\7\4\2\2\u00bd\u00be\5\""+
		"\22\2\u00be\u00bf\7\5\2\2\u00bf\u00d5\3\2\2\2\u00c0\u00c1\7\6\2\2\u00c1"+
		"\u00c5\7?\2\2\u00c2\u00c4\5\n\6\2\u00c3\u00c2\3\2\2\2\u00c4\u00c7\3\2"+
		"\2\2\u00c5\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c8\3\2\2\2\u00c7"+
		"\u00c5\3\2\2\2\u00c8\u00c9\7\7\2\2\u00c9\u00ca\7?\2\2\u00ca\u00cb\7\4"+
		"\2\2\u00cb\u00cc\5\"\22\2\u00cc\u00cd\7\5\2\2\u00cd\u00d5\3\2\2\2\u00ce"+
		"\u00cf\7\b\2\2\u00cf\u00d0\7?\2\2\u00d0\u00d1\7\4\2\2\u00d1\u00d2\5&\24"+
		"\2\u00d2\u00d3\7\5\2\2\u00d3\u00d5\3\2\2\2\u00d4\u0084\3\2\2\2\u00d4\u008a"+
		"\3\2\2\2\u00d4\u0097\3\2\2\2\u00d4\u00a6\3\2\2\2\u00d4\u00b2\3\2\2\2\u00d4"+
		"\u00c0\3\2\2\2\u00d4\u00ce\3\2\2\2\u00d5\t\3\2\2\2\u00d6\u00d7\b\6\1\2"+
		"\u00d7\u00d8\7\t\2\2\u00d8\u00d9\7?\2\2\u00d9\u00df\3\2\2\2\u00da\u00db"+
		"\f\3\2\2\u00db\u00dc\7\n\2\2\u00dc\u00de\7?\2\2\u00dd\u00da\3\2\2\2\u00de"+
		"\u00e1\3\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\13\3\2\2"+
		"\2\u00e1\u00df\3\2\2\2\u00e2\u00e3\7;\2\2\u00e3\u00e8\5\20\t\2\u00e4\u00e5"+
		"\7\n\2\2\u00e5\u00e7\5\20\t\2\u00e6\u00e4\3\2\2\2\u00e7\u00ea\3\2\2\2"+
		"\u00e8\u00e6\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00eb\3\2\2\2\u00ea\u00e8"+
		"\3\2\2\2\u00eb\u00ec\7<\2\2\u00ec\r\3\2\2\2\u00ed\u00ee\7;\2\2\u00ee\u00f3"+
		"\5<\37\2\u00ef\u00f0\7\n\2\2\u00f0\u00f2\5<\37\2\u00f1\u00ef\3\2\2\2\u00f2"+
		"\u00f5\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f6\3\2"+
		"\2\2\u00f5\u00f3\3\2\2\2\u00f6\u00f7\7<\2\2\u00f7\17\3\2\2\2\u00f8\u00fb"+
		"\5<\37\2\u00f9\u00fa\7\7\2\2\u00fa\u00fc\5<\37\2\u00fb\u00f9\3\2\2\2\u00fb"+
		"\u00fc\3\2\2\2\u00fc\21\3\2\2\2\u00fd\u00fe\b\n\1\2\u00fe\u0101\5\24\13"+
		"\2\u00ff\u0101\3\2\2\2\u0100\u00fd\3\2\2\2\u0100\u00ff\3\2\2\2\u0101\u0106"+
		"\3\2\2\2\u0102\u0103\f\5\2\2\u0103\u0105\5\24\13\2\u0104\u0102\3\2\2\2"+
		"\u0105\u0108\3\2\2\2\u0106\u0104\3\2\2\2\u0106\u0107\3\2\2\2\u0107\23"+
		"\3\2\2\2\u0108\u0106\3\2\2\2\u0109\u010e\5(\25\2\u010a\u010e\5\66\34\2"+
		"\u010b\u010e\5\26\f\2\u010c\u010e\5\36\20\2\u010d\u0109\3\2\2\2\u010d"+
		"\u010a\3\2\2\2\u010d\u010b\3\2\2\2\u010d\u010c\3\2\2\2\u010e\25\3\2\2"+
		"\2\u010f\u0110\7\13\2\2\u0110\u0111\5\30\r\2\u0111\u0112\7?\2\2\u0112"+
		"\u0113\7\4\2\2\u0113\u0114\5\32\16\2\u0114\u0115\7\5\2\2\u0115\u015c\3"+
		"\2\2\2\u0116\u0117\7\13\2\2\u0117\u0118\5\30\r\2\u0118\u0119\7?\2\2\u0119"+
		"\u011a\7\4\2\2\u011a\u011b\5\32\16\2\u011b\u011c\7\5\2\2\u011c\u011d\7"+
		"/\2\2\u011d\u011e\7\4\2\2\u011e\u011f\5\34\17\2\u011f\u0120\7\5\2\2\u0120"+
		"\u015c\3\2\2\2\u0121\u0122\5\64\33\2\u0122\u0123\7\13\2\2\u0123\u0124"+
		"\5\30\r\2\u0124\u0125\7?\2\2\u0125\u0126\7\4\2\2\u0126\u0127\5\32\16\2"+
		"\u0127\u0128\7\5\2\2\u0128\u015c\3\2\2\2\u0129\u012a\5\64\33\2\u012a\u012b"+
		"\7\13\2\2\u012b\u012c\5\30\r\2\u012c\u012d\7?\2\2\u012d\u012e\7\4\2\2"+
		"\u012e\u012f\5\32\16\2\u012f\u0130\7\5\2\2\u0130\u0131\7/\2\2\u0131\u0132"+
		"\7\4\2\2\u0132\u0133\5\34\17\2\u0133\u0134\7\5\2\2\u0134\u015c\3\2\2\2"+
		"\u0135\u0136\7\13\2\2\u0136\u0137\5\30\r\2\u0137\u0138\7?\2\2\u0138\u0139"+
		"\7\4\2\2\u0139\u013a\5\32\16\2\u013a\u013b\7\5\2\2\u013b\u015c\3\2\2\2"+
		"\u013c\u013d\7\13\2\2\u013d\u013e\5\30\r\2\u013e\u013f\7?\2\2\u013f\u0140"+
		"\7\4\2\2\u0140\u0141\5\32\16\2\u0141\u0142\7\5\2\2\u0142\u0143\7/\2\2"+
		"\u0143\u0144\7\4\2\2\u0144\u0145\5\34\17\2\u0145\u0146\7\5\2\2\u0146\u015c"+
		"\3\2\2\2\u0147\u0148\5\64\33\2\u0148\u0149\7\13\2\2\u0149\u014a\5\30\r"+
		"\2\u014a\u014b\7?\2\2\u014b\u014c\7\4\2\2\u014c\u014d\5\32\16\2\u014d"+
		"\u014e\7\5\2\2\u014e\u015c\3\2\2\2\u014f\u0150\5\64\33\2\u0150\u0151\7"+
		"\13\2\2\u0151\u0152\5\30\r\2\u0152\u0153\7?\2\2\u0153\u0154\7\4\2\2\u0154"+
		"\u0155\5\32\16\2\u0155\u0156\7\5\2\2\u0156\u0157\7/\2\2\u0157\u0158\7"+
		"\4\2\2\u0158\u0159\5\34\17\2\u0159\u015a\7\5\2\2\u015a\u015c\3\2\2\2\u015b"+
		"\u010f\3\2\2\2\u015b\u0116\3\2\2\2\u015b\u0121\3\2\2\2\u015b\u0129\3\2"+
		"\2\2\u015b\u0135\3\2\2\2\u015b\u013c\3\2\2\2\u015b\u0147\3\2\2\2\u015b"+
		"\u014f\3\2\2\2\u015c\27\3\2\2\2\u015d\u015e\7\f\2\2\u015e\u0161\7\r\2"+
		"\2\u015f\u0161\3\2\2\2\u0160\u015d\3\2\2\2\u0160\u015f\3\2\2\2\u0161\31"+
		"\3\2\2\2\u0162\u0163\b\16\1\2\u0163\u0167\5(\25\2\u0164\u0167\5\66\34"+
		"\2\u0165\u0167\3\2\2\2\u0166\u0162\3\2\2\2\u0166\u0164\3\2\2\2\u0166\u0165"+
		"\3\2\2\2\u0167\u016e\3\2\2\2\u0168\u0169\f\6\2\2\u0169\u016d\5(\25\2\u016a"+
		"\u016b\f\4\2\2\u016b\u016d\5\66\34\2\u016c\u0168\3\2\2\2\u016c\u016a\3"+
		"\2\2\2\u016d\u0170\3\2\2\2\u016e\u016c\3\2\2\2\u016e\u016f\3\2\2\2\u016f"+
		"\33\3\2\2\2\u0170\u016e\3\2\2\2\u0171\u0172\b\17\1\2\u0172\u0173\5,\27"+
		"\2\u0173\u017b\3\2\2\2\u0174\u0175\f\5\2\2\u0175\u0176\7\16\2\2\u0176"+
		"\u017a\5,\27\2\u0177\u0178\f\3\2\2\u0178\u017a\7\16\2\2\u0179\u0174\3"+
		"\2\2\2\u0179\u0177\3\2\2\2\u017a\u017d\3\2\2\2\u017b\u0179\3\2\2\2\u017b"+
		"\u017c\3\2\2\2\u017c\35\3\2\2\2\u017d\u017b\3\2\2\2\u017e\u017f\7\17\2"+
		"\2\u017f\u0180\5\30\r\2\u0180\u0181\7?\2\2\u0181\u0182\7\4\2\2\u0182\u0183"+
		"\5 \21\2\u0183\u0184\7\5\2\2\u0184\u01c7\3\2\2\2\u0185\u0186\7\17\2\2"+
		"\u0186\u0187\5\30\r\2\u0187\u0188\7?\2\2\u0188\u0189\7\4\2\2\u0189\u018a"+
		"\5 \21\2\u018a\u018b\7\5\2\2\u018b\u018c\7/\2\2\u018c\u018d\7\4\2\2\u018d"+
		"\u018e\5\34\17\2\u018e\u018f\7\5\2\2\u018f\u01c7\3\2\2\2\u0190\u0191\5"+
		"\64\33\2\u0191\u0192\7\17\2\2\u0192\u0193\5\30\r\2\u0193\u0194\7?\2\2"+
		"\u0194\u0195\7\4\2\2\u0195\u0196\5 \21\2\u0196\u0197\7\5\2\2\u0197\u01c7"+
		"\3\2\2\2\u0198\u0199\5\64\33\2\u0199\u019a\7\17\2\2\u019a\u019b\5\30\r"+
		"\2\u019b\u019c\7?\2\2\u019c\u019d\7\4\2\2\u019d\u019e\5 \21\2\u019e\u019f"+
		"\7\5\2\2\u019f\u01a0\7/\2\2\u01a0\u01a1\7\4\2\2\u01a1\u01a2\5\34\17\2"+
		"\u01a2\u01a3\7\5\2\2\u01a3\u01c7\3\2\2\2\u01a4\u01a5\7\17\2\2\u01a5\u01a6"+
		"\5\30\r\2\u01a6\u01a7\7?\2\2\u01a7\u01a8\7\4\2\2\u01a8\u01a9\7\5\2\2\u01a9"+
		"\u01c7\3\2\2\2\u01aa\u01ab\7\17\2\2\u01ab\u01ac\5\30\r\2\u01ac\u01ad\7"+
		"?\2\2\u01ad\u01ae\7\4\2\2\u01ae\u01af\7\5\2\2\u01af\u01b0\7/\2\2\u01b0"+
		"\u01b1\7\4\2\2\u01b1\u01b2\5\34\17\2\u01b2\u01b3\7\5\2\2\u01b3\u01c7\3"+
		"\2\2\2\u01b4\u01b5\5\64\33\2\u01b5\u01b6\7\17\2\2\u01b6\u01b7\5\30\r\2"+
		"\u01b7\u01b8\7?\2\2\u01b8\u01b9\7\4\2\2\u01b9\u01ba\7\5\2\2\u01ba\u01c7"+
		"\3\2\2\2\u01bb\u01bc\5\64\33\2\u01bc\u01bd\7\17\2\2\u01bd\u01be\5\30\r"+
		"\2\u01be\u01bf\7?\2\2\u01bf\u01c0\7\4\2\2\u01c0\u01c1\7\5\2\2\u01c1\u01c2"+
		"\7/\2\2\u01c2\u01c3\7\4\2\2\u01c3\u01c4\5\34\17\2\u01c4\u01c5\7\5\2\2"+
		"\u01c5\u01c7\3\2\2\2\u01c6\u017e\3\2\2\2\u01c6\u0185\3\2\2\2\u01c6\u0190"+
		"\3\2\2\2\u01c6\u0198\3\2\2\2\u01c6\u01a4\3\2\2\2\u01c6\u01aa\3\2\2\2\u01c6"+
		"\u01b4\3\2\2\2\u01c6\u01bb\3\2\2\2\u01c7\37\3\2\2\2\u01c8\u01c9\b\21\1"+
		"\2\u01c9\u01cc\5(\25\2\u01ca\u01cc\5\66\34\2\u01cb\u01c8\3\2\2\2\u01cb"+
		"\u01ca\3\2\2\2\u01cc\u01d3\3\2\2\2\u01cd\u01ce\f\5\2\2\u01ce\u01d2\5("+
		"\25\2\u01cf\u01d0\f\3\2\2\u01d0\u01d2\5\66\34\2\u01d1\u01cd\3\2\2\2\u01d1"+
		"\u01cf\3\2\2\2\u01d2\u01d5\3\2\2\2\u01d3\u01d1\3\2\2\2\u01d3\u01d4\3\2"+
		"\2\2\u01d4!\3\2\2\2\u01d5\u01d3\3\2\2\2\u01d6\u01d7\b\22\1\2\u01d7\u01da"+
		"\5$\23\2\u01d8\u01da\3\2\2\2\u01d9\u01d6\3\2\2\2\u01d9\u01d8\3\2\2\2\u01da"+
		"\u01df\3\2\2\2\u01db\u01dc\f\5\2\2\u01dc\u01de\5$\23\2\u01dd\u01db\3\2"+
		"\2\2\u01de\u01e1\3\2\2\2\u01df\u01dd\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0"+
		"#\3\2\2\2\u01e1\u01df\3\2\2\2\u01e2\u01e5\5(\25\2\u01e3\u01e5\5\66\34"+
		"\2\u01e4\u01e2\3\2\2\2\u01e4\u01e3\3\2\2\2\u01e5%\3\2\2\2\u01e6\u01e7"+
		"\b\24\1\2\u01e7\u01e8\5,\27\2\u01e8\u01f0\3\2\2\2\u01e9\u01ea\f\5\2\2"+
		"\u01ea\u01eb\7\16\2\2\u01eb\u01ef\5,\27\2\u01ec\u01ed\f\3\2\2\u01ed\u01ef"+
		"\7\16\2\2\u01ee\u01e9\3\2\2\2\u01ee\u01ec\3\2\2\2\u01ef\u01f2\3\2\2\2"+
		"\u01f0\u01ee\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1\'\3\2\2\2\u01f2\u01f0\3"+
		"\2\2\2\u01f3\u01f4\5*\26\2\u01f4\u01f5\7\4\2\2\u01f5\u01f6\5.\30\2\u01f6"+
		"\u01f7\7\5\2\2\u01f7)\3\2\2\2\u01f8\u01f9\5,\27\2\u01f9+\3\2\2\2\u01fa"+
		"\u01fb\5\64\33\2\u01fb\u01fc\5\60\31\2\u01fc\u01fd\5\62\32\2\u01fd\u01fe"+
		"\7\20\2\2\u01fe\u01ff\5B\"\2\u01ff\u0203\7\21\2\2\u0200\u0202\7\63\2\2"+
		"\u0201\u0200\3\2\2\2\u0202\u0205\3\2\2\2\u0203\u0201\3\2\2\2\u0203\u0204"+
		"\3\2\2\2\u0204\u021b\3\2\2\2\u0205\u0203\3\2\2\2\u0206\u0207\5\64\33\2"+
		"\u0207\u0208\5\60\31\2\u0208\u020c\5\62\32\2\u0209\u020b\7\63\2\2\u020a"+
		"\u0209\3\2\2\2\u020b\u020e\3\2\2\2\u020c\u020a\3\2\2\2\u020c\u020d\3\2"+
		"\2\2\u020d\u021b\3\2\2\2\u020e\u020c\3\2\2\2\u020f\u0210\5\64\33\2\u0210"+
		"\u0211\5\62\32\2\u0211\u0212\7\20\2\2\u0212\u0213\5B\"\2\u0213\u0217\7"+
		"\21\2\2\u0214\u0216\7\63\2\2\u0215\u0214\3\2\2\2\u0216\u0219\3\2\2\2\u0217"+
		"\u0215\3\2\2\2\u0217\u0218\3\2\2\2\u0218\u021b\3\2\2\2\u0219\u0217\3\2"+
		"\2\2\u021a\u01fa\3\2\2\2\u021a\u0206\3\2\2\2\u021a\u020f\3\2\2\2\u021b"+
		"-\3\2\2\2\u021c\u021d\b\30\1\2\u021d\u0224\5\66\34\2\u021e\u021f\5F$\2"+
		"\u021f\u0220\7\16\2\2\u0220\u0221\5\66\34\2\u0221\u0224\3\2\2\2\u0222"+
		"\u0224\3\2\2\2\u0223\u021c\3\2\2\2\u0223\u021e\3\2\2\2\u0223\u0222\3\2"+
		"\2\2\u0224\u022d\3\2\2\2\u0225\u0226\f\6\2\2\u0226\u022c\5\66\34\2\u0227"+
		"\u0228\f\5\2\2\u0228\u022c\5F$\2\u0229\u022a\f\4\2\2\u022a\u022c\7\16"+
		"\2\2\u022b\u0225\3\2\2\2\u022b\u0227\3\2\2\2\u022b\u0229\3\2\2\2\u022c"+
		"\u022f\3\2\2\2\u022d\u022b\3\2\2\2\u022d\u022e\3\2\2\2\u022e/\3\2\2\2"+
		"\u022f\u022d\3\2\2\2\u0230\u0233\5<\37\2\u0231\u0233\3\2\2\2\u0232\u0230"+
		"\3\2\2\2\u0232\u0231\3\2\2\2\u0233\61\3\2\2\2\u0234\u0235\t\2\2\2\u0235"+
		"\63\3\2\2\2\u0236\u023a\7\22\2\2\u0237\u023a\7\23\2\2\u0238\u023a\3\2"+
		"\2\2\u0239\u0236\3\2\2\2\u0239\u0237\3\2\2\2\u0239\u0238\3\2\2\2\u023a"+
		"\65\3\2\2\2\u023b\u023c\5\64\33\2\u023c\u023d\5:\36\2\u023d\u023e\5@!"+
		"\2\u023e\u023f\7\16\2\2\u023f\u024c\3\2\2\2\u0240\u0241\5\64\33\2\u0241"+
		"\u0242\5:\36\2\u0242\u0243\5@!\2\u0243\u024c\3\2\2\2\u0244\u0245\5:\36"+
		"\2\u0245\u0246\5@!\2\u0246\u0247\7\16\2\2\u0247\u024c\3\2\2\2\u0248\u0249"+
		"\5:\36\2\u0249\u024a\5@!\2\u024a\u024c\3\2\2\2\u024b\u023b\3\2\2\2\u024b"+
		"\u0240\3\2\2\2\u024b\u0244\3\2\2\2\u024b\u0248\3\2\2\2\u024c\67\3\2\2"+
		"\2\u024d\u024e\5:\36\2\u024e\u024f\7?\2\2\u024f9\3\2\2\2\u0250\u0251\5"+
		"<\37\2\u0251\u0252\7\f\2\2\u0252\u0253\7\r\2\2\u0253\u0256\3\2\2\2\u0254"+
		"\u0256\5<\37\2\u0255\u0250\3\2\2\2\u0255\u0254\3\2\2\2\u0256;\3\2\2\2"+
		"\u0257\u025c\7?\2\2\u0258\u0259\7?\2\2\u0259\u025c\5\16\b\2\u025a\u025c"+
		"\5> \2\u025b\u0257\3\2\2\2\u025b\u0258\3\2\2\2\u025b\u025a\3\2\2\2\u025c"+
		"=\3\2\2\2\u025d\u025e\t\3\2\2\u025e?\3\2\2\2\u025f\u0260\b!\1\2\u0260"+
		"\u0265\7?\2\2\u0261\u0262\7?\2\2\u0262\u0263\7C\2\2\u0263\u0265\5F$\2"+
		"\u0264\u025f\3\2\2\2\u0264\u0261\3\2\2\2\u0265\u0270\3\2\2\2\u0266\u0267"+
		"\f\5\2\2\u0267\u0268\7\n\2\2\u0268\u026f\7?\2\2\u0269\u026a\f\3\2\2\u026a"+
		"\u026b\7\n\2\2\u026b\u026c\7?\2\2\u026c\u026d\7C\2\2\u026d\u026f\5F$\2"+
		"\u026e\u0266\3\2\2\2\u026e\u0269\3\2\2\2\u026f\u0272\3\2\2\2\u0270\u026e"+
		"\3\2\2\2\u0270\u0271\3\2\2\2\u0271A\3\2\2\2\u0272\u0270\3\2\2\2\u0273"+
		"\u0274\b\"\1\2\u0274\u0277\5D#\2\u0275\u0277\3\2\2\2\u0276\u0273\3\2\2"+
		"\2\u0276\u0275\3\2\2\2\u0277\u027d\3\2\2\2\u0278\u0279\f\4\2\2\u0279\u027a"+
		"\7\n\2\2\u027a\u027c\5D#\2\u027b\u0278\3\2\2\2\u027c\u027f\3\2\2\2\u027d"+
		"\u027b\3\2\2\2\u027d\u027e\3\2\2\2\u027eC\3\2\2\2\u027f\u027d\3\2\2\2"+
		"\u0280\u0281\5:\36\2\u0281\u0282\7?\2\2\u0282E\3\2\2\2\u0283\u0291\5H"+
		"%\2\u0284\u0291\5P)\2\u0285\u0291\5Z.\2\u0286\u0291\5^\60\2\u0287\u0291"+
		"\5`\61\2\u0288\u0291\5b\62\2\u0289\u0291\5d\63\2\u028a\u0291\5f\64\2\u028b"+
		"\u0291\7,\2\2\u028c\u0291\7-\2\2\u028d\u028e\7.\2\2\u028e\u0291\5F$\2"+
		"\u028f\u0291\7.\2\2\u0290\u0283\3\2\2\2\u0290\u0284\3\2\2\2\u0290\u0285"+
		"\3\2\2\2\u0290\u0286\3\2\2\2\u0290\u0287\3\2\2\2\u0290\u0288\3\2\2\2\u0290"+
		"\u0289\3\2\2\2\u0290\u028a\3\2\2\2\u0290\u028b\3\2\2\2\u0290\u028c\3\2"+
		"\2\2\u0290\u028d\3\2\2\2\u0290\u028f\3\2\2\2\u0291G\3\2\2\2\u0292\u0293"+
		"\b%\1\2\u0293\u0298\5J&\2\u0294\u0295\7\67\2\2\u0295\u0297\5J&\2\u0296"+
		"\u0294\3\2\2\2\u0297\u029a\3\2\2\2\u0298\u0296\3\2\2\2\u0298\u0299\3\2"+
		"\2\2\u0299\u029d\3\2\2\2\u029a\u0298\3\2\2\2\u029b\u029d\5^\60\2\u029c"+
		"\u0292\3\2\2\2\u029c\u029b\3\2\2\2\u029d\u02a3\3\2\2\2\u029e\u029f\f\3"+
		"\2\2\u029f\u02a0\7C\2\2\u02a0\u02a2\5F$\2\u02a1\u029e\3\2\2\2\u02a2\u02a5"+
		"\3\2\2\2\u02a3\u02a1\3\2\2\2\u02a3\u02a4\3\2\2\2\u02a4I\3\2\2\2\u02a5"+
		"\u02a3\3\2\2\2\u02a6\u02a7\b&\1\2\u02a7\u02ac\5L\'\2\u02a8\u02a9\78\2"+
		"\2\u02a9\u02ab\5L\'\2\u02aa\u02a8\3\2\2\2\u02ab\u02ae\3\2\2\2\u02ac\u02aa"+
		"\3\2\2\2\u02ac\u02ad\3\2\2\2\u02ad\u02b4\3\2\2\2\u02ae\u02ac\3\2\2\2\u02af"+
		"\u02b0\f\4\2\2\u02b0\u02b1\7\64\2\2\u02b1\u02b3\5N(\2\u02b2\u02af\3\2"+
		"\2\2\u02b3\u02b6\3\2\2\2\u02b4\u02b2\3\2\2\2\u02b4\u02b5\3\2\2\2\u02b5"+
		"K\3\2\2\2\u02b6\u02b4\3\2\2\2\u02b7\u02b8\7\67\2\2\u02b8\u02bb\5N(\2\u02b9"+
		"\u02bb\5N(\2\u02ba\u02b7\3\2\2\2\u02ba\u02b9\3\2\2\2\u02bbM\3\2\2\2\u02bc"+
		"\u02bd\b(\1\2\u02bd\u02be\7>\2\2\u02be\u02e6\5N(\f\u02bf\u02c0\7\60\2"+
		"\2\u02c0\u02e6\5X-\2\u02c1\u02c2\7\60\2\2\u02c2\u02c3\5<\37\2\u02c3\u02c4"+
		"\7\f\2\2\u02c4\u02c5\5F$\2\u02c5\u02c6\7\r\2\2\u02c6\u02e6\3\2\2\2\u02c7"+
		"\u02c8\7\60\2\2\u02c8\u02c9\7?\2\2\u02c9\u02ca\5\16\b\2\u02ca\u02cb\7"+
		"\20\2\2\u02cb\u02cc\5n8\2\u02cc\u02cd\7\21\2\2\u02cd\u02e6\3\2\2\2\u02ce"+
		"\u02cf\5> \2\u02cf\u02d0\7\30\2\2\u02d0\u02d1\5X-\2\u02d1\u02e6\3\2\2"+
		"\2\u02d2\u02e6\5j\66\2\u02d3\u02e6\5X-\2\u02d4\u02e6\7?\2\2\u02d5\u02e6"+
		"\5l\67\2\u02d6\u02d7\7\20\2\2\u02d7\u02d8\5H%\2\u02d8\u02d9\7\21\2\2\u02d9"+
		"\u02e6\3\2\2\2\u02da\u02db\7>\2\2\u02db\u02dc\5F$\2\u02dc\u02dd\7\f\2"+
		"\2\u02dd\u02de\5F$\2\u02de\u02df\7\r\2\2\u02df\u02e6\3\2\2\2\u02e0\u02e1"+
		"\7>\2\2\u02e1\u02e2\5F$\2\u02e2\u02e3\7\30\2\2\u02e3\u02e4\7?\2\2\u02e4"+
		"\u02e6\3\2\2\2\u02e5\u02bc\3\2\2\2\u02e5\u02bf\3\2\2\2\u02e5\u02c1\3\2"+
		"\2\2\u02e5\u02c7\3\2\2\2\u02e5\u02ce\3\2\2\2\u02e5\u02d2\3\2\2\2\u02e5"+
		"\u02d3\3\2\2\2\u02e5\u02d4\3\2\2\2\u02e5\u02d5\3\2\2\2\u02e5\u02d6\3\2"+
		"\2\2\u02e5\u02da\3\2\2\2\u02e5\u02e0\3\2\2\2\u02e6\u0308\3\2\2\2\u02e7"+
		"\u02e8\f\23\2\2\u02e8\u02e9\7\30\2\2\u02e9\u0307\5X-\2\u02ea\u02eb\f\21"+
		"\2\2\u02eb\u02ec\7\30\2\2\u02ec\u0307\7?\2\2\u02ed\u02ee\f\r\2\2\u02ee"+
		"\u0307\7>\2\2\u02ef\u02f0\f\t\2\2\u02f0\u02f1\7\f\2\2\u02f1\u02f2\5F$"+
		"\2\u02f2\u02f3\7\r\2\2\u02f3\u0307\3\2\2\2\u02f4\u02f5\f\b\2\2\u02f5\u02f6"+
		"\7\f\2\2\u02f6\u02f7\5F$\2\u02f7\u02f8\7\r\2\2\u02f8\u02f9\7>\2\2\u02f9"+
		"\u0307\3\2\2\2\u02fa\u02fb\f\5\2\2\u02fb\u02fc\7\30\2\2\u02fc\u02fd\7"+
		"?\2\2\u02fd\u0307\7>\2\2\u02fe\u02ff\f\4\2\2\u02ff\u0300\7\30\2\2\u0300"+
		"\u0307\7\61\2\2\u0301\u0302\f\3\2\2\u0302\u0303\7\30\2\2\u0303\u0304\7"+
		"\61\2\2\u0304\u0305\7\20\2\2\u0305\u0307\7\21\2\2\u0306\u02e7\3\2\2\2"+
		"\u0306\u02ea\3\2\2\2\u0306\u02ed\3\2\2\2\u0306\u02ef\3\2\2\2\u0306\u02f4"+
		"\3\2\2\2\u0306\u02fa\3\2\2\2\u0306\u02fe\3\2\2\2\u0306\u0301\3\2\2\2\u0307"+
		"\u030a\3\2\2\2\u0308\u0306\3\2\2\2\u0308\u0309\3\2\2\2\u0309O\3\2\2\2"+
		"\u030a\u0308\3\2\2\2\u030b\u030c\b)\1\2\u030c\u0311\5R*\2\u030d\u030e"+
		"\7\65\2\2\u030e\u0310\5R*\2\u030f\u030d\3\2\2\2\u0310\u0313\3\2\2\2\u0311"+
		"\u030f\3\2\2\2\u0311\u0312\3\2\2\2\u0312\u031b\3\2\2\2\u0313\u0311\3\2"+
		"\2\2\u0314\u031b\5^\60\2\u0315\u0316\5H%\2\u0316\u0317\t\4\2\2\u0317\u0318"+
		"\5H%\2\u0318\u031b\3\2\2\2\u0319\u031b\5H%\2\u031a\u030b\3\2\2\2\u031a"+
		"\u0314\3\2\2\2\u031a\u0315\3\2\2\2\u031a\u0319\3\2\2\2\u031b\u0324\3\2"+
		"\2\2\u031c\u031d\f\6\2\2\u031d\u031e\t\5\2\2\u031e\u0323\5P)\7\u031f\u0320"+
		"\f\4\2\2\u0320\u0321\7C\2\2\u0321\u0323\5F$\2\u0322\u031c\3\2\2\2\u0322"+
		"\u031f\3\2\2\2\u0323\u0326\3\2\2\2\u0324\u0322\3\2\2\2\u0324\u0325\3\2"+
		"\2\2\u0325Q\3\2\2\2\u0326\u0324\3\2\2\2\u0327\u032c\5T+\2\u0328\u0329"+
		"\7\66\2\2\u0329\u032b\5T+\2\u032a\u0328\3\2\2\2\u032b\u032e\3\2\2\2\u032c"+
		"\u032a\3\2\2\2\u032c\u032d\3\2\2\2\u032dS\3\2\2\2\u032e\u032c\3\2\2\2"+
		"\u032f\u0330\7=\2\2\u0330\u0333\5P)\2\u0331\u0333\5V,\2\u0332\u032f\3"+
		"\2\2\2\u0332\u0331\3\2\2\2\u0333U\3\2\2\2\u0334\u033c\5l\67\2\u0335\u033c"+
		"\7?\2\2\u0336\u033c\5j\66\2\u0337\u0338\7\20\2\2\u0338\u0339\5P)\2\u0339"+
		"\u033a\7\21\2\2\u033a\u033c\3\2\2\2\u033b\u0334\3\2\2\2\u033b\u0335\3"+
		"\2\2\2\u033b\u0336\3\2\2\2\u033b\u0337\3\2\2\2\u033cW\3\2\2\2\u033d\u033e"+
		"\5\62\32\2\u033e\u033f\7\20\2\2\u033f\u0340\5n8\2\u0340\u0341\7\21\2\2"+
		"\u0341\u0348\3\2\2\2\u0342\u0343\5<\37\2\u0343\u0344\7\20\2\2\u0344\u0345"+
		"\5n8\2\u0345\u0346\7\21\2\2\u0346\u0348\3\2\2\2\u0347\u033d\3\2\2\2\u0347"+
		"\u0342\3\2\2\2\u0348Y\3\2\2\2\u0349\u034a\7\4\2\2\u034a\u034b\5.\30\2"+
		"\u034b\u034c\7\5\2\2\u034c\u0350\3\2\2\2\u034d\u034e\7\4\2\2\u034e\u0350"+
		"\7\5\2\2\u034f\u0349\3\2\2\2\u034f\u034d\3\2\2\2\u0350[\3\2\2\2\u0351"+
		"\u0354\5F$\2\u0352\u0354\3\2\2\2\u0353\u0351\3\2\2\2\u0353\u0352\3\2\2"+
		"\2\u0354]\3\2\2\2\u0355\u0356\7\37\2\2\u0356\u0357\7\20\2\2\u0357\u0358"+
		"\5F$\2\u0358\u0359\7\21\2\2\u0359\u035a\5F$\2\u035a\u0364\3\2\2\2\u035b"+
		"\u035c\7\37\2\2\u035c\u035d\7\20\2\2\u035d\u035e\5F$\2\u035e\u035f\7\21"+
		"\2\2\u035f\u0360\5F$\2\u0360\u0361\7 \2\2\u0361\u0362\5F$\2\u0362\u0364"+
		"\3\2\2\2\u0363\u0355\3\2\2\2\u0363\u035b\3\2\2\2\u0364_\3\2\2\2\u0365"+
		"\u0366\7!\2\2\u0366\u0367\7\20\2\2\u0367\u0368\5F$\2\u0368\u0369\7\16"+
		"\2\2\u0369\u036a\5F$\2\u036a\u036b\7\16\2\2\u036b\u036c\5F$\2\u036c\u036d"+
		"\7\21\2\2\u036d\u036e\5F$\2\u036e\u0389\3\2\2\2\u036f\u0370\7!\2\2\u0370"+
		"\u0371\7\20\2\2\u0371\u0372\5\66\34\2\u0372\u0373\5F$\2\u0373\u0374\7"+
		"\16\2\2\u0374\u0375\5F$\2\u0375\u0376\7\21\2\2\u0376\u0377\5F$\2\u0377"+
		"\u0389\3\2\2\2\u0378\u0379\7!\2\2\u0379\u037a\7\20\2\2\u037a\u037b\7?"+
		"\2\2\u037b\u037c\7\"\2\2\u037c\u037d\5F$\2\u037d\u037e\7\21\2\2\u037e"+
		"\u037f\5F$\2\u037f\u0389\3\2\2\2\u0380\u0381\7!\2\2\u0381\u0382\7\20\2"+
		"\2\u0382\u0383\58\35\2\u0383\u0384\7\"\2\2\u0384\u0385\5F$\2\u0385\u0386"+
		"\7\21\2\2\u0386\u0387\5F$\2\u0387\u0389\3\2\2\2\u0388\u0365\3\2\2\2\u0388"+
		"\u036f\3\2\2\2\u0388\u0378\3\2\2\2\u0388\u0380\3\2\2\2\u0389a\3\2\2\2"+
		"\u038a\u038b\7#\2\2\u038b\u038c\7\20\2\2\u038c\u038d\5F$\2\u038d\u038e"+
		"\7\21\2\2\u038e\u038f\5F$\2\u038fc\3\2\2\2\u0390\u0391\7$\2\2\u0391\u0392"+
		"\5F$\2\u0392\u0393\7#\2\2\u0393\u0394\7\20\2\2\u0394\u0395\5F$\2\u0395"+
		"\u0396\7\21\2\2\u0396e\3\2\2\2\u0397\u0398\7)\2\2\u0398\u0399\7\20\2\2"+
		"\u0399\u039a\5F$\2\u039a\u039b\7\21\2\2\u039b\u039f\7\4\2\2\u039c\u039e"+
		"\5h\65\2\u039d\u039c\3\2\2\2\u039e\u03a1\3\2\2\2\u039f\u039d\3\2\2\2\u039f"+
		"\u03a0\3\2\2\2\u03a0\u03a2\3\2\2\2\u03a1\u039f\3\2\2\2\u03a2\u03a3\7\5"+
		"\2\2\u03a3g\3\2\2\2\u03a4\u03a5\7*\2\2\u03a5\u03a8\5l\67\2\u03a6\u03a8"+
		"\7+\2\2\u03a7\u03a4\3\2\2\2\u03a7\u03a6\3\2\2\2\u03a8\u03a9\3\2\2\2\u03a9"+
		"\u03aa\7\"\2\2\u03aa\u03ab\5.\30\2\u03abi\3\2\2\2\u03ac\u03ad\7\62\2\2"+
		"\u03adk\3\2\2\2\u03ae\u03af\t\6\2\2\u03afm\3\2\2\2\u03b0\u03b1\b8\1\2"+
		"\u03b1\u03b4\5F$\2\u03b2\u03b4\3\2\2\2\u03b3\u03b0\3\2\2\2\u03b3\u03b2"+
		"\3\2\2\2\u03b4\u03ba\3\2\2\2\u03b5\u03b6\f\4\2\2\u03b6\u03b7\7\n\2\2\u03b7"+
		"\u03b9\5F$\2\u03b8\u03b5\3\2\2\2\u03b9\u03bc\3\2\2\2\u03ba\u03b8\3\2\2"+
		"\2\u03ba\u03bb\3\2\2\2\u03bbo\3\2\2\2\u03bc\u03ba\3\2\2\2Mt{\u0081\u0090"+
		"\u009f\u00ab\u00b9\u00c5\u00d4\u00df\u00e8\u00f3\u00fb\u0100\u0106\u010d"+
		"\u015b\u0160\u0166\u016c\u016e\u0179\u017b\u01c6\u01cb\u01d1\u01d3\u01d9"+
		"\u01df\u01e4\u01ee\u01f0\u0203\u020c\u0217\u021a\u0223\u022b\u022d\u0232"+
		"\u0239\u024b\u0255\u025b\u0264\u026e\u0270\u0276\u027d\u0290\u0298\u029c"+
		"\u02a3\u02ac\u02b4\u02ba\u02e5\u0306\u0308\u0311\u031a\u0322\u0324\u032c"+
		"\u0332\u033b\u0347\u034f\u0353\u0363\u0388\u039f\u03a7\u03b3\u03ba";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}