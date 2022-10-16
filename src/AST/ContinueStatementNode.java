package AST;

import basic.Locate;

public class ContinueStatementNode extends ControlStatementNode{
    public ContinueStatementNode(Locate l){
        super(l);
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
