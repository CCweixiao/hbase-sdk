grammar HBaseSQLV2;

@header {
package com.github.CCwexiao.hbase.sdk.dsl.antlr;
}

prog   : inserthqlc         # insertHqlCl
	   | selecthqlc         # selectHqlCl
	   | deletehqlc         # deleteHqlCl
       ;


inserthqlc : INSERT INTO tableName LB cidList RB VALUES insertValueList WHERE ROWKEY IS rowKeyExp ( TS IS tsexp )  ?
		   ;

selecthqlc : SELECT selectCidList FROM tableName WHERE rowKeyRange? wherec? maxVersionExp? tsrange? limitExp?
	       ;

deletehqlc : DELETE selectCidList FROM tableName WHERE rowKeyRange? wherec? ( TS IS tsexp ) ?
	       ;

wherec: conditionc;

conditionc : LB conditionc RB              # conditionwrapper
	| conditionc AND conditionc            # andcondition
	| conditionc OR conditionc             # orcondition
	| cid EQUAL constant                   # equalconstant
	| cid EQUAL var                        # equalvar
	| cid LESS constant                    # lessconstant
	| cid LESS var                         # lessvar
	| cid GREATER constant                 # greaterconstant
	| cid GREATER var                      # greatervar
	| cid LESSEQUAL constant               # lessequalconstant
	| cid LESSEQUAL var                    # lessequalvar
	| cid GREATEREQUAL constant            # greaterequalconstant
	| cid GREATEREQUAL var                 # greaterequalvar
	| cid NOTEQUAL constant                # notequalconstant
	| cid NOTEQUAL var                     # notequalvar
	| cid NOTMATCH constant                # notmatchconstant
	| cid NOTMATCH var                     # notmatchvar
	| cid MATCH constant                   # matchconstant
	| cid MATCH var                        # matchvar
	| cid IN constantList                  # inconstantlist
	| cid IN var                           # invarlist
	| cid NOTIN constantList               # notinconstantlist
	| cid NOTIN var                        # notinvarlist
	| cid BETWEEN constant AND constant    # betweenconstant
	| cid BETWEEN var AND var              # betweenvar
	| cid NOTBETWEEN constant AND constant # notbetweenconstant
	| cid NOTBETWEEN var AND var           # notbetweenvar
	| cid ISNULL                           # isnullc
	| cid ISNOTNULL                        # isnotnullc
	| cid ISMISSING                        # ismissingc
	| cid ISNOTMISSING                     # isnotmissingc
	;

rowKeyRange : STARTKEY IS rowKeyExp ',' ENDKEY IS rowKeyExp   # rowkeyrange_startAndEnd
			| STARTKEY IS rowKeyExp                           # rowkeyrange_start
            | ENDKEY IS rowKeyExp		                      # rowkeyrange_end
            | ROWKEY IS rowKeyExp 			                  # rowkeyrange_onerowkey
            | ROWKEY IS rowKeyExp                             # rowkeyrange_insomekeys
			;

rowKeyExp : LB rowKeyExp RB                               # rowkey_Wrapper
	| funcname LB constant RB                             # rowkey_FuncConstant
	| funcname IN LB constant ( ',' constant)* RB         # rowkey_inRangeKey
	| HBASESTARTKEY                                       # rowkey_hbasestart
	| HBASEENDKEY                                         # rowkey_hbaseend
    ;

tsrange : LB STARTTS IS tsexp ',' ENDTS IS tsexp RB                  # tsrange_startAndEnd
		| LB STARTTS IS tsexp RB                                     # tsrange_start
		| LB ENDTS IS tsexp RB                                       # tsrange_end
	    ;

tsexp: constant ;


selectCidList : cidList   # cidList_CidList
			  | STAR      # cidList_Star
			  | TEXT      # cidList_Regx
	     	  ;

cidList :  cid (',' cid)* ;
cid : TEXT ;

funcname: TEXT ;

constantList  : LB constant ( ',' constant)* RB ;
insertValueList : LB insertValue ( ',' insertValue)* RB ;

insertValue: constant                # insertValue_NotNull
            | NULL                   # insertValue_Null
		    ;

maxVersionExp : LB MAXVERSION IS maxversion RB
			  ;

limitExp : LIMIT TEXT ( ',' TEXT)?
		 ;

tableName : TEXT ;
maxversion : TEXT ;
constant: '\'' TEXT '\'';
var : '#' TEXT '#' ;


STAR : '*' ;

LB : '(' ;
RB : ')' ;

WHERE : 'where' | 'WHERE' ;

SELECT : 'select' | 'SELECT' ;
INSERT : 'insert' | 'INSERT' ;
DELETE : 'delete' | 'DELETE' ;
INTO   : 'into' | 'INTO' ;
VALUES : 'values' | 'VALUES' ;
FROM   : 'from' | 'FROM' ;

ROWKEY   : 'rowkey' | 'rowKey' ;
STARTKEY : 'startkey' | 'startKey' ;
ENDKEY   : 'endkey' | 'endKey' ;
HBASESTARTKEY : 'hbasestartkey';
HBASEENDKEY   : 'hbaseendkey';
MAXVERSION    : 'maxversion' | 'maxVersion' ;

LIMIT : 'limit' | 'LIMIT' ;


TS : 'ts' ;
STARTTS : 'startTS' ;
ENDTS : 'endTS' ;

IS : 'is' | 'IS';
NULL : 'null';
NOT : 'not' | 'NOT' ;

AND : 'and' | 'AND' ;
OR : 'or' | 'OR' ;


LESSEQUAL : 'lessequal' ;
LESS : 'less' ;

GREATEREQUAL : 'greaterequal';
GREATER: 'greater' ;

NOTEQUAL : 'notequal' ;
EQUAL : 'equal' ;

ENDER : ';' ;

NOTMATCH : 'notmatch' ;
MATCH : 'match' ;

IN : 'in' ;
NOTIN : 'notin' ;

BETWEEN : 'between' ;
NOTBETWEEN : 'notbetween' ;

ISNULL : 'isnull' ;
ISNOTNULL : 'isnotnull' ;

ISMISSING : 'ismissing' ;
ISNOTMISSING : 'isnotmissing' ;

TEXT :  [a-zA-Z0-9_:*-+.,\\|=&^%$#@!~`()<>\r"]+ ;

SPACE:                               ( '\t' | ' ' | '\r' | '\n' )+ -> channel(HIDDEN);
COMMENT_INPUT:                       '/*' .*? '*/' -> channel(HIDDEN);
LINE_COMMENT:                        (
                                       ('-- ' | '#') ~[\r\n]* ('\r'? '\n' | EOF)
                                       | '--' ('\r'? '\n' | EOF)
                                     ) -> channel(HIDDEN);