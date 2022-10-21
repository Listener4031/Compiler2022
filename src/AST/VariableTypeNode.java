package AST;

import basic.Locate;

public class VariableTypeNode extends ASTNode{
    public TypeNameNode type_;
    public int dimension;

    public VariableTypeNode(Locate l,TypeNameNode t,int d){
        super(l);
        type_=t;
        dimension=d;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
