package metrics;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 Test Comment Percentage functionality
 */
public class TestCommentPercentage {

    /**
     Testing: Count lines of code.
     Test Case: One.
      */
    @Test
    public void testOneTestCaseOneCountCode() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        //System.out.println(allCompilationUnits.size());
        //System.out.println(allCompilationUnits.get(0).getPrimaryTypeName());
        //System.out.println(parser.getClassNameFromCompilationUnit(allCompilationUnits.get(0)));

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCOne_Main");
        //System.out.println(cu.getPrimaryTypeName());


        CommentPercentage cp = new CommentPercentage();

        //Act (Invoke method and capture result)
        int linesOfCode = cp.countCode(cu);

        //Assert (Check)
        Assert.assertEquals(linesOfCode,24);



    }

    /**
     Testing: Count amount of comments.
     Test Case: One.
     */
    @Test
    public void testOneTestCaseOneCountComments() {

        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCOne_Main");
        CommentPercentage cp = new CommentPercentage();

        int numOfComments = cp.countComments(cu);

        Assert.assertEquals(numOfComments,11);
    }

    /**
     Testing: Get comment percentage
     Test Case: One.
     */
    @Test
    public void testOneTestCaseOneCommentPercentage() {
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCOne_Main");
        CommentPercentage cp = new CommentPercentage();

        double gottenPercentage = cp.getCommentPercentageForOneCompilationUnit(cu);
        double comments = 11;
        double code = 24;
        double calculatedPercentage = comments/code;
        double calculatedPercentageFormatted = Math.round(calculatedPercentage * 100.0)/ 100.0;

        Assert.assertEquals(gottenPercentage,calculatedPercentageFormatted,0.0001);

    }

    /**
     Testing: Count lines of code.
     Test Case: Two: Aisle class.
     */
    @Test
    public void testTwoAisleCountCode() {
        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Aisle");
        CommentPercentage cp = new CommentPercentage();

        //Act (Invoke method and capture result)
        int linesOfCode = cp.countCode(cu);

        //Assert (Check)
        Assert.assertEquals(linesOfCode,55);

    }

    /**
     Testing: Count amount of comments.
     Test Case: Two: Aisle class.
     */
    @Test
    public void testTwoAisleCountComments() {

        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Aisle");
        CommentPercentage cp = new CommentPercentage();

        int numOfComments = cp.countComments(cu);

        Assert.assertEquals(numOfComments,10);
    }

    /**
     Testing: Count comment percentage.
     Test Case: Two: Aisle class.
     */
    @Test
    public void testTwoAisleCommentPercentage() {

        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Aisle");
        CommentPercentage cp = new CommentPercentage();

        double gottenPercentage = cp.getCommentPercentageForOneCompilationUnit(cu);
        double comments = 10;
        double code = 55;
        double calculatedPercentage = comments/code;
        double calculatedPercentageFormatted = Math.round(calculatedPercentage * 100.0)/ 100.0;

        //System.out.println(calculatedPercentageFormatted);

        Assert.assertEquals(gottenPercentage,calculatedPercentageFormatted,0.0001);
    }

    /**
     Testing: Count lines of code.
     Test Case: Two: Item class.
     */
    @Test
    public void testThreeItemCountCode() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Item");
        CommentPercentage cp = new CommentPercentage();

        //Act (Invoke method and capture result)
        int linesOfCode = cp.countCode(cu);

        //Assert (Check)
        Assert.assertEquals(linesOfCode,27);


    }

    /**
     Testing: Count amount of comments.
     Test Case: Two: Item class.
     */
    @Test
    public void testThreeItemCountComments() {

        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Item");
        CommentPercentage cp = new CommentPercentage();

        int numOfComments = cp.countComments(cu);

        Assert.assertEquals(numOfComments,4);
    }

    /**
     Testing: Count comment percentage.
     Test Case: Two: Item class.
     */
    @Test
    public void testThreeItemCommentPercentage() {

        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Item");
        CommentPercentage cp = new CommentPercentage();

        double gottenPercentage = cp.getCommentPercentageForOneCompilationUnit(cu);
        double comments = 4;
        double code = 27;
        double calculatedPercentage = comments/code;
        double calculatedPercentageFormatted = Math.round(calculatedPercentage * 100.0)/ 100.0;

        Assert.assertEquals(gottenPercentage,calculatedPercentageFormatted,0.0001);
    }

    /**
     Testing: Count lines of code.
     Test Case: Two: Main class.
     */
    @Test
    public void testFourMainCountCode() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Main");
        CommentPercentage cp = new CommentPercentage();

        //Act (Invoke method and capture result)
        int linesOfCode = cp.countCode(cu);

        //Assert (Check)
        Assert.assertEquals(linesOfCode,23);
    }

    /**
     Testing: Count amount of comments.
     Test Case: Two: Main class.
     */
    @Test
    public void testFourMainCountComments() {

        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Main");
        CommentPercentage cp = new CommentPercentage();

        int numOfComments = cp.countComments(cu);

        Assert.assertEquals(numOfComments,6);
    }

    /**
     Testing: Count comment percentage.
     Test Case: Two: Main class.
     */
    @Test
    public void testFourMainCommentPercentage() {

        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Main");
        CommentPercentage cp = new CommentPercentage();

        double gottenPercentage = cp.getCommentPercentageForOneCompilationUnit(cu);
        double comments = 6;
        double code = 23;
        double calculatedPercentage = comments/code;
        double calculatedPercentageFormatted = Math.round(calculatedPercentage * 100.0)/ 100.0;

        Assert.assertEquals(gottenPercentage,calculatedPercentageFormatted,0.0001);
    }

    /**
     Testing: Count lines of code.
     Test Case: Two: Supermarket class.
     */
    @Test
    public void testFiveSupermarketCountCode() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Supermarket");
        CommentPercentage cp = new CommentPercentage();

        //Act (Invoke method and capture result)
        int linesOfCode = cp.countCode(cu);

        //Assert (Check)
        Assert.assertEquals(linesOfCode,37);
    }

    /**
     Testing: Count amount of comments.
     Test Case: Two: Supermarket class.
     */
    @Test
    public void testFiveSupermarketCountComments() {

        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Supermarket");
        CommentPercentage cp = new CommentPercentage();

        int numOfComments = cp.countComments(cu);

        Assert.assertEquals(numOfComments,7);
    }

    /**
     Testing: Count comment percentage.
     Test Case: Two: Supermarket class.
     */
    @Test
    public void testFiveSupermarketCommentPercentage() {

        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Supermarket");
        CommentPercentage cp = new CommentPercentage();

        double gottenPercentage = cp.getCommentPercentageForOneCompilationUnit(cu);
        double comments = 7;
        double code = 37;
        double calculatedPercentage = comments/code;
        double calculatedPercentageFormatted = Math.round(calculatedPercentage * 100.0)/ 100.0;

        Assert.assertEquals(gottenPercentage,calculatedPercentageFormatted,0.0001);
    }

    /**
     Testing: Count combined comment percentage.
     Test Case: Two: Item class, Main class, Supermarket class, Aisle class.
     */
    @Test
    public void testSixTestCaseTwoCommentPercentage() {

        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");
        CompilationUnit cuAisle = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Aisle");
        CompilationUnit cuMain = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Main");
        CompilationUnit cuItem = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Item");
        CompilationUnit cuSupermarket = parser.getCompilationUnitByName(allCompilationUnits,"comment_percentage.CP_TCTwo_Supermarket");

        List<CompilationUnit> testCaseTwoCompilationUnits = new ArrayList<>();

        testCaseTwoCompilationUnits.add(cuAisle);
        testCaseTwoCompilationUnits.add(cuMain);
        testCaseTwoCompilationUnits.add(cuItem);
        testCaseTwoCompilationUnits.add(cuSupermarket);


        CommentPercentage cp = new CommentPercentage();



        double gottenPercentage = cp.getCommentPercentageForAllCompilationUnits(testCaseTwoCompilationUnits);

        double aisleCommentPercentage = cp.getCommentPercentageForOneCompilationUnit(cuAisle);
        double itemCommentPercentage = cp.getCommentPercentageForOneCompilationUnit(cuItem);
        double mainCommentPercentage = cp.getCommentPercentageForOneCompilationUnit(cuMain);
        double supermarketCommentPercentage = cp.getCommentPercentageForOneCompilationUnit(cuSupermarket);

        double calculatedAveragePercentage = (aisleCommentPercentage + itemCommentPercentage
                + mainCommentPercentage + supermarketCommentPercentage) / testCaseTwoCompilationUnits.size();
        double calculatedPercentageFormatted = Math.round(calculatedAveragePercentage * 100.0)/ 100.0;

        Assert.assertEquals(gottenPercentage,calculatedPercentageFormatted,0.0001);

    }


    }
