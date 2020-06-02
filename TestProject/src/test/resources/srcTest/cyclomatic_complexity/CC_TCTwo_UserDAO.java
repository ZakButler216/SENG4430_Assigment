package cyclomatic_complexity;

import java.util.*;

public class CC_TCTwo_UserDAO {

    //Counters for users added throughout history. Remain same even after a user is deleted.

    private static int userCounter;

    private static ArrayList<CC_TCTwo_UserBean> usersList;

    static {
        usersList = new ArrayList<>();
        userCounter=0;
    }

    public CC_TCTwo_UserDAO() {

    }

    public static ArrayList<CC_TCTwo_UserBean> getUsersList() {
        return usersList;
    }

    public static void setUsers(ArrayList<CC_TCTwo_UserBean> in) {
        usersList = in;
    }

    public static int getUserCounter() {
        return userCounter;
    }

    public static void setUserCounter(int counter) {
        userCounter=counter;
    }

    public static void addUsers(CC_TCTwo_UserBean u) {

        //create staff id (i.e. b1,b2,b3,b4,b5)
        String append="b";

        int tempCounter=userCounter+1;
        userCounter++;

        String stringCounter=String.valueOf(tempCounter);
        String id=append+stringCounter;

        u.setStaffID(id);

        //default password given by system is password. user can then change it themselves.
        u.setPassword("password");


        usersList.add(u);

    }


    public static CC_TCTwo_UserBean getUserByStaffID(String id) {
        CC_TCTwo_UserBean user = new CC_TCTwo_UserBean();
        for(int i=0;i<usersList.size();i++) {
            if(usersList.get(i).getStaffID().equalsIgnoreCase(id)) {
                user = usersList.get(i);
            }
        }

        return user;
    }

    public static void deleteUserByUserID(String id) {
        for(int i=0;i<usersList.size();i++) {
            if(usersList.get(i).getStaffID().equalsIgnoreCase(id)) {
                usersList.remove(i);
                break;
            }
        }
    }

    public static int getUsersListSize() {
        return usersList.size();
    }


}
