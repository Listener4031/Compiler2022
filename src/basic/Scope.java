package basic;

import IR.Entity;
import basic.types.Type;
import basic.error.SemanticError;
import java.util.HashMap;

public class Scope {
    public HashMap<String,Type> members;
    public Scope parent_scope = null;
    public HashMap<String, Entity> entities_;
    public HashMap<String, Integer> memberIDs_;
    Integer count_member_;

    public Scope(){
        this.members = new HashMap<>();
        this.entities_ = new HashMap<>();
        this.memberIDs_ = new HashMap<>();
        this.count_member_ = 0;
    }

    public Scope(Scope _parent){
        this.members = new HashMap<>();
        this.entities_ = new HashMap<>();
        this.memberIDs_ = new HashMap<>();
        this.count_member_ = 0;
        this.parent_scope = _parent;
    }

    public boolean IsDefined(boolean is_forward, String _name){//consider forward reference
        if(this.members.containsKey(_name)) return true;
        else if(this.parent_scope != null && is_forward) return this.parent_scope.IsDefined(true, _name);
        else return false;
    }

    public void DefineVariable(Locate l, String _name, Type _type){//try to define a new variable
        if(this.members.containsKey(_name)) throw new SemanticError(l, "redefined variable " + _name);
        this.members.put(_name, _type);
        this.memberIDs_.put(_name, this.count_member_);
        this.count_member_++;
    }

    public Type GetType(Locate l, boolean is_forward, String _name){
        if(this.members.containsKey(_name)) return this.members.get(_name);
        else if(this.parent_scope != null && is_forward) return this.parent_scope.GetType(l, true, _name);
        else throw new SemanticError(l, "identifier " + _name + " not found");
    }

    public Entity GetEntity(boolean is_forward, String _name){
        if(this.entities_.containsKey(_name)) return this.entities_.get(_name);
        else if(this.parent_scope != null && is_forward) return this.parent_scope.GetEntity(true, _name);
        else return null;
    }
}