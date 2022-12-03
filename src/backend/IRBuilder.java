package backend;

import AST.*;
import IR.*;
import basic.GlobalScope;
import basic.IRtypes.*;
import basic.Locate;
import basic.Scope;
import basic.types.Type;

import java.util.ArrayList;
import java.util.Stack;

public class IRBuilder implements ASTVisitor {
    private Scope scope_;
    public GlobalScope global_scope_;
    public GlobalDefinition global_definition_;
    public boolean is_global_def, is_class_def;
    public IRClassType current_class_type;
    public Block current_block_, global_block_;
    public Function current_function_, global_function_;
    public FunctCallStatement current_function_call;
    public ArrayList<IRType> current_parameters_;
    public Register current_class_;
    public Entity current_entity_;
    public Type return_type_;
    public boolean is_returned;
    public String current_function_name;
    public Stack<Label> stepping_labels_;
    public Stack<Label> condition_labels_;
    public Stack<Label> breakout_labels_;
    public int count_loop;
    public boolean is_function_identifier;

    public IRBuilder(GlobalDefinition _global_definition, GlobalScope _global_scope){
        this.global_scope_ = _global_scope;
        this.global_definition_ = _global_definition;
        this.scope_ = _global_scope;
        this.is_global_def = false;
        this.is_class_def = false;
        this.global_function_ = new Function("_global");
        this.global_function_.return_type_ = new IRVoidType();
        Label global_func_label = new Label(this.global_function_.entry_block.identifier_);
        this.global_function_.allocate_block.Add(new BranchStatement(global_func_label));
        this.global_block_ = this.global_function_.entry_block;
        Init();
        this.global_definition_.functions_.add(this.global_function_);
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

    public void TypeConverse(Register _from, IRType _to){
        if(_from.type_ instanceof IRIntType && _to instanceof IRIntType){
            Register new_register = new Register(_to, this.current_function_.current_register_id++);
            if(((IRIntType) _from.type_).width_ < ((IRIntType) _to).width_){
                ZextStatement new_zext = new ZextStatement(_from, new_register, _from.type_, _to);
                this.current_block_.Add(new_zext);
            }
            else if(((IRIntType) _from.type_).width_ > ((IRIntType) _to).width_){
                TruncStatement new_trunc = new TruncStatement(_from, new_register, _from.type_, _to);
                this.current_block_.Add(new_trunc);
            }
            this.current_entity_ = new_register;
        }
        else{
            if(_from.type_ instanceof IRNullType){
                ((IRNullType) _from.type_).type_ = _to;
                this.current_entity_ = _from;
            }
            else{
                Register new_register = new Register(_to, this.current_function_.current_register_id++);
                this.current_block_.Add(new BitcastStatement(_from, new_register, _to));
                this.current_entity_ = new_register;
            }
        }
    }

    public void Init(){
        //void print(string str); --function
        Function new_function = new Function("print");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRVoidType();
        IRIntType new_IRIntType = new IRIntType(8);
        IRPointerType new_IRPointerType = new IRPointerType(new_IRIntType);
        Register new_reg = new Register(new_IRPointerType, new_function.current_register_id++);
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        //void println(string str);
        new_function = new Function("println");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRVoidType();
        new_IRIntType = new IRIntType(8);
        new_IRPointerType = new IRPointerType(new_IRIntType);
        new_reg = new Register(new_IRPointerType, new_function.current_register_id++);
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        //void printInt(int n);
        new_function = new Function("printInt");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRVoidType();
        new_IRIntType = new IRIntType(32);
        new_reg = new Register(new_IRIntType, new_function.current_register_id++);
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        // void printlnInt(int n);
        new_function = new Function("printlnInt");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRVoidType();
        new_IRIntType = new IRIntType(32);
        new_reg = new Register(new_IRIntType, new_function.current_register_id++);
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
        new_reg = new Register(new_IRIntType, new_function.current_register_id++);
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
        new_reg = new Register(new_IRPointerType,  new_function.current_register_id++);
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        // string str.substring(int left,int right);
        new_function = new Function("substring");
        new_function.is_built_in = true;
        new_IRIntType = new IRIntType(8);
        new_function.return_type_ = new IRPointerType(new_IRIntType);
        new_IRIntType = new IRIntType(8);
        new_IRPointerType = new IRPointerType(new_IRIntType);
        new_reg = new Register(new_IRPointerType, new_function.current_register_id++);
        new_function.parameters_.add(new_reg);
        new_IRIntType = new IRIntType(32);
        new_reg = new Register(new_IRIntType, new_function.current_register_id++);
        new_function.parameters_.add(new_reg);
        new_IRIntType = new IRIntType(32);
        new_reg = new Register(new_IRIntType, new_function.current_register_id++);
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        // int str.parseInt();
        new_function = new Function("parseInt");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRIntType(32);
        new_IRIntType = new IRIntType(8);
        new_IRPointerType = new IRPointerType(new_IRIntType);
        new_reg = new Register(new_IRPointerType, new_function.current_register_id++);
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        // int str.ord(int pos);
        new_function = new Function("ord");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRIntType(32);
        new_IRIntType = new IRIntType(8);
        new_IRPointerType = new IRPointerType(new_IRIntType);
        new_reg = new Register(new_IRPointerType, new_function.current_register_id++);
        new_function.parameters_.add(new_reg);
        this.global_definition_.functions_.add(new_function);
        global_scope_ = (GlobalScope) global_scope_.parent_scope;
    }

    @Override
    public void visit(ProgramNode node){
        node.def_list.forEach(it -> it.accept(this));
        Label return_label = new Label(this.global_function_.return_block.identifier_);
        this.global_block_.Add(new BranchStatement(return_label));
        this.global_function_.blocks_.add(this.global_function_.return_block);
        this.global_block_ = this.global_function_.return_block;
        this.global_block_.Add(new ReturnStatement(new Register(new IRVoidType(), 0)));
    }

    @Override
    public void visit(GlobalVariableDefNode node){
        this.is_global_def = true;
        node.variable_def.accept(this);
        this.is_global_def = false;
    }

    @Override
    public void visit(FunctionDefNode node){
        Function new_function;
        if(this.is_class_def) new_function = new Function("class " + current_class_type.name_ + "_" + node.name);
        else new_function = new Function(node.name);
        if(node.return_type.is_void) new_function.return_type_ = new IRVoidType();
        else new_function.return_type_ = TypeConverse(node.return_type.variable_type.type);
        this.current_block_ = new_function.entry_block;
        this.current_function_ = new_function;
        this.global_definition_.functions_.add(new_function);
        this.current_function_name = node.name;
        this.is_returned = false;
        this.count_loop = 0;
        this.stepping_labels_ = new Stack<>();
        this.condition_labels_ = new Stack<>();
        this.breakout_labels_ = new Stack<>();
        this.scope_ = this.global_scope_.GetFunctionScope(node.position, node.name);
        if(is_class_def){
            throw new RuntimeException();
        }
        node.parameter_def.accept(this);
        for(int i = 0; i < this.current_function_.parameters_.size(); i++){
            Register it = this.current_function_.parameters_.get(i);
            IRPointerType new_IRPointerType = new IRPointerType(it.type_);
            Register tmp_register = new Register(new_IRPointerType, this.current_function_.current_register_id++);
            tmp_register.assignable_ = true;
            AllocateStatement new_allocation = new AllocateStatement(tmp_register, it.type_);
            this.current_function_.allocations_.add(new_allocation);
            StoreStatement new_store = new StoreStatement(tmp_register.type_, it, tmp_register);
            this.current_block_.Add(new_store);
            this.scope_.entities_.put(this.current_function_.parameterIDs_.get(i), tmp_register);
            if(this.is_class_def && i == 0){
                throw new RuntimeException();
            }
        }
        IRPointerType new_IRPointerType = new IRPointerType(this.current_function_.return_type_);
        Register return_register = new Register(new_IRPointerType, this.current_function_.current_register_id++);
        this.current_function_.return_register_ = return_register;
        if(!(this.current_function_.return_type_ instanceof IRVoidType)){
            AllocateStatement new_allocation = new AllocateStatement(return_register, this.current_function_.return_type_);
            this.current_function_.allocations_.add(new_allocation);
            if(this.current_function_.identifier_.equals("main")){
                IRIntType new_IRIntType = new IRIntType(32);
                Constant new_constant = new Constant(new_IRIntType, 0);
                StoreStatement new_store = new StoreStatement(this.current_function_.return_type_, new_constant, return_register);
                this.current_block_.Add(new_store);
            }
        }
        node.statement_block.accept(this);
        Label new_label = new Label(this.current_function_.return_block.identifier_);
        BranchStatement new_branch = new BranchStatement(new_label);
        this.current_block_.Add(new_branch);
        this.current_function_.blocks_.add(this.current_function_.return_block);
        this.current_block_ = this.current_function_.return_block;
        if(this.current_function_.return_type_ instanceof IRVoidType){
            Register new_register = new Register(new IRVoidType(), 0);
            ReturnStatement new_return = new ReturnStatement(new_register);
            this.current_block_.Add(new_return);
        }
        else{
            Register new_register = new Register(this.current_function_.return_type_, this.current_function_.current_register_id++);
            LoadStatement new_load = new LoadStatement(new_register.type_, this.current_function_.return_register_, new_register);
            this.current_block_.Add(new_load);
            ReturnStatement new_return = new ReturnStatement(new_register);
            this.current_block_.Add(new_return);
        }
        this.current_block_ = this.current_function_.allocate_block;
        for(AllocateStatement it : this.current_function_.allocations_) this.current_block_.Add(it);
        this.current_block_.Add(new BranchStatement(new Label(this.current_function_.entry_block.identifier_)));
        this.scope_ = this.scope_.parent_scope;
        this.current_function_ = null;
        this.current_block_ = null;
        this.current_class_ = null;
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
                    //throw new RuntimeException();
                    this.global_function_.is_empty = false;
                    this.current_function_ = this.global_function_;
                    this.current_block_ = this.global_block_;
                    it.expression.accept(this);
                    if(this.current_entity_.assignable_){
                        Register new_register = new Register((IRPointerType) this.current_entity_.type_, this.current_function_.current_register_id++);
                        this.current_block_.Add(new LoadStatement(new_register.type_, this.current_entity_, new_register));
                        this.current_entity_ = new_register;
                    }
                    if(tmp_Type.dimension > 0) new_reg.type_ = new IRPointerType(this.current_entity_.type_);
                    if(this.current_entity_ instanceof Constant){
                        GlobalDefStatement new_global_def = new GlobalDefStatement(new_reg, (Constant) this.current_entity_);
                        this.global_definition_.global_def_statements.add(new_global_def);
                    }
                    else{
                        Constant new_constant = new Constant((tmp_IRType instanceof IRIntType) ? tmp_IRType : new IRNullType(), 0);
                        this.global_definition_.global_def_statements.add(new GlobalDefStatement(new_reg, new_constant));
                        TypeConverse((Register) this.current_entity_, tmp_IRType);
                        this.current_block_.Add(new StoreStatement(new_reg.type_, this.current_entity_, new_reg));
                    }
                    this.global_block_ = this.current_block_;
                    this.current_function_ = null;
                    this.current_block_ = null;
                }
                else{
                    Constant new_c = new Constant(((IRPointerType) new_reg.type_).type_, 0);
                    GlobalDefStatement new_statement = new GlobalDefStatement(new_reg, new_c);
                    this.global_definition_.global_def_statements.add(new_statement);
                }
            }
            else if(this.current_function_ != null){
                Register new_reg = new Register(new IRPointerType(tmp_IRType), this.current_function_.current_register_id++);
                new_reg.assignable_ = true;
                this.scope_.entities_.put(it.name, new_reg);
                this.current_function_.allocations_.add(new AllocateStatement(new_reg, tmp_IRType));
                if(it.initialized){
                    //throw new RuntimeException();
                    it.expression.accept(this);
                    if(this.current_entity_.assignable_){
                        Register new_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
                        this.current_block_.Add(new LoadStatement(new_register.type_, this.current_entity_, new_register));
                        this.current_entity_ = new_register;
                    }
                    if(tmp_Type.dimension > 0) new_reg.type_ = new IRPointerType(this.current_entity_.type_);
                    if(this.current_entity_ instanceof Register) TypeConverse((Register) this.current_entity_, tmp_IRType);
                    this.current_block_.Add(new StoreStatement(tmp_IRType, this.current_entity_, new_reg));
                }
            }
        });
    }

    @Override
    public void visit(VariableDeclarationNode node){}

    @Override
    public void visit(IfStatementNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(ForStatementNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(ForInitNode node){
        if(node.variable_def != null) node.variable_def.accept(this);
        if(node.init_expression != null) node.init_expression.accept(this);
    }

    @Override
    public void visit(ForConditionNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(SteppingNode node){
        node.stepping.accept(this);
    }

    @Override
    public void visit(WhileStatementNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(BreakStatementNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(ContinueStatementNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(ReturnStatementNode node){
        if(node.expression != null) node.expression.accept(this);
        if(!(this.current_function_.return_type_ instanceof IRVoidType)){
            if(this.current_entity_.assignable_){
                Register new_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(new_register.type_, this.current_entity_, new_register));
                this.current_entity_ = new_register;
            }
            if(this.current_entity_ instanceof Register) TypeConverse((Register) this.current_entity_, this.current_function_.return_type_);
            this.current_block_.Add(new StoreStatement(this.current_entity_.type_, this.current_entity_, this.current_function_.return_register_));
        }
        this.current_block_.Add(new BranchStatement(new Label(this.current_function_.identifier_ + "_return")));
    }

    @Override
    public void visit(ClassDefNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(ClassConstructorNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(NewVariableNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(ExpressionListNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(ArrayExpressionNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(BinaryExpressionNode node){
        //throw new RuntimeException();
        if(node.binary_op == BinaryExpressionNode.BINARY_OP.DOT){

        }
    }

    @Override
    public void visit(BracketExpressionNode node){}

    @Override
    public void visit(FunctionCallExpressionNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(PrefixExpressionNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(PostfixExpressionNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(UnaryExpressionNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(AtomExpressionNode node){// public enum ATOM_EXPR{THIS,NULL,UNSIGNED_INTEGER,BOOL_LITERAL,STRING_OBJECT,IDENTIFIER}
        if(node.atom_expr == AtomExpressionNode.ATOM_EXPR.THIS) this.current_entity_ = this.current_class_;
        else if(node.atom_expr == AtomExpressionNode.ATOM_EXPR.NULL) this.current_entity_ = new Register(new IRNullType(), 0);
        else if(node.atom_expr == AtomExpressionNode.ATOM_EXPR.UNSIGNED_INTEGER) this.current_entity_ = new Constant(new IRIntType(32), Integer.parseInt(node.ID));
        else if(node.atom_expr == AtomExpressionNode.ATOM_EXPR.BOOL_LITERAL){
            if(node.ID.equals("true")) this.current_entity_ = new Constant(new IRIntType(8), 1);
            else this.current_entity_ = new Constant(new IRIntType(8), 0);
        }
        else if(node.atom_expr == AtomExpressionNode.ATOM_EXPR.STRING_OBJECT){
            throw new RuntimeException();
        }
        else{
            if(this.is_function_identifier){
                throw new RuntimeException();
            }
            else{
                throw new RuntimeException();
            }
        }
    }

    @Override
    public void visit(ParameterNode node){
        IRType cur_IRType = TypeConverse(node.type_.type);
        Register cur_parameter = new Register(cur_IRType, this.current_function_.current_register_id++);
        this.current_function_.parameterIDs_.add(node.name);
        this.current_entity_ = cur_parameter;
        this.current_function_.parameters_.add((Register) this.current_entity_);
    }

    @Override
    public void visit(FunctionParameterDefNode node){
        node.parameters.forEach(it -> it.accept(this));
    }

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
