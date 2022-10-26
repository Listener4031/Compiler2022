package basic.types;

public class Type {
    public enum TYPE {NULL, INT, BOOL, STRING, CLASS, FUNCTION, VOID, THIS}
    public TYPE type_;
    public int dimension=0;
    public String name=null;
    public boolean assignable=true,is_function=false,is_class=false;

    public Type (TYPE x){
        type_ = x;
        switch (x){
            case NULL: name="null";break;
            case INT: name="int";break;
            case BOOL: name="bool";break;
            case VOID: name="void";break;
            case STRING:name="string";break;
            default: break;//function and class have their own names
        }
    }

    public Type (Type x){
        type_=x.type_;
        dimension=x.dimension;
        name=x.name;
        assignable=x.assignable;
        is_class=x.is_class;
        is_function=x.is_function;
    }

}
