package IR;

import basic.IRtypes.IRType;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Function {
    public String identifier_;
    public ArrayList<Block> blocks_;
    public IRType return_type_;
    public ArrayList<Register> parameters_;
    public ArrayList<String> parameterIDs_;
    public boolean is_built_in;
    public int current_register_id;
    public Block allocate_block, entry_block, return_block;
    public boolean is_empty;
    public ArrayList<AllocateStatement> allocations_;
    public int count_;
    public Map<String, Block> label_to_block_;
    public Register return_register_;

    public Function(String _identifier){
        this.identifier_ = _identifier;
        this.blocks_ = new ArrayList<>();
        this.return_type_ = null;
        this.parameters_ = new ArrayList<>();
        this.parameterIDs_ = new ArrayList<>();
        this.current_register_id = 0;
        this.allocate_block = new Block(_identifier + "_allocate");
        this.entry_block = new Block(_identifier + "_entry");
        this.return_block = new Block(_identifier + "_return");
        this.is_empty = true;
        this.is_built_in = false;
        this.allocations_ = new ArrayList<>();
        this.count_ = 0;
        this.label_to_block_ = new HashMap<>();
        this.blocks_.add(this.allocate_block);
        this.blocks_.add(this.entry_block);
    }

    public void Print(PrintStream _out){
        _out.print("define " + this.return_type_ + " @" + this.identifier_ + "(");
        for(int i = 0; i <= this.parameters_.size() - 2; i++){
            Register tmp_reg = this.parameters_.get(i);
            _out.print(tmp_reg.type_ + " " + tmp_reg + ", ");
        }
        if(!this.parameters_.isEmpty()){
            int t = this.parameters_.size() - 1;
            Register tmp_reg = this.parameters_.get(t);
            _out.print(tmp_reg.type_ + " " + tmp_reg);
        }
        if(this.is_built_in) _out.println(")");
        else{
            _out.println(") {");
            for(int i = 0; i <= this.blocks_.size() - 2; i++){
                this.blocks_.get(i).Print(_out);
                _out.println();
            }
            if(!this.blocks_.isEmpty()){
                int t = this.blocks_.size() - 1;
                this.blocks_.get(t).Print(_out);
            }
            _out.println("}");
        }
    }
}
