package AST;

import basic.Locate;

public class ArrayExpressionNode extends ExpressionNode{
    public ExpressionNode identifier,index;

    public ArrayExpressionNode(Locate l,ExpressionNode id,ExpressionNode ind){
        super(l);
        identifier=id;
        index=ind;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}