package com.csci468.micro;

import com.csci468.micro.scanner.MicroScanner;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        //Read java arg for input
        String input = "PROGRAM loop\n" +
                "BEGIN\n" +
                "\n" +
                "     STRING guess := \"Guess a number: \";\n" +
                "     STRING correct := \"Correct!\\n\";\n" +
                "     STRING wrong := \"Wrong!\\n\";\n" +
                "     STRING out1 := \"It took you \";\n" +
                "     STRING out2 := \" guesses\";\n" +
                "\n" +
                "\tINT i;\n" +
                "\tINT j;\n" +
                "\n" +
                "\t--PROTO VOID main();\n" +
                "\n" +
                "\tFUNCTION VOID main()\n" +
                "\tBEGIN\n" +
                "\t\ti := 17;\n" +
                "\t\tj := 0;\n" +
                "\t\tk := 0;\n" +
                "\t\tWHILE (i != j)\n" +
                "\t\t\tWRITE(guess);\n" +
                "\t\t\tREAD(j);\n" +
                "\t\t\tk := k + 1;\n" +
                "\t\t\tIF (i = j)\n" +
                "\t\t\t     WRITE(correct);\n" +
                "\t\t\tELSE\n" +
                "\t\t\t     WRITE(wrong);\n" +
                "\t\t\tENDIF\n" +
                "\t\tENDWHILE\n" +
                "\n" +
                "\t\tWRITE (out1, k, out2);\n" +
                "\tEND\n" +
                "END\n";

        if(args.length > 0)
            input = args[0];
        //else
            //System.out.println("You must supply an input.");

        MicroScanner microScanner = new MicroScanner(input);
        microScanner.Scan();
    }
}
