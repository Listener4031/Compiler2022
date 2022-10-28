package frontend;

import AST.*;
import basic.*;
import basic.error.SemanticError;
import basic.types.ClassType;
import basic.types.FunctionType;
import basic.types.Type;

import java.util.ArrayList;

public class SemanticChecker implements ASTVisitor {
    public GlobalScope global_scope;
    public Scope scope;
    public Type current_type;
    public Type lambda_return_type=null;
    public boolean is_function_identifier;
    public int count_in_loop=0;
    public String name_;
    public boolean is_returned=false;

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
            if(_left_value.type_!= Type.TYPE.CLASS||!_left_value.name.equals(global_scope.ID)) throw new SemanticError(l,"the value cannot be assigned this");
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
        node.def_list.forEach(it-> it.accept(this));
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
            current_type=global_scope.GetFunctionReturnType(node.position,node.name);
            if(current_type.type_!= Type.TYPE.VOID&&!is_returned) throw new SemanticError(node.position,"non-void function should not return nothing");
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
    public void visit(VariableDefNode node){
        node.type_.accept(this);
        node.type=new Type(current_type);
        Type tmp_type=current_type;
        node.variable_declarations.forEach(it->{
            if(global_scope.ExistClass(true,it.name)) throw new SemanticError(it.position,"variable name duplicates with class");
            if(it.initialized){
                it.expression.accept(this);
                CheckAssignment(it.position,tmp_type,current_type);
            }
            scope.DefineVariable(it.position,it.name,tmp_type);
        });
    }

    @Override
    public void visit(VariableDeclarationNode node){}

    @Override
    public void visit(IfStatementNode node){
        node.expression.accept(this);
        node.expression.type=new Type(current_type);
        if(current_type.type_!= Type.TYPE.BOOL||current_type.dimension>0) throw new SemanticError(node.position,"the type should be bool");
        scope=new Scope(scope);
        node.true_statement.accept(this);
        scope=scope.parent_scope;
        if(node.false_statement!=null){
            scope=new Scope(scope);
            node.false_statement.accept(this);
            scope=scope.parent_scope;
        }
    }

    @Override
    public void visit(ForStatementNode node){
        count_in_loop++;
        scope=new Scope(scope);
        if(node.init_statement!=null) node.init_statement.accept(this);
        if(node.for_condition!=null) node.for_condition.accept(this);
        if(node.step_statement!=null) node.step_statement.accept(this);
        node.statement.accept(this);
        scope=scope.parent_scope;
        count_in_loop--;
    }

    @Override
    public void visit(ForInitNode node){
        if(node.variable_def!=null) node.variable_def.accept(this);
        if(node.init_expression!=null){
            node.init_expression.accept(this);
            node.init_expression.type=new Type(current_type);
        }
    }

    @Override
    public void visit(ForConditionNode node){
        node.condition.accept(this);
        node.condition.type=new Type(current_type);
        if(node.condition.type.type_!= Type.TYPE.BOOL||node.condition.type.dimension>0) throw new SemanticError(node.position,"type of condition should be bool");
    }

    @Override
    public void visit(SteppingNode node){
        node.stepping.accept(this);
    }

    @Override
    public void visit(WhileStatementNode node){
        node.condition.accept(this);
        if(current_type.type_!= Type.TYPE.BOOL||current_type.dimension>0) throw new SemanticError(node.position,"condition expression type should be bool");
        count_in_loop++;
        scope=new Scope(scope);
        node.statement.accept(this);
        scope=scope.parent_scope;
        count_in_loop--;
    }

    @Override
    public void visit(BreakStatementNode node){
        if(count_in_loop<=0) throw new SemanticError(node.position,"no loop to break");
    }

    @Override
    public void visit(ContinueStatementNode node){
        if(count_in_loop<=0) throw new SemanticError(node.position,"no loop to continue");
    }

    @Override
    public void visit(ReturnStatementNode node){
        if(node.expression==null) current_type=new Type(Type.TYPE.VOID,0,false);
        else node.expression.accept(this);
        Type return_type_;
        if(name_.equals("_lambda")){
            if(lambda_return_type==null) lambda_return_type=new Type(current_type);
            return_type_=lambda_return_type;
        }
        else return_type_=global_scope.GetFunctionReturnType(node.position,name_);
        if(return_type_.type_== Type.TYPE.VOID){
            if(current_type.type_!= Type.TYPE.VOID) throw new SemanticError(node.position,"non-void function should not return nothing");
        }
        else CheckAssignment(node.position,return_type_,current_type);
        is_returned=true;
    }

    @Override
    public void visit(ClassDefNode node){
        scope=((GlobalScope) scope).GetClassScope(node.position,node.name);
        global_scope=(GlobalScope) scope;
        if(node.constructor!=null){
            if(node.constructor.name.equals(node.name)) node.constructor.accept(this);
            else throw new SemanticError(node.position,"constructor error");
        }
        node.functions.forEach(it->{
            if(it.name.equals(node.name)) throw new SemanticError(node.position,"function name duplicates with class name");
            it.accept(this);
        });
        global_scope=(GlobalScope) global_scope.parent_scope;
        scope=scope.parent_scope;
    }

    @Override
    public void visit(ClassConstructorNode node){
        scope=new Scope(scope);
        Type t=new Type(node.name,0,false);
        t.name=node.name;
        global_scope.DefineFunction(node.position,node.name,scope,t,new ArrayList<>());
        name_=node.name;
        is_returned=false;
        count_in_loop=0;
        node.statement_block.accept(this);
        scope=scope.parent_scope;
    }

    @Override
    public void visit(NewVariableNode node){
        Type tmp_type;
        if(node.type_.is_basic_type) tmp_type=new Type(node.type_.basic_type.type_,0,true);
        else tmp_type=new Type(node.type_.ID,0,true);
        tmp_type.dimension=node.new_sizes.size();
        boolean exist_empty_bracket=false;
        boolean tag2=false;
        for(int i=0;i<node.new_sizes.size();i++){
            BracketExpressionNode tmp_node=node.new_sizes.get(i);
            if(tmp_node.expression_==null) exist_empty_bracket=true;
            else{
                if(exist_empty_bracket) throw new SemanticError(tmp_node.position,"array assignment error");
                tmp_node.expression_.accept(this);
                if(current_type.type_!= Type.TYPE.INT
                        ||current_type.dimension>0) throw new SemanticError(node.position,"type of index should be int");
                tag2=true;
            }
        }
        if(!tag2&&exist_empty_bracket) throw new SemanticError(node.position,"array assignment error");
        /*
        node.new_sizes.forEach(it->{
            if(it==null) throw new SemanticError(node.position,"true false?");
            if(it.expression_!=null){
                it.expression_.accept(this);
                if(current_type.type_!= Type.TYPE.INT
                        ||current_type.dimension>0) throw new SemanticError(node.position,"type of index should be int");
            }
        });
         */
        current_type=tmp_type;
        node.type=new Type(current_type);
    }

    @Override
    public void visit(ExpressionListNode node){
        node.expressions.forEach(it->it.accept(this));
    }

    @Override
    public void visit(ArrayExpressionNode node){
        boolean tmp_switch=is_function_identifier;
        is_function_identifier=false;
        node.index.accept(this);
        if(current_type.type_!= Type.TYPE.INT
                ||current_type.dimension>0) throw new SemanticError(node.index.position,"array index should be int");
        is_function_identifier=tmp_switch;
        node.index.type=new Type(current_type);
        node.identifier.accept(this);
        if(current_type.dimension<=0) throw new SemanticError(node.position,"dimension error");
        current_type.dimension--;
        node.type=new Type(current_type);
    }

    @Override
    public void visit(BinaryExpressionNode node){
        if(node.binary_op== BinaryExpressionNode.BINARY_OP.DOT){
            boolean tmp_switch=is_function_identifier;
            is_function_identifier=false;
            node.left_expression.accept(this);
            node.left_expression.type=new Type(current_type);
            is_function_identifier=tmp_switch;
            GlobalScope tmp_global_scope=global_scope;
            Type left_type=current_type;
            if(left_type.dimension>=1) global_scope=(GlobalScope) global_scope.GetClassScope(node.position,"_array");
            else if(left_type.type_== Type.TYPE.STRING) global_scope=(GlobalScope) global_scope.GetClassScope(node.position,"string");
            else if(left_type.is_class) global_scope=(GlobalScope) global_scope.GetClassScope(node.position,left_type.name);
            else if(left_type.type_!= Type.TYPE.THIS) throw new SemanticError(node.position,"dot operator cannot be used");
            Scope tmp_scope=scope;
            if(left_type.type_!=Type.TYPE.STRING) scope=global_scope;
            node.right_expression.accept(this);
            node.right_expression.type=new Type(current_type);
            global_scope=tmp_global_scope;
            scope=tmp_scope;
        }
        else{
            node.left_expression.accept(this);
            node.left_expression.type=new Type(current_type);
            Type left_type=current_type;
            node.right_expression.accept(this);
            node.right_expression.type=new Type(current_type);
            Type right_type=current_type;
            if(node.binary_op==BinaryExpressionNode.BINARY_OP.ASSIGN){
                CheckAssignment(node.position,left_type,right_type);
                current_type.assignable=false;
            }
            else if(node.binary_op== BinaryExpressionNode.BINARY_OP.MULTIPLY
                    ||node.binary_op== BinaryExpressionNode.BINARY_OP.DIVIDE
                    ||node.binary_op== BinaryExpressionNode.BINARY_OP.MOD){
                if(left_type.type_!= Type.TYPE.INT
                        ||left_type.dimension>0
                        ||right_type.dimension>0
                        ||right_type.type_!= Type.TYPE.INT) throw new SemanticError(node.position,"binary operator error");
                current_type=new Type(Type.TYPE.INT,0,false);
            }
            else if(node.binary_op== BinaryExpressionNode.BINARY_OP.PLUS){
                if(left_type.type_!= Type.TYPE.INT
                        ||left_type.dimension>0
                        ||right_type.dimension>0
                        ||right_type.type_!= Type.TYPE.INT){
                    if(left_type.type_== Type.TYPE.STRING&&right_type.type_== Type.TYPE.STRING
                            &&left_type.dimension==0&&right_type.dimension==0){
                        current_type=new Type(Type.TYPE.STRING,0,false);
                    }
                    else throw new SemanticError(node.position,"binary operator error");
                }
                else current_type=new Type(Type.TYPE.INT,0,false);
            }
            else if(node.binary_op== BinaryExpressionNode.BINARY_OP.MINUS){
                if(left_type.type_!= Type.TYPE.INT
                        ||left_type.dimension>0
                        ||right_type.dimension>0
                        ||right_type.type_!= Type.TYPE.INT) throw new SemanticError(node.position,"binary operator error");
                current_type=new Type(Type.TYPE.INT,0,false);
            }
            else if(node.binary_op== BinaryExpressionNode.BINARY_OP.LEFT_SHIFT
                    ||node.binary_op== BinaryExpressionNode.BINARY_OP.RIGHT_SHIFT){
                if(left_type.type_!= Type.TYPE.INT
                        ||left_type.dimension>0
                        ||right_type.dimension>0
                        ||right_type.type_!= Type.TYPE.INT) throw new SemanticError(node.position,"binary operator error");
                current_type=new Type(Type.TYPE.INT,0,false);
            }
            else if(node.binary_op== BinaryExpressionNode.BINARY_OP.LESS
                    ||node.binary_op== BinaryExpressionNode.BINARY_OP.LESS_EQUAL
                    ||node.binary_op== BinaryExpressionNode.BINARY_OP.GREATER
                    ||node.binary_op== BinaryExpressionNode.BINARY_OP.GREATER_EQUAL){
                if(left_type.type_!= Type.TYPE.INT
                        ||left_type.dimension>0
                        ||right_type.dimension>0
                        ||right_type.type_!= Type.TYPE.INT){
                    if(left_type.type_== Type.TYPE.STRING&&right_type.type_== Type.TYPE.STRING
                            &&left_type.dimension==0&&right_type.dimension==0){
                        current_type=new Type(Type.TYPE.BOOL,0,false);
                    }
                    else throw new SemanticError(node.position,"binary operator error");
                }
                else current_type=new Type(Type.TYPE.BOOL,0,false);
            }
            else if(node.binary_op== BinaryExpressionNode.BINARY_OP.EQUAL
                    ||node.binary_op== BinaryExpressionNode.BINARY_OP.NOT_EQUAL){
                //if(left_type.type_== Type.TYPE.NULL) throw new SemanticError(node.position,"left value should not be null");
                if(right_type.type_== Type.TYPE.NULL||left_type.type_==Type.TYPE.NULL){
                    if(right_type.type_== Type.TYPE.NULL){
                        if((left_type.type_== Type.TYPE.INT||left_type.type_== Type.TYPE.BOOL||left_type.type_== Type.TYPE.STRING)
                                &&left_type.dimension==0&&right_type.dimension==0){
                            throw new SemanticError(node.position,"the value cannot be compared with null");
                        }
                        else current_type=new Type(Type.TYPE.BOOL,0,false);
                    }
                    else{
                        if((right_type.type_== Type.TYPE.INT||right_type.type_== Type.TYPE.BOOL||right_type.type_== Type.TYPE.STRING)
                                &&right_type.dimension==0&&left_type.dimension==0){
                            throw new SemanticError(node.position,"the value cannot be compared with null");
                        }
                        else current_type=new Type(Type.TYPE.BOOL,0,false);
                    }
                }
                else{
                    if(left_type.dimension>0) throw new SemanticError(node.position,"array could only compare with null");
                    CheckTypeMatch(node.position,left_type,right_type);
                    current_type=new Type(Type.TYPE.BOOL,0,false);
                }
            }
            else if(node.binary_op== BinaryExpressionNode.BINARY_OP.AND
                    ||node.binary_op== BinaryExpressionNode.BINARY_OP.CARET
                    ||node.binary_op== BinaryExpressionNode.BINARY_OP.OR){
                if(left_type.type_!= Type.TYPE.INT
                        ||left_type.dimension>0
                        ||right_type.dimension>0
                        ||right_type.type_!= Type.TYPE.INT) throw new SemanticError(node.position,"binary operator error");
                current_type=new Type(Type.TYPE.INT,0,false);
            }
            else if(node.binary_op== BinaryExpressionNode.BINARY_OP.AND_AND
                    ||node.binary_op== BinaryExpressionNode.BINARY_OP.OR_OR){
                if(left_type.type_!= Type.TYPE.BOOL
                        ||left_type.dimension>0
                        ||right_type.dimension>0
                        ||right_type.type_!= Type.TYPE.BOOL) throw new SemanticError(node.position,"binary operator error");
                current_type=new Type(Type.TYPE.BOOL,0,false);
            }
            else throw new SemanticError(node.position,"true false?");
            /*
            public enum BINARY_OP{DOT,
        MULTIPLY,DIVIDE,MOD,                      * / %
        PLUS,MINUS,                               + -
        LEFT_SHIFT,RIGHT_SHIFT,                   <<  >>
        LESS,LESS_EQUAL,GREATER,GREATER_EQUAL,    < <= > >=
        EQUAL,NOT_EQUAL,                          == !=
        AND,CARET,OR,                             & ^ |
        AND_AND,OR_OR,                            && ||
        ASSIGN}                                   =
             */
        }
    }

    @Override
    public void visit(BracketExpressionNode node){
        if(node.expression_!=null){
            node.expression_.accept(this);
            node.type=new Type(current_type);
        }
    }

    @Override
    public void visit(FunctionCallExpressionNode node){
        is_function_identifier=true;
        node.identifier.accept(this);
        is_function_identifier=false;
        if(current_type.type_!= Type.TYPE.FUNCTION) throw new SemanticError(node.position,"fail to call function "+node.identifier.toString());
        ArrayList<Type> para=current_type.parameters;
        Type return_type_=current_type.return_type;
        if(para.size()!=node.expression_list.expressions.size()) throw new SemanticError(node.position,"size of parameters does not match");
        for(int i=0;i<para.size();i++){
            node.expression_list.expressions.get(i).accept(this);
            Type t=para.get(i);
            t.assignable=true;
            CheckAssignment(node.expression_list.position,t,current_type);
        }
        current_type=new Type(return_type_);
        current_type.assignable=false;
        node.type=new Type(current_type);
    }

    @Override
    public void visit(PrefixExpressionNode node){
        node.expr.accept(this);
        node.expr.type=new Type(current_type);
        if(current_type.type_!= Type.TYPE.INT||current_type.dimension>0) throw new SemanticError(node.position,"the type cannot use prefix operator");
        if(!current_type.assignable) throw new SemanticError(node.position,"the value is not assignable");
    }

    @Override
    public void visit(PostfixExpressionNode node){
        node.expr.accept(this);
        node.expr.type=new Type(current_type);
        if(current_type.type_!= Type.TYPE.INT||current_type.dimension>0) throw new SemanticError(node.position,"the type cannot use postfix operator");
        if(!current_type.assignable) throw new SemanticError(node.position,"the value is not assignable");
        current_type.assignable=false;
    }

    @Override
    public void visit(UnaryExpressionNode node){
        node.expr.accept(this);
        if(node.unary_op== UnaryExpressionNode.UNARY_OP.NOT){
            if(current_type.type_!= Type.TYPE.BOOL
                    ||current_type.dimension>0) throw new SemanticError(node.position,"the object cannot use unary operator");
        }
        else{
            if(current_type.type_!= Type.TYPE.INT
                    ||current_type.dimension>0) throw new SemanticError(node.position,"the object cannot use unary operator");
        }
        current_type.assignable=false;
        node.type=new Type(current_type);
        /*
        public enum UNARY_OP{PLUS,MINUS,TILDE,NOT}
         */
    }

    @Override
    public void visit(AtomExpressionNode node){
        if(node.atom_expr== AtomExpressionNode.ATOM_EXPR.THIS) current_type=new Type(Type.TYPE.THIS,0,false);
        else if(node.atom_expr== AtomExpressionNode.ATOM_EXPR.NULL) current_type=new Type(Type.TYPE.NULL,0,false);
        else if(node.atom_expr== AtomExpressionNode.ATOM_EXPR.UNSIGNED_INTEGER) current_type=new Type(Type.TYPE.INT,0,false);
        else if(node.atom_expr== AtomExpressionNode.ATOM_EXPR.BOOL_LITERAL) current_type=new Type(Type.TYPE.BOOL,0,false);
        else if(node.atom_expr== AtomExpressionNode.ATOM_EXPR.STRING_OBJECT) current_type=new Type(Type.TYPE.STRING,0,false);
        else{
            if(is_function_identifier){
                current_type=new Type(node.ID,null,null);
                current_type.return_type=global_scope.GetFunctionReturnType(node.position,node.ID);
                current_type.parameters=global_scope.GetFunctionParameters(node.position,node.ID);
            }
            else current_type=new Type(scope.GetType(node.position,true,node.ID));
        }
    }

    @Override
    public void visit(ParameterNode node){}

    @Override
    public void visit(FunctionParameterDefNode node){}

    @Override
    public void visit(BasicTypeNode node){}

    @Override
    public void visit(FunctionTypeNode node){}

    @Override
    public void visit(VariableTypeNode node){
        node.type_.accept(this);
        current_type.dimension=node.dimension;
        node.type=new Type(current_type);
    }

    @Override
    public void visit(TypeNameNode node){
        if(node.is_basic_type) current_type=new Type(node.basic_type.type_,0,true);
        else{
            if(!global_scope.ExistClass(true,node.ID)) throw new SemanticError(node.position,"class name not found");
            current_type=new Type(node.ID,0,true);
        }
    }

    @Override
    public void visit(LambdaStatementNode node){
        scope=new Scope(scope);
        ArrayList<Type> para=new ArrayList<>();
        if(node.parameter_def!=null){
            node.parameter_def.parameters.forEach(it->{
                it.type_.accept(this);
                scope.DefineVariable(it.position,it.name,current_type);
                para.add(current_type);
            });
        }
        Type tmp_type=lambda_return_type;
        int tmp_cnt=count_in_loop;
        String tmp_name=name_;
        boolean tmp_judge=is_returned;
        count_in_loop=0;
        name_="_lambda";
        is_returned=false;
        node.statement_block.accept(this);
        if(!is_returned) throw new SemanticError(node.position,"lambda statement should return something");
        Type t=new Type(tmp_type);
        lambda_return_type=tmp_type;
        count_in_loop=tmp_cnt;
        name_=tmp_name;
        is_returned=tmp_judge;
        scope=scope.parent_scope;
        if(node.expression_list.expressions.size()!=para.size()) throw new SemanticError(node.position,"parameter size does not match");
        for(int i=0;i<para.size();i++){
            node.expression_list.expressions.get(i).accept(this);
            tmp_type=para.get(i);
            CheckAssignment(node.position,tmp_type,current_type);
        }
        current_type=t;
        current_type.assignable=false;
        node.type=new Type(current_type);
    }

    @Override
    public void visit(ParentheseExpressionNode node){
        node.expression_.accept(this);
        node.expression_.type=new Type(current_type);
    }

}
