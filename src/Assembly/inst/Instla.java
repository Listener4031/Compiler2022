package Assembly.inst;

import Assembly.operand.Reg;

public class Instla extends Inst{
    public String symbol_;

    public Instla(Reg _rd, String _symbol){
        rd_ = _rd;
        symbol_ = _symbol;
    }

    @Override
    public String toString(){
        return "la\t" + rd_ + "," + symbol_;
    }
}
