package AST;

import basic.Locate;
import basic.types.Type;

import java.util.ArrayList;

public class VariableDefNode extends ASTNode{
    public VariableTypeNode type_;
    public ArrayList<VariableDeclarationNode> variable_declarations;

    public VariableDefNode(Locate l,VariableTypeNode t){
        super(l);
        type_=t;
        variable_declarations=new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
