package AST;

import basic.Locate;
import basic.types.Type;

abstract public class ASTNode {
    public Locate position;
    public Type type;
    public ASTNode(Locate l){
        position=l;
    }

    abstract public void accept(ASTVisitor visitor);

}
/*
package AST;

import Util.Type;
import Util.position;

abstract public class ASTNode {
    public position pos ;
    public Type type ;

    public ASTNode (position _pos) {
        pos = _pos ;
    }

    abstract public void accept (ASTVisitor visitor) ;
}
 */