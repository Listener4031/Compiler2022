package IR;

import basic.IRtypes.IRType;

public class Alloca {
    public IRType type_;
    public Register register_;

    public Alloca (IRType _type, Register _register){
        this.type_ = _type;
        this.register_ = _register;
    }

    @Override
    public String toString(){
        return this.register_ + " = alloca " + this.type_;
    }

}
