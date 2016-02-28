package com.csci468.micro.scanner;

import org.antlr.runtime.EarlyExitException;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.IOException;

public class MicroScanner {
    private String inputFile;
    MicroLexerLexer lexer;
    Vocabulary vocab;

    public MicroScanner(String inputPath) throws IOException {
        inputFile = inputPath;
        try
        {
            lexer = new MicroLexerLexer(new ANTLRFileStream(inputFile));
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

        MicroLexerParser parser = new MicroLexerParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        try {
            parser.program();
            System.out.println("success");
        }catch(Exception e){
            System.out.println("error");
        }

        /*for(Token token: lexer.getAllTokens())
        {
            System.out.println("Token Type: " + vocab.getDisplayName(token.getType()) + "\nValue: " + token.getText());

            if(vocab.getDisplayName(token.getType()).equals("COMMENT")) {
                int i = 0;
            }
        }
        */

    }
}
