package basic.error;

import basic.Locate;

public abstract class Error extends RuntimeException{
    public Locate position;
    public String error_message;

    public Error(Locate l,String s){
        position=l;
        error_message=s;
    }

    public String toString(){
        return error_message+": "+position.ToString();
    }

}
