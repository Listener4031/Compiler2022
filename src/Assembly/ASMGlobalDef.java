package Assembly;

import Assembly.operand.PhysicalReg;

import java.util.ArrayList;

public class ASMGlobalDef {
    public PhysicalReg[] physical_regs = new PhysicalReg[32];
    public ArrayList<GlobalItem> global_items = new ArrayList<>();
    public ArrayList<ASMFunction> asm_functions = new ArrayList<>();

    public ASMGlobalDef(){
        physical_regs[0] = new PhysicalReg("zero");
        physical_regs[1] = new PhysicalReg("ra");
        physical_regs[2] = new PhysicalReg("sp");
        physical_regs[3] = new PhysicalReg("gp");
        physical_regs[4] = new PhysicalReg("tp");
        physical_regs[5] = new PhysicalReg("t0");
        physical_regs[6] = new PhysicalReg("t1");
        physical_regs[7] = new PhysicalReg("t2");
        physical_regs[8] = new PhysicalReg("s0");
        physical_regs[9] = new PhysicalReg("s1");
        physical_regs[10] = new PhysicalReg("a0");
        physical_regs[11] = new PhysicalReg("a1");
        physical_regs[12] = new PhysicalReg("a2");
        physical_regs[13] = new PhysicalReg("a3");
        physical_regs[14] = new PhysicalReg("a4");
        physical_regs[15] = new PhysicalReg("a5");
        physical_regs[16] = new PhysicalReg("a6");
        physical_regs[17] = new PhysicalReg("a7");
        physical_regs[18] = new PhysicalReg("s2");
        physical_regs[19] = new PhysicalReg("s3");
        physical_regs[20] = new PhysicalReg("s4");
        physical_regs[21] = new PhysicalReg("s5");
        physical_regs[22] = new PhysicalReg("s6");
        physical_regs[23] = new PhysicalReg("s7");
        physical_regs[24] = new PhysicalReg("s8");
        physical_regs[25] = new PhysicalReg("s9");
        physical_regs[26] = new PhysicalReg("s10");
        physical_regs[27] = new PhysicalReg("s11");
        physical_regs[28] = new PhysicalReg("t3");
        physical_regs[29] = new PhysicalReg("t4");
        physical_regs[30] = new PhysicalReg("t5");
        physical_regs[31] = new PhysicalReg("t6");
    }
}
