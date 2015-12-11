package info.fulloo.trygve.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class DebugPassListener extends KantBaseListener  {
    final static String TAB = "    ";
    private int indent = 0;

    public DebugPassListener() {

    }

    void tab(){
        for(int i = 0; i < indent; i++){
            System.err.print(TAB);
        }
    }

    @Override public void visitTerminal(final TerminalNode n){
        tab();
        System.err.format("> %s \"%s\"\n", n.getClass().getName(), n.getText());
    }

    @Override public void visitErrorNode(final ErrorNode n){
        tab();
        System.err.print("E ");
        System.err.println(n.getText());
    }

    @Override public void enterEveryRule(final ParserRuleContext c){
        tab();
        System.err.print(c.getClass().getName());
        System.err.println(" {");

        indent++;
    }

    @Override public void exitEveryRule(final ParserRuleContext c){
        indent--;
        tab();
        System.err.println("}");
    }
}