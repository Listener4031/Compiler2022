package frontend;

import AST.*;
import Parser.MxStarBaseVisitor;
import Parser.MxStarParser;
import basic.Locate;
import basic.types.FunctionType;

public class ASTBuilder extends MxStarBaseVisitor<ASTNode>{
    @Override public ASTNode visitProgram(MxStarParser.ProgramContext ctx) {
        ProgramNode program=new ProgramNode(new Locate(ctx));
        ctx.definition().forEach(it->program.def_list.add((DefinitionNode) visit(it)));
        return program;
    }

    @Override public ASTNode visitBasicType(MxStarParser.BasicTypeContext ctx) {
        return visitChildren(ctx);
    }

    @Override public ASTNode visitTypeName(MxStarParser.TypeNameContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitVariableType(MxStarParser.VariableTypeContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitNewVar(MxStarParser.NewVarContext ctx) {
        return visit(ctx.typeName());
    }

    @Override public ASTNode visitFunctionType(MxStarParser.FunctionTypeContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitVariableDeclaration(MxStarParser.VariableDeclarationContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitVariableDef(MxStarParser.VariableDefContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitParameter(MxStarParser.ParameterContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitFunctionParameterDef(MxStarParser.FunctionParameterDefContext ctx) {
        Locate l=new Locate(ctx);
        FunctionParameterDefNode new_node=new FunctionParameterDefNode(l);
        for(int i=0;i<ctx.parameter().size();i++){
            new_node.parameters.add((ParameterNode) visit(ctx.parameter(i)));
        }
        return new_node;
    }

    @Override public ASTNode visitFunctionDef(MxStarParser.FunctionDefContext ctx) {
        Locate tmp_l=new Locate(ctx);
        //FunctionDefNode new_node=new FunctionDefNode(tmp_l);

        return new FunctionDefNode(tmp_l);
        //return visitChildren(ctx);
    }

    @Override public ASTNode visitClassConstructor(MxStarParser.ClassConstructorContext ctx) {
        Locate l=new Locate(ctx);
        return new ClassConstructorNode(l,ctx.Identifier().getText(),(StatementBlockNode) visit(ctx.statementBlock()));
    }

    @Override public ASTNode visitClassDef(MxStarParser.ClassDefContext ctx) {
        Locate l=new Locate(ctx);
        ClassDefNode new_node=new ClassDefNode(l,ctx.Identifier().toString());
        if(ctx.classConstructor()!=null) new_node.constructor=(ClassConstructorNode) visit(ctx.classConstructor());
        for(int i=0;i<ctx.variableDefStatement().size();i++){
            new_node.variables.add((VariableDefNode) visit(ctx.variableDefStatement(i)));
        }
        for(int i=0;i<ctx.functionDef().size();i++){
            new_node.functions.add((FunctionDefNode) visit(ctx.functionDef(i)));
        }
        return new_node;
    }

    @Override public ASTNode visitStatementBlock(MxStarParser.StatementBlockContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitIfStatement(MxStarParser.IfStatementContext ctx) {
        Locate l=new Locate(ctx);
        IfStatementNode new_node=new IfStatementNode(l,(ExpressionNode) visit(ctx.expression()),(StatementNode) visit(ctx.statement(0)),null);
        if(ctx.statement(1)!=null) new_node.false_statement=(StatementNode) visit(ctx.statement(1));
        return new_node;
    }

    @Override public ASTNode visitForInit(MxStarParser.ForInitContext ctx) {
        Locate l=new Locate(ctx);
        return new ForInitNode(l,(VariableDefNode) visit(ctx.variableDef()),(ExpressionNode) visit(ctx.expression()));
    }

    @Override public ASTNode visitForCondition(MxStarParser.ForConditionContext ctx) {
        Locate l=new Locate(ctx);
        return new ForConditionNode(l,(ExpressionNode) visit(ctx.expression()));
    }

    @Override public ASTNode visitStepping(MxStarParser.SteppingContext ctx) {
        Locate l=new Locate(ctx);
        return new SteppingNode(l,(ExpressionNode) visit(ctx.expression()));
    }

    @Override public ASTNode visitForStatement(MxStarParser.ForStatementContext ctx) {
        Locate l=new Locate(ctx);
        ForInitNode initNode=null;
        if(ctx.forInit()!=null) initNode=(ForInitNode) visit(ctx.forInit());
        ForConditionNode conditionNode=null;
        if(ctx.forCondition()!=null) conditionNode=(ForConditionNode) visit(ctx.forCondition());
        SteppingNode steppingNode=null;
        if(ctx.stepping()!=null) steppingNode=(SteppingNode) visit(ctx.stepping());
        return new ForStatementNode(l,initNode,conditionNode,steppingNode,(StatementNode) visit(ctx.statement()));
    }

    @Override public ASTNode visitWhileStatement(MxStarParser.WhileStatementContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitLoopStatement(MxStarParser.LoopStatementContext ctx) {
        if(ctx.forStatement()!=null) return visit(ctx.forStatement());
        else return visit(ctx.whileStatement());
        //return visitChildren(ctx);
    }

    @Override public ASTNode visitBreakStatement(MxStarParser.BreakStatementContext ctx) {
        Locate l=new Locate(ctx);
        return new BreakStatementNode(l);
    }

    @Override public ASTNode visitContinueStatement(MxStarParser.ContinueStatementContext ctx) {
        Locate l=new Locate(ctx);
        return new ContinueStatementNode(l);
    }

    @Override public ASTNode visitReturnStatement(MxStarParser.ReturnStatementContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitControlStatement(MxStarParser.ControlStatementContext ctx) {
        Locate l=new Locate(ctx);
        if(ctx.breakStatement()!=null) return visit(ctx.breakStatement());
        else if(ctx.continueStatement()!=null) return visit(ctx.continueStatement());
        else return visit(ctx.returnStatement());
    }

    @Override public ASTNode visitStatement(MxStarParser.StatementContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitGlobalvariableDefStatement(MxStarParser.GlobalvariableDefStatementContext ctx) {
        Locate tmp_l=new Locate(ctx);
        return new GlobalVariableDefNode(tmp_l,(VariableDefNode) visit(ctx.variableDef()));
    }

    @Override public ASTNode visitVariableDefStatement(MxStarParser.VariableDefStatementContext ctx) {
        return visitChildren(ctx);
    }

    @Override public ASTNode visitLambdaStatement(MxStarParser.LambdaStatementContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitAtomExpr(MxStarParser.AtomExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitExpressionList(MxStarParser.ExpressionListContext ctx) {
        Locate l=new Locate(ctx);
        ExpressionListNode new_node=new ExpressionListNode(l);
        for(int i=0;i<ctx.expression().size();i++){
            new_node.expressions.add((ExpressionNode) visit(ctx.expression(i)));
        }
        return new_node;
    }

    @Override public ASTNode visitBinaryExpression(MxStarParser.BinaryExpressionContext ctx) {
        Locate l=new Locate(ctx);
        BinaryExpressionNode new_node=new BinaryExpressionNode(l,null,(ExpressionNode) visit(ctx.expression(0)),(ExpressionNode) visit(ctx.expression(1)));
        if(ctx.Dot()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.DOT;
        else if(ctx.Multiply()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.MULTIPLY;
        else if(ctx.Divide()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.DIVIDE;
        else if(ctx.Mod()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.MOD;
        else if(ctx.Plus()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.PLUS;
        else if(ctx.Minus()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.MINUS;
        else if(ctx.LeftShift()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.LEFT_SHIFT;
        else if(ctx.RightShift()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.RIGHT_SHIFT;
        else if(ctx.Less()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.LESS;
        else if(ctx.LessEqual()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.LESS_EQUAL;
        else if(ctx.Greater()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.GREATER;
        else if(ctx.GreaterEqual()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.GREATER_EQUAL;
        else if(ctx.Equal()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.EQUAL;
        else if(ctx.NotEqual()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.NOT_EQUAL;
        else if(ctx.And()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.AND;
        else if(ctx.Caret()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.CARET;
        else if(ctx.Or()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.OR;
        else if(ctx.AND()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.AND_AND;
        else if(ctx.OR()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.OR_OR;
        else if(ctx.Assign()!=null) new_node.binary_op=BinaryExpressionNode.BINARY_OP.ASSIGN;
            /*
            public enum BINARY_OP{DOT,
        MULTIPLY,DIVIDE,MOD,
        PLUS,MINUS,
        LEFT_SHIFT,RIGHT_SHIFT,
        LESS,LESS_EQUAL,GREATER,GREATER_EQUAL,
        EQUAL,NOT_EQUAL,
        AND,CARET,OR,
        AND_AND,OR_OR,
        ASSIGN}
             */
        return new_node;
    }

    @Override public ASTNode visitLambdaExpression(MxStarParser.LambdaExpressionContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitSelfplusExpression(MxStarParser.SelfplusExpressionContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitArrayExpression(MxStarParser.ArrayExpressionContext ctx) {
        Locate l=new Locate(ctx);
        return new ArrayExpressionNode(l,(ExpressionNode) visit(ctx.expression(0)),(ExpressionNode) visit(ctx.expression(1)));
        //return visitChildren(ctx);
    }

    @Override public ASTNode visitAtomExpression(MxStarParser.AtomExpressionContext ctx) {
        return visit(ctx.atomExpr());
        //return visitChildren(ctx);
    }

    @Override public ASTNode visitPlusselfExpression(MxStarParser.PlusselfExpressionContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitFunctionCallExpression(MxStarParser.FunctionCallExpressionContext ctx) {
        Locate l=new Locate(ctx);
        return new FunctionCallExpressionNode(l,(ExpressionNode) visit(ctx.expression()),(ExpressionListNode) visit(ctx.expressionList()));
    }

    @Override public ASTNode visitUnaryExpression(MxStarParser.UnaryExpressionContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitBracketExpression(MxStarParser.BracketExpressionContext ctx) {
        Locate l=new Locate(ctx);
        return new BracketExpressionNode(l,(ExpressionNode) visit(ctx.expression()));
    }
}

