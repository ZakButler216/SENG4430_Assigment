/*
 * File name:    FanOutTest.java
 * Author:       Naneth Sayao
 * Date:         26 May 2020
 * Version:      2.4
 * Description:  This is a JUnit test class for fan-out
 * */
package metrics;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FanOutTest {

    //this method parses a source and assert expected vs actual
    private void doTest(String source, String className, List<Integer> expected){
        //create a new parser
        Parser rootParser = new Parser();

        //array list of compilation units
        //initialise allCU
        List<CompilationUnit> allCU = rootParser.getCompilationUnits(source);

        //make fan in/out parser
        FanInOutParser fioParser = new FanInOutParser();

        //find the desired class and save the index of the desired class
        int desiredIndex = 0;
        for(int i = 0; i < allCU.size(); i++){
            String currentName = allCU.get(i).getPrimaryTypeName().toString();
            //substring currentName to remove i.e. "[" etc.
            currentName = currentName.substring(currentName.indexOf("[")+1);
            currentName = currentName.substring(0,currentName.indexOf("]"));

            //compare className to currentName
            if(currentName.equals(className)){
                desiredIndex = i;
            }
        }
        fioParser.singleClassVisitor(allCU.get(desiredIndex));

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
        String source = "src\\test\\resources\\srcValid";
        String className = "One";

        List<Integer> expected = new ArrayList<>();
        expected.add(4);
        expected.add(1);
        doTest(source, className, expected);
    }

    @Test
    void testCalculateFanOutValidTwo() {
        //path
        String source = "src\\test\\resources\\srcValid";
        String className = "Two";

        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(1);
        doTest(source, className, expected);
    }

    @Test
    void testCalculateFanOutValidThree() {
        //path
        String source = "src\\test\\resources\\srcConstructor";
        String className = "Try";

        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(1);
        doTest(source, className, expected);
    }

    @Test
    void testCalculateFanOutValidFour() {
        //path
        String source = "src\\test\\resources\\srcProject";
        String className = "FanInOutMethod";

        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(0);
        doTest(source, className, expected);
    }

    @Test
    void testCalculateFanOutValidFive() {
        //path
        String source = "src\\test\\resources\\srcProject";
        String className = "Main";

        List<Integer> expected = new ArrayList<>();
        expected.add(12);
        expected.add(6);
        doTest(source, className, expected);
    }
}