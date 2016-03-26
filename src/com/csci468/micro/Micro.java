package com.csci468.micro;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

class Micro {
    private MicroLexer lexer;

    Micro(String inputPath) throws IOException {
        try
        {
            lexer = new MicroLexer(new ANTLRFileStream(inputPath));
        }
        catch(IOException e)
        {
            System.out.println(e.getLocalizedMessage());
        }
    }

    void Scan()
    {
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MicroParser parser = new MicroParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        try {
            ParseTree tree = parser.program();
            ParseTreeWalker.DEFAULT.walk(new MicroSymbols(), tree);
        }catch(Exception e){
            System.out.println("Not accepted");
        }
    }
}
