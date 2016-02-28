package com.csci468.micro.scanner;

import org.antlr.v4.runtime.*;

import java.io.IOException;

public class MicroScanner {
    private String inputFile;
    MicroLexer lexer;
    Vocabulary vocab;

    public MicroScanner(String inputPath) throws IOException {
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

        MicroLexerParser parser = new MicroLexerParser(tokens);

        parser.program();
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
