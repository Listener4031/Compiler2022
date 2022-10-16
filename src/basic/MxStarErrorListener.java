package basic;

import basic.error.SyntaxError;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class MxStarErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?,?> recognizer, Object object, int row, int column, String s, RecognitionException exception){
        throw new SyntaxError(new Locate(row,column),s);
    }
}
