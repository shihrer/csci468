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

    //Store all scopes.  Necessary for outputting.
    private ArrayList<Scope> scopes;

    SymbolTable(){
        //Initialize stack
        scopeStack = new Stack<>();
        scopes = new ArrayList<>();

        //Create the default, global scope.
        Scope global = new Scope("GLOBAL");
        scopeStack.push(global);
        scopes.add(global);
    }

    Scope createScope(String name){
        Scope scope = new Scope(name);
        scopeStack.push(scope);
        scopes.add(scope);

        //System.out.println(String.format("Symbol table %s", name));
        return scope;
    }

    Scope destroyScope(){
        return scopeStack.pop();
    }

    //Define a symbol
    Symbol createSymbol(String name, String type, String value) {
        Symbol symbol = new Symbol(name, type, value);

        scopeStack.peek().addSymbol(symbol);
        String output = String.format("name %s type %s value %s", name, type, value);
        //System.out.println(output);

        return symbol;
    }
    Symbol createSymbol(String name, String type){
        Symbol symbol = new Symbol(name, type);

        scopeStack.peek().addSymbol(symbol);
        String output = String.format("name %s type %s", name, type);
        //System.out.println(output);

        return symbol;
    }

    Symbol getSymbol(String ID){
        for (Scope aScopeStack : scopeStack) {
            if (aScopeStack.hasSymbol(ID)) {
                return aScopeStack.getSymbol(ID);
            }
        }

        return null;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        for(Scope scope : scopes)
            output.append(scope);

        return output.toString();
    }
}
