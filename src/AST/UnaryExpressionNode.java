package AST;

import basic.Locate;

public class UnaryExpressionNode extends ExpressionNode{
    public enum UNARY_OP{PLUS,MINUS,TILDE,NOT}

    public UNARY_OP unary_op;
    public ExpressionNode expr;

    public UnaryExpressionNode(Locate l,UNARY_OP op,ExpressionNode e){
        super(l);
        unary_op=op;
        expr=e;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
