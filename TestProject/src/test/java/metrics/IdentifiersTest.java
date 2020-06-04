package metrics;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;


import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import static org.junit.Assert.assertEquals;

public class IdentifiersTest {

    @Test
    public void test1() throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(new File("src\\test\\resources\\Identifiers\\ITC1.java"));
        Identifiers ident = new Identifiers();
        assertEquals("The average length of identifiers is: 3\n" +
                "The average length of identifiers is too short and not informative enough.", ident.getResult(cu).trim());
    }

    @Test
    public void test2() throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(new File("src\\test\\resources\\Identifiers\\IdentifiersTestClass2.java"));
        Identifiers ident = new Identifiers();
        assertEquals("The average length of identifiers is: 12\n" +
                "The average length of identifiers is too long and may affect readability.", ident.getResult(cu).trim());
    }
}






