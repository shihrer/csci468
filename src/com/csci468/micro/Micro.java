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

            for (IRNode irNode : irNodes) System.out.println(irNode.getIRCode());
            TinyGenerator gen = new TinyGenerator(irNodes);
            System.out.print(gen.getTiny());
            //System.out.print(microListener);

        }catch(ParseCancellationException e){
            System.out.println(e.getMessage());
            //System.out.println("Not accepted");
        }
    }
}
