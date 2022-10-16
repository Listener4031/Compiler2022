package AST;

import basic.Locate;

public class PrefixExpressionNode extends ExpressionNode{
    public enum PREFIX_OP{SELF_PLUS,SELF_MINUS}

    public PREFIX_OP prefix_op;
    public ExpressionNode expr;

    public PrefixExpressionNode(Locate l,PREFIX_OP op,ExpressionNode e){
        super(l);
        prefix_op=op;
        expr=e;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
