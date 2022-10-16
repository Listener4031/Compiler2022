package basic;

import basic.types.Type;
import basic.error.SemanticError;
import java.util.HashMap;

public class Scope {
    public HashMap<String,Type> members;
    public HashMap<String,Integer> IDs;
    public Scope parent_scope=null;
    Integer count_member=0;

    public Scope(){
        members=new HashMap<>();
        IDs=new HashMap<>();
    }

    public Scope(Scope parent){
        members=new HashMap<>();
        IDs=new HashMap<>();
        parent_scope=parent;
        count_member=0;
    }

    public boolean IsDefined(boolean is_forward,String name){//consider forward reference
        if(members.containsKey(name)) return true;
        else if(parent_scope!=null&&is_forward) return parent_scope.IsDefined(true,name);
        else return false;
    }

    public void DefineVariable(Locate l,String name,Type t){//try to define a new variable
        if(members.containsKey(name)) throw new SemanticError(l,"redefined variable "+name);
        members.put(name,t);
        IDs.put(name,count_member++);
    }

    public Type GetType(Locate l,boolean is_forward,String name){
        if(members.containsKey(name)) return members.get(name);
        else if(parent_scope!=null&&is_forward) return parent_scope.GetType(l,true,name);
        else throw new SemanticError(l,"identifier "+name+" not found");
    }

}