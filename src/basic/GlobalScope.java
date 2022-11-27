package basic;

import basic.IRtypes.IRType;
import basic.Scope;
import basic.error.SemanticError;
import basic.types.Type;
import java.util.HashMap;
import java.util.ArrayList;

public class GlobalScope extends Scope {
    public String ID;
    public HashMap<String,GlobalScope> ClassScope;//class name to its scope
    public HashMap<String,Scope> FunctionScope;//function name to its scope
    public HashMap<String,Type> FunctionReturnType;//function name to its return type
    public HashMap<String,ArrayList<Type>> FunctionParameters;//function name to its parameter list
    public HashMap<String, IRType> class_types_;

    public GlobalScope(){
        ID="Global";
        ClassScope=new HashMap<>();
        FunctionParameters=new HashMap<>();
        FunctionScope=new HashMap<>();
        FunctionReturnType=new HashMap<>();
        this.class_types_ = new HashMap<>();
    }

    public GlobalScope(Scope s,String id){
        super(s);
        ID=id;
        ClassScope=new HashMap<>();
        FunctionParameters=new HashMap<>();
        FunctionScope=new HashMap<>();
        FunctionReturnType=new HashMap<>();
        this.class_types_ = new HashMap<>();
    }

    public void DefineClass(Locate l,String name,GlobalScope s){
        if(ClassScope.containsKey(name)) throw new SemanticError(l,"class "+name+" redefined");
        else if(FunctionScope.containsKey(name)||super.IsDefined(false,name)) throw new SemanticError(l,"identifier "+name+" reused");
        else ClassScope.put(name,s);
    }
    
    public Scope GetClassScope(Locate l,String name){
        if(ClassScope.containsKey(name)) return ClassScope.get(name);
        else if(parent_scope!=null) return ((GlobalScope)parent_scope).GetClassScope(l,name);
        else throw new SemanticError(l,"class "+name+" not found");
    }
    
    public void DefineFunction(Locate l,String name,Scope s,Type return_type,ArrayList<Type> parameters){
        if(FunctionScope.containsKey(name)) throw new SemanticError(l,"function "+name+" redefined");
        else if(ClassScope.containsKey(name)) throw new SemanticError(l,"identifier "+name+" reused");
        else{
            FunctionScope.put(name,s);
            FunctionReturnType.put(name,return_type);
            FunctionParameters.put(name,parameters);
        }
    }
    
    public Scope GetFunctionScope(Locate l,String name){
        if(FunctionScope.containsKey(name)) return FunctionScope.get(name);
        else if(parent_scope!=null) return ((GlobalScope)parent_scope).GetFunctionScope(l,name);
        else throw new SemanticError(l,"function "+name+" not found");
    }
    
    public Type GetFunctionReturnType(Locate l,String name){
        if(FunctionReturnType.containsKey(name)) return FunctionReturnType.get(name);
        else if(parent_scope!=null) return ((GlobalScope)parent_scope).GetFunctionReturnType(l,name);
        else throw new SemanticError(l,"function "+name+" not found");
    }
    
    public ArrayList<Type> GetFunctionParameters(Locate l,String name){
        if(FunctionParameters.containsKey(name)) return FunctionParameters.get(name);
        else if(parent_scope!=null) return ((GlobalScope)parent_scope).GetFunctionParameters(l,name);
        else throw new SemanticError(l,"function "+name+" not found");
    }

    public boolean ExistClass(boolean is_forward,String name){
        if(ClassScope.containsKey(name)) return true;
        else if(parent_scope!=null&&is_forward) return ((GlobalScope)parent_scope).ExistClass(true,name);
        else return false;
    }

    public boolean ExistFunction(boolean is_forward,String name){
        if(FunctionScope.containsKey(name)) return true;
        else if(parent_scope!=null&&is_forward) return ((GlobalScope)parent_scope).ExistFunction(true,name);
        else return false;
    }

    @Override
    public boolean IsDefined(boolean is_forward,String name){
        if(FunctionScope.containsKey(name)) return true;
        else return super.IsDefined(is_forward,name);
    }

    public boolean GetFunctionInClass(String s){
        return FunctionReturnType.containsKey(s);
    }

    public IRType GetIRType(String _name){
        if(this.class_types_.containsKey(_name)) return this.class_types_.get(_name);
        else if(this.parent_scope != null) return ((GlobalScope) this.parent_scope).GetIRType(_name);
        else return null;
    }
}
