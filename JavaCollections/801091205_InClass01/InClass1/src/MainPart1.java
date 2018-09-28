/*
a. Assignment # : In Class Assignment 1
b. File Name : 801091205_InClass01.zip
c. Name : Pawan Ramesh Bole.
*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainPart1 {
    /*
     * Question 1:
     * - In this question you will use the Data.users array that includes
     * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
     * - Create a User class that should parse all the parameters for each user.
     * - Insert each of the users in a list.
     * - Print out the TOP 10 oldest users.
     * */

    public static void main(String[] args) {

        List<User> userList = new ArrayList<User>();
        //example on how to access the Data.users array.
        for (String str : Data.users) {
            User user = new User(str);
            userList.add(user);
        }

        Collections.sort(userList, new Comparator<User>() {

            @Override
            public int compare(User o1, User o2) {
                return o2.age - o1.age;
            }
        });

        for (int i = 0; i < 10; i++) {
            System.out.println(userList.get(i).getFirstName() + " " + userList.get(i).getAge());
        }
    }
}