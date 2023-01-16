package IR;

import basic.IRtypes.IRType;

import java.util.ArrayList;
// 取决于从哪个块跳转过来
public class PhiStatement extends Statement{
    public Register dest_reg_;
    public IRType type_;
    public ArrayList<Entity> values_;
    public ArrayList<Label> labels_;

    public PhiStatement(Register _dest_reg, IRType _type){
        dest_reg_ = _dest_reg;
        type_ = _type;
        values_ = new ArrayList<>();
        labels_ = new ArrayList<>();
    }

    @Override
    public String toString(){
        String ans = dest_reg_ + " = phi " + type_ + " ";
        for(int i = 0; i <= values_.size() - 2; i++){
            ans += "[ " + values_.get(i) + ", " + labels_.get(i) + " ], ";
        }
        int t = values_.size() - 1;
        ans += "[ " + values_.get(t) + ", " + labels_.get(t) + " ]";
        return ans;
    }
}
