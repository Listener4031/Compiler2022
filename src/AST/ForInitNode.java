package AST;

import basic.Locate;

public class ForInitNode extends ASTNode{
    public VariableDefNode variable_def;
    public ExpressionNode init_expression;

    public ForInitNode(Locate l,VariableDefNode v_def,ExpressionNode init_e){
        super(l);
        variable_def=v_def;
        init_expression=init_e;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
