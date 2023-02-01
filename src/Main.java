import AST.*;
import Assembly.ASMGlobalDef;
import IR.GlobalDefinition;
import backend.ASMBuilder;
import backend.ASMPrinter;
import backend.IRBuilder;
import backend.IRPrinter;
import frontend.*;
import basic.*;
import Parser.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) throws Exception{
        String file_name = "test.mx";
        InputStream raw = new FileInputStream(file_name);
        try {
            CharStream input = CharStreams.fromStream(raw);
            MxStarLexer lexer = new MxStarLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxStarErrorListener());
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MxStarParser parser = new MxStarParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new MxStarErrorListener());
            ParseTree parseTree = parser.program();
            ASTBuilder astBuilder = new ASTBuilder();
            ProgramNode root = (ProgramNode) astBuilder.visit(parseTree);
            GlobalScope scope = new GlobalScope();
            new SymbolCollector(scope).visit(root);
            new SemanticChecker(scope).visit(root);

            GlobalDefinition globalDefinition = new GlobalDefinition();
            new IRBuilder(globalDefinition, scope).visit(root);
            PrintStream IR_out = new PrintStream("llvm_test.ll");
            new IRPrinter().Print(IR_out, globalDefinition);
            //ASMGlobalDef asm_global_def = new ASMGlobalDef();
            //new ASMBuilder(globalDefinition, asm_global_def);
            //PrintStream ASM_out = new PrintStream("test.s");
            //new ASMPrinter(ASM_out, asm_global_def);
        }
        catch (Error error){
            System.err.println(error.toString());
            throw new RuntimeException();
        }
    }
}
