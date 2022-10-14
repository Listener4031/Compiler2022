package basic.types;

public class Type {
    public enum TYPE {NULL, INT, BOOL, STRING, CLASS, FUNCTION, VOID, CONST}
    public TYPE type=null;
    public int dimension=0;
    public String name=null;
    public boolean assignable=false,is_function=false,is_class=false;

    public Type (TYPE x){
        type = x;
        switch (x){
            case NULL: name="null";break;
            case INT: name="int";break;
            case BOOL: name="bool";break;
            case VOID: name="void";break;
            case CONST: name="const_null";break;
        }
    }

    public Type (Type x){
        type=x.type;
        dimension=x.dimension;
        name=x.name;
        assignable=x.assignable;
        is_class=x.is_class;
        is_function=x.is_function;
    }



}
