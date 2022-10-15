package basic.error;

import basic.Locate;

public class InternalError extends Error{
    public InternalError(Locate l,String s){
        super(l, "Internal error: "+s);
    }
}
