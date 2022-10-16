package AST;

import basic.Locate;

import java.util.ArrayList;

public class ProgramNode extends ASTNode{
    public ArrayList<DefinitionNode> def_list;

    public ProgramNode(Locate l){
        super(l);
        def_list=new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
