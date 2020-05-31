import com.github.javaparser.ast.CompilationUnit;
import programSize.ProgramSize;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
 
import static org.junit.Assert.*;

public class ProgramSizeTest {

    @org.junit.Test
    public void ProgramSize1() {
        String filePath = "C:\\\\Users\\\\geeth\\\\OneDrive\\\\Documents\\\\SQ proj\\\\Prog\\\\src";
        String outputTest = "";
        try {
            File myObj = new File(".\\src\\test\\testingText\\outputTest3.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                outputTest += myReader.nextLine() + "\n";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Parser p = new Parser();

        List<CompilationUnit> cuList = p.getCompilationUnits(filePath);

        ProgramSize progSize = new ProgramSize(cuList, "");
        String sizeResults = progSize.getResults();

        assertEquals(outputTest, sizeResults);
    }

    @org.junit.Test
    public void ProgramSize2() {
        String filePath = "C:\\\\Users\\\\geeth\\\\OneDrive\\\\Documents\\\\SQ proj\\\\Prog\\\\src";
        String outputTest = "";
        try {
            File myObj = new File(".\\src\\test\\testingText\\outputTest4.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                outputTest += myReader.nextLine()  + "\n";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Parser p = new Parser();

        List<CompilationUnit> cuList = p.getCompilationUnits(filePath);

        ProgramSize progSize = new ProgramSize(cuList, "PA3");
        String sizeResults = progSize.getResults();

        assertEquals(outputTest, sizeResults);
    }
}