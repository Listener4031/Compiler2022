package AST;

import basic.Locate;

public class NewSizeNode extends ASTNode{
    public ExpressionNode size_expression;

    public NewSizeNode(Locate l,ExpressionNode e){
        super(l);
        size_expression=e;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
