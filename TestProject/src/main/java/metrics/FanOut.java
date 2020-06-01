/*
 * File name:    FanOut.java
 * Author:       Naneth Sayao
 * Date:         24 May 2020
 * Version:      2.4
 * Description:  Fan-out is a software metrics that means:
 *                  - the number of functions that are called by
 *                      function X.
 *                      - Reference for fan-in and fan-out descriptions is
 *                          the week 2 notes on UoN Blackboard
 *                          "SQ02 Inspections and Metrics" PowerPoint. Page 32.
 *                   - Reference: https://www.aivosto.com/project/help/pm-sf.html
 *                          States the below explanations
 *                   - number of files this file depends on
 *                   - number of procedures this procedure calls
 *                   - SFOUT and coupling
 *                          A high SFOUT denotes strongly coupled code. The code
 *                          depends on other code and is probably more complex to execute and test.
 *
 *                          A low or zero fan-out means independent, self-sufficient code. This kind
 *                          of code is easier to reuse in another project or for another purpose. A
 *                          file whose SFOUT=0 is a leaf file in the project. You can include it in
 *                          another project as such and it will most probably continue to work the same way.
 *
 *                          To evaluate the average coupling between files, monitor the average SFOUT/file
 *                          value. This is the average of "how many other files my files depend on". Try to
 *                          keep this value low. Achieving a low cross-file coupling should be done via
 *                          restructuring and planning. It should not be achieved by just mechanically joining
 *                          files — so keep an eye on the file sizes as well. Notice that SFOUT/file is likely
 *                          to be higher in a large system because the parts of the system need to interact with
 *                          each other. As your project grows, SFOUT/file is likely to increase even if your
 *                          code is well designed.
 *                  - other resources:
 *                          - https://portal.tiobe.com/8.5/docs/metrics/index.html
 * */

package metrics;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FanOut {
    private String report;

    public FanOut() {
        report = "";
    }

    //getter for the summary and detailed report after calculation
    public String getReport(List<FanInOutMethod> methodsList) {
        calculateFanOut(methodsList);
        return report;
    }

    public List<Integer> calculateFanOut(List<FanInOutMethod> methodsList){
        //populate this int array list with; # total called methods, and average fan out value
        List<Integer> result = new ArrayList<>();
        int totalCalledMethods = 0;
        int averageFanOut = 0;
        double average = 0;
        report = ""; //clear report

        //if the file is invalid/ has code errors, or or there are no methods
        if(methodsList.size() <= 0){
            report = "No methods detected.";
        }
        else{
            report += "\n///////////////////////////////////////////( fan-out result )/////////////////////////////////////////////" +
                    "\nFan-out is the number of methods that are called by method X.\n"                                                     +
                    "\nIt is ideal to keep the fan-out value on average or below average. "                                                 +
                    "\nThe lower fan-out value, the better for code maintainability and reusability."                                       +
                    "\n//****************************( summary )****************************//\n";

            String format = "%-40s%s%n";

            for(int a = 0; a < methodsList.size(); a++){
                totalCalledMethods += methodsList.get(a).getCalledMethodsList().size();
            }
            report += String.format(format, "Total number of called methods: ", totalCalledMethods);
            report += String.format(format, "Total number of methods: ", methodsList.size());

            //avoid division by zero
            if(methodsList.size() > 0){
                average = totalCalledMethods/methodsList.size();
                averageFanOut = totalCalledMethods/methodsList.size();
                report += String.format(format, "Average fan-out value: ", average);
                report += "\n";
            }

            report += "\n//****************************( detailed )****************************//\n";
            String  tableFormat = "%30s%10s%20s",
                    mn = "Method Name + Grade",
                    cm = "Called Methods",
                    div = "___________________________";

            report += String.format("%30s%10s%20s", mn, " | ", cm);
            report += "\n";
            report += String.format(tableFormat, div, div, div + "\n");
            //iterate on the methods list and print how many callers and called each method has.
            for(int k = 0; k < methodsList.size(); k++){
                String methodName = methodsList.get(k).getMethodName();
                int called = methodsList.get(k).getCalledMethodsList().size();

                //see if this method is a constructor
                if(methodsList.get(k).isConstructor == true){
                    methodName += " (constructor)";
                }
                report += String.format(tableFormat, methodName, " | ", "total: " + called);

                //calculate grade. Formula = (average / number of called methods) * 100
                DecimalFormat df = new DecimalFormat("####0"); //format to 2 decimal places
                double grade = (average / called) * 100;

                //evaluate the grade
                String evalGrade = "";
                if(grade < 50){
                    evalGrade = " Not good...";
                }
                else if(grade < 55){
                    evalGrade = " Pass.";
                }
                else if(grade < 65){
                    evalGrade = " Credit.";
                }
                else if(grade < 75){
                    evalGrade = " Nice.";
                }
                else if(grade < 85){
                    evalGrade = " Good.";
                }
                else if(grade < 95){
                    evalGrade = " Great.";
                }
                else if(grade < 101){
                    evalGrade = " Perfect!";
                }
                else{
                    evalGrade = " BRILLIAN!";
                }

                //if called methods list is empty, there will be no iteration to print grade. Print it separately.
                if(called == 0){
                    report += "\n";
                    report += String.format(tableFormat, "1000% BRILLIAN!", " | ", "");
                }
                else{
                    for(int e = 0; e < methodsList.get(k).getCalledMethodsList().size(); e++){
                        report += "\n";
                        //only print grade on first iteration
                        if(e == 0){
                            report += String.format(tableFormat, df.format(grade)+"%" + evalGrade, " | ", methodsList.get(k).getCalledMethodsList().get(e));
                        }
                        else{
                            report += String.format(tableFormat, "", " | ", methodsList.get(k).getCalledMethodsList().get(e));
                        }
                    }
                }


                report += "\n";
                report += String.format(tableFormat, div, div, div, div, div);
                report += "\n";
            }
        }

        result.add(totalCalledMethods);
        result.add(averageFanOut);
        return result;
    }
}