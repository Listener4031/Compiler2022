package AST;

import basic.Locate;

public class StatementNode extends ASTNode{
    public StatementBlockNode statement_block=null;
    public VariableDefNode variable_def=null;
    public IfStatementNode if_statement=null;
    public LoopStatementNode loop_statement=null;
    public ControlStatementNode control_statement=null;
    public ExpressionNode expression=null;

    public StatementNode(Locate l){
        super(l);
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
