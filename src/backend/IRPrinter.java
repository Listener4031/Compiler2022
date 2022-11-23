package backend;

import IR.GlobalDefinition;

import java.io.PrintStream;

public class IRPrinter {
    public void Print(PrintStream _out, GlobalDefinition _global_definition){
        for(int i = 0; i < _global_definition.global_def_statements.size(); i++){
            _out.println(_global_definition.global_def_statements.get(i));
        }
        _global_definition.functions_.forEach(it -> it.Print(_out));
    }
}
