/*
 * File name:    FanIn.java
 * Author:       Naneth Sayao
 * Date:         24 May 2020
 * Version:      1.0
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

import java.util.List;

public class FanOut {
    public FanOut() {
    }

    public void calculateFanOut(List<FanInOutMethod> methodsList){
        System.out.println("///////////////////////////////////////////( fan-out result )/////////////////////////////////////////////");
        System.out.println("It is ideal to keep the fan-out value on average or below average. The lower the better for code reusability.");
        System.out.println("");

        System.out.println("//****************************( summary )****************************//");
        int totalCalledMethods = 0;
        for(int a = 0; a < methodsList.size(); a++){
            totalCalledMethods += methodsList.get(a).getCalledMethodsList().size();
        }
        System.out.println("Total number of called methods: " + totalCalledMethods);

        //avoid division by zero
        if(methodsList.size() > 0){
            int averageFanOut = totalCalledMethods/methodsList.size();
            System.out.println("Average fan-out value: " + averageFanOut);
            System.out.println("");
        }

        System.out.println("//****************************( detailed )****************************//");
        for(int b = 0; b < methodsList.size(); b++){
            System.out.println("Method Name: " + methodsList.get(b).getMethodName());
            System.out.println("Number of methods called: " + methodsList.get(b).getCalledMethodsList().size() + "\n");
        }
        System.out.println("");
    }
}
