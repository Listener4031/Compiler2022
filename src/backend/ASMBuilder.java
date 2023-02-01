package backend;

import Assembly.*;
import Assembly.inst.*;
import Assembly.operand.ImmValue;
import Assembly.operand.PhysicalReg;
import Assembly.operand.VirtualReg;
import IR.*;
import basic.IRtypes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ASMBuilder {
    public GlobalDefinition global_definition_;
    public ASMGlobalDef asm_global_def_;
    public ASMBlock current_block_;
    public ASMFunction current_function_;
    public PhysicalReg[] physical_regs_;
    public Map<String, ArrayList<Integer>> classes_offset_;

    public ASMBuilder(GlobalDefinition _global_definition, ASMGlobalDef _asm_global_def){
        global_definition_ = _global_definition;
        asm_global_def_ = _asm_global_def;
        physical_regs_ = _asm_global_def.physical_regs;
        classes_offset_ = new HashMap<>();
        BuildGlobalDef();
        for(Function it : global_definition_.functions_){
            if(!it.is_built_in){
                BuildFunction(it);
                asm_global_def_.asm_functions.add(current_function_);
            }
        }
        FixPhi();
    }

    private void BuildGlobalDef(){
        for(Statement it : global_definition_.global_def_statements){
            if(it instanceof GlobalDefStatement) {
                GlobalReg tmp_reg = new GlobalReg(((GlobalDefStatement) it).register_.identifier_, ((GlobalDefStatement) it).constant_.value_, ((GlobalDefStatement) it).register_.type_.size_);
                asm_global_def_.global_items.add(tmp_reg);
            }
            else if(it instanceof GlobalStringDefStmt){
                GlobalString tmp_string = new GlobalString(((GlobalStringDefStmt) it).register_.identifier_, ((GlobalStringDefStmt) it).content_);
                asm_global_def_.global_items.add(tmp_string);
            }
            else if(it instanceof StructDefStatement){
                ArrayList<Integer> offset_sum = new ArrayList<>();
                int sum = 0;
                offset_sum.add(sum);
                for(int i = 0; i < ((StructDefStatement) it).type_.types_.size(); i++){
                    IRType tmp_IRType = ((StructDefStatement) it).type_.types_.get(i);
                    int size = (tmp_IRType instanceof IRPointerType) ? 8 : ((IRIntType) tmp_IRType).size_;
                    sum += size;
                    offset_sum.add(sum);
                }
                classes_offset_.put(((StructDefStatement) it).type_.name_, offset_sum);
            }
        }
    }

    private void BuildFunction(Function _function){
        current_function_ = new ASMFunction(_function.identifier_);
        ASMBlock new_asm_block = new ASMBlock(current_function_.identifier_ + "_block");
        current_function_.asm_blocks_.add(new_asm_block);
        current_function_.offset_ += 4;
        current_function_.reg_offset_.put(physical_regs_[1], current_function_.offset_); // ra
        current_function_.offset_ += 4;
        current_function_.reg_offset_.put(physical_regs_[8], current_function_.offset_); // s0
        for(int i = 0; i < Integer.min(8, _function.parameters_.size()); i++){
            Register tmp_register = _function.parameters_.get(i);
            VirtualReg tmp_virtual_reg = new VirtualReg(current_function_.current_register_id++, tmp_register.type_.size_);
            new_asm_block.PushBack(new Instmv(tmp_virtual_reg, physical_regs_[i + 10]));
            current_function_.regID_to_virtualReg.put(tmp_register.identifier_, tmp_virtual_reg);
        }
        for(int i = 8; i < _function.parameters_.size(); i++){
            Register tmp_register = _function.parameters_.get(i);
            VirtualReg tmp_virtual_reg = new VirtualReg(current_function_.current_register_id++, tmp_register.type_.size_);
            new_asm_block.PushBack(new InstLoad(tmp_virtual_reg.size_, tmp_virtual_reg, new ImmValue((i - 8) * 4), physical_regs_[8]));
            current_function_.regID_to_virtualReg.put(tmp_register.identifier_, tmp_virtual_reg);
        }
        // save callee registers
        VirtualReg[] callee_regs = new VirtualReg[32];
        for(int i = 8; i <= 9; i++){
            callee_regs[i] = new VirtualReg(current_function_.current_register_id++, 4);
            new_asm_block.PushBack(new Instmv(callee_regs[i], physical_regs_[i]));
        }
        for(int i = 18; i <= 27; i++){
            callee_regs[i] = new VirtualReg(current_function_.current_register_id++, 4);
            new_asm_block.PushBack(new Instmv(callee_regs[i], physical_regs_[i]));
        }
        new_asm_block.PushBack(new InstJump(new Label(_function.blocks_.get(0).identifier_)));
        for(Block it : _function.blocks_){
            BuildBlock(it);
            current_function_.asm_blocks_.add(current_block_);
        }
        // recover callee registers
        int t = current_function_.asm_blocks_.size() - 1;
        ASMBlock tmp_ans_block = current_function_.asm_blocks_.get(t);
        for(int i = 8; i <= 9; i++){
            tmp_ans_block.PushBack(new Instmv(physical_regs_[i], callee_regs[i]));
        }
        for(int i = 18; i <= 27; i++){
            tmp_ans_block.PushBack(new Instmv(physical_regs_[i], callee_regs[i]));
        }
    }

    private void BuildBlock(Block _block){
        current_block_ = new ASMBlock(_block.identifier_);
        for(Statement it : _block.statements_) AddInst(it);
    }

    private void AddInst(Statement _statement){
        if(_statement instanceof AllocateStatement){
            if(!current_function_.regID_to_virtualReg.containsKey(((AllocateStatement) _statement).register_.identifier_)){
                VirtualReg tmp_virtual_reg = new VirtualReg(current_function_.current_register_id++, ((AllocateStatement) _statement).type_.size_);
                current_function_.regID_to_virtualReg.put(((AllocateStatement) _statement).register_.identifier_, tmp_virtual_reg);
                current_function_.allocated_regs.add(((AllocateStatement) _statement).register_.identifier_);
            }
        }
        else if(_statement instanceof BinaryStatement){
            Entity dest_entity = ((BinaryStatement) _statement).to_entity_;
            Entity left_entity = ((BinaryStatement) _statement).left_entity_;
            Entity right_entity = ((BinaryStatement) _statement).right_entity_;
            VirtualReg rd = RegConverse(dest_entity);
            VirtualReg rs1 = RegConverse(left_entity);
            VirtualReg rs2 = RegConverse(right_entity);
            if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.mul){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.mul, rd, rs1, rs2));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.sdiv){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.div, rd, rs1, rs2));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.srem){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.rem, rd, rs1, rs2));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.add){
                if(left_entity instanceof Constant && ((Constant) left_entity).value_ >= -2048 && ((Constant) left_entity).value_ < 2048){
                    ImmValue imm = new ImmValue(((Constant) left_entity).value_);
                    current_block_.PushBack(new InstImm(InstImm.IMM_OP.addi, rd, rs1, imm));
                }
                else{
                    if(right_entity instanceof Constant && ((Constant) right_entity).value_ >= -2048 && ((Constant) right_entity).value_ < 2048){
                        ImmValue imm = new ImmValue(((Constant) right_entity).value_);
                        current_block_.PushBack(new InstImm(InstImm.IMM_OP.addi, rd, rs2, imm));
                    }
                    else{
                        current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.add, rd, rs1, rs2));
                    }
                }
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.sub){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.sub, rd, rs1, rs2));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.shl){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.sll, rd, rs1, rs2));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.ashr){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.sra, rd, rs1, rs2));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.slt){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.slt, rd, rs1, rs2));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.sle){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.slt, rd, rs2, rs1));
                current_block_.PushBack(new InstImm(InstImm.IMM_OP.xori, rd, rd, new ImmValue(1)));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.sgt){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.slt, rd, rs2, rs1));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.sge){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.slt, rd, rs1, rs2));
                current_block_.PushBack(new InstImm(InstImm.IMM_OP.xori, rd, rd, new ImmValue(1)));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.eq){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.xor, rd, rs1, rs2));
                current_block_.PushBack(new InstImm(InstImm.IMM_OP.sltiu, rd, rd, new ImmValue(1)));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.ne){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.xor, rd, rs1, rs2));
                current_block_.PushBack(new InstImm(InstImm.IMM_OP.sltiu, rd, rd, new ImmValue(1)));
                current_block_.PushBack(new InstImm(InstImm.IMM_OP.xori, rd, rd, new ImmValue(1)));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.and){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.and, rd, rs1, rs2));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.xor){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.xor, rd, rs1, rs2));
            }
            else if(((BinaryStatement) _statement).op_ == BinaryStatement.IR_BINARY_OP.or){
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.or, rd, rs1, rs2));
            }
        }
        else if(_statement instanceof BitcastStatement){
            VirtualReg rs1 = RegConverse(((BitcastStatement) _statement).from_reg_);
            VirtualReg rd = RegConverse(((BitcastStatement) _statement).to_reg_);
            current_block_.PushBack(new Instmv(rd, rs1));
        }
        else if(_statement instanceof BranchStatement){
            if(((BranchStatement) _statement).unconditional_){
                current_block_.PushBack(new InstJump(((BranchStatement) _statement).true_label));
            }
            else{
                VirtualReg rs1 = RegConverse(((BranchStatement) _statement).condition_);
                current_block_.PushBack(new Instbnez(rs1, ((BranchStatement) _statement).true_label));
                current_block_.PushBack(new InstJump(((BranchStatement) _statement).false_label));
            }
        }
        else if(_statement instanceof FunctCallStatement){
            for(int i = 0; i < Math.min(8, ((FunctCallStatement) _statement).parameters_.size()); i++){
                VirtualReg rs1 = RegConverse(((FunctCallStatement) _statement).parameters_.get(i));
                current_block_.PushBack(new Instmv(physical_regs_[i + 10], rs1));
            }
            for(int i = 8; i < ((FunctCallStatement) _statement).parameters_.size(); i++){
                Entity tmp_entity = ((FunctCallStatement) _statement).parameters_.get(i);
                VirtualReg rs1 = RegConverse(tmp_entity);
                current_block_.PushBack(new InstStore(tmp_entity.type_.size_, rs1, new ImmValue((i - 8) * 4), physical_regs_[2]));
            }
            current_block_.PushBack(new Instcall(((FunctCallStatement) _statement).name_));
            if(((FunctCallStatement) _statement).is_void) return;
            VirtualReg rd = RegConverse(((FunctCallStatement) _statement).register_);
            current_block_.PushBack(new Instmv(rd, physical_regs_[10]));
            current_function_.regID_to_virtualReg.put(((FunctCallStatement) _statement).register_.identifier_, rd);
        }
        else if(_statement instanceof GetElementPtr){
            Register from_register = ((GetElementPtr) _statement).reg_from_;
            Register to_register = ((GetElementPtr) _statement).reg_to_;
            VirtualReg rs1 = RegConverse(from_register);
            VirtualReg rd = RegConverse(to_register);
            if(((GetElementPtr) _statement).values.size() == 1){
                Entity tmp_value = ((GetElementPtr) _statement).values.get(0);
                VirtualReg tmp_reg = new VirtualReg(current_function_.current_register_id++, tmp_value.type_.size_);
                VirtualReg rs2 = RegConverse(new Constant(tmp_value.type_, ((IRPointerType) from_register.type_).type_.size_));
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.mul, tmp_reg, RegConverse(tmp_value), rs2));
                current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.add, rd, rs1, tmp_reg));
            }
            else{
                Entity tmp_value = ((GetElementPtr) _statement).values.get(1);
                String class_name = ((IRClassType) ((IRPointerType) from_register.type_).type_).name_;
                ImmValue imm = new ImmValue(classes_offset_.get(class_name).get(((Constant) tmp_value).value_));
                current_block_.PushBack(new InstImm(InstImm.IMM_OP.addi, rd, rs1, imm));
            }
            current_function_.regID_to_virtualReg.put(to_register.identifier_, rd);
        }
        else if(_statement instanceof LoadStatement){
            Entity from_entity = ((LoadStatement) _statement).from_;
            Entity to_entity = ((LoadStatement) _statement).to_;
            VirtualReg rs1 = RegConverse(from_entity);
            VirtualReg rd = RegConverse(to_entity);
            if(from_entity instanceof Register && ((Register) from_entity).is_global){
                current_block_.PushBack(new InstLoad(((Register) from_entity).type_.size_, rd, ((Register) from_entity).identifier_));
            }
            else{
                if(current_function_.allocated_regs.contains(((Register) from_entity).identifier_)){
                    current_block_.PushBack(new Instmv(rd, rs1));
                }
                else{
                    if(current_function_.reg_offset_.containsKey(rs1)){
                        ImmValue imm = new ImmValue(-current_function_.reg_offset_.get(rs1));
                        VirtualReg tmp_virtual_reg = new VirtualReg(current_function_.current_register_id++, 4);
                        current_block_.PushBack(new Instli(tmp_virtual_reg, imm));
                        current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.add, tmp_virtual_reg, physical_regs_[8], tmp_virtual_reg));
                        current_block_.PushBack(new InstLoad(to_entity.type_.size_, rd, new ImmValue(0), tmp_virtual_reg));
                    }
                    else{
                        current_block_.PushBack(new InstLoad(to_entity.type_.size_, rd, new ImmValue(0), rs1));
                    }
                }
            }
            current_function_.regID_to_virtualReg.put(((Register) to_entity).identifier_, rd);
        }
        else if(_statement instanceof PhiStatement){ // must be the first statement of a block
            Register tmp_register = ((PhiStatement) _statement).dest_reg_;
            VirtualReg rd = RegConverse(tmp_register);
            current_function_.regID_to_virtualReg.put(tmp_register.identifier_, rd);
            for(int i = 0; i < ((PhiStatement) _statement).labels_.size(); i++){
                Label tmp_label = ((PhiStatement) _statement).labels_.get(i);
                Entity tmp_value = ((PhiStatement) _statement).values_.get(i);
                if(current_function_.label_to_values.containsKey(tmp_label.identifier_)){
                    current_function_.label_to_values.get(tmp_label.identifier_).add(tmp_value);
                    current_function_.label_to_labels.get(tmp_label.identifier_).add(current_block_.identifier_);
                    current_function_.label_to_regs.get(tmp_label.identifier_).add(rd);
                }
                else{
                    current_function_.label_to_regs.put(tmp_label.identifier_, new ArrayList<>());
                    current_function_.label_to_regs.get(tmp_label.identifier_).add(rd);
                    current_function_.label_to_labels.put(tmp_label.identifier_, new ArrayList<>());
                    current_function_.label_to_labels.get(tmp_label.identifier_).add(current_block_.identifier_);
                    current_function_.label_to_values.put(tmp_label.identifier_, new ArrayList<>());
                    current_function_.label_to_values.get(tmp_label.identifier_).add(tmp_value);
                }
            }
        }
        else if(_statement instanceof ReturnStatement){
            if(((ReturnStatement) _statement).entity_.type_ instanceof IRVoidType) return;
            VirtualReg rs1 = RegConverse(((ReturnStatement) _statement).entity_);
            current_block_.PushBack(new Instmv(physical_regs_[10], rs1));
        }
        else if(_statement instanceof StoreStatement){
            Entity from_entity = ((StoreStatement) _statement).from_;
            Entity to_entity = ((StoreStatement) _statement).to_;
            if(from_entity == null || to_entity == null) return;
            if(to_entity instanceof Register && ((Register) to_entity).is_global){
                VirtualReg rs1 = RegConverse(from_entity);
                VirtualReg tmp_virtual_reg = new VirtualReg(current_function_.current_register_id++, 4);
                current_block_.PushBack(new Instla(tmp_virtual_reg, ((Register) to_entity).identifier_));
                current_block_.PushBack(new InstStore(((Register) to_entity).type_.size_, rs1, new ImmValue(0), tmp_virtual_reg));
            }
            else{
                VirtualReg rs1 = RegConverse(from_entity);
                VirtualReg rd = RegConverse(to_entity);
                if(current_function_.allocated_regs.contains(((Register) to_entity).identifier_)){
                    current_block_.PushBack(new Instmv(rd, rs1));
                }
                else{
                    if(current_function_.reg_offset_.containsKey(rd)){
                        ImmValue imm = new ImmValue(-current_function_.reg_offset_.get(rd));
                        VirtualReg tmp_reg = new VirtualReg(current_function_.current_register_id++, 4);
                        current_block_.PushBack(new Instli(tmp_reg, imm));
                        current_block_.PushBack(new InstBinary(InstBinary.BINARY_INST_OP.add, tmp_reg, physical_regs_[8], tmp_reg));
                        current_block_.PushBack(new InstStore(from_entity.type_.size_, rs1, new ImmValue(0), tmp_reg));
                    }
                    else{
                        current_block_.PushBack(new InstStore(from_entity.type_.size_, rs1, new ImmValue(0), rd));
                    }
                }
            }
        }
        else if(_statement instanceof TruncStatement){
            VirtualReg rs1 = RegConverse(((TruncStatement) _statement).from_entity_);
            VirtualReg rd = RegConverse(((TruncStatement) _statement).to_entity_);
            current_block_.PushBack(new Instmv(rd, rs1));
        }
        else if(_statement instanceof ZextStatement){
            VirtualReg rs1 = RegConverse(((ZextStatement) _statement).from_entity_);
            VirtualReg rd = RegConverse(((ZextStatement) _statement).to_entity_);
            current_block_.PushBack(new Instmv(rd, rs1));
        }
    }

    private VirtualReg RegConverse(Entity _entity){
        if(_entity instanceof Constant){
            if(_entity.type_ instanceof IRNullType){
                VirtualReg return_virtual_reg = new VirtualReg(current_function_.current_register_id++, 4);
                current_block_.PushBack(new Instli(return_virtual_reg, new ImmValue(0)));
                return return_virtual_reg;
            }
            else{
                VirtualReg return_virtual_reg = new VirtualReg(current_function_.current_register_id++, 4);
                current_block_.PushBack(new Instli(return_virtual_reg, new ImmValue(((Constant) _entity).value_)));
                return return_virtual_reg;
            }
        }
        else{
            if(_entity.type_ instanceof IRNullType){
                VirtualReg return_virtual_reg = new VirtualReg(current_function_.current_register_id++, 4);
                current_block_.PushBack(new Instli(return_virtual_reg, new ImmValue(0)));
                return return_virtual_reg;
            }
            else{
                if(((Register) _entity).is_global){
                    VirtualReg return_virtual_reg = new VirtualReg(current_function_.current_register_id++, ((Register) _entity).type_.size_);
                    current_block_.PushBack(new Instla(return_virtual_reg, ((Register) _entity).identifier_));
                    return return_virtual_reg;
                }
                else{
                    if(current_function_.regID_to_virtualReg.containsKey(((Register) _entity).identifier_)){
                        return current_function_.regID_to_virtualReg.get(((Register) _entity).identifier_);
                    }
                    else{
                        VirtualReg return_virtual_reg = new VirtualReg(current_function_.current_register_id++, _entity.type_.size_);
                        current_function_.regID_to_virtualReg.put(((Register) _entity).identifier_, return_virtual_reg);
                        return return_virtual_reg;
                    }
                }
            }
        }
    }

    private void FixPhi(){
        for(ASMFunction it_function : asm_global_def_.asm_functions){
            if(it_function.label_to_values.isEmpty()) continue;
            current_function_ = it_function;
            for(ASMBlock it_block : it_function.asm_blocks_){
                current_block_ = it_block;
                if(it_function.label_to_values.containsKey(it_block.identifier_)){
                    for(int i = 0; i < it_function.label_to_values.get(it_block.identifier_).size(); i++){
                        Entity tmp_value = it_function.label_to_values.get(it_block.identifier_).get(i);
                        VirtualReg rd = it_function.label_to_regs.get(it_block.identifier_).get(i);
                        String labelID = it_function.label_to_labels.get(it_block.identifier_).get(i);
                        for(Inst it_inst = it_block.head_inst_; it_inst != null; it_inst = it_inst.next_inst_){
                            if((it_inst instanceof InstJump
                                    && ((InstJump) it_inst).label_.identifier_.equals(labelID))
                                    || it_inst instanceof Instbnez
                                    && ((Instbnez) it_inst).label_.identifier_.equals(labelID)){
                                if(tmp_value instanceof Constant){
                                    VirtualReg tmp_reg = new VirtualReg(current_function_.current_register_id++, 4);
                                    ImmValue imm = new ImmValue(((Constant) tmp_value).value_);
                                    current_block_.Insert(it_inst, new Instli(tmp_reg, imm));
                                    it_block.Insert(it_inst, new Instmv(rd, tmp_reg));
                                }
                                else{
                                    if(((Register) tmp_value).is_global){
                                        VirtualReg tmp_reg = new VirtualReg(current_function_.current_register_id++, tmp_value.type_.size_);
                                        current_block_.Insert(it_inst, new Instla(tmp_reg, ((Register) tmp_value).identifier_));
                                        it_block.Insert(it_inst, new Instmv(rd, tmp_reg));
                                    }
                                    else{
                                        if(current_function_.regID_to_virtualReg.containsKey(((Register) tmp_value).identifier_)){
                                            it_block.Insert(it_inst, new Instmv(rd, current_function_.regID_to_virtualReg.get(((Register) tmp_value).identifier_)));
                                        }
                                        else{
                                            VirtualReg tmp_reg = new VirtualReg(current_function_.current_register_id++, tmp_value.type_.size_);
                                            current_function_.regID_to_virtualReg.put(((Register) tmp_value).identifier_, tmp_reg);
                                            it_block.Insert(it_inst, new Instmv(rd, tmp_reg));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
