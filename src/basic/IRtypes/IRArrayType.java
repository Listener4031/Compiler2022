package basic.IRtypes;

public class IRArrayType extends IRType{
    public IRType type_;
    public int array_size;

    public IRArrayType(IRType _type, int _size){
        super();
        this.type_ = _type;
        this.array_size = _size;
        this.size_ = _type.size_ * _size;
    }

    @Override
    public String toString(){
        return "[" + array_size + " x " + type_ + "]";
    }
}
