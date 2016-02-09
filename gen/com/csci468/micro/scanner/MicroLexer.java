// Generated from C:/Users/michael/Documents/IdeaProjects/csci468/src/com/csci468/micro/scanner\MicroLexer.g4 by ANTLR 4.5.1
package com.csci468.micro.scanner;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MicroLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		KEYWORD=1, OPERATOR=2, COMMENT=3, INTLITERAL=4, FLOATLITERAL=5, STRINGLITERAL=6, 
		IDENTIFIER=7, NAME=8, WS=9;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"KEYWORD", "OPERATOR", "COMMENT", "INTLITERAL", "FLOATLITERAL", "STRINGLITERAL", 
		"IDENTIFIER", "NAME", "WS", "WINEOL", "UNIEOL", "DIGIT"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "KEYWORD", "OPERATOR", "COMMENT", "INTLITERAL", "FLOATLITERAL", 
		"STRINGLITERAL", "IDENTIFIER", "NAME", "WS"
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


	public MicroLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MicroLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\13\u00c6\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2y\n\2\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u0085\n\3\3\4\3\4\3\4\3\4\7\4\u008b\n\4"+
		"\f\4\16\4\u008e\13\4\3\4\3\4\5\4\u0092\n\4\3\4\3\4\3\5\6\5\u0097\n\5\r"+
		"\5\16\5\u0098\3\6\7\6\u009c\n\6\f\6\16\6\u009f\13\6\3\6\3\6\6\6\u00a3"+
		"\n\6\r\6\16\6\u00a4\3\7\3\7\7\7\u00a9\n\7\f\7\16\7\u00ac\13\7\3\7\3\7"+
		"\3\b\3\b\3\t\3\t\7\t\u00b4\n\t\f\t\16\t\u00b7\13\t\3\n\6\n\u00ba\n\n\r"+
		"\n\16\n\u00bb\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\u008c\2\16\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\2\27\2\31\2\3\2\b\6\2,-//\61"+
		"\61??\6\2*+..=>@@\3\2$$\4\2C\\c|\5\2\62;C\\c|\5\2\13\f\17\17\"\"\u00e0"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\3x\3\2\2\2\5\u0084\3\2\2\2"+
		"\7\u0086\3\2\2\2\t\u0096\3\2\2\2\13\u009d\3\2\2\2\r\u00a6\3\2\2\2\17\u00af"+
		"\3\2\2\2\21\u00b1\3\2\2\2\23\u00b9\3\2\2\2\25\u00bf\3\2\2\2\27\u00c2\3"+
		"\2\2\2\31\u00c4\3\2\2\2\33\34\7R\2\2\34\35\7T\2\2\35\36\7Q\2\2\36\37\7"+
		"I\2\2\37 \7T\2\2 !\7C\2\2!y\7O\2\2\"#\7D\2\2#$\7G\2\2$%\7I\2\2%&\7K\2"+
		"\2&y\7P\2\2\'(\7G\2\2()\7P\2\2)y\7F\2\2*+\7H\2\2+,\7W\2\2,-\7P\2\2-.\7"+
		"E\2\2./\7V\2\2/\60\7K\2\2\60\61\7Q\2\2\61y\7P\2\2\62\63\7T\2\2\63\64\7"+
		"G\2\2\64\65\7C\2\2\65y\7F\2\2\66\67\7Y\2\2\678\7T\2\289\7K\2\29:\7V\2"+
		"\2:y\7G\2\2;<\7K\2\2<y\7H\2\2=>\7G\2\2>?\7N\2\2?@\7U\2\2@y\7G\2\2AB\7"+
		"G\2\2BC\7P\2\2CD\7F\2\2DE\7K\2\2Ey\7H\2\2FG\7Y\2\2GH\7J\2\2HI\7K\2\2I"+
		"J\7N\2\2Jy\7G\2\2KL\7G\2\2LM\7P\2\2MN\7F\2\2NO\7Y\2\2OP\7J\2\2PQ\7K\2"+
		"\2QR\7N\2\2Ry\7G\2\2ST\7E\2\2TU\7Q\2\2UV\7P\2\2VW\7V\2\2WX\7K\2\2XY\7"+
		"P\2\2YZ\7W\2\2Zy\7G\2\2[\\\7D\2\2\\]\7T\2\2]^\7G\2\2^_\7C\2\2_y\7M\2\2"+
		"`a\7T\2\2ab\7G\2\2bc\7V\2\2cd\7W\2\2de\7T\2\2ey\7P\2\2fg\7K\2\2gh\7P\2"+
		"\2hy\7V\2\2ij\7X\2\2jk\7Q\2\2kl\7K\2\2ly\7F\2\2mn\7U\2\2no\7V\2\2op\7"+
		"T\2\2pq\7K\2\2qr\7P\2\2ry\7I\2\2st\7H\2\2tu\7N\2\2uv\7Q\2\2vw\7C\2\2w"+
		"y\7V\2\2x\33\3\2\2\2x\"\3\2\2\2x\'\3\2\2\2x*\3\2\2\2x\62\3\2\2\2x\66\3"+
		"\2\2\2x;\3\2\2\2x=\3\2\2\2xA\3\2\2\2xF\3\2\2\2xK\3\2\2\2xS\3\2\2\2x[\3"+
		"\2\2\2x`\3\2\2\2xf\3\2\2\2xi\3\2\2\2xm\3\2\2\2xs\3\2\2\2y\4\3\2\2\2z{"+
		"\7<\2\2{\u0085\7?\2\2|\u0085\t\2\2\2}~\7#\2\2~\u0085\7?\2\2\177\u0085"+
		"\t\3\2\2\u0080\u0081\7>\2\2\u0081\u0085\7?\2\2\u0082\u0083\7@\2\2\u0083"+
		"\u0085\7?\2\2\u0084z\3\2\2\2\u0084|\3\2\2\2\u0084}\3\2\2\2\u0084\177\3"+
		"\2\2\2\u0084\u0080\3\2\2\2\u0084\u0082\3\2\2\2\u0085\6\3\2\2\2\u0086\u0087"+
		"\7/\2\2\u0087\u0088\7/\2\2\u0088\u008c\3\2\2\2\u0089\u008b\13\2\2\2\u008a"+
		"\u0089\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008d\3\2\2\2\u008c\u008a\3\2"+
		"\2\2\u008d\u0091\3\2\2\2\u008e\u008c\3\2\2\2\u008f\u0092\5\25\13\2\u0090"+
		"\u0092\5\27\f\2\u0091\u008f\3\2\2\2\u0091\u0090\3\2\2\2\u0092\u0093\3"+
		"\2\2\2\u0093\u0094\b\4\2\2\u0094\b\3\2\2\2\u0095\u0097\5\31\r\2\u0096"+
		"\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2"+
		"\2\2\u0099\n\3\2\2\2\u009a\u009c\5\31\r\2\u009b\u009a\3\2\2\2\u009c\u009f"+
		"\3\2\2\2\u009d\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u00a0\3\2\2\2\u009f"+
		"\u009d\3\2\2\2\u00a0\u00a2\7\60\2\2\u00a1\u00a3\5\31\r\2\u00a2\u00a1\3"+
		"\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5"+
		"\f\3\2\2\2\u00a6\u00aa\7$\2\2\u00a7\u00a9\n\4\2\2\u00a8\u00a7\3\2\2\2"+
		"\u00a9\u00ac\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ad"+
		"\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00ae\7$\2\2\u00ae\16\3\2\2\2\u00af"+
		"\u00b0\5\21\t\2\u00b0\20\3\2\2\2\u00b1\u00b5\t\5\2\2\u00b2\u00b4\t\6\2"+
		"\2\u00b3\u00b2\3\2\2\2\u00b4\u00b7\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b5\u00b6"+
		"\3\2\2\2\u00b6\22\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b8\u00ba\t\7\2\2\u00b9"+
		"\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bb\u00bc\3\2"+
		"\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00be\b\n\2\2\u00be\24\3\2\2\2\u00bf\u00c0"+
		"\7\17\2\2\u00c0\u00c1\7\f\2\2\u00c1\26\3\2\2\2\u00c2\u00c3\7\f\2\2\u00c3"+
		"\30\3\2\2\2\u00c4\u00c5\4\62;\2\u00c5\32\3\2\2\2\r\2x\u0084\u008c\u0091"+
		"\u0098\u009d\u00a4\u00aa\u00b5\u00bb\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}