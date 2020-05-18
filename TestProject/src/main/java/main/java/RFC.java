package main.java;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
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
    private int publicCount;

    private static class MainClassFinder extends VoidVisitorAdapter<List<ClassOrInterfaceDeclaration>>{
        @Override
        public void visit(ClassOrInterfaceDeclaration cid, List<ClassOrInterfaceDeclaration> mainClass){
            super.visit(cid, mainClass);
            if(cid.isTopLevelType() && !cid.isInterface() && !cid.isPrivate()) {
                mainClass.add(cid);
            }
        }
    }

    private static class ClassFinder extends VoidVisitorAdapter<List<ClassOrInterfaceDeclaration>>{
        @Override
        public void visit(ClassOrInterfaceDeclaration cid, List<ClassOrInterfaceDeclaration> classes){
            super.visit(cid, classes);
            if(!cid.isTopLevelType() && !cid.isInterface()){
                classes.add(cid);
            }
        }
    }

    private static class MethodFinder extends VoidVisitorAdapter<List<MethodDeclaration>>{
        @Override
        public void visit(MethodDeclaration md, List<MethodDeclaration> methods){
            super.visit(md, methods);
            methods.add(md);
        }
    }

    private void setup (){
        VoidVisitor<List<ClassOrInterfaceDeclaration>> mainClassFinder = new MainClassFinder();
        mainClassFinder.visit(cu, otherClasses);
        mainClass = otherClasses.get(0);
        otherClasses = new ArrayList<>();
        VoidVisitor<List<ClassOrInterfaceDeclaration>> otherClassesFinder = new ClassFinder();
        otherClassesFinder.visit(cu, otherClasses);
        VoidVisitor<List<MethodDeclaration>> methodFinder = new MethodFinder();
        methodFinder.visit(mainClass, mainClassMethods);
        for (ClassOrInterfaceDeclaration otherClass : otherClasses) {
            methodFinder.visit(otherClass, otherClassesMethods);
        }
        for(int i=0; i<mainClassMethods.size(); i++){
            for (MethodDeclaration otherClassesMethod : otherClassesMethods) {
                if (mainClassMethods.get(i).getDeclarationAsString().equals(otherClassesMethod.getDeclarationAsString())) {
                    mainClassMethods.remove(i);
                }
            }
        }
        for (MethodDeclaration mainClassMethod : mainClassMethods) {
            if (!mainClassMethod.isPrivate()) {
                publicCount++;
            }
        }
    }

    RFC(CompilationUnit newCu){
        cu = newCu;
        otherClasses = new ArrayList<>();
        mainClassMethods = new ArrayList<>();
        otherClassesMethods = new ArrayList<>();
        publicCount = 0;

        setup();
    }

    public void showResults(){
        System.out.println("Response for Class " + mainClass.getNameAsString() + " is: " + publicCount);
  }
}

