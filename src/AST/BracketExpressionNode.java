package AST;

import basic.Locate;

public class BracketExpressionNode extends ASTNode{
    public ExpressionNode expression_;

    public BracketExpressionNode(Locate l,ExpressionNode _expression){
        super(l);
        expression_=_expression;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
