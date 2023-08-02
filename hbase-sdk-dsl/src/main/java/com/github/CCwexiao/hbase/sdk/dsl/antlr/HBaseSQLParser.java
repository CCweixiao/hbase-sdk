// Generated from HBaseSQL.g4 by ANTLR 4.5.1

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
		T__0=1, T__1=2, T__2=3, T__3=4, LR_BRACKET=5, RR_BRACKET=6, COMMA=7, SEMICOLON=8, 
		EQ=9, NOTEQ=10, GREATER=11, GREATEREQUAL=12, LESS=13, LESSEQUAL=14, PLUS=15, 
		MINUS=16, ASTERISK=17, SLASH=18, MOD=19, IS=20, NOTMATCH=21, MATCH=22, 
		BETWEEN=23, MISSING=24, LIMIT=25, TS=26, STARTTS=27, ENDTS=28, CREATE=29, 
		DROP=30, VIRTUAL=31, TABLE=32, WITH=33, PROPERTIES=34, NULLABLE=35, INSERT=36, 
		INTO=37, VALUES=38, SELECT=39, UPDATE=40, SET=41, DELETE=42, FROM=43, 
		COLUMNFAMILY=44, WHERE=45, AND=46, OR=47, IN=48, LIKE=49, NULL=50, NOT=51, 
		INTEGER=52, ROWKEY=53, ISROWKEY=54, STARTKEY=55, ENDKEY=56, MAXVERSION=57, 
		ID=58, STRING=59, SPACE=60, COMMENT_INPUT=61, LINE_COMMENT=62;
	public static final int
		RULE_query = 0, RULE_createTableStatement = 1, RULE_dropTableStatement = 2, 
		RULE_tableName = 3, RULE_fields = 4, RULE_field = 5, RULE_fieldName = 6, 
		RULE_fieldType = 7, RULE_properties = 8, RULE_keyValue = 9, RULE_column = 10, 
		RULE_funcname = 11, RULE_selectColList = 12, RULE_columnList = 13, RULE_insertStatement = 14, 
		RULE_multiValueList = 15, RULE_valueList = 16, RULE_value = 17, RULE_selectStatement = 18, 
		RULE_deleteStatement = 19, RULE_rowKeyRangeExp = 20, RULE_rowKeyExp = 21, 
		RULE_funcParamsList = 22, RULE_funcCol = 23, RULE_gtOper = 24, RULE_leOper = 25, 
		RULE_tsRange = 26, RULE_constant = 27, RULE_constantList = 28, RULE_var = 29, 
		RULE_varList = 30, RULE_multiVersionExp = 31, RULE_maxVersionExp = 32, 
		RULE_integer = 33, RULE_tsExp = 34, RULE_timestamp = 35, RULE_wherec = 36, 
		RULE_conditionc = 37, RULE_limitExp = 38;
	public static final String[] ruleNames = {
		"query", "createTableStatement", "dropTableStatement", "tableName", "fields", 
		"field", "fieldName", "fieldType", "properties", "keyValue", "column", 
		"funcname", "selectColList", "columnList", "insertStatement", "multiValueList", 
		"valueList", "value", "selectStatement", "deleteStatement", "rowKeyRangeExp", 
		"rowKeyExp", "funcParamsList", "funcCol", "gtOper", "leOper", "tsRange", 
		"constant", "constantList", "var", "varList", "multiVersionExp", "maxVersionExp", 
		"integer", "tsExp", "timestamp", "wherec", "conditionc", "limitExp"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'`'", "'''", "'${'", "'}'", "'('", "')'", "','", "';'", "'='", 
		"'!='", "'>'", "'>='", "'<'", "'<='", "'+'", "'-'", "'*'", "'/'", "'%'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "LR_BRACKET", "RR_BRACKET", "COMMA", "SEMICOLON", 
		"EQ", "NOTEQ", "GREATER", "GREATEREQUAL", "LESS", "LESSEQUAL", "PLUS", 
		"MINUS", "ASTERISK", "SLASH", "MOD", "IS", "NOTMATCH", "MATCH", "BETWEEN", 
		"MISSING", "LIMIT", "TS", "STARTTS", "ENDTS", "CREATE", "DROP", "VIRTUAL", 
		"TABLE", "WITH", "PROPERTIES", "NULLABLE", "INSERT", "INTO", "VALUES", 
		"SELECT", "UPDATE", "SET", "DELETE", "FROM", "COLUMNFAMILY", "WHERE", 
		"AND", "OR", "IN", "LIKE", "NULL", "NOT", "INTEGER", "ROWKEY", "ISROWKEY", 
		"STARTKEY", "ENDKEY", "MAXVERSION", "ID", "STRING", "SPACE", "COMMENT_INPUT", 
		"LINE_COMMENT"
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
	public static class QueryContext extends ParserRuleContext {
		public CreateTableStatementContext createTableStatement() {
			return getRuleContext(CreateTableStatementContext.class,0);
		}
		public DropTableStatementContext dropTableStatement() {
			return getRuleContext(DropTableStatementContext.class,0);
		}
		public InsertStatementContext insertStatement() {
			return getRuleContext(InsertStatementContext.class,0);
		}
		public SelectStatementContext selectStatement() {
			return getRuleContext(SelectStatementContext.class,0);
		}
		public DeleteStatementContext deleteStatement() {
			return getRuleContext(DeleteStatementContext.class,0);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_query);
		try {
			setState(83);
			switch (_input.LA(1)) {
			case CREATE:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				createTableStatement();
				}
				break;
			case DROP:
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				dropTableStatement();
				}
				break;
			case INSERT:
				enterOuterAlt(_localctx, 3);
				{
				setState(80);
				insertStatement();
				}
				break;
			case SELECT:
				enterOuterAlt(_localctx, 4);
				{
				setState(81);
				selectStatement();
				}
				break;
			case DELETE:
				enterOuterAlt(_localctx, 5);
				{
				setState(82);
				deleteStatement();
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

	public static class CreateTableStatementContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(HBaseSQLParser.CREATE, 0); }
		public TerminalNode VIRTUAL() { return getToken(HBaseSQLParser.VIRTUAL, 0); }
		public TerminalNode TABLE() { return getToken(HBaseSQLParser.TABLE, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public FieldsContext fields() {
			return getRuleContext(FieldsContext.class,0);
		}
		public TerminalNode WITH() { return getToken(HBaseSQLParser.WITH, 0); }
		public TerminalNode PROPERTIES() { return getToken(HBaseSQLParser.PROPERTIES, 0); }
		public PropertiesContext properties() {
			return getRuleContext(PropertiesContext.class,0);
		}
		public CreateTableStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTableStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitCreateTableStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateTableStatementContext createTableStatement() throws RecognitionException {
		CreateTableStatementContext _localctx = new CreateTableStatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_createTableStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(CREATE);
			setState(86);
			match(VIRTUAL);
			setState(87);
			match(TABLE);
			setState(88);
			tableName();
			setState(89);
			match(LR_BRACKET);
			setState(90);
			fields();
			setState(91);
			match(RR_BRACKET);
			setState(98);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(92);
				match(WITH);
				setState(93);
				match(PROPERTIES);
				setState(94);
				match(LR_BRACKET);
				setState(95);
				properties();
				setState(96);
				match(RR_BRACKET);
				}
			}

			setState(101);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(100);
				match(SEMICOLON);
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

	public static class DropTableStatementContext extends ParserRuleContext {
		public TerminalNode DROP() { return getToken(HBaseSQLParser.DROP, 0); }
		public TerminalNode VIRTUAL() { return getToken(HBaseSQLParser.VIRTUAL, 0); }
		public TerminalNode TABLE() { return getToken(HBaseSQLParser.TABLE, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public DropTableStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dropTableStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitDropTableStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DropTableStatementContext dropTableStatement() throws RecognitionException {
		DropTableStatementContext _localctx = new DropTableStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_dropTableStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(DROP);
			setState(104);
			match(VIRTUAL);
			setState(105);
			match(TABLE);
			setState(106);
			tableName();
			setState(108);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(107);
				match(SEMICOLON);
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
		public TerminalNode ID() { return getToken(HBaseSQLParser.ID, 0); }
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
		enterRule(_localctx, 6, RULE_tableName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldsContext extends ParserRuleContext {
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public FieldsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fields; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitFields(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldsContext fields() throws RecognitionException {
		FieldsContext _localctx = new FieldsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_fields);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			field();
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(113);
				match(COMMA);
				setState(114);
				field();
				}
				}
				setState(119);
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

	public static class FieldContext extends ParserRuleContext {
		public FieldNameContext fieldName() {
			return getRuleContext(FieldNameContext.class,0);
		}
		public FieldTypeContext fieldType() {
			return getRuleContext(FieldTypeContext.class,0);
		}
		public TerminalNode ISROWKEY() { return getToken(HBaseSQLParser.ISROWKEY, 0); }
		public TerminalNode NULLABLE() { return getToken(HBaseSQLParser.NULLABLE, 0); }
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_field);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			fieldName();
			setState(121);
			fieldType();
			setState(123);
			_la = _input.LA(1);
			if (_la==ISROWKEY) {
				{
				setState(122);
				match(ISROWKEY);
				}
			}

			setState(126);
			_la = _input.LA(1);
			if (_la==NULLABLE) {
				{
				setState(125);
				match(NULLABLE);
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

	public static class FieldNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(HBaseSQLParser.ID, 0); }
		public FieldNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitFieldName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldNameContext fieldName() throws RecognitionException {
		FieldNameContext _localctx = new FieldNameContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_fieldName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldTypeContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(HBaseSQLParser.ID, 0); }
		public FieldTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitFieldType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldTypeContext fieldType() throws RecognitionException {
		FieldTypeContext _localctx = new FieldTypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_fieldType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertiesContext extends ParserRuleContext {
		public List<KeyValueContext> keyValue() {
			return getRuleContexts(KeyValueContext.class);
		}
		public KeyValueContext keyValue(int i) {
			return getRuleContext(KeyValueContext.class,i);
		}
		public PropertiesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_properties; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitProperties(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertiesContext properties() throws RecognitionException {
		PropertiesContext _localctx = new PropertiesContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_properties);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			keyValue();
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(133);
				match(COMMA);
				setState(134);
				keyValue();
				}
				}
				setState(139);
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

	public static class KeyValueContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(HBaseSQLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(HBaseSQLParser.ID, i);
		}
		public KeyValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitKeyValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeyValueContext keyValue() throws RecognitionException {
		KeyValueContext _localctx = new KeyValueContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_keyValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(ID);
			setState(141);
			match(EQ);
			setState(142);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ColumnContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(HBaseSQLParser.ID, 0); }
		public ColumnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_column; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitColumn(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnContext column() throws RecognitionException {
		ColumnContext _localctx = new ColumnContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_column);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(ID);
			}
		}
		catch (RecognitionException re) {
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
		public TerminalNode ID() { return getToken(HBaseSQLParser.ID, 0); }
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
		enterRule(_localctx, 22, RULE_funcname);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			match(ID);
			}
		}
		catch (RecognitionException re) {
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
		public ColumnListContext columnList() {
			return getRuleContext(ColumnListContext.class,0);
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
		enterRule(_localctx, 24, RULE_selectColList);
		try {
			setState(150);
			switch (_input.LA(1)) {
			case ASTERISK:
				_localctx = new ColList_StarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(148);
				match(ASTERISK);
				}
				break;
			case ID:
				_localctx = new ColList_ColListContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(149);
				columnList();
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

	public static class ColumnListContext extends ParserRuleContext {
		public List<ColumnContext> column() {
			return getRuleContexts(ColumnContext.class);
		}
		public ColumnContext column(int i) {
			return getRuleContext(ColumnContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(HBaseSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(HBaseSQLParser.COMMA, i);
		}
		public ColumnListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitColumnList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnListContext columnList() throws RecognitionException {
		ColumnListContext _localctx = new ColumnListContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_columnList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			column();
			setState(157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(153);
				match(COMMA);
				setState(154);
				column();
				}
				}
				setState(159);
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

	public static class InsertStatementContext extends ParserRuleContext {
		public TerminalNode INSERT() { return getToken(HBaseSQLParser.INSERT, 0); }
		public TerminalNode INTO() { return getToken(HBaseSQLParser.INTO, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode LR_BRACKET() { return getToken(HBaseSQLParser.LR_BRACKET, 0); }
		public ColumnListContext columnList() {
			return getRuleContext(ColumnListContext.class,0);
		}
		public TerminalNode RR_BRACKET() { return getToken(HBaseSQLParser.RR_BRACKET, 0); }
		public TerminalNode VALUES() { return getToken(HBaseSQLParser.VALUES, 0); }
		public MultiValueListContext multiValueList() {
			return getRuleContext(MultiValueListContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(HBaseSQLParser.WHERE, 0); }
		public TerminalNode TS() { return getToken(HBaseSQLParser.TS, 0); }
		public TerminalNode EQ() { return getToken(HBaseSQLParser.EQ, 0); }
		public TsExpContext tsExp() {
			return getRuleContext(TsExpContext.class,0);
		}
		public InsertStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitInsertStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertStatementContext insertStatement() throws RecognitionException {
		InsertStatementContext _localctx = new InsertStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_insertStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			match(INSERT);
			setState(161);
			match(INTO);
			setState(162);
			tableName();
			setState(163);
			match(LR_BRACKET);
			setState(164);
			columnList();
			setState(165);
			match(RR_BRACKET);
			setState(166);
			match(VALUES);
			setState(167);
			multiValueList();
			setState(172);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(168);
				match(WHERE);
				setState(169);
				match(TS);
				setState(170);
				match(EQ);
				setState(171);
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

	public static class MultiValueListContext extends ParserRuleContext {
		public List<TerminalNode> LR_BRACKET() { return getTokens(HBaseSQLParser.LR_BRACKET); }
		public TerminalNode LR_BRACKET(int i) {
			return getToken(HBaseSQLParser.LR_BRACKET, i);
		}
		public List<ValueListContext> valueList() {
			return getRuleContexts(ValueListContext.class);
		}
		public ValueListContext valueList(int i) {
			return getRuleContext(ValueListContext.class,i);
		}
		public List<TerminalNode> RR_BRACKET() { return getTokens(HBaseSQLParser.RR_BRACKET); }
		public TerminalNode RR_BRACKET(int i) {
			return getToken(HBaseSQLParser.RR_BRACKET, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(HBaseSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(HBaseSQLParser.COMMA, i);
		}
		public MultiValueListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiValueList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitMultiValueList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiValueListContext multiValueList() throws RecognitionException {
		MultiValueListContext _localctx = new MultiValueListContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_multiValueList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			match(LR_BRACKET);
			setState(175);
			valueList();
			setState(176);
			match(RR_BRACKET);
			setState(184);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(177);
				match(COMMA);
				setState(178);
				match(LR_BRACKET);
				setState(179);
				valueList();
				setState(180);
				match(RR_BRACKET);
				}
				}
				setState(186);
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

	public static class ValueListContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(HBaseSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(HBaseSQLParser.COMMA, i);
		}
		public ValueListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitValueList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueListContext valueList() throws RecognitionException {
		ValueListContext _localctx = new ValueListContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_valueList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			value();
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(188);
				match(COMMA);
				setState(189);
				value();
				}
				}
				setState(194);
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

	public static class ValueContext extends ParserRuleContext {
		public List<TerminalNode> STRING() { return getTokens(HBaseSQLParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(HBaseSQLParser.STRING, i);
		}
		public List<TerminalNode> ID() { return getTokens(HBaseSQLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(HBaseSQLParser.ID, i);
		}
		public List<TerminalNode> NULL() { return getTokens(HBaseSQLParser.NULL); }
		public TerminalNode NULL(int i) {
			return getToken(HBaseSQLParser.NULL, i);
		}
		public List<TerminalNode> ROWKEY() { return getTokens(HBaseSQLParser.ROWKEY); }
		public TerminalNode ROWKEY(int i) {
			return getToken(HBaseSQLParser.ROWKEY, i);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_value);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(196); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(195);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NULL) | (1L << ROWKEY) | (1L << ID) | (1L << STRING))) != 0)) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(198); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectStatementContext extends ParserRuleContext {
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
		public MultiVersionExpContext multiVersionExp() {
			return getRuleContext(MultiVersionExpContext.class,0);
		}
		public LimitExpContext limitExp() {
			return getRuleContext(LimitExpContext.class,0);
		}
		public SelectStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitSelectStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectStatementContext selectStatement() throws RecognitionException {
		SelectStatementContext _localctx = new SelectStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_selectStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			match(SELECT);
			setState(201);
			selectColList();
			setState(202);
			match(FROM);
			setState(203);
			tableName();
			setState(204);
			match(WHERE);
			setState(205);
			rowKeyRangeExp();
			setState(208);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(206);
				match(AND);
				setState(207);
				wherec();
				}
				break;
			}
			setState(212);
			_la = _input.LA(1);
			if (_la==AND) {
				{
				setState(210);
				match(AND);
				setState(211);
				multiVersionExp();
				}
			}

			setState(215);
			_la = _input.LA(1);
			if (_la==LIMIT) {
				{
				setState(214);
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

	public static class DeleteStatementContext extends ParserRuleContext {
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
		public DeleteStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitDeleteStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeleteStatementContext deleteStatement() throws RecognitionException {
		DeleteStatementContext _localctx = new DeleteStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_deleteStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			match(DELETE);
			setState(218);
			selectColList();
			setState(219);
			match(FROM);
			setState(220);
			tableName();
			setState(221);
			match(WHERE);
			setState(222);
			rowKeyRangeExp();
			setState(225);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(223);
				match(AND);
				setState(224);
				wherec();
				}
				break;
			}
			setState(231);
			_la = _input.LA(1);
			if (_la==AND) {
				{
				setState(227);
				match(AND);
				setState(228);
				match(TS);
				setState(229);
				match(EQ);
				setState(230);
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
		public TerminalNode STARTKEY() { return getToken(HBaseSQLParser.STARTKEY, 0); }
		public GtOperContext gtOper() {
			return getRuleContext(GtOperContext.class,0);
		}
		public List<RowKeyExpContext> rowKeyExp() {
			return getRuleContexts(RowKeyExpContext.class);
		}
		public RowKeyExpContext rowKeyExp(int i) {
			return getRuleContext(RowKeyExpContext.class,i);
		}
		public TerminalNode AND() { return getToken(HBaseSQLParser.AND, 0); }
		public TerminalNode ENDKEY() { return getToken(HBaseSQLParser.ENDKEY, 0); }
		public LeOperContext leOper() {
			return getRuleContext(LeOperContext.class,0);
		}
		public Rowkeyrange_startAndEndContext(RowKeyRangeExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkeyrange_startAndEnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkeyrange_endContext extends RowKeyRangeExpContext {
		public TerminalNode ENDKEY() { return getToken(HBaseSQLParser.ENDKEY, 0); }
		public LeOperContext leOper() {
			return getRuleContext(LeOperContext.class,0);
		}
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
		public TerminalNode LR_BRACKET() { return getToken(HBaseSQLParser.LR_BRACKET, 0); }
		public List<RowKeyExpContext> rowKeyExp() {
			return getRuleContexts(RowKeyExpContext.class);
		}
		public RowKeyExpContext rowKeyExp(int i) {
			return getRuleContext(RowKeyExpContext.class,i);
		}
		public TerminalNode RR_BRACKET() { return getToken(HBaseSQLParser.RR_BRACKET, 0); }
		public List<TerminalNode> COMMA() { return getTokens(HBaseSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(HBaseSQLParser.COMMA, i);
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
		public GtOperContext gtOper() {
			return getRuleContext(GtOperContext.class,0);
		}
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
		enterRule(_localctx, 40, RULE_rowKeyRangeExp);
		int _la;
		try {
			setState(268);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				_localctx = new Rowkeyrange_startAndEndContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(233);
				match(STARTKEY);
				setState(234);
				gtOper();
				setState(235);
				rowKeyExp();
				setState(236);
				match(AND);
				setState(237);
				match(ENDKEY);
				setState(238);
				leOper();
				setState(239);
				rowKeyExp();
				}
				break;
			case 2:
				_localctx = new Rowkeyrange_startContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(241);
				match(STARTKEY);
				setState(242);
				gtOper();
				setState(243);
				rowKeyExp();
				}
				break;
			case 3:
				_localctx = new Rowkeyrange_endContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(245);
				match(ENDKEY);
				setState(246);
				leOper();
				setState(247);
				rowKeyExp();
				}
				break;
			case 4:
				_localctx = new Rowkeyrange_onerowkeyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(249);
				match(ROWKEY);
				setState(250);
				match(EQ);
				setState(251);
				rowKeyExp();
				}
				break;
			case 5:
				_localctx = new Rowkeyrange_insomekeysContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(252);
				match(ROWKEY);
				setState(253);
				match(IN);
				setState(254);
				match(LR_BRACKET);
				setState(255);
				rowKeyExp();
				setState(260);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(256);
					match(COMMA);
					setState(257);
					rowKeyExp();
					}
					}
					setState(262);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(263);
				match(RR_BRACKET);
				}
				break;
			case 6:
				_localctx = new Rowkeyrange_prefixContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(265);
				match(ROWKEY);
				setState(266);
				match(LIKE);
				setState(267);
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
		public FuncParamsListContext funcParamsList() {
			return getRuleContext(FuncParamsListContext.class,0);
		}
		public Rowkey_FuncConstantContext(RowKeyExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkey_FuncConstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkey_WrapperContext extends RowKeyExpContext {
		public TerminalNode LR_BRACKET() { return getToken(HBaseSQLParser.LR_BRACKET, 0); }
		public RowKeyExpContext rowKeyExp() {
			return getRuleContext(RowKeyExpContext.class,0);
		}
		public TerminalNode RR_BRACKET() { return getToken(HBaseSQLParser.RR_BRACKET, 0); }
		public Rowkey_WrapperContext(RowKeyExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitRowkey_Wrapper(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Rowkey_ConstantContext extends RowKeyExpContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
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
		enterRule(_localctx, 42, RULE_rowKeyExp);
		try {
			setState(278);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				_localctx = new Rowkey_WrapperContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(270);
				match(LR_BRACKET);
				setState(271);
				rowKeyExp();
				setState(272);
				match(RR_BRACKET);
				}
				break;
			case 2:
				_localctx = new Rowkey_ConstantContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(274);
				value();
				}
				break;
			case 3:
				_localctx = new Rowkey_FuncConstantContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(275);
				funcname();
				setState(276);
				funcParamsList();
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

	public static class FuncParamsListContext extends ParserRuleContext {
		public TerminalNode LR_BRACKET() { return getToken(HBaseSQLParser.LR_BRACKET, 0); }
		public List<FuncColContext> funcCol() {
			return getRuleContexts(FuncColContext.class);
		}
		public FuncColContext funcCol(int i) {
			return getRuleContext(FuncColContext.class,i);
		}
		public TerminalNode RR_BRACKET() { return getToken(HBaseSQLParser.RR_BRACKET, 0); }
		public List<TerminalNode> COMMA() { return getTokens(HBaseSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(HBaseSQLParser.COMMA, i);
		}
		public FuncParamsListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcParamsList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitFuncParamsList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncParamsListContext funcParamsList() throws RecognitionException {
		FuncParamsListContext _localctx = new FuncParamsListContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_funcParamsList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			match(LR_BRACKET);
			setState(281);
			funcCol();
			setState(286);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(282);
				match(COMMA);
				setState(283);
				funcCol();
				}
				}
				setState(288);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(289);
			match(RR_BRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncColContext extends ParserRuleContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public FuncColContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcCol; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitFuncCol(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncColContext funcCol() throws RecognitionException {
		FuncColContext _localctx = new FuncColContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_funcCol);
		try {
			setState(303);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(291);
				match(T__0);
				setState(292);
				value();
				setState(293);
				match(T__0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(295);
				match(T__1);
				setState(296);
				value();
				setState(297);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(299);
				value();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(300);
				match(T__1);
				setState(301);
				match(T__1);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
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

	public static class GtOperContext extends ParserRuleContext {
		public TerminalNode GREATER() { return getToken(HBaseSQLParser.GREATER, 0); }
		public TerminalNode GREATEREQUAL() { return getToken(HBaseSQLParser.GREATEREQUAL, 0); }
		public GtOperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gtOper; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitGtOper(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GtOperContext gtOper() throws RecognitionException {
		GtOperContext _localctx = new GtOperContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_gtOper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			_la = _input.LA(1);
			if ( !(_la==GREATER || _la==GREATEREQUAL) ) {
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

	public static class LeOperContext extends ParserRuleContext {
		public TerminalNode LESS() { return getToken(HBaseSQLParser.LESS, 0); }
		public TerminalNode LESSEQUAL() { return getToken(HBaseSQLParser.LESSEQUAL, 0); }
		public LeOperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_leOper; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitLeOper(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LeOperContext leOper() throws RecognitionException {
		LeOperContext _localctx = new LeOperContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_leOper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			_la = _input.LA(1);
			if ( !(_la==LESS || _la==LESSEQUAL) ) {
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
		public GtOperContext gtOper() {
			return getRuleContext(GtOperContext.class,0);
		}
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
		public LeOperContext leOper() {
			return getRuleContext(LeOperContext.class,0);
		}
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
		public TerminalNode LR_BRACKET() { return getToken(HBaseSQLParser.LR_BRACKET, 0); }
		public TerminalNode STARTTS() { return getToken(HBaseSQLParser.STARTTS, 0); }
		public GtOperContext gtOper() {
			return getRuleContext(GtOperContext.class,0);
		}
		public List<TsExpContext> tsExp() {
			return getRuleContexts(TsExpContext.class);
		}
		public TsExpContext tsExp(int i) {
			return getRuleContext(TsExpContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(HBaseSQLParser.COMMA, 0); }
		public TerminalNode ENDTS() { return getToken(HBaseSQLParser.ENDTS, 0); }
		public LeOperContext leOper() {
			return getRuleContext(LeOperContext.class,0);
		}
		public TerminalNode RR_BRACKET() { return getToken(HBaseSQLParser.RR_BRACKET, 0); }
		public Tsrange_startAndEndContext(TsRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitTsrange_startAndEnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TsRangeContext tsRange() throws RecognitionException {
		TsRangeContext _localctx = new TsRangeContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_tsRange);
		try {
			setState(330);
			switch (_input.LA(1)) {
			case LR_BRACKET:
				_localctx = new Tsrange_startAndEndContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(309);
				match(LR_BRACKET);
				setState(310);
				match(STARTTS);
				setState(311);
				gtOper();
				setState(312);
				tsExp();
				setState(313);
				match(COMMA);
				setState(314);
				match(ENDTS);
				setState(315);
				leOper();
				setState(316);
				tsExp();
				setState(317);
				match(RR_BRACKET);
				}
				break;
			case STARTTS:
				_localctx = new Tsrange_startContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(319);
				match(STARTTS);
				setState(320);
				gtOper();
				setState(321);
				tsExp();
				}
				break;
			case ENDTS:
				_localctx = new Tsrange_endContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(323);
				match(ENDTS);
				setState(324);
				leOper();
				setState(325);
				tsExp();
				}
				break;
			case TS:
				_localctx = new TsequalContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(327);
				match(TS);
				setState(328);
				match(EQ);
				setState(329);
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

	public static class ConstantContext extends ParserRuleContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode NULL() { return getToken(HBaseSQLParser.NULL, 0); }
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
		enterRule(_localctx, 54, RULE_constant);
		try {
			setState(342);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(332);
				match(T__1);
				setState(333);
				value();
				setState(334);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(336);
				value();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(337);
				match(T__1);
				setState(338);
				match(NULL);
				setState(339);
				match(T__1);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(340);
				match(T__1);
				setState(341);
				match(T__1);
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

	public static class ConstantListContext extends ParserRuleContext {
		public TerminalNode LR_BRACKET() { return getToken(HBaseSQLParser.LR_BRACKET, 0); }
		public List<ConstantContext> constant() {
			return getRuleContexts(ConstantContext.class);
		}
		public ConstantContext constant(int i) {
			return getRuleContext(ConstantContext.class,i);
		}
		public TerminalNode RR_BRACKET() { return getToken(HBaseSQLParser.RR_BRACKET, 0); }
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
		enterRule(_localctx, 56, RULE_constantList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(344);
			match(LR_BRACKET);
			setState(345);
			constant();
			setState(350);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(346);
				match(COMMA);
				setState(347);
				constant();
				}
				}
				setState(352);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(353);
			match(RR_BRACKET);
			}
		}
		catch (RecognitionException re) {
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
		public TerminalNode ID() { return getToken(HBaseSQLParser.ID, 0); }
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
		enterRule(_localctx, 58, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(355);
			match(T__2);
			setState(356);
			match(ID);
			setState(357);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarListContext extends ParserRuleContext {
		public TerminalNode LR_BRACKET() { return getToken(HBaseSQLParser.LR_BRACKET, 0); }
		public List<VarContext> var() {
			return getRuleContexts(VarContext.class);
		}
		public VarContext var(int i) {
			return getRuleContext(VarContext.class,i);
		}
		public TerminalNode RR_BRACKET() { return getToken(HBaseSQLParser.RR_BRACKET, 0); }
		public VarListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitVarList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarListContext varList() throws RecognitionException {
		VarListContext _localctx = new VarListContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_varList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(359);
			match(LR_BRACKET);
			setState(360);
			var();
			setState(365);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(361);
				match(COMMA);
				setState(362);
				var();
				}
				}
				setState(367);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(368);
			match(RR_BRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiVersionExpContext extends ParserRuleContext {
		public MaxVersionExpContext maxVersionExp() {
			return getRuleContext(MaxVersionExpContext.class,0);
		}
		public TsRangeContext tsRange() {
			return getRuleContext(TsRangeContext.class,0);
		}
		public MultiVersionExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiVersionExp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitMultiVersionExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiVersionExpContext multiVersionExp() throws RecognitionException {
		MultiVersionExpContext _localctx = new MultiVersionExpContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_multiVersionExp);
		try {
			setState(372);
			switch (_input.LA(1)) {
			case MAXVERSION:
				enterOuterAlt(_localctx, 1);
				{
				setState(370);
				maxVersionExp();
				}
				break;
			case LR_BRACKET:
			case TS:
			case STARTTS:
			case ENDTS:
				enterOuterAlt(_localctx, 2);
				{
				setState(371);
				tsRange();
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
		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class,0);
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
		enterRule(_localctx, 64, RULE_maxVersionExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(374);
			match(MAXVERSION);
			setState(375);
			match(EQ);
			setState(376);
			integer();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntegerContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(HBaseSQLParser.ID, 0); }
		public IntegerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitInteger(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntegerContext integer() throws RecognitionException {
		IntegerContext _localctx = new IntegerContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_integer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(378);
			match(ID);
			}
		}
		catch (RecognitionException re) {
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
		enterRule(_localctx, 68, RULE_tsExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(380);
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

	public static class TimestampContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(HBaseSQLParser.ID, 0); }
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
		enterRule(_localctx, 70, RULE_timestamp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
			match(ID);
			}
		}
		catch (RecognitionException re) {
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
		enterRule(_localctx, 72, RULE_wherec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
	public static class NotbetweenvarContext extends ConditioncContext {
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
	public static class EqualvarContext extends ConditioncContext {
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
	public static class NotinconstantlistContext extends ConditioncContext {
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
	public static class InvarlistContext extends ConditioncContext {
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
		}
		public TerminalNode IN() { return getToken(HBaseSQLParser.IN, 0); }
		public VarListContext varList() {
			return getRuleContext(VarListContext.class,0);
		}
		public InvarlistContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitInvarlist(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessconstantContext extends ConditioncContext {
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public TerminalNode LR_BRACKET() { return getToken(HBaseSQLParser.LR_BRACKET, 0); }
		public ConditioncContext conditionc() {
			return getRuleContext(ConditioncContext.class,0);
		}
		public TerminalNode RR_BRACKET() { return getToken(HBaseSQLParser.RR_BRACKET, 0); }
		public ConditionwrapperContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitConditionwrapper(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotinvarlistContext extends ConditioncContext {
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
		}
		public TerminalNode NOT() { return getToken(HBaseSQLParser.NOT, 0); }
		public TerminalNode IN() { return getToken(HBaseSQLParser.IN, 0); }
		public VarListContext varList() {
			return getRuleContext(VarListContext.class,0);
		}
		public NotinvarlistContext(ConditioncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HBaseSQLVisitor ) return ((HBaseSQLVisitor<? extends T>)visitor).visitNotinvarlist(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessequalconstantContext extends ConditioncContext {
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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
		public ColumnContext column() {
			return getRuleContext(ColumnContext.class,0);
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

	public final ConditioncContext conditionc() throws RecognitionException {
		return conditionc(0);
	}

	private ConditioncContext conditionc(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConditioncContext _localctx = new ConditioncContext(_ctx, _parentState);
		ConditioncContext _prevctx = _localctx;
		int _startState = 74;
		enterRecursionRule(_localctx, 74, RULE_conditionc, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(492);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				_localctx = new ConditionwrapperContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(387);
				match(LR_BRACKET);
				setState(388);
				conditionc(0);
				setState(389);
				match(RR_BRACKET);
				}
				break;
			case 2:
				{
				_localctx = new EqualconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(391);
				column();
				setState(392);
				match(EQ);
				setState(393);
				constant();
				}
				break;
			case 3:
				{
				_localctx = new EqualvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(395);
				column();
				setState(396);
				match(EQ);
				setState(397);
				var();
				}
				break;
			case 4:
				{
				_localctx = new NotequalconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(399);
				column();
				setState(400);
				match(NOTEQ);
				setState(401);
				constant();
				}
				break;
			case 5:
				{
				_localctx = new NotequalvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(403);
				column();
				setState(404);
				match(NOTEQ);
				setState(405);
				var();
				}
				break;
			case 6:
				{
				_localctx = new LessconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(407);
				column();
				setState(408);
				match(LESS);
				setState(409);
				constant();
				}
				break;
			case 7:
				{
				_localctx = new LessvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(411);
				column();
				setState(412);
				match(LESS);
				setState(413);
				var();
				}
				break;
			case 8:
				{
				_localctx = new GreaterconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(415);
				column();
				setState(416);
				match(GREATER);
				setState(417);
				constant();
				}
				break;
			case 9:
				{
				_localctx = new GreatervarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(419);
				column();
				setState(420);
				match(GREATER);
				setState(421);
				var();
				}
				break;
			case 10:
				{
				_localctx = new LessequalconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(423);
				column();
				setState(424);
				match(LESSEQUAL);
				setState(425);
				constant();
				}
				break;
			case 11:
				{
				_localctx = new LessequalvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(427);
				column();
				setState(428);
				match(LESSEQUAL);
				setState(429);
				var();
				}
				break;
			case 12:
				{
				_localctx = new GreaterequalconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(431);
				column();
				setState(432);
				match(GREATEREQUAL);
				setState(433);
				constant();
				}
				break;
			case 13:
				{
				_localctx = new GreaterequalvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(435);
				column();
				setState(436);
				match(GREATEREQUAL);
				setState(437);
				var();
				}
				break;
			case 14:
				{
				_localctx = new InconstantlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(439);
				column();
				setState(440);
				match(IN);
				setState(441);
				constantList();
				}
				break;
			case 15:
				{
				_localctx = new InvarlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(443);
				column();
				setState(444);
				match(IN);
				setState(445);
				varList();
				}
				break;
			case 16:
				{
				_localctx = new NotinconstantlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(447);
				column();
				setState(448);
				match(NOT);
				setState(449);
				match(IN);
				setState(450);
				constantList();
				}
				break;
			case 17:
				{
				_localctx = new NotinvarlistContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(452);
				column();
				setState(453);
				match(NOT);
				setState(454);
				match(IN);
				setState(455);
				varList();
				}
				break;
			case 18:
				{
				_localctx = new BetweenconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(457);
				column();
				setState(458);
				match(BETWEEN);
				setState(459);
				constant();
				setState(460);
				match(AND);
				setState(461);
				constant();
				}
				break;
			case 19:
				{
				_localctx = new BetweenvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(463);
				column();
				setState(464);
				match(BETWEEN);
				setState(465);
				var();
				setState(466);
				match(AND);
				setState(467);
				var();
				}
				break;
			case 20:
				{
				_localctx = new NotbetweenconstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(469);
				column();
				setState(470);
				match(NOT);
				setState(471);
				match(BETWEEN);
				setState(472);
				constant();
				setState(473);
				match(AND);
				setState(474);
				constant();
				}
				break;
			case 21:
				{
				_localctx = new NotbetweenvarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(476);
				column();
				setState(477);
				match(NOT);
				setState(478);
				match(BETWEEN);
				setState(479);
				var();
				setState(480);
				match(AND);
				setState(481);
				var();
				}
				break;
			case 22:
				{
				_localctx = new IsnullcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(483);
				column();
				setState(484);
				match(IS);
				setState(485);
				match(NULL);
				}
				break;
			case 23:
				{
				_localctx = new IsnotnullcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(487);
				column();
				setState(488);
				match(IS);
				setState(489);
				match(NOT);
				setState(490);
				match(NULL);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(502);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(500);
					switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
					case 1:
						{
						_localctx = new AndconditionContext(new ConditioncContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_conditionc);
						setState(494);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(495);
						match(AND);
						setState(496);
						conditionc(25);
						}
						break;
					case 2:
						{
						_localctx = new OrconditionContext(new ConditioncContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_conditionc);
						setState(497);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(498);
						match(OR);
						setState(499);
						conditionc(24);
						}
						break;
					}
					} 
				}
				setState(504);
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

	public static class LimitExpContext extends ParserRuleContext {
		public TerminalNode LIMIT() { return getToken(HBaseSQLParser.LIMIT, 0); }
		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class,0);
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
		enterRule(_localctx, 76, RULE_limitExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(505);
			match(LIMIT);
			setState(506);
			integer();
			}
		}
		catch (RecognitionException re) {
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
		case 37:
			return conditionc_sempred((ConditioncContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean conditionc_sempred(ConditioncContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 24);
		case 1:
			return precpred(_ctx, 23);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3@\u01ff\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\3\2\3\2\3\2\3\2\3\2"+
		"\5\2V\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3e\n\3"+
		"\3\3\5\3h\n\3\3\4\3\4\3\4\3\4\3\4\5\4o\n\4\3\5\3\5\3\6\3\6\3\6\7\6v\n"+
		"\6\f\6\16\6y\13\6\3\7\3\7\3\7\5\7~\n\7\3\7\5\7\u0081\n\7\3\b\3\b\3\t\3"+
		"\t\3\n\3\n\3\n\7\n\u008a\n\n\f\n\16\n\u008d\13\n\3\13\3\13\3\13\3\13\3"+
		"\f\3\f\3\r\3\r\3\16\3\16\5\16\u0099\n\16\3\17\3\17\3\17\7\17\u009e\n\17"+
		"\f\17\16\17\u00a1\13\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\5\20\u00af\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\7\21\u00b9\n\21\f\21\16\21\u00bc\13\21\3\22\3\22\3\22\7\22\u00c1\n\22"+
		"\f\22\16\22\u00c4\13\22\3\23\6\23\u00c7\n\23\r\23\16\23\u00c8\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u00d3\n\24\3\24\3\24\5\24\u00d7\n"+
		"\24\3\24\5\24\u00da\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25"+
		"\u00e4\n\25\3\25\3\25\3\25\3\25\5\25\u00ea\n\25\3\26\3\26\3\26\3\26\3"+
		"\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3"+
		"\26\3\26\3\26\3\26\3\26\3\26\3\26\7\26\u0105\n\26\f\26\16\26\u0108\13"+
		"\26\3\26\3\26\3\26\3\26\3\26\5\26\u010f\n\26\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\5\27\u0119\n\27\3\30\3\30\3\30\3\30\7\30\u011f\n\30\f"+
		"\30\16\30\u0122\13\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\5\31\u0132\n\31\3\32\3\32\3\33\3\33\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\5\34\u014d\n\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\5\35\u0159\n\35\3\36\3\36\3\36\3\36\7\36\u015f\n\36\f"+
		"\36\16\36\u0162\13\36\3\36\3\36\3\37\3\37\3\37\3\37\3 \3 \3 \3 \7 \u016e"+
		"\n \f \16 \u0171\13 \3 \3 \3!\3!\5!\u0177\n!\3\"\3\"\3\"\3\"\3#\3#\3$"+
		"\3$\3%\3%\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'"+
		"\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'"+
		"\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'"+
		"\3\'\3\'\3\'\3\'\3\'\5\'\u01ef\n\'\3\'\3\'\3\'\3\'\3\'\3\'\7\'\u01f7\n"+
		"\'\f\'\16\'\u01fa\13\'\3(\3(\3(\3(\2\3L)\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLN\2\5\5\2\64\64\67\67<=\3\2\r"+
		"\16\3\2\17\20\u021b\2U\3\2\2\2\4W\3\2\2\2\6i\3\2\2\2\bp\3\2\2\2\nr\3\2"+
		"\2\2\fz\3\2\2\2\16\u0082\3\2\2\2\20\u0084\3\2\2\2\22\u0086\3\2\2\2\24"+
		"\u008e\3\2\2\2\26\u0092\3\2\2\2\30\u0094\3\2\2\2\32\u0098\3\2\2\2\34\u009a"+
		"\3\2\2\2\36\u00a2\3\2\2\2 \u00b0\3\2\2\2\"\u00bd\3\2\2\2$\u00c6\3\2\2"+
		"\2&\u00ca\3\2\2\2(\u00db\3\2\2\2*\u010e\3\2\2\2,\u0118\3\2\2\2.\u011a"+
		"\3\2\2\2\60\u0131\3\2\2\2\62\u0133\3\2\2\2\64\u0135\3\2\2\2\66\u014c\3"+
		"\2\2\28\u0158\3\2\2\2:\u015a\3\2\2\2<\u0165\3\2\2\2>\u0169\3\2\2\2@\u0176"+
		"\3\2\2\2B\u0178\3\2\2\2D\u017c\3\2\2\2F\u017e\3\2\2\2H\u0180\3\2\2\2J"+
		"\u0182\3\2\2\2L\u01ee\3\2\2\2N\u01fb\3\2\2\2PV\5\4\3\2QV\5\6\4\2RV\5\36"+
		"\20\2SV\5&\24\2TV\5(\25\2UP\3\2\2\2UQ\3\2\2\2UR\3\2\2\2US\3\2\2\2UT\3"+
		"\2\2\2V\3\3\2\2\2WX\7\37\2\2XY\7!\2\2YZ\7\"\2\2Z[\5\b\5\2[\\\7\7\2\2\\"+
		"]\5\n\6\2]d\7\b\2\2^_\7#\2\2_`\7$\2\2`a\7\7\2\2ab\5\22\n\2bc\7\b\2\2c"+
		"e\3\2\2\2d^\3\2\2\2de\3\2\2\2eg\3\2\2\2fh\7\n\2\2gf\3\2\2\2gh\3\2\2\2"+
		"h\5\3\2\2\2ij\7 \2\2jk\7!\2\2kl\7\"\2\2ln\5\b\5\2mo\7\n\2\2nm\3\2\2\2"+
		"no\3\2\2\2o\7\3\2\2\2pq\7<\2\2q\t\3\2\2\2rw\5\f\7\2st\7\t\2\2tv\5\f\7"+
		"\2us\3\2\2\2vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2x\13\3\2\2\2yw\3\2\2\2z{\5\16"+
		"\b\2{}\5\20\t\2|~\78\2\2}|\3\2\2\2}~\3\2\2\2~\u0080\3\2\2\2\177\u0081"+
		"\7%\2\2\u0080\177\3\2\2\2\u0080\u0081\3\2\2\2\u0081\r\3\2\2\2\u0082\u0083"+
		"\7<\2\2\u0083\17\3\2\2\2\u0084\u0085\7<\2\2\u0085\21\3\2\2\2\u0086\u008b"+
		"\5\24\13\2\u0087\u0088\7\t\2\2\u0088\u008a\5\24\13\2\u0089\u0087\3\2\2"+
		"\2\u008a\u008d\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\23"+
		"\3\2\2\2\u008d\u008b\3\2\2\2\u008e\u008f\7<\2\2\u008f\u0090\7\13\2\2\u0090"+
		"\u0091\7<\2\2\u0091\25\3\2\2\2\u0092\u0093\7<\2\2\u0093\27\3\2\2\2\u0094"+
		"\u0095\7<\2\2\u0095\31\3\2\2\2\u0096\u0099\7\23\2\2\u0097\u0099\5\34\17"+
		"\2\u0098\u0096\3\2\2\2\u0098\u0097\3\2\2\2\u0099\33\3\2\2\2\u009a\u009f"+
		"\5\26\f\2\u009b\u009c\7\t\2\2\u009c\u009e\5\26\f\2\u009d\u009b\3\2\2\2"+
		"\u009e\u00a1\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\35"+
		"\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2\u00a3\7&\2\2\u00a3\u00a4\7\'\2\2\u00a4"+
		"\u00a5\5\b\5\2\u00a5\u00a6\7\7\2\2\u00a6\u00a7\5\34\17\2\u00a7\u00a8\7"+
		"\b\2\2\u00a8\u00a9\7(\2\2\u00a9\u00ae\5 \21\2\u00aa\u00ab\7/\2\2\u00ab"+
		"\u00ac\7\34\2\2\u00ac\u00ad\7\13\2\2\u00ad\u00af\5F$\2\u00ae\u00aa\3\2"+
		"\2\2\u00ae\u00af\3\2\2\2\u00af\37\3\2\2\2\u00b0\u00b1\7\7\2\2\u00b1\u00b2"+
		"\5\"\22\2\u00b2\u00ba\7\b\2\2\u00b3\u00b4\7\t\2\2\u00b4\u00b5\7\7\2\2"+
		"\u00b5\u00b6\5\"\22\2\u00b6\u00b7\7\b\2\2\u00b7\u00b9\3\2\2\2\u00b8\u00b3"+
		"\3\2\2\2\u00b9\u00bc\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb"+
		"!\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bd\u00c2\5$\23\2\u00be\u00bf\7\t\2\2"+
		"\u00bf\u00c1\5$\23\2\u00c0\u00be\3\2\2\2\u00c1\u00c4\3\2\2\2\u00c2\u00c0"+
		"\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3#\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c5"+
		"\u00c7\t\2\2\2\u00c6\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00c6\3\2"+
		"\2\2\u00c8\u00c9\3\2\2\2\u00c9%\3\2\2\2\u00ca\u00cb\7)\2\2\u00cb\u00cc"+
		"\5\32\16\2\u00cc\u00cd\7-\2\2\u00cd\u00ce\5\b\5\2\u00ce\u00cf\7/\2\2\u00cf"+
		"\u00d2\5*\26\2\u00d0\u00d1\7\60\2\2\u00d1\u00d3\5J&\2\u00d2\u00d0\3\2"+
		"\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d6\3\2\2\2\u00d4\u00d5\7\60\2\2\u00d5"+
		"\u00d7\5@!\2\u00d6\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d9\3\2\2"+
		"\2\u00d8\u00da\5N(\2\u00d9\u00d8\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\'\3"+
		"\2\2\2\u00db\u00dc\7,\2\2\u00dc\u00dd\5\32\16\2\u00dd\u00de\7-\2\2\u00de"+
		"\u00df\5\b\5\2\u00df\u00e0\7/\2\2\u00e0\u00e3\5*\26\2\u00e1\u00e2\7\60"+
		"\2\2\u00e2\u00e4\5J&\2\u00e3\u00e1\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e9"+
		"\3\2\2\2\u00e5\u00e6\7\60\2\2\u00e6\u00e7\7\34\2\2\u00e7\u00e8\7\13\2"+
		"\2\u00e8\u00ea\5F$\2\u00e9\u00e5\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea)\3"+
		"\2\2\2\u00eb\u00ec\79\2\2\u00ec\u00ed\5\62\32\2\u00ed\u00ee\5,\27\2\u00ee"+
		"\u00ef\7\60\2\2\u00ef\u00f0\7:\2\2\u00f0\u00f1\5\64\33\2\u00f1\u00f2\5"+
		",\27\2\u00f2\u010f\3\2\2\2\u00f3\u00f4\79\2\2\u00f4\u00f5\5\62\32\2\u00f5"+
		"\u00f6\5,\27\2\u00f6\u010f\3\2\2\2\u00f7\u00f8\7:\2\2\u00f8\u00f9\5\64"+
		"\33\2\u00f9\u00fa\5,\27\2\u00fa\u010f\3\2\2\2\u00fb\u00fc\7\67\2\2\u00fc"+
		"\u00fd\7\13\2\2\u00fd\u010f\5,\27\2\u00fe\u00ff\7\67\2\2\u00ff\u0100\7"+
		"\62\2\2\u0100\u0101\7\7\2\2\u0101\u0106\5,\27\2\u0102\u0103\7\t\2\2\u0103"+
		"\u0105\5,\27\2\u0104\u0102\3\2\2\2\u0105\u0108\3\2\2\2\u0106\u0104\3\2"+
		"\2\2\u0106\u0107\3\2\2\2\u0107\u0109\3\2\2\2\u0108\u0106\3\2\2\2\u0109"+
		"\u010a\7\b\2\2\u010a\u010f\3\2\2\2\u010b\u010c\7\67\2\2\u010c\u010d\7"+
		"\63\2\2\u010d\u010f\5,\27\2\u010e\u00eb\3\2\2\2\u010e\u00f3\3\2\2\2\u010e"+
		"\u00f7\3\2\2\2\u010e\u00fb\3\2\2\2\u010e\u00fe\3\2\2\2\u010e\u010b\3\2"+
		"\2\2\u010f+\3\2\2\2\u0110\u0111\7\7\2\2\u0111\u0112\5,\27\2\u0112\u0113"+
		"\7\b\2\2\u0113\u0119\3\2\2\2\u0114\u0119\5$\23\2\u0115\u0116\5\30\r\2"+
		"\u0116\u0117\5.\30\2\u0117\u0119\3\2\2\2\u0118\u0110\3\2\2\2\u0118\u0114"+
		"\3\2\2\2\u0118\u0115\3\2\2\2\u0119-\3\2\2\2\u011a\u011b\7\7\2\2\u011b"+
		"\u0120\5\60\31\2\u011c\u011d\7\t\2\2\u011d\u011f\5\60\31\2\u011e\u011c"+
		"\3\2\2\2\u011f\u0122\3\2\2\2\u0120\u011e\3\2\2\2\u0120\u0121\3\2\2\2\u0121"+
		"\u0123\3\2\2\2\u0122\u0120\3\2\2\2\u0123\u0124\7\b\2\2\u0124/\3\2\2\2"+
		"\u0125\u0126\7\3\2\2\u0126\u0127\5$\23\2\u0127\u0128\7\3\2\2\u0128\u0132"+
		"\3\2\2\2\u0129\u012a\7\4\2\2\u012a\u012b\5$\23\2\u012b\u012c\7\4\2\2\u012c"+
		"\u0132\3\2\2\2\u012d\u0132\5$\23\2\u012e\u012f\7\4\2\2\u012f\u0132\7\4"+
		"\2\2\u0130\u0132\3\2\2\2\u0131\u0125\3\2\2\2\u0131\u0129\3\2\2\2\u0131"+
		"\u012d\3\2\2\2\u0131\u012e\3\2\2\2\u0131\u0130\3\2\2\2\u0132\61\3\2\2"+
		"\2\u0133\u0134\t\3\2\2\u0134\63\3\2\2\2\u0135\u0136\t\4\2\2\u0136\65\3"+
		"\2\2\2\u0137\u0138\7\7\2\2\u0138\u0139\7\35\2\2\u0139\u013a\5\62\32\2"+
		"\u013a\u013b\5F$\2\u013b\u013c\7\t\2\2\u013c\u013d\7\36\2\2\u013d\u013e"+
		"\5\64\33\2\u013e\u013f\5F$\2\u013f\u0140\7\b\2\2\u0140\u014d\3\2\2\2\u0141"+
		"\u0142\7\35\2\2\u0142\u0143\5\62\32\2\u0143\u0144\5F$\2\u0144\u014d\3"+
		"\2\2\2\u0145\u0146\7\36\2\2\u0146\u0147\5\64\33\2\u0147\u0148\5F$\2\u0148"+
		"\u014d\3\2\2\2\u0149\u014a\7\34\2\2\u014a\u014b\7\13\2\2\u014b\u014d\5"+
		"F$\2\u014c\u0137\3\2\2\2\u014c\u0141\3\2\2\2\u014c\u0145\3\2\2\2\u014c"+
		"\u0149\3\2\2\2\u014d\67\3\2\2\2\u014e\u014f\7\4\2\2\u014f\u0150\5$\23"+
		"\2\u0150\u0151\7\4\2\2\u0151\u0159\3\2\2\2\u0152\u0159\5$\23\2\u0153\u0154"+
		"\7\4\2\2\u0154\u0155\7\64\2\2\u0155\u0159\7\4\2\2\u0156\u0157\7\4\2\2"+
		"\u0157\u0159\7\4\2\2\u0158\u014e\3\2\2\2\u0158\u0152\3\2\2\2\u0158\u0153"+
		"\3\2\2\2\u0158\u0156\3\2\2\2\u01599\3\2\2\2\u015a\u015b\7\7\2\2\u015b"+
		"\u0160\58\35\2\u015c\u015d\7\t\2\2\u015d\u015f\58\35\2\u015e\u015c\3\2"+
		"\2\2\u015f\u0162\3\2\2\2\u0160\u015e\3\2\2\2\u0160\u0161\3\2\2\2\u0161"+
		"\u0163\3\2\2\2\u0162\u0160\3\2\2\2\u0163\u0164\7\b\2\2\u0164;\3\2\2\2"+
		"\u0165\u0166\7\5\2\2\u0166\u0167\7<\2\2\u0167\u0168\7\6\2\2\u0168=\3\2"+
		"\2\2\u0169\u016a\7\7\2\2\u016a\u016f\5<\37\2\u016b\u016c\7\t\2\2\u016c"+
		"\u016e\5<\37\2\u016d\u016b\3\2\2\2\u016e\u0171\3\2\2\2\u016f\u016d\3\2"+
		"\2\2\u016f\u0170\3\2\2\2\u0170\u0172\3\2\2\2\u0171\u016f\3\2\2\2\u0172"+
		"\u0173\7\b\2\2\u0173?\3\2\2\2\u0174\u0177\5B\"\2\u0175\u0177\5\66\34\2"+
		"\u0176\u0174\3\2\2\2\u0176\u0175\3\2\2\2\u0177A\3\2\2\2\u0178\u0179\7"+
		";\2\2\u0179\u017a\7\13\2\2\u017a\u017b\5D#\2\u017bC\3\2\2\2\u017c\u017d"+
		"\7<\2\2\u017dE\3\2\2\2\u017e\u017f\5H%\2\u017fG\3\2\2\2\u0180\u0181\7"+
		"<\2\2\u0181I\3\2\2\2\u0182\u0183\5L\'\2\u0183K\3\2\2\2\u0184\u0185\b\'"+
		"\1\2\u0185\u0186\7\7\2\2\u0186\u0187\5L\'\2\u0187\u0188\7\b\2\2\u0188"+
		"\u01ef\3\2\2\2\u0189\u018a\5\26\f\2\u018a\u018b\7\13\2\2\u018b\u018c\5"+
		"8\35\2\u018c\u01ef\3\2\2\2\u018d\u018e\5\26\f\2\u018e\u018f\7\13\2\2\u018f"+
		"\u0190\5<\37\2\u0190\u01ef\3\2\2\2\u0191\u0192\5\26\f\2\u0192\u0193\7"+
		"\f\2\2\u0193\u0194\58\35\2\u0194\u01ef\3\2\2\2\u0195\u0196\5\26\f\2\u0196"+
		"\u0197\7\f\2\2\u0197\u0198\5<\37\2\u0198\u01ef\3\2\2\2\u0199\u019a\5\26"+
		"\f\2\u019a\u019b\7\17\2\2\u019b\u019c\58\35\2\u019c\u01ef\3\2\2\2\u019d"+
		"\u019e\5\26\f\2\u019e\u019f\7\17\2\2\u019f\u01a0\5<\37\2\u01a0\u01ef\3"+
		"\2\2\2\u01a1\u01a2\5\26\f\2\u01a2\u01a3\7\r\2\2\u01a3\u01a4\58\35\2\u01a4"+
		"\u01ef\3\2\2\2\u01a5\u01a6\5\26\f\2\u01a6\u01a7\7\r\2\2\u01a7\u01a8\5"+
		"<\37\2\u01a8\u01ef\3\2\2\2\u01a9\u01aa\5\26\f\2\u01aa\u01ab\7\20\2\2\u01ab"+
		"\u01ac\58\35\2\u01ac\u01ef\3\2\2\2\u01ad\u01ae\5\26\f\2\u01ae\u01af\7"+
		"\20\2\2\u01af\u01b0\5<\37\2\u01b0\u01ef\3\2\2\2\u01b1\u01b2\5\26\f\2\u01b2"+
		"\u01b3\7\16\2\2\u01b3\u01b4\58\35\2\u01b4\u01ef\3\2\2\2\u01b5\u01b6\5"+
		"\26\f\2\u01b6\u01b7\7\16\2\2\u01b7\u01b8\5<\37\2\u01b8\u01ef\3\2\2\2\u01b9"+
		"\u01ba\5\26\f\2\u01ba\u01bb\7\62\2\2\u01bb\u01bc\5:\36\2\u01bc\u01ef\3"+
		"\2\2\2\u01bd\u01be\5\26\f\2\u01be\u01bf\7\62\2\2\u01bf\u01c0\5> \2\u01c0"+
		"\u01ef\3\2\2\2\u01c1\u01c2\5\26\f\2\u01c2\u01c3\7\65\2\2\u01c3\u01c4\7"+
		"\62\2\2\u01c4\u01c5\5:\36\2\u01c5\u01ef\3\2\2\2\u01c6\u01c7\5\26\f\2\u01c7"+
		"\u01c8\7\65\2\2\u01c8\u01c9\7\62\2\2\u01c9\u01ca\5> \2\u01ca\u01ef\3\2"+
		"\2\2\u01cb\u01cc\5\26\f\2\u01cc\u01cd\7\31\2\2\u01cd\u01ce\58\35\2\u01ce"+
		"\u01cf\7\60\2\2\u01cf\u01d0\58\35\2\u01d0\u01ef\3\2\2\2\u01d1\u01d2\5"+
		"\26\f\2\u01d2\u01d3\7\31\2\2\u01d3\u01d4\5<\37\2\u01d4\u01d5\7\60\2\2"+
		"\u01d5\u01d6\5<\37\2\u01d6\u01ef\3\2\2\2\u01d7\u01d8\5\26\f\2\u01d8\u01d9"+
		"\7\65\2\2\u01d9\u01da\7\31\2\2\u01da\u01db\58\35\2\u01db\u01dc\7\60\2"+
		"\2\u01dc\u01dd\58\35\2\u01dd\u01ef\3\2\2\2\u01de\u01df\5\26\f\2\u01df"+
		"\u01e0\7\65\2\2\u01e0\u01e1\7\31\2\2\u01e1\u01e2\5<\37\2\u01e2\u01e3\7"+
		"\60\2\2\u01e3\u01e4\5<\37\2\u01e4\u01ef\3\2\2\2\u01e5\u01e6\5\26\f\2\u01e6"+
		"\u01e7\7\26\2\2\u01e7\u01e8\7\64\2\2\u01e8\u01ef\3\2\2\2\u01e9\u01ea\5"+
		"\26\f\2\u01ea\u01eb\7\26\2\2\u01eb\u01ec\7\65\2\2\u01ec\u01ed\7\64\2\2"+
		"\u01ed\u01ef\3\2\2\2\u01ee\u0184\3\2\2\2\u01ee\u0189\3\2\2\2\u01ee\u018d"+
		"\3\2\2\2\u01ee\u0191\3\2\2\2\u01ee\u0195\3\2\2\2\u01ee\u0199\3\2\2\2\u01ee"+
		"\u019d\3\2\2\2\u01ee\u01a1\3\2\2\2\u01ee\u01a5\3\2\2\2\u01ee\u01a9\3\2"+
		"\2\2\u01ee\u01ad\3\2\2\2\u01ee\u01b1\3\2\2\2\u01ee\u01b5\3\2\2\2\u01ee"+
		"\u01b9\3\2\2\2\u01ee\u01bd\3\2\2\2\u01ee\u01c1\3\2\2\2\u01ee\u01c6\3\2"+
		"\2\2\u01ee\u01cb\3\2\2\2\u01ee\u01d1\3\2\2\2\u01ee\u01d7\3\2\2\2\u01ee"+
		"\u01de\3\2\2\2\u01ee\u01e5\3\2\2\2\u01ee\u01e9\3\2\2\2\u01ef\u01f8\3\2"+
		"\2\2\u01f0\u01f1\f\32\2\2\u01f1\u01f2\7\60\2\2\u01f2\u01f7\5L\'\33\u01f3"+
		"\u01f4\f\31\2\2\u01f4\u01f5\7\61\2\2\u01f5\u01f7\5L\'\32\u01f6\u01f0\3"+
		"\2\2\2\u01f6\u01f3\3\2\2\2\u01f7\u01fa\3\2\2\2\u01f8\u01f6\3\2\2\2\u01f8"+
		"\u01f9\3\2\2\2\u01f9M\3\2\2\2\u01fa\u01f8\3\2\2\2\u01fb\u01fc\7\33\2\2"+
		"\u01fc\u01fd\5D#\2\u01fdO\3\2\2\2\"Udgnw}\u0080\u008b\u0098\u009f\u00ae"+
		"\u00ba\u00c2\u00c8\u00d2\u00d6\u00d9\u00e3\u00e9\u0106\u010e\u0118\u0120"+
		"\u0131\u014c\u0158\u0160\u016f\u0176\u01ee\u01f6\u01f8";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}