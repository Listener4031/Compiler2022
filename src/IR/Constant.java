package IR;

import basic.IRtypes.IRIntType;
import basic.IRtypes.IRType;

public class Constant extends Entity{
    public int value_;

    public Constant(Constant _constant){
        super(_constant.type_);
        this.value_ = _constant.value_;
    }

    public Constant(IRType _type, int _value){
        super(_type);
        this.value_ = _value;
    }

    @Override
    public String toString(){
        if(this.type_ instanceof IRIntType){
            if(((IRIntType) this.type_).width_ == 1){
                if(this.value_ == 1) return "true";
                else return "false";
            }
            else return Integer.toString(this.value_);
        }
        else return "null";
    }
}
