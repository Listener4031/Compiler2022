package AST;

import basic.Locate;

public class SteppingNode extends ASTNode{
    public ExpressionNode stepping;

    public SteppingNode(Locate l,ExpressionNode e){
        super(l);
        stepping=e;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
