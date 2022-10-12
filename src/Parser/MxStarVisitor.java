// Generated from /Users/weijie/Desktop/Compiler2022/src/Parser/MxStar.g4 by ANTLR 4.10.1
package Parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxStarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxStarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxStarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MxStarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#basicType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicType(MxStarParser.BasicTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#typeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeName(MxStarParser.TypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#variableType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableType(MxStarParser.VariableTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#newVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewVar(MxStarParser.NewVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#functionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionType(MxStarParser.FunctionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(MxStarParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#variableDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDef(MxStarParser.VariableDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(MxStarParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#functionParameterDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParameterDef(MxStarParser.FunctionParameterDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#functionDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDef(MxStarParser.FunctionDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#classConstructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassConstructor(MxStarParser.ClassConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(MxStarParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#statementBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementBlock(MxStarParser.StatementBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(MxStarParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(MxStarParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#forCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForCondition(MxStarParser.ForConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#stepping}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStepping(MxStarParser.SteppingContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(MxStarParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(MxStarParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(MxStarParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(MxStarParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(MxStarParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(MxStarParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitControlStatement(MxStarParser.ControlStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MxStarParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#globalvariableDefStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalvariableDefStatement(MxStarParser.GlobalvariableDefStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#variableDefStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDefStatement(MxStarParser.VariableDefStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#lambdaStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaStatement(MxStarParser.LambdaStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#atomExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpression(MxStarParser.AtomExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(MxStarParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpr(MxStarParser.NewExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ororExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrorExpr(MxStarParser.OrorExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dotExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotExpr(MxStarParser.DotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(MxStarParser.ArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bracketExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketExpr(MxStarParser.BracketExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mutiExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMutiExpr(MxStarParser.MutiExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpr(MxStarParser.AtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(MxStarParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code minusExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinusExpr(MxStarParser.MinusExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andandExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndandExpr(MxStarParser.AndandExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selfplusExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelfplusExpr(MxStarParser.SelfplusExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(MxStarParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plusselfExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusselfExpr(MxStarParser.PlusselfExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code caretExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaretExpr(MxStarParser.CaretExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lambdaExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpr(MxStarParser.LambdaExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tidleExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTidleExpr(MxStarParser.TidleExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plusminusExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusminusExpr(MxStarParser.PlusminusExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code listExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListExpr(MxStarParser.ListExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(MxStarParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compareExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareExpr(MxStarParser.CompareExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualExpr(MxStarParser.EqualExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(MxStarParser.AndExprContext ctx);
}