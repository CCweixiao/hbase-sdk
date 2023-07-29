// Generated from HBaseSQL.g4 by ANTLR 4.5.1

package com.github.CCwexiao.hbase.sdk.dsl.antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HBaseSQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HBaseSQLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(HBaseSQLParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#createTableStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateTableStatement(HBaseSQLParser.CreateTableStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#tableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableName(HBaseSQLParser.TableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#columnFamily}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnFamily(HBaseSQLParser.ColumnFamilyContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#column}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumn(HBaseSQLParser.ColumnContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#funcname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncname(HBaseSQLParser.FuncnameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code colList_Star}
	 * labeled alternative in {@link HBaseSQLParser#selectColList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColList_Star(HBaseSQLParser.ColList_StarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code colList_ColList}
	 * labeled alternative in {@link HBaseSQLParser#selectColList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColList_ColList(HBaseSQLParser.ColList_ColListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#columnList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnList(HBaseSQLParser.ColumnListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#columnFamilyList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnFamilyList(HBaseSQLParser.ColumnFamilyListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#insertStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertStatement(HBaseSQLParser.InsertStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#multiValueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiValueList(HBaseSQLParser.MultiValueListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#valueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueList(HBaseSQLParser.ValueListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(HBaseSQLParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#selectStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectStatement(HBaseSQLParser.SelectStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#deleteStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteStatement(HBaseSQLParser.DeleteStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rowkeyrange_startAndEnd}
	 * labeled alternative in {@link HBaseSQLParser#rowKeyRangeExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowkeyrange_startAndEnd(HBaseSQLParser.Rowkeyrange_startAndEndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rowkeyrange_start}
	 * labeled alternative in {@link HBaseSQLParser#rowKeyRangeExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowkeyrange_start(HBaseSQLParser.Rowkeyrange_startContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rowkeyrange_end}
	 * labeled alternative in {@link HBaseSQLParser#rowKeyRangeExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowkeyrange_end(HBaseSQLParser.Rowkeyrange_endContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rowkeyrange_onerowkey}
	 * labeled alternative in {@link HBaseSQLParser#rowKeyRangeExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowkeyrange_onerowkey(HBaseSQLParser.Rowkeyrange_onerowkeyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rowkeyrange_insomekeys}
	 * labeled alternative in {@link HBaseSQLParser#rowKeyRangeExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowkeyrange_insomekeys(HBaseSQLParser.Rowkeyrange_insomekeysContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rowkeyrange_prefix}
	 * labeled alternative in {@link HBaseSQLParser#rowKeyRangeExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowkeyrange_prefix(HBaseSQLParser.Rowkeyrange_prefixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rowkey_Wrapper}
	 * labeled alternative in {@link HBaseSQLParser#rowKeyExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowkey_Wrapper(HBaseSQLParser.Rowkey_WrapperContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rowkey_Constant}
	 * labeled alternative in {@link HBaseSQLParser#rowKeyExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowkey_Constant(HBaseSQLParser.Rowkey_ConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rowkey_FuncConstant}
	 * labeled alternative in {@link HBaseSQLParser#rowKeyExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowkey_FuncConstant(HBaseSQLParser.Rowkey_FuncConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#funcParamsList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncParamsList(HBaseSQLParser.FuncParamsListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#funcCol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCol(HBaseSQLParser.FuncColContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#gtOper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGtOper(HBaseSQLParser.GtOperContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#leOper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeOper(HBaseSQLParser.LeOperContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tsrange_startAndEnd}
	 * labeled alternative in {@link HBaseSQLParser#tsRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTsrange_startAndEnd(HBaseSQLParser.Tsrange_startAndEndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tsrange_start}
	 * labeled alternative in {@link HBaseSQLParser#tsRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTsrange_start(HBaseSQLParser.Tsrange_startContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tsrange_end}
	 * labeled alternative in {@link HBaseSQLParser#tsRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTsrange_end(HBaseSQLParser.Tsrange_endContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tsequal}
	 * labeled alternative in {@link HBaseSQLParser#tsRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTsequal(HBaseSQLParser.TsequalContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(HBaseSQLParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#constantList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantList(HBaseSQLParser.ConstantListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(HBaseSQLParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#varList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarList(HBaseSQLParser.VarListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#multiVersionExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiVersionExp(HBaseSQLParser.MultiVersionExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#maxVersionExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMaxVersionExp(HBaseSQLParser.MaxVersionExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#integer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(HBaseSQLParser.IntegerContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#tsExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTsExp(HBaseSQLParser.TsExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#timestamp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimestamp(HBaseSQLParser.TimestampContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#wherec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWherec(HBaseSQLParser.WherecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notequalvar}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotequalvar(HBaseSQLParser.NotequalvarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lessequalvar}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessequalvar(HBaseSQLParser.LessequalvarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notbetweenvar}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotbetweenvar(HBaseSQLParser.NotbetweenvarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andcondition}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndcondition(HBaseSQLParser.AndconditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code greaterequalconstant}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterequalconstant(HBaseSQLParser.GreaterequalconstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalvar}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualvar(HBaseSQLParser.EqualvarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code greaterconstant}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterconstant(HBaseSQLParser.GreaterconstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code betweenvar}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBetweenvar(HBaseSQLParser.BetweenvarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code betweenconstant}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBetweenconstant(HBaseSQLParser.BetweenconstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isnotnullc}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsnotnullc(HBaseSQLParser.IsnotnullcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inconstantlist}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInconstantlist(HBaseSQLParser.InconstantlistContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notbetweenconstant}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotbetweenconstant(HBaseSQLParser.NotbetweenconstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notinconstantlist}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotinconstantlist(HBaseSQLParser.NotinconstantlistContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orcondition}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrcondition(HBaseSQLParser.OrconditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isnullc}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsnullc(HBaseSQLParser.IsnullcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalconstant}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualconstant(HBaseSQLParser.EqualconstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code greaterequalvar}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterequalvar(HBaseSQLParser.GreaterequalvarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lessvar}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessvar(HBaseSQLParser.LessvarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notequalconstant}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotequalconstant(HBaseSQLParser.NotequalconstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code invarlist}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvarlist(HBaseSQLParser.InvarlistContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lessconstant}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessconstant(HBaseSQLParser.LessconstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code conditionwrapper}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionwrapper(HBaseSQLParser.ConditionwrapperContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notinvarlist}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotinvarlist(HBaseSQLParser.NotinvarlistContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lessequalconstant}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessequalconstant(HBaseSQLParser.LessequalconstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code greatervar}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreatervar(HBaseSQLParser.GreatervarContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#limitExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitExp(HBaseSQLParser.LimitExpContext ctx);
}