package AST;

import basic.Locate;

public class ForConditionNode extends ASTNode{
    public ExpressionNode condition;

    public ForConditionNode(Locate l,ExpressionNode e){
        super(l);
        condition=e;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
