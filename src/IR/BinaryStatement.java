package IR;

import basic.IRtypes.IRType;

public class BinaryStatement extends Statement{
    /*
        public enum BINARY_OP{DOT,
        MULTIPLY,DIVIDE,MOD,
        PLUS,MINUS,
        LEFT_SHIFT,RIGHT_SHIFT,
        LESS,LESS_EQUAL,GREATER,GREATER_EQUAL,
        EQUAL,NOT_EQUAL,
        AND,CARET,OR,
        AND_AND,OR_OR,
        ASSIGN}
         */
    public enum IR_BINARY_OP{
        mul, sdiv, srem,
        add, sub,
        shl, ashr,
        slt, sle, sgt, sge,
        eq, ne,
        and, xor, or
    }

    public IR_BINARY_OP op_;
    public IRType type_;
    public Entity left_entity_, right_entity_, to_entity_;

    public BinaryStatement(IR_BINARY_OP _op, IRType _type, Entity _left_entity, Entity _right_entity, Entity _to_entity){
        super();
        this.op_ = _op;
        this.type_ = _type;
        this.left_entity_ = _left_entity;
        this.right_entity_ = _right_entity;
        this.to_entity_ = _to_entity;
    }

    @Override
    public String toString(){
        if(this.op_ == IR_BINARY_OP.slt
                || this.op_ == IR_BINARY_OP.sle
                || this.op_ == IR_BINARY_OP.sgt
                || this.op_ == IR_BINARY_OP.sge
                || this.op_ == IR_BINARY_OP.eq
                || this.op_ == IR_BINARY_OP.ne){
            return this.to_entity_ + " = icmp " + this.op_.toString() + " " + this.type_ + " " + this.left_entity_ + ", " + this.right_entity_;
        }
        else{
            return this.to_entity_ + " = " + this.op_.toString() + " " + this.type_ + " " + this.left_entity_ + ", " + this.right_entity_;
        }
    }
}
