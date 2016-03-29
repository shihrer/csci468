package com.csci468.micro;

import java.util.Stack;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 28 March 2016
 */
class SymbolTable {
    private Stack<Scope> scopeStack;

    SymbolTable(){
        scopeStack = new Stack<>();
        scopeStack.push(new Scope("GLOBAL"));
    }

    Scope pushScope(String name){
        Scope scope = new Scope(name);
        scopeStack.push(scope);

        return scope;
    }

    public Scope popScope(){
        return scopeStack.pop();
    }

    //Define a symbol
    public void createSymbol(String name, String type, String value) {
        Symbol symbol = new Symbol(name, type, value);

        scopeStack.peek().addSymbol(symbol);
    }
    public void createSymbol(String name, String type){

    }
}
