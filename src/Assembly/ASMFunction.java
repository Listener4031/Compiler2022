package Assembly;

import Assembly.operand.Reg;
import Assembly.operand.VirtualReg;
import IR.Entity;

import java.io.PrintStream;
import java.util.*;

public class ASMFunction {
    public String identifier_;
    public int current_register_id;
    public ArrayList<ASMBlock> asm_blocks_;
    public int offset_;
    public Map<Reg, Integer> reg_offset_;
    public Map<String, VirtualReg> regID_to_virtualReg;
    public Set<String> allocated_regs;
    public Map<String, ArrayList<VirtualReg>> label_to_regs;
    public Map<String, ArrayList<String>> label_to_labels;
    public Map<String, ArrayList<Entity>> label_to_values;

    public ASMFunction(String _identifier){
        identifier_ = _identifier;
        current_register_id = 0;
        asm_blocks_ = new ArrayList<>();
        offset_ = 0;
        reg_offset_ = new HashMap<>();
        regID_to_virtualReg = new HashMap<>();
        allocated_regs = new HashSet<>();
        label_to_regs = new HashMap<>();
        label_to_labels = new HashMap<>();
        label_to_values = new HashMap<>();
    }

    public void Print(PrintStream _out){
        _out.println("\t.globl\t" + identifier_);
        _out.println("\t.p2align\t2");
        _out.println("\t.type\t" + identifier_ + ",@function");
        _out.println(identifier_ + ":");
        for(ASMBlock it : asm_blocks_) it.Print(_out);
    }
}
