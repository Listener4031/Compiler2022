package AST;

import basic.Locate;

import java.util.ArrayList;

public class FunctionParameterDefNode extends ASTNode{
    public ArrayList<ParameterNode> parameters;

    public FunctionParameterDefNode(Locate l){
        super(l);
        parameters=new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
