/*
 * File name:    FanInTest.java
 * Author:       Naneth Sayao
 * Date:         26 May 2020
 * Version:      2.1
 * Description:  Test fan-in with data that are;
 *                  - valid,
 *                  - invalid, and
 *                  - empty.
 * */

package Team2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FanInTest {

    private void doTest(String s1, List<Integer> expected){
        //make fan in/out parser
        FanInOutParser fioParser = new FanInOutParser();

        fioParser.classSplitter(s1);

        //make new FanIn object
        FanIn fi = new FanIn();
        //execute fan out method of FanOut object and save result
        List<Integer> result = fi.calculateFanIn(fioParser.getMethodsList());
        for(int a = 0; a < expected.size(); a++){
            assertEquals(expected.get(a), result.get(a));
        }
    }

    /*
     * This test is for a valid test
     * Should PASS
     * */
    @Test
    void testCalculateFanInValid() {
        //path
        String s1 = "srcValid";

        List<Integer> expected = new ArrayList<>();
        expected.add(6);
        expected.add(3);
        expected.add(0);
        expected.add(3);
        expected.add(0);
        doTest(s1, expected);
    }

    @Test
    void testCalculateFanInInvalidFile() {
        //path
        String s1 = "srcInvalidFile";

        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(0);
        expected.add(0);
        expected.add(0);
        expected.add(0);
        doTest(s1, expected);
    }

    /*
     * This test is for an empty source. There are no Java files.
     * Should PASS
     * */
    @Test
    void testCalculateFanInEmpty() {
        //path
        String s1 = "srcEmptyNoFile";

        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(0);
        expected.add(0);
        expected.add(0);
        expected.add(0);
        doTest(s1, expected);
    }

}
