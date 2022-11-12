// Generated from ./HBaseSQL.g4 by ANTLR 4.5.1

package com.github.CCwexiao.hbase.sdk.dsl.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HBaseSQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, STAR=4, LB=5, RB=6, WHERE=7, SELECT=8, INSERT=9, 
		DELETE=10, INTO=11, VALUES=12, FROM=13, ROWKEY=14, STARTKEY=15, ENDKEY=16, 
		HBASESTARTKEY=17, HBASEENDKEY=18, MAXVERSION=19, LIMIT=20, TS=21, STARTTS=22, 
		ENDTS=23, IS=24, NULL=25, NOT=26, AND=27, OR=28, LESSEQUAL=29, LESS=30, 
		GREATEREQUAL=31, GREATER=32, NOTEQUAL=33, EQUAL=34, ENDER=35, NOTMATCH=36, 
		MATCH=37, IN=38, NOTIN=39, BETWEEN=40, NOTBETWEEN=41, ISNULL=42, ISNOTNULL=43, 
		ISMISSING=44, ISNOTMISSING=45, TEXT=46, SPACE=47, COMMENT_INPUT=48, LINE_COMMENT=49;
	public static final int
		RULE_prog = 0, RULE_inserthqlc = 1, RULE_selecthqlc = 2, RULE_deletehqlc = 3, 
		RULE_wherec = 4, RULE_conditionc = 5, RULE_rowKeyRange = 6, RULE_rowKeyExp = 7, 
		RULE_tsrange = 8, RULE_tsexp = 9, RULE_selectCidList = 10, RULE_cidList = 11, 
		RULE_cid = 12, RULE_funcname = 13, RULE_constantList = 14, RULE_insertValueList = 15, 
		RULE_insertValue = 16, RULE_maxVersionExp = 17, RULE_limitExp = 18, RULE_tableName = 19, 
		RULE_maxversion = 20, RULE_constant = 21, RULE_var = 22;
	public static final String[] ruleNames = {
		"prog", "inserthqlc", "selecthqlc", "deletehqlc", "wherec", "conditionc", 
		"rowKeyRange", "rowKeyExp", "tsrange", "tsexp", "selectCidList", "cidList", 
		"cid", "funcname", "constantList", "insertValueList", "insertValue", "maxVersionExp", 
		"limitExp", "tableName", "maxversion", "constant", "var"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", "'''", "'#'", "'*'", "'('", "')'", null, null, null, null, 
		null, null, null, null, null, null, "'hbasestartkey'", "'hbaseendkey'", 
		null, null, "'ts'", "'startTS'", "'endTS'", null, "'null'", null, null, 
		null, "'lessequal'", "'less'", "'greaterequal'", "'greater'", "'notequal'", 
		"'equal'", "';'", "'notmatch'", "'match'", "'in'", "'notin'", "'between'", 
		"'notbetween'", "'isnull'", "'isnotnull'", "'ismissing'", "'isnotmissing'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, "STAR", "LB", "RB", "WHERE", "SELECT", "INSERT", 
		"DELETE", "INTO", "VALUES", "FROM", "ROWKEY", "STARTKEY", "ENDKEY", "HBASESTARTKEY", 
		"HBASEENDKEY", "MAXVERSION", "LIMIT", "TS", "STARTTS", "ENDTS", "IS", 
		"NULL", "NOT", "AND", "OR", "LESSEQUAL", "LESS", "GREATEREQUAL", "GREATER", 
		"NOTEQUAL", "EQUAL", "ENDER", "NOTMATCH", "MATCH", "IN", "NOTIN", "BETWEEN", 
		"NOTBETWEEN", "ISNULL", "ISNOTNULL", "ISMISSING", "ISNOTMISSING", "TEXT", 
		"SPACE", "COMMENT_INPUT", "LINE_COMMENT"
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
	public String getGrammarFileName() { return "HBaseSQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public HBaseSQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
	 
		public ProgContext() { }
		public void copyFrom(ProgContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SelectHqlClContext extends ProgContext {
		public SelecthqlcContext selecthqlc() {
			return getRuleContext(SelecthqlcContext.class,0);
		}
		public SelectHqlClContext(ProgContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitSelectHqlCl(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InsertHqlClContext extends ProgContext {
		public InserthqlcContext inserthqlc() {
			return getRuleContext(InserthqlcContext.class,0);
		}
		public InsertHqlClContext(ProgContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitInsertHqlCl(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DeleteHqlClContext extends ProgContext {
		public DeletehqlcContext deletehqlc() {
			return getRuleContext(DeletehqlcContext.class,0);
		}
		public DeleteHqlClContext(ProgContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitDeleteHqlCl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		try {
			setState(49);
			switch (_input.LA(1)) {
			case INSERT:
				_localctx = new InsertHqlClContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(46);
				inserthqlc();
				}
				break;
			case SELECT:
				_localctx = new SelectHqlClContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(47);
				selecthqlc();
				}
				break;
			case DELETE:
				_localctx = new DeleteHqlClContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(48);
				deletehqlc();
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

	public static class InserthqlcContext extends ParserRuleContext {
		public TerminalNode INSERT() { return getToken(HBaseSQLParser.INSERT, 0); }
		public TerminalNode INTO() { return getToken(HBaseSQLParser.INTO, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public CidListContext cidList() {
			return getRuleContext(CidListContext.class,0);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public TerminalNode VALUES() { return getToken(HBaseSQLParser.VALUES, 0); }
		public InsertValueListContext insertValueList() {
			return getRuleContext(InsertValueListContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(HBaseSQLParser.WHERE, 0); }
		public TerminalNode ROWKEY() { return getToken(HBaseSQLParser.ROWKEY, 0); }
		public List<TerminalNode> IS() { return getTokens(HBaseSQLParser.IS); }
		public TerminalNode IS(int i) {
			return getToken(HBaseSQLParser.IS, i);
		}
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public TerminalNode TS() { return getToken(HBaseSQLParser.TS, 0); }
		public TsexpContext tsexp() {
			return getRuleContext(TsexpContext.class,0);
		}
		public InserthqlcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inserthqlc; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitInserthqlc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InserthqlcContext inserthqlc() throws RecognitionException {
		InserthqlcContext _localctx = new InserthqlcContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_inserthqlc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(INSERT);
			setState(52);
			match(INTO);
			setState(53);
			tableName();
			setState(54);
			match(LB);
			setState(55);
			cidList();
			setState(56);
			match(RB);
			setState(57);
			match(VALUES);
			setState(58);
			insertValueList();
			setState(59);
			match(WHERE);
			setState(60);
			match(ROWKEY);
			setState(61);
			match(IS);
			setState(62);
			rowKeyExp();
			setState(66);
			_la = _input.LA(1);
			if (_la==TS) {
				{
				setState(63);
				match(TS);
				setState(64);
				match(IS);
				setState(65);
				tsexp();
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

	public static class SelecthqlcContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(HBaseSQLParser.SELECT, 0); }
		public SelectCidListContext selectCidList() {
			return getRuleContext(SelectCidListContext.class,0);
		}
		public TerminalNode FROM() { return getToken(HBaseSQLParser.FROM, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(HBaseSQLParser.WHERE, 0); }
		public RowKeyRangeContext rowKeyRange() {
			return getRuleContext(RowKeyRangeContext.class,0);
		}
		public WherecContext wherec() {
			return getRuleContext(WherecContext.class,0);
		}
		public MaxVersionExpContext maxVersionExp() {
			return getRuleContext(MaxVersionExpContext.class,0);
		}
		public TsrangeContext tsrange() {
			return getRuleContext(TsrangeContext.class,0);
		}
		public LimitExpContext limitExp() {
			return getRuleContext(LimitExpContext.class,0);
		}
		public SelecthqlcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selecthqlc; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitSelecthqlc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelecthqlcContext selecthqlc() throws RecognitionException {
		SelecthqlcContext _localctx = new SelecthqlcContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_selecthqlc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(SELECT);
			setState(69);
			selectCidList();
			setState(70);
			match(FROM);
			setState(71);
			tableName();
			setState(72);
			match(WHERE);
			setState(74);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ROWKEY) | (1L << STARTKEY) | (1L << ENDKEY))) != 0)) {
				{
				setState(73);
				rowKeyRange();
				}
			}

			setState(77);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(76);
				wherec();
				}
				break;
			}
			setState(80);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(79);
				maxVersionExp();
				}
				break;
			}
			setState(83);
			_la = _input.LA(1);
			if (_la==LB) {
				{
				setState(82);
				tsrange();
				}
			}

			setState(86);
			_la = _input.LA(1);
			if (_la==LIMIT) {
				{
				setState(85);
				limitExp();
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

	public static class DeletehqlcContext extends ParserRuleContext {
		public TerminalNode DELETE() { return getToken(HBaseSQLParser.DELETE, 0); }
		public SelectCidListContext selectCidList() {
			return getRuleContext(SelectCidListContext.class,0);
		}
		public TerminalNode FROM() { return getToken(HBaseSQLParser.FROM, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(HBaseSQLParser.WHERE, 0); }
		public RowKeyRangeContext rowKeyRange() {
			return getRuleContext(RowKeyRangeContext.class,0);
		}
		public WherecContext wherec() {
			return getRuleContext(WherecContext.class,0);
		}
		public TerminalNode TS() { return getToken(HBaseSQLParser.TS, 0); }
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public TsexpContext tsexp() {
			return getRuleContext(TsexpContext.class,0);
		}
		public DeletehqlcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deletehqlc; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitDeletehqlc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeletehqlcContext deletehqlc() throws RecognitionException {
		DeletehqlcContext _localctx = new DeletehqlcContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_deletehqlc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(DELETE);
			setState(89);
			selectCidList();
			setState(90);
			match(FROM);
			setState(91);
			tableName();
			setState(92);
			match(WHERE);
			setState(94);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ROWKEY) | (1L << STARTKEY) | (1L << ENDKEY))) != 0)) {
				{
				setState(93);
				rowKeyRange();
				}
			}

			setState(97);
			_la = _input.LA(1);
			if (_la==LB || _la==TEXT) {
				{
				setState(96);
				wherec();
				}
			}

			setState(102);
			_la = _input.LA(1);
			if (_la==TS) {
				{
				setState(99);
				match(TS);
				setState(100);
				match(IS);
				setState(101);
				tsexp();
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

	public static class WherecContext extends ParserRuleContext {
		public ConditioncContext conditionc() {
			return getRuleContext(ConditioncContext.class,0);
		}
		public WherecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_wherec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitWherec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WherecContext wherec() throws RecognitionException {
		WherecContext _localctx = new WherecContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_wherec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			conditionc(0);
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

	public static class ConditioncContext extends ParserRuleContext {
		public ConditioncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionc; }
	 
		public ConditioncContext() { }
		public void copyFrom(ConditioncContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NotequalvarContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode NOTEQUAL() { return getToken(HBaseSQLParser.NOTEQUAL, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public NotequalvarContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitNotequalvar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessequalvarContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode LESSEQUAL() { return getToken(HBaseSQLParser.LESSEQUAL, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public LessequalvarContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitLessequalvar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotmatchvarContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode NOTMATCH() { return getToken(HBaseSQLParser.NOTMATCH, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public NotmatchvarContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitNotmatchvar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotbetweenvarContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode NOTBETWEEN() { return getToken(HBaseSQLParser.NOTBETWEEN, 0); }
		public List<VarContext> var() {
			return getRuleContexts(VarContext.class);
		}
		public VarContext var(int i) {
			return getRuleContext(VarContext.class,i);
		}
		public TerminalNode AND() { return getToken(HBaseSQLParser.AND, 0); }
		public NotbetweenvarContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitNotbetweenvar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsmissingcContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode ISMISSING() { return getToken(HBaseSQLParser.ISMISSING, 0); }
		public IsmissingcContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitIsmissingc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndconditionContext extends ConditioncContext {
		public List<ConditioncContext> conditionc() {
			return getRuleContexts(ConditioncContext.class);
		}
		public ConditioncContext conditionc(int i) {
			return getRuleContext(ConditioncContext.class,i);
		}
		public TerminalNode AND() { return getToken(HBaseSQLParser.AND, 0); }
		public AndconditionContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitAndcondition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GreaterequalconstantContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode GREATEREQUAL() { return getToken(HBaseSQLParser.GREATEREQUAL, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public GreaterequalconstantContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitGreaterequalconstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotmatchconstantContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode NOTMATCH() { return getToken(HBaseSQLParser.NOTMATCH, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public NotmatchconstantContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitNotmatchconstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualvarContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode EQUAL() { return getToken(HBaseSQLParser.EQUAL, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public EqualvarContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitEqualvar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GreaterconstantContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode GREATER() { return getToken(HBaseSQLParser.GREATER, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public GreaterconstantContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitGreaterconstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BetweenvarContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode BETWEEN() { return getToken(HBaseSQLParser.BETWEEN, 0); }
		public List<VarContext> var() {
			return getRuleContexts(VarContext.class);
		}
		public VarContext var(int i) {
			return getRuleContext(VarContext.class,i);
		}
		public TerminalNode AND() { return getToken(HBaseSQLParser.AND, 0); }
		public BetweenvarContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitBetweenvar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BetweenconstantContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode BETWEEN() { return getToken(HBaseSQLParser.BETWEEN, 0); }
		public List<ConstantContext> constant() {
			return getRuleContexts(ConstantContext.class);
		}
		public ConstantContext constant(int i) {
			return getRuleContext(ConstantContext.class,i);
		}
		public TerminalNode AND() { return getToken(HBaseSQLParser.AND, 0); }
		public BetweenconstantContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitBetweenconstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsnotnullcContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode ISNOTNULL() { return getToken(HBaseSQLParser.ISNOTNULL, 0); }
		public IsnotnullcContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitIsnotnullc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InconstantlistContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode IN() { return getToken(HBaseSQLParser.IN, 0); }
		public ConstantListContext constantList() {
			return getRuleContext(ConstantListContext.class,0);
		}
		public InconstantlistContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitInconstantlist(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotbetweenconstantContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode NOTBETWEEN() { return getToken(HBaseSQLParser.NOTBETWEEN, 0); }
		public List<ConstantContext> constant() {
			return getRuleContexts(ConstantContext.class);
		}
		public ConstantContext constant(int i) {
			return getRuleContext(ConstantContext.class,i);
		}
		public TerminalNode AND() { return getToken(HBaseSQLParser.AND, 0); }
		public NotbetweenconstantContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitNotbetweenconstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsnotmissingcContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode ISNOTMISSING() { return getToken(HBaseSQLParser.ISNOTMISSING, 0); }
		public IsnotmissingcContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitIsnotmissingc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotinconstantlistContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode NOTIN() { return getToken(HBaseSQLParser.NOTIN, 0); }
		public ConstantListContext constantList() {
			return getRuleContext(ConstantListContext.class,0);
		}
		public NotinconstantlistContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitNotinconstantlist(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrconditionContext extends ConditioncContext {
		public List<ConditioncContext> conditionc() {
			return getRuleContexts(ConditioncContext.class);
		}
		public ConditioncContext conditionc(int i) {
			return getRuleContext(ConditioncContext.class,i);
		}
		public TerminalNode OR() { return getToken(HBaseSQLParser.OR, 0); }
		public OrconditionContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitOrcondition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsnullcContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode ISNULL() { return getToken(HBaseSQLParser.ISNULL, 0); }
		public IsnullcContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitIsnullc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualconstantContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode EQUAL() { return getToken(HBaseSQLParser.EQUAL, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public EqualconstantContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitEqualconstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GreaterequalvarContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode GREATEREQUAL() { return getToken(HBaseSQLParser.GREATEREQUAL, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public GreaterequalvarContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitGreaterequalvar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessvarContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode LESS() { return getToken(HBaseSQLParser.LESS, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public LessvarContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitLessvar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotequalconstantContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode NOTEQUAL() { return getToken(HBaseSQLParser.NOTEQUAL, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public NotequalconstantContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitNotequalconstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MatchvarContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode MATCH() { return getToken(HBaseSQLParser.MATCH, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public MatchvarContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitMatchvar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InvarlistContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode IN() { return getToken(HBaseSQLParser.IN, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public InvarlistContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitInvarlist(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessconstantContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode LESS() { return getToken(HBaseSQLParser.LESS, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public LessconstantContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitLessconstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConditionwrapperContext extends ConditioncContext {
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public ConditioncContext conditionc() {
			return getRuleContext(ConditioncContext.class,0);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public ConditionwrapperContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitConditionwrapper(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotinvarlistContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode NOTIN() { return getToken(HBaseSQLParser.NOTIN, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public NotinvarlistContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitNotinvarlist(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessequalconstantContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode LESSEQUAL() { return getToken(HBaseSQLParser.LESSEQUAL, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public LessequalconstantContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitLessequalconstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GreatervarContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode GREATER() { return getToken(HBaseSQLParser.GREATER, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public GreatervarContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitGreatervar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MatchconstantContext extends ConditioncContext {
		public CidContext cid() {
			return getRuleContext(CidContext.class,0);
		}
		public TerminalNode MATCH() { return getToken(HBaseSQLParser.MATCH, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public MatchconstantContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitMatchconstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditioncContext conditionc() throws RecognitionException {
		return conditionc(0);
	}

	private ConditioncContext conditionc(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConditioncContext _localctx = new ConditioncContext(_ctx, _parentState);
		ConditioncContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_conditionc, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				_localctx = new ConditionwrapperContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(107);
				match(LB);
				setState(108);
				conditionc(0);
				setState(109);
				match(RB);
				}
				break;
			case 2:
				{
				_localctx = new EqualconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(111);
				cid();
				setState(112);
				match(EQUAL);
				setState(113);
				constant();
				}
				break;
			case 3:
				{
				_localctx = new EqualvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(115);
				cid();
				setState(116);
				match(EQUAL);
				setState(117);
				var();
				}
				break;
			case 4:
				{
				_localctx = new LessconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(119);
				cid();
				setState(120);
				match(LESS);
				setState(121);
				constant();
				}
				break;
			case 5:
				{
				_localctx = new LessvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(123);
				cid();
				setState(124);
				match(LESS);
				setState(125);
				var();
				}
				break;
			case 6:
				{
				_localctx = new GreaterconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(127);
				cid();
				setState(128);
				match(GREATER);
				setState(129);
				constant();
				}
				break;
			case 7:
				{
				_localctx = new GreatervarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(131);
				cid();
				setState(132);
				match(GREATER);
				setState(133);
				var();
				}
				break;
			case 8:
				{
				_localctx = new LessequalconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(135);
				cid();
				setState(136);
				match(LESSEQUAL);
				setState(137);
				constant();
				}
				break;
			case 9:
				{
				_localctx = new LessequalvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(139);
				cid();
				setState(140);
				match(LESSEQUAL);
				setState(141);
				var();
				}
				break;
			case 10:
				{
				_localctx = new GreaterequalconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(143);
				cid();
				setState(144);
				match(GREATEREQUAL);
				setState(145);
				constant();
				}
				break;
			case 11:
				{
				_localctx = new GreaterequalvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(147);
				cid();
				setState(148);
				match(GREATEREQUAL);
				setState(149);
				var();
				}
				break;
			case 12:
				{
				_localctx = new NotequalconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(151);
				cid();
				setState(152);
				match(NOTEQUAL);
				setState(153);
				constant();
				}
				break;
			case 13:
				{
				_localctx = new NotequalvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(155);
				cid();
				setState(156);
				match(NOTEQUAL);
				setState(157);
				var();
				}
				break;
			case 14:
				{
				_localctx = new NotmatchconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(159);
				cid();
				setState(160);
				match(NOTMATCH);
				setState(161);
				constant();
				}
				break;
			case 15:
				{
				_localctx = new NotmatchvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(163);
				cid();
				setState(164);
				match(NOTMATCH);
				setState(165);
				var();
				}
				break;
			case 16:
				{
				_localctx = new MatchconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(167);
				cid();
				setState(168);
				match(MATCH);
				setState(169);
				constant();
				}
				break;
			case 17:
				{
				_localctx = new MatchvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(171);
				cid();
				setState(172);
				match(MATCH);
				setState(173);
				var();
				}
				break;
			case 18:
				{
				_localctx = new InconstantlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(175);
				cid();
				setState(176);
				match(IN);
				setState(177);
				constantList();
				}
				break;
			case 19:
				{
				_localctx = new InvarlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(179);
				cid();
				setState(180);
				match(IN);
				setState(181);
				var();
				}
				break;
			case 20:
				{
				_localctx = new NotinconstantlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(183);
				cid();
				setState(184);
				match(NOTIN);
				setState(185);
				constantList();
				}
				break;
			case 21:
				{
				_localctx = new NotinvarlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(187);
				cid();
				setState(188);
				match(NOTIN);
				setState(189);
				var();
				}
				break;
			case 22:
				{
				_localctx = new BetweenconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(191);
				cid();
				setState(192);
				match(BETWEEN);
				setState(193);
				constant();
				setState(194);
				match(AND);
				setState(195);
				constant();
				}
				break;
			case 23:
				{
				_localctx = new BetweenvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(197);
				cid();
				setState(198);
				match(BETWEEN);
				setState(199);
				var();
				setState(200);
				match(AND);
				setState(201);
				var();
				}
				break;
			case 24:
				{
				_localctx = new NotbetweenconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(203);
				cid();
				setState(204);
				match(NOTBETWEEN);
				setState(205);
				constant();
				setState(206);
				match(AND);
				setState(207);
				constant();
				}
				break;
			case 25:
				{
				_localctx = new NotbetweenvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(209);
				cid();
				setState(210);
				match(NOTBETWEEN);
				setState(211);
				var();
				setState(212);
				match(AND);
				setState(213);
				var();
				}
				break;
			case 26:
				{
				_localctx = new IsnullcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(215);
				cid();
				setState(216);
				match(ISNULL);
				}
				break;
			case 27:
				{
				_localctx = new IsnotnullcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(218);
				cid();
				setState(219);
				match(ISNOTNULL);
				}
				break;
			case 28:
				{
				_localctx = new IsmissingcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(221);
				cid();
				setState(222);
				match(ISMISSING);
				}
				break;
			case 29:
				{
				_localctx = new IsnotmissingcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(224);
				cid();
				setState(225);
				match(ISNOTMISSING);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(237);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(235);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new AndconditionContext(new ConditioncContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_conditionc);
						setState(229);
						if (!(precpred(_ctx, 30))) throw new FailedPredicateException(this, "precpred(_ctx, 30)");
						setState(230);
						match(AND);
						setState(231);
						conditionc(31);
						}
						break;
					case 2:
						{
						_localctx = new OrconditionContext(new ConditioncContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_conditionc);
						setState(232);
						if (!(precpred(_ctx, 29))) throw new FailedPredicateException(this, "precpred(_ctx, 29)");
						setState(233);
						match(OR);
						setState(234);
						conditionc(30);
						}
						break;
					}
					} 
				}
				setState(239);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
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

	public static class RowKeyRangeContext extends ParserRuleContext {
		public RowKeyRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rowKeyRange; }
	 
		public RowKeyRangeContext() { }
		public void copyFrom(RowKeyRangeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Rowkeyrange_startAndEndContext extends RowKeyRangeContext {
		public TerminalNode STARTKEY() { return getToken(HBaseSQLParser.STARTKEY, 0); }
		public List<TerminalNode> IS() { return getTokens(HBaseSQLParser.IS); }
		public TerminalNode IS(int i) {
			return getToken(HBaseSQLParser.IS, i);
		}
		public List<RowKeyExpContext> rowKeyExp() {
			return getRuleContexts(RowKeyExpContext.class);
		}
		public RowKeyExpContext rowKeyExp(int i) {
			return getRuleContext(RowKeyExpContext.class,i);
		}
		public TerminalNode ENDKEY() { return getToken(HBaseSQLParser.ENDKEY, 0); }
		public Rowkeyrange_startAndEndContext(RowKeyRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkeyrange_startAndEnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkeyrange_endContext extends RowKeyRangeContext {
		public TerminalNode ENDKEY() { return getToken(HBaseSQLParser.ENDKEY, 0); }
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public Rowkeyrange_endContext(RowKeyRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkeyrange_end(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkeyrange_onerowkeyContext extends RowKeyRangeContext {
		public TerminalNode ROWKEY() { return getToken(HBaseSQLParser.ROWKEY, 0); }
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public Rowkeyrange_onerowkeyContext(RowKeyRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkeyrange_onerowkey(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkeyrange_insomekeysContext extends RowKeyRangeContext {
		public TerminalNode ROWKEY() { return getToken(HBaseSQLParser.ROWKEY, 0); }
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public Rowkeyrange_insomekeysContext(RowKeyRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkeyrange_insomekeys(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkeyrange_startContext extends RowKeyRangeContext {
		public TerminalNode STARTKEY() { return getToken(HBaseSQLParser.STARTKEY, 0); }
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public Rowkeyrange_startContext(RowKeyRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkeyrange_start(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RowKeyRangeContext rowKeyRange() throws RecognitionException {
		RowKeyRangeContext _localctx = new RowKeyRangeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_rowKeyRange);
		try {
			setState(260);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new Rowkeyrange_startAndEndContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(240);
				match(STARTKEY);
				setState(241);
				match(IS);
				setState(242);
				rowKeyExp();
				setState(243);
				match(T__0);
				setState(244);
				match(ENDKEY);
				setState(245);
				match(IS);
				setState(246);
				rowKeyExp();
				}
				break;
			case 2:
				_localctx = new Rowkeyrange_startContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(248);
				match(STARTKEY);
				setState(249);
				match(IS);
				setState(250);
				rowKeyExp();
				}
				break;
			case 3:
				_localctx = new Rowkeyrange_endContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(251);
				match(ENDKEY);
				setState(252);
				match(IS);
				setState(253);
				rowKeyExp();
				}
				break;
			case 4:
				_localctx = new Rowkeyrange_onerowkeyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(254);
				match(ROWKEY);
				setState(255);
				match(IS);
				setState(256);
				rowKeyExp();
				}
				break;
			case 5:
				_localctx = new Rowkeyrange_insomekeysContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(257);
				match(ROWKEY);
				setState(258);
				match(IS);
				setState(259);
				rowKeyExp();
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

	public static class RowKeyExpContext extends ParserRuleContext {
		public RowKeyExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rowKeyExp; }
	 
		public RowKeyExpContext() { }
		public void copyFrom(RowKeyExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Rowkey_FuncConstantContext extends RowKeyExpContext {
		public FuncnameContext funcname() {
			return getRuleContext(FuncnameContext.class,0);
		}
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public Rowkey_FuncConstantContext(RowKeyExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkey_FuncConstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkey_inRangeKeyContext extends RowKeyExpContext {
		public FuncnameContext funcname() {
			return getRuleContext(FuncnameContext.class,0);
		}
		public TerminalNode IN() { return getToken(HBaseSQLParser.IN, 0); }
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public List<ConstantContext> constant() {
			return getRuleContexts(ConstantContext.class);
		}
		public ConstantContext constant(int i) {
			return getRuleContext(ConstantContext.class,i);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public Rowkey_inRangeKeyContext(RowKeyExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkey_inRangeKey(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkey_hbaseendContext extends RowKeyExpContext {
		public TerminalNode HBASEENDKEY() { return getToken(HBaseSQLParser.HBASEENDKEY, 0); }
		public Rowkey_hbaseendContext(RowKeyExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkey_hbaseend(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkey_WrapperContext extends RowKeyExpContext {
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public Rowkey_WrapperContext(RowKeyExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkey_Wrapper(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkey_hbasestartContext extends RowKeyExpContext {
		public TerminalNode HBASESTARTKEY() { return getToken(HBaseSQLParser.HBASESTARTKEY, 0); }
		public Rowkey_hbasestartContext(RowKeyExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkey_hbasestart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RowKeyExpContext rowKeyExp() throws RecognitionException {
		RowKeyExpContext _localctx = new RowKeyExpContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_rowKeyExp);
		int _la;
		try {
			setState(286);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new Rowkey_WrapperContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(262);
				match(LB);
				setState(263);
				rowKeyExp();
				setState(264);
				match(RB);
				}
				break;
			case 2:
				_localctx = new Rowkey_FuncConstantContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(266);
				funcname();
				setState(267);
				match(LB);
				setState(268);
				constant();
				setState(269);
				match(RB);
				}
				break;
			case 3:
				_localctx = new Rowkey_inRangeKeyContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(271);
				funcname();
				setState(272);
				match(IN);
				setState(273);
				match(LB);
				setState(274);
				constant();
				setState(279);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(275);
					match(T__0);
					setState(276);
					constant();
					}
					}
					setState(281);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(282);
				match(RB);
				}
				break;
			case 4:
				_localctx = new Rowkey_hbasestartContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(284);
				match(HBASESTARTKEY);
				}
				break;
			case 5:
				_localctx = new Rowkey_hbaseendContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(285);
				match(HBASEENDKEY);
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

	public static class TsrangeContext extends ParserRuleContext {
		public TsrangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tsrange; }
	 
		public TsrangeContext() { }
		public void copyFrom(TsrangeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Tsrange_startContext extends TsrangeContext {
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public TerminalNode STARTTS() { return getToken(HBaseSQLParser.STARTTS, 0); }
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public TsexpContext tsexp() {
			return getRuleContext(TsexpContext.class,0);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public Tsrange_startContext(TsrangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTsrange_start(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Tsrange_endContext extends TsrangeContext {
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public TerminalNode ENDTS() { return getToken(HBaseSQLParser.ENDTS, 0); }
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public TsexpContext tsexp() {
			return getRuleContext(TsexpContext.class,0);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public Tsrange_endContext(TsrangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTsrange_end(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Tsrange_startAndEndContext extends TsrangeContext {
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public TerminalNode STARTTS() { return getToken(HBaseSQLParser.STARTTS, 0); }
		public List<TerminalNode> IS() { return getTokens(HBaseSQLParser.IS); }
		public TerminalNode IS(int i) {
			return getToken(HBaseSQLParser.IS, i);
		}
		public List<TsexpContext> tsexp() {
			return getRuleContexts(TsexpContext.class);
		}
		public TsexpContext tsexp(int i) {
			return getRuleContext(TsexpContext.class,i);
		}
		public TerminalNode ENDTS() { return getToken(HBaseSQLParser.ENDTS, 0); }
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public Tsrange_startAndEndContext(TsrangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTsrange_startAndEnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TsrangeContext tsrange() throws RecognitionException {
		TsrangeContext _localctx = new TsrangeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_tsrange);
		try {
			setState(310);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new Tsrange_startAndEndContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(288);
				match(LB);
				setState(289);
				match(STARTTS);
				setState(290);
				match(IS);
				setState(291);
				tsexp();
				setState(292);
				match(T__0);
				setState(293);
				match(ENDTS);
				setState(294);
				match(IS);
				setState(295);
				tsexp();
				setState(296);
				match(RB);
				}
				break;
			case 2:
				_localctx = new Tsrange_startContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(298);
				match(LB);
				setState(299);
				match(STARTTS);
				setState(300);
				match(IS);
				setState(301);
				tsexp();
				setState(302);
				match(RB);
				}
				break;
			case 3:
				_localctx = new Tsrange_endContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(304);
				match(LB);
				setState(305);
				match(ENDTS);
				setState(306);
				match(IS);
				setState(307);
				tsexp();
				setState(308);
				match(RB);
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

	public static class TsexpContext extends ParserRuleContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public TsexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tsexp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTsexp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TsexpContext tsexp() throws RecognitionException {
		TsexpContext _localctx = new TsexpContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_tsexp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			constant();
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

	public static class SelectCidListContext extends ParserRuleContext {
		public SelectCidListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectCidList; }
	 
		public SelectCidListContext() { }
		public void copyFrom(SelectCidListContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CidList_CidListContext extends SelectCidListContext {
		public CidListContext cidList() {
			return getRuleContext(CidListContext.class,0);
		}
		public CidList_CidListContext(SelectCidListContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitCidList_CidList(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CidList_StarContext extends SelectCidListContext {
		public TerminalNode STAR() { return getToken(HBaseSQLParser.STAR, 0); }
		public CidList_StarContext(SelectCidListContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitCidList_Star(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CidList_RegxContext extends SelectCidListContext {
		public TerminalNode TEXT() { return getToken(HBaseSQLParser.TEXT, 0); }
		public CidList_RegxContext(SelectCidListContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitCidList_Regx(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectCidListContext selectCidList() throws RecognitionException {
		SelectCidListContext _localctx = new SelectCidListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_selectCidList);
		try {
			setState(317);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				_localctx = new CidList_CidListContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(314);
				cidList();
				}
				break;
			case 2:
				_localctx = new CidList_StarContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(315);
				match(STAR);
				}
				break;
			case 3:
				_localctx = new CidList_RegxContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(316);
				match(TEXT);
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

	public static class CidListContext extends ParserRuleContext {
		public List<CidContext> cid() {
			return getRuleContexts(CidContext.class);
		}
		public CidContext cid(int i) {
			return getRuleContext(CidContext.class,i);
		}
		public CidListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cidList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitCidList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CidListContext cidList() throws RecognitionException {
		CidListContext _localctx = new CidListContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_cidList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			cid();
			setState(324);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(320);
				match(T__0);
				setState(321);
				cid();
				}
				}
				setState(326);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class CidContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(HBaseSQLParser.TEXT, 0); }
		public CidContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cid; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitCid(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CidContext cid() throws RecognitionException {
		CidContext _localctx = new CidContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_cid);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			match(TEXT);
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

	public static class FuncnameContext extends ParserRuleContext {
		public FuncnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcname; }
	 
		public FuncnameContext() { }
		public void copyFrom(FuncnameContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RowKey_FunctionNameContext extends FuncnameContext {
		public TerminalNode TEXT() { return getToken(HBaseSQLParser.TEXT, 0); }
		public RowKey_FunctionNameContext(FuncnameContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowKey_FunctionName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncnameContext funcname() throws RecognitionException {
		FuncnameContext _localctx = new FuncnameContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_funcname);
		try {
			_localctx = new RowKey_FunctionNameContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(329);
			match(TEXT);
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

	public static class ConstantListContext extends ParserRuleContext {
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public List<ConstantContext> constant() {
			return getRuleContexts(ConstantContext.class);
		}
		public ConstantContext constant(int i) {
			return getRuleContext(ConstantContext.class,i);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public ConstantListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitConstantList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantListContext constantList() throws RecognitionException {
		ConstantListContext _localctx = new ConstantListContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_constantList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331);
			match(LB);
			setState(332);
			constant();
			setState(337);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(333);
				match(T__0);
				setState(334);
				constant();
				}
				}
				setState(339);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(340);
			match(RB);
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

	public static class InsertValueListContext extends ParserRuleContext {
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public List<InsertValueContext> insertValue() {
			return getRuleContexts(InsertValueContext.class);
		}
		public InsertValueContext insertValue(int i) {
			return getRuleContext(InsertValueContext.class,i);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public InsertValueListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertValueList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitInsertValueList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertValueListContext insertValueList() throws RecognitionException {
		InsertValueListContext _localctx = new InsertValueListContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_insertValueList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(342);
			match(LB);
			setState(343);
			insertValue();
			setState(348);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(344);
				match(T__0);
				setState(345);
				insertValue();
				}
				}
				setState(350);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(351);
			match(RB);
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

	public static class InsertValueContext extends ParserRuleContext {
		public InsertValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertValue; }
	 
		public InsertValueContext() { }
		public void copyFrom(InsertValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class InsertValue_NullContext extends InsertValueContext {
		public TerminalNode NULL() { return getToken(HBaseSQLParser.NULL, 0); }
		public InsertValue_NullContext(InsertValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitInsertValue_Null(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InsertValue_NotNullContext extends InsertValueContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public InsertValue_NotNullContext(InsertValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitInsertValue_NotNull(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertValueContext insertValue() throws RecognitionException {
		InsertValueContext _localctx = new InsertValueContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_insertValue);
		try {
			setState(355);
			switch (_input.LA(1)) {
			case T__1:
				_localctx = new InsertValue_NotNullContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(353);
				constant();
				}
				break;
			case NULL:
				_localctx = new InsertValue_NullContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(354);
				match(NULL);
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

	public static class MaxVersionExpContext extends ParserRuleContext {
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public TerminalNode MAXVERSION() { return getToken(HBaseSQLParser.MAXVERSION, 0); }
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public MaxversionContext maxversion() {
			return getRuleContext(MaxversionContext.class,0);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public MaxVersionExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_maxVersionExp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitMaxVersionExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MaxVersionExpContext maxVersionExp() throws RecognitionException {
		MaxVersionExpContext _localctx = new MaxVersionExpContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_maxVersionExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(357);
			match(LB);
			setState(358);
			match(MAXVERSION);
			setState(359);
			match(IS);
			setState(360);
			maxversion();
			setState(361);
			match(RB);
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

	public static class LimitExpContext extends ParserRuleContext {
		public TerminalNode LIMIT() { return getToken(HBaseSQLParser.LIMIT, 0); }
		public List<TerminalNode> TEXT() { return getTokens(HBaseSQLParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(HBaseSQLParser.TEXT, i);
		}
		public LimitExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_limitExp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitLimitExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LimitExpContext limitExp() throws RecognitionException {
		LimitExpContext _localctx = new LimitExpContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_limitExp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
			match(LIMIT);
			setState(364);
			match(TEXT);
			setState(367);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(365);
				match(T__0);
				setState(366);
				match(TEXT);
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

	public static class TableNameContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(HBaseSQLParser.TEXT, 0); }
		public TableNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTableName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableNameContext tableName() throws RecognitionException {
		TableNameContext _localctx = new TableNameContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_tableName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(369);
			match(TEXT);
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

	public static class MaxversionContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(HBaseSQLParser.TEXT, 0); }
		public MaxversionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_maxversion; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitMaxversion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MaxversionContext maxversion() throws RecognitionException {
		MaxversionContext _localctx = new MaxversionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_maxversion);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(371);
			match(TEXT);
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
		public TerminalNode TEXT() { return getToken(HBaseSQLParser.TEXT, 0); }
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_constant);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(373);
			match(T__1);
			setState(374);
			match(TEXT);
			setState(375);
			match(T__1);
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

	public static class VarContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(HBaseSQLParser.TEXT, 0); }
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(377);
			match(T__2);
			setState(378);
			match(TEXT);
			setState(379);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 5:
			return conditionc_sempred((ConditioncContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean conditionc_sempred(ConditioncContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 30);
		case 1:
			return precpred(_ctx, 29);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\63\u0180\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2\3\2\3"+
		"\2\5\2\64\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\5\3E\n\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4M\n\4\3\4\5\4P\n\4\3\4\5\4S\n"+
		"\4\3\4\5\4V\n\4\3\4\5\4Y\n\4\3\5\3\5\3\5\3\5\3\5\3\5\5\5a\n\5\3\5\5\5"+
		"d\n\5\3\5\3\5\3\5\5\5i\n\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u00e6\n\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7"+
		"\u00ee\n\7\f\7\16\7\u00f1\13\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0107\n\b\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u0118\n\t\f\t\16\t\u011b"+
		"\13\t\3\t\3\t\3\t\3\t\5\t\u0121\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u0139\n\n\3"+
		"\13\3\13\3\f\3\f\3\f\5\f\u0140\n\f\3\r\3\r\3\r\7\r\u0145\n\r\f\r\16\r"+
		"\u0148\13\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\20\7\20\u0152\n\20\f"+
		"\20\16\20\u0155\13\20\3\20\3\20\3\21\3\21\3\21\3\21\7\21\u015d\n\21\f"+
		"\21\16\21\u0160\13\21\3\21\3\21\3\22\3\22\5\22\u0166\n\22\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\5\24\u0172\n\24\3\25\3\25\3\26"+
		"\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\2\3\f\31\2\4\6\b\n"+
		"\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\2\2\u01a3\2\63\3\2\2\2\4\65\3\2"+
		"\2\2\6F\3\2\2\2\bZ\3\2\2\2\nj\3\2\2\2\f\u00e5\3\2\2\2\16\u0106\3\2\2\2"+
		"\20\u0120\3\2\2\2\22\u0138\3\2\2\2\24\u013a\3\2\2\2\26\u013f\3\2\2\2\30"+
		"\u0141\3\2\2\2\32\u0149\3\2\2\2\34\u014b\3\2\2\2\36\u014d\3\2\2\2 \u0158"+
		"\3\2\2\2\"\u0165\3\2\2\2$\u0167\3\2\2\2&\u016d\3\2\2\2(\u0173\3\2\2\2"+
		"*\u0175\3\2\2\2,\u0177\3\2\2\2.\u017b\3\2\2\2\60\64\5\4\3\2\61\64\5\6"+
		"\4\2\62\64\5\b\5\2\63\60\3\2\2\2\63\61\3\2\2\2\63\62\3\2\2\2\64\3\3\2"+
		"\2\2\65\66\7\13\2\2\66\67\7\r\2\2\678\5(\25\289\7\7\2\29:\5\30\r\2:;\7"+
		"\b\2\2;<\7\16\2\2<=\5 \21\2=>\7\t\2\2>?\7\20\2\2?@\7\32\2\2@D\5\20\t\2"+
		"AB\7\27\2\2BC\7\32\2\2CE\5\24\13\2DA\3\2\2\2DE\3\2\2\2E\5\3\2\2\2FG\7"+
		"\n\2\2GH\5\26\f\2HI\7\17\2\2IJ\5(\25\2JL\7\t\2\2KM\5\16\b\2LK\3\2\2\2"+
		"LM\3\2\2\2MO\3\2\2\2NP\5\n\6\2ON\3\2\2\2OP\3\2\2\2PR\3\2\2\2QS\5$\23\2"+
		"RQ\3\2\2\2RS\3\2\2\2SU\3\2\2\2TV\5\22\n\2UT\3\2\2\2UV\3\2\2\2VX\3\2\2"+
		"\2WY\5&\24\2XW\3\2\2\2XY\3\2\2\2Y\7\3\2\2\2Z[\7\f\2\2[\\\5\26\f\2\\]\7"+
		"\17\2\2]^\5(\25\2^`\7\t\2\2_a\5\16\b\2`_\3\2\2\2`a\3\2\2\2ac\3\2\2\2b"+
		"d\5\n\6\2cb\3\2\2\2cd\3\2\2\2dh\3\2\2\2ef\7\27\2\2fg\7\32\2\2gi\5\24\13"+
		"\2he\3\2\2\2hi\3\2\2\2i\t\3\2\2\2jk\5\f\7\2k\13\3\2\2\2lm\b\7\1\2mn\7"+
		"\7\2\2no\5\f\7\2op\7\b\2\2p\u00e6\3\2\2\2qr\5\32\16\2rs\7$\2\2st\5,\27"+
		"\2t\u00e6\3\2\2\2uv\5\32\16\2vw\7$\2\2wx\5.\30\2x\u00e6\3\2\2\2yz\5\32"+
		"\16\2z{\7 \2\2{|\5,\27\2|\u00e6\3\2\2\2}~\5\32\16\2~\177\7 \2\2\177\u0080"+
		"\5.\30\2\u0080\u00e6\3\2\2\2\u0081\u0082\5\32\16\2\u0082\u0083\7\"\2\2"+
		"\u0083\u0084\5,\27\2\u0084\u00e6\3\2\2\2\u0085\u0086\5\32\16\2\u0086\u0087"+
		"\7\"\2\2\u0087\u0088\5.\30\2\u0088\u00e6\3\2\2\2\u0089\u008a\5\32\16\2"+
		"\u008a\u008b\7\37\2\2\u008b\u008c\5,\27\2\u008c\u00e6\3\2\2\2\u008d\u008e"+
		"\5\32\16\2\u008e\u008f\7\37\2\2\u008f\u0090\5.\30\2\u0090\u00e6\3\2\2"+
		"\2\u0091\u0092\5\32\16\2\u0092\u0093\7!\2\2\u0093\u0094\5,\27\2\u0094"+
		"\u00e6\3\2\2\2\u0095\u0096\5\32\16\2\u0096\u0097\7!\2\2\u0097\u0098\5"+
		".\30\2\u0098\u00e6\3\2\2\2\u0099\u009a\5\32\16\2\u009a\u009b\7#\2\2\u009b"+
		"\u009c\5,\27\2\u009c\u00e6\3\2\2\2\u009d\u009e\5\32\16\2\u009e\u009f\7"+
		"#\2\2\u009f\u00a0\5.\30\2\u00a0\u00e6\3\2\2\2\u00a1\u00a2\5\32\16\2\u00a2"+
		"\u00a3\7&\2\2\u00a3\u00a4\5,\27\2\u00a4\u00e6\3\2\2\2\u00a5\u00a6\5\32"+
		"\16\2\u00a6\u00a7\7&\2\2\u00a7\u00a8\5.\30\2\u00a8\u00e6\3\2\2\2\u00a9"+
		"\u00aa\5\32\16\2\u00aa\u00ab\7\'\2\2\u00ab\u00ac\5,\27\2\u00ac\u00e6\3"+
		"\2\2\2\u00ad\u00ae\5\32\16\2\u00ae\u00af\7\'\2\2\u00af\u00b0\5.\30\2\u00b0"+
		"\u00e6\3\2\2\2\u00b1\u00b2\5\32\16\2\u00b2\u00b3\7(\2\2\u00b3\u00b4\5"+
		"\36\20\2\u00b4\u00e6\3\2\2\2\u00b5\u00b6\5\32\16\2\u00b6\u00b7\7(\2\2"+
		"\u00b7\u00b8\5.\30\2\u00b8\u00e6\3\2\2\2\u00b9\u00ba\5\32\16\2\u00ba\u00bb"+
		"\7)\2\2\u00bb\u00bc\5\36\20\2\u00bc\u00e6\3\2\2\2\u00bd\u00be\5\32\16"+
		"\2\u00be\u00bf\7)\2\2\u00bf\u00c0\5.\30\2\u00c0\u00e6\3\2\2\2\u00c1\u00c2"+
		"\5\32\16\2\u00c2\u00c3\7*\2\2\u00c3\u00c4\5,\27\2\u00c4\u00c5\7\35\2\2"+
		"\u00c5\u00c6\5,\27\2\u00c6\u00e6\3\2\2\2\u00c7\u00c8\5\32\16\2\u00c8\u00c9"+
		"\7*\2\2\u00c9\u00ca\5.\30\2\u00ca\u00cb\7\35\2\2\u00cb\u00cc\5.\30\2\u00cc"+
		"\u00e6\3\2\2\2\u00cd\u00ce\5\32\16\2\u00ce\u00cf\7+\2\2\u00cf\u00d0\5"+
		",\27\2\u00d0\u00d1\7\35\2\2\u00d1\u00d2\5,\27\2\u00d2\u00e6\3\2\2\2\u00d3"+
		"\u00d4\5\32\16\2\u00d4\u00d5\7+\2\2\u00d5\u00d6\5.\30\2\u00d6\u00d7\7"+
		"\35\2\2\u00d7\u00d8\5.\30\2\u00d8\u00e6\3\2\2\2\u00d9\u00da\5\32\16\2"+
		"\u00da\u00db\7,\2\2\u00db\u00e6\3\2\2\2\u00dc\u00dd\5\32\16\2\u00dd\u00de"+
		"\7-\2\2\u00de\u00e6\3\2\2\2\u00df\u00e0\5\32\16\2\u00e0\u00e1\7.\2\2\u00e1"+
		"\u00e6\3\2\2\2\u00e2\u00e3\5\32\16\2\u00e3\u00e4\7/\2\2\u00e4\u00e6\3"+
		"\2\2\2\u00e5l\3\2\2\2\u00e5q\3\2\2\2\u00e5u\3\2\2\2\u00e5y\3\2\2\2\u00e5"+
		"}\3\2\2\2\u00e5\u0081\3\2\2\2\u00e5\u0085\3\2\2\2\u00e5\u0089\3\2\2\2"+
		"\u00e5\u008d\3\2\2\2\u00e5\u0091\3\2\2\2\u00e5\u0095\3\2\2\2\u00e5\u0099"+
		"\3\2\2\2\u00e5\u009d\3\2\2\2\u00e5\u00a1\3\2\2\2\u00e5\u00a5\3\2\2\2\u00e5"+
		"\u00a9\3\2\2\2\u00e5\u00ad\3\2\2\2\u00e5\u00b1\3\2\2\2\u00e5\u00b5\3\2"+
		"\2\2\u00e5\u00b9\3\2\2\2\u00e5\u00bd\3\2\2\2\u00e5\u00c1\3\2\2\2\u00e5"+
		"\u00c7\3\2\2\2\u00e5\u00cd\3\2\2\2\u00e5\u00d3\3\2\2\2\u00e5\u00d9\3\2"+
		"\2\2\u00e5\u00dc\3\2\2\2\u00e5\u00df\3\2\2\2\u00e5\u00e2\3\2\2\2\u00e6"+
		"\u00ef\3\2\2\2\u00e7\u00e8\f \2\2\u00e8\u00e9\7\35\2\2\u00e9\u00ee\5\f"+
		"\7!\u00ea\u00eb\f\37\2\2\u00eb\u00ec\7\36\2\2\u00ec\u00ee\5\f\7 \u00ed"+
		"\u00e7\3\2\2\2\u00ed\u00ea\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00ed\3\2"+
		"\2\2\u00ef\u00f0\3\2\2\2\u00f0\r\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00f3"+
		"\7\21\2\2\u00f3\u00f4\7\32\2\2\u00f4\u00f5\5\20\t\2\u00f5\u00f6\7\3\2"+
		"\2\u00f6\u00f7\7\22\2\2\u00f7\u00f8\7\32\2\2\u00f8\u00f9\5\20\t\2\u00f9"+
		"\u0107\3\2\2\2\u00fa\u00fb\7\21\2\2\u00fb\u00fc\7\32\2\2\u00fc\u0107\5"+
		"\20\t\2\u00fd\u00fe\7\22\2\2\u00fe\u00ff\7\32\2\2\u00ff\u0107\5\20\t\2"+
		"\u0100\u0101\7\20\2\2\u0101\u0102\7\32\2\2\u0102\u0107\5\20\t\2\u0103"+
		"\u0104\7\20\2\2\u0104\u0105\7\32\2\2\u0105\u0107\5\20\t\2\u0106\u00f2"+
		"\3\2\2\2\u0106\u00fa\3\2\2\2\u0106\u00fd\3\2\2\2\u0106\u0100\3\2\2\2\u0106"+
		"\u0103\3\2\2\2\u0107\17\3\2\2\2\u0108\u0109\7\7\2\2\u0109\u010a\5\20\t"+
		"\2\u010a\u010b\7\b\2\2\u010b\u0121\3\2\2\2\u010c\u010d\5\34\17\2\u010d"+
		"\u010e\7\7\2\2\u010e\u010f\5,\27\2\u010f\u0110\7\b\2\2\u0110\u0121\3\2"+
		"\2\2\u0111\u0112\5\34\17\2\u0112\u0113\7(\2\2\u0113\u0114\7\7\2\2\u0114"+
		"\u0119\5,\27\2\u0115\u0116\7\3\2\2\u0116\u0118\5,\27\2\u0117\u0115\3\2"+
		"\2\2\u0118\u011b\3\2\2\2\u0119\u0117\3\2\2\2\u0119\u011a\3\2\2\2\u011a"+
		"\u011c\3\2\2\2\u011b\u0119\3\2\2\2\u011c\u011d\7\b\2\2\u011d\u0121\3\2"+
		"\2\2\u011e\u0121\7\23\2\2\u011f\u0121\7\24\2\2\u0120\u0108\3\2\2\2\u0120"+
		"\u010c\3\2\2\2\u0120\u0111\3\2\2\2\u0120\u011e\3\2\2\2\u0120\u011f\3\2"+
		"\2\2\u0121\21\3\2\2\2\u0122\u0123\7\7\2\2\u0123\u0124\7\30\2\2\u0124\u0125"+
		"\7\32\2\2\u0125\u0126\5\24\13\2\u0126\u0127\7\3\2\2\u0127\u0128\7\31\2"+
		"\2\u0128\u0129\7\32\2\2\u0129\u012a\5\24\13\2\u012a\u012b\7\b\2\2\u012b"+
		"\u0139\3\2\2\2\u012c\u012d\7\7\2\2\u012d\u012e\7\30\2\2\u012e\u012f\7"+
		"\32\2\2\u012f\u0130\5\24\13\2\u0130\u0131\7\b\2\2\u0131\u0139\3\2\2\2"+
		"\u0132\u0133\7\7\2\2\u0133\u0134\7\31\2\2\u0134\u0135\7\32\2\2\u0135\u0136"+
		"\5\24\13\2\u0136\u0137\7\b\2\2\u0137\u0139\3\2\2\2\u0138\u0122\3\2\2\2"+
		"\u0138\u012c\3\2\2\2\u0138\u0132\3\2\2\2\u0139\23\3\2\2\2\u013a\u013b"+
		"\5,\27\2\u013b\25\3\2\2\2\u013c\u0140\5\30\r\2\u013d\u0140\7\6\2\2\u013e"+
		"\u0140\7\60\2\2\u013f\u013c\3\2\2\2\u013f\u013d\3\2\2\2\u013f\u013e\3"+
		"\2\2\2\u0140\27\3\2\2\2\u0141\u0146\5\32\16\2\u0142\u0143\7\3\2\2\u0143"+
		"\u0145\5\32\16\2\u0144\u0142\3\2\2\2\u0145\u0148\3\2\2\2\u0146\u0144\3"+
		"\2\2\2\u0146\u0147\3\2\2\2\u0147\31\3\2\2\2\u0148\u0146\3\2\2\2\u0149"+
		"\u014a\7\60\2\2\u014a\33\3\2\2\2\u014b\u014c\7\60\2\2\u014c\35\3\2\2\2"+
		"\u014d\u014e\7\7\2\2\u014e\u0153\5,\27\2\u014f\u0150\7\3\2\2\u0150\u0152"+
		"\5,\27\2\u0151\u014f\3\2\2\2\u0152\u0155\3\2\2\2\u0153\u0151\3\2\2\2\u0153"+
		"\u0154\3\2\2\2\u0154\u0156\3\2\2\2\u0155\u0153\3\2\2\2\u0156\u0157\7\b"+
		"\2\2\u0157\37\3\2\2\2\u0158\u0159\7\7\2\2\u0159\u015e\5\"\22\2\u015a\u015b"+
		"\7\3\2\2\u015b\u015d\5\"\22\2\u015c\u015a\3\2\2\2\u015d\u0160\3\2\2\2"+
		"\u015e\u015c\3\2\2\2\u015e\u015f\3\2\2\2\u015f\u0161\3\2\2\2\u0160\u015e"+
		"\3\2\2\2\u0161\u0162\7\b\2\2\u0162!\3\2\2\2\u0163\u0166\5,\27\2\u0164"+
		"\u0166\7\33\2\2\u0165\u0163\3\2\2\2\u0165\u0164\3\2\2\2\u0166#\3\2\2\2"+
		"\u0167\u0168\7\7\2\2\u0168\u0169\7\25\2\2\u0169\u016a\7\32\2\2\u016a\u016b"+
		"\5*\26\2\u016b\u016c\7\b\2\2\u016c%\3\2\2\2\u016d\u016e\7\26\2\2\u016e"+
		"\u0171\7\60\2\2\u016f\u0170\7\3\2\2\u0170\u0172\7\60\2\2\u0171\u016f\3"+
		"\2\2\2\u0171\u0172\3\2\2\2\u0172\'\3\2\2\2\u0173\u0174\7\60\2\2\u0174"+
		")\3\2\2\2\u0175\u0176\7\60\2\2\u0176+\3\2\2\2\u0177\u0178\7\4\2\2\u0178"+
		"\u0179\7\60\2\2\u0179\u017a\7\4\2\2\u017a-\3\2\2\2\u017b\u017c\7\5\2\2"+
		"\u017c\u017d\7\60\2\2\u017d\u017e\7\5\2\2\u017e/\3\2\2\2\31\63DLORUX`"+
		"ch\u00e5\u00ed\u00ef\u0106\u0119\u0120\u0138\u013f\u0146\u0153\u015e\u0165"+
		"\u0171";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}