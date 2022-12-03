package IR;

import basic.IRtypes.IRType;

public class BitcastStatement extends Statement{
    public Register from_reg_, to_reg_;
    public IRType type_;

    public BitcastStatement(Register _from_reg, Register _to_reg, IRType _type){
        super();
        this.from_reg_ = _from_reg;
        this.to_reg_ = _to_reg;
        this.type_ = _type;
    }

    @Override
    public String toString(){
        return this.to_reg_ + " = bitcast " + this.from_reg_.type_ + " " + this.from_reg_ + " to " + this.type_;
    }
}
