package IR;

import basic.IRtypes.IRArrayType;
import basic.IRtypes.IRIntType;
import basic.IRtypes.IRPointerType;

public class GlobalStringDefStmt extends Statement{
    public Register register_;
    public String raw_content_, content_;
    static int identifier_ = 0;

    public GlobalStringDefStmt(String _raw_content){
        int l = _raw_content.length();
        register_ = new Register(new IRPointerType(new IRArrayType(new IRIntType(8), l)), "string." + Integer.toString(identifier_++), true);
        raw_content_ = _raw_content;
        content_ = _raw_content.replace("\\", "\\5C").replace("\n", "\\0A").replace("\"", "\\22").replace("\0", "\\00") ;
    }

    @Override
    public String toString(){
        return register_ + " = string " + ((IRPointerType) register_.type_).type_ + " " + content_;
    }
}
