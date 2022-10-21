package AST;

import basic.Locate;

public class FunctionTypeNode extends ASTNode{
    public boolean is_void;
    public VariableTypeNode variable_type;

    public FunctionTypeNode(Locate l,boolean is_v,VariableTypeNode t){
        super(l);
        is_void=is_v;
        variable_type=t;
    }

    public FunctionTypeNode(Locate l,boolean is_v){
        super(l);
        is_void=is_v;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
