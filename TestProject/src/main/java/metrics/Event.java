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

        if(!scanInput.equals("exit")) {

            checkNew(scanInput);

            checkParse(scanInput);

            checkClass(scanInput);

            checkEval(scanInput);

            checkView(scanInput);

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
        if(scanInput.equals("parse")) {
            parseCompilationUnits();
            System.out.println("Dem units have been parsed :D");
            System.out.println("Classes are: ");
            printClassesList();

        }
    }

    public static void checkNew(String scanInput) {

        String checkCommand="";
        String input = "";

        try {

            checkCommand = scanInput.substring(0,4);

        } catch(StringIndexOutOfBoundsException e) {

        }

        if(checkCommand.equals("new ")) {

            input = scanInput.substring(4,scanInput.length());
            input = input.replaceAll("\\s\\s+","");

            Parser parser = new Parser();

            //Stores the directory in a static place so anybody can access it
            parser.setStoredDirectory(input);
            System.out.println("New directory stored :)");
            System.out.println("Directory name: "+Parser.getStoredDirectory());

            //Parse compilation units
            //parseCompilationUnits();

        }

    }

    public static void checkClass(String scanInput) {
        String checkCommand = "";
        String input = "";

        try {

            checkCommand = scanInput.substring(0,6);

        } catch(StringIndexOutOfBoundsException e) {

        }

        if(checkCommand.equals("class ")) {
            input = scanInput.substring(6,scanInput.length());
            input = input.replaceAll("\\s\\s+","");

            for(int i=0;i<allClassesInProgram.size();i++) {
                if(input.equalsIgnoreCase(allClassesInProgram.get(i))) {
                    setCurrentClass(input);

                    System.out.println("Current class is now:");
                    System.out.println(getCurrentClass()+" :DD");

                    break;
                }
            }
        }


    }

    public static void checkEval(String scanInput) throws IOException {

        String checkCommand = "";
        String input = "";
        try {

            checkCommand = scanInput.substring(0,5);

        } catch(StringIndexOutOfBoundsException e) {

        }
        if(checkCommand.equals("eval ")) {

            input = scanInput.substring(5,scanInput.length());
            input = input.replaceAll("\\s\\s+","");

            evaluate(input);

        }
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
                        String rfcResult = rfc.getResults();
                        totalResult+=rfcResult;
                        break;

                    case "h":
                        FogIndex fi = new FogIndex(file);
                        String fiResult = fi.getResults();
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

