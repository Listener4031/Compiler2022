package AST;

import basic.Locate;

public class BreakStatementNode extends ControlStatementNode{
    public BreakStatementNode(Locate l){
        super(l);
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
