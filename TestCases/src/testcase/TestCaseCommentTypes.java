package testcase;

/**
 Test Criteria:
 Javadoc comments
 Line Comments
 Block Comments
 Comments with lines in them
 Comments without empty lines
 */
public class TestCaseCommentTypes {

    private int number;

    /* comment here
    This is orphan comment
    All orphan comments are either line comment or block comment or javadoc comment





     */

    //this is the main function
    public static void main(String[] args) {

        //a has the initial number
        int a = 0;

        //b returns the result
        int b = add(a);


        /**
         This enables the system
         to print
         the result


         */
        System.out.println(b);
        //


        /**
         *
         *
         */

        /*

        //

         */
    }

    //this is the add function
    public static int add(int a) {
        a = a+1;
        return a;
    }
}
