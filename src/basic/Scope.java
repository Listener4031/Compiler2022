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
        parent_scope=null;
        count_member=0;
    }

    public Scope(Scope parent){
        members=new HashMap<>();
        IDs=new HashMap<>();
        parent_scope=parent;
        count_member=0;
    }

    

}
/*

    public void defineVariable (String name, Type type, position pos) {
        if (members.containsKey(name)) throw new semanticError("redefined variable " + name, pos) ;
        members.put(name, type) ;
        memberID.put (name, curNum ++) ;
    }

    public boolean containsVariable (String name, boolean lookUpon) {
        if (members.containsKey(name)) return true ;
        else if (parentScope != null && lookUpon) return parentScope.containsVariable(name, true) ;
        else return false ;
    }

    public Type getType (position pos, String name, boolean lookUpon) {
        if (members.containsKey(name)) return members.get(name) ;
        else if (parentScope != null && lookUpon) return parentScope.getType(pos, name, true) ;
        else throw new semanticError("identifier " + name + " not found", pos);
    }

    public entity getEntity (String name, boolean lookUpon) {
        if (entities.containsKey(name)) return entities.get(name) ;
        else if (parentScope != null && lookUpon) return parentScope.getEntity(name, true) ;
        return null ;
    }
}
 */