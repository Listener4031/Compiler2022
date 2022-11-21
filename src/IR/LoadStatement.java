package IR;

import basic.IRtypes.IRType;

public class LoadStatement extends Statement{
    public IRType type_;
    public Entity from_, to_;

    public LoadStatement(IRType _type, Entity _from, Entity _to){
        super();
        this.type_ = _type;
        this.from_ = _from;
        this.to_ = _to;
    }

    @Override
    public String toString(){
        return this.to_ + " = load " + this.to_.type_ + ", " + this.from_.type_ + " " + this.from_;
    }
}

