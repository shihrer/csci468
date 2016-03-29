package com.csci468.micro;

import java.util.Stack;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 26 March 2016
 */

class MicroSymbols extends MicroBaseListener {
    private Stack scopeStack;

    MicroSymbols()
    {
        scopeStack = new Stack();
    }

    @Override
    public void enterProgram(MicroParser.ProgramContext ctx){
        // This is our global scope
        String name = "GLOBAL";
        String output = String.format("Symbol table %s", name);

        System.out.println(output);
    }

    @Override
    public void enterFunc_decl(MicroParser.Func_declContext ctx) {
        String name = ctx.id().getText();
        String output = String.format("Symbol table %s", name);

        System.out.println(output);
    }
}
