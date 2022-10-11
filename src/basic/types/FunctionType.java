package basic.types;

import java.util.ArrayList;

public class FunctionType extends Type{
    public Type return_type=null;
    public ArrayList<Type> parameters=null; //参数列表

    public FunctionType(String n, Type t){
        super(TYPE.FUNCTION);
        name=n;
        return_type=t;
    }

    public FunctionType(TYPE x){
        super(x);
    }

    public FunctionType(){
        super(TYPE.FUNCTION);
    }
}
