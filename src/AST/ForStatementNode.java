package AST;

import basic.Locate;

public class ForStatementNode extends LoopStatementNode{
    public ForInitNode init_statement;
    public ForConditionNode for_condition;
    public SteppingNode step_statement;
    public StatementNode statement;

    public ForStatementNode(Locate l,ForInitNode init,ForConditionNode condition,SteppingNode step,StatementNode stmt){
        super(l);
        init_statement=init;
        for_condition=condition;
        step_statement=step;
        statement=stmt;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
