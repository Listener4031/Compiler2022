package backend;

import AST.*;
import IR.*;
import basic.GlobalScope;
import basic.IRtypes.*;
import basic.Locate;
import basic.Scope;
import basic.types.Type;

import java.util.ArrayList;

public class IRBuilder implements ASTVisitor {
    private Scope scope_;
    public GlobalScope global_scope_;
    public GlobalDefinition global_definition_;
    public boolean is_global_def, is_class_def;
    public IRClassType current_class_type;

    public IRBuilder(GlobalDefinition _global_definition, GlobalScope _global_scope){
        this.global_scope_ = _global_scope;
        this.global_definition_ = _global_definition;
        this.scope_ = _global_scope;
        this.is_global_def = false;
        this.is_class_def = false;
        Init();
    }

    public IRType TypeConverse(Type _type){ // public enum TYPE {NULL, INT, BOOL, STRING, CLASS, FUNCTION, VOID, THIS}
        IRType result = new IRIntType(32);
        if(_type.type_ == Type.TYPE.NULL) result = new IRNullType();
        else if(_type.type_ == Type.TYPE.INT){
            for(int i = 1; i <= _type.dimension; i++) result = new IRPointerType(result);
        }
        else if(_type.type_ == Type.TYPE.BOOL){
            result = new IRIntType(8);
            for(int i = 1; i <= _type.dimension; i++) result = new IRPointerType(result);
        }
        else if(_type.type_ == Type.TYPE.STRING){
            result = new IRPointerType(new IRIntType(8));
            for(int i = 1; i <= _type.dimension; i++) result = new IRPointerType(result);
        }
        else if(_type.type_ == Type.TYPE.CLASS){
            IRType tmp_type = new IRPointerType(new IRClassType(_type.name));
            tmp_type.size_ = 0;
            Scope new_scope = global_scope_.GetClassScope(new Locate(0,0), _type.name);
            for(String key : new_scope.members.keySet()){
                Type val = new_scope.members.get(key);
                if(val.type_ == Type.TYPE.BOOL) tmp_type.size_ += 1;
                else if(val.type_ == Type.TYPE.INT && val.dimension == 0) tmp_type.size_ += 4;
                else tmp_type.size_ += 8;
            }
            result = tmp_type;
            for(int i = 1; i <= _type.dimension; i++) result = new IRPointerType(result);
        }
        else if(_type.type_ == Type.TYPE.VOID) result = new IRVoidType();
        return result;
    }

    public void Init(){
        //void print(string str); --function
        Function new_function = new Function("print");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRVoidType();
        IRIntType new_IRIntType = new IRIntType(8);
        IRPointerType new_IRPointerType = new IRPointerType(new_IRIntType);
        Register new_reg = new Register(new_IRPointerType, new_function.current_register_id);
        new_function.current_register_id++;
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        //void println(string str);
        new_function = new Function("println");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRVoidType();
        new_IRIntType = new IRIntType(8);
        new_IRPointerType = new IRPointerType(new_IRIntType);
        new_reg = new Register(new_IRPointerType, new_function.current_register_id);
        new_function.current_register_id++;
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        //void printInt(int n);
        new_function = new Function("printInt");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRVoidType();
        new_IRIntType = new IRIntType(32);
        new_reg = new Register(new_IRIntType, new_function.current_register_id);
        new_function.current_register_id++;
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        // void printlnInt(int n);
        new_function = new Function("printlnInt");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRVoidType();
        new_IRIntType = new IRIntType(32);
        new_reg = new Register(new_IRIntType, new_function.current_register_id);
        new_function.current_register_id++;
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        // string getString();
        new_function = new Function("getString");
        new_function.is_built_in = true;
        new_IRIntType = new IRIntType(8);
        new_function.return_type_ = new IRPointerType(new_IRIntType);
        this.global_definition_.functions_.add(new_function);
        // int getInt();
        new_function = new Function("getInt");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRIntType(32);
        this.global_definition_.functions_.add(new_function);
        // string toString(int i);
        new_function = new Function("toString");
        new_function.is_built_in = true;
        new_IRIntType = new IRIntType(8);
        new_function.return_type_ = new IRPointerType(new_IRIntType);
        new_IRIntType = new IRIntType(32);
        new_reg = new Register(new_IRIntType, new_function.current_register_id);
        new_function.current_register_id++;
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        // string
        this.global_scope_ = (GlobalScope) this.global_scope_.GetClassScope(new Locate(0, 0), "string");
        // int str.length();
        new_function = new Function("length");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRIntType(32);
        new_IRIntType = new IRIntType(8);
        new_IRPointerType = new IRPointerType(new_IRIntType);
        new_reg = new Register(new_IRPointerType,  new_function.current_register_id);
        new_function.current_register_id++;
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        // string str.substring(int left,int right);
        new_function = new Function("substring");
        new_function.is_built_in = true;
        new_IRIntType = new IRIntType(8);
        new_function.return_type_ = new IRPointerType(new_IRIntType);
        new_IRIntType = new IRIntType(8);
        new_IRPointerType = new IRPointerType(new_IRIntType);
        new_reg = new Register(new_IRPointerType, new_function.current_register_id);
        new_function.current_register_id++;
        new_function.parameters_.add(new_reg);
        new_IRIntType = new IRIntType(32);
        new_reg = new Register(new_IRIntType, new_function.current_register_id);
        new_function.current_register_id++;
        new_function.parameters_.add(new_reg);
        new_IRIntType = new IRIntType(32);
        new_reg = new Register(new_IRIntType, new_function.current_register_id);
        new_function.current_register_id++;
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        // int str.parseInt();
        new_function = new Function("parseInt");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRIntType(32);
        new_IRIntType = new IRIntType(8);
        new_IRPointerType = new IRPointerType(new_IRIntType);
        new_reg = new Register(new_IRPointerType, new_function.current_register_id);
        new_function.current_register_id++;
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        // int str.ord(int pos);
        new_function = new Function("ord");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRIntType(32);
        new_IRIntType = new IRIntType(8);
        new_IRPointerType = new IRPointerType(new_IRIntType);
        new_reg = new Register(new_IRPointerType, new_function.current_register_id);
        new_function.current_register_id++;
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        global_scope_ = (GlobalScope) global_scope_.parent_scope;
    }

    @Override
    public void visit(ProgramNode node){
        node.def_list.forEach(it -> it.accept(this));
        //
        //
    }

    @Override
    public void visit(GlobalVariableDefNode node){
        this.is_global_def = true;
        node.variable_def.accept(this);
        this.is_global_def = false;
    }

    @Override
    public void visit(FunctionDefNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(StatementBlockNode node){
        node.statements.forEach(it -> it.accept(this));
    }

    @Override
    public void visit(StatementNode node){
        if(node.statement_block != null){
            scope_ = new Scope(scope_);
            node.statement_block.accept(this);
            scope_ = scope_.parent_scope;
        }
        if(node.variable_def != null) node.variable_def.accept(this);
        if(node.if_statement != null) node.if_statement.accept(this);
        if(node.loop_statement != null) node.loop_statement.accept(this);
        if(node.control_statement != null) node.control_statement.accept(this);
        if(node.expression != null) node.expression.accept(this);
    }

    @Override
    public void visit(VariableDefNode node){
        node.type_.accept(this);
        Type tmp_Type = node.type_.type;
        IRType tmp_IRType = TypeConverse(tmp_Type);
        node.variable_declarations.forEach(it -> {
            if(this.is_class_def){
                this.current_class_type.types_.add(tmp_IRType);
                this.current_class_type.size_ += tmp_IRType.size_;
            }
            if(this.is_global_def){
                IRPointerType new_IRPointerType = new IRPointerType(tmp_IRType);
                Register new_reg = new Register(new_IRPointerType, it.name, true);
                new_reg.assignable_ = true;
                this.scope_.entities_.put(it.name, new_reg);
                if(it.initialized){
                    throw new RuntimeException();
                }
                else{
                    Constant new_c = new Constant(((IRPointerType) new_reg.type_).type_, 0);
                    GlobalDefStatement new_statement = new GlobalDefStatement(new_reg, new_c);
                    this.global_definition_.global_def_statements.add(new_statement);
                }
            }
        });
    }

    @Override
    public void visit(VariableDeclarationNode node){}

    @Override
    public void visit(IfStatementNode node){}

    @Override
    public void visit(ForStatementNode node){}

    @Override
    public void visit(ForInitNode node){
        if(node.variable_def != null) node.variable_def.accept(this);
        if(node.init_expression != null) node.init_expression.accept(this);
    }

    @Override
    public void visit(ForConditionNode node){}

    @Override
    public void visit(SteppingNode node){
        node.stepping.accept(this);
    }

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
    public void visit(ArrayExpressionNode node){}

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
    public void visit(ParentheseExpressionNode node){
        node.expression_.accept(this);
    }
}
