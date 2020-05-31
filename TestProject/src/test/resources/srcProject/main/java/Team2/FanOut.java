/*
 * File name:    FanOut.java
 * Author:       Naneth Sayao
 * Date:         24 May 2020
 * Version:      2.1
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
 * */

package Team2;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.util.ArrayList;
import java.util.List;

public class FanOut {
    public FanOut() {
    }

    public List<Integer> calculateFanOut(List<FanInOutMethod> methodsList){
        //populate this int array list with; # total called methods, and average fan out value
        List<Integer> result = new ArrayList<>();
        int totalCalledMethods = 0;
        int averageFanOut = 0;

        //if the file is invalid/ has code errors, or or there are no methods
        if(methodsList.size() <= 0){
            System.out.println( "No methods detected. \n");
            System.out.println("");
        }
        else{
            System.out.println("///////////////////////////////////////////( fan-out result )/////////////////////////////////////////////");
            System.out.println("Fan-out is the number of methods that are called by method X.\n");

            System.out.println("It is ideal to keep the fan-out value on average or below average. The lower the better for code reusability.");
            System.out.println("");

            System.out.println("//****************************( summary )****************************//");

            String format = "%-40s%s%n";

            for(int a = 0; a < methodsList.size(); a++){
                totalCalledMethods += methodsList.get(a).getCalledMethodsList().size();
            }
            System.out.printf(format, "Total number of called methods: ", totalCalledMethods);

            //avoid division by zero
            if(methodsList.size() > 0){
                averageFanOut = totalCalledMethods/methodsList.size();
                System.out.printf(format, "Average fan-out value: ", averageFanOut);
                System.out.println("");
            }
            //System.out.format("%32s%10d%16s", string1, int1, string2);
            System.out.println("//****************************( detailed )****************************//");
            String  tableFormat = "%20s%10s%10s",
                    mn = "Method Name",
                    cm = "# of Methods Called",
                    div = "____________________";
            System.out.format("%20s%10s%20s", mn, " | ", cm);
            System.out.println("");
            System.out.format(tableFormat, div, div, div);
            for(int b = 0; b < methodsList.size(); b++){
                String  methodName = methodsList.get(b).getMethodName();
                int numCalledMethods = methodsList.get(b).getCalledMethodsList().size();

                System.out.println("");
                System.out.format(tableFormat, methodName, " | ", numCalledMethods);
                System.out.println("");
                System.out.format(tableFormat, div, div, div);
                //System.out.println("Method Name: " + methodsList.get(b).getMethodName());
                //System.out.println("Number of methods called: " + methodsList.get(b).getCalledMethodsList().size() + "\n");
            }
            System.out.println("\n");
        }

        result.add(totalCalledMethods);
        result.add(averageFanOut);
        return result;
    }
}
