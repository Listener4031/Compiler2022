package Assembly.inst;

import Assembly.operand.Reg;

public class Instmv extends Inst{
    public Instmv(Reg _rd, Reg _rs1){
        rd_ = _rd;
        rs1_ = _rs1;
    }

    @Override
    public String toString(){
        return "mv\t" + rd_ + "," + rs1_;
    }
}
