package AST;

import basic.Locate;

import java.util.ArrayList;

public class ExpressionListNode extends ASTNode{
    public ArrayList<ExpressionNode> expressions;

    public ExpressionListNode(Locate l){
        super(l);
        expressions=new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
