/*
a. Assignment # : In Class Assignment 1
b. File Name : 801091205_InClass01.zip
c. Name : Pawan Ramesh Bole.
*/

import java.util.HashSet;
import java.util.Set;

public class MainPart3 {
    /*
     * Question 3:
     * - In this question you will use the Data.users and Data.otherUsers array that includes
     * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
     * - Create a User class that should parse all the parameters for each user.
     * - The goal is to print out the users that are exist in both the Data.users and Data.otherUsers.
     * Two users are equal if all their attributes are equal.
     * - Print out the list of users which exist in both Data.users and Data.otherUsers.
     * */


    public static void main(String[] args) {

        Set<User> userSet = new HashSet<User>();
        for (String str : Data.users) {
            User user = new User(str);
            userSet.add(user);
        }
        Set<User> otherUserSet = new HashSet<User>();
        for (String str : Data.otherUsers) {
            User user = new User(str);

            if (!otherUserSet.contains(userSet)) {
                otherUserSet.add(user);
            }
        }
        System.out.println(otherUserSet);
    }
}
