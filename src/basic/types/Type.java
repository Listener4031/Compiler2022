package basic.types;

public class Type {
    public enum TYPE {NULL, INT, BOOL, STRING, CLASS, FUNCTION, VOID}
    public TYPE type = null;
    public int dimension = 0;
    public String name = null;

    public Type (TYPE x){
        type = x;
        switch (x){
            case NULL: name = "null";break;
            case INT: name = "int";break;
            case BOOL: name = "bool";break;
            case VOID: name = "void";break;
        }
    }

    public Type (Type x){
        type = x.type;
        dimension = x.dimension;
        name=x.name;
    }

}
