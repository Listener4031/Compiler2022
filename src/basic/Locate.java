package basic;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
public class Locate {
    public int row,column;

    public Locate(int r,int c){
        row=r;
        column=c;
    }

    public String ToString(){
        return row+","+column;
    }

    public Locate(Token t){
        row=t.getLine();
        column=t.getCharPositionInLine();
    }

    public Locate(TerminalNode t){
        this(t.getSymbol());
    }

    public Locate(ParserRuleContext p){
        this(p.getStart());
    }

}
