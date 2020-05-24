package Team2;

import java.util.List;
import Team2.FanInOutMethod;

public class FanIn {

    public FanIn() {
    }

    public void calculateFanIn(List<FanInOutMethod> methodsList){
        System.out.println("///////////////////////////////////////////( fan-in result )/////////////////////////////////////////////");

        int methodCallerCount = 0;
        for(int s = 0; s < methodsList.size(); s++){
            //if called methods ArrayList size is greater than zero, increment methodCallerCount
            if(methodsList.get(s).getCalledMethodsList().size() > 0){
                methodCallerCount++;
            }
            //System.out.println("Method Name: " + methodsList.get(a).getMethodName());
            //System.out.println("Number of methods called: " + methodsList.get(a).getCalledMethodsList().size());
        }
        System.out.println("Total Number of methods: " + methodsList.size());
        System.out.println("Number of methods that called a method: " + methodCallerCount);
        System.out.println("");
    }
}
