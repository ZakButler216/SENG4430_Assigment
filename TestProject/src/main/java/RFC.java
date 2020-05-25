//Student Author: Zachery Butler
//Student Number: C3232981
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 20/05/2020

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class RFC {

    private final CompilationUnit cu;
    private ClassOrInterfaceDeclaration mainClass;
    private List<ClassOrInterfaceDeclaration> otherClasses;
    private final List<MethodDeclaration> mainClassMethods;
    private final List<MethodDeclaration> otherClassesMethods;
    private final List<MethodCallExpr> calledMethods;
    private int rfc;

    //A private class used to find the top level class for the file being tested
    //The top level class in java should be the class the file is named for
    //For example, the top level class of this file is RFC
    private static class MainClassFinder extends VoidVisitorAdapter<List<ClassOrInterfaceDeclaration>>{
        @Override
        public void visit(ClassOrInterfaceDeclaration cid, List<ClassOrInterfaceDeclaration> mainClass){
            super.visit(cid, mainClass);
            if(cid.isTopLevelType() && !cid.isInterface() && !cid.isPrivate()) {
                mainClass.add(cid);
            }
        }
    }


    //Method to find all the non top level classes
    //These will mostly be private classes used only by the top level class
    private static class ClassFinder extends VoidVisitorAdapter<List<ClassOrInterfaceDeclaration>>{
        @Override
        public void visit(ClassOrInterfaceDeclaration cid, List<ClassOrInterfaceDeclaration> classes){
            super.visit(cid, classes);
            if(!cid.isTopLevelType() && !cid.isInterface()){
                classes.add(cid);
            }
        }
    }

    //Finds all the method declarations in a class
    private static class MethodDecFinder extends VoidVisitorAdapter<List<MethodDeclaration>>{
        @Override
        public void visit(MethodDeclaration md, List<MethodDeclaration> methods){
            super.visit(md, methods);
            methods.add(md);
        }
    }

    private static class MethodCallFinder extends VoidVisitorAdapter<List<MethodCallExpr>>{
        @Override
        public void visit(MethodCallExpr mc, List<MethodCallExpr> methods){
            super.visit(mc, methods);
            methods.add(mc);
        }
    }

    //Calculates the Response for the Top level Class of the java class parsed in
    //Creates an object used to find the main class
    //Finds the main class, puts it in a list that will be reused later
    //Sets the mainClass variable to the class found and sets the list to new list to be reused
    //Creates an object to find other classes
    //Finds the other classes and puts them in a list
    //Next, finds all the methods in the top level class and puts them in a list
    //Then finds all the classes in the sub classes and puts them in a list
    //Removes the methods from the sub class from the list of the top level class' methods
    //Counts how many public methods remain in the top level class, this number is the RFC
    private void calcRFC (){
        VoidVisitor<List<ClassOrInterfaceDeclaration>> mainClassFinder = new MainClassFinder();
        mainClassFinder.visit(cu, otherClasses);
        mainClass = otherClasses.get(0);
        otherClasses = new ArrayList<>();
        VoidVisitor<List<ClassOrInterfaceDeclaration>> otherClassesFinder = new ClassFinder();
        otherClassesFinder.visit(cu, otherClasses);
        VoidVisitor<List<MethodDeclaration>> methodFinder = new MethodDecFinder();
        methodFinder.visit(mainClass, mainClassMethods);
        for (ClassOrInterfaceDeclaration otherClass : otherClasses) {
            methodFinder.visit(otherClass, otherClassesMethods);
        }
        for(int i=0; i<mainClassMethods.size(); i++){
            for (MethodDeclaration otherClassesMethod : otherClassesMethods) {
                if (mainClassMethods.get(i).getDeclarationAsString()
                .equals(otherClassesMethod.getDeclarationAsString())) {
                    mainClassMethods.remove(i);
                }
            }
        }
        MethodCallFinder mcf = new MethodCallFinder();
        for (MethodDeclaration mainClassMethod : mainClassMethods) {
            mcf.visit(mainClassMethod, calledMethods);
            if (!mainClassMethod.isPrivate()) {
                rfc++;
            }
        }
        rfc = rfc + calledMethods.size();
    }

    //Constructor that calls method that does all the calculations needed
    RFC(CompilationUnit newCu){
        cu = newCu;
        otherClasses = new ArrayList<>();
        mainClassMethods = new ArrayList<>();
        otherClassesMethods = new ArrayList<>();
        calledMethods = new ArrayList<>();
        rfc = 0;

        calcRFC();
    }

    //Prints out the Response for the top level class
    public void showResults(){
        System.out.println("Response for Class " + mainClass.getNameAsString() + " is: " + rfc);
  }
}

