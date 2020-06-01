package metrics;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class CouplingTest {

    @Test
    public void Coupling1() {
        //String filePath = "C:\\\\Users\\\\geeth\\\\OneDrive\\\\Documents\\\\SQ proj\\\\Prog\\\\src";
        String filePath = "src\\test\\resources\\Prog\\src";
        String outputTest = "";

        try {
            File myObj = new File(".\\src\\test\\resources\\testingText\\outputTest.txt");
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

        Coupling coupling = new Coupling(cuList, "");
        String results = coupling.getResults();

        assertEquals(outputTest, results);
    }

    @Test
    public void Coupling2() {
        String filePath = "src\\test\\resources\\Prog\\src";
        String outputTest = "";
        try {
            File myObj = new File(".\\src\\test\\resources\\testingText\\outputTest2.txt");
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

        Coupling coupling = new Coupling(cuList, "PA3");
        String results = coupling.getResults();

        assertEquals(outputTest, results);
    }
}