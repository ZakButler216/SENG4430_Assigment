package metrics;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import static org.junit.Assert.assertEquals;

public class LCOMTest {

    @Test
    public void test1() throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(new File("src\\test\\resources\\LCOM\\LCOMTest1.java"));
        LCOM lcom = new LCOM();
        assertEquals("The lack of cohesion of methods for the class is 0.67\n" +
                "This result is too high. The methods in this class should be revised.", lcom.getResult(cu).trim());
    }

    @Test
    public void test2() throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(new File("src\\test\\resources\\LCOM\\LCOMTest2.java"));
        LCOM lcom = new LCOM();
        assertEquals("The lack of cohesion of methods for the class is 0\n" +
                "This is an acceptable result.", lcom.getResult(cu).trim());
    }
}








