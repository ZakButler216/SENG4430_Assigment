package metrics;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {
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




/*
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

        CompilationUnit cu = StaticJavaParser.parse(new File(filePath));
        List<String> lineList = readFileInList(filePath);

        System.out.println("Fog Index Results:");
        FogIndex fogIndex = new FogIndex(lineList);
        fogIndex.showResults();

        System.out.println("\n");

        System.out.println("Response For a Class Results");
        RFC rfc = new RFC(cu);
        rfc.showResults();

        Tree t = new Tree(cu);
        t.result();
    }
}
*/