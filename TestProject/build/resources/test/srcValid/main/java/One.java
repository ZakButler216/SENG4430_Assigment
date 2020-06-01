/*
 * File name: One.java
 * Author:       Naneth Sayao
 * Date:         25 May 2020
 * Version:      1.0
 * Description:  A test file
 *                   - One.java will call a method from Two.java
 *                   - Two.java will call a method from Three.java
 *                   - Three.java will call a method from One.java
 * */
public class One {
    public One() {
    }

    public void oneMethod(){
        Two dos = new Two();
        dos.twoMethod();
    }

    public void oneMethod2(){
        Three tres = new Three();
        System.out.println("");
    }
}
