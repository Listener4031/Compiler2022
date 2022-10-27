package basic.types;

import java.util.HashMap;

public class ClassType extends Type{
    public HashMap<String,Type> members;
    public HashMap<String,FunctionType> functions;

    public ClassType(String _name){
        super(TYPE.CLASS);
        name=_name;
        is_class=true;//now it is a class_type
        members=new HashMap<>();
        functions=new HashMap<>();
    }
}
