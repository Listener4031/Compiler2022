package AST;

import basic.Locate;
import basic.types.Type;

import java.util.ArrayList;

public class NewVariableNode extends ExpressionNode{
    public String name;
    public Type type;
    public ArrayList<NewSizeNode> new_sizes;

    public NewVariableNode(Locate l,String s,Type t){
        super(l);
        name=s;
        type=t;
        new_sizes=new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
