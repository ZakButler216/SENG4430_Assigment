package metrics;
/*
 * File name:    LCOM.java
 * Author:       Tamara Wold
 * Date:         20 May 2020
 * Version:      1.0
 * Description:  Calculates the lack of cohesion of
 *               methods in a string object
 * */

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class LCOM {

    List<List<String>> fieldNames = new ArrayList<>();
    List<List<String>> methodNames = new ArrayList<>();
    List<Integer> methodCount = new ArrayList<>();
    List<Integer> fieldCount = new ArrayList<>();
    List<Field> fieldObjects = new ArrayList<>();
    private int index;
    List<Integer> totalMF = new ArrayList<>();

    public static void main(String[] args) {
        Path pathToSource = Paths.get("src/main/java/Test");
        SourceRoot sourceRoot = new SourceRoot(pathToSource);
        try {
            sourceRoot.tryToParse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<CompilationUnit> compilations = sourceRoot.getCompilationUnits();
        LCOM count = new LCOM();
        count.Counter(compilations);
    }

    public void Counter (List<CompilationUnit> compUnit) {
        /* Computes each compilation unit separately.
         * Finds all methods and instance fields for each unit.
         * Adds instance names to an array for that unit.
         */
        List<CompilationUnit> compilations = compUnit;
        for (int i = 0; i < compilations.size(); i++) {
            List<String> methodList = new ArrayList<>();
            List<String> fieldList = new ArrayList<>();
            AtomicInteger methodsInClass = new AtomicInteger();
            AtomicInteger fieldsInClass = new AtomicInteger();
            CompilationUnit cu = compilations.get(i);
            cu.findAll(CallableDeclaration.class).forEach(callableDeclaration -> {
                methodsInClass.getAndIncrement();
                methodList.add(callableDeclaration.getNameAsString());
            });
            methodCount.add(i, methodsInClass.get());
            int finalI = i;
            cu.findAll(FieldDeclaration.class).forEach(field -> {
                field.getVariables().forEach(variable -> {
                    fieldsInClass.getAndIncrement();
                    fieldList.add(variable.getNameAsString());
                    Field makeField = new Field(variable.getNameAsString(), finalI);
                    fieldObjects.add(makeField);
                });
            });
            fieldCount.add(i, fieldsInClass.get());
            fieldNames.add(fieldList);
            methodNames.add(methodList);
        }

        index = 0;
        for (CompilationUnit cU: compilations) {
            cU.findAll(ConstructorDeclaration.class).forEach(constructorDeclaration -> {
                VoidVisitor<Integer> visitor = new VisitConstructor();
                visitor.visit(constructorDeclaration, index);
            });

            cU.findAll(MethodDeclaration.class).forEach(methodDeclaration -> {
                VoidVisitor<Integer> visitor = new VisitMethod();
                visitor.visit(methodDeclaration, index);
            });
            index++;
        }

        for (int i = 0; i < compilations.size(); i++) {
            totalMF.add(i, 0);
            for (Field f : fieldObjects) {
                if (f.getNumber() == (i + 1)) {
                    int mf = totalMF.get(i);
                    mf = mf + f.getTotalMF();
                    totalMF.set(i, mf);
                }
            }
            //LCOM = 1 – (sum(MF)/M*F)
            double LCOM = 1 - (totalMF.get(i)/(methodCount.get(i)*fieldCount.get(i)));
            System.out.println("LCOM for class " + i + " : " + LCOM);
        }
    }

    public class Field {
        private String name;
        private int number;
        private int totalMF;

        public Field(String name, int number) {
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








