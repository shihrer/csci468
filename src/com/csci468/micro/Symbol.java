package com.csci468.micro;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 28 March 2016
 */
class Symbol {
    private String name;
    private String type;
    private String value;

    Symbol(String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    Symbol(String name, String type) {
        this.name = name;
        this.type = type;
        this.value = "";
    }

    String getName() {
        return name;
    }

    String getType() {
        return type;
    }

    @Override
    public String toString() {
        if (value.isEmpty()) {
            return String.format("name %s type %s\n", this.name, this.type);
        } else {
            return String.format("name %s type %s value %s\n", this.name, this.type, this.value);
        }
    }
}
