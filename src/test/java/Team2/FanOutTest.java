/*
 * File name:    FanOutTest.java
 * Author:       Naneth Sayao
 * Date:         26 May 2020
 * Version:      2.0
 * Description:  Test fan-out with data that are;
 *                  - valid,
 *                  - invalid, and
 *                  - empty.
 * */

package Team2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FanOutTest {

    private void doTest(String s1, List<Integer> expected){
        //make fan in/out parser
        FanInOutParser fioParser = new FanInOutParser();

        //clear our methodsList
        fioParser.clearMethodsList();
        fioParser.classSplitter(s1);

        //make new FanOut object
        FanOut fo = new FanOut();
        //execute fan out method of FanOut object and save result
        List<Integer> result = fo.calculateFanOut(fioParser.getMethodsList());
        for(int a = 0; a < expected.size(); a++){
            assertEquals(expected.get(a), result.get(a));
        }
    }

    /*
    * This test is for a valid test
    * Should PASS
    * */
    @Test
    void testCalculateFanOutValid() {
        //path
        String s1 = "srcValid";

        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(0);
        doTest(s1, expected);
    }

    /*
     * This test is for an invalid test file
     * Should FAIL
     * */
    @Test
    void testCalculateFanOutInvalidFile() {
        //path
        String s1 = "srcInvalidFile";

        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(0);
        doTest(s1, expected);
    }

    /*
     * This test is for an empty source. There are no Java files.
     * Should PASS
     * */
    @Test
    void testCalculateFanOutEmpty() {
        //path
        String s1 = "srcEmptyNoFile";

        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(0);
        doTest(s1, expected);
    }
}
