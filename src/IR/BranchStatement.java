package IR;

public class BranchStatement extends Statement{
    public boolean unconditional_;
    public Entity condition_;
    public Label true_label, false_label;

    public BranchStatement(Label _label){
        super();
        this.unconditional_ = true;
        this.condition_ = null;
        this.true_label = _label;
    }

    public BranchStatement(Entity _condition, Label _true_label, Label _false_label){
        super();
        this.unconditional_ = false;
        this.condition_ = _condition;
        this.true_label = _true_label;
        this.false_label = _false_label;
    }

    @Override
    public String toString(){
        if(unconditional_) return "br label " + this.true_label;
        else return "br i1 " + this.condition_ + ", label " + this.true_label + ", label " + this.false_label;
    }
}
