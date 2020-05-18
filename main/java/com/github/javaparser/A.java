/*
 * File name:    A.java
 * Author:       Naneth Sayao
 * Date:         2 May 2020
 * Version:      2.0
 * Description:  This is a simple test file.
 * */

package com.github.javaparser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class A {
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

    public void empty(){}
}
