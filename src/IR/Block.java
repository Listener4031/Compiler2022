package IR;

import java.util.ArrayList;

public class Block {
    public String identifier_;
    public ArrayList<Statement> statements_;

    public Block(String _identifier){
        this.identifier_ = _identifier;
        statements_ = new ArrayList<>();
    }
}


