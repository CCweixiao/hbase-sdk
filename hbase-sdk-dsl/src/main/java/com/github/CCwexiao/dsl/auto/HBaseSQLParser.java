// Generated from HBaseSQL.g4 by ANTLR 4.5.1

package com.github.CCwexiao.dsl.auto;

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
		GREATEREQUAL=31, GREATER=32, NOTEQUAL=33, EQUAL=34, NOTMATCH=35, MATCH=36, 
		IN=37, NOTIN=38, BETWEEN=39, NOTBETWEEN=40, ISNULL=41, ISNOTNULL=42, ISMISSING=43, 
		ISNOTMISSING=44, TEXT=45, NUM=46, WS=47;
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
		"'equal'", "'notmatch'", "'match'", "'in'", "'notin'", "'between'", "'notbetween'", 
		"'isnull'", "'isnotnull'", "'ismissing'", "'isnotmissing'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, "STAR", "LB", "RB", "WHERE", "SELECT", "INSERT", 
		"DELETE", "INTO", "VALUES", "FROM", "ROWKEY", "STARTKEY", "ENDKEY", "HBASESTARTKEY", 
		"HBASEENDKEY", "MAXVERSION", "LIMIT", "TS", "STARTTS", "ENDTS", "IS", 
		"NULL", "NOT", "AND", "OR", "LESSEQUAL", "LESS", "GREATEREQUAL", "GREATER", 
		"NOTEQUAL", "EQUAL", "NOTMATCH", "MATCH", "IN", "NOTIN", "BETWEEN", "NOTBETWEEN", 
		"ISNULL", "ISNOTNULL", "ISMISSING", "ISNOTMISSING", "TEXT", "NUM", "WS"
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
		public CidListContext cidList() {
			return getRuleContext(CidListContext.class,0);
		}
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
			cidList();
			setState(55);
			match(VALUES);
			setState(56);
			insertValueList();
			setState(57);
			match(WHERE);
			setState(58);
			match(ROWKEY);
			setState(59);
			match(IS);
			setState(60);
			rowKeyExp();
			setState(64);
			_la = _input.LA(1);
			if (_la==TS) {
				{
				setState(61);
				match(TS);
				setState(62);
				match(IS);
				setState(63);
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
			setState(66);
			match(SELECT);
			setState(67);
			selectCidList();
			setState(68);
			match(FROM);
			setState(69);
			tableName();
			setState(70);
			match(WHERE);
			setState(72);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ROWKEY) | (1L << STARTKEY) | (1L << ENDKEY))) != 0)) {
				{
				setState(71);
				rowKeyRange();
				}
			}

			setState(75);
			_la = _input.LA(1);
			if (_la==LB || _la==TEXT) {
				{
				setState(74);
				wherec();
				}
			}

			setState(78);
			_la = _input.LA(1);
			if (_la==MAXVERSION) {
				{
				setState(77);
				maxVersionExp();
				}
			}

			setState(81);
			_la = _input.LA(1);
			if (_la==STARTTS || _la==ENDTS) {
				{
				setState(80);
				tsrange();
				}
			}

			setState(84);
			_la = _input.LA(1);
			if (_la==LIMIT) {
				{
				setState(83);
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
			setState(86);
			match(DELETE);
			setState(87);
			selectCidList();
			setState(88);
			match(FROM);
			setState(89);
			tableName();
			setState(90);
			match(WHERE);
			setState(92);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ROWKEY) | (1L << STARTKEY) | (1L << ENDKEY))) != 0)) {
				{
				setState(91);
				rowKeyRange();
				}
			}

			setState(95);
			_la = _input.LA(1);
			if (_la==LB || _la==TEXT) {
				{
				setState(94);
				wherec();
				}
			}

			setState(100);
			_la = _input.LA(1);
			if (_la==TS) {
				{
				setState(97);
				match(TS);
				setState(98);
				match(IS);
				setState(99);
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
			setState(102);
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
			setState(225);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				_localctx = new ConditionwrapperContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(105);
				match(LB);
				setState(106);
				conditionc(0);
				setState(107);
				match(RB);
				}
				break;
			case 2:
				{
				_localctx = new EqualconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(109);
				cid();
				setState(110);
				match(EQUAL);
				setState(111);
				constant();
				}
				break;
			case 3:
				{
				_localctx = new EqualvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(113);
				cid();
				setState(114);
				match(EQUAL);
				setState(115);
				var();
				}
				break;
			case 4:
				{
				_localctx = new LessconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(117);
				cid();
				setState(118);
				match(LESS);
				setState(119);
				constant();
				}
				break;
			case 5:
				{
				_localctx = new LessvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(121);
				cid();
				setState(122);
				match(LESS);
				setState(123);
				var();
				}
				break;
			case 6:
				{
				_localctx = new GreaterconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(125);
				cid();
				setState(126);
				match(GREATER);
				setState(127);
				constant();
				}
				break;
			case 7:
				{
				_localctx = new GreatervarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(129);
				cid();
				setState(130);
				match(GREATER);
				setState(131);
				var();
				}
				break;
			case 8:
				{
				_localctx = new LessequalconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(133);
				cid();
				setState(134);
				match(LESSEQUAL);
				setState(135);
				constant();
				}
				break;
			case 9:
				{
				_localctx = new LessequalvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(137);
				cid();
				setState(138);
				match(LESSEQUAL);
				setState(139);
				var();
				}
				break;
			case 10:
				{
				_localctx = new GreaterequalconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(141);
				cid();
				setState(142);
				match(GREATEREQUAL);
				setState(143);
				constant();
				}
				break;
			case 11:
				{
				_localctx = new GreaterequalvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(145);
				cid();
				setState(146);
				match(GREATEREQUAL);
				setState(147);
				var();
				}
				break;
			case 12:
				{
				_localctx = new NotequalconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(149);
				cid();
				setState(150);
				match(NOTEQUAL);
				setState(151);
				constant();
				}
				break;
			case 13:
				{
				_localctx = new NotequalvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(153);
				cid();
				setState(154);
				match(NOTEQUAL);
				setState(155);
				var();
				}
				break;
			case 14:
				{
				_localctx = new NotmatchconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(157);
				cid();
				setState(158);
				match(NOTMATCH);
				setState(159);
				constant();
				}
				break;
			case 15:
				{
				_localctx = new NotmatchvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(161);
				cid();
				setState(162);
				match(NOTMATCH);
				setState(163);
				var();
				}
				break;
			case 16:
				{
				_localctx = new MatchconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(165);
				cid();
				setState(166);
				match(MATCH);
				setState(167);
				constant();
				}
				break;
			case 17:
				{
				_localctx = new MatchvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(169);
				cid();
				setState(170);
				match(MATCH);
				setState(171);
				var();
				}
				break;
			case 18:
				{
				_localctx = new InconstantlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(173);
				cid();
				setState(174);
				match(IN);
				setState(175);
				constantList();
				}
				break;
			case 19:
				{
				_localctx = new InvarlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(177);
				cid();
				setState(178);
				match(IN);
				setState(179);
				var();
				}
				break;
			case 20:
				{
				_localctx = new NotinconstantlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(181);
				cid();
				setState(182);
				match(NOTIN);
				setState(183);
				constantList();
				}
				break;
			case 21:
				{
				_localctx = new NotinvarlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(185);
				cid();
				setState(186);
				match(NOTIN);
				setState(187);
				var();
				}
				break;
			case 22:
				{
				_localctx = new BetweenconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(189);
				cid();
				setState(190);
				match(BETWEEN);
				setState(191);
				constant();
				setState(192);
				match(AND);
				setState(193);
				constant();
				}
				break;
			case 23:
				{
				_localctx = new BetweenvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(195);
				cid();
				setState(196);
				match(BETWEEN);
				setState(197);
				var();
				setState(198);
				match(AND);
				setState(199);
				var();
				}
				break;
			case 24:
				{
				_localctx = new NotbetweenconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(201);
				cid();
				setState(202);
				match(NOTBETWEEN);
				setState(203);
				constant();
				setState(204);
				match(AND);
				setState(205);
				constant();
				}
				break;
			case 25:
				{
				_localctx = new NotbetweenvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(207);
				cid();
				setState(208);
				match(NOTBETWEEN);
				setState(209);
				var();
				setState(210);
				match(AND);
				setState(211);
				var();
				}
				break;
			case 26:
				{
				_localctx = new IsnullcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(213);
				cid();
				setState(214);
				match(ISNULL);
				}
				break;
			case 27:
				{
				_localctx = new IsnotnullcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(216);
				cid();
				setState(217);
				match(ISNOTNULL);
				}
				break;
			case 28:
				{
				_localctx = new IsmissingcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(219);
				cid();
				setState(220);
				match(ISMISSING);
				}
				break;
			case 29:
				{
				_localctx = new IsnotmissingcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(222);
				cid();
				setState(223);
				match(ISNOTMISSING);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(235);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(233);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new AndconditionContext(new ConditioncContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_conditionc);
						setState(227);
						if (!(precpred(_ctx, 30))) throw new FailedPredicateException(this, "precpred(_ctx, 30)");
						setState(228);
						match(AND);
						setState(229);
						conditionc(31);
						}
						break;
					case 2:
						{
						_localctx = new OrconditionContext(new ConditioncContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_conditionc);
						setState(230);
						if (!(precpred(_ctx, 29))) throw new FailedPredicateException(this, "precpred(_ctx, 29)");
						setState(231);
						match(OR);
						setState(232);
						conditionc(30);
						}
						break;
					}
					} 
				}
				setState(237);
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
			setState(255);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new Rowkeyrange_startAndEndContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(238);
				match(STARTKEY);
				setState(239);
				match(IS);
				setState(240);
				rowKeyExp();
				setState(241);
				match(T__0);
				setState(242);
				match(ENDKEY);
				setState(243);
				match(IS);
				setState(244);
				rowKeyExp();
				}
				break;
			case 2:
				_localctx = new Rowkeyrange_startContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(246);
				match(STARTKEY);
				setState(247);
				match(IS);
				setState(248);
				rowKeyExp();
				}
				break;
			case 3:
				_localctx = new Rowkeyrange_endContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(249);
				match(ENDKEY);
				setState(250);
				match(IS);
				setState(251);
				rowKeyExp();
				}
				break;
			case 4:
				_localctx = new Rowkeyrange_onerowkeyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(252);
				match(ROWKEY);
				setState(253);
				match(IS);
				setState(254);
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
		try {
			setState(268);
			switch (_input.LA(1)) {
			case LB:
				_localctx = new Rowkey_WrapperContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(257);
				match(LB);
				setState(258);
				rowKeyExp();
				setState(259);
				match(RB);
				}
				break;
			case TEXT:
				_localctx = new Rowkey_FuncConstantContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(261);
				funcname();
				setState(262);
				match(LB);
				setState(263);
				constant();
				setState(264);
				match(RB);
				}
				break;
			case HBASESTARTKEY:
				_localctx = new Rowkey_hbasestartContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(266);
				match(HBASESTARTKEY);
				}
				break;
			case HBASEENDKEY:
				_localctx = new Rowkey_hbaseendContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(267);
				match(HBASEENDKEY);
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
		public TerminalNode STARTTS() { return getToken(HBaseSQLParser.STARTTS, 0); }
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public TsexpContext tsexp() {
			return getRuleContext(TsexpContext.class,0);
		}
		public Tsrange_startContext(TsrangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTsrange_start(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Tsrange_endContext extends TsrangeContext {
		public TerminalNode ENDTS() { return getToken(HBaseSQLParser.ENDTS, 0); }
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public TsexpContext tsexp() {
			return getRuleContext(TsexpContext.class,0);
		}
		public Tsrange_endContext(TsrangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTsrange_end(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Tsrange_startAndEndContext extends TsrangeContext {
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
			setState(284);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new Tsrange_startAndEndContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(270);
				match(STARTTS);
				setState(271);
				match(IS);
				setState(272);
				tsexp();
				setState(273);
				match(T__0);
				setState(274);
				match(ENDTS);
				setState(275);
				match(IS);
				setState(276);
				tsexp();
				}
				break;
			case 2:
				_localctx = new Tsrange_startContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(278);
				match(STARTTS);
				setState(279);
				match(IS);
				setState(280);
				tsexp();
				}
				break;
			case 3:
				_localctx = new Tsrange_endContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(281);
				match(ENDTS);
				setState(282);
				match(IS);
				setState(283);
				tsexp();
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
			setState(286);
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
			setState(291);
			switch (_input.LA(1)) {
			case LB:
				_localctx = new CidList_CidListContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(288);
				cidList();
				}
				break;
			case STAR:
				_localctx = new CidList_StarContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(289);
				match(STAR);
				}
				break;
			case TEXT:
				_localctx = new CidList_RegxContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(290);
				match(TEXT);
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

	public static class CidListContext extends ParserRuleContext {
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public List<CidContext> cid() {
			return getRuleContexts(CidContext.class);
		}
		public CidContext cid(int i) {
			return getRuleContext(CidContext.class,i);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
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
			setState(293);
			match(LB);
			setState(294);
			cid();
			setState(299);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(295);
				match(T__0);
				setState(296);
				cid();
				}
				}
				setState(301);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(302);
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
			setState(304);
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
		public TerminalNode TEXT() { return getToken(HBaseSQLParser.TEXT, 0); }
		public FuncnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcname; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitFuncname(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncnameContext funcname() throws RecognitionException {
		FuncnameContext _localctx = new FuncnameContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_funcname);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306);
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
			setState(308);
			match(LB);
			setState(309);
			constant();
			setState(314);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(310);
				match(T__0);
				setState(311);
				constant();
				}
				}
				setState(316);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(317);
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
			setState(319);
			match(LB);
			setState(320);
			insertValue();
			setState(325);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(321);
				match(T__0);
				setState(322);
				insertValue();
				}
				}
				setState(327);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(328);
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
			setState(332);
			switch (_input.LA(1)) {
			case T__1:
				_localctx = new InsertValue_NotNullContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(330);
				constant();
				}
				break;
			case NULL:
				_localctx = new InsertValue_NullContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(331);
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
		public TerminalNode MAXVERSION() { return getToken(HBaseSQLParser.MAXVERSION, 0); }
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public MaxversionContext maxversion() {
			return getRuleContext(MaxversionContext.class,0);
		}
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
			setState(334);
			match(MAXVERSION);
			setState(335);
			match(IS);
			setState(336);
			maxversion();
			}
		}
		catch (RecognitionException re) {
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
			setState(338);
			match(LIMIT);
			setState(339);
			match(TEXT);
			setState(342);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(340);
				match(T__0);
				setState(341);
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
			setState(344);
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
			setState(346);
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
			setState(348);
			match(T__1);
			setState(349);
			match(TEXT);
			setState(350);
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
			setState(352);
			match(T__2);
			setState(353);
			match(TEXT);
			setState(354);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\61\u0167\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2\3\2\3"+
		"\2\5\2\64\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3"+
		"C\n\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4K\n\4\3\4\5\4N\n\4\3\4\5\4Q\n\4\3\4\5"+
		"\4T\n\4\3\4\5\4W\n\4\3\5\3\5\3\5\3\5\3\5\3\5\5\5_\n\5\3\5\5\5b\n\5\3\5"+
		"\3\5\3\5\5\5g\n\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\5\7\u00e4\n\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u00ec\n"+
		"\7\f\7\16\7\u00ef\13\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\5\b\u0102\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\5\t\u010f\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\5\n\u011f\n\n\3\13\3\13\3\f\3\f\3\f\5\f\u0126\n\f\3\r\3\r\3\r"+
		"\3\r\7\r\u012c\n\r\f\r\16\r\u012f\13\r\3\r\3\r\3\16\3\16\3\17\3\17\3\20"+
		"\3\20\3\20\3\20\7\20\u013b\n\20\f\20\16\20\u013e\13\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\21\7\21\u0146\n\21\f\21\16\21\u0149\13\21\3\21\3\21\3\22"+
		"\3\22\5\22\u014f\n\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\5\24\u0159"+
		"\n\24\3\25\3\25\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30"+
		"\2\3\f\31\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\2\2\u0187\2"+
		"\63\3\2\2\2\4\65\3\2\2\2\6D\3\2\2\2\bX\3\2\2\2\nh\3\2\2\2\f\u00e3\3\2"+
		"\2\2\16\u0101\3\2\2\2\20\u010e\3\2\2\2\22\u011e\3\2\2\2\24\u0120\3\2\2"+
		"\2\26\u0125\3\2\2\2\30\u0127\3\2\2\2\32\u0132\3\2\2\2\34\u0134\3\2\2\2"+
		"\36\u0136\3\2\2\2 \u0141\3\2\2\2\"\u014e\3\2\2\2$\u0150\3\2\2\2&\u0154"+
		"\3\2\2\2(\u015a\3\2\2\2*\u015c\3\2\2\2,\u015e\3\2\2\2.\u0162\3\2\2\2\60"+
		"\64\5\4\3\2\61\64\5\6\4\2\62\64\5\b\5\2\63\60\3\2\2\2\63\61\3\2\2\2\63"+
		"\62\3\2\2\2\64\3\3\2\2\2\65\66\7\13\2\2\66\67\7\r\2\2\678\5(\25\289\5"+
		"\30\r\29:\7\16\2\2:;\5 \21\2;<\7\t\2\2<=\7\20\2\2=>\7\32\2\2>B\5\20\t"+
		"\2?@\7\27\2\2@A\7\32\2\2AC\5\24\13\2B?\3\2\2\2BC\3\2\2\2C\5\3\2\2\2DE"+
		"\7\n\2\2EF\5\26\f\2FG\7\17\2\2GH\5(\25\2HJ\7\t\2\2IK\5\16\b\2JI\3\2\2"+
		"\2JK\3\2\2\2KM\3\2\2\2LN\5\n\6\2ML\3\2\2\2MN\3\2\2\2NP\3\2\2\2OQ\5$\23"+
		"\2PO\3\2\2\2PQ\3\2\2\2QS\3\2\2\2RT\5\22\n\2SR\3\2\2\2ST\3\2\2\2TV\3\2"+
		"\2\2UW\5&\24\2VU\3\2\2\2VW\3\2\2\2W\7\3\2\2\2XY\7\f\2\2YZ\5\26\f\2Z[\7"+
		"\17\2\2[\\\5(\25\2\\^\7\t\2\2]_\5\16\b\2^]\3\2\2\2^_\3\2\2\2_a\3\2\2\2"+
		"`b\5\n\6\2a`\3\2\2\2ab\3\2\2\2bf\3\2\2\2cd\7\27\2\2de\7\32\2\2eg\5\24"+
		"\13\2fc\3\2\2\2fg\3\2\2\2g\t\3\2\2\2hi\5\f\7\2i\13\3\2\2\2jk\b\7\1\2k"+
		"l\7\7\2\2lm\5\f\7\2mn\7\b\2\2n\u00e4\3\2\2\2op\5\32\16\2pq\7$\2\2qr\5"+
		",\27\2r\u00e4\3\2\2\2st\5\32\16\2tu\7$\2\2uv\5.\30\2v\u00e4\3\2\2\2wx"+
		"\5\32\16\2xy\7 \2\2yz\5,\27\2z\u00e4\3\2\2\2{|\5\32\16\2|}\7 \2\2}~\5"+
		".\30\2~\u00e4\3\2\2\2\177\u0080\5\32\16\2\u0080\u0081\7\"\2\2\u0081\u0082"+
		"\5,\27\2\u0082\u00e4\3\2\2\2\u0083\u0084\5\32\16\2\u0084\u0085\7\"\2\2"+
		"\u0085\u0086\5.\30\2\u0086\u00e4\3\2\2\2\u0087\u0088\5\32\16\2\u0088\u0089"+
		"\7\37\2\2\u0089\u008a\5,\27\2\u008a\u00e4\3\2\2\2\u008b\u008c\5\32\16"+
		"\2\u008c\u008d\7\37\2\2\u008d\u008e\5.\30\2\u008e\u00e4\3\2\2\2\u008f"+
		"\u0090\5\32\16\2\u0090\u0091\7!\2\2\u0091\u0092\5,\27\2\u0092\u00e4\3"+
		"\2\2\2\u0093\u0094\5\32\16\2\u0094\u0095\7!\2\2\u0095\u0096\5.\30\2\u0096"+
		"\u00e4\3\2\2\2\u0097\u0098\5\32\16\2\u0098\u0099\7#\2\2\u0099\u009a\5"+
		",\27\2\u009a\u00e4\3\2\2\2\u009b\u009c\5\32\16\2\u009c\u009d\7#\2\2\u009d"+
		"\u009e\5.\30\2\u009e\u00e4\3\2\2\2\u009f\u00a0\5\32\16\2\u00a0\u00a1\7"+
		"%\2\2\u00a1\u00a2\5,\27\2\u00a2\u00e4\3\2\2\2\u00a3\u00a4\5\32\16\2\u00a4"+
		"\u00a5\7%\2\2\u00a5\u00a6\5.\30\2\u00a6\u00e4\3\2\2\2\u00a7\u00a8\5\32"+
		"\16\2\u00a8\u00a9\7&\2\2\u00a9\u00aa\5,\27\2\u00aa\u00e4\3\2\2\2\u00ab"+
		"\u00ac\5\32\16\2\u00ac\u00ad\7&\2\2\u00ad\u00ae\5.\30\2\u00ae\u00e4\3"+
		"\2\2\2\u00af\u00b0\5\32\16\2\u00b0\u00b1\7\'\2\2\u00b1\u00b2\5\36\20\2"+
		"\u00b2\u00e4\3\2\2\2\u00b3\u00b4\5\32\16\2\u00b4\u00b5\7\'\2\2\u00b5\u00b6"+
		"\5.\30\2\u00b6\u00e4\3\2\2\2\u00b7\u00b8\5\32\16\2\u00b8\u00b9\7(\2\2"+
		"\u00b9\u00ba\5\36\20\2\u00ba\u00e4\3\2\2\2\u00bb\u00bc\5\32\16\2\u00bc"+
		"\u00bd\7(\2\2\u00bd\u00be\5.\30\2\u00be\u00e4\3\2\2\2\u00bf\u00c0\5\32"+
		"\16\2\u00c0\u00c1\7)\2\2\u00c1\u00c2\5,\27\2\u00c2\u00c3\7\35\2\2\u00c3"+
		"\u00c4\5,\27\2\u00c4\u00e4\3\2\2\2\u00c5\u00c6\5\32\16\2\u00c6\u00c7\7"+
		")\2\2\u00c7\u00c8\5.\30\2\u00c8\u00c9\7\35\2\2\u00c9\u00ca\5.\30\2\u00ca"+
		"\u00e4\3\2\2\2\u00cb\u00cc\5\32\16\2\u00cc\u00cd\7*\2\2\u00cd\u00ce\5"+
		",\27\2\u00ce\u00cf\7\35\2\2\u00cf\u00d0\5,\27\2\u00d0\u00e4\3\2\2\2\u00d1"+
		"\u00d2\5\32\16\2\u00d2\u00d3\7*\2\2\u00d3\u00d4\5.\30\2\u00d4\u00d5\7"+
		"\35\2\2\u00d5\u00d6\5.\30\2\u00d6\u00e4\3\2\2\2\u00d7\u00d8\5\32\16\2"+
		"\u00d8\u00d9\7+\2\2\u00d9\u00e4\3\2\2\2\u00da\u00db\5\32\16\2\u00db\u00dc"+
		"\7,\2\2\u00dc\u00e4\3\2\2\2\u00dd\u00de\5\32\16\2\u00de\u00df\7-\2\2\u00df"+
		"\u00e4\3\2\2\2\u00e0\u00e1\5\32\16\2\u00e1\u00e2\7.\2\2\u00e2\u00e4\3"+
		"\2\2\2\u00e3j\3\2\2\2\u00e3o\3\2\2\2\u00e3s\3\2\2\2\u00e3w\3\2\2\2\u00e3"+
		"{\3\2\2\2\u00e3\177\3\2\2\2\u00e3\u0083\3\2\2\2\u00e3\u0087\3\2\2\2\u00e3"+
		"\u008b\3\2\2\2\u00e3\u008f\3\2\2\2\u00e3\u0093\3\2\2\2\u00e3\u0097\3\2"+
		"\2\2\u00e3\u009b\3\2\2\2\u00e3\u009f\3\2\2\2\u00e3\u00a3\3\2\2\2\u00e3"+
		"\u00a7\3\2\2\2\u00e3\u00ab\3\2\2\2\u00e3\u00af\3\2\2\2\u00e3\u00b3\3\2"+
		"\2\2\u00e3\u00b7\3\2\2\2\u00e3\u00bb\3\2\2\2\u00e3\u00bf\3\2\2\2\u00e3"+
		"\u00c5\3\2\2\2\u00e3\u00cb\3\2\2\2\u00e3\u00d1\3\2\2\2\u00e3\u00d7\3\2"+
		"\2\2\u00e3\u00da\3\2\2\2\u00e3\u00dd\3\2\2\2\u00e3\u00e0\3\2\2\2\u00e4"+
		"\u00ed\3\2\2\2\u00e5\u00e6\f \2\2\u00e6\u00e7\7\35\2\2\u00e7\u00ec\5\f"+
		"\7!\u00e8\u00e9\f\37\2\2\u00e9\u00ea\7\36\2\2\u00ea\u00ec\5\f\7 \u00eb"+
		"\u00e5\3\2\2\2\u00eb\u00e8\3\2\2\2\u00ec\u00ef\3\2\2\2\u00ed\u00eb\3\2"+
		"\2\2\u00ed\u00ee\3\2\2\2\u00ee\r\3\2\2\2\u00ef\u00ed\3\2\2\2\u00f0\u00f1"+
		"\7\21\2\2\u00f1\u00f2\7\32\2\2\u00f2\u00f3\5\20\t\2\u00f3\u00f4\7\3\2"+
		"\2\u00f4\u00f5\7\22\2\2\u00f5\u00f6\7\32\2\2\u00f6\u00f7\5\20\t\2\u00f7"+
		"\u0102\3\2\2\2\u00f8\u00f9\7\21\2\2\u00f9\u00fa\7\32\2\2\u00fa\u0102\5"+
		"\20\t\2\u00fb\u00fc\7\22\2\2\u00fc\u00fd\7\32\2\2\u00fd\u0102\5\20\t\2"+
		"\u00fe\u00ff\7\20\2\2\u00ff\u0100\7\32\2\2\u0100\u0102\5\20\t\2\u0101"+
		"\u00f0\3\2\2\2\u0101\u00f8\3\2\2\2\u0101\u00fb\3\2\2\2\u0101\u00fe\3\2"+
		"\2\2\u0102\17\3\2\2\2\u0103\u0104\7\7\2\2\u0104\u0105\5\20\t\2\u0105\u0106"+
		"\7\b\2\2\u0106\u010f\3\2\2\2\u0107\u0108\5\34\17\2\u0108\u0109\7\7\2\2"+
		"\u0109\u010a\5,\27\2\u010a\u010b\7\b\2\2\u010b\u010f\3\2\2\2\u010c\u010f"+
		"\7\23\2\2\u010d\u010f\7\24\2\2\u010e\u0103\3\2\2\2\u010e\u0107\3\2\2\2"+
		"\u010e\u010c\3\2\2\2\u010e\u010d\3\2\2\2\u010f\21\3\2\2\2\u0110\u0111"+
		"\7\30\2\2\u0111\u0112\7\32\2\2\u0112\u0113\5\24\13\2\u0113\u0114\7\3\2"+
		"\2\u0114\u0115\7\31\2\2\u0115\u0116\7\32\2\2\u0116\u0117\5\24\13\2\u0117"+
		"\u011f\3\2\2\2\u0118\u0119\7\30\2\2\u0119\u011a\7\32\2\2\u011a\u011f\5"+
		"\24\13\2\u011b\u011c\7\31\2\2\u011c\u011d\7\32\2\2\u011d\u011f\5\24\13"+
		"\2\u011e\u0110\3\2\2\2\u011e\u0118\3\2\2\2\u011e\u011b\3\2\2\2\u011f\23"+
		"\3\2\2\2\u0120\u0121\5,\27\2\u0121\25\3\2\2\2\u0122\u0126\5\30\r\2\u0123"+
		"\u0126\7\6\2\2\u0124\u0126\7/\2\2\u0125\u0122\3\2\2\2\u0125\u0123\3\2"+
		"\2\2\u0125\u0124\3\2\2\2\u0126\27\3\2\2\2\u0127\u0128\7\7\2\2\u0128\u012d"+
		"\5\32\16\2\u0129\u012a\7\3\2\2\u012a\u012c\5\32\16\2\u012b\u0129\3\2\2"+
		"\2\u012c\u012f\3\2\2\2\u012d\u012b\3\2\2\2\u012d\u012e\3\2\2\2\u012e\u0130"+
		"\3\2\2\2\u012f\u012d\3\2\2\2\u0130\u0131\7\b\2\2\u0131\31\3\2\2\2\u0132"+
		"\u0133\7/\2\2\u0133\33\3\2\2\2\u0134\u0135\7/\2\2\u0135\35\3\2\2\2\u0136"+
		"\u0137\7\7\2\2\u0137\u013c\5,\27\2\u0138\u0139\7\3\2\2\u0139\u013b\5,"+
		"\27\2\u013a\u0138\3\2\2\2\u013b\u013e\3\2\2\2\u013c\u013a\3\2\2\2\u013c"+
		"\u013d\3\2\2\2\u013d\u013f\3\2\2\2\u013e\u013c\3\2\2\2\u013f\u0140\7\b"+
		"\2\2\u0140\37\3\2\2\2\u0141\u0142\7\7\2\2\u0142\u0147\5\"\22\2\u0143\u0144"+
		"\7\3\2\2\u0144\u0146\5\"\22\2\u0145\u0143\3\2\2\2\u0146\u0149\3\2\2\2"+
		"\u0147\u0145\3\2\2\2\u0147\u0148\3\2\2\2\u0148\u014a\3\2\2\2\u0149\u0147"+
		"\3\2\2\2\u014a\u014b\7\b\2\2\u014b!\3\2\2\2\u014c\u014f\5,\27\2\u014d"+
		"\u014f\7\33\2\2\u014e\u014c\3\2\2\2\u014e\u014d\3\2\2\2\u014f#\3\2\2\2"+
		"\u0150\u0151\7\25\2\2\u0151\u0152\7\32\2\2\u0152\u0153\5*\26\2\u0153%"+
		"\3\2\2\2\u0154\u0155\7\26\2\2\u0155\u0158\7/\2\2\u0156\u0157\7\3\2\2\u0157"+
		"\u0159\7/\2\2\u0158\u0156\3\2\2\2\u0158\u0159\3\2\2\2\u0159\'\3\2\2\2"+
		"\u015a\u015b\7/\2\2\u015b)\3\2\2\2\u015c\u015d\7/\2\2\u015d+\3\2\2\2\u015e"+
		"\u015f\7\4\2\2\u015f\u0160\7/\2\2\u0160\u0161\7\4\2\2\u0161-\3\2\2\2\u0162"+
		"\u0163\7\5\2\2\u0163\u0164\7/\2\2\u0164\u0165\7\5\2\2\u0165/\3\2\2\2\30"+
		"\63BJMPSV^af\u00e3\u00eb\u00ed\u0101\u010e\u011e\u0125\u012d\u013c\u0147"+
		"\u014e\u0158";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}