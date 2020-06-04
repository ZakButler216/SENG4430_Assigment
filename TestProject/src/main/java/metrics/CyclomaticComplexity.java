package metrics;

/**
 File Name: CyclomaticComplexity.java
 Author: Cliff Ng
 Description: A class which provides utility to find cyclomatic complexity software quality metric.
 */

import com.github.javaparser.ast.body.MethodDeclaration;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 This class provides functionality to find Cyclomatic Complexity.
 */
public class CyclomaticComplexity extends Generic {

    //variable to count and and ors predicate
    private static int countAndOrs;

    //Evaluation levels enum
    public enum Evaluation {
        Low_Risk {
            public String toString() {
                return "Low Risk. Testable Code.";
            }
        },
        Moderate_Risk {
            public String toString() {
                return "Moderate Risk. Slightly difficult to test.";
            }
        },
        High_Risk {
            public String toString() {
                return "High Risk. Difficult to test.";
            }
        },
        Very_High_Risk {
            public String toString() {
                return "Very High Risk. Untestable Code.";
            }
        }
    }

    static {
        countAndOrs = 0;
    }

    /**
     This inner class is to store cyclomatic complexity output for one single method.
     Thus facilitating the use of list of methods, for one whole class.
     Each IndividualMethod stores the method name, method cyclomatic complexity, and evaluation level.
     */
    public class IndividualMethod {
        private String name;
        private int cc;
        private Evaluation eval;

        public IndividualMethod() {

        }

        public IndividualMethod(String name, int cc, Evaluation eval) {
            this.name = name;
            this.cc = cc;
            this.eval = eval;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCC() {
            return cc;
        }

        public void setCC(int cc) {
            this.cc = cc;
        }

        public Evaluation getEval() {
            return eval;
        }

        public void setEval(Evaluation eval) {
            this.eval = eval;
        }
    }


    /**
     This takes in the cyclomatic complexity for a method, and outputs the evaluation.
     <=10 Low Risk
     11-20 Moderate Risk
     21-50 High Risk
     >50 Very High Risk
     */
    public Evaluation evaluateLevelsForCC(int cc) {

        Evaluation evaluation;

        if(cc<=10) {
            evaluation = Evaluation.Low_Risk;
        } else if (cc<=20) {
            evaluation = Evaluation.Moderate_Risk;
        } else if (cc<=50) {
            evaluation = Evaluation.High_Risk;
        } else {
            evaluation = Evaluation.Very_High_Risk;
        }

        return evaluation;
    }

    /**
     This finds the cyclomatic complexity of a method.
     It does it by counting all the predicates and adding 1 to it. As per the formula:
     v(G) = p+1
     The predicates consist of if statements, else if statements, and or conditionals,
     switch case statements, while statements, do while statements, for statements, for each statements,
     and try catch statements.
     */
    public int findCCForAMethod(BodyDeclaration<?> aMethod) {

        //variable to store and ors
        int methodAndOrs = 0;

        //variable to store cyclomatic complexity count
        int cyclomaticComplexity = 0;

        //finds all if and else if statements
        List<?> ifList = aMethod.findAll(IfStmt.class);


        //finds and or conditionals in if and else if statements
        for(int i=0;i<ifList.size();i++) {

            IfStmt ifStmt = (IfStmt) ifList.get(i);
            Expression condition = ifStmt.getCondition();


            if(condition.isBinaryExpr()) {


                exploreChildNodes(condition);

            }

        }

        //stores and or conditionals amount
        methodAndOrs = countAndOrs;

        countAndOrs = 0;

        //finds all switch case statements
        List<?> switchCaseList = aMethod.findAll(SwitchEntry.class);

        //finds all while statements
        List<?> whileList = aMethod.findAll(WhileStmt.class);

        //finds all do while statements
        List<?> doWhileList = aMethod.findAll(DoStmt.class);

        //finds all for statements
        List<?> forList = aMethod.findAll(ForStmt.class);

        //finds all for each statements
        List<?> forEachList = aMethod.findAll(ForEachStmt.class);

        //finds all try catch statements
        List<?> catchList = aMethod.findAll(CatchClause.class);

        //sums up all predicates and adds 1 to them, to get cyclomatic complexity
        cyclomaticComplexity = IntStream.of(ifList.size(),methodAndOrs,switchCaseList.size(),
                whileList.size(),doWhileList.size(),forList.size(),
                forEachList.size(),catchList.size(),1).sum();

        //returns cyclomatic complexity
        return cyclomaticComplexity;

    }

    /**
     This method finds cyclomatic complexity for all the methods in a class.
     It takes in the compilation unit class,
     and returns a list of the class individual methods, in the form of an IndividualMethod List.
     Each IndividualMethod consists of the method name, cyclomatic complexity, and evaluation.
     */
    public List<IndividualMethod> findCCForAClass(CompilationUnit cu) {

        //Finds all methods in the class
        List<MethodDeclaration> methodDeclarationList = cu.findAll(MethodDeclaration.class);

        //Create an empty list of IndividualMethod
        List<IndividualMethod> allMethods = new ArrayList<>();

        //Traverse through all the methods in the class
        for(int i=0;i<methodDeclarationList.size();i++) {

            //Gets each method's name, cyclomatic complexity, and evaluation
            String methodName = methodDeclarationList.get(i).getName().toString();
            int methodCC = findCCForAMethod(methodDeclarationList.get(i));
            Evaluation methodEvaluation = evaluateLevelsForCC(methodCC);

            //Stores it in the IndividualMethod object
            IndividualMethod individualMethod = new IndividualMethod(methodName,methodCC,methodEvaluation);

            //adds it to the list
            allMethods.add(individualMethod);
        }

        //returns all the IndividualMethods
        return allMethods;
    }

    /**
     This method explores the child nodes of an expression,
     to find the and ors in them
     */
    @Override
    public void exploreChildNodes(Node n) {

        try {

            //Cast the node to expression
            Expression expression = (Expression) n;

            //If it's a binary expression
            if(expression.isBinaryExpr()) {

                //Convert to binary expression
                BinaryExpr binaryExpression = (BinaryExpr) expression;

                //If it is an And or Or operator
                if(binaryExpression.getOperator().toString().equalsIgnoreCase("AND")||
                        binaryExpression.getOperator().toString().equalsIgnoreCase("OR")) {

                    //Increment And Or count
                    countAndOrs++;
                }

            }

        } catch(ClassCastException e) {

        }

        //Explore all child nodes
        super.exploreChildNodes(n);

    }

    //Encapsulation thus returning it as a string
    //Better for testing (i.e. can test the string),
    //and maintenance (i.e. if switch view to another platform, can just transfer the string)
    //and scalability (i.e. if want to distribute it to other platforms, can just use the string)
    //and extensibility (i.e. can utilize inheritance/polymorphism/composition to modify String)
    public String getResult(CompilationUnit cu) {


        String result="";
        String allMethods="";
        String className;

        List<IndividualMethod> methodsList = findCCForAClass(cu);


        for(int i=0;i<methodsList.size();i++) {
            String totalMethod = "";

            String methodName ="Method name: "+methodsList.get(i).getName();

            int cc = methodsList.get(i).getCC();
            String methodCC = "Method Cyclomatic Complexity: "+Integer.toString(cc);

            String methodEvaluation ="Method evaluation: "+ methodsList.get(i).getEval().toString();

            totalMethod = "\n"+methodName +"\n" +methodCC +"\n" +methodEvaluation+"\n";
            allMethods += totalMethod;

        }

        Parser parser = new Parser();
        className = "Class: "+parser.getClassNameFromCompilationUnit(cu);

        result = className+"\n"+allMethods;

        return result;
    }


}
