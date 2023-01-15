package Assembly.operand;

public class ImmValue extends Operand{
    public int value_;

    public ImmValue(int _value){
        this.value_ = _value;
    }

    @Override
    public String toString(){
        return Integer.toString(this.value_);
    }
}
