package AST;

import basic.Locate;
import basic.types.Type;

public class BasicTypeNode extends ASTNode{
    public Type.TYPE type_=null;

    public BasicTypeNode(Locate l,Type.TYPE t){
        super(l);
        type_=t;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
