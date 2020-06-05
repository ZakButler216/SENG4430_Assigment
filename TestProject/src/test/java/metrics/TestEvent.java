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

/**
 This class tests the control flow of the program.
 It focuses on the main commands: new, parse, class, view, eval, and exit.
 */
public class TestEvent {


    /**
     Command tested: new
     Event: user enters a new valid directory name
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

    /**
     Command tested: new
     Event: user enters a new valid directory name but with spaces in between
     */
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

    /**
     Command tested: new
     Event: user enters new command but without a directory name
     */
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

    /**
     Command tested: new
     Event: user enters an invalid command.
     Note: All commands that start with words other than new or parse or class or eval or view or exit
     are considered invalid commands.
     */
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

    /**
     Command tested: new
     Event: user enters a directory name that doesn't exist.
     */
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

    /**
     Command tested: parse
     Event: user parses compilation units before entering a directory.
     */
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

    /**
     Command tested: parse
     Event: user tries to parse compilation units from a directory which doesn't contain any compilation units.
     */
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


    /**
     Command tested: parse
     Event: user tries to parses compilation units.
     */
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

    /**
     Command tested: class
     Event: user sets a current class when there are no classes stored in program yet.
     */
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

    /**
     Command tested: class
     Event: user sets a current class when there are no classes stored in program yet,
     but a directory has been set.
     */
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

    /**
     Command tested: class
     Event: after classes are set in the program, user tries to set a non existing class as current.
     */
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


    /**
     Command tested: class
     Event: after classes are set in the program, user utilizes the class command without entering the class name.
     */
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

    /**
     Command tested: class
     Event: after classes are set in the program, user sets a valid current class.
     */
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

    /**
     Command tested: view
     Event: user enters view command without entering an item to view.
     */
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

    /**
     Command tested: view
     Event: user enters view command without entering an item to view, with added spaces.
     */
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

    /**
     Command tested: view
     Event: user tries to view a non existing item.
     */
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

    /**
     Command tested: view
     Event: user views a valid item.
     */
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

    /**
     Command tested: exit
     Event: user exits the program.
     */
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

    /**
     Command tested: eval
     Event: user enters eval command without entering a metric to evaluate.
     */
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

    /**
     Command tested: eval
     Event: user enters eval command without entering a metric to evaluate, with added spaces.
     */
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

    /**
     Command tested: eval
     Event: user enters eval command without choosing a current class to evaluate.
     */
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

    /**
     Command tested: eval
     Event: user enters eval command with a metric to evaluate.
     */
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



}
