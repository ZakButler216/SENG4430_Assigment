package metrics;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;

public class TestCommentPercentage {

@Test
public void testOneCountCode() {

    //Arrange (Arrange everything needed to run)
    Parser parser = new Parser();
    List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("C:\\Users\\Cliff\\Documents\\GitHub\\SENG4430_Assigment\\TestCases");
    CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"TestCaseCommentTypes");
    CommentPercentage cp = new CommentPercentage();

    //Act (Invoke method and capture result)
    int linesOfCode = cp.countCode(cu);

    //Assert (Check)
    Assert.assertEquals(linesOfCode,29);

}

@Test
public void testOneCountComments() {

    Parser parser = new Parser();
    List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("C:\\Users\\Cliff\\Documents\\GitHub\\SENG4430_Assigment\\TestCases");
    CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"TestCaseCommentTypes");
    CommentPercentage cp = new CommentPercentage();

    int numOfComments = cp.countComments(cu);

    Assert.assertEquals(numOfComments,16);
}

@Test
public void testOneCommentPercentage() {
    Parser parser = new Parser();
    List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("C:\\Users\\Cliff\\Documents\\GitHub\\SENG4430_Assigment\\TestCases");
    CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"TestCaseCommentTypes");
    CommentPercentage cp = new CommentPercentage();

    double gottenPercentage = cp.getCommentPercentageForOneCompilationUnit(cu);
    double comments = 16;
    double code = 29;
    double calculatedPercentage = comments/code;
    double calculatedPercentageFormatted = Math.round(calculatedPercentage * 100.0)/ 100.0;

    //System.out.println(calculatedPercentageFormatted);

    Assert.assertEquals(gottenPercentage,calculatedPercentageFormatted,0.0001);

}

}
