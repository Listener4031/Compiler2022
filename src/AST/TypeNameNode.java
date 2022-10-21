package AST;

import basic.Locate;

public class TypeNameNode extends ASTNode{
    public BasicTypeNode basic_type=null;
    public boolean is_basic_type;
    public String ID=null;

    public TypeNameNode(Locate l,BasicTypeNode n){
        super(l);
        basic_type=n;
        is_basic_type=true;
    }

    public TypeNameNode(Locate l,String s){
        super(l);
        ID=s;
        is_basic_type=false;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
