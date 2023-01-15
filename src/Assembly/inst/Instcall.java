package Assembly.inst;

public class Instcall extends Inst{
    String symbol_;

    public Instcall(String _symbol){
        symbol_ = _symbol;
    }

    @Override
    public String toString(){
        return "call\t" + symbol_;
    }
}
