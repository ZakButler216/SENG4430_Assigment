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
import main.java.programSize.ProgramSize;
import main.java.coupling.tests.Node;

public class Main {

    private static final String filePath = "C:\\\\Users\\\\geeth\\\\OneDrive\\\\Documents\\\\SQ proj\\\\Prog\\\\src";
    //"C:\\\\Users\\\\geeth\\\\OneDrive\\\\Documents\\\\SQ proj\\\\SENG4430_Assigment\\\\TestProject\\\\src";
    private static List<String> readFileInList() {
        List<String> lineList = Collections.emptyList();
        try
        {
            lineList = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        }catch(IOException e){
            e.printStackTrace();
        }
        return lineList;
    }

    public static void main(String[] args) throws FileNotFoundException {

        /*
        CompilationUnit cu = StaticJavaParser.parse(new File(filePath));
        List<String> lineList = readFileInList();
        FogIndex fogIndex = new FogIndex(lineList);
        RFC rfc = new RFC(cu);
        //Tree t = new Tree(cu);
        System.out.println("Fog Index Results:");
        fogIndex.showResults();
        System.out.println("\n");
        System.out.println("Response For a Class Results");
        rfc.showResults();
        System.out.println("\n");
        //System.out.println("Depth of Tree/Inheritances Results");
        //t.result();
         */


        Parser p = new Parser();

        List<CompilationUnit> cuList = p.getCompilationUnits(filePath);
/*
        RFC rfc = new RFC(cuList.get(0));
        rfc.showResults();*/

        /*
        Tree t = new Tree(cuList.get(0));
        t.result();*/

        Coupling coupling = new Coupling(cuList, "");
        String results = coupling.getResults();
        System.out.println(results);

        ProgramSize progSize = new ProgramSize(cuList, "");
        String sizeResults = progSize.getResults();
        System.out.println(sizeResults);
        //coupling.showResult();
    }
}
