//Student Author: Zachery Butler
//Student Number: C3232981
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 30/05/2020
package metrics;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import static org.junit.Assert.assertEquals;

public class RFCTest {

    @Test
    public void test1() throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(new File("src\\test\\resources\\RFC_TestClass\\TestThing.java"));
        RFC rfc = new RFC(cu);
        assertEquals(6, rfc.getRfc());
    }

    @Test
    public void test2() throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(new File("src\\main\\java\\metrics\\RFC.java"));
        RFC rfc = new RFC(cu);
        assertEquals(3, rfc.getRfc());
    }
}