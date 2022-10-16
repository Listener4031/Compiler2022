package AST;

import basic.Locate;

public class VariableDeclarationNode extends ASTNode{
    public String name;
    public ExpressionNode expression;
    public boolean initialized;

    public VariableDeclarationNode(Locate l,String s){
        super(l);
        name=s;
        expression=null;
        initialized=false;
    }

    public VariableDeclarationNode(Locate l,String s,ExpressionNode e){
        super(l);
        name=s;
        expression=e;
        initialized=true;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
