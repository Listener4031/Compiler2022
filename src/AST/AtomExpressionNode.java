package AST;

import basic.Locate;

public class AtomExpressionNode extends ExpressionNode{
    public enum ATOM_EXPR{THIS,NULL,UNSIGNED_INTEGER,BOOL_LITERAL,STRING_OBJECT,IDENTIFIER}

    public ATOM_EXPR atom_expr;
    public String ID;

    public AtomExpressionNode(Locate l,ATOM_EXPR expr,String s){
        super(l);
        atom_expr=expr;
        ID=s;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
