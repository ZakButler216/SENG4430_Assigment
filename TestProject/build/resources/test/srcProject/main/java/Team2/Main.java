package Team2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final String filePath = "src/test/java/Tree_TestData/src/main.java";

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
        /*
        CompilationUnit cu = StaticJavaParser.parse(new File(filePath));
        List<String> lineList = readFileInList(filePath);

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

        //////////////////////////////////////( Fan-in/out: starts )/////////////////////////////////////////
        //path
        String s1 = "srcValid";
        //make fan in/out parser
        FanInOutParser fioParser = new FanInOutParser();

        //split all the .java files, then split all the methods in each .java file
        fioParser.wholeProjectVisitor(s1);

        //******************( fan-out )******************//
        //make new FanOut object
        FanOut fo = new FanOut();
        //execute fan out method of FanOut object
        List<Integer> result = fo.calculateFanOut(fioParser.getMethodsList());

        //******************( fan-in )******************//
        //make new FanIn object
        FanIn fi = new FanIn();
        //execute fan in method of FanIn object
        fi.calculateFanIn(fioParser.getMethodsList());

        ////////////////////////////////////( Fan-in/out: finished )///////////////////////////////////////

    }
}