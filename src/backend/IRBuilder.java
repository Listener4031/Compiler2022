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
    public boolean is_global_def, is_class_def, function_call_flag, have_function_flag;
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

    public IRBuilder(GlobalDefinition _global_definition, GlobalScope _global_scope){
        this.global_scope_ = _global_scope;
        this.global_definition_ = _global_definition;
        this.scope_ = _global_scope;
        this.is_global_def = false;
        this.is_class_def = false;
        this.function_call_flag = false;
        this.have_function_flag = false;
        this.global_function_ = new Function("_global");
        this.global_function_.return_type_ = new IRVoidType();
        this.global_function_.allocate_block.Add(new BranchStatement(new Label(this.global_function_.entry_block.identifier_)));
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
        new_function.parameters_.add(new Register(new IRPointerType(new IRIntType(8)), new_function.current_register_id++));
        this.global_definition_.functions_.add(new_function);
        //void println(string str);
        new_function = new Function("println");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRVoidType();
        new_function.parameters_.add(new Register(new IRPointerType(new IRIntType(8)), new_function.current_register_id++));
        this.global_definition_.functions_.add(new_function);
        //void printInt(int n);
        new_function = new Function("printInt");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRVoidType();
        new_function.parameters_.add(new Register(new IRIntType(32), new_function.current_register_id++));
        this.global_definition_.functions_.add(new_function);
        // void printlnInt(int n);
        new_function = new Function("printlnInt");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRVoidType();
        new_function.parameters_.add(new Register(new IRIntType(32), new_function.current_register_id++));
        this.global_definition_.functions_.add(new_function);
        // string getString();
        new_function = new Function("getString");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRPointerType(new IRIntType(8));
        this.global_definition_.functions_.add(new_function);
        // int getInt();
        new_function = new Function("getInt");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRIntType(32);
        this.global_definition_.functions_.add(new_function);
        // string toString(int i);
        new_function = new Function("toString");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRPointerType(new IRIntType(8));
        new_function.parameters_.add(new Register(new IRIntType(32), new_function.current_register_id++));
        this.global_definition_.functions_.add(new_function);
        // string
        this.global_scope_ = (GlobalScope) this.global_scope_.GetClassScope(new Locate(0, 0), "string");
        // int str.length();
        new_function = new Function("length");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRIntType(32);
        new_function.parameters_.add(new Register(new IRPointerType(new IRIntType(8)),  new_function.current_register_id++));
        this.global_definition_.functions_.add(new_function);
        // string str.substring(int left,int right);
        new_function = new Function("substring");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRPointerType(new IRIntType(8));
        new_function.parameters_.add(new Register(new IRPointerType(new IRIntType(8)), new_function.current_register_id++));
        new_function.parameters_.add(new Register(new IRIntType(32), new_function.current_register_id++));
        new_function.parameters_.add(new Register(new IRIntType(32), new_function.current_register_id++));
        this.global_definition_.functions_.add(new_function);
        // int str.parseInt();
        new_function = new Function("parseInt");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRIntType(32);
        new_function.parameters_.add(new Register(new IRPointerType(new IRIntType(8)), new_function.current_register_id++));
        this.global_definition_.functions_.add(new_function);
        // int str.ord(int pos);
        new_function = new Function("ord");
        new_function.is_built_in = true;
        new_function.return_type_ = new IRIntType(32);
        new_function.parameters_.add(new Register(new IRPointerType(new IRIntType(8)), new_function.current_register_id++));
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
        if(this.is_class_def){
            //throw new RuntimeException();
            Register tmp_register = new Register(new IRPointerType(this.current_class_type), this.current_function_.current_register_id++);
            this.current_function_.parameterIDs_.add("_class_pointer");
            this.current_function_.parameters_.add(tmp_register);
        }
        node.parameter_def.accept(this);
        if(this.is_class_def){
            Register class_register = this.current_function_.parameters_.get(0);
            Register tmp_register = new Register(new IRPointerType(class_register.type_), this.current_function_.current_register_id++);
            tmp_register.assignable_ = true;
            this.current_function_.allocations_.add(new AllocateStatement(tmp_register, class_register.type_));
            this.current_block_.Add(new StoreStatement(tmp_register.type_, class_register, tmp_register));
            this.scope_.entities_.put(this.current_function_.parameterIDs_.get(0), tmp_register);
            Register new_register = new Register(((IRPointerType) tmp_register.type_).type_, this.current_function_.current_register_id++);
            this.current_block_.Add(new LoadStatement(new_register.type_, tmp_register, new_register));
            this.current_class_ = new_register;
        }
        for(int i = 1; i < this.current_function_.parameters_.size(); i++){
            Register it = this.current_function_.parameters_.get(i);
            IRPointerType new_IRPointerType = new IRPointerType(it.type_);
            Register tmp_register = new Register(new_IRPointerType, this.current_function_.current_register_id++);
            tmp_register.assignable_ = true;
            AllocateStatement new_allocation = new AllocateStatement(tmp_register, it.type_);
            this.current_function_.allocations_.add(new_allocation);
            StoreStatement new_store = new StoreStatement(tmp_register.type_, it, tmp_register);
            this.current_block_.Add(new_store);
            this.scope_.entities_.put(this.current_function_.parameterIDs_.get(i), tmp_register);
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
        //throw new RuntimeException();
        node.expression.accept(this);
        if(this.current_entity_.assignable_){
            Register new_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
            this.current_block_.Add(new LoadStatement(new_register.type_, this.current_entity_, new_register));
            this.current_entity_ = new_register;
        }
        if(this.current_entity_ instanceof Register) TypeConverse((Register) this.current_entity_, new IRIntType(1));
        Label true_label = new Label(this.current_function_.identifier_ + "_" + (this.current_function_.current_register_id - 1) + "_true");
        Block true_block = new Block(true_label.identifier_);
        Label false_label = new Label(this.current_function_.identifier_ + "_" + (this.current_function_.current_register_id - 1) + "_false");
        Block false_block = new Block(false_label.identifier_);
        Label out_label = new Label(this.current_function_.identifier_ + "_" + (this.current_function_.current_register_id - 1) + "_out");
        Block out_block = new Block(out_label.identifier_);
        if(node.false_statement == null){
            this.current_block_.Add(new BranchStatement(this.current_entity_, true_label, out_label));
            this.current_block_ = true_block;
            this.scope_ = new Scope(this.scope_);
            node.true_statement.accept(this);
            this.current_block_.Add(new BranchStatement(out_label));
            this.scope_ = this.scope_.parent_scope;
            this.current_block_ = out_block;
            this.current_function_.blocks_.add(true_block);
            this.current_function_.blocks_.add(out_block);
        }
        else{
            this.current_block_.Add(new BranchStatement(this.current_entity_, true_label, false_label));
            this.current_block_ = true_block;
            this.scope_ = new Scope(this.scope_);
            node.true_statement.accept(this);
            this.current_block_.Add(new BranchStatement(out_label));
            this.current_block_ = false_block;
            node.false_statement.accept(this);
            this.current_block_.Add(new BranchStatement(out_label));
            this.scope_ = this.scope_.parent_scope;
            this.current_block_ = out_block;
            this.current_function_.blocks_.add(true_block);
            this.current_function_.blocks_.add(false_block);
            this.current_function_.blocks_.add(out_block);
        }
    }

    @Override
    public void visit(ForStatementNode node){
        this.scope_ = new Scope(this.scope_);
        if(node.init_statement != null) node.init_statement.accept(this);
        Label condition_label = new Label(this.current_function_.identifier_ + "_" + (this.current_function_.current_register_id - 1) + "_condition");
        Block condition_block = new Block(condition_label.identifier_);
        Label stepping_label = new Label(this.current_function_.identifier_ + "_" + (this.current_function_.current_register_id - 1) + "_stepping");
        Block stepping_block = new Block(stepping_label.identifier_);
        Label body_label = new Label(this.current_function_.identifier_ + "_" + (this.current_function_.current_register_id - 1) + "_body");
        Block body_block = new Block(body_label.identifier_);
        Label out_label = new Label(this.current_function_.identifier_ + "_" + (this.current_function_.current_register_id - 1) + "_out");
        Block out_block = new Block(out_label.identifier_);
        this.stepping_labels_.add(stepping_label);
        this.condition_labels_.add(condition_label);
        this.breakout_labels_.add(out_label);
        if(node.for_condition != null){
            this.current_block_.Add(new BranchStatement(condition_label));
            this.current_block_ = condition_block;
            node.for_condition.accept(this);
            if(this.current_entity_.assignable_){
                Register new_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(new_register.type_, this.current_entity_, new_register));
                this.current_entity_ = new_register;
            }
            this.current_block_.Add(new BranchStatement(this.current_entity_, body_label, out_label));
            this.current_function_.blocks_.add(condition_block);
        }
        else current_block_.Add(new BranchStatement(body_label));
        this.current_block_ = body_block;
        this.current_function_.blocks_.add(this.current_block_);
        node.statement.accept(this);
        if(node.step_statement != null){
            this.current_block_.Add(new BranchStatement(stepping_label));
            this.current_block_ = stepping_block;
            node.step_statement.accept(this);
            if(node.for_condition != null) this.current_block_.Add(new BranchStatement(condition_label));
            else this.current_block_.Add(new BranchStatement(body_label));
            this.current_function_.blocks_.add(stepping_block);
        }
        else{
            if(node.for_condition != null) this.current_block_.Add(new BranchStatement(condition_label));
            else this.current_block_.Add(new BranchStatement(body_label));
        }
        this.stepping_labels_.pop();
        this.condition_labels_.pop();
        this.breakout_labels_.pop();
        this.current_block_ = out_block;
        this.current_function_.blocks_.add(this.current_block_);
        this.scope_ = this.scope_.parent_scope;
    }

    @Override
    public void visit(ForInitNode node){
        if(node.variable_def != null) node.variable_def.accept(this);
        if(node.init_expression != null) node.init_expression.accept(this);
    }

    @Override
    public void visit(ForConditionNode node){
        node.condition.accept(this);
        if(this.current_entity_ instanceof Register) TypeConverse((Register) this.current_entity_, new IRIntType(1));
    }

    @Override
    public void visit(SteppingNode node){
        node.stepping.accept(this);
    }

    @Override
    public void visit(WhileStatementNode node){
        Label condition_label = new Label(this.current_function_.identifier_ + "_" + (this.current_function_.current_register_id - 1) + "_condition");
        Block condition_block = new Block(condition_label.identifier_);
        this.current_block_.Add(new BranchStatement(condition_label));
        this.current_block_ = condition_block;
        node.condition.accept(this);
        if(this.current_entity_ instanceof Register) TypeConverse((Register) this.current_entity_, new IRIntType(1));
        Label body_label = new Label(this.current_function_.identifier_ + "_" + (this.current_function_.current_register_id - 1) + "_body");
        Block body_block = new Block(body_label.identifier_);
        Label out_label = new Label(this.current_function_.identifier_ + "_" + (this.current_function_.current_register_id - 1) + "_out");
        Block out_block = new Block(out_label.identifier_);
        this.current_block_.Add(new BranchStatement(this.current_entity_, body_label, out_label));
        this.current_function_.blocks_.add(condition_block);
        Label stepping_label = new Label(this.current_function_.identifier_ + "_" + (this.current_function_.current_register_id - 1) + "_stepping");
        this.stepping_labels_.add(stepping_label);
        this.condition_labels_.add(condition_label);
        this.breakout_labels_.add(out_label);
        this.current_block_ = body_block;
        this.scope_ = new Scope(this.scope_);
        node.statement.accept(this);
        this.scope_ = this.scope_.parent_scope;
        this.current_block_.Add(new BranchStatement(condition_label));
        this.current_function_.blocks_.add(body_block);
        this.stepping_labels_.pop();
        this.condition_labels_.pop();
        this.breakout_labels_.pop();
        this.current_block_ = out_block;
        this.current_function_.blocks_.add(this.current_block_);
    }

    @Override
    public void visit(BreakStatementNode node){
        this.current_block_.Add(new BranchStatement(this.breakout_labels_.peek()));
    }

    @Override
    public void visit(ContinueStatementNode node){
        //throw new RuntimeException();
        if(this.stepping_labels_.peek() != null){
            this.current_block_.Add(new BranchStatement(this.stepping_labels_.peek()));
        }
        else this.current_block_.Add(new BranchStatement(this.condition_labels_.peek()));
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
        this.is_class_def = true;
        this.scope_ = ((GlobalScope) this.scope_).GetClassScope(node.position, node.name);
        this.global_scope_ = (GlobalScope) this.scope_;
        this.current_class_type = new IRClassType(node.name);
        node.variables.forEach(it -> it.accept(this));
        if(node.constructor != null) node.constructor.accept(this);
        else{ // add default constructor
            this.current_function_ = new Function(node.name);
            this.current_function_.return_type_ = new IRVoidType();
            Register new_register = new Register(new IRPointerType(this.current_class_type), this.current_function_.current_register_id++);
            this.current_function_.parameters_.add(new_register);
            this.current_function_.parameterIDs_.add("class_" + node.name);
            Register tmp_register = new Register(new IRPointerType(new_register.type_), this.current_function_.current_register_id++);
            this.current_function_.allocations_.add(new AllocateStatement(tmp_register, new_register.type_));
            this.current_block_ = this.current_function_.entry_block;
            this.current_block_.Add(new StoreStatement(tmp_register.type_, new_register, tmp_register));
            this.current_block_.Add(new BranchStatement(new Label(this.current_function_.return_block.identifier_)));
            this.current_block_ = this.current_function_.return_block;
            this.current_block_.Add(new ReturnStatement(new Register(new IRVoidType(), 0)));
            this.current_block_ = this.current_function_.allocate_block;
            for(AllocateStatement it : this.current_function_.allocations_) this.current_block_.Add(it);
            this.current_block_.Add(new BranchStatement(new Label(this.current_function_.entry_block.identifier_)));
            this.current_function_.blocks_.add(this.current_function_.return_block);
            this.global_definition_.functions_.add(this.current_function_);
            this.current_function_ = null;
            this.current_block_ = null;
        }
        node.functions.forEach(it -> it.accept(this));
        this.global_definition_.global_def_statements.add(new StructDefStatement(this.current_class_type));
        this.global_scope_ = (GlobalScope) this.global_scope_.parent_scope;
        this.scope_ = this.scope_.parent_scope;
        this.global_scope_.class_types_.put(node.name, this.current_class_type);
        this.current_class_type = null;
        this.is_class_def = false;
    }

    @Override
    public void visit(ClassConstructorNode node){
        //throw new RuntimeException();
        Function class_constructor = new Function(node.name);
        class_constructor.return_type_ = new IRVoidType();
        this.current_function_ = class_constructor;
        this.current_block_ = this.current_function_.entry_block;
        // Register
        this.current_function_.parameterIDs_.add("_class_pointer");
        Register parameter_register = new Register(new IRPointerType(this.current_class_type), this.current_function_.current_register_id++);
        this.current_function_.parameters_.add(parameter_register);
        Register tmp_register = new Register(new IRPointerType(parameter_register.type_), this.current_function_.current_register_id++);
        this.current_function_.allocations_.add(new AllocateStatement(tmp_register, parameter_register.type_));
        this.current_block_.Add(new StoreStatement(tmp_register.type_, parameter_register, tmp_register));
        // prepare
        this.current_function_name = node.name;
        this.is_returned = false;
        this.count_loop = 0;
        this.stepping_labels_ = new Stack<>();
        this.condition_labels_ = new Stack<>();
        this.breakout_labels_ = new Stack<>();
        this.current_class_ = parameter_register;
        node.statement_block.accept(this);
        this.current_class_ = null;
        this.current_block_.Add(new BranchStatement(new Label(this.current_function_.return_block.identifier_)));
        this.current_block_ = current_function_.return_block;
        this.current_block_.Add(new ReturnStatement(new Register(new IRVoidType(), 0)));
        this.current_function_.blocks_.add(this.current_function_.return_block);
        this.current_block_ = this.current_function_.allocate_block;
        for(AllocateStatement it : this.current_function_.allocations_){
            this.current_block_.Add(it);
        }
        this.global_definition_.functions_.add(class_constructor);
    }

    @Override
    public void visit(NewVariableNode node){
        throw new RuntimeException();
    }

    @Override
    public void visit(ExpressionListNode node){
        //throw new RuntimeException();
        if(!node.expressions.isEmpty()){
            int t = this.current_parameters_.size() - node.expressions.size();
            for(int i = 0; i < node.expressions.size(); i++){
                node.expressions.get(i).accept(this);
                if(this.current_entity_.assignable_){
                    Register tmp_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
                    this.current_block_.Add(new LoadStatement(tmp_register.type_, this.current_entity_, tmp_register));
                    this.current_entity_ = tmp_register;
                }
                if(this.current_entity_ instanceof Register){
                    TypeConverse((Register) this.current_entity_, this.current_parameters_.get(i + t));
                }
                this.current_function_call.parameters_.add(this.current_entity_);
            }
        }
    }

    @Override
    public void visit(ArrayExpressionNode node){
        //throw new RuntimeException();
        node.identifier.accept(this);
        Register tmp_register = (Register) this.current_entity_;
        Register array_register = new Register(((IRPointerType) tmp_register.type_).type_, this.current_function_.current_register_id++);
        this.current_block_.Add(new LoadStatement(array_register.type_, tmp_register, array_register));
        node.index.accept(this);
        Entity tmp_entity = this.current_entity_;
        if(this.current_entity_.assignable_){
            tmp_entity = new Register(new IRIntType(32), this.current_function_.current_register_id++);
            this.current_block_.Add(new LoadStatement(new IRIntType(32), this.current_entity_, tmp_entity));
        }
        Register return_register = new Register(array_register.type_, this.current_function_.current_register_id++);
        return_register.assignable_ = true;
        GetElementPtr ptr_stmt = new GetElementPtr(array_register, return_register);
        ptr_stmt.values.add(tmp_entity);
        this.current_block_.Add(ptr_stmt);
        this.current_entity_ = return_register;
    }

    @Override
    public void visit(BinaryExpressionNode node){
        /*
            BinaryStatement.IR_BINARY_OP cur_op;
            if(node.binary_op == BinaryExpressionNode.BINARY_OP.MULTIPLY) cur_op = BinaryStatement.IR_BINARY_OP.mul;
            else if(node.binary_op == BinaryExpressionNode.BINARY_OP.DIVIDE) cur_op = BinaryStatement.IR_BINARY_OP.sdiv;
            else cur_op = BinaryStatement.IR_BINARY_OP.srem;
            node.left_expression.accept(this);
            Type left_Type = node.left_expression.type;
            Entity left_entity = this.current_entity_;
            if(left_entity.assignable_){
                left_entity = new Register(((IRPointerType) left_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(left_entity.type_, this.current_entity_, left_entity));
            }
            node.right_expression.accept(this);
            Type right_Type = node.right_expression.type;
            Entity right_entity = this.current_entity_;
            if(right_entity.assignable_){
                right_entity = new Register(((IRPointerType) right_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(right_entity.type_, this.current_entity_, right_entity));
            }
            Register dest_entity = new Register(, this.current_function_.current_register_id++);
            BinaryStatement new_stmt = new BinaryStatement(cur_op, , left_entity, right_entity, dest_entity);
            this.current_block_.Add(new_stmt);
            this.current_entity_ = dest_entity;
         */
        if(node.binary_op == BinaryExpressionNode.BINARY_OP.DOT){
            //throw new RuntimeException();
            boolean return_function_call_flag = this.function_call_flag;
            this.function_call_flag = false;
            node.left_expression.accept(this); // identifier
            this.function_call_flag = return_function_call_flag;
            Type left_type = node.left_expression.type;
            if(left_type.dimension > 0){
                if(this.current_function_call != null){
                    Register tmp_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
                    this.current_block_.Add(new LoadStatement(tmp_register.type_, this.current_entity_, tmp_register));
                    this.current_function_call.parameters_.add(tmp_register);
                }
                Register return_class = this.current_class_;
                IRClassType return_class_type = this.current_class_type;
                GlobalScope return_global_scope = this.global_scope_;
                Scope return_scope = this.scope_;
                this.current_class_ = null;
                this.current_class_type = null;
                this.global_scope_ = (GlobalScope) this.global_scope_.GetClassScope(node.position, "_array");
                this.scope_ = this.global_scope_;
                node.right_expression.accept(this);
                this.global_scope_ = return_global_scope;
                this.scope_ = return_scope;
                this.current_class_ = return_class;
                this.current_class_type = return_class_type;
            }
            else if(left_type.type_ == Type.TYPE.THIS || left_type.type_ == Type.TYPE.CLASS){
                if(this.current_entity_.assignable_){
                    Register tmp_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
                    this.current_block_.Add(new LoadStatement(tmp_register.type_, this.current_entity_, tmp_register));
                    this.current_class_ = tmp_register;
                }
                else this.current_class_ = (Register) this.current_entity_;
                IRType tmp_IRType = ((IRPointerType) this.current_class_.type_).type_;
                String tmp_name = ((IRClassType) tmp_IRType).name_;
                if(this.current_function_call != null && this.function_call_flag){ // function call
                    this.current_function_call.name_ += " class" + tmp_name + "_";
                    this.current_function_call.parameters_.add(this.current_class_);
                    this.have_function_flag = true;
                }
                this.current_class_type = new IRClassType(tmp_name);
                GlobalScope return_global_scope = this.global_scope_;
                Scope return_scope = this.scope_;
                Register return_class = this.current_class_;
                IRClassType return_class_type = this.current_class_type;
                boolean return_have_function_flag = have_function_flag;
                this.global_scope_ = (GlobalScope) this.global_scope_.GetClassScope(node.position, tmp_name);
                this.scope_ = this.global_scope_;
                node.right_expression.accept(this);
                this.global_scope_ = return_global_scope;
                this.scope_ = return_scope;
                this.current_class_ = return_class;
                this.current_class_type = return_class_type;
                this.have_function_flag = return_have_function_flag;
            }
            else if(left_type.type_ == Type.TYPE.STRING){
                if(this.current_function_call != null){ // inn function call
                    if(this.current_entity_.assignable_){
                        Register tmp_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
                        this.current_block_.Add(new LoadStatement(tmp_register.type_, this.current_entity_, tmp_register));
                        this.current_function_call.parameters_.add(tmp_register);
                    }
                    else this.current_function_call.parameters_.add(this.current_entity_);
                }
                GlobalScope return_global_scope = this.global_scope_;
                Scope return_scope = this.scope_;
                this.global_scope_ = (GlobalScope) this.global_scope_.GetClassScope(node.position, "string");
                this.scope_ = this.global_scope_;
                node.right_expression.accept(this);
                this.global_scope_ = return_global_scope;
                this.scope_ = return_scope;
            }
        }
        else if(node.binary_op == BinaryExpressionNode.BINARY_OP.MULTIPLY
                || node.binary_op == BinaryExpressionNode.BINARY_OP.DIVIDE
                || node.binary_op == BinaryExpressionNode.BINARY_OP.MOD){ // int
            BinaryStatement.IR_BINARY_OP cur_op;
            if(node.binary_op == BinaryExpressionNode.BINARY_OP.MULTIPLY) cur_op = BinaryStatement.IR_BINARY_OP.mul;
            else if(node.binary_op == BinaryExpressionNode.BINARY_OP.DIVIDE) cur_op = BinaryStatement.IR_BINARY_OP.sdiv;
            else cur_op = BinaryStatement.IR_BINARY_OP.srem;
            IRIntType fixed_int_IRType = new IRIntType(32);
            node.left_expression.accept(this);
            Entity left_entity = this.current_entity_;
            if(left_entity.assignable_){
                left_entity = new Register(((IRPointerType) left_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(fixed_int_IRType, this.current_entity_, left_entity));
            }
            node.right_expression.accept(this);
            Entity right_entity = this.current_entity_;
            if(right_entity.assignable_){
                right_entity = new Register(((IRPointerType) right_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(fixed_int_IRType, this.current_entity_, right_entity));
            }
            Register dest_entity = new Register(fixed_int_IRType, this.current_function_.current_register_id++);
            BinaryStatement new_stmt = new BinaryStatement(cur_op, fixed_int_IRType, left_entity, right_entity, dest_entity);
            this.current_block_.Add(new_stmt);
            this.current_entity_ = dest_entity;
        }
        else if(node.binary_op == BinaryExpressionNode.BINARY_OP.PLUS){ // int / string
            BinaryStatement.IR_BINARY_OP cur_op;
            cur_op = BinaryStatement.IR_BINARY_OP.add;
            node.left_expression.accept(this);
            Type left_Type = node.left_expression.type;
            Entity left_entity = this.current_entity_;
            if(left_entity.assignable_){
                left_entity = new Register(((IRPointerType) left_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(left_entity.type_, this.current_entity_, left_entity));
            }
            node.right_expression.accept(this);
            Type right_Type = node.right_expression.type;
            Entity right_entity = this.current_entity_;
            if(right_entity.assignable_){
                right_entity = new Register(((IRPointerType) right_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(right_entity.type_, this.current_entity_, right_entity));
            }
            if(left_Type.type_ == Type.TYPE.INT){
                IRIntType fixed_int_IRType = new IRIntType(32);
                Register dest_entity = new Register(fixed_int_IRType, this.current_function_.current_register_id++);
                BinaryStatement new_stmt = new BinaryStatement(cur_op, fixed_int_IRType, left_entity, right_entity, dest_entity);
                this.current_block_.Add(new_stmt);
                this.current_entity_ = dest_entity;
            }
            else{ // string
                //throw new RuntimeException();
                TypeConverse((Register) left_entity, new IRPointerType(new IRIntType(8)));
                Register left_register = (Register) this.current_entity_;
                TypeConverse((Register) right_entity, new IRPointerType(new IRIntType(8)));
                Register right_register = (Register) this.current_entity_;
                Register tmp_register = new Register(new IRPointerType(new IRIntType(8)), this.current_function_.current_register_id++);
                FunctCallStatement new_function_call = new FunctCallStatement("string_add", tmp_register.type_, tmp_register);
                new_function_call.parameters_.add(left_register);
                new_function_call.parameters_.add(right_register);
                this.current_block_.Add(new_function_call);
                this.current_entity_ =tmp_register;
            }
        }
        else if(node.binary_op == BinaryExpressionNode.BINARY_OP.MINUS){ // int
            BinaryStatement.IR_BINARY_OP cur_op = BinaryStatement.IR_BINARY_OP.sub;
            IRIntType fixed_int_IRType = new IRIntType(32);
            node.left_expression.accept(this);
            Entity left_entity = this.current_entity_;
            if(left_entity.assignable_){
                left_entity = new Register(((IRPointerType) left_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(fixed_int_IRType, this.current_entity_, left_entity));
            }
            node.right_expression.accept(this);
            Entity right_entity = this.current_entity_;
            if(right_entity.assignable_){
                right_entity = new Register(((IRPointerType) right_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(fixed_int_IRType, this.current_entity_, right_entity));
            }
            Register dest_entity = new Register(fixed_int_IRType, this.current_function_.current_register_id++);
            BinaryStatement new_stmt = new BinaryStatement(cur_op, fixed_int_IRType, left_entity, right_entity, dest_entity);
            this.current_block_.Add(new_stmt);
            this.current_entity_ = dest_entity;
        }
        else if(node.binary_op == BinaryExpressionNode.BINARY_OP.LEFT_SHIFT
                || node.binary_op == BinaryExpressionNode.BINARY_OP.RIGHT_SHIFT){ // int
            BinaryStatement.IR_BINARY_OP cur_op;
            if(node.binary_op == BinaryExpressionNode.BINARY_OP.LEFT_SHIFT) cur_op = BinaryStatement.IR_BINARY_OP.shl;
            else cur_op = BinaryStatement.IR_BINARY_OP.ashr;
            IRIntType fixed_int_IRType = new IRIntType(32);
            node.left_expression.accept(this);
            Entity left_entity = this.current_entity_;
            if(left_entity.assignable_){
                left_entity = new Register(((IRPointerType) left_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(fixed_int_IRType, this.current_entity_, left_entity));
            }
            node.right_expression.accept(this);
            Entity right_entity = this.current_entity_;
            if(right_entity.assignable_){
                right_entity = new Register(((IRPointerType) right_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(fixed_int_IRType, this.current_entity_, right_entity));
            }
            Register dest_entity = new Register(fixed_int_IRType, this.current_function_.current_register_id++);
            BinaryStatement new_stmt = new BinaryStatement(cur_op, fixed_int_IRType, left_entity, right_entity, dest_entity);
            this.current_block_.Add(new_stmt);
            this.current_entity_ = dest_entity;
        }
        else if(node.binary_op == BinaryExpressionNode.BINARY_OP.LESS
                || node.binary_op == BinaryExpressionNode.BINARY_OP.LESS_EQUAL
                || node.binary_op == BinaryExpressionNode.BINARY_OP.GREATER
                || node.binary_op == BinaryExpressionNode.BINARY_OP.GREATER_EQUAL){ // int / string
            BinaryStatement.IR_BINARY_OP cur_op;
            if(node.binary_op == BinaryExpressionNode.BINARY_OP.LESS) cur_op = BinaryStatement.IR_BINARY_OP.slt;
            else if(node.binary_op == BinaryExpressionNode.BINARY_OP.LESS_EQUAL) cur_op = BinaryStatement.IR_BINARY_OP.sle;
            else if(node.binary_op == BinaryExpressionNode.BINARY_OP.GREATER) cur_op = BinaryStatement.IR_BINARY_OP.sgt;
            else cur_op = BinaryStatement.IR_BINARY_OP.sge;
            node.left_expression.accept(this);
            Type left_Type = node.left_expression.type;
            Entity left_entity = this.current_entity_;
            if(left_entity.assignable_){
                left_entity = new Register(((IRPointerType) left_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(left_entity.type_, this.current_entity_, left_entity));
            }
            node.right_expression.accept(this);
            Type right_Type = node.right_expression.type;
            Entity right_entity = this.current_entity_;
            if(right_entity.assignable_){
                right_entity = new Register(((IRPointerType) right_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(right_entity.type_, this.current_entity_, right_entity));
            }
            if(left_Type.type_ == Type.TYPE.INT){
                IRIntType fixed_bool_IRType = new IRIntType(1);
                Register dest_entity = new Register(fixed_bool_IRType, this.current_function_.current_register_id++);
                BinaryStatement new_stmt = new BinaryStatement(cur_op, fixed_bool_IRType, left_entity, right_entity, dest_entity);
                this.current_block_.Add(new_stmt);
                this.current_entity_ = dest_entity;
            }
            else{ // both string
                //throw new RuntimeException();
                TypeConverse((Register) left_entity, new IRPointerType(new IRIntType(8)));
                Register left_register = (Register) this.current_entity_;
                TypeConverse((Register) right_entity, new IRPointerType(new IRIntType(8)));
                Register right_register = (Register) this.current_entity_;
                Register return_register = new Register(new IRIntType(1), this.current_function_.current_register_id++);
                if(node.binary_op == BinaryExpressionNode.BINARY_OP.LESS){
                    FunctCallStatement new_function_call = new FunctCallStatement("string_less", return_register.type_, return_register);
                    new_function_call.parameters_.add(left_register);
                    new_function_call.parameters_.add(right_register);
                    this.current_block_.Add(new_function_call);
                }
                else if(node.binary_op == BinaryExpressionNode.BINARY_OP.LESS_EQUAL){
                    FunctCallStatement new_function_call = new FunctCallStatement("string_less", return_register.type_, return_register);
                    new_function_call.parameters_.add(left_register);
                    new_function_call.parameters_.add(right_register);
                    this.current_block_.Add(new_function_call);
                }
                else if(node.binary_op == BinaryExpressionNode.BINARY_OP.GREATER){
                    FunctCallStatement new_function_call = new FunctCallStatement("string_less", return_register.type_, return_register);
                    new_function_call.parameters_.add(left_register);
                    new_function_call.parameters_.add(right_register);
                    this.current_block_.Add(new_function_call);
                }
                else{
                    FunctCallStatement new_function_call = new FunctCallStatement("string_less", return_register.type_, return_register);
                    new_function_call.parameters_.add(left_register);
                    new_function_call.parameters_.add(right_register);
                    this.current_block_.Add(new_function_call);
                }
                this.current_entity_ = return_register;
            }
        }
        else if(node.binary_op == BinaryExpressionNode.BINARY_OP.EQUAL
                || node.binary_op == BinaryExpressionNode.BINARY_OP.NOT_EQUAL){ //
            BinaryStatement.IR_BINARY_OP cur_op;
            if(node.binary_op == BinaryExpressionNode.BINARY_OP.EQUAL) cur_op = BinaryStatement.IR_BINARY_OP.eq;
            else cur_op = BinaryStatement.IR_BINARY_OP.ne;
            node.left_expression.accept(this);
            Type left_Type = node.left_expression.type;
            Entity left_entity = this.current_entity_;
            if(left_entity.assignable_){
                left_entity = new Register(((IRPointerType) left_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(left_entity.type_, this.current_entity_, left_entity));
            }
            node.right_expression.accept(this);
            Type right_Type = node.right_expression.type;
            Entity right_entity = this.current_entity_;
            if(right_entity.assignable_){
                right_entity = new Register(((IRPointerType) right_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(right_entity.type_, this.current_entity_, right_entity));
            }
            if(left_Type.type_ == Type.TYPE.NULL || right_Type.type_ == Type.TYPE.NULL){
                Register return_register = new Register(new IRIntType(1), this.current_function_.current_register_id++);
                IRType tmp_IRType = (left_Type.type_ == Type.TYPE.NULL) ? right_entity.type_ : left_entity.type_;
                this.current_block_.Add(new BinaryStatement(cur_op, tmp_IRType, left_entity, right_entity, return_register));
                this.current_entity_ = return_register;
            }
            else{
                if(left_Type.type_ == Type.TYPE.STRING){
                    TypeConverse((Register) left_entity, new IRPointerType(new IRIntType(8)));
                    Register left_register = (Register) this.current_entity_;
                    TypeConverse((Register) right_entity, new IRPointerType(new IRIntType(8)));
                    Register right_register = (Register) this.current_entity_;
                    Register return_register = new Register(new IRIntType(1), this.current_function_.current_register_id++);
                    if(node.binary_op == BinaryExpressionNode.BINARY_OP.EQUAL){
                        FunctCallStatement new_function_call = new FunctCallStatement("string_equal", return_register.type_, return_register);
                        new_function_call.parameters_.add(left_register);
                        new_function_call.parameters_.add(right_register);
                        this.current_block_.Add(new_function_call);
                    }
                    else{
                        FunctCallStatement new_function_call = new FunctCallStatement("string_not_equal", return_register.type_, return_register);
                        new_function_call.parameters_.add(left_register);
                        new_function_call.parameters_.add(right_register);
                        this.current_block_.Add(new_function_call);
                    }
                    this.current_entity_ = return_register;
                }
                else{
                    Register return_register = new Register(new IRIntType(1), this.current_function_.current_register_id++);
                    this.current_block_.Add(new BinaryStatement(cur_op, left_entity.type_, left_entity, right_entity, return_register));
                    this.current_entity_ = return_register;
                }
            }
        }
        else if(node.binary_op == BinaryExpressionNode.BINARY_OP.AND
                || node.binary_op == BinaryExpressionNode.BINARY_OP.CARET
                || node.binary_op == BinaryExpressionNode.BINARY_OP.OR){ // int
            BinaryStatement.IR_BINARY_OP cur_op;
            if(node.binary_op == BinaryExpressionNode.BINARY_OP.AND) cur_op = BinaryStatement.IR_BINARY_OP.and;
            else if(node.binary_op == BinaryExpressionNode.BINARY_OP.CARET) cur_op = BinaryStatement.IR_BINARY_OP.xor;
            else cur_op = BinaryStatement.IR_BINARY_OP.or;
            IRIntType fixed_int_IRType = new IRIntType(32);
            node.left_expression.accept(this);
            Entity left_entity = this.current_entity_;
            if(left_entity.assignable_){
                left_entity = new Register(((IRPointerType) left_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(fixed_int_IRType, this.current_entity_, left_entity));
            }
            node.right_expression.accept(this);
            Entity right_entity = this.current_entity_;
            if(right_entity.assignable_){
                right_entity = new Register(((IRPointerType) right_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(fixed_int_IRType, this.current_entity_, right_entity));
            }
            Register dest_entity = new Register(fixed_int_IRType, this.current_function_.current_register_id++);
            BinaryStatement new_stmt = new BinaryStatement(cur_op, fixed_int_IRType, left_entity, right_entity, dest_entity);
            this.current_block_.Add(new_stmt);
            this.current_entity_ = dest_entity;
        }
        else if(node.binary_op == BinaryExpressionNode.BINARY_OP.AND_AND
                || node.binary_op == BinaryExpressionNode.BINARY_OP.OR_OR){ // bool
            //throw new RuntimeException();
            if(node.binary_op == BinaryExpressionNode.BINARY_OP.AND_AND){
                node.left_expression.accept(this);
                if(this.current_entity_.assignable_){
                    Register tmp_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
                    this.current_block_.Add(new LoadStatement(tmp_register.type_, this.current_entity_, tmp_register));
                    this.current_entity_ = tmp_register;
                }
                Label current_label = new Label(this.current_block_.identifier_);
                Label true_label = new Label(this.current_function_.identifier_ + " true (&&)");
                Block true_block = new Block(this.current_function_.identifier_ + " true (&&)");
                Label return_label = new Label(this.current_function_.identifier_ + " return (&&)");
                Block return_block = new Block(this.current_function_.identifier_ + " return (&&)");
                if(this.current_entity_ instanceof Constant){
                    if(((Constant) this.current_entity_).value_ == 1) this.current_block_.Add(new BranchStatement(true_label));
                    else this.current_block_.Add(new BranchStatement(return_label));
                }
                else{
                    TypeConverse((Register) this.current_entity_, new IRIntType(1));
                    this.current_block_.Add(new BranchStatement(this.current_entity_, true_label, return_label));
                }
                this.current_block_ = true_block;
                node.right_expression.accept(this);
                if(this.current_entity_.assignable_){
                    Register tmp_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
                    this.current_block_.Add(new LoadStatement(tmp_register.type_, this.current_entity_, tmp_register));
                    this.current_entity_ = tmp_register;
                }
                if(this.current_entity_ instanceof Register) TypeConverse((Register) this.current_entity_, new IRIntType(1));
                this.current_block_.Add(new BranchStatement(return_label));
                this.current_block_ = return_block;
                Register return_register = new Register(new IRIntType(1), this.current_function_.current_register_id++);
                PhiStatement new_phi = new PhiStatement(return_register, new IRIntType(1));
                new_phi.labels_.add(current_label);
                new_phi.values_.add(new Constant(new IRIntType(1), 0));
                new_phi.labels_.add(true_label);
                new_phi.values_.add(this.current_entity_);
                this.current_block_.Add(new_phi);
                this.current_function_.blocks_.add(true_block);
                this.current_function_.blocks_.add(return_block);
                this.current_entity_ = return_register;
            }
            else{
                node.left_expression.accept(this);
                if(this.current_entity_.assignable_){
                    Register tmp_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
                    this.current_block_.Add(new LoadStatement(tmp_register.type_, this.current_entity_, tmp_register));
                    this.current_entity_ = tmp_register;
                }
                Label current_label = new Label(this.current_block_.identifier_);
                Label return_label = new Label(this.current_function_.identifier_ + " return (||)");
                Block return_block = new Block(this.current_function_.identifier_ + " return (||)");
                Label false_label = new Label(this.current_function_.identifier_ + " false (||)");
                Block false_block = new Block(this.current_function_.identifier_ + " false (||)");
                if(this.current_entity_ instanceof Register) TypeConverse((Register) this.current_entity_, new IRIntType(1));
                this.current_block_.Add(new BranchStatement(this.current_entity_, return_label, false_label));
                this.current_block_ = false_block;
                node.right_expression.accept(this);
                if(this.current_entity_.assignable_){
                    Register tmp_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
                    this.current_block_.Add(new LoadStatement(tmp_register.type_, this.current_entity_, tmp_register));
                    this.current_entity_ = tmp_register;
                }
                if(this.current_entity_ instanceof Register) TypeConverse((Register) this.current_entity_, new IRIntType(1));
                this.current_block_.Add(new BranchStatement(return_label));
                this.current_block_ = return_block;
                Register return_register = new Register(new IRIntType(1), this.current_function_.current_register_id++);
                PhiStatement new_phi = new PhiStatement(return_register, new IRIntType(1));
                new_phi.labels_.add(current_label);
                new_phi.values_.add(new Constant(new IRIntType(1), 1));
                new_phi.labels_.add(false_label);
                new_phi.values_.add(this.current_entity_);
                this.current_block_.Add(new_phi);
                this.current_function_.blocks_.add(false_block);
                this.current_function_.blocks_.add(return_block);
                this.current_entity_ = return_register;
            }
        }
        else{ // ASSIGN
            node.left_expression.accept(this);
            Entity left_entity = this.current_entity_;
            node.right_expression.accept(this);
            Entity right_entity = this.current_entity_;
            if(right_entity.assignable_){
                right_entity = new Register(((IRPointerType) right_entity.type_).type_, this.current_function_.current_register_id++);
                this.current_block_.Add(new LoadStatement(right_entity.type_, this.current_entity_, right_entity));
            }
            if(right_entity instanceof Register) TypeConverse((Register) right_entity, ((IRPointerType) left_entity.type_).type_);
            this.current_block_.Add(new StoreStatement(((IRPointerType) left_entity.type_).type_, this.current_entity_, left_entity));
            this.current_entity_ = right_entity;
        }
        /*
        public enum BINARY_OP{DOT,
        MULTIPLY,DIVIDE,MOD,
        PLUS,MINUS,
        LEFT_SHIFT,RIGHT_SHIFT,
        LESS,LESS_EQUAL,GREATER,GREATER_EQUAL,
        EQUAL,NOT_EQUAL,
        AND,CARET,OR,
        AND_AND,OR_OR,
        ASSIGN}
         */
    }

    @Override
    public void visit(BracketExpressionNode node){}

    @Override
    public void visit(FunctionCallExpressionNode node){
        //throw new RuntimeException();
        boolean return_function_call_flag = this.function_call_flag;
        this.function_call_flag = true;
        FunctCallStatement return_function_call_stmt = this.current_function_call;
        ArrayList<IRType> return_parameters = this.current_parameters_;
        this.current_function_call = new FunctCallStatement();
        node.identifier.accept(this);
        this.function_call_flag = return_function_call_flag;
        node.expression_list.accept(this);
        if(this.current_function_call.name_.equals("size")){
            Register tmp_register = (Register) this.current_function_call.parameters_.get(0);
            Register head_register = new Register(new IRPointerType(new IRIntType(32)), this.current_function_.current_register_id++);
            Register size_register = new Register(new IRPointerType(new IRIntType(32)), this.current_function_.current_register_id++);
           this.current_block_.Add(new BitcastStatement(tmp_register, head_register, head_register.type_));
            GetElementPtr tmp_ptr_stmt = new GetElementPtr(head_register, size_register);
            tmp_ptr_stmt.values.add(new Constant(new IRIntType(8), -1));
            this.current_block_.Add(tmp_ptr_stmt);
            Register return_register = new Register(new IRIntType(32), this.current_function_.current_register_id++);
            this.current_block_.Add(new LoadStatement(return_register.type_, size_register, return_register));
            this.current_entity_ = return_register;
        }
        else{
            Register return_register = new Register(this.current_function_call.return_type_, this.current_function_.current_register_id++);
            this.current_function_call.register_ = return_register;
            this.current_block_.Add(this.current_function_call);
            this.current_entity_ = return_register;
        }
        this.current_function_call = return_function_call_stmt;
        this.current_parameters_ = return_parameters;
    }

    @Override
    public void visit(PrefixExpressionNode node){
        //throw new RuntimeException();
        node.expr.accept(this);
        Register tmp_register = new Register(((IRPointerType) this.current_entity_.type_).type_, this.current_function_.current_register_id++);
        this.current_block_.Add(new LoadStatement(tmp_register.type_, this.current_entity_, tmp_register));
        Register return_register = new Register(new IRIntType(32), this.current_function_.current_register_id++);
        if(node.prefix_op == PrefixExpressionNode.PREFIX_OP.SELF_PLUS){
            this.current_block_.Add(new BinaryStatement(BinaryStatement.IR_BINARY_OP.add, tmp_register.type_, tmp_register, new Constant(new IRIntType(32), 1), return_register));
        }
        else{
            this.current_block_.Add(new BinaryStatement(BinaryStatement.IR_BINARY_OP.sub, tmp_register.type_, tmp_register, new Constant(new IRIntType(32), 1), return_register));
        }
        this.current_block_.Add(new StoreStatement(tmp_register.type_, return_register, this.current_entity_));
        this.current_entity_.assignable_ = true;
    }

    @Override
    public void visit(PostfixExpressionNode node){
        //throw new RuntimeException();
        node.expr.accept(this);
        Register tmp_register = new Register(((IRPointerType)current_entity_.type_).type_, this.current_function_.current_register_id++);
        this.current_block_.Add(new LoadStatement(tmp_register.type_, this.current_entity_, tmp_register));
        Register return_register = new Register(new IRIntType(32), this.current_function_.current_register_id++);
        if(node.postfix_op == PostfixExpressionNode.POSTFIX_OP.SELF_PLUS){
            this.current_block_.Add(new BinaryStatement(BinaryStatement.IR_BINARY_OP.add, tmp_register.type_, tmp_register, new Constant(new IRIntType(32), 1), return_register));
        }
        else{
            this.current_block_.Add(new BinaryStatement(BinaryStatement.IR_BINARY_OP.sub, tmp_register.type_, tmp_register, new Constant(new IRIntType(32), 1), return_register));
        }
        this.current_block_.Add(new StoreStatement(tmp_register.type_, return_register, this.current_entity_));
        this.current_entity_ = tmp_register;
    }

    @Override
    public void visit(UnaryExpressionNode node){
        //throw new RuntimeException();
        node.expr.accept(this);
        if(this.current_entity_.assignable_){
            Register tmp_register = new Register(((IRPointerType)this.current_entity_.type_).type_, this.current_function_.current_register_id++);
            this.current_block_.Add(new LoadStatement(tmp_register.type_, this.current_entity_, tmp_register));
            this.current_entity_ = tmp_register;
        }
        if(node.unary_op == UnaryExpressionNode.UNARY_OP.PLUS){
            Register return_register = new Register(new IRIntType(32), this.current_function_.current_register_id++);
            this.current_block_.Add(new BinaryStatement(BinaryStatement.IR_BINARY_OP.add, new IRIntType(32), new Constant(new IRIntType(32), 0), this.current_entity_, return_register));
            this.current_entity_ = return_register;
        }
        else if(node.unary_op == UnaryExpressionNode.UNARY_OP.MINUS){
            Register return_register = new Register(new IRIntType(32), this.current_function_.current_register_id++);
            this.current_block_.Add(new BinaryStatement(BinaryStatement.IR_BINARY_OP.sub, new IRIntType(32), new Constant(new IRIntType(32), 0), this.current_entity_, return_register));
            this.current_entity_ = return_register;
        }
        else if(node.unary_op == UnaryExpressionNode.UNARY_OP.TILDE){
            Register return_register = new Register(new IRIntType(32), this.current_function_.current_register_id++);
            this.current_block_.Add(new BinaryStatement(BinaryStatement.IR_BINARY_OP.xor, new IRIntType(32), this.current_entity_, new Constant(new IRIntType(32), -1), return_register));
            this.current_entity_ = return_register;
        }
        else if(node.unary_op == UnaryExpressionNode.UNARY_OP.NOT){
            if(this.current_entity_ instanceof Register) TypeConverse((Register) this.current_entity_, new IRIntType(1));
            Register return_register = new Register(new IRIntType(1), this.current_function_.current_register_id++);
            this.current_block_.Add(new BinaryStatement(BinaryStatement.IR_BINARY_OP.xor, new IRIntType(1), this.current_entity_, new Constant(new IRIntType(1), 1), return_register));
            this.current_entity_ = return_register;
        }
        else{
            throw new RuntimeException();
        }
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
            if(this.function_call_flag){
                Type return_type = this.global_scope_.GetFunctionReturnType(node.position, node.ID);
                if(this.current_class_ != null && this.global_scope_.GetFunctionInClass(node.ID) && !have_function_flag){
                    this.current_function_call.name_ += " class" + this.current_class_type.name_ + "_";
                    this.current_function_call.parameters_.add(this.current_class_);
                }
                if(return_type.type_ == Type.TYPE.VOID){
                    this.current_function_call.initial_name_ = node.ID;
                    this.current_function_call.name_ += node.ID;
                    this.current_function_call.is_void = true;
                }
                else{
                    IRType return_IRType = TypeConverse(return_type);
                    this.current_function_call.initial_name_ = node.ID;
                    this.current_function_call.name_ += node.ID;
                    this.current_function_call.return_type_ = return_IRType;
                }
                ArrayList<Type> tmp_parameters = this.global_scope_.GetFunctionParameters(node.position, this.current_function_call.initial_name_);
                this.current_parameters_ = new ArrayList<>();
                for(int i = 0; i < tmp_parameters.size(); i++){
                    this.current_parameters_.add(TypeConverse(tmp_parameters.get(i)));
                }
            }
            else{
                if(this.current_class_ == null) this.current_entity_ = this.scope_.GetEntity(true, node.ID);
                else{
                    Entity return_entity = scope_.GetEntity(true, node.ID);
                    if(return_entity == null){
                        Integer tmp_ID = this.global_scope_.memberIDs_.get(node.ID);
                        IRType tmp_IRType = TypeConverse(this.scope_.GetType(node.position, true, node.ID));
                        Register tmp_register = new Register(new IRPointerType(tmp_IRType), this.current_function_.current_register_id++);
                        GetElementPtr tmp_ptr_stmt = new GetElementPtr(this.current_class_, tmp_register);
                        tmp_ptr_stmt.values.add(new Constant(new IRIntType(32), 0));
                        tmp_ptr_stmt.values.add(new Constant(new IRIntType(32), tmp_ID));
                        this.current_block_.Add(tmp_ptr_stmt);
                        this.current_entity_ = tmp_register;
                        this.current_entity_.assignable_ = true;
                    }
                    else this.current_entity_ = return_entity;
                }
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
