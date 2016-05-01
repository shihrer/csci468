package com.csci468.micro;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.LinkedList;

class Micro {
    private MicroLexer lexer;

    Micro(String inputPath) throws IOException {
        try
        {
            lexer = new MicroLexer(new ANTLRFileStream(inputPath));
        }
        catch(IOException e2)
        {
            System.out.println(e2.getLocalizedMessage());
        }
    }

    void Scan()
    {
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MicroParser parser = new MicroParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        try {
            LinkedList<IRNode> irNodes = new LinkedList<>();
            ParseTree tree = parser.program();

            Listener microListener = new Listener(irNodes);
            ParseTreeWalker test = new ParseTreeWalker();
            test.walk(microListener, tree);

            //Testing functionality
//            LinkedList<IRNode> list = new LinkedList<>();
//            list.add(new IRNode("VAR", "count", "", ""));
//            list.add(new IRNode("VAR", "enough", "", ""));
//            list.add(new IRNode("VAR", "newapprox", "", ""));
//            list.add(new IRNode("VAR", "approx", "", ""));
//            list.add(new IRNode("VAR", "num", "", ""));
//            list.add(new IRNode("VAR", "tolerance", "", ""));
//            list.add(new IRNode("VAR", "diff", "", ""));
//            list.add(new IRNode("LABEL", "main", "", ""));
//            list.add(new IRNode("LINK", "", "", ""));
//            list.add(new IRNode("STORER", "0.0001", "$T1", ""));
//            list.add(new IRNode("STORER", "$T1", "tolerance", ""));
//            list.add(new IRNode("STORER", "7.0", "$T2", ""));
//            list.add(new IRNode("STORER", "$T2", "num", ""));
//            list.add(new IRNode("STORER", "num", "approx", ""));
//            list.add(new IRNode("STOREI", "0", "$T3", ""));
//            list.add(new IRNode("STOREI", "$T3", "count", ""));
//            list.add(new IRNode("STORER", "0.0", "$T4", ""));
//            list.add(new IRNode("STORER", "$T4", "diff", ""));
//            list.add(new IRNode("STOREI", "0", "$T5", ""));
//            list.add(new IRNode("STOREI", "$T5", "enough", ""));
//            list.add(new IRNode("LABEL", "label1", "", ""));
//            list.add(new IRNode("STOREI", "1", "$T6", ""));
//            list.add(new IRNode("EQI", "enough", "$T6", "label2"));
//            list.add(new IRNode("STOREI", "1", "$T7", ""));
//            list.add(new IRNode("ADDI", "count", "$T7", "$T8"));
//            list.add(new IRNode("STOREI", "$T8", "count", ""));
//            list.add(new IRNode("STORER", "0.5", "$T9", ""));
//            list.add(new IRNode("DIVR", "num", "approx", "$T10"));
//            list.add(new IRNode("ADDR", "approx", "$T10", "$T11"));
//            list.add(new IRNode("MULR", "$T9", "$T11", "$T12"));
//            list.add(new IRNode("STORER", "$T12", "newapprox", ""));
//            list.add(new IRNode("SUBR", "approx", "newapprox", "$T13"));
//            list.add(new IRNode("STORER", "$T13", "diff", ""));
//            list.add(new IRNode("STORER", "0.0", "$T14", ""));
//            list.add(new IRNode("LER", "diff", "$T14", "label3"));
//            list.add(new IRNode("GER", "diff", "tolerance", "label4"));
//            list.add(new IRNode("STOREI", "1", "$T15", ""));
//            list.add(new IRNode("STOREI", "$T15", "enough", ""));
//            list.add(new IRNode("LABEL", "label4", "", ""));
//            list.add(new IRNode("JUMP", "label5", "", ""));
//            list.add(new IRNode("LABEL", "label3", "", ""));
//            list.add(new IRNode("STORER", "0.0", "$T16", ""));
//            list.add(new IRNode("SUBR", "$T16", "tolerance", "$T17"));
//            list.add(new IRNode("LER", "diff", "$T17", "label6"));
//            list.add(new IRNode("STOREI", "1", "$T18", ""));
//            list.add(new IRNode("STOREI", "$T18", "enough", ""));
//            list.add(new IRNode("LABEL", "label6", "", ""));
//            list.add(new IRNode("LABEL", "label5", "", ""));
//            list.add(new IRNode("STORER", "newapprox", "approx", ""));
//            list.add(new IRNode("JUMP", "label1", "", ""));
//            list.add(new IRNode("LABEL", "label2", "", ""));
//            list.add(new IRNode("WRITER", "approx", "", ""));
//            list.add(new IRNode("WRITEI", "count", "", ""));
//            list.add(new IRNode("RET", "", "", ""));

            for (IRNode irNode : irNodes) System.out.println(irNode.getIRCode());
            //TinyGenerator gen = new TinyGenerator(irNodes);
            //System.out.print(gen.getTiny());
            //System.out.print(microListener);

        }catch(ParseCancellationException e){
            System.out.println(e.getMessage());
            //System.out.println("Not accepted");
        }
    }
}
