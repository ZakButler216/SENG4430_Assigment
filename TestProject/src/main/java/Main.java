//Student Author: Zachery Butler
//Student Number: C3232981
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 20/05/2020

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

public class Main {

    private static final String filePath = "src";

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

        for(CompilationUnit cu : cuList){
            System.out.println(cu.getPrimaryTypeName());
        }
    }
}
