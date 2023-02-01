package IR;

import basic.IRtypes.IRPointerType;

import java.util.ArrayList;

public class GetElementPtr extends Statement{
    public Register reg_from_, reg_to_;
    public ArrayList<Entity> values;

    public GetElementPtr(Register _reg_from, Register _reg_to){
        super();
        this.reg_from_ = _reg_from;
        this.reg_to_ = _reg_to;
        this.values = new ArrayList<>();
    }

    @Override
    public String toString(){
        String ans = reg_to_ + " = getelementptr inbounds " + ((IRPointerType) reg_from_.type_).type_;
        ans += ", " + reg_from_.type_ + " " + reg_from_ + ", ";
        for(int i = 0; i <= this.values.size() - 2; i++){
            ans += this.values.get(i).type_ + " " + this.values.get(i) + ", ";
        }
        int t = this.values.size() - 1;
        ans += this.values.get(t).type_ + " " + this.values.get(t);
        return ans;
    }
}
