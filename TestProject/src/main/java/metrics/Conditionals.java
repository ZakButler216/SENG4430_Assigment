package metrics;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Conditionals {

    private static int totalAndOrs;

    public Conditionals() {
        totalAndOrs = 0;
    }

    public static void main(String[] args) throws IOException {

        List<CompilationUnit> allCompilationUnits = new ArrayList<>();

        String s1 = "C:\\Users\\Cliff\\eclipse-workspace\\TestMultipleSourceRoots";
        String s2 = "C:\\Users\\Cliff\\eclipse-workspace\\TestMultipleClasses";
        String s3 = "C:\\Users\\Cliff\\eclipse-workspace\\TestSingleClass";
        String s4 = "C:\\Users\\Cliff\\Documents\\GitHub\\Bank-System";
        String s5 = "C:\\Users\\Cliff\\eclipse-workspace\\TestConditionals";
        String s6 = "C:\\Users\\Cliff\\Documents\\GitHub\\SENG4430_Assigment\\TestCases";
        Path root = Paths.get(s6);
        ProjectRoot projectRoot = new SymbolSolverCollectionStrategy().collect(root);

        for(int i =0;i<projectRoot.getSourceRoots().size();i++) {

            SourceRoot sourceRoot = projectRoot.getSourceRoots().get(i);

            sourceRoot.tryToParse();

            List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();

            for(int j = 0;j<compilationUnits.size();j++) {

                allCompilationUnits.add(compilationUnits.get(j));
            }
        }

        //System.out.println(projectRoot.getSourceRoots().get(0).toString());
        System.out.println("Amount of compilation units is:");
        System.out.println(allCompilationUnits.size());

        CompilationUnit cu = allCompilationUnits.get(0);
        //System.out.println(cu.getTypes().get(0).getMembers());

        /**
         Gets allTypes from compilation unit
         */
        NodeList<TypeDeclaration<?>> allTypes = cu.getTypes();

        /**
         Gets allMembers from type
         */
        NodeList<BodyDeclaration<?>> allMembers = allTypes.get(0).getMembers();
        //FieldDeclaration aField = (FieldDeclaration) allTypes.get(0).getMembers().get(3);
        TypeDeclaration aType = (TypeDeclaration) allTypes.get(0);

        /**
         Gets all functions of that compilation unit
         */
        NodeList<BodyDeclaration<?>> allFunctions = new NodeList<>();
        for(int i=0;i<allMembers.size();i++) {
            if(allMembers.get(i).isCallableDeclaration()) {
                allFunctions.add(allMembers.get(i));
                //System.out.println("Member added:");
                //System.out.println(allMembers.get(i));
            }
        }

        /**
         Gets all methods of that compilation unit
         */
        NodeList<BodyDeclaration<?>> allMethods = new NodeList<>();
        for(int i=0;i<allFunctions.size();i++) {
            if (!allFunctions.get(i).isConstructorDeclaration()) {
                allMethods.add(allFunctions.get(i));
                //System.out.println("Member added:");
                //System.out.println(allFunctions.get(i));
            }
        }

        /**
         Gets a single method from method list
         */

        //BodyDeclaration<?> aMethod = allMethods.get(12);
        MethodDeclaration aMethod = (MethodDeclaration) allMethods.get(0);
        CyclomaticComplexity cyclomaticComplexity = new CyclomaticComplexity();
        int cc = cyclomaticComplexity.findCCForAMethod(aMethod);
        System.out.println(aMethod);
        System.out.println("CC is "+cc);

        /**
         This part shows all predicates
         */
        /*if(aMethod.findAll(IfStmt.class).size()>0) {
            System.out.println("This method has "+aMethod.findAll(IfStmt.class).size()
                    +" if statements.");
            System.out.println("else if statement counts as if.");
        }

        if(aMethod.findAll(SwitchEntry.class).size()>0) {
            System.out.println("This method has "+aMethod.findAll(SwitchEntry.class).size()
                    +" total switch cases.");
        }

        if(aMethod.findAll(WhileStmt.class).size()>0) {
            System.out.println("This method has "+aMethod.findAll(WhileStmt.class).size()
                    +" while loops.");
        }

        if(aMethod.findAll(DoStmt.class).size()>0) {
            System.out.println("This method has "+aMethod.findAll(DoStmt.class).size()
                    +" do while loops.");
        }

        if(aMethod.findAll(ForStmt.class).size()>0) {
            System.out.println("This method has "+aMethod.findAll(ForStmt.class).size()
                    +" for loops.");
        }

        if(aMethod.findAll(ForEachStmt.class).size()>0) {
            System.out.println("This method has "+aMethod.findAll(ForEachStmt.class).size()
                    +" for each loops.");
        }

        if(aMethod.findAll(CatchClause.class).size()>0) {
            System.out.println("This method has "+aMethod.findAll(CatchClause.class).size()
                    +"catch statements.");
        }

        System.out.println("The Method is:");
        System.out.println(aMethod);

         */



    }



}
