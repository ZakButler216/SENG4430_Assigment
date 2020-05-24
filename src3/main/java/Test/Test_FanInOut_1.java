/*
 * File name:    Test_FanInOut_1.java
 * Author:       Naneth Sayao
 * Date:         2 May 2020
 * Version:      2.0
 * Description:  A simple test file with:
 *                  - one class
 *                  - 4 methods
 * */

package Test;

import java.util.concurrent.atomic.AtomicInteger;

public class Test_FanInOut_1 {
    public void foo(Object param){
        System.out.println(1);
        System.out.println("hi");
        System.out.println(param);
    }

    public void bar() {
        System.out.println(8);
    }

    public void choco(){
        AtomicInteger a = new AtomicInteger();
    }

    public void empty(){

    }
}
