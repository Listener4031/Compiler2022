package AST;

import basic.Locate;
import basic.types.*;

public class FunctionDefNode extends DefinitionNode{
    public FunctionTypeNode return_type=null;
    public String name=null;
    public FunctionParameterDefNode parameter_def=null;
    public StatementBlockNode statement_block=null;

    public FunctionDefNode(Locate l){
        super(l);
    }

    public FunctionDefNode(Locate l,FunctionTypeNode t,String s,FunctionParameterDefNode para,StatementBlockNode block){
        super(l);
        return_type=t;
        name=s;
        parameter_def=para;
        statement_block=block;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
