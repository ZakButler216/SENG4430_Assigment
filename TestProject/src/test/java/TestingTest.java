package java;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TestingTest {

    @Test
        //JUnit test num of children
    void numChildren() throws FileNotFoundException {
        CherrenSection m = new CherrenSection();
        m.setup();

        int result = m.getNumChildrenInt("Animal");
        assertEquals(3, result);
    }

    @Test
    //JUnit test num of children
    void DepthTree() throws FileNotFoundException {
        CherrenSection m = new CherrenSection();
        m.setup();

        int result = m.getMaxDepthInt("Boar");
        assertEquals(2, result);
    }
}