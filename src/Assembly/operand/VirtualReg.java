package Assembly.operand;

public class VirtualReg extends Reg{
    public int size_;

    public VirtualReg(String _identifier, int _size){
        super(_identifier);
        this.size_ = _size;
    }

    public VirtualReg(int _identifier, int _size){
        super("VirtualReg_" + Integer.toString(_identifier));
        this.size_ = _size;
    }

    @Override
    public String toString(){
        return this.identifier_;
    }
}
