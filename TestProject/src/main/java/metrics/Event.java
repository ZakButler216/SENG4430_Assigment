package metrics;

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

public class Event {

    private static List<String> allClassesInProgram;
    private static String currentClass;

    static {
        allClassesInProgram = new ArrayList<>();
        currentClass = "";
    }


/*
    public static void main(String[] args) throws FileNotFoundException {

    }
*/




    /**
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

        return true;


    }

    public static void checkView(String scanInput) {

        String checkCommand="";
        String input = "";

        try {

            checkCommand = scanInput.substring(0,5);

        } catch(StringIndexOutOfBoundsException e) {

        }

        if(checkCommand.equals("view ")) {

            input = scanInput.substring(5,scanInput.length());
            input = input.replaceAll("\\s\\s+","");

            view(input);

        }
    }

    public static void view(String input) {
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
            default:
                break;

        }
    }

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
    }

    public static void checkNew(String scanInput) {

            String input = scanInput.substring(4,scanInput.length());
            input = input.replaceAll("\\s\\s+","");

            File tmpDir = new File(input);

            //Case user didn't enter a directory along with new command
            if(input.isBlank()) {

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




    }

   /**
     This method handles the class command.
     Class command is for when user wants to set a new class to be evaluated.
     */
    public static void checkClass(String scanInput) {

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
        CompilationUnit cu = parser.getCompilationUnitByName(Parser.getStoredCompilationUnits(),currentClass);
        //System.out.println("Units are "+Parser.getStoredCompilationUnits());
        //System.out.println("Current class is "+currentClass);

        FolderReader fr = new FolderReader(new File(parser.getStoredDirectory()));
        File file = fr.getClassFile(currentClass);

        //to store total string
        String totalResult="";

        //menu to choose from
        String[] menu = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l"};

        boolean couplingAll=false;

        //if user inputted *, process all metrics
        if(metricsChosen.equals("*")) {
            metricsChosen = "abcdefghkl";
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
                        FanInOutParser fioParserOne = new FanInOutParser(); //prepare fan-in/out parser
                        fioParserOne.wholeProjectVisitor(parser.getStoredCompilationUnits()); //visit the parsed units

                        FanIn fi = new FanIn(); //create a new FanIn object
                        totalResult+= fi.getReport(fioParserOne.getMethodsList(), getCurrentClass());//calculate and get fan-in result
                        break;

                    case "d":
                        FanInOutParser fioParserTwo = new FanInOutParser(); //prepare fan-in/out parser
                        fioParserTwo.singleClassVisitor(cu); //visit the parsed unit
                        FanOut fo = new FanOut();//create a new FanOut object
                        totalResult+= fo.getReport(fioParserTwo.getMethodsList());//calculate and get fan-out result
                        break;


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

                    case "f":
                        ProgramSize progSize = new ProgramSize(Parser.getStoredCompilationUnits(), parser.getClassNameFromCompilationUnit(cu));
                        String sizeResults = progSize.getResults();
                        totalResult+=sizeResults;
                        break;

                    case "g":
                        RFC rfc = new RFC(cu);
                        String rfcResult = rfc.getResults()+"\n";
                        totalResult+=rfcResult;
                        break;

                    case "h":
                        FolderReader fr = new FolderReader(new File(parser.getStoredDirectory()));
                        File file = fr.getClassFile(currentClass);
                        FogIndex fogIndex = new FogIndex(file);
                        String fogIndexResult = fogIndex.getResults()+"\n";
                        totalResult+=fogIndexResult;

                        break;

                    case "i":
                        //CherrenSection a = new CherrenSection(cu);
                        //a.DepthTreeresult();
                        break;

                    case "j":
                        //CherrenSection b = new CherrenSection(cu);
                        //b.NumChildrenresult();
                        break;

                    case "k":
                        //Identifiers
                        Identifiers identifier = new Identifiers();
                        List<CompilationUnit> allUnits = Parser.getStoredCompilationUnits();
                        String identiferResult=identifier.getResult(cu)+"\n";
                        totalResult+=identiferResult;

                        break;

                    case "l":
                        //LCOM
                        LCOM lcom = new LCOM();
                        String lcomResult=lcom.getResult(cu)+"\n";
                        totalResult+=lcomResult;
                        break;

                    default:
                        break;

                }
            }
        }

        System.out.println(totalResult);

    }



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

    public static void printClassesList() {

        System.out.println("All Classes: ");
        for(int i=0;i<allClassesInProgram.size();i++) {
            System.out.println(allClassesInProgram.get(i));
        }

    }

    public static void printCurrentClass() {
        System.out.println("Current Class:");
        System.out.println(currentClass);
    }

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
     Precondition: Program parsed and compilation units stored in Parser class.
     This method is used to display classes output on UI.
     */
    public static void setAllClassesInProgram() {
        Parser parser = new Parser();

        allClassesInProgram = parser.getClassesAsString(Parser.getStoredCompilationUnits());

    }

    public static List<String> getAllClassesInProgram() {
        return allClassesInProgram;
    }

    public static void setCurrentClass(String current) {
        currentClass = current;



    }

    public static String getCurrentClass() {
        return currentClass;
    }







}

