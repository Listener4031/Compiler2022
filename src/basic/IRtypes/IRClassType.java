package basic.IRtypes;

import java.util.ArrayList;

public class IRClassType extends IRType{
    public String name_;
    public ArrayList<IRType> types_; // class A{int x, bool y};

    public IRClassType(String _name){
        super();
        this.name_ = _name;
        this.types_ = new ArrayList<>();
        this.size_ = 0;
    }

    @Override
    public String toString(){
        return "%class." + name_;
    }
}
