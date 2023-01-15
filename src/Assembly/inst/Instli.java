package Assembly.inst;

import Assembly.operand.ImmValue;
import Assembly.operand.Reg;

public class Instli extends Inst{
    public Instli(Reg _rd, ImmValue _imm){
        this.rd_ = _rd;
        this.imm_ = _imm;
    }

    @Override
    public String toString(){
        return "li\t" + this.rd_ + "," + this.imm_;
    }
}
