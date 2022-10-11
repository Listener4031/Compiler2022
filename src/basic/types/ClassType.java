package basic.types;

import java.util.HashMap;

public class ClassType extends Type{
    public HashMap<String,Type> classes=new HashMap<>();
    public HashMap<String,FunctionType> parameters=new HashMap<>();

    public ClassType(TYPE t){
        super(t);
    }
}
