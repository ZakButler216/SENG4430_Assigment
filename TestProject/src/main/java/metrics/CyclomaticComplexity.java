package metrics;

import com.github.javaparser.ast.body.MethodDeclaration;

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
import java.util.stream.IntStream;

public class CyclomaticComplexity extends Generic {

    private static int countAndOrs;



    static {
        countAndOrs = 0;
    }


    public int findCCForAMethod(BodyDeclaration<?> aMethod) {

        int methodAndOrs = 0;
        int cyclomaticComplexity = 0;

        List<?> ifList = aMethod.findAll(IfStmt.class);

        for(int i=0;i<ifList.size();i++) {

            IfStmt ifStmt = (IfStmt) ifList.get(i);
            Expression condition = ifStmt.getCondition();
            System.out.println("Checking out If Statement");

            if(condition.isBinaryExpr()) {
                System.out.println(condition+" is a Binary Expression");

                exploreChildNodes(condition);
                methodAndOrs = countAndOrs;
                countAndOrs = 0;

            }

        }

        List<?> switchCaseList = aMethod.findAll(SwitchEntry.class);

        List<?> whileList = aMethod.findAll(WhileStmt.class);

        List<?> doWhileList = aMethod.findAll(DoStmt.class);

        List<?> forList = aMethod.findAll(ForStmt.class);

        List<?> forEachList = aMethod.findAll(ForEachStmt.class);

        List<?> catchList = aMethod.findAll(CatchClause.class);

        cyclomaticComplexity = IntStream.of(ifList.size(),methodAndOrs,switchCaseList.size(),
                whileList.size(),doWhileList.size(),forList.size(),
                forEachList.size(),catchList.size(),1).sum();

        return cyclomaticComplexity;




    }

    @Override
    public void exploreChildNodes(Node n) {

        try {

            Expression expression = (Expression) n;

            if(expression.isBinaryExpr()) {

                BinaryExpr binaryExpression = (BinaryExpr) expression;

                if(binaryExpression.getOperator().toString().equalsIgnoreCase("AND")||
                        binaryExpression.getOperator().toString().equalsIgnoreCase("OR")) {
                    System.out.println("Found And Or!!");
                    countAndOrs++;
                }

            }

        } catch(ClassCastException e) {

        }

        super.exploreChildNodes(n);

    }


}
