grammar HBaseSQL;

@header {
package com.github.CCwexiao.hbase.sdk.dsl.antlr;
}

// Keywords

// Constructors symbols
LR_BRACKET:                          '(';
RR_BRACKET:                          ')';
COMMA:                               ',';
SEMICOLON:                           ';';

// Operators
EQ : '=';
NOTEQ : '!=';
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
VIRTUAL : V I R T U A L;
TABLE : T A B L E;
WITH : W I T H ;
PROPERTIES : P R O P E R T I E S;
NULLABLE : N U L L A B L E;
INSERT : I N S E R T ;
INTO   : I N T O ;
VALUES : V A L U E S ;
SELECT : S E L E C T ;
UPDATE : U P D A T E ;
SET : S E T ;
DELETE : D E L E T E ;
FROM   : F R O M;
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
ISROWKEY   : I S R O W K E Y ;
STARTKEY : S T A R T K E Y ;
ENDKEY   : E N D K E Y ;
MAXVERSION    : M A X V E R S I O N ;

fragment A      : [aA];
fragment B      : [bB];
fragment C      : [cC];
fragment D      : [dD];
fragment E      : [eE];
fragment F      : [fF];
fragment G      : [gG];
fragment H      : [hH];
fragment I      : [iI];
fragment J      : [jJ];
fragment K      : [kK];
fragment L      : [lL];
fragment M      : [mM];
fragment N      : [nN];
fragment O      : [oO];
fragment P      : [pP];
fragment Q      : [qQ];
fragment R      : [rR];
fragment S      : [sS];
fragment T      : [tT];
fragment U      : [uU];
fragment V      : [vV];
fragment W      : [wW];
fragment X      : [xX];
fragment Y      : [yY];
fragment Z      : [zZ];

ID : [a-zA-Z0-9_.:]+;
STRING : DOT_L STR DOT_R;

fragment STR : ([a-zA-Z0-9\u0020\u0080-\uFFFF_:*-+.,\\|=&^%$#@{}[\]!~`()<>\r\t\n"])*;
fragment DOT_L : '\'';
fragment DOT_R : '\'';


/* parser rules */
query : createTableStatement
      | insertStatement
      | selectStatement
      | deleteStatement
      ;

createTableStatement : CREATE VIRTUAL TABLE tableName '(' fields ')' (WITH PROPERTIES '(' properties ')')? ';'?;

tableName : ID;
columnFamily : ID;

fields : field (',' field)* ;
field : fieldName fieldType ISROWKEY? NULLABLE? ;
fieldName : ID ;
fieldType : ID ;
properties : keyValue (',' keyValue)* ;
keyValue : ID '=' ID ;

column : ID;
funcname : ID;

selectColList : '*'          # colList_Star
			  | columnList   # colList_ColList
	     	  ;
columnList : column (COMMA column)*;
columnFamilyList : columnFamily (COMMA columnFamily)*;

insertStatement : INSERT INTO tableName LR_BRACKET columnList RR_BRACKET VALUES multiValueList (WHERE TS EQ tsExp)?;



multiValueList : LR_BRACKET valueList RR_BRACKET (COMMA LR_BRACKET valueList RR_BRACKET)*;
valueList: value ( COMMA value ) *;
value : (STRING | ID | NULL | ROWKEY)+;


selectStatement : SELECT selectColList FROM tableName WHERE rowKeyRangeExp (AND wherec)? (AND multiVersionExp)? limitExp?;

deleteStatement : DELETE selectColList FROM tableName WHERE rowKeyRangeExp (AND wherec)? (AND TS EQ tsExp)?;

rowKeyRangeExp :  STARTKEY gtOper rowKeyExp AND ENDKEY leOper rowKeyExp            # rowkeyrange_startAndEnd
                | STARTKEY gtOper rowKeyExp                                          # rowkeyrange_start
                | ENDKEY leOper rowKeyExp		                                     # rowkeyrange_end
                | ROWKEY EQ rowKeyExp 			                                 # rowkeyrange_onerowkey
                | ROWKEY IN LR_BRACKET rowKeyExp (COMMA rowKeyExp)* RR_BRACKET   # rowkeyrange_insomekeys
                | ROWKEY LIKE rowKeyExp                                          # rowkeyrange_prefix
                ;

rowKeyExp :  LR_BRACKET rowKeyExp RR_BRACKET                              # rowkey_Wrapper
	| value                                                               # rowkey_Constant
	| funcname funcParamsList                                             # rowkey_FuncConstant
    ;

funcParamsList  : LR_BRACKET funcCol ( COMMA funcCol )* RR_BRACKET;
funcCol: '`' value '`' | '\'' value '\'' | value | '\'' '\'' | ;

gtOper: GREATER | GREATEREQUAL;
leOper: LESS | LESSEQUAL;

tsRange : LR_BRACKET STARTTS gtOper tsExp COMMA ENDTS leOper tsExp RR_BRACKET      # tsrange_startAndEnd
		| STARTTS gtOper tsExp                                                 # tsrange_start
		| ENDTS leOper tsExp                                                   # tsrange_end
		| TS EQ tsExp                                                      # tsequal
	    ;


constant: '\'' value '\'' | value | '\'' NULL '\'' | '\'' '\'';
constantList  : LR_BRACKET constant ( ',' constant )* RR_BRACKET ;
var : '${' ID '}' ;
varList  : LR_BRACKET var ( ',' var )* RR_BRACKET ;

multiVersionExp: maxVersionExp
                 | tsRange;

maxVersionExp : MAXVERSION EQ integer
			  ;
integer : ID ;

tsExp: timestamp ;
timestamp: ID;

wherec: conditionc;
conditionc : LR_BRACKET conditionc RR_BRACKET              # conditionwrapper
	| conditionc AND conditionc            # andcondition
	| conditionc OR conditionc             # orcondition
	| column EQ constant                      # equalconstant
	| column EQ var                           # equalvar
	| column NOTEQ constant                   # notequalconstant
    | column NOTEQ var                        # notequalvar
	| column LESS constant                    # lessconstant
	| column LESS var                         # lessvar
	| column GREATER constant                 # greaterconstant
	| column GREATER var                      # greatervar
	| column LESSEQUAL constant               # lessequalconstant
	| column LESSEQUAL var                    # lessequalvar
	| column GREATEREQUAL constant            # greaterequalconstant
	| column GREATEREQUAL var                 # greaterequalvar
	| column IN constantList                  # inconstantlist
	| column IN varList                       # invarlist
	| column NOT IN constantList              # notinconstantlist
	| column NOT IN varList                   # notinvarlist
	| column BETWEEN constant AND constant    # betweenconstant
	| column BETWEEN var AND var              # betweenvar
	| column NOT BETWEEN constant AND constant # notbetweenconstant
	| column NOT BETWEEN var AND var           # notbetweenvar
	| column IS NULL                           # isnullc
	| column IS NOT NULL                        # isnotnullc
	;

limitExp : LIMIT integer
		 ;

/* skipped rules */
SPACE:                               [ \t\r\n]+    -> channel(HIDDEN);
// WS : [ \t\r\n]+ -> skip;
COMMENT_INPUT:                       '/*' .*? '*/' -> channel(HIDDEN);
LINE_COMMENT:                        (
                                       ('--' [ \t]* | '#') ~[\r\n]* ('\r'? '\n' | EOF)
                                       | '--' ('\r'? '\n' | EOF)
                                     ) -> channel(HIDDEN);