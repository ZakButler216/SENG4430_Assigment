/*
 * File name:    FanIn.java
 * Author:       Naneth Sayao
 * Date:         24 May 2020
 * Version:      2.0
 * Description:  Fan-in is a software metrics that means:
 *                  - a measure of the number of functions or
 *                      methods that calls another function or method.
 *                      - Reference for fan-in and fan-out descriptions is
 *                          the week 2 notes on UoN Blackboard
 *                          "SQ02 Inspections and Metrics" PowerPoint. Page 32.
 *                     - Reference: https://www.aivosto.com/project/help/pm-sf.html
 *                          States the below explanations
 *                     - number of files that depend on this file
 *                     - number of procedures that call this procedure
 *                     - Structural fan-in (SFIN) and reuse
 *                          A SFIN value of 2 or more indicates reused code.
 *                          The higher the fan-in, the more reuse.
 *
 *                          A high SFIN is desirable for procedures because it indicates
 *                          a routine that is called from many locations. Thus, it is
 *                          reused, which is usually a good objective.
 *
 *                          A high SFIN is not as desirable for a file. While it can
 *                          indicate good reuse, it also represents a high level of
 *                          cross-file coupling. SFIN for a file should be "reasonable".
 *                          We leave the definition of "reasonable" to be determined case by case.
 *                      - SFIN and inlining
 *                          A special use for procedure-level SFIN is the detection of procedures
 *                          that could be inlined. If a procedure's SFIN is low but positive, it
 *                          has a small number of callers. Depending on what the procedure does
 *                          and how complex it is, you could possibly embed it within the caller(s).
 *                          This is a speed optimization technique. We don't recommend inlining for
 *                          usual coding because keeping the code modular improves reuse and legibility.
 * */

package Team2;

import java.util.List;
import Team2.FanInOutMethod;

public class FanIn {

    public FanIn() {
    }

    public void calculateFanIn(List<FanInOutMethod> methodsList){
        System.out.println("///////////////////////////////////////////( fan-in result )/////////////////////////////////////////////");
        System.out.println("Zero caller may or may not indicate a dead method code.");
        System.out.println("One caller makes the method a candidate to be moved inside the caller method for optimisation.");
        System.out.println("Two or more callers indicate reuse.");
        System.out.println("A high the number of callers may mean: \n - good for reuse \n - bad for cross-file coupling \n");

        System.out.println("//****************************( summary )****************************//");
        int methodCallerCount = 0;
        int oneCaller = 0;
        int twoOrMoreCaller = 0;
        for(int s = 0; s < methodsList.size(); s++){
            //if called methods ArrayList size is greater than zero, increment methodCallerCount
            if(methodsList.get(s).getCalledMethodsList().size() > 0){
                methodCallerCount++;
            }

            //see if method has 1 or more than 2 callers
            if(methodsList.get(s).getCalledMethodsList().size() == 1){
                //if only 1 caller
                oneCaller++;
            }
            else if(methodsList.get(s).getCalledMethodsList().size() >= 2){
                twoOrMoreCaller++;
            }

            //how many called a specific method?
            //save the name of this current method
            String name = methodsList.get(s).getMethodName();
            //iterate each method in methodsList
            for(int i = 0; i < methodsList.size(); i++){
                //and then iterate again on their called methods list
                for(int j = 0; j < methodsList.get(i).getCalledMethodsList().size(); j++){
                    //compare the name of this method and the called methods
                    if(name.equals(methodsList.get(i).getCalledMethodsList().get(j))){
                        //if there is a match, save the caller name on the callerList of the FanInOutMethod object of this method
                        methodsList.get(i).getCallerList().add(methodsList.get(i).getCalledMethodsList().get(j));
                    }

                }
            }

        }

        System.out.println("Total Number of methods: " + methodsList.size());
        System.out.println("Number of methods that called a method: " + methodCallerCount);
        int deadMethods = methodsList.size() - methodCallerCount;
        System.out.println("Number of possibly dead methods: " + deadMethods);
        System.out.println("Number of methods that can be transferred to the caller: " + oneCaller);
        System.out.println("Number of reused methods: " + twoOrMoreCaller + "\n");
        System.out.println("//****************************( detailed )****************************//");

        //iterate on the methods list and print how many callers each method has
        for(int k = 0; k < methodsList.size(); k++){
            System.out.println("Method Name: " + methodsList.get(k).getMethodName());
            System.out.println("Number of callers of " + methodsList.get(k).getMethodName() + ": " + methodsList.get(k).getCallerList().size());

            for(int d = 0; d < methodsList.get(k).getCallerList().size(); d++){
                System.out.println(" - " + methodsList.get(k).getCallerList().get(d));
            }

            System.out.println("");
        }

        System.out.println("");

    }
}
