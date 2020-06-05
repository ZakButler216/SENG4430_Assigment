package metrics;

/**
 File Name: Main.java
 Author: Cliff Ng
 Description: The user interface of the application.
 */


import com.github.javaparser.ast.CompilationUnit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 This class provides the interface view for the application.
 */
public class Main {


    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String scanInput;

        System.out.println("Welcome to JSQST Software Quality Tool.");
        System.out.println("There are 12 software quality metrics that this tool can measure:");
        System.out.println();

        Event.printMetricsList();
        System.out.println();

        Event.printCommands();
        System.out.println();

        boolean exit=false;

        while(exit!=true) {
            scanInput = scan.nextLine();
            exit = Event.processInput(scanInput);
        }



    }


}
