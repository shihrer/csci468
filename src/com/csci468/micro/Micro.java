package com.csci468.micro;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Vocabulary;

import java.io.IOException;

public class Micro {
    private String inputFile;
    MicroLexer lexer;
    Vocabulary vocab;

    public Micro(String inputPath) throws IOException {
        inputFile = inputPath;
        try
        {
            lexer = new MicroLexer(new ANTLRFileStream(inputFile));
        }
        catch(IOException e)
        {
            System.out.println(e.getLocalizedMessage());
        }

        vocab = lexer.getVocabulary();
    }

    public void Scan()
    {
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MicroParser parser = new MicroParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        try {
            parser.program();
            System.out.println("Accepted");
        }catch(Exception e){
            System.out.println("Not accepted");
        }

    }
}
