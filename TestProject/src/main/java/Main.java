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

    private static final String filePath = "src/main/java/TestThing.java";

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
    }
}
