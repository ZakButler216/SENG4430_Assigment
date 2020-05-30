package metrics;

import Team2.Parser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FanOutTest {

    //this method parses a source and assert expected vs actual
    private void doTest(String source, int index, List<Integer> expected){
        //create a new parser
        Team2.Parser rootParser = new Parser();

        //array list of compilation units
        //initialise allCU
        List<CompilationUnit> allCU = rootParser.getCompilationUnits(source);

        //make fan in/out parser
        FanInOutParser fioParser = new FanInOutParser();

        //clear our methodsList
        //fioParser.clearMethodsList();
        fioParser.singleClassVisitor(allCU.get(index));

        //make new FanOut object
        FanOut fo = new FanOut();
        //execute fan out method of FanOut object and save result
        List<Integer> result = fo.calculateFanOut(fioParser.getMethodsList());
        for(int a = 0; a < expected.size(); a++){
            assertEquals(expected.get(a), result.get(a));
        }
    }

    @Test
    void testCalculateFanOutValidOne() {
        //path
        String source = "srcValid";
        int index = 0; //for method 'One'

        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(1);
        doTest(source, index, expected);
    }

    @Test
    void testCalculateFanOutValidTwo() {
        //path
        String source = "srcValid";
        int index = 1; //for method 'Two'

        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(1);
        doTest(source, index, expected);
    }

    @Test
    void testCalculateFanOutValidThree() {
        //path
        String source = "srcConstructor";
        int index = 0; //for constructor 'Try'

        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(1);
        doTest(source, index, expected);
    }

    @Test
    void testCalculateFanOutValidFour() {
        //path
        String source = "srcProject";
        int index = 4; //for constructor 'Try'

        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(0);
        doTest(source, index, expected);
    }

    @Test
    void testCalculateFanOutValidFive() {
        //path
        String source = "srcProject";
        int index = 2; //for constructor 'Try'

        List<Integer> expected = new ArrayList<>();
        expected.add(5);
        expected.add(2);
        doTest(source, index, expected);
    }
}