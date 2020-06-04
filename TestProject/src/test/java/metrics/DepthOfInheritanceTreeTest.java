package metrics;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


public class DepthOfInheritanceTreeTest {

    @Test//JUnit test num of children
    void DepthTree() throws FileNotFoundException {

        String dir="src\\test\\resources\\srcInheritance";
        Parser parser = new Parser();
        parser.setStoredCompilationUnits(parser.getCompilationUnits(dir));
        CherrenSection m = new CherrenSection();
        m.setup(dir);

        int result = m.getMaxDepthInt("Boar");
        assertEquals(2, result);
    }
}
