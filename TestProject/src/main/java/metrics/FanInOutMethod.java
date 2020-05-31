package metrics;
/*
 * File name:    FanInOutMethod.java
 * Author:       Naneth Sayao
 * Date:         17 May 2020
 * Version:      2.4
 * Description:  An object that will hold:
 *                  - one method block
 *                  - record fan-in/fan-out related data
 * */


import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.util.ArrayList;
import java.util.List;

public class FanInOutMethod {
    /*
     * make a new object named fanInOutMethod that takes in:
     *      - string = method name
     *      - string = for the whole method block
     *      - array list = store names of the called external method
     *      - int = record calls to other method/s
     *      - string = for grading the measurement, i.e., excellent, good, bad, disaster
     *      - string = recommendation
     * */

    //////////////////////////////////////////////////( variables )///////////////////////////////////////////////////

    String  methodName,
            methodBlock,
            parentClass,
            evaluation; //evaluate if a method is reused, transferable, dead, etc.

    boolean isConstructor;

    ArrayList<String> calledMethodsList, callerList;//|NOTE: ASK THIS| do we skip same external method names?

    /////////////////////////////////////////////////( constructors )/////////////////////////////////////////////////

    public FanInOutMethod(){
        //initialise variables
        methodName = "";
        methodBlock = "";
        parentClass = "";
        evaluation= "";

        isConstructor = false;

        calledMethodsList = new ArrayList<>();
        callerList = new ArrayList<>();
    }

    ////////////////////////////////////////////////////( getters )////////////////////////////////////////////////////

    public String getMethodName() {
        return methodName;
    }

    public String getMethodBlock() {
        return methodBlock;
    }

    public String getParentClass() {
        return parentClass;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public boolean isConstructor() {

        return isConstructor;
    }

    public ArrayList<String> getCalledMethodsList() {
        return calledMethodsList;
    }

    public ArrayList<String> getCallerList() {
        return callerList;
    }

    ////////////////////////////////////////////////////( setters )////////////////////////////////////////////////////

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setMethodBlock(String methodBlock) {
        this.methodBlock = methodBlock;
    }

    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public void setConstructor(boolean constructor) {
        isConstructor = constructor;
    }
}