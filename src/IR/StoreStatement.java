package IR;

import basic.IRtypes.IRPointerType;
import basic.IRtypes.IRType;

public class StoreStatement extends Statement{
    public IRType type_;
    public Entity from_, to_;

    public StoreStatement(IRType _type, Entity _from, Entity _to){
        super();
        this.type_ = _type;
        this.from_ = _from;
        this.to_ = _to;
    }

    @Override
    public String toString(){
        return "store " + ((IRPointerType) this.to_.type_).type_ + " " + this.from_ + ", " + this.to_.type_ + " " + this.to_;
    }
}
