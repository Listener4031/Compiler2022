package IR;

import basic.IRtypes.IRType;

public class AllocateStatement extends Statement{
    public Register register_;
    public IRType type_;

    public AllocateStatement(Register _register, IRType _type){
        super();
        this.register_ = _register;
        this.type_ = _type;
    }

    @Override
    public String toString(){
        return this.register_ + " = alloca " + this.type_;
    }
}
