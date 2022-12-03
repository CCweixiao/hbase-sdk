grammar HBaseSQL;

@header {
package com.github.CCwexiao.hbase.sdk.dsl.antlr;
}

prog   : inserthqlc         # insertHqlCl
	   | selecthqlc         # selectHqlCl
	   | deletehqlc         # deleteHqlCl
       ;


inserthqlc : INSERT INTO tableName LB colList RB VALUES insertValueList WHERE ROWKEY EQ rowKeyExp (AND TS EQ tsExp )  ?
		   ;

selecthqlc : SELECT selectColList FROM tableName WHERE rowKeyRangeExp (AND wherec)? (AND maxVersionExp)? (AND tsRange)? limitExp?
	       ;

deletehqlc : DELETE selectColList FROM tableName WHERE rowKeyRangeExp (AND wherec)? (AND TS EQ tsExp ) ?
	       ;

wherec: conditionc;

conditionc : LB conditionc RB              # conditionwrapper
	| conditionc AND conditionc            # andcondition
	| conditionc OR conditionc             # orcondition
	| col EQ constant                      # equalconstant
	| col EQ var                           # equalvar
	| col LESS constant                    # lessconstant
	| col LESS var                         # lessvar
	| col GREATER constant                 # greaterconstant
	| col GREATER var                      # greatervar
	| col LESSEQUAL constant               # lessequalconstant
	| col LESSEQUAL var                    # lessequalvar
	| col GREATEREQUAL constant            # greaterequalconstant
	| col GREATEREQUAL var                 # greaterequalvar
	| col NOTEQ constant                   # notequalconstant
	| col NOTEQ var                        # notequalvar
	| col NOTMATCH constant                # notmatchconstant
	| col NOTMATCH var                     # notmatchvar
	| col MATCH constant                   # matchconstant
	| col MATCH var                        # matchvar
	| col IN constantList                  # inconstantlist
	| col IN var                           # invarlist
	| col NOT IN constantList               # notinconstantlist
	| col NOT IN var                        # notinvarlist
	| col BETWEEN constant AND constant    # betweenconstant
	| col BETWEEN var AND var              # betweenvar
	| col NOT BETWEEN constant AND constant # notbetweenconstant
	| col NOT BETWEEN var AND var           # notbetweenvar
	| col IS NULL                           # isnullc
	| col IS NOT NULL                        # isnotnullc
	| col IS MISSING                        # ismissingc
	| col IS NOT MISSING                     # isnotmissingc
	;

rowKeyRangeExp : LB STARTKEY EQ rowKeyExp COMMA_CHAR ENDKEY EQ rowKeyExp RB   # rowkeyrange_startAndEnd
                | STARTKEY EQ rowKeyExp                                       # rowkeyrange_start
                | ENDKEY EQ rowKeyExp		                                  # rowkeyrange_end
                | ROWKEY EQ rowKeyExp 			                              # rowkeyrange_onerowkey
                | ROWKEY IN rowKeyExp                                         # rowkeyrange_insomekeys
                | ROWKEY LIKE rowKeyExp                                       # rowkeyrange_prefix
                ;

rowKeyExp :  LB rowKeyExp RB                              # rowkey_Wrapper
	| constant                                            # rowkey_Constant
	| LB constant ( ',' constant )* RB                    # rowkey_inRangeKey
	| funcname LB constant RB                             # rowkey_FuncConstant
    | LB rowKeyExp ( ',' rowKeyExp )* RB                  # rowkey_inRangeFuncKey
    ;

tsRange : LB STARTTS EQ tsExp COMMA_CHAR ENDTS EQ tsExp RB      # tsrange_startAndEnd
		| STARTTS EQ tsExp                                      # tsrange_start
		| ENDTS EQ tsExp                                        # tsrange_end
		| TS EQ tsExp                                           # tsequal
	    ;

tsExp: timestamp ;


selectColList : '*'       # colList_Star
			  | colList   # colList_ColList
	     	  ;

colList :  col ( ',' col )* ;
col : STRING ;

funcname: STRING ;

constantList  : LB constant ( ',' constant )* RB ;
insertValueList : LB insertValue ( ',' insertValue )* RB ;

insertValue: constant                # insertValue_NotNull
            | NULL                   # insertValue_Null
		    ;

maxVersionExp : MAXVERSION EQ maxversion
			  ;

limitExp : LIMIT STRING
		 ;

tableName : STRING ;
maxversion : STRING ;
constant: '\'' STRING '\'' | STRING;
timestamp: STRING;
var : '#' STRING '#' ;

LB : '(' ;
RB : ')' ;
COMMA_CHAR: ',';

SELECT : S E L E C T ;
INSERT : I N S E R T ;
DELETE : D E L E T E ;
INTO   : I N T O ;
VALUES : V A L U E S ;
WHERE : W H E R E ;
FROM   : F R O M;

ROWKEY   : R O W K E Y ;
STARTKEY : S T A R T K E Y ;
ENDKEY   : E N D K E Y ;
MAXVERSION    : M A X V E R S I O N ;

LIMIT : L I M I T ;

// 过滤时间戳
TS : T S ;
STARTTS : S T A R T T S ;
ENDTS : E N D T S;

IS : I S;
EQ : '=';
NOTEQ : '!=';
NULL : N U L L;
NOT : N O T ;

AND : A N D ;
OR : O R ;


LESSEQUAL : '<=' ;
LESS : '<' ;

GREATEREQUAL : '>=';
GREATER: '>' ;

NOTMATCH : N O T M A T C H ;
MATCH : M A T C H ;

IN : I N ;

LIKE : L I K E ;

BETWEEN : B E T W E E N ;

MISSING : M I S S I N G ;


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

STRING :  [a-zA-Z0-9_:*-+.,\\|=&^%$#@!~`()<>\r\t\n"]+ ;


SPACE:                               ( '\t' | ' ' | '\r' | '\n' )+ -> channel(HIDDEN);
COMMENT_INPUT:                       '/*' .*? '*/' -> channel(HIDDEN);
LINE_COMMENT:                        (
                                       ('-- ' | '#') ~[\r\n]* ('\r'? '\n' | EOF)
                                       | '--' ('\r'? '\n' | EOF)
                                     ) -> channel(HIDDEN);