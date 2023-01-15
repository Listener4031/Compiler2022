package Assembly.inst;

import IR.Label;

public class InstJump extends Inst{
    public Label label_;

    public InstJump(Label _label){
        label_ = _label;
    }

    @Override
    public String toString(){
        return "j\t" + label_.identifier_;
    }
}
