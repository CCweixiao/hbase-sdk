// Generated from HBaseSQL.g4 by ANTLR 4.5.1

package com.github.CCwexiao.hbase.sdk.dsl.antlr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HBaseSQLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, LR_BRACKET=5, RR_BRACKET=6, COMMA=7, SEMICOLON=8, 
		EQ=9, NOTEQ=10, GREATER=11, GREATEREQUAL=12, LESS=13, LESSEQUAL=14, PLUS=15, 
		MINUS=16, ASTERISK=17, SLASH=18, MOD=19, IS=20, NOTMATCH=21, MATCH=22, 
		BETWEEN=23, MISSING=24, LIMIT=25, TS=26, STARTTS=27, ENDTS=28, CREATE=29, 
		INSERT=30, INTO=31, VALUES=32, SELECT=33, UPDATE=34, SET=35, DELETE=36, 
		FROM=37, TABLE=38, COLUMNFAMILY=39, WHERE=40, AND=41, OR=42, IN=43, LIKE=44, 
		NULL=45, NOT=46, INTEGER=47, ROWKEY=48, STARTKEY=49, ENDKEY=50, MAXVERSION=51, 
		ID=52, STRING=53, SPACE=54, COMMENT_INPUT=55, LINE_COMMENT=56;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "LR_BRACKET", "RR_BRACKET", "COMMA", "SEMICOLON", 
		"EQ", "NOTEQ", "GREATER", "GREATEREQUAL", "LESS", "LESSEQUAL", "PLUS", 
		"MINUS", "ASTERISK", "SLASH", "MOD", "IS", "NOTMATCH", "MATCH", "BETWEEN", 
		"MISSING", "LIMIT", "TS", "STARTTS", "ENDTS", "CREATE", "INSERT", "INTO", 
		"VALUES", "SELECT", "UPDATE", "SET", "DELETE", "FROM", "TABLE", "COLUMNFAMILY", 
		"WHERE", "AND", "OR", "IN", "LIKE", "NULL", "NOT", "INTEGER", "ROWKEY", 
		"STARTKEY", "ENDKEY", "MAXVERSION", "A", "B", "C", "D", "E", "F", "G", 
		"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", 
		"V", "W", "X", "Y", "Z", "ID", "STRING", "STR", "DOT_L", "DOT_R", "SPACE", 
		"COMMENT_INPUT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'`'", "'''", "'${'", "'}'", "'('", "')'", "','", "';'", "'='", 
		"'!='", "'>'", "'>='", "'<'", "'<='", "'+'", "'-'", "'*'", "'/'", "'%'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "LR_BRACKET", "RR_BRACKET", "COMMA", "SEMICOLON", 
		"EQ", "NOTEQ", "GREATER", "GREATEREQUAL", "LESS", "LESSEQUAL", "PLUS", 
		"MINUS", "ASTERISK", "SLASH", "MOD", "IS", "NOTMATCH", "MATCH", "BETWEEN", 
		"MISSING", "LIMIT", "TS", "STARTTS", "ENDTS", "CREATE", "INSERT", "INTO", 
		"VALUES", "SELECT", "UPDATE", "SET", "DELETE", "FROM", "TABLE", "COLUMNFAMILY", 
		"WHERE", "AND", "OR", "IN", "LIKE", "NULL", "NOT", "INTEGER", "ROWKEY", 
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


	public HBaseSQLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "HBaseSQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2:\u0226\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3"+
		"\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\17\3"+
		"\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3"+
		"\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3"+
		"\34\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3"+
		"\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 "+
		"\3 \3 \3 \3!\3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3"+
		"#\3#\3#\3#\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3%\3&\3&\3&\3&\3&\3\'\3\'\3\'"+
		"\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)"+
		"\3*\3*\3*\3*\3+\3+\3+\3,\3,\3,\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3/\3/\3/"+
		"\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3"+
		"\61\3\61\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3"+
		"\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3"+
		"\64\3\65\3\65\3\66\3\66\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3<\3<\3=\3="+
		"\3>\3>\3?\3?\3@\3@\3A\3A\3B\3B\3C\3C\3D\3D\3E\3E\3F\3F\3G\3G\3H\3H\3I"+
		"\3I\3J\3J\3K\3K\3L\3L\3M\3M\3N\3N\3O\6O\u01d9\nO\rO\16O\u01da\3P\3P\3"+
		"P\3P\3Q\7Q\u01e2\nQ\fQ\16Q\u01e5\13Q\3R\3R\3S\3S\3T\6T\u01ec\nT\rT\16"+
		"T\u01ed\3T\3T\3U\3U\3U\3U\7U\u01f6\nU\fU\16U\u01f9\13U\3U\3U\3U\3U\3U"+
		"\3V\3V\3V\3V\7V\u0204\nV\fV\16V\u0207\13V\3V\5V\u020a\nV\3V\7V\u020d\n"+
		"V\fV\16V\u0210\13V\3V\5V\u0213\nV\3V\3V\5V\u0217\nV\3V\3V\3V\3V\5V\u021d"+
		"\nV\3V\3V\5V\u0221\nV\5V\u0223\nV\3V\3V\3\u01f7\2W\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'"+
		"\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'"+
		"M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\2k\2m\2o\2q\2s\2u\2w\2y\2{\2"+
		"}\2\177\2\u0081\2\u0083\2\u0085\2\u0087\2\u0089\2\u008b\2\u008d\2\u008f"+
		"\2\u0091\2\u0093\2\u0095\2\u0097\2\u0099\2\u009b\2\u009d\66\u009f\67\u00a1"+
		"\2\u00a3\2\u00a5\2\u00a78\u00a99\u00ab:\3\2!\4\2CCcc\4\2DDdd\4\2EEee\4"+
		"\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNn"+
		"n\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2"+
		"WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\7\2\60\60\62<C\\aac|\13"+
		"\2\13\f\17\17\"(*.\60\60\62<>@B\u0080\u0082\1\5\2\13\f\17\17\"\"\4\2\13"+
		"\13\"\"\4\2\f\f\17\17\u0214\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3"+
		"\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37"+
		"\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3"+
		"\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2"+
		"\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C"+
		"\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2"+
		"\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2"+
		"\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2\u009d"+
		"\3\2\2\2\2\u009f\3\2\2\2\2\u00a7\3\2\2\2\2\u00a9\3\2\2\2\2\u00ab\3\2\2"+
		"\2\3\u00ad\3\2\2\2\5\u00af\3\2\2\2\7\u00b1\3\2\2\2\t\u00b4\3\2\2\2\13"+
		"\u00b6\3\2\2\2\r\u00b8\3\2\2\2\17\u00ba\3\2\2\2\21\u00bc\3\2\2\2\23\u00be"+
		"\3\2\2\2\25\u00c0\3\2\2\2\27\u00c3\3\2\2\2\31\u00c5\3\2\2\2\33\u00c8\3"+
		"\2\2\2\35\u00ca\3\2\2\2\37\u00cd\3\2\2\2!\u00cf\3\2\2\2#\u00d1\3\2\2\2"+
		"%\u00d3\3\2\2\2\'\u00d5\3\2\2\2)\u00d7\3\2\2\2+\u00da\3\2\2\2-\u00e3\3"+
		"\2\2\2/\u00e9\3\2\2\2\61\u00f1\3\2\2\2\63\u00f9\3\2\2\2\65\u00ff\3\2\2"+
		"\2\67\u0102\3\2\2\29\u010a\3\2\2\2;\u0110\3\2\2\2=\u0117\3\2\2\2?\u011e"+
		"\3\2\2\2A\u0123\3\2\2\2C\u012a\3\2\2\2E\u0131\3\2\2\2G\u0138\3\2\2\2I"+
		"\u013c\3\2\2\2K\u0143\3\2\2\2M\u0148\3\2\2\2O\u014e\3\2\2\2Q\u015b\3\2"+
		"\2\2S\u0161\3\2\2\2U\u0165\3\2\2\2W\u0168\3\2\2\2Y\u016b\3\2\2\2[\u0170"+
		"\3\2\2\2]\u0175\3\2\2\2_\u0179\3\2\2\2a\u0181\3\2\2\2c\u0188\3\2\2\2e"+
		"\u0191\3\2\2\2g\u0198\3\2\2\2i\u01a3\3\2\2\2k\u01a5\3\2\2\2m\u01a7\3\2"+
		"\2\2o\u01a9\3\2\2\2q\u01ab\3\2\2\2s\u01ad\3\2\2\2u\u01af\3\2\2\2w\u01b1"+
		"\3\2\2\2y\u01b3\3\2\2\2{\u01b5\3\2\2\2}\u01b7\3\2\2\2\177\u01b9\3\2\2"+
		"\2\u0081\u01bb\3\2\2\2\u0083\u01bd\3\2\2\2\u0085\u01bf\3\2\2\2\u0087\u01c1"+
		"\3\2\2\2\u0089\u01c3\3\2\2\2\u008b\u01c5\3\2\2\2\u008d\u01c7\3\2\2\2\u008f"+
		"\u01c9\3\2\2\2\u0091\u01cb\3\2\2\2\u0093\u01cd\3\2\2\2\u0095\u01cf\3\2"+
		"\2\2\u0097\u01d1\3\2\2\2\u0099\u01d3\3\2\2\2\u009b\u01d5\3\2\2\2\u009d"+
		"\u01d8\3\2\2\2\u009f\u01dc\3\2\2\2\u00a1\u01e3\3\2\2\2\u00a3\u01e6\3\2"+
		"\2\2\u00a5\u01e8\3\2\2\2\u00a7\u01eb\3\2\2\2\u00a9\u01f1\3\2\2\2\u00ab"+
		"\u0222\3\2\2\2\u00ad\u00ae\7b\2\2\u00ae\4\3\2\2\2\u00af\u00b0\7)\2\2\u00b0"+
		"\6\3\2\2\2\u00b1\u00b2\7&\2\2\u00b2\u00b3\7}\2\2\u00b3\b\3\2\2\2\u00b4"+
		"\u00b5\7\177\2\2\u00b5\n\3\2\2\2\u00b6\u00b7\7*\2\2\u00b7\f\3\2\2\2\u00b8"+
		"\u00b9\7+\2\2\u00b9\16\3\2\2\2\u00ba\u00bb\7.\2\2\u00bb\20\3\2\2\2\u00bc"+
		"\u00bd\7=\2\2\u00bd\22\3\2\2\2\u00be\u00bf\7?\2\2\u00bf\24\3\2\2\2\u00c0"+
		"\u00c1\7#\2\2\u00c1\u00c2\7?\2\2\u00c2\26\3\2\2\2\u00c3\u00c4\7@\2\2\u00c4"+
		"\30\3\2\2\2\u00c5\u00c6\7@\2\2\u00c6\u00c7\7?\2\2\u00c7\32\3\2\2\2\u00c8"+
		"\u00c9\7>\2\2\u00c9\34\3\2\2\2\u00ca\u00cb\7>\2\2\u00cb\u00cc\7?\2\2\u00cc"+
		"\36\3\2\2\2\u00cd\u00ce\7-\2\2\u00ce \3\2\2\2\u00cf\u00d0\7/\2\2\u00d0"+
		"\"\3\2\2\2\u00d1\u00d2\7,\2\2\u00d2$\3\2\2\2\u00d3\u00d4\7\61\2\2\u00d4"+
		"&\3\2\2\2\u00d5\u00d6\7\'\2\2\u00d6(\3\2\2\2\u00d7\u00d8\5y=\2\u00d8\u00d9"+
		"\5\u008dG\2\u00d9*\3\2\2\2\u00da\u00db\5\u0083B\2\u00db\u00dc\5\u0085"+
		"C\2\u00dc\u00dd\5\u008fH\2\u00dd\u00de\5\u0081A\2\u00de\u00df\5i\65\2"+
		"\u00df\u00e0\5\u008fH\2\u00e0\u00e1\5m\67\2\u00e1\u00e2\5w<\2\u00e2,\3"+
		"\2\2\2\u00e3\u00e4\5\u0081A\2\u00e4\u00e5\5i\65\2\u00e5\u00e6\5\u008f"+
		"H\2\u00e6\u00e7\5m\67\2\u00e7\u00e8\5w<\2\u00e8.\3\2\2\2\u00e9\u00ea\5"+
		"k\66\2\u00ea\u00eb\5q9\2\u00eb\u00ec\5\u008fH\2\u00ec\u00ed\5\u0095K\2"+
		"\u00ed\u00ee\5q9\2\u00ee\u00ef\5q9\2\u00ef\u00f0\5\u0083B\2\u00f0\60\3"+
		"\2\2\2\u00f1\u00f2\5\u0081A\2\u00f2\u00f3\5y=\2\u00f3\u00f4\5\u008dG\2"+
		"\u00f4\u00f5\5\u008dG\2\u00f5\u00f6\5y=\2\u00f6\u00f7\5\u0083B\2\u00f7"+
		"\u00f8\5u;\2\u00f8\62\3\2\2\2\u00f9\u00fa\5\177@\2\u00fa\u00fb\5y=\2\u00fb"+
		"\u00fc\5\u0081A\2\u00fc\u00fd\5y=\2\u00fd\u00fe\5\u008fH\2\u00fe\64\3"+
		"\2\2\2\u00ff\u0100\5\u008fH\2\u0100\u0101\5\u008dG\2\u0101\66\3\2\2\2"+
		"\u0102\u0103\5\u008dG\2\u0103\u0104\5\u008fH\2\u0104\u0105\5i\65\2\u0105"+
		"\u0106\5\u008bF\2\u0106\u0107\5\u008fH\2\u0107\u0108\5\u008fH\2\u0108"+
		"\u0109\5\u008dG\2\u01098\3\2\2\2\u010a\u010b\5q9\2\u010b\u010c\5\u0083"+
		"B\2\u010c\u010d\5o8\2\u010d\u010e\5\u008fH\2\u010e\u010f\5\u008dG\2\u010f"+
		":\3\2\2\2\u0110\u0111\5m\67\2\u0111\u0112\5\u008bF\2\u0112\u0113\5q9\2"+
		"\u0113\u0114\5i\65\2\u0114\u0115\5\u008fH\2\u0115\u0116\5q9\2\u0116<\3"+
		"\2\2\2\u0117\u0118\5y=\2\u0118\u0119\5\u0083B\2\u0119\u011a\5\u008dG\2"+
		"\u011a\u011b\5q9\2\u011b\u011c\5\u008bF\2\u011c\u011d\5\u008fH\2\u011d"+
		">\3\2\2\2\u011e\u011f\5y=\2\u011f\u0120\5\u0083B\2\u0120\u0121\5\u008f"+
		"H\2\u0121\u0122\5\u0085C\2\u0122@\3\2\2\2\u0123\u0124\5\u0093J\2\u0124"+
		"\u0125\5i\65\2\u0125\u0126\5\177@\2\u0126\u0127\5\u0091I\2\u0127\u0128"+
		"\5q9\2\u0128\u0129\5\u008dG\2\u0129B\3\2\2\2\u012a\u012b\5\u008dG\2\u012b"+
		"\u012c\5q9\2\u012c\u012d\5\177@\2\u012d\u012e\5q9\2\u012e\u012f\5m\67"+
		"\2\u012f\u0130\5\u008fH\2\u0130D\3\2\2\2\u0131\u0132\5\u0091I\2\u0132"+
		"\u0133\5\u0087D\2\u0133\u0134\5o8\2\u0134\u0135\5i\65\2\u0135\u0136\5"+
		"\u008fH\2\u0136\u0137\5q9\2\u0137F\3\2\2\2\u0138\u0139\5\u008dG\2\u0139"+
		"\u013a\5q9\2\u013a\u013b\5\u008fH\2\u013bH\3\2\2\2\u013c\u013d\5o8\2\u013d"+
		"\u013e\5q9\2\u013e\u013f\5\177@\2\u013f\u0140\5q9\2\u0140\u0141\5\u008f"+
		"H\2\u0141\u0142\5q9\2\u0142J\3\2\2\2\u0143\u0144\5s:\2\u0144\u0145\5\u008b"+
		"F\2\u0145\u0146\5\u0085C\2\u0146\u0147\5\u0081A\2\u0147L\3\2\2\2\u0148"+
		"\u0149\5\u008fH\2\u0149\u014a\5i\65\2\u014a\u014b\5k\66\2\u014b\u014c"+
		"\5\177@\2\u014c\u014d\5q9\2\u014dN\3\2\2\2\u014e\u014f\5m\67\2\u014f\u0150"+
		"\5\u0085C\2\u0150\u0151\5\177@\2\u0151\u0152\5\u0091I\2\u0152\u0153\5"+
		"\u0081A\2\u0153\u0154\5\u0083B\2\u0154\u0155\5s:\2\u0155\u0156\5i\65\2"+
		"\u0156\u0157\5\u0081A\2\u0157\u0158\5y=\2\u0158\u0159\5\177@\2\u0159\u015a"+
		"\5\u0099M\2\u015aP\3\2\2\2\u015b\u015c\5\u0095K\2\u015c\u015d\5w<\2\u015d"+
		"\u015e\5q9\2\u015e\u015f\5\u008bF\2\u015f\u0160\5q9\2\u0160R\3\2\2\2\u0161"+
		"\u0162\5i\65\2\u0162\u0163\5\u0083B\2\u0163\u0164\5o8\2\u0164T\3\2\2\2"+
		"\u0165\u0166\5\u0085C\2\u0166\u0167\5\u008bF\2\u0167V\3\2\2\2\u0168\u0169"+
		"\5y=\2\u0169\u016a\5\u0083B\2\u016aX\3\2\2\2\u016b\u016c\5\177@\2\u016c"+
		"\u016d\5y=\2\u016d\u016e\5}?\2\u016e\u016f\5q9\2\u016fZ\3\2\2\2\u0170"+
		"\u0171\5\u0083B\2\u0171\u0172\5\u0091I\2\u0172\u0173\5\177@\2\u0173\u0174"+
		"\5\177@\2\u0174\\\3\2\2\2\u0175\u0176\5\u0083B\2\u0176\u0177\5\u0085C"+
		"\2\u0177\u0178\5\u008fH\2\u0178^\3\2\2\2\u0179\u017a\5y=\2\u017a\u017b"+
		"\5\u0083B\2\u017b\u017c\5\u008fH\2\u017c\u017d\5q9\2\u017d\u017e\5u;\2"+
		"\u017e\u017f\5q9\2\u017f\u0180\5\u008bF\2\u0180`\3\2\2\2\u0181\u0182\5"+
		"\u008bF\2\u0182\u0183\5\u0085C\2\u0183\u0184\5\u0095K\2\u0184\u0185\5"+
		"}?\2\u0185\u0186\5q9\2\u0186\u0187\5\u0099M\2\u0187b\3\2\2\2\u0188\u0189"+
		"\5\u008dG\2\u0189\u018a\5\u008fH\2\u018a\u018b\5i\65\2\u018b\u018c\5\u008b"+
		"F\2\u018c\u018d\5\u008fH\2\u018d\u018e\5}?\2\u018e\u018f\5q9\2\u018f\u0190"+
		"\5\u0099M\2\u0190d\3\2\2\2\u0191\u0192\5q9\2\u0192\u0193\5\u0083B\2\u0193"+
		"\u0194\5o8\2\u0194\u0195\5}?\2\u0195\u0196\5q9\2\u0196\u0197\5\u0099M"+
		"\2\u0197f\3\2\2\2\u0198\u0199\5\u0081A\2\u0199\u019a\5i\65\2\u019a\u019b"+
		"\5\u0097L\2\u019b\u019c\5\u0093J\2\u019c\u019d\5q9\2\u019d\u019e\5\u008b"+
		"F\2\u019e\u019f\5\u008dG\2\u019f\u01a0\5y=\2\u01a0\u01a1\5\u0085C\2\u01a1"+
		"\u01a2\5\u0083B\2\u01a2h\3\2\2\2\u01a3\u01a4\t\2\2\2\u01a4j\3\2\2\2\u01a5"+
		"\u01a6\t\3\2\2\u01a6l\3\2\2\2\u01a7\u01a8\t\4\2\2\u01a8n\3\2\2\2\u01a9"+
		"\u01aa\t\5\2\2\u01aap\3\2\2\2\u01ab\u01ac\t\6\2\2\u01acr\3\2\2\2\u01ad"+
		"\u01ae\t\7\2\2\u01aet\3\2\2\2\u01af\u01b0\t\b\2\2\u01b0v\3\2\2\2\u01b1"+
		"\u01b2\t\t\2\2\u01b2x\3\2\2\2\u01b3\u01b4\t\n\2\2\u01b4z\3\2\2\2\u01b5"+
		"\u01b6\t\13\2\2\u01b6|\3\2\2\2\u01b7\u01b8\t\f\2\2\u01b8~\3\2\2\2\u01b9"+
		"\u01ba\t\r\2\2\u01ba\u0080\3\2\2\2\u01bb\u01bc\t\16\2\2\u01bc\u0082\3"+
		"\2\2\2\u01bd\u01be\t\17\2\2\u01be\u0084\3\2\2\2\u01bf\u01c0\t\20\2\2\u01c0"+
		"\u0086\3\2\2\2\u01c1\u01c2\t\21\2\2\u01c2\u0088\3\2\2\2\u01c3\u01c4\t"+
		"\22\2\2\u01c4\u008a\3\2\2\2\u01c5\u01c6\t\23\2\2\u01c6\u008c\3\2\2\2\u01c7"+
		"\u01c8\t\24\2\2\u01c8\u008e\3\2\2\2\u01c9\u01ca\t\25\2\2\u01ca\u0090\3"+
		"\2\2\2\u01cb\u01cc\t\26\2\2\u01cc\u0092\3\2\2\2\u01cd\u01ce\t\27\2\2\u01ce"+
		"\u0094\3\2\2\2\u01cf\u01d0\t\30\2\2\u01d0\u0096\3\2\2\2\u01d1\u01d2\t"+
		"\31\2\2\u01d2\u0098\3\2\2\2\u01d3\u01d4\t\32\2\2\u01d4\u009a\3\2\2\2\u01d5"+
		"\u01d6\t\33\2\2\u01d6\u009c\3\2\2\2\u01d7\u01d9\t\34\2\2\u01d8\u01d7\3"+
		"\2\2\2\u01d9\u01da\3\2\2\2\u01da\u01d8\3\2\2\2\u01da\u01db\3\2\2\2\u01db"+
		"\u009e\3\2\2\2\u01dc\u01dd\5\u00a3R\2\u01dd\u01de\5\u00a1Q\2\u01de\u01df"+
		"\5\u00a5S\2\u01df\u00a0\3\2\2\2\u01e0\u01e2\t\35\2\2\u01e1\u01e0\3\2\2"+
		"\2\u01e2\u01e5\3\2\2\2\u01e3\u01e1\3\2\2\2\u01e3\u01e4\3\2\2\2\u01e4\u00a2"+
		"\3\2\2\2\u01e5\u01e3\3\2\2\2\u01e6\u01e7\7)\2\2\u01e7\u00a4\3\2\2\2\u01e8"+
		"\u01e9\7)\2\2\u01e9\u00a6\3\2\2\2\u01ea\u01ec\t\36\2\2\u01eb\u01ea\3\2"+
		"\2\2\u01ec\u01ed\3\2\2\2\u01ed\u01eb\3\2\2\2\u01ed\u01ee\3\2\2\2\u01ee"+
		"\u01ef\3\2\2\2\u01ef\u01f0\bT\2\2\u01f0\u00a8\3\2\2\2\u01f1\u01f2\7\61"+
		"\2\2\u01f2\u01f3\7,\2\2\u01f3\u01f7\3\2\2\2\u01f4\u01f6\13\2\2\2\u01f5"+
		"\u01f4\3\2\2\2\u01f6\u01f9\3\2\2\2\u01f7\u01f8\3\2\2\2\u01f7\u01f5\3\2"+
		"\2\2\u01f8\u01fa\3\2\2\2\u01f9\u01f7\3\2\2\2\u01fa\u01fb\7,\2\2\u01fb"+
		"\u01fc\7\61\2\2\u01fc\u01fd\3\2\2\2\u01fd\u01fe\bU\2\2\u01fe\u00aa\3\2"+
		"\2\2\u01ff\u0200\7/\2\2\u0200\u0201\7/\2\2\u0201\u0205\3\2\2\2\u0202\u0204"+
		"\t\37\2\2\u0203\u0202\3\2\2\2\u0204\u0207\3\2\2\2\u0205\u0203\3\2\2\2"+
		"\u0205\u0206\3\2\2\2\u0206\u020a\3\2\2\2\u0207\u0205\3\2\2\2\u0208\u020a"+
		"\7%\2\2\u0209\u01ff\3\2\2\2\u0209\u0208\3\2\2\2\u020a\u020e\3\2\2\2\u020b"+
		"\u020d\n \2\2\u020c\u020b\3\2\2\2\u020d\u0210\3\2\2\2\u020e\u020c\3\2"+
		"\2\2\u020e\u020f\3\2\2\2\u020f\u0216\3\2\2\2\u0210\u020e\3\2\2\2\u0211"+
		"\u0213\7\17\2\2\u0212\u0211\3\2\2\2\u0212\u0213\3\2\2\2\u0213\u0214\3"+
		"\2\2\2\u0214\u0217\7\f\2\2\u0215\u0217\7\2\2\3\u0216\u0212\3\2\2\2\u0216"+
		"\u0215\3\2\2\2\u0217\u0223\3\2\2\2\u0218\u0219\7/\2\2\u0219\u021a\7/\2"+
		"\2\u021a\u0220\3\2\2\2\u021b\u021d\7\17\2\2\u021c\u021b\3\2\2\2\u021c"+
		"\u021d\3\2\2\2\u021d\u021e\3\2\2\2\u021e\u0221\7\f\2\2\u021f\u0221\7\2"+
		"\2\3\u0220\u021c\3\2\2\2\u0220\u021f\3\2\2\2\u0221\u0223\3\2\2\2\u0222"+
		"\u0209\3\2\2\2\u0222\u0218\3\2\2\2\u0223\u0224\3\2\2\2\u0224\u0225\bV"+
		"\2\2\u0225\u00ac\3\2\2\2\17\2\u01da\u01e3\u01ed\u01f7\u0205\u0209\u020e"+
		"\u0212\u0216\u021c\u0220\u0222\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}