package AST;

import basic.Locate;

public class IfStatementNode extends ASTNode{
    public ExpressionNode expression;
    public StatementNode true_statement,false_statement;

    public IfStatementNode(Locate l,ExpressionNode e,StatementNode true_stmt,StatementNode false_stmt){
        super(l);
        expression=e;
        true_statement=true_stmt;
        false_statement=false_stmt;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
