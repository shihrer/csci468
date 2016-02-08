package com.csci468.micro;

import com.csci468.micro.scanner.MicroScanner;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        //Read java arg for input
        String input = "PROGRAM fibonacci\n" +
                "BEGIN\n" +
                "\n" +
                "\tSTRING input := \"Please input an integer number: \";\n" +
                "\tSTRING space := \" \";\n" +
                "\tSTRING eol := \"\\n\";\n" +
                "\n" +
                "\tFUNCTION INT F (INT n)\n" +
                "\tBEGIN\n" +
                "\n" +
                "\t\tIF (n > 2)\n" +
                "\t\t     RETURN F(n-1)+F(n-2);\n" +
                "\t\tELSE\n" +
                "\t\t\tRETURN 1;\n" +
                "\t    ENDIF\n" +
                "\tEND\n" +
                "\t\n" +
                "\t\n" +
                "\tFUNCTION VOID main ()\n" +
                "\tBEGIN\n" +
                "\t\tINT i, end, result;\n" +
                "\t\tWRITE(input);\n" +
                "\t\tREAD(end);\n" +
                "\n" +
                "\ti := 0;\n" +
                "\tWHILE (i != end)\n" +
                "\t\tresult := F(i);\n" +
                "\t\tWRITE (i,space);\n" +
                "\t\tWRITE (result,eol);\n" +
                "\t\ti := i + 1;\t\n" +
                "\tENDWHILE\n" +
                "\n" +
                "\tEND\n" +
                "\n" +
                "END\t\n";

        if(args.length > 0)
            input = args[0];
        //else
            //System.out.println("You must supply an input.");

        MicroScanner microScanner = new MicroScanner(input);
        microScanner.Scan();
    }
}
