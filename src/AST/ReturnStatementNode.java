package AST;

import basic.Locate;

public class ReturnStatementNode extends ControlStatementNode {
    public ExpressionNode expression;

    public ReturnStatementNode(Locate l,ExpressionNode e){
        super(l);
        expression=e;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
