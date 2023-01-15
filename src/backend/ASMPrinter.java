package backend;

import Assembly.ASMFunction;
import Assembly.ASMGlobalDef;
import Assembly.GlobalItem;

import java.io.PrintStream;

public class ASMPrinter {
    public ASMPrinter(PrintStream _out, ASMGlobalDef _global_def){
        _out.println("output:\n");
        for(ASMFunction it : _global_def.asm_functions){
            it.Print(_out);
            _out.println();
        }
        for(GlobalItem it : _global_def.global_items){
            it.Print(_out);
            _out.println();
        }
    }
}
