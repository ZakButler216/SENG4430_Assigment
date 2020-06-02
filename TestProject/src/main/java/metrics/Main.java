package metrics;

import com.github.javaparser.ast.CompilationUnit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
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



       /* String s1="C:\\Users\\Cliff\\eclipse-workspace\\TestMultipleSourceRoots";
        Parser parser = new Parser();
        //parser.setStoredDirectory(s1);
        List<CompilationUnit> allUnits = parser.getCompilationUnits(s1);

        */

        /*for(int i=0;i<allUnits.size();i++) {
            CompilationUnit unit = allUnits.get(i);
            System.out.println(unit.getPackageDeclaration().get().getName());
            System.out.println(unit.getPrimaryTypeName());
        }

         */
        /*List<String> allClasses = parser.getClassesAsString(allUnits);
        for(int i=0;i<allClasses.size();i++) {
            System.out.println(allClasses.get(i));
        }

        CompilationUnit cu = parser.getCompilationUnitByName(allUnits,"data.Car");
        System.out.println(cu.getPrimaryTypeName());

         */

    }


}
