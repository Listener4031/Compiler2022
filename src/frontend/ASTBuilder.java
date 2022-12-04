package frontend;

import AST.*;
import Parser.MxStarBaseVisitor;
import Parser.MxStarParser;
import basic.Locate;
import basic.error.SemanticError;
import basic.types.FunctionType;
import basic.types.Type;

public class ASTBuilder extends MxStarBaseVisitor<ASTNode>{
    @Override public ASTNode visitProgram(MxStarParser.ProgramContext ctx) {
        ProgramNode program=new ProgramNode(new Locate(ctx));
        ctx.definition().forEach(it->program.def_list.add((DefinitionNode) visit(it)));
        return program;
    }

    @Override public ASTNode visitDefinition(MxStarParser.DefinitionContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitBasicType(MxStarParser.BasicTypeContext ctx) {
        Locate l=new Locate(ctx);
        BasicTypeNode new_node=new BasicTypeNode(l,null);
        if(ctx.Int()!=null) new_node.type_= Type.TYPE.INT;
        else if(ctx.Bool()!=null) new_node.type_= Type.TYPE.BOOL;
        else if(ctx.String()!=null) new_node.type_= Type.TYPE.STRING;
        return new_node;
    }

    @Override public ASTNode visitTypeName(MxStarParser.TypeNameContext ctx) {
        Locate l=new Locate(ctx);
        if(ctx.basicType()!=null) return new TypeNameNode(l,(BasicTypeNode) visit(ctx.basicType()));
        else return new TypeNameNode(l,ctx.Identifier().toString());
    }

    @Override public ASTNode visitVariableType(MxStarParser.VariableTypeContext ctx) {
        assert (ctx.LeftBracket().size()==ctx.RightBracket().size());
        Locate l=new Locate(ctx);
        return new VariableTypeNode(l,(TypeNameNode) visit(ctx.typeName()),ctx.LeftBracket().size());
    }

    @Override public ASTNode visitNewVar(MxStarParser.NewVarContext ctx) {
        Locate l=new Locate(ctx);
        NewVariableNode new_node=new NewVariableNode(l,(TypeNameNode) visit(ctx.typeName()));
        ctx.bracketExpression().forEach(it->new_node.new_sizes.add((BracketExpressionNode) visit(it)));
        return new_node;
    }

    @Override public ASTNode visitFunctionType(MxStarParser.FunctionTypeContext ctx) {
        Locate l=new Locate(ctx);
        FunctionTypeNode new_node=new FunctionTypeNode(l,false);
        if(ctx.Void()!=null) new_node.is_void=true;
        else new_node.variable_type=(VariableTypeNode) visit(ctx.variableType());
        return new_node;
    }

    @Override public ASTNode visitVariableDeclaration(MxStarParser.VariableDeclarationContext ctx) {
        Locate l=new Locate(ctx);
        if(ctx.expression()!=null) return new VariableDeclarationNode(l,ctx.Identifier().toString(),(ExpressionNode) visit(ctx.expression()));
        else return new VariableDeclarationNode(l,ctx.Identifier().toString());
    }

    @Override public ASTNode visitVariableDef(MxStarParser.VariableDefContext ctx) {
        Locate l=new Locate(ctx);
        VariableDefNode new_node=new VariableDefNode(l,(VariableTypeNode) visit(ctx.variableType()));
        ctx.variableDeclaration().forEach(it->new_node.variable_declarations.add((VariableDeclarationNode) visit(it)));
        return new_node;
    }

    @Override public ASTNode visitParameter(MxStarParser.ParameterContext ctx) {
        Locate l=new Locate(ctx);
        //ParameterNode new_node=new ParameterNode(l,(VariableTypeNode) visit(ctx.variableType()),ctx.Identifier().toString());
        return new ParameterNode(l,(VariableTypeNode) visit(ctx.variableType()),ctx.Identifier().toString());
    }

    @Override public ASTNode visitFunctionParameterDef(MxStarParser.FunctionParameterDefContext ctx) {
        Locate l=new Locate(ctx);
        FunctionParameterDefNode new_node=new FunctionParameterDefNode(l);
        ctx.parameter().forEach(it->new_node.parameters.add((ParameterNode) visit(it)));
        /*
        for(int i=0;i<ctx.parameter().size();i++){
            new_node.parameters.add((ParameterNode) visit(ctx.parameter(i)));
        }
         */
        return new_node;
    }

    @Override public ASTNode visitFunctionDef(MxStarParser.FunctionDefContext ctx) {
        Locate l=new Locate(ctx);
        //FunctionDefNode new_node=new FunctionDefNode(tmp_l);
        return new FunctionDefNode(l,(FunctionTypeNode) visit(ctx.functionType()),ctx.Identifier().toString(),(FunctionParameterDefNode) visit(ctx.functionParameterDef()),(StatementBlockNode) visit(ctx.statementBlock()));
        //return visitChildren(ctx);
    }

    @Override public ASTNode visitClassConstructor(MxStarParser.ClassConstructorContext ctx) {
        Locate l=new Locate(ctx);
        return new ClassConstructorNode(l,ctx.Identifier().toString(),(StatementBlockNode) visit(ctx.statementBlock()));
    }

    @Override public ASTNode visitClassDef(MxStarParser.ClassDefContext ctx) {
        Locate l=new Locate(ctx);
        ClassDefNode new_node=new ClassDefNode(l,ctx.Identifier().toString());
        if(ctx.classConstructor()!=null) new_node.constructor=(ClassConstructorNode) visit(ctx.classConstructor());
        ctx.functionDef().forEach(it->new_node.functions.add((FunctionDefNode) visit(it)));
        ctx.variableDefStatement().forEach(it->new_node.variables.add((VariableDefNode) visit(it)));
        /*
        for(int i=0;i<ctx.variableDefStatement().size();i++){
            new_node.variables.add((VariableDefNode) visit(ctx.variableDefStatement(i)));
        }
        for(int i=0;i<ctx.functionDef().size();i++){
            new_node.functions.add((FunctionDefNode) visit(ctx.functionDef(i)));
        }
         */
        return new_node;
    }

    @Override public ASTNode visitStatementBlock(MxStarParser.StatementBlockContext ctx) {
        Locate l=new Locate(ctx);
        StatementBlockNode new_node=new StatementBlockNode(l);
        ctx.statement().forEach(it->new_node.statements.add((StatementNode) visit(it)));
        return new_node;
    }

    @Override public ASTNode visitIfStatement(MxStarParser.IfStatementContext ctx) {
        Locate l=new Locate(ctx);
        IfStatementNode new_node=new IfStatementNode(l,(ExpressionNode) visit(ctx.expression()),(StatementNode) visit(ctx.statement(0)),null);
        if(ctx.statement(1)!=null) new_node.false_statement=(StatementNode) visit(ctx.statement(1));
        return new_node;
    }

    @Override public ASTNode visitForInit(MxStarParser.ForInitContext ctx) {
        Locate l=new Locate(ctx);
        if(ctx.variableDef() == null) return new ForInitNode(l, null, (ExpressionNode) visit(ctx.expression()));
        else return new ForInitNode(l, (VariableDefNode) visit(ctx.variableDef()), null);
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

    @Override public ASTNode visitWhileStatement(MxStarParser.WhileStatementContext ctx) {
        Locate l=new Locate(ctx);
        return new WhileStatementNode(l,(ExpressionNode) visit(ctx.expression()),(StatementNode) visit(ctx.statement()));
    }

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

    @Override public ASTNode visitReturnStatement(MxStarParser.ReturnStatementContext ctx) {
        Locate l=new Locate(ctx);
        ReturnStatementNode new_node=new ReturnStatementNode(l,null);
        if(ctx.expression()!=null) new_node.expression=(ExpressionNode) visit(ctx.expression());
        return new_node;
    }

    @Override public ASTNode visitControlStatement(MxStarParser.ControlStatementContext ctx) {
        Locate l=new Locate(ctx);
        if(ctx.breakStatement()!=null) return visit(ctx.breakStatement());
        else if(ctx.continueStatement()!=null) return visit(ctx.continueStatement());
        else return visit(ctx.returnStatement());
    }

    @Override public ASTNode visitStatement(MxStarParser.StatementContext ctx) {
        Locate l=new Locate(ctx);
        StatementNode new_node=new StatementNode(l);
        if(ctx.statementBlock()!=null) new_node.statement_block=(StatementBlockNode) visit(ctx.statementBlock());
        if(ctx.variableDefStatement()!=null) new_node.variable_def=(VariableDefNode) visit(ctx.variableDefStatement());
        if(ctx.ifStatement()!=null) new_node.if_statement=(IfStatementNode) visit(ctx.ifStatement());
        if(ctx.loopStatement()!=null) new_node.loop_statement=(LoopStatementNode) visit(ctx.loopStatement());
        if(ctx.controlStatement()!=null) new_node.control_statement=(ControlStatementNode) visit(ctx.controlStatement());
        if(ctx.expression()!=null) new_node.expression=(ExpressionNode) visit(ctx.expression());
        return new_node;
        /*
        statement: statementBlock
         | variableDefStatement
         | ifStatement
         | loopStatement
         | controlStatement
         | expression ';'
         | ';'
         ;
         */
    }

    @Override public ASTNode visitGlobalvariableDefStatement(MxStarParser.GlobalvariableDefStatementContext ctx) {
        Locate tmp_l=new Locate(ctx);
        return new GlobalVariableDefNode(tmp_l,(VariableDefNode) visit(ctx.variableDef()));
    }

    @Override public ASTNode visitVariableDefStatement(MxStarParser.VariableDefStatementContext ctx) {
        return visit(ctx.variableDef());
    }

    @Override public ASTNode visitLambdaStatement(MxStarParser.LambdaStatementContext ctx) {
        Locate l=new Locate(ctx);
        FunctionParameterDefNode node=null;
        if(ctx.functionParameterDef()!=null) node=(FunctionParameterDefNode) visit(ctx.functionParameterDef());
        return new LambdaStatementNode(l,node,(StatementBlockNode) visit(ctx.statementBlock()),(ExpressionListNode) visit(ctx.expressionList()));
    }

    @Override public ASTNode visitAtomExpr(MxStarParser.AtomExprContext ctx) {
        Locate l=new Locate(ctx);
        AtomExpressionNode new_node=new AtomExpressionNode(l,null,null);
        if(ctx.This()!=null){
            new_node.atom_expr= AtomExpressionNode.ATOM_EXPR.THIS;
        }
        else if(ctx.Null()!=null){
            new_node.atom_expr= AtomExpressionNode.ATOM_EXPR.NULL;
        }
        else if(ctx.UnsignedInteger()!=null){
            new_node.atom_expr= AtomExpressionNode.ATOM_EXPR.UNSIGNED_INTEGER;
            new_node.ID=ctx.UnsignedInteger().toString();
        }
        else if(ctx.BoolLiteral()!=null){
            new_node.atom_expr= AtomExpressionNode.ATOM_EXPR.BOOL_LITERAL;
            new_node.ID=ctx.BoolLiteral().toString();
        }
        else if(ctx.StringObject()!=null){
            new_node.atom_expr= AtomExpressionNode.ATOM_EXPR.STRING_OBJECT;
            new_node.ID=ctx.StringObject().toString();
        }
        else if(ctx.Identifier()!=null){
            new_node.atom_expr= AtomExpressionNode.ATOM_EXPR.IDENTIFIER;
            new_node.ID=ctx.Identifier().toString();
        }
        return new_node;
    }

    @Override public ASTNode visitExpressionList(MxStarParser.ExpressionListContext ctx) {
        Locate l=new Locate(ctx);
        ExpressionListNode new_node=new ExpressionListNode(l);
        ctx.expression().forEach(it->new_node.expressions.add((ExpressionNode) visit(it)));
        /*
        for(int i=0;i<ctx.expression().size();i++){
            new_node.expressions.add((ExpressionNode) visit(ctx.expression(i)));
        }
         */
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

    @Override public ASTNode visitLambdaExpression(MxStarParser.LambdaExpressionContext ctx) {
        return visit(ctx.lambdaStatement());
    }

    @Override public ASTNode visitPrefixExpression(MxStarParser.PrefixExpressionContext ctx) {
        Locate l=new Locate(ctx);
        PrefixExpressionNode new_node=new PrefixExpressionNode(l,null,(ExpressionNode) visit(ctx.expression()));
        if(ctx.SelfPlus()!=null) new_node.prefix_op= PrefixExpressionNode.PREFIX_OP.SELF_PLUS;
        else new_node.prefix_op= PrefixExpressionNode.PREFIX_OP.SELF_MINUS;
        return new_node;
    }

    @Override public ASTNode visitArrayExpression(MxStarParser.ArrayExpressionContext ctx) {
        Locate l=new Locate(ctx);
        return new ArrayExpressionNode(l,(ExpressionNode) visit(ctx.expression(0)),(ExpressionNode) visit(ctx.expression(1)));
    }

    @Override public ASTNode visitAtomExpression(MxStarParser.AtomExpressionContext ctx) {
        return visit(ctx.atomExpr());
    }

    @Override public ASTNode visitNewExpression(MxStarParser.NewExpressionContext ctx) {
        return visit(ctx.newVar());
    }

    @Override public ASTNode visitPostfixExpression(MxStarParser.PostfixExpressionContext ctx) {
        Locate l=new Locate(ctx);
        PostfixExpressionNode new_node=new PostfixExpressionNode(l,null,(ExpressionNode) visit(ctx.expression()));
        if(ctx.SelfPlus()!=null) new_node.postfix_op= PostfixExpressionNode.POSTFIX_OP.SELF_PLUS;
        else new_node.postfix_op= PostfixExpressionNode.POSTFIX_OP.SELF_MINUS;
        return new_node;
    }

    @Override public ASTNode visitFunctionCallExpression(MxStarParser.FunctionCallExpressionContext ctx) {
        Locate l=new Locate(ctx);
        return new FunctionCallExpressionNode(l,(ExpressionNode) visit(ctx.expression()),(ExpressionListNode) visit(ctx.expressionList()));
    }

    @Override public ASTNode visitUnaryExpression(MxStarParser.UnaryExpressionContext ctx) {
        Locate l=new Locate(ctx);
        UnaryExpressionNode new_node=new UnaryExpressionNode(l,null,(ExpressionNode) visit(ctx.expression()));
        /*
        public enum UNARY_OP{PLUS,MINUS,TILDE,NOT}
         */
        if(ctx.Plus()!=null) new_node.unary_op= UnaryExpressionNode.UNARY_OP.PLUS;
        else if(ctx.Minus()!=null) new_node.unary_op= UnaryExpressionNode.UNARY_OP.MINUS;
        else if(ctx.Tilde()!=null) new_node.unary_op= UnaryExpressionNode.UNARY_OP.TILDE;
        else if(ctx.Not()!=null) new_node.unary_op= UnaryExpressionNode.UNARY_OP.NOT;
        return new_node;
    }

    @Override public ASTNode visitBracketExpression(MxStarParser.BracketExpressionContext ctx) {
        Locate l=new Locate(ctx);
        BracketExpressionNode new_node=new BracketExpressionNode(l,null);
        if(ctx.expression()!=null) new_node.expression_=(ExpressionNode) visit(ctx.expression());
        return new_node;
    }

    @Override public ASTNode visitParentheseExpression(MxStarParser.ParentheseExpressionContext ctx){
        Locate l=new Locate(ctx);
        return new ParentheseExpressionNode(l,(ExpressionNode) visit(ctx.expression()));
    }
}

