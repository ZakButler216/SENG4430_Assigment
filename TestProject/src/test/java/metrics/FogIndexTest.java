//Student Author: Zachery Butler
//Student Number: C3232981
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 30/05/2020

package metrics;

import org.junit.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class FogIndexTest {

    @Test
    public void test1() throws IOException {
        File file = new File("src\\test\\resources\\FogIndex_TestData\\TestText");
        FogIndex fi = new FogIndex(file);
        int fiResult = (int)fi.getFogIndex();
        assertEquals(13, fiResult);
    }

    @Test
    public void test2() throws IOException {
        File file = new File("src\\main\\java\\metrics\\FogIndex.java");
        FogIndex fi = new FogIndex(file);
        int fiResult = (int)fi.getFogIndex();
        assertEquals(6, fiResult);
    }
}