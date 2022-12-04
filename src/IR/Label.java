package IR;

public class Label {
    public String identifier_;

    public Label(String _identifier){
        this.identifier_ = _identifier;
    }

    @Override
    public String toString(){
        return "%" + this.identifier_;
    }
}
