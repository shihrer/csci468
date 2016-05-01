package com.csci468.micro;

import java.util.LinkedList;

class TinyGenerator {

    //Stores var declarations
    private StringBuilder vars = new StringBuilder();
    //Builds the Tiny code
    private StringBuilder tiny = new StringBuilder();

    private LinkedList<IRNode> code;

    TinyGenerator (LinkedList<IRNode> IRcode){
        code = IRcode;
        generate();
    }

    private void generate(){
        while (!code.isEmpty()){
            IRNode current = code.pop();
            int register = 0;
            String opcode = current.get_opcode();
            String f1 = current.get_first();
            String f2 = current.get_second();
            String f3 = current.get_third();
            //Temp register for cases such as: a := b;
            //need to first assign a to r99 and then r99 to b.
            String temp = "r99";

            switch (opcode){
                //int and float variable declaration
                case "VAR":
                    vars.append(String.format("var %s\n", f1));
                    break;
                //String variable declaration
                case "STR":
                    vars.append(String.format("var %s %s\n", f1, f2));
                    break;
                //Labels
                case "LABEL":
                    tiny.append(String.format("label %s\n", f1));
                    break;
                //Unconditional jump
                case "JUMP":
                    tiny.append(String.format("jmp %s\n", f1));
                    break;
                //Read inputs
                case "READI":
                case "READF":
                    tiny.append(String.format("sys %s %s\n", opcode.toLowerCase(), f1));
                    break;
                //Assignment statements
                case "STOREI":
                case "STOREF":
                    //check to catch statements such as a := b;
                    if(!f1.contains("$") && !f2.contains("$")){
                        tiny.append(String.format("move %s %s\n", f1.replace("$T", "r"), temp));
                        tiny.append(String.format("move %s %s\n", temp, f2.replace("$T", "r")));
                    }else {
                        tiny.append(String.format("move %s %s\n", f1.replace("$T", "r"), f2.replace("$T", "r")));
                    }
                    break;
                //Increment and decriment
                case "INCI":
                case "DECI":
                    tiny.append(String.format("%s %s\n", opcode.toLowerCase(), f1.replace("$T","r")));
                    break;
                //Addition
                case "ADDI":
                case "ADDR":
                    tiny.append(String.format("move %s %s\n", f1.replace("$T","r"), f3.replace("$T","r")));
                    tiny.append(String.format("%s %s %s\n", opcode.toLowerCase(), f2.replace("$T","r"), f3.replace("$T","r")));
                    break;
                //Subtraction
                case "SUBI":
                case "SUBR":
                    tiny.append(String.format("move %s %s\n", f1.replace("$T","r"), f3.replace("$T","r")));
                    tiny.append(String.format("%s %s %s\n", opcode.toLowerCase(), f2.replace("$T","r"), f3.replace("$T","r")));
                    break;
                //Multiplication
                case "MULI":
                case "MULR":
                    tiny.append(String.format("move %s %s\n", f1.replace("$T","r"), f3.replace("$T","r")));
                    tiny.append(String.format("%s %s %s\n", opcode.toLowerCase(), f2.replace("$T","r"), f3.replace("$T","r")));
                    break;
                //Division
                case "DIVI":
                case "DIVR":
                    tiny.append(String.format("move %s %s\n", f1.replace("$T","r"), f3.replace("$T","r")));
                    tiny.append(String.format("%s %s %s\n", opcode.toLowerCase(), f2.replace("$T","r"), f3.replace("$T","r")));
                    break;

                //Integer comparison
                case "EQI":
                case "NEI":
                case "GEI":
                case "LEI":
                case "LTI":
                case "GTI":
                    if(!f1.contains("$") && !f2.contains("$")){
                        tiny.append(String.format("move %s %s\n", f2, temp));
                        tiny.append(String.format("cmpi %s %s\n", f1, temp));
                    }else {
                        tiny.append(String.format("cmpi %s %s\n", f1, f2.replace("$T","r")));
                    }
                    tiny.append(String.format("j%s %s\n", opcode.substring(0,2).toLowerCase(), f3));
                    break;
                //Real number comparison
                case "EQR":
                case "NER":
                case "GER":
                case "LER":
                case "LTR":
                case "GTR":
                    if(!f1.contains("$") && !f2.contains("$")){
                        tiny.append(String.format("move %s %s\n", f2, temp));
                        tiny.append(String.format("cmpr %s %s\n", f1, temp));
                    }else {
                        tiny.append(String.format("cmpr %s %s\n", f1, f2.replace("$T","r")));
                    }
                    tiny.append(String.format("j%s %s\n", opcode.substring(0,2).toLowerCase(), f3));
                    break;
                //System output
                case "WRITEI":
                case "WRITES":
                case "WRITER":
                    tiny.append(String.format("sys %s %s\n", opcode.toLowerCase(), f1));
                    break;
                //Links for function calls
                case "LINK":
                case "RET":
                    //unused
                    break;
                default:
                    System.out.println("Invalid opcode: " + opcode);

            }
        }
        tiny.append("sys halt\n");
        tiny.insert(0, vars);
    }

    String getTiny(){ return tiny.toString(); }
}
