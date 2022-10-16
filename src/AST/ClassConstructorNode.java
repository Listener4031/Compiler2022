package AST;

import basic.Locate;

public class ClassConstructorNode extends ASTNode{
    public String name;
    public StatementBlockNode statement_block;

    public ClassConstructorNode(Locate l,String s,StatementBlockNode block){
        super(l);
        name=s;
        statement_block=block;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
