package AST;

import basic.Locate;

abstract public class ASTNode {
    public Locate position;

    public ASTNode(Locate l){
        position=l;
    }

    abstract public void accept(ASTVisitor visitor);
}
