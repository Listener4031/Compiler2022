package frontend;

import AST.*;
import AST.ProgramNode;
import basic.GlobalScope;
import basic.Scope;
import basic.error.SemanticError;
import basic.types.*;

import java.util.ArrayList;

public class SymbolCollector implements ASTVisitor {
    public GlobalScope global_scope=null;
    public Scope scope=null;
    public Type type_=null;
    public ArrayList<Type> parameters;

    public SymbolCollector(Scope s){
        global_scope=(GlobalScope) s;
        scope=s;
    }

    @Override
    public void visit(ProgramNode node){
        node.def_list.forEach(it->{
            if(it instanceof ClassDefNode){
                GlobalScope new_global_scope=new GlobalScope(scope,((ClassDefNode)it).name);
                ((GlobalScope)scope).DefineClass(node.position,((ClassDefNode)it).name,new_global_scope);
            }
        });
        node.def_list.forEach(it->{
            if(it instanceof ClassDefNode){
                node.accept(this);
            }
        });
        node.def_list.forEach(it->{
            if(it instanceof FunctionDefNode){
                node.accept(this);
            }
        });

        //void print(string str); --function
        ArrayList<Type> parameters_=new ArrayList<>();
        Type t=new Type(Type.TYPE.STRING);//参数类型
        parameters_.add(t);
        t=new Type(Type.TYPE.VOID);
        t.assignable=false;//return_type
        FunctionType tt=new FunctionType("print",t);
        global_scope.DefineFunction(node.position,"print",new Scope(),tt,parameters_);
        //void println(string str);
        tt=new FunctionType("println",t);
        global_scope.DefineFunction(node.position,"println",new Scope(),tt,parameters_);
        //void printInt(int n);
        t=new Type(Type.TYPE.INT);
        parameters_=new ArrayList<>();
        parameters_.add(t);
        t=new Type(Type.TYPE.VOID);
        t.assignable=false;
        tt=new FunctionType("printInt",t);
        global_scope.DefineFunction(node.position,"printInt",new Scope(),tt,parameters_);
        //void printlnInt(int n);
        tt=new FunctionType("printlnInt",t);
        global_scope.DefineFunction(node.position,"printlnInt",new Scope(),tt,parameters_);
        //string getString();
        t=new Type(Type.TYPE.STRING);
        tt=new FunctionType("getString",t);
        global_scope.DefineFunction(node.position,"getString",new Scope(),tt,new ArrayList<>());
        //int getInt();
        t=new Type(Type.TYPE.INT);
        tt=new FunctionType("getInt",t);
        global_scope.DefineFunction(node.position,"getInt",new Scope(),tt,new ArrayList<>());
        //string toString(int i);
        parameters_=new ArrayList<>();
        parameters_.add(t);
        t=new Type(Type.TYPE.STRING);
        tt=new FunctionType("toString",t);
        global_scope.DefineFunction(node.position,"toString",new Scope(),tt,parameters_);
        //_array
        GlobalScope new_global_scope=new GlobalScope(scope,"_array");
        ((GlobalScope)scope).DefineClass(node.position,"_array",new_global_scope);
        //array.size()
        t=new Type(Type.TYPE.INT);
        tt=new FunctionType("size",t);
        new_global_scope.DefineFunction(node.position,"size",new Scope(),tt,new ArrayList<>());
        //string
        new_global_scope=new GlobalScope(scope,"string");
        ((GlobalScope)scope).DefineClass(node.position,"string",new_global_scope);
        //int str.length();
        tt=new FunctionType("length",t);
        new_global_scope.DefineFunction(node.position,"length",new Scope(),tt,new ArrayList<>());
        //string str.substring(int left,int right);
        t=new Type(Type.TYPE.INT);
        parameters_=new ArrayList<>();
        parameters_.add(t);
        parameters_.add(t);
        t=new Type(Type.TYPE.STRING);
        tt=new FunctionType("substring",t);
        new_global_scope.DefineFunction(node.position,"substring",new Scope(),tt,parameters_);
        //int str.parseInt();
        t=new Type(Type.TYPE.INT);
        tt=new FunctionType("parseInt",t);
        new_global_scope.DefineFunction(node.position,"parseInt",new Scope(),tt,new ArrayList<>());
        //int str.ord(int pos);
        parameters_=new ArrayList<>();
        parameters_.add(t);
        tt=new FunctionType("ord",t);
        new_global_scope.DefineFunction(node.position,"ord",new Scope(),tt,parameters_);
    }

    @Override
    public void visit(GlobalVariableDefNode node){}

    @Override
    public void visit(FunctionDefNode node){
        Type return_type=null;
        if(node.return_type.is_void){
            return_type=new Type(Type.TYPE.VOID);
            return_type.assignable=false;
        }
        else{
            node.return_type.variable_type.accept(this);
            return_type=type_;
        }
        Scope function_scope=new Scope(scope);
        scope=function_scope;
        scope=scope.parent_scope;
        node.parameter_def.accept(this);
        ((GlobalScope)scope).DefineFunction(node.position,node.name,function_scope,return_type,parameters);
    }

    @Override
    public void visit(StatementBlockNode node){}

    @Override
    public void visit(StatementNode node){}

    @Override
    public void visit(VariableDefNode node){
        node.type_.accept(this);
        Type variable_type=type_;
        node.variable_declarations.forEach(it->{
            if(global_scope.ExistClass(true,it.name)){
                throw new SemanticError(node.position,"variable has the same name with a class");
            }
            else{
                scope.DefineVariable(node.position,it.name,variable_type);
            }
        });
        node.type=variable_type;
    }

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
    public void visit(ClassDefNode node){
        scope=((GlobalScope)scope).GetClassScope(node.position,node.name);
        global_scope=(GlobalScope) scope;
        node.variables.forEach(it->it.accept(this));
        node.functions.forEach(it->it.accept(this));
        scope=scope.parent_scope;
        global_scope=(GlobalScope) global_scope.parent_scope;
    }

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
    public void visit(ParameterNode node){
        node.type_.accept(this);
        scope.DefineVariable(node.position,node.name,type_);
        parameters.add(type_);
    }

    @Override
    public void visit(FunctionParameterDefNode node){
        parameters=new ArrayList<>();
        node.parameters.forEach(it->it.accept(this));
    }

    @Override
    public void visit(BasicTypeNode node){}

    @Override
    public void visit(FunctionTypeNode node){}

    @Override
    public void visit(VariableTypeNode node){
        if(node.type_.is_basic_type){
            type_=new Type(node.type);
            type_.assignable=true;
        }
        else{
            if(global_scope.ExistClass(true,node.type_.ID)){
                type_=new Type(node.type);
                type_.assignable=true;
            }
            else throw new SemanticError(node.position,"class not found");
        }
        node.type=type_;
    }

    @Override
    public void visit(TypeNameNode node){}

    @Override
    public void visit(LambdaStatementNode node){}

}
