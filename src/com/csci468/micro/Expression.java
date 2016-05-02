package com.csci468.micro;

class Expression {
    private String name;
    private String type;

    Expression(String name, String type){
        this.name = name;
        this.type = type;
    }

    String getType(){return this.type;}
    String getName() {return this.name;}
}
