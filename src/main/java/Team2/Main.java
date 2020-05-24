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

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final String filePath = "src/test/java/Tree_TestData/src/main.java";
    private static List<FanInOutMethod> methodsList = new ArrayList<FanInOutMethod>();

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
    private static class MethodSplitter extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration md, Void arg){
            super.visit(md, arg);

            //create new FanInOutMethod Object
            FanInOutMethod temp = new FanInOutMethod();

            //update values of FanInOutMethod object
            temp.setMethodName(md.getNameAsString());
            temp.setMethodBlock(md.toString());

            //grade
            //recommendation
            //list of names of called methods. Find all method calls. Need to iterate
            AtomicInteger mceCount = new AtomicInteger();

            TypeSolver typeSolver = new ReflectionTypeSolver();

            JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
            StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

            //parse input
            //add dummy class declaration to surround method coz it won't compile code with error
            CompilationUnit cu2 = StaticJavaParser.parse("public class dummy {" + temp.getMethodBlock() + "}");

            //visit the method block and count method call
            cu2.findAll(MethodCallExpr.class).forEach(mce -> {
                //save the method name to the ArrayList of method calls
                temp.getCalledMethodsList().add(mce.getNameAsString());
            });

            //add temp to methodsList
            methodsList.add(temp);
        }
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
        String s2 = "src";
        //array list of compilation units
        List<CompilationUnit> allCU;

        //create a new parser
        Parser rootParser = new Parser();

        //initialise allCU
        allCU = rootParser.getCompilationUnits(s2);
        System.out.println("allCU size = " + allCU.size());

        //iterate on each compilation units
        for(int i = 0; i < allCU.size(); i++){
            //now we want to separate each methods and save them in a FanInOutMethod object
            //create visitor
            VoidVisitor<?> methodVisitor = new MethodSplitter();
            methodVisitor.visit(allCU.get(i), null);
        }

        //*****************************( fan-out )*****************************//
        //make new FanOut object
        FanOut fo = new FanOut();

        //execute fan out method of FanOut object
        fo.calculateFanOut(methodsList);

        //*****************************( fan-in )*****************************//
        //make new FanIn object
        FanIn fi = new FanIn();

        //execute fan in method of FanIn object
        fi.calculateFanIn(methodsList);

        ////////////////////////////////////( Fan-in/out: finished )///////////////////////////////////////

    }
}