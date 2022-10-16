package AST;

import basic.Locate;

public class BracketExpressionNode extends ExpressionNode{
    public ExpressionNode expression;

    public BracketExpressionNode(Locate l,ExpressionNode e){
        super(l);
        expression=e;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
