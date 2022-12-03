package IR;

import basic.IRtypes.IRType;

public class ZextStatement extends Statement{
    public Entity from_entity_, to_entity_;
    public IRType from_IRType_, to_IRType_;

    public ZextStatement(Entity _from_entity, Entity _to_entity, IRType _from_IRType, IRType _to_IRType){
        super();
        this.from_entity_ = _from_entity;
        this.from_IRType_ = _from_IRType;
        this.to_entity_ = _to_entity;
        this.to_IRType_ = _to_IRType;
    }

    @Override
    public String toString(){
        return this.to_entity_ + " = zext " + this.from_IRType_ + " " + this.from_entity_ + " to " + this.to_IRType_;
    }
}
