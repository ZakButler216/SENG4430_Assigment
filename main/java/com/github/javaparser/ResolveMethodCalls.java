/*
* File name:    ResolveMethodCall.java
* Author:       Naneth Sayao
* Date:         2 May 2020
* Version:      3.0
* Description:  This code will measure the fan-in and fan-out
*                   metrics of other java code/files.
*               Fan-in:     a measure of the number of functions or
*                               methods that calls another function or method.
*               Fan-out:    the number of functions that are called by
*                               function X.
*                              if interested in complexity:
*
*                           put an argument
*               Reference for fan-in and fan-out descriptions is
*                   the week 2 notes on UoN Blackboard
*                   "SQ02 Inspections and Metrics" PowerPoint. Page 32.
* input:        Must input java code/files to be parsed
* output:       The following are the outputs:
*                   - fan-in and fan-out measure count
*                   - meaning of measurement, i.e. good, bad, etc
*                   - recommendation
*                   - etc.
* */

package com.github.javaparser;
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

public class ResolveMethodCalls {
    private static final String FILE_PATH = "src/main/java/com/github/javaparser/A.java";
    private static List<FanInOutMethod> methodsList = new ArrayList<FanInOutMethod>();

    public static void main(String[] args) throws Exception {


        //find each method declaration and save on list
        //calculate how many times each method declaration calls a method?
        //research what is a good percentage for fan in / fan out


        /*****************************************( fan-out )**********************************************
         * read all input files (for now practice with taking one input file as an input)
         *
         * count how many methods the file has (can an array list be created without knowing the needed amount initially?)
         *
         * create an array list
         *
         * make a new object named fanInOutMethod that takes in:
         *      - string = method name
         *      - string = for the whole method block
         *      - array list = store names of the called external method
         *      - int = record calls to other method/s
         *      - string = for grading the measurement, i.e., excellent, good, bad, disaster
         *      - string = recommendation
         *      - boolean = true if this method calls another method
         *
         * parse input and save into fanInOutMethod objects and then put that object in an array list
         *
         * parse each method from array list and fill the values for the fanInOutMethod objects
         *
         * print the recorded values of each fanInOutMethod objects
         *
         * there maybe changes to the order of execution of this English language pseudocode
         * */


        //create a list/s
        List<String> methodToString = new ArrayList<String>();

  //      System.out.println("methodsList size: " + methodsList.size());

        //now we want to separate each methods and save them in a FanInOutMethod object
        //create visitor
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
        VoidVisitor<?> methodVisitor = new MethodSpliter();
        methodVisitor.visit(cu, null);

 //       System.out.println("methodsList size: " + methodsList.size());

        System.out.println("///////////////////////////////////////////( fan-out result )/////////////////////////////////////////////");

        for(int a = 0; a < methodsList.size(); a++){
            System.out.println("Method Name: " + methodsList.get(a).getMethodName());
            System.out.println("Number of methods called: " + methodsList.get(a).getCalledMethodsList().size() + "\n");
        }
        System.out.println("");

        System.out.println("///////////////////////////////////////////( fan-in result )/////////////////////////////////////////////");

        int methodCallerCount = 0;
        for(int s = 0; s < methodsList.size(); s++){
            //if called methods ArrayList size is greater than zero, increment methodCallerCount
            if(methodsList.get(s).getCalledMethodsList().size() > 0){
                methodCallerCount++;
            }
            //System.out.println("Method Name: " + methodsList.get(a).getMethodName());
            //System.out.println("Number of methods called: " + methodsList.get(a).getCalledMethodsList().size());
        }
        System.out.println("Number of methods that called a method: " + methodCallerCount);

        /*
        for(int i = 0; i < methodsList.size(); i++){
            //System.out.println(methodsList.get(i).getMethodName());
            String input = methodsList.get(i).getMethodBlock();
            //System.out.println(input);


            TypeSolver typeSolver = new ReflectionTypeSolver();
            JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
            StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
            //parse input
            CompilationUnit cu2 = StaticJavaParser.parse("public class dummy {" + input + "}");
            cu2.findAll(MethodCallExpr.class).forEach(mce -> {
                //save the method name to the ArrayList of method calls

                System.out.println(mce.resolve().getQualifiedSignature());
            });
        }


        AtomicInteger mceCount = new AtomicInteger();
        TypeSolver typeSolver = new ReflectionTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
        //parse input
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
        cu.findAll(MethodCallExpr.class).forEach(mce -> {
            //save the method name to the ArrayList of method calls

                    mceCount.addAndGet(1);
                    System.out.println(mce.resolve().getQualifiedSignature());
        });
*/

        /*****************************************( fan-in )**********************************************
         * make a counter variable
         *
         * iterate the array list of fanInOutMethod objects
         *
         * increase the counter value if the boolean of the fanInOutMethod object is equal to true
         *
         * */
    }

    private static class MethodSpliter extends VoidVisitorAdapter<Void> {

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

//            System.out.println(temp.getMethodBlock());
            //parse input
            //add dummy class declaration coz it won't compile code with error
            CompilationUnit cu2 = StaticJavaParser.parse("public class dummy {" + temp.getMethodBlock() + "}");

            //visit the method block and count method call
            cu2.findAll(MethodCallExpr.class).forEach(mce -> {
                //save the method name to the ArrayList of method calls
                temp.getCalledMethodsList().add(mce.resolve().getQualifiedSignature());
            });

            //add temp to methodsList
            methodsList.add(temp);
        }
    }
}
