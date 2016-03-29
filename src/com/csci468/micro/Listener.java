package com.csci468.micro;

import java.util.Stack;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 26 March 2016
 */

class Listener extends MicroBaseListener {
    private Stack<String> scopeStack;

    Listener()
    {
        scopeStack = new Stack<>();
    }

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

        scopeStack.push(output);
    }
}
