package basic.IRtypes;

public class IRPointerType extends IRType{
    public IRType type_;

    public IRPointerType(IRType _type){
        super();
        this.type_ = _type;
        this.size_ = 8;
    }

    @Override
    public String toString(){
        return type_ + "*";
    }
}
