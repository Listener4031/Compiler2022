package AST;

import basic.Locate;
import basic.types.Type;

public class ParameterNode extends ASTNode{
    public VariableTypeNode type_;
    public String name;

    public ParameterNode(Locate l,VariableTypeNode t,String s){
        super(l);
        type_=t;
        name=s;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
