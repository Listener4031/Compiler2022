package IR;

import basic.IRtypes.IRType;

import java.util.ArrayList;

public class FunctCallStatement extends Statement{
    public String name_;
    public boolean is_void;
    public IRType return_type_;
    public Register register_;
    public ArrayList<Entity> parameters_;

    public FunctCallStatement(){
        super();
        this.name_ = new String();
        this.return_type_ = null;
        this.register_ = null;
        this.parameters_ = new ArrayList<>();
    }

    public FunctCallStatement(String _name){
        super();
        this.name_ = _name;
        this.is_void = true;
        this.return_type_ = null;
        this.register_ = null;
        this.parameters_ = new ArrayList<>();
    }

    public FunctCallStatement(String _name, IRType _type, Register _register){
        super();
        this.name_ = _name;
        this.is_void = false;
        this.return_type_ = _type;
        this.register_ = _register;
        this.parameters_ = new ArrayList<>();
    }

    @Override
    public String toString(){
        String s = new String();
        if(this.is_void) s = "call void";
        else s = this.register_ + " = call " + this.return_type_;
        s = s + " @" + this.name_ + "(";
        for(int i = 0; i <= this.parameters_.size() - 2; i++){
            Entity tmp_entity = this.parameters_.get(i);
            s = s + tmp_entity.type_ + " " + tmp_entity + ", ";
        }
        if(this.parameters_.isEmpty()) return s + ")";
        else{
            int t = this.parameters_.size() - 1;
            Entity tmp_entity = this.parameters_.get(t);
            return s + tmp_entity.type_ + " " + tmp_entity + ")";
        }
    }
}
