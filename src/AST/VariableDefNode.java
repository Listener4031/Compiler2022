package AST;

import basic.Locate;
import basic.types.Type;

import java.util.ArrayList;

public class VariableDefNode extends ASTNode{
    public Type type=null;
    public ArrayList<VariableDeclarationNode> variable_declarations=null;

    public VariableDefNode(Locate l,Type t){
        super(l);
        type=t;
        variable_declarations=new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
