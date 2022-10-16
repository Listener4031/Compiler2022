package AST;

import basic.Locate;

public class FunctionCallExpressionNode extends ExpressionNode{
    public ExpressionNode identifier;
    public ExpressionListNode expression_list;

    public FunctionCallExpressionNode(Locate l,ExpressionNode id,ExpressionListNode e){
        super(l);
        identifier=id;
        expression_list=e;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
