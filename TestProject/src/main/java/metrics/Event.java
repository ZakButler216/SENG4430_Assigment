package metrics;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.regex.Pattern;

/**
 This class changes the view on the user interface according to the event.
 */
public class Event {

    //variable that stores all classes in program user entered
    private static List<String> allClassesInProgram;

    //variable that stores current class that user is currently evaluating
    private static String currentClass;

    static {
        allClassesInProgram = new ArrayList<>();
        currentClass = "";
    }

    /**
     This method processes user input.

     Note: parse and class and eval commands have to be separate and sequential,
     as parse will update classes list on UI,
     class will select a class (or if already selected, remain with current class)
     and eval will evaluate metrics of current class
     */
    public static boolean processInput(String scanInput) throws IOException {

        //gets the command
        String[] container = scanInput.split("\\s");

        String command = container[0];

        //This switch case determines which command to process
        switch(command) {
            case "new":
                checkNew(scanInput);
                return false;
            case "parse":
                checkParse(scanInput);
                return false;
            case "class":
                checkClass(scanInput);
                return false;
            case "eval":
                checkEval(scanInput);
                return false;
            case "view":
                checkView(scanInput);
                return false;
            case "exit":
                return true;
            default:
                return false;
        }

    }

    /**
     This method handles the view command.
     View command is for when user wants to view something.
     */
    public static void checkView(String scanInput) {

            //gets the rest of the input
            String input = scanInput.substring(5,scanInput.length());
            input = input.replaceAll("\\s\\s+","");

            //calls the view method
            view(input);

    }

    /**
     This method prints whatever user wanted to view.
     Based on the input.
     */
    public static void view(String input) {

        //prints output according to user choice
        switch(input) {
            case "metrics":
                printMetricsList();
                break;
            case "current":
                printCurrentClass();
                break;
            case "all":
                printClassesList();
                break;
            case "commands":
                printCommands();
                break;
            default:
                break;

        }
    }

    /**
     This method handles the parse command.
     Parse command is for when the user wants to parse all compilation units that is in the stored directory.
     */
    public static void checkParse(String scanInput) {

            //parses all the compilation units in the directory stored
            parseCompilationUnits();
            System.out.println("Dem units have been parsed :D");
            System.out.println("Classes are: ");

            //prints out all the classes/compilation units for user to see
            printClassesList();

    }

    /**
     This method handles the new command.
     New command is for when the user wants to store a new directory path.
     */
    public static void checkNew(String scanInput) {

            String input = scanInput.substring(4,scanInput.length());
            input = input.replaceAll("\\s\\s+","");

            Parser parser = new Parser();

            //Stores the directory in a static place so anybody can access it
            parser.setStoredDirectory(input);

            //feedback notification to user
            System.out.println("New directory stored :)");
            System.out.println("Directory name: "+Parser.getStoredDirectory());


    }

    /**
     This method handles the class command.
     Class command is for when user wants to set a new class to be evaluated.
     */
    public static void checkClass(String scanInput) {

            String input = scanInput.substring(6,scanInput.length());
            input = input.replaceAll("\\s\\s+","");

            //traverses all the classes in program
            for(int i=0;i<allClassesInProgram.size();i++) {

                //if user's input class matches the class in the for loop
                if(input.equalsIgnoreCase(allClassesInProgram.get(i))) {

                    //set it as current class for user to evaluate
                    setCurrentClass(input);

                    //feedback notification to user
                    System.out.println("Current class is now:");
                    System.out.println(getCurrentClass()+" :DD");

                    break;
                }

        }


    }

    /**
     This method handles the evaluate command.
     Evaluate command is for when user wants to evaluate certain metrics. (Can be 1 to many).
     */
    public static void checkEval(String scanInput) throws IOException {


            String input = scanInput.substring(5,scanInput.length());
            input = input.replaceAll("\\s\\s+","");

            //evaluates metrics
            evaluate(input);

    }

    /**
     This method evaluates metrics that have been chosen.

     Preconditions:
     Newly parsed compilation units. Classes all shown on UI
     Parser class stores all newly parsed compilation units.

     a) Cyclomatic Complexity
     b) Comment Percentage
     c) Fan In
     d) Fan Out
     e) Coupling Between Object Classes
     f) Program Size
     g) Return for a Class
     h) Fog Index
     i) Number of a Children
     j) Depth of Inheritance Tree
     k) Length of Identifiers
     l) Lack of cohesion in Methods

     */
    public static void evaluate(String metricsChosen) throws IOException {

        Parser parser = new Parser();

        //Gets the current compilation unit that user has selected to be evaluated
        CompilationUnit cu = parser.getCompilationUnitByName(Parser.getStoredCompilationUnits(),currentClass);

        //initialize variable to store total string
        String totalResult="";

        //initialize menu to choose from
        String[] menu = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l"};

        //if user inputted *, process all metrics
        if(metricsChosen.equals("*")) {
            metricsChosen = "abcdefghijkl";
        }

        //Traverses the menu of options
        for(int i=0;i<menu.length;i++) {

            //if there is an occurence of this (menu) alphabet in metricsChosen string
            if(metricsChosen.indexOf(menu[i])!=-1) {

                //Action: insert metric action here
                switch(menu[i]) {

                    case "a":
                        CyclomaticComplexity cc = new CyclomaticComplexity();
                        String ccResult = cc.getResult(cu);
                        totalResult+=ccResult;
                        break;

                    case "b":
                        CommentPercentage cp = new CommentPercentage();
                        String cpResult = cp.getResult(cu);
                        totalResult+=cpResult;
                        break;

                    case "c":
                        break;

                    case "d":
                        FanInOutParser fioParserOne = new FanInOutParser();
                        fioParserOne.classSplitter();
                        FanOut fo = new FanOut();
                        List<Integer> numericResult = fo.calculateFanOut(fioParserOne.getMethodsList());
                        String foResult = fo.getOutputResult();

                        totalResult+=foResult;
                        break;

                    case "e":
                        Coupling coupling = new Coupling(Parser.getStoredCompilationUnits());

                        break;

                    case "f":
                        break;

                    case "g":
                        RFC rfc = new RFC(cu);
                        String rfcResult = rfc.getResults()+"\n";
                        totalResult+=rfcResult;

                        break;

                    case "h":
                        FolderReader fr = new FolderReader(new File(parser.getStoredDirectory()));
                        File file = fr.getClassFile(currentClass);
                        FogIndex fi = new FogIndex(file);
                        String fiResult = fi.getResults()+"\n";
                        totalResult+=fiResult;
                        break;

                    case "i":
                        break;

                    case "j":
                        break;

                    case "k":
                        //Identifiers
                        break;

                    case "l":
                        //LCOM
                        break;

                    default:
                        break;

                }
            }
        }

        //Output appended metric(s) result for user
        System.out.println(totalResult);

    }


    /**
     This method prints out all the metrics that are available to be evaluated.
     */
    public static void printMetricsList() {

        System.out.println("Software Quality Metrics:");
        System.out.println("a) Cyclomatic Complexity");
        System.out.println("b) Comment Percentage");
        System.out.println("c) Fan In");
        System.out.println("d) Fan Out");
        System.out.println("e) Coupling Between Object Classes");
        System.out.println("f) Program Size");
        System.out.println("g) Return for a Class");
        System.out.println("h) Fog Index");
        System.out.println("i) Number of a Children");
        System.out.println("j) Depth of Inheritance Tree");
        System.out.println("k) Length of Identifiers");
        System.out.println("l) Lack of cohesion in Methods");
    }

    /**
     This method prints out all the classes/compilation units,
     from the list of compilation units in the program
     */
    public static void printClassesList() {

        System.out.println("All Classes: ");
        for(int i=0;i<allClassesInProgram.size();i++) {
            System.out.println(allClassesInProgram.get(i));
        }

    }

    /**
     This method prints out current class user is evaluating.
     */
    public static void printCurrentClass() {
        System.out.println("Current Class:");
        System.out.println(currentClass);
    }

    /**
     This method prints out all the commands available for this software.
     */
    public static void printCommands() {
        System.out.println("Commands for this software are:");

        System.out.println("1. new");
        System.out.println("This command saves a new directory address to be parsed.");
        System.out.println("Input format: \"new directoryAddress\"");
        System.out.println();

        System.out.println("2. parse");
        System.out.println("This command parses the compilation units of the saved directory address.");
        System.out.println("Input format: \"parse\"");
        System.out.println();

        System.out.println("3. class");
        System.out.println("This command sets the current class to be evaluated.");
        System.out.println("Input format: \"class className\"");
        System.out.println();

        System.out.println("4. eval");
        System.out.println("This command evaluates the current class according to selected metrics.");
        System.out.println("You can select one , a few, or all metrics to be evaluated.");
        System.out.println("To select a metric(s), select the corresponding alphabet(s) from the metrics list");

        System.out.println("To select all, key in *");
        System.out.println("Input format: \"eval metrics\"");
        System.out.println("Examples:");
        System.out.println("\"eval a\" to evaluate cyclomatic complexity for current class.");
        System.out.println("\"eval cd\" to evaluate fan in and fan out for current class.");
        System.out.println("\"eval bfh\" to evaluate comment percentage, program size, and fog index for current class.");
        System.out.println("\"eval *\" to evaluate all 12 metrics for current class.");
        System.out.println("(To view the metrics list, input \"view metrics\")");
        System.out.println();

        System.out.println("5. view");
        System.out.println("This command allows you to view certain items.");
        System.out.println("Input format: \"view itemName\"");
        System.out.println("Items viewable are:");
        System.out.println("\"metrics\" which shows list of all metrics that can be used to evaluate the class.");
        System.out.println("\"current\" which shows name of current class that the software will be evaluating.");
        System.out.println("\"all\" which shows all the classes (compilation units) that have been parsed.");
        System.out.println("\"commands\" which show list of all the commands");
        System.out.println();

        System.out.println("Once done, simply key in \"exit\" to exit the program.");


    }


    /**
     This method parses all the compilation units and stores it in a static variable in Parser class.
     */
    public static void parseCompilationUnits() {

        Parser parser = new Parser();

        //Stores the directory in a static place so anybody can access it
        //parser.setStoredDirectory(directory);

        //Gets all compilation units from the directory
        List<CompilationUnit> compilationUnits = parser.getCompilationUnits(Parser.getStoredDirectory());
        //This facilitates the action of same directory, but different compilation units
        //while user using the tool

        //Stores the compilation units in a static place so anybody (metrics, interface etc) can access it.
        parser.setStoredCompilationUnits(compilationUnits);

        //This is to print on user interface
        setAllClassesInProgram();

    }

    /**
     This method stores all classes/compilation units for user to view.

     Precondition: Program parsed and compilation units stored in Parser class.
     This method is used to display classes output on UI.
     */
    public static void setAllClassesInProgram() {
        Parser parser = new Parser();

        allClassesInProgram = parser.getClassesAsString(Parser.getStoredCompilationUnits());

    }

    /**
     This method gets all classes in the program.
     */
    public static List<String> getAllClassesInProgram() {
        return allClassesInProgram;
    }

    /**
     This method sets current class user is evaluating.
     */
    public static void setCurrentClass(String current) {
        currentClass = current;


    }

    /**
     This method gets current class user is evaluating.
     */
    public static String getCurrentClass() {
        return currentClass;
    }


}
