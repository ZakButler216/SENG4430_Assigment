/*
 * File name:    FanInOutParser.java
 * Author:       Naneth Sayao
 * Date:         29 May 2020
 * Version:      8.2
 * Description:  A specific parser for the fan-in and fan-out metrics.
 *                  This parser will use the 'Parser' class written by Cliff.
 * */

package Team2;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.util.ArrayList;
import java.util.List;

public class FanInOutParser {
    private List<FanInOutMethod> methodsList = new ArrayList<>();

    //This inner class separates the methods of one class and puts the methods in a static array list
    public class MethodVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration md, Void arg){
            super.visit(md, arg);

            //create new FanInOutMethod Object
            FanInOutMethod temp = new FanInOutMethod();

            //update values of FanInOutMethod object
            temp.setMethodName(md.getNameAsString());
            temp.setMethodBlock(md.toString());

            methodSplitter(temp);
        }
    }

    //This inner class separates the methods of one class and puts the methods in a static array list
    public class ConstructorVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(ConstructorDeclaration md, Void arg){
            super.visit(md, arg);

            //create new FanInOutMethod Object
            FanInOutMethod temp = new FanInOutMethod();

            //update values of FanInOutMethod object
            temp.setMethodName(md.getNameAsString() + " (constructor)");
            temp.setMethodBlock(md.toString());
            temp.setConstructor(true); //for calculating dead methods (fan in), exclude constructors

            methodSplitter(temp);

        }
    }

    //This method helps(is used by) the two visitor classes; MethodSplitter, and ConstructorVisitor
    public void methodSplitter(FanInOutMethod temp){
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

    /*
     * Use this method if parsing only one class or .java file
     * This method separates the methods and constructors in the source
     */
    public void singleClassVisitor(String source){
        //clear our methodsList
        methodsList = new ArrayList<>();

        String currentClassName = "";

        //create a new parser
        Parser rootParser = new Parser();

        //array list of compilation units
        //initialise allCU
        List<CompilationUnit> allCU = rootParser.getCompilationUnits(source);

        for(int i = 0; i < allCU.size(); i++){
            //now we want to separate each methods and save them in a FanInOutMethod object

            currentClassName = allCU.get(i).getPrimaryTypeName().toString();
            System.out.println(currentClassName);

            //create visitor for normal methods
            VoidVisitor<?> methodVisitor = new MethodVisitor();
            methodVisitor.visit(allCU.get(i), null);
        }

        for(int j = 0; j < allCU.size(); j++){
            //now we want to separate each methods and save them in a FanInOutMethod object

            //create visitor for normal methods
            VoidVisitor<?> constructorVisitor = new ConstructorVisitor();
            constructorVisitor.visit(allCU.get(j), null);
        }

    }

    /*
    * Use this method if parsing a whole project
    * This method separates the Java classes in the source
    */
    public void wholeProjectVisitor(String source){
        //clear our methodsList
        methodsList = new ArrayList<>();

        String currentClassName = "";

        //create a new parser
        Parser rootParser = new Parser();

        //array list of compilation units
        //initialise allCU
        List<CompilationUnit> allCU = rootParser.getCompilationUnits(source);

        for(int i = 0; i < allCU.size(); i++){
            //now we want to separate each methods and save them in a FanInOutMethod object

            currentClassName = allCU.get(i).getPrimaryTypeName().toString();
            System.out.println(currentClassName);

            //create visitor for normal methods
            VoidVisitor<?> methodVisitor = new MethodVisitor();
            methodVisitor.visit(allCU.get(i), null);
        }

        for(int j = 0; j < allCU.size(); j++){
            //now we want to separate each methods and save them in a FanInOutMethod object

            //create visitor for normal methods
            VoidVisitor<?> constructorVisitor = new ConstructorVisitor();
            constructorVisitor.visit(allCU.get(j), null);
        }

    }

    //This method returns the methodsList
    public List<FanInOutMethod> getMethodsList() {
        return methodsList;
    }

}
