package metrics;
/*
 * File name:    FanIn.java
 * Author:       Naneth Sayao
 * Date:         24 May 2020
 * Version:      4.4
 * Description:  Fan-in is a software metrics that means:
 *                  - a measure of the number of functions or
 *                      methods that call function X.
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
 *                  - other resources:
 *                          - https://portal.tiobe.com/8.5/docs/metrics/index.html
 * */



import java.util.ArrayList;
import java.util.List;

public class FanIn {
    private String report;

    public FanIn() {
        report = "";
    }

    //getter for the summary and detailed report after calculation
    public String getReport(List<FanInOutMethod> methodsList, String currentClassName) {
        calculateFanIn(methodsList, currentClassName);
        return report;
    }

    public List<Integer> calculateFanIn(List<FanInOutMethod> methodsList, String currentClassName){
        /*
         * populate this int array list with;
         *   # total methods,
         *   # methods that called a method,
         *   # possibly dead methods,
         *   # transferable methods, and
         *   # reused methods.
         * */
        List<Integer> result = new ArrayList<>();
        List<FanInOutMethod> methodsOfCurrentClass = new ArrayList<>(); //collect methods of current class only
        int oneCaller = 0;
        int twoOrMoreCaller = 0;
        int deadMethods = 0;
        report = ""; // clear out the report string

        String format = "%-40s%s%n";

        //if the file is invalid/ has code errors, or or there are no methods
        if(methodsList.size() > 0){
            report += ("\n///////////////////////////////////////////( fan-in result )/////////////////////////////////////////////"  +
                    "\nFan-in is the number of methods that calls another method. \nIn short, the number of callers that a method has.\n"   +
                    "\nZero caller may or may not indicate a dead method code."                                                             +
                    "\nOne caller makes the method a candidate to be moved inside the caller method for optimisation."                      +
                    "\nTwo or more callers indicate reuse."                                                                                 +
                    "\nA high the number of callers may mean: \n - good for reuse \n - bad for cross-file coupling \n"                      +
                    "\n//****************************( summary )****************************//\n");

            //iterate methodsList to find out total number of a method's caller (#caller number)
            for(int u = 0; u < methodsList.size(); u++){
                //save this current method (called method)
                FanInOutMethod calledMethod = methodsList.get(u);
                //iterate each method in methodsList to find caller of 'calledName'
                for(int i = 0; i < methodsList.size(); i++){
                    //save the name of the caller method
                    FanInOutMethod callerMethod = methodsList.get(i);
                    //and then iterate on their called methods list to see if 'calledMethod' was called
                    for(int j = 0; j < callerMethod.getCalledMethodsList().size(); j++){
                        //compare the name of calledMethod to the called methods of callerMethod
                        if(calledMethod.getMethodName().equals(callerMethod.getCalledMethodsList().get(j))){
                            //if there is a match, add name of callerMethod to caller list of calledMethod
                            calledMethod.getCallerList().add(callerMethod.getMethodName());
                        }

                    }
                }
            }

            //iterate each method in methodsList to update summary variables
            for(int s = 0; s < methodsList.size(); s++){
                //we only want to count for the methods in the current class
                if(methodsList.get(s).getParentClass().equals(currentClassName)){
                    //update methodsOfCurrentClass
                    methodsOfCurrentClass.add(methodsList.get(s));

                    //see if method has 1 or more than 2 callers
                    // if only 1 caller
                    //update evaluation
                    if(methodsList.get(s).getCallerList().size() == 1){
                        //count how many methods are transferable
                        //transferable methods only have one caller
                        oneCaller++;
                        methodsList.get(s).setEvaluation("(Eval: Maybe transferable.)");
                    }
                    else if(methodsList.get(s).getCallerList().size() >= 2){
                        //count how many methods are reused
                        //reused methods have two or more callers
                        twoOrMoreCaller++;
                        methodsList.get(s).setEvaluation("(Eval: Good reusability.)");
                    }

                    //dead methods are methods without callers
                    //counts number of methods only
                    else if(methodsList.get(s).getCallerList().size() == 0){
                        //check if if(){
                        deadMethods++;
                        methodsList.get(s).setEvaluation("(Eval: Dead method.)");
                    }
                }
            }

            report += String.format(format, "Total Number of methods: ", methodsOfCurrentClass.size());
            report += String.format(format, "Number of possibly dead methods: ", deadMethods);
            report += String.format(format, "Number of transferable methods : ", oneCaller);
            report += String.format(format, "Number of reused methods: ", twoOrMoreCaller + "\n");

            report += "\n//****************************( detailed )****************************//\n";
            String  tableFormat = "%30s%10s%20s",
                    mn = "Method Name + Evaluation",
                    c = "Callers",
                    div = "__________________________";

            report += String.format("%30s%10s%20s", mn, " | ", c);
            report += "\n";
            report += String.format(tableFormat, div, div, div + "\n");

            //iterate on the methods list and print how many callers and called each method has.
            for(int k = 0; k < methodsOfCurrentClass.size(); k++){
                String methodName = methodsOfCurrentClass.get(k).getMethodName();
                //is it a constructor?
                if(methodsOfCurrentClass.get(k).isConstructor == true){
                    methodName += " (constructor)";
                }
                int callers = methodsOfCurrentClass.get(k).getCallerList().size();
                String evaluation = methodsOfCurrentClass.get(k).getEvaluation();

                report += String.format(tableFormat, methodName, " | ", "total: " + callers);

                //if caller size is zero, can't print evaluation through iteration. Print it separately.
                if(callers == 0){
                    report += "\n";
                    report += String.format(tableFormat, evaluation, " | ", "");
                }
                else{
                    //iterate on the caller list and print all callers of the current method
                    for(int d = 0; d < methodsOfCurrentClass.get(k).getCallerList().size(); d++){
                        report += "\n";
                        //print evaluation on first iteration
                        if(d == 0){
                            report += String.format(tableFormat, evaluation, " | ", methodsOfCurrentClass.get(k).getCallerList().get(d));
                        }
                        else{
                            report += String.format(tableFormat, "", " | ", methodsOfCurrentClass.get(k).getCallerList().get(d));
                        }

                    }
                }

                report += "\n";
                report += String.format(tableFormat, div, div, div, div, div);
                report += "\n";
            }
            report += "\n";
        }
        else{
            report += "\nPlease ensure that the Java file/s have no error and that \n" +
                    "there is at least one method in the Java file/s.\n";
        }

        result.add(methodsOfCurrentClass.size());
        result.add(deadMethods);
        result.add(oneCaller);
        result.add(twoOrMoreCaller);
        return result;
    }
}