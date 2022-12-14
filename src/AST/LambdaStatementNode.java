package AST;

import basic.Locate;

public class LambdaStatementNode extends ExpressionNode{
    public FunctionParameterDefNode parameter_def;
    public StatementBlockNode statement_block;
    public ExpressionListNode expression_list;

    public LambdaStatementNode(Locate l,FunctionParameterDefNode _parameter_def,StatementBlockNode _statement_block,ExpressionListNode _expression_list){
        super(l);
        parameter_def=_parameter_def;
        statement_block=_statement_block;
        expression_list=_expression_list;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

}
