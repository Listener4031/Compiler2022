package backend;

import AST.*;

public class IRBuilder implements ASTVisitor {
    @Override
    public void visit(ProgramNode node){}

    @Override
    public void visit(GlobalVariableDefNode node){}

    @Override
    public void visit(FunctionDefNode node){}

    @Override
    public void visit(StatementBlockNode node){}

    @Override
    public void visit(StatementNode node){}

    @Override
    public void visit(VariableDefNode node){}

    @Override
    public void visit(VariableDeclarationNode node){}

    @Override
    public void visit(IfStatementNode node){}

    @Override
    public void visit(ForStatementNode node){}

    @Override
    public void visit(ForInitNode node){}

    @Override
    public void visit(ForConditionNode node){}

    @Override
    public void visit(SteppingNode node){}

    @Override
    public void visit(WhileStatementNode node){}

    @Override
    public void visit(BreakStatementNode node){}

    @Override
    public void visit(ContinueStatementNode node){}

    @Override
    public void visit(ReturnStatementNode node){}

    @Override
    public void visit(ClassDefNode node){}

    @Override
    public void visit(ClassConstructorNode node){}

    @Override
    public void visit(NewVariableNode node){}

    @Override
    public void visit(ExpressionListNode node){}

    @Override
    public void visit(ArrayExpressionNode node){
        node.identifier.accept(this);
        //register arrayReg = (register) returnEntity ;
        //register array = new register(curFunction.curRegisterID ++, ((IRPointerType)arrayReg.type).type) ;
        //currentBlock.push_back(new load(array.type, arrayReg, array)) ;
        node.index.accept(this);
        //entity arrayInd = returnEntity ;
        //if (returnEntity.isLvalue) {
        //    arrayInd = new register(curFunction.curRegisterID ++, new IRIntType(32)) ;
        //    currentBlock.push_back(new load(new IRIntType(32), returnEntity, arrayInd));
        //}
        //register returnRegPointer = new register (curFunction.curRegisterID ++, array.type) ;
        //returnRegPointer.isLvalue = true ;
        //getelementptr cur = new getelementptr(array, returnRegPointer) ;
        //cur.value.add(arrayInd) ;
        //currentBlock.push_back(cur) ;
        //returnEntity = returnRegPointer ;
    }

    @Override
    public void visit(BinaryExpressionNode node){}

    @Override
    public void visit(BracketExpressionNode node){}

    @Override
    public void visit(FunctionCallExpressionNode node){}

    @Override
    public void visit(PrefixExpressionNode node){}

    @Override
    public void visit(PostfixExpressionNode node){}

    @Override
    public void visit(UnaryExpressionNode node){}

    @Override
    public void visit(AtomExpressionNode node){}

    @Override
    public void visit(ParameterNode node){}

    @Override
    public void visit(FunctionParameterDefNode node){}

    @Override
    public void visit(BasicTypeNode node){}

    @Override
    public void visit(FunctionTypeNode node){}

    @Override
    public void visit(VariableTypeNode node){}

    @Override
    public void visit(TypeNameNode node){}

    @Override
    public void visit(LambdaStatementNode node){}

    @Override
    public void visit(ParentheseExpressionNode node){}
}
