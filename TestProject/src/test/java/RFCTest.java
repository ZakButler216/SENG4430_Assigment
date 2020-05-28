import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import static org.junit.Assert.assertEquals;

public class RFCTest {

    @Test
    public void test1() throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(new File("src\\test\\RFCTestClass\\TestThing.java"));
        RFC rfc = new RFC(cu);
        assertEquals(6, rfc.getRfc());
    }
}