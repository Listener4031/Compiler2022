package IR;

import basic.IRtypes.*;

public class Register extends Entity{
    public String identifier_;
    public boolean is_global;

    public Register(Register _register){
        super(_register.type_);
        this.identifier_ = _register.identifier_;
        this.is_global = _register.is_global;
    }

    public Register(IRType _type, int _identifier){
        super(_type);
        this.identifier_ = new String("reg_" + Integer.toString(_identifier));
        this.is_global = false;
    }

    public Register(IRType _type, String _identifier, boolean _is_global){
        super(_type);
        this.identifier_ = new String("reg_" + _identifier);
        this.is_global = _is_global;
    }

    @Override
    public String toString(){
        if(this.type_ instanceof IRNullType) return "null";
        else if(this.is_global) return "@" + this.identifier_;
        else return "%" + this.identifier_;
    }
}

