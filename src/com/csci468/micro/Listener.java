package com.csci468.micro;

import javax.lang.model.type.MirroredTypeException;
import java.util.Stack;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 26 March 2016
 */

class Listener extends MicroBaseListener {

    @Override
    public void enterProgram(MicroParser.ProgramContext ctx){
        // This is our global scope
        String name = "GLOBAL";
        String output = String.format("Symbol table %s", name);

        System.out.println(output);
    }

    @Override
    public void enterFuncDecl(MicroParser.FuncDeclContext ctx) {
        //Create new scope
        String name = ctx.id().getText();
        String output = String.format("Symbol table %s", name);

        System.out.println(output);
    }

    @Override
    public void enterIfStmt(MicroParser.IfStmtContext ctx){
        //Create new scope
        String name = ctx.getText();
        String output = String.format("Symbol table %s", name);

        System.out.println(output);
    }

    @Override
    public void enterWhileStmt(MicroParser.WhileStmtContext ctx){
        //Create new scope
        String name = ctx.getText();
        String output = String.format("Symbol table %s", name);

        System.out.println(output);
    }

    @Override
    public void enterElsePart(MicroParser.ElsePartContext ctx){
        String name = ctx.getText();
        String output = String.format("Symbol table %s", name);

        System.out.println(output);
    }

    @Override
    public void enterStringDecl(MicroParser.StringDeclContext ctx){
        //Create an entry in the current scope

    }

    @Override
    public void enterVarDecl(MicroParser.VarDeclContext ctx){
        //Create an entry in the current scope

    }
}
