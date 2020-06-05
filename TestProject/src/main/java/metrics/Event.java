package metrics;

/**
 File Name: Event.java
 Author: Cliff Ng
 Description: As this is an event driven application, this class provides control flow for events.
 */


import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
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
                System.out.println("Invalid command. Please enter a valid command.");
                return false;
        }

    }

    /**
     This method handles the view command.
     View command is for when user wants to view something.
     */
    public static void checkView(String scanInput) {

        try {



            //gets the rest of the input
            String input = scanInput.substring(5,scanInput.length());
            input = input.replaceAll("\\s\\s+","");

            if(input.isBlank()) {
                System.out.println("Please enter something to view, along with the view command.");
            } else {
                //calls the view method
                view(input);
            }

        } catch(StringIndexOutOfBoundsException e) {
            System.out.println("Please enter something to view, along with the view command.");
        }


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
                System.out.println("Invalid item. Please enter a valid item to view.");
                System.out.println("Options are metrics (\"metrics\"), current class(\"current\"), all classes(\"all\"), program commands(\"commands\").");
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

            if(Parser.getStoredCompilationUnits().size()>0) {

                System.out.println("Dem units have been parsed :D");
                System.out.println("Classes are: ");

                //prints out all the classes/compilation units for user to see
                printClassesList();

            }

    }

    /**
     This method handles the new command.
     New command is for when the user wants to store a new directory path.
     */
    public static void checkNew(String scanInput) {

            //if(!scanInput.contains("\\s")) {
            //    System.out.println("Please enter a directory along with the new command.");
            //}

            try {
                String input = scanInput.substring(4, scanInput.length());
                input = input.replaceAll("\\s\\s+", "");


                File tmpDir = new File(input);


                //Case user didn't enter a directory along with new command
                if (input.isBlank()) {

                    System.out.println("Please enter a directory along with the new command.");

                    //Case user enter an invalid directory
                } else if (!tmpDir.isDirectory()) {


                    System.out.println("The directory does not exist.");

                    //Case user entered a valid directory
                } else {

                    Parser parser = new Parser();

                    //Stores the directory in a static place so anybody can access it
                    parser.setStoredDirectory(input);

                    //feedback notification to user
                    System.out.println("New directory stored :)");
                    System.out.println("Directory name: " + Parser.getStoredDirectory());

                }
            } catch (StringIndexOutOfBoundsException e) {

                System.out.println("Please enter a directory along with the new command.");

            }



    }

    /**
     This method handles the class command.
     Class command is for when user wants to set a new class to be evaluated.
     */
    public static void checkClass(String scanInput) {

        try {


            String input = scanInput.substring(6,scanInput.length());
            input = input.replaceAll("\\s\\s+","");


            if(allClassesInProgram.isEmpty()) {

                System.out.println("No classes stored in program.");

            } else if (input.isBlank()) {

                System.out.println("Please enter a class name along with the class command.");

            }   else {

                boolean foundClass = false;

                //traverses all the classes in program
                for(int i=0;i<allClassesInProgram.size();i++) {

                    //if user's input class matches the class in the for loop
                    if (input.equalsIgnoreCase(allClassesInProgram.get(i))) {



                    //set it as current class for user to evaluate
                    setCurrentClass(input);

                    //feedback notification to user
                    System.out.println("Current class is now:");
                    System.out.println(getCurrentClass() + " :DD");

                    foundClass=true;

                    break;
                    }
                }

            if(foundClass==false) {
                System.out.println("The class entered does not match any of the classes stored in the program.");
            }


            }


        } catch(StringIndexOutOfBoundsException e) {
            System.out.println("Please enter a class name along with the class command.");
        }


    }

    /**
     This method handles the evaluate command.
     Evaluate command is for when user wants to evaluate certain metrics. (Can be 1 to many).
     */
    public static void checkEval(String scanInput) throws IOException {


        try {
            String input = scanInput.substring(5,scanInput.length());
            input = input.replaceAll("\\s\\s+","");

            if(input.isBlank()) {
                System.out.println("Please enter something to evaluate along with the eval command.");
            }
            else if (currentClass.equalsIgnoreCase("")) {

                System.out.println("Please choose a current class before evaluating metrics.");}

            else {

                //evaluates metrics
                evaluate(input);

            }


        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Please enter something to evaluate along with the eval command.");
        }


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

        boolean couplingAll=false;

        //if user inputted *, process all metrics
        if(metricsChosen.equals("*")) {
            metricsChosen = "abcdefghijkl";
        }
        if(metricsChosen.contains("e*")) {
            couplingAll = true;
            metricsChosen.replaceAll("e\\*","e");
        }

        //Traverses the menu of options
        for(int i=0;i<menu.length;i++) {

            //if there is an occurence of this (menu) alphabet in metricsChosen string
            if(metricsChosen.indexOf(menu[i])!=-1) {

                //Action: insert metric action here
                switch(menu[i]) {


                        //Cyclomatic Complexity
                    case "a":
                        CyclomaticComplexity cc = new CyclomaticComplexity();
                        String ccResult = cc.getResult(cu);
                        totalResult+=ccResult;
                        break;

                        //Comment Percentage
                    case "b":
                        CommentPercentage cp = new CommentPercentage();
                        String cpResult = cp.getResult(cu);
                        totalResult+=cpResult;
                        break;

                        //Fan In
                    case "c":
                        FanInOutParser fioParserOne = new FanInOutParser(); //prepare fan-in/out parser
                        fioParserOne.wholeProjectVisitor(parser.getStoredCompilationUnits()); //visit the parsed units

                        FanIn fi = new FanIn(); //create a new FanIn object
                        totalResult+= fi.getReport(fioParserOne.getMethodsList(), getCurrentClass());//calculate and get fan-in result
                        break;

                        //Fan Out
                    case "d":
                        FanInOutParser fioParserTwo = new FanInOutParser(); //prepare fan-in/out parser
                        fioParserTwo.singleClassVisitor(cu); //visit the parsed unit
                        FanOut fo = new FanOut();//create a new FanOut object
                        totalResult+= fo.getReport(fioParserTwo.getMethodsList());//calculate and get fan-out result
                        break;

                        //Coupling
                    case "e":
                        Coupling coupling;
                        if (couplingAll==true) {
                             coupling = new Coupling(Parser.getStoredCompilationUnits(),"");

                        } else {
                             coupling = new Coupling(Parser.getStoredCompilationUnits(),parser.getClassNameFromCompilationUnit(cu));

                        }

                        String couplingResult = coupling.getResults();
                        totalResult+=couplingResult;

                        break;

                        //Program Size
                    case "f":
                        ProgramSize progSize = new ProgramSize(Parser.getStoredCompilationUnits(), parser.getClassNameFromCompilationUnit(cu));
                        String sizeResults = progSize.getResults();
                        totalResult+=sizeResults;
                        break;

                        //Response for a Class
                    case "g":
                        RFC rfc = new RFC(cu);
                        String rfcResult = rfc.getResults()+"\n";
                        totalResult+=rfcResult;

                        break;

                        //Fog Index
                    case "h":
                        FolderReader fr = new FolderReader(new File(parser.getStoredDirectory()));

                        String fogIndexClassName = currentClass;

                        if(currentClass.contains(".")) {

                            String[] splitPackageClass = fogIndexClassName.split("\\.");
                            String justClass = splitPackageClass[1];
                            fogIndexClassName = justClass;
                        }

                        File file = fr.getClassFile(fogIndexClassName);
                        FogIndex fogIndex = new FogIndex(file);
                        String fogIndexResult = fogIndex.getResults()+"\n";
                        totalResult+=fogIndexResult;
                        break;

                        //Number of Children
                    case "i":

                        CherrenSection numSection = new CherrenSection();
                        numSection.setup(parser.getStoredDirectory());

                        String classToCheckChildren=currentClass;

                        if(currentClass.contains(".")) {

                            String[] splitPackageClass = classToCheckChildren.split("\\.");
                            String justClass = splitPackageClass[1];
                            classToCheckChildren = justClass;
                        }

                        String numChildrenResult = numSection.getNumChildren(classToCheckChildren)+"\n";
                        totalResult+=numChildrenResult;


                        break;

                        //Depth of Inheritance Tree
                    case "j":

                        CherrenSection depthSection = new CherrenSection();
                        depthSection.setup(parser.getStoredDirectory());

                        String classToCheckInheritanceDepth=currentClass;

                        if(currentClass.contains(".")) {

                            String[] splitPackageClass = classToCheckInheritanceDepth.split("\\.");
                            String justClass = splitPackageClass[1];
                            classToCheckInheritanceDepth = justClass;
                        }

                        String depthInheritanceTreeResult = depthSection.getMaxDepth(classToCheckInheritanceDepth)+"\n";
                        totalResult+=depthInheritanceTreeResult;


                        break;

                        //Length of Identifiers
                    case "k":

                        Identifiers identifier = new Identifiers();
                        List<CompilationUnit> allUnits = Parser.getStoredCompilationUnits();
                        String identiferResult=identifier.getResult(cu)+"\n";
                        totalResult+=identiferResult;

                        break;

                        //Lack of Cohesion in Methods
                    case "l":

                        LCOM lcom = new LCOM();
                        String lcomResult=lcom.getResult(cu)+"\n";
                        totalResult+=lcomResult;
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
        System.out.println("Note: For coupling, you can choose to evaluate coupling for a class (\"e\"), " +
                "or coupling for the whole program(\"e*\")");
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

        if(Parser.getStoredDirectory()!=null) {

            Parser parser = new Parser();

            //Stores the directory in a static place so anybody can access it
            //parser.setStoredDirectory(directory);

            //Gets all compilation units from the directory
            List<CompilationUnit> compilationUnits = parser.getCompilationUnits(Parser.getStoredDirectory());
            //This facilitates the action of same directory, but different compilation units
            //while user using the tool

            if(compilationUnits.size()==0) {

                System.out.println("No compilation units found in the directory.");

            } else {

                //Stores the compilation units in a static place so anybody (metrics, interface etc) can access it.
                parser.setStoredCompilationUnits(compilationUnits);

                //This is to print on user interface
                setAllClassesInProgram();
            }

        } else {

            System.out.println("Please enter a new directory before parsing compilation units.");
        }



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

