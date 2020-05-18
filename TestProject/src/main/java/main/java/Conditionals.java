package main.java;

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
        Path root = Paths.get(s5);
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
        MethodDeclaration aMethod = (MethodDeclaration) allMethods.get(6);
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


        /**
         This part finds if statements
         */
        /*List<?> ifList = aMethod.findAll(IfStmt.class);
        System.out.println("Method is:");
        System.out.println(aMethod);
        System.out.println("Conditional List size is:");
        System.out.println(ifList.size());
        System.out.println(ifList.get(0));

         */



        /**
         This part finds else if statements
         */
        /*List<?> elseIfList = aMethod.findAll(IfStmt.class);
        System.out.println("Else if list size is:");
        System.out.println(elseIfList.size());
        System.out.println(elseIfList.get(0));

         */

        /**
         This part finds switch case statements
         */
        /*List<?> switchCaseList = aMethod.findAll(SwitchEntry.class);
        System.out.println("Switch entry list is:");
        System.out.println(switchCaseList.size());
        System.out.println(switchCaseList.get(0));

         */

        /**
         This part finds while statements
         */
        /*List<?> whileList = aMethod.findAll(WhileStmt.class);
        System.out.println("While list is:");
        System.out.println(whileList.size());
        System.out.println(whileList.get(0));

         */

        /**
         This part finds do while statements
         */
        /*List<?> doWhileList = aMethod.findAll(DoStmt.class);
        System.out.println("Do while list is:");
        System.out.println(doWhileList.size());
        System.out.println(doWhileList.get(0));
        List<?> whileListHere = aMethod.findAll(WhileStmt.class);
        System.out.println("While list in do while is:");
        System.out.println(whileListHere.size());
        */

        /**
         This part finds for statements
         */

        /*List<?> forList = aMethod.findAll(ForStmt.class);
        System.out.println("For list is:");
        System.out.println(forList.size());

         */
        //System.out.println(aMethod);

        /**
         This part finds for each statements
         */
        /*List<?> forEachList = aMethod.findAll(ForEachStmt.class);
        System.out.println("For Each list is:");
        System.out.println(forEachList.size());

         */
        //System.out.println(aMethod);

        /**
         This part finds || and && statements
         */
        /*List<?> andOrList = aMethod.findAll(IfStmt.class);
        System.out.println("And Or List is:");
        System.out.println(andOrList.size());
        //System.out.println(andOrList.get(0));
        IfStmt anIfStmt = (IfStmt) andOrList.get(0);
        //System.out.println(anIfStmt);
        Expression condition = anIfStmt.getCondition();
        System.out.println("Condition is:");
        System.out.println(condition);
        //int andOrCount=0;
        //checkAllChildNodes(anIfStmt);
        int andOrsForMethod = 0;

        if(condition.isBinaryExpr()) {
            exploreChildNodes(condition);
            andOrsForMethod = totalAndOrs;
            totalAndOrs = 0;
        }

         */


        //System.out.println("Exploring Child Nodes");

        //System.out.println(andOrsForMethod);

        //List<?> binaryExpressionList =  condition.findAll(BinaryExpr.class);
        //System.out.println(binaryExpressionList);


        //System.out.println("If statement is:"+anIfStmt);
        //anIfStmt.toString();


        //VoidVisitor<?> theMethod = new MethodDetails();
        //theMethod.visit(cu,null);
        //VoidVisitor<?> theExpressionStmt = new ExpressionStmtDetails();
        //theExpressionStmt.visit(condition<ExpressionStmt>,null);

        //System.out.println();



        // while(condition.isBinaryExpr()) {
        //    condition.getChildNodes()

        //}
        /*
        if(condition.isBinaryExpr()) {
            BinaryExpr expression = (BinaryExpr) condition;
            if(expression.getOperator().toString().equalsIgnoreCase("OR")||
            expression.getOperator().toString().equalsIgnoreCase("AND")) {
                andOrCount++;

                System.out.println("Expression is:");
                System.out.println(expression);

                Expression condition2 = expression.getLeft();

                if(condition2.isBinaryExpr()) {
                    BinaryExpr expression2 = (BinaryExpr) condition2;
                    if(expression2.getOperator().toString().equalsIgnoreCase("OR")||
                    expression2.getOperator().toString().equalsIgnoreCase("AND")) {
                        andOrCount++;

                        System.out.println("Expression2 is:");
                        System.out.println(expression2);

                        Expression condition3 = expression2.getLeft();

                        if(condition3.isBinaryExpr()) {
                            BinaryExpr expression3 = (BinaryExpr) condition3;
                            if(expression3.getOperator().toString().equalsIgnoreCase("OR")||
                            expression3.getOperator().toString().equalsIgnoreCase("AND")) {
                                andOrCount++;

                                System.out.println("Expression3 is:");
                                System.out.println(expression3);
                            }
                        }

                        Expression condition3fromright = expression2.getRight();
                        if(condition3fromright.isBinaryExpr()) {
                            BinaryExpr expression3fromright = (BinaryExpr) condition3;
                            if(expression3fromright.getOperator().toString().equalsIgnoreCase("OR")||
                                    expression3fromright.getOperator().toString().equalsIgnoreCase("AND")) {
                                andOrCount++;

                                System.out.println("Expression3fromright is:");
                                System.out.println(expression3fromright);
                            }
                        }


                    }
                }




                //go to left node

            }

        }

         */
        //System.out.println("AndOr Count is:");
        //System.out.println(andOrCount);








        //System.out.println(condition.isBinaryExpr());

        //System.out.println("Condtion child nodes size is:");
        //System.out.println(condition.getChildNodes().size());

        /*String conditionString = condition.toString();
        if(conditionString.contains("||")||conditionString.contains("&&")) {
            System.out.println("Has ");
        }
         */
        //System.out.println(anIfStmt.getCondition().);

        /**
         This part finds catch statements
         (try, throw, finally statements v(G) +0)
         (catch statements v(G) +1 for each)
         */
        /*List<?> catchList = aMethod.findAll(CatchClause.class);
        System.out.println("Catch List is:");
        System.out.println(catchList.size());
        System.out.println(catchList.get(1));

         */






    }


    public static void exploreChildNodes(Node n) {

        //int totalAndOrs =0;
        //System.out.println("Node is:");
        //System.out.println(n);
        //System.out.println("Break");

        try {
            Expression expression = (Expression) n;
            //System.out.println("Casted Node to Expression");

            if(expression.isBinaryExpr()) {
                BinaryExpr binaryExpression = (BinaryExpr) expression;
                //System.out.println("BinaryExpression is "+binaryExpression);
                //System.out.println("It's operator is "+binaryExpression.getOperator());

                if(binaryExpression.getOperator().toString().equalsIgnoreCase("AND")||
                        binaryExpression.getOperator().toString().equalsIgnoreCase("OR")) {
                    //System.out.println("And Or Found!!!");
                    totalAndOrs++;

                }

            }

        } catch(ClassCastException e) {
            System.out.println("Didn't manage to cast Node to Expression");

        }

        List<Node> childNodes = n.getChildNodes();

        for(Node a:childNodes) {
            exploreChildNodes(a);
        }

        //return totalAndOrs;
    }


    /*
        void process(Node node){
            // Do something with the node
            for (Node child : node.getChildrenNodes()){
                process(child);
            }
        }

 */
    private static class IfStmtDetails extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(IfStmt n, Void arg) {
            super.visit(n, arg);
            System.out.println(n.asIfStmt());
            Expression a =  n.getCondition();


        }

    }


    public class LogicalOperators {

        //public class
    }

    public static void checkAllChildNodes(Node node) {
        System.out.println("Node is:"+node);

        for(Node child:node.getChildNodes()) {
            checkAllChildNodes(child);
        }
    }
    /*
        void process(Node node){
            // Do something with the node
            for (Node child : node.getChildrenNodes()){
                process(child);
            }
        }

 */


}
