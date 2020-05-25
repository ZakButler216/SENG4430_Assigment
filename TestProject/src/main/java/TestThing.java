//Student Author: Zachery Butler
//Student Number: C3232981
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 25/05/2020

public class TestThing {

    private int num = 7;
    private String word = "Word!";

    public int getNum() {
        return num;
    }

    public String getWord() {
        return word;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setWord(String word) {
        this.word = word;
        this.getNum();
        this.getWord();
    }
}
