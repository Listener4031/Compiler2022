package backend;

import AST.*;
import IR.GlobalDefinition;
import basic.GlobalScope;
import basic.Scope;

public class IRBuilder implements ASTVisitor {
    private Scope scope_;
    private GlobalScope global_scope_;
    private GlobalDefinition global_definition_;

    public IRBuilder(GlobalDefinition _global_def, GlobalScope _global_scope){
    }

    @Override
    public void visit(ProgramNode node){}

    @Override
    public void visit(GlobalVariableDefNode node){}

    @Override
    public void visit(FunctionDefNode node){}

    @Override
    public void visit(StatementBlockNode node){
        node.statements.forEach(it -> it.accept(this));
    }

    @Override
    public void visit(StatementNode node){
        if(node.statement_block != null){
            scope_ = new Scope(scope_);
            node.statement_block.accept(this);
            scope_ = scope_.parent_scope;
        }
        if(node.variable_def != null) node.variable_def.accept(this);
        if(node.if_statement != null) node.if_statement.accept(this);
        if(node.loop_statement != null) node.loop_statement.accept(this);
        if(node.control_statement != null) node.control_statement.accept(this);
        if(node.expression != null) node.expression.accept(this);
    }

    @Override
    public void visit(VariableDefNode node){}

    @Override
    public void visit(VariableDeclarationNode node){}

    @Override
    public void visit(IfStatementNode node){}

    @Override
    public void visit(ForStatementNode node){}

    @Override
    public void visit(ForInitNode node){}

    @Override
    public void visit(ForConditionNode node){}

    @Override
    public void visit(SteppingNode node){}

    @Override
    public void visit(WhileStatementNode node){}

    @Override
    public void visit(BreakStatementNode node){}

    @Override
    public void visit(ContinueStatementNode node){}

    @Override
    public void visit(ReturnStatementNode node){}

    @Override
    public void visit(ClassDefNode node){}

    @Override
    public void visit(ClassConstructorNode node){}

    @Override
    public void visit(NewVariableNode node){}

    @Override
    public void visit(ExpressionListNode node){}

    @Override
    public void visit(ArrayExpressionNode node){}

    @Override
    public void visit(BinaryExpressionNode node){}

    @Override
    public void visit(BracketExpressionNode node){}

    @Override
    public void visit(FunctionCallExpressionNode node){}

    @Override
    public void visit(PrefixExpressionNode node){}

    @Override
    public void visit(PostfixExpressionNode node){}

    @Override
    public void visit(UnaryExpressionNode node){}

    @Override
    public void visit(AtomExpressionNode node){}

    @Override
    public void visit(ParameterNode node){}

    @Override
    public void visit(FunctionParameterDefNode node){}

    @Override
    public void visit(BasicTypeNode node){}

    @Override
    public void visit(FunctionTypeNode node){}

    @Override
    public void visit(VariableTypeNode node){}

    @Override
    public void visit(TypeNameNode node){}

    @Override
    public void visit(LambdaStatementNode node){}

    @Override
    public void visit(ParentheseExpressionNode node){}
}
