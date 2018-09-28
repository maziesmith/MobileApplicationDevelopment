/*
a. Assignment # : In Class Assignment 1
b. File Name : 801091205_InClass01.zip
c. Name : Pawan Ramesh Bole.
*/

import java.util.*;

public class MainPart2 {
    /*
     * Question 2:
     * - In this question you will use the Data.users array that includes
     * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
     * - Create a User class that should parse all the parameters for each user.
     * - The goal is to count the number of users living each state.
     * - Print out the list of State, Count order in ascending order by count.
     * */


    public static void main(String[] args) {

        List<User> userList = new ArrayList<User>();
        Map<String, Integer> stateCount = new HashMap<>();

        for (String str : Data.users) {
            User user = new User(str);
            userList.add(user);

            if (stateCount.containsKey(user.getState())) {
                stateCount.put(user.getState(), stateCount.get(user.getState()) + 1);
            } else {
                stateCount.put(user.getState(), 1);
            }
        }

        List<Map.Entry<String,Integer>> stateList = new ArrayList<Map.Entry<String, Integer>>(stateCount.entrySet());

        Collections.sort(stateList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        });

        System.out.println(stateList);
    }
}
