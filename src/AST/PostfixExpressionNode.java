package AST;

import basic.Locate;

public class PostfixExpressionNode extends ExpressionNode{
    public enum POSTFIX_OP{SELF_PLUS,SELF_MINUS}

    public POSTFIX_OP postfix_op;
    public ExpressionNode expr;

    public PostfixExpressionNode(Locate l,POSTFIX_OP op,ExpressionNode e){
        super(l);
        postfix_op=op;
        expr=e;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
