package com.csci468.micro;


import java.util.HashMap;
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
        symbolMap = new HashMap<>();
    }

    //Add method to insert a symbol to the scope
    void addSymbol(Symbol symbol){
        //Create new symbol
        //Add it to a scope
        if(!symbolMap.containsKey(symbol.name)){
            symbolMap.put(symbol.name, symbol);
        }else{
            //error
        }
    }
}
