package com.csci468.micro;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 26 March 2016
 */

class Listener extends MicroBaseListener {
    private int count = 1;

    private SymbolTable microSymbolTable;

    Listener(){
        microSymbolTable = new SymbolTable();
    }

    @Override
    public void enterProgram(MicroParser.ProgramContext ctx){
        // This is our global scope - don't need to push a scope
        String name = "GLOBAL";
        String output = String.format("Symbol table %s", name);

        System.out.println(output);
    }

    @Override
    public void enterFuncDecl(MicroParser.FuncDeclContext ctx) {
        //Create new scope
        microSymbolTable.pushScope(ctx.id().getText());
        String name = ctx.id().getText();
        String output = String.format("Symbol table %s", name);

        System.out.println(output);
    }

    @Override
    public void enterIfStmt(MicroParser.IfStmtContext ctx){
        //Create new scope
        String name = "BLOCK " + count;
        count++;
        String output = String.format("Symbol table %s", name);

        System.out.println(output);
    }

    @Override
    public void enterWhileStmt(MicroParser.WhileStmtContext ctx){
        //Create new scope
        String name = "BLOCK " + count;
        count++;
        String output = String.format("Symbol table %s", name);

        System.out.println(output);
    }

    @Override
    public void enterElsePart(MicroParser.ElsePartContext ctx){
        if(!ctx.getText().equals("")){
            String name = "BLOCK " + count;
            count++;

            String output = String.format("Symbol table %s", name);

            System.out.println(output);
        }
    }

    @Override
    public void enterStringDecl(MicroParser.StringDeclContext ctx){
        //Create an entry in the current scope
        String input = ctx.getText();
        int cut1 = input.indexOf(":=");
        String name = input.substring(6,cut1);
        String value = input.substring(cut1+2,input.length()-1);
        String output = String.format("name %s type STRING value %s", name, value);

        System.out.println(output);
    }

    @Override
    public void enterVarDecl(MicroParser.VarDeclContext ctx){
        //Create an entry in the current scope
        String input = ctx.getText();
        if(input.startsWith("FLOAT")){
            String names = input.substring(5,input.length());
            String[] tokens = names.split(",");
            for (String s : tokens){
                s = s.replace(";","");
                String output = String.format("name %s type FLOAT", s);
                System.out.println(output);
            }
        }
        if(input.startsWith("INT")) {
            String names = input.substring(3,input.length());
            String[] tokens = names.split(",");
            for (String s : tokens){
                s = s.replace(";","");
                String output = String.format("name %s type INT", s);
                System.out.println(output);
            }
        }



    }
}
