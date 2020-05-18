package main.java;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.XmlPrinter;
import com.github.javaparser.printer.YamlPrinter;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ParserCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParseRoot {

    public static void main(String[] args) throws IOException {

        /**
         This section parses the whole project
         */
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();

        String s1 = "C:\\Users\\Cliff\\eclipse-workspace\\TestMultipleSourceRoots";
        String s2 = "C:\\Users\\Cliff\\eclipse-workspace\\TestMultipleClasses";
        String s3 = "C:\\Users\\Cliff\\eclipse-workspace\\TestSingleClass";
        String s4 = "C:\\Users\\Cliff\\Documents\\GitHub\\Bank-System";
        String s5 = "C:\\Users\\Cliff\\eclipse-workspace\\TestConditionals";

        Path root = Paths.get(s2);
        ProjectRoot projectRoot = new SymbolSolverCollectionStrategy().collect(root);

        for (int i = 0; i < projectRoot.getSourceRoots().size(); i++) {

            SourceRoot sourceRoot = projectRoot.getSourceRoots().get(i);

            sourceRoot.tryToParse();

            List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();

            for (int j = 0; j < compilationUnits.size(); j++) {

                allCompilationUnits.add(compilationUnits.get(j));
            }
        }

        //System.out.println(projectRoot.getSourceRoots().get(0).toString());
        System.out.println("Amount of compilation units is:");
        System.out.println(allCompilationUnits.size());

        /**
         This section utilizes CommentPercentage class to count comment percentage
         Tested all CommentPercentage methods, all accurate.
         */


        CommentPercentage commentPercentage = new CommentPercentage();

        /**
         This subsection shows comment percentage for one compilation unit
         */
        /*CompilationUnit unit  = allCompilationUnits.get(1);
        System.out.println("Code:");
        System.out.println(commentPercentage.countCode(unit));
        System.out.println("Comments:");
        System.out.println(commentPercentage.countComments(unit));
        System.out.println("Comment Percentage for single compilation unit:");
        System.out.println(commentPercentage.getCommentPercentageForOneCompilationUnit(unit));
*/
        /**
         This subsection shows comment percentage for whole project
         */
        System.out.println("Comment Percentage for all units:");
        System.out.println(commentPercentage.getCommentPercentageForAllCompilationUnits(allCompilationUnits));


        /**
         This section gets a method and counts the amount of predicates in that method
         */


        //CompilationUnit cu = allCompilationUnits.get(0);
        //NodeList<TypeDeclaration<?>> allTypes = cu.getTypes();
        //System.out.println("Amount of allTypes is:");
        //System.out.println(allTypes.size());


        /**
         This subsection gets allMembers from allTypes above
         */
        //All members consist of variables and methods
        //NodeList<BodyDeclaration<?>> allMembers = allTypes.get(0).getMembers();

        //System.out.println("Amount of allMembers is:");
        //System.out.println(allMembers.size());
        //System.out.println("allMembers is:");
        //System.out.println(allMembers);
        //System.out.println(allMembers.get(2));
        //System.out.println(allMembers.get(2).getChildNodes());
        //System.out.println(allMembers.get(2).getChildNodes().get(1).getChildNodes());

        /**
         This subsection shows declaration type of all members in all types
         */
        /*
        for(int i=0;i<allMembers.size();i++) {
            System.out.println("Break");
            System.out.println(allMembers.get(i));
            //System.out.println("Member finished");
            if(allMembers.get(i).isFieldDeclaration()) {
                System.out.println("Is a Field Declaration");
            }
            if(allMembers.get(i).isAnnotationDeclaration()) {
                System.out.println("Is an Annotation Declaration");
            }
            if(allMembers.get(i).isCallableDeclaration()) {
                System.out.println("Is a Callable Declaration");
            }
            if(allMembers.get(i).isAnnotationMemberDeclaration()) {
                System.out.println("Is an AnnotationMember Declaration");
            }
            if(allMembers.get(i).isClassOrInterfaceDeclaration()) {
                System.out.println("Is a ClassOrInterface Declaration");
            }
            if(allMembers.get(i).isConstructorDeclaration()) {
                System.out.println("Is a Constructor Declaration");
            }
            if(allMembers.get(i).isEnumConstantDeclaration()) {
                System.out.println("Is an EnumConstant Declaration");
            }
            if(allMembers.get(i).isEnumDeclaration()) {
                System.out.println("Is an Enum Declaration");
            }
            if(allMembers.get(i).isInitializerDeclaration()) {
                System.out.println("Is an Initializer Declaration");
            }
            if(allMembers.get(i).isMethodDeclaration()) {
                System.out.println("Is a Method Declaration");
            }
            if(allMembers.get(i).isTypeDeclaration()) {
                System.out.println("Is a Type Declaration");
            }
        }

        */


        /**
         This subsection gets all functions in compilation unit
         */
        //Methods are CallableDeclaration and MethodDeclaration
        //Constructors are CallableDeclaration and ConstructorDeclaration
        //Variables are FieldDeclaration
        //Enum are EnumDeclaration and TypeDeclaration

        //NodeList<BodyDeclaration<?>> allMembers = allTypes.get(0).getMembers();

     /*   NodeList<BodyDeclaration<?>> allFunctions = new NodeList<>();
        for(int i=0;i<allMembers.size();i++) {
            if(allMembers.get(i).isCallableDeclaration()) {
                allFunctions.add(allMembers.get(i));
                //System.out.println("Member added:");
                //System.out.println(allMembers.get(i));
            }
        }

      */

        /*System.out.println("List of functions is:");
        for(int i=0;i<allFunctions.size();i++) {
            System.out.println("Break");
            System.out.println(allFunctions.get(i));
        }

         */

        /**
         This subsection gets all methods in compilation unit
         */
        /*
        NodeList<BodyDeclaration<?>> allMethods = new NodeList<>();
        for(int i=0;i<allFunctions.size();i++) {
            if (!allFunctions.get(i).isConstructorDeclaration()) {
                allMethods.add(allFunctions.get(i));
                System.out.println("Member added:");
                System.out.println(allFunctions.get(i));
            }
        }

        BodyDeclaration<?> aMethod = allMethods.get(0);
        List<?> ifList = aMethod.findAll(IfStmt.class);
        System.out.println("Method is:");
        System.out.println(aMethod);
        System.out.println("Conditional List size is:");
        System.out.println(ifList.size());

        */
    }
}