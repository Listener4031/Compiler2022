package IR;

import basic.IRtypes.IRType;

public class Entity {
    public IRType type_;
    public boolean assignable_;

    public Entity(IRType _type){
        this.type_ = _type;
        this.assignable_ = false;
    }
}
