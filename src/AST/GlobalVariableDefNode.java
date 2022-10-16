package AST;

import basic.Locate;

public class GlobalVariableDefNode extends DefinitionNode{
    public VariableDefNode variable_def=null;

    public GlobalVariableDefNode(Locate l,VariableDefNode v){
        super(l);
        variable_def=v;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
