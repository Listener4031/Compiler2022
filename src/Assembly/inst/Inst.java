package Assembly.inst;

import Assembly.operand.ImmValue;
import Assembly.operand.Reg;

public abstract class Inst {
    public Reg rd_ = null, rs1_ = null, rs2_ = null;
    public ImmValue imm_;
}
