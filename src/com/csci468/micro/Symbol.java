package com.csci468.micro;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 28 March 2016
 */
class Symbol {
    protected String name;
    protected String type;
    protected String value;

    public Symbol(String name, String type, String value){
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Symbol(String name, String type){
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString(){
        throw new UnsupportedOperationException("This is not implemented yet.");
    }
}
