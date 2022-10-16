package AST;

import basic.Locate;
import basic.types.FunctionType;

public class FunctionDefNode extends DefinitionNode{
    public String name=null;
    public FunctionType type=null;
    public StatementBlockNode statement_block=null;

    public FunctionDefNode(Locate l){
        super(l);
    }

    public FunctionDefNode(Locate l,String s,FunctionType t,StatementBlockNode block){
        super(l);
        name=s;
        type=t;
        statement_block=block;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
