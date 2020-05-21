package main.java;

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
import main.java.coupling.Coupling;


import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import java.net.URISyntaxException;

public class Main {

    private static final String filePath = "C:\\Users\\geeth\\OneDrive\\Documents\\SQ proj\\SENG4430_Assigment\\TestProject\\src\\main\\java\\coupling\\Coupling.java"; //"src/test/java/Tree_TestData/src/main.java";

    private static List<String> readFileInList(String fileName)
    {
        List<String> lineList = Collections.emptyList();
        try
        {
            lineList = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        }catch(IOException e){
            e.printStackTrace();
        }
        return lineList;
    }

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);
        List<String> lineList = readFileInList(filePath);

        // These solvers will also parse the other files around the given file as well as classes in the current JVM
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());
        combinedTypeSolver.add(new JavaParserTypeSolver(file.getParentFile()));

        try {
            combinedTypeSolver.add(new JarTypeSolver(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        // Configure JavaParser to use type resolution
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

        CompilationUnit newCu = StaticJavaParser.parse(new File(filePath));

        /*
        System.out.println("Fog Index Results:");
        FogIndex fogIndex = new FogIndex(lineList);
        fogIndex.showResults();

        System.out.println("\n");
         */
        //System.out.println("Response For a Class Results");

        /*
        RFC rfc = new RFC(cu);
        rfc.showResults();

        Tree t = new Tree(cu);
        t.result();
        */

        Coupling coupling = new Coupling(newCu);
        //coupling.showResult();
    }
}
