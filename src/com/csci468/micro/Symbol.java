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
        this.value = "";
    }

    @Override
    public String toString(){
        if(value.isEmpty()) {
            return String.format("name %s type %s\n", this.name, this.type);
        }else{
            return String.format("name %s type %s value %s\n", this.name, this.type, this.value);
        }
    }
}
