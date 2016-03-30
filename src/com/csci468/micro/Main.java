package com.csci468.micro;

import java.io.IOException;

class Main {

    public static void main(String[] args) throws IOException {
        args = new String[1];
        args[0] = "resources//Step3//inputs//test6.micro";

        if(args.length > 0) {
                String input = args[0];
                Micro micro = new Micro(input);
                micro.Scan();
        }
        else
            System.out.println("You must supply an input.");
    }
}