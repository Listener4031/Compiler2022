package frontend;

import AST.*;
import basic.*;
import basic.error.SemanticError;
import basic.types.Type;

import java.util.ArrayList;

public class SemanticChecker implements ASTVisitor {
    public GlobalScope global_scope;
    public Scope scope;
    public Type return_type;
    public boolean is_function_identifier=false;
    public int count_in_loop=0;
    public String name_;
    public boolean is_returned=false;

    public boolean IsCompareOperator(BinaryExpressionNode.BINARY_OP op){
        return op== BinaryExpressionNode.BINARY_OP.LESS
                ||op== BinaryExpressionNode.BINARY_OP.LESS_EQUAL
                ||op== BinaryExpressionNode.BINARY_OP.GREATER
                ||op== BinaryExpressionNode.BINARY_OP.GREATER_EQUAL
                ||op== BinaryExpressionNode.BINARY_OP.EQUAL
                ||op== BinaryExpressionNode.BINARY_OP.NOT_EQUAL;
    }

    public boolean IsArithmeticOperator(BinaryExpressionNode.BINARY_OP op){
        return op== BinaryExpressionNode.BINARY_OP.MULTIPLY
                ||op== BinaryExpressionNode.BINARY_OP.DIVIDE
                ||op== BinaryExpressionNode.BINARY_OP.MOD
                ||op== BinaryExpressionNode.BINARY_OP.PLUS
                ||op== BinaryExpressionNode.BINARY_OP.MINUS
                ||op== BinaryExpressionNode.BINARY_OP.LEFT_SHIFT
                ||op== BinaryExpressionNode.BINARY_OP.RIGHT_SHIFT
                ||op== BinaryExpressionNode.BINARY_OP.AND
                ||op== BinaryExpressionNode.BINARY_OP.OR
                ||op== BinaryExpressionNode.BINARY_OP.CARET;
    }

    public void CheckTypeMatch(Locate l,Type _left_value,Type _right_value){
        if(_right_value.type_!=_left_value.type_) throw new SemanticError(l,"types mismatch");
        if(_right_value.is_class){//then the left must be class
            if(_right_value.name==null||!_right_value.name.equals(_left_value.name)) throw new SemanticError(l,"types mismatch");
        }
        if(_right_value.dimension!=_left_value.dimension) throw new SemanticError(l,"dimensions mismatch");
    }

    public void CheckAssignment(Locate l,Type _left_value,Type _right_value){
        if(!_left_value.assignable) throw new SemanticError(l,"the value is not assignable");
        if(_right_value.type_== Type.TYPE.THIS){
            if(_left_value.type_!= Type.TYPE.CLASS||!_left_value.name.equals(_right_value.name)) throw new SemanticError(l,"the value cannot be assigned this");
        }
        else if(_right_value.type_== Type.TYPE.NULL){
            if(_left_value.dimension>0) return;// OK
            if(_left_value.type_!= Type.TYPE.CLASS&&_left_value.type_!= Type.TYPE.THIS&&_left_value.type_!= Type.TYPE.STRING) throw new SemanticError(l,"the value cannot be assigned null");
        }
        else CheckTypeMatch(l,_left_value,_right_value);
    }

    public SemanticChecker(GlobalScope _global_scope){
        global_scope=_global_scope;
        scope=_global_scope;
        is_function_identifier=false;
    }

    @Override
    public void visit(ProgramNode node){//check function main, visit def_list
        if(!global_scope.ExistFunction(false,"main")) throw new SemanticError(node.position,"the program does not have function main");
        Type t=global_scope.GetFunctionReturnType(node.position,"main");
        if(t.type_!= Type.TYPE.INT||t.dimension>0) throw new SemanticError(node.position,"function main can only return int");
        ArrayList<Type> para=global_scope.GetFunctionParameters(node.position,"main");
        if(para.size()!=0) throw new SemanticError(node.position,"function main should not have parameters");
        node.def_list.forEach(it->it.accept(this));
    }

    @Override
    public void visit(GlobalVariableDefNode node){
        node.variable_def.accept(this);
    }

    @Override
    public void visit(FunctionDefNode node){
        scope=global_scope.GetFunctionScope(node.position,node.name);
        name_=node.name;
        node.statement_block.accept(this);
        if(!name_.equals("main")){
            return_type=global_scope.GetFunctionReturnType(node.position,node.name);
            if(return_type.type_!= Type.TYPE.VOID&&!is_returned) throw new SemanticError(node.position,"non-void function should not return nothing");
        }
        scope=scope.parent_scope;
    }

    @Override
    public void visit(StatementBlockNode node){//visit statements
        node.statements.forEach(it->it.accept(this));
    }

    @Override
    public void visit(StatementNode node){
        if(node.statement_block!=null){
            scope=new Scope(scope);
            node.statement_block.accept(this);
            scope=scope.parent_scope;
        }
        if(node.expression!=null) node.expression.accept(this);
        if(node.control_statement!=null) node.control_statement.accept(this);
        if(node.loop_statement!=null) node.loop_statement.accept(this);
        if(node.if_statement!=null) node.if_statement.accept(this);
        if(node.variable_def!=null) node.variable_def.accept(this);
    }

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
        boolean tmp_switch=is_function_identifier;
        is_function_identifier=false;
        node.index.accept(this);
        if(return_type.type_!= Type.TYPE.INT||return_type.dimension>0) throw new SemanticError(node.index.position,"array index should be int");
        is_function_identifier=tmp_switch;
        node.index.type=new Type(return_type);
        node.identifier.accept(this);
        if(return_type.dimension<=0) throw new SemanticError(node.position,"dimension error");
        return_type.dimension--;
        node.type=new Type(return_type);
    }

    @Override
    public void visit(BinaryExpressionNode node){
        if(node.binary_op== BinaryExpressionNode.BINARY_OP.DOT){
            boolean tmp_switch=is_function_identifier;
            is_function_identifier=false;
            node.left_expression.accept(this);
            node.left_expression.type=new Type(return_type);
            is_function_identifier=tmp_switch;
            GlobalScope tmp_global_scope=global_scope;
            Type left_type=return_type;
            if(left_type.dimension>=1) global_scope=(GlobalScope) global_scope.GetClassScope(node.position,"_array");
            else if(left_type.type_== Type.TYPE.STRING) global_scope=(GlobalScope) global_scope.GetClassScope(node.position,"string");
            else if(left_type.type_== Type.TYPE.CLASS) global_scope=(GlobalScope) global_scope.GetClassScope(node.position,left_type.name);
            else if(left_type.type_== Type.TYPE.THIS){
                Scope tmp_scope=scope;
                scope=global_scope;
                node.right_expression.accept(this);
                node.right_expression.type=new Type(return_type);
                global_scope=tmp_global_scope;
                scope=tmp_scope;
            }
            else throw new SemanticError(node.position,"operator cannot be used");
        }
        else{
            node.left_expression.accept(this);
            node.left_expression.type=new Type(return_type);
            Type left_type=return_type;
            node.right_expression.accept(this);
            node.right_expression.type=new Type(return_type);
            Type right_type=return_type;
            if(node.binary_op== BinaryExpressionNode.BINARY_OP.ASSIGN){
                CheckAssignment(node.position,left_type,right_type);
                return_type.assignable=false;
            }

        }
    }

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

}
