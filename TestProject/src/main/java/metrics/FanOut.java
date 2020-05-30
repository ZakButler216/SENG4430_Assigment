package metrics;
/*
 * File name:    FanIn.java
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

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.util.ArrayList;
import java.util.List;

public class FanOut {

    public String getOutputResult() {
        return outputResult;
    }

    public void setOutputResult(String outputResult) {
        this.outputResult = outputResult;
    }

    private String outputResult;
    public FanOut() {
        String outputResult = "";
    }

    public List<Integer> calculateFanOut(List<FanInOutMethod> methodsList){

        CompilationUnit cu = Parser.getStoredCurrentCompilationUnit();
        Parser parser = new Parser();



        //populate this int array list with; # total called methods, and average fan out value
        List<Integer> result = new ArrayList<>();
        int totalCalledMethods = 0;
        int averageFanOut = 0;

        String sTotal="";
        //if the file is invalid/ has code errors, or or there are no methods
        if(methodsList.size() <= 0){
            //System.out.println( "No methods detected. \n");
            String a1 = "No methods detected. \n\n";

            //System.out.println("");
            String a2 = "\n";

            sTotal = a1+a2;
        }
        else{
            //System.out.println("///////////////////////////////////////////( fan-out result )/////////////////////////////////////////////");
            String s1 = "///////////////////////////////////////////( fan-out result )/////////////////////////////////////////////\n";

            //System.out.println("Fan-out is the number of methods that are called by method X.\n");
            String s2 = "Fan-out is the number of methods that are called by method X.\n\n";

            //System.out.println("It is ideal to keep the fan-out value on average or below average. The lower the better for code reusability.");
            String s3 = "It is ideal to keep the fan-out value on average or below average. The lower the better for code reusability.\n";

            //System.out.println("");
            String s4 = "\n";

            System.out.println("//****************************( summary )****************************//");
            String s5 = "//****************************( summary )****************************//\n";

            String format = "%-40s%s%n";

            for(int a = 0; a < methodsList.size(); a++){
                //System.out.println(methodsList.get(a).getMethodName());
                totalCalledMethods += methodsList.get(a).getCalledMethodsList().size();
            }
            //System.out.printf(format, "Total number of called methods: ", totalCalledMethods);
            String s6 = String.format(format,"Total number of called methods: ",totalCalledMethods);


            String s7="";
            String s8="";
            //avoid division by zero
            if(methodsList.size() > 0){
                averageFanOut = totalCalledMethods/methodsList.size();
                //System.out.printf(format, "Average fan-out value: ", averageFanOut);
                s7 = String.format(format,"Average fan-out value: ", averageFanOut);

                //System.out.println("");
                s8="\n";
            }

            //System.out.println("//****************************( detailed )****************************//");
            String s9="//****************************( detailed )****************************//\n";

            String  tableFormat = "%30s%10s%10s",
                    mn = "Method Name",
                    cm = "# of Methods Called",
                    div = "____________________";
            //System.out.format("%30s%10s%20s", mn, " | ", cm);
            String s10 = String.format("%30s%10s%20s", mn, " | ", cm);

            //System.out.println("");
            String s11 = "\n";

            //System.out.format(tableFormat, div, div, div);
            String s12 = String.format(tableFormat, div, div, div);

            String s13 ="";

            for(int b = 0; b < methodsList.size(); b++){
                String  methodName = methodsList.get(b).getMethodName();
                int numCalledMethods = methodsList.get(b).getCalledMethodsList().size();

                //System.out.println("");
                String m1 = "\n";

                //System.out.format(tableFormat, methodName, " | ", numCalledMethods);
                String m2 = String.format(tableFormat, methodName, " | ", numCalledMethods);

                //System.out.println("");
                String m3 = "\n";

                //System.out.format(tableFormat, div, div, div);
                String m4= String.format(tableFormat, div, div, div);

                //(Already commented out by Naneth)
                //System.out.println("Method Name: " + methodsList.get(b).getMethodName());
                //System.out.println("Number of methods called: " + methodsList.get(b).getCalledMethodsList().size() + "\n");

                String methodTotal = m1+m2+m3+m4;
                s13 += methodTotal;

            }

            //System.out.println("");
            String s14="\n";

            sTotal = s1+s2+s3+s4+s5+s6+s7+s8+s9+s10+s11+s12+s13+s14;


        }

        setOutputResult(sTotal);

        result.add(totalCalledMethods);
        System.out.println("Total Called Methods is "+totalCalledMethods);
        result.add(averageFanOut);
        System.out.println("Average Fan Out is "+averageFanOut);
        return result;
    }




}