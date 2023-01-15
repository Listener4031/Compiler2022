package Assembly.inst;

import Assembly.operand.ImmValue;
import Assembly.operand.Reg;

public class InstLoad extends Inst{
    public enum LOAD_OP{lb, lh, lw, lbu, lhu}

    public LOAD_OP op_;
    public String symbol_ = null;

    public InstLoad(int _size, Reg _rd, ImmValue _imm, Reg _rs1){
        if(_size == 1) op_ = LOAD_OP.lb;
        else if(_size == 2) op_ = LOAD_OP.lh;
        else op_ = LOAD_OP.lw;
        rd_ = _rd;
        imm_ = _imm;
        rs1_ = _rs1;
    }

    public InstLoad(int _size, Reg _rd, String _symbol){
        if(_size == 1) op_ = LOAD_OP.lb;
        else if(_size == 2) op_ = LOAD_OP.lh;
        else op_ = LOAD_OP.lw;
        rd_ = _rd;
        symbol_ = _symbol;
    }

    @Override
    public String toString(){
        if(symbol_ == null) return op_.toString() + "\t" + rd_ + "," + imm_ + "(" + rs1_ + ")";
        else return op_.toString() + "\t" + rd_ + "," + symbol_;
    }
}
