package com.csci468.micro;

import com.csci468.micro.scanner.MicroScanner;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = "";

        if(args.length > 0)
            input = args[0];
        else
            System.out.println("You must supply an input.");

        MicroScanner microScanner = new MicroScanner(input);
        microScanner.Scan();


    }
}