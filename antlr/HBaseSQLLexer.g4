lexer grammar HBaseSQLLexer;

options { caseInsensitive = true; }

channels { HBASESQLCOMMENT, ERRORCHANNEL }

// SKIP

SPACE:                               [ \t\r\n]+ -> channel(HIDDEN);
SPEC_HBASE_SQL_COMMENT:                  '/*!' .+? '*/' -> channel(HBASESQLCOMMENT);
COMMENT_INPUT:                       '/*' .*? '*/' -> channel(HIDDEN);
LINE_COMMENT:                        (
                                       ('--' [ \t]* | '#') ~[\r\n]* ('\r'? '\n' | EOF)
                                       | '--' ('\r'? '\n' | EOF)
                                     ) -> channel(HIDDEN);

// Keywords

// Constructors symbols
LR_BRACKET:                          '(';
RR_BRACKET:                          ')';
COMMA:                               ',';
SEMICOLON:                           ';';

// Operators
EQ : '=';
NOTEQ : '!=';
NOT_EQ : '<>';
GREATER : '>';
GREATEREQUAL : '>=';
LESS : '<';
LESSEQUAL : '<=';
PLUS : '+';
MINUS : '-';
ASTERISK : '*';
SLASH : '/';
MOD : '%';

// KeyWords

IS : I S;
NOTMATCH : N O T M A T C H ;
MATCH : M A T C H ;
BETWEEN : B E T W E E N ;
MISSING : M I S S I N G ;
LIMIT : L I M I T ;
TS : T S ;
STARTTS : S T A R T T S ;
ENDTS : E N D T S;
CREATE : C R E A T E;
INSERT : I N S E R T ;
INTO   : I N T O ;
VALUES : V A L U E S ;
SELECT : S E L E C T ;
UPDATE : U P D A T E ;
DELETE : D E L E T E ;
FROM   : F R O M;
TABLE : T A B L E;
COLUMNFAMILY : C O L U M N F A M I L Y;
WHERE : W H E R E ;
AND : A N D ;
OR : O R ;
IN : I N ;
LIKE : L I K E ;
NULL : N U L L;
NOT : N O T ;
INTEGER : I N T E G E R;
ROWKEY   : R O W K E Y ;
STARTKEY : S T A R T K E Y ;
ENDKEY   : E N D K E Y ;
MAXVERSION    : M A X V E R S I O N ;

fragment A      : [a][A];
fragment B      : [b][B];
fragment C      : [c][C];
fragment D      : [d][D];
fragment E      : [e][E];
fragment F      : [f][F];
fragment G      : [g][G];
fragment H      : [h][H];
fragment I      : [i][I];
fragment J      : [j][J];
fragment K      : [k][K];
fragment L      : [l][L];
fragment M      : [m][M];
fragment N      : [n][N];
fragment O      : [o][O];
fragment P      : [p][P];
fragment Q      : [q][Q];
fragment R      : [r][R];
fragment S      : [s][S];
fragment T      : [t][T];
fragment U      : [u][U];
fragment V      : [v][V];
fragment W      : [w][W];
fragment X      : [x][X];
fragment Y      : [y][Y];
fragment Z      : [z][Z];



ID : [a-zA-Z0-9_:]+;
INT : [0-9]+;
FLOAT : [0-9]+ '.' [0-9]*;
STRING : '\'' (~[\\'\n\r])* '\'';
BOOLEAN : 'true' | 'false';

// Last tokens must generate Errors

ERROR_RECONGNIGION:                  .    -> channel(ERRORCHANNEL);