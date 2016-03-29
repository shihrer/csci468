package com.csci468.micro;


import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 26 March 2016
 */
class Scope {
    private Map<String, Symbol> symbolMap;

    private String name;

    Scope(String name){
        this.name = name;
        symbolMap = new LinkedHashMap<>();
    }

    //Add method to insert a symbol to the scope
    void addSymbol(Symbol symbol){
        //Create new symbol
        //Add it to a scope
        if(!symbolMap.containsKey(symbol.name)){
            symbolMap.put(symbol.name, symbol);
        }else{
            throw new ParseCancellationException(String.format("DECLARATION ERROR %s", symbol.name));
        }
    }

    public String toString(){
        StringBuilder output = new StringBuilder();

        output.append(String.format("Symbol table %s\n", this.name));
        //Append symbols
        for(Map.Entry<String, Symbol> entry : symbolMap.entrySet()){
            Symbol symbol = entry.getValue();
            output.append(symbol);
        }

        output.append("\n");
        return output.toString();
    }
}
