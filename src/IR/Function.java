package IR;

import basic.IRtypes.IRType;

import java.io.PrintStream;
import java.util.ArrayList;

public class Function {
    public String identifier_;
    public ArrayList<Block> blocks_;
    public IRType return_type_;
    public ArrayList<Register> parameters_;
    public ArrayList<String> parameterIDs_;
    public boolean is_built_in;
    public int current_register_id;

    public Function(String _identifier){
        this.identifier_ = _identifier;
        this.blocks_ = new ArrayList<>();
        this.parameters_ = new ArrayList<>();
        this.parameterIDs_ = new ArrayList<>();
        current_register_id = 0;
    }

    public void Print(PrintStream out){
    }
}

