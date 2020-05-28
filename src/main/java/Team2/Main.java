package Team2;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLSyntaxErrorException;
import java.util.Collections;
import java.util.List;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.metamodel.CallableDeclarationMetaModel;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import java.util.ArrayList;

public class Main {

    private static final String filePath = "src/test/java/Tree_TestData/src/main.java";

    private static List<FanInOutMethod> methodsList = new ArrayList<>();

    private static List<String> readFileInList(String fileName)
    {
        List<String> lineList = Collections.emptyList();
        try
        {
            lineList = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        }catch(IOException e){
            e.printStackTrace();
        }
        return lineList;
    }


    //Naneth: This inner class separates the methods of one class and puts the methods in a static array list
    public static class MethodSplitter extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration md, Void arg){
            super.visit(md, arg);

            //create new FanInOutMethod Object
            FanInOutMethod temp = new FanInOutMethod();

            //update values of FanInOutMethod object
            temp.setMethodName(md.getNameAsString());
            temp.setMethodBlock(md.toString());

            visitorHelper(temp);
        }
    }

    //Naneth: This inner class separates the methods of one class and puts the methods in a static array list
    public static class ConstructorVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(ConstructorDeclaration md, Void arg){
            super.visit(md, arg);

            //create new FanInOutMethod Object
            FanInOutMethod temp = new FanInOutMethod();

            //update values of FanInOutMethod object
            temp.setMethodName(md.getNameAsString() + " (constructor)");
            temp.setMethodBlock(md.toString());

            visitorHelper(temp);

        }
    }

    public static void visitorHelper(FanInOutMethod temp){
        //prepare parser
        TypeSolver typeSolver = new ReflectionTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

        //parse input
        //add dummy class declaration to surround method coz it won't compile code with error
        CompilationUnit cu2 = StaticJavaParser.parse("public class Dummy {" + temp.getMethodBlock() + "}");

        //visit each method
        cu2.findAll(MethodCallExpr.class).forEach(mce -> {
            //save the method name to the FanInMethod object's ArrayList of method calls
            temp.getCalledMethodsList().add(mce.getNameAsString());
        });

        //add temp to methodsList
        methodsList.add(temp);
    }

    //Naneth: This inner class separates the Java classes in the source
    public static void classSplitter(String source){
        //create a new parser
        Parser rootParser = new Parser();

        //array list of compilation units
        //initialise allCU
        List<CompilationUnit> allCU = rootParser.getCompilationUnits(source);

        for(int i = 0; i < allCU.size(); i++){
            //now we want to separate each methods and save them in a FanInOutMethod object

            //create visitor for normal methods
            VoidVisitor<?> methodVisitor = new Main.MethodSplitter();
            methodVisitor.visit(allCU.get(i), null);
        }

        for(int j = 0; j < allCU.size(); j++){
            //now we want to separate each methods and save them in a FanInOutMethod object

            //create visitor for normal methods
            VoidVisitor<?> constructorVisitor = new Main.ConstructorVisitor();
            constructorVisitor.visit(allCU.get(j), null);
        }

    }

    public static List<FanInOutMethod> getMethodsList() {
        return methodsList;
    }

    /*Naneth:   This method is used for JUnit testing where the methodsList must be refreshed.
    *           Don't transfer to classSplitter method, as more than one module uses the
    *               methodsList array list.
    * */
    public static void clearMethodsList(){
        //clear our methodsList
        methodsList = new ArrayList<>();
    }

    public static void main(String[] args) throws FileNotFoundException {
        /*
        CompilationUnit cu = StaticJavaParser.parse(new File(filePath));
        List<String> lineList = readFileInList(filePath);

        System.out.println("Fog Index Results:");
        FogIndex fogIndex = new FogIndex(lineList);
        fogIndex.showResults();
        System.out.println("\n");
         */
        //System.out.println("Response For a Class Results");
        /*
        RFC rfc = new RFC(cu);
        rfc.showResults();

        Tree t = new Tree(cu);
        t.result();
        */

        //////////////////////////////////////( Fan-in/out: starts )/////////////////////////////////////////
        //path
        String s1 = "src3";

        classSplitter(s1);

        //******************( fan-out )******************//
        //make new FanOut object
        FanOut fo = new FanOut();

        //execute fan out method of FanOut object
        List<Integer> result = fo.calculateFanOut(methodsList);

        //******************( fan-in )******************//
        //make new FanIn object
        FanIn fi = new FanIn();

        //execute fan in method of FanIn object
        fi.calculateFanIn(methodsList);

        ////////////////////////////////////( Fan-in/out: finished )///////////////////////////////////////

    }
}
