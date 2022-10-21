package AST;

import basic.Locate;
import basic.types.Type;

public class ParameterNode extends ASTNode{
    public String name=null;
    public Type variable_type=null;

    public ParameterNode(Locate l,Type t,String s){
        super(l);
        name=s;
        variable_type=t;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
