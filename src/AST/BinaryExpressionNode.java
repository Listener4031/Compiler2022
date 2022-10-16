package AST;

import basic.Locate;

public class BinaryExpressionNode extends ExpressionNode{
    public enum BINARY_OP{DOT,
        MULTIPLY,DIVIDE,MOD,
        PLUS,MINUS,
        LEFT_SHIFT,RIGHT_SHIFT,
        LESS,LESS_EQUAL,GREATER,GREATER_EQUAL,
        EQUAL,NOT_EQUAL,
        AND,CARET,OR,
        AND_AND,OR_OR,
        ASSIGN}

    public BINARY_OP binary_op;
    public ExpressionNode left_expression,right_expression;

    public BinaryExpressionNode(Locate l,BINARY_OP op,ExpressionNode left,ExpressionNode right){
        super(l);
        binary_op=op;
        left_expression=left;
        right_expression=right;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
