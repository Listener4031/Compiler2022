package Assembly.inst;

import Assembly.operand.ImmValue;
import Assembly.operand.Reg;

public class InstStore extends Inst{
    public enum STORE_OP{sb, sh, sw}

    public STORE_OP op_;
    public String symbol_ = null;

    public InstStore(int _size, Reg _rs1, ImmValue _imm, Reg _rs2){
        if(_size == 1) this.op_ = STORE_OP.sb;
        else if(_size == 2) this.op_ = STORE_OP.sh;
        else this.op_ = STORE_OP.sw;
        this.rs1_ = _rs1;
        this.rs2_ = _rs2;
        this.imm_ = _imm;
    }

    public InstStore(int _size, Reg _rs1, Reg _rs2, String _symbol){
        if(_size == 1) this.op_ = STORE_OP.sb;
        else if(_size == 2) this.op_ = STORE_OP.sh;
        else this.op_ = STORE_OP.sw;
        this.rs1_ = _rs1;
        this.rs2_ = _rs2;
        this.symbol_ = _symbol;
    }

    @Override
    public String toString(){
        if(this.symbol_ == null) return op_.toString() + "\t" + rs1_ + "," + imm_ + "(" + rs2_ + ")";
        else return op_.toString() + "\t" + rs1_ + "," + symbol_ + "," + rs2_;
    }
}
