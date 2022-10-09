package basic.types;

public class Types {
    public enum TypeName {NULL, INT, BOOL, STRING, VOID, CLASS, FUNCTION}
    public TypeName type = null;
    public String name = null;
    public int dimension = 0;

    public Types(TypeName x){
        type = x;
        switch(x){
            case NULL: name = "null";break;
            case INT: name = "int";break;
            case BOOL: name = "bool";break;
            case STRING: name = "string";break;
            case VOID: name = "void";break;
            case CLASS: name = "class";break;
            case FUNCTION: name = "function";break;
            default: break;
        }
    }

}
