// Generated from Kant.g4 by ANTLR 4.5.1
package info.fulloo.trygve.parser;

    // package info.fulloo.trygve.parser;

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
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, STRING=41, INTEGER=42, FLOAT=43, BOOLEAN=44, SWITCH=45, 
		CASE=46, DEFAULT=47, BREAK=48, CONTINUE=49, RETURN=50, REQUIRES=51, NEW=52, 
		CLONE=53, NULL=54, CONST=55, POW=56, ELLIPSIS=57, BOOLEAN_SUMOP=58, BOOLEAN_MULOP=59, 
		ABELIAN_SUMOP=60, ABELIAN_MULOP=61, MINUS=62, PLUS=63, LT=64, GT=65, LOGICAL_NEGATION=66, 
		ABELIAN_INCREMENT_OP=67, JAVA_ID=68, INLINE_COMMENT=69, C_COMMENT=70, 
		WHITESPACE=71, ASSIGN=72;
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
	public static final String[] ruleNames = {
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

	private static final String[] _LITERAL_NAMES = {
		null, "'context'", "'{'", "'}'", "'class'", "'extends'", "'interface'", 
		"'implements'", "','", "'role'", "'['", "']'", "';'", "'stageprop'", "'('", 
		"')'", "'public'", "'private'", "'int'", "'double'", "'char'", "'String'", 
		"'.'", "'||'", "'^'", "'is'", "'Is'", "'not'", "'Not'", "'!='", "'=='", 
		"'>='", "'<='", "'isnot'", "'IsNot'", "'if'", "'else'", "'for'", "':'", 
		"'while'", "'do'", null, null, null, null, "'switch'", "'case'", "'default'", 
		"'break'", "'continue'", "'return'", "'requires'", "'new'", "'clone'", 
		"'null'", "'const'", "'**'", "'...'", null, "'&&'", null, null, "'-'", 
		"'+'", "'<'", "'>'", "'!'", null, null, null, null, null, "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, "STRING", "INTEGER", "FLOAT", "BOOLEAN", 
		"SWITCH", "CASE", "DEFAULT", "BREAK", "CONTINUE", "RETURN", "REQUIRES", 
		"NEW", "CLONE", "NULL", "CONST", "POW", "ELLIPSIS", "BOOLEAN_SUMOP", "BOOLEAN_MULOP", 
		"ABELIAN_SUMOP", "ABELIAN_MULOP", "MINUS", "PLUS", "LT", "GT", "LOGICAL_NEGATION", 
		"ABELIAN_INCREMENT_OP", "JAVA_ID", "INLINE_COMMENT", "C_COMMENT", "WHITESPACE", 
		"ASSIGN"
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
			setState(122);
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
			setState(232);
			match(T__6);
			setState(233);
			match(JAVA_ID);
			}
			_ctx.stop = _input.LT(-1);
			setState(240);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Implements_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_implements_list);
					setState(235);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(236);
					match(T__7);
					setState(237);
					match(JAVA_ID);
					}
					} 
				}
				setState(242);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
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
			setState(243);
			match(LT);
			setState(244);
			type_parameter();
			setState(249);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(245);
				match(T__7);
				setState(246);
				type_parameter();
				}
				}
				setState(251);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(252);
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
			setState(254);
			match(LT);
			setState(255);
			type_name();
			setState(260);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(256);
				match(T__7);
				setState(257);
				type_name();
				}
				}
				setState(262);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(263);
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
			setState(265);
			type_name();
			setState(268);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(266);
				match(T__4);
				setState(267);
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
			setState(273);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(271);
				context_body_element();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(279);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Context_bodyContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_context_body);
					setState(275);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(276);
					context_body_element();
					}
					} 
				}
				setState(281);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
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
			setState(287);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(282);
				method_decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(283);
				object_decl();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(284);
				role_decl();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(285);
				stageprop_decl();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(286);
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
			setState(365);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(289);
				match(T__8);
				setState(290);
				role_vec_modifier();
				setState(291);
				match(JAVA_ID);
				setState(292);
				match(T__1);
				setState(293);
				role_body(0);
				setState(294);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
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
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(307);
				access_qualifier();
				setState(308);
				match(T__8);
				setState(309);
				role_vec_modifier();
				setState(310);
				match(JAVA_ID);
				setState(311);
				match(T__1);
				setState(312);
				role_body(0);
				setState(313);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(315);
				access_qualifier();
				setState(316);
				match(T__8);
				setState(317);
				role_vec_modifier();
				setState(318);
				match(JAVA_ID);
				setState(319);
				match(T__1);
				setState(320);
				role_body(0);
				setState(321);
				match(T__2);
				setState(322);
				match(REQUIRES);
				setState(323);
				match(T__1);
				setState(324);
				self_methods(0);
				setState(325);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(327);
				match(T__8);
				setState(328);
				role_vec_modifier();
				setState(329);
				match(JAVA_ID);
				setState(330);
				match(T__1);
				setState(331);
				role_body(0);
				setState(332);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
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
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(345);
				access_qualifier();
				setState(346);
				match(T__8);
				setState(347);
				role_vec_modifier();
				setState(348);
				match(JAVA_ID);
				setState(349);
				match(T__1);
				setState(350);
				role_body(0);
				setState(351);
				match(T__2);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(353);
				access_qualifier();
				setState(354);
				match(T__8);
				setState(355);
				role_vec_modifier();
				setState(356);
				match(JAVA_ID);
				setState(357);
				match(T__1);
				setState(358);
				role_body(0);
				setState(359);
				match(T__2);
				setState(360);
				match(REQUIRES);
				setState(361);
				match(T__1);
				setState(362);
				self_methods(0);
				setState(363);
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
			setState(370);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(367);
				match(T__9);
				setState(368);
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
			setState(383);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(373);
				method_decl();
				}
				break;
			case 2:
				{
				setState(374);
				object_decl();
				}
				break;
			case 3:
				{
				setState(375);
				method_signature();
				setState(379);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(376);
						match(T__11);
						}
						} 
					}
					setState(381);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				}
				}
				break;
			case 4:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(399);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(397);
					switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
					case 1:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(385);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(386);
						method_decl();
						}
						break;
					case 2:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(387);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(388);
						object_decl();
						}
						break;
					case 3:
						{
						_localctx = new Role_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_role_body);
						setState(389);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(390);
						method_signature();
						setState(394);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(391);
								match(T__11);
								}
								} 
							}
							setState(396);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
						}
						}
						break;
					}
					} 
				}
				setState(401);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
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
			setState(403);
			method_signature();
			}
			_ctx.stop = _input.LT(-1);
			setState(412);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(410);
					switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
					case 1:
						{
						_localctx = new Self_methodsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_self_methods);
						setState(405);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(406);
						match(T__11);
						setState(407);
						method_signature();
						}
						break;
					case 2:
						{
						_localctx = new Self_methodsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_self_methods);
						setState(408);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(409);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(414);
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
			setState(487);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(415);
				match(T__12);
				setState(416);
				role_vec_modifier();
				setState(417);
				match(JAVA_ID);
				setState(418);
				match(T__1);
				setState(419);
				stageprop_body(0);
				setState(420);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
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
				stageprop_body(0);
				setState(427);
				match(T__2);
				setState(428);
				match(REQUIRES);
				setState(429);
				match(T__1);
				setState(430);
				self_methods(0);
				setState(431);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(433);
				access_qualifier();
				setState(434);
				match(T__12);
				setState(435);
				role_vec_modifier();
				setState(436);
				match(JAVA_ID);
				setState(437);
				match(T__1);
				setState(438);
				stageprop_body(0);
				setState(439);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
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
				stageprop_body(0);
				setState(447);
				match(T__2);
				setState(448);
				match(REQUIRES);
				setState(449);
				match(T__1);
				setState(450);
				self_methods(0);
				setState(451);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
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
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(459);
				match(T__12);
				setState(460);
				role_vec_modifier();
				setState(461);
				match(JAVA_ID);
				setState(462);
				match(T__1);
				setState(463);
				match(T__2);
				setState(464);
				match(REQUIRES);
				setState(465);
				match(T__1);
				setState(466);
				self_methods(0);
				setState(467);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(469);
				access_qualifier();
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
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(476);
				access_qualifier();
				setState(477);
				match(T__12);
				setState(478);
				role_vec_modifier();
				setState(479);
				match(JAVA_ID);
				setState(480);
				match(T__1);
				setState(481);
				match(T__2);
				setState(482);
				match(REQUIRES);
				setState(483);
				match(T__1);
				setState(484);
				self_methods(0);
				setState(485);
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
			setState(499);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(490);
				method_decl();
				}
				break;
			case 2:
				{
				setState(491);
				object_decl();
				}
				break;
			case 3:
				{
				setState(492);
				method_signature();
				setState(496);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(493);
						match(T__11);
						}
						} 
					}
					setState(498);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				}
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(515);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(513);
					switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
					case 1:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(501);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(502);
						method_decl();
						}
						break;
					case 2:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(503);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(504);
						object_decl();
						}
						break;
					case 3:
						{
						_localctx = new Stageprop_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_stageprop_body);
						setState(505);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(506);
						method_signature();
						setState(510);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(507);
								match(T__11);
								}
								} 
							}
							setState(512);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
						}
						}
						break;
					}
					} 
				}
				setState(517);
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
			setState(521);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(519);
				class_body_element();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(527);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Class_bodyContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_class_body);
					setState(523);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(524);
					class_body_element();
					}
					} 
				}
				setState(529);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
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
			setState(533);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(530);
				method_decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(531);
				object_decl();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(532);
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
			setState(536);
			method_signature();
			}
			_ctx.stop = _input.LT(-1);
			setState(545);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(543);
					switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
					case 1:
						{
						_localctx = new Interface_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_interface_body);
						setState(538);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(539);
						match(T__11);
						setState(540);
						method_signature();
						}
						break;
					case 2:
						{
						_localctx = new Interface_bodyContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_interface_body);
						setState(541);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(542);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(547);
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
			setState(548);
			method_decl_hook();
			setState(549);
			match(T__1);
			setState(550);
			type_and_expr_and_decl_list();
			setState(551);
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
			setState(553);
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
			setState(587);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(555);
				access_qualifier();
				setState(556);
				return_type();
				setState(557);
				method_name();
				setState(558);
				match(T__13);
				setState(559);
				param_list(0);
				setState(560);
				match(T__14);
				setState(564);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(561);
						match(CONST);
						}
						} 
					}
					setState(566);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(567);
				access_qualifier();
				setState(568);
				return_type();
				setState(569);
				method_name();
				setState(573);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(570);
						match(CONST);
						}
						} 
					}
					setState(575);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(576);
				access_qualifier();
				setState(577);
				method_name();
				setState(578);
				match(T__13);
				setState(579);
				param_list(0);
				setState(580);
				match(T__14);
				setState(584);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(581);
						match(CONST);
						}
						} 
					}
					setState(586);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
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
			setState(596);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				{
				setState(590);
				object_decl();
				}
				break;
			case 2:
				{
				setState(591);
				expr();
				setState(592);
				match(T__11);
				setState(593);
				object_decl();
				}
				break;
			case 3:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(606);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(604);
					switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
					case 1:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(598);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(599);
						object_decl();
						}
						break;
					case 2:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(600);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(601);
						expr();
						}
						break;
					case 3:
						{
						_localctx = new Expr_and_decl_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_and_decl_list);
						setState(602);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(603);
						match(T__11);
						}
						break;
					}
					} 
				}
				setState(608);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
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
			setState(616);
			switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(609);
				expr_and_decl_list(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(610);
				expr_and_decl_list(0);
				setState(611);
				type_declaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(613);
				type_declaration();
				setState(614);
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
			setState(624);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(618);
				type_name();
				setState(619);
				match(T__9);
				setState(620);
				match(T__10);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(622);
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
			setState(626);
			_la = _input.LA(1);
			if ( !(((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (ABELIAN_SUMOP - 60)) | (1L << (ABELIAN_MULOP - 60)) | (1L << (JAVA_ID - 60)))) != 0)) ) {
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
		enterRule(_localctx, 58, RULE_access_qualifier);
		try {
			setState(631);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(628);
				match(T__15);
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 2);
				{
				setState(629);
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
			setState(649);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(633);
				access_qualifier();
				setState(634);
				compound_type_name();
				setState(635);
				identifier_list(0);
				setState(636);
				match(T__11);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(638);
				access_qualifier();
				setState(639);
				compound_type_name();
				setState(640);
				identifier_list(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(642);
				compound_type_name();
				setState(643);
				identifier_list(0);
				setState(644);
				match(T__11);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(646);
				compound_type_name();
				setState(647);
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
			setState(651);
			compound_type_name();
			setState(652);
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
			setState(659);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(654);
				type_name();
				setState(655);
				match(T__9);
				setState(656);
				match(T__10);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(658);
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
			setState(665);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(661);
				match(JAVA_ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(662);
				match(JAVA_ID);
				setState(663);
				type_list();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(664);
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
			setState(667);
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
		int _startState = 70;
		enterRecursionRule(_localctx, 70, RULE_identifier_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(674);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				{
				setState(670);
				match(JAVA_ID);
				}
				break;
			case 2:
				{
				setState(671);
				match(JAVA_ID);
				setState(672);
				match(ASSIGN);
				setState(673);
				expr();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(686);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(684);
					switch ( getInterpreter().adaptivePredict(_input,52,_ctx) ) {
					case 1:
						{
						_localctx = new Identifier_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identifier_list);
						setState(676);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(677);
						match(T__7);
						setState(678);
						match(JAVA_ID);
						}
						break;
					case 2:
						{
						_localctx = new Identifier_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identifier_list);
						setState(679);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(680);
						match(T__7);
						setState(681);
						match(JAVA_ID);
						setState(682);
						match(ASSIGN);
						setState(683);
						expr();
						}
						break;
					}
					} 
				}
				setState(688);
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
			setState(692);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				{
				setState(690);
				param_decl();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(704);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,56,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(702);
					switch ( getInterpreter().adaptivePredict(_input,55,_ctx) ) {
					case 1:
						{
						_localctx = new Param_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_param_list);
						setState(694);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(695);
						match(T__7);
						setState(696);
						param_decl();
						}
						break;
					case 2:
						{
						_localctx = new Param_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_param_list);
						setState(697);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(698);
						match(ELLIPSIS);
						}
						break;
					case 3:
						{
						_localctx = new Param_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_param_list);
						setState(699);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(700);
						match(T__7);
						setState(701);
						match(ELLIPSIS);
						}
						break;
					}
					} 
				}
				setState(706);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,56,_ctx);
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
			setState(707);
			compound_type_name();
			setState(708);
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
			setState(723);
			switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(710);
				abelian_expr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(711);
				boolean_expr(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(712);
				block();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(713);
				if_expr();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(714);
				for_expr();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(715);
				while_expr();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(716);
				do_while_expr();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(717);
				switch_expr();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(718);
				match(BREAK);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(719);
				match(CONTINUE);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(720);
				match(RETURN);
				setState(721);
				expr();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(722);
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
			setState(735);
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
				setState(726);
				abelian_product(0);
				setState(731);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(727);
						match(ABELIAN_SUMOP);
						setState(728);
						abelian_product(0);
						}
						} 
					}
					setState(733);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
				}
				}
				break;
			case T__34:
				{
				setState(734);
				if_expr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(742);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Abelian_exprContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_abelian_expr);
					setState(737);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(738);
					match(ASSIGN);
					setState(739);
					expr();
					}
					} 
				}
				setState(744);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
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
			setState(746);
			abelian_unary_op();
			setState(751);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(747);
					match(ABELIAN_MULOP);
					setState(748);
					abelian_unary_op();
					}
					} 
				}
				setState(753);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
			}
			}
			_ctx.stop = _input.LT(-1);
			setState(759);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,62,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Abelian_productContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_abelian_product);
					setState(754);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(755);
					match(POW);
					setState(756);
					abelian_atom(0);
					}
					} 
				}
				setState(761);
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
			setState(765);
			switch ( getInterpreter().adaptivePredict(_input,63,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(762);
				match(ABELIAN_SUMOP);
				setState(763);
				abelian_atom(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(764);
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
		int _startState = 84;
		enterRecursionRule(_localctx, 84, RULE_abelian_atom, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(808);
			switch ( getInterpreter().adaptivePredict(_input,64,_ctx) ) {
			case 1:
				{
				setState(768);
				match(ABELIAN_INCREMENT_OP);
				setState(769);
				abelian_atom(10);
				}
				break;
			case 2:
				{
				setState(770);
				match(NEW);
				setState(771);
				message();
				}
				break;
			case 3:
				{
				setState(772);
				match(NEW);
				setState(773);
				type_name();
				setState(774);
				match(T__9);
				setState(775);
				expr();
				setState(776);
				match(T__10);
				}
				break;
			case 4:
				{
				setState(778);
				match(NEW);
				setState(779);
				match(JAVA_ID);
				setState(780);
				type_list();
				setState(781);
				match(T__13);
				setState(782);
				argument_list(0);
				setState(783);
				match(T__14);
				}
				break;
			case 5:
				{
				setState(785);
				builtin_type_name();
				setState(786);
				match(T__21);
				setState(787);
				message();
				}
				break;
			case 6:
				{
				setState(789);
				null_expr();
				}
				break;
			case 7:
				{
				setState(790);
				message();
				}
				break;
			case 8:
				{
				setState(791);
				match(JAVA_ID);
				}
				break;
			case 9:
				{
				setState(792);
				constant();
				}
				break;
			case 10:
				{
				setState(793);
				match(T__13);
				setState(794);
				abelian_expr(0);
				setState(795);
				match(T__14);
				}
				break;
			case 11:
				{
				setState(797);
				match(ABELIAN_INCREMENT_OP);
				setState(798);
				expr();
				setState(799);
				match(T__9);
				setState(800);
				expr();
				setState(801);
				match(T__10);
				}
				break;
			case 12:
				{
				setState(803);
				match(ABELIAN_INCREMENT_OP);
				setState(804);
				expr();
				setState(805);
				match(T__21);
				setState(806);
				match(JAVA_ID);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(843);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(841);
					switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
					case 1:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(810);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(811);
						match(T__21);
						setState(812);
						message();
						}
						break;
					case 2:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(813);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(814);
						match(T__21);
						setState(815);
						match(JAVA_ID);
						}
						break;
					case 3:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(816);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(817);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 4:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(818);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(819);
						match(T__9);
						setState(820);
						expr();
						setState(821);
						match(T__10);
						}
						break;
					case 5:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(823);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(824);
						match(T__9);
						setState(825);
						expr();
						setState(826);
						match(T__10);
						setState(827);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 6:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(829);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(830);
						match(T__21);
						setState(831);
						match(JAVA_ID);
						setState(832);
						match(ABELIAN_INCREMENT_OP);
						}
						break;
					case 7:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(833);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(834);
						match(T__21);
						setState(835);
						match(CLONE);
						}
						break;
					case 8:
						{
						_localctx = new Abelian_atomContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_abelian_atom);
						setState(836);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(837);
						match(T__21);
						setState(838);
						match(CLONE);
						setState(839);
						match(T__13);
						setState(840);
						match(T__14);
						}
						break;
					}
					} 
				}
				setState(845);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
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
			setState(866);
			switch ( getInterpreter().adaptivePredict(_input,68,_ctx) ) {
			case 1:
				{
				setState(847);
				boolean_product();
				setState(852);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,67,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(848);
						match(BOOLEAN_SUMOP);
						setState(849);
						boolean_product();
						}
						} 
					}
					setState(854);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,67,_ctx);
				}
				}
				break;
			case 2:
				{
				setState(855);
				if_expr();
				}
				break;
			case 3:
				{
				setState(856);
				abelian_expr(0);
				setState(857);
				((Boolean_exprContext)_localctx).op1 = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__24 || _la==T__25) ) {
					((Boolean_exprContext)_localctx).op1 = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(858);
				((Boolean_exprContext)_localctx).op2 = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__26 || _la==T__27) ) {
					((Boolean_exprContext)_localctx).op2 = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(859);
				abelian_expr(0);
				}
				break;
			case 4:
				{
				setState(861);
				abelian_expr(0);
				setState(862);
				((Boolean_exprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(((((_la - 25)) & ~0x3f) == 0 && ((1L << (_la - 25)) & ((1L << (T__24 - 25)) | (1L << (T__25 - 25)) | (1L << (T__28 - 25)) | (1L << (T__29 - 25)) | (1L << (T__30 - 25)) | (1L << (T__31 - 25)) | (1L << (T__32 - 25)) | (1L << (T__33 - 25)) | (1L << (LT - 25)) | (1L << (GT - 25)))) != 0)) ) {
					((Boolean_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(863);
				abelian_expr(0);
				}
				break;
			case 5:
				{
				setState(865);
				abelian_expr(0);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(876);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,70,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(874);
					switch ( getInterpreter().adaptivePredict(_input,69,_ctx) ) {
					case 1:
						{
						_localctx = new Boolean_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolean_expr);
						setState(868);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(869);
						((Boolean_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << BOOLEAN_MULOP))) != 0)) ) {
							((Boolean_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(870);
						boolean_expr(6);
						}
						break;
					case 2:
						{
						_localctx = new Boolean_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolean_expr);
						setState(871);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(872);
						match(ASSIGN);
						setState(873);
						expr();
						}
						break;
					}
					} 
				}
				setState(878);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,70,_ctx);
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
			setState(879);
			boolean_unary_op();
			setState(884);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,71,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(880);
					match(BOOLEAN_MULOP);
					setState(881);
					boolean_unary_op();
					}
					} 
				}
				setState(886);
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
			setState(890);
			switch (_input.LA(1)) {
			case LOGICAL_NEGATION:
				enterOuterAlt(_localctx, 1);
				{
				setState(887);
				match(LOGICAL_NEGATION);
				setState(888);
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
				setState(889);
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
			setState(899);
			switch (_input.LA(1)) {
			case STRING:
			case INTEGER:
			case FLOAT:
			case BOOLEAN:
				enterOuterAlt(_localctx, 1);
				{
				setState(892);
				constant();
				}
				break;
			case JAVA_ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(893);
				match(JAVA_ID);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 3);
				{
				setState(894);
				null_expr();
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 4);
				{
				setState(895);
				match(T__13);
				setState(896);
				boolean_expr(0);
				setState(897);
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
			setState(911);
			switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(901);
				method_name();
				setState(902);
				match(T__13);
				setState(903);
				argument_list(0);
				setState(904);
				match(T__14);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(906);
				type_name();
				setState(907);
				match(T__13);
				setState(908);
				argument_list(0);
				setState(909);
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
			setState(919);
			switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(913);
				match(T__1);
				setState(914);
				expr_and_decl_list(0);
				setState(915);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(917);
				match(T__1);
				setState(918);
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
			setState(923);
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
				setState(921);
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
			setState(939);
			switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(925);
				match(T__34);
				setState(926);
				match(T__13);
				setState(927);
				expr();
				setState(928);
				match(T__14);
				setState(929);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(931);
				match(T__34);
				setState(932);
				match(T__13);
				setState(933);
				expr();
				setState(934);
				match(T__14);
				setState(935);
				expr();
				setState(936);
				match(T__35);
				setState(937);
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
			setState(976);
			switch ( getInterpreter().adaptivePredict(_input,78,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(941);
				match(T__36);
				setState(942);
				match(T__13);
				setState(943);
				expr();
				setState(944);
				match(T__11);
				setState(945);
				expr();
				setState(946);
				match(T__11);
				setState(947);
				expr();
				setState(948);
				match(T__14);
				setState(949);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(951);
				match(T__36);
				setState(952);
				match(T__13);
				setState(953);
				object_decl();
				setState(954);
				expr();
				setState(955);
				match(T__11);
				setState(956);
				expr();
				setState(957);
				match(T__14);
				setState(958);
				expr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(960);
				match(T__36);
				setState(961);
				match(T__13);
				setState(962);
				match(JAVA_ID);
				setState(963);
				match(T__37);
				setState(964);
				expr();
				setState(965);
				match(T__14);
				setState(966);
				expr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(968);
				match(T__36);
				setState(969);
				match(T__13);
				setState(970);
				trivial_object_decl();
				setState(971);
				match(T__37);
				setState(972);
				expr();
				setState(973);
				match(T__14);
				setState(974);
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
			setState(978);
			match(T__38);
			setState(979);
			match(T__13);
			setState(980);
			expr();
			setState(981);
			match(T__14);
			setState(982);
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
			setState(984);
			match(T__39);
			setState(985);
			expr();
			setState(986);
			match(T__38);
			setState(987);
			match(T__13);
			setState(988);
			expr();
			setState(989);
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
			setState(991);
			match(SWITCH);
			setState(992);
			match(T__13);
			setState(993);
			expr();
			setState(994);
			match(T__14);
			setState(995);
			match(T__1);
			setState(999);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE || _la==DEFAULT) {
				{
				{
				setState(996);
				switch_body();
				}
				}
				setState(1001);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1002);
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
			setState(1007);
			switch (_input.LA(1)) {
			case CASE:
				{
				setState(1004);
				match(CASE);
				setState(1005);
				constant();
				}
				break;
			case DEFAULT:
				{
				setState(1006);
				match(DEFAULT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1009);
			match(T__37);
			setState(1010);
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
			setState(1012);
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
			setState(1014);
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
		int _startState = 116;
		enterRecursionRule(_localctx, 116, RULE_argument_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1019);
			switch ( getInterpreter().adaptivePredict(_input,81,_ctx) ) {
			case 1:
				{
				setState(1017);
				expr();
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(1026);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,82,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Argument_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_argument_list);
					setState(1021);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(1022);
					match(T__7);
					setState(1023);
					expr();
					}
					} 
				}
				setState(1028);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,82,_ctx);
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
			return precpred(_ctx, 6);
		case 4:
			return precpred(_ctx, 4);
		case 5:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean self_methods_sempred(Self_methodsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return precpred(_ctx, 3);
		case 7:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean stageprop_body_sempred(Stageprop_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 5);
		case 9:
			return precpred(_ctx, 3);
		case 10:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean class_body_sempred(Class_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 11:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean interface_body_sempred(Interface_bodyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 12:
			return precpred(_ctx, 3);
		case 13:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expr_and_decl_list_sempred(Expr_and_decl_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 14:
			return precpred(_ctx, 4);
		case 15:
			return precpred(_ctx, 3);
		case 16:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean identifier_list_sempred(Identifier_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 17:
			return precpred(_ctx, 3);
		case 18:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean param_list_sempred(Param_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 19:
			return precpred(_ctx, 4);
		case 20:
			return precpred(_ctx, 3);
		case 21:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean abelian_expr_sempred(Abelian_exprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 22:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean abelian_product_sempred(Abelian_productContext _localctx, int predIndex) {
		switch (predIndex) {
		case 23:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean abelian_atom_sempred(Abelian_atomContext _localctx, int predIndex) {
		switch (predIndex) {
		case 24:
			return precpred(_ctx, 17);
		case 25:
			return precpred(_ctx, 15);
		case 26:
			return precpred(_ctx, 11);
		case 27:
			return precpred(_ctx, 7);
		case 28:
			return precpred(_ctx, 6);
		case 29:
			return precpred(_ctx, 3);
		case 30:
			return precpred(_ctx, 2);
		case 31:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean boolean_expr_sempred(Boolean_exprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 32:
			return precpred(_ctx, 5);
		case 33:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean argument_list_sempred(Argument_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 34:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3J\u0408\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\3\2\3"+
		"\2\3\2\3\2\5\2}\n\2\3\3\3\3\3\4\3\4\3\4\5\4\u0084\n\4\3\4\3\4\7\4\u0088"+
		"\n\4\f\4\16\4\u008b\13\4\3\5\3\5\3\5\5\5\u0090\n\5\3\6\3\6\3\6\7\6\u0095"+
		"\n\6\f\6\16\6\u0098\13\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\7\7\u00a2\n\7"+
		"\f\7\16\7\u00a5\13\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u00b1"+
		"\n\7\f\7\16\7\u00b4\13\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u00bd\n\7\f\7"+
		"\16\7\u00c0\13\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u00cb\n\7\f\7"+
		"\16\7\u00ce\13\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u00d7\n\7\f\7\16\7\u00da"+
		"\13\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u00e2\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u00f1\n\t\f\t\16\t\u00f4\13\t\3\n\3\n\3"+
		"\n\3\n\7\n\u00fa\n\n\f\n\16\n\u00fd\13\n\3\n\3\n\3\13\3\13\3\13\3\13\7"+
		"\13\u0105\n\13\f\13\16\13\u0108\13\13\3\13\3\13\3\f\3\f\3\f\5\f\u010f"+
		"\n\f\3\r\3\r\3\r\5\r\u0114\n\r\3\r\3\r\7\r\u0118\n\r\f\r\16\r\u011b\13"+
		"\r\3\16\3\16\3\16\3\16\3\16\5\16\u0122\n\16\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\5\17\u0170\n\17\3\20\3\20\3\20\5\20\u0175\n\20\3\21\3\21\3\21\3\21"+
		"\3\21\7\21\u017c\n\21\f\21\16\21\u017f\13\21\3\21\5\21\u0182\n\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\7\21\u018b\n\21\f\21\16\21\u018e\13\21"+
		"\7\21\u0190\n\21\f\21\16\21\u0193\13\21\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\7\22\u019d\n\22\f\22\16\22\u01a0\13\22\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u01ea"+
		"\n\23\3\24\3\24\3\24\3\24\3\24\7\24\u01f1\n\24\f\24\16\24\u01f4\13\24"+
		"\5\24\u01f6\n\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\7\24\u01ff\n\24\f"+
		"\24\16\24\u0202\13\24\7\24\u0204\n\24\f\24\16\24\u0207\13\24\3\25\3\25"+
		"\3\25\5\25\u020c\n\25\3\25\3\25\7\25\u0210\n\25\f\25\16\25\u0213\13\25"+
		"\3\26\3\26\3\26\5\26\u0218\n\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\7\27\u0222\n\27\f\27\16\27\u0225\13\27\3\30\3\30\3\30\3\30\3\30\3\31"+
		"\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\7\32\u0235\n\32\f\32\16\32\u0238"+
		"\13\32\3\32\3\32\3\32\3\32\7\32\u023e\n\32\f\32\16\32\u0241\13\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\7\32\u0249\n\32\f\32\16\32\u024c\13\32\5\32"+
		"\u024e\n\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u0257\n\33\3\33\3"+
		"\33\3\33\3\33\3\33\3\33\7\33\u025f\n\33\f\33\16\33\u0262\13\33\3\34\3"+
		"\34\3\34\3\34\3\34\3\34\3\34\5\34\u026b\n\34\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\5\35\u0273\n\35\3\36\3\36\3\37\3\37\3\37\5\37\u027a\n\37\3 \3 \3"+
		" \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \5 \u028c\n \3!\3!\3!\3\"\3\""+
		"\3\"\3\"\3\"\5\"\u0296\n\"\3#\3#\3#\3#\5#\u029c\n#\3$\3$\3%\3%\3%\3%\3"+
		"%\5%\u02a5\n%\3%\3%\3%\3%\3%\3%\3%\3%\7%\u02af\n%\f%\16%\u02b2\13%\3&"+
		"\3&\3&\5&\u02b7\n&\3&\3&\3&\3&\3&\3&\3&\3&\7&\u02c1\n&\f&\16&\u02c4\13"+
		"&\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\5(\u02d6\n(\3)\3"+
		")\3)\3)\7)\u02dc\n)\f)\16)\u02df\13)\3)\5)\u02e2\n)\3)\3)\3)\7)\u02e7"+
		"\n)\f)\16)\u02ea\13)\3*\3*\3*\3*\7*\u02f0\n*\f*\16*\u02f3\13*\3*\3*\3"+
		"*\7*\u02f8\n*\f*\16*\u02fb\13*\3+\3+\3+\5+\u0300\n+\3,\3,\3,\3,\3,\3,"+
		"\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,"+
		"\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\5,\u032b\n,\3,\3,\3,\3,\3,\3,\3,"+
		"\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,"+
		"\3,\7,\u034c\n,\f,\16,\u034f\13,\3-\3-\3-\3-\7-\u0355\n-\f-\16-\u0358"+
		"\13-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\5-\u0365\n-\3-\3-\3-\3-\3-\3-\7"+
		"-\u036d\n-\f-\16-\u0370\13-\3.\3.\3.\7.\u0375\n.\f.\16.\u0378\13.\3/\3"+
		"/\3/\5/\u037d\n/\3\60\3\60\3\60\3\60\3\60\3\60\3\60\5\60\u0386\n\60\3"+
		"\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\5\61\u0392\n\61\3\62"+
		"\3\62\3\62\3\62\3\62\3\62\5\62\u039a\n\62\3\63\3\63\5\63\u039e\n\63\3"+
		"\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\5"+
		"\64\u03ae\n\64\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65"+
		"\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65"+
		"\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\5\65\u03d3\n\65\3\66"+
		"\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\3\67\38\38\38"+
		"\38\38\38\78\u03e8\n8\f8\168\u03eb\138\38\38\39\39\39\59\u03f2\n9\39\3"+
		"9\39\3:\3:\3;\3;\3<\3<\3<\5<\u03fe\n<\3<\3<\3<\7<\u0403\n<\f<\16<\u0406"+
		"\13<\3<\2\22\6\20\30 \"&(,\64HJPRVXv=\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtv\2\t\4\2>"+
		"?FF\3\2\24\27\3\2\33\34\3\2\35\36\5\2\33\34\37$BC\4\2\31\32==\3\2+.\u0463"+
		"\2|\3\2\2\2\4~\3\2\2\2\6\u0083\3\2\2\2\b\u008f\3\2\2\2\n\u0091\3\2\2\2"+
		"\f\u00e1\3\2\2\2\16\u00e3\3\2\2\2\20\u00e9\3\2\2\2\22\u00f5\3\2\2\2\24"+
		"\u0100\3\2\2\2\26\u010b\3\2\2\2\30\u0113\3\2\2\2\32\u0121\3\2\2\2\34\u016f"+
		"\3\2\2\2\36\u0174\3\2\2\2 \u0181\3\2\2\2\"\u0194\3\2\2\2$\u01e9\3\2\2"+
		"\2&\u01f5\3\2\2\2(\u020b\3\2\2\2*\u0217\3\2\2\2,\u0219\3\2\2\2.\u0226"+
		"\3\2\2\2\60\u022b\3\2\2\2\62\u024d\3\2\2\2\64\u0256\3\2\2\2\66\u026a\3"+
		"\2\2\28\u0272\3\2\2\2:\u0274\3\2\2\2<\u0279\3\2\2\2>\u028b\3\2\2\2@\u028d"+
		"\3\2\2\2B\u0295\3\2\2\2D\u029b\3\2\2\2F\u029d\3\2\2\2H\u02a4\3\2\2\2J"+
		"\u02b6\3\2\2\2L\u02c5\3\2\2\2N\u02d5\3\2\2\2P\u02e1\3\2\2\2R\u02eb\3\2"+
		"\2\2T\u02ff\3\2\2\2V\u032a\3\2\2\2X\u0364\3\2\2\2Z\u0371\3\2\2\2\\\u037c"+
		"\3\2\2\2^\u0385\3\2\2\2`\u0391\3\2\2\2b\u0399\3\2\2\2d\u039d\3\2\2\2f"+
		"\u03ad\3\2\2\2h\u03d2\3\2\2\2j\u03d4\3\2\2\2l\u03da\3\2\2\2n\u03e1\3\2"+
		"\2\2p\u03f1\3\2\2\2r\u03f6\3\2\2\2t\u03f8\3\2\2\2v\u03fd\3\2\2\2xy\5\6"+
		"\4\2yz\5\4\3\2z}\3\2\2\2{}\5\6\4\2|x\3\2\2\2|{\3\2\2\2}\3\3\2\2\2~\177"+
		"\5N(\2\177\5\3\2\2\2\u0080\u0081\b\4\1\2\u0081\u0084\5\b\5\2\u0082\u0084"+
		"\3\2\2\2\u0083\u0080\3\2\2\2\u0083\u0082\3\2\2\2\u0084\u0089\3\2\2\2\u0085"+
		"\u0086\f\4\2\2\u0086\u0088\5\b\5\2\u0087\u0085\3\2\2\2\u0088\u008b\3\2"+
		"\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a\7\3\2\2\2\u008b\u0089"+
		"\3\2\2\2\u008c\u0090\5\n\6\2\u008d\u0090\5\f\7\2\u008e\u0090\5\16\b\2"+
		"\u008f\u008c\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u008e\3\2\2\2\u0090\t\3"+
		"\2\2\2\u0091\u0092\7\3\2\2\u0092\u0096\7F\2\2\u0093\u0095\5\20\t\2\u0094"+
		"\u0093\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097\3\2"+
		"\2\2\u0097\u0099\3\2\2\2\u0098\u0096\3\2\2\2\u0099\u009a\7\4\2\2\u009a"+
		"\u009b\5\30\r\2\u009b\u009c\7\5\2\2\u009c\13\3\2\2\2\u009d\u009e\7\6\2"+
		"\2\u009e\u009f\7F\2\2\u009f\u00a3\5\22\n\2\u00a0\u00a2\5\20\t\2\u00a1"+
		"\u00a0\3\2\2\2\u00a2\u00a5\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2"+
		"\2\2\u00a4\u00a6\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a6\u00a7\7\4\2\2\u00a7"+
		"\u00a8\5(\25\2\u00a8\u00a9\7\5\2\2\u00a9\u00e2\3\2\2\2\u00aa\u00ab\7\6"+
		"\2\2\u00ab\u00ac\7F\2\2\u00ac\u00ad\5\22\n\2\u00ad\u00ae\7\7\2\2\u00ae"+
		"\u00b2\7F\2\2\u00af\u00b1\5\20\t\2\u00b0\u00af\3\2\2\2\u00b1\u00b4\3\2"+
		"\2\2\u00b2\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b5\3\2\2\2\u00b4"+
		"\u00b2\3\2\2\2\u00b5\u00b6\7\4\2\2\u00b6\u00b7\5(\25\2\u00b7\u00b8\7\5"+
		"\2\2\u00b8\u00e2\3\2\2\2\u00b9\u00ba\7\6\2\2\u00ba\u00be\7F\2\2\u00bb"+
		"\u00bd\5\20\t\2\u00bc\u00bb\3\2\2\2\u00bd\u00c0\3\2\2\2\u00be\u00bc\3"+
		"\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c1\3\2\2\2\u00c0\u00be\3\2\2\2\u00c1"+
		"\u00c2\7\4\2\2\u00c2\u00c3\5(\25\2\u00c3\u00c4\7\5\2\2\u00c4\u00e2\3\2"+
		"\2\2\u00c5\u00c6\7\6\2\2\u00c6\u00c7\7F\2\2\u00c7\u00c8\7\7\2\2\u00c8"+
		"\u00cc\7F\2\2\u00c9\u00cb\5\20\t\2\u00ca\u00c9\3\2\2\2\u00cb\u00ce\3\2"+
		"\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00cf\3\2\2\2\u00ce"+
		"\u00cc\3\2\2\2\u00cf\u00d0\7\4\2\2\u00d0\u00d1\5(\25\2\u00d1\u00d2\7\5"+
		"\2\2\u00d2\u00e2\3\2\2\2\u00d3\u00d4\7\6\2\2\u00d4\u00d8\7F\2\2\u00d5"+
		"\u00d7\5\20\t\2\u00d6\u00d5\3\2\2\2\u00d7\u00da\3\2\2\2\u00d8\u00d6\3"+
		"\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00db\3\2\2\2\u00da\u00d8\3\2\2\2\u00db"+
		"\u00dc\7\7\2\2\u00dc\u00dd\7F\2\2\u00dd\u00de\7\4\2\2\u00de\u00df\5(\25"+
		"\2\u00df\u00e0\7\5\2\2\u00e0\u00e2\3\2\2\2\u00e1\u009d\3\2\2\2\u00e1\u00aa"+
		"\3\2\2\2\u00e1\u00b9\3\2\2\2\u00e1\u00c5\3\2\2\2\u00e1\u00d3\3\2\2\2\u00e2"+
		"\r\3\2\2\2\u00e3\u00e4\7\b\2\2\u00e4\u00e5\7F\2\2\u00e5\u00e6\7\4\2\2"+
		"\u00e6\u00e7\5,\27\2\u00e7\u00e8\7\5\2\2\u00e8\17\3\2\2\2\u00e9\u00ea"+
		"\b\t\1\2\u00ea\u00eb\7\t\2\2\u00eb\u00ec\7F\2\2\u00ec\u00f2\3\2\2\2\u00ed"+
		"\u00ee\f\3\2\2\u00ee\u00ef\7\n\2\2\u00ef\u00f1\7F\2\2\u00f0\u00ed\3\2"+
		"\2\2\u00f1\u00f4\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3"+
		"\21\3\2\2\2\u00f4\u00f2\3\2\2\2\u00f5\u00f6\7B\2\2\u00f6\u00fb\5\26\f"+
		"\2\u00f7\u00f8\7\n\2\2\u00f8\u00fa\5\26\f\2\u00f9\u00f7\3\2\2\2\u00fa"+
		"\u00fd\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fe\3\2"+
		"\2\2\u00fd\u00fb\3\2\2\2\u00fe\u00ff\7C\2\2\u00ff\23\3\2\2\2\u0100\u0101"+
		"\7B\2\2\u0101\u0106\5D#\2\u0102\u0103\7\n\2\2\u0103\u0105\5D#\2\u0104"+
		"\u0102\3\2\2\2\u0105\u0108\3\2\2\2\u0106\u0104\3\2\2\2\u0106\u0107\3\2"+
		"\2\2\u0107\u0109\3\2\2\2\u0108\u0106\3\2\2\2\u0109\u010a\7C\2\2\u010a"+
		"\25\3\2\2\2\u010b\u010e\5D#\2\u010c\u010d\7\7\2\2\u010d\u010f\5D#\2\u010e"+
		"\u010c\3\2\2\2\u010e\u010f\3\2\2\2\u010f\27\3\2\2\2\u0110\u0111\b\r\1"+
		"\2\u0111\u0114\5\32\16\2\u0112\u0114\3\2\2\2\u0113\u0110\3\2\2\2\u0113"+
		"\u0112\3\2\2\2\u0114\u0119\3\2\2\2\u0115\u0116\f\5\2\2\u0116\u0118\5\32"+
		"\16\2\u0117\u0115\3\2\2\2\u0118\u011b\3\2\2\2\u0119\u0117\3\2\2\2\u0119"+
		"\u011a\3\2\2\2\u011a\31\3\2\2\2\u011b\u0119\3\2\2\2\u011c\u0122\5.\30"+
		"\2\u011d\u0122\5> \2\u011e\u0122\5\34\17\2\u011f\u0122\5$\23\2\u0120\u0122"+
		"\5\n\6\2\u0121\u011c\3\2\2\2\u0121\u011d\3\2\2\2\u0121\u011e\3\2\2\2\u0121"+
		"\u011f\3\2\2\2\u0121\u0120\3\2\2\2\u0122\33\3\2\2\2\u0123\u0124\7\13\2"+
		"\2\u0124\u0125\5\36\20\2\u0125\u0126\7F\2\2\u0126\u0127\7\4\2\2\u0127"+
		"\u0128\5 \21\2\u0128\u0129\7\5\2\2\u0129\u0170\3\2\2\2\u012a\u012b\7\13"+
		"\2\2\u012b\u012c\5\36\20\2\u012c\u012d\7F\2\2\u012d\u012e\7\4\2\2\u012e"+
		"\u012f\5 \21\2\u012f\u0130\7\5\2\2\u0130\u0131\7\65\2\2\u0131\u0132\7"+
		"\4\2\2\u0132\u0133\5\"\22\2\u0133\u0134\7\5\2\2\u0134\u0170\3\2\2\2\u0135"+
		"\u0136\5<\37\2\u0136\u0137\7\13\2\2\u0137\u0138\5\36\20\2\u0138\u0139"+
		"\7F\2\2\u0139\u013a\7\4\2\2\u013a\u013b\5 \21\2\u013b\u013c\7\5\2\2\u013c"+
		"\u0170\3\2\2\2\u013d\u013e\5<\37\2\u013e\u013f\7\13\2\2\u013f\u0140\5"+
		"\36\20\2\u0140\u0141\7F\2\2\u0141\u0142\7\4\2\2\u0142\u0143\5 \21\2\u0143"+
		"\u0144\7\5\2\2\u0144\u0145\7\65\2\2\u0145\u0146\7\4\2\2\u0146\u0147\5"+
		"\"\22\2\u0147\u0148\7\5\2\2\u0148\u0170\3\2\2\2\u0149\u014a\7\13\2\2\u014a"+
		"\u014b\5\36\20\2\u014b\u014c\7F\2\2\u014c\u014d\7\4\2\2\u014d\u014e\5"+
		" \21\2\u014e\u014f\7\5\2\2\u014f\u0170\3\2\2\2\u0150\u0151\7\13\2\2\u0151"+
		"\u0152\5\36\20\2\u0152\u0153\7F\2\2\u0153\u0154\7\4\2\2\u0154\u0155\5"+
		" \21\2\u0155\u0156\7\5\2\2\u0156\u0157\7\65\2\2\u0157\u0158\7\4\2\2\u0158"+
		"\u0159\5\"\22\2\u0159\u015a\7\5\2\2\u015a\u0170\3\2\2\2\u015b\u015c\5"+
		"<\37\2\u015c\u015d\7\13\2\2\u015d\u015e\5\36\20\2\u015e\u015f\7F\2\2\u015f"+
		"\u0160\7\4\2\2\u0160\u0161\5 \21\2\u0161\u0162\7\5\2\2\u0162\u0170\3\2"+
		"\2\2\u0163\u0164\5<\37\2\u0164\u0165\7\13\2\2\u0165\u0166\5\36\20\2\u0166"+
		"\u0167\7F\2\2\u0167\u0168\7\4\2\2\u0168\u0169\5 \21\2\u0169\u016a\7\5"+
		"\2\2\u016a\u016b\7\65\2\2\u016b\u016c\7\4\2\2\u016c\u016d\5\"\22\2\u016d"+
		"\u016e\7\5\2\2\u016e\u0170\3\2\2\2\u016f\u0123\3\2\2\2\u016f\u012a\3\2"+
		"\2\2\u016f\u0135\3\2\2\2\u016f\u013d\3\2\2\2\u016f\u0149\3\2\2\2\u016f"+
		"\u0150\3\2\2\2\u016f\u015b\3\2\2\2\u016f\u0163\3\2\2\2\u0170\35\3\2\2"+
		"\2\u0171\u0172\7\f\2\2\u0172\u0175\7\r\2\2\u0173\u0175\3\2\2\2\u0174\u0171"+
		"\3\2\2\2\u0174\u0173\3\2\2\2\u0175\37\3\2\2\2\u0176\u0177\b\21\1\2\u0177"+
		"\u0182\5.\30\2\u0178\u0182\5> \2\u0179\u017d\5\62\32\2\u017a\u017c\7\16"+
		"\2\2\u017b\u017a\3\2\2\2\u017c\u017f\3\2\2\2\u017d\u017b\3\2\2\2\u017d"+
		"\u017e\3\2\2\2\u017e\u0182\3\2\2\2\u017f\u017d\3\2\2\2\u0180\u0182\3\2"+
		"\2\2\u0181\u0176\3\2\2\2\u0181\u0178\3\2\2\2\u0181\u0179\3\2\2\2\u0181"+
		"\u0180\3\2\2\2\u0182\u0191\3\2\2\2\u0183\u0184\f\b\2\2\u0184\u0190\5."+
		"\30\2\u0185\u0186\f\6\2\2\u0186\u0190\5> \2\u0187\u0188\f\4\2\2\u0188"+
		"\u018c\5\62\32\2\u0189\u018b\7\16\2\2\u018a\u0189\3\2\2\2\u018b\u018e"+
		"\3\2\2\2\u018c\u018a\3\2\2\2\u018c\u018d\3\2\2\2\u018d\u0190\3\2\2\2\u018e"+
		"\u018c\3\2\2\2\u018f\u0183\3\2\2\2\u018f\u0185\3\2\2\2\u018f\u0187\3\2"+
		"\2\2\u0190\u0193\3\2\2\2\u0191\u018f\3\2\2\2\u0191\u0192\3\2\2\2\u0192"+
		"!\3\2\2\2\u0193\u0191\3\2\2\2\u0194\u0195\b\22\1\2\u0195\u0196\5\62\32"+
		"\2\u0196\u019e\3\2\2\2\u0197\u0198\f\5\2\2\u0198\u0199\7\16\2\2\u0199"+
		"\u019d\5\62\32\2\u019a\u019b\f\3\2\2\u019b\u019d\7\16\2\2\u019c\u0197"+
		"\3\2\2\2\u019c\u019a\3\2\2\2\u019d\u01a0\3\2\2\2\u019e\u019c\3\2\2\2\u019e"+
		"\u019f\3\2\2\2\u019f#\3\2\2\2\u01a0\u019e\3\2\2\2\u01a1\u01a2\7\17\2\2"+
		"\u01a2\u01a3\5\36\20\2\u01a3\u01a4\7F\2\2\u01a4\u01a5\7\4\2\2\u01a5\u01a6"+
		"\5&\24\2\u01a6\u01a7\7\5\2\2\u01a7\u01ea\3\2\2\2\u01a8\u01a9\7\17\2\2"+
		"\u01a9\u01aa\5\36\20\2\u01aa\u01ab\7F\2\2\u01ab\u01ac\7\4\2\2\u01ac\u01ad"+
		"\5&\24\2\u01ad\u01ae\7\5\2\2\u01ae\u01af\7\65\2\2\u01af\u01b0\7\4\2\2"+
		"\u01b0\u01b1\5\"\22\2\u01b1\u01b2\7\5\2\2\u01b2\u01ea\3\2\2\2\u01b3\u01b4"+
		"\5<\37\2\u01b4\u01b5\7\17\2\2\u01b5\u01b6\5\36\20\2\u01b6\u01b7\7F\2\2"+
		"\u01b7\u01b8\7\4\2\2\u01b8\u01b9\5&\24\2\u01b9\u01ba\7\5\2\2\u01ba\u01ea"+
		"\3\2\2\2\u01bb\u01bc\5<\37\2\u01bc\u01bd\7\17\2\2\u01bd\u01be\5\36\20"+
		"\2\u01be\u01bf\7F\2\2\u01bf\u01c0\7\4\2\2\u01c0\u01c1\5&\24\2\u01c1\u01c2"+
		"\7\5\2\2\u01c2\u01c3\7\65\2\2\u01c3\u01c4\7\4\2\2\u01c4\u01c5\5\"\22\2"+
		"\u01c5\u01c6\7\5\2\2\u01c6\u01ea\3\2\2\2\u01c7\u01c8\7\17\2\2\u01c8\u01c9"+
		"\5\36\20\2\u01c9\u01ca\7F\2\2\u01ca\u01cb\7\4\2\2\u01cb\u01cc\7\5\2\2"+
		"\u01cc\u01ea\3\2\2\2\u01cd\u01ce\7\17\2\2\u01ce\u01cf\5\36\20\2\u01cf"+
		"\u01d0\7F\2\2\u01d0\u01d1\7\4\2\2\u01d1\u01d2\7\5\2\2\u01d2\u01d3\7\65"+
		"\2\2\u01d3\u01d4\7\4\2\2\u01d4\u01d5\5\"\22\2\u01d5\u01d6\7\5\2\2\u01d6"+
		"\u01ea\3\2\2\2\u01d7\u01d8\5<\37\2\u01d8\u01d9\7\17\2\2\u01d9\u01da\5"+
		"\36\20\2\u01da\u01db\7F\2\2\u01db\u01dc\7\4\2\2\u01dc\u01dd\7\5\2\2\u01dd"+
		"\u01ea\3\2\2\2\u01de\u01df\5<\37\2\u01df\u01e0\7\17\2\2\u01e0\u01e1\5"+
		"\36\20\2\u01e1\u01e2\7F\2\2\u01e2\u01e3\7\4\2\2\u01e3\u01e4\7\5\2\2\u01e4"+
		"\u01e5\7\65\2\2\u01e5\u01e6\7\4\2\2\u01e6\u01e7\5\"\22\2\u01e7\u01e8\7"+
		"\5\2\2\u01e8\u01ea\3\2\2\2\u01e9\u01a1\3\2\2\2\u01e9\u01a8\3\2\2\2\u01e9"+
		"\u01b3\3\2\2\2\u01e9\u01bb\3\2\2\2\u01e9\u01c7\3\2\2\2\u01e9\u01cd\3\2"+
		"\2\2\u01e9\u01d7\3\2\2\2\u01e9\u01de\3\2\2\2\u01ea%\3\2\2\2\u01eb\u01ec"+
		"\b\24\1\2\u01ec\u01f6\5.\30\2\u01ed\u01f6\5> \2\u01ee\u01f2\5\62\32\2"+
		"\u01ef\u01f1\7\16\2\2\u01f0\u01ef\3\2\2\2\u01f1\u01f4\3\2\2\2\u01f2\u01f0"+
		"\3\2\2\2\u01f2\u01f3\3\2\2\2\u01f3\u01f6\3\2\2\2\u01f4\u01f2\3\2\2\2\u01f5"+
		"\u01eb\3\2\2\2\u01f5\u01ed\3\2\2\2\u01f5\u01ee\3\2\2\2\u01f6\u0205\3\2"+
		"\2\2\u01f7\u01f8\f\7\2\2\u01f8\u0204\5.\30\2\u01f9\u01fa\f\5\2\2\u01fa"+
		"\u0204\5> \2\u01fb\u01fc\f\3\2\2\u01fc\u0200\5\62\32\2\u01fd\u01ff\7\16"+
		"\2\2\u01fe\u01fd\3\2\2\2\u01ff\u0202\3\2\2\2\u0200\u01fe\3\2\2\2\u0200"+
		"\u0201\3\2\2\2\u0201\u0204\3\2\2\2\u0202\u0200\3\2\2\2\u0203\u01f7\3\2"+
		"\2\2\u0203\u01f9\3\2\2\2\u0203\u01fb\3\2\2\2\u0204\u0207\3\2\2\2\u0205"+
		"\u0203\3\2\2\2\u0205\u0206\3\2\2\2\u0206\'\3\2\2\2\u0207\u0205\3\2\2\2"+
		"\u0208\u0209\b\25\1\2\u0209\u020c\5*\26\2\u020a\u020c\3\2\2\2\u020b\u0208"+
		"\3\2\2\2\u020b\u020a\3\2\2\2\u020c\u0211\3\2\2\2\u020d\u020e\f\5\2\2\u020e"+
		"\u0210\5*\26\2\u020f\u020d\3\2\2\2\u0210\u0213\3\2\2\2\u0211\u020f\3\2"+
		"\2\2\u0211\u0212\3\2\2\2\u0212)\3\2\2\2\u0213\u0211\3\2\2\2\u0214\u0218"+
		"\5.\30\2\u0215\u0218\5> \2\u0216\u0218\5\b\5\2\u0217\u0214\3\2\2\2\u0217"+
		"\u0215\3\2\2\2\u0217\u0216\3\2\2\2\u0218+\3\2\2\2\u0219\u021a\b\27\1\2"+
		"\u021a\u021b\5\62\32\2\u021b\u0223\3\2\2\2\u021c\u021d\f\5\2\2\u021d\u021e"+
		"\7\16\2\2\u021e\u0222\5\62\32\2\u021f\u0220\f\3\2\2\u0220\u0222\7\16\2"+
		"\2\u0221\u021c\3\2\2\2\u0221\u021f\3\2\2\2\u0222\u0225\3\2\2\2\u0223\u0221"+
		"\3\2\2\2\u0223\u0224\3\2\2\2\u0224-\3\2\2\2\u0225\u0223\3\2\2\2\u0226"+
		"\u0227\5\60\31\2\u0227\u0228\7\4\2\2\u0228\u0229\5\66\34\2\u0229\u022a"+
		"\7\5\2\2\u022a/\3\2\2\2\u022b\u022c\5\62\32\2\u022c\61\3\2\2\2\u022d\u022e"+
		"\5<\37\2\u022e\u022f\58\35\2\u022f\u0230\5:\36\2\u0230\u0231\7\20\2\2"+
		"\u0231\u0232\5J&\2\u0232\u0236\7\21\2\2\u0233\u0235\79\2\2\u0234\u0233"+
		"\3\2\2\2\u0235\u0238\3\2\2\2\u0236\u0234\3\2\2\2\u0236\u0237\3\2\2\2\u0237"+
		"\u024e\3\2\2\2\u0238\u0236\3\2\2\2\u0239\u023a\5<\37\2\u023a\u023b\58"+
		"\35\2\u023b\u023f\5:\36\2\u023c\u023e\79\2\2\u023d\u023c\3\2\2\2\u023e"+
		"\u0241\3\2\2\2\u023f\u023d\3\2\2\2\u023f\u0240\3\2\2\2\u0240\u024e\3\2"+
		"\2\2\u0241\u023f\3\2\2\2\u0242\u0243\5<\37\2\u0243\u0244\5:\36\2\u0244"+
		"\u0245\7\20\2\2\u0245\u0246\5J&\2\u0246\u024a\7\21\2\2\u0247\u0249\79"+
		"\2\2\u0248\u0247\3\2\2\2\u0249\u024c\3\2\2\2\u024a\u0248\3\2\2\2\u024a"+
		"\u024b\3\2\2\2\u024b\u024e\3\2\2\2\u024c\u024a\3\2\2\2\u024d\u022d\3\2"+
		"\2\2\u024d\u0239\3\2\2\2\u024d\u0242\3\2\2\2\u024e\63\3\2\2\2\u024f\u0250"+
		"\b\33\1\2\u0250\u0257\5> \2\u0251\u0252\5N(\2\u0252\u0253\7\16\2\2\u0253"+
		"\u0254\5> \2\u0254\u0257\3\2\2\2\u0255\u0257\3\2\2\2\u0256\u024f\3\2\2"+
		"\2\u0256\u0251\3\2\2\2\u0256\u0255\3\2\2\2\u0257\u0260\3\2\2\2\u0258\u0259"+
		"\f\6\2\2\u0259\u025f\5> \2\u025a\u025b\f\5\2\2\u025b\u025f\5N(\2\u025c"+
		"\u025d\f\4\2\2\u025d\u025f\7\16\2\2\u025e\u0258\3\2\2\2\u025e\u025a\3"+
		"\2\2\2\u025e\u025c\3\2\2\2\u025f\u0262\3\2\2\2\u0260\u025e\3\2\2\2\u0260"+
		"\u0261\3\2\2\2\u0261\65\3\2\2\2\u0262\u0260\3\2\2\2\u0263\u026b\5\64\33"+
		"\2\u0264\u0265\5\64\33\2\u0265\u0266\5\b\5\2\u0266\u026b\3\2\2\2\u0267"+
		"\u0268\5\b\5\2\u0268\u0269\5\64\33\2\u0269\u026b\3\2\2\2\u026a\u0263\3"+
		"\2\2\2\u026a\u0264\3\2\2\2\u026a\u0267\3\2\2\2\u026b\67\3\2\2\2\u026c"+
		"\u026d\5D#\2\u026d\u026e\7\f\2\2\u026e\u026f\7\r\2\2\u026f\u0273\3\2\2"+
		"\2\u0270\u0273\5D#\2\u0271\u0273\3\2\2\2\u0272\u026c\3\2\2\2\u0272\u0270"+
		"\3\2\2\2\u0272\u0271\3\2\2\2\u02739\3\2\2\2\u0274\u0275\t\2\2\2\u0275"+
		";\3\2\2\2\u0276\u027a\7\22\2\2\u0277\u027a\7\23\2\2\u0278\u027a\3\2\2"+
		"\2\u0279\u0276\3\2\2\2\u0279\u0277\3\2\2\2\u0279\u0278\3\2\2\2\u027a="+
		"\3\2\2\2\u027b\u027c\5<\37\2\u027c\u027d\5B\"\2\u027d\u027e\5H%\2\u027e"+
		"\u027f\7\16\2\2\u027f\u028c\3\2\2\2\u0280\u0281\5<\37\2\u0281\u0282\5"+
		"B\"\2\u0282\u0283\5H%\2\u0283\u028c\3\2\2\2\u0284\u0285\5B\"\2\u0285\u0286"+
		"\5H%\2\u0286\u0287\7\16\2\2\u0287\u028c\3\2\2\2\u0288\u0289\5B\"\2\u0289"+
		"\u028a\5H%\2\u028a\u028c\3\2\2\2\u028b\u027b\3\2\2\2\u028b\u0280\3\2\2"+
		"\2\u028b\u0284\3\2\2\2\u028b\u0288\3\2\2\2\u028c?\3\2\2\2\u028d\u028e"+
		"\5B\"\2\u028e\u028f\7F\2\2\u028fA\3\2\2\2\u0290\u0291\5D#\2\u0291\u0292"+
		"\7\f\2\2\u0292\u0293\7\r\2\2\u0293\u0296\3\2\2\2\u0294\u0296\5D#\2\u0295"+
		"\u0290\3\2\2\2\u0295\u0294\3\2\2\2\u0296C\3\2\2\2\u0297\u029c\7F\2\2\u0298"+
		"\u0299\7F\2\2\u0299\u029c\5\24\13\2\u029a\u029c\5F$\2\u029b\u0297\3\2"+
		"\2\2\u029b\u0298\3\2\2\2\u029b\u029a\3\2\2\2\u029cE\3\2\2\2\u029d\u029e"+
		"\t\3\2\2\u029eG\3\2\2\2\u029f\u02a0\b%\1\2\u02a0\u02a5\7F\2\2\u02a1\u02a2"+
		"\7F\2\2\u02a2\u02a3\7J\2\2\u02a3\u02a5\5N(\2\u02a4\u029f\3\2\2\2\u02a4"+
		"\u02a1\3\2\2\2\u02a5\u02b0\3\2\2\2\u02a6\u02a7\f\5\2\2\u02a7\u02a8\7\n"+
		"\2\2\u02a8\u02af\7F\2\2\u02a9\u02aa\f\3\2\2\u02aa\u02ab\7\n\2\2\u02ab"+
		"\u02ac\7F\2\2\u02ac\u02ad\7J\2\2\u02ad\u02af\5N(\2\u02ae\u02a6\3\2\2\2"+
		"\u02ae\u02a9\3\2\2\2\u02af\u02b2\3\2\2\2\u02b0\u02ae\3\2\2\2\u02b0\u02b1"+
		"\3\2\2\2\u02b1I\3\2\2\2\u02b2\u02b0\3\2\2\2\u02b3\u02b4\b&\1\2\u02b4\u02b7"+
		"\5L\'\2\u02b5\u02b7\3\2\2\2\u02b6\u02b3\3\2\2\2\u02b6\u02b5\3\2\2\2\u02b7"+
		"\u02c2\3\2\2\2\u02b8\u02b9\f\6\2\2\u02b9\u02ba\7\n\2\2\u02ba\u02c1\5L"+
		"\'\2\u02bb\u02bc\f\5\2\2\u02bc\u02c1\7;\2\2\u02bd\u02be\f\4\2\2\u02be"+
		"\u02bf\7\n\2\2\u02bf\u02c1\7;\2\2\u02c0\u02b8\3\2\2\2\u02c0\u02bb\3\2"+
		"\2\2\u02c0\u02bd\3\2\2\2\u02c1\u02c4\3\2\2\2\u02c2\u02c0\3\2\2\2\u02c2"+
		"\u02c3\3\2\2\2\u02c3K\3\2\2\2\u02c4\u02c2\3\2\2\2\u02c5\u02c6\5B\"\2\u02c6"+
		"\u02c7\7F\2\2\u02c7M\3\2\2\2\u02c8\u02d6\5P)\2\u02c9\u02d6\5X-\2\u02ca"+
		"\u02d6\5b\62\2\u02cb\u02d6\5f\64\2\u02cc\u02d6\5h\65\2\u02cd\u02d6\5j"+
		"\66\2\u02ce\u02d6\5l\67\2\u02cf\u02d6\5n8\2\u02d0\u02d6\7\62\2\2\u02d1"+
		"\u02d6\7\63\2\2\u02d2\u02d3\7\64\2\2\u02d3\u02d6\5N(\2\u02d4\u02d6\7\64"+
		"\2\2\u02d5\u02c8\3\2\2\2\u02d5\u02c9\3\2\2\2\u02d5\u02ca\3\2\2\2\u02d5"+
		"\u02cb\3\2\2\2\u02d5\u02cc\3\2\2\2\u02d5\u02cd\3\2\2\2\u02d5\u02ce\3\2"+
		"\2\2\u02d5\u02cf\3\2\2\2\u02d5\u02d0\3\2\2\2\u02d5\u02d1\3\2\2\2\u02d5"+
		"\u02d2\3\2\2\2\u02d5\u02d4\3\2\2\2\u02d6O\3\2\2\2\u02d7\u02d8\b)\1\2\u02d8"+
		"\u02dd\5R*\2\u02d9\u02da\7>\2\2\u02da\u02dc\5R*\2\u02db\u02d9\3\2\2\2"+
		"\u02dc\u02df\3\2\2\2\u02dd\u02db\3\2\2\2\u02dd\u02de\3\2\2\2\u02de\u02e2"+
		"\3\2\2\2\u02df\u02dd\3\2\2\2\u02e0\u02e2\5f\64\2\u02e1\u02d7\3\2\2\2\u02e1"+
		"\u02e0\3\2\2\2\u02e2\u02e8\3\2\2\2\u02e3\u02e4\f\3\2\2\u02e4\u02e5\7J"+
		"\2\2\u02e5\u02e7\5N(\2\u02e6\u02e3\3\2\2\2\u02e7\u02ea\3\2\2\2\u02e8\u02e6"+
		"\3\2\2\2\u02e8\u02e9\3\2\2\2\u02e9Q\3\2\2\2\u02ea\u02e8\3\2\2\2\u02eb"+
		"\u02ec\b*\1\2\u02ec\u02f1\5T+\2\u02ed\u02ee\7?\2\2\u02ee\u02f0\5T+\2\u02ef"+
		"\u02ed\3\2\2\2\u02f0\u02f3\3\2\2\2\u02f1\u02ef\3\2\2\2\u02f1\u02f2\3\2"+
		"\2\2\u02f2\u02f9\3\2\2\2\u02f3\u02f1\3\2\2\2\u02f4\u02f5\f\4\2\2\u02f5"+
		"\u02f6\7:\2\2\u02f6\u02f8\5V,\2\u02f7\u02f4\3\2\2\2\u02f8\u02fb\3\2\2"+
		"\2\u02f9\u02f7\3\2\2\2\u02f9\u02fa\3\2\2\2\u02faS\3\2\2\2\u02fb\u02f9"+
		"\3\2\2\2\u02fc\u02fd\7>\2\2\u02fd\u0300\5V,\2\u02fe\u0300\5V,\2\u02ff"+
		"\u02fc\3\2\2\2\u02ff\u02fe\3\2\2\2\u0300U\3\2\2\2\u0301\u0302\b,\1\2\u0302"+
		"\u0303\7E\2\2\u0303\u032b\5V,\f\u0304\u0305\7\66\2\2\u0305\u032b\5`\61"+
		"\2\u0306\u0307\7\66\2\2\u0307\u0308\5D#\2\u0308\u0309\7\f\2\2\u0309\u030a"+
		"\5N(\2\u030a\u030b\7\r\2\2\u030b\u032b\3\2\2\2\u030c\u030d\7\66\2\2\u030d"+
		"\u030e\7F\2\2\u030e\u030f\5\24\13\2\u030f\u0310\7\20\2\2\u0310\u0311\5"+
		"v<\2\u0311\u0312\7\21\2\2\u0312\u032b\3\2\2\2\u0313\u0314\5F$\2\u0314"+
		"\u0315\7\30\2\2\u0315\u0316\5`\61\2\u0316\u032b\3\2\2\2\u0317\u032b\5"+
		"r:\2\u0318\u032b\5`\61\2\u0319\u032b\7F\2\2\u031a\u032b\5t;\2\u031b\u031c"+
		"\7\20\2\2\u031c\u031d\5P)\2\u031d\u031e\7\21\2\2\u031e\u032b\3\2\2\2\u031f"+
		"\u0320\7E\2\2\u0320\u0321\5N(\2\u0321\u0322\7\f\2\2\u0322\u0323\5N(\2"+
		"\u0323\u0324\7\r\2\2\u0324\u032b\3\2\2\2\u0325\u0326\7E\2\2\u0326\u0327"+
		"\5N(\2\u0327\u0328\7\30\2\2\u0328\u0329\7F\2\2\u0329\u032b\3\2\2\2\u032a"+
		"\u0301\3\2\2\2\u032a\u0304\3\2\2\2\u032a\u0306\3\2\2\2\u032a\u030c\3\2"+
		"\2\2\u032a\u0313\3\2\2\2\u032a\u0317\3\2\2\2\u032a\u0318\3\2\2\2\u032a"+
		"\u0319\3\2\2\2\u032a\u031a\3\2\2\2\u032a\u031b\3\2\2\2\u032a\u031f\3\2"+
		"\2\2\u032a\u0325\3\2\2\2\u032b\u034d\3\2\2\2\u032c\u032d\f\23\2\2\u032d"+
		"\u032e\7\30\2\2\u032e\u034c\5`\61\2\u032f\u0330\f\21\2\2\u0330\u0331\7"+
		"\30\2\2\u0331\u034c\7F\2\2\u0332\u0333\f\r\2\2\u0333\u034c\7E\2\2\u0334"+
		"\u0335\f\t\2\2\u0335\u0336\7\f\2\2\u0336\u0337\5N(\2\u0337\u0338\7\r\2"+
		"\2\u0338\u034c\3\2\2\2\u0339\u033a\f\b\2\2\u033a\u033b\7\f\2\2\u033b\u033c"+
		"\5N(\2\u033c\u033d\7\r\2\2\u033d\u033e\7E\2\2\u033e\u034c\3\2\2\2\u033f"+
		"\u0340\f\5\2\2\u0340\u0341\7\30\2\2\u0341\u0342\7F\2\2\u0342\u034c\7E"+
		"\2\2\u0343\u0344\f\4\2\2\u0344\u0345\7\30\2\2\u0345\u034c\7\67\2\2\u0346"+
		"\u0347\f\3\2\2\u0347\u0348\7\30\2\2\u0348\u0349\7\67\2\2\u0349\u034a\7"+
		"\20\2\2\u034a\u034c\7\21\2\2\u034b\u032c\3\2\2\2\u034b\u032f\3\2\2\2\u034b"+
		"\u0332\3\2\2\2\u034b\u0334\3\2\2\2\u034b\u0339\3\2\2\2\u034b\u033f\3\2"+
		"\2\2\u034b\u0343\3\2\2\2\u034b\u0346\3\2\2\2\u034c\u034f\3\2\2\2\u034d"+
		"\u034b\3\2\2\2\u034d\u034e\3\2\2\2\u034eW\3\2\2\2\u034f\u034d\3\2\2\2"+
		"\u0350\u0351\b-\1\2\u0351\u0356\5Z.\2\u0352\u0353\7<\2\2\u0353\u0355\5"+
		"Z.\2\u0354\u0352\3\2\2\2\u0355\u0358\3\2\2\2\u0356\u0354\3\2\2\2\u0356"+
		"\u0357\3\2\2\2\u0357\u0365\3\2\2\2\u0358\u0356\3\2\2\2\u0359\u0365\5f"+
		"\64\2\u035a\u035b\5P)\2\u035b\u035c\t\4\2\2\u035c\u035d\t\5\2\2\u035d"+
		"\u035e\5P)\2\u035e\u0365\3\2\2\2\u035f\u0360\5P)\2\u0360\u0361\t\6\2\2"+
		"\u0361\u0362\5P)\2\u0362\u0365\3\2\2\2\u0363\u0365\5P)\2\u0364\u0350\3"+
		"\2\2\2\u0364\u0359\3\2\2\2\u0364\u035a\3\2\2\2\u0364\u035f\3\2\2\2\u0364"+
		"\u0363\3\2\2\2\u0365\u036e\3\2\2\2\u0366\u0367\f\7\2\2\u0367\u0368\t\7"+
		"\2\2\u0368\u036d\5X-\b\u0369\u036a\f\4\2\2\u036a\u036b\7J\2\2\u036b\u036d"+
		"\5N(\2\u036c\u0366\3\2\2\2\u036c\u0369\3\2\2\2\u036d\u0370\3\2\2\2\u036e"+
		"\u036c\3\2\2\2\u036e\u036f\3\2\2\2\u036fY\3\2\2\2\u0370\u036e\3\2\2\2"+
		"\u0371\u0376\5\\/\2\u0372\u0373\7=\2\2\u0373\u0375\5\\/\2\u0374\u0372"+
		"\3\2\2\2\u0375\u0378\3\2\2\2\u0376\u0374\3\2\2\2\u0376\u0377\3\2\2\2\u0377"+
		"[\3\2\2\2\u0378\u0376\3\2\2\2\u0379\u037a\7D\2\2\u037a\u037d\5X-\2\u037b"+
		"\u037d\5^\60\2\u037c\u0379\3\2\2\2\u037c\u037b\3\2\2\2\u037d]\3\2\2\2"+
		"\u037e\u0386\5t;\2\u037f\u0386\7F\2\2\u0380\u0386\5r:\2\u0381\u0382\7"+
		"\20\2\2\u0382\u0383\5X-\2\u0383\u0384\7\21\2\2\u0384\u0386\3\2\2\2\u0385"+
		"\u037e\3\2\2\2\u0385\u037f\3\2\2\2\u0385\u0380\3\2\2\2\u0385\u0381\3\2"+
		"\2\2\u0386_\3\2\2\2\u0387\u0388\5:\36\2\u0388\u0389\7\20\2\2\u0389\u038a"+
		"\5v<\2\u038a\u038b\7\21\2\2\u038b\u0392\3\2\2\2\u038c\u038d\5D#\2\u038d"+
		"\u038e\7\20\2\2\u038e\u038f\5v<\2\u038f\u0390\7\21\2\2\u0390\u0392\3\2"+
		"\2\2\u0391\u0387\3\2\2\2\u0391\u038c\3\2\2\2\u0392a\3\2\2\2\u0393\u0394"+
		"\7\4\2\2\u0394\u0395\5\64\33\2\u0395\u0396\7\5\2\2\u0396\u039a\3\2\2\2"+
		"\u0397\u0398\7\4\2\2\u0398\u039a\7\5\2\2\u0399\u0393\3\2\2\2\u0399\u0397"+
		"\3\2\2\2\u039ac\3\2\2\2\u039b\u039e\5N(\2\u039c\u039e\3\2\2\2\u039d\u039b"+
		"\3\2\2\2\u039d\u039c\3\2\2\2\u039ee\3\2\2\2\u039f\u03a0\7%\2\2\u03a0\u03a1"+
		"\7\20\2\2\u03a1\u03a2\5N(\2\u03a2\u03a3\7\21\2\2\u03a3\u03a4\5N(\2\u03a4"+
		"\u03ae\3\2\2\2\u03a5\u03a6\7%\2\2\u03a6\u03a7\7\20\2\2\u03a7\u03a8\5N"+
		"(\2\u03a8\u03a9\7\21\2\2\u03a9\u03aa\5N(\2\u03aa\u03ab\7&\2\2\u03ab\u03ac"+
		"\5N(\2\u03ac\u03ae\3\2\2\2\u03ad\u039f\3\2\2\2\u03ad\u03a5\3\2\2\2\u03ae"+
		"g\3\2\2\2\u03af\u03b0\7\'\2\2\u03b0\u03b1\7\20\2\2\u03b1\u03b2\5N(\2\u03b2"+
		"\u03b3\7\16\2\2\u03b3\u03b4\5N(\2\u03b4\u03b5\7\16\2\2\u03b5\u03b6\5N"+
		"(\2\u03b6\u03b7\7\21\2\2\u03b7\u03b8\5N(\2\u03b8\u03d3\3\2\2\2\u03b9\u03ba"+
		"\7\'\2\2\u03ba\u03bb\7\20\2\2\u03bb\u03bc\5> \2\u03bc\u03bd\5N(\2\u03bd"+
		"\u03be\7\16\2\2\u03be\u03bf\5N(\2\u03bf\u03c0\7\21\2\2\u03c0\u03c1\5N"+
		"(\2\u03c1\u03d3\3\2\2\2\u03c2\u03c3\7\'\2\2\u03c3\u03c4\7\20\2\2\u03c4"+
		"\u03c5\7F\2\2\u03c5\u03c6\7(\2\2\u03c6\u03c7\5N(\2\u03c7\u03c8\7\21\2"+
		"\2\u03c8\u03c9\5N(\2\u03c9\u03d3\3\2\2\2\u03ca\u03cb\7\'\2\2\u03cb\u03cc"+
		"\7\20\2\2\u03cc\u03cd\5@!\2\u03cd\u03ce\7(\2\2\u03ce\u03cf\5N(\2\u03cf"+
		"\u03d0\7\21\2\2\u03d0\u03d1\5N(\2\u03d1\u03d3\3\2\2\2\u03d2\u03af\3\2"+
		"\2\2\u03d2\u03b9\3\2\2\2\u03d2\u03c2\3\2\2\2\u03d2\u03ca\3\2\2\2\u03d3"+
		"i\3\2\2\2\u03d4\u03d5\7)\2\2\u03d5\u03d6\7\20\2\2\u03d6\u03d7\5N(\2\u03d7"+
		"\u03d8\7\21\2\2\u03d8\u03d9\5N(\2\u03d9k\3\2\2\2\u03da\u03db\7*\2\2\u03db"+
		"\u03dc\5N(\2\u03dc\u03dd\7)\2\2\u03dd\u03de\7\20\2\2\u03de\u03df\5N(\2"+
		"\u03df\u03e0\7\21\2\2\u03e0m\3\2\2\2\u03e1\u03e2\7/\2\2\u03e2\u03e3\7"+
		"\20\2\2\u03e3\u03e4\5N(\2\u03e4\u03e5\7\21\2\2\u03e5\u03e9\7\4\2\2\u03e6"+
		"\u03e8\5p9\2\u03e7\u03e6\3\2\2\2\u03e8\u03eb\3\2\2\2\u03e9\u03e7\3\2\2"+
		"\2\u03e9\u03ea\3\2\2\2\u03ea\u03ec\3\2\2\2\u03eb\u03e9\3\2\2\2\u03ec\u03ed"+
		"\7\5\2\2\u03edo\3\2\2\2\u03ee\u03ef\7\60\2\2\u03ef\u03f2\5t;\2\u03f0\u03f2"+
		"\7\61\2\2\u03f1\u03ee\3\2\2\2\u03f1\u03f0\3\2\2\2\u03f2\u03f3\3\2\2\2"+
		"\u03f3\u03f4\7(\2\2\u03f4\u03f5\5\64\33\2\u03f5q\3\2\2\2\u03f6\u03f7\7"+
		"8\2\2\u03f7s\3\2\2\2\u03f8\u03f9\t\b\2\2\u03f9u\3\2\2\2\u03fa\u03fb\b"+
		"<\1\2\u03fb\u03fe\5N(\2\u03fc\u03fe\3\2\2\2\u03fd\u03fa\3\2\2\2\u03fd"+
		"\u03fc\3\2\2\2\u03fe\u0404\3\2\2\2\u03ff\u0400\f\4\2\2\u0400\u0401\7\n"+
		"\2\2\u0401\u0403\5N(\2\u0402\u03ff\3\2\2\2\u0403\u0406\3\2\2\2\u0404\u0402"+
		"\3\2\2\2\u0404\u0405\3\2\2\2\u0405w\3\2\2\2\u0406\u0404\3\2\2\2U|\u0083"+
		"\u0089\u008f\u0096\u00a3\u00b2\u00be\u00cc\u00d8\u00e1\u00f2\u00fb\u0106"+
		"\u010e\u0113\u0119\u0121\u016f\u0174\u017d\u0181\u018c\u018f\u0191\u019c"+
		"\u019e\u01e9\u01f2\u01f5\u0200\u0203\u0205\u020b\u0211\u0217\u0221\u0223"+
		"\u0236\u023f\u024a\u024d\u0256\u025e\u0260\u026a\u0272\u0279\u028b\u0295"+
		"\u029b\u02a4\u02ae\u02b0\u02b6\u02c0\u02c2\u02d5\u02dd\u02e1\u02e8\u02f1"+
		"\u02f9\u02ff\u032a\u034b\u034d\u0356\u0364\u036c\u036e\u0376\u037c\u0385"+
		"\u0391\u0399\u039d\u03ad\u03d2\u03e9\u03f1\u03fd\u0404";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}