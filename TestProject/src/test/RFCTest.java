import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RFCTest {

    @Test
    public void test1(){
        final String directory = "src\\test\\RFCTestClass";
        Parser p = new Parser();
        List<CompilationUnit> cuList = p.getCompilationUnits(directory);
        CompilationUnit cu = cuList.get(0);
        RFC rfc = new RFC(cu);

    }

}