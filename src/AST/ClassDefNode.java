package AST;

import basic.Locate;

import java.util.ArrayList;

public class ClassDefNode extends DefinitionNode{
    public String name;
    public ClassConstructorNode constructor;
    public ArrayList<FunctionDefNode> functions;
    public ArrayList<VariableDefNode> variables;

    public ClassDefNode(Locate l,String s){
        super(l);
        name=s;
        functions=new ArrayList<>();
        variables=new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
