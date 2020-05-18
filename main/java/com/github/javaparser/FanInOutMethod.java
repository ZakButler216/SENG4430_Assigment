/*
 * File name:    FanInOutMethod.java
 * Author:       Naneth Sayao
 * Date:         17 May 2020
 * Version:      1.0
 * Description:  An object that will hold:
 *                  - one method block
 *                  - record fan-in/fan-out related data
 * */

package com.github.javaparser;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
            grade,
            recommendation;

    ArrayList<String> calledMethodsList;//|NOTE: ASK THIS| do we skip same external method names?

    /////////////////////////////////////////////////( constructors )/////////////////////////////////////////////////

    public FanInOutMethod(){
        //initialise variables
        methodName = "";
        methodBlock = "";
        grade = "";
        recommendation = "";

        calledMethodsList = new ArrayList<>();
    }
    ////////////////////////////////////////////////////( getters )////////////////////////////////////////////////////

    public String getMethodName() {
        return methodName;
    }

    public String getMethodBlock() {
        return methodBlock;
    }

    public String getGrade() {
        return grade;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public ArrayList<String> getCalledMethodsList() {
        return calledMethodsList;
    }

    ////////////////////////////////////////////////////( setters )////////////////////////////////////////////////////

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setMethodBlock(String methodBlock) {
        this.methodBlock = methodBlock;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public void setCalledMethodsList(ArrayList<String> calledMethodsList) {
        this.calledMethodsList = calledMethodsList;
    }

}
