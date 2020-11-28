// Generated from HBaseSQL.g4 by ANTLR 4.5.1

package com.github.CCwexiao.dsl.auto;

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
		T__0=1, T__1=2, T__2=3, STAR=4, LB=5, RB=6, WHERE=7, SELECT=8, INSERT=9, 
		DELETE=10, INTO=11, VALUES=12, FROM=13, ROWKEY=14, STARTKEY=15, ENDKEY=16, 
		HBASESTARTKEY=17, HBASEENDKEY=18, MAXVERSION=19, LIMIT=20, TS=21, STARTTS=22, 
		ENDTS=23, IS=24, NULL=25, NOT=26, AND=27, OR=28, LESSEQUAL=29, LESS=30, 
		GREATEREQUAL=31, GREATER=32, NOTEQUAL=33, EQUAL=34, NOTMATCH=35, MATCH=36, 
		IN=37, NOTIN=38, BETWEEN=39, NOTBETWEEN=40, ISNULL=41, ISNOTNULL=42, ISMISSING=43, 
		ISNOTMISSING=44, TEXT=45, NUM=46, WS=47;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "STAR", "LB", "RB", "WHERE", "SELECT", "INSERT", 
		"DELETE", "INTO", "VALUES", "FROM", "ROWKEY", "STARTKEY", "ENDKEY", "HBASESTARTKEY", 
		"HBASEENDKEY", "MAXVERSION", "LIMIT", "TS", "STARTTS", "ENDTS", "IS", 
		"NULL", "NOT", "AND", "OR", "LESSEQUAL", "LESS", "GREATEREQUAL", "GREATER", 
		"NOTEQUAL", "EQUAL", "NOTMATCH", "MATCH", "IN", "NOTIN", "BETWEEN", "NOTBETWEEN", 
		"ISNULL", "ISNOTNULL", "ISMISSING", "ISNOTMISSING", "TEXT", "NUM", "WS"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\61\u01f8\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\bx\n\b\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u0086\n\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u0094\n\n\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u00a2\n\13\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\5\f\u00ac\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\5\r\u00ba\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00c4"+
		"\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17"+
		"\u00d2\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\5\20\u00e4\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\5\21\u00f2\n\21\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u0122"+
		"\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u012e\n\25"+
		"\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\31\3\31\3\31\3\31\5\31\u0145\n\31\3\32\3\32\3\32\3\32"+
		"\3\32\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u0152\n\33\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\5\34\u015a\n\34\3\35\3\35\3\35\3\35\5\35\u0160\n\35\3\36\3"+
		"\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3"+
		" \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3!\3!\3\"\3\""+
		"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3"+
		"$\3$\3%\3%\3%\3%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3"+
		"(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3*\3+\3"+
		"+\3+\3+\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3-\3-\3-\3-\3"+
		"-\3-\3-\3-\3-\3-\3-\3-\3-\3.\6.\u01e9\n.\r.\16.\u01ea\3/\6/\u01ee\n/\r"+
		"/\16/\u01ef\3\60\6\60\u01f3\n\60\r\60\16\60\u01f4\3\60\3\60\2\2\61\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!"+
		"A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61\3\2\5\r\2##%(*.\60\60\62<>@B\\"+
		"^^`|~~\u0080\u0080\3\2\62;\5\2\13\f\17\17\"\"\u020a\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'"+
		"\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63"+
		"\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2"+
		"?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3"+
		"\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2"+
		"\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\3a\3\2\2\2\5c\3\2\2\2\7"+
		"e\3\2\2\2\tg\3\2\2\2\13i\3\2\2\2\rk\3\2\2\2\17w\3\2\2\2\21\u0085\3\2\2"+
		"\2\23\u0093\3\2\2\2\25\u00a1\3\2\2\2\27\u00ab\3\2\2\2\31\u00b9\3\2\2\2"+
		"\33\u00c3\3\2\2\2\35\u00d1\3\2\2\2\37\u00e3\3\2\2\2!\u00f1\3\2\2\2#\u00f3"+
		"\3\2\2\2%\u0101\3\2\2\2\'\u0121\3\2\2\2)\u012d\3\2\2\2+\u012f\3\2\2\2"+
		"-\u0132\3\2\2\2/\u013a\3\2\2\2\61\u0144\3\2\2\2\63\u0146\3\2\2\2\65\u0151"+
		"\3\2\2\2\67\u0159\3\2\2\29\u015f\3\2\2\2;\u0161\3\2\2\2=\u016b\3\2\2\2"+
		"?\u0170\3\2\2\2A\u017d\3\2\2\2C\u0185\3\2\2\2E\u018e\3\2\2\2G\u0194\3"+
		"\2\2\2I\u019d\3\2\2\2K\u01a3\3\2\2\2M\u01a6\3\2\2\2O\u01ac\3\2\2\2Q\u01b4"+
		"\3\2\2\2S\u01bf\3\2\2\2U\u01c6\3\2\2\2W\u01d0\3\2\2\2Y\u01da\3\2\2\2["+
		"\u01e8\3\2\2\2]\u01ed\3\2\2\2_\u01f2\3\2\2\2ab\7.\2\2b\4\3\2\2\2cd\7)"+
		"\2\2d\6\3\2\2\2ef\7%\2\2f\b\3\2\2\2gh\7,\2\2h\n\3\2\2\2ij\7*\2\2j\f\3"+
		"\2\2\2kl\7+\2\2l\16\3\2\2\2mn\7y\2\2no\7j\2\2op\7g\2\2pq\7t\2\2qx\7g\2"+
		"\2rs\7Y\2\2st\7J\2\2tu\7G\2\2uv\7T\2\2vx\7G\2\2wm\3\2\2\2wr\3\2\2\2x\20"+
		"\3\2\2\2yz\7u\2\2z{\7g\2\2{|\7n\2\2|}\7g\2\2}~\7e\2\2~\u0086\7v\2\2\177"+
		"\u0080\7U\2\2\u0080\u0081\7G\2\2\u0081\u0082\7N\2\2\u0082\u0083\7G\2\2"+
		"\u0083\u0084\7E\2\2\u0084\u0086\7V\2\2\u0085y\3\2\2\2\u0085\177\3\2\2"+
		"\2\u0086\22\3\2\2\2\u0087\u0088\7k\2\2\u0088\u0089\7p\2\2\u0089\u008a"+
		"\7u\2\2\u008a\u008b\7g\2\2\u008b\u008c\7t\2\2\u008c\u0094\7v\2\2\u008d"+
		"\u008e\7K\2\2\u008e\u008f\7P\2\2\u008f\u0090\7U\2\2\u0090\u0091\7G\2\2"+
		"\u0091\u0092\7T\2\2\u0092\u0094\7V\2\2\u0093\u0087\3\2\2\2\u0093\u008d"+
		"\3\2\2\2\u0094\24\3\2\2\2\u0095\u0096\7f\2\2\u0096\u0097\7g\2\2\u0097"+
		"\u0098\7n\2\2\u0098\u0099\7g\2\2\u0099\u009a\7v\2\2\u009a\u00a2\7g\2\2"+
		"\u009b\u009c\7F\2\2\u009c\u009d\7G\2\2\u009d\u009e\7N\2\2\u009e\u009f"+
		"\7G\2\2\u009f\u00a0\7V\2\2\u00a0\u00a2\7G\2\2\u00a1\u0095\3\2\2\2\u00a1"+
		"\u009b\3\2\2\2\u00a2\26\3\2\2\2\u00a3\u00a4\7k\2\2\u00a4\u00a5\7p\2\2"+
		"\u00a5\u00a6\7v\2\2\u00a6\u00ac\7q\2\2\u00a7\u00a8\7K\2\2\u00a8\u00a9"+
		"\7P\2\2\u00a9\u00aa\7V\2\2\u00aa\u00ac\7Q\2\2\u00ab\u00a3\3\2\2\2\u00ab"+
		"\u00a7\3\2\2\2\u00ac\30\3\2\2\2\u00ad\u00ae\7x\2\2\u00ae\u00af\7c\2\2"+
		"\u00af\u00b0\7n\2\2\u00b0\u00b1\7w\2\2\u00b1\u00b2\7g\2\2\u00b2\u00ba"+
		"\7u\2\2\u00b3\u00b4\7X\2\2\u00b4\u00b5\7C\2\2\u00b5\u00b6\7N\2\2\u00b6"+
		"\u00b7\7W\2\2\u00b7\u00b8\7G\2\2\u00b8\u00ba\7U\2\2\u00b9\u00ad\3\2\2"+
		"\2\u00b9\u00b3\3\2\2\2\u00ba\32\3\2\2\2\u00bb\u00bc\7h\2\2\u00bc\u00bd"+
		"\7t\2\2\u00bd\u00be\7q\2\2\u00be\u00c4\7o\2\2\u00bf\u00c0\7H\2\2\u00c0"+
		"\u00c1\7T\2\2\u00c1\u00c2\7Q\2\2\u00c2\u00c4\7O\2\2\u00c3\u00bb\3\2\2"+
		"\2\u00c3\u00bf\3\2\2\2\u00c4\34\3\2\2\2\u00c5\u00c6\7t\2\2\u00c6\u00c7"+
		"\7q\2\2\u00c7\u00c8\7y\2\2\u00c8\u00c9\7m\2\2\u00c9\u00ca\7g\2\2\u00ca"+
		"\u00d2\7{\2\2\u00cb\u00cc\7t\2\2\u00cc\u00cd\7q\2\2\u00cd\u00ce\7y\2\2"+
		"\u00ce\u00cf\7M\2\2\u00cf\u00d0\7g\2\2\u00d0\u00d2\7{\2\2\u00d1\u00c5"+
		"\3\2\2\2\u00d1\u00cb\3\2\2\2\u00d2\36\3\2\2\2\u00d3\u00d4\7u\2\2\u00d4"+
		"\u00d5\7v\2\2\u00d5\u00d6\7c\2\2\u00d6\u00d7\7t\2\2\u00d7\u00d8\7v\2\2"+
		"\u00d8\u00d9\7m\2\2\u00d9\u00da\7g\2\2\u00da\u00e4\7{\2\2\u00db\u00dc"+
		"\7u\2\2\u00dc\u00dd\7v\2\2\u00dd\u00de\7c\2\2\u00de\u00df\7t\2\2\u00df"+
		"\u00e0\7v\2\2\u00e0\u00e1\7M\2\2\u00e1\u00e2\7g\2\2\u00e2\u00e4\7{\2\2"+
		"\u00e3\u00d3\3\2\2\2\u00e3\u00db\3\2\2\2\u00e4 \3\2\2\2\u00e5\u00e6\7"+
		"g\2\2\u00e6\u00e7\7p\2\2\u00e7\u00e8\7f\2\2\u00e8\u00e9\7m\2\2\u00e9\u00ea"+
		"\7g\2\2\u00ea\u00f2\7{\2\2\u00eb\u00ec\7g\2\2\u00ec\u00ed\7p\2\2\u00ed"+
		"\u00ee\7f\2\2\u00ee\u00ef\7M\2\2\u00ef\u00f0\7g\2\2\u00f0\u00f2\7{\2\2"+
		"\u00f1\u00e5\3\2\2\2\u00f1\u00eb\3\2\2\2\u00f2\"\3\2\2\2\u00f3\u00f4\7"+
		"j\2\2\u00f4\u00f5\7d\2\2\u00f5\u00f6\7c\2\2\u00f6\u00f7\7u\2\2\u00f7\u00f8"+
		"\7g\2\2\u00f8\u00f9\7u\2\2\u00f9\u00fa\7v\2\2\u00fa\u00fb\7c\2\2\u00fb"+
		"\u00fc\7t\2\2\u00fc\u00fd\7v\2\2\u00fd\u00fe\7m\2\2\u00fe\u00ff\7g\2\2"+
		"\u00ff\u0100\7{\2\2\u0100$\3\2\2\2\u0101\u0102\7j\2\2\u0102\u0103\7d\2"+
		"\2\u0103\u0104\7c\2\2\u0104\u0105\7u\2\2\u0105\u0106\7g\2\2\u0106\u0107"+
		"\7g\2\2\u0107\u0108\7p\2\2\u0108\u0109\7f\2\2\u0109\u010a\7m\2\2\u010a"+
		"\u010b\7g\2\2\u010b\u010c\7{\2\2\u010c&\3\2\2\2\u010d\u010e\7o\2\2\u010e"+
		"\u010f\7c\2\2\u010f\u0110\7z\2\2\u0110\u0111\7x\2\2\u0111\u0112\7g\2\2"+
		"\u0112\u0113\7t\2\2\u0113\u0114\7u\2\2\u0114\u0115\7k\2\2\u0115\u0116"+
		"\7q\2\2\u0116\u0122\7p\2\2\u0117\u0118\7o\2\2\u0118\u0119\7c\2\2\u0119"+
		"\u011a\7z\2\2\u011a\u011b\7X\2\2\u011b\u011c\7g\2\2\u011c\u011d\7t\2\2"+
		"\u011d\u011e\7u\2\2\u011e\u011f\7k\2\2\u011f\u0120\7q\2\2\u0120\u0122"+
		"\7p\2\2\u0121\u010d\3\2\2\2\u0121\u0117\3\2\2\2\u0122(\3\2\2\2\u0123\u0124"+
		"\7n\2\2\u0124\u0125\7k\2\2\u0125\u0126\7o\2\2\u0126\u0127\7k\2\2\u0127"+
		"\u012e\7v\2\2\u0128\u0129\7N\2\2\u0129\u012a\7K\2\2\u012a\u012b\7O\2\2"+
		"\u012b\u012c\7K\2\2\u012c\u012e\7V\2\2\u012d\u0123\3\2\2\2\u012d\u0128"+
		"\3\2\2\2\u012e*\3\2\2\2\u012f\u0130\7v\2\2\u0130\u0131\7u\2\2\u0131,\3"+
		"\2\2\2\u0132\u0133\7u\2\2\u0133\u0134\7v\2\2\u0134\u0135\7c\2\2\u0135"+
		"\u0136\7t\2\2\u0136\u0137\7v\2\2\u0137\u0138\7V\2\2\u0138\u0139\7U\2\2"+
		"\u0139.\3\2\2\2\u013a\u013b\7g\2\2\u013b\u013c\7p\2\2\u013c\u013d\7f\2"+
		"\2\u013d\u013e\7V\2\2\u013e\u013f\7U\2\2\u013f\60\3\2\2\2\u0140\u0141"+
		"\7k\2\2\u0141\u0145\7u\2\2\u0142\u0143\7K\2\2\u0143\u0145\7U\2\2\u0144"+
		"\u0140\3\2\2\2\u0144\u0142\3\2\2\2\u0145\62\3\2\2\2\u0146\u0147\7p\2\2"+
		"\u0147\u0148\7w\2\2\u0148\u0149\7n\2\2\u0149\u014a\7n\2\2\u014a\64\3\2"+
		"\2\2\u014b\u014c\7p\2\2\u014c\u014d\7q\2\2\u014d\u0152\7v\2\2\u014e\u014f"+
		"\7P\2\2\u014f\u0150\7Q\2\2\u0150\u0152\7V\2\2\u0151\u014b\3\2\2\2\u0151"+
		"\u014e\3\2\2\2\u0152\66\3\2\2\2\u0153\u0154\7c\2\2\u0154\u0155\7p\2\2"+
		"\u0155\u015a\7f\2\2\u0156\u0157\7C\2\2\u0157\u0158\7P\2\2\u0158\u015a"+
		"\7F\2\2\u0159\u0153\3\2\2\2\u0159\u0156\3\2\2\2\u015a8\3\2\2\2\u015b\u015c"+
		"\7q\2\2\u015c\u0160\7t\2\2\u015d\u015e\7Q\2\2\u015e\u0160\7T\2\2\u015f"+
		"\u015b\3\2\2\2\u015f\u015d\3\2\2\2\u0160:\3\2\2\2\u0161\u0162\7n\2\2\u0162"+
		"\u0163\7g\2\2\u0163\u0164\7u\2\2\u0164\u0165\7u\2\2\u0165\u0166\7g\2\2"+
		"\u0166\u0167\7s\2\2\u0167\u0168\7w\2\2\u0168\u0169\7c\2\2\u0169\u016a"+
		"\7n\2\2\u016a<\3\2\2\2\u016b\u016c\7n\2\2\u016c\u016d\7g\2\2\u016d\u016e"+
		"\7u\2\2\u016e\u016f\7u\2\2\u016f>\3\2\2\2\u0170\u0171\7i\2\2\u0171\u0172"+
		"\7t\2\2\u0172\u0173\7g\2\2\u0173\u0174\7c\2\2\u0174\u0175\7v\2\2\u0175"+
		"\u0176\7g\2\2\u0176\u0177\7t\2\2\u0177\u0178\7g\2\2\u0178\u0179\7s\2\2"+
		"\u0179\u017a\7w\2\2\u017a\u017b\7c\2\2\u017b\u017c\7n\2\2\u017c@\3\2\2"+
		"\2\u017d\u017e\7i\2\2\u017e\u017f\7t\2\2\u017f\u0180\7g\2\2\u0180\u0181"+
		"\7c\2\2\u0181\u0182\7v\2\2\u0182\u0183\7g\2\2\u0183\u0184\7t\2\2\u0184"+
		"B\3\2\2\2\u0185\u0186\7p\2\2\u0186\u0187\7q\2\2\u0187\u0188\7v\2\2\u0188"+
		"\u0189\7g\2\2\u0189\u018a\7s\2\2\u018a\u018b\7w\2\2\u018b\u018c\7c\2\2"+
		"\u018c\u018d\7n\2\2\u018dD\3\2\2\2\u018e\u018f\7g\2\2\u018f\u0190\7s\2"+
		"\2\u0190\u0191\7w\2\2\u0191\u0192\7c\2\2\u0192\u0193\7n\2\2\u0193F\3\2"+
		"\2\2\u0194\u0195\7p\2\2\u0195\u0196\7q\2\2\u0196\u0197\7v\2\2\u0197\u0198"+
		"\7o\2\2\u0198\u0199\7c\2\2\u0199\u019a\7v\2\2\u019a\u019b\7e\2\2\u019b"+
		"\u019c\7j\2\2\u019cH\3\2\2\2\u019d\u019e\7o\2\2\u019e\u019f\7c\2\2\u019f"+
		"\u01a0\7v\2\2\u01a0\u01a1\7e\2\2\u01a1\u01a2\7j\2\2\u01a2J\3\2\2\2\u01a3"+
		"\u01a4\7k\2\2\u01a4\u01a5\7p\2\2\u01a5L\3\2\2\2\u01a6\u01a7\7p\2\2\u01a7"+
		"\u01a8\7q\2\2\u01a8\u01a9\7v\2\2\u01a9\u01aa\7k\2\2\u01aa\u01ab\7p\2\2"+
		"\u01abN\3\2\2\2\u01ac\u01ad\7d\2\2\u01ad\u01ae\7g\2\2\u01ae\u01af\7v\2"+
		"\2\u01af\u01b0\7y\2\2\u01b0\u01b1\7g\2\2\u01b1\u01b2\7g\2\2\u01b2\u01b3"+
		"\7p\2\2\u01b3P\3\2\2\2\u01b4\u01b5\7p\2\2\u01b5\u01b6\7q\2\2\u01b6\u01b7"+
		"\7v\2\2\u01b7\u01b8\7d\2\2\u01b8\u01b9\7g\2\2\u01b9\u01ba\7v\2\2\u01ba"+
		"\u01bb\7y\2\2\u01bb\u01bc\7g\2\2\u01bc\u01bd\7g\2\2\u01bd\u01be\7p\2\2"+
		"\u01beR\3\2\2\2\u01bf\u01c0\7k\2\2\u01c0\u01c1\7u\2\2\u01c1\u01c2\7p\2"+
		"\2\u01c2\u01c3\7w\2\2\u01c3\u01c4\7n\2\2\u01c4\u01c5\7n\2\2\u01c5T\3\2"+
		"\2\2\u01c6\u01c7\7k\2\2\u01c7\u01c8\7u\2\2\u01c8\u01c9\7p\2\2\u01c9\u01ca"+
		"\7q\2\2\u01ca\u01cb\7v\2\2\u01cb\u01cc\7p\2\2\u01cc\u01cd\7w\2\2\u01cd"+
		"\u01ce\7n\2\2\u01ce\u01cf\7n\2\2\u01cfV\3\2\2\2\u01d0\u01d1\7k\2\2\u01d1"+
		"\u01d2\7u\2\2\u01d2\u01d3\7o\2\2\u01d3\u01d4\7k\2\2\u01d4\u01d5\7u\2\2"+
		"\u01d5\u01d6\7u\2\2\u01d6\u01d7\7k\2\2\u01d7\u01d8\7p\2\2\u01d8\u01d9"+
		"\7i\2\2\u01d9X\3\2\2\2\u01da\u01db\7k\2\2\u01db\u01dc\7u\2\2\u01dc\u01dd"+
		"\7p\2\2\u01dd\u01de\7q\2\2\u01de\u01df\7v\2\2\u01df\u01e0\7o\2\2\u01e0"+
		"\u01e1\7k\2\2\u01e1\u01e2\7u\2\2\u01e2\u01e3\7u\2\2\u01e3\u01e4\7k\2\2"+
		"\u01e4\u01e5\7p\2\2\u01e5\u01e6\7i\2\2\u01e6Z\3\2\2\2\u01e7\u01e9\t\2"+
		"\2\2\u01e8\u01e7\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01e8\3\2\2\2\u01ea"+
		"\u01eb\3\2\2\2\u01eb\\\3\2\2\2\u01ec\u01ee\t\3\2\2\u01ed\u01ec\3\2\2\2"+
		"\u01ee\u01ef\3\2\2\2\u01ef\u01ed\3\2\2\2\u01ef\u01f0\3\2\2\2\u01f0^\3"+
		"\2\2\2\u01f1\u01f3\t\4\2\2\u01f2\u01f1\3\2\2\2\u01f3\u01f4\3\2\2\2\u01f4"+
		"\u01f2\3\2\2\2\u01f4\u01f5\3\2\2\2\u01f5\u01f6\3\2\2\2\u01f6\u01f7\b\60"+
		"\2\2\u01f7`\3\2\2\2\26\2w\u0085\u0093\u00a1\u00ab\u00b9\u00c3\u00d1\u00e3"+
		"\u00f1\u0121\u012d\u0144\u0151\u0159\u015f\u01ea\u01ef\u01f4\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}