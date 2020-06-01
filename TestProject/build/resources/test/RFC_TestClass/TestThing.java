//Student Author: Zachery Butler
//Student Number: C3232981
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 30/05/2020

package RFC_TestClass;

public class TestThing {

    private class subThing{
        private int value = 10;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

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
