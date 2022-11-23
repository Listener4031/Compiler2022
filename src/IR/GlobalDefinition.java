package IR;

import java.util.ArrayList;

public class GlobalDefinition {
    public ArrayList<Statement> global_def_statements;
    public ArrayList<Function> functions_;

    public GlobalDefinition(){
        this.global_def_statements = new ArrayList<>();
        this.functions_ = new ArrayList<>();
    }
}
