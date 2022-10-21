package AST;

public interface ASTVisitor {
    void visit(ProgramNode node);
    void visit(GlobalVariableDefNode node);
    void visit(FunctionDefNode node);
    void visit(StatementBlockNode node);
    void visit(StatementNode node);
    void visit(VariableDefNode node);
    void visit(VariableDeclarationNode node);
    void visit(IfStatementNode node);
    void visit(ForStatementNode node);
    void visit(ForInitNode node);
    void visit(ForConditionNode node);
    void visit(SteppingNode node);
    void visit(WhileStatementNode node);
    void visit(BreakStatementNode node);
    void visit(ContinueStatementNode node);
    void visit(ReturnStatementNode node);
    void visit(ClassDefNode node);
    void visit(ClassConstructorNode node);
    void visit(NewVariableNode node);
    void visit(ExpressionListNode node);
    void visit(ArrayExpressionNode node);
    void visit(BinaryExpressionNode node);
    void visit(BracketExpressionNode node);
    void visit(FunctionCallExpressionNode node);
    void visit(PrefixExpressionNode node);
    void visit(PostfixExpressionNode node);
    void visit(UnaryExpressionNode node);
    void visit(AtomExpressionNode node);
    void visit(ParameterNode node);
    void visit(FunctionParameterDefNode node);
    void visit(BasicTypeNode node);
    void visit(FunctionTypeNode node);
    void visit(VariableTypeNode node);
    void visit(TypeNameNode node);
}
