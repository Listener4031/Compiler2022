package basic.IRtypes;

public class IRIntType extends IRType{
    public int width_;

    public IRIntType(int _width){
        super();
        this.width_ = _width;
        this.size_ = _width / 8;
    }

    @Override
    public String toString(){
        return "i" + width_;
    }
}
