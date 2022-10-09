// Generated from /Users/weijie/Desktop/Compiler/src/Parser/MxStar.g4 by ANTLR 4.10.1
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
	 * Enter a parse tree produced by {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(MxStarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(MxStarParser.ExpressionContext ctx);
}