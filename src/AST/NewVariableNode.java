package AST;

import basic.Locate;
import basic.types.Type;

import java.util.ArrayList;

public class NewVariableNode extends ExpressionNode{
    public TypeNameNode type_;
    public ArrayList<ExpressionNode> new_sizes;

    public NewVariableNode(Locate l,TypeNameNode t){
        super(l);
        type_=t;
        new_sizes=new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
