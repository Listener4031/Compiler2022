package Assembly.inst;

import Assembly.operand.Reg;

/*
`define ADD     6'd19
`define SUB     6'd20
`define SLL     6'd21
`define SLT     6'd22
`define SLTU    6'd23
`define XOR     6'd24
`define SRL     6'd25
`define SRA     6'd26
`define OR      6'd27
`define AND     6'd28
 */
public class InstBinary extends Inst{
    public enum BINARY_INST_OP{add, sub, sll, slt, sltu, xor, srl, sra, or, and, mul, div, rem}

    public BINARY_INST_OP op_;

    public InstBinary(BINARY_INST_OP _op, Reg _rd, Reg _rs1, Reg _rs2){
        op_ = _op;
        rd_ = _rd;
        rs1_ = _rs1;
        rs2_ = _rs2;
    }

    @Override
    public String toString(){
        return op_.toString() + "\t" + rd_ + "," + rs1_ + "," + rs2_;
    }
}
