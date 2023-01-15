package Assembly.inst;

import Assembly.operand.Reg;
import IR.Label;

public class Instbnez extends Inst{
    public Label label_;

    public Instbnez(Reg _rs1, Label _label){
        rs1_ = _rs1;
        label_ = _label;
    }

    @Override
    public String toString(){
        return "bnez\t" + rs1_ + "," + label_.identifier_;
    }
}
