package Assembly;

import java.io.PrintStream;

public class GlobalString extends GlobalItem{
    public String identifier_;
    public String raw_content_, content_;
    public int size_;

    public GlobalString(String _identifier, String _raw_content){
        identifier_ = _identifier;
        raw_content_ = _raw_content;
        content_ = _raw_content.replace("\\5C", "\\\\").replace("\\0A", "\\n").replace("\\22", "\\\"").replace("\\00", "\0");
        size_ = content_.length();
    }

    @Override
    public void Print(PrintStream _out) {
        _out.println("global_string " + identifier_);
        _out.println("size: " + size_);
        _out.println("content: " + content_);
    }
}
