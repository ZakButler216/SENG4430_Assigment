
package metrics;


import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import java.util.ArrayList;


import java.io.FileNotFoundException;
import java.util.Scanner;

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