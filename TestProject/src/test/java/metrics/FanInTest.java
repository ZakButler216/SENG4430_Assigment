package metrics;

import Team2.Parser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FanInTest {

    //this method parses a source and assert expected vs actual
    private void doTest(String source, String className, List<Integer> expected){
        //create a new parser
        Parser rootParser = new Parser();

        //array list of compilation units
        //initialise allCU
        List<CompilationUnit> allCU = rootParser.getCompilationUnits(source);

        //make fan in/out parser
        FanInOutParser fioParser = new FanInOutParser();

        //clear our methodsList
        //fioParser.clearMethodsList();
        fioParser.wholeProjectVisitor(allCU);

        //make new FanIn object
        FanIn fi = new FanIn();
        //execute fan out method of FanOut object and save result
        List<Integer> result = fi.calculateFanIn(fioParser.getMethodsList(), className);
        for(int a = 0; a < expected.size(); a++){
            assertEquals(expected.get(a), result.get(a));
        }
    }

    @Test
    void testCalculateFanInValidOne() {
        //path
        String source = "srcValid";
        String className = "One";

        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(2);
        expected.add(1);
        expected.add(1);
        expected.add(0);
        doTest(source, className, expected);
    }

    @Test
    void testCalculateFanInValidTwo() {
        //path
        String source = "srcValid";
        String className = "Two";

        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(1);
        expected.add(0);
        expected.add(1);
        expected.add(0);
        doTest(source, className, expected);
    }

    @Test
    void testCalculateFanInValidThree() {
        //path
        String source = "srcConstructor";
        String className = "Try";

        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(0);
        expected.add(1);
        expected.add(0);
        expected.add(0);
        doTest(source, className, expected);
    }

    @Test
    void testCalculateFanInValidFour() {
        //path
        String source = "srcProject";
        String className = "FanInOutMethod";

        List<Integer> expected = new ArrayList<>();
        expected.add(8);
        expected.add(0);
        expected.add(0);
        expected.add(3);
        expected.add(5);
        doTest(source, className, expected);
    }

    @Test
    void testCalculateFanInValidFive() {
        //path
        String source = "srcProject";
        String className = "FanOut";

        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(1);
        expected.add(0);
        expected.add(1);
        expected.add(0);
        doTest(source, className, expected);
    }
}