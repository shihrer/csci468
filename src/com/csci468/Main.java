package com.csci468;
import com.csci468.scanner.LittleLexer;
import org.antlr.v4.runtime.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String src = "PROGRAM test\nBEGIN\nSTRING input := \"Please input an integer number: \"";

        LittleLexer lexer = new LittleLexer(new ANTLRInputStream(src));
        Vocabulary vocab = lexer.getVocabulary();

        while(true)
        {
            Token token = lexer.nextToken();
            if(lexer._hitEOF)
                break;

            System.out.println("Token Type: " + vocab.getSymbolicName(token.getType()) + "\nValue: " + token.getText());
        }
    }
}
