package metrics;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class NumOfChildrenTest {

    @Test //JUnit test num of children
    void numChildren() throws FileNotFoundException {
        String dir="src\\test\\resources\\srcInheritance";
        CherrenSection m = new CherrenSection();
        m.setup(dir);

        int result = m.getNumChildrenInt("Animal");
        assertEquals(3, result);
    }
}
