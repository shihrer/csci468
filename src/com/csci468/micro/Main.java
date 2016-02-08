package com.csci468.micro;

import com.csci468.micro.scanner.MicroScanner;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        //Read java arg for input
        String input = "PROGRAM sqrt\n" +
                "BEGIN\n" +
                "\n" +
                "\tSTRING dummy := \"abcde\";  --This is dummy to satisfy the grammar\n" +
                "\n" +
                "\tFLOAT n;\n" +
                "\tFLOAT x1,x2;\n" +
                "\tFLOAT fx, dfx;\n" +
                "\tFLOAT error;\n" +
                "\tINT i;\n" +
                "\n" +
                "\tFUNCTION VOID main()\n" +
                "\tBEGIN\n" +
                "\t\terror := 0.001;\n" +
                "\t\tREAD (x1);\n" +
                "\t\tfx := x1*x1 - n;\n" +
                "\t\tdfx := 2.0*x1;\n" +
                "\t\tx2 := x1 - fx/dfx;\n" +
                "\n" +
                "\t\tx1 := x2;\n" +
                "\t\tfx := x1*x1 - n;\n" +
                "\t\tdfx := 2.0*x1;\n" +
                "\t\tx2 := x1 - fx/dfx;\n" +
                "\t\tWHILE ((x1 - x2) > error)\n" +
                "\t\t\tx1 := x2;\n" +
                "\t\t\tfx := x1*x1 - n;\n" +
                "\t\t\tdfx := 2.0*x1;\n" +
                "\t\t\tx2 := x1 - fx/dfx;\n" +
                "\t\tENDWHILE\n" +
                "\n" +
                "\t\tWRITE (x2);\n" +
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
