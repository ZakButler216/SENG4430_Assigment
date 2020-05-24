package Team2;

import java.util.List;

public class FanOut {
    public FanOut() {
    }

    public void calculateFanOut(List<FanInOutMethod> methodsList){
        System.out.println("///////////////////////////////////////////( fan-out result )/////////////////////////////////////////////");

        for(int a = 0; a < methodsList.size(); a++){
            System.out.println("Method Name: " + methodsList.get(a).getMethodName());
            System.out.println("Number of methods called: " + methodsList.get(a).getCalledMethodsList().size() + "\n");
        }
        System.out.println("");
    }
}
