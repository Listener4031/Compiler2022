package basic.error;

import basic.Locate;

public class SyntaxError extends Error{
    public SyntaxError(Locate l,String s){
        super(l,"Syntax error: "+s);
    }
}
