package AST;

import basic.Locate;

public class ParentheseExpressionNode extends ExpressionNode{
    public ExpressionNode expression_;

    public ParentheseExpressionNode(Locate l,ExpressionNode _expression){
        super(l);
        expression_=_expression;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
