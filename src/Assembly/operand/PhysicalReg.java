package Assembly.operand;

public class PhysicalReg extends Reg{
    public PhysicalReg(String _identifier){
        super(_identifier);
    }

    @Override
    public String toString(){
        return this.identifier_;
    }
}
