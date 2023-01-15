package Assembly.inst;

import Assembly.operand.ImmValue;
import Assembly.operand.Reg;

public class InstImm extends Inst{
    public enum IMM_OP{addi, slti, sltiu, xori, ori, andi, slli ,srli, srai}

    public IMM_OP op_;

    public InstImm(IMM_OP _op, Reg _rd, Reg _rs1, ImmValue _imm){
        op_ = _op;
        rd_ = _rd;
        rs1_ = _rs1;
        imm_ = _imm;
    }

    @Override
    public String toString(){
        return op_.toString() + "\t" + rd_ + "," + rs1_ + "," + imm_;
    }
}
