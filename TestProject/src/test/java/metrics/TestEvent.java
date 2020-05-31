package metrics;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


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
        System.setOut(new PrintStream(outContent));

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
        System.setOut(new PrintStream(outContent));

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
        System.setOut(new PrintStream(outContent));

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

        String userInput = "parse";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        //Act
        Event.processInput(userInput);

        //Assert
        Assert.assertEquals("Please enter a new directory before parsing compilation units.",outContent.toString().trim());

    }

    @Test
    public void testProcessInputParseTwo() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);

        String userInput = "new out";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

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

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String userInput = "class someClass";
        Event.processInput(userInput);

        Assert.assertEquals("No classes stored in program.",outContent.toString().trim());

    }

    @Test
    public void testProcessInputClassTwo() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();

        String userInput = "new src";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userInput = "class someClass";
        Event.processInput(userInput);

        Assert.assertEquals("No classes stored in program.",outContent.toString().trim());

    }

    @Test
    public void testProcessInputClassThree() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userInput = "class nonExistingClass";
        Event.processInput(userInput);

        Assert.assertEquals("The class entered does not match any of the classes stored in the program.",outContent.toString().trim());
    }

    @Test
    public void testProcessInputClassFour() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userInput = "class Main";
        Event.processInput(userInput);


        Assert.assertEquals("The class entered does not match any of the classes stored in the program.",outContent.toString().trim());
    }

    @Test
    public void testProcessInputClassFive() throws IOException {
        Parser parser = new Parser();
        parser.setStoredDirectory(null);
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();

        String userInput = "new src";
        Event.processInput(userInput);

        userInput = "parse";
        Event.processInput(userInput);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userInput = "class    ";
        Event.processInput(userInput);

        Assert.assertEquals("Please enter a class name along with the class command.",outContent.toString().trim());
    }








}
