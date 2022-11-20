package basic.IRtypes;

public class IRNullType extends IRType{
    public IRType type_;

    public IRNullType(){
        super();
    }

    @Override
    public String toString(){
        return type_.toString();
    }
}
