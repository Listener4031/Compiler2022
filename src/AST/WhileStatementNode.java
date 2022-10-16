package AST;

import basic.Locate;

public class WhileStatementNode extends LoopStatementNode{
    public ExpressionNode condition;
    public StatementNode statement;

    public WhileStatementNode(Locate l,ExpressionNode e,StatementNode stmt){
        super(l);
        condition=e;
        statement=stmt;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}