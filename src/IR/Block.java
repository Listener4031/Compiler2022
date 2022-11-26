package IR;

import java.io.PrintStream;
import java.util.ArrayList;

public class Block {
    public String identifier_;
    public ArrayList<Statement> statements_;

    public Block(String _identifier){
        this.identifier_ = _identifier;
        statements_ = new ArrayList<>();
    }

    public void Print(PrintStream _out){
        _out.println(this.identifier_ + ":");
        this.statements_.forEach(it -> _out.println("  " + it));
    }
}


