/*
 * File name:    LCOM.java
 * Author:       Tamara Wold
 * Date:         20 May 2020
 * Version:      1.0
 * Description:  Calculates the lack of cohesion of
 *               methods (LCOM) of each class for the given data.
 *               LCOM is based on the single responsibility principle which
 *               states that a code element should have a single reason to change.
 *               LCOM is calculated using the formula LCOM = 1 – (sum(MF)/M*F)
 *               where M is the number of methods in a class, F is the number
 *               of instance fields in a class, MF is the number of methods of the
 *               class accessing a particular instance field, and sumMF is the sum of
 *               MF over all instance fields of the class.
 *               LCOM takes its values in the range [0-1]. The closer the value is to
 *               0, the better the cohesion of the class. A class with a LCOM value
 *               closer to 1 should be re-evaluated as it has a high lack of cohesion.
 * */
package metrics;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class LCOM {


    private List<String> fieldNames = new ArrayList<>();

    private List<String> methodNames = new ArrayList<>();

    private Integer methodCount;

    private Integer fieldCount;
    private List<Field> fieldObjects = new ArrayList<>();
    private int index;

    private Integer totalMF;


    public String getResult (CompilationUnit cu) {


            List<String> methodList = new ArrayList<>();
            List<String> fieldList = new ArrayList<>();
            AtomicInteger methodsInClass = new AtomicInteger();
            AtomicInteger fieldsInClass = new AtomicInteger();

            cu.findAll(CallableDeclaration.class).forEach(callableDeclaration -> {
                methodsInClass.getAndIncrement();
                methodList.add(callableDeclaration.getNameAsString());
            });
            methodCount= methodsInClass.get();

            cu.findAll(FieldDeclaration.class).forEach(field -> {
                field.getVariables().forEach(variable -> {
                    fieldsInClass.getAndIncrement();
                    fieldList.add(variable.getNameAsString());
                    Field makeField = new Field(variable.getNameAsString());
                    fieldObjects.add(makeField);
                });
            });
            fieldCount=fieldsInClass.get();
            fieldNames=fieldList;
            methodNames=methodList;


        index = 0;

            cu.findAll(ConstructorDeclaration.class).forEach(constructorDeclaration -> {
                VoidVisitor<Integer> visitor = new VisitConstructor();
                visitor.visit(constructorDeclaration, index);
            });

            cu.findAll(MethodDeclaration.class).forEach(methodDeclaration -> {
                VoidVisitor<Integer> visitor = new VisitMethod();
                visitor.visit(methodDeclaration, index);
            });
            index++;

            totalMF=0;
            for (Field f : fieldObjects) {

                totalMF = totalMF+f.getTotalMF();

            }

            DecimalFormat df = new DecimalFormat("#.##");
            float LCOM = methodCount*fieldCount;
            LCOM = totalMF/LCOM;
            LCOM = 1 - LCOM;
            String formatted = df.format(LCOM);

            String total="";
            String s1="The lack of cohesion of methods for the class is " + formatted+"\n";
            String s2="";
            if (LCOM < 0.5) {
                s2="This is an acceptable result.\n";
            }
            else if (LCOM >= 0.5) {
                s2="This result is too high. The methods in this class should be revised.\n";
            }

            total=s1+s2;
            return total;

    }

    /**
     * Class to create a Field object
     */
    public class Field {
        private String name;
        private int number;
        private int totalMF;

        public Field(String name) {
            this.name = name;
            this.number = number;
            this.totalMF = 0;
        }
        public String getName() {
            return name;
        }

        public int getNumber() {
            return number;
        }

        public int getTotalMF() {
            return totalMF;
        }

        public void incrementMF() {
            this.totalMF++;
        }
    }

    /**
     * This class visits a method and finds if
     * the method calls instance fields.
     */
    public class VisitMethod extends VoidVisitorAdapter<Integer> {
        @Override
        public void visit(MethodDeclaration n, Integer arg) {
            Optional<BlockStmt> body = n.getBody();
            String bodyString = body.toString();
            for (Field m : fieldObjects) {
                if (m.getNumber() == arg){
                    if (bodyString.contains(m.getName())) {
                        m.incrementMF();
                    }
                }
            }
        }
    }

    /**
     * This class visits a given constructor and finds if
     * the constructor calls instance fields.
     */
    public class VisitConstructor extends VoidVisitorAdapter<Integer> {
        @Override
        public void visit(ConstructorDeclaration n, Integer arg) {
            Optional<BlockStmt> body = Optional.ofNullable(n.getBody());
            String bodyString = body.toString();
            for (Field m : fieldObjects) {
                if (m.getNumber() == arg){
                    if (bodyString.contains(m.getName())) {
                        m.incrementMF();
                    }
                }
            }
        }
    }
}