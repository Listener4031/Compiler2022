package basic.error;

import basic.Locate;

public class SemanticError extends Error{
    public SemanticError(Locate l,String s){
        super(l,"Semantic error: "+s);
    }
}
