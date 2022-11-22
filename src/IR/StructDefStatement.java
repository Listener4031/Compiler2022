package IR;

import basic.IRtypes.IRClassType;
import basic.IRtypes.IRType;

public class StructDefStatement extends Statement{
    public IRClassType type_;

    public StructDefStatement(IRClassType _type){
        super();
        this.type_ = _type;
    }

    @Override
    public String toString(){
        String s = this.type_ + " = type { ";
        for(int i = 0;i <= this.type_.types_.size() - 2; i++){
            IRType tmp_type = this.type_.types_.get(i);
            s = (new StringBuilder()).append(s).append(new String(tmp_type + ", ")).toString();
        }
        if(this.type_.types_.isEmpty()) return  s + " }";
        else{
            int t = this.type_.types_.size() - 1;
            IRType tmp_type = this.type_.types_.get(t);
            return s + tmp_type + " }";
        }
    }
}

