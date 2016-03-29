package com.csci468.micro;

import java.util.Stack;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 28 March 2016
 */
class SymbolTable {
    private Stack<Scope> scopeStack;

    public Scope pushScope(){
        Scope scope = new Scope();
        scopeStack.push(scope);

        return scope;
    }

    public Scope popScope(){
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    //Define a symbol
    public void createSymbol() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
