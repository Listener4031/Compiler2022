package basic.types;

import java.util.ArrayList;

public class Type {
    public enum TYPE {NULL, INT, BOOL, STRING, CLASS, FUNCTION, VOID, THIS}
    public TYPE type_;
    public int dimension=0;
    public String name=null;
    public boolean assignable=true,is_function=false,is_class=false;
    public Type return_type=null;
    public ArrayList<Type> parameters=null;

    public Type(TYPE _type,int _dimension,boolean _assignable){
        type_=_type;
        dimension=_dimension;
        assignable=_assignable;
        if(type_==TYPE.NULL) name="null";
        else if(type_==TYPE.INT) name="int";
        else if(type_==TYPE.BOOL) name="boll";
        else if(type_==TYPE.STRING) name="string";
        else if(type_==TYPE.VOID) name="void";
        else if(type_==TYPE.THIS) name="this";
    }

    public Type(String _name,Type _return_type,ArrayList<Type> _parameters){
        type_=TYPE.FUNCTION;
        dimension=0;
        name=_name;
        assignable=false;
        is_function=true;
        return_type=_return_type;
        parameters=_parameters;
    }

    public Type(String _name,int _dimension,boolean _assignable){
        type_=TYPE.CLASS;
        dimension=_dimension;
        name=_name;
        assignable=_assignable;
        is_class=true;
    }

    public Type(Type _type){
        if(_type.is_function){
            type_=TYPE.FUNCTION;
            dimension=0;
            name=_type.name;
            assignable=false;
            is_function=true;
            return_type=_type.return_type;
            parameters=_type.parameters;
        }
        else if(_type.is_class){
            type_=TYPE.CLASS;
            dimension=_type.dimension;
            name=_type.name;
            assignable=_type.assignable;
            is_class=true;
        }
        else{
            type_=_type.type_;
            dimension=_type.dimension;
            name=_type.name;
            assignable=_type.assignable;
        }
    }

}
