package Assembly;

import java.io.PrintStream;

public class GlobalReg extends GlobalItem{
    public String identifier_;
    public int size_;
    public int value_;

    public GlobalReg(String _identifier, int _value, int _size){
        identifier_ = _identifier;
        value_ = _value;
        size_ = _size;
    }

    @Override
    public void Print(PrintStream _out) {
        _out.println("global_reg " + identifier_);
        _out.println("size: " + size_);
        _out.println("value:" + value_);
    }
}
