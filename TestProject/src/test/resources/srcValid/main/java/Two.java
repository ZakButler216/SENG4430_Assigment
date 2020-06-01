/*
 * File name: Two.java
 * Author:       Naneth Sayao
 * Date:         25 May 2020
 * Version:      1.0
 * Description:  A test file
 *                   - One.java will call a method from Two.java
 *                   - Two.java will call a method from Three.java
 *                   - Three.java will call a method from One.java
 * */
public class Two {
    public Two() {
    }

    public void twoMethod(){
        Three tres = new Three();
        tres.threeMethod();
    }
}