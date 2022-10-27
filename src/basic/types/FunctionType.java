package basic.types;

import java.util.ArrayList;

public class FunctionType extends Type{
    public Type return_type=null;
    public ArrayList<Type> parameters=null; //参数列表

    public FunctionType(String n, Type t){
        super(TYPE.FUNCTION,0,false);
        name=n;
        return_type=t;
        is_function=true;
    }

    public FunctionType(){
        super(TYPE.FUNCTION,0,false);
        is_function=true;
    }
}
