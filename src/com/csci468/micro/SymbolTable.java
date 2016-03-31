package com.csci468.micro;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 28 March 2016
 */
class SymbolTable {
    private Stack<Scope> scopeStack;
    private ArrayList<Scope> scopes;

    SymbolTable(){
        scopeStack = new Stack<>();
        scopes = new ArrayList<>();
        Scope global = new Scope("GLOBAL");
        scopeStack.push(global);
        scopes.add(global);
    }

    Scope pushScope(String name){
        Scope scope = new Scope(name);
        scopeStack.push(scope);
        scopes.add(scope);

        //System.out.println(String.format("Symbol table %s", name));

        return scope;
    }

    Scope popScope(){
        return scopeStack.pop();
    }

    //Define a symbol
    void createSymbol(String name, String type, String value) {
        Symbol symbol = new Symbol(name, type, value);

        scopeStack.peek().addSymbol(symbol);
        String output = String.format("name %s type %s value %s", name, type, value);
        //System.out.println(output);
    }
    void createSymbol(String name, String type){
        Symbol symbol = new Symbol(name, type);

        scopeStack.peek().addSymbol(symbol);
        String output = String.format("name %s type %s", name, type);
        //System.out.println(output);
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        for(Scope scope : scopes){
            output.append(scope);
        }
        return output.toString();
    }
}
