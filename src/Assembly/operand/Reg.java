package Assembly.operand;

public abstract class Reg extends Operand{
    public String identifier_;

    public Reg(String _identifier){
        this.identifier_ = _identifier;
    }
}
