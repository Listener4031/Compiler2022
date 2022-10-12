// Generated from /Users/weijie/Desktop/Compiler2022/src/Parser/MxStar.g4 by ANTLR 4.10.1
package Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxStarParser}.
 */
public interface MxStarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxStarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxStarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxStarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#basicType}.
	 * @param ctx the parse tree
	 */
	void enterBasicType(MxStarParser.BasicTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#basicType}.
	 * @param ctx the parse tree
	 */
	void exitBasicType(MxStarParser.BasicTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(MxStarParser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(MxStarParser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#variableType}.
	 * @param ctx the parse tree
	 */
	void enterVariableType(MxStarParser.VariableTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#variableType}.
	 * @param ctx the parse tree
	 */
	void exitVariableType(MxStarParser.VariableTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#newVar}.
	 * @param ctx the parse tree
	 */
	void enterNewVar(MxStarParser.NewVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#newVar}.
	 * @param ctx the parse tree
	 */
	void exitNewVar(MxStarParser.NewVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#functionType}.
	 * @param ctx the parse tree
	 */
	void enterFunctionType(MxStarParser.FunctionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#functionType}.
	 * @param ctx the parse tree
	 */
	void exitFunctionType(MxStarParser.FunctionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(MxStarParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(MxStarParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#variableDef}.
	 * @param ctx the parse tree
	 */
	void enterVariableDef(MxStarParser.VariableDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#variableDef}.
	 * @param ctx the parse tree
	 */
	void exitVariableDef(MxStarParser.VariableDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(MxStarParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(MxStarParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#functionParameterDef}.
	 * @param ctx the parse tree
	 */
	void enterFunctionParameterDef(MxStarParser.FunctionParameterDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#functionParameterDef}.
	 * @param ctx the parse tree
	 */
	void exitFunctionParameterDef(MxStarParser.FunctionParameterDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDef(MxStarParser.FunctionDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDef(MxStarParser.FunctionDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#classConstructor}.
	 * @param ctx the parse tree
	 */
	void enterClassConstructor(MxStarParser.ClassConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#classConstructor}.
	 * @param ctx the parse tree
	 */
	void exitClassConstructor(MxStarParser.ClassConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(MxStarParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(MxStarParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#statementBlock}.
	 * @param ctx the parse tree
	 */
	void enterStatementBlock(MxStarParser.StatementBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#statementBlock}.
	 * @param ctx the parse tree
	 */
	void exitStatementBlock(MxStarParser.StatementBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(MxStarParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(MxStarParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(MxStarParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(MxStarParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#forCondition}.
	 * @param ctx the parse tree
	 */
	void enterForCondition(MxStarParser.ForConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#forCondition}.
	 * @param ctx the parse tree
	 */
	void exitForCondition(MxStarParser.ForConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#stepping}.
	 * @param ctx the parse tree
	 */
	void enterStepping(MxStarParser.SteppingContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#stepping}.
	 * @param ctx the parse tree
	 */
	void exitStepping(MxStarParser.SteppingContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(MxStarParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(MxStarParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(MxStarParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(MxStarParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(MxStarParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(MxStarParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(MxStarParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(MxStarParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(MxStarParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(MxStarParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(MxStarParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(MxStarParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterControlStatement(MxStarParser.ControlStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitControlStatement(MxStarParser.ControlStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MxStarParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MxStarParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#globalvariableDefStatement}.
	 * @param ctx the parse tree
	 */
	void enterGlobalvariableDefStatement(MxStarParser.GlobalvariableDefStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#globalvariableDefStatement}.
	 * @param ctx the parse tree
	 */
	void exitGlobalvariableDefStatement(MxStarParser.GlobalvariableDefStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#variableDefStatement}.
	 * @param ctx the parse tree
	 */
	void enterVariableDefStatement(MxStarParser.VariableDefStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#variableDefStatement}.
	 * @param ctx the parse tree
	 */
	void exitVariableDefStatement(MxStarParser.VariableDefStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#lambdaStatement}.
	 * @param ctx the parse tree
	 */
	void enterLambdaStatement(MxStarParser.LambdaStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#lambdaStatement}.
	 * @param ctx the parse tree
	 */
	void exitLambdaStatement(MxStarParser.LambdaStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#atomExpression}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpression(MxStarParser.AtomExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#atomExpression}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpression(MxStarParser.AtomExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(MxStarParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(MxStarParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpr(MxStarParser.NewExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpr(MxStarParser.NewExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ororExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrorExpr(MxStarParser.OrorExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ororExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrorExpr(MxStarParser.OrorExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dotExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDotExpr(MxStarParser.DotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dotExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDotExpr(MxStarParser.DotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(MxStarParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(MxStarParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bracketExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBracketExpr(MxStarParser.BracketExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bracketExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBracketExpr(MxStarParser.BracketExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mutiExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMutiExpr(MxStarParser.MutiExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mutiExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMutiExpr(MxStarParser.MutiExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(MxStarParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(MxStarParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(MxStarParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(MxStarParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code minusExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMinusExpr(MxStarParser.MinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code minusExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMinusExpr(MxStarParser.MinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andandExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndandExpr(MxStarParser.AndandExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andandExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndandExpr(MxStarParser.AndandExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selfplusExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSelfplusExpr(MxStarParser.SelfplusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selfplusExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSelfplusExpr(MxStarParser.SelfplusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(MxStarParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(MxStarParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plusselfExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPlusselfExpr(MxStarParser.PlusselfExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plusselfExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPlusselfExpr(MxStarParser.PlusselfExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code caretExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCaretExpr(MxStarParser.CaretExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code caretExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCaretExpr(MxStarParser.CaretExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lambdaExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpr(MxStarParser.LambdaExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lambdaExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpr(MxStarParser.LambdaExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tidleExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTidleExpr(MxStarParser.TidleExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tidleExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTidleExpr(MxStarParser.TidleExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plusminusExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPlusminusExpr(MxStarParser.PlusminusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plusminusExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPlusminusExpr(MxStarParser.PlusminusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code listExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterListExpr(MxStarParser.ListExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code listExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitListExpr(MxStarParser.ListExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(MxStarParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(MxStarParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compareExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCompareExpr(MxStarParser.CompareExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compareExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCompareExpr(MxStarParser.CompareExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqualExpr(MxStarParser.EqualExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqualExpr(MxStarParser.EqualExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(MxStarParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(MxStarParser.AndExprContext ctx);
}