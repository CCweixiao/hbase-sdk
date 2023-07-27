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
	 * Visit a parse tree produced by the {@code insertHqlCl}
	 * labeled alternative in {@link HBaseSQLParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertHqlCl(HBaseSQLParser.InsertHqlClContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selectHqlCl}
	 * labeled alternative in {@link HBaseSQLParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectHqlCl(HBaseSQLParser.SelectHqlClContext ctx);
	/**
	 * Visit a parse tree produced by the {@code deleteHqlCl}
	 * labeled alternative in {@link HBaseSQLParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteHqlCl(HBaseSQLParser.DeleteHqlClContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#inserthqlc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInserthqlc(HBaseSQLParser.InserthqlcContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#selecthqlc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelecthqlc(HBaseSQLParser.SelecthqlcContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#deletehqlc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeletehqlc(HBaseSQLParser.DeletehqlcContext ctx);
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
	 * Visit a parse tree produced by the {@code notmatchvar}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotmatchvar(HBaseSQLParser.NotmatchvarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notbetweenvar}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotbetweenvar(HBaseSQLParser.NotbetweenvarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ismissingc}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsmissingc(HBaseSQLParser.IsmissingcContext ctx);
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
	 * Visit a parse tree produced by the {@code notmatchconstant}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotmatchconstant(HBaseSQLParser.NotmatchconstantContext ctx);
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
	 * Visit a parse tree produced by the {@code isnotmissingc}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsnotmissingc(HBaseSQLParser.IsnotmissingcContext ctx);
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
	 * Visit a parse tree produced by the {@code matchvar}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatchvar(HBaseSQLParser.MatchvarContext ctx);
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
	 * Visit a parse tree produced by the {@code matchconstant}
	 * labeled alternative in {@link HBaseSQLParser#conditionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatchconstant(HBaseSQLParser.MatchconstantContext ctx);
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
	 * Visit a parse tree produced by {@link HBaseSQLParser#tsExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTsExp(HBaseSQLParser.TsExpContext ctx);
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
	 * Visit a parse tree produced by {@link HBaseSQLParser#colList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColList(HBaseSQLParser.ColListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#col}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCol(HBaseSQLParser.ColContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#funcname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncname(HBaseSQLParser.FuncnameContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#constantList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantList(HBaseSQLParser.ConstantListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#insertValueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertValueList(HBaseSQLParser.InsertValueListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code insertValue_NotNull}
	 * labeled alternative in {@link HBaseSQLParser#insertValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertValue_NotNull(HBaseSQLParser.InsertValue_NotNullContext ctx);
	/**
	 * Visit a parse tree produced by the {@code insertValue_Null}
	 * labeled alternative in {@link HBaseSQLParser#insertValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertValue_Null(HBaseSQLParser.InsertValue_NullContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#maxVersionExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMaxVersionExp(HBaseSQLParser.MaxVersionExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#limitExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitExp(HBaseSQLParser.LimitExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#tableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableName(HBaseSQLParser.TableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#maxversion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMaxversion(HBaseSQLParser.MaxversionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(HBaseSQLParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#timestamp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimestamp(HBaseSQLParser.TimestampContext ctx);
	/**
	 * Visit a parse tree produced by {@link HBaseSQLParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(HBaseSQLParser.VarContext ctx);
}