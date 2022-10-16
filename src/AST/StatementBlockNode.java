package AST;

import basic.Locate;

import java.util.ArrayList;

public class StatementBlockNode extends ASTNode{
    public ArrayList<StatementNode> statements=null;

    public StatementBlockNode(Locate l){
        super(l);
        statements=new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
