package basic.types;

import java.util.ArrayList;

public class FunctionType extends Type{
    public Type return_type=null;
    public ArrayList<Type> parameters=null; //参数列表

    public FunctionType(String n, Type t){
        super(TYPE.FUNCTION);
        name=n;
        return_type=t;
        is_function=true;
    }

    public FunctionType(TYPE x){
        super(x);
        is_function=true;
    }

    public FunctionType(){
        super(TYPE.FUNCTION);
        is_function=true;
    }
}
