package IR;

import basic.IRtypes.IRVoidType;

public class ReturnStatement extends Statement{
    public Entity entity_;

    public ReturnStatement(Entity _entity){
        super();
        this.entity_ = _entity;
    }

    @Override
    public String toString(){
        if(((Register) this.entity_).type_ instanceof IRVoidType) return "ret void";
        else if(this.entity_ instanceof Constant) return "ret i32 " + this.entity_;
        else return "ret " + ((Register) this.entity_).type_ + " " + this.entity_;
    }
}
