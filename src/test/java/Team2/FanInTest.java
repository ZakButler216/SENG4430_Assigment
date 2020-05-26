package Team2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FanInTest {

    private void doTest(String s1, List<Integer> expected){
        //clear our methodsList
        Main.clearMethodsList();
        Main.classSplitter(s1);

        //make new FanIn object
        FanIn fi = new FanIn();
        //execute fan out method of FanOut object and save result
        List<Integer> result = fi.calculateFanIn(Main.getMethodsList());
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
        expected.add(3);
        expected.add(3);
        expected.add(0);
        expected.add(3);
        expected.add(0);
        doTest(s1, expected);
    }

    /*
     * This test is for an invalid assert test
     * Should FAIL
     * */
    @Test
    void testCalculateFanInInvalidAssert() {
        //path
        String s1 = "srcValid";

        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(0);
        expected.add(0);
        expected.add(0);
        expected.add(1);
        doTest(s1, expected);
    }

    /*
     * This test is for an invalid test file
     * Should PASS
     * */
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