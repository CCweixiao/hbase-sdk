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
		T__0=1, T__1=2, T__2=3, LB=4, RB=5, COMMA_CHAR=6, SELECT=7, INSERT=8, 
		DELETE=9, INTO=10, VALUES=11, WHERE=12, FROM=13, ROWKEY=14, STARTKEY=15, 
		ENDKEY=16, MAXVERSION=17, LIMIT=18, TS=19, STARTTS=20, ENDTS=21, IS=22, 
		EQ=23, NOTEQ=24, NULL=25, NOT=26, AND=27, OR=28, LESSEQUAL=29, LESS=30, 
		GREATEREQUAL=31, GREATER=32, NOTMATCH=33, MATCH=34, IN=35, LIKE=36, BETWEEN=37, 
		MISSING=38, STRING=39, SPACE=40, COMMENT_INPUT=41, LINE_COMMENT=42;
	public static final int
		RULE_prog = 0, RULE_inserthqlc = 1, RULE_selecthqlc = 2, RULE_deletehqlc = 3, 
		RULE_wherec = 4, RULE_conditionc = 5, RULE_rowKeyRangeExp = 6, RULE_rowKeyExp = 7, 
		RULE_tsRange = 8, RULE_tsExp = 9, RULE_selectColList = 10, RULE_colList = 11, 
		RULE_col = 12, RULE_funcname = 13, RULE_constantList = 14, RULE_insertValueList = 15, 
		RULE_insertValue = 16, RULE_maxVersionExp = 17, RULE_limitExp = 18, RULE_tableName = 19, 
		RULE_maxversion = 20, RULE_constant = 21, RULE_timestamp = 22, RULE_var = 23;
	public static final String[] ruleNames = {
		"prog", "inserthqlc", "selecthqlc", "deletehqlc", "wherec", "conditionc", 
		"rowKeyRangeExp", "rowKeyExp", "tsRange", "tsExp", "selectColList", "colList", 
		"col", "funcname", "constantList", "insertValueList", "insertValue", "maxVersionExp", 
		"limitExp", "tableName", "maxversion", "constant", "timestamp", "var"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'*'", "'''", "'#'", "'('", "')'", "','", null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"'='", "'!='", null, null, null, null, "'<='", "'<'", "'>='", "'>'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, "LB", "RB", "COMMA_CHAR", "SELECT", "INSERT", 
		"DELETE", "INTO", "VALUES", "WHERE", "FROM", "ROWKEY", "STARTKEY", "ENDKEY", 
		"MAXVERSION", "LIMIT", "TS", "STARTTS", "ENDTS", "IS", "EQ", "NOTEQ", 
		"NULL", "NOT", "AND", "OR", "LESSEQUAL", "LESS", "GREATEREQUAL", "GREATER", 
		"NOTMATCH", "MATCH", "IN", "LIKE", "BETWEEN", "MISSING", "STRING", "SPACE", 
		"COMMENT_INPUT", "LINE_COMMENT"
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
			setState(51);
			switch (_input.LA(1)) {
			case INSERT:
				_localctx = new InsertHqlClContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(48);
				inserthqlc();
				}
				break;
			case SELECT:
				_localctx = new SelectHqlClContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(49);
				selecthqlc();
				}
				break;
			case DELETE:
				_localctx = new DeleteHqlClContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(50);
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
		public ColListContext colList() {
			return getRuleContext(ColListContext.class,0);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public TerminalNode VALUES() { return getToken(HBaseSQLParser.VALUES, 0); }
		public InsertValueListContext insertValueList() {
			return getRuleContext(InsertValueListContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(HBaseSQLParser.WHERE, 0); }
		public TerminalNode ROWKEY() { return getToken(HBaseSQLParser.ROWKEY, 0); }
		public List<TerminalNode> EQ() { return getTokens(HBaseSQLParser.EQ); }
		public TerminalNode EQ(int i) {
			return getToken(HBaseSQLParser.EQ, i);
		}
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public TerminalNode AND() { return getToken(HBaseSQLParser.AND, 0); }
		public TerminalNode TS() { return getToken(HBaseSQLParser.TS, 0); }
		public TsExpContext tsExp() {
			return getRuleContext(TsExpContext.class,0);
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
			setState(53);
			match(INSERT);
			setState(54);
			match(INTO);
			setState(55);
			tableName();
			setState(56);
			match(LB);
			setState(57);
			colList();
			setState(58);
			match(RB);
			setState(59);
			match(VALUES);
			setState(60);
			insertValueList();
			setState(61);
			match(WHERE);
			setState(62);
			match(ROWKEY);
			setState(63);
			match(EQ);
			setState(64);
			rowKeyExp();
			setState(69);
			_la = _input.LA(1);
			if (_la==AND) {
				{
				setState(65);
				match(AND);
				setState(66);
				match(TS);
				setState(67);
				match(EQ);
				setState(68);
				tsExp();
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
		public SelectColListContext selectColList() {
			return getRuleContext(SelectColListContext.class,0);
		}
		public TerminalNode FROM() { return getToken(HBaseSQLParser.FROM, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(HBaseSQLParser.WHERE, 0); }
		public RowKeyRangeExpContext rowKeyRangeExp() {
			return getRuleContext(RowKeyRangeExpContext.class,0);
		}
		public List<TerminalNode> AND() { return getTokens(HBaseSQLParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(HBaseSQLParser.AND, i);
		}
		public WherecContext wherec() {
			return getRuleContext(WherecContext.class,0);
		}
		public MaxVersionExpContext maxVersionExp() {
			return getRuleContext(MaxVersionExpContext.class,0);
		}
		public TsRangeContext tsRange() {
			return getRuleContext(TsRangeContext.class,0);
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
			setState(71);
			match(SELECT);
			setState(72);
			selectColList();
			setState(73);
			match(FROM);
			setState(74);
			tableName();
			setState(75);
			match(WHERE);
			setState(76);
			rowKeyRangeExp();
			setState(79);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(77);
				match(AND);
				setState(78);
				wherec();
				}
				break;
			}
			setState(83);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(81);
				match(AND);
				setState(82);
				maxVersionExp();
				}
				break;
			}
			setState(87);
			_la = _input.LA(1);
			if (_la==AND) {
				{
				setState(85);
				match(AND);
				setState(86);
				tsRange();
				}
			}

			setState(90);
			_la = _input.LA(1);
			if (_la==LIMIT) {
				{
				setState(89);
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
		public SelectColListContext selectColList() {
			return getRuleContext(SelectColListContext.class,0);
		}
		public TerminalNode FROM() { return getToken(HBaseSQLParser.FROM, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(HBaseSQLParser.WHERE, 0); }
		public RowKeyRangeExpContext rowKeyRangeExp() {
			return getRuleContext(RowKeyRangeExpContext.class,0);
		}
		public List<TerminalNode> AND() { return getTokens(HBaseSQLParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(HBaseSQLParser.AND, i);
		}
		public WherecContext wherec() {
			return getRuleContext(WherecContext.class,0);
		}
		public TerminalNode TS() { return getToken(HBaseSQLParser.TS, 0); }
		public TerminalNode EQ() { return getToken(HBaseSQLParser.EQ, 0); }
		public TsExpContext tsExp() {
			return getRuleContext(TsExpContext.class,0);
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
			setState(92);
			match(DELETE);
			setState(93);
			selectColList();
			setState(94);
			match(FROM);
			setState(95);
			tableName();
			setState(96);
			match(WHERE);
			setState(97);
			rowKeyRangeExp();
			setState(100);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(98);
				match(AND);
				setState(99);
				wherec();
				}
				break;
			}
			setState(106);
			_la = _input.LA(1);
			if (_la==AND) {
				{
				setState(102);
				match(AND);
				setState(103);
				match(TS);
				setState(104);
				match(EQ);
				setState(105);
				tsExp();
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
			setState(108);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
		}
		public TerminalNode NOTEQ() { return getToken(HBaseSQLParser.NOTEQ, 0); }
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
		}
		public TerminalNode NOT() { return getToken(HBaseSQLParser.NOT, 0); }
		public TerminalNode BETWEEN() { return getToken(HBaseSQLParser.BETWEEN, 0); }
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
		}
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public TerminalNode MISSING() { return getToken(HBaseSQLParser.MISSING, 0); }
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
		}
		public TerminalNode EQ() { return getToken(HBaseSQLParser.EQ, 0); }
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
		}
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public TerminalNode NOT() { return getToken(HBaseSQLParser.NOT, 0); }
		public TerminalNode NULL() { return getToken(HBaseSQLParser.NULL, 0); }
		public IsnotnullcContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitIsnotnullc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InconstantlistContext extends ConditioncContext {
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
		}
		public TerminalNode NOT() { return getToken(HBaseSQLParser.NOT, 0); }
		public TerminalNode BETWEEN() { return getToken(HBaseSQLParser.BETWEEN, 0); }
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
		}
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public TerminalNode NOT() { return getToken(HBaseSQLParser.NOT, 0); }
		public TerminalNode MISSING() { return getToken(HBaseSQLParser.MISSING, 0); }
		public IsnotmissingcContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitIsnotmissingc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotinconstantlistContext extends ConditioncContext {
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
		}
		public TerminalNode NOT() { return getToken(HBaseSQLParser.NOT, 0); }
		public TerminalNode IN() { return getToken(HBaseSQLParser.IN, 0); }
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
		}
		public TerminalNode IS() { return getToken(HBaseSQLParser.IS, 0); }
		public TerminalNode NULL() { return getToken(HBaseSQLParser.NULL, 0); }
		public IsnullcContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitIsnullc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualconstantContext extends ConditioncContext {
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
		}
		public TerminalNode EQ() { return getToken(HBaseSQLParser.EQ, 0); }
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
		}
		public TerminalNode NOTEQ() { return getToken(HBaseSQLParser.NOTEQ, 0); }
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
		}
		public TerminalNode NOT() { return getToken(HBaseSQLParser.NOT, 0); }
		public TerminalNode IN() { return getToken(HBaseSQLParser.IN, 0); }
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
		public ColContext col() {
			return getRuleContext(ColContext.class,0);
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
			setState(241);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				_localctx = new ConditionwrapperContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(111);
				match(LB);
				setState(112);
				conditionc(0);
				setState(113);
				match(RB);
				}
				break;
			case 2:
				{
				_localctx = new EqualconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(115);
				col();
				setState(116);
				match(EQ);
				setState(117);
				constant();
				}
				break;
			case 3:
				{
				_localctx = new EqualvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(119);
				col();
				setState(120);
				match(EQ);
				setState(121);
				var();
				}
				break;
			case 4:
				{
				_localctx = new LessconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(123);
				col();
				setState(124);
				match(LESS);
				setState(125);
				constant();
				}
				break;
			case 5:
				{
				_localctx = new LessvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(127);
				col();
				setState(128);
				match(LESS);
				setState(129);
				var();
				}
				break;
			case 6:
				{
				_localctx = new GreaterconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(131);
				col();
				setState(132);
				match(GREATER);
				setState(133);
				constant();
				}
				break;
			case 7:
				{
				_localctx = new GreatervarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(135);
				col();
				setState(136);
				match(GREATER);
				setState(137);
				var();
				}
				break;
			case 8:
				{
				_localctx = new LessequalconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(139);
				col();
				setState(140);
				match(LESSEQUAL);
				setState(141);
				constant();
				}
				break;
			case 9:
				{
				_localctx = new LessequalvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(143);
				col();
				setState(144);
				match(LESSEQUAL);
				setState(145);
				var();
				}
				break;
			case 10:
				{
				_localctx = new GreaterequalconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(147);
				col();
				setState(148);
				match(GREATEREQUAL);
				setState(149);
				constant();
				}
				break;
			case 11:
				{
				_localctx = new GreaterequalvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(151);
				col();
				setState(152);
				match(GREATEREQUAL);
				setState(153);
				var();
				}
				break;
			case 12:
				{
				_localctx = new NotequalconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(155);
				col();
				setState(156);
				match(NOTEQ);
				setState(157);
				constant();
				}
				break;
			case 13:
				{
				_localctx = new NotequalvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(159);
				col();
				setState(160);
				match(NOTEQ);
				setState(161);
				var();
				}
				break;
			case 14:
				{
				_localctx = new NotmatchconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(163);
				col();
				setState(164);
				match(NOTMATCH);
				setState(165);
				constant();
				}
				break;
			case 15:
				{
				_localctx = new NotmatchvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(167);
				col();
				setState(168);
				match(NOTMATCH);
				setState(169);
				var();
				}
				break;
			case 16:
				{
				_localctx = new MatchconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(171);
				col();
				setState(172);
				match(MATCH);
				setState(173);
				constant();
				}
				break;
			case 17:
				{
				_localctx = new MatchvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(175);
				col();
				setState(176);
				match(MATCH);
				setState(177);
				var();
				}
				break;
			case 18:
				{
				_localctx = new InconstantlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(179);
				col();
				setState(180);
				match(IN);
				setState(181);
				constantList();
				}
				break;
			case 19:
				{
				_localctx = new InvarlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(183);
				col();
				setState(184);
				match(IN);
				setState(185);
				var();
				}
				break;
			case 20:
				{
				_localctx = new NotinconstantlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(187);
				col();
				setState(188);
				match(NOT);
				setState(189);
				match(IN);
				setState(190);
				constantList();
				}
				break;
			case 21:
				{
				_localctx = new NotinvarlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(192);
				col();
				setState(193);
				match(NOT);
				setState(194);
				match(IN);
				setState(195);
				var();
				}
				break;
			case 22:
				{
				_localctx = new BetweenconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(197);
				col();
				setState(198);
				match(BETWEEN);
				setState(199);
				constant();
				setState(200);
				match(AND);
				setState(201);
				constant();
				}
				break;
			case 23:
				{
				_localctx = new BetweenvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(203);
				col();
				setState(204);
				match(BETWEEN);
				setState(205);
				var();
				setState(206);
				match(AND);
				setState(207);
				var();
				}
				break;
			case 24:
				{
				_localctx = new NotbetweenconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(209);
				col();
				setState(210);
				match(NOT);
				setState(211);
				match(BETWEEN);
				setState(212);
				constant();
				setState(213);
				match(AND);
				setState(214);
				constant();
				}
				break;
			case 25:
				{
				_localctx = new NotbetweenvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(216);
				col();
				setState(217);
				match(NOT);
				setState(218);
				match(BETWEEN);
				setState(219);
				var();
				setState(220);
				match(AND);
				setState(221);
				var();
				}
				break;
			case 26:
				{
				_localctx = new IsnullcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(223);
				col();
				setState(224);
				match(IS);
				setState(225);
				match(NULL);
				}
				break;
			case 27:
				{
				_localctx = new IsnotnullcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(227);
				col();
				setState(228);
				match(IS);
				setState(229);
				match(NOT);
				setState(230);
				match(NULL);
				}
				break;
			case 28:
				{
				_localctx = new IsmissingcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(232);
				col();
				setState(233);
				match(IS);
				setState(234);
				match(MISSING);
				}
				break;
			case 29:
				{
				_localctx = new IsnotmissingcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(236);
				col();
				setState(237);
				match(IS);
				setState(238);
				match(NOT);
				setState(239);
				match(MISSING);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(251);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(249);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new AndconditionContext(new ConditioncContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_conditionc);
						setState(243);
						if (!(precpred(_ctx, 30))) throw new FailedPredicateException(this, "precpred(_ctx, 30)");
						setState(244);
						match(AND);
						setState(245);
						conditionc(31);
						}
						break;
					case 2:
						{
						_localctx = new OrconditionContext(new ConditioncContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_conditionc);
						setState(246);
						if (!(precpred(_ctx, 29))) throw new FailedPredicateException(this, "precpred(_ctx, 29)");
						setState(247);
						match(OR);
						setState(248);
						conditionc(30);
						}
						break;
					}
					} 
				}
				setState(253);
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

	public static class RowKeyRangeExpContext extends ParserRuleContext {
		public RowKeyRangeExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rowKeyRangeExp; }
	 
		public RowKeyRangeExpContext() { }
		public void copyFrom(RowKeyRangeExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Rowkeyrange_startAndEndContext extends RowKeyRangeExpContext {
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public TerminalNode STARTKEY() { return getToken(HBaseSQLParser.STARTKEY, 0); }
		public List<TerminalNode> EQ() { return getTokens(HBaseSQLParser.EQ); }
		public TerminalNode EQ(int i) {
			return getToken(HBaseSQLParser.EQ, i);
		}
		public List<RowKeyExpContext> rowKeyExp() {
			return getRuleContexts(RowKeyExpContext.class);
		}
		public RowKeyExpContext rowKeyExp(int i) {
			return getRuleContext(RowKeyExpContext.class,i);
		}
		public TerminalNode COMMA_CHAR() { return getToken(HBaseSQLParser.COMMA_CHAR, 0); }
		public TerminalNode ENDKEY() { return getToken(HBaseSQLParser.ENDKEY, 0); }
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public Rowkeyrange_startAndEndContext(RowKeyRangeExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkeyrange_startAndEnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkeyrange_endContext extends RowKeyRangeExpContext {
		public TerminalNode ENDKEY() { return getToken(HBaseSQLParser.ENDKEY, 0); }
		public TerminalNode EQ() { return getToken(HBaseSQLParser.EQ, 0); }
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public Rowkeyrange_endContext(RowKeyRangeExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkeyrange_end(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkeyrange_onerowkeyContext extends RowKeyRangeExpContext {
		public TerminalNode ROWKEY() { return getToken(HBaseSQLParser.ROWKEY, 0); }
		public TerminalNode EQ() { return getToken(HBaseSQLParser.EQ, 0); }
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public Rowkeyrange_onerowkeyContext(RowKeyRangeExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkeyrange_onerowkey(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkeyrange_insomekeysContext extends RowKeyRangeExpContext {
		public TerminalNode ROWKEY() { return getToken(HBaseSQLParser.ROWKEY, 0); }
		public TerminalNode IN() { return getToken(HBaseSQLParser.IN, 0); }
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public Rowkeyrange_insomekeysContext(RowKeyRangeExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkeyrange_insomekeys(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkeyrange_startContext extends RowKeyRangeExpContext {
		public TerminalNode STARTKEY() { return getToken(HBaseSQLParser.STARTKEY, 0); }
		public TerminalNode EQ() { return getToken(HBaseSQLParser.EQ, 0); }
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public Rowkeyrange_startContext(RowKeyRangeExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkeyrange_start(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkeyrange_prefixContext extends RowKeyRangeExpContext {
		public TerminalNode ROWKEY() { return getToken(HBaseSQLParser.ROWKEY, 0); }
		public TerminalNode LIKE() { return getToken(HBaseSQLParser.LIKE, 0); }
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public Rowkeyrange_prefixContext(RowKeyRangeExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkeyrange_prefix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RowKeyRangeExpContext rowKeyRangeExp() throws RecognitionException {
		RowKeyRangeExpContext _localctx = new RowKeyRangeExpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_rowKeyRangeExp);
		try {
			setState(279);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				_localctx = new Rowkeyrange_startAndEndContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(254);
				match(LB);
				setState(255);
				match(STARTKEY);
				setState(256);
				match(EQ);
				setState(257);
				rowKeyExp();
				setState(258);
				match(COMMA_CHAR);
				setState(259);
				match(ENDKEY);
				setState(260);
				match(EQ);
				setState(261);
				rowKeyExp();
				setState(262);
				match(RB);
				}
				break;
			case 2:
				_localctx = new Rowkeyrange_startContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(264);
				match(STARTKEY);
				setState(265);
				match(EQ);
				setState(266);
				rowKeyExp();
				}
				break;
			case 3:
				_localctx = new Rowkeyrange_endContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(267);
				match(ENDKEY);
				setState(268);
				match(EQ);
				setState(269);
				rowKeyExp();
				}
				break;
			case 4:
				_localctx = new Rowkeyrange_onerowkeyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(270);
				match(ROWKEY);
				setState(271);
				match(EQ);
				setState(272);
				rowKeyExp();
				}
				break;
			case 5:
				_localctx = new Rowkeyrange_insomekeysContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(273);
				match(ROWKEY);
				setState(274);
				match(IN);
				setState(275);
				rowKeyExp();
				}
				break;
			case 6:
				_localctx = new Rowkeyrange_prefixContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(276);
				match(ROWKEY);
				setState(277);
				match(LIKE);
				setState(278);
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
	public static class Rowkey_inRangeKeyContext extends RowKeyExpContext {
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
	public static class Rowkey_inRangeFuncKeyContext extends RowKeyExpContext {
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public List<RowKeyExpContext> rowKeyExp() {
			return getRuleContexts(RowKeyExpContext.class);
		}
		public RowKeyExpContext rowKeyExp(int i) {
			return getRuleContext(RowKeyExpContext.class,i);
		}
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public Rowkey_inRangeFuncKeyContext(RowKeyExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkey_inRangeFuncKey(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkey_ConstantContext extends RowKeyExpContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public Rowkey_ConstantContext(RowKeyExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkey_Constant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RowKeyExpContext rowKeyExp() throws RecognitionException {
		RowKeyExpContext _localctx = new RowKeyExpContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_rowKeyExp);
		int _la;
		try {
			setState(313);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new Rowkey_WrapperContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(281);
				match(LB);
				setState(282);
				rowKeyExp();
				setState(283);
				match(RB);
				}
				break;
			case 2:
				_localctx = new Rowkey_ConstantContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(285);
				constant();
				}
				break;
			case 3:
				_localctx = new Rowkey_inRangeKeyContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(286);
				match(LB);
				setState(287);
				constant();
				setState(292);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA_CHAR) {
					{
					{
					setState(288);
					match(COMMA_CHAR);
					setState(289);
					constant();
					}
					}
					setState(294);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(295);
				match(RB);
				}
				break;
			case 4:
				_localctx = new Rowkey_FuncConstantContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(297);
				funcname();
				setState(298);
				match(LB);
				setState(299);
				constant();
				setState(300);
				match(RB);
				}
				break;
			case 5:
				_localctx = new Rowkey_inRangeFuncKeyContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(302);
				match(LB);
				setState(303);
				rowKeyExp();
				setState(308);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA_CHAR) {
					{
					{
					setState(304);
					match(COMMA_CHAR);
					setState(305);
					rowKeyExp();
					}
					}
					setState(310);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(311);
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

	public static class TsRangeContext extends ParserRuleContext {
		public TsRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tsRange; }
	 
		public TsRangeContext() { }
		public void copyFrom(TsRangeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TsequalContext extends TsRangeContext {
		public TerminalNode TS() { return getToken(HBaseSQLParser.TS, 0); }
		public TerminalNode EQ() { return getToken(HBaseSQLParser.EQ, 0); }
		public TsExpContext tsExp() {
			return getRuleContext(TsExpContext.class,0);
		}
		public TsequalContext(TsRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTsequal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Tsrange_startContext extends TsRangeContext {
		public TerminalNode STARTTS() { return getToken(HBaseSQLParser.STARTTS, 0); }
		public TerminalNode EQ() { return getToken(HBaseSQLParser.EQ, 0); }
		public TsExpContext tsExp() {
			return getRuleContext(TsExpContext.class,0);
		}
		public Tsrange_startContext(TsRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTsrange_start(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Tsrange_endContext extends TsRangeContext {
		public TerminalNode ENDTS() { return getToken(HBaseSQLParser.ENDTS, 0); }
		public TerminalNode EQ() { return getToken(HBaseSQLParser.EQ, 0); }
		public TsExpContext tsExp() {
			return getRuleContext(TsExpContext.class,0);
		}
		public Tsrange_endContext(TsRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTsrange_end(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Tsrange_startAndEndContext extends TsRangeContext {
		public TerminalNode LB() { return getToken(HBaseSQLParser.LB, 0); }
		public TerminalNode STARTTS() { return getToken(HBaseSQLParser.STARTTS, 0); }
		public List<TerminalNode> EQ() { return getTokens(HBaseSQLParser.EQ); }
		public TerminalNode EQ(int i) {
			return getToken(HBaseSQLParser.EQ, i);
		}
		public List<TsExpContext> tsExp() {
			return getRuleContexts(TsExpContext.class);
		}
		public TsExpContext tsExp(int i) {
			return getRuleContext(TsExpContext.class,i);
		}
		public TerminalNode COMMA_CHAR() { return getToken(HBaseSQLParser.COMMA_CHAR, 0); }
		public TerminalNode ENDTS() { return getToken(HBaseSQLParser.ENDTS, 0); }
		public TerminalNode RB() { return getToken(HBaseSQLParser.RB, 0); }
		public Tsrange_startAndEndContext(TsRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTsrange_startAndEnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TsRangeContext tsRange() throws RecognitionException {
		TsRangeContext _localctx = new TsRangeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_tsRange);
		try {
			setState(334);
			switch (_input.LA(1)) {
			case LB:
				_localctx = new Tsrange_startAndEndContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(315);
				match(LB);
				setState(316);
				match(STARTTS);
				setState(317);
				match(EQ);
				setState(318);
				tsExp();
				setState(319);
				match(COMMA_CHAR);
				setState(320);
				match(ENDTS);
				setState(321);
				match(EQ);
				setState(322);
				tsExp();
				setState(323);
				match(RB);
				}
				break;
			case STARTTS:
				_localctx = new Tsrange_startContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(325);
				match(STARTTS);
				setState(326);
				match(EQ);
				setState(327);
				tsExp();
				}
				break;
			case ENDTS:
				_localctx = new Tsrange_endContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(328);
				match(ENDTS);
				setState(329);
				match(EQ);
				setState(330);
				tsExp();
				}
				break;
			case TS:
				_localctx = new TsequalContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(331);
				match(TS);
				setState(332);
				match(EQ);
				setState(333);
				tsExp();
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

	public static class TsExpContext extends ParserRuleContext {
		public TimestampContext timestamp() {
			return getRuleContext(TimestampContext.class,0);
		}
		public TsExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tsExp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTsExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TsExpContext tsExp() throws RecognitionException {
		TsExpContext _localctx = new TsExpContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_tsExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			timestamp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectColListContext extends ParserRuleContext {
		public SelectColListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectColList; }
	 
		public SelectColListContext() { }
		public void copyFrom(SelectColListContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ColList_StarContext extends SelectColListContext {
		public ColList_StarContext(SelectColListContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitColList_Star(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ColList_ColListContext extends SelectColListContext {
		public ColListContext colList() {
			return getRuleContext(ColListContext.class,0);
		}
		public ColList_ColListContext(SelectColListContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitColList_ColList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectColListContext selectColList() throws RecognitionException {
		SelectColListContext _localctx = new SelectColListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_selectColList);
		try {
			setState(340);
			switch (_input.LA(1)) {
			case T__0:
				_localctx = new ColList_StarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(338);
				match(T__0);
				}
				break;
			case STRING:
				_localctx = new ColList_ColListContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(339);
				colList();
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

	public static class ColListContext extends ParserRuleContext {
		public List<ColContext> col() {
			return getRuleContexts(ColContext.class);
		}
		public ColContext col(int i) {
			return getRuleContext(ColContext.class,i);
		}
		public ColListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitColList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColListContext colList() throws RecognitionException {
		ColListContext _localctx = new ColListContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_colList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(342);
			col();
			setState(347);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA_CHAR) {
				{
				{
				setState(343);
				match(COMMA_CHAR);
				setState(344);
				col();
				}
				}
				setState(349);
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

	public static class ColContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(HBaseSQLParser.STRING, 0); }
		public ColContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_col; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitCol(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColContext col() throws RecognitionException {
		ColContext _localctx = new ColContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_col);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(350);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
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
		public TerminalNode STRING() { return getToken(HBaseSQLParser.STRING, 0); }
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
			setState(352);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
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
			setState(354);
			match(LB);
			setState(355);
			constant();
			setState(360);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA_CHAR) {
				{
				{
				setState(356);
				match(COMMA_CHAR);
				setState(357);
				constant();
				}
				}
				setState(362);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(363);
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
			setState(365);
			match(LB);
			setState(366);
			insertValue();
			setState(371);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA_CHAR) {
				{
				{
				setState(367);
				match(COMMA_CHAR);
				setState(368);
				insertValue();
				}
				}
				setState(373);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(374);
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
			setState(378);
			switch (_input.LA(1)) {
			case T__1:
			case STRING:
				_localctx = new InsertValue_NotNullContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(376);
				constant();
				}
				break;
			case NULL:
				_localctx = new InsertValue_NullContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(377);
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
		public TerminalNode EQ() { return getToken(HBaseSQLParser.EQ, 0); }
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
			setState(380);
			match(MAXVERSION);
			setState(381);
			match(EQ);
			setState(382);
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
		public TerminalNode STRING() { return getToken(HBaseSQLParser.STRING, 0); }
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384);
			match(LIMIT);
			setState(385);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
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
		public TerminalNode STRING() { return getToken(HBaseSQLParser.STRING, 0); }
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
			setState(387);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
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
		public TerminalNode STRING() { return getToken(HBaseSQLParser.STRING, 0); }
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
			setState(389);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
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
		public TerminalNode STRING() { return getToken(HBaseSQLParser.STRING, 0); }
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
			setState(395);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(391);
				match(T__1);
				setState(392);
				match(STRING);
				setState(393);
				match(T__1);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(394);
				match(STRING);
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

	public static class TimestampContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(HBaseSQLParser.STRING, 0); }
		public TimestampContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timestamp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTimestamp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TimestampContext timestamp() throws RecognitionException {
		TimestampContext _localctx = new TimestampContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_timestamp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(397);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
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
		public TerminalNode STRING() { return getToken(HBaseSQLParser.STRING, 0); }
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
		enterRule(_localctx, 46, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(399);
			match(T__2);
			setState(400);
			match(STRING);
			setState(401);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3,\u0196\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\3\2\3\2\5\2\66\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\5\3H\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4R\n\4\3"+
		"\4\3\4\5\4V\n\4\3\4\3\4\5\4Z\n\4\3\4\5\4]\n\4\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\5\5g\n\5\3\5\3\5\3\5\3\5\5\5m\n\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\5\7\u00f4\n\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u00fc\n\7\f\7"+
		"\16\7\u00ff\13\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u011a\n\b\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u0125\n\t\f\t\16\t\u0128\13\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u0135\n\t\f\t\16\t\u0138\13\t\3"+
		"\t\3\t\5\t\u013c\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u0151\n\n\3\13\3\13\3\f\3\f\5\f\u0157\n"+
		"\f\3\r\3\r\3\r\7\r\u015c\n\r\f\r\16\r\u015f\13\r\3\16\3\16\3\17\3\17\3"+
		"\20\3\20\3\20\3\20\7\20\u0169\n\20\f\20\16\20\u016c\13\20\3\20\3\20\3"+
		"\21\3\21\3\21\3\21\7\21\u0174\n\21\f\21\16\21\u0177\13\21\3\21\3\21\3"+
		"\22\3\22\5\22\u017d\n\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25"+
		"\3\26\3\26\3\27\3\27\3\27\3\27\5\27\u018e\n\27\3\30\3\30\3\31\3\31\3\31"+
		"\3\31\3\31\2\3\f\32\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60"+
		"\2\2\u01b8\2\65\3\2\2\2\4\67\3\2\2\2\6I\3\2\2\2\b^\3\2\2\2\nn\3\2\2\2"+
		"\f\u00f3\3\2\2\2\16\u0119\3\2\2\2\20\u013b\3\2\2\2\22\u0150\3\2\2\2\24"+
		"\u0152\3\2\2\2\26\u0156\3\2\2\2\30\u0158\3\2\2\2\32\u0160\3\2\2\2\34\u0162"+
		"\3\2\2\2\36\u0164\3\2\2\2 \u016f\3\2\2\2\"\u017c\3\2\2\2$\u017e\3\2\2"+
		"\2&\u0182\3\2\2\2(\u0185\3\2\2\2*\u0187\3\2\2\2,\u018d\3\2\2\2.\u018f"+
		"\3\2\2\2\60\u0191\3\2\2\2\62\66\5\4\3\2\63\66\5\6\4\2\64\66\5\b\5\2\65"+
		"\62\3\2\2\2\65\63\3\2\2\2\65\64\3\2\2\2\66\3\3\2\2\2\678\7\n\2\289\7\f"+
		"\2\29:\5(\25\2:;\7\6\2\2;<\5\30\r\2<=\7\7\2\2=>\7\r\2\2>?\5 \21\2?@\7"+
		"\16\2\2@A\7\20\2\2AB\7\31\2\2BG\5\20\t\2CD\7\35\2\2DE\7\25\2\2EF\7\31"+
		"\2\2FH\5\24\13\2GC\3\2\2\2GH\3\2\2\2H\5\3\2\2\2IJ\7\t\2\2JK\5\26\f\2K"+
		"L\7\17\2\2LM\5(\25\2MN\7\16\2\2NQ\5\16\b\2OP\7\35\2\2PR\5\n\6\2QO\3\2"+
		"\2\2QR\3\2\2\2RU\3\2\2\2ST\7\35\2\2TV\5$\23\2US\3\2\2\2UV\3\2\2\2VY\3"+
		"\2\2\2WX\7\35\2\2XZ\5\22\n\2YW\3\2\2\2YZ\3\2\2\2Z\\\3\2\2\2[]\5&\24\2"+
		"\\[\3\2\2\2\\]\3\2\2\2]\7\3\2\2\2^_\7\13\2\2_`\5\26\f\2`a\7\17\2\2ab\5"+
		"(\25\2bc\7\16\2\2cf\5\16\b\2de\7\35\2\2eg\5\n\6\2fd\3\2\2\2fg\3\2\2\2"+
		"gl\3\2\2\2hi\7\35\2\2ij\7\25\2\2jk\7\31\2\2km\5\24\13\2lh\3\2\2\2lm\3"+
		"\2\2\2m\t\3\2\2\2no\5\f\7\2o\13\3\2\2\2pq\b\7\1\2qr\7\6\2\2rs\5\f\7\2"+
		"st\7\7\2\2t\u00f4\3\2\2\2uv\5\32\16\2vw\7\31\2\2wx\5,\27\2x\u00f4\3\2"+
		"\2\2yz\5\32\16\2z{\7\31\2\2{|\5\60\31\2|\u00f4\3\2\2\2}~\5\32\16\2~\177"+
		"\7 \2\2\177\u0080\5,\27\2\u0080\u00f4\3\2\2\2\u0081\u0082\5\32\16\2\u0082"+
		"\u0083\7 \2\2\u0083\u0084\5\60\31\2\u0084\u00f4\3\2\2\2\u0085\u0086\5"+
		"\32\16\2\u0086\u0087\7\"\2\2\u0087\u0088\5,\27\2\u0088\u00f4\3\2\2\2\u0089"+
		"\u008a\5\32\16\2\u008a\u008b\7\"\2\2\u008b\u008c\5\60\31\2\u008c\u00f4"+
		"\3\2\2\2\u008d\u008e\5\32\16\2\u008e\u008f\7\37\2\2\u008f\u0090\5,\27"+
		"\2\u0090\u00f4\3\2\2\2\u0091\u0092\5\32\16\2\u0092\u0093\7\37\2\2\u0093"+
		"\u0094\5\60\31\2\u0094\u00f4\3\2\2\2\u0095\u0096\5\32\16\2\u0096\u0097"+
		"\7!\2\2\u0097\u0098\5,\27\2\u0098\u00f4\3\2\2\2\u0099\u009a\5\32\16\2"+
		"\u009a\u009b\7!\2\2\u009b\u009c\5\60\31\2\u009c\u00f4\3\2\2\2\u009d\u009e"+
		"\5\32\16\2\u009e\u009f\7\32\2\2\u009f\u00a0\5,\27\2\u00a0\u00f4\3\2\2"+
		"\2\u00a1\u00a2\5\32\16\2\u00a2\u00a3\7\32\2\2\u00a3\u00a4\5\60\31\2\u00a4"+
		"\u00f4\3\2\2\2\u00a5\u00a6\5\32\16\2\u00a6\u00a7\7#\2\2\u00a7\u00a8\5"+
		",\27\2\u00a8\u00f4\3\2\2\2\u00a9\u00aa\5\32\16\2\u00aa\u00ab\7#\2\2\u00ab"+
		"\u00ac\5\60\31\2\u00ac\u00f4\3\2\2\2\u00ad\u00ae\5\32\16\2\u00ae\u00af"+
		"\7$\2\2\u00af\u00b0\5,\27\2\u00b0\u00f4\3\2\2\2\u00b1\u00b2\5\32\16\2"+
		"\u00b2\u00b3\7$\2\2\u00b3\u00b4\5\60\31\2\u00b4\u00f4\3\2\2\2\u00b5\u00b6"+
		"\5\32\16\2\u00b6\u00b7\7%\2\2\u00b7\u00b8\5\36\20\2\u00b8\u00f4\3\2\2"+
		"\2\u00b9\u00ba\5\32\16\2\u00ba\u00bb\7%\2\2\u00bb\u00bc\5\60\31\2\u00bc"+
		"\u00f4\3\2\2\2\u00bd\u00be\5\32\16\2\u00be\u00bf\7\34\2\2\u00bf\u00c0"+
		"\7%\2\2\u00c0\u00c1\5\36\20\2\u00c1\u00f4\3\2\2\2\u00c2\u00c3\5\32\16"+
		"\2\u00c3\u00c4\7\34\2\2\u00c4\u00c5\7%\2\2\u00c5\u00c6\5\60\31\2\u00c6"+
		"\u00f4\3\2\2\2\u00c7\u00c8\5\32\16\2\u00c8\u00c9\7\'\2\2\u00c9\u00ca\5"+
		",\27\2\u00ca\u00cb\7\35\2\2\u00cb\u00cc\5,\27\2\u00cc\u00f4\3\2\2\2\u00cd"+
		"\u00ce\5\32\16\2\u00ce\u00cf\7\'\2\2\u00cf\u00d0\5\60\31\2\u00d0\u00d1"+
		"\7\35\2\2\u00d1\u00d2\5\60\31\2\u00d2\u00f4\3\2\2\2\u00d3\u00d4\5\32\16"+
		"\2\u00d4\u00d5\7\34\2\2\u00d5\u00d6\7\'\2\2\u00d6\u00d7\5,\27\2\u00d7"+
		"\u00d8\7\35\2\2\u00d8\u00d9\5,\27\2\u00d9\u00f4\3\2\2\2\u00da\u00db\5"+
		"\32\16\2\u00db\u00dc\7\34\2\2\u00dc\u00dd\7\'\2\2\u00dd\u00de\5\60\31"+
		"\2\u00de\u00df\7\35\2\2\u00df\u00e0\5\60\31\2\u00e0\u00f4\3\2\2\2\u00e1"+
		"\u00e2\5\32\16\2\u00e2\u00e3\7\30\2\2\u00e3\u00e4\7\33\2\2\u00e4\u00f4"+
		"\3\2\2\2\u00e5\u00e6\5\32\16\2\u00e6\u00e7\7\30\2\2\u00e7\u00e8\7\34\2"+
		"\2\u00e8\u00e9\7\33\2\2\u00e9\u00f4\3\2\2\2\u00ea\u00eb\5\32\16\2\u00eb"+
		"\u00ec\7\30\2\2\u00ec\u00ed\7(\2\2\u00ed\u00f4\3\2\2\2\u00ee\u00ef\5\32"+
		"\16\2\u00ef\u00f0\7\30\2\2\u00f0\u00f1\7\34\2\2\u00f1\u00f2\7(\2\2\u00f2"+
		"\u00f4\3\2\2\2\u00f3p\3\2\2\2\u00f3u\3\2\2\2\u00f3y\3\2\2\2\u00f3}\3\2"+
		"\2\2\u00f3\u0081\3\2\2\2\u00f3\u0085\3\2\2\2\u00f3\u0089\3\2\2\2\u00f3"+
		"\u008d\3\2\2\2\u00f3\u0091\3\2\2\2\u00f3\u0095\3\2\2\2\u00f3\u0099\3\2"+
		"\2\2\u00f3\u009d\3\2\2\2\u00f3\u00a1\3\2\2\2\u00f3\u00a5\3\2\2\2\u00f3"+
		"\u00a9\3\2\2\2\u00f3\u00ad\3\2\2\2\u00f3\u00b1\3\2\2\2\u00f3\u00b5\3\2"+
		"\2\2\u00f3\u00b9\3\2\2\2\u00f3\u00bd\3\2\2\2\u00f3\u00c2\3\2\2\2\u00f3"+
		"\u00c7\3\2\2\2\u00f3\u00cd\3\2\2\2\u00f3\u00d3\3\2\2\2\u00f3\u00da\3\2"+
		"\2\2\u00f3\u00e1\3\2\2\2\u00f3\u00e5\3\2\2\2\u00f3\u00ea\3\2\2\2\u00f3"+
		"\u00ee\3\2\2\2\u00f4\u00fd\3\2\2\2\u00f5\u00f6\f \2\2\u00f6\u00f7\7\35"+
		"\2\2\u00f7\u00fc\5\f\7!\u00f8\u00f9\f\37\2\2\u00f9\u00fa\7\36\2\2\u00fa"+
		"\u00fc\5\f\7 \u00fb\u00f5\3\2\2\2\u00fb\u00f8\3\2\2\2\u00fc\u00ff\3\2"+
		"\2\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\r\3\2\2\2\u00ff\u00fd"+
		"\3\2\2\2\u0100\u0101\7\6\2\2\u0101\u0102\7\21\2\2\u0102\u0103\7\31\2\2"+
		"\u0103\u0104\5\20\t\2\u0104\u0105\7\b\2\2\u0105\u0106\7\22\2\2\u0106\u0107"+
		"\7\31\2\2\u0107\u0108\5\20\t\2\u0108\u0109\7\7\2\2\u0109\u011a\3\2\2\2"+
		"\u010a\u010b\7\21\2\2\u010b\u010c\7\31\2\2\u010c\u011a\5\20\t\2\u010d"+
		"\u010e\7\22\2\2\u010e\u010f\7\31\2\2\u010f\u011a\5\20\t\2\u0110\u0111"+
		"\7\20\2\2\u0111\u0112\7\31\2\2\u0112\u011a\5\20\t\2\u0113\u0114\7\20\2"+
		"\2\u0114\u0115\7%\2\2\u0115\u011a\5\20\t\2\u0116\u0117\7\20\2\2\u0117"+
		"\u0118\7&\2\2\u0118\u011a\5\20\t\2\u0119\u0100\3\2\2\2\u0119\u010a\3\2"+
		"\2\2\u0119\u010d\3\2\2\2\u0119\u0110\3\2\2\2\u0119\u0113\3\2\2\2\u0119"+
		"\u0116\3\2\2\2\u011a\17\3\2\2\2\u011b\u011c\7\6\2\2\u011c\u011d\5\20\t"+
		"\2\u011d\u011e\7\7\2\2\u011e\u013c\3\2\2\2\u011f\u013c\5,\27\2\u0120\u0121"+
		"\7\6\2\2\u0121\u0126\5,\27\2\u0122\u0123\7\b\2\2\u0123\u0125\5,\27\2\u0124"+
		"\u0122\3\2\2\2\u0125\u0128\3\2\2\2\u0126\u0124\3\2\2\2\u0126\u0127\3\2"+
		"\2\2\u0127\u0129\3\2\2\2\u0128\u0126\3\2\2\2\u0129\u012a\7\7\2\2\u012a"+
		"\u013c\3\2\2\2\u012b\u012c\5\34\17\2\u012c\u012d\7\6\2\2\u012d\u012e\5"+
		",\27\2\u012e\u012f\7\7\2\2\u012f\u013c\3\2\2\2\u0130\u0131\7\6\2\2\u0131"+
		"\u0136\5\20\t\2\u0132\u0133\7\b\2\2\u0133\u0135\5\20\t\2\u0134\u0132\3"+
		"\2\2\2\u0135\u0138\3\2\2\2\u0136\u0134\3\2\2\2\u0136\u0137\3\2\2\2\u0137"+
		"\u0139\3\2\2\2\u0138\u0136\3\2\2\2\u0139\u013a\7\7\2\2\u013a\u013c\3\2"+
		"\2\2\u013b\u011b\3\2\2\2\u013b\u011f\3\2\2\2\u013b\u0120\3\2\2\2\u013b"+
		"\u012b\3\2\2\2\u013b\u0130\3\2\2\2\u013c\21\3\2\2\2\u013d\u013e\7\6\2"+
		"\2\u013e\u013f\7\26\2\2\u013f\u0140\7\31\2\2\u0140\u0141\5\24\13\2\u0141"+
		"\u0142\7\b\2\2\u0142\u0143\7\27\2\2\u0143\u0144\7\31\2\2\u0144\u0145\5"+
		"\24\13\2\u0145\u0146\7\7\2\2\u0146\u0151\3\2\2\2\u0147\u0148\7\26\2\2"+
		"\u0148\u0149\7\31\2\2\u0149\u0151\5\24\13\2\u014a\u014b\7\27\2\2\u014b"+
		"\u014c\7\31\2\2\u014c\u0151\5\24\13\2\u014d\u014e\7\25\2\2\u014e\u014f"+
		"\7\31\2\2\u014f\u0151\5\24\13\2\u0150\u013d\3\2\2\2\u0150\u0147\3\2\2"+
		"\2\u0150\u014a\3\2\2\2\u0150\u014d\3\2\2\2\u0151\23\3\2\2\2\u0152\u0153"+
		"\5.\30\2\u0153\25\3\2\2\2\u0154\u0157\7\3\2\2\u0155\u0157\5\30\r\2\u0156"+
		"\u0154\3\2\2\2\u0156\u0155\3\2\2\2\u0157\27\3\2\2\2\u0158\u015d\5\32\16"+
		"\2\u0159\u015a\7\b\2\2\u015a\u015c\5\32\16\2\u015b\u0159\3\2\2\2\u015c"+
		"\u015f\3\2\2\2\u015d\u015b\3\2\2\2\u015d\u015e\3\2\2\2\u015e\31\3\2\2"+
		"\2\u015f\u015d\3\2\2\2\u0160\u0161\7)\2\2\u0161\33\3\2\2\2\u0162\u0163"+
		"\7)\2\2\u0163\35\3\2\2\2\u0164\u0165\7\6\2\2\u0165\u016a\5,\27\2\u0166"+
		"\u0167\7\b\2\2\u0167\u0169\5,\27\2\u0168\u0166\3\2\2\2\u0169\u016c\3\2"+
		"\2\2\u016a\u0168\3\2\2\2\u016a\u016b\3\2\2\2\u016b\u016d\3\2\2\2\u016c"+
		"\u016a\3\2\2\2\u016d\u016e\7\7\2\2\u016e\37\3\2\2\2\u016f\u0170\7\6\2"+
		"\2\u0170\u0175\5\"\22\2\u0171\u0172\7\b\2\2\u0172\u0174\5\"\22\2\u0173"+
		"\u0171\3\2\2\2\u0174\u0177\3\2\2\2\u0175\u0173\3\2\2\2\u0175\u0176\3\2"+
		"\2\2\u0176\u0178\3\2\2\2\u0177\u0175\3\2\2\2\u0178\u0179\7\7\2\2\u0179"+
		"!\3\2\2\2\u017a\u017d\5,\27\2\u017b\u017d\7\33\2\2\u017c\u017a\3\2\2\2"+
		"\u017c\u017b\3\2\2\2\u017d#\3\2\2\2\u017e\u017f\7\23\2\2\u017f\u0180\7"+
		"\31\2\2\u0180\u0181\5*\26\2\u0181%\3\2\2\2\u0182\u0183\7\24\2\2\u0183"+
		"\u0184\7)\2\2\u0184\'\3\2\2\2\u0185\u0186\7)\2\2\u0186)\3\2\2\2\u0187"+
		"\u0188\7)\2\2\u0188+\3\2\2\2\u0189\u018a\7\4\2\2\u018a\u018b\7)\2\2\u018b"+
		"\u018e\7\4\2\2\u018c\u018e\7)\2\2\u018d\u0189\3\2\2\2\u018d\u018c\3\2"+
		"\2\2\u018e-\3\2\2\2\u018f\u0190\7)\2\2\u0190/\3\2\2\2\u0191\u0192\7\5"+
		"\2\2\u0192\u0193\7)\2\2\u0193\u0194\7\5\2\2\u0194\61\3\2\2\2\30\65GQU"+
		"Y\\fl\u00f3\u00fb\u00fd\u0119\u0126\u0136\u013b\u0150\u0156\u015d\u016a"+
		"\u0175\u017c\u018d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}