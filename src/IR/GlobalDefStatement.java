package IR;

import basic.IRtypes.IRPointerType;

public class GlobalDefStatement extends Statement{
    public Register register_;
    public Constant constant_;

    public GlobalDefStatement(Register _register, Constant _constant){
        super();
        this.register_ = _register;
        this.constant_ = _constant;
    }

    @Override
    public String toString(){
        return this.register_ + " = global " + ((IRPointerType) this.register_.type_).type_ + " " + this.constant_;
    }
}
