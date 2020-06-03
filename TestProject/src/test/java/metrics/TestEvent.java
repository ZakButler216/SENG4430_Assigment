package metrics;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;


public class TestEvent {

    /*
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


     */



    @Test
    public void testProcessInputNewOne() throws IOException {

        //Arrange
        Parser parser = new Parser();
        parser.setStoredDirectory(null);

        String userInput = "new src";
        String planStored = "src";

        //Act
        Event.processInput(userInput);
        String actualStored = Parser.getStoredDirectory();

        //Assert
        Assert.assertEquals(planStored,actualStored);



    }

    @Test
    public void testProcessInputNewTwo() throws IOException {

        //Arrange
        Parser parser = new Parser();
        parser.setStoredDirectory(null);

        String userInput = "new             src  ";
        String planStored = "src";

        //Act
        Event.processInput(userInput);
        String actualStored = Parser.getStoredDirectory();

        //Assert
        Assert.assertEquals(planStored,actualStored);


    }

    @Test
    public void testProcessInputNewThree() throws IOException {

        //Arrange
        Parser parser = new Parser();
        parser.setStoredDirectory(null);

        String userInput = "new     ";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        //Act
        Event.processInput(userInput);

        //Assert
        Assert.assertEquals("Please enter a directory along with the new command.",outContent.toString().trim());


    }

    @Test
    public void testProcessInputNewFour() throws IOException {

        //Arrange
        Parser parser = new Parser();
        parser.setStoredDirectory(null);

        String userInput = "newsrc";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        //Act
        Event.processInput(userInput);

        //Assert
        Assert.assertEquals("Invalid command. Please enter a valid command.",outContent.toString().trim());


    }

    @Test
    public void testProcessInputNewFive() throws IOException {
        //Arrange
        Parser parser = new Parser();
        parser.setStoredDirectory(null);

        String userInput = "new nonExistingDirectory";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        //Act
        Event.processInput(userInput);

        //Assert
        Assert.assertEquals("The directory does not exist.",outContent.toString().trim());

    }

    @Test
    public void testProcessInputParseOne() throws IOException {
        //Arrange
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();

        String userInput = "parse";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        //Act
        Event.processInput(userInput);

        //Assert
        Assert.assertEquals("Please enter a new directory before parsing compilation units.",outContent.toString().trim());

    }

    @Test
    public void testProcessInputParseTwo() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();

        String userInput = "new out";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        userInput = "parse";
        Event.processInput(userInput);

        Assert.assertEquals("No compilation units found in the directory.",outContent.toString().trim());

    }


    @Test
    public void testProcessInputParseThree() throws IOException {

        Parser parser = new Parser();
        parser.setStoredDirectory(null);

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        Assert.assertEquals(false,Parser.getStoredCompilationUnits().isEmpty());




    }

    @Test
    public void testProcessInputClassOne() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        parser.setStoredCompilationUnits(allCompilationUnits);
        Event.setAllClassesInProgram();
        Event.setCurrentClass("");



        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        String userInput = "class someClass";
        Event.processInput(userInput);

        Assert.assertEquals("No classes stored in program.",outContent.toString().trim());

        parser.setStoredDirectory(null);
        allCompilationUnits = new ArrayList<>();


    }

    @Test
    public void testProcessInputClassTwo() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        String userInput = "new src";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        userInput = "class someClass";
        Event.processInput(userInput);

        Assert.assertEquals("No classes stored in program.",outContent.toString().trim());

        parser.setStoredDirectory(null);
        allCompilationUnits = new ArrayList<>();


    }

    @Test
    public void testProcessInputClassThree() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        userInput = "class nonExistingClass";
        Event.processInput(userInput);

        Assert.assertEquals("The class entered does not match any of the classes stored in the program.",outContent.toString().trim());

        parser.setStoredDirectory(null);
        allCompilationUnits = new ArrayList<>();


    }


    @Test
    public void testProcessInputClassFour() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        userInput = "class    ";
        Event.processInput(userInput);

        Assert.assertEquals("Please enter a class name along with the class command.",outContent.toString().trim());

    }

    @Test
    public void testProcessInputClassFive() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        userInput = "class metrics.Main";
        Event.processInput(userInput);

        Assert.assertEquals("metrics.Main",Event.getCurrentClass());

    }

    @Test
    public void testProcessInputViewOne() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        userInput = "class metrics.Main";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        userInput = "view";
        Event.processInput(userInput);

        Assert.assertEquals("Please enter something to view, along with the view command.",outContent.toString().trim());


    }

    @Test
    public void testProcessInputViewTwo() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        userInput = "class metrics.Main";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        userInput = "view          ";
        Event.processInput(userInput);

        Assert.assertEquals("Please enter something to view, along with the view command.",outContent.toString().trim());


    }

    @Test
    public void testProcessInputViewThree() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        userInput = "class metrics.Main";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        userInput = "view nonExistingItem";
        Event.processInput(userInput);

        String expectedOutput = "Invalid item. Please enter a valid item to view."+"\r\n"
                +"Options are metrics (\"metrics\"), current class(\"current\"), all classes(\"all\"), program commands(\"commands\").";

        Assert.assertEquals(expectedOutput,outContent.toString().trim());

    }

    @Test
    public void testProcessInputViewFour() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        userInput = "class metrics.Main";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        userInput = "view current";
        Event.processInput(userInput);

        String expectedOutput = "Current Class:"+"\r\n"+Event.getCurrentClass();
        Assert.assertEquals(expectedOutput,outContent.toString().trim());

    }

    @Test
    public void testProcessInputExitOne() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        String userInput = "exit";
        boolean returnResult = Event.processInput(userInput);

        Assert.assertEquals(true,returnResult);

    }

    @Test
    public void testProcessInputEvalOne() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");


        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        String userInput = "eval";
        Event.processInput(userInput);

        Assert.assertEquals("Please enter something to evaluate along with the eval command.",outContent.toString().trim());


    }

    @Test
    public void testProcessInputEvalTwo() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        String userInput = "eval      ";
        Event.processInput(userInput);

        Assert.assertEquals("Please enter something to evaluate along with the eval command.",outContent.toString().trim());


    }

    @Test
    public void testProcessInputEvalThree() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        userInput = "eval a";
        Event.processInput(userInput);

        Assert.assertEquals("Please choose a current class before evaluating metrics.",outContent.toString().trim());

    }

    @Test
    public void testProcessInputEvalFour() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        userInput = "class metrics.Main";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent,true));

        userInput = "eval a";
        Event.processInput(userInput);

        String obtainedOutput = outContent.toString().trim();

        Assert.assertEquals(true,!obtainedOutput.isBlank());

    }

    /*
    @Test
    public void testProcessInputEvalFour() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Event.setCurrentClass("");

        //String userInput = "new src";
        //Event.processInput(userInput);

        //userInput = "parse";
        //Event.processInput(userInput);

        //userInput="class metrics.Main";
        //Event.processInput(userInput);

        CyclomaticComplexity cc = mock(CyclomaticComplexity.class);
        Event ev = mock(Event.class);
        String userInput = "new src";
        ev.processInput(userInput);
        userInput = "parse";
        ev.processInput(userInput);
        userInput="class metrics.Main";
        ev.processInput(userInput);
        userInput = "eval a";
        ev.processInput(userInput);

        //List<String> mockList = mock(List.class);
        //CyclomaticComplexity cc = mock(CyclomaticComplexity.class);
        //cc.getResult(any());

        //CommentPercentage cp = mock(CommentPercentage.class);
        //cp.getResult((any()));
        //List<String> mockList = mock(CyclomaticComplexity.class);

        //userInput="eval a";
        //Event.processInput(userInput);
        Mockito.verify(cc).getResult(any());

        //ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        //System.setOut(new PrintStream(outContent,true));

        //userInput = "eval a";
        //Event.processInput(userInput);

        //CyclomaticComplexity cc = Mock(CyclomaticComplexity.class);



        //Assert.assertEquals("Please choose a current class before evaluating metrics.",outContent.toString().trim());

    }

     */

    /*
    @Test
public void testDoFoo() {
  Bar bar = mock(Bar.class);
  BarFactory myFactory = new BarFactory() {
    public Bar createBar() { return bar;}
  };

  Foo foo = new Foo(myFactory);
  foo.foo();

  verify(bar, times(1)).someMethod();
}
     */









}
