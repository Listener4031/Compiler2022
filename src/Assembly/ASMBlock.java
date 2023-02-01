package Assembly;

import Assembly.inst.Inst;

import java.io.PrintStream;

public class ASMBlock {
    public String identifier_;
    public Inst head_inst_, tail_inst_;
    public int size_;

    public ASMBlock(String _identifier){
        identifier_ = _identifier;
        head_inst_ = null;
        tail_inst_ = null;
        size_ = 0;
    }

    public void PushBack(Inst _new_inst){
        if(size_ == 0){
            head_inst_ = _new_inst;
            tail_inst_ = _new_inst;
        }
        else{
            tail_inst_.next_inst_ = _new_inst;
            _new_inst.pre_inst_ = tail_inst_;
            tail_inst_ = _new_inst;
        }
        size_++;
    }

    public void Insert(Inst _target_inst, Inst _new_inst){
        if(size_ == 0){
            head_inst_ = _new_inst;
            tail_inst_ = _new_inst;
        }
        else{
            if(_target_inst.pre_inst_ == null) head_inst_ = _new_inst;
            else _target_inst.pre_inst_.next_inst_ = _new_inst;
            _new_inst.pre_inst_ = _target_inst.pre_inst_;
            _new_inst.next_inst_ = _target_inst;
            _target_inst.pre_inst_ = _new_inst;
        }
        size_++;
    }

    public void Print(PrintStream _out){
        _out.println("ASMBlock: "+ identifier_ + ":");
        for(Inst it_inst = head_inst_; it_inst != null; it_inst = it_inst.next_inst_){
            _out.println("\t" + it_inst);
        }
    }
}
